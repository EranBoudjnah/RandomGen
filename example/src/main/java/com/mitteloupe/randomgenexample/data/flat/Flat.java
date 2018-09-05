package com.mitteloupe.randomgenexample.data.flat;

import android.support.annotation.NonNull;

/**
 * Created by Eran Boudjnah on 26/08/2018.
 */
public final class Flat {
	private final Room mRooms;

	public Flat(@NonNull Room pRooms) {
		mRooms = pRooms;
	}

	public @NonNull Room getRooms() {
		return mRooms;
	}
}
