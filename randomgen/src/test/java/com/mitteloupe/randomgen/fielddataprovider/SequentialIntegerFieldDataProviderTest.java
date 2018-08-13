package com.mitteloupe.randomgen.fielddataprovider;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Eran Boudjnah on 11/08/2018.
 */
public class SequentialIntegerFieldDataProviderTest {
	private SequentialIntegerFieldDataProvider<?> mCut;

	@Test
	public void givenNoInitialValueWhenGenerateThenReturnsCorrectSequence() {
		// Given
		mCut = new SequentialIntegerFieldDataProvider<>();

		// When
		int result = mCut.generate(null);

		// Then
		assertEquals(1, result);

		// When
		result = mCut.generate(null);

		// Then
		assertEquals(2, result);
	}

	@Test
	public void givenAnInitialValueWhenGenerateThenReturnsCorrectSequence() {
		// Given
		mCut = new SequentialIntegerFieldDataProvider<>(0xBED);

		// When
		int result = mCut.generate(null);

		// Then
		assertEquals(0xBED, result);

		// When
		result = mCut.generate(null);

		// Then
		assertEquals(0xBEE, result);
	}
}