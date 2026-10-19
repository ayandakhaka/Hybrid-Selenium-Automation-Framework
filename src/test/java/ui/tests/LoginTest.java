package ui.tests;

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
		
		// Arrange
		homePage.clickSignLoginButton();
		
		// Act
		loginPage.loginToAutomationExercisePage(user.getEmail(), user.getPassword());
		
		// Assert
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
		
		// Arrange
		homePage.clickSignLoginButton();
		
		// Act
		loginPage.loginToAutomationExercisePage(ConfigReader.getProperty("uiInvalidEmail"), user.getPassword());
		
		// Assert
		Assert.assertEquals(loginPage.getEmailValidationMessage() , ConfigReader.getProperty("emailValidationMessage") 
				+ " '" + ConfigReader.getProperty("uiInvalidEmail") + "' is missing an '@'." ,
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
		
		// Arrange
		homePage.clickSignLoginButton();
		
		// Act
		loginPage.loginToAutomationExercisePage(user.getEmail(), ConfigReader.getProperty("uiInvalidPassword"));
		
		// Assert
		
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
		
		// Arrange
		homePage.clickSignLoginButton();
		// Login to automation exercise web site
		loginPage.loginToAutomationExercisePage(user.getEmail(), user.getPassword());
		
		// Act
		logoutPage.clickLogout();
		
		// Assert
		Assert.assertTrue(loginPage.validateLoginFormHeaderText(), "Failed to validate user logged out.");
		
		FrameworkLogger.testEnd("verifyLogout");
	}

}
