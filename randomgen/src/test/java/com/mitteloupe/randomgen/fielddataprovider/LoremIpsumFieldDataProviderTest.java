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
public class LoremIpsumFieldDataProviderTest {
	private LoremIpsumFieldDataProvider<?> mCut;

	@Mock private Random mRandom;

	@Test
	public void whenGenerateThenReturnsOneInstanceOfLoremIpsum() {
		// Given
		mCut = LoremIpsumFieldDataProvider.getInstance();

		// When
		String result = mCut.generate(null);

		// Then
		assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et doloremagna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute iruredolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum.", result);
	}

	@Test
	public void givenLengthShorterThanWholeLoremIpsumWhenGenerateThenReturnsCorrectLengthOfLoremIpsum() {
		// Given
		mCut = LoremIpsumFieldDataProvider.getInstanceWithLength(39);

		// When
		String result = mCut.generate(null);

		// Then
		assertEquals("Lorem ipsum dolor sit amet, consectetur", result);
	}

	@Test
	public void givenLengthLongerThanWholeLoremIpsumWhenGenerateThenReturnsCorrectLengthOfLoremIpsum() {
		// Given
		mCut = LoremIpsumFieldDataProvider.getInstanceWithLength(449);

		// When
		String result = mCut.generate(null);

		// Then
		assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et doloremagna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute iruredolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum.\n" +
			"\nLorem", result);
	}

	@Test
	public void givenLengthRangeWhenGenerateThenReturnsCorrectLengthOfLoremIpsum() {
		// Given
		mCut = LoremIpsumFieldDataProvider.getInstanceWithRange(mRandom, 39, 41);
		given(mRandom.nextInt(3)).willReturn(0);

		// When
		String result = mCut.generate(null);

		// Then
		assertEquals("Lorem ipsum dolor sit amet, consectetur", result);

		// Given
		given(mRandom.nextInt(3)).willReturn(2);

		// When
		result = mCut.generate(null);

		// Then
		assertEquals("Lorem ipsum dolor sit amet, consectetur a", result);
	}

	@Test
	public void givenLengthRangeAndDelimiterWhenGenerateThenReturnsCorrectLengthOfLoremIpsum() {
		// Given
		mCut = LoremIpsumFieldDataProvider.getInstanceWithRangeAndDelimiter(mRandom, 444, 449, "**");
		given(mRandom.nextInt(6)).willReturn(5);

		// When
		String result = mCut.generate(null);

		// Then
		assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et doloremagna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute iruredolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum." +
			"**Lorem", result);

		// Given
		given(mRandom.nextInt(6)).willReturn(0);

		// When
		result = mCut.generate(null);

		// Then
		assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et doloremagna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute iruredolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum.",
			result);
	}
}