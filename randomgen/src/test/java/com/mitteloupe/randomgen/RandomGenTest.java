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

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Created by Eran Boudjnah on 07/08/2018.
 */
@SuppressWarnings("unchecked")
public class RandomGenTest {
	private RandomGen.Builder<TestPerson> mBuilder;
	private RandomGen<TestPerson> mCut;

	@Mock private FieldDataProviderFactory<TestPerson> mFactory;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mBuilder = new RandomGen.Builder<>(new RandomGen.InstanceProvider<TestPerson>() {
			@Override
			public TestPerson provideInstance() {
				return new TestPerson();
			}
		}, mFactory);
	}

	@Test
	public void givenBuilderReturningExplicitlyWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		final String NAME = "Superman";

		ExplicitFieldDataProvider<TestPerson, String> explicitFieldDataProvider = mock(ExplicitFieldDataProvider.class);
		given(mFactory.getExplicitFieldDataProvider(NAME)).willReturn(explicitFieldDataProvider);
		given(explicitFieldDataProvider.generate(any(TestPerson.class))).willReturn(NAME);

		mCut = mBuilder
			.withField("mName")
			.returningExplicitly(NAME)
			.build();

		// When
		TestPerson testPerson = mCut.generate();

		// Then
		assertEquals(NAME, testPerson.getName());
	}

	@Test
	public void givenBuilderReturningFromListWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		final String NAME_1 = "Rocksteady";
		final String NAME_2 = "Bebop";

		List<String> namesList = Arrays.asList(NAME_1, NAME_2);
		GenericListFieldDataProvider<TestPerson, String> explicitFieldDataProvider = mock(GenericListFieldDataProvider.class);
		given(mFactory.getGenericListFieldDataProvider(namesList)).willReturn(explicitFieldDataProvider);
		given(explicitFieldDataProvider.generate(any(TestPerson.class))).willReturn(NAME_2);

		mCut = mBuilder
			.withField("mName")
			.returning(namesList)
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(NAME_2, testPerson.getName());

		// Given
		given(explicitFieldDataProvider.generate(any(TestPerson.class))).willReturn(NAME_1);

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(NAME_1, testPerson.getName());
	}

	@Test
	public void givenBuilderReturningBooleanWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		BooleanFieldDataProvider<TestPerson> booleanFieldDataProvider = mock(BooleanFieldDataProvider.class);
		given(mFactory.getBooleanFieldDataProvider()).willReturn(booleanFieldDataProvider);
		given(booleanFieldDataProvider.generate(any(TestPerson.class))).willReturn(false);

		mCut = mBuilder
			.withField("mIsBrave")
			.returningBoolean()
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertFalse(testPerson.isBrave());

		// Given
		given(booleanFieldDataProvider.generate(any(TestPerson.class))).willReturn(true);

		// When
		testPerson = mCut.generate();

		// Then
		assertTrue(testPerson.isBrave());
	}

	@Test
	public void givenBuilderReturningByteWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		ByteFieldDataProvider<TestPerson> byteFieldDataProvider = mock(ByteFieldDataProvider.class);
		given(mFactory.getByteFieldDataProvider()).willReturn(byteFieldDataProvider);
		given(byteFieldDataProvider.generate(any(TestPerson.class))).willReturn((byte)3);

		mCut = mBuilder
			.withField("mBite")
			.returningByte()
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals((byte)3, testPerson.getBite());
	}

	@Test
	public void givenBuilderReturningByteListWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		ByteListFieldDataProvider<TestPerson> byteListFieldDataProvider = mock(ByteListFieldDataProvider.class);
		given(mFactory.getByteListFieldDataProvider(5)).willReturn(byteListFieldDataProvider);
		List<Byte> byteList = Arrays.asList((byte)1, (byte)2, (byte)3, (byte)4, (byte)5);
		given(byteListFieldDataProvider.generate(any(TestPerson.class))).willReturn(byteList);

		mCut = mBuilder
			.withField("mBites")
			.returningBytes(5)
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(byteList, Arrays.asList(testPerson.getBites()));
	}

	@Test
	public void givenBuilderReturningByteListWithRangeWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		ByteListFieldDataProvider<TestPerson> byteListFieldDataProvider = mock(ByteListFieldDataProvider.class);
		given(mFactory.getByteListFieldDataProvider(4, 5)).willReturn(byteListFieldDataProvider);
		List<Byte> byteList = Arrays.asList((byte)1, (byte)2, (byte)3, (byte)4, (byte)5);
		given(byteListFieldDataProvider.generate(any(TestPerson.class))).willReturn(byteList);

		mCut = mBuilder
			.withField("mBites")
			.returningBytes(4, 5)
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(byteList, Arrays.asList(testPerson.getBites()));
	}

	@Test
	public void givenBuilderReturningDoubleWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		DoubleFieldDataProvider<TestPerson> doubleFieldDataProvider = mock(DoubleFieldDataProvider.class);
		given(mFactory.getDoubleFieldDataProvider()).willReturn(doubleFieldDataProvider);
		final double EXPECTED_VALUE = 1.2345;
		given(doubleFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mWealth")
			.returningDouble()
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getWealth());
	}

	@Test
	public void givenBuilderReturningDoubleRangeWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		DoubleFieldDataProvider<TestPerson> doubleRangeFieldDataProvider = mock(DoubleFieldDataProvider.class);
		given(mFactory.getDoubleFieldDataProvider(1d, 2d)).willReturn(doubleRangeFieldDataProvider);
		final double EXPECTED_VALUE = 1.2345;
		given(doubleRangeFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mWealth")
			.returning(1d, 2d)
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getWealth());
	}


	@Test
	public void givenBuilderReturningFloatWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		FloatFieldDataProvider<TestPerson> floatFieldDataProvider = mock(FloatFieldDataProvider.class);
		given(mFactory.getFloatFieldDataProvider()).willReturn(floatFieldDataProvider);
		final float EXPECTED_VALUE = 1.23f;
		given(floatFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mHeight")
			.returningFloat()
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getHeight());
	}


	@Test
	public void givenBuilderReturningFloatRangeWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		FloatFieldDataProvider<TestPerson> floatRangeFieldDataProvider = mock(FloatFieldDataProvider.class);
		given(mFactory.getFloatFieldDataProvider(1f, 2f)).willReturn(floatRangeFieldDataProvider);
		final float EXPECTED_VALUE = 1.23f;
		given(floatRangeFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mHeight")
			.returning(1f, 2f)
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getHeight());
	}

	@Test
	public void givenBuilderReturningIntegerWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		IntegerFieldDataProvider integerFieldDataProvider = mock(IntegerFieldDataProvider.class);
		given(mFactory.getIntegerFieldDataProvider()).willReturn(integerFieldDataProvider);
		final int EXPECTED_VALUE = 400;
		given(integerFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mCandiesCount")
			.returningInteger()
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getCandiesCount());
	}

	@Test
	public void givenBuilderReturningIntegerRangeWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		IntegerFieldDataProvider<TestPerson> integerFieldDataProvider = mock(IntegerFieldDataProvider.class);
		given(mFactory.getIntegerFieldDataProvider(300, 500)).willReturn(integerFieldDataProvider);
		final int EXPECTED_VALUE = 400;
		given(integerFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mCandiesCount")
			.returning(300, 500)
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getCandiesCount());
	}

	@Test
	public void givenBuilderReturningLongWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		LongFieldDataProvider<TestPerson> longFieldDataProvider = mock(LongFieldDataProvider.class);
		given(mFactory.getLongFieldDataProvider()).willReturn(longFieldDataProvider);
		final long EXPECTED_VALUE = 1337L;
		given(longFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mSoLong")
			.returningLong()
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getSoLong());
	}

	@Test
	public void givenBuilderReturningLongRangeWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		LongFieldDataProvider<TestPerson> longFieldDataProvider = mock(LongFieldDataProvider.class);
		given(mFactory.getLongFieldDataProvider(1000L, 2000L)).willReturn(longFieldDataProvider);
		final long EXPECTED_VALUE = 1337L;
		given(longFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mSoLong")
			.returning(1000L, 2000L)
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getSoLong());
	}

	@Test
	public void givenBuilderReturningSequentialIntegerWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		SequentialIntegerFieldDataProvider<TestPerson> sequentialIntegerFieldDataProvider = mock(SequentialIntegerFieldDataProvider.class);
		given(mFactory.getSequentialIntegerFieldDataProvider()).willReturn(sequentialIntegerFieldDataProvider);
		final int EXPECTED_VALUE = 1234567;
		given(sequentialIntegerFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mId")
			.returningSequentialInteger()
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getId());
	}

	@Test
	public void givenBuilderReturningSequentialIntegerWithStartValueWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		SequentialIntegerFieldDataProvider<TestPerson> sequentialIntegerFieldDataProvider = mock(SequentialIntegerFieldDataProvider.class);
		given(mFactory.getSequentialIntegerFieldDataProvider(5)).willReturn(sequentialIntegerFieldDataProvider);
		final int EXPECTED_VALUE = 5;
		given(sequentialIntegerFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mId")
			.returningSequentialInteger(5)
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getId());
	}

	@Test
	public void givenBuilderReturningUuidWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		UuidFieldDataProvider<TestPerson> uuidFieldDataProvider = mock(UuidFieldDataProvider.class);
		given(mFactory.getUuidFieldDataProvider()).willReturn(uuidFieldDataProvider);
		final String EXPECTED_VALUE = "8b3728d0-9c1d-11e8-98d0-529269fb1459";
		given(uuidFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mUuid")
			.returningUuid()
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getUuid());
	}

	@Test
	public void givenBuilderReturningRgbWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		RgbFieldDataProvider<TestPerson> rgbFieldDataProvider = mock(RgbFieldDataProvider.class);
		RgbFieldDataProvider<TestPerson> rgbaFieldDataProvider = mock(RgbFieldDataProvider.class);
		given(mFactory.getRgbFieldDataProvider(false)).willReturn(rgbFieldDataProvider);
		given(mFactory.getRgbFieldDataProvider(true)).willReturn(rgbaFieldDataProvider);
		final String EXPECTED_VALUE_RGB = "#AABBAA";
		final String EXPECTED_VALUE_RGBA = "#FFAABBAA";
		given(rgbFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE_RGB);
		given(rgbaFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE_RGBA);

		mCut = mBuilder
			.withField("mShirtColor")
			.returningRgb(true)
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE_RGBA, testPerson.getShirtColor());

		// Given
		mCut = mBuilder
			.withField("mShirtColor")
			.returningRgb(false)
			.build();

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE_RGB, testPerson.getShirtColor());
	}

	@Test
	public void givenBuilderReturningLoremIpsumWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		LoremIpsumFieldDataProvider<TestPerson> loremIpsumFieldDataProvider = mock(LoremIpsumFieldDataProvider.class);
		given(mFactory.getLoremIpsumFieldDataProvider()).willReturn(loremIpsumFieldDataProvider);
		final String EXPECTED_VALUE = "Lorem ipsum and stuff";
		given(loremIpsumFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mBiography")
			.returningLoremIpsum()
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getBiography());
	}

	@Test
	public void givenBuilderReturningLoremIpsumWithLengthWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		LoremIpsumFieldDataProvider<TestPerson> loremIpsumFieldDataProvider = mock(LoremIpsumFieldDataProvider.class);
		given(mFactory.getLoremIpsumFieldDataProvider(21)).willReturn(loremIpsumFieldDataProvider);
		final String EXPECTED_VALUE = "Lorem ipsum and stuff";
		given(loremIpsumFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mBiography")
			.returningLoremIpsum(21)
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getBiography());
	}

	@Test
	public void givenBuilderReturningLoremIpsumWithLengthRangeWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		LoremIpsumFieldDataProvider<TestPerson> loremIpsumFieldDataProvider = mock(LoremIpsumFieldDataProvider.class);
		given(mFactory.getLoremIpsumFieldDataProvider(20, 22)).willReturn(loremIpsumFieldDataProvider);
		final String EXPECTED_VALUE = "Lorem ipsum and stuff";
		given(loremIpsumFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mBiography")
			.returningLoremIpsum(20, 22)
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getBiography());
	}

	@Test
	public void givenBuilderReturningLoremIpsumWithLengthRangeAndDelimiterWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		LoremIpsumFieldDataProvider<TestPerson> loremIpsumFieldDataProvider = mock(LoremIpsumFieldDataProvider.class);
		given(mFactory.getLoremIpsumFieldDataProvider(20, 22, "\n")).willReturn(loremIpsumFieldDataProvider);
		final String EXPECTED_VALUE = "Lorem ipsum and stuff";
		given(loremIpsumFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mBiography")
			.returningLoremIpsum(20, 22, "\n")
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getBiography());
	}

	@Test
	public void givenBuilderReturningEnumWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		RandomEnumFieldDataProvider<TestPerson, Gender> randomEnumFieldDataProvider = mock(RandomEnumFieldDataProvider.class);
		given(mFactory.getRandomEnumFieldDataProvider(Gender.class)).willReturn(randomEnumFieldDataProvider);
		given(randomEnumFieldDataProvider.generate(any(TestPerson.class))).willReturn(Gender.MALE);

		mCut = mBuilder
			.withField("mGender")
			.returning(Gender.class)
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(Gender.MALE, testPerson.getGender());
	}

	@Test
	public void givenBuilderReturningFieldDataProviderWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		FieldDataProvider<TestPerson, String> fieldDataProvider = mock(FieldDataProvider.class);
		String EXPECTED_VALUE = "Inigo Montoya";
		given(fieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUE);

		mCut = mBuilder
			.withField("mName")
			.returning(fieldDataProvider)
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUE, testPerson.getName());
	}

	@Test
	public void givenBuilderReturningCustomListFieldDataProviderWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		FieldDataProvider<TestPerson, String> fieldDataProvider = mock(FieldDataProvider.class);
		CustomListFieldDataProvider<TestPerson, String> customListFieldDataProvider = mock(CustomListFieldDataProvider.class);
		given(mFactory.getCustomListFieldDataProvider(3, fieldDataProvider)).willReturn(customListFieldDataProvider);
		List<String> EXPECTED_VALUES = Arrays.asList("The Shadow", "Captain Hammer", "Mr. Nobody");
		given(customListFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUES);

		mCut = mBuilder
			.withField("mAliases")
			.returning(3, fieldDataProvider)
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUES, testPerson.getAliases());
	}

	@Test
	public void givenBuilderReturningCustomListRangeFieldDataProviderWhenGenerateThenInstanceHasCorrectValue() {
		// Given
		FieldDataProvider<TestPerson, String> fieldDataProvider = mock(FieldDataProvider.class);
		CustomListRangeFieldDataProvider<TestPerson, String> customListRangeFieldDataProvider = mock(CustomListRangeFieldDataProvider.class);
		given(mFactory.getCustomListRangeFieldDataProvider(2, 4, fieldDataProvider)).willReturn(customListRangeFieldDataProvider);
		List<String> EXPECTED_VALUES = Arrays.asList("The Shadow", "Captain Hammer", "Mr. Nobody");
		given(customListRangeFieldDataProvider.generate(any(TestPerson.class))).willReturn(EXPECTED_VALUES);

		mCut = mBuilder
			.withField("mAliases")
			.returning(2, 4, fieldDataProvider)
			.build();

		TestPerson testPerson;

		// When
		testPerson = mCut.generate();

		// Then
		assertEquals(EXPECTED_VALUES, testPerson.getAliases());
	}

	@Test
	public void givenInvalidValueWhenGenerateThenInstanceThrowsIllegalArgumentException() {
		// Given
		final String NAME = "Superman";

		ExplicitFieldDataProvider<TestPerson, String> explicitFieldDataProvider = mock(ExplicitFieldDataProvider.class);
		given(mFactory.getExplicitFieldDataProvider(NAME)).willReturn(explicitFieldDataProvider);
		given(explicitFieldDataProvider.generate(any(TestPerson.class))).willReturn(NAME);

		mCut = mBuilder
			.withField("mCandiesCount")
			.returningExplicitly(NAME)
			.build();

		Throwable caughtException = null;

		try {
			// When
			mCut.generate();

		} catch (Throwable exception) {
			// Then
			caughtException = exception;
		}

		assertTrue(caughtException instanceof IllegalArgumentException);
		assertEquals("Cannot set field mCandiesCount due to invalid value", caughtException.getMessage());
	}

	@Test
	public void givenNonExistentFieldWhenGenerateThenInstanceThrowsIllegalArgumentException() {
		// Given
		final String NAME = "Superman";

		ExplicitFieldDataProvider<TestPerson, String> explicitFieldDataProvider = mock(ExplicitFieldDataProvider.class);
		given(mFactory.getExplicitFieldDataProvider(NAME)).willReturn(explicitFieldDataProvider);
		given(explicitFieldDataProvider.generate(any(TestPerson.class))).willReturn(NAME);

		mCut = mBuilder
			.withField("mUnknownField")
			.returningExplicitly(NAME)
			.build();

		Throwable caughtException = null;

		try {
			// When
			mCut.generate();

		} catch (Throwable exception) {
			// Then
			caughtException = exception;
		}

		assertTrue(caughtException instanceof IllegalArgumentException);
		assertEquals("Cannot set field mUnknownField - field not found", caughtException.getMessage());
	}

	@SuppressWarnings("unused") // Setting is done via RandomGen :)
	private static class TestPerson {
		private int mId;
		private String mName;
		private List<String> mAliases;
		private boolean mIsBrave;
		private byte mBite; // Everybody gets hungry sometimes!
		private Byte[] mBites; // Sometimes you get even hungrier!
		private double mWealth;
		private float mHeight;
		private int mCandiesCount;
		private long mSoLong;
		private String mUuid;
		private String mShirtColor;
		private String mBiography;
		private Gender mGender;

		int getId() {
			return mId;
		}

		String getUuid() {
			return mUuid;
		}

		String getName() {
			return mName;
		}

		boolean isBrave() {
			return mIsBrave;
		}

		byte getBite() {
			return mBite;
		}

		Byte[] getBites() {
			return mBites;
		}

		double getWealth() {
			return mWealth;
		}

		float getHeight() {
			return mHeight;
		}

		int getCandiesCount() { // Like taking candies from a baby!
			return mCandiesCount;
		}

		long getSoLong() {
			return mSoLong;
		}

		String getShirtColor() {
			return mShirtColor;
		}

		String getBiography() {
			return mBiography;
		}

		Gender getGender() {
			return mGender;
		}

		List<String> getAliases() {
			return mAliases;
		}
	}

	private enum Gender {
		MALE, FEMALE
	}
}