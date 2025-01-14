package eu.zavadil.java.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public abstract class DateUtils {

	private static final int MILISECONDS_PER_DAY = 1000 * 3600 * 24;

	public static final List<SimpleDateFormat> DATE_FORMATS = Arrays.asList(
		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
		new SimpleDateFormat("yyyy-MM-dd HH:mm"),
		new SimpleDateFormat("yyyy-MM-dd"),
		new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));

	public static Date dateFromZoned(ZonedDateTime zoned) {
		if (zoned == null) return null;
		return Date.from(zoned.toInstant());
	}

	private static Date tryParseDate(String dateString, List<SimpleDateFormat> dateFormats, TimeZone timeZone) {
		for (SimpleDateFormat dateFormat : dateFormats) {
			try {
				dateFormat.setTimeZone(timeZone);
				return dateFormat.parse(dateString);
			} catch (Exception ignored) {
			}
		}
		return null;
	}

	public static Date parseDate(String dateString, List<SimpleDateFormat> dateFormats, TimeZone timezone) {
		if (StringUtils.isEmpty(dateString)) {
			return null;
		}
		return tryParseDate(dateString, new ArrayList<>(dateFormats), timezone);
	}

	public static Date parseDate(String dateString, TimeZone timezone) {
		return parseDate(dateString, DATE_FORMATS, timezone);
	}

	public static int differenceInDays(Timestamp from, Timestamp to) {
		return differenceInDays(from.getTime(), to.getTime());
	}

	public static int differenceInDays(Date from, Date to) {
		return differenceInDays(from.getTime(), to.getTime());
	}

	public static int differenceInDays(Long from, Long to) {
		long result = (to - from) / MILISECONDS_PER_DAY;
		return (int) result;
	}

	/**
	 * Return the first non-null value from args.
	 * If all args are null then default value is returned
	 * @param values
	 * @return
	 */
	public static Date selectNonNull(Date defaultValue, Date... values) {
		for (Date val: values) {
			if (val != null) return val;
		}
		return defaultValue;
	}
}
