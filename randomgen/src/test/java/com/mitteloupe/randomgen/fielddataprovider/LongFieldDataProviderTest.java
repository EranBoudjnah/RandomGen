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
public class LongFieldDataProviderTest {
	private LongFieldDataProvider<?> mCut;

	@Mock private Random mRandom;

	@Test
	public void givenRandomDoubleValueWhenGenerateThenReturnsLongValue() {
		// Given
		mCut = LongFieldDataProvider.getInstance(mRandom);
		given(mRandom.nextDouble()).willReturn(0d);

		// When
		long result = mCut.generate(null);

		// Then
		assertEquals(Long.MIN_VALUE, result);

		// Given
		given(mRandom.nextDouble()).willReturn(0.9999999999999999d);

		// When
		result = mCut.generate(null);

		// Then
		assertEquals(Long.MAX_VALUE, result, 1L);
	}

	@Test
	public void givenRandomFloatValueAndRangeWhenGenerateThenReturnsCorrectValue() {
		// Given
		mCut = LongFieldDataProvider.getInstanceWithRange(mRandom, 0L, 100L);
		given(mRandom.nextDouble()).willReturn(0d);

		// When
		long result = mCut.generate(null);

		// Then
		assertEquals(0, result);

		// Given
		given(mRandom.nextDouble()).willReturn(0.9999999999999999d);

		// When
		result = mCut.generate(null);

		// Then
		assertEquals(100, result);
	}
}