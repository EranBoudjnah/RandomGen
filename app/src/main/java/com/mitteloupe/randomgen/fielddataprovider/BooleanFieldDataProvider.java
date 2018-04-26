package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class BooleanFieldDataProvider implements FieldDataProvider<Boolean> {
	private final Random mRandom;

	public BooleanFieldDataProvider(Random pRandom) {
		mRandom = pRandom;
	}

	@Override
	public Boolean generate() {
		return mRandom.nextBoolean();
	}
}
