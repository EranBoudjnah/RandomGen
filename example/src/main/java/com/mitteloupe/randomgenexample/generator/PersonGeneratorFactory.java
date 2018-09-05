package com.mitteloupe.randomgenexample.generator;

import android.support.annotation.NonNull;

import com.mitteloupe.randomgen.FieldDataProvider;
import com.mitteloupe.randomgen.RandomGen;
import com.mitteloupe.randomgen.fielddataprovider.ConcatenateFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.ExplicitFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.GenericListFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.IntegerFieldDataProvider;
import com.mitteloupe.randomgen.fielddataprovider.PaddedFieldDataProvider;
import com.mitteloupe.randomgenexample.data.person.Gender;
import com.mitteloupe.randomgenexample.data.person.Occupation;
import com.mitteloupe.randomgenexample.data.person.Person;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Eran Boudjnah on 28/08/2018.
 */
public class PersonGeneratorFactory {
	public RandomGen<Person> getNewPersonGenerator() {
		Random random = new Random();

		return new RandomGen.Builder<>(new RandomGen.InstanceProvider<Person>() {
			@Override
			public Person provideInstance() {
				return new Person(null, null, 0, null, null);
			}
		})
			.withField("mGender")
			.returning(Gender.class)
			.withField("mName")
			.returning(newFullNameFieldDataProvider(random))
			.withField("mAge")
			.returning(18, 70)
			.withField("mOccupation")
			.returning(Occupation.class)
			.withField("mPhoneNumber")
			.returning(newPhoneFieldDataProvider(random))
			.build();
	}

	@NonNull
	private FieldDataProvider<Person, String> newFullNameFieldDataProvider(final Random pRandom) {
		final FieldDataProvider<Person, String> maleNameFieldDataProvider =
			new GenericListFieldDataProvider<>(pRandom, Arrays.asList("Dave", "George", "Jack", "James", "John", "Oliver", "Russel", "Steve"));
		final FieldDataProvider<Person, String> femaleNameFieldDataProvider =
			new GenericListFieldDataProvider<>(pRandom, Arrays.asList("Amanda", "Chloe", "Clair", "Daisy", "Jane", "Jessica", "Maya", "Sarah"));
		final FieldDataProvider<Person, String> lastNameFieldDataProvider =
			new GenericListFieldDataProvider<>(pRandom, Arrays.asList("Brown", "Davis", "Johnson", "Jones", "Miller", "Smith", "Williams", "Wilson"));
		final FieldDataProvider<Person, String> maleConcatenateFieldDataProvider =
			ConcatenateFieldDataProvider.getInstanceWithDelimiterAndProviders(" ", maleNameFieldDataProvider, lastNameFieldDataProvider);
		final FieldDataProvider<Person, String> femaleConcatenateFieldDataProvider =
			ConcatenateFieldDataProvider.getInstanceWithDelimiterAndProviders(" ", femaleNameFieldDataProvider, lastNameFieldDataProvider);

		return new FieldDataProvider<Person, String>() {
			@Override
			public String generate(Person pPerson) {
				if (pPerson.getGender() == Gender.MALE) {
					return maleConcatenateFieldDataProvider.generate(pPerson);

				} else {
					return femaleConcatenateFieldDataProvider.generate(pPerson);
				}
			}
		};
	}

	@NonNull
	private FieldDataProvider<Person, String> newPhoneFieldDataProvider(Random pRandom) {

		final ExplicitFieldDataProvider<Person, String> plusProvider =
			new ExplicitFieldDataProvider<>("+");
		final FieldDataProvider<Person, Integer> phoneCountryFieldDataProvider =
			IntegerFieldDataProvider.getInstanceWithRange(pRandom, 1, 99);
		final ExplicitFieldDataProvider<Person, String> spaceProvider =
			new ExplicitFieldDataProvider<>(" ");
		final PaddedFieldDataProvider<Person> number3DigitsProvider =
			getFieldDataProviderWith3Digits(pRandom, 3, 999);
		final PaddedFieldDataProvider<Person> number5DigitsProvider =
			getFieldDataProviderWith3Digits(pRandom, 5, 99999);

		return ConcatenateFieldDataProvider.getInstanceWithProviders(
			plusProvider, phoneCountryFieldDataProvider,
			spaceProvider, number3DigitsProvider,
			spaceProvider, number3DigitsProvider,
			spaceProvider, number5DigitsProvider
		);
	}

	@NonNull
	private PaddedFieldDataProvider<Person> getFieldDataProviderWith3Digits(
		Random pRandom,
		int pLength,
		int pMaximumValue
	) {
		return new PaddedFieldDataProvider<>(pLength, "0",
			IntegerFieldDataProvider.<Person>getInstanceWithRange(pRandom, 0, pMaximumValue));
	}
}
