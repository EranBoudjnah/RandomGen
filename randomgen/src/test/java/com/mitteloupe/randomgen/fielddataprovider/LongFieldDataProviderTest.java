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
public class LongFieldDataProviderTest {
	private LongFieldDataProvider<?> mCut;

	@Mock
	private Random mRandom;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void givenRandomDoubleValueWhenGenerateThenReturnsLongValue() {
		// Given
		mCut = new LongFieldDataProvider<>(mRandom);
		given(mRandom.nextDouble()).willReturn(0d);

		// When
		long result = mCut.generate(null);

		// Then
		assertEquals(Long.MIN_VALUE, result);

		// Given
		given(mRandom.nextDouble()).willReturn(0.99999999999d);

		// When
		result = mCut.generate(null);

		// Then
		assertEquals(Long.MAX_VALUE, result, 500000000L);
	}

	@Test
	public void givenRandomFloatValueAndRangeWhenGenerateThenReturnsCorrectValue() {
		// Given
		mCut = new LongFieldDataProvider<>(mRandom, 0L, 100L);
		given(mRandom.nextDouble()).willReturn(0d);

		// When
		long result = mCut.generate(null);

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