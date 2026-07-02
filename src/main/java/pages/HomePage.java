package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utility.BasePage;

public class HomePage extends BasePage {
	
	public HomePage(WebDriver driver) {
		
		super(driver);
	}
	
	private By signupLoginButton = By.xpath("//i[@class='fa fa-lock' ]");
	
	public void clickSignLoginButton() {
		actions.click(signupLoginButton);
	}

}
