package com.mitteloupe.randomgen.fielddataprovider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Created by Eran Boudjnah on 10/08/2018.
 */
public class GenericListFieldDataProviderTest {
	private GenericListFieldDataProvider<String> mCut;

	@Mock
	private Random mRandom;

	private List<String> mValues;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mValues = Arrays.asList("The First", "The Last", "Eternity");

		mCut = new GenericListFieldDataProvider<>(mRandom, mValues);
	}

	@Test
	public void givenMinRandomValueWhenGenerateThenReturnsFirstValue() {
		// Given
		given(mRandom.nextDouble()).willReturn(0d);

		// When
		String result = mCut.generate();

		// Then
		assertEquals("The First", result);
	}

	@Test
	public void givenMaxRandomValueWhenGenerateThenReturnsLastValue() {
		// Given
		given(mRandom.nextDouble()).willReturn(0.99999999999d);

		// When
		String result = mCut.generate();

		// Then
		assertEquals("Eternity", result);
	}

	@Test
	public void givenListIsModifiedWhenGenerateThenReturnsUnmodifiedValue() {
		// Given
		given(mRandom.nextDouble()).willReturn(0d);
		mValues.set(0, "The Firstest");

		// When
		String result = mCut.generate();

		// Then
		assertEquals("The First", result);
	}
}