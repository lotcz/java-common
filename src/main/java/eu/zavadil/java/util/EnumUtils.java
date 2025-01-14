package eu.zavadil.java.util;

import java.util.Arrays;
import java.util.List;

public class EnumUtils {

	public static List<String> namesOf(Class<? extends Enum<?>> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).map(Enum::name).toList();
	}
}
