package eu.zavadil.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HashUtilsTest {

	@Test
	public void testHash() {
		String sha256 = HashUtils.hashSha256("test");
		Assertions.assertEquals("n4bQgYhMfWWaL+qgxVrQFaO/TxsrC4Is0V1sFbDwCgg=", sha256);

		String md5 = HashUtils.hashMd5("test");
		Assertions.assertEquals("CY9rzUYh03PK3k6DJie09g==", md5);
	}

	@Test
	public void testVerify() {
		Assertions.assertTrue(HashUtils.verifyMd5("test", HashUtils.hashMd5("test")));
		Assertions.assertTrue(HashUtils.verifySha256("test", HashUtils.hashSha256("test")));
		Assertions.assertFalse(HashUtils.verifyMd5("test", HashUtils.hashSha256("test")));
		Assertions.assertFalse(HashUtils.verifySha256("test", HashUtils.hashMd5("test")));
	}

}
