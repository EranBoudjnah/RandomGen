package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.Random;

/**
 * A {@link FieldDataProvider} that generates a {@code Double} value.
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class DoubleFieldDataProvider<OUTPUT_TYPE> implements FieldDataProvider<OUTPUT_TYPE, Double> {
	private final Random mRandom;
	private final double mMinimum;
	private final double mMaximum;

	/**
	 * Returns a new instance of {@link DoubleFieldDataProvider} generating a {@code Double} between {@code 0.0} and {@code 1.0}.
	 *
	 * @param pRandom A random value generator
	 */
	public static <OUTPUT_TYPE> DoubleFieldDataProvider<OUTPUT_TYPE> getInstance(Random pRandom) {
		return new DoubleFieldDataProvider<>(pRandom);
	}

	/**
	 * Returns a new instance of {@link DoubleFieldDataProvider} generating a {@code Double} between {@code pMinimum} and {@code pMaximum}.
	 *
	 * @param pRandom A random value generator
	 * @param pMinimum The lowest possible value
	 * @param pMaximum The highest possible value
	 */
	public static <OUTPUT_TYPE> DoubleFieldDataProvider<OUTPUT_TYPE> getInstanceWithRange(Random pRandom, double pMinimum, double pMaximum) {
		return new DoubleFieldDataProvider<>(pRandom, pMinimum, pMaximum);
	}

	private DoubleFieldDataProvider(Random pRandom) {
		this(pRandom, 0d, 1d);
	}

	private DoubleFieldDataProvider(Random pRandom, double pMinimum, double pMaximum) {
		mRandom = pRandom;
		mMinimum = pMinimum;
		mMaximum = pMaximum;
	}

	@Override
	public Double generate(OUTPUT_TYPE instance) {
		return mRandom.nextDouble() * (mMaximum - mMinimum) + mMinimum;
	}
}
