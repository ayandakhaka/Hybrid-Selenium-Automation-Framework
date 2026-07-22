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

import java.time.Duration;

public class BaseTest {

	protected WebDriver driver;
	protected UserModel user;
	
	@BeforeSuite
	@Step("Create an allure enviroment")
    public void createAllureEnvironment() {

        AllureEnvironment.createEnvironmentFile();
    }

	@BeforeMethod
	@Step("Create a random user, driver and launches the url")
	public void setup() {

		user = UserApiService.registerRandomUser();

		FrameworkLogger.info("Created user: " + user.getEmail());

		driver = DriverFactory.createDriver();

		driver.manage().timeouts().pageLoadTimeout(
				Duration.ofSeconds(
						Integer.parseInt(ConfigReader.getProperty("waitForPageToLoad"))
						));

		driver.manage().timeouts().implicitlyWait(
				Duration.ofSeconds(
						Integer.parseInt(ConfigReader.getProperty("implicitWait"))
						));
		driver.get(ConfigReader.getProperty("url"));

	}

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
	            String pageSource = driver.getPageSource();

	            Allure.addAttachment(
	                    "Page Source",
	                    "text/html",
	                    pageSource
	            );

	            // 3. Capture Current URL
	            String currentUrl = driver.getCurrentUrl();

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