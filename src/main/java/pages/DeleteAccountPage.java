package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utility.BasePage;

public class DeleteAccountPage extends BasePage {

	public DeleteAccountPage(WebDriver driver) {
		super(driver);
	}
	
	private By deleteAccountHeaderText = By.xpath("//h2[@class='title text-center' and @data-qa='account-deleted']");
	
	public boolean validateDeleteAccountHeaderText(String deleteAccountText) {
		return actions.validateText(deleteAccountHeaderText, deleteAccountText, "Validating delete account header text");
	}
}
