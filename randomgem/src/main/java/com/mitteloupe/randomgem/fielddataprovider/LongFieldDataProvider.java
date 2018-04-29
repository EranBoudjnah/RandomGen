package com.mitteloupe.randomgem.fielddataprovider;

import com.mitteloupe.randomgem.FieldDataProvider;

import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class LongFieldDataProvider implements FieldDataProvider<Long> {
	private final Random mRandom;

	public LongFieldDataProvider(Random pRandom) {
		mRandom = pRandom;
	}

	@Override
	public Long generate() {
		return mRandom.nextLong();
	}
}