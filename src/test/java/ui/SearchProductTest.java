package ui;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.LoginPage;
import pages.ProductsPage;
import utility.BaseTest;
import utility.ConfigReader;
import utility.FrameworkLogger;

public class SearchProductTest extends BaseTest {

	private HomePage homePage;
	private LoginPage loginPage;
	private ProductsPage productsPage;

	@BeforeMethod
	public void setupLogin() {

		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		productsPage = new ProductsPage(driver);

		homePage.clickSignLoginButton();
		loginPage.loginToAutomationExercisePage(user.getEmail(), user.getPassword());	

	}
	@Test(priority = 1)
	public void verifyUserNavigatedToProductsPage() {

		FrameworkLogger.testStart("verifyUserNavigatedToProductsPage");

		FrameworkLogger.info("User clicks on Products link");

		productsPage.clickProductsLink();

		FrameworkLogger.info("Validate if the user is redirected to the Products page");

		Assert.assertTrue(productsPage.validateAllProductsHeaderText(ConfigReader.getProperty("allProductsText")));

		FrameworkLogger.testEnd("verifyUserNavigatedToProductsPage");	
	}

	@Test(priority = 2)
	public void verifyAllItemsRelatedToSearchAreDisplayed() {

		FrameworkLogger.testStart("verifyAllItemsRelatedToSearchAreDisplayed");

		FrameworkLogger.info("User clicks on Products link");

		productsPage.clickProductsLink();
		
		FrameworkLogger.info("User enters item to search");
		
		productsPage.enterSearchItem(ConfigReader.getProperty("searchItem"));
		
		FrameworkLogger.testEnd("User clicks on submit search button");
		
		productsPage.clickOnSearchProduct();
		
		List<String> actualProducts = productsPage.getSearchedProductNames();

		Assert.assertFalse(actualProducts.isEmpty(), "No products were returned.");
		
		Assert.assertTrue(productsPage.validateSearchedProductText(ConfigReader.getProperty("searchedProductText")), 
				"Failed to validate searched product text");

		for (String product : actualProducts) {
		    Assert.assertTrue(product.toLowerCase().contains(ConfigReader.getProperty("searchItem").trim().toLowerCase()),
		            "Product does not match search: " + product);
		}
		
		FrameworkLogger.testEnd("verifyAllItemsRelatedToSearchAreDisplayed");
	}
	
	@Test(priority = 3)
	public void verifyUserSearchNonExistingItem() {
		
		FrameworkLogger.testStart("verifyUserSearchNonExistingItem");

		FrameworkLogger.info("User clicks on Products link");

		productsPage.clickProductsLink();
		
		FrameworkLogger.info("User enters item to search");
		
		productsPage.enterSearchItem(ConfigReader.getProperty("nonExistingProduct"));
		
		FrameworkLogger.testEnd("User clicks on submit search button");
		
		productsPage.clickOnSearchProduct();
		
		List<String> actualProducts = productsPage.getSearchedProductNames();

		Assert.assertTrue(actualProducts.isEmpty(), "Failed to validate search non existing product.");
		
		FrameworkLogger.testEnd("verifyUserSearchNonExistingItem");
		
	}
}
