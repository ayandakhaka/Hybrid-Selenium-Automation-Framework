package ui;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pages.HomePage;
import pages.LoginPage;
import pages.LogoutPage;
import utility.BaseTest;
import utility.ConfigReader;
import utility.FrameworkLogger;


@Epic("Automation exercise")
@Feature("User authentication")
public class LoginTest extends BaseTest {

	private HomePage homePage;
	private LoginPage loginPage;
	private LogoutPage logoutPage;

	@BeforeMethod
	public void setUpPages() {

		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		logoutPage = new LogoutPage(driver);
	}

	@Test(priority = 1)
	@Owner("Ayanda Khaka")
	@Story("Registered user login to the application")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that registered user login successfully.")
	public void verifyUserCanLoginSuccessfully() {

		FrameworkLogger.testStart("verifyUserCanLoginSuccessfully");

		FrameworkLogger.info("User navigate to the login page");

		homePage.clickSignLoginButton();

		FrameworkLogger.info("User enters valid email address, password and click login button");

		loginPage.loginToAutomationExercisePage(user.getEmail(), user.getPassword());

		FrameworkLogger.info("Validating success user logged in");

		Assert.assertTrue(loginPage.verifySuccessLoginUser(user.getName()),
				"Failed to validate user logged in.");

		FrameworkLogger.testEnd("verifyUserCanLoginSuccessfully");
	}
	
	@Test(priority = 2)
	@Owner("Ayanda Khaka")
	@Story("User login with invalid email")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that user is unable to login with invalid format email.")
	public void verifyLoginWithInvalidEmailFormat() {

		FrameworkLogger.testStart("verifyLoginWithInvalidEmail");

		homePage.clickSignLoginButton();

		FrameworkLogger.info("User enters invalid email address, valid password and click login button");

		loginPage.loginToAutomationExercisePage(ConfigReader.getProperty("invalidEmail"), user.getPassword());
		
		FrameworkLogger.info("Validate email validation message");
		
		Assert.assertEquals(loginPage.getEmailValidationMessage() , ConfigReader.getProperty("emailValidationMessage") 
				+ " '" + ConfigReader.getProperty("invalidEmail") + "' is missing an '@'." ,
				"Failed to validate email message validation");
		
		FrameworkLogger.testEnd("verifyLoginWithInvalidEmail");

	}
	@Test(priority = 3)
	@Owner("Ayanda Khaka")
	@Story("User login with invalid password")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that user is unable to login with invalid password.")
	public void verifyLoginWithInvalidPassword() {
		
		FrameworkLogger.testStart("verifyLoginWithInvalidPassword");

		homePage.clickSignLoginButton();

		FrameworkLogger.info("User enters valid email address, invalid password and click login button");

		loginPage.loginToAutomationExercisePage(user.getEmail(), ConfigReader.getProperty("invalidPassword"));
		
		FrameworkLogger.info("Validate login error message");
		
		Assert.assertTrue(loginPage.validateLoginErrorMessage(), "Failed to validate login error message.");
		
		FrameworkLogger.testEnd("verifyLoginWithInvalidPassword");
		
	}
	
	@Test(priority = 4)
	@Owner("Ayanda Khaka")
	@Story("Loggedin user logout from the application")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that loggedin user logout successfully from the application.")
	public void verifyLogout() {
		
		FrameworkLogger.testStart("verifyLogout");
		
		homePage.clickSignLoginButton();

		FrameworkLogger.info("User enters valid email address, valid password and click login button");

		loginPage.loginToAutomationExercisePage(user.getEmail(), user.getPassword());
		
		FrameworkLogger.info("Validating success user logged in");

		Assert.assertTrue(loginPage.verifySuccessLoginUser(user.getName()), "Failed to validate user logged in.");
		
		FrameworkLogger.info("Clicking logout link");
		
		logoutPage.clickLogout();
		
		FrameworkLogger.info("Validating if user logged out successfully");
		
		Assert.assertTrue(loginPage.validateLoginFormHeaderText(), "Failed to validate user logged out.");
		
		FrameworkLogger.testEnd("verifyLogout");
	}

}
