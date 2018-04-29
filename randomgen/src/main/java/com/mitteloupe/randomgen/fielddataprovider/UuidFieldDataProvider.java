package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.UUID;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class UuidFieldDataProvider implements FieldDataProvider<String> {
	@Override
	public String generate() {
		return UUID.randomUUID().toString();
	}
}
