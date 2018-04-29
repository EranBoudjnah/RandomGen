package com.mitteloupe.randomgen;

/**
 * Generates one value of the defined type.
 *
 * @param <FIELD_DATA_TYPE>
 */
public interface FieldDataProvider<FIELD_DATA_TYPE> {
	FIELD_DATA_TYPE generate();
}
