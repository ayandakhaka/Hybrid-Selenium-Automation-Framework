package utility;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import api.model.UserModel;
import api.services.UserApiService;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import java.io.ByteArrayInputStream;

public class BaseTest {

	protected WebDriver driver;
	protected UserModel user;
	protected ActionHelper action;
	
	public BaseTest() {
		action = new ActionHelper(getDriver());
	}
	
	@BeforeSuite
	@Step("Create an allure enviroment")
    public void createAllureEnvironment() {

        AllureEnvironment.createEnvironmentFile();
    }

	@BeforeMethod
	@Step("Create a random user, driver and launches the url")
	public void setup() {

		// This create a new random user.
		user = UserApiService.registerRandomUser();

		// This is logging to the console.
		FrameworkLogger.info("Created user: " + user.getEmail());

		// This is creating webdriver
		driver = DriverFactory.createDriver();

//		driver.manage().timeouts().pageLoadTimeout(
//				Duration.ofSeconds(
//						Integer.parseInt(ConfigReader.getProperty("waitForPageToLoad"))
//						));
		// This ensures that the page is loaded.
		action.waitForPageLoad();

//		driver.manage().timeouts().implicitlyWait(
//				Duration.ofSeconds(
//						Integer.parseInt(ConfigReader.getProperty("implicitWait"))
//						));
		// This navigate to the automation exercise home page
		action.navigateTo(ConfigReader.getProperty("url"));

	}

	// This returns the current driver.
	public WebDriver getDriver() {
		return driver;
	}

	@AfterMethod(alwaysRun = true)
	@Step("Take a screenshot and quit the browser")
	public void tearDown(ITestResult result) {

	    if (result.getStatus() == ITestResult.FAILURE && driver != null) {

	        try {

	            // 1. Capture Screenshot
	            byte[] screenshot = ((TakesScreenshot) driver)
	                    .getScreenshotAs(OutputType.BYTES);

	            Allure.addAttachment(
	                    "Failure Screenshot",
	                    "image/png",
	                    new ByteArrayInputStream(screenshot),
	                    ".png"
	            );

	            // 2. Capture Page Source
	            //String pageSource = driver.getPageSource();
	            String pageSource = action.getPageSource();

	            Allure.addAttachment(
	                    "Page Source",
	                    "text/html",
	                    pageSource
	            );

	            // 3. Capture Current URL
	            String currentUrl = action.getCurrentUrl();

	            Allure.addAttachment(
	                    "Current URL",
	                    "text/plain",
	                    currentUrl
	            );

	            // 4. Capture Exception
	            if (result.getThrowable() != null) {

	                String exceptionMessage =
	                        result.getThrowable().toString();

	                Allure.addAttachment(
	                        "Exception Details",
	                        "text/plain",
	                        exceptionMessage
	                );
	            }

	        } catch (Exception e) {

	            FrameworkLogger.error(
	                    "Failed to capture failure diagnostics: "
	                            + e.getMessage()
	            );
	        }
	    }

	    // Always close browser
	    if (driver != null) {
	        driver.quit();
	    }
	}
}