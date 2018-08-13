package com.mitteloupe.randomgen;

/**
 * Generates one value of the defined type.
 *
 * @param <FIELD_DATA_TYPE>
 */
public interface FieldDataProvider<OUTPUT_TYPE, FIELD_DATA_TYPE> {
	FIELD_DATA_TYPE generate(OUTPUT_TYPE instance);
}
