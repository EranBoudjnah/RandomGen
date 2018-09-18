package com.mitteloupe.randomgen;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class RandomGen<GENERATED_INSTANCE> implements FieldDataProvider<Object, GENERATED_INSTANCE> {
	private final InstanceProvider<GENERATED_INSTANCE> mInstanceProvider;
	private final Map<String, FieldDataProvider<GENERATED_INSTANCE, ?>> mDataProviders;
	private final Map<String, Field> mFields;
	private final List<OnGenerateCallback<GENERATED_INSTANCE>> mOnGenerateCallbacks;

	private RandomGen(
		InstanceProvider<GENERATED_INSTANCE> pInstanceProvider,
		Map<String, FieldDataProvider<GENERATED_INSTANCE, ?>> pDataProviders,
		List<OnGenerateCallback<GENERATED_INSTANCE>> pOnGenerateCallbacks
	) {
		mInstanceProvider = pInstanceProvider;
		mDataProviders = pDataProviders;
		mFields = new HashMap<>();
		mOnGenerateCallbacks = pOnGenerateCallbacks;

		getAllFields();
	}

	public GENERATED_INSTANCE generate() {
		return generate(null);
	}

	@Override
	public GENERATED_INSTANCE generate(Object unused) {
		final GENERATED_INSTANCE instance = mInstanceProvider.provideInstance();

		setAllFields(instance);

		notifyOnGenerateCallbacks(instance);

		return instance;
	}

	private void setAllFields(GENERATED_INSTANCE pInstance) {
		for (final Map.Entry<String, FieldDataProvider<GENERATED_INSTANCE, ?>> entry : mDataProviders.entrySet()) {
			final String key = entry.getKey();
			if (!mFields.containsKey(key)) {
				throw new IllegalArgumentException("Cannot set field " + key + " - field not found");
			}

			final Field field = mFields.get(key);

			final Object value = entry.getValue().generate(pInstance);

			try {
				setField(pInstance, field, value);
			} catch (AssignmentException exception) {
				throw new IllegalArgumentException("Cannot set field " + key + " due to invalid value", exception);
			}
		}
	}

	private void setField(GENERATED_INSTANCE pInstance, Field pField, Object pValue) throws IllegalArgumentException {
		Object valueToSet = isFieldArray(pField) ?
			getValueAsArray(pField, pValue) :
			pValue;

		setFieldToRawValue(pInstance, pField, valueToSet);
	}

	private boolean isFieldArray(Field pField) {
		return pField.getType().isArray();
	}

	private Object getValueAsArray(Field pField, Object pValue) {
		if (!Collection.class.isAssignableFrom(pValue.getClass())) {
			throw new AssignmentException(new RuntimeException("Expected collection value"));
		}

		List valueAsList = (List) pValue;
		TypedArray typedArray = new TypedArray<>(pField.getType().getComponentType(), valueAsList.size());

		try {
			return valueAsList.toArray(typedArray.get());
		} catch (ArrayStoreException exception) {
			throw new AssignmentException(exception);
		}
	}

	private void setFieldToRawValue(GENERATED_INSTANCE pInstance, Field pField, Object pValue) {
		try {
			pField.set(pInstance, pValue);
		} catch (IllegalAccessException | IllegalArgumentException exception) {
			throw new AssignmentException(exception);
		}
	}

	private void notifyOnGenerateCallbacks(GENERATED_INSTANCE pInstance) {
		for (OnGenerateCallback<GENERATED_INSTANCE> onGenerateCallbacks : mOnGenerateCallbacks) {
			onGenerateCallbacks.onGenerate(pInstance);
		}
	}

	private void getAllFields() {
		GENERATED_INSTANCE instance = mInstanceProvider.provideInstance();
		Field[] allFields = instance.getClass().getDeclaredFields();
		for (Field field : allFields) {
			if (Modifier.isPrivate(field.getModifiers())) {
				field.setAccessible(true);
			}

			mFields.put(field.getName(), field);
		}
	}

	public static class Builder<GENERATED_INSTANCE> {
		public BuilderField<GENERATED_INSTANCE> ofClass(Class<GENERATED_INSTANCE> pClass) {
			return new BuilderField<>(pClass, new DefaultFieldDataProviderFactory<GENERATED_INSTANCE>());
		}

		BuilderField<GENERATED_INSTANCE> ofClassWithFactory(@SuppressWarnings("SameParameterValue") Class<GENERATED_INSTANCE> pClass,
		                                                    FieldDataProviderFactory<GENERATED_INSTANCE> pFactory) {
			return new BuilderField<>(pClass, pFactory);
		}

		public BuilderField<GENERATED_INSTANCE> withProvider(InstanceProvider<GENERATED_INSTANCE> pInstanceProvider) {
			return new BuilderField<>(pInstanceProvider, new DefaultFieldDataProviderFactory<GENERATED_INSTANCE>());
		}

		BuilderField<GENERATED_INSTANCE> withProviderAndFactory(InstanceProvider<GENERATED_INSTANCE> pInstanceProvider,
		                                                        FieldDataProviderFactory<GENERATED_INSTANCE> pFactory) {
			return new BuilderField<>(pInstanceProvider, pFactory);
		}

		private static class DefaultFieldDataProviderFactory<GENERATED_INSTANCE> extends SimpleFieldDataProviderFactory<GENERATED_INSTANCE> {
			private DefaultFieldDataProviderFactory() {
				super(new Random(), new DefaultUuidGenerator());
			}
		}

		private static class DefaultUuidGenerator implements UuidGenerator {
			@Override
			public String randomUUID() {
				return UUID.randomUUID().toString();
			}
		}
	}

	public static class BuilderField<GENERATED_INSTANCE> {
		private final Map<String, FieldDataProvider<GENERATED_INSTANCE, ?>> mDataProviders;
		private final FieldDataProviderFactory<GENERATED_INSTANCE> mFactory;
		private InstanceProvider<GENERATED_INSTANCE> mInstanceProvider;
		private Class<GENERATED_INSTANCE> mClass;
		private List<OnGenerateCallback<GENERATED_INSTANCE>> mOnGenerateCallbacks = new ArrayList<>();

		BuilderField(Class<GENERATED_INSTANCE> pClass,
		             FieldDataProviderFactory<GENERATED_INSTANCE> pFactory) {
			this(pFactory);

			mClass = pClass;
		}

		BuilderField(InstanceProvider<GENERATED_INSTANCE> pInstanceProvider,
		             FieldDataProviderFactory<GENERATED_INSTANCE> pFactory) {
			this(pFactory);

			mInstanceProvider = pInstanceProvider;
		}

		private BuilderField(FieldDataProviderFactory<GENERATED_INSTANCE> pFactory) {
			mDataProviders = new LinkedHashMap<>();
			mFactory = pFactory;
		}

		public RandomGen<GENERATED_INSTANCE> build() {
			if (mInstanceProvider == null) {
				mInstanceProvider = new DefaultValuesInstanceProvider<>(mClass);
			}

			RandomGen<GENERATED_INSTANCE> randomGen =
				new RandomGen<>(mInstanceProvider,
					new HashMap<>(mDataProviders),
					new ArrayList<>(mOnGenerateCallbacks));

			mDataProviders.clear();
			mOnGenerateCallbacks.clear();

			return randomGen;
		}

		public BuilderReturnValue<GENERATED_INSTANCE> withField(String pFieldName) {
			return new BuilderReturnValue<>(this, pFieldName, mFactory);
		}

		private <FIELD_DATA_TYPE> BuilderField<GENERATED_INSTANCE> returning(String pField,
		                                                                     FieldDataProvider<GENERATED_INSTANCE, FIELD_DATA_TYPE> pFieldDataProvider) {
			mDataProviders.put(pField, pFieldDataProvider);
			return this;
		}

		public BuilderField<GENERATED_INSTANCE> onGenerate(OnGenerateCallback<GENERATED_INSTANCE> pOnGenerateCallback) {
			mOnGenerateCallbacks.add(pOnGenerateCallback);
			return this;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class BuilderReturnValue<RETURN_TYPE> {
		private final BuilderField<RETURN_TYPE> mBuilderField;
		private final String mField;
		private final FieldDataProviderFactory<RETURN_TYPE> mFactory;

		private BuilderReturnValue(BuilderField<RETURN_TYPE> pBuilderField, String pField,
		                           FieldDataProviderFactory<RETURN_TYPE> pFactory) {
			mBuilderField = pBuilderField;
			mField = pField;
			mFactory = pFactory;
		}

		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returningExplicitly(final VALUE_TYPE pValue) {
			return mBuilderField.returning(mField, mFactory.getExplicitFieldDataProvider(pValue));
		}

		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returning(List<VALUE_TYPE> pList) {
			final List<VALUE_TYPE> immutableList = new ArrayList<>(pList);
			return mBuilderField.returning(mField, mFactory.getGenericListFieldDataProvider(immutableList));
		}

		public BuilderField<RETURN_TYPE> returningBoolean() {
			return mBuilderField.returning(mField, mFactory.getBooleanFieldDataProvider());
		}

		public BuilderField<RETURN_TYPE> returningByte() {
			return mBuilderField.returning(mField, mFactory.getByteFieldDataProvider());
		}

		public BuilderField<RETURN_TYPE> returningBytes(int pSize) {
			return mBuilderField.returning(mField, mFactory.getByteListFieldDataProvider(pSize));
		}

		public BuilderField<RETURN_TYPE> returningBytes(int pMinSize, int pMaxSize) {
			return mBuilderField.returning(mField, mFactory.getByteListFieldDataProvider(pMinSize, pMaxSize));
		}

		public BuilderField<RETURN_TYPE> returningDouble() {
			return mBuilderField.returning(mField, mFactory.getDoubleFieldDataProvider());
		}

		public BuilderField<RETURN_TYPE> returningFloat() {
			return mBuilderField.returning(mField, mFactory.getFloatFieldDataProvider());
		}

		public BuilderField<RETURN_TYPE> returningInteger() {
			return mBuilderField.returning(mField, mFactory.getIntegerFieldDataProvider());
		}

		public BuilderField<RETURN_TYPE> returningLong() {
			return mBuilderField.returning(mField, mFactory.getLongFieldDataProvider());
		}

		public BuilderField<RETURN_TYPE> returning(final double pMinimum, final double pMaximum) {
			return mBuilderField.returning(mField, mFactory.getDoubleFieldDataProvider(pMinimum, pMaximum));
		}

		public BuilderField<RETURN_TYPE> returning(final float pMinimum, final float pMaximum) {
			return mBuilderField.returning(mField, mFactory.getFloatFieldDataProvider(pMinimum, pMaximum));
		}

		public BuilderField<RETURN_TYPE> returning(final int pMinimum, final int pMaximum) {
			return mBuilderField.returning(mField, mFactory.getIntegerFieldDataProvider(pMinimum, pMaximum));
		}

		public BuilderField<RETURN_TYPE> returning(final long pMinimum, final long pMaximum) {
			return mBuilderField.returning(mField, mFactory.getLongFieldDataProvider(pMinimum, pMaximum));
		}

		public BuilderField<RETURN_TYPE> returningSequentialInteger() {
			return mBuilderField.returning(mField, mFactory.getSequentialIntegerFieldDataProvider());
		}

		public BuilderField<RETURN_TYPE> returningSequentialInteger(int pStartValue) {
			return mBuilderField.returning(mField, mFactory.getSequentialIntegerFieldDataProvider(pStartValue));
		}

		public BuilderField<RETURN_TYPE> returningUuid() {
			return mBuilderField.returning(mField, mFactory.getUuidFieldDataProvider());
		}

		public BuilderField<RETURN_TYPE> returningRgb(boolean pAlpha) {
			return mBuilderField.returning(mField, mFactory.getRgbFieldDataProvider(pAlpha));
		}

		/**
		 * See {@link #returningLoremIpsum(int, int, String)}.
		 *
		 * @return builder generating one copy of the Lorem Ipsum text
		 */
		public BuilderField<RETURN_TYPE> returningLoremIpsum() {
			return mBuilderField.returning(mField, mFactory.getLoremIpsumFieldDataProvider());
		}

		/**
		 * See {@link #returningLoremIpsum(int, int, String)}.
		 *
		 * @param pLength The number of characters to return
		 * @return A builder generating a substring of an infinite (well, kind of) Lorem Ipsum text
		 */
		public BuilderField<RETURN_TYPE> returningLoremIpsum(int pLength) {
			return mBuilderField.returning(mField, mFactory.getLoremIpsumFieldDataProvider(pLength));
		}

		/**
		 * See {@link #returningLoremIpsum(int, int, String)}.
		 *
		 * @param pMinLength The minimal number of characters to return
		 * @param pMaxLength The maximal number of characters to return
		 * @return A builder generating a substring of an infinite (well, kind of) Lorem Ipsum text
		 */
		public BuilderField<RETURN_TYPE> returningLoremIpsum(int pMinLength, int pMaxLength) {
			return mBuilderField.returning(mField, mFactory.getLoremIpsumFieldDataProvider(pMinLength, pMaxLength));
		}

		/**
		 * Adds a String containing Lorem Ipsum. Length determines how many characters of Lorem Ipsum to return. The content will repeat itself if
		 * the requested length exceeds the length of Lorem Ipsum.
		 *
		 * @param pMinLength          The minimal number of characters to return
		 * @param pMaxLength          The maximal number of characters to return
		 * @param pParagraphDelimiter The string to use between Lorem Ipsum paragraphs
		 * @return A builder generating a substring of an infinite (well, kind of) Lorem Ipsum text
		 */
		public BuilderField<RETURN_TYPE> returningLoremIpsum(int pMinLength, int pMaxLength, String pParagraphDelimiter) {
			return mBuilderField.returning(mField, mFactory.getLoremIpsumFieldDataProvider(pMinLength, pMaxLength, pParagraphDelimiter));
		}

		/**
		 * Adds a generator of random enum values for the given field.
		 *
		 * @param pEnumClass  An enum class
		 * @param <ENUM_TYPE> Implicit. The enum type to use
		 * @return A builder with a data provider
		 */
		public <ENUM_TYPE extends Enum> BuilderField<RETURN_TYPE> returning(final Class<ENUM_TYPE> pEnumClass) {
			return mBuilderField.returning(mField, mFactory.getRandomEnumFieldDataProvider(pEnumClass));
		}

		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returning(final RandomGen<VALUE_TYPE> pRandomGen) {
			//noinspection unchecked - This should be safe, as RandomGen doesn't use the RETURN_TYPE.
			return mBuilderField.returning(mField, (FieldDataProvider<RETURN_TYPE, VALUE_TYPE>) pRandomGen);
		}

		/**
		 * Adds a {@link FieldDataProvider} generated value for the given field.
		 *
		 * @param pFieldDataProvider An instance of the {@link FieldDataProvider} to use
		 * @param <VALUE_TYPE>       The type returned vy the {@link FieldDataProvider}
		 * @return An instance of the specified {@code VALUE_TYPE}
		 */
		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returning(FieldDataProvider<RETURN_TYPE, VALUE_TYPE> pFieldDataProvider) {
			return mBuilderField.returning(mField, pFieldDataProvider);
		}

		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returning(final int pInstances, final FieldDataProvider<RETURN_TYPE, VALUE_TYPE> pFieldDataProvider) {
			return mBuilderField.returning(mField, mFactory.getCustomListFieldDataProvider(pInstances, pFieldDataProvider));
		}

		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returning(final int pInstances, final RandomGen<VALUE_TYPE> pRandomGen) {
			//noinspection unchecked - This should be safe, as RandomGen doesn't use the RETURN_TYPE.
			return mBuilderField.returning(mField, mFactory.getCustomListFieldDataProvider(pInstances,
				(FieldDataProvider<RETURN_TYPE, VALUE_TYPE>) pRandomGen));
		}

		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returning(final int pMinInstances, final int pMaxInstances,
		                                                        final FieldDataProvider<RETURN_TYPE, VALUE_TYPE> pFieldDataProvider) {
			return mBuilderField.returning(mField, mFactory.getCustomListRangeFieldDataProvider(pMinInstances, pMaxInstances, pFieldDataProvider));
		}

		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returning(final int pMinInstances, final int pMaxInstances,
		                                                        final RandomGen<VALUE_TYPE> pFieldDataProvider) {
			//noinspection unchecked - This should be safe, as RandomGen doesn't use the RETURN_TYPE.
			return mBuilderField.returning(mField, mFactory.getCustomListRangeFieldDataProvider(pMinInstances, pMaxInstances,
				(FieldDataProvider<RETURN_TYPE, VALUE_TYPE>) pFieldDataProvider));
		}
	}

	private class TypedArray<ELEMENT_TYPE> {
		private ELEMENT_TYPE[] mTypedArray;

		TypedArray(Class<ELEMENT_TYPE> pElementClass, int pCapacity) {
			// Use Array native method to create array of a type only known at run time
			final Object newInstance = Array.newInstance(pElementClass, pCapacity);
			//noinspection unchecked We just generated the instance, we know what type to expect.
			mTypedArray = (ELEMENT_TYPE[]) newInstance;
		}

		ELEMENT_TYPE[] get() {
			return mTypedArray;
		}
	}

	/**
	 * Classes implementing this interface provide a new instance of the desired type that will get populated by {@link RandomGen}.
	 *
	 * @param <INSTANCE_TYPE> The type of instance to be returned
	 */
	public interface InstanceProvider<INSTANCE_TYPE> {
		INSTANCE_TYPE provideInstance();
	}

	public interface OnGenerateCallback<INSTANCE_TYPE> {
		void onGenerate(INSTANCE_TYPE pGeneratedInstance);
	}

	private static class AssignmentException extends RuntimeException {
		private AssignmentException(Exception pException) {
			super(pException);
		}
	}
}

