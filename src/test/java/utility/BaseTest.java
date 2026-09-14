package utility;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import api.model.UserModel;
import api.setup.TestDataSetup;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.io.ByteArrayInputStream;

public class BaseTest extends TestDataSetup {

	protected WebDriver driver;
	protected UserModel user;
	protected ActionHelper action;

	@BeforeMethod
	@Step("Create driver and launch the application")
	public void setup() {

		// Clear the logs
		FrameworkLogger.clearTestLogs();

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

		// Navigate to application
		action.navigateTo(
				ConfigReader.getProperty("url")
				);

		FrameworkLogger.info("Current URL before wait: " + driver.getCurrentUrl());
		FrameworkLogger.info("Page title before wait: " + driver.getTitle());
		// Wait for page load
		action.waitForPageLoad();
		
		FrameworkLogger.info("Page loaded successfully.");
		FrameworkLogger.info("Current URL after wait: " + driver.getCurrentUrl());
		FrameworkLogger.info("Page title after wait: " + driver.getTitle());

		String currentTitle = driver.getTitle();
		String currentUrl = driver.getCurrentUrl();

		FrameworkLogger.info("Current URL: " + currentUrl);
		FrameworkLogger.info("Page Title: " + currentTitle);

		if (currentTitle.equalsIgnoreCase("One moment, please...")) {

			throw new IllegalStateException(
					"Application is blocked by a verification page. " +
							"Expected the Automation Exercise homepage but received: "
							+ currentTitle
					);

		}
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

		// Attach execution log to Allure
		AllureAttachment.attachExecutionLog();
	}
}