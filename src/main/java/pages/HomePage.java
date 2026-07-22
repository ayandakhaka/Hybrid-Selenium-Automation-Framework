package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import utility.BasePage;

public class HomePage extends BasePage {
	
	public HomePage(WebDriver driver) {
		
		super(driver);
	}
	
	private By signupLoginButton = By.xpath("//i[@class='fa fa-lock' ]");
	private By cartButton = By.cssSelector("a[href='/view_cart']");
	private By emptyCart = By.xpath("//p[@class='text-center']");
	
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

}
