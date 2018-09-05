package com.mitteloupe.randomgenexample.data.planet;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Eran Boudjnah on 19/08/2018.
 */
public class Material {
	private final List<Pair<String, Integer>> mCompound = new ArrayList<>();

	@SafeVarargs
	public Material(Pair<String, Integer>... pCompound) {
		mCompound.addAll(Arrays.asList(pCompound));
	}

	public List<Pair<String, Integer>> getCompound() {
		return mCompound;
	}
}
