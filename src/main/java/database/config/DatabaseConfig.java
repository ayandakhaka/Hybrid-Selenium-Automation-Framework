package database.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties properties = 
    		new Properties();
    
    private static final String CONFIG_FILE =
            "environmentvariables/config.properties";

    static {
        try (InputStream input = DatabaseConfig.class
                .getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            if (input == null) {
                throw new RuntimeException(
                    "config.properties not found in resources folder"
                );
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                "Failed to load database configuration",
                e
            );
        }
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getUsername() {
        return properties.getProperty("db.username");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }

    private DatabaseConfig() {
        // Prevent object creation
    }
}