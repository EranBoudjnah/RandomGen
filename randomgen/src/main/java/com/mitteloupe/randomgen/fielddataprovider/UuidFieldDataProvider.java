package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;
import com.mitteloupe.randomgen.UuidGenerator;

/**
 * A {@link FieldDataProvider} that generates random {@code UUID} {@code String} values.
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class UuidFieldDataProvider<OUTPUT_TYPE> implements FieldDataProvider<OUTPUT_TYPE, String> {
	private final UuidGenerator mUuidGenerator;

	/**
	 * Creates an instance of {@link UuidFieldDataProvider} generating random {@code UUID} {@code String} values.
	 *
	 * @param pUuidGenerator A generator of UUIDs
	 */
	public UuidFieldDataProvider(UuidGenerator pUuidGenerator) {
		mUuidGenerator = pUuidGenerator;
	}

	@Override
	public String generate(OUTPUT_TYPE instance) {
		return mUuidGenerator.randomUUID();
	}
}
