package eu.zavadil.java.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.CRC32;

public class HashUtils {

	public static String hash(String payload, String algorithm) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(payload.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(String.format("%s algorithm not found!", algorithm), e);
		}
	}

	public static boolean verify(String original, String hashed, String algorithm) {
		return StringUtils.safeEquals(hashed, HashUtils.hash(original, algorithm));
	}

	public static String hashSha256(String payload) {
		return HashUtils.hash(payload, "SHA-256");
	}

	public static boolean verifySha256(String original, String hashed) {
		return HashUtils.verify(original, hashed, "SHA-256");
	}

	public static String hashMd5(String payload) {
		return HashUtils.hash(payload, "MD5");
	}

	public static boolean verifyMd5(String original, String hashed) {
		return HashUtils.verify(original, hashed, "MD5");
	}

	public static String crc32Hex(String input) {
		CRC32 crc = new CRC32();
		byte[] bytes = input.getBytes(java.nio.charset.StandardCharsets.UTF_8);
		crc.update(bytes, 0, bytes.length);
		long checksum = crc.getValue();
		return Long.toHexString(checksum);
	}
}
