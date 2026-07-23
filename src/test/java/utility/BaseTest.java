package utility;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import api.model.UserModel;
import api.services.UserApiService;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.io.ByteArrayInputStream;

public class BaseTest {

	protected WebDriver driver;
	protected UserModel user;
	protected ActionHelper action;

	@BeforeMethod
	@Step("Create a random user, driver and launch the URL")
	public void setup() {

	    // Create random user
	    user = UserApiService.registerRandomUser();

	    FrameworkLogger.info(
	            "Created user: " + user.getEmail()
	    );

	    // Create thread-safe WebDriver
	    DriverFactory.createDriver();

	    // Get driver for current thread
	    driver = DriverFactory.getDriver();

	    // Create ActionHelper using current thread driver
	    action = new ActionHelper(driver);

	    // Create Allure environment
	    AllureEnvironment.createEnvironmentFile(
	            driver
	    );

	    // Wait for page load
	    action.waitForPageLoad();

	    // Navigate to application
	    action.navigateTo(
	            ConfigReader.getProperty("url")
	    );
	}
	
	// Returns the current driver
	public WebDriver getDriver() {
		return driver;
	}

	@AfterMethod(alwaysRun = true)
	@Step("Take screenshot and quit the browser")
	public void tearDown(ITestResult result) {

	    WebDriver currentDriver =
	            DriverFactory.getDriver();

	    if (result.getStatus() == ITestResult.FAILURE
	            && currentDriver != null) {

	        try {

	            byte[] screenshot =
	                    ((TakesScreenshot) currentDriver)
	                            .getScreenshotAs(
	                                    OutputType.BYTES
	                            );

	            Allure.addAttachment(
	                    "Failure Screenshot",
	                    "image/png",
	                    new ByteArrayInputStream(
	                            screenshot
	                    ),
	                    ".png"
	            );

	            String pageSource =
	                    currentDriver.getPageSource();

	            Allure.addAttachment(
	                    "Page Source",
	                    "text/html",
	                    pageSource
	            );

	            String currentUrl =
	                    currentDriver.getCurrentUrl();

	            Allure.addAttachment(
	                    "Current URL",
	                    "text/plain",
	                    currentUrl
	            );

	        } catch (Exception e) {

	            FrameworkLogger.error(
	                    "Failed to capture failure diagnostics: "
	                            + e.getMessage()
	            );
	        }
	    }

	    // Quit current thread's driver
	    DriverFactory.quitDriver();
	}
}