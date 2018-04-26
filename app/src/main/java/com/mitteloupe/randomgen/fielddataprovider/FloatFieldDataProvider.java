package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class FloatFieldDataProvider implements FieldDataProvider<Float> {
	private final Random mRandom;

	public FloatFieldDataProvider(Random pRandom) {
		mRandom = pRandom;
	}

	@Override
	public Float generate() {
		return mRandom.nextFloat();
	}
}
