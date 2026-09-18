package api.tests;

import org.testng.annotations.Test;
import api.model.UserModel;
import api.services.UserApiService;
import api.setup.ApiTestSetup;
import api.testdata.TestDataManager;
import io.restassured.response.Response;
import org.testng.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import utility.ConfigReader;
import utility.FrameworkLogger;

@Epic("Automation exercise")
@Feature("API Automation")
public class AutomationExerciseAPITests extends ApiTestSetup {

	private UserModel user;
	private Response response;

	@Test(priority = 1)
	@Owner("Ayanda Khaka")
	@Story("Login with valid credentials")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate login with valid login credentials")
	public void verifyLoginWithValidLoginDetails() {

		FrameworkLogger.testStart("verifyLoginWithValidLoginDetails");

		// Arrange

		user = this.getUser();

		// Act
		response = UserApiService.validLogin(user);

		// Assert
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
				"Failed to verify " + response.jsonPath().getInt("responseCode"));

		Assert.assertEquals(response.jsonPath().getString("message"), 
				ConfigReader.getProperty("userExistMessage"), 
				"Failed to verify " + 
						response.jsonPath().getString("message"));

		FrameworkLogger.testEnd("verifyLoginWithValidLoginDetails");
	}

	@Test(priority = 2)
	@Owner("Ayanda Khaka")
	@Story("Login without email parameter")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate login without email parameter")
	public void verifyLoginWithoutEmailParameter() {

		FrameworkLogger.testStart("verifyLoginWithoutEmailParameter");

		// Arrange
		user = getUser();

		// Act
		response = UserApiService.loginWithoutEmailParameter(user);

		// Assert
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 
				Integer.parseInt(ConfigReader.getProperty("badRequestStatusCode")),
				"Failed to verify " + 
						response.jsonPath().getInt("responseCode"));
		Assert.assertEquals(response.jsonPath().getString("message"),
				ConfigReader.getProperty("loginWithoutEmailMessage"), 
				"Failed to verify " + 
						response.jsonPath().getString("message"));

		FrameworkLogger.testEnd("verifyLoginWithoutEmailParameter");
	}

	@Test(priority = 3)
	@Owner("Ayanda Khaka")
	@Story("Search product item")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate searched product item")
	public void verifySearchProductItem() {

		FrameworkLogger.testStart("verifySearchProductItem");

		// Act
		response = UserApiService.SearchProductItem(ConfigReader.getProperty("searchItem"));
		int productSize = response.jsonPath().getList("products").size();

		// Assert
		Assert.assertEquals(response.statusCode(), 
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")), 
				"Failed to verify status code.");

		// Verify response success status code
		Assert.assertEquals(response.jsonPath().getInt("responseCode"),
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
				"Failed to verify " + 
						response.jsonPath().getInt("responseCode"));
		// Verify product size
		Assert.assertTrue(productSize > 0,
				"Product list is empty");

		FrameworkLogger.testEnd("verifySearchProductItem");

	}

	@Test(priority = 4)
	@Owner("Ayanda Khaka")
	@Story("Search product item without product parameter")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate searched product item without providing product parameter")
	public void verifySearchProductItemWithoutProductItemParameter() {

		FrameworkLogger.testStart("verifySearchProductItemWithoutProductItemParameter");
		// Act

		response = UserApiService.searchProductItemWithoutSearchProduct();

		// Assert
		Assert.assertEquals(response.statusCode(), 
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")), 
				"Failed to verify status code.");
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 
				Integer.parseInt(ConfigReader.getProperty("badRequestStatusCode")), 
				"Failed to verify " + 
						+ response.jsonPath().getInt("responseCode"));

		Assert.assertEquals(response.jsonPath().getString("message"),
				ConfigReader.getProperty("searchProductItemWithoutProductParameter"),
				"Failed to verify " +
						response.jsonPath().getString("message"));

		FrameworkLogger.testEnd("verifySearchProductItemWithoutProductItemParameter");
	}

	@Test(priority = 5)
	@Owner("Ayanda Khaka")
	@Story("Update to all brand list")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate update to all brand list")
	public void verifyUpdateToAllBrandList() {

		FrameworkLogger.testStart("verifyUpdateToAllBrandList");
		// Act

		response = UserApiService.updateToAllBrandList();

		// Assert
		Assert.assertEquals(response.statusCode(), 
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")), 
				"Failed to verify " +
						response.statusCode());

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 
				Integer.parseInt(ConfigReader.getProperty("methodNotSupportedStatusCode")), 
				"Failed to verify " +
						response.jsonPath().getInt("responseCode"));

		Assert.assertEquals(response.jsonPath().getString("message"),
				ConfigReader.getProperty("methodNotSupportedMessage"),
				"Failed to verify " +
						response.jsonPath().getString("message"));

		FrameworkLogger.testEnd("verifyUpdateToAllBrandList");
	}

	@Test(priority = 6)
	@Owner("Ayanda Khaka")
	@Story("Post to all product list")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate post to all product list")
	public void verifyPostToAllProductList() {

		FrameworkLogger.testStart("verifyPostToAllProductList");
		// Act

		response = UserApiService.postToAllProductList();

		// Assert
		Assert.assertEquals(response.statusCode(), 
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")), 
				"Failed to verify " +
						response.statusCode());

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 
				Integer.parseInt(ConfigReader.getProperty("methodNotSupportedStatusCode")), 
				"Failed to verify " +
						response.jsonPath().getInt("responseCode"));

		Assert.assertEquals(response.jsonPath().getString("message"),
				ConfigReader.getProperty("methodNotSupportedMessage"),
				"Failed to verify " +
						response.jsonPath().getString("message"));

		FrameworkLogger.testEnd("verifyPostToAllProductList");
	}


	@Test(priority = 7)
	@Owner("Ayanda Khaka")
	@Story("Return user details by email")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate return user details by email")
	public void verifyReturnUserDetailsByEmail() {

		FrameworkLogger.testStart("verifyReturnUserDetailsByEmail");

		// Arrange
		user = getUser();

		// Act
		response = UserApiService.getUserDetailsByEmail(user.getEmail());

		// Assert
		// Verify status code
		Assert.assertEquals(response.statusCode(),
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
				"Failed to verify " + 
						response.statusCode());
		// Verify response code
		Assert.assertEquals(response.jsonPath().getInt("responseCode"),
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")), 
				"Failed to verify " + 
						response.jsonPath().getInt("responseCode"));
		// Verify email 
		Assert.assertEquals(response.jsonPath().getString("user.email"),
				user.getEmail(), 
				"Failed to verify " + 
						response.jsonPath().getString("user.email"));
		// Verify name
		Assert.assertEquals(response.jsonPath().getString("user.name"),
				user.getName(),
				"Failed to verify " + 
						response.jsonPath().getString("user.name"));
		// Verify title
		Assert.assertEquals(response.jsonPath().getString("user.title"), 
				user.getTitle(), 
				"Failed to verify " + 
						response.jsonPath().getString("user.title"));
		// Verify Birth day
		Assert.assertEquals(response.jsonPath().getString("user.birth_day"),
				user.getBirth_date(),
				"Failed to verify " + 
						response.jsonPath().getString("user.birth_day"));
		// Verify Birth month
		Assert.assertEquals(response.jsonPath().getString("user.birth_month"),
				user.getBirth_month(),
				"Failed to verify " + 
						response.jsonPath().getString("user.birth_month"));
		// Verify Birth year
		Assert.assertEquals(response.jsonPath().getString("user.birth_year"),
				user.getBirth_year(), 
				"Failed to verify " + 
						response.jsonPath().getString("user.birth_year"));
		// Verify first name
		Assert.assertEquals(response.jsonPath().getString("user.first_name"),
				user.getFirstname(),
				"Failed to verify " +
						response.jsonPath().getString("user.first_name"));
		// Verify last name
		Assert.assertEquals(response.jsonPath().getString("user.last_name"),
				user.getLastname(),
				"Failed to verify " + 
						response.jsonPath().getString("user.last_name"));
		// Verify company
		Assert.assertEquals(response.jsonPath().getString("user.company"),
				user.getCompany(),
				"Failed to verify " + 
						response.jsonPath().getString("user.company"));
		// Verify address 1
		Assert.assertEquals(response.jsonPath().getString("user.address1"), 
				user.getAddress1(),
				"Failed to verify " + 
						response.jsonPath().getString("user.address1"));
		// Verify address 2
		Assert.assertEquals(response.jsonPath().getString("user.address2"),
				user.getAddress2(), 
				"Failed to verify " + 
						response.jsonPath().getString("user.address2"));
		// Verify country
		Assert.assertEquals(response.jsonPath().getString("user.country"),
				user.getCountry(),
				"Failed to verify " + 
						response.jsonPath().getString("user.country"));
		// Verify state
		Assert.assertEquals(response.jsonPath().getString("user.state"),
				user.getState(),
				"Failed to verify " + 
						response.jsonPath().getString("user.state"));
		// Verify city
		Assert.assertEquals(response.jsonPath().getString("user.city"),
				user.getCity(), 
				"Failed to verify " + 
						response.jsonPath().getString("user.city"));
		// Verify zipcode
		Assert.assertEquals(response.jsonPath().getString("user.zipcode"),
				user.getZipcode(),
				"Failed to verify " + 
						response.jsonPath().getString("user.zipcode"));

		FrameworkLogger.testEnd("verifyReturnUserDetailsByEmail");
	}

	@Test(priority = 8)
	@Owner("Ayanda Khaka")
	@Story("Account update")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate user account update")
	public void verifyUserAccountUpdate() {

		FrameworkLogger.testStart("verifyUserAccountUpdate");

		// Arrange
		user = getUser();

		// Act
		response = UserApiService.userAccountUpdate(user);

		// Assert
		// Verify status code
		Assert.assertEquals(response.getStatusCode(),
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
				"Failed to verify " + 
						response.getStatusCode());
		// Verify response status code
		Assert.assertEquals(response.jsonPath().getInt("responseCode"),
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
				"Failed to verify " + 
						response.jsonPath().getInt("responseCode"));
		// Verify success update message
		Assert.assertEquals(response.jsonPath().getString("message"),
				ConfigReader.getProperty("userUpdatedMessage"),
				"Failed to verify " + 
						response.jsonPath().getString("message"));	

		FrameworkLogger.testEnd("verifyUserAccountUpdate");
	}

	@Test(priority = 9)
	@Owner("Ayanda Khaka")
	@Story("Delete user account")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate delete user account")
	public void verifyDeleteUserAccount() {

		FrameworkLogger.testStart("verifyDeleteUserAccount");

		// Arrange
		user = getUser();

		// Act
		response = UserApiService.deleteUser(user);
		// Verify status code
		Assert.assertEquals(response.getStatusCode(),
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
				"Failed to verify " + 
						response.getStatusCode());
		// Verify response status code
		Assert.assertEquals(response.jsonPath().getInt("responseCode"),
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
				"Failed to verify " + response.jsonPath().getInt("responseCode"));
		// Verify success delete message
		Assert.assertEquals(response.jsonPath().getString("message"),
				ConfigReader.getProperty("userDeletedMessage"),
				"Failed to verify " +
						response.jsonPath().getString("message"));

		FrameworkLogger.testEnd("verifyDeleteUserAccount");
	}

	@Test(priority = 10)
	@Owner("Ayanda Khaka")
	@Story("Invalid login")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate login with invalid login credentials")
	public void verifyLoginWithInvalidDetails() {

		FrameworkLogger.testStart("verifyLoginWithInvalidDetails");

		// Act

		response = UserApiService.loginWithInvalidCredentials(ConfigReader.getProperty("apiInvalidEmail"),
				ConfigReader.getProperty("apiInvalidPassword"));
		// Assert
		Assert.assertEquals(response.getStatusCode(),
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
				"Failed to verify " +
				response.getStatusCode());
		// Verify response status code
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 
				Integer.parseInt(ConfigReader.getProperty("notFoundStatusCode")), 
				"Failed to verify " +
						response.jsonPath().getInt("responseCode"));
		// Verify success response message
		Assert.assertEquals(response.jsonPath().getString("message"),
				ConfigReader.getProperty("deleteNonExistingUserMessage"),
				"Failed to verify " + 
						response.jsonPath().getString("message"));

		FrameworkLogger.testEnd("verifyLoginWithInvalidDetails");
	}

	@Test(priority = 11)
	@Owner("Ayanda Khaka")
	@Story("Delete login")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate delete login")
	public void verifyDeleteLogin() {

		FrameworkLogger.testStart("verifyDeleteLogin");

		// Act 
		response = UserApiService.deleteLogin();

		// Assert
		Assert.assertEquals(response.getStatusCode(),
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
				"Failed to verify " +
						response.getStatusCode());
		// Verify response status code
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 
				Integer.parseInt(ConfigReader.getProperty("methodNotSupportedStatusCode")),
				"Failed to verify " + response.getStatusCode());
		// Verify method not supported response message
		Assert.assertEquals(response.jsonPath().getString("message"),
				ConfigReader.getProperty("methodNotSupportedMessage"),
				"Failed to verify " + 
						response.jsonPath().getString("message"));

		FrameworkLogger.testEnd("verifyDeleteLogin");
	}

	private UserModel getUser() {

		return TestDataManager.getInstance().gerUser();
	}

}