package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.Random;

/**
 * A {@link FieldDataProvider} that generates a single Byte value.
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class ByteFieldDataProvider<OUTPUT_TYPE> implements FieldDataProvider<OUTPUT_TYPE, Byte> {
	private final Random mRandom;
	private final byte[] mByte = new byte[1];

	public ByteFieldDataProvider(Random pRandom) {
		mRandom = pRandom;
	}

	@Override
	public Byte generate(OUTPUT_TYPE instance) {
		mRandom.nextBytes(mByte);
		return mByte[0];
	}
}
