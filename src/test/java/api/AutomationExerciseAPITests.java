package api;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import api.helpers.UserDataHelper;
import api.model.UserModel;
import api.payload.UserPayload;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import io.restassured.response.Response;

import org.testng.Assert;

import io.restassured.http.ContentType;
import utility.ConfigReader;
import utility.FrameworkLogger;
public class AutomationExerciseAPITests {

	private UserModel user;

	@BeforeClass
	public void setupData() {
		System.setProperty("https.protocols", "TLSv1.2");
		UserDataHelper.generateUserData();
		user = UserDataHelper.readUserData();
	}

	@Test(groups = "Register user account", priority = 1)
	public void verifyRegisterUserAccount() {

		// Start test
		FrameworkLogger.testStart("verifyRegisterUserAccount");

		FrameworkLogger.info("Register user account by sending a post request to: " + ConfigReader.getProperty("createAccountEndpoint"));

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
				// Act to the endpoint
				.when()
				.post(ConfigReader.getProperty("createAccountEndpoint"));
		// Logger out console for status code
		FrameworkLogger.apiResponse(response.getStatusCode());
		// Logger response body output console
		FrameworkLogger.info(
				"Response Body:\n" +
						response.asPrettyString());

		// Verify status code
		FrameworkLogger.info(
				"Verifying Status Code. Expected: "
						+ ConfigReader.getProperty("successStatusCode")
						+ ", Actual: "
						+ response.getStatusCode());


		Assert.assertEquals(response.getStatusCode(), 
				Integer.parseInt(
						ConfigReader.getProperty("successStatusCode")),
				"Failed to verify status code");

		// Verify response code
		FrameworkLogger.info(
				"Verifying Response Code. Expected: "
						+ ConfigReader.getProperty("methodNotSupportedStatusCode")
						+ ", Actual: "
						+ response.jsonPath().getInt("responseCode"));

		Assert.assertEquals(response.jsonPath().getInt("responseCode"),
				Integer.parseInt(ConfigReader.getProperty("createdStatusCode")),
				"Failed to verify success response code");
		// Verify success message
		FrameworkLogger.info(
				"Verifying Message body. Expected: "
						+ ConfigReader.getProperty("userCreatedMessage")
						+ ", Actual: "
						+ response.jsonPath().getString("message"));

		Assert.assertEquals(response.jsonPath().getString("message"), 
				ConfigReader.getProperty("userCreatedMessage"), 
				"Failed to verify success message body.");
		// End test
		FrameworkLogger.testEnd("verifyRegisterUserAccount");
	}

	@Test(dependsOnGroups = "Register user account", priority = 2)
	public void verifyLoginWithValidLoginDetails() {
		FrameworkLogger.testStart("verifyLoginWithValidLoginDetails");

		FrameworkLogger.info("Sending a login post request : " + ConfigReader.getProperty("loginEndpoint"));
		Response response = 
				given()
				.relaxedHTTPSValidation()
				.baseUri(ConfigReader.getProperty("apiBaseUrl"))
				.contentType(ContentType.URLENC)
				.formParams(UserPayload.createValidLoginPayload(user.getEmail(), user.getPassword()))

				.when()
				.post(ConfigReader.getProperty("loginEndpoint"));

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

		FrameworkLogger.testEnd("verifyLoginWithValidLoginDetails");
	}

	@Test(dependsOnGroups = "Register user account", priority = 3)
	public void verifyLoginWithoutEmailParamedter() {

		FrameworkLogger.testStart("verifyLoginWithoutEmailParamedter");

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
		FrameworkLogger.testEnd("verifyLoginWithoutEmailParamedter");
	}

	@Test(dependsOnGroups = "Register user account", priority = 4)
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

	@Test(dependsOnGroups = "Register user account", priority = 5)
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

	@Test(dependsOnGroups = "Register user account", priority = 6)
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
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to validate status code.");
		// Verify response status code
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify response status code");
		// Verify success update message
		Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("userUpdatedMessage"), "Failed to validate update message");	
		
		FrameworkLogger.testEnd("verifyUserAccountUpdate");
	}

	@Test(dependsOnGroups = "Register user account", priority = 7)
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
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify success status code.");
		// Verify response status code
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify response status code.");
		// Verify success delete message
		Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("userDeletedMessage"), "Failed to verify delete success message.");
		
		FrameworkLogger.testEnd("verifyDeleteUserAccount");
	}

	@Test(dependsOnGroups = "Register user account", priority = 8)
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

	@Test(dependsOnGroups = "Register user account", priority = 9)
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