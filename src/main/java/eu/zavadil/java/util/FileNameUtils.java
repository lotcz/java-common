package eu.zavadil.java.util;

import java.nio.file.Path;

/**
 * Various file name helpers.
 */
public class FileNameUtils {

	public static String extractFileName(Path path) {
		return path.getFileName().toString();
	}

	public static String extractFileName(String path) {
		if (path == null) return "";
		return extractFileName(Path.of(path));
	}

	public static String extractExtension(String fileOrPath) {
		String fileName = extractFileName(fileOrPath);
		final int i = fileName.lastIndexOf('.');
		return i > 0 ? fileName.substring(i + 1) : "";
	}

	public static String extractBaseName(String fileOrPath) {
		String fileName = extractFileName(fileOrPath);
		final int i = fileName.lastIndexOf('.');
		return i > 0 ? fileName.substring(0, i) : fileName;
	}
}
