package utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AllureEnvironment {

    public static void createEnvironmentFile() {

        String resultsDirectory =
                "target/allure-results";

        File directory =
                new File(resultsDirectory);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File environmentFile =
                new File(
                        directory,
                        "environment.properties"
                );

        try (FileWriter writer =
                     new FileWriter(environmentFile)) {

            writer.write(
                    "Environment=QA\n"
            );

            writer.write(
                    "Browser="
                    + System.getProperty(
                            "browser",
                            ConfigReader.getProperty(
                                    "browser"
                            )
                    )
                    + "\n"
            );

            writer.write(
                    "Operating.System="
                    + System.getProperty(
                            "os.name"
                    )
                    + "\n"
            );

            writer.write(
                    "OS.Version="
                    + System.getProperty(
                            "os.version"
                    )
                    + "\n"
            );

            writer.write(
                    "Java.Version="
                    + System.getProperty(
                            "java.version"
                    )
                    + "\n"
            );

            writer.write(
                    "Test.Framework=TestNG\n"
            );

            writer.write(
                    "Selenium.Version=4.43.0\n"
            );

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}