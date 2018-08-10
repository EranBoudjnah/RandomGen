package com.mitteloupe.randomgen.fielddataprovider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Created by Eran Boudjnah on 11/08/2018.
 */
public class RandomEnumFieldDataProviderTest {
	private RandomEnumFieldDataProvider<MagicColors> mCut;

	@Mock
	private Random mRandom;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mCut = new RandomEnumFieldDataProvider<>(mRandom, MagicColors.class);
	}

	@Test
	public void givenRandomDoubleValueWhenGenerateThenReturnsIntegerValue() {
		// Given
		given(mRandom.nextDouble()).willReturn(0d);

		// When
		MagicColors result = mCut.generate();

		// Then
		assertEquals(MagicColors.WHITE, result);

		// Given
		given(mRandom.nextDouble()).willReturn(0.5d);

		// When
		result = mCut.generate();

		// Then
		assertEquals(MagicColors.BLACK, result);

		// Given
		given(mRandom.nextDouble()).willReturn(0.99999999999d);

		// When
		result = mCut.generate();

		// Then
		assertEquals(MagicColors.GREEN, result);
	}

	private enum MagicColors {
		WHITE,
		BLUE,
		BLACK,
		RED,
		GREEN
	}
}