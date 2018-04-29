package com.mitteloupe.randomgem.fielddataprovider;

import com.mitteloupe.randomgem.FieldDataProvider;

import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class IntegerRangeFieldDataProvider implements FieldDataProvider<Integer> {
	private final Random mRandom;
	private final int mMaximum;
	private final int mMinimum;

	public IntegerRangeFieldDataProvider(Random pRandom, int pMinimum, int pMaximum) {
		mRandom = pRandom;
		mMinimum = pMinimum;
		mMaximum = pMaximum;
	}

	@Override
	public Integer generate() {
		return (int)(mRandom.nextDouble() * (mMaximum - mMinimum + 1) + mMinimum);
	}
}
