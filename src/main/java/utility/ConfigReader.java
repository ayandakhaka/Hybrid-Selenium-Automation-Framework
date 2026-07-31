package utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        properties = new Properties();

        try (InputStream inputStream =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream("environmentvariables/config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException(
                    "config.properties not found in classpath: " +
                    "environmentvariables/config.properties"
                );
            }

            properties.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException(
                "Failed to load config.properties",
                e
            );
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}