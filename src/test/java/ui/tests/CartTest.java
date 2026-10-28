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
	
	private HomePage homePage;
	private LoginPage loginPage;
	private DashboardPage dashboardPage;
	private ProductsPage productsPage;
	private ViewCartPage viewCartPage;
	
	
	@BeforeMethod
	public void setupLogin() {
		
		user = TestDataManager.getInstance().getUser();
		
		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		productsPage = new ProductsPage(driver);
		dashboardPage = new DashboardPage(driver);
		viewCartPage = new ViewCartPage(driver);
	

		homePage.clickSignLoginButton();
		loginPage.loginToAutomationExercisePage(user.getEmail(), user.getPassword());
		
		dashboardPage.clickProductsLink();
	}
	
	@Test(priority = 1)
	public void verifyAddSingleProductToCart() {

	    FrameworkLogger.testStart("verifyFirstProductCanBeAddedToCart");

	    // Act
	    productsPage.hoverOverFirstProduct();
	    productsPage.clickAddToCartOnFirstProduct();
	    
	    // Assert
	    Assert.assertTrue(
	            productsPage.validateAddedToCartSuccessMessage(
	                    ConfigReader.getProperty("addtoCartSuccessMessage")),
	            "The success message was not displayed after adding the product to the cart.");
	    FrameworkLogger.testEnd("verifyFirstProductCanBeAddedToCart");
	    

	}
	
	@Test(priority = 2)
	public void verifyAddTwoProductsToCart() {
		
		FrameworkLogger.testStart("verifyAddTwoProductsToCart");
		// Arrange
		productsPage.hoverOverFirstProduct();
	    productsPage.clickAddToCartOnFirstProduct();
	    productsPage.clickContinueShopping();
	   
	    
	    // Act 
	    productsPage.hoverOverSecondProduct();
	    productsPage.clickAddToCartOnSecondProduct();
	    productsPage.clickViewCartLink();
		
		// Assert
	    
	 // Blue Top: product ID 1
	    Assert.assertEquals(
	    	viewCartPage.getProductName(1),
	        "Blue Top",
	        "Product name mismatch"
	    );

	    Assert.assertEquals(
	    	viewCartPage.getProductCategory(1),
	        "Women > Tops",
	        "Product category mismatch"
	    );

	    Assert.assertEquals(viewCartPage.getProductPrice(1), 500);
	    Assert.assertEquals(viewCartPage.getProductQuantity(1), 2);
	    Assert.assertEquals(viewCartPage.getProductTotal(1), 1000);

	}
}
