package com.mitteloupe.randomgenexample.data.planet;

/**
 * Created by Eran Boudjnah on 25/04/2018.
 */
public class PlanetarySystem {
	private float mStarAgeBillionYears;
	private double mStarDiameterSunRadii;
	private double mStarSolarMass;
	private Planet[] mPlanets;

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
