package eu.zavadil.java.util;

import java.util.*;

public abstract class CollectionUtils {

	public static boolean isEmpty(Collection collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean notEmpty(Collection collection) {
		return !isEmpty(collection);
	}

}
