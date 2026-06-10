package eu.zavadil.java.util;

public class EmailUtils {

	public static boolean isValidEmail(String email) {
		if (StringUtils.isBlank(email)) return false;
		return email.matches("^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~.-]{1,64}@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}$");
	}
}
