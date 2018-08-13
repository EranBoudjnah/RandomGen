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
public class FloatFieldDataProviderTest {
	private FloatFieldDataProvider<?> mCut;

	@Mock
	private Random mRandom;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenRandomFloatValueWhenGenerateThenReturnsSameValue() {
		// Given
		mCut = new FloatFieldDataProvider<>(mRandom);
		given(mRandom.nextFloat()).willReturn(0f);

		// When
		double result = mCut.generate(null);

		// Then
		assertEquals(0d, result, 0.00001);

		// Given
		given(mRandom.nextFloat()).willReturn(1f);

		// When
		result = mCut.generate(null);

		// Then
		assertEquals(1d, result, 0.00001);
	}

	@Test
	public void givenRandomFloatValueAndRangeWhenGenerateThenReturnsCorrectValue() {
		// Given
		mCut = new FloatFieldDataProvider<>(mRandom, Float.MIN_VALUE, Float.MAX_VALUE);
		given(mRandom.nextFloat()).willReturn(0f);

		// When
		double result = mCut.generate(null);

		// Then
		assertEquals(Float.MIN_VALUE, result, 0.00001);

		// Given
		given(mRandom.nextFloat()).willReturn(1f);

		// When
		result = mCut.generate(null);

		// Then
		assertEquals(Float.MAX_VALUE, result, 0.00001);
	}
}