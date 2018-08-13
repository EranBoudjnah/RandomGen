package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Created by Eran Boudjnah on 10/08/2018.
 */
public class CustomListFieldDataProviderTest {
	private static final int INSTANCES = 3;

	private CustomListFieldDataProvider<?, String> mCut;

	@Mock
	private FieldDataProvider<?, String> fieldDataProvider;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mCut = new CustomListFieldDataProvider<>(INSTANCES, fieldDataProvider);
	}

	@Test
	public void givenFixedSizeListOfRandomBytesWhenGenerateThenReturnsSameBytes() {
		// Given
		final String EXPECTED_RESULT_1 = "I'm the king of the world!";
		final String EXPECTED_RESULT_2 = "I'm on a boat!";
		final String EXPECTED_RESULT_3 = "I'm cold!";
		given(fieldDataProvider.generate(null)).willReturn(EXPECTED_RESULT_1, EXPECTED_RESULT_2, EXPECTED_RESULT_3);

		// When
		List<String> result = mCut.generate(null);

		// Then
		assertEquals(Arrays.asList(EXPECTED_RESULT_1, EXPECTED_RESULT_2, EXPECTED_RESULT_3), result);
	}
}