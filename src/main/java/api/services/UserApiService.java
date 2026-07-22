package api.services;


import api.helpers.UserDataHelper;
import api.model.UserModel;
import api.payload.UserPayload;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import utility.ConfigReader;

public class UserApiService {

	@Step("Register a random user.")
	public static UserModel registerRandomUser() {

		// Generate a random user
		UserModel user = UserDataHelper.generateUserData();

		// Register the user via API

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
				.post(ConfigReader.getProperty("createAccountEndpoint"));

		if (response.statusCode() != 200
				|| response.jsonPath().getInt("responseCode") != 201) {

			throw new RuntimeException(
					"User registration failed.\nResponse:\n"
							+ response.asPrettyString());
		}

		return user;

	}
	
	@Step("Delete existing user")
	public static void deleteUser(UserModel user) {

		given()
		.relaxedHTTPSValidation()
		.baseUri(ConfigReader.getProperty("apiBaseUrl"))
		.contentType(ContentType.URLENC)
		.formParams(
				UserPayload.createDeleteUserAccountPayload(
						user.getEmail(),
						user.getPassword()))
		.when()
		.delete(ConfigReader.getProperty("deleteUserAccountEndpoint"));
	}

}