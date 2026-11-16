package config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import utility.FrameworkLogger;
public class AllureEnvironment {

	private static Properties properties;
	private static File directory;

	public static void createEnvironmentFile(WebDriver driver) {

		properties = new Properties();

		// Get browser capabilities
		Capabilities capabilities =
				((RemoteWebDriver) driver).getCapabilities();

		// Test environment
		properties.setProperty(
				"Test Environment",
				EnvironmentManager.getEnvironment()
				);

		// Browser information
		properties.setProperty(
				"Browser",
				capabilities.getBrowserName()
				);

		properties.setProperty(
				"Configured Browser",
				EnvironmentManager.getBrowser()
				);
		
		properties.setProperty(
			    "Execution Mode",
			    System.getProperty(
			        "execution",
			        System.getenv("CI") != null ? "CI/CD" : "Local"
			    ));

		properties.setProperty(
				"Build Number",
				System.getProperty("build.number", "Local")
				);

		properties.setProperty(
				"Browser Version",
				capabilities.getBrowserVersion()
				);

		// Operating system
		properties.setProperty(
				"Operating System",
				System.getProperty("os.name")
				);
		// OS
		properties.setProperty(
				"OS Version",
				System.getProperty("os.version")
				);

		// Java
		properties.setProperty(
				"Java Version",
				System.getProperty("java.version")
				);

		// UI Base URL
		properties.setProperty(
				"UI Base URL",
				EnvironmentManager.getUiBaseUrl()
				);
		// API Base URL
		properties.setProperty(
				"API Base URL",
				EnvironmentManager.getApiBaseUrl()
				);

		// Execution mode
		properties.setProperty(
				"Execution Mode",
				System.getProperty(
						"execution",
						System.getenv("CI") != null ? "CI/CD" : "Local"
						)
				);

		// Create Allure results directory
		directory =
				new File("target/allure-results");

		if (!directory.exists()) {
			directory.mkdirs();
		}

		File file =
				new File(
						directory,
						"environment.properties"
						);

		try (FileWriter writer =
				new FileWriter(file)) {

			properties.store(
					writer,
					"Allure Test Environment Information"
					);

			FrameworkLogger.info(
					"Allure environment.properties created successfully."
					);

		} catch (IOException e) {

			FrameworkLogger.error(
					"Failed to create Allure environment.properties: "
							+ e.getMessage()
					);

			throw new RuntimeException(
					"Failed to create Allure environment.properties",
					e
					);
		}
	}


	public static void createEnvironmentFile() {

		properties = new Properties();

		// Operating system
		properties.setProperty(
				"Operating System",
				System.getProperty("os.name")
				);

		properties.setProperty(
				"OS Version",
				System.getProperty("os.version")
				);

		properties.setProperty(
				"Build Number",
				System.getProperty("build.number", "Local")
				);

		// Java
		properties.setProperty(
				"Java Version",
				System.getProperty("java.version")
				);

		// Test environment
		properties.setProperty(
				"Test Environment",
				EnvironmentManager.getEnvironment()
				);

		properties.setProperty(
				"UI Base URL",
				EnvironmentManager.getUiBaseUrl()
				);

		properties.setProperty(
				"API Base URL",
				EnvironmentManager.getApiBaseUrl()
				);

		// Execution mode
		properties.setProperty(
				"Execution Mode",
				System.getProperty(
						"execution",
						System.getenv("CI") != null ? "CI/CD" : "Local"
						)
				);

		// Test suite
		properties.setProperty(
				"Test Suite",
				System.getProperty(
						"suite",
						"API"
						)
				);

		// Create Allure results directory
		directory =
				new File("target/allure-results");

		if (!directory.exists()) {
			directory.mkdirs();
		}

		File file =
				new File(
						directory,
						"environment.properties"
						);

		try (FileWriter writer =
				new FileWriter(file)) {

			properties.store(
					writer,
					"Allure Test Environment Information"
					);

			FrameworkLogger.info(
					"Allure API/CI environment.properties created successfully."
					);

		} catch (IOException e) {

			FrameworkLogger.error(
					"Failed to create Allure environment.properties: "
							+ e.getMessage()
					);

			throw new RuntimeException(
					"Failed to create Allure environment.properties",
					e
					);
		}
	}
}