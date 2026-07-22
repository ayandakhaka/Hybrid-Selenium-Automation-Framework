package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import utility.BasePage;

public class LogoutPage extends BasePage {

	public LogoutPage(WebDriver driver) {
		super(driver);
	}
	
	private By logout = By.linkText("Logout");
	
	@Step("User click on logout button")
	public LogoutPage clickLogout() {
		actions.click(logout);
		return this;
	}
}
