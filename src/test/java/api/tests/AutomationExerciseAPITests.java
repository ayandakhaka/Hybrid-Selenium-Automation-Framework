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
				"Failed to verify success response code.");

		Assert.assertEquals(response.jsonPath().getString("message"), 
				ConfigReader.getProperty("userExistMessage"), 
				"Failed to verify success message body.");

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
				"Failed to verify response code.");
		Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("loginWithoutEmailMessage"), 
				"Failed to verify message body.");

		FrameworkLogger.testEnd("verifyLoginWithoutEmailParameter");
	}

	@Test(priority = 3)
	@Owner("Ayanda Khaka")
	@Story("Search product item")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate searched product item")
	public void verifySearchProductItem() {

		FrameworkLogger.testStart("verifySearchProductItem");
		
		// Arrange
		user = getUser();

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
				"Failed to verify response code.");
		// Verify product size
		Assert.assertTrue(productSize > 0, "Product list is empty");

		FrameworkLogger.testEnd("verifySearchProductItem");

	}

		@Test(priority = 4)
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
			Assert.assertEquals(response.statusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify status code.");
			// Verify response code
			Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify response code.");
			// Verify email 
			Assert.assertEquals(response.jsonPath().getString("user.email"), user.getEmail(), "Failed to verify email");
			// Verify name
			Assert.assertEquals(response.jsonPath().getString("user.name"), user.getName(), "Failed to verify name");
			// Verify title
			Assert.assertEquals(response.jsonPath().getString("user.title"), user.getTitle(), "Failed to verify title");
			// Verify Birth day
			Assert.assertEquals(response.jsonPath().getString("user.birth_day"), user.getBirth_date(), "Failed to verify birth_day");
			// Verify Birth month
			Assert.assertEquals(response.jsonPath().getString("user.birth_month"), user.getBirth_month(), "Failed to verify birth_day");
			// Verify Birth year
			Assert.assertEquals(response.jsonPath().getString("user.birth_year"), user.getBirth_year(), "Failed to verify birth_year");
			// Verify first name
			Assert.assertEquals(response.jsonPath().getString("user.first_name"), user.getFirstname(), "Failed to verify firstname");
			// Verify last name
			Assert.assertEquals(response.jsonPath().getString("user.last_name"), user.getLastname(), "Failed to verify lastname");
			// Verify company
			Assert.assertEquals(response.jsonPath().getString("user.company"), user.getCompany(), "Failed to verify company");
			// Verify address 1
			Assert.assertEquals(response.jsonPath().getString("user.address1"), user.getAddress1(), "Failed to verify address 1");
			// Verify address 2
			Assert.assertEquals(response.jsonPath().getString("user.address2"), user.getAddress2(), "Failed to verify address 2");
			// Verify country
			Assert.assertEquals(response.jsonPath().getString("user.country"), user.getCountry(), "Failed to verify country");
			// Verify state
			Assert.assertEquals(response.jsonPath().getString("user.state"), user.getState(), "Failed to verify state");
			// Verify city
			Assert.assertEquals(response.jsonPath().getString("user.city"), user.getCity(), "Failed to verify city");
			// Verify zipcode
			Assert.assertEquals(response.jsonPath().getString("user.zipcode"), user.getZipcode(), "Failed to verify zipcode");
	
			FrameworkLogger.testEnd("verifyReturnUserDetailsByEmail");
		}
	
		@Test(priority = 5)
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
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
					"Failed to validate status code.");
			// Verify response status code
			Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
					"Failed to verify response status code");
			// Verify success update message
			Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("userUpdatedMessage"),
					"Failed to validate update message");	
	
			FrameworkLogger.testEnd("verifyUserAccountUpdate");
		}
	
		@Test(priority = 6)
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
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
					"Failed to verify success status code.");
			// Verify response status code
			Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
					"Failed to verify response status code.");
			// Verify success delete message
			Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("userDeletedMessage"),
					"Failed to verify delete success message.");
	
			FrameworkLogger.testEnd("verifyDeleteUserAccount");
		}
	
		@Test(priority = 7)
		@Owner("Ayanda Khaka")
		@Story("Invalid login")
		@Severity(SeverityLevel.CRITICAL)
		@Description("Validate login with invalid login credentials")
		public void verifyLoginWithInvalidDetails() {
	
			FrameworkLogger.testStart("verifyLoginWithInvalidDetails");
			
			// Arrange
			user = getUser();
			// Act
	
			response = UserApiService.loginWithInvalidCredentials(ConfigReader.getProperty("apiInvalidEmail"), ConfigReader.getProperty("apiInvalidPassword"));
			// Assert
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify status code.");
			// Verify response status code
			Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("notFoundStatusCode")), "Failed to verify response code.");
			// Verify success response message
			Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("deleteNonExistingUserMessage"), "Failed to verify delete non existing user message body");
	
			FrameworkLogger.testEnd("verifyLoginWithInvalidDetails");
		}
	
		@Test(priority = 8)
		@Owner("Ayanda Khaka")
		@Story("Delete login")
		@Severity(SeverityLevel.CRITICAL)
		@Description("Validate delete login")
		public void verifyDeleteLogin() {
	
			FrameworkLogger.testStart("verifyDeleteLogin");
	
			// Arrange
			user = getUser();
			// Act 
			response = UserApiService.deleteLogin();
	
			// Assert
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify status code.");
			// Verify response status code
			Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("methodNotSupportedStatusCode")), "Failed to verify response code.");
			// Verify method not supported response message
			Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("methodNotSupportedMessage"), "Failed to verify method not supported message body");
	
			FrameworkLogger.testEnd("verifyDeleteLogin");
		}

	private UserModel getUser() {

		return TestDataManager.getInstance().gerUser();
	}

}