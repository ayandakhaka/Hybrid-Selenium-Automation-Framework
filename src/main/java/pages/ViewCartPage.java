package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utility.BasePage;

public class ViewCartPage extends BasePage {
	
	public ViewCartPage(WebDriver driver) {
		super(driver);
	}
	
	private By firstProductItem = By.xpath("//a[@href='/product_details/1']");
	private By secondProductItem = By.xpath("//a[@href='/product_details/2']");

	private By cartPrice = By.xpath("//p[text()='Rs. 500']");
	private By cartQuantity = By.xpath("//button[@class='disabled']");
	private By cartTotalPrice = By.xpath("//p[@class='cart_total_price']");
	private By removeProduct = By.cssSelector("a.cart_quantity_delete");
	
	public boolean verifyProductName(String productName) {
		return actions.validateText(firstProductItem, productName, "Validating product name.");
	}
	
	public boolean verifyCartPrice(String price) {
		
		return actions.validateText(cartPrice, price, "Validating cart price.");
	}
	
	public boolean verifyQuantity(String quantity) {
		return actions.validateText(cartQuantity, quantity, "Validating cart quantity.");
	}
	
	public int getCartPrice() {
		return Integer.parseInt(actions.getText(cartPrice).replace("Rs. ", "").trim());
	}
	
	public int getCartQuantity() {
		return Integer.parseInt(actions.getText(cartQuantity));
	}
	
	public String expectedCartTotalPrice() {
		int totalPrice = this.getCartQuantity() * this.getCartPrice();
		return "Rs. " + String.valueOf(totalPrice);
	}
	
	public String actualCartTotalPrice() {
		return actions.getText(cartTotalPrice);
	}
	
	public ViewCartPage clickRemoveProduct() {
		actions.click(removeProduct);
		actions.waitForElement(removeProduct);
		return this;
	}
	
	

}
