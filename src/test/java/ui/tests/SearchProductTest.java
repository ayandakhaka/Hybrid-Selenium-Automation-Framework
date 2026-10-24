package ui.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import api.model.UserModel;
import api.testdata.TestDataManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pages.DashboardPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductsPage;
import utility.BaseTest;
import utility.ConfigReader;
import utility.FrameworkLogger;

@Epic("Automation exercise")
@Feature("Search Products")
public class SearchProductTest extends BaseTest {

	private UserModel user;

	private HomePage homePage;
	private LoginPage loginPage;
	private DashboardPage dashboardPage;
	private ProductsPage productsPage;

	@BeforeMethod
	public void setupLogin() {

		user = TestDataManager.getInstance().getUser();

		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		productsPage = new ProductsPage(driver);
		dashboardPage = new DashboardPage(driver);

		homePage.clickSignLoginButton();
		loginPage.loginToAutomationExercisePage(user.getEmail(), user.getPassword());	

	}

	@Test(priority = 1)
	@Story("Registered user navigate to product page")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that registered user navigated to product page successfully.")
	public void verifyUserNavigatedToProductsPage() {

		FrameworkLogger.testStart("verifyUserNavigatedToProductsPage");

		// Arrange
		FrameworkLogger.info("Loggedin user text" + dashboardPage.getLoggedinUsernameText());
		popupHandler.closeUnexpectedPopup();

		// Act
		dashboardPage.clickProductsLink();

		// Assert
		Assert.assertTrue(productsPage.validateAllProductsHeaderText(ConfigReader.getProperty("allProductsText")));

		FrameworkLogger.testEnd("verifyUserNavigatedToProductsPage");	
	}

	@Test(priority = 2)
	@Story("Search an existing product.")
	@Severity(SeverityLevel.NORMAL)
	@Description("Validating that all product items related to search are displayed.")
	public void verifyAllItemsRelatedToSearchAreDisplayed() {

		FrameworkLogger.testStart("verifyAllItemsRelatedToSearchAreDisplayed");

		// Arrange
		dashboardPage.clickProductsLink();

		// Act
		productsPage.enterSearchItem(ConfigReader.getProperty("searchItem"));
		productsPage.clickOnSearchProduct();
		
		List<String> actualProducts = productsPage.getSearchedProductNames();

		// Assert

		Assert.assertFalse(actualProducts.isEmpty(), "No products were returned.");
		
		Assert.assertTrue(productsPage.validateSearchedProductText(
				ConfigReader.getProperty("searchedProductText")), 
				"Failed to validate searched product text");

		for (String product : actualProducts) {
			Assert.assertTrue(product.toLowerCase().contains(
					ConfigReader.getProperty("searchItem").trim().toLowerCase()),
					"Product does not match search: " + product);
		}

		FrameworkLogger.testEnd("verifyAllItemsRelatedToSearchAreDisplayed");
	}

	@Test(priority = 3)
	@Story("Search a non existing product.")
	@Severity(SeverityLevel.MINOR)
	@Description("User searches a non existing product.")
	public void verifyUserSearchNonExistingItem() {

		FrameworkLogger.testStart("verifyUserSearchNonExistingItem");

		// Arrange
		dashboardPage.clickProductsLink();

		// Act
		productsPage.enterSearchItem(ConfigReader.getProperty("nonExistingProduct"));
		productsPage.clickOnSearchProduct();
		
		List<String> actualProducts = productsPage.getSearchedProductNames();

		// Assert
		Assert.assertTrue(actualProducts.isEmpty(), "Failed to validate search non existing product.");

		FrameworkLogger.testEnd("verifyUserSearchNonExistingItem");

	}
}
