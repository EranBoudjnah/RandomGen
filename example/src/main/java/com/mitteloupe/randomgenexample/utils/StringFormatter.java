package com.mitteloupe.randomgenexample.utils;

import androidx.annotation.NonNull;

/**
 * Created by Eran Boudjnah on 29/08/2018.
 */
public class StringFormatter<TYPE extends Enum> {
	private StringBuilder mStringBuilder = new StringBuilder();

	public String formatEnumValue(TYPE pEnum) {
		String[] words = getWordsFromEnum(pEnum);
		clearStringBuilder();
		concatWordsWithSpaces(words);
		return mStringBuilder.toString();
	}

	@NonNull
	private String[] getWordsFromEnum(TYPE pEnum) {
		return pEnum.toString().split("_");
	}

	private void clearStringBuilder() {
		mStringBuilder.delete(0, mStringBuilder.length());
	}

	private void concatWordsWithSpaces(String[] pWords) {
		int lastCharacter = pWords.length - 1;

		for (int characterPosition = 0; characterPosition <= lastCharacter; ++characterPosition) {
			String word = pWords[characterPosition];
			addCapitalizedWord(word);
			if (characterPosition < lastCharacter) {
				mStringBuilder.append(" ");
			}
		}
	}

	private void addCapitalizedWord(String pWord) {
		mStringBuilder.append(pWord.substring(0, 1).toUpperCase())
			.append(pWord.substring(1).toLowerCase());
	}
}
