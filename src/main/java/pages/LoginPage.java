package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	private WebDriver driver;
	private WebDriverWait driverWait;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	private By emailInput = By.xpath("//input[@name='email' and @placeholder='Email Address']");
	private By passwordInput = By.name("password");
	private By loginButton = By.cssSelector(".btn btn-default");
	
	// Action Methods
	
	public LoginPage setEmailText(String emailText) {
		WebElement email = driverWait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
		email.sendKeys(emailText);
		return this;
	}
	
	public LoginPage setPassword(String passwordText) {
		WebElement password = driverWait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
		password.sendKeys(passwordText);
		return this;
	}
	
	public LoginPage clickLoginButton() {
		
		WebElement login = driverWait.until(ExpectedConditions.elementToBeClickable(loginButton));
		login.click();
		return this;
	}
	
	

}
