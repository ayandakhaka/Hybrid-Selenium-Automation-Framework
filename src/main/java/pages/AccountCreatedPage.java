package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import utility.BasePage;

public class AccountCreatedPage extends BasePage {

	public AccountCreatedPage(WebDriver driver) {
		
		super(driver);
	}
	
	private By successMessage = By.xpath("//p[contains(.,'Congratulations! Your new account has been successfully created!')]");
	private By continueButton = By.xpath("//a[@data-qa='continue-button' and @class='btn btn-primary']");
	
	@Step("Validating account created success message")
	public boolean validateAccountCreatedMessage(String message) {
		return actions.validateText(successMessage, message, "Validating account created success message");
	}
	
	@Step("User clicks continue button")
	public AccountCreatedPage clickContinueButton() {
		actions.click(continueButton);
		return this;
	}
	
}
