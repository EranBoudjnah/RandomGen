package com.mitteloupe.randomgen.fielddataprovider;

import org.junit.Before;
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
public class BooleanFieldDataProviderTest {
	private BooleanFieldDataProvider<?> mCut;

	@Mock private Random mRandom;

	@Before
	public void setUp() {
		mCut = new BooleanFieldDataProvider<>(mRandom);
	}

	@Test
	public void givenFalseRandomValueWhenGenerateThenReturnsFalse() {
		// Given
		given(mRandom.nextBoolean()).willReturn(false);

		// When
		Boolean result = mCut.generate(null);

		// Then
		assertEquals(false, result);
	}

	@Test
	public void givenTrueRandomValueWhenGenerateThenReturnsTrue() {
		// Given
		given(mRandom.nextBoolean()).willReturn(true);

		// When
		Boolean result = mCut.generate(null);

		// Then
		assertEquals(true, result);
	}
}