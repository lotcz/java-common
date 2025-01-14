package eu.zavadil.java.util;

import java.util.regex.Pattern;

public class UrlUtils {

	private static final String HTTP_PROTOCOL_PREFIX = "http://";

	private static final String HTTPS_PROTOCOL_PREFIX = "https://";

	private static final Pattern VALIDATE_URL_REGEX = Pattern.compile(
		"^((http|https|ftp|file)://)?(([-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|](\\.)[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|])|localhost)[-A-Za-z0-9+&@#/%?=~_|!:,.;]*"
	);

	/**
	 * Return true if given string is a valid URL.
	 * @param url
	 * @param requireHttpProtocol To pass this test, string must start with http:// or https://
	 * @return
	 */
	public static boolean isValidUrl(String url, boolean requireHttpProtocol) {
		if (StringUtils.isEmpty(url)) return false;
		boolean urlOK = VALIDATE_URL_REGEX.matcher(url).matches();
		if (!(requireHttpProtocol && urlOK)) return urlOK;
		String lower = url.toLowerCase();
		return lower.startsWith(HTTP_PROTOCOL_PREFIX) || lower.startsWith(HTTPS_PROTOCOL_PREFIX);
	}

	/**
	 * Return true if given string is a valid URL.
	 * @param url
	 * @return
	 */
	public static boolean isValidUrl(String url) {
		return isValidUrl(url, false);
	}
}
