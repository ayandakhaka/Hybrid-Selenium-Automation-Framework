package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import utility.BasePage;
import utility.FrameworkLogger;

public class DashboardPage extends BasePage {

	public DashboardPage(WebDriver driver) {
		super(driver);
	}
	
	private By username = By.xpath("//a[contains(., 'Logged in as')]/b");
	private By productsLink = By.xpath("//a[@href='/products']");
	
	@Step("Get loggedin user text")
	public String getLoggedinUsernameText() {
		return actions.getText(username);
	}
	
	@Step("User click on products link")
	public DashboardPage clickProductsLink() {
		FrameworkLogger.info("Click in product link...");
		actions.click(productsLink);
		return this;
	}
	
	
	
}
