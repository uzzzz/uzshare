package uzblog.base.utils;

import java.security.SecureRandom;

public final class DefaultRandomStringGenerator implements RandomStringGenerator {

	private static final char[] PRINTABLE_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ012345679"
			.toCharArray();

	private static final int DEFAULT_MAX_RANDOM_LENGTH = 35;

	private SecureRandom randomizer = new SecureRandom();

	private final int maximumRandomLength;

	public DefaultRandomStringGenerator() {
		this.maximumRandomLength = DEFAULT_MAX_RANDOM_LENGTH;
	}

	public DefaultRandomStringGenerator(final int maxRandomLength) {
		this.maximumRandomLength = maxRandomLength;
	}

	public int getMinLength() {
		return this.maximumRandomLength;
	}

	public int getMaxLength() {
		return this.maximumRandomLength;
	}

	public String getNewString() {
		final byte[] random = getNewStringAsBytes();

		return convertBytesToString(random);
	}

	public byte[] getNewStringAsBytes() {
		final byte[] random = new byte[this.maximumRandomLength];

		this.randomizer.nextBytes(random);

		return random;
	}

	private String convertBytesToString(final byte[] random) {
		final char[] output = new char[random.length];
		for (int i = 0; i < random.length; i++) {
			final int index = Math.abs(random[i] % PRINTABLE_CHARACTERS.length);
			output[i] = PRINTABLE_CHARACTERS[index];
		}

		return new String(output);
	}
}
