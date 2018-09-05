package com.mitteloupe.randomgenexample.data.planet;

import java.util.List;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class Planet {
	private final int mId;

	private final float mDiameterEarthRatio;
	private final float mSolarMass;

	private final float mOrbitalPeriodYears;
	private final float mRotationPeriodDays;

	private final int mMoons;
	private final boolean mHasRings;

	private final List<Material> mAtmosphere;

	public Planet(int pId, float pDiameterEarthRatio, float pSolarMass, float pOrbitalPeriodYears, float pRotationPeriodDays, int pMoons, boolean pHasRings, List<Material> pAtmosphere) {
		mId = pId;
		mDiameterEarthRatio = pDiameterEarthRatio;
		mSolarMass = pSolarMass;
		mOrbitalPeriodYears = pOrbitalPeriodYears;
		mRotationPeriodDays = pRotationPeriodDays;
		mMoons = pMoons;
		mHasRings = pHasRings;
		mAtmosphere = pAtmosphere;
	}

	public int getId() {
		return mId;
	}

	public float getDiameterEarthRatio() {
		return mDiameterEarthRatio;
	}

	public float getSolarMass() {
		return mSolarMass;
	}

	public float getOrbitalPeriodYears() {
		return mOrbitalPeriodYears;
	}

	public float getRotationPeriodDays() {
		return mRotationPeriodDays;
	}

	public int getMoons() {
		return mMoons;
	}

	public boolean hasRings() {
		return mHasRings;
	}

	public List<Material> getAtmosphere() {
		return mAtmosphere;
	}
}
