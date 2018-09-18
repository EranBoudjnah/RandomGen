package com.mitteloupe.randomgenexample.generator;

import android.support.v4.util.Pair;

import com.mitteloupe.randomgen.RandomGen;
import com.mitteloupe.randomgenexample.data.planet.Material;
import com.mitteloupe.randomgenexample.data.planet.Planet;
import com.mitteloupe.randomgenexample.data.planet.PlanetarySystem;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Eran Boudjnah on 28/08/2018.
 */
public class PlanetarySystemGeneratorFactory {
	public RandomGen<PlanetarySystem> getNewPlanetarySystemGenerator() {
		// Create a random star system with 2 to 4 planets
		return new RandomGen.Builder<PlanetarySystem>()
			.ofClass(PlanetarySystem.class)
			.withField("mStarAgeBillionYears")
			.returning(1f, 10f)
			.withField("mStarDiameterSunRadii")
			.returning(0.00001438d, 250d)
			.withField("mStarSolarMass")
			.returning(0.08d, 150d)
			.withField("mPlanets")
			.returning(0, 15, newPlanetGenerator())
			.build();
	}

	private RandomGen<Planet> newPlanetGenerator() {
		return new RandomGen.Builder<Planet>()
			.ofClass(Planet.class)
			.withField("mId")
			.returningSequentialInteger()
			.withField("mDiameterEarthRatio")
			.returning(0.3f, 12f)
			.withField("mSolarMass")
			.returning(0.05f, 350f)
			.withField("mOrbitalPeriodYears")
			.returning(0.2f, 200f)
			.withField("mRotationPeriodDays")
			.returning(0.3f, 250f)
			.withField("mMoons")
			.returning(0, 100)
			.withField("mHasRings")
			.returningBoolean()
			.withField("mAtmosphere")
			.returning(0, 3, newMaterialGenerator())
			.build();
	}

	private RandomGen<Material> newMaterialGenerator() {
		List<Pair<String, Integer>> materialAr = Collections.singletonList(new Pair<>("Ar", 1));
		List<Pair<String, Integer>> materialCH4 = Arrays.asList(new Pair<>("C", 1), new Pair<>("H", 4));
		List<Pair<String, Integer>> materialCO2 = Arrays.asList(new Pair<>("C", 1), new Pair<>("O", 2));
		List<Pair<String, Integer>> materialH2 = Collections.singletonList(new Pair<>("H", 2));
		List<Pair<String, Integer>> materialHe = Collections.singletonList(new Pair<>("He", 1));
		List<Pair<String, Integer>> materialN2 = Collections.singletonList(new Pair<>("N", 2));
		List<Pair<String, Integer>> materialO2 = Collections.singletonList(new Pair<>("O", 2));

		return new RandomGen.Builder<Material>()
				.ofClass(Material.class)
				.withField("mCompound")
				.returning(Arrays.asList(materialAr, materialCH4, materialCO2, materialH2, materialHe, materialN2, materialO2))
				.build();
	}
}
