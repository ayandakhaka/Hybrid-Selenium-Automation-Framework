package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import utility.BasePage;

public class HomePage extends BasePage {
	
	public HomePage(WebDriver driver) {
		
		super(driver);
	}
	
	private By signupLoginButton = By.cssSelector("a[href='/login']");
	private By cartButton = By.cssSelector("a[href='/view_cart']");
	private By emptyCart = By.xpath("//p[@class='text-center']");
	private By contactUsLink = By.xpath("//a[@href='/contact_us']");
	
	@Step("Click 'Signup / Login' button")
	public void clickSignLoginButton() {
		actions.click(signupLoginButton);
	}
	
	@Step("Click cart button")
	public HomePage clickCartButton() {
		actions.click(cartButton);
		return this;
	}
	
	@Step("Verifying cart is empty")
	public boolean isCartEmpty() {
		return actions.getText(emptyCart).contains("Cart is empty!");
	}
	
	public HomePage clickContactUsLink() {
		actions.click(contactUsLink);
		return this;
	}

}
