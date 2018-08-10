package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;
import com.mitteloupe.randomgen.UuidGenerator;

/**
 * A {@link FieldDataProvider} that generates random {@code UUID} {@code String} values.
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class UuidFieldDataProvider implements FieldDataProvider<String> {
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
	public String generate() {
		return mUuidGenerator.randomUUID();
	}
}
