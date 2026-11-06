package utility;

import org.openqa.selenium.WebDriver;

import pages.AccountCreatedPage;
import pages.ContactUsPage;
import pages.DashboardPage;
import pages.DeleteAccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.LogoutPage;
import pages.ProductsPage;
import pages.SignupPage;
import pages.ViewCartPage;

public class PageManager {
	
	private final WebDriver driver;
	
	public PageManager(WebDriver driver) {
		this.driver = driver;
	}
	
	public HomePage getHomePage() {
		return new HomePage(driver);
	}
	
	public LoginPage getLoginPage() {
		return new LoginPage(driver);
	}
	
	public LogoutPage getLogout() {
		return new LogoutPage(driver);
	}
	
	public ContactUsPage getContactUsPage() {
		return new ContactUsPage(driver);
	}
	
	public DashboardPage getDashboardPage() {
		return new DashboardPage(driver);
	}
	
	public ProductsPage getProductsPage() {
		return new ProductsPage(driver);
	}
	
	public ViewCartPage getViewCartPage() {
		return new ViewCartPage(driver);
	}
	
	public SignupPage getSignupPage() {
		return new SignupPage(driver);
	}
	
	public AccountCreatedPage getAccountCreatedPage() {
		return new AccountCreatedPage(driver);
	}
	
	public DeleteAccountPage getDeleteAccountPage() {
		return new DeleteAccountPage(driver);
	}

}
