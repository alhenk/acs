package kz.trei.acs.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyManager {
    public static final Properties properties = new Properties();
    private static final Logger LOGGER = Logger
            .getLogger(PropertyManager.class);

    private PropertyManager() {
    }

    public static InputStream getResourceAsStream(String fileName) {
        return FileManager.class.getClassLoader().getResourceAsStream(fileName);
    }

    public static void load(String fileName) {
        try {
            properties.load(getResourceAsStream(fileName));
        } catch (IOException e) {
            LOGGER.error("Reading " + fileName + " error" + e);
        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }
}
