package com.mitteloupe.randomgem.fielddataprovider;

import com.mitteloupe.randomgem.FieldDataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Eran Boudjnah on 25/04/2018.
 */
public class CustomListRangeFieldDataProvider<VALUE_TYPE> implements FieldDataProvider<List<VALUE_TYPE>> {
	private final Random mRandom;
	private final int mMinInstances;
	private final int mMaxInstances;
	private final FieldDataProvider<VALUE_TYPE> mFieldDataProvider;

	public CustomListRangeFieldDataProvider(Random pRandom, int pMinInstances, int pMaxInstances, FieldDataProvider<VALUE_TYPE> pFieldDataProvider) {
		mRandom = pRandom;
		mMinInstances = pMinInstances;
		mMaxInstances = pMaxInstances;
		mFieldDataProvider = pFieldDataProvider;
	}

	@Override
	public List<VALUE_TYPE> generate() {
		int instances = mRandom.nextInt(mMaxInstances - mMinInstances + 1) + mMinInstances;
		List<VALUE_TYPE> ret = new ArrayList<>(instances);
		for (int i = 0; i < instances; ++i) {
			ret.add(mFieldDataProvider.generate());
		}
		return ret;
	}
}
