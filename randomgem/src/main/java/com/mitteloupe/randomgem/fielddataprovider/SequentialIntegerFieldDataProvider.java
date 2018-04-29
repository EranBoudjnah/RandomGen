package com.mitteloupe.randomgem.fielddataprovider;

import com.mitteloupe.randomgem.FieldDataProvider;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class SequentialIntegerFieldDataProvider implements FieldDataProvider<Integer> {
	private int mCounter;

	public SequentialIntegerFieldDataProvider() {
		this(1);
	}

	@SuppressWarnings("WeakerAccess")
	public SequentialIntegerFieldDataProvider(int pStartValue) {
		mCounter = pStartValue;
	}

	@Override
	public Integer generate() {
		return mCounter++;
	}
}
