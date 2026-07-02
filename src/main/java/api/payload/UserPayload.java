package api.payload;

import java.util.HashMap;
import java.util.Map;

public class UserPayload {
	
	public static Map<String, String> payload;
	
	public static Map<String, String> createUserPayload(
			String nameText, 
			String emailText, 
			String passwordText, 
			String titleText, 
			String birthDateText, 
			String birthMonthText,
			String birthYearText, 
			String firstNameText, 
			String lastNameText, 
			String companyText, 
			String address1Text, 
			String address2Text, 
			String countryText, 
			String zipCodeText, 
			String stateText, 
			String cityText, 
			String mobileNumberText
			) {
		
		payload = new HashMap<>();
		
		payload.put("name", nameText);
		payload.put("email", emailText);
		payload.put("password", passwordText);
		payload.put("title", titleText);
		payload.put("birth_date", birthDateText);
		payload.put("birth_month", birthMonthText);
		payload.put("birth_year", birthYearText);
		payload.put("firstname", firstNameText);
		payload.put("lastname", lastNameText);
		payload.put("company", companyText);
		payload.put("address1", address1Text);
		payload.put("address2", address2Text);
		payload.put("country", countryText);
		payload.put("zipcode", zipCodeText);
		payload.put("state", stateText);
		payload.put("city", cityText);
		payload.put("mobile_number", mobileNumberText);
		
		return payload;	
	}
	
	public static Map<String, String> updateUserPayload(
			String nameText, 
			String emailText, 
			String passwordText, 
			String titleText, 
			String birthDateText, 
			String birthMonthText,
			String birthYearText, 
			String firstNameText, 
			String lastNameText, 
			String companyText, 
			String address1Text, 
			String address2Text, 
			String countryText, 
			String zipCodeText, 
			String stateText, 
			String cityText, 
			String mobileNumberText
			) {
		
		payload = new HashMap<>();
		
		payload.put("name", nameText);
		payload.put("email", emailText);
		payload.put("password", passwordText);
		payload.put("title", titleText);
		payload.put("birth_date", birthDateText);
		payload.put("birth_month", birthMonthText);
		payload.put("birth_year", birthYearText);
		payload.put("firstname", firstNameText);
		payload.put("lastname", lastNameText);
		payload.put("company", companyText);
		payload.put("address1", address1Text);
		payload.put("address2", address2Text);
		payload.put("country", countryText);
		payload.put("zipcode", zipCodeText);
		payload.put("state", stateText);
		payload.put("city", cityText);
		payload.put("mobile_number", mobileNumberText);
		
		return payload;	
	}
	
	public static Map<String, String> createValidLoginPayload(String email, String password) {
		
		payload = new HashMap<>();
		
		payload.put("email", email);
		payload.put("password", password);
		
		return payload;
	}
	
	public static Map<String, String> createInvalidLoginPayload(String email, String password) {
		
		payload = new HashMap<>();
		
		payload.put("email", email);
		payload.put("password", password);
		
		return payload;
	}
	
	public static Map<String, String> createLoginPayloadWithoutEmailPayload(String password) {
		
		payload = new HashMap<>();
		
		payload.put("password", password);
		
		return payload;
	}
	
	public static Map<String, String> createSearchProductPayload(String searchItem) {
		
		payload = new HashMap<>();
		
		payload.put("search_product", searchItem);
		
		return payload;
	}
	
	public static Map<String, String> createDeleteUserAccountPayload(String email, String password) {
		
		payload = new HashMap<>();
		
		payload.put("email", email);
		payload.put("password", password);
		
		return payload;
	}
	
	
}
