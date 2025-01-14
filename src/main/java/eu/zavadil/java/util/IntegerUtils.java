package eu.zavadil.java.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegerUtils {

	/**
	 * Return the first non-null value from args.
	 * If all args are null then default value is returned
	 * @param values
	 * @return
	 */
	public static Integer selectNonNull(Integer defaultValue, Integer... values) {
		for (Integer val: values) {
			if (val != null) return val;
		}
		return defaultValue;
	}

	public static boolean isInteger(String str) {
		try {
			Integer.valueOf(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static final Pattern numericPattern = Pattern.compile("\\d+");

	public static Optional<Integer> extractInteger(String str) {
		Matcher matcher = numericPattern.matcher(str);
		if (!matcher.find()) {
			return Optional.empty();
		}
		final String iStr = matcher.group();
		if (!isInteger(iStr)) {
			return Optional.empty();
		}
		return Optional.of(Integer.parseInt(iStr));
	}

	public static boolean safeEquals(Integer a, Integer b) {
		if (a == null) return b == null;
		return a.equals(b);
	}

}
