package eu.zavadil.java.util;

import lombok.NonNull;

import java.io.File;
import java.nio.file.Path;

public class FileUtils {

	public static void deleteDirectory(@NonNull File file) {
		File[] files = file.listFiles();
		if (files != null) {
			for (File subfile : files) {
				if (subfile.isDirectory()) {
					deleteDirectory(subfile);
				}
				subfile.delete();
			}
		}
		file.delete();
	}

	public static void delete(File file) {
		if (file != null) deleteDirectory(file);
	}

	public static void delete(String path) {
		deleteDirectory(new File(path));
	}

	public static void delete(Path path) {
		if (path != null) deleteDirectory(path.getFileName().toFile());
	}



}
