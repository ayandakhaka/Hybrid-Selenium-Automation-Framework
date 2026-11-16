
package config;

import java.util.Locale;
import java.util.Properties;

import utility.ConfigReader;
import utility.FrameworkLogger;

public class EnvironmentManager {

    // Single source of truth for selected environment
    private static final String ENVIRONMENT = resolveEnvironment();

    private static final Properties PROPERTIES = loadProperties();

    private static final EnvironmentConfig CONFIG = buildConfiguration();

    private EnvironmentManager() {
        // Prevent object creation
    }

    // Step 1: Resolve environment
    private static String resolveEnvironment() {

        String environment = System.getProperty("environment");

        // Fall back to configuration if no system property exists
        if (environment == null || environment.trim().isEmpty()) {
            environment = ConfigReader.getProperty("environment");
        }

        // Default environment
        if (environment == null || environment.trim().isEmpty()) {
            environment = "QA";
        }

        return environment.trim().toUpperCase(Locale.ROOT);
    }

    // Step 2: Load configuration
    private static Properties loadProperties() {

        Properties properties = new Properties();

        properties.setProperty("environment", ENVIRONMENT);

        properties.setProperty(
            "ui.base.url",
            ConfigReader.getProperty("ui.base.url")
        );

        properties.setProperty(
            "api.base.url",
            ConfigReader.getProperty("api.base.url")
        );

        properties.setProperty(
            "browser",
            ConfigReader.getProperty("browser")
        );

        properties.setProperty(
            "browser.headless",
            ConfigReader.getProperty("browser.headless")
        );

        FrameworkLogger.info(
            "Environment configuration loaded: " + ENVIRONMENT
        );

        return properties;
    }

    // Step 3: Build EnvironmentConfig
    private static EnvironmentConfig buildConfiguration() {

        String environment = PROPERTIES.getProperty("environment");

        String uiBaseUrl = PROPERTIES.getProperty("ui.base.url");

        String apiBaseUrl = PROPERTIES.getProperty("api.base.url");

        String browser = PROPERTIES.getProperty("browser", "chrome");

        boolean headless = Boolean.parseBoolean(
            PROPERTIES.getProperty("browser.headless", "false")
        );

        if (uiBaseUrl == null || uiBaseUrl.trim().isEmpty()) {
            throw new IllegalStateException(
                "UI Base URL is missing from configuration."
            );
        }

        if (apiBaseUrl == null || apiBaseUrl.trim().isEmpty()) {
            throw new IllegalStateException(
                "API Base URL is missing from configuration."
            );
        }

        return new EnvironmentConfig(
            environment,
            uiBaseUrl,
            apiBaseUrl,
            browser,
            headless
        );
    }

    // Step 4: Expose configuration

    public static EnvironmentConfig getConfig() {
        return CONFIG;
    }

    public static String getEnvironment() {
        return CONFIG.getEnvironment();
    }

    public static String getUiBaseUrl() {
        return CONFIG.getUiBaseUrl();
    }

    public static String getApiBaseUrl() {
        return CONFIG.getApiBaseUrl();
    }

    public static String getBrowser() {
        return CONFIG.getBrowser();
    }

    public static boolean isHeadless() {
        return CONFIG.isHeadless();
    }
}
