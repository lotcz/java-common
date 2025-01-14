package eu.zavadil.java.util;

public class ObjectUtils {

	/**
	 * Return the first non-null value from args.
	 * If all args are null then default value is returned
	 * @param values
	 * @return
	 */
	public static Object selectNonNull(Object defaultValue, Object... values) {
		for (Object val: values) {
			if (val != null) return val;
		}
		return defaultValue;
	}

	public static boolean safeEquals(Object o1, Object o2) {
		if (o1 == null) return o2 == null;
		if (o2 == null) return false;
		return o1.equals(o2);
	}

}
