package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link FieldDataProvider} that generates a fixed sized list of instances of type {@link VALUE_TYPE}.
 *
 * Created by Eran Boudjnah on 25/04/2018.
 */
public class CustomListFieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> implements FieldDataProvider<OUTPUT_TYPE, List<VALUE_TYPE>> {
	private final int mInstances;
	private final FieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> mFieldDataProvider;

	public CustomListFieldDataProvider(int pInstances, FieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> pFieldDataProvider) {
		mInstances = pInstances;
		mFieldDataProvider = pFieldDataProvider;
	}

	@Override
	public List<VALUE_TYPE> generate(OUTPUT_TYPE instance) {
		List<VALUE_TYPE> ret = new ArrayList<>(mInstances);
		for (int i = 0; i < mInstances; ++i) {
			ret.add(mFieldDataProvider.generate(instance));
		}
		return ret;
	}
}
