package eu.zavadil.java.util;

import eu.zavadil.java.Lazy;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Various string helpers.
 * All methods starting with "safe" accept null values without throwing exceptions and
 * never return null - an empty string is returned when necessary.
 */
public class StringUtils {

	public static final String EMPTY_STRING = "";

	private static final Lazy<Random> randomGenerator = new Lazy<>(Random::new);

	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public static boolean notEmpty(String str) {
		return !StringUtils.isEmpty(str);
	}

	public static String emptyToNull(String str) {
		return StringUtils.isEmpty(str) ? null : str;
	}

	public static String nullToEmpty(String str) {
		return str == null ? EMPTY_STRING : str;
	}

	public static String safeSubstr(String str, int start, int length) {
		if (str == null) return EMPTY_STRING;
		if (start < 0 || start >= str.length()) return EMPTY_STRING;
		if (str.length() <= (start + length)) length = str.length() - start;
		return str.substring(start, start + length);
	}

	public static String safeTruncate(String str, int maxLength) {
		return StringUtils.safeSubstr(str, 0, maxLength);
	}

	/**
	 * Safely trims spaces from the start and end of the string - one or both can be null and no exception is thrown.
	 * @param str
	 * @return
	 */
	public static String safeTrim(String str) {
		if (str == null) return EMPTY_STRING;
		return str.trim();
	}

	public static String safeLowerCase(String str) {
		if (StringUtils.isEmpty(str)) return EMPTY_STRING;
		return str.toLowerCase();
	}

	public static String safeUpperCase(String str) {
		if (StringUtils.isEmpty(str)) return EMPTY_STRING;
		return str.toUpperCase();
	}

	/**
	 * Safely compare two strings - one or both can be null and no exception is thrown
	 * @param s1
	 * @param s2
	 * @return True if both strings are null, empty or they are the same sequence of characters
	 */
	public static boolean safeEquals(String s1, String s2) {
		s1 = emptyToNull(s1);
		s2 = emptyToNull(s2);
		if (s1 == null) return s2 == null;
		if (s2 == null) return false;
		return s1.equals(s2);
	}

	public static boolean safeEqualsIgnoreCase(String s1, String s2) {
		return safeEquals(safeLowerCase(s1), safeLowerCase(s2));
	}

	public static boolean safeContains(String haystack, String needle) {
		if (StringUtils.isEmpty(haystack) || StringUtils.isEmpty(needle)) return false;
		return haystack.contains(needle);
	}

	public static boolean safeContainsIgnoreCase(String haystack, String needle) {
		return safeContains(safeLowerCase(haystack), safeLowerCase(needle));
	}

	public static List<String> safeSplit(String input, String separator) {
		if (isEmpty(input)) {
			return new ArrayList<>();
		}

		return Arrays.stream(input.split(separator)).toList();
	}

	public static List<String> safeSplit(String input, List<String> separators) {
		List<String> list = new ArrayList<>();

		if (isEmpty(input)) {
			return list;
		}

		for (String separator : separators) {
			if (input.contains(separator)) {
				return safeSplit(input, separator);
			}
		}

		return list;
	}

	/**
	 * Return unformatted raw pattern when there is formatting exception.
	 *
	 * @param pattern
	 * @param values
	 * @return
	 */
	public static String safeFormat(String pattern, List<String> values) {
		try {
			return String.format(pattern, values.toArray());
		} catch (Exception e) {
			return pattern;
		}
	}

	/**
	 * Return the first non-empty string from args. Null is considered to be an empty string.
	 * If all args are null or empty then default value is returned
	 * @param strings
	 * @return
	 */
	public static String selectNonEmpty(String defaultValue, String... strings) {
		for (String str: strings) {
			if (StringUtils.notEmpty(str)) return str;
		}
		return defaultValue;
	}

	public static String stripHtmlTags(String str) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		return str.replaceAll("<[^>].*?>", "");
	}

	public static String deAccent(String str) {
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}

	/**
	 * Transforms string to camel case.
	 * @param str
	 * @param separator Character(s) used to separate words
	 * @return
	 */
	public static String toCamelCase(String str, String separator) {
		assert StringUtils.notEmpty(separator);

		if (StringUtils.isEmpty(str)) {
			return str;
		}

		str = str.replaceAll("[\u201D%:.,)&(\\]\\[}{+-]", separator).trim();
		str = str.replaceAll(separator + "+", separator).trim();
		while (str.contains(separator)) {
			String afterReplace = str.replaceFirst(separator + "[a-zA-Z0-9]", String.valueOf(Character.toUpperCase(str.charAt(str.indexOf(separator) + 1))));
			if (afterReplace.equals(str)) {
				return str;
			} else {
				str = afterReplace;
			}
		}
		return str;
	}

	/**
	 * Transforms string with underscores to camel case.
	 * @param str
	 * @return
	 */
	public static String toCamelCase(String str) {
		return StringUtils.toCamelCase(str, "_");
	}

	public static String getLangISO2(String string) {
		if (string != null && string.length() > 2) {
			string = string.substring(0, 2);
		}
		return string;
	}

	public String stripNewLines(String input) {
		if (isEmpty(input)) return input;
		return input.replace("\n", " ");
	}

	/**
	 * Generates a random string
	 */
	public static String random(int length) {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'

		return randomGenerator
			.get()
			.ints(leftLimit, rightLimit + 1)
			.limit(length)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
	}

	/**
	 * Generates a random string
	 */
	public static String random(int minLength, int maxLength) {
		return random(randomGenerator.get().nextInt(minLength, maxLength + 1));
	}

	public static List<String> sort(List<String> list) {
		if (list == null || list.isEmpty()) return List.of();
		return list.stream().sorted(Comparator.comparing(s -> StringUtils.safeLowerCase(s))).toList();
	}

}
