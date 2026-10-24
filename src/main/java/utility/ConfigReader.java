package utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties properties =
            new Properties();

    private static final String CONFIG_FILE =
            "environmentvariables/config.properties";

    private static final String ENV_CONFIG_PATH =
            "environmentvariables/";

    private static final String ENV_PROPERTY = "env";

    static {
        loadConfiguration();
    }

    private ConfigReader() {
        // Utility class
    }

    private static void loadConfiguration() {

        // 1. Load shared configuration
        loadFile(CONFIG_FILE, true);

        // 2. Resolve the selected environment
        String environment = System.getProperty(
                ENV_PROPERTY, "dev"
        ).trim().toLowerCase(Locale.ROOT);

        if (!environment.matches("dev|qa|uat|prod")) {
            throw new IllegalArgumentException(
                    "Invalid environment: " + environment
                    + ". Allowed values: dev, qa, uat, prod"
            );
        }

        // 3. Override shared properties with environment values
        String environmentFile =
                ENV_CONFIG_PATH + environment + ".properties";

        loadFile(environmentFile, true);

        // 4. Store the resolved environment
        properties.setProperty("environment", environment);
               
        FrameworkLogger.info("Loaded configuration for environment: "
                + environment.toUpperCase(Locale.ROOT));
    }

    private static void loadFile(
            String fileName, boolean required) {

        try (InputStream inputStream =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream(fileName)) {

            if (inputStream == null) {

                if (required) {
                    throw new RuntimeException(
                            "Configuration file not found: "
                                    + fileName
                    );
                }

                return;
            }

            properties.load(inputStream);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to load configuration file: "
                            + fileName,
                    e
            );
        }
    }

    public static String getProperty(String key) {

        // JVM arguments take precedence over property files
        String value = System.getProperty(key);

        if (value == null || value.trim().isEmpty()) {
            value = properties.getProperty(key);
        }

        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException(
                    "Configuration property is missing or empty: "
                            + key
            );
        }

        return value.trim();
    }
}

