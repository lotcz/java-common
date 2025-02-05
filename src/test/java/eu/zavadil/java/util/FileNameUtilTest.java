package eu.zavadil.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FileNameUtilTest {

	@Test
	public void testFileNameUtil() {
		String fileNull = null;
		String file0 = "";
		String file1 = "file1.txt";
		String file2 = "C:\\test\\file2.txt";
		String file3 = "/test/file3.tnt";

		Assertions.assertEquals("", FileNameUtils.extractFileName(fileNull));
		Assertions.assertEquals("", FileNameUtils.extractFileName(file0));
		Assertions.assertEquals("file1.txt", FileNameUtils.extractFileName(file1));
		Assertions.assertEquals("file2.txt", FileNameUtils.extractFileName(file2));
		Assertions.assertEquals("file3.tnt", FileNameUtils.extractFileName(file3));

		Assertions.assertEquals("", FileNameUtils.extractExtension(fileNull));
		Assertions.assertEquals("", FileNameUtils.extractExtension(file0));
		Assertions.assertEquals("txt", FileNameUtils.extractExtension(file1));
		Assertions.assertEquals("txt", FileNameUtils.extractExtension(file2));
		Assertions.assertEquals("tnt", FileNameUtils.extractExtension(file3));

	}

}
