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
import pages.HomePage;
import pages.LoginPage;
import pages.LogoutPage;
import api.testdata.TestDataManager;

import java.io.ByteArrayInputStream;

public class BaseTest extends TestDataSetup {

	protected WebDriver driver;
	protected UserModel user;
	
//	protected HomePage homePage;
//	protected LoginPage loginPage;
//	protected LogoutPage logoutPage;
	protected PageManager pages;
	
	protected ActionHelper action;
	protected PopupHandler popupHandler;
	
	@BeforeMethod(alwaysRun = true)
	@Step("Create driver and launch the application")
	public void setup() {

	    FrameworkLogger.info("========== BASETEST SETUP STARTED ==========");

	    FrameworkLogger.clearTestLogs();

	    user = TestDataManager.getInstance().getUser();

	    FrameworkLogger.info("Shared user retrieved successfully.");

	    DriverFactory.createDriver();

	    driver = DriverFactory.getDriver();

	    FrameworkLogger.info("WebDriver initialized: " + (driver != null));

	    // Initialize page objects
	   pages = new PageManager(driver);
	   
	   FrameworkLogger.info(pages + " : initialized");

	    popupHandler = new PopupHandler(driver);
	    action = new ActionHelper(driver);

	    action.navigateTo(ConfigReader.getProperty("url"));

	    action.waitForPageLoad();

	    FrameworkLogger.info("========== BASETEST SETUP COMPLETED ==========");
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