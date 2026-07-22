package ui;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.LoginPage;
import pages.ProductsPage;
import pages.ViewCartPage;
import utility.BaseTest;
import utility.ConfigReader;
import utility.FrameworkLogger;

public class CartTest extends BaseTest {
	
	private HomePage homePage;
	private LoginPage loginPage;
	private ProductsPage productsPage;
	private ViewCartPage viewPageCart;
	
	
//	@BeforeMethod
//	public void setupLogin() {
//		
//		homePage = new HomePage(driver);
//		loginPage = new LoginPage(driver);
//		productsPage = new ProductsPage(driver);
//		viewPageCart = new ViewCartPage(driver);
//
//		homePage.clickSignLoginButton();
//		loginPage.loginToAutomationExercisePage(user.getEmail(), user.getPassword());
//		
//		productsPage.clickProductsLink();
//	}
//	
//	@Test(priority = 1)
//	public void verifyFirstProductCanBeAddedToCart() {
//
//	    FrameworkLogger.testStart("verifyFirstProductCanBeAddedToCart");
//
//	    FrameworkLogger.info("Adding the first product to the cart.");
//
//	    productsPage.addFirstProductToCart();
//
//	    Assert.assertTrue(
//	            productsPage.validateAddedToCartSuccessMessage(
//	                    ConfigReader.getProperty("addtoCartSuccessMessage")),
//	            "The success message was not displayed after adding the product to the cart.");
//	    FrameworkLogger.testEnd("verifyFirstProductCanBeAddedToCart");
//	    
//
//	}
//	
//	@AfterMethod
//	public void cleanCart() {
//		homePage.clickCartButton();
//		while(!homePage.isCartEmpty()) {
//			viewPageCart.clickRemoveProduct();
//		}
//	}

}
