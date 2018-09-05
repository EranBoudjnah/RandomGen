package com.mitteloupe.randomgenexample.generator;

import android.support.annotation.NonNull;

import com.mitteloupe.randomgen.RandomGen;
import com.mitteloupe.randomgenexample.data.flat.DivisionType;
import com.mitteloupe.randomgenexample.data.flat.Flat;
import com.mitteloupe.randomgenexample.data.flat.Room;
import com.mitteloupe.randomgenexample.data.flat.RoomType;

import java.util.Random;

/**
 * Created by Eran Boudjnah on 19/08/2018.
 */
public class FlatGeneratorFactory {
	private final Random mRandom;

	public FlatGeneratorFactory(Random pRandom) {
		mRandom = pRandom;
	}

	public RandomGen<Flat> getNewFlatGenerator() {
		return new RandomGen.Builder<>(new RandomGen.InstanceProvider<Flat>() {
			@Override
			public Flat provideInstance() {
				return new Flat(newRoom());
			}
		})
			.withField("mRooms")
			.returning(newContainerRoomRandomGen())
			.build();
	}

	private RandomGen<Room> newContainerRoomRandomGen() {
		return new RandomGen.Builder<>(new RandomGen.InstanceProvider<Room>() {
			@Override
			public Room provideInstance() {
				return newRoom();
			}
		})
			.withField("mRoomType")
			.returning(RoomType.class)
			.onGenerate(new RandomGen.OnGenerateCallback<Room>() {
				@Override
				public void onGenerate(Room pGeneratedInstance) {
					DivisionType divisionType = mRandom.nextBoolean() ? DivisionType.HORIZONTAL : DivisionType.VERTICAL;
					int roomsRemaining = mRandom.nextInt(7) + 1;
					splitRooms(pGeneratedInstance, roomsRemaining - 1, divisionType);
				}
			})
			.build();
	}

	private RandomGen<Room> newRoomRandomGen() {
		return new RandomGen.Builder<>(new RandomGen.InstanceProvider<Room>() {
			@Override
			public Room provideInstance() {
				return newRoom();
			}
		})
			.withField("mRoomType")
			.returning(RoomType.class)
			.build();
	}

	@NonNull
	private Room newRoom() {
		return new Room(RoomType.LIVING_ROOM, DivisionType.NONE, 0f, null, null);
	}

	private void splitRoomUsingRandomGen(final Room pRoom, RandomGen<Room> pRoomRandomGen, DivisionType pDivisionType) {
		new RandomGen.Builder<>(new RandomGen.InstanceProvider<Room>() {
			@Override
			public Room provideInstance() {
				return pRoom;
			}
		})
			.withField("mFirstRoom")
			.returning(pRoomRandomGen)
			.withField("mSecondRoom")
			.returning(pRoomRandomGen)
			.withField("mDivisionType")
			.returningExplicitly(pDivisionType)
			.withField("mDivisionRatio")
			.returning(0.25f, 0.75f)
			.build()
			.generate();
	}

	private void splitRooms(Room pRoom, int pRoomsRemaining, DivisionType pLastDivisionType) {
		if (pRoomsRemaining == 0) return;

		DivisionType newDivisionType = DivisionType.HORIZONTAL == pLastDivisionType ? DivisionType.VERTICAL : DivisionType.HORIZONTAL;
		splitRoomUsingRandomGen(pRoom, newRoomRandomGen(), newDivisionType);

		int roomsRemaining = pRoomsRemaining - 1;

		int leftRoomsRemaining = roomsRemaining != 0 ? mRandom.nextInt(roomsRemaining + 1) : 0;
		int rightRoomsRemaining = roomsRemaining - leftRoomsRemaining;

		splitRooms(pRoom.getFirstRoom(), leftRoomsRemaining, newDivisionType);
		splitRooms(pRoom.getSecondRoomBranch(), rightRoomsRemaining, newDivisionType);
	}
}
