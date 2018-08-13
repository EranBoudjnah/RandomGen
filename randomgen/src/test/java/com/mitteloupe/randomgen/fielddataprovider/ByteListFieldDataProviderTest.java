package com.mitteloupe.randomgen.fielddataprovider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;

/**
 * Created by Eran Boudjnah on 10/08/2018.
 */
public class ByteListFieldDataProviderTest {
	private ByteListFieldDataProvider<?> mCut;

	@Mock
	private Random mRandom;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenFixedSizeListOfRandomBytesWhenGenerateThenReturnsSameBytes() {
		// Given
		final List<Byte> EXPECTED_RESULT = Arrays.asList((byte)123, (byte)12, (byte)1);

		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) {
				byte[] result = invocation.getArgument(0);
				result[0] = EXPECTED_RESULT.get(0);
				result[1] = EXPECTED_RESULT.get(1);
				result[2] = EXPECTED_RESULT.get(2);
				return null;
			}
		}).when(mRandom).nextBytes(any(byte[].class));

		mCut = new ByteListFieldDataProvider<>(mRandom, 3);

		// When
		List<Byte> result = mCut.generate(null);

		// Then
		assertEquals(EXPECTED_RESULT, result);
	}

	@Test
	public void givenRangedSizeListOfRandomBytesWhenGenerateThenReturnsSameBytes() {
		// Given
		final List<Byte> EXPECTED_RESULT = Arrays.asList((byte)123, (byte)12, (byte)1);

		given(mRandom.nextInt(5)).willReturn(2);
		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) {
				byte[] result = invocation.getArgument(0);
				result[0] = EXPECTED_RESULT.get(0);
				result[1] = EXPECTED_RESULT.get(1);
				result[2] = EXPECTED_RESULT.get(2);
				return null;
			}
		}).when(mRandom).nextBytes(any(byte[].class));

		mCut = new ByteListFieldDataProvider<>(mRandom, 1, 5);

		// When
		List<Byte> result = mCut.generate(null);

		// Then
		assertEquals(EXPECTED_RESULT, result);
	}
}