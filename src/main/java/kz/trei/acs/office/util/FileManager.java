package kz.trei.acs.office.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * Utility class for File methods
 * 
 * @author Alexandr Koryagin
 * @date 20140308
 */
public final class FileManager {
	private static final Logger LOGGER = Logger.getLogger(FileManager.class);

	private FileManager() {
	}

	public static InputStream getResourceAsStream(String name) {
		return FileManager.class.getClassLoader().getResourceAsStream(name);
	}

	public static String readFile(String fileName) {
		StringBuilder content = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					getResourceAsStream(fileName)));
		} catch (Exception e) {
			LOGGER.error("File " + fileName + " reading error " + e);
		}
		if (bufferedReader != null) {
			try {
				String line = bufferedReader.readLine();
				while (line != null) {
					content.append(line).append("\n");
					line = bufferedReader.readLine();
				}
			} catch (IOException e) {
				LOGGER.error("Reading Line Error " + e);
			} finally {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					LOGGER.error("File Close Error " + e);
				}
			}
		}
		return content.toString();
	}

	public static void writeFile(String fileName, String content) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter writer = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(content);
			bufferedWriter.close();
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}
}
