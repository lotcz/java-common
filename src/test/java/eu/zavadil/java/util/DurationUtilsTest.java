package eu.zavadil.java.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Duration;
import java.time.Instant;

public class DurationUtilsTest {

	@Test
	void testCalculateSecs() {
		Instant instant1 = Instant.ofEpochSecond(100);
		Instant instant2 = Instant.ofEpochSecond(150);
		Duration duration = DurationUtils.calculate(instant1, instant2);
		long ms = duration.toSeconds();
		assertEquals(50, ms);
	}

	@Test
	void testCalculateNow() {
		Duration controlDuration = Duration.ofSeconds(15);
		Instant instant1 = Instant.now().minus(controlDuration);
		Duration duration = DurationUtils.calculate(instant1, null);
		long s = duration.toSeconds();
		assertEquals(15, s);
	}

	@Test
	void testFormatMillis() {
		Duration duration = Duration.ofMillis(150);
		String result = DurationUtils.format(duration);
		assertEquals("150ms", result);
	}

	@Test
	void testFormatSecs() {
		Duration duration = Duration.ofSeconds(24);
		String result = DurationUtils.format(duration);
		assertEquals("24s", result);
	}

	@Test
	void testFormatMinsAndSecs() {
		Duration duration = Duration.ofSeconds(88);
		String result = DurationUtils.format(duration);
		assertEquals("1min 28s", result);
	}

	@Test
	void testFormatMins() {
		Duration duration = Duration.ofMinutes(5);
		String result = DurationUtils.format(duration);
		assertEquals("5min", result);
	}

	@Test
	void testFormatHrs() {
		Duration duration = Duration.ofHours(4);
		String result = DurationUtils.format(duration);
		assertEquals("4h", result);
	}

	@Test
	void testFormatComplex() {
		int hrs = 2;
		int mins = 25;
		int secs = 34;
		int ms = 150;
		long total = (1000 * (secs + (60 * (mins + (60 * hrs))))) + ms;
		Duration duration = Duration.ofMillis(total);
		String result = DurationUtils.format(duration);
		assertEquals("2h 25min 34s", result);
	}
}
