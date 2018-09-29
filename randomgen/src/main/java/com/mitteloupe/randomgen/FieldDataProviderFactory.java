package com.mitteloupe.randomgen;

import java.util.Date;
import java.util.List;

/**
 * Created by Eran Boudjnah on 07/08/2018.
 */
interface FieldDataProviderFactory<OUTPUT_TYPE> {
	<VALUE_TYPE> FieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> getExplicitFieldDataProvider(VALUE_TYPE pValue);

	<VALUE_TYPE> FieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> getGenericListFieldDataProvider(List<VALUE_TYPE> pImmutableList);

	FieldDataProvider<OUTPUT_TYPE, Boolean> getBooleanFieldDataProvider();

	FieldDataProvider<OUTPUT_TYPE, Byte> getByteFieldDataProvider();

	FieldDataProvider<OUTPUT_TYPE, List<Byte>> getByteListFieldDataProvider(int pSize);

	FieldDataProvider<OUTPUT_TYPE, List<Byte>> getByteListFieldDataProvider(int pMinSize, int pMaxSize);

	FieldDataProvider<OUTPUT_TYPE, Double> getDoubleFieldDataProvider();

	FieldDataProvider<OUTPUT_TYPE, Double> getDoubleFieldDataProvider(double pMinimum, double pMaximum);

	FieldDataProvider<OUTPUT_TYPE, Float> getFloatFieldDataProvider();

	FieldDataProvider<OUTPUT_TYPE, Float> getFloatFieldDataProvider(float pMinimum, float pMaximum);

	FieldDataProvider<OUTPUT_TYPE, Integer> getIntegerFieldDataProvider();

	FieldDataProvider<OUTPUT_TYPE, Integer> getIntegerFieldDataProvider(int pMinimum, int pMaximum);

	FieldDataProvider<OUTPUT_TYPE, Long> getLongFieldDataProvider();

	FieldDataProvider<OUTPUT_TYPE, Long> getLongFieldDataProvider(long pMinimum, long pMaximum);

	FieldDataProvider<OUTPUT_TYPE, Integer> getSequentialIntegerFieldDataProvider();

	FieldDataProvider<OUTPUT_TYPE, Integer> getSequentialIntegerFieldDataProvider(int pStartValue);

	FieldDataProvider<OUTPUT_TYPE, String> getUuidFieldDataProvider();

	FieldDataProvider<OUTPUT_TYPE, String> getRgbFieldDataProvider(boolean pAlpha);

	FieldDataProvider<OUTPUT_TYPE, Date> getDateFieldDataProvider();

	FieldDataProvider<OUTPUT_TYPE, Date> getDateFieldDataProvider(long pLatestTimestamp);

	FieldDataProvider<OUTPUT_TYPE, Date> getDateFieldDataProvider(long pEarliestTimestamp, long pLatestTimestamp);

	FieldDataProvider<OUTPUT_TYPE, String> getLoremIpsumFieldDataProvider();

	FieldDataProvider<OUTPUT_TYPE, String> getLoremIpsumFieldDataProvider(int pLength);

	FieldDataProvider<OUTPUT_TYPE, String> getLoremIpsumFieldDataProvider(int pMinLength, int pMaxLength);

	FieldDataProvider<OUTPUT_TYPE, String> getLoremIpsumFieldDataProvider(int pMinLength, int pMaxLength, String pParagraphDelimiter);

	FieldDataProvider<OUTPUT_TYPE, ?> getWeightedFieldDataProvidersFieldDataProvider(FieldDataProvider<OUTPUT_TYPE, ?> pFieldDataProvider);

	<ENUM_TYPE extends Enum> FieldDataProvider<OUTPUT_TYPE, ENUM_TYPE> getRandomEnumFieldDataProvider(Class<ENUM_TYPE> pValue);

	FieldDataProvider<OUTPUT_TYPE, String>
	getPaddedFieldDataProvider(int pMinimumLength, String pPaddingString, FieldDataProvider<OUTPUT_TYPE, ?> pFieldDataProvider);

	<VALUE_TYPE> FieldDataProvider<OUTPUT_TYPE, List<VALUE_TYPE>>
	getCustomListFieldDataProvider(int pInstances, FieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> pFieldDataProvider);

	<VALUE_TYPE> FieldDataProvider<OUTPUT_TYPE, List<VALUE_TYPE>>
	getCustomListRangeFieldDataProvider(int pMinInstances, int pMaxInstances, FieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> pFieldDataProvider);
}
