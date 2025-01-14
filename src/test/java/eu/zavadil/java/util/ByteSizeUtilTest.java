package eu.zavadil.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ByteSizeUtilTest {

	@Test
	public void testGigas() {
		String str = ByteSizeUtils.format(2268586792L);
		Assertions.assertEquals("2.1 GiB", str);

		String strSi = ByteSizeUtils.formatSi(2268586792L);
		Assertions.assertEquals("2.3 GB", strSi);
	}

}
