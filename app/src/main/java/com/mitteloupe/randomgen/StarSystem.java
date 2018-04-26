package com.mitteloupe.randomgen;

import java.util.List;

/**
 * Created by Eran Boudjnah on 25/04/2018.
 */
class StarSystem {
	private String name;
	private List<Planet> stars;

	@Override
	public String toString() {
		return name + "\nSTARS:\n" + stars.toString();
	}
}
