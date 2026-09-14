package utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties =
            new Properties();

    private static final String CONFIG_FILE =
            "environmentvariables/config.properties";

    static {

        try (InputStream inputStream =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream(CONFIG_FILE)) {

            if (inputStream == null) {

                throw new RuntimeException(
                        "Configuration file not found in classpath: "
                                + CONFIG_FILE
                );
            }

            properties.load(inputStream);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to load configuration file: "
                            + CONFIG_FILE,
                    e
            );
        }
    }

    public static String getProperty(String key) {

        String value = properties.getProperty(key);

        if (value == null || value.trim().isEmpty()) {

            throw new RuntimeException(
                    "Configuration property is missing or empty: "
                            + key
            );
        }

        return value.trim();
    }
}

