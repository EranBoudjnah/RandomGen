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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by Eran Boudjnah on 11/08/2018.
 */
public class SimpleFieldDataProviderFactoryTest {
	private SimpleFieldDataProviderFactory mCut;

	@Mock private Random mRandom;
	@Mock private UuidGenerator mUuidGenerator;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mCut = new SimpleFieldDataProviderFactory(mRandom, mUuidGenerator);
	}

	@Test
	public void whenGetExplicitFieldDataProviderThenReturnsInstanceWithExpectedValue() throws Exception {
		// When
		String POPCORN = "Popcorn";
		ExplicitFieldDataProvider<String> dataProvider = mCut.getExplicitFieldDataProvider(POPCORN);

		// Then
		assertFieldEquals(dataProvider, "mValue", POPCORN);
	}

	@Test
	public void givenListOfValuesWhenGetGenericListFieldDataProviderThenReturnsInstanceWithCorrectFieldValues() throws Exception {
		// Given
		List<String> list = Arrays.asList("A", "B");

		// When
		GenericListFieldDataProvider dataProvider = mCut.getGenericListFieldDataProvider(list);

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mList", list);
	}

	@Test
	public void whenGetBooleanFieldDataProviderThenReturnsInstanceWithRandomSet() throws Exception {
		// When
		BooleanFieldDataProvider dataProvider = mCut.getBooleanFieldDataProvider();

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
	}

	@Test
	public void whenGetByteFieldDataProviderThenReturnsInstanceWithRandomSet() throws Exception {
		// When
		ByteFieldDataProvider dataProvider = mCut.getByteFieldDataProvider();

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
	}

	@Test
	public void givenFixedSizeWhenGetByteListFieldDataProviderThenReturnsInstanceWithCorrectFieldValues() throws Exception {
		// Given
		final int SIZE = 4;

		// When
		ByteListFieldDataProvider dataProvider = mCut.getByteListFieldDataProvider(SIZE);

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mMinSize", SIZE);
		assertFieldEquals(dataProvider, "mMaxSize", SIZE);
	}

	@Test
	public void givenRangedSizeWhenGetByteListFieldDataProviderThenReturnsInstanceWithCorrectFieldValues() throws Exception {
		// Given
		final int MIN_SIZE = 3;
		final int MAX_SIZE = 5;

		// When
		ByteListFieldDataProvider dataProvider = mCut.getByteListFieldDataProvider(MIN_SIZE, MAX_SIZE);

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mMinSize", MIN_SIZE);
		assertFieldEquals(dataProvider, "mMaxSize", MAX_SIZE);
	}

	@Test
	public void whenGetDoubleFieldDataProviderThenReturnsReturnsInstanceWithCorrectMinAndMax() throws Exception {
		// When
		DoubleFieldDataProvider dataProvider = mCut.getDoubleFieldDataProvider();

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mMinimum", 0d);
		assertFieldEquals(dataProvider, "mMaximum", 1d);
	}

	@Test
	public void givenRangeWhenGetDoubleFieldDataProviderThenReturnsReturnsInstanceWithCorrectMinAndMax() throws Exception {
		final double MIN_VALUE = -3d;
		final double MAX_VALUE = 3d;

		// When
		DoubleFieldDataProvider dataProvider = mCut.getDoubleFieldDataProvider(MIN_VALUE, MAX_VALUE);

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mMinimum", MIN_VALUE);
		assertFieldEquals(dataProvider, "mMaximum", MAX_VALUE);
	}

	@Test
	public void whenGetFloatFieldDataProviderThenReturnsReturnsInstanceWithCorrectMinAndMax() throws Exception {
		// When
		FloatFieldDataProvider dataProvider = mCut.getFloatFieldDataProvider();

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mMinimum", 0f);
		assertFieldEquals(dataProvider, "mMaximum", 1f);
	}

	@Test
	public void givenRangeWhenGetFloatFieldDataProviderThenReturnsReturnsInstanceWithCorrectMinAndMax() throws Exception {
		final float MIN_VALUE = -3f;
		final float MAX_VALUE = 3f;

		// When
		FloatFieldDataProvider dataProvider = mCut.getFloatFieldDataProvider(MIN_VALUE, MAX_VALUE);

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mMinimum", MIN_VALUE);
		assertFieldEquals(dataProvider, "mMaximum", MAX_VALUE);
	}

	@Test
	public void whenGetIntegerFieldDataProviderThenReturnsReturnsInstanceWithCorrectMinAndMax() throws Exception {
		// When
		IntegerFieldDataProvider dataProvider = mCut.getIntegerFieldDataProvider();

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mMinimum", Integer.MIN_VALUE);
		assertFieldEquals(dataProvider, "mMaximum", Integer.MAX_VALUE);
	}

	@Test
	public void givenRangeWhenGetIntegerFieldDataProviderThenReturnsReturnsInstanceWithCorrectMinAndMax() throws Exception {
		final int MIN_VALUE = -3;
		final int MAX_VALUE = 3;

		// When
		IntegerFieldDataProvider dataProvider = mCut.getIntegerFieldDataProvider(MIN_VALUE, MAX_VALUE);

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mMinimum", MIN_VALUE);
		assertFieldEquals(dataProvider, "mMaximum", MAX_VALUE);
	}

	@Test
	public void whenGetLongFieldDataProviderThenReturnsReturnsInstanceWithCorrectMinAndMax() throws Exception {
		// When
		LongFieldDataProvider dataProvider = mCut.getLongFieldDataProvider();

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mMinimum", Long.MIN_VALUE);
		assertFieldEquals(dataProvider, "mMaximum", Long.MAX_VALUE);
	}

	@Test
	public void givenRangeWhenGetLongFieldDataProviderThenReturnsReturnsInstanceWithCorrectMinAndMax() throws Exception {
		final long MIN_VALUE = -3L;
		final long MAX_VALUE = 3L;

		// When
		LongFieldDataProvider dataProvider = mCut.getLongFieldDataProvider(MIN_VALUE, MAX_VALUE);

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mMinimum", MIN_VALUE);
		assertFieldEquals(dataProvider, "mMaximum", MAX_VALUE);
	}

	@Test
	public void whenGetSequentialIntegerFieldDataProviderThenReturnsInstanceWithCounterAtDefault() throws Exception {
		// When
		SequentialIntegerFieldDataProvider dataProvider = mCut.getSequentialIntegerFieldDataProvider();

		// Then
		assertFieldEquals(dataProvider, "mCounter", 1);
	}

	@Test
	public void givenInitialValuewhenGetSequentialIntegerFieldDataProviderThenReturnsInstanceWithCounterSet() throws Exception {
		// When
		final int START_VALUE = 42;
		SequentialIntegerFieldDataProvider dataProvider = mCut.getSequentialIntegerFieldDataProvider(START_VALUE);

		// Then
		assertFieldEquals(dataProvider, "mCounter", START_VALUE);
	}

	@Test
	public void whenGetUuidFieldDataProviderThenReturnsInstanceWithUuidGeneratorSet() throws Exception {
		// When
		UuidFieldDataProvider dataProvider = mCut.getUuidFieldDataProvider();

		// Then
		assertFieldEquals(dataProvider, "mUuidGenerator", mUuidGenerator);
	}

	@Test
	public void givenAlphaWhenGetRgbFieldDataProviderThenReturnsInstanceWithCorrectAlphaFlagAndRandomSet() throws Exception {
		// When
		RgbFieldDataProvider dataProvider = mCut.getRgbFieldDataProvider(true);

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mAlpha", true);

		// When
		dataProvider = mCut.getRgbFieldDataProvider(false);

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mAlpha", false);
	}

	@Test
	public void whenGetLoremIpsumFieldDataProviderThenReturnsInstanceWithMinAndMaxLengthSetToOneWholeLoremIpsum() throws Exception {
		// When
		LoremIpsumFieldDataProvider dataProvider = mCut.getLoremIpsumFieldDataProvider();

		// Then
		assertFieldEquals(dataProvider, "mMinLength", 442);
		assertFieldEquals(dataProvider, "mMaxLength", 442);
	}

	@Test
	public void givenLengthWhenGetLoremIpsumFieldDataProviderThenReturnsInstanceWithCorrectPropertiesSet() throws Exception {
		// Given
		final int LENGTH = 64;

		// When
		LoremIpsumFieldDataProvider dataProvider = mCut.getLoremIpsumFieldDataProvider(LENGTH);

		// Then
		assertFieldEquals(dataProvider, "mMinLength", LENGTH);
		assertFieldEquals(dataProvider, "mMaxLength", LENGTH);
		assertFieldEquals(dataProvider, "mParagraphDelimiter", "\n\n");
	}

	@Test
	public void givenRangedLengthWhenGetLoremIpsumFieldDataProviderThenReturnsInstanceWithCorrectPropertiesSet() throws Exception {
		// Given
		final int MIN_LENGTH = 74;
		final int MAX_LENGTH = 75;

		// When
		LoremIpsumFieldDataProvider dataProvider = mCut.getLoremIpsumFieldDataProvider(MIN_LENGTH, MAX_LENGTH);

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mMinLength", MIN_LENGTH);
		assertFieldEquals(dataProvider, "mMaxLength", MAX_LENGTH);
		assertFieldEquals(dataProvider, "mParagraphDelimiter", "\n\n");
	}

	@Test
	public void givenRangeLengthAndDelimiterWhenGetLoremIpsumFieldDataProviderThenReturnsInstanceWithCorrectPropertiesSet() throws Exception {
		// Given
		final int MIN_LENGTH = 74;
		final int MAX_LENGTH = 75;
		final String PARAGRAPH_DELIMITER = "... ";

		// When
		LoremIpsumFieldDataProvider dataProvider = mCut.getLoremIpsumFieldDataProvider(MIN_LENGTH, MAX_LENGTH, PARAGRAPH_DELIMITER);

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mMinLength", MIN_LENGTH);
		assertFieldEquals(dataProvider, "mMaxLength", MAX_LENGTH);
		assertFieldEquals(dataProvider, "mParagraphDelimiter", PARAGRAPH_DELIMITER);
	}

	@Test
	public void whenGetRandomEnumFieldDataProviderThenReturnsInstanceWithRandomSet() throws Exception {
		// When
		RandomEnumFieldDataProvider<Rings> dataProvider = mCut.getRandomEnumFieldDataProvider(Rings.class);

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		Rings[] values = getFieldValue(dataProvider, "mPossibleValues");
		assertEquals(Arrays.asList(Rings.ONE_RING_TO_RULE_THEM_ALL, Rings.ONE_RING_TO_FIND_THEM, Rings.ONE_RING_TO_BRING_THEM_ALL),
			Arrays.asList(values));
	}

	@Test
	public void givenInstancesCountAndFieldDataProviderWhenGetCustomListFieldDataProviderThenReturnsInstanceWithCorrectPropertiesSet()
		throws Exception {
		// When
		FieldDataProvider<String> provider = mock(FieldDataProvider.class);
		final int INSTANCES = 5;
		CustomListFieldDataProvider<String> dataProvider = mCut.getCustomListFieldDataProvider(INSTANCES, provider);

		// Then
		assertFieldEquals(dataProvider, "mInstances", INSTANCES);
		assertFieldEquals(dataProvider, "mFieldDataProvider", provider);
	}

	@Test
	public void givenInstancesCountRangeAndFieldDataProviderWhenGetCustomListRangeFieldDataProviderThenReturnsInstanceWithCorrectPropertiesSet()
		throws Exception {
		// When
		FieldDataProvider<String> provider = mock(FieldDataProvider.class);
		final int MIN_INSTANCES = 5;
		final int MAX_INSTANCES = 5;
		CustomListRangeFieldDataProvider<String> dataProvider = mCut.getCustomListRangeFieldDataProvider(MIN_INSTANCES, MAX_INSTANCES, provider);

		// Then
		assertFieldEquals(dataProvider, "mRandom", mRandom);
		assertFieldEquals(dataProvider, "mMinInstances", MIN_INSTANCES);
		assertFieldEquals(dataProvider, "mMaxInstances", MAX_INSTANCES);
		assertFieldEquals(dataProvider, "mFieldDataProvider", provider);
	}

	private <DATA_TYPE> void assertFieldEquals(Object pObject, String pFieldName, DATA_TYPE pExpectedValue) throws Exception {
		DATA_TYPE value = getFieldValue(pObject, pFieldName);

		assertEquals(pExpectedValue, value);
	}

	private <DATA_TYPE> DATA_TYPE getFieldValue(Object pObject, String pFieldName) throws Exception {
		Field field = pObject.getClass().getDeclaredField(pFieldName);

		if (Modifier.isPrivate(field.getModifiers())) {
			field.setAccessible(true);
		}

		//noinspection unchecked
		return (DATA_TYPE)field.get(pObject);
	}

	private enum Rings {
		ONE_RING_TO_RULE_THEM_ALL,
		ONE_RING_TO_FIND_THEM,
		ONE_RING_TO_BRING_THEM_ALL
	}
}