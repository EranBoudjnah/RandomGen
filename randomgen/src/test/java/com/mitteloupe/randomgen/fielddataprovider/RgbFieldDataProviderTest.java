package com.mitteloupe.randomgen.fielddataprovider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Created by Eran Boudjnah on 11/08/2018.
 */
public class RgbFieldDataProviderTest {
	private RgbFieldDataProvider<?> mCut;

	@Mock
	private Random mRandom;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenARandomGeneratorAndAlphaWhenGenerateThenReturnsRGBAValue() {
		// Given
		mCut = new RgbFieldDataProvider<>(mRandom, true);
		given(mRandom.nextInt(255)).willReturn(  173, 250, 17, 222);

		// When
		String result = mCut.generate(null);

		// Then
		assertEquals("#deadfa11", result);
	}

	@Test
	public void givenARandomGeneratorAndNoAlphaWhenGenerateThenReturnsRGBValue() {
		// Given
		mCut = new RgbFieldDataProvider<>(mRandom, false);
		given(mRandom.nextInt(255)).willReturn(  176, 85, 237);

		// When
		String result = mCut.generate(null);

		// Then
		assertEquals("#b055ed", result);
	}
}