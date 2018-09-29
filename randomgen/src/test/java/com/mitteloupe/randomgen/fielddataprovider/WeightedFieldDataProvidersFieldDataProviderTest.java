package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Created by Eran Boudjnah on 29/09/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class WeightedFieldDataProvidersFieldDataProviderTest {
	private static final String FIRST_VALUE = "Superman";
	private static final String SECOND_VALUE = "Spider Man";
	private static final String THIRD_VALUE = "Batman";

	@Mock
	private Random mRandom;
	@Mock
	private FieldDataProvider<TestClass, String> mFieldDataProvider;

	private WeightedFieldDataProvidersFieldDataProvider<TestClass, String> mCut;

	@Before
	public void setUp() {
		given(mFieldDataProvider.generate(any(TestClass.class))).willReturn(FIRST_VALUE);

		mCut = new WeightedFieldDataProvidersFieldDataProvider<>(mRandom, mFieldDataProvider);
	}

	@Test
	public void givenAnyInstanceWhenGenerateThenReturnsFirstValue() {
		// Given
		TestClass instance = new TestClass();

		// When
		String result = mCut.generate(instance);

		// Then
		assertEquals(FIRST_VALUE, result);
	}

	@Test
	public void givenASecondFieldDataProviderAndAnyInstanceWhenGenerateThenReturnsExpectedValue() {
		// Given
		TestClass instance = new TestClass();
		FieldDataProvider<TestClass, String> fieldDataProvider2 = mock(FieldDataProvider.class);
		given(fieldDataProvider2.generate(instance)).willReturn(SECOND_VALUE);
		mCut.addFieldDataProvider(fieldDataProvider2, 2f);
		FieldDataProvider<TestClass, String> fieldDataProvider3 = mock(FieldDataProvider.class);
		given(fieldDataProvider3.generate(instance)).willReturn(THIRD_VALUE);
		mCut.addFieldDataProvider(fieldDataProvider3, 3f);
		given(mRandom.nextDouble()).willReturn(0.7d);

		// When
		String result = mCut.generate(instance);

		// Then
		assertEquals(THIRD_VALUE, result);

		// Given
		given(mRandom.nextDouble()).willReturn(0.3d);

		// When
		result = mCut.generate(instance);

		// Then
		assertEquals(SECOND_VALUE, result);

		// Given
		given(mRandom.nextDouble()).willReturn(0.1d);

		// When
		result = mCut.generate(instance);

		// Then
		assertEquals(FIRST_VALUE, result);
	}

	private static class TestClass {
	}
}