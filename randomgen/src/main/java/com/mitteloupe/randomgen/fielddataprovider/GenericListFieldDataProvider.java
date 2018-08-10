package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class GenericListFieldDataProvider<VALUE_TYPE> implements FieldDataProvider<VALUE_TYPE> {
	private final Random mRandom;
	private final List<VALUE_TYPE> mList;

	public GenericListFieldDataProvider(Random pRandom, List<VALUE_TYPE> pList) {
		mRandom = pRandom;
		mList = new ArrayList<>(pList);
	}

	@Override
	public VALUE_TYPE generate() {
		return mList.get((int)(mRandom.nextDouble() * mList.size()));
	}
}
