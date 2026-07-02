package ui;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import api.helpers.UserDataHelper;
import api.model.UserModel;
import pages.HomePage;
import pages.LoginPage;
import utility.BaseTest;
import utility.ConfigReader;
import utility.FrameworkLogger;

public class LoginTest extends BaseTest{

	private HomePage homePage;
	private LoginPage loginPage;	

	@BeforeMethod
	public void setUpPages() {

		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		//user = UserDataHelper.readUserData();
	}

	@Test(priority = 1)
	public void verifyUserCanLoginSuccessfully() {

		FrameworkLogger.testStart("verifyUserCanLoginSuccessfully");

		FrameworkLogger.info("User navigate to the login page");

		homePage.clickSignLoginButton();

		FrameworkLogger.info("User enters valid email address, password and click login button");

		loginPage.loginToAutomationExercisePage(user.getEmail(), user.getPassword());

		FrameworkLogger.info("Validating success user logged in");

		Assert.assertTrue(loginPage.verifySuccessLoginUser(user.getName()), "Failed to validate user logged in.");

		FrameworkLogger.testEnd("verifyUserCanLoginSuccessfully");
	}
	
	@Test(priority = 2)
	public void verifyLoginWithInvalidEmailFormat() {

		FrameworkLogger.testStart("verifyLoginWithInvalidEmail");

		homePage.clickSignLoginButton();

		FrameworkLogger.info("User enters invalid email address, valid password and click login button");

		loginPage.loginToAutomationExercisePage(ConfigReader.getProperty("invalidEmail"), user.getPassword());
		
		FrameworkLogger.info("Validate email validation message");
		
		Assert.assertEquals(loginPage.getEmailValidationMessage() , ConfigReader.getProperty("emailValidationMessage") 
				+ " '" + ConfigReader.getProperty("invalidEmail") + "' is missing an '@'." , "Failed to validate email message validation");
		
		FrameworkLogger.testEnd("verifyLoginWithInvalidEmail");

	}
	@Test(priority = 3)
	public void verifyLoginWithInvalidPassword() {
		
		FrameworkLogger.testStart("verifyLoginWithInvalidPassword");

		homePage.clickSignLoginButton();

		FrameworkLogger.info("User enters valid email address, invalid password and click login button");

		loginPage.loginToAutomationExercisePage(user.getEmail(), ConfigReader.getProperty("invalidPassword"));
		
		FrameworkLogger.info("Validate login error message");
		
		Assert.assertTrue(loginPage.validateLoginErrorMessage(), "Failed to validate login error message.");
		
		FrameworkLogger.testEnd("verifyLoginWithInvalidPassword");
		
	}

}
