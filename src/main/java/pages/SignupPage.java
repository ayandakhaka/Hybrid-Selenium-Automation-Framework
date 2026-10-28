package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utility.BasePage;
import utility.FrameworkLogger;

public class SignupPage extends BasePage {
	
	public SignupPage(WebDriver driver) {
		
		super(driver);
	}
	
	private By signupHeaderText = By.xpath("//h2[@class='title text-center']");
	private By passwordInput = By.id("password");
	private By maleTitle = By.id("id_gender1");
	private By femaleTitle = By.id("id_gender2");
	private By birthDate = By.id("days");
	private By birthYear = By.id("years");
	private By birthMonth = By.id("months");
	private By firstNameInput = By.id("first_name");
	private By lastNameInput = By.id("last_name");
	private By companyInput = By.id("company");
	private By address1Input = By.id("address1");
	private By address2Input = By.id("address2");
	private By countryDropdown = By.id("country");
	private By zipcodeInput = By.id("zipcode");
	private By stateInput = By.id("state");
	private By cityInput = By.id("city");
	private By mobileNumber = By.id("mobile_number");
	private By createAccountButton = By.xpath("//button[@data-qa='create-account' and @class='btn btn-default']");
	private By newsLetterCheckbox = By.id("newsletter");
	private By specialOffers = By.id("optin");
	private By emailErrorText = By.xpath("//p[normalize-space()='Email Address already exist!']");
	
	public SignupPage registerUser(
			String title,
			String password,
			String date,
			String birthMonth,
			String birthYear,
			String firstName,
			String lastName,
			String company,
			String address1,
			String address2,
			String country,
			String state,
			String city,
			String zipcode,
			String telephoneNumber
			) {
		
		this.selectTitle(title);
		actions
			.typeText(passwordInput, password);
		this.selectDateOfBirth(date, birthMonth, birthYear);
		actions
			.click(newsLetterCheckbox)
			.click(specialOffers)
			.typeText(firstNameInput, firstName)
			.typeText(lastNameInput, lastName)
			.typeText(companyInput, company)
			.typeText(address1Input, address1)
			.typeText(address2Input, address2);
		actions.scrollToElement(countryDropdown);
		actions.selectByVisibleText(countryDropdown, country);
		actions
			.typeText(stateInput, state)
			.typeText(cityInput, city)
			.typeText(zipcodeInput, zipcode)
			.typeText(mobileNumber, telephoneNumber)
			.click(createAccountButton);
		
		return this;
	}
	
	public void selectDateOfBirth(String date, String month, String year) {
		actions.selectByVisibleText(birthDate, date);
		actions.selectByVisibleText(birthMonth, month);
		actions.selectByVisibleText(birthYear, year);
	}
	
	private void selectTitle(String title) {
		
		switch(title) {
		case "Mr" :
			actions.click(maleTitle);
			break;
		case "Mrs" :
			actions.click(femaleTitle);
			break;
		default:
			FrameworkLogger.info("Invalid gender");
		
		}
	}
	
	public boolean validateEnterAccountInformationText(String headerText) {
		return actions.validateText(signupHeaderText, headerText, "Validate signup header text");
	}
	
	public boolean validateEmailErrorMessage(String emailError) {
		return actions.validateText(emailErrorText, emailError, "Validating email error message");
	}
	
	

}
