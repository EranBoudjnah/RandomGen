package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

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
public class CustomListRangeFieldDataProviderTest {
	private static final int MIN_INSTANCES = 1;
	private static final int MAX_INSTANCES = 5;

	private CustomListRangeFieldDataProvider<?, String> mCut;

	@Mock
	private Random mRandom;
	@Mock
	private FieldDataProvider<?, String> fieldDataProvider;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mCut = new CustomListRangeFieldDataProvider<>(mRandom, MIN_INSTANCES, MAX_INSTANCES, fieldDataProvider);
	}

	@Test
	public void givenFixedSizeListOfRandomBytesWhenGenerateThenReturnsSameBytes() {
		// Given
		final String EXPECTED_RESULT_1 = "I'm the king of the world!";
		final String EXPECTED_RESULT_2 = "I'm on a boat!";
		final String EXPECTED_RESULT_3 = "I'm cold!";
		given(fieldDataProvider.generate(null)).willReturn(EXPECTED_RESULT_1, EXPECTED_RESULT_2, EXPECTED_RESULT_3);
		given(mRandom.nextInt(5)).willReturn(2);

		// When
		List<String> result = mCut.generate(null);

		// Then
		assertEquals(Arrays.asList(EXPECTED_RESULT_1, EXPECTED_RESULT_2, EXPECTED_RESULT_3), result);
	}
}