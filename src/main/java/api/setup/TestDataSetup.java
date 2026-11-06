package api.setup;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import api.testdata.TestDataManager;
import utility.FrameworkLogger;

public class TestDataSetup {

    protected TestDataManager testDataManager;

    @BeforeSuite(alwaysRun = true)
    public void initializeTestData() {

        FrameworkLogger.info("Initializing shared test data...");

        testDataManager = TestDataManager.getInstance();

        testDataManager.initializeUser();

        FrameworkLogger.info("Shared test data initialized.");
    }

    @AfterSuite(alwaysRun = true)
    public void cleanupTestData() {

        FrameworkLogger.info("Cleaning up shared test data...");

        if (testDataManager != null) {
            testDataManager.cleanupUser();
        }

        FrameworkLogger.info("Shared test data cleanup completed.");
    }
}