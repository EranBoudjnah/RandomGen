package com.mitteloupe.randomgem.fielddataprovider;

import com.mitteloupe.randomgem.FieldDataProvider;

import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class IntegerFieldDataProvider implements FieldDataProvider<Integer> {
	private final Random mRandom;

	public IntegerFieldDataProvider(Random pRandom) {
		mRandom = pRandom;
	}

	@Override
	public Integer generate() {
		return mRandom.nextInt();
	}
}
