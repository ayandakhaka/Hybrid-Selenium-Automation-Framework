package pages;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utility.BasePage;
import utility.FrameworkLogger;

public class ContactUsPage extends BasePage {

	public ContactUsPage(WebDriver driver) {
		super(driver);
	}

	private By contactUsHeaderText = By.xpath("//h2[@class='title text-center']");
	private By nameInput = By.xpath("//input[@class='form-control' and @data-qa='name']");
	private By emailInput = By.xpath("//input[@data-qa='email' and @placeholder='Email']");
	private By subjectInput = By.xpath("//input[@class='form-control' and @placeholder='Subject']");
	private By messageInput = By.xpath("//textarea[@placeholder='Your Message Here' and @id='message']");
	private By fileUpload = By.name("upload_file");
	private By submitButton = By.xpath("//input[@class='btn btn-primary pull-left submit_form' and @data-qa='submit-button']");
	private By contactUsSuccessMessage = By.xpath("//div[@class='status alert alert-success']");

	public boolean validateContactUsHeaderText(String text) {
		return actions.validateText(contactUsHeaderText, text, "Validating contact us header text");
	}



	private ContactUsPage uploadFile() {

		Path filePath = Paths.get(
				"src", "test", "resources", "file",
				"AHT QA Engineer Senior Automation Requisition JD.pdf"
				).toAbsolutePath().normalize();

		if (!Files.isRegularFile(filePath)) {
			throw new RuntimeException(
					"Upload file does not exist: " + filePath
					);
		}

		actions.typeText(fileUpload, filePath.toString());

		return this;
	}



	public ContactUsPage contactUs(String name, String email, String subject, String message) {

		actions
		.typeText(nameInput, name)
		.typeText(emailInput, email)
		.typeText(subjectInput, subject)
		.typeText(messageInput, message);
		this.uploadFile();
		actions
		.click(submitButton)
		.acceptAlert();
		return this;
	}

	public boolean validateContactUsSuccessMessage(String text) {
		return actions.validateText(contactUsSuccessMessage, text, "Validating contact us success message");
	}
	
	public String getEmailValidationMessage() {
		FrameworkLogger.info("Validation message : " + actions.getValidationMessage(emailInput, "validationMessage"));
		return actions.getValidationMessage(emailInput, "validationMessage");
	}
	
	public ContactUsPage clickSubmitButton() {
		actions.click(submitButton);
		return this;
	}

}
