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
	private By loggedInUser = By.xpath("//a[contains(text(), 'Logged in as')]");
	private By deleteAccount = By.xpath("//a[@href='/delete_account']");
	
	
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
	
	public boolean verifySuccessRegistration(String username) {
		return actions.validateText(loggedInUser, " Logged in as "+ username , "Logged In user");
	}
	
	public DashboardPage clickDeleteAccount() {
		actions.click(deleteAccount);
		return this;
	}
	
}
