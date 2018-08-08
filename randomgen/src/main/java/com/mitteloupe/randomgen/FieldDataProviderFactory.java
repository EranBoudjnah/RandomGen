package com.mitteloupe.randomgen;

import com.mitteloupe.randomgen.fielddataprovider.BooleanFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.ByteFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.ByteListFieldDataProvider;
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
import com.mitteloupe.randomgen.fielddataprovider.LoremIpsumFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.RandomEnumFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.RgbFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.SequentialIntegerFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.UuidFieldDataProvider;

import java.util.List;

/**
 * Created by Eran Boudjnah on 07/08/2018.
 */
interface FieldDataProviderFactory {
	<VALUE_TYPE> ExplicitFieldDataProvider<VALUE_TYPE> getExplicitFieldDataProvider(VALUE_TYPE pValue);

	<VALUE_TYPE> GenericListFieldDataProvider<VALUE_TYPE> getGenericListFieldDataProvider(List<VALUE_TYPE> pImmutableList);

	BooleanFieldDataProvider getBooleanFieldDataProvider();

	ByteFieldDataProvider getByteFieldDataProvider();

	ByteListFieldDataProvider getByteListFieldDataProvider(int pSize);

	ByteListFieldDataProvider getByteListFieldDataProvider(int pMinSize, int pMaxSize);

	DoubleFieldDataProvider getDoubleFieldDataProvider();

	FloatFieldDataProvider getFloatFieldDataProvider();

	IntegerFieldDataProvider getIntegerFieldDataProvider();

	LongFieldDataProvider getLongFieldDataProvider();

	DoubleRangeFieldDataProvider getDoubleRangeFieldDataProvider(double pMinimum, double pMaximum);

	FloatRangeFieldDataProvider getFloatRangeFieldDataProvider(float pMinimum, float pMaximum);

	IntegerRangeFieldDataProvider getIntegerRangeFieldDataProvider(int pMinimum, int pMaximum);

	LongRangeFieldDataProvider getLongRangeFieldDataProvider(long pMinimum, long pMaximum);

	SequentialIntegerFieldDataProvider getSequentialIntegerFieldDataProvider();

	UuidFieldDataProvider getUuidFieldDataProvider();

	RgbFieldDataProvider getRgbFieldDataProvider(boolean pAlpha);

	LoremIpsumFieldDataProvider getLoremIpsumFieldDataProvider();

	LoremIpsumFieldDataProvider getLoremIpsumFieldDataProvider(int pLength);

	LoremIpsumFieldDataProvider getLoremIpsumFieldDataProvider(int pMinLength, int pMaxLength);

	LoremIpsumFieldDataProvider getLoremIpsumFieldDataProvider(int pMinLength, int pMaxLength, String pParagraphDelimiter);

	<ENUM_TYPE extends Enum> RandomEnumFieldDataProvider<ENUM_TYPE> getRandomEnumFieldDataProvider(ENUM_TYPE pValue);

	<VALUE_TYPE> CustomListFieldDataProvider<VALUE_TYPE> getCustomListFieldDataProvider(int pInstances,
	                                                                                    FieldDataProvider<VALUE_TYPE> pFieldDataProvider);

	<VALUE_TYPE> CustomListRangeFieldDataProvider<VALUE_TYPE> getCustomListRangeFieldDataProvider(int pMinInstances, int pMaxInstances,
	                                                                                              FieldDataProvider<VALUE_TYPE> pFieldDataProvider);
}
