package com.mitteloupe.randomgen.fielddataprovider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * Created by Eran Boudjnah on 10/08/2018.
 */
public class ByteFieldDataProviderTest {
	private ByteFieldDataProvider mCut;

	@Mock
	private Random mRandom;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mCut = new ByteFieldDataProvider(mRandom);
	}

	@Test
	public void givenRandomByteValueWhenGenerateThenReturnsSameByte() {
		// Given
		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) {
				((byte[])invocation.getArgument(0))[0] = 113;
				return null;
			}
		}).when(mRandom).nextBytes(any(byte[].class));

		// When
		byte result = mCut.generate();

		// Then
		assertEquals(113, result);
	}
}