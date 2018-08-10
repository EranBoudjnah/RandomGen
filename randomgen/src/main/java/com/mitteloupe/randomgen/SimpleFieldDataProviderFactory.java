package com.mitteloupe.randomgen;

import com.mitteloupe.randomgen.fielddataprovider.BooleanFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.ByteFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.ByteListFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.CustomListFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.CustomListRangeFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.DoubleFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.ExplicitFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.FloatFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.GenericListFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.IntegerFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.LongFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.LoremIpsumFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.RandomEnumFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.RgbFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.SequentialIntegerFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.UuidFieldDataProvider;

import java.util.List;
import java.util.Random;

/**
 * Created by Eran Boudjnah on 07/08/2018.
 */
class SimpleFieldDataProviderFactory implements FieldDataProviderFactory {
	private Random mRandom;

	private UuidGenerator mUuidGenerator;

	SimpleFieldDataProviderFactory(Random pRandom, UuidGenerator pUuidGenerator) {
		mRandom = pRandom;
		mUuidGenerator = pUuidGenerator;
	}

	@Override
	public <VALUE_TYPE> ExplicitFieldDataProvider<VALUE_TYPE> getExplicitFieldDataProvider(VALUE_TYPE pValue) {
		return new ExplicitFieldDataProvider<>(pValue);
	}

	@Override
	public <VALUE_TYPE> GenericListFieldDataProvider<VALUE_TYPE> getGenericListFieldDataProvider(List<VALUE_TYPE> pList) {
		return new GenericListFieldDataProvider<>(mRandom, pList);
	}

	@Override
	public BooleanFieldDataProvider getBooleanFieldDataProvider() {
		return new BooleanFieldDataProvider(mRandom);
	}

	@Override
	public ByteFieldDataProvider getByteFieldDataProvider() {
		return new ByteFieldDataProvider(mRandom);
	}

	@Override
	public ByteListFieldDataProvider getByteListFieldDataProvider(int pSize) {
		return new ByteListFieldDataProvider(mRandom, pSize);
	}

	@Override
	public ByteListFieldDataProvider getByteListFieldDataProvider(int pMinSize, int pMaxSize) {
		return new ByteListFieldDataProvider(mRandom, pMinSize, pMaxSize);
	}

	@Override
	public DoubleFieldDataProvider getDoubleFieldDataProvider() {
		return new DoubleFieldDataProvider(mRandom);
	}

	@Override
	public DoubleFieldDataProvider getDoubleFieldDataProvider(double pMinimum, double pMaximum) {
		return new DoubleFieldDataProvider(mRandom, pMinimum, pMaximum);
	}

	@Override
	public FloatFieldDataProvider getFloatFieldDataProvider() {
		return new FloatFieldDataProvider(mRandom);
	}

	@Override
	public FloatFieldDataProvider getFloatFieldDataProvider(float pMinimum, float pMaximum) {
		return new FloatFieldDataProvider(mRandom, pMinimum, pMaximum);
	}

	@Override
	public IntegerFieldDataProvider getIntegerFieldDataProvider() {
		return new IntegerFieldDataProvider(mRandom);
	}

	@Override
	public IntegerFieldDataProvider getIntegerFieldDataProvider(int pMinimum, int pMaximum) {
		return new IntegerFieldDataProvider(mRandom, pMinimum, pMaximum);
	}

	@Override
	public LongFieldDataProvider getLongFieldDataProvider() {
		return new LongFieldDataProvider(mRandom);
	}

	@Override
	public LongFieldDataProvider getLongFieldDataProvider(long pMinimum, long pMaximum) {
		return new LongFieldDataProvider(mRandom, pMinimum, pMaximum);
	}

	@Override
	public SequentialIntegerFieldDataProvider getSequentialIntegerFieldDataProvider() {
		return new SequentialIntegerFieldDataProvider();
	}

	@Override
	public SequentialIntegerFieldDataProvider getSequentialIntegerFieldDataProvider(int pStartValue) {
		return new SequentialIntegerFieldDataProvider(pStartValue);
	}

	@Override
	public UuidFieldDataProvider getUuidFieldDataProvider() {
		return new UuidFieldDataProvider(mUuidGenerator);
	}

	@Override
	public RgbFieldDataProvider getRgbFieldDataProvider(boolean pAlpha) {
		return new RgbFieldDataProvider(mRandom, pAlpha);
	}

	@Override
	public LoremIpsumFieldDataProvider getLoremIpsumFieldDataProvider() {
		return new LoremIpsumFieldDataProvider(mRandom);
	}

	@Override
	public LoremIpsumFieldDataProvider getLoremIpsumFieldDataProvider(int pLength) {
		return new LoremIpsumFieldDataProvider(mRandom, pLength);
	}

	@Override
	public LoremIpsumFieldDataProvider getLoremIpsumFieldDataProvider(int pMinLength, int pMaxLength) {
		return new LoremIpsumFieldDataProvider(mRandom, pMinLength, pMaxLength);
	}

	@Override
	public LoremIpsumFieldDataProvider getLoremIpsumFieldDataProvider(int pMinLength, int pMaxLength, String pParagraphDelimiter) {
		return new LoremIpsumFieldDataProvider(mRandom, pMinLength, pMaxLength, pParagraphDelimiter);
	}

	@Override
	public <ENUM_TYPE extends Enum> RandomEnumFieldDataProvider<ENUM_TYPE> getRandomEnumFieldDataProvider(Class<ENUM_TYPE> pValue) {
		return new RandomEnumFieldDataProvider<>(mRandom, pValue);
	}

	@Override
	public <VALUE_TYPE> CustomListFieldDataProvider<VALUE_TYPE> getCustomListFieldDataProvider(int pInstances,
	                                                                                           FieldDataProvider<VALUE_TYPE> pFieldDataProvider) {
		return new CustomListFieldDataProvider<>(pInstances, pFieldDataProvider);
	}

	@Override
	public <VALUE_TYPE> CustomListRangeFieldDataProvider<VALUE_TYPE>
	getCustomListRangeFieldDataProvider(int pMinInstances, int pMaxInstances,
	                                    FieldDataProvider<VALUE_TYPE> pFieldDataProvider) {
		return new CustomListRangeFieldDataProvider<>(mRandom, pMinInstances, pMaxInstances, pFieldDataProvider);
	}
}
