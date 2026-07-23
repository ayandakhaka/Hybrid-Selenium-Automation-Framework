package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import utility.BasePage;
import utility.ConfigReader;
import utility.FrameworkLogger;

public class LoginPage extends BasePage {
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	private By emailInput = By.xpath("//input[@name='email' and @placeholder='Email Address']");
	private By passwordInput = By.name("password");
	private By loginButton = By.xpath("//button[@type='submit' and @data-qa='login-button']");
	private By loggedInUser = By.xpath("//a[contains(text(), 'Logged in as')]");
	private By loginError = By.xpath("//p[contains(text(), 'Your email or password is incorrect!')]");
	private By loginFormHeaderText = By.xpath("//h2[text()='Login to your account']");
	
	// Login Method
	@Step("Login with valid credentials")
	public void loginToAutomationExercisePage(String email, String password) {
		actions
			.typeText(emailInput, email)
			.typeText(passwordInput, password)
			.click(loginButton);
	}
	
	// Login validation method
	@Step("Verify user is logged in.")
	public boolean verifySuccessLoginUser(String username) {
		return actions.validateText(loggedInUser, " Logged in as "+ username , "Logged In user");
	}
	
	// Get email validation message
	@Step("Getting email validation message")
	public String getEmailValidationMessage() {
		FrameworkLogger.info("Email validation messege is : " + driver.findElement(emailInput).getDomProperty("validationMessage"));
		return driver.findElement(emailInput).getDomProperty("validationMessage");
	}
	
	@Step("Verify login error message displayed")
	public boolean validateLoginErrorMessage() {
		return actions.validateText(loginError, ConfigReader.getProperty("loginErrorText"),
				"Validating login error message");
	}
	
	@Step("Verify login header text")
	public boolean validateLoginFormHeaderText() {
		return actions.validateText(loginFormHeaderText, ConfigReader.getProperty("loginFormHeaderText"), 
				"Validating login form header text");
	}
}
