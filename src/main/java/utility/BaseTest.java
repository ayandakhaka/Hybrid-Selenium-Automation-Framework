package utility;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.*;

import api.model.UserModel;
import api.services.UserApiService;

import java.time.Duration;

public class BaseTest {

	protected WebDriver driver;
	protected UserModel user;

	@BeforeMethod
	public void setup() {

		//Create a new user through API
		user = UserApiService.registerRandomUser();

		//driver = DriverFactory.createDriver();

		System.setProperty(
				"webdriver.edge.driver",
				"C:\\Webdriver\\msedgedriver.exe"
				);

		EdgeOptions options = new EdgeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.EAGER);

		driver = new EdgeDriver(options);

		driver.manage().window().maximize();

		driver.manage().timeouts()
		.pageLoadTimeout(Duration.ofSeconds(
				Integer.parseInt(
						ConfigReader.getProperty("waitForPageToLoad"))));

		driver.manage().timeouts()
		.implicitlyWait(Duration.ofSeconds(
				Integer.parseInt(ConfigReader.getProperty("explicitWaitTimeout"))));

		FrameworkLogger.info("User navigates to the automation exercise home page");
		driver.get(ConfigReader.getProperty("url"));




		//
		//		driver.manage().window().maximize();
		//		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
		//		driver.manage().timeouts()
		//		.implicitlyWait(Duration.ofSeconds(10));
		//
		//		driver.get(ConfigReader.getProperty("url"));
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {

		if(driver != null) {
			driver.quit();
		}
	}
}