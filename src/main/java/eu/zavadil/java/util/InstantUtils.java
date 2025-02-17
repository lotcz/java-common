package eu.zavadil.java.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class InstantUtils {

	public static boolean safeEquals(Instant a, Instant b) {
		if (a == null) return b == null;
		return a.equals(b);
	}

	public static Instant selectMostRecent(Instant a, Instant b) {
		if (a == null) return b;
		if (b == null) return a;
		return a.isAfter(b) ? a : b;
	}

	public static int differenceInDays(Instant from, Instant to) {
		if (from == null || to == null) return 0;
		return (int) Duration.between(from, to).toDays();
	}

	public static Instant safeParseIso(String value) {
		if (StringUtils.isEmpty(value)) return null;
		try {
			return Instant.parse(value);
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatAsIsoString(Instant value) {
		if (value == null) return "";
		return value.toString();
	}

	public static String formatAsHumanReadable(Instant value, ZoneId zone, String nullValue) {
		if (value == null) return nullValue;
		return String.format(
			"%s (%s)",
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(zone).format(value),
			zone.getId()
		);
	}

	public static String formatAsHumanReadable(Instant value, ZoneId zone) {
		return formatAsHumanReadable(value, zone, "null");
	}

	public static String formatAsHumanReadable(Instant value, String nullValue) {
		return formatAsHumanReadable(value, ZoneId.systemDefault(), nullValue);
	}

	public static String formatAsHumanReadable(Instant value) {
		return formatAsHumanReadable(value, "null");
	}

	public static boolean isValidIsoString(String value) {
		if (StringUtils.isEmpty(value)) return false;
		return InstantUtils.safeParseIso(value) != null;
	}

	public static Instant atStartOfDay(LocalDate value) {
		if (value == null) return null;
		return value.atStartOfDay(ZoneId.systemDefault()).toInstant();
	}

	public static Instant atEndOfDay(LocalDate value) {
		Instant atStart = InstantUtils.atStartOfDay(value);
		if (value == null) return null;
		return atStart.plus(Duration.ofHours(24)).minus(Duration.ofMillis(1));
	}

	/**
	 * Return the first non-null value from args.
	 * If all args are null then default value is returned
	 * @param values
	 * @return
	 */
	public static Instant selectNonNull(Instant defaultValue, Instant... values) {
		for (Instant val: values) {
			if (val != null) return val;
		}
		return defaultValue;
	}
}
