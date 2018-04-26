package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class FloatRangeFieldDataProvider implements FieldDataProvider<Float> {
	private final Random mRandom;
	private final float mMinimum;
	private final float mMaximum;

	public FloatRangeFieldDataProvider(Random pRandom, float pMinimum, float pMaximum) {
		mRandom = pRandom;
		mMinimum = pMinimum;
		mMaximum = pMaximum;
	}

	@Override
	public Float generate() {
		return (float)(mRandom.nextDouble() * (mMaximum - mMinimum) + mMinimum);
	}
}
