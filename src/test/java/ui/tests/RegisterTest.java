package ui.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
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
import pages.AccountCreatedPage;
import pages.DashboardPage;
import pages.DeleteAccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SignupPage;
import utility.BaseTest;
import utility.ConfigReader;
import utility.FrameworkLogger;

@Epic("Automation exercise")
@Feature("Registration Tests")
public class RegisterTest extends BaseTest {
	
	//private UserModel user;
	
	private HomePage homePage;
	private LoginPage loginPage;
	private SignupPage signupPage;
	private AccountCreatedPage accountCreatedPage;
	private DashboardPage dashboardPage;
	private DeleteAccountPage  deleteAccountPage;
	
	
	@BeforeMethod
	public void setupPages() {
		
		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		signupPage = new SignupPage(driver);
		accountCreatedPage = new AccountCreatedPage(driver);
		dashboardPage = new DashboardPage(driver);
		deleteAccountPage = new DeleteAccountPage(driver);
		
	}
	
	@Test(priority = 1, groups = "regression")
	@Owner("Ayanda Khaka")
	@Story("Register new user")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that user navigate to signup page")
	public void verifyUserNavigateToSignupPage() {
		
		FrameworkLogger.testStart("verifyUserNavigateToSignupPage");
		
		// Arrange
		UserModel user = UserDataHelper.generateUserData();
		homePage.clickSignLoginButton();
		// Act
		loginPage.enterNewSignupDetails(user.getName(), user.getEmail());
		// Assert
		Assert.assertTrue(signupPage.validateEnterAccountInformationText(
				ConfigReader.getProperty("signupHeaderText")), "Failed to validate enter account information text");
		
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
		homePage.clickSignLoginButton();
		loginPage.enterNewSignupDetails(user.getName(), user.getEmail());
		
		// Act
		signupPage.registerUser(
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
		Assert.assertTrue(accountCreatedPage.validateAccountCreatedMessage(
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
		homePage.clickSignLoginButton();
		loginPage.enterNewSignupDetails(user.getName(), user.getEmail());
		
		signupPage.registerUser(
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
		accountCreatedPage.clickContinueButton();
		
		// Assert
		Assert.assertTrue(dashboardPage.verifySuccessRegistration(user.getName()), "Failed to validate user registration");
		
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
		homePage.clickSignLoginButton();
		loginPage.enterNewSignupDetails(user.getName(), user.getEmail());
		
		signupPage.registerUser(
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
		
		accountCreatedPage.clickContinueButton();
		// Act
		dashboardPage.clickDeleteAccount();
		
		// Assert
		Assert.assertTrue(deleteAccountPage.validateDeleteAccountHeaderText(ConfigReader.getProperty("accountDeleted")));
		
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
		homePage.clickSignLoginButton();
		
		// Act
		loginPage.enterNewSignupDetails(user.getName(), user.getEmail());
		
		// Assert
		Assert.assertTrue(signupPage.validateEmailErrorMessage(ConfigReader.getProperty("emailError")));
		
		FrameworkLogger.testEnd("verifyEmailAddressErrorMessage");
	}
}
