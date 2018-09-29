package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A {@link FieldDataProvider} that routes to one of its {@link FieldDataProvider}s randomly with a weighted bias.
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class WeightedFieldDataProvidersFieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> implements FieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> {
	private final Random mRandom;
	private final List<WeightedFieldDataProvider> mWeightedFieldDataProviders;

	/**
	 * Creates an instance of {@link WeightedFieldDataProvidersFieldDataProvider}.
	 *
	 * @param pFieldDataProvider An initial field data provider
	 */
	public WeightedFieldDataProvidersFieldDataProvider(Random pRandom, FieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> pFieldDataProvider) {
		mRandom = pRandom;
		mWeightedFieldDataProviders = new ArrayList<>(1);

		addFieldDataProvider(pFieldDataProvider, 1d);
	}

	public void addFieldDataProvider(FieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> pFieldDataProvider, double pWeight) {
		WeightedFieldDataProvider weightedFieldDataProvider = new WeightedFieldDataProvider();
		weightedFieldDataProvider.mFieldDataProvider = pFieldDataProvider;
		double lastFieldDataProviderWeight = getLastFieldDataProviderWeight();

		double newFieldDataProviderWeight = lastFieldDataProviderWeight != 0 ?
			lastFieldDataProviderWeight * pWeight :
			pWeight;

		weightedFieldDataProvider.mWeight = newFieldDataProviderWeight;
		weightedFieldDataProvider.mSummedWeight = lastFieldDataProviderWeight + newFieldDataProviderWeight;
		mWeightedFieldDataProviders.add(weightedFieldDataProvider);
	}

	private double getLastFieldDataProviderWeight() {
		if (mWeightedFieldDataProviders.isEmpty()) {
			return 0d;
		}

		WeightedFieldDataProvider lastFieldDataProvider = mWeightedFieldDataProviders.get(mWeightedFieldDataProviders.size() - 1);
		return lastFieldDataProvider.mWeight;
	}

	@Override
	public VALUE_TYPE generate(OUTPUT_TYPE instance) {
		return generateRandomValue(instance);
	}

	private VALUE_TYPE generateRandomValue(OUTPUT_TYPE instance) {
		double randomWeightInRange = mRandom.nextDouble() * getLastFieldDataProviderWeight();
		int position = getFieldDataProviderPositionByWeight(randomWeightInRange);

		return mWeightedFieldDataProviders
			.get(position)
			.mFieldDataProvider
			.generate(instance);
	}

	private int getFieldDataProviderPositionByWeight(double pWeight) {
		int lowIndex = 0;
		int highIndex = mWeightedFieldDataProviders.size() - 1;

		while (highIndex >= lowIndex) {
			int currentIndex = (lowIndex + highIndex) / 2;

			WeightedFieldDataProvider weightedFieldDataProvider = mWeightedFieldDataProviders.get(currentIndex);
			double weightSumAtGuess = weightedFieldDataProvider.mSummedWeight;
			double weightAtGuess = weightedFieldDataProvider.mWeight;

			if (weightSumAtGuess < pWeight) {
				lowIndex = currentIndex + 1;
			} else if (weightSumAtGuess - weightAtGuess > pWeight) {
				highIndex = currentIndex - 1;
			} else {
				return currentIndex;
			}
		}

		return 0;
	}

	private class WeightedFieldDataProvider {
		FieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> mFieldDataProvider;
		double mWeight;
		double mSummedWeight;
	}
}
