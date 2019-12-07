package com.mitteloupe.randomgenexample;

import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ViewFlipper;

import com.mitteloupe.randomgen.RandomGen;
import com.mitteloupe.randomgenexample.data.flat.Flat;
import com.mitteloupe.randomgenexample.data.person.Person;
import com.mitteloupe.randomgenexample.data.planet.PlanetarySystem;
import com.mitteloupe.randomgenexample.generator.FlatGeneratorFactory;
import com.mitteloupe.randomgenexample.generator.PersonGeneratorFactory;
import com.mitteloupe.randomgenexample.generator.PlanetarySystemGeneratorFactory;
import com.mitteloupe.randomgenexample.widget.FlatView;
import com.mitteloupe.randomgenexample.widget.PersonView;
import com.mitteloupe.randomgenexample.widget.PlanetarySystemView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
	private Handler mHandler = new Handler();

	private RandomGen<Person> mPersonRandomGen;
	private PersonView mPersonView;

	private RandomGen<PlanetarySystem> mPlanetarySystemRandomGen;
	private PlanetarySystemView mPlanetarySystemView;

	private RandomGen<Flat> mFlatRandomGen;
	private FlatView mFlatView;

	private ViewFlipper mViewFlipper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mViewFlipper = findViewById(R.id.content_container);
		mPersonView = findViewById(R.id.person_view);
		mPlanetarySystemView = findViewById(R.id.planetary_system_view);
		mFlatView = findViewById(R.id.flat_view);

		mHandler.post(new Runnable() {
			@Override
			public void run() {
				initGenerators();
			}
		});
	}

	private void initGenerators() {
		FlatGeneratorFactory flatGeneratorFactory = new FlatGeneratorFactory(new Random());
		mFlatRandomGen = flatGeneratorFactory.getNewFlatGenerator();
		PersonGeneratorFactory personGeneratorFactory = new PersonGeneratorFactory();
		mPersonRandomGen = personGeneratorFactory.getNewPersonGenerator();
		PlanetarySystemGeneratorFactory planetarySystemGeneratorFactory = new PlanetarySystemGeneratorFactory();
		mPlanetarySystemRandomGen = planetarySystemGeneratorFactory.getNewPlanetarySystemGenerator();
	}

	public void onPersonClick(View view) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				generatePerson();
			}
		});
	}

	public void onPlanetarySystemClick(View view) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				generatePlanetarySystem();
			}
		});
	}

	public void onFlatClick(View view) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				generateFlat();
			}
		});
	}

	private void generatePerson() {
		Person person = mPersonRandomGen.generate();
		mViewFlipper.setDisplayedChild(1);
		mPersonView.setPerson(person);
	}

	private void generatePlanetarySystem() {
		PlanetarySystem planetarySystem = mPlanetarySystemRandomGen.generate();
		mViewFlipper.setDisplayedChild(2);
		mPlanetarySystemView.setPlanetarySystem(planetarySystem);
	}

	private void generateFlat() {
		Flat flat = mFlatRandomGen.generate();
		mViewFlipper.setDisplayedChild(3);
		mFlatView.setFlat(flat);
	}
}
