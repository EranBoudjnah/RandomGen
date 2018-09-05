package com.mitteloupe.randomgen.fielddataprovider;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Created by Eran Boudjnah on 12/08/2018.
 */
public class ExplicitFieldDataProviderTest {
	@Test
	public void givenExplicitStringWhenGenerateThenReturnsSameValue() {
		// Given
		String EXPECTED_RESULT = "Thou shall not pass!";
		ExplicitFieldDataProvider<?, String> cut = new ExplicitFieldDataProvider<>(EXPECTED_RESULT);

		// When
		String result = cut.generate(null);

		// Then
		assertEquals(EXPECTED_RESULT, result);
	}

	@Test
	public void givenExplicitObjectWhenGenerateThenReturnsSameValue() {
		// Given
		TestClass EXPECTED_RESULT = new TestClass();
		ExplicitFieldDataProvider<?, TestClass> cut = new ExplicitFieldDataProvider<>(EXPECTED_RESULT);

		// When
		TestClass result = cut.generate(null);

		// Then
		assertSame(EXPECTED_RESULT, result);
	}

	private static class TestClass {
	}
}