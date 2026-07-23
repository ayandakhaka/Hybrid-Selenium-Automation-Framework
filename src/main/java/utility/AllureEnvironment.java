package utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AllureEnvironment {

    public static void createEnvironmentFile(WebDriver driver) {

        Properties properties = new Properties();

        // Get browser capabilities
        Capabilities capabilities =
                ((RemoteWebDriver) driver).getCapabilities();

        // Browser information
        properties.setProperty(
                "Browser",
                capabilities.getBrowserName()
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

        properties.setProperty(
                "OS Version",
                System.getProperty("os.version")
        );

        // Java
        properties.setProperty(
                "Java Version",
                System.getProperty("java.version")
        );

        // Test environment
        properties.setProperty(
                "Test Environment",
                System.getProperty(
                        "environment",
                        "QA"
                )
        );

        // Execution mode
        properties.setProperty(
                "Execution Mode",
                System.getProperty(
                        "execution",
                        "local"
                )
        );

        // Create Allure results directory
        File directory =
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
}