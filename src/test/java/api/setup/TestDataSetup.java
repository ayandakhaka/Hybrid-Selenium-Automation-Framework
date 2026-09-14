package api.setup;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import api.testdata.TestDataManager;
import utility.AllureEnvironment;
import utility.ConfigReader;
import utility.FrameworkLogger;

public class TestDataSetup {

    @BeforeSuite
    public void initializeTestData() {

        System.setProperty(
                ConfigReader.getProperty("systemPropertyName"),
                ConfigReader.getProperty("systemPropertyValue")
        );

        FrameworkLogger.info("========== CREATE ENVIRONMENT FILE ==========");
        AllureEnvironment.createEnvironmentFile();

        FrameworkLogger.info("========== TEST SUITE SETUP ==========");

        TestDataManager.getInstance().initializeUser();

        FrameworkLogger.info("========== TEST USER READY ==========");
    }

    @AfterSuite(alwaysRun = true)
    public void cleanupTestData() {

        FrameworkLogger.info("========== TEST SUITE CLEANUP ==========");

        TestDataManager.getInstance().cleanupUser();

        FrameworkLogger.info("========== TEST DATA CLEANED ==========");
    }
}