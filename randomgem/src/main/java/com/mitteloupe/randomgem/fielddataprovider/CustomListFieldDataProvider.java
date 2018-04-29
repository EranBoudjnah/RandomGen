package com.mitteloupe.randomgem.fielddataprovider;

import com.mitteloupe.randomgem.FieldDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates a fixed number of instances of type {@link VALUE_TYPE} to populate a list or an array field with.
 *
 * Created by Eran Boudjnah on 25/04/2018.
 */
public class CustomListFieldDataProvider<VALUE_TYPE> implements FieldDataProvider<List<VALUE_TYPE>> {
	private final int mInstances;
	private final FieldDataProvider<VALUE_TYPE> mFieldDataProvider;

	public CustomListFieldDataProvider(int pInstances, FieldDataProvider<VALUE_TYPE> pFieldDataProvider) {
		mInstances = pInstances;
		mFieldDataProvider = pFieldDataProvider;
	}

	@Override
	public List<VALUE_TYPE> generate() {
		List<VALUE_TYPE> ret = new ArrayList<>(mInstances);
		for (int i = 0; i < mInstances; ++i) {
			ret.add(mFieldDataProvider.generate());
		}
		return ret;
	}
}
