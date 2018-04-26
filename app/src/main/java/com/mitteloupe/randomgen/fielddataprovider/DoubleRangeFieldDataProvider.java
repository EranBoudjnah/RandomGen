package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class DoubleRangeFieldDataProvider implements FieldDataProvider<Double> {
	private final Random mRandom;
	private final double mMinimum;
	private final double mMaximum;

	public DoubleRangeFieldDataProvider(Random pRandom, double pMinimum, double pMaximum) {
		mRandom = pRandom;
		mMinimum = pMinimum;
		mMaximum = pMaximum;
	}

	@Override
	public Double generate() {
		return mRandom.nextDouble() * (mMaximum - mMinimum) + mMinimum;
	}
}
