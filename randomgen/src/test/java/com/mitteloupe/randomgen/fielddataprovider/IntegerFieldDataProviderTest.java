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
public class IntegerFieldDataProviderTest {
	private IntegerFieldDataProvider<?> mCut;

	@Mock private Random mRandom;

	@Test
	public void givenRandomDoubleValueWhenGenerateThenReturnsIntegerValue() {
		// Given
		mCut = IntegerFieldDataProvider.getInstance(mRandom);
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
		mCut = IntegerFieldDataProvider.getInstanceWithRange(mRandom, 0, 100);
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