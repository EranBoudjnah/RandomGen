package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.math.BigDecimal;
import java.util.Random;

/**
 * A {@link FieldDataProvider} that generates a {@code Long} value.
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class LongFieldDataProvider<OUTPUT_TYPE> implements FieldDataProvider<OUTPUT_TYPE, Long> {
	private final Random mRandom;
	private final long mMinimum;
	private final long mMaximum;

	/**
	 * Returns a new instance of {@link LongFieldDataProvider} generating a {@code Long} between {@code Long.MIN_VALUE} and {@code Long.MAX_VALUE}.
	 *
	 * @param pRandom A random value generator
	 */
	public static <OUTPUT_TYPE> LongFieldDataProvider<OUTPUT_TYPE> getInstance(Random pRandom) {
		return new LongFieldDataProvider<>(pRandom);
	}

	/**
	 * Returns a new instance of {@link LongFieldDataProvider} generating a {@code Long} between {@code pMinimum} and {@code pMaximum}.
	 *
	 * @param pRandom A random value generator
	 * @param pMinimum The lowest possible value
	 * @param pMaximum The highest possible value
	 */
	public static <OUTPUT_TYPE> LongFieldDataProvider<OUTPUT_TYPE> getInstanceWithRange(Random pRandom, long pMinimum, long pMaximum) {
		return new LongFieldDataProvider<>(pRandom, pMinimum, pMaximum);
	}

	private LongFieldDataProvider(Random pRandom) {
		this(pRandom, Long.MIN_VALUE, Long.MAX_VALUE);
	}

	private LongFieldDataProvider(Random pRandom, long pMinimum, long pMaximum) {
		mRandom = pRandom;
		mMinimum = pMinimum;
		mMaximum = pMaximum;
	}

	@Override
	public Long generate(OUTPUT_TYPE instance) {
		BigDecimal min = BigDecimal.valueOf(mMinimum);
		BigDecimal max = BigDecimal.valueOf(mMaximum);
		BigDecimal value = max
			.subtract(min)
			.add(BigDecimal.valueOf(1))
			.multiply(BigDecimal.valueOf(mRandom.nextDouble()))
			.add(min);

		return value.longValue();
	}
}
