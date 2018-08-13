package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A {@link FieldDataProvider} that generates a Byte list.
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class ByteListFieldDataProvider<OUTPUT_TYPE> implements FieldDataProvider<OUTPUT_TYPE, List<Byte>> {
	private final Random mRandom;
	private final byte[] mBytes;
	private final int mMinSize;
	private final int mMaxSize;

	/**
	 * Creates an instance of {@link ByteListFieldDataProvider} generating precisely {@code pSize} Bytes.
	 *
	 * @param pRandom A random value generator
	 * @param pSize   The number of Bytes to generate
	 */
	public ByteListFieldDataProvider(Random pRandom, int pSize) {
		this(pRandom, pSize, pSize);
	}

	/**
	 * Creates an instance of {@link ByteListFieldDataProvider} generating between {@code pMinSize} and {@code pMaxSize} Bytes (inclusive).
	 *
	 * @param pRandom  A random value generator
	 * @param pMinSize The minimal number of Bytes to generate
	 * @param pMaxSize The maximal number of Bytes to generate
	 */
	public ByteListFieldDataProvider(Random pRandom, int pMinSize, int pMaxSize) {
		mRandom = pRandom;
		mMinSize = pMinSize;
		mMaxSize = pMaxSize;
		mBytes = new byte[pMaxSize];
	}

	@Override
	public List<Byte> generate(OUTPUT_TYPE instance) {
		mRandom.nextBytes(mBytes);
		int items = mRandom.nextInt(mMaxSize - mMinSize + 1) + mMinSize;
		List<Byte> bytes = new ArrayList<>(items);
		for (int i = 0; i < items; ++i) {
			bytes.add(mBytes[i]);
		}
		return bytes;
	}
}