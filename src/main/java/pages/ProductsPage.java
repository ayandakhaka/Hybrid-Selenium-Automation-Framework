package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;
import utility.BasePage;
import utility.FrameworkLogger;

public class ProductsPage extends BasePage {
	
	public ProductsPage(WebDriver driver) {
		super(driver);
	}
	
	private By productsLink = By.xpath("//a[@href='/products']");
	private By allProductsHeaderText = By.xpath("//h2[@class='title text-center']");
	private By searchProduct = By.id("search_product");
	private By submitSearch = By.id("submit_search");
	private By searchedProducts = By.xpath("//div[@class='productinfo text-center']//p");
	private By searchedProductText = By.xpath("//h2[@class='title text-center']");
	private By firstProduct = By.xpath("//img[@src='/get_product_picture/1']");
	private By secondProduct = By.xpath("//img[@src='/get_product_picture/2']");
	private By addToCartFirstProduct = By.xpath("//a[@data-product-id='1']");
	private By addToCartSecondProduct = By.xpath("//a[@data-product-id='2']");
	private By viewCart = By.cssSelector("a[href='view_cart']");
	private By addedToCartSuccessText = By.xpath("//p[@class='text-center']");
	
	@Step("User click on products link")
	public ProductsPage clickProductsLink() {
		actions.click(productsLink);
		return this;
	}
	
	@Step("Validating all products header text : '{0}'")
	public boolean validateAllProductsHeaderText(String textValidation) {
		return actions.validateText(allProductsHeaderText, textValidation, "Validating All products header text");
	}
	
	@Step("User enters search item : '{0}'")
	public ProductsPage enterSearchItem(String searchItem) {
		actions.typeText(searchProduct, searchItem);
		return this;
	}
	
	@Step("User click on search product")
	public ProductsPage clickOnSearchProduct() {
		actions.click(submitSearch);
		return this;
	}
	
	@Step("Validate searched product text : '{0}'")
	public boolean validateSearchedProductText(String text) {
		return actions.validateText(searchedProductText, text , "Validating searched product text");
	}
	// The method returns the product names
	@Step("Returns the list of product names")
	public List<String> getSearchedProductNames() {
		
		List<WebElement> products = driver.findElements(searchedProducts);
		
		List<String> productNames = new ArrayList<String>();
		
		for(WebElement product : products) {
			FrameworkLogger.info("Products : " + product.getText().trim());
			productNames.add(product.getText().trim());
		}
		
		return productNames;
	}
	
	@Step("User hovers over the first product")
	public ProductsPage hoverOverFirstProduct() {
		actions.hoverOverElement(firstProduct);
		return this;
	}
	
	@Step("User hovers over the second product")
	public ProductsPage hoverOverSecondProduct() {
		actions.hoverOverElement(secondProduct);
		return this;
	}
	
	@Step("User click first product, adding to cart")
	public ProductsPage addFirstProductToCart() {
		actions.click(addToCartFirstProduct);
		return this;
	}
	
	@Step("User click second product, adding to cart")
	public ProductsPage addSecondProductToCart() {
		actions.click(addToCartSecondProduct);
		return this;
	}
	
	@Step("Validate added to cart success message : '0'")
	public boolean validateAddedToCartSuccessMessage(String text) {
		return actions.validateText(addedToCartSuccessText, text, "Validating add to cart success message");
	}
	
	@Step("User click on view cart link")
	public ProductsPage clickViewCartLink() {
		actions.click(viewCart);
		return this;
	}
}
