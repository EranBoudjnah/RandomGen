package com.mitteloupe.randomgen.fielddataprovider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Created by Eran Boudjnah on 11/08/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class RgbFieldDataProviderTest {
	private RgbFieldDataProvider<?> mCut;

	@Mock private Random mRandom;

	@Test
	public void givenARandomGeneratorAndAlphaWhenGenerateThenReturnsRGBAValue() {
		// Given
		mCut = new RgbFieldDataProvider<>(mRandom, true);
		givenRandomValues(173, 250, 17, 222);

		// When
		String result = mCut.generate(null);

		// Then
		assertEquals("#deadfa11", result);
	}

	@Test
	public void givenARandomGeneratorAndNoAlphaWhenGenerateThenReturnsRGBValue() {
		// Given
		mCut = new RgbFieldDataProvider<>(mRandom, false);
		givenRandomValues(  176, 85, 237);

		// When
		String result = mCut.generate(null);

		// Then
		assertEquals("#b055ed", result);
	}

	private void givenRandomValues(Integer pValue, Integer... pMoreValues) {
		given(mRandom.nextInt(255)).willReturn(pValue, pMoreValues);
	}
}