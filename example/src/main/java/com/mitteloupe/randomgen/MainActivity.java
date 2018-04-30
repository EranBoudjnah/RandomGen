package com.mitteloupe.randomgen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mitteloupe.randomgen.data.Planet;
import com.mitteloupe.randomgen.data.StarSystem;
import com.mitteloupe.randomgen.datasource.AstronomyDataSource;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
			.returning(Planet.PlanetClass.SMALL)
			.build();

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

		// Pools of assets - countries, musical notes?, animals, gender, currencies, shapes, sizes, distance units, weight units
		// From file
		// Coordinate
		// Char (random, fixed)
		// Sentences
		// returning().or().returning()
		// timestamp
		// bitmaps
		// date
		// standard date string
		// numeric string (ranged/fixed length)
		// hex string (ranged/fixed length)
		// determine if list or array
		// Android:
		//   From resource?
		//   Point

		for (int i = 0; i < 1000; ++i) {
			StarSystem starSystem = starSystemRandomGen.generate();
			Log.d("TAG", starSystem.toString() +
				"\n--");
		}
	}
}
