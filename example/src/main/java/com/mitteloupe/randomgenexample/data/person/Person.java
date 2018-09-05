package com.mitteloupe.randomgenexample.data.person;

/**
 * Created by Eran Boudjnah on 13/08/2018.
 */
public class Person {
	private final String mName;
	private final int mAge;
	private final Occupation mOccupation;
	private final String mPhoneNumber;
	private final Gender mGender;

	public Person(Gender pGender, String pName, int pAge, Occupation pOccupation, String pPhoneNumber) {
		mGender = pGender;
		mName = pName;
		mAge = pAge;
		mOccupation = pOccupation;
		mPhoneNumber = pPhoneNumber;
	}

	public String getName() {
		return mName;
	}

	public int getAge() {
		return mAge;
	}

	public Occupation getOccupation() {
		return mOccupation;
	}

	public String getPhoneNumber() {
		return mPhoneNumber;
	}

	public Gender getGender() {
		return mGender;
	}
}
