package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.Random;

/**
 * A {@link FieldDataProvider} that generates an RGB/RGBA {@code String} value.
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class RgbFieldDataProvider<OUTPUT_TYPE> implements FieldDataProvider<OUTPUT_TYPE, String> {
	private final Random mRandom;
	private final boolean mAlpha;

	/**
	 * Creates an instance of {@link RgbFieldDataProvider} generating a random RGB or RGBA {@code String} value.
	 *
	 * Values are in the format #RRGGBB or #AARRGGBB, respectively.
	 *
	 * @param pRandom A random value generator
	 * @param pAlpha  True to generate RGBA values, false to generage RGB values
	 */
	public RgbFieldDataProvider(Random pRandom, boolean pAlpha) {
		mRandom = pRandom;
		mAlpha = pAlpha;
	}

	@Override
	public String generate(OUTPUT_TYPE instance) {
		int red = mRandom.nextInt(255);
		int green = mRandom.nextInt(255);
		int blue = mRandom.nextInt(255);
		if (mAlpha) {
			int alpha = mRandom.nextInt(255);
			return String.format("#%02x%02x%02x%02x", alpha, red, green, blue);

		} else {
			return String.format("#%02x%02x%02x", red, green, blue);
		}
	}
}
