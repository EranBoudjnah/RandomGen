package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class DoubleFieldDataProvider implements FieldDataProvider<Double> {
	private final Random mRandom;

	public DoubleFieldDataProvider(Random pRandom) {
		mRandom = pRandom;
	}

	@Override
	public Double generate() {
		return mRandom.nextDouble();
	}
}
