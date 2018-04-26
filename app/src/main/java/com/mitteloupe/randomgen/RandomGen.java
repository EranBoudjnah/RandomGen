package com.mitteloupe.randomgen;

import com.mitteloupe.randomgen.fielddataprovider.BooleanFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.CustomListFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.CustomListRangeFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.DoubleFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.DoubleRangeFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.ExplicitFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.FloatFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.FloatRangeFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.GenericListFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.IntegerFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.IntegerRangeFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.LongFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.LongRangeFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.RandomEnumFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.RgbFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.SequentialIntegerFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.UuidFieldDataProvider;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
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
			if (!mPrivateFields.containsKey(key)) continue;

			final Field field = mPrivateFields.get(key);
			final Object value = entry.getValue().generate();
			field.setAccessible(true);
			try {
				field.set(instance, value);
			} catch (IllegalAccessException pE) {
				throw new IllegalArgumentException("Cannot set field " + key + " - unable to access field");
			} catch (IllegalArgumentException pE) {
				throw new IllegalArgumentException("Cannot set field " + key + " due to invalid value");
			}
		}

		return instance;
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

		public Builder(InstanceProvider<T> pInstanceProvider) {
			mDataProviders = new HashMap<>();
			mInstanceProvider = pInstanceProvider;
		}

		public RandomGen<T> build() {
			return new RandomGen<>(mInstanceProvider, mDataProviders);
		}

		public BuilderField<T> withField(String pFieldName) {
			return new BuilderField<>(this, pFieldName);
		}

		private <T2> Builder<T> returning(String pField, FieldDataProvider<T2> pFieldDataProvider) {
			mDataProviders.put(pField, pFieldDataProvider);
			return this;
		}
	}

	public static class BuilderField<RETURN_TYPE> {
		private final Builder<RETURN_TYPE> mBuilder;
		private final String mField;
		private final Random mRandom;

		private BuilderField(Builder<RETURN_TYPE> pBuilder, String pField) {
			mRandom = new Random();
			mBuilder = pBuilder;
			mField = pField;
		}

		public <VALUE_TYPE> Builder<RETURN_TYPE> returningExplicitly(final VALUE_TYPE pValue) {
			return mBuilder.returning(mField, new ExplicitFieldDataProvider<>(pValue));
		}

		public <VALUE_TYPE> Builder<RETURN_TYPE> returning(List<VALUE_TYPE> pList) {
			final List<VALUE_TYPE> immutableList = new ArrayList<>(pList);
			return mBuilder.returning(mField, new GenericListFieldDataProvider<>(mRandom, immutableList));
		}

		public Builder<RETURN_TYPE> returningBoolean() {
			return mBuilder.returning(mField, new BooleanFieldDataProvider(mRandom));
		}

		public Builder<RETURN_TYPE> returningDouble() {
			return mBuilder.returning(mField, new DoubleFieldDataProvider(mRandom));
		}

		public Builder<RETURN_TYPE> returningFloat() {
			return mBuilder.returning(mField, new FloatFieldDataProvider(mRandom));
		}

		public Builder<RETURN_TYPE> returningInteger() {
			return mBuilder.returning(mField, new IntegerFieldDataProvider(mRandom));
		}

		public Builder<RETURN_TYPE> returningLong() {
			return mBuilder.returning(mField, new LongFieldDataProvider(mRandom));
		}

		public Builder<RETURN_TYPE> returning(final double pMinimum, final double pMaximum) {
			return mBuilder.returning(mField, new DoubleRangeFieldDataProvider(mRandom, pMinimum, pMaximum));
		}

		public Builder<RETURN_TYPE> returning(final float pMinimum, final float pMaximum) {
			return mBuilder.returning(mField, new FloatRangeFieldDataProvider(mRandom, pMinimum, pMaximum));
		}

		public Builder<RETURN_TYPE> returning(final int pMinimum, final int pMaximum) {
			return mBuilder.returning(mField, new IntegerRangeFieldDataProvider(mRandom, pMinimum, pMaximum));
		}

		public Builder<RETURN_TYPE> returning(final long pMinimum, final long pMaximum) {
			return mBuilder.returning(mField, new LongRangeFieldDataProvider(mRandom, pMinimum, pMaximum));
		}

		public Builder<RETURN_TYPE> returningSequentialInteger() {
			return mBuilder.returning(mField, new SequentialIntegerFieldDataProvider());
		}

		public Builder<RETURN_TYPE> returningUuid() {
			return mBuilder.returning(mField, new UuidFieldDataProvider());
		}

		public Builder<RETURN_TYPE> returningRGB(boolean pAlpha) {
			return mBuilder.returning(mField, new RgbFieldDataProvider(mRandom, pAlpha));
		}

		/**
		 * Adds a generator of random enum values for the given field.
		 *
		 * @param pValue      Any of the values of the desired enum
		 * @param <ENUM_TYPE> Implicit. The enum type to use
		 * @return A builder with a data provider
		 */
		public <ENUM_TYPE extends Enum> Builder<RETURN_TYPE> returning(final ENUM_TYPE pValue) {
			return mBuilder.returning(mField, new RandomEnumFieldDataProvider<>(mRandom, pValue));
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
			return mBuilder.returning(mField, new CustomListFieldDataProvider<>(pInstances, pFieldDataProvider));
		}

		public <VALUE_TYPE> Builder<RETURN_TYPE> returning(final int pMinInstances, final int pMaxInstances,
		                                                   final FieldDataProvider<VALUE_TYPE> pFieldDataProvider) {
			return mBuilder.returning(mField, new CustomListRangeFieldDataProvider<>(mRandom, pMinInstances, pMaxInstances, pFieldDataProvider));
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
