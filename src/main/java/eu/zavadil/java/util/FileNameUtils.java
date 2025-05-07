package eu.zavadil.java.util;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Various file name helpers.
 */
public class FileNameUtils {

	public static String slugify(String name) {
		return StringUtils.safeTrim(
			StringUtils.safeReplace(name, "_", "-")
		).replaceAll("\\W+", "-");
	}

	public static String extractDirectory(Path path) {
		if (path == null) return "";
		if (Files.isDirectory(path)) return path.toString();
		Path parent = path.getParent();
		if (parent == null) return "";
		return parent.toString();
	}

	public static String extractDirectory(String fileOrPath) {
		if (fileOrPath == null) return "";
		return extractDirectory(Path.of(fileOrPath));
	}

	public static String extractFileName(Path path) {
		return path.getFileName().toString();
	}

	public static String extractFileName(String path) {
		if (path == null) return "";
		return extractFileName(Path.of(path));
	}

	public static String extractExtension(Path path) {
		String fileName = extractFileName(path);
		final int i = fileName.lastIndexOf('.');
		return i > 0 ? fileName.substring(i + 1) : "";
	}

	public static String extractExtension(String fileOrPath) {
		if (fileOrPath == null) return "";
		return extractExtension(Path.of(fileOrPath));
	}

	public static String extractBaseName(Path path) {
		String fileName = extractFileName(path);
		final int i = fileName.lastIndexOf('.');
		return i > 0 ? fileName.substring(0, i) : fileName;
	}

	public static String extractBaseName(String fileOrPath) {
		return extractBaseName(Path.of(fileOrPath));
	}

	public static Path changeExtension(Path path, String extension) {
		return Path.of(extractDirectory(path), String.format("%s.%s", extractBaseName(path), extension));
	}

	public static String changeExtension(String fileOrPath, String extension) {
		return changeExtension(Path.of(fileOrPath), extension).toString();
	}

	public static Path changeBaseName(Path path, String base) {
		return Path.of(extractDirectory(path), String.format("%s.%s", base, extractExtension(path)));
	}

	public static String changeBaseName(String fileOrPath, String extension) {
		return changeBaseName(Path.of(fileOrPath), extension).toString();
	}
}
