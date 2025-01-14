package eu.zavadil.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;

public class InstantUtilTest {

	@Test
	public void testHumanReadable() {
		Assertions.assertEquals(
			"2024-01-02 16:20:00 (UTC)",
			InstantUtils.formatAsHumanReadable(
				Instant.parse("2024-01-02T16:20:00.000Z"),
				ZoneId.of("UTC")
			)
		);
	}

}
