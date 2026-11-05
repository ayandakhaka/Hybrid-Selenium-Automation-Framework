package ui.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pages.ContactUsPage;
import pages.HomePage;
import utility.BaseTest;
import utility.ConfigReader;
import utility.FrameworkLogger;

public class ContactUsTest extends BaseTest {

	private HomePage homePage;
	private ContactUsPage contactUsPage;

	@BeforeMethod
	public void setupPages() {

		homePage = new HomePage(driver);
		contactUsPage = new ContactUsPage(driver);
	}
	
	@Test(priority = 1, groups = "regression")
	@Owner("Ayanda Khaka")
	@Story("Contact form validation")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that contact us header text is displayed")
	public void verifyContactUsHeaderText() {
		
		FrameworkLogger.testStart("verifyContactUsHeaderText");
	
		// Act
		homePage.clickContactUsLink();
		// Assert
		Assert.assertTrue(contactUsPage.validateContactUsHeaderText(
				ConfigReader.getProperty("contactUsHeaderText")),
				"Failed to validate contact us header text");
	}

	@Test(priority = 2, groups = "regression")
	@Owner("Ayanda Khaka")
	@Story("Contact form validation")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that contact us success message is displayed")
	public void verifyContactUsSuccessMessage() {

		FrameworkLogger.testStart("verifyContactUsSuccessMessage");

		// Arrange
		homePage.clickContactUsLink();

		// Act
		contactUsPage.contactUs(
				user.getName(),
				user.getEmail(), 
				ConfigReader.getProperty("subject"), 
				ConfigReader.getProperty("message"));

		// Assert
		Assert.assertTrue(contactUsPage.validateContactUsSuccessMessage(
				ConfigReader.getProperty("contactUsSuccessMessage")),
				"Failed to validate contact us success message");

		FrameworkLogger.testEnd("verifyContactUsSuccessMessage");
	}
	
	@Test(priority = 3, groups = "regression")
	@Owner("Ayanda Khaka")
	@Story("Contact form validation")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that email text field is validated when left blanck")
	public void verifyEmailTextFieldValidationText() {
		
		FrameworkLogger.testStart("verifyEmailTextFieldValidationText");
		
		// Arrange
		homePage.clickContactUsLink();
		
		// Act
		contactUsPage.clickSubmitButton();
		// Assert
		Assert.assertEquals(contactUsPage.getEmailValidationMessage(), 
				ConfigReader.getProperty("emailValidationText"),
				"Failed to verify email text field.");
		
		FrameworkLogger.testEnd("verifyEmailTextFieldValidationText");
	}
	
	
}
