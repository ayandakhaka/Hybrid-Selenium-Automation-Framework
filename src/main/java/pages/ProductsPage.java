package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;
import utility.BasePage;
import utility.FrameworkLogger;

public class ProductsPage extends BasePage {
	
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	public ProductsPage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	
	private By allProductsHeaderText = By.xpath("//h2[@class='title text-center']");
	private By searchProduct = By.id("search_product");
	private By submitSearch = By.id("submit_search");
	private By searchedProducts = By.xpath("//div[@class='productinfo text-center']//p");
	private By searchedProductText = By.xpath("//h2[@class='title text-center']");
	private By addToCartSecondProduct = By.cssSelector("");
	private By viewCart = By.cssSelector("a[href='/view_cart']");
	private By addedToCartSuccessText = By.xpath("//p[@class='text-center']");
	private By firstProductOverlayButton = By.xpath(
	        ".//div[contains(@class,'product-overlay')]"
	        + "//a[@data-product-id='1' and contains(@class,'add-to-cart')]"
	    );
	
	private By secondProductOverlayButton = By.xpath(
	        ".//div[contains(@class,'product-overlay')]"
	        + "//a[@data-product-id='2' and contains(@class,'add-to-cart')]"
	    );
	private By firstProduct = By.xpath(
	        "//div[contains(@class,'product-image-wrapper')]"
	        + "[.//img[@src='/get_product_picture/1']]"
	    );
	private By continueShoppingButton = By.xpath("//button[@class='btn btn-success close-modal btn-block' "
			+ "and @data-dismiss='modal']");
	private By secondProduct = By.xpath(
	        "//div[contains(@class,'product-image-wrapper')]"
	        + "[.//img[@src='/get_product_picture/2']]"
	    );
	
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
	// The method returns a list of product names
	@Step("Returns the list of product names")
	public List<String> getSearchedProductNames() {
		
		List<WebElement> products = driver.findElements(searchedProducts);
		
		List<String> productNames = new ArrayList<>();
		
		for(WebElement product : products) {
			FrameworkLogger.info("Products : " + product.getText().trim());
			productNames.add(product.getText().trim());
		}
		
		return productNames;
	}
	
	@Step("User hovers over the first product")
	public ProductsPage hoverOverFirstProduct() {
		actions.scrollToElement(firstProduct);
		actions.hoverOverElement(firstProduct);
		return this;
	}
	
	@Step("User hovers over the second product")
	public ProductsPage hoverOverSecondProduct() {
		actions.scrollToElement(secondProduct);
		actions.hoverOverElement(secondProduct);
		return this;
	}
	
	@Step("User click first product, adding to cart")
	public void clickAddToCartOnFirstProduct() {
	    WebElement productCard = wait.until(
	        ExpectedConditions.visibilityOfElementLocated(firstProduct)
	    );

	    // Hover over the product card to reveal its overlay.
	    new Actions(driver)
	        .moveToElement(productCard)
	        .perform();
	    
	    WebElement button = wait.until(d -> {
	        WebElement card = d.findElement(firstProduct);

	        List<WebElement> matches = card.findElements(firstProductOverlayButton);

	        for (WebElement element : matches) {
	            if (element.isDisplayed() && element.isEnabled()) {
	                return element;
	            }
	        }

	        return null;
	    });

	    button.click();
	}
	
	@Step("User click second product, adding to cart")
	public void clickAddToCartOnSecondProduct() {
	    WebElement productCard = wait.until(
	        ExpectedConditions.visibilityOfElementLocated(secondProduct)
	    );

	    // Hover over the product card to reveal its overlay.
	    new Actions(driver)
	        .moveToElement(productCard)
	        .perform();
	    
	    WebElement button = wait.until(d -> {
	        WebElement card = d.findElement(secondProduct);

	        List<WebElement> matches = card.findElements(secondProductOverlayButton);

	        for (WebElement element : matches) {
	            if (element.isDisplayed() && element.isEnabled()) {
	                return element;
	            }
	        }

	        return null;
	    });

	    button.click();
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
		actions.waitForVisibility(viewCart, 15);
		actions.click(viewCart);
		return this;
	}
	
	public ProductsPage clickContinueShopping() {
		actions.waitForVisibility(continueShoppingButton, 10);
		actions.click(continueShoppingButton);
		return this;
	}
}
