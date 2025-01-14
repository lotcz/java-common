package eu.zavadil.java.util;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public abstract class DurationUtils {

	public static String format(Duration duration) {
		long mils = Math.round((float) duration.getNano() / 1000000);
		long secs = duration.getSeconds();
		long mins = (long) Math.floor((float) secs / 60);
		secs = secs - (mins * 60);
		long hrs = (long) Math.floor((float) mins / 60);
		mins = mins - (hrs * 60);

		List<String> parts = new ArrayList<>();
		if (hrs > 0) parts.add(String.format("%dh", hrs));
		if (mins > 0) parts.add(String.format("%dmin", mins));
		if (secs > 0) parts.add(String.format("%ds", secs));
		if (mils > 0 && hrs == 0 && mins == 0) parts.add(String.format("%dms", mils));

		if (parts.isEmpty()) return "0";

		return String.join(" ", parts);
	}

	public static Duration calculate(Instant start, Instant end) {
		if (start == null) return Duration.ZERO;
		if (end == null) end = Instant.now();
		return Duration.between(start, end);
	}

	public static long calculateMs(Instant start, Instant end) {
		return DurationUtils.calculate(start, end).toMillis();
	}
}
