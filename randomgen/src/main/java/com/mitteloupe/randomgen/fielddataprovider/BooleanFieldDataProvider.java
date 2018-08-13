package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.Random;

/**
 * A {@link FieldDataProvider} that generates a random {@code Boolean} value.
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class BooleanFieldDataProvider<OUTPUT_TYPE> implements FieldDataProvider<OUTPUT_TYPE, Boolean> {
	private final Random mRandom;

	public BooleanFieldDataProvider(Random pRandom) {
		mRandom = pRandom;
	}

	@Override
	public Boolean generate(OUTPUT_TYPE instance) {
		return mRandom.nextBoolean();
	}
}
