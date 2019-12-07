package com.mitteloupe.randomgenexample.data.flat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Eran Boudjnah on 26/08/2018.
 */
public final class Room {
	private final RoomType mRoomType;
	private final DivisionType mDivisionType;
	private final float mDivisionRatio;
	private final Room mFirstRoom;
	private final Room mSecondRoom;

	public Room(
		@NonNull RoomType pRoomType,
		DivisionType pDivisionType,
		float pDivisionRatio,
		@Nullable Room pFirstRoom,
		@Nullable Room pSecondRoom
	) {
		mRoomType = pRoomType;
		mDivisionType = pDivisionType;
		mDivisionRatio = pDivisionRatio;
		mFirstRoom = pFirstRoom;
		mSecondRoom = pSecondRoom;
	}

	@NonNull
	public RoomType getRoomType() {
		return mRoomType;
	}

	public DivisionType getDivisionType() {
		return mDivisionType;
	}

	public float getDivisionRatio() {
		return mDivisionRatio;
	}

	@Nullable
	public Room getFirstRoom() {
		return mFirstRoom;
	}

	@Nullable
	public Room getSecondRoomBranch() {
		return mSecondRoom;
	}
}
