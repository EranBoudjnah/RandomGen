package com.mitteloupe.randomgem.fielddataprovider;

import com.mitteloupe.randomgem.FieldDataProvider;

import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class ByteFieldDataProvider implements FieldDataProvider<Byte> {
	private final Random mRandom;
	private final byte[] mByte = new byte[1];

	public ByteFieldDataProvider(Random pRandom) {
		mRandom = pRandom;
	}

	@Override
	public Byte generate() {
		mRandom.nextBytes(mByte);
		return mByte[0];
	}
}
