package ui.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import utility.BaseTest;
import utility.ConfigReader;
import utility.FrameworkLogger;


@Epic("Automation exercise")
@Feature("User authentication Tests")
public class LoginTest extends BaseTest {

	@Test(priority = 1, groups = {"smoke", "regression"})
	@Owner("Ayanda Khaka")
	@Story("Registered user login to the application")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that registered user login successfully.")
	public void verifyUserCanLoginSuccessfully() {

		FrameworkLogger.testStart("verifyUserCanLoginSuccessfully");

		// Arrange
		pages.getHomePage().clickSignLoginButton();

		// Act
		pages
		.getLoginPage()
		.loginToAutomationExercisePage(
				user.getEmail(),
				user.getPassword()
				);

		// Assert
		Assert.assertTrue(pages
				.getLoginPage()
				.verifySuccessLoginUser(
						user.getName()
						),
				"Failed to validate user logged in.");

		FrameworkLogger.testEnd("verifyUserCanLoginSuccessfully");		
	}

	@Test(priority = 2, groups = "regression")
	@Owner("Ayanda Khaka")
	@Story("User login with invalid email")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that user is unable to login with invalid format email.")
	public void verifyLoginWithInvalidEmailFormat() {

		FrameworkLogger.testStart("verifyLoginWithInvalidEmail");

		// Arrange
		pages
		.getHomePage()
		.clickSignLoginButton();

		// Act
		pages
		.getLoginPage()
		.loginToAutomationExercisePage(
				ConfigReader.getProperty("uiInvalidEmail")
				, user.getPassword()
				);

		// Assert
		Assert.assertEquals(pages
				.getLoginPage()
				.getEmailValidationMessage(),
				ConfigReader.getProperty("emailValidationMessage") +
				" '" + ConfigReader.getProperty("uiInvalidEmail") +
				"' is missing an '@'." ,
				"Failed to validate email message validation");

		FrameworkLogger.testEnd("verifyLoginWithInvalidEmail");

	}

	@Test(priority = 3, groups = "regression")
	@Owner("Ayanda Khaka")
	@Story("User login with invalid password")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that user is unable to login with invalid password.")
	public void verifyLoginWithInvalidPassword() {

		FrameworkLogger.testStart("verifyLoginWithInvalidPassword");

		// Arrange
		pages
		.getHomePage()
		.clickSignLoginButton();

		// Act
		pages
		.getLoginPage()
		.loginToAutomationExercisePage(user.getEmail()
				, ConfigReader.getProperty("uiInvalidPassword"));

		// Assert

		Assert.assertTrue(pages
				.getLoginPage()
				.validateLoginErrorMessage(), 
				"Failed to validate login error message.");

		FrameworkLogger.testEnd("verifyLoginWithInvalidPassword");

	}

	@Test(priority = 4, groups = {"smoke", "regression"})
	@Owner("Ayanda Khaka")
	@Story("Loggedin user logout from the application")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that loggedin user logout successfully from the application.")
	public void verifyLogout() {

		FrameworkLogger.testStart("verifyLogout");

		// Arrange
		pages
		.getHomePage()
		.clickSignLoginButton();
		pages
		.getLoginPage()
		.loginToAutomationExercisePage(
				user.getEmail(),
				user.getPassword());

		// Act
		pages
		.getLogout()
		.clickLogout();

		// Assert
		Assert.assertTrue(pages
				.getLoginPage()
				.validateLoginFormHeaderText(),
				"Failed to validate user logged out.");

		FrameworkLogger.testEnd("verifyLogout");
	}

}
