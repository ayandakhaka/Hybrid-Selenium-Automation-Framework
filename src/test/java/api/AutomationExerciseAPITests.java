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
        Assert.assertEquals(response.jsonPath().getString("responseCode"), Integer.parseInt(ConfigReader.getProperty("createdStatusCode")));
        
        // Verify success message
        Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("userCreatedMessage"));
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
    	Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")));
    	
    	// Verify Response code
    	Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("successStatusCode")));
    	
    	// Verify success message
    	Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("userExistMessage"));
    }
    
    @Test(dependsOnGroups = "Register user account", priority = 3)
    public void verifyWithoutEmail() {
    	
    	Response response = 
    			given()
	    			.relaxedHTTPSValidation()
	                .baseUri(ConfigReader.getProperty("apiBaseUrl"))
	                .contentType(ContentType.URLENC)
	                .formParams(UserPayload.createLoginPayloadWithoutEmail(user.getPassword()))
	                
	             .when()
	             	.post(ConfigReader.getProperty("loginEndpoint"));
    	
    	// Verify Status code
    	Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")));
    	
    	// Verify response code
    	Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("badRequestStatusCode")));
    	
    	// Verify message body
    	Assert.assertEquals(response.jsonPath().getString("message"), ConfigReader.getProperty("loginWithoutEmailMessage"));
    }
    
    @Test(dependsOnGroups = "Register user account", priority = 4)
    public void verifySearchProductItem() {
    	
    	Response response = 
    			given()
	    			.relaxedHTTPSValidation()
	                .baseUri(ConfigReader.getProperty("apiBaseUrl"))
	                .contentType(ContentType.URLENC)
	                .formParams(UserPayload.createSearchProduct(ConfigReader.getProperty("searchItem")))
                .when()
                	.post(ConfigReader.getProperty("searchProductEndpoint"));
    	
    	int productSize = response.jsonPath().getList("products").size();
    	
    	
    	// Verify Status code
    	Assert.assertEquals(response.statusCode(), Integer.parseInt(ConfigReader.getProperty("successStatusCode")));
    	
    	// Verify response code
    	Assert.assertEquals(response.jsonPath().getInt("responseCode"), Integer.parseInt(ConfigReader.getProperty("successStatusCode")));
    	
    	// Verify products exists
    	Assert.assertTrue(productSize > 0, "Product list is empty");
                 				
    }
    
}