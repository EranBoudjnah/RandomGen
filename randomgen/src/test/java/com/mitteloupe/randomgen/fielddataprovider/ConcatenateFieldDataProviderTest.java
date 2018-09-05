package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Created by Eran Boudjnah on 02/09/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConcatenateFieldDataProviderTest {
	private ConcatenateFieldDataProvider<TestClass> mCut;

	@Mock private TestClass mInstance;
	@Mock private FieldDataProvider<TestClass, String> mFieldDataProvider1;
	@Mock private FieldDataProvider<TestClass, String> mFieldDataProvider2;

	@Before
	public void setUp() {
		given(mFieldDataProvider1.generate(mInstance)).willReturn("Test1");
		given(mFieldDataProvider2.generate(mInstance)).willReturn("Test2");
	}

	@Test
	public void givenFieldDataProvidersWhenGenerateThenReturnsConcatenatedOutput() {
		// Given
		mCut = ConcatenateFieldDataProvider.getInstanceWithProviders(mFieldDataProvider1, mFieldDataProvider2);

		// When
		String result = mCut.generate(mInstance);

		// Then
		assertEquals("Test1Test2", result);
	}

	@Test
	public void givenDelimiterAndFieldDataProvidersWhenGenerateThenReturnsDelimitedConcatenatedOutput() {
		// Given
		mCut = ConcatenateFieldDataProvider.getInstanceWithDelimiterAndProviders(", ", mFieldDataProvider1, mFieldDataProvider2);

		// When
		String result = mCut.generate(mInstance);

		// Then
		assertEquals("Test1, Test2", result);
	}

	private class TestClass {
	}
}