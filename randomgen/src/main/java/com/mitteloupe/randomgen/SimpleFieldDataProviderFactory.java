package com.mitteloupe.randomgen;

import com.mitteloupe.randomgen.fielddataprovider.BooleanFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.ByteFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.ByteListFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.CustomListFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.CustomListRangeFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.DateFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.DoubleFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.ExplicitFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.FloatFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.GenericListFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.IntegerFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.LongFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.LoremIpsumFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.PaddedFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.RandomEnumFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.RgbFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.SequentialIntegerFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.UuidFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.WeightedFieldDataProvidersFieldDataProvider;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Eran Boudjnah on 07/08/2018.
 */
class SimpleFieldDataProviderFactory<OUTPUT_TYPE> implements FieldDataProviderFactory<OUTPUT_TYPE> {
	private final Random mRandom;

	private final UuidGenerator mUuidGenerator;

	SimpleFieldDataProviderFactory(Random pRandom, UuidGenerator pUuidGenerator) {
		mRandom = pRandom;
		mUuidGenerator = pUuidGenerator;
	}

	@Override
	public <VALUE_TYPE> ExplicitFieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> getExplicitFieldDataProvider(VALUE_TYPE pValue) {
		return new ExplicitFieldDataProvider<>(pValue);
	}

	@Override
	public <VALUE_TYPE> GenericListFieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> getGenericListFieldDataProvider(List<VALUE_TYPE> pList) {
		return new GenericListFieldDataProvider<>(mRandom, pList);
	}

	@Override
	public BooleanFieldDataProvider<OUTPUT_TYPE> getBooleanFieldDataProvider() {
		return new BooleanFieldDataProvider<>(mRandom);
	}

	@Override
	public ByteFieldDataProvider<OUTPUT_TYPE> getByteFieldDataProvider() {
		return new ByteFieldDataProvider<>(mRandom);
	}

	@Override
	public ByteListFieldDataProvider<OUTPUT_TYPE> getByteListFieldDataProvider(int pSize) {
		return ByteListFieldDataProvider.getInstanceWithSize(mRandom, pSize);
	}

	@Override
	public ByteListFieldDataProvider<OUTPUT_TYPE> getByteListFieldDataProvider(int pMinSize, int pMaxSize) {
		return ByteListFieldDataProvider.getInstanceWithSizeRange(mRandom, pMinSize, pMaxSize);
	}

	@Override
	public DoubleFieldDataProvider<OUTPUT_TYPE> getDoubleFieldDataProvider() {
		return DoubleFieldDataProvider.getInstance(mRandom);
	}

	@Override
	public DoubleFieldDataProvider<OUTPUT_TYPE> getDoubleFieldDataProvider(double pMinimum, double pMaximum) {
		return DoubleFieldDataProvider.getInstanceWithRange(mRandom, pMinimum, pMaximum);
	}

	@Override
	public FloatFieldDataProvider<OUTPUT_TYPE> getFloatFieldDataProvider() {
		return FloatFieldDataProvider.getInstance(mRandom);
	}

	@Override
	public FloatFieldDataProvider<OUTPUT_TYPE> getFloatFieldDataProvider(float pMinimum, float pMaximum) {
		return FloatFieldDataProvider.getInstanceWithRange(mRandom, pMinimum, pMaximum);
	}

	@Override
	public IntegerFieldDataProvider<OUTPUT_TYPE> getIntegerFieldDataProvider() {
		return IntegerFieldDataProvider.getInstance(mRandom);
	}

	@Override
	public IntegerFieldDataProvider<OUTPUT_TYPE> getIntegerFieldDataProvider(int pMinimum, int pMaximum) {
		return IntegerFieldDataProvider.getInstanceWithRange(mRandom, pMinimum, pMaximum);
	}

	@Override
	public LongFieldDataProvider<OUTPUT_TYPE> getLongFieldDataProvider() {
		return LongFieldDataProvider.getInstance(mRandom);
	}

	@Override
	public LongFieldDataProvider<OUTPUT_TYPE> getLongFieldDataProvider(long pMinimum, long pMaximum) {
		return LongFieldDataProvider.getInstanceWithRange(mRandom, pMinimum, pMaximum);
	}

	@Override
	public SequentialIntegerFieldDataProvider<OUTPUT_TYPE> getSequentialIntegerFieldDataProvider() {
		return SequentialIntegerFieldDataProvider.getInstance();
	}

	@Override
	public SequentialIntegerFieldDataProvider<OUTPUT_TYPE> getSequentialIntegerFieldDataProvider(int pStartValue) {
		return SequentialIntegerFieldDataProvider.getInstanceWithStartValue(pStartValue);
	}

	@Override
	public UuidFieldDataProvider<OUTPUT_TYPE> getUuidFieldDataProvider() {
		return new UuidFieldDataProvider<>(mUuidGenerator);
	}

	@Override
	public RgbFieldDataProvider<OUTPUT_TYPE> getRgbFieldDataProvider(boolean pAlpha) {
		return new RgbFieldDataProvider<>(mRandom, pAlpha);
	}

	@Override
	public FieldDataProvider<OUTPUT_TYPE, Date> getDateFieldDataProvider() {
		return DateFieldDataProvider.getInstance(mRandom);
	}

	@Override
	public FieldDataProvider<OUTPUT_TYPE, Date> getDateFieldDataProvider(long pLatestTimestamp) {
		return DateFieldDataProvider.getInstanceWithLatestTimestamp(mRandom, pLatestTimestamp);
	}

	@Override
	public FieldDataProvider<OUTPUT_TYPE, Date> getDateFieldDataProvider(long pEarliestTimestamp, long pLatestTimestamp) {
		return DateFieldDataProvider.getInstanceWithRange(mRandom, pEarliestTimestamp, pLatestTimestamp);
	}

	@Override
	public LoremIpsumFieldDataProvider<OUTPUT_TYPE> getLoremIpsumFieldDataProvider() {
		return LoremIpsumFieldDataProvider.getInstance();
	}

	@Override
	public LoremIpsumFieldDataProvider<OUTPUT_TYPE> getLoremIpsumFieldDataProvider(int pLength) {
		return LoremIpsumFieldDataProvider.getInstanceWithLength(pLength);
	}

	@Override
	public LoremIpsumFieldDataProvider<OUTPUT_TYPE> getLoremIpsumFieldDataProvider(int pMinLength, int pMaxLength) {
		return LoremIpsumFieldDataProvider.getInstanceWithRange(mRandom, pMinLength, pMaxLength);
	}

	@Override
	public LoremIpsumFieldDataProvider<OUTPUT_TYPE> getLoremIpsumFieldDataProvider(int pMinLength, int pMaxLength, String pParagraphDelimiter) {
		return LoremIpsumFieldDataProvider.getInstanceWithRangeAndDelimiter(mRandom, pMinLength, pMaxLength, pParagraphDelimiter);
	}

	@Override
	public FieldDataProvider<OUTPUT_TYPE, ?> getWeightedFieldDataProvidersFieldDataProvider(FieldDataProvider<OUTPUT_TYPE, ?> pFieldDataProvider) {
		return new WeightedFieldDataProvidersFieldDataProvider<>(mRandom, pFieldDataProvider);
	}

	@Override
	public <ENUM_TYPE extends Enum> RandomEnumFieldDataProvider<OUTPUT_TYPE, ENUM_TYPE> getRandomEnumFieldDataProvider(Class<ENUM_TYPE> pValue) {
		return new RandomEnumFieldDataProvider<>(mRandom, pValue);
	}

	@Override
	public FieldDataProvider<OUTPUT_TYPE, String>
	getPaddedFieldDataProvider(int pMinimumLength, String pPaddingString, FieldDataProvider<OUTPUT_TYPE, ?> pFieldDataProvider) {
		return new PaddedFieldDataProvider<>(pMinimumLength, pPaddingString, pFieldDataProvider);
	}

	@Override
	public <VALUE_TYPE> CustomListFieldDataProvider<OUTPUT_TYPE, VALUE_TYPE>
	getCustomListFieldDataProvider(int pInstances, FieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> pFieldDataProvider) {
		return new CustomListFieldDataProvider<>(pInstances, pFieldDataProvider);
	}

	@Override
	public <VALUE_TYPE> CustomListRangeFieldDataProvider<OUTPUT_TYPE, VALUE_TYPE>
	getCustomListRangeFieldDataProvider(int pMinInstances, int pMaxInstances, FieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> pFieldDataProvider) {
		return new CustomListRangeFieldDataProvider<>(mRandom, pMinInstances, pMaxInstances, pFieldDataProvider);
	}
}
