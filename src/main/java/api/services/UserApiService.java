package api.services;

import api.config.ApiRequestConfig;
import api.model.UserModel;
import api.payload.UserPayload;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import utility.AllureApiAttachment;
import utility.ConfigReader;
import utility.FrameworkLogger;


public class UserApiService {

	private UserApiService() {

	}

	public static Response createUser(UserModel user) {

		Map<String, String> requestPayload = UserPayload.createUserPayload(user.getName(), user.getEmail(), user.getPassword(),
				user.getTitle(), user.getBirth_date(), user.getBirth_month(), user.getBirth_year(), user.getFirstname(),
				user.getLastname(), user.getCompany(), user.getAddress1(), user.getAddress2(), user.getCountry(),
				user.getZipcode(), user.getState(), user.getCity(), user.getMobile_number());

		Map<String, String> allureRequestPayload = new HashMap<>(requestPayload);

		allureRequestPayload.put("password", "********");

		String requestDetails = "Method: POST\n" + "Base URL: " + ConfigReader.getProperty("api.base.url") + "\n"
				+ "Endpoint: " + ConfigReader.getProperty("createAccountEndpoint") + "\n" + "Content-Type: "
				+ ContentType.URLENC + "\n" + "Request Parameters:\n" + allureRequestPayload;

		AllureApiAttachment.attachRequest(requestDetails);

		Response response = 
				given()
				.spec(ApiRequestConfig.getRequestSpecification())
				.relaxedHTTPSValidation()
				.contentType(ContentType.URLENC)
				.formParams(requestPayload)
				.when()
				.post(ConfigReader.getProperty("createAccountEndpoint"));

		// Attach API response directly to Allure
		AllureApiAttachment.attachResponse(response.asPrettyString());
		if (response.statusCode() != 200) {

			throw new RuntimeException("User registration failed." + "\nHTTP Status: " + response.statusCode()
			+ "\nResponse:\n" + response.getBody().asString());
		}

		if (!response.getContentType().contains(ConfigReader.getProperty("contentType"))) {

			throw new RuntimeException("Expected JSON response but received: " + response.getContentType()
			+ "\nResponse:\n" + response.getBody().asString());
		}

		int responseCode = response.jsonPath().getInt("responseCode");

		if (responseCode != 201) {

			throw new RuntimeException("User creation failed." + "\nAPI Response Code: " + responseCode
					+ "\nResponse:\n" + response.getBody().asString());
		}

		return response;
	}

	@Step("Create a valid login")
	public static Response validLogin(UserModel user) {

		Map<String, String> requestPayload = 
				UserPayload.createValidLoginPayload(
						user.getEmail(),
						user.getPassword()
						);
		Map<String, String> allureRequestPayload = new HashMap<>(requestPayload);

		allureRequestPayload.put("password", "********");

		String requestDetails = "Method: POST\n" + "Base URL: " + ConfigReader.getProperty("api.base.url") + "\n" + "Endpoint: "
				+ ConfigReader.getProperty("loginEndpoint") + "\n" + "Content-Type: " + ContentType.URLENC + "\n"
				+ "Request Parameters:\n" + allureRequestPayload;

		AllureApiAttachment.attachRequest(requestDetails);

		Response response = 
				given()
				.spec(ApiRequestConfig.getRequestSpecification())
				.relaxedHTTPSValidation()
				.contentType(ContentType.URLENC)
				.formParams(requestPayload)
				.when()
				.post(ConfigReader.getProperty("loginEndpoint"));

		AllureApiAttachment.attachResponse(response.asPrettyString());

		return response;
	}

	@Step("Login without email parameter.")
	public static Response loginWithoutEmailParameter(UserModel user) {

		Map<String, String> requestPayload = UserPayload.createLoginPayloadWithoutEmailPayload(user.getPassword());

		Map<String, String> allureRequestPayload = new HashMap<>(requestPayload);

		allureRequestPayload.put("password", "********");

		String requestDetails = "Method: POST\n" + "Base URL: " + ConfigReader.getProperty("api.base.url") + "\n" + "Endpoint: "
				+ ConfigReader.getProperty("loginEndpoint") + "\n" + "Content-Type: " + ContentType.URLENC + "\n"
				+ "Request Parameters:\n" + allureRequestPayload;

		AllureApiAttachment.attachRequest(requestDetails);

		Response response = 
				given()
				.spec(ApiRequestConfig.getRequestSpecification())
				.relaxedHTTPSValidation()
				.contentType(ContentType.URLENC)
				.formParams(requestPayload)
				.when()
				.post(ConfigReader.getProperty("loginEndpoint"));

		AllureApiAttachment.attachResponse(response.asPrettyString());

		return response;
	}

	@Step("Search product item : {0}")
	public static Response SearchProductItem(String product) {

		Map<String, String> requestPayload = UserPayload.createSearchProductPayload(product);

		String requestDetails = "Method: POST\n" + "Base URL: " + ConfigReader.getProperty("api.base.url") + "\n" + "Endpoint: "
				+ ConfigReader.getProperty("searchProductEndpoint") + "\n" + "Content-Type: " + ContentType.URLENC
				+ "\n" + "Request Parameters:\n" + requestPayload;

		AllureApiAttachment.attachRequest(requestDetails);

		Response response = 
				given()
				.spec(ApiRequestConfig.getRequestSpecification())
				.relaxedHTTPSValidation()
				.contentType(ContentType.URLENC)
				.formParams(requestPayload)
				.when()
				.post(ConfigReader.getProperty("searchProductEndpoint"));

		AllureApiAttachment.attachResponse(response.asPrettyString());

		return response;

	}

	@Step("Search product item without providing product parameter")
	public static Response searchProductItemWithoutSearchProduct() {

		String requestDetails = "Method: POST\n" + "Base URL: " + ConfigReader.getProperty("api.base.url") + "\n" + "Endpoint: "
				+ ConfigReader.getProperty("searchProductEndpoint") + "\n" + "Content-Type: " + ContentType.URLENC;

		AllureApiAttachment.attachRequest(requestDetails);

		Response response = 
				given()
				.spec(ApiRequestConfig.getRequestSpecification())
				.relaxedHTTPSValidation()
				.contentType(ContentType.URLENC)
				.when()
				.post(ConfigReader.getProperty("searchProductEndpoint"));

		AllureApiAttachment.attachResponse(response.asPrettyString());

		return response;

	}

	@Step("Update to all brand list")
	public static Response updateToAllBrandList() {

		String requestDetails = "Method: PUT\n" + "Base URL: " + ConfigReader.getProperty("api.base.url") + "\n" + "Endpoint: "
				+ ConfigReader.getProperty("putToAllBrandList") + "\n" + "Content-Type: " + ContentType.URLENC;

		AllureApiAttachment.attachRequest(requestDetails);

		Response response = 
				given()
				.spec(ApiRequestConfig.getRequestSpecification())
				.relaxedHTTPSValidation()
				.contentType(ContentType.URLENC)
				.when()
				.post(ConfigReader.getProperty("putToAllBrandList"));

		AllureApiAttachment.attachResponse(response.asPrettyString());

		return response;

	}

	@Step("Post to all product list")
	public static Response postToAllProductList() {

		String requestDetails = "Method: POST\n" + "Base URL: " + ConfigReader.getProperty("api.base.url") + "\n" + "Endpoint: "
				+ ConfigReader.getProperty("postToAllProductList") + "\n" + "Content-Type: " + ContentType.URLENC;

		AllureApiAttachment.attachRequest(requestDetails);

		Response response = 
				given()
				.spec(ApiRequestConfig.getRequestSpecification())
				.relaxedHTTPSValidation()
				.contentType(ContentType.URLENC)
				.when()
				.post(ConfigReader.getProperty("postToAllProductList"));

		AllureApiAttachment.attachResponse(response.asPrettyString());

		return response;

	}

	@Step("Get user details by email.")
	public static Response getUserDetailsByEmail(String email) {

		String requestDetails = "Method: GET\n" + "Base URL: " + ConfigReader.getProperty("api.base.url") + "\n" + "Endpoint: "
				+ ConfigReader.getProperty("getUserDetailsByEmailEndpoint") + "\n" + "Content-Type: "
				+ ContentType.URLENC;

		AllureApiAttachment.attachRequest(requestDetails);

		Response response = 
				given()
				.spec(ApiRequestConfig.getRequestSpecification())
				.relaxedHTTPSValidation()
				.contentType(ContentType.URLENC)
				.queryParams("email", email)
				.when()
				.get(ConfigReader.getProperty("getUserDetailsByEmailEndpoint"));

		AllureApiAttachment.attachResponse(response.asPrettyString());

		return response;

	}

	@Step("User account update")
	public static Response userAccountUpdate(UserModel user) {

		Map<String, String> requestPayload = UserPayload.updateUserPayload(ConfigReader.getProperty("updatedNameText"), user.getEmail(),
				user.getPassword(), user.getTitle(), user.getBirth_date(), user.getBirth_month(), user.getBirth_year(),
				user.getFirstname(), user.getLastname(), user.getCompany(),
				ConfigReader.getProperty("updatedAddress1Text"), user.getAddress2(), user.getCountry(),
				user.getZipcode(), user.getState(), user.getCity(), ConfigReader.getProperty("updatedMobileNumber"));
		Map<String, String> allureRequestPayload = new HashMap<>(requestPayload);

		allureRequestPayload.put("password", "********");

		String requestDetails = "Method: PUT\n" + "Base URL: " + ConfigReader.getProperty("api.base.url") + "\n" + "Endpoint: "
				+ ConfigReader.getProperty("loginEndpoint") + "\n" + "Content-Type: " + ContentType.URLENC + "\n"
				+ "Request Parameters:\n" + allureRequestPayload;

		AllureApiAttachment.attachRequest(requestDetails);

		Response response = 
				given()
				.spec(ApiRequestConfig.getRequestSpecification())
				.relaxedHTTPSValidation()
				.contentType(ContentType.URLENC)
				.formParams(requestPayload)
				.when()
				.put(ConfigReader.getProperty("updateUserAccountEndpoint"));

		AllureApiAttachment.attachResponse(response.asPrettyString());

		return response;
	}

	@Step("Login with invalid login credentials")
	public static Response loginWithInvalidCredentials(String email, String password) {

		Map<String, String> requestPayload = UserPayload.createInvalidLoginPayload(email, password);
		Map<String, String> allureRequestPayload = new HashMap<>(requestPayload);

		allureRequestPayload.put("password", "********");

		String requestDetails = "Method: POST\n" + "Base URL: " + ConfigReader.getProperty("api.base.url") + "\n" + "Endpoint: "
				+ ConfigReader.getProperty("loginEndpoint") + "\n" + "Content-Type: " + ContentType.URLENC + "\n"
				+ "Request Parameters:\n" + allureRequestPayload;

		AllureApiAttachment.attachRequest(requestDetails);

		Response response = 
				given()
				.spec(ApiRequestConfig.getRequestSpecification())
				.relaxedHTTPSValidation()
				.contentType(ContentType.URLENC)
				.formParams(requestPayload)
				.when()
				.post(ConfigReader.getProperty("loginEndpoint"));

		AllureApiAttachment.attachResponse(response.asPrettyString());

		return response;
	}

	@Step("Deleting existing user.")
	public static Response deleteUser(UserModel user) {

		FrameworkLogger.info("Deleting user: " + user.getEmail());

		Map<String, String> requestPayload = UserPayload.createDeleteUserAccountPayload(user.getEmail(), user.getPassword());

		Map<String, String> allureRequestPayload = new HashMap<>(requestPayload);

		allureRequestPayload.put("password", "********");

		String requestDetails = "Method: DELETE\n" + "Base URL: " + ConfigReader.getProperty("api.base.url") + "\n"
				+ "Endpoint: " + ConfigReader.getProperty("deleteUserAccountEndpoint") + "\n" + "Content-Type: "
				+ ContentType.URLENC + "\n" + "Request Parameters:\n" + allureRequestPayload;

		AllureApiAttachment.attachRequest(requestDetails);

		Response response = 
				given()
				.spec(ApiRequestConfig.getRequestSpecification())
				.relaxedHTTPSValidation()
				.contentType(ContentType.URLENC)
				.formParams(requestPayload)
				.when()
				.delete(ConfigReader.getProperty("deleteUserAccountEndpoint"));

		AllureApiAttachment.attachResponse(response.asPrettyString());

		return response;

	}

	@Step("Delete login")
	public static Response deleteLogin() {

		String requestDetails = "Method: DELETE\n" + "Base URL: " + ConfigReader.getProperty("api.base.url") + "\n"
				+ "Endpoint: " + ConfigReader.getProperty("loginEndpoint") + "\n" + "Content-Type: "
				+ ContentType.URLENC + "\n";

		AllureApiAttachment.attachRequest(requestDetails);

		Response response = 
				given()
				.spec(ApiRequestConfig.getRequestSpecification())
				.relaxedHTTPSValidation()
				.contentType(ContentType.URLENC)
				.when()
				.delete(ConfigReader.getProperty("loginEndpoint"));

		AllureApiAttachment.attachResponse(response.asPrettyString());

		return response;
	}

}