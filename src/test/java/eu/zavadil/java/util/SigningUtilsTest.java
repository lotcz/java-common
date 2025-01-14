package eu.zavadil.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class SigningUtilsTest {

	@Test
	public void signingTestRSA() throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(2048);

		KeyPair keyPair1 = keyGen.generateKeyPair();
		String data1 = "This is some data to be signed";
		String signature1 = SigningUtils.sign(data1, keyPair1.getPrivate());

		KeyPair keyPair2 = keyGen.generateKeyPair();
		String data2 = "some other data";
		String signature2 = SigningUtils.sign(data2, keyPair2.getPrivate());

		Assertions.assertTrue(SigningUtils.verifySignature(data1, signature1, keyPair1.getPublic()));
		Assertions.assertTrue(SigningUtils.verifySignature(data2, signature2, keyPair2.getPublic()));

		Assertions.assertFalse(SigningUtils.verifySignature(data1, signature2, keyPair1.getPublic()));
		Assertions.assertFalse(SigningUtils.verifySignature(data1, signature1, keyPair2.getPublic()));
		Assertions.assertFalse(SigningUtils.verifySignature(data2, signature1, keyPair1.getPublic()));
		Assertions.assertFalse(SigningUtils.verifySignature(data2, signature2, keyPair1.getPublic()));
	}

	@Test
	public void signingTestEllipticCurves() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
		ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
		keyGen.initialize(ecSpec);

		KeyPair keyPair1 = keyGen.generateKeyPair();
		String data1 = "This is some data to be signed";
		String signature1 = SigningUtils.sign(data1, keyPair1.getPrivate(), "SHA256withECDSA");

		KeyPair keyPair2 = keyGen.generateKeyPair();
		String data2 = "some other data";
		String signature2 = SigningUtils.sign(data2, keyPair2.getPrivate(), "SHA256withECDSA");

		Assertions.assertTrue(SigningUtils.verifySignature(data1, signature1, keyPair1.getPublic(), "SHA256withECDSA"));
		Assertions.assertTrue(SigningUtils.verifySignature(data2, signature2, keyPair2.getPublic(), "SHA256withECDSA"));

		Assertions.assertFalse(SigningUtils.verifySignature(data1, signature2, keyPair1.getPublic(), "SHA256withECDSA"));
		Assertions.assertFalse(SigningUtils.verifySignature(data1, signature1, keyPair2.getPublic(), "SHA256withECDSA"));
		Assertions.assertFalse(SigningUtils.verifySignature(data2, signature1, keyPair1.getPublic(), "SHA256withECDSA"));
		Assertions.assertFalse(SigningUtils.verifySignature(data2, signature2, keyPair1.getPublic(), "SHA256withECDSA"));
	}

	@Test
	public void upgatesKeyExtractionTest() {
		String fileContent = "-----BEGIN PUBLIC KEY-----\n" +
			"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAED1b1Rp7dM4ulrn9ySyMaEVL5674q\n" +
			"c8yUX7OZwhqiAvDmeQ9insZhyUboPEyGBnj95P1CiH4ZtBQh5BeQZBfQfg==\n" +
			"-----END PUBLIC KEY-----";
		PublicKey publicKey = SigningUtils.extractPublicKeyFromPEM(fileContent, "EC");

		Assertions.assertEquals("X.509", publicKey.getFormat());
		Assertions.assertEquals("EC", publicKey.getAlgorithm());
	}

}
