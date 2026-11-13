
package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import utility.ConfigReader;
import utility.FrameworkLogger;

public class EnvironmentManager {

	private static final String ENVIRONMENT = System.getProperty("environment", "QA").trim().toUpperCase(Locale.ROOT);

	private static final Properties PROPERTIES = loadProperties();

	private static final EnvironmentConfig CONFIG = buildConfiguration();

	private EnvironmentManager() {
		// Prevent object creation
	}

	// Step 1: Load the selected environment properties file
	private static Properties loadProperties() {

		Properties properties = new Properties();

		properties.setProperty("environment", ConfigReader.getProperty("environment"));

		properties.setProperty("ui.base.url", ConfigReader.getProperty("ui.base.url"));

		properties.setProperty("api.base.url", ConfigReader.getProperty("api.base.url"));

		properties.setProperty("browser", ConfigReader.getProperty("browser"));

		properties.setProperty("browser.headless", ConfigReader.getProperty("browser.headless"));

		FrameworkLogger.info("Environment configuration loaded: " + properties.getProperty("environment"));

		return properties;
	}

	// Step 2: Build the EnvironmentConfig object
	private static EnvironmentConfig buildConfiguration() {

		String environment = PROPERTIES.getProperty("environment");

		String uiBaseUrl = PROPERTIES.getProperty("ui.base.url");

		String apiBaseUrl = PROPERTIES.getProperty("api.base.url");

		String browser = PROPERTIES.getProperty("browser", "chrome");

		boolean headless = Boolean.parseBoolean(PROPERTIES.getProperty("browser.headless", "false"));

		// Validate mandatory configuration
		if (uiBaseUrl == null || uiBaseUrl.trim().isEmpty()) {
			throw new IllegalStateException("UI Base URL is missing from configuration.");
		}

		if (apiBaseUrl == null || apiBaseUrl.trim().isEmpty()) {
			throw new IllegalStateException("API Base URL is missing from configuration.");
		}

		return new EnvironmentConfig(environment, uiBaseUrl, apiBaseUrl, browser, headless);
	}

	// Step 3: Expose configuration values to other classes

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
