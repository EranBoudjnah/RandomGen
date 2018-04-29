package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
@SuppressWarnings("unused")
public final class ByteListFieldDataProvider implements FieldDataProvider<List<Byte>> {
	private final Random mRandom;
	private final byte[] mBytes;
	private final int mMinSize;
	private final int mMaxSize;

	public ByteListFieldDataProvider(Random pRandom, int pSize) {
		this(pRandom, pSize, pSize);
	}

	@SuppressWarnings("WeakerAccess")
	public ByteListFieldDataProvider(Random pRandom, int pMinSize, int pMaxSize) {
		mRandom = pRandom;
		mMinSize = pMinSize;
		mMaxSize = pMaxSize;
		mBytes = new byte[pMaxSize];
	}

	@Override
	public List<Byte> generate() {
		mRandom.nextBytes(mBytes);
		int items = mRandom.nextInt(mMaxSize - mMinSize + 1) + mMinSize;
		List<Byte> bytes = new ArrayList<>(items);
		for (int i = 0; i < items; ++i) {
			bytes.add(mBytes[i]);
		}
		return bytes;
	}
}