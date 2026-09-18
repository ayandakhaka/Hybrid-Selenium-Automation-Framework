package api.testdata;

import api.helpers.UserDataHelper;
import api.model.UserModel;
import api.services.UserApiService;
import io.restassured.response.Response;
import utility.ConfigReader;
import utility.FrameworkLogger;

public class TestDataManager {

	private static TestDataManager instance;

	private UserModel user;
	private boolean userCreated = false;

	private TestDataManager() {

		// This prevent external instantiation
	}

	public static synchronized TestDataManager getInstance() {

		if(instance == null) {

			instance = new TestDataManager();
		}

		return instance;
	}

	/**
	 * Generates and create a shared test user once
	 */
	public synchronized void initializeUser() {

		if(userCreated && user !=null) {

			FrameworkLogger.info(
					"Test user already already exist. Reusing existing user: "
							+ user.getEmail());

			return;
		}

		FrameworkLogger.info("Generating test user data..");

		user = UserDataHelper.generateUserData();

		FrameworkLogger.info("Creating test user through API: " 
				+ user.getEmail());

		Response response = UserApiService.createUser(user);

		FrameworkLogger.apiResponse(response.getStatusCode());

		FrameworkLogger.info("Create user response:\n" 
				+ response.asPrettyString());

		int expectedStatusCode = Integer.parseInt(ConfigReader.getProperty("successStatusCode"));

		if (response.getStatusCode() != expectedStatusCode) {
			throw new RuntimeException(
					"Failed to create shared test user. " 
							+ "Status code: " 
							+ response.getStatusCode() 
							+
							", Response: " 
							+ response.asPrettyString()); 
		}

		userCreated = true;

		FrameworkLogger.info("Shared test user created successfully: " + user.getEmail());
	}

	/**
	 * Returns shared test user
	 */
	public UserModel gerUser() {

		if(user == null) {

			throw new IllegalStateException("Test user has not been initialized. " 
					+ "Call initializeUser() first.");
		}

		return user;
	}

	/**
	 * Indicate that test user created
	 */
	public boolean isUserCreated() {
		return userCreated;
	}

	/**
	 * Deletes shared test user
	 */
	public synchronized void cleanupUser() {

		if (!userCreated || user == null) {
			FrameworkLogger.info( "No shared test user to clean up.");
			return; 
		}

		FrameworkLogger.info( "Cleaning up shared test user: " + user.getEmail());

		Response response = UserApiService.deleteUser(user);

		FrameworkLogger.apiResponse(response.getStatusCode());

		FrameworkLogger.info("Delete user response:\n" 
				+ response.asPrettyString());
		
		int statusCode = Integer.parseInt(ConfigReader.getProperty("successStatusCode"));

		if(response.getStatusCode() == statusCode) {

			FrameworkLogger.info( "Shared test user deleted successfully.");
			user = null;
			userCreated = false;

		} else {
			FrameworkLogger.info( "Unable to delete shared test user. " 
					+ "Status code: " 
					+ response.getStatusCode());
		}

	}
}
