package com.mitteloupe.randomgenexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mitteloupe.randomgen.RandomGen;
import com.mitteloupe.randomgen.datasource.AstronomyDataSource;
import com.mitteloupe.randomgenexample.data.Planet;
import com.mitteloupe.randomgenexample.data.StarSystem;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create a random planet generator
		RandomGen<Planet> planetRandomGen = new RandomGen.Builder<>(new RandomGen.InstanceProvider<Planet>() {
			@Override
			public Planet provideInstance() {
				return new Planet();
			}
		})
			.withField("id")
			.returningSequentialInteger()
			.withField("uuid")
			.returningUuid()
			.withField("name")
			.returning(AstronomyDataSource.getInstance().getStars())
			.withField("diameter")
			.returning(400L, 500L)
			.withField("planetClass")
			.returning(Planet.PlanetClass.class)
			.build();

		// Create a random star system with 2 to 4 planets
		RandomGen<StarSystem> starSystemRandomGen = new RandomGen.Builder<>(new RandomGen.InstanceProvider<StarSystem>() {
			@Override
			public StarSystem provideInstance() {
				return new StarSystem();
			}
		})
			.withField("name")
			.returningExplicitly("System A")
			.withField("stars")
			.returning(2, 4, planetRandomGen)
			.build();

		// Generate 1,000 star systems
		for (int i = 0; i < 1000; ++i) {
			StarSystem starSystem = starSystemRandomGen.generate();
			Log.d("RandomGen", starSystem.toString() +
				"\n--");
		}
	}
}
