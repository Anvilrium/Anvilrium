package anvilrium.common;

import java.io.InputStream;

public class FileUtils {

	public static InputStream getFileFromResourceAsStream(String filePath) {

		Class<?> classLoader = FileUtils.class;
		InputStream inputStream = classLoader.getResourceAsStream(filePath);

		if (inputStream == null) {
			throw new IllegalArgumentException("file " + filePath + " not found! " + filePath);
		} else {
			return inputStream;
		}

	}

}
