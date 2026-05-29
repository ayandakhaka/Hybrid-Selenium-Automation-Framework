package locators;

import api.helpers.UserDataHelper;
import api.model.UserModel;

public class UserRegistrationAccountTest {
	
	private static UserModel userModel;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Clear old json and create a new
		
		UserDataHelper.generateUserData();
		
		// Reads fresh json
		
		userModel = UserDataHelper.readUserData();
		
		System.out.println("Generated email : " + userModel.getEmail());
	}

}
