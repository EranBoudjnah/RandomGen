package com.mitteloupe.randomgem.fielddataprovider;

import com.mitteloupe.randomgem.FieldDataProvider;

import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class RgbFieldDataProvider implements FieldDataProvider<String> {
	private final Random mRandom;
	private final boolean mAlpha;

	public RgbFieldDataProvider(Random pRandom, boolean pAlpha) {
		mRandom = pRandom;
		mAlpha = pAlpha;
	}

	@Override
	public String generate() {
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
