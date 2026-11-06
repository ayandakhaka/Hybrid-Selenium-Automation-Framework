package ui.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import api.model.UserModel;
import api.testdata.TestDataManager;
import pages.DashboardPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductsPage;
import pages.ViewCartPage;
import utility.BaseTest;
import utility.ConfigReader;
import utility.FrameworkLogger;

public class CartTest extends BaseTest {

	private UserModel user;

	@BeforeMethod
	public void setupLogin() {

		user = TestDataManager.getInstance().getUser();

		pages
		.getHomePage()
		.clickSignLoginButton();
		pages
		.getLoginPage()
		.loginToAutomationExercisePage(
				user.getEmail(),
				user.getPassword());

		pages
		.getDashboardPage()
		.clickProductsLink();
	}

	@Test(priority = 1)
	public void verifyAddSingleProductToCart() {

		FrameworkLogger.testStart("verifyFirstProductCanBeAddedToCart");

		// Act
		pages
		.getProductsPage()
		.hoverOverFirstProduct();
		pages
		.getProductsPage()
		.clickAddToCartOnFirstProduct();

		// Assert
		Assert.assertTrue(
				pages
				.getProductsPage()
				.validateAddedToCartSuccessMessage(
						ConfigReader.getProperty("addtoCartSuccessMessage")),
				"The success message was not displayed after adding the product to the cart.");
		FrameworkLogger.testEnd("verifyFirstProductCanBeAddedToCart");


	}

	@Test(priority = 2)
	public void verifyAddTwoProductsToCart() {

		FrameworkLogger.testStart("verifyAddTwoProductsToCart");
		// Arrange
		pages
		.getProductsPage()
		.hoverOverFirstProduct();
		pages
		.getProductsPage()
		.clickAddToCartOnFirstProduct();
		pages
		.getProductsPage()
		.clickContinueShopping();


		// Act 
		pages
		.getProductsPage()
		.hoverOverSecondProduct();
		pages
		.getProductsPage()
		.clickAddToCartOnSecondProduct();
		pages
		.getProductsPage()
		.clickViewCartLink();

		// Assert

		// Blue Top: product ID 1
		Assert.assertEquals(
				pages
				.getViewCartPage()
				.getProductName(1),
				"Blue Top",
				"Product name mismatch"
				);

		Assert.assertEquals(
				pages
				.getViewCartPage()
				.getProductCategory(1),
				"Women > Tops",
				"Product category mismatch"
				);

		Assert.assertEquals(
				pages
				.getViewCartPage()
				.getProductPrice(1), 500);
		Assert.assertEquals(
				pages
				.getViewCartPage()
				.getProductQuantity(1), 2);
		Assert.assertEquals(
				pages
				.getViewCartPage()
				.getProductTotal(1), 1000);

	}
}
