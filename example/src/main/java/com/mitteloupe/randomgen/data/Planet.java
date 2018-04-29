package com.mitteloupe.randomgen.data;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public class Planet {
	private int id;
	private String uuid;
	private String name;
	private long diameter;
	private PlanetClass planetClass;

	public Planet() {
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public long getDiameter() {
		return diameter;
	}

	public PlanetClass getPlanetClass() {
		return planetClass;
	}

	@Override
	public String toString() {
		return id + " [" + uuid + "]: " + name + "\nDiameter: " + diameter + "\nClass: " + planetClass;
	}

	public enum PlanetClass {
		LARGE,
		MEDIUM,
		SMALL
	}
}
