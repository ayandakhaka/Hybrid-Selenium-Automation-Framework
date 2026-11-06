package ui.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.helpers.UserDataHelper;
import api.model.UserModel;
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
@Feature("Registration Tests")
public class RegisterTest extends BaseTest {

	@Test(priority = 1, groups = "regression")
	@Owner("Ayanda Khaka")
	@Story("Register new user")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that user navigate to signup page")
	public void verifyUserNavigateToSignupPage() {

		FrameworkLogger.testStart("verifyUserNavigateToSignupPage");

		// Arrange
		UserModel user = UserDataHelper.generateUserData();
		pages
		.getHomePage()
		.clickSignLoginButton();
		// Act
		pages
		.getLoginPage()
		.enterNewSignupDetails(
				user.getName(),
				user.getEmail());
		// Assert
		Assert.assertTrue(
				pages
				.getSignupPage()
				.validateEnterAccountInformationText(
						ConfigReader.getProperty("signupHeaderText")), 
				"Failed to validate enter account information text");

		FrameworkLogger.testEnd("verifyUserNavigateToSignupPage");

	}

	@Test(priority = 2, groups = "regression")
	@Owner("Ayanda Khaka")
	@Story("Register new user")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that user is navigated account created page")
	public void verifyAccountCreatedSuccessText() {

		FrameworkLogger.testStart("verifyAccountCreatedSuccessText");

		// Arrange
		UserModel user = UserDataHelper.generateUserData();
		pages
		.getHomePage()
		.clickSignLoginButton();
		pages
		.getLoginPage()
		.enterNewSignupDetails(
				user.getName(),
				user.getEmail());

		// Act
		pages
		.getSignupPage()
		.registerUser(
				user.getTitle(), 
				user.getPassword(), 
				user.getBirth_date(), 
				user.getBirth_month(), 
				user.getBirth_year(), 
				user.getFirstname(), 
				user.getLastname(), 
				user.getCompany(), 
				user.getAddress1(), 
				user.getAddress2(), 
				ConfigReader.getProperty("country"), 
				user.getState(), 
				user.getCity(), 
				user.getZipcode(), 
				user.getMobile_number());


		// Assert
		Assert.assertTrue(pages
				.getAccountCreatedPage()
				.validateAccountCreatedMessage(
						ConfigReader.getProperty("accountCreatedSuccessMessage")), 
				"Failed to verify account created message");

		FrameworkLogger.testEnd("verifyAccountCreatedSuccessText");

	}

	@Test(priority = 3, groups = "regression")
	@Owner("Ayanda Khaka")
	@Story("Register new user")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that user is registered succesfully")
	public void verifyUserRgistersSuccessfully() {

		FrameworkLogger.testStart("verifyUserRgistersSuccessfully");

		// Arrange
		UserModel user = UserDataHelper.generateUserData();
		pages
		.getHomePage()
		.clickSignLoginButton();
		pages
		.getLoginPage()
		.enterNewSignupDetails(
				user.getName(), 
				user.getEmail());

		pages
		.getSignupPage()
		.registerUser(
				user.getTitle(), 
				user.getPassword(), 
				user.getBirth_date(), 
				user.getBirth_month(), 
				user.getBirth_year(), 
				user.getFirstname(), 
				user.getLastname(), 
				user.getCompany(), 
				user.getAddress1(), 
				user.getAddress2(), 
				ConfigReader.getProperty("country"), 
				user.getState(), 
				user.getCity(), 
				user.getZipcode(), 
				user.getMobile_number());


		// Act
		pages
		.getAccountCreatedPage()
		.clickContinueButton();

		// Assert
		Assert.assertTrue(
				pages
				.getDashboardPage()
				.verifySuccessRegistration(
						user.getName()), 
				"Failed to validate user registration");

		FrameworkLogger.testEnd("verifyUserRgistersSuccessfully");
	}

	@Test(priority = 4, groups = "regression")
	@Owner("Ayanda Khaka")
	@Story("Register new user")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that account is deleted successfully")
	public void verifyDeleteAccount() {

		FrameworkLogger.testStart("verifyDeleteAccount");

		// Arrange
		UserModel user = UserDataHelper.generateUserData();
		pages
		.getHomePage()
		.clickSignLoginButton();
		pages
		.getLoginPage()
		.enterNewSignupDetails(
				user.getName(),
				user.getEmail());

		pages
		.getSignupPage()
		.registerUser(
				user.getTitle(), 
				user.getPassword(), 
				user.getBirth_date(), 
				user.getBirth_month(), 
				user.getBirth_year(), 
				user.getFirstname(), 
				user.getLastname(), 
				user.getCompany(), 
				user.getAddress1(), 
				user.getAddress2(), 
				ConfigReader.getProperty("country"), 
				user.getState(), 
				user.getCity(), 
				user.getZipcode(), 
				user.getMobile_number());

		pages
		.getAccountCreatedPage()
		.clickContinueButton();
		// Act
		pages
		.getDashboardPage()
		.clickDeleteAccount();

		// Assert
		Assert.assertTrue(
				pages
				.getDeleteAccountPage()
				.validateDeleteAccountHeaderText(
						ConfigReader.getProperty("accountDeleted")),
				"Failed to verify delete account header text");

		FrameworkLogger.testEnd("verifyDeleteAccount");
	}

	@Test(priority = 5, groups = "regression")
	@Owner("Ayanda Khaka")
	@Story("Register new user")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that email error message is displayed")
	public void verifyEmailAddressErrorMessage() {

		FrameworkLogger.testStart("verifyEmailAddressErrorMessage");

		// Arrange
		pages
		.getHomePage()
		.clickSignLoginButton();

		// Act
		pages
		.getLoginPage()
		.enterNewSignupDetails(
				user.getName(),
				user.getEmail());

		// Assert
		Assert.assertTrue(
				pages
				.getSignupPage()
				.validateEmailErrorMessage(
						ConfigReader.getProperty("emailError")), 
				"Failed to verify email address error message");

		FrameworkLogger.testEnd("verifyEmailAddressErrorMessage");
	}
}
