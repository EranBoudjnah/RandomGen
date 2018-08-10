package com.mitteloupe.randomgen;

import java.util.List;

/**
 * Created by Eran Boudjnah on 07/08/2018.
 */
interface FieldDataProviderFactory {
	<VALUE_TYPE> FieldDataProvider<VALUE_TYPE> getExplicitFieldDataProvider(VALUE_TYPE pValue);

	<VALUE_TYPE> FieldDataProvider<VALUE_TYPE> getGenericListFieldDataProvider(List<VALUE_TYPE> pImmutableList);

	FieldDataProvider<Boolean> getBooleanFieldDataProvider();

	FieldDataProvider<Byte> getByteFieldDataProvider();

	FieldDataProvider<List<Byte>> getByteListFieldDataProvider(int pSize);

	FieldDataProvider<List<Byte>> getByteListFieldDataProvider(int pMinSize, int pMaxSize);

	FieldDataProvider<Double> getDoubleFieldDataProvider();

	FieldDataProvider<Double> getDoubleFieldDataProvider(double pMinimum, double pMaximum);

	FieldDataProvider<Float> getFloatFieldDataProvider();

	FieldDataProvider<Float> getFloatFieldDataProvider(float pMinimum, float pMaximum);

	FieldDataProvider<Integer> getIntegerFieldDataProvider();

	FieldDataProvider<Integer> getIntegerFieldDataProvider(int pMinimum, int pMaximum);

	FieldDataProvider<Long> getLongFieldDataProvider();

	FieldDataProvider<Long> getLongFieldDataProvider(long pMinimum, long pMaximum);

	FieldDataProvider<Integer> getSequentialIntegerFieldDataProvider();

	FieldDataProvider<Integer> getSequentialIntegerFieldDataProvider(int pStartValue);

	FieldDataProvider<String> getUuidFieldDataProvider();

	FieldDataProvider<String> getRgbFieldDataProvider(boolean pAlpha);

	FieldDataProvider<String> getLoremIpsumFieldDataProvider();

	FieldDataProvider<String> getLoremIpsumFieldDataProvider(int pLength);

	FieldDataProvider<String> getLoremIpsumFieldDataProvider(int pMinLength, int pMaxLength);

	FieldDataProvider<String> getLoremIpsumFieldDataProvider(int pMinLength, int pMaxLength, String pParagraphDelimiter);

	<ENUM_TYPE extends Enum> FieldDataProvider<ENUM_TYPE> getRandomEnumFieldDataProvider(Class<ENUM_TYPE> pValue);

	<VALUE_TYPE> FieldDataProvider<List<VALUE_TYPE>> getCustomListFieldDataProvider(int pInstances, FieldDataProvider<VALUE_TYPE> pFieldDataProvider);

	<VALUE_TYPE> FieldDataProvider<List<VALUE_TYPE>> getCustomListRangeFieldDataProvider(int pMinInstances, int pMaxInstances,
	                                                                                     FieldDataProvider<VALUE_TYPE> pFieldDataProvider);
}
