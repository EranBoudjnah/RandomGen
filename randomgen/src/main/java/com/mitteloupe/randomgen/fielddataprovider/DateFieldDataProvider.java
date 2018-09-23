package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

/**
 * A {@link FieldDataProvider} that generates a {@code Date} value.
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class DateFieldDataProvider<OUTPUT_TYPE> implements FieldDataProvider<OUTPUT_TYPE, Date> {
	private final Random mRandom;
	private final long mEarliestTimestamp;
	private final long mLatestTimestamp;

	/**
	 * Returns a new instance of {@link DateFieldDataProvider} generating a {@code Date} between {@code 0} and {@code Long.MAX_VALUE}.
	 *
	 * @param pRandom A random value generator
	 */
	public static <OUTPUT_TYPE> DateFieldDataProvider<OUTPUT_TYPE> getInstance(Random pRandom) {
		return new DateFieldDataProvider<>(pRandom);
	}

	/**
	 * Returns a new instance of {@link DateFieldDataProvider} generating a {@code Date} between {@code pEarliestTimestamp} and {@code pLatestTimestamp}.
	 *
	 * @param pRandom A random value generator
	 * @param pLatestTimestamp Milliseconds since January 1, 1970, 00:00:00 GMT.
	 */
	public static <OUTPUT_TYPE> DateFieldDataProvider<OUTPUT_TYPE> getInstanceWithLatestTimestamp(Random pRandom, long pLatestTimestamp) {
		return new DateFieldDataProvider<>(pRandom, pLatestTimestamp);
	}

	/**
	 * Returns a new instance of {@link DateFieldDataProvider} generating a {@code Date} between {@code pEarliestTimestamp} and {@code pLatestTimestamp}.
	 *
	 * @param pRandom A random value generator
	 * @param pEarliestTimestamp Milliseconds since January 1, 1970, 00:00:00 GMT.
	 * @param pLatestTimestamp Milliseconds since January 1, 1970, 00:00:00 GMT.
	 */
	public static <OUTPUT_TYPE> DateFieldDataProvider<OUTPUT_TYPE> getInstanceWithRange(Random pRandom, long pEarliestTimestamp, long pLatestTimestamp) {
		return new DateFieldDataProvider<>(pRandom, pEarliestTimestamp, pLatestTimestamp);
	}

	private DateFieldDataProvider(Random pRandom) {
		this(pRandom, 0, Long.MAX_VALUE);
	}

	private DateFieldDataProvider(Random pRandom, long pLatestTimestamp) {
		this(pRandom, 0, pLatestTimestamp);
	}

	private DateFieldDataProvider(Random pRandom, long pEarliestTimestamp, long pLatestTimestamp) {
		mRandom = pRandom;
		mEarliestTimestamp = pEarliestTimestamp;
		mLatestTimestamp = pLatestTimestamp;
	}

	@Override
	public Date generate(OUTPUT_TYPE instance) {
		BigDecimal min = BigDecimal.valueOf(mEarliestTimestamp);
		BigDecimal max = BigDecimal.valueOf(mLatestTimestamp);
		BigDecimal value = max
			.subtract(min)
			.add(BigDecimal.valueOf(1))
			.multiply(BigDecimal.valueOf(mRandom.nextDouble()))
			.add(min);

		return new Date(value.longValue());
	}
}
