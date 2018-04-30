package com.mitteloupe.randomgen.datasource;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Eran Boudjnah on 29/04/2018.
 */
@SuppressWarnings("unused")
public class ColorsDataSource {
	private static ColorsDataSource sDataSource = new ColorsDataSource();

	public static ColorsDataSource getInstance() {
		return sDataSource;
	}

	private ColorsDataSource() {
	}

	public List<String> getColors() {
		return Arrays.asList("Black", "Blue", "Brown", "Cyan", "Green", "Grey", "Magenta", "Orange", "Pink", "Red", "Violet", "White", "Yellow");
	}
}
