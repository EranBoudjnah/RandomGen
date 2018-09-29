package com.mitteloupe.randomgen;

import com.mitteloupe.randomgen.fielddataprovider.WeightedFieldDataProvidersFieldDataProvider;

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

		List valueAsList = (List)pValue;
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
		public IncompleteBuilderField<GENERATED_INSTANCE> ofClass(Class<GENERATED_INSTANCE> pClass) {
			return new IncompleteBuilderField<>(pClass, new DefaultFieldDataProviderFactory<GENERATED_INSTANCE>());
		}

		IncompleteBuilderField<GENERATED_INSTANCE> ofClassWithFactory(@SuppressWarnings("SameParameterValue") Class<GENERATED_INSTANCE> pClass,
		                                                              FieldDataProviderFactory<GENERATED_INSTANCE> pFactory) {
			return new IncompleteBuilderField<>(pClass, pFactory);
		}

		public IncompleteBuilderField<GENERATED_INSTANCE> withProvider(InstanceProvider<GENERATED_INSTANCE> pInstanceProvider) {
			return new IncompleteBuilderField<>(pInstanceProvider, new DefaultFieldDataProviderFactory<GENERATED_INSTANCE>());
		}

		IncompleteBuilderField<GENERATED_INSTANCE> withProviderAndFactory(InstanceProvider<GENERATED_INSTANCE> pInstanceProvider,
		                                                                  FieldDataProviderFactory<GENERATED_INSTANCE> pFactory) {
			return new IncompleteBuilderField<>(pInstanceProvider, pFactory);
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

	public static class BuilderField<GENERATED_INSTANCE> extends IncompleteBuilderField<GENERATED_INSTANCE> {
		private double mLastWeight;

		BuilderField(Class<GENERATED_INSTANCE> pClass,
		             IncompleteBuilderField<GENERATED_INSTANCE> pIncompleteBuilderField) {
			super(pClass, pIncompleteBuilderField.mFactory);

			copyFieldsFromIncompleteInstanceProvider(pIncompleteBuilderField);
		}

		BuilderField(InstanceProvider<GENERATED_INSTANCE> pInstanceProvider,
		             IncompleteBuilderField<GENERATED_INSTANCE> pIncompleteBuilderField) {
			super(pInstanceProvider, pIncompleteBuilderField.mFactory);

			copyFieldsFromIncompleteInstanceProvider(pIncompleteBuilderField);
		}

		@Override
		<FIELD_DATA_TYPE> IncompleteBuilderField<GENERATED_INSTANCE> returning(FieldDataProvider<GENERATED_INSTANCE, FIELD_DATA_TYPE> pFieldDataProvider) {
			if (wrappedInWeightedFieldDataProvider()) {
				addFieldDataProviderToWeightedFieldDataProvider(pFieldDataProvider, mLastWeight);
				return this;

			} else {
				return super.returning(pFieldDataProvider);
			}
		}

		private void copyFieldsFromIncompleteInstanceProvider(IncompleteBuilderField<GENERATED_INSTANCE> pIncompleteBuilderField) {
			mDataProviders.putAll(pIncompleteBuilderField.mDataProviders);
			mOnGenerateCallbacks.addAll(pIncompleteBuilderField.mOnGenerateCallbacks);
			mLastUsedFieldName = pIncompleteBuilderField.mLastUsedFieldName;

			if (pIncompleteBuilderField instanceof BuilderField) {
				mLastWeight = ((BuilderField)pIncompleteBuilderField).mLastWeight;
			}
		}

		public BuilderReturnValue<GENERATED_INSTANCE> or() {
			return orWithWeight(1d);
		}

		public BuilderReturnValue<GENERATED_INSTANCE> orWithWeight(double pWeight) {
			wrapInWeightedFieldDataProviderIfNotWrapped();
			mLastWeight = pWeight;
			return getBuilderReturnValueForInstance();
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

		private void wrapInWeightedFieldDataProviderIfNotWrapped() {
			FieldDataProvider<GENERATED_INSTANCE, ?> lastFieldDataProvider =
				mDataProviders.get(mLastUsedFieldName);

			if (!wrappedInWeightedFieldDataProvider()) {
				FieldDataProvider<GENERATED_INSTANCE, ?> wrapper =
					mFactory.getWeightedFieldDataProvidersFieldDataProvider(lastFieldDataProvider);
				mDataProviders.put(mLastUsedFieldName, wrapper);
			}
		}

		private boolean wrappedInWeightedFieldDataProvider() {
			FieldDataProvider<GENERATED_INSTANCE, ?> lastFieldDataProvider =
				mDataProviders.get(mLastUsedFieldName);

			return lastFieldDataProvider instanceof WeightedFieldDataProvidersFieldDataProvider;
		}

		private <FIELD_DATA_TYPE> void addFieldDataProviderToWeightedFieldDataProvider(
			FieldDataProvider<GENERATED_INSTANCE, FIELD_DATA_TYPE> pFieldDataProvider,
			double pWeight
		) {
			FieldDataProvider<GENERATED_INSTANCE, ?> lastFieldDataProvider = mDataProviders.get(mLastUsedFieldName);

			//noinspection unchecked This will fail if we try returning different data types for the same field, but seems impossible to check.
			FieldDataProvider<GENERATED_INSTANCE, FIELD_DATA_TYPE> qualifiedLastFieldDataProvider =
				(FieldDataProvider<GENERATED_INSTANCE, FIELD_DATA_TYPE>)lastFieldDataProvider;

			((WeightedFieldDataProvidersFieldDataProvider<GENERATED_INSTANCE, FIELD_DATA_TYPE>)qualifiedLastFieldDataProvider).addFieldDataProvider(pFieldDataProvider, pWeight);
		}
	}

	public static class IncompleteBuilderField<GENERATED_INSTANCE> {
		final FieldDataProviderFactory<GENERATED_INSTANCE> mFactory;
		final Map<String, FieldDataProvider<GENERATED_INSTANCE, ?>> mDataProviders;
		final List<OnGenerateCallback<GENERATED_INSTANCE>> mOnGenerateCallbacks;

		InstanceProvider<GENERATED_INSTANCE> mInstanceProvider;
		Class<GENERATED_INSTANCE> mClass;

		String mLastUsedFieldName;

		IncompleteBuilderField(Class<GENERATED_INSTANCE> pClass,
		                       FieldDataProviderFactory<GENERATED_INSTANCE> pFactory) {
			this(pFactory);

			mClass = pClass;
		}

		IncompleteBuilderField(InstanceProvider<GENERATED_INSTANCE> pInstanceProvider,
		                       FieldDataProviderFactory<GENERATED_INSTANCE> pFactory) {
			this(pFactory);

			mInstanceProvider = pInstanceProvider;
		}

		private IncompleteBuilderField(FieldDataProviderFactory<GENERATED_INSTANCE> pFactory) {
			mDataProviders = new LinkedHashMap<>();
			mOnGenerateCallbacks = new ArrayList<>();
			mFactory = pFactory;
		}

		public BuilderReturnValue<GENERATED_INSTANCE> withField(String pFieldName) {
			mLastUsedFieldName = pFieldName;

			return getBuilderReturnValueForInstance();
		}

		BuilderReturnValue<GENERATED_INSTANCE> getBuilderReturnValueForInstance() {
			if (mClass != null) {
				return new BuilderReturnValue<>(new BuilderField<>(mClass, this), mFactory);

			} else {
				return new BuilderReturnValue<>(new BuilderField<>(mInstanceProvider, this), mFactory);
			}
		}

		<FIELD_DATA_TYPE> IncompleteBuilderField<GENERATED_INSTANCE> returning(FieldDataProvider<GENERATED_INSTANCE, FIELD_DATA_TYPE> pFieldDataProvider) {
			mDataProviders.put(mLastUsedFieldName, pFieldDataProvider);
			return this;
		}

		public IncompleteBuilderField<GENERATED_INSTANCE> onGenerate(OnGenerateCallback<GENERATED_INSTANCE> pOnGenerateCallback) {
			mOnGenerateCallbacks.add(pOnGenerateCallback);
			return this;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class BuilderReturnValue<RETURN_TYPE> {
		private final BuilderField<RETURN_TYPE> mBuilderField;
		private final FieldDataProviderFactory<RETURN_TYPE> mFactory;

		BuilderReturnValue(BuilderField<RETURN_TYPE> pBuilderField,
		                   FieldDataProviderFactory<RETURN_TYPE> pFactory) {
			mBuilderField = pBuilderField;
			mFactory = pFactory;
		}

		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returningExplicitly(final VALUE_TYPE pValue) {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getExplicitFieldDataProvider(pValue)));
		}

		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returning(List<VALUE_TYPE> pList) {
			final List<VALUE_TYPE> immutableList = new ArrayList<>(pList);
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getGenericListFieldDataProvider(immutableList)));
		}

		public BuilderField<RETURN_TYPE> returningBoolean() {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getBooleanFieldDataProvider()));
		}

		public BuilderField<RETURN_TYPE> returningByte() {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getByteFieldDataProvider()));
		}

		public BuilderField<RETURN_TYPE> returningBytes(int pSize) {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getByteListFieldDataProvider(pSize)));
		}

		public BuilderField<RETURN_TYPE> returningBytes(int pMinSize, int pMaxSize) {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getByteListFieldDataProvider(pMinSize, pMaxSize)));
		}

		public BuilderField<RETURN_TYPE> returningDouble() {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getDoubleFieldDataProvider()));
		}

		public BuilderField<RETURN_TYPE> returningFloat() {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getFloatFieldDataProvider()));
		}

		public BuilderField<RETURN_TYPE> returningInteger() {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getIntegerFieldDataProvider()));
		}

		public BuilderField<RETURN_TYPE> returningLong() {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getLongFieldDataProvider()));
		}

		public BuilderField<RETURN_TYPE> returning(final double pMinimum, final double pMaximum) {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getDoubleFieldDataProvider(pMinimum, pMaximum)));
		}

		public BuilderField<RETURN_TYPE> returning(final float pMinimum, final float pMaximum) {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getFloatFieldDataProvider(pMinimum, pMaximum)));
		}

		public BuilderField<RETURN_TYPE> returning(final int pMinimum, final int pMaximum) {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getIntegerFieldDataProvider(pMinimum, pMaximum)));
		}

		public BuilderField<RETURN_TYPE> returning(final long pMinimum, final long pMaximum) {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getLongFieldDataProvider(pMinimum, pMaximum)));
		}

		public BuilderField<RETURN_TYPE> returningSequentialInteger() {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getSequentialIntegerFieldDataProvider()));
		}

		public BuilderField<RETURN_TYPE> returningSequentialInteger(int pStartValue) {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getSequentialIntegerFieldDataProvider(pStartValue)));
		}

		public BuilderField<RETURN_TYPE> returningUuid() {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getUuidFieldDataProvider()));
		}

		public BuilderField<RETURN_TYPE> returningRgb(boolean pAlpha) {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getRgbFieldDataProvider(pAlpha)));
		}

		/**
		 * See {@link #returningLoremIpsum(int, int, String)}.
		 *
		 * @return builder generating one copy of the Lorem Ipsum text
		 */
		public BuilderField<RETURN_TYPE> returningLoremIpsum() {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getLoremIpsumFieldDataProvider()));
		}

		/**
		 * See {@link #returningLoremIpsum(int, int, String)}.
		 *
		 * @param pLength The number of characters to return
		 * @return A builder generating a substring of an infinite (well, kind of) Lorem Ipsum text
		 */
		public BuilderField<RETURN_TYPE> returningLoremIpsum(int pLength) {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getLoremIpsumFieldDataProvider(pLength)));
		}

		/**
		 * See {@link #returningLoremIpsum(int, int, String)}.
		 *
		 * @param pMinLength The minimal number of characters to return
		 * @param pMaxLength The maximal number of characters to return
		 * @return A builder generating a substring of an infinite (well, kind of) Lorem Ipsum text
		 */
		public BuilderField<RETURN_TYPE> returningLoremIpsum(int pMinLength, int pMaxLength) {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getLoremIpsumFieldDataProvider(pMinLength, pMaxLength)));
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
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getLoremIpsumFieldDataProvider(pMinLength, pMaxLength, pParagraphDelimiter)));
		}

		/**
		 * Adds a generator of random enum values for the given field.
		 *
		 * @param pEnumClass  An enum class
		 * @param <ENUM_TYPE> Implicit. The enum type to use
		 * @return A builder with a data provider
		 */
		public <ENUM_TYPE extends Enum> BuilderField<RETURN_TYPE> returning(final Class<ENUM_TYPE> pEnumClass) {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getRandomEnumFieldDataProvider(pEnumClass)));
		}

		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returning(final RandomGen<VALUE_TYPE> pRandomGen) {
			//noinspection unchecked - This should be safe, as RandomGen doesn't use the RETURN_TYPE.
			return getBuilderFieldFromIncomplete(mBuilderField.returning((FieldDataProvider<RETURN_TYPE, VALUE_TYPE>)pRandomGen));
		}

		/**
		 * Adds a {@link FieldDataProvider} generated value for the given field.
		 *
		 * @param pFieldDataProvider An instance of the {@link FieldDataProvider} to use
		 * @param <VALUE_TYPE>       The type returned vy the {@link FieldDataProvider}
		 * @return An instance of the specified {@code VALUE_TYPE}
		 */
		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returning(FieldDataProvider<RETURN_TYPE, VALUE_TYPE> pFieldDataProvider) {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(pFieldDataProvider));
		}

		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returning(final int pInstances, final FieldDataProvider<RETURN_TYPE, VALUE_TYPE> pFieldDataProvider) {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getCustomListFieldDataProvider(pInstances, pFieldDataProvider)));
		}

		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returning(final int pInstances, final RandomGen<VALUE_TYPE> pRandomGen) {
			//noinspection unchecked - This should be safe, as RandomGen doesn't use the RETURN_TYPE.
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getCustomListFieldDataProvider(pInstances,
				(FieldDataProvider<RETURN_TYPE, VALUE_TYPE>)pRandomGen)));
		}

		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returning(final int pMinInstances, final int pMaxInstances,
		                                                        final FieldDataProvider<RETURN_TYPE, VALUE_TYPE> pFieldDataProvider) {
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getCustomListRangeFieldDataProvider(pMinInstances, pMaxInstances, pFieldDataProvider)));
		}

		public <VALUE_TYPE> BuilderField<RETURN_TYPE> returning(final int pMinInstances, final int pMaxInstances,
		                                                        final RandomGen<VALUE_TYPE> pFieldDataProvider) {
			//noinspection unchecked - This should be safe, as RandomGen doesn't use the RETURN_TYPE.
			return getBuilderFieldFromIncomplete(mBuilderField.returning(mFactory.getCustomListRangeFieldDataProvider(pMinInstances, pMaxInstances,
				(FieldDataProvider<RETURN_TYPE, VALUE_TYPE>)pFieldDataProvider)));
		}
		
		private BuilderField<RETURN_TYPE> getBuilderFieldFromIncomplete(IncompleteBuilderField<RETURN_TYPE> pIncompleteBuilderField) {
			if (pIncompleteBuilderField.mClass != null) {
				return new BuilderField<>(pIncompleteBuilderField.mClass, pIncompleteBuilderField);
	
			} else {
				return new BuilderField<>(pIncompleteBuilderField.mInstanceProvider, pIncompleteBuilderField);
			}
		}
	}

	private class TypedArray<ELEMENT_TYPE> {
		private ELEMENT_TYPE[] mTypedArray;

		TypedArray(Class<ELEMENT_TYPE> pElementClass, int pCapacity) {
			// Use Array native method to create array of a type only known at run time
			final Object newInstance = Array.newInstance(pElementClass, pCapacity);
			//noinspection unchecked We just generated the instance, we know what type to expect.
			mTypedArray = (ELEMENT_TYPE[])newInstance;
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
		AssignmentException(Exception pException) {
			super(pException);
		}
	}
}

