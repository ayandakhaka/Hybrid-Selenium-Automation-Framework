package api;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import api.helpers.UserDataHelper;
import api.model.UserModel;
import api.payload.UserPayload;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import org.testng.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import utility.ConfigReader;
import utility.FrameworkLogger;

@Epic("Automation exercise")
@Feature("API Automation")
public class AutomationExerciseAPITests {

	private UserModel user;

	@BeforeClass
	public void setupData() {
		// This sets the system property
		System.setProperty(ConfigReader.getProperty("systemPropertyName"), 
				ConfigReader.getProperty("systemPropertyValue"));
		
		// This generate a user data and save to a json file
		UserDataHelper.generateUserData();
		
		// This reads a user data from json file and store it in user model object
		user = UserDataHelper.readUserData();
	}

	@Test(priority = 1)
	@Owner("Ayanda Khaka")
	@Story("Register new user account")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate register user account")
	public void verifyRegisterUserAccount() {

		// Start test
		FrameworkLogger.testStart("verifyRegisterUserAccount");

		FrameworkLogger.info("Register user account by sending a post request to: " +
		ConfigReader.getProperty("createAccountEndpoint"));

		Response response =
				given()
				.relaxedHTTPSValidation()
				.baseUri(ConfigReader.getProperty("apiBaseUrl"))
				.contentType(ContentType.URLENC)
				.formParams(UserPayload.createUserPayload(
						user.getName(),
						user.getEmail(),
						user.getPassword(),
						user.getTitle(),
						user.getBirth_date(),
						user.getBirth_month(),
						user.getBirth_year(),
						user.getFirstname(),
						user.getLastname(),
						user.getCompany(),
						user.getAddress1(),
						user.getAddress2(),
						user.getCountry(),
						user.getZipcode(),
						user.getState(),
						user.getCity(),
						user.getMobile_number()
						))
				.when()
				// Act to the endpoint
				.post(ConfigReader.getProperty("createAccountEndpoint"));
		// Logger output console for status code
		FrameworkLogger.apiResponse(response.getStatusCode());
		// Logger response body output console
		FrameworkLogger.info(
				"Response Body:\n" +
						response.asPrettyString());
		
		// Output logger to the console
		FrameworkLogger.info(
				"Verifying Status Code. Expected: "
						+ ConfigReader.getProperty("successStatusCode")
						+ ", Actual: "
						+ response.getStatusCode());

		// Verify status code
		Assert.assertEquals(response.getStatusCode(), 
				Integer.parseInt(
						ConfigReader.getProperty("successStatusCode")),
				"Failed to verify status code");

		// Output logger to the console
		FrameworkLogger.info(
				"Verifying Response Code. Expected: "
						+ ConfigReader.getProperty("methodNotSupportedStatusCode")
						+ ", Actual: "
						+ response.jsonPath().getInt("responseCode"));
		
		// Verify response code
		Assert.assertEquals(response.jsonPath().getInt("responseCode"),
				Integer.parseInt(ConfigReader.getProperty("createdStatusCode")),
				"Failed to verify success response code");
		
		// Output logger to the console
		FrameworkLogger.info(
				"Verifying Message body. Expected: "
						+ ConfigReader.getProperty("userCreatedMessage")
						+ ", Actual: "
						+ response.jsonPath().getString("message"));
		// Verify success message
		Assert.assertEquals(response.jsonPath().getString("message"), 
				ConfigReader.getProperty("userCreatedMessage"), 
				"Failed to verify success message body.");
		// End test
		FrameworkLogger.testEnd("verifyRegisterUserAccount");
	}

	@Test(priority = 2)
	@Owner("Ayanda Khaka")
	@Story("Login with valid credentials")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate login with valid login credentials")
	@Step("Login with valid credentials")
	public void verifyLoginWithValidLoginDetails() {
		FrameworkLogger.testStart("verifyLoginWithValidLoginDetails");
		
		// Output logger to the console
		FrameworkLogger.info("Sending a login post request : " + ConfigReader.getProperty("loginEndpoint"));
		Response response = 
				given()
				.relaxedHTTPSValidation()
				.baseUri(ConfigReader.getProperty("apiBaseUrl"))
				.contentType(ContentType.URLENC)
				.formParams(UserPayload.createValidLoginPayload(user.getEmail(), user.getPassword()))

				.when()
				.post(ConfigReader.getProperty("loginEndpoint"));
		
		// Output logger to the console
		FrameworkLogger.apiResponse(Integer.parseInt(ConfigReader.getProperty("successStatusCode")));

		// Logger response body
		FrameworkLogger.info(
				"Response Body:\n" +
						response.asPrettyString());

		// Logger output console
		FrameworkLogger.info(
				"Verifying Status Code. Expected: "
						+ ConfigReader.getProperty("successStatusCode")
						+ ", Actual: "
						+ response.getStatusCode());
		// Verify status code
		Assert.assertEquals(response.getStatusCode(),
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
				"Failed to verify status code.");
		// Logger output console
		FrameworkLogger.info(
				"Verifying Response Code. Expected: "
						+ Integer.parseInt(ConfigReader.getProperty("successStatusCode"))
						+ ", Actual: "
						+ response.jsonPath().getInt("responseCode"));
		// Verify response code
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
				"Failed to verify success response code.");
		// Logger output console
		FrameworkLogger.info(
				"Verifying Message body. Expected: "
						+ ConfigReader.getProperty("userCreatedMessage")
						+ ", Actual: "
						+ response.jsonPath().getString("message"));
		// Verify success message
		Assert.assertEquals(response.jsonPath().getString("message"), 
				ConfigReader.getProperty("userExistMessage"), 
				"Failed to verify success message body.");
		
		// Output logger to the console
		FrameworkLogger.testEnd("verifyLoginWithValidLoginDetails");
	}

	@Test(priority = 3)
	@Owner("Ayanda Khaka")
	@Story("Login without email parameter")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate login without email parameter")
	public void verifyLoginWithoutEmailParameter() {

		FrameworkLogger.testStart("verifyLoginWithoutEmailParameter");

		FrameworkLogger.info("Sending login post requestion without email parameter : " 
				+ ConfigReader.getProperty("loginEndpoint"));

		Response response = 
				given()
				.relaxedHTTPSValidation()
				.baseUri(ConfigReader.getProperty("apiBaseUrl"))
				.contentType(ContentType.URLENC)
				.formParams(UserPayload.createLoginPayloadWithoutEmailPayload(user.getPassword()))

				.when()
				.post(ConfigReader.getProperty("loginEndpoint"));
		FrameworkLogger.apiResponse(Integer.parseInt(ConfigReader.getProperty("successStatusCode")));

		// Logger output console
		FrameworkLogger.info(
				"Verifying Status Code. Expected: "
						+ ConfigReader.getProperty("successStatusCode")
						+ ", Actual: "
						+ response.getStatusCode());
		// Logger response body
		FrameworkLogger.info(
				"Response Body:\n" +
						response.asPrettyString());

		// Verify Status code
		Assert.assertEquals(response.getStatusCode(),
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")), 
				"Failed to verify status code.");
		// Logger output console
		FrameworkLogger.info(
				"Verifying response status Code. Expected: "
						+ Integer.parseInt(ConfigReader.getProperty("badRequestStatusCode"))
						+ ", Actual: "
						+ response.jsonPath().getInt("responseCode"));

		// Verify response code
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 
				Integer.parseInt(ConfigReader.getProperty("badRequestStatusCode")),
				"Failed to verify response code.");
		FrameworkLogger.info("Verify response message body. Expected: " + ConfigReader.getProperty("loginWithoutEmailMessage"));
		// Verify message body
		Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("loginWithoutEmailMessage"), "Failed to verify message body.");
		// End test case
		FrameworkLogger.testEnd("verifyLoginWithoutEmailParameter");
	}

	@Test(priority = 4)
	@Owner("Ayanda Khaka")
	@Story("Search product item")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate searched product item")
	public void verifySearchProductItem() {

		// Start test
		FrameworkLogger.testStart("verifySearchProductItem");

		// Sending a post request to search the product
		FrameworkLogger.info("Sending a post request with search item as parameter to : " 
				+ ConfigReader.getProperty("searchProductEndpoint"));

		Response response = 
				given()
				.relaxedHTTPSValidation()
				.baseUri(ConfigReader.getProperty("apiBaseUrl"))
				.contentType(ContentType.URLENC)
				.formParams(UserPayload.createSearchProductPayload(ConfigReader.getProperty("searchItem")))
				.when()
				.post(ConfigReader.getProperty("searchProductEndpoint"));
		// Storing product size
		int productSize = response.jsonPath().getList("products").size();
		FrameworkLogger.info("Returned product size is : "  + productSize);

		// Logger response body
		FrameworkLogger.info(
				"Response Body:\n" +
						response.asPrettyString());

		FrameworkLogger.apiResponse(Integer.parseInt(ConfigReader.getProperty("successStatusCode")));

		// Verify Status code
		FrameworkLogger.info("Verify status code. Expected : "
				+ response.statusCode() 
				+ ", Actual : " 
				+ Integer.parseInt(ConfigReader.getProperty("successStatusCode")));

		Assert.assertEquals(response.statusCode(), 
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")), 
				"Failed to verify status code.");

		// Verify response code
		FrameworkLogger.info("Verify response code. Expected : " + 
				response.jsonPath().getInt("responseCode") +
				", Actual : " +
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")));

		Assert.assertEquals(response.jsonPath().getInt("responseCode"),
				Integer.parseInt(ConfigReader.getProperty("successStatusCode")),
				"Failed to verify response code.");

		// Verify products exists
		FrameworkLogger.info("Verify products exist : " + productSize);
		Assert.assertTrue(productSize > 0, "Product list is empty");

		// End test
		FrameworkLogger.testEnd("verifySearchProductItem");

	}

	@Test(priority = 5)
	@Owner("Ayanda Khaka")
	@Story("Return user details by email")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate return user details by email")
	public void verifyReturnUserDetailsByEmail() {

		FrameworkLogger.testStart("verifyReturnUserDetailsByEmail");

		FrameworkLogger.info("Sending get request with email query parameter endpoint : " + ConfigReader.getProperty("getUserDetailsByEmailEndpoint"));

		Response response = 
				given()
				.relaxedHTTPSValidation()
				.baseUri(ConfigReader.getProperty("apiBaseUrl"))
				.contentType(ContentType.URLENC)
				.queryParams("email", user.getEmail())
				.when()
				.get(ConfigReader.getProperty("getUserDetailsByEmailEndpoint"));

		FrameworkLogger.apiResponse(Integer.parseInt(ConfigReader.getProperty("successStatusCode")));

		FrameworkLogger.info("Response body : \n" + response.asPrettyString());

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

		FrameworkLogger.testEnd("verifyReturnUserDetailsByEmail");
	}

	@Test(priority = 6)
	@Owner("Ayanda Khaka")
	@Story("Account update")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate user account update")
	public void verifyUserAccountUpdate() {

		FrameworkLogger.testStart("verifyUserAccountUpdate");

		FrameworkLogger.info("Sending put endpoint : " + ConfigReader.getProperty("updateUserAccountEndpoint"));
		Response response = 
				given()
				.relaxedHTTPSValidation()
				.baseUri(ConfigReader.getProperty("apiBaseUrl"))
				.contentType(ContentType.URLENC)
				.formParams(UserPayload.updateUserPayload(
						ConfigReader.getProperty("updatedNameText"),
						user.getEmail(),
						user.getPassword(),
						user.getTitle(),
						user.getBirth_date(),
						user.getBirth_month(),
						user.getBirth_year(),
						user.getFirstname(),
						user.getLastname(), 
						user.getCompany(), 
						ConfigReader.getProperty("updatedAddress1Text"), 
						user.getAddress2(), 
						user.getCountry(), 
						user.getZipcode(), 
						user.getState(), 
						user.getCity(), 
						ConfigReader.getProperty("updatedMobileNumber")))
				.when()
				.put(ConfigReader.getProperty("updateUserAccountEndpoint"));

		FrameworkLogger.apiResponse(Integer.parseInt(ConfigReader.getProperty("successStatusCode")));
		
		FrameworkLogger.info("Response body : \n" + response.asPrettyString());

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

	@Test(priority = 7)
	@Owner("Ayanda Khaka")
	@Story("Delete user account")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate delete user account")
	public void verifyDeleteUserAccount() {
		
		FrameworkLogger.testStart("verifyDeleteUserAccount");
		
		FrameworkLogger.info("Sending a delete endpoint : " + ConfigReader.getProperty("deleteUserAccountEndpoint"));
		Response response = 
				given()
				.relaxedHTTPSValidation()
				.baseUri(ConfigReader.getProperty("apiBaseUrl"))
				.contentType(ContentType.URLENC)
				.formParams(UserPayload.createDeleteUserAccountPayload(user.getEmail(), user.getPassword()))

				.when()
				.delete(ConfigReader.getProperty("deleteUserAccountEndpoint"));
		FrameworkLogger.apiResponse(Integer.parseInt(ConfigReader.getProperty("successStatusCode")));

		FrameworkLogger.info("Response body : \n" + response.asPrettyString());
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

	@Test(priority = 8)
	@Owner("Ayanda Khaka")
	@Story("Invalid login")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate login with invalid login credentials")
	public void verifyLoginWithInvalidDetails() {
		
		FrameworkLogger.testStart("verifyLoginWithInvalidDetails");
		
		FrameworkLogger.info("Sending a post endpoint : " + ConfigReader.getProperty("loginEndpoint"));
		Response response =
				given()
				.relaxedHTTPSValidation()
				.baseUri(ConfigReader.getProperty("apiBaseUrl"))
				.contentType(ContentType.URLENC)
				.formParams(UserPayload.createValidLoginPayload(ConfigReader.getProperty("invalidEmail"), ConfigReader.getProperty("invalidPassword")))

				.when()
				.post(ConfigReader.getProperty("loginEndpoint"));
		
		FrameworkLogger.apiResponse(Integer.parseInt(ConfigReader.getProperty("successStatusCode")));
		
		FrameworkLogger.info("Response body : \n" + response.asPrettyString());

		//Verify status code
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify status code.");
		// Verify response status code
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("notFoundStatusCode")), "Failed to verify response code.");
		// Verify success response message
		Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("deleteNonExistingUserMessage"), "Failed to verify delete non existing user message body");
		
		FrameworkLogger.testEnd("verifyLoginWithInvalidDetails");
	}

	@Test(priority = 9)
	@Owner("Ayanda Khaka")
	@Story("Delete login")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Validate delete login")
	public void verifyDeleteLogin() {
		
		FrameworkLogger.testStart("verifyDeleteLogin");
		
		FrameworkLogger.info("Sending a delete login endpoint : " + ConfigReader.getProperty("loginEndpoint"));
		
		Response response = 
				given()
				.relaxedHTTPSValidation()
				.baseUri(ConfigReader.getProperty("apiBaseUrl"))
				.contentType(ContentType.URLENC)

				.when()
				.delete(ConfigReader.getProperty("loginEndpoint"));

		FrameworkLogger.apiResponse(Integer.parseInt(ConfigReader.getProperty("successStatusCode")));
		
		FrameworkLogger.info("Response body : \n" + response.asPrettyString());
		//Verify status code
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify status code.");
		// Verify response status code
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("methodNotSupportedStatusCode")), "Failed to verify response code.");
		// Verify method not supported response message
		Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("methodNotSupportedMessage"), "Failed to verify method not supported message body");
		
		FrameworkLogger.testEnd("verifyDeleteLogin");
	}
	
	
}