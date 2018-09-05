package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Created by Eran Boudjnah on 04/09/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class PaddedFieldDataProviderTest {
	private PaddedFieldDataProvider<TestClass> mCut;

	@Mock private TestClass mInstance;
	@Mock private FieldDataProvider<TestClass, ?> mFieldDataProvider;

	@Test
	public void givenSinglePaddingCharacterAndMinimumLengthWhenGenerateThenReturnsExpectedString() {
		mCut = new PaddedFieldDataProvider<>(3, "0", mFieldDataProvider);
		given(mFieldDataProvider.generate(mInstance)).willReturn("7");

		String result = mCut.generate(mInstance);

		assertEquals("007", result);
	}

	@Test
	public void givenPaddingStringAndMinimumLengthWhenGenerateThenReturnsExpectedString() {
		mCut = new PaddedFieldDataProvider<>(3, "000", mFieldDataProvider);
		given(mFieldDataProvider.generate(mInstance)).willReturn("7");

		String result = mCut.generate(mInstance);

		assertEquals("007", result);
	}

	@Test
	public void givenStringAsLongAsPaddingWhenGenerateThenReturnsUnmodifiedString() {
		mCut = new PaddedFieldDataProvider<>(3, "000", mFieldDataProvider);
		given(mFieldDataProvider.generate(mInstance)).willReturn("123");

		String result = mCut.generate(mInstance);

		assertEquals("123", result);
	}

	@Test
	public void givenStringLongerThanPaddingWhenGenerateThenReturnsUnmodifiedString() {
		mCut = new PaddedFieldDataProvider<>(3, "000", mFieldDataProvider);
		given(mFieldDataProvider.generate(mInstance)).willReturn("1337");

		String result = mCut.generate(mInstance);

		assertEquals("1337", result);
	}

	private class TestClass {
	}
}