package com.mitteloupe.randomgen.fielddataprovider;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Eran Boudjnah on 12/08/2018.
 */
public class ExplicitFieldDataProviderTest {
	@Test
	public void givenRandomDoubleValueWhenGenerateThenReturnsSameValue() {
		// Given
		String EXPECTED_RESULT = "Thou shall not pass!";
		ExplicitFieldDataProvider<?, String> cut = new ExplicitFieldDataProvider<>(EXPECTED_RESULT);

		// When
		String result = cut.generate(null);

		// Then
		assertEquals(EXPECTED_RESULT, result);
	}
}