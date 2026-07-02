package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utility.BasePage;
import utility.ConfigReader;
import utility.FrameworkLogger;

public class LoginPage extends BasePage {
	
	//private WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		super(driver);
		//this.driver = driver;
		
	}
	
	private By emailInput = By.xpath("//input[@name='email' and @placeholder='Email Address']");
	private By passwordInput = By.name("password");
	private By loginButton = By.xpath("//button[@type='submit' and @data-qa='login-button']");
	private By loggedInUser = By.xpath("//a[contains(text(), 'Logged in as')]");
	private By loginError = By.xpath("//p[contains(text(), 'Your email or password is incorrect!')]");
	
	// Login Method
	public void loginToAutomationExercisePage(String email, String password) {
		actions
			.typeText(emailInput, email)
			.typeText(passwordInput, password)
			.click(loginButton);
	}
	
	// Login validation method
	public boolean verifySuccessLoginUser(String username) {
		return actions.validateText(loggedInUser, " Logged in as "+ username , "Logged In user");
	}
	
	// Get email validation message
	public String getEmailValidationMessage() {
		FrameworkLogger.info("Email validation messege is : " + driver.findElement(emailInput).getDomProperty("validationMessage"));
		return driver.findElement(emailInput).getDomProperty("validationMessage");
	}
	
	public boolean validateLoginErrorMessage() {
		return actions.validateText(loginError, ConfigReader.getProperty("loginErrorText"), "Validating login error message");
	}
	
	
	
	
//	public LoginPage setEmailText(String emailText) {
//		WebElement email = driverWait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
//		email.sendKeys(emailText);
//		return this;
//	}
//	
//	public LoginPage setPassword(String passwordText) {
//		WebElement password = driverWait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
//		password.sendKeys(passwordText);
//		return this;
//	}
//	
//	public LoginPage clickLoginButton() {
//		
//		WebElement login = driverWait.until(ExpectedConditions.elementToBeClickable(loginButton));
//		login.click();
//		return this;
//	}
	
	

}
