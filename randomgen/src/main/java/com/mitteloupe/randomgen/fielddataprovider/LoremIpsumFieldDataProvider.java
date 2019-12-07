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
public final class LoremIpsumFieldDataProvider<OUTPUT_TYPE> implements FieldDataProvider<OUTPUT_TYPE, String> {
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
	 * Returns a new instance of {@link LoremIpsumFieldDataProvider} generating one whole {@code String} of Lorem Ipsum.
	 */
	public static <OUTPUT_TYPE> LoremIpsumFieldDataProvider<OUTPUT_TYPE> getInstance() {
		return new LoremIpsumFieldDataProvider<>();
	}

	/**
	 * Returns a new instance of {@link LoremIpsumFieldDataProvider} generating a {@code String} of Lorem Ipsum of the requested length.
	 *
	 * The Lorem Ipsum text is repeated if the requested length is longer than the length of Lorem Ipsum. In such cases, the default
	 * delimiter {@code DEFAULT_PARAGRAPH_DELIMITER} is used.
	 *
	 * @param pLength The length of the returned string
	 */
	public static <OUTPUT_TYPE> LoremIpsumFieldDataProvider<OUTPUT_TYPE> getInstanceWithLength(int pLength) {
		return new LoremIpsumFieldDataProvider<>(pLength);
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
	public static <OUTPUT_TYPE> LoremIpsumFieldDataProvider<OUTPUT_TYPE> getInstanceWithRange(Random pRandom, int pMinLength, int pMaxLength) {
		return new LoremIpsumFieldDataProvider<>(pRandom, pMinLength, pMaxLength);
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
	public static <OUTPUT_TYPE> LoremIpsumFieldDataProvider<OUTPUT_TYPE> getInstanceWithRangeAndDelimiter(
		Random pRandom,
		int pMinLength,
		int pMaxLength,
		String pParagraphDelimiter
	) {
		return new LoremIpsumFieldDataProvider<>(pRandom, pMinLength, pMaxLength, pParagraphDelimiter);
	}

	private LoremIpsumFieldDataProvider() {
		this(LOREM_IPSUM_LENGTH);
	}

	private LoremIpsumFieldDataProvider(int pLength) {
		this(new FakeRandom(), pLength, pLength);
	}

	private LoremIpsumFieldDataProvider(Random pRandom, int pMinLength, int pMaxLength) {
		this(pRandom, pMinLength, pMaxLength, DEFAULT_PARAGRAPH_DELIMITER);
	}

	private LoremIpsumFieldDataProvider(Random pRandom, int pMinLength, int pMaxLength, String pParagraphDelimiter) {
		mRandom = pRandom;
		mMinLength = pMinLength;
		mMaxLength = pMaxLength;
		mParagraphDelimiter = pParagraphDelimiter;
	}

	@Override
	public String generate(OUTPUT_TYPE instance) {
		int remainingLength = mRandom.nextInt(mMaxLength - mMinLength + 1) + mMinLength;

		return getLoremIpsumString(remainingLength);
	}

	private String getLoremIpsumString(int pRemainingLength) {
		final int delimiterLength = mParagraphDelimiter.length();
		StringBuilder stringBuilder = new StringBuilder();

		while (pRemainingLength >= LOREM_IPSUM_LENGTH + delimiterLength) {
			stringBuilder
				.append(LOREM_IPSUM)
				.append(mParagraphDelimiter);
			pRemainingLength -= (LOREM_IPSUM_LENGTH + delimiterLength);
		}

		if (pRemainingLength == 0) {
			if (stringBuilder.length() != 0) {
				stringBuilder.delete((stringBuilder.length() - delimiterLength), stringBuilder.length());
			}

		} else {
			stringBuilder.append(LOREM_IPSUM, 0, pRemainingLength);
		}

		return stringBuilder.toString();
	}

	private static class FakeRandom extends Random {
		@Override
		public int nextInt() {
			return 0;
		}
	}
}