package com.mitteloupe.randomgenexample.data;

import java.util.Arrays;

/**
 * Created by Eran Boudjnah on 25/04/2018.
 */
public class StarSystem {
	private String name;
	private Planet[] stars;

	@Override
	public String toString() {
		String starsString = (stars != null) ? Arrays.asList(stars).toString() : "[NONE]";
		return name + "\nSTARS:\n" + starsString;
	}
}
