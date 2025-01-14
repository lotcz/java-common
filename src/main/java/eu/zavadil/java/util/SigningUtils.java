package eu.zavadil.java.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SigningUtils {

	public static PublicKey extractPublicKeyFromPEM(String fileContent, String algorithm) {
		try {
			String publicKeyContent = fileContent
				.replaceAll("-----BEGIN PUBLIC KEY-----", "")
				.replaceAll("-----END PUBLIC KEY-----", "")
				.replaceAll("\\s", "");
			byte[] decodedKey = Base64.getDecoder().decode(publicKeyContent);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey, algorithm);
			KeyFactory keyFactory = KeyFactory.getInstance(keySpec.getAlgorithm());
			return keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			throw new RuntimeException("Error when extracting public key", e);
		}
	}

	public static PublicKey extractPublicKeyFromPEM(String fileContent) {
		return extractPublicKeyFromPEM(fileContent, "RSA");
	}

	public static PublicKey extractPublicKeyFromPEM(InputStream inputStream, String algorithm) throws IOException {
		return extractPublicKeyFromPEM(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8), algorithm);
	}

	public static PublicKey extractPublicKeyFromPEM(InputStream inputStream) throws IOException {
		return extractPublicKeyFromPEM(inputStream, "RSA");
	}

	public static String sign(String data, PrivateKey privateKey, String algorithm) {
		try {
			Signature signature = Signature.getInstance(algorithm);
			signature.initSign(privateKey);
			signature.update(data.getBytes());
			byte[] signedBytes = signature.sign();
			return Base64.getEncoder().encodeToString(signedBytes);
		} catch (Exception e) {
			throw new RuntimeException("Error when signing data", e);
		}
	}

	/**
	 * Signs data string using SHA256withRSA and return base64 encoded signature
	 */
	public static String sign(String data, PrivateKey privateKey) {
		return sign(data, privateKey, "SHA256withRSA");
	}

	public static boolean verifySignature(String data, String signature, PublicKey publicKey, String algorithm) {
		try {
			Signature sig = Signature.getInstance(algorithm);
			sig.initVerify(publicKey);
			sig.update(data.getBytes());
			byte[] signatureBytes = Base64.getDecoder().decode(signature);
			return sig.verify(signatureBytes);
		} catch (Exception e) {
			throw new RuntimeException("Error when verifying signature", e);
		}
	}

	/**
	 * Verifies signature created using SHA256withRSA. Signature must be base64 encoded.
	 */
	public static boolean verifySignature(String data, String signature, PublicKey publicKey) {
		return verifySignature(data, signature, publicKey, "SHA256withRSA");
	}
}
