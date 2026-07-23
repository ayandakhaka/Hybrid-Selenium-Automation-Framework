package ui;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductsPage;
import utility.BaseTest;
import utility.ConfigReader;
import utility.FrameworkLogger;

@Epic("Automation exercise")
@Feature("Search Products")
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
	@Test(priority = 1, groups = "Product page")
	@Story("Registered user navigate to product page")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that registered user navigated to product page successfully.")
	public void verifyUserNavigatedToProductsPage() {

		FrameworkLogger.testStart("verifyUserNavigatedToProductsPage");

		FrameworkLogger.info("User clicks on Products link");

		productsPage.clickProductsLink();

		FrameworkLogger.info("Validate if the user is redirected to the Products page");

		Assert.assertTrue(productsPage.validateAllProductsHeaderText(ConfigReader.getProperty("allProductsText")));

		FrameworkLogger.testEnd("verifyUserNavigatedToProductsPage");	
	}

	@Test(priority = 2, dependsOnGroups = "Product page")
	@Story("Search an existing product.")
	@Severity(SeverityLevel.NORMAL)
	@Description("Validating that all product items related to search are displayed.")
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
	
	@Test(priority = 3, dependsOnGroups = "Product page")
	@Story("Search a non existing product.")
	@Severity(SeverityLevel.MINOR)
	@Description("User searches a non existing product.")
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
