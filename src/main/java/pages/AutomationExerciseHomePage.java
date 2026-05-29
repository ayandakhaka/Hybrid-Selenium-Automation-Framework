package pages;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AutomationExerciseHomePage {
	
	private WebDriver driver;
	private WebDriverWait driverWait;
	
	public AutomationExerciseHomePage(WebDriver driver) {
		
		this.driver = driver;
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	private By signupLoginButton = By.xpath("//i[@class='fa fa-lock' ]");
	
	public AutomationExerciseHomePage clickSignLoginButton() {
		WebElement signupLogin = driverWait.until(ExpectedConditions.elementToBeClickable(signupLoginButton));
		signupLogin.click();
		return this;
	}

}
