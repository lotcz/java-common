package eu.zavadil.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StringUtilTest {

	@Test
	public void testRandom() {
		String str = StringUtils.random(10);
		Assertions.assertNotNull(str);
		Assertions.assertEquals(10, str.length());
		Assertions.assertNotEquals(str, StringUtils.random(10));
	}

	@Test
	public void testSafeSubstr() {
		String str = "Hello, World!";
		Assertions.assertEquals("Hello", StringUtils.safeSubstr(str, 0, 5));
		Assertions.assertEquals("o, ", StringUtils.safeSubstr(str, 4, 3));
		Assertions.assertEquals("rld!", StringUtils.safeSubstr(str, 9, 10));
		Assertions.assertEquals("", StringUtils.safeSubstr(str, 15, 10));
		Assertions.assertEquals("", StringUtils.safeSubstr(null, 15, 10));
	}

	@Test
	public void testSelectNonEmpty() {
		Assertions.assertEquals("Hello", StringUtils.selectNonEmpty("Default", "Hello", "World"));
		Assertions.assertEquals("Hello", StringUtils.selectNonEmpty("Default",  "", null, "Hello", "World"));
		Assertions.assertEquals("Default", StringUtils.selectNonEmpty("Default",  "", null, "", "", null));
	}

	@Test
	public void testStripHtmlTags() {
		Assertions.assertEquals("Hello", StringUtils.stripHtmlTags("<div>Hello</div>"));

	}

	@Test
	public void testSort() {
		List<String> strings = List.of("Bender", "je", "buh", "a", "Karel", "taky");
		Assertions.assertEquals(List.of("a", "Bender", "buh", "je", "Karel", "taky"), StringUtils.sort(strings));
	}
}
