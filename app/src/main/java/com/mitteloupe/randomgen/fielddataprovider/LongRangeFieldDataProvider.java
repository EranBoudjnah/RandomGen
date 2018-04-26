package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class LongRangeFieldDataProvider implements FieldDataProvider<Long> {
	private final Random mRandom;
	private final long mMinimum;
	private final long mMaximum;

	public LongRangeFieldDataProvider(Random pRandom, long pMinimum, long pMaximum) {
		mRandom = pRandom;
		mMinimum = pMinimum;
		mMaximum = pMaximum;
	}

	@Override
	public Long generate() {
		return (long)(mRandom.nextDouble() * (mMaximum - mMinimum + 1) + mMinimum);
	}
}
