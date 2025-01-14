package eu.zavadil.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlUtilTest {

	@Test
	public void testValidation() {
		Assertions.assertTrue(UrlUtils.isValidUrl("test.com"));
		Assertions.assertTrue(UrlUtils.isValidUrl("test.com/neco/"));
		Assertions.assertTrue(UrlUtils.isValidUrl("localhost"));
		Assertions.assertTrue(UrlUtils.isValidUrl("http://localhost"));
		Assertions.assertTrue(UrlUtils.isValidUrl("http://localhost/test"));
		Assertions.assertTrue(UrlUtils.isValidUrl("http://localhost:8080"));
		Assertions.assertTrue(UrlUtils.isValidUrl("http://test.com"));
		Assertions.assertTrue(UrlUtils.isValidUrl("http://neco.neco2.test.com"));
		Assertions.assertTrue(UrlUtils.isValidUrl("https://wordpress.test.incomaker.com"));
		Assertions.assertTrue(UrlUtils.isValidUrl("https://test.com//neco//"));
		Assertions.assertTrue(UrlUtils.isValidUrl("https://test.com?q=1&a=test"));
		Assertions.assertTrue(UrlUtils.isValidUrl("http://www.regexbuddy.com/index.html?source=library#copyright"));
		Assertions.assertTrue(UrlUtils.isValidUrl("www.regexbuddy.com/index.html?source=library#copyright"));

		Assertions.assertFalse(UrlUtils.isValidUrl("test.com", true));
		Assertions.assertFalse(UrlUtils.isValidUrl("www.regexbuddy.com/index.html?source=library#copyright", true));
		Assertions.assertFalse(UrlUtils.isValidUrl("word"));
		Assertions.assertFalse(UrlUtils.isValidUrl("spaces not allowed"));
		Assertions.assertFalse(UrlUtils.isValidUrl("no/actual/domain"));
		Assertions.assertFalse(UrlUtils.isValidUrl("httpno/actual/domain"));
		Assertions.assertFalse(UrlUtils.isValidUrl("http://no/actual/domain"));
	}

}
