package com.mitteloupe.randomgen.fielddataprovider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Created by Eran Boudjnah on 23/09/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class DateFieldDataProviderTest {
	private DateFieldDataProvider<?> mCut;

	@Mock
	private Random mRandom;

	@Test
	public void givenRandomDoubleValueWhenGenerateThenReturnsCorrectDate() {
		// Given
		mCut = DateFieldDataProvider.getInstance(mRandom);
		given(mRandom.nextDouble()).willReturn(0d);

		// When
		Date result = mCut.generate(null);

		// Then
		assertEquals(0, result.getTime());

		// Given
		given(mRandom.nextDouble()).willReturn(0.9999999999999999d);

		// When
		result = mCut.generate(null);

		// Then
		assertEquals(Long.MAX_VALUE, result.getTime(), 1L);
	}

	@Test
	public void givenZeroDoubleValueAndLatestTimestampWhenGenerateThenReturnsCorrectDate() {
		// Given
		mCut = DateFieldDataProvider.getInstanceWithLatestTimestamp(mRandom, 100L);
		given(mRandom.nextDouble()).willReturn(0d);

		// When
		Date result = mCut.generate(null);

		// Then
		assertEquals(0, result.getTime());
	}

	@Test
	public void givenMaximalDoubleValueAndLatestTimestampWhenGenerateThenReturnsCorrectDate() {
		// Given
		mCut = DateFieldDataProvider.getInstanceWithLatestTimestamp(mRandom, 100L);
		given(mRandom.nextDouble()).willReturn(0.9999999999999999d);

		// When
		Date result = mCut.generate(null);

		// Then
		assertEquals(100, result.getTime());
	}

	@Test
	public void givenZeroDoubleValueAndRangeWhenGenerateThenReturnsCorrectDate() {
		// Given
		mCut = DateFieldDataProvider.getInstanceWithRange(mRandom, 0L, 100L);
		given(mRandom.nextDouble()).willReturn(0d);

		// When
		Date result = mCut.generate(null);

		// Then
		assertEquals(0, result.getTime());
	}

	@Test
	public void givenMaximalDoubleValueAndRangeWhenGenerateThenReturnsCorrectDate() {
		// Given
		mCut = DateFieldDataProvider.getInstanceWithRange(mRandom, 0L, 100L);
		given(mRandom.nextDouble()).willReturn(0.9999999999999999d);

		// When
		Date result = mCut.generate(null);

		// Then
		assertEquals(100, result.getTime());
	}
}