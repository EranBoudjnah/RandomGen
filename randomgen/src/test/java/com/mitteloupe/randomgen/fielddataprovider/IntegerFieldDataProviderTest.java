package com.mitteloupe.randomgen.fielddataprovider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Created by Eran Boudjnah on 10/08/2018.
 */
public class IntegerFieldDataProviderTest {
	private IntegerFieldDataProvider<?> mCut;

	@Mock
	private Random mRandom;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void givenRandomDoubleValueWhenGenerateThenReturnsIntegerValue() {
		// Given
		mCut = new IntegerFieldDataProvider<>(mRandom);
		given(mRandom.nextDouble()).willReturn(0d);

		// When
		int result = mCut.generate(null);

		// Then
		assertEquals(Integer.MIN_VALUE, result);

		// Given
		given(mRandom.nextDouble()).willReturn(0.99999999999d);

		// When
		result = mCut.generate(null);

		// Then
		assertEquals(Integer.MAX_VALUE, result);
	}

	@Test
	public void givenRandomFloatValueAndRangeWhenGenerateThenReturnsCorrectValue() {
		// Given
		mCut = new IntegerFieldDataProvider<>(mRandom, 0, 100);
		given(mRandom.nextDouble()).willReturn(0d);

		// When
		int result = mCut.generate(null);

		// Then
		assertEquals(0, result);

		// Given
		given(mRandom.nextDouble()).willReturn(0.99999999999d);

		// When
		result = mCut.generate(null);

		// Then
		assertEquals(100, result);
	}
}