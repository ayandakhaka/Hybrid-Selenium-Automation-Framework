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
        
        // Verify status code
        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify status code");
        
        // Verify response code
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("createdStatusCode")), "Failed to verify success response code");
        
        // Verify success message
        Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("userCreatedMessage"), "Failed to verify success message body.");
    }
    
    @Test(dependsOnGroups = "Register user account", priority = 2)
    public void verifyLoginWithValidLoginDetails() {
    	
    	Response response = 
    			given()
	    			.relaxedHTTPSValidation()
	                .baseUri(ConfigReader.getProperty("apiBaseUrl"))
	                .contentType(ContentType.URLENC)
	                .formParams(UserPayload.createValidLoginPayload(user.getEmail(), user.getPassword()))
    	
    			.when()
    				.post(ConfigReader.getProperty("loginEndpoint"));
    	// Verify Status code
    	Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify status code.");
    	
    	// Verify Response code
    	Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify success response code.");
    	
    	// Verify success message
    	Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("userExistMessage"), "Failed to verify success message body.");
    }
    
    @Test(dependsOnGroups = "Register user account", priority = 3)
    public void verifyWithoutEmail() {
    	
    	Response response = 
    			given()
	    			.relaxedHTTPSValidation()
	                .baseUri(ConfigReader.getProperty("apiBaseUrl"))
	                .contentType(ContentType.URLENC)
	                .formParams(UserPayload.createLoginPayloadWithoutEmailPayload(user.getPassword()))
	                
	             .when()
	             	.post(ConfigReader.getProperty("loginEndpoint"));
    	
    	// Verify Status code
    	Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify status code.");
    	
    	// Verify response code
    	Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("badRequestStatusCode")), "Failed to verify response code.");
    	
    	// Verify message body
    	Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("loginWithoutEmailMessage"), "Failed to verify message body.");
    }
    
    @Test(dependsOnGroups = "Register user account", priority = 4)
    public void verifySearchProductItem() {
    	
    	Response response = 
    			given()
	    			.relaxedHTTPSValidation()
	                .baseUri(ConfigReader.getProperty("apiBaseUrl"))
	                .contentType(ContentType.URLENC)
	                .formParams(UserPayload.createSearchProductPayload(ConfigReader.getProperty("searchItem")))
                .when()
                	.post(ConfigReader.getProperty("searchProductEndpoint"));
    	
    	int productSize = response.jsonPath().getList("products").size();
    	
    	
    	// Verify Status code
    	Assert.assertEquals(response.statusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify status code.");
    	
    	// Verify response code
    	Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify response code.");
    	
    	// Verify products exists
    	Assert.assertTrue(productSize > 0, "Product list is empty");
                 				
    }
    
    @Test(dependsOnGroups = "Register user account", priority = 5)
    public void verifyReturnUserDetailsByEmail() {
    	
    	Response response = 
    			given()
    				.relaxedHTTPSValidation()
    				.baseUri(ConfigReader.getProperty("apiBaseUrl"))
    				.contentType(ContentType.URLENC)
    				.queryParams("email", user.getEmail())
    			.when()
    				.get(ConfigReader.getProperty("getUserDetailsByEmailEndpoint"));
    				
    	System.out.println("Response : " + response.asPrettyString());
    	
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
    }
    
    @Test(dependsOnGroups = "Register user account", priority = 6)
    public void verifyUserAccountUpdate() {
        
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
        			
        	System.out.println("Response : " + response.asPrettyString());
        	// Verify status code
        	Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to validate status code.");
        	// Verify response status code
        	Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify response status code");
        	// Verify success update message
        	Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("userUpdatedMessage"), "Failed to validate update message");	
    }
    
    @Test(dependsOnGroups = "Register user account", priority = 7)
    public void verifyDeleteUserAccount() {
    	
    	Response response = 
    			given()
    				.relaxedHTTPSValidation()
    				.baseUri(ConfigReader.getProperty("apiBaseUrl"))
    				.contentType(ContentType.URLENC)
    				.formParams(UserPayload.createDeleteUserAccountPayload(user.getEmail(), user.getPassword()))
    				
    			.when()
    				.delete(ConfigReader.getProperty("deleteUserAccountEndpoint"));
    	
    	System.out.println("Response : " + response.asPrettyString());
    	// Verify status code
    	Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify success status code.");
    	// Verify response status code
    	Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify response status code.");
    	// Verify success delete message
    	Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("userDeletedMessage"), "Failed to verify delete success message.");
    }
    
    @Test(dependsOnGroups = "Register user account", priority = 8)
    public void verifyLoginWithInvalidDetails() {
    	
    	Response response =
    			given()
    				.relaxedHTTPSValidation()
    				.baseUri(ConfigReader.getProperty("apiBaseUrl"))
    				.contentType(ContentType.URLENC)
    				.formParams(UserPayload.createValidLoginPayload(ConfigReader.getProperty("invalidEmail"), ConfigReader.getProperty("invalidPassword")))
    				
    			.when()
    				.post(ConfigReader.getProperty("loginEndpoint"));
    	
    	System.out.println("Response : " + response.asPrettyString());
    	//Verify status code
    	Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify status code.");
    	// Verify response status code
    	Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("notFoundStatusCode")), "Failed to verify response code.");
    	// Verify success response message
    	Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("deleteNonExistingUserMessage"), "Failed to verify delete non existing user message body");
    }
    
    @Test(dependsOnGroups = "Register user account", priority = 9)
    public void verifyDeleteLogin() {
    	Response response = 
    			given()
    				.relaxedHTTPSValidation()
    				.baseUri(ConfigReader.getProperty("apiBaseUrl"))
    				.contentType(ContentType.URLENC)
    				
    			.when()
    				.delete(ConfigReader.getProperty("loginEndpoint"));
    				
    	System.out.println("Response : " + response.asPrettyString());
    	//Verify status code
    	Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")), "Failed to verify status code.");
    	// Verify response status code
    	Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("methodNotSupportedStatusCode")), "Failed to verify response code.");
    	// Verify method not supported response message
    	Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("methodNotSupportedMessage"), "Failed to verify method not supported message body");
    }
    
    
    
}