package eu.zavadil.java.util;

public class ExceptionUtils {

	/**
	 * Attempts to get human-readable exception message.
	 */
	public static String getMessage(Exception e) {
		String name = e.getClass().getName();
		String message = getMessageFromTree(e);
		return String.format("[%s] %s", name, message);
	}

	/**
	 * Return the first exception message from tree that is not empty
	 */
	public static String getMessageFromTree(Throwable e) {
		String msg = e.getMessage();
		if (StringUtils.isEmpty(msg)) {
			if (e.getCause() != null) {
				return getMessageFromTree(e.getCause());
			}
		}
		return msg;
	}

}
