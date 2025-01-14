package eu.zavadil.java.util;

import java.math.BigDecimal;

public class BigDecimalUtils {

	/**
	 * Return the first non-null value from args.
	 * If all args are null then default value is returned
	 * @param values
	 * @return
	 */
	public static BigDecimal selectNonNull(BigDecimal defaultValue, BigDecimal... values) {
		return (BigDecimal) ObjectUtils.selectNonNull(defaultValue, (Object[]) values);
	}

	public static BigDecimal parse(String str) {
		return new BigDecimal(str);
	}

	public static BigDecimal safeParse(String str, BigDecimal defaultValue) {
		try {
			assert StringUtils.notEmpty(str);
			return parse(str.replace(",", ".").replace(" ", ""));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static BigDecimal safeParse(String str) {
		return safeParse(str, null);
	}

	/**
	 * Both are null or values are equal -> 0
	 * Number A is greater than B -> 1
	 * Number A is less than B -> -1
	 * One of numbers is null -> null
	 *
	 */
	public static Integer safeCompare(BigDecimal a, BigDecimal b) {
		if (a == null && b == null) return 0;
		if (a == null || b == null) return null;
		return a.compareTo(b);
	}

	public static boolean safeEquals(BigDecimal a, BigDecimal b) {
		Integer comparison = safeCompare(a, b);
		return (comparison != null && comparison == 0);
	}

	/**
	 * @return True if A is greater than B
	 */
	public static boolean greaterThan(BigDecimal a, BigDecimal b) {
		Integer comparison = safeCompare(a, b);
		return (comparison != null && comparison > 0);
	}

	/**
	 * @return True if A is lesser than B
	 */
	public static boolean lesserThan(BigDecimal a, BigDecimal b) {
		Integer comparison = safeCompare(a, b);
		return (comparison != null && comparison < 0);
	}

}
