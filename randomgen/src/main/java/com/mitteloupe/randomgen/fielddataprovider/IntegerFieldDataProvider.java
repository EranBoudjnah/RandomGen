package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.math.BigDecimal;
import java.util.Random;

/**
 * A {@link FieldDataProvider} that generates an {@code Integer} value.
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class IntegerFieldDataProvider<OUTPUT_TYPE> implements FieldDataProvider<OUTPUT_TYPE, Integer> {
	private final Random mRandom;
	private final int mMaximum;
	private final int mMinimum;

	/**
	 * Creates an instance of {@link IntegerFieldDataProvider} generating an {@code Integer}
	 * between {@code Integer.MIN_VALUE} and {@code Integer.MAX_VALUE}.
	 *
	 * @param pRandom A random value generator
	 */
	public IntegerFieldDataProvider(Random pRandom) {
		this(pRandom, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * Creates an instance of {@link IntegerFieldDataProvider} generating an {@code Integer} between {@code pMinimum} and {@code pMaximum}.
	 *
	 * @param pRandom A random value generator
	 * @param pMinimum The lowest possible value
	 * @param pMaximum The highest possible value
	 */
	public IntegerFieldDataProvider(Random pRandom, int pMinimum, int pMaximum) {
		mRandom = pRandom;
		mMinimum = pMinimum;
		mMaximum = pMaximum;
	}

	@Override
	public Integer generate(OUTPUT_TYPE instance) {
		BigDecimal min = BigDecimal.valueOf(mMinimum);
		BigDecimal max = BigDecimal.valueOf(mMaximum);
		BigDecimal value = max
			.subtract(min)
			.add(BigDecimal.valueOf(1))
			.multiply(BigDecimal.valueOf(mRandom.nextDouble()))
			.add(min);
		return value.intValue();
	}
}
