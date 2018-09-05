package com.mitteloupe.randomgen.fielddataprovider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Created by Eran Boudjnah on 10/08/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class DoubleFieldDataProviderTest {
	private DoubleFieldDataProvider<?> mCut;

	@Mock
	private Random mRandom;

	@Test
	public void givenRandomDoubleValueWhenGenerateThenReturnsSameValue() {
		// Given
		mCut = DoubleFieldDataProvider.getInstance(mRandom);
		given(mRandom.nextDouble()).willReturn(0d);

		// When
		double result = mCut.generate(null);

		// Then
		assertEquals(0d, result, 0.00001);

		// Given
		given(mRandom.nextDouble()).willReturn(0.99999999999d);

		// When
		result = mCut.generate(null);

		// Then
		assertEquals(1d, result, 0.00001);
	}

	@Test
	public void givenRandomDoubleValueAndRangeWhenGenerateThenReturnsCorrectValue() {
		// Given
		mCut = DoubleFieldDataProvider.getInstanceWithRange(mRandom, Double.MIN_VALUE, Double.MAX_VALUE);
		given(mRandom.nextDouble()).willReturn(0d);

		// When
		double result = mCut.generate(null);

		// Then
		assertEquals(Double.MIN_VALUE, result, 0.00001);

		// Given
		given(mRandom.nextDouble()).willReturn(0.99999999999d);

		// When
		result = mCut.generate(null);

		// Then
		assertEquals(Double.MAX_VALUE, result, 1.0E300d);
	}
}