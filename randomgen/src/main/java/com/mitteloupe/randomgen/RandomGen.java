package com.mitteloupe.randomgen;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
@SuppressWarnings("unused")
public class RandomGen<OUTPUT_TYPE> implements FieldDataProvider<OUTPUT_TYPE> {
	private final InstanceProvider<OUTPUT_TYPE> mInstanceProvider;
	private final Map<String, FieldDataProvider<?>> mDataProviders;
	private final Map<String, Field> mPrivateFields;

	private RandomGen(InstanceProvider<OUTPUT_TYPE> pInstanceProvider, Map<String, FieldDataProvider<?>> pDataProviders) {
		mInstanceProvider = pInstanceProvider;
		mDataProviders = pDataProviders;
		mPrivateFields = new HashMap<>();

		getAllFields();
	}

	public OUTPUT_TYPE generate() {
		final OUTPUT_TYPE instance = mInstanceProvider.provideInstance();

		for (final Map.Entry<String, FieldDataProvider<?>> entry : mDataProviders.entrySet()) {
			final String key = entry.getKey();
			if (!mPrivateFields.containsKey(key)) {
				throw new IllegalArgumentException("Cannot set field " + key + " - field not found");
			}

			final Field field = mPrivateFields.get(key);
			field.setAccessible(true);
			try {
				final Object value = entry.getValue().generate();
				setField(instance, field, value);
			} catch (IllegalArgumentException pE) {
				throw new IllegalArgumentException("Cannot set field " + key + " due to invalid value", pE);
			}
		}

		return instance;
	}

	private void setField(OUTPUT_TYPE pInstance, Field pField, Object pValue) throws IllegalArgumentException {
		if (isFieldArray(pField)) {
			if (!Collection.class.isAssignableFrom(pValue.getClass())) throw new IllegalArgumentException("Expected collection value");
			List valueAsList = (List)pValue;
			TypedArray typedArray = new TypedArray<>(pField.getType().getComponentType(), valueAsList.size());

			try {
				pField.set(pInstance, valueAsList.toArray(typedArray.get()));
			} catch (IllegalAccessException ignore) {
			}

		} else {
			try {
				pField.set(pInstance, pValue);
			} catch (IllegalAccessException ignore) {
			}
		}
	}

	private boolean isFieldArray(Field pField) {
		return pField.getType().isArray();
	}

	private void getAllFields() {
		OUTPUT_TYPE instance = mInstanceProvider.provideInstance();
		Field[] allFields = instance.getClass().getDeclaredFields();
		for (Field field : allFields) {
			if (Modifier.isPrivate(field.getModifiers())) {
				mPrivateFields.put(field.getName(), field);
			}
		}
	}

	public static class Builder<T> {
		private final Map<String, FieldDataProvider<?>> mDataProviders;
		private final InstanceProvider<T> mInstanceProvider;
		private FieldDataProviderFactory mFactory;

		@SuppressWarnings("WeakerAccess") // Public library constructor
		public Builder(InstanceProvider<T> pInstanceProvider) {
			this(pInstanceProvider, new SimpleFieldDataProviderFactory(new Random()));
		}

		Builder(InstanceProvider<T> pInstanceProvider, FieldDataProviderFactory pFactory) {
			mDataProviders = new HashMap<>();
			mInstanceProvider = pInstanceProvider;
			mFactory = pFactory;
		}

		@SuppressWarnings("unused") // Public library interface
		public RandomGen<T> build() {
			return new RandomGen<>(mInstanceProvider, mDataProviders);
		}

		@SuppressWarnings("unused") // Public library interface
		public BuilderField<T> withField(String pFieldName) {
			return new BuilderField<>(this, pFieldName, mFactory);
		}

		private <T2> Builder<T> returning(String pField, FieldDataProvider<T2> pFieldDataProvider) {
			mDataProviders.put(pField, pFieldDataProvider);
			return this;
		}

	}

	public static class BuilderField<RETURN_TYPE> {
		private final Builder<RETURN_TYPE> mBuilder;
		private final String mField;
		private final FieldDataProviderFactory mFactory;

		private BuilderField(Builder<RETURN_TYPE> pBuilder, String pField, FieldDataProviderFactory pFactory) {
			mBuilder = pBuilder;
			mField = pField;
			mFactory = pFactory;
		}

		public <VALUE_TYPE> Builder<RETURN_TYPE> returningExplicitly(final VALUE_TYPE pValue) {
			return mBuilder.returning(mField, mFactory.getExplicitFieldDataProvider(pValue));
		}

		public <VALUE_TYPE> Builder<RETURN_TYPE> returning(List<VALUE_TYPE> pList) {
			final List<VALUE_TYPE> immutableList = new ArrayList<>(pList);
			return mBuilder.returning(mField, mFactory.getGenericListFieldDataProvider(immutableList));
		}

		public Builder<RETURN_TYPE> returningBoolean() {
			return mBuilder.returning(mField, mFactory.getBooleanFieldDataProvider());
		}

		public Builder<RETURN_TYPE> returningByte() {
			return mBuilder.returning(mField, mFactory.getByteFieldDataProvider());
		}

		public Builder<RETURN_TYPE> returningBytes(int pSize) {
			return mBuilder.returning(mField, mFactory.getByteListFieldDataProvider(pSize));
		}

		public Builder<RETURN_TYPE> returningBytes(int pMinSize, int pMaxSize) {
			return mBuilder.returning(mField, mFactory.getByteListFieldDataProvider(pMinSize, pMaxSize));
		}

		public Builder<RETURN_TYPE> returningDouble() {
			return mBuilder.returning(mField, mFactory.getDoubleFieldDataProvider());
		}

		public Builder<RETURN_TYPE> returningFloat() {
			return mBuilder.returning(mField, mFactory.getFloatFieldDataProvider());
		}

		public Builder<RETURN_TYPE> returningInteger() {
			return mBuilder.returning(mField, mFactory.getIntegerFieldDataProvider());
		}

		public Builder<RETURN_TYPE> returningLong() {
			return mBuilder.returning(mField, mFactory.getLongFieldDataProvider());
		}

		public Builder<RETURN_TYPE> returning(final double pMinimum, final double pMaximum) {
			return mBuilder.returning(mField, mFactory.getDoubleRangeFieldDataProvider(pMinimum, pMaximum));
		}

		public Builder<RETURN_TYPE> returning(final float pMinimum, final float pMaximum) {
			return mBuilder.returning(mField, mFactory.getFloatRangeFieldDataProvider(pMinimum, pMaximum));
		}

		public Builder<RETURN_TYPE> returning(final int pMinimum, final int pMaximum) {
			return mBuilder.returning(mField, mFactory.getIntegerRangeFieldDataProvider(pMinimum, pMaximum));
		}

		public Builder<RETURN_TYPE> returning(final long pMinimum, final long pMaximum) {
			return mBuilder.returning(mField, mFactory.getLongRangeFieldDataProvider(pMinimum, pMaximum));
		}

		public Builder<RETURN_TYPE> returningSequentialInteger() {
			return mBuilder.returning(mField, mFactory.getSequentialIntegerFieldDataProvider());
		}

		public Builder<RETURN_TYPE> returningUuid() {
			return mBuilder.returning(mField, mFactory.getUuidFieldDataProvider());
		}

		public Builder<RETURN_TYPE> returningRgb(boolean pAlpha) {
			return mBuilder.returning(mField, mFactory.getRgbFieldDataProvider(pAlpha));
		}

		/**
		 * See {@link #returningLoremIpsum(int, int, String)}.
		 *
		 * @return builder generating one copy of the Lorem Ipsum text
		 */
		public Builder<RETURN_TYPE> returningLoremIpsum() {
			return mBuilder.returning(mField, mFactory.getLoremIpsumFieldDataProvider());
		}

		/**
		 * See {@link #returningLoremIpsum(int, int, String)}.
		 *
		 * @param pLength The number of characters to return
		 * @return A builder generating a substring of an infinite (well, kind of) Lorem Ipsum text
		 */
		public Builder<RETURN_TYPE> returningLoremIpsum(int pLength) {
			return mBuilder.returning(mField, mFactory.getLoremIpsumFieldDataProvider(pLength));
		}

		/**
		 * See {@link #returningLoremIpsum(int, int, String)}.
		 *
		 * @param pMinLength The minimal number of characters to return
		 * @param pMaxLength The maximal number of characters to return
		 * @return A builder generating a substring of an infinite (well, kind of) Lorem Ipsum text
		 */
		public Builder<RETURN_TYPE> returningLoremIpsum(int pMinLength, int pMaxLength) {
			return mBuilder.returning(mField, mFactory.getLoremIpsumFieldDataProvider(pMinLength, pMaxLength));
		}

		/**
		 * Adds a String containing Lorem Ipsum. Length determines how many characters of Lorem Ipsum to return. The content will repeat itself if
		 * the requested length exceeds the length of Lorem Ipsum.
		 *
		 * @param pMinLength The minimal number of characters to return
		 * @param pMaxLength The maximal number of characters to return
		 * @param pParagraphDelimiter The string to use between Lorem Ipsum paragraphs
		 * @return A builder generating a substring of an infinite (well, kind of) Lorem Ipsum text
		 */
		@SuppressWarnings("WeakerAccess")
		public Builder<RETURN_TYPE> returningLoremIpsum(int pMinLength, int pMaxLength, String pParagraphDelimiter) {
			return mBuilder.returning(mField, mFactory.getLoremIpsumFieldDataProvider(pMinLength, pMaxLength, pParagraphDelimiter));
		}

		/**
		 * Adds a generator of random enum values for the given field.
		 *
		 * @param pValue      Any of the values of the desired enum
		 * @param <ENUM_TYPE> Implicit. The enum type to use
		 * @return A builder with a data provider
		 */
		public <ENUM_TYPE extends Enum> Builder<RETURN_TYPE> returning(final ENUM_TYPE pValue) {
			return mBuilder.returning(mField, mFactory.getRandomEnumFieldDataProvider(pValue));
		}

		/**
		 * Adds a {@link FieldDataProvider} generated value for the given field.
		 *
		 * @param pFieldDataProvider An instance of the {@link FieldDataProvider} to use
		 * @param <VALUE_TYPE>       The type returned vy the {@link FieldDataProvider}
		 * @return An instance of the specified {@code VALUE_TYPE}
		 */
		public <VALUE_TYPE> Builder<RETURN_TYPE> returning(FieldDataProvider<VALUE_TYPE> pFieldDataProvider) {
			return mBuilder.returning(mField, pFieldDataProvider);
		}

		public <VALUE_TYPE> Builder<RETURN_TYPE> returning(final int pInstances, final FieldDataProvider<VALUE_TYPE> pFieldDataProvider) {
			return mBuilder.returning(mField, mFactory.getCustomListFieldDataProvider(pInstances, pFieldDataProvider));
		}

		public <VALUE_TYPE> Builder<RETURN_TYPE> returning(final int pMinInstances, final int pMaxInstances,
		                                                   final FieldDataProvider<VALUE_TYPE> pFieldDataProvider) {
			return mBuilder.returning(mField, mFactory.getCustomListRangeFieldDataProvider(pMinInstances, pMaxInstances, pFieldDataProvider));
		}
	}

	private class TypedArray<ELEMENT_TYPE> {
		private ELEMENT_TYPE[] mTypedArray;

		TypedArray(Class<ELEMENT_TYPE> pElementClass, int pCapacity) {
			// Use Array native method to create array
			// of a type only known at run time
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
}

