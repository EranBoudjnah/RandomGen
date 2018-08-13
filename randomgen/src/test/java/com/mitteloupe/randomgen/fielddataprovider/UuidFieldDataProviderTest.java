package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.UuidGenerator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Created by Eran Boudjnah on 11/08/2018.
 */
public class UuidFieldDataProviderTest {
	private UuidFieldDataProvider<?> mCut;

	@Mock
	private UuidGenerator mUuidGenerator;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mCut = new UuidFieldDataProvider<>(mUuidGenerator);
	}

	@Test
	public void givenAUuidWhenGenerateThenReturnsGeneratedValue() {
		// Given
		final String EXPECTED_VALUE = "c9569006-9d99-11e8-98d0-529269fb1459";
		given(mUuidGenerator.randomUUID()).willReturn(EXPECTED_VALUE);

		// When
		String result = mCut.generate(null);

		// Then
		assertEquals(EXPECTED_VALUE, result);
	}
}