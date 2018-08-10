package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.Random;

/**
 * Provides strings of Lorem Ipsum.
 *
 * See: <a href="https://www.lipsum.com/">https://www.lipsum.com/</a>
 *
 * TODO
 * Introduce Options:
 *   Shuffle sentences
 *   Hard cut, Trim at word, Trim at sentence, Ellipsize
 * Use builder pattern
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class LoremIpsumFieldDataProvider implements FieldDataProvider<String> {
	private final static String DEFAULT_PARAGRAPH_DELIMITER = "\n\n";
	private final static String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore" +
		"magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure" +
		"dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in" +
		"culpa qui officia deserunt mollit anim id est laborum.";
	private final static int LOREM_IPSUM_LENGTH = LOREM_IPSUM.length();

	private final Random mRandom;
	private final String mParagraphDelimiter;
	private final int mMinLength;
	private final int mMaxLength;

	/**
	 * Creates an instance of {@link LoremIpsumFieldDataProvider} generating one whole {@code String} of Lorem Ipsum.
	 *
	 * @param pRandom A random value generator
	 */
	public LoremIpsumFieldDataProvider(Random pRandom) {
		this(pRandom, LOREM_IPSUM_LENGTH);
	}

	/**
	 * Creates an instance of {@link LoremIpsumFieldDataProvider} generating a {@code String} of Lorem Ipsum of the requested length.
	 *
	 * The Lorem Ipsum text is repeated if the requested length is longer than the length of Lorem Ipsum. In such cases, the default
	 * delimiter {@code DEFAULT_PARAGRAPH_DELIMITER} is used.
	 *
	 * @param pRandom A random value generator
	 * @param pLength The length of the returned string
	 */
	public LoremIpsumFieldDataProvider(Random pRandom, int pLength) {
		this(pRandom, pLength, pLength);
	}

	/**
	 * Creates an instance of {@link LoremIpsumFieldDataProvider} generating a {@code String} of Lorem Ipsum with length in the range of
	 * {@code pMinLength} to {@code pMaxLength}.
	 *
	 * The Lorem Ipsum text is repeated if the generated length is longer than the length of Lorem Ipsum. In such cases, the default
	 * delimiter {@code DEFAULT_PARAGRAPH_DELIMITER} is used.
	 *
	 * @param pRandom    A random value generator
	 * @param pMinLength The minimum length of the returned string
	 * @param pMaxLength The maximum length of the returned string
	 */
	public LoremIpsumFieldDataProvider(Random pRandom, int pMinLength, int pMaxLength) {
		this(pRandom, pMinLength, pMaxLength, DEFAULT_PARAGRAPH_DELIMITER);
	}

	/**
	 * Creates an instance of {@link LoremIpsumFieldDataProvider} generating a {@code String} of Lorem Ipsum with length in the range of
	 * {@code pMinLength} to {@code pMaxLength}.
	 *
	 * The Lorem Ipsum text is repeated if the generated length is longer than the length of Lorem Ipsum. In such cases, the provided
	 * delimiter is used.
	 *
	 * @param pRandom             A random value generator
	 * @param pMinLength          The minimum length of the returned string
	 * @param pMaxLength          The maximum length of the returned string
	 * @param pParagraphDelimiter The delimiter to use for long Lorem Ipsums
	 */
	public LoremIpsumFieldDataProvider(Random pRandom, int pMinLength, int pMaxLength, String pParagraphDelimiter) {
		mRandom = pRandom;
		mMinLength = pMinLength;
		mMaxLength = pMaxLength;
		mParagraphDelimiter = pParagraphDelimiter;
	}

	@Override
	public String generate() {
		final int delimiterLength = mParagraphDelimiter.length();
		int remainingLength = mRandom.nextInt(mMaxLength - mMinLength + 1) + mMinLength;

		StringBuilder stringBuilder = new StringBuilder();

		while (remainingLength >= LOREM_IPSUM_LENGTH + delimiterLength) {
			stringBuilder
				.append(LOREM_IPSUM)
				.append(mParagraphDelimiter);
			remainingLength -= (LOREM_IPSUM_LENGTH + delimiterLength);
		}

		if (remainingLength == 0) {
			if (stringBuilder.length() != 0) {
				stringBuilder.delete((stringBuilder.length() - delimiterLength), stringBuilder.length());
			}

		} else {
				stringBuilder.append(LOREM_IPSUM.substring(0, remainingLength));
		}

		return stringBuilder.toString();
	}
}