package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

/**
 * A {@link FieldDataProvider} that generates sequential {@code Integer} values.
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class SequentialIntegerFieldDataProvider<OUTPUT_TYPE> implements FieldDataProvider<OUTPUT_TYPE, Integer> {
	private int mCounter;

	/**
	 * Returns a new instance of {@link SequentialIntegerFieldDataProvider} generating sequential {@code Integer} values, starting at 1,
	 * inclusive.
	 */
	public static <OUTPUT_TYPE> SequentialIntegerFieldDataProvider<OUTPUT_TYPE> getInstance() {
		return new SequentialIntegerFieldDataProvider<>();
	}

	/**
	 * Returns a new instance of {@link SequentialIntegerFieldDataProvider} generating sequential {@code Integer} values, starting at {@code pStartValue},
	 * inclusive.
	 *
	 * @param pStartValue The first value to generate
	 */
	public static <OUTPUT_TYPE> SequentialIntegerFieldDataProvider<OUTPUT_TYPE> getInstanceWithStartValue(int pStartValue) {
		return new SequentialIntegerFieldDataProvider<>(pStartValue);
	}

	private SequentialIntegerFieldDataProvider() {
		this(1);
	}

	private SequentialIntegerFieldDataProvider(int pStartValue) {
		mCounter = pStartValue;
	}

	@Override
	public Integer generate(OUTPUT_TYPE instance) {
		return mCounter++;
	}
}
