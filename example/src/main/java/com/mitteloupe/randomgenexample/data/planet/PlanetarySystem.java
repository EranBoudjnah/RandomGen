package com.mitteloupe.randomgenexample.data.planet;

/**
 * Created by Eran Boudjnah on 25/04/2018.
 */
public class PlanetarySystem {
	private final float mStarAgeBillionYears;
	private final double mStarDiameterSunRadii;
	private final double mStarSolarMass;
	private final Planet[] mPlanets;

	public PlanetarySystem(float pStarAgeBillionYears, double pStarDiameterSunRadii, double pStarSolarMass, Planet[] pPlanets) {
		mStarAgeBillionYears = pStarAgeBillionYears;
		mStarDiameterSunRadii = pStarDiameterSunRadii;
		mStarSolarMass = pStarSolarMass;
		mPlanets = pPlanets;
	}

	public float getStarAgeBillionYears() {
		return mStarAgeBillionYears;
	}

	public double getStarDiameterSunRadii() {
		return mStarDiameterSunRadii;
	}

	public double getStarSolarMass() {
		return mStarSolarMass;
	}

	public Planet[] getPlanets() {
		return mPlanets;
	}
}
