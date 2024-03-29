package anvilrium.common;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileUtils {

	public static InputStream getFileFromResourceAsStream(String filePath) throws FileNotFoundException {

		Class<?> classLoader = FileUtils.class;
		InputStream inputStream = classLoader.getResourceAsStream(filePath);

		if (inputStream == null) {
			throw new FileNotFoundException("File " + filePath + " not found! " + filePath);
		} else {
			return inputStream;
		}

	}

}
