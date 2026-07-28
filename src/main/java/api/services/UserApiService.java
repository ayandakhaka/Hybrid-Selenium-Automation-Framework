package api.services;


import api.helpers.UserDataHelper;
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

	private static UserModel user;
	private static Map<String, String> requestPayload;
	private static Map<String, String> allureRequestPayload;
	private static String requestDetails;
	private static Response response;

	@Step("Register a random user.")
	public static UserModel registerRandomUser() {

		// Generate a random user
		user = UserDataHelper.generateUserData();

		// Register the user via API

		Map<String, String> requestPayload =
				UserPayload.createUserPayload(
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
						);

		allureRequestPayload =
				new HashMap<>(requestPayload);

		allureRequestPayload.put(
				"password",
				"********"
				);

		requestDetails =
				"Method: POST\n"
						+ "Base URL: "
						+ ConfigReader.getProperty("apiBaseUrl")
						+ "\n"
						+ "Endpoint: "
						+ ConfigReader.getProperty("createAccountEndpoint")
						+ "\n"
						+ "Content-Type: "
						+ ContentType.URLENC
						+ "\n"
						+ "Request Parameters:\n"
						+ allureRequestPayload;

		AllureApiAttachment.attachRequest(
				requestDetails
				);

		response = 
				given()
				.relaxedHTTPSValidation()
				.baseUri(ConfigReader.getProperty("apiBaseUrl"))
				.contentType(ContentType.URLENC)
				.formParams(requestPayload)
				.when()
				.post(ConfigReader.getProperty("createAccountEndpoint"));

		// Attach API response directly to Allure
		AllureApiAttachment.attachResponse(
				response.asPrettyString()
				);
		if (response.statusCode() != 200
				|| response.jsonPath().getInt("responseCode") != 201) {

			throw new RuntimeException(
					"User registration failed.\nResponse:\n"
							+ response.asPrettyString());
		}

		return user;

	}

	@Step("User ccount update")
	public static Response userAccountUpdate(UserModel user) {

		requestPayload = UserPayload.updateUserPayload(
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
				ConfigReader.getProperty("updatedMobileNumber"));
		allureRequestPayload =
				new HashMap<>(requestPayload);

		allureRequestPayload.put(
				"password",
				"********"
				);

		requestDetails =
				"Method: PUT\n"
						+ "Base URL: "
						+ ConfigReader.getProperty("apiBaseUrl")
						+ "\n"
						+ "Endpoint: "
						+ ConfigReader.getProperty("loginEndpoint")
						+ "\n"
						+ "Content-Type: "
						+ ContentType.URLENC
						+ "\n"
						+ "Request Parameters:\n"
						+ allureRequestPayload;

		AllureApiAttachment.attachRequest(
				requestDetails
				);

		response =
				given()
				.relaxedHTTPSValidation()
				.baseUri(
						ConfigReader.getProperty("apiBaseUrl")
						)
				.contentType(ContentType.URLENC)
				.formParams(requestPayload)
				.when()
				.put(
						ConfigReader.getProperty(
								"updateUserAccountEndpoint"
								)
						);

		AllureApiAttachment.attachResponse(
				response.asPrettyString()
				);

		return response;
	}

	@Step("Create a valid login")
	public static Response validLogin(UserModel user) {

		requestPayload = UserPayload.createValidLoginPayload(
				user.getEmail(),
				user.getPassword()
				);
		allureRequestPayload =
				new HashMap<>(requestPayload);

		allureRequestPayload.put(
				"password",
				"********"
				);

		requestDetails =
				"Method: POST\n"
						+ "Base URL: "
						+ ConfigReader.getProperty("apiBaseUrl")
						+ "\n"
						+ "Endpoint: "
						+ ConfigReader.getProperty("loginEndpoint")
						+ "\n"
						+ "Content-Type: "
						+ ContentType.URLENC
						+ "\n"
						+ "Request Parameters:\n"
						+ allureRequestPayload;

		AllureApiAttachment.attachRequest(
				requestDetails
				);

		response =
				given()
				.relaxedHTTPSValidation()
				.baseUri(
						ConfigReader.getProperty("apiBaseUrl")
						)
				.contentType(ContentType.URLENC)
				.formParams(requestPayload)
				.when()
				.post(
						ConfigReader.getProperty(
								"loginEndpoint"
								)
						);

		AllureApiAttachment.attachResponse(
				response.asPrettyString()
				);

		return response;
	}

	public static Response loginWithInvalidCredentials(String email, String password) {

		requestPayload = UserPayload.createInvalidLoginPayload(
				email,
				password
				);
		allureRequestPayload =
				new HashMap<>(requestPayload);

		allureRequestPayload.put(
				"password",
				"********"
				);

		requestDetails =
				"Method: POST\n"
						+ "Base URL: "
						+ ConfigReader.getProperty("apiBaseUrl")
						+ "\n"
						+ "Endpoint: "
						+ ConfigReader.getProperty("loginEndpoint")
						+ "\n"
						+ "Content-Type: "
						+ ContentType.URLENC
						+ "\n"
						+ "Request Parameters:\n"
						+ allureRequestPayload;

		AllureApiAttachment.attachRequest(
				requestDetails
				);

		response =
				given()
				.relaxedHTTPSValidation()
				.baseUri(
						ConfigReader.getProperty("apiBaseUrl")
						)
				.contentType(ContentType.URLENC)
				.formParams(requestPayload)
				.when()
				.post(
						ConfigReader.getProperty(
								"loginEndpoint"
								)
						);

		AllureApiAttachment.attachResponse(
				response.asPrettyString()
				);

		return response;
	}

	@Step("Login without email parameter.")
	public static Response loginWithoutEmailParameter(UserModel user) {

		requestPayload = UserPayload.createLoginPayloadWithoutEmailPayload(user.getPassword());

		allureRequestPayload =
				new HashMap<>(requestPayload);

		allureRequestPayload.put(
				"password",
				"********"
				);

		requestDetails =
				"Method: POST\n"
						+ "Base URL: "
						+ ConfigReader.getProperty("apiBaseUrl")
						+ "\n"
						+ "Endpoint: "
						+ ConfigReader.getProperty("loginEndpoint")
						+ "\n"
						+ "Content-Type: "
						+ ContentType.URLENC
						+ "\n"
						+ "Request Parameters:\n"
						+ allureRequestPayload;

		AllureApiAttachment.attachRequest(
				requestDetails
				);

		response =
				given()
				.relaxedHTTPSValidation()
				.baseUri(
						ConfigReader.getProperty("apiBaseUrl")
						)
				.contentType(ContentType.URLENC)
				.formParams(requestPayload)
				.when()
				.post(
						ConfigReader.getProperty(
								"loginEndpoint"
								)
						);

		AllureApiAttachment.attachResponse(
				response.asPrettyString()
				);

		return response;
	}

	@Step("Search product item : {0}")
	public static Response SearchProductItem(String product) {

		requestPayload = UserPayload.createSearchProductPayload(product);

		requestDetails =
				"Method: POST\n"
						+ "Base URL: "
						+ ConfigReader.getProperty("apiBaseUrl")
						+ "\n"
						+ "Endpoint: "
						+ ConfigReader.getProperty("searchProductEndpoint")
						+ "\n"
						+ "Content-Type: "
						+ ContentType.URLENC
						+ "\n"
						+ "Request Parameters:\n"
						+ requestPayload;

		AllureApiAttachment.attachRequest(
				requestDetails
				);

		response =
				given()
				.relaxedHTTPSValidation()
				.baseUri(
						ConfigReader.getProperty("apiBaseUrl")
						)
				.contentType(ContentType.URLENC)
				.formParams(requestPayload)
				.when()
				.post(
						ConfigReader.getProperty(
								"searchProductEndpoint"
								)
						);

		AllureApiAttachment.attachResponse(
				response.asPrettyString()
				);

		return response;

	}

	@Step("Get user details by email.")
	public static Response getUserDetailsByEmail(String email) {

		requestDetails =
				"Method: GET\n"
						+ "Base URL: "
						+ ConfigReader.getProperty("apiBaseUrl")
						+ "\n"
						+ "Endpoint: "
						+ ConfigReader.getProperty("getUserDetailsByEmailEndpoint")
						+ "\n"
						+ "Content-Type: "
						+ ContentType.URLENC;

		AllureApiAttachment.attachRequest(
				requestDetails
				);

		response =
				given()
				.relaxedHTTPSValidation()
				.baseUri(
						ConfigReader.getProperty("apiBaseUrl")
						)
				.contentType(ContentType.URLENC)
				.queryParams("email", email)
				.when()
				.get(
						ConfigReader.getProperty(
								"getUserDetailsByEmailEndpoint"
								)
						);

		AllureApiAttachment.attachResponse(
				response.asPrettyString()
				);
		
		return response;

	}

	@Step("Delete existing user")
	public static Response deleteUser(UserModel user) {

		requestPayload = UserPayload.createDeleteUserAccountPayload(user.getEmail(), user.getPassword());

		allureRequestPayload =
				new HashMap<>(requestPayload);

		allureRequestPayload.put(
				"password",
				"********"
				);

		requestDetails =
				"Method: DELETE\n"
						+ "Base URL: "
						+ ConfigReader.getProperty("apiBaseUrl")
						+ "\n"
						+ "Endpoint: "
						+ ConfigReader.getProperty("deleteUserAccountEndpoint")
						+ "\n"
						+ "Content-Type: "
						+ ContentType.URLENC
						+ "\n"
						+ "Request Parameters:\n"
						+ allureRequestPayload;

		AllureApiAttachment.attachRequest(
				requestDetails
				);

		response =
				given()
				.relaxedHTTPSValidation()
				.baseUri(
						ConfigReader.getProperty("apiBaseUrl")
						)
				.contentType(ContentType.URLENC)
				.formParams(requestPayload)
				.when()
				.delete(
						ConfigReader.getProperty(
								"deleteUserAccountEndpoint"
								)
						);

		AllureApiAttachment.attachResponse(
				response.asPrettyString()
				);

		return response;
	}

	public static Response deleteLogin() {

		requestDetails =
				"Method: DELETE\n"
						+ "Base URL: "
						+ ConfigReader.getProperty("apiBaseUrl")
						+ "\n"
						+ "Endpoint: "
						+ ConfigReader.getProperty("loginEndpoint")
						+ "\n"
						+ "Content-Type: "
						+ ContentType.URLENC
						+ "\n";


		AllureApiAttachment.attachRequest(
				requestDetails
				);

		response =
				given()
				.relaxedHTTPSValidation()
				.baseUri(
						ConfigReader.getProperty("apiBaseUrl")
						)
				.contentType(ContentType.URLENC)
				.when()
				.delete(
						ConfigReader.getProperty(
								"loginEndpoint"
								)
						);

		AllureApiAttachment.attachResponse(
				response.asPrettyString()
				);

		return response;
	}

}