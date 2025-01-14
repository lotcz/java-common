package eu.zavadil.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BigDecimalUtilTest {

	@Test
	public void testComparisons() {
		BigDecimal a = new BigDecimal(15);
		BigDecimal b = new BigDecimal(1.5D);
		BigDecimal c = null;

		Assertions.assertNotNull(BigDecimalUtils.safeCompare(a, b));
		Assertions.assertTrue(BigDecimalUtils.safeCompare(a, b) > 0);
		Assertions.assertTrue(BigDecimalUtils.safeCompare(b, a) < 0);

		Assertions.assertNull(BigDecimalUtils.safeCompare(a, c));

		Assertions.assertTrue(BigDecimalUtils.greaterThan(a, b));
		Assertions.assertFalse(BigDecimalUtils.greaterThan(b, a));
		Assertions.assertFalse(BigDecimalUtils.greaterThan(b, c));
	}

}
