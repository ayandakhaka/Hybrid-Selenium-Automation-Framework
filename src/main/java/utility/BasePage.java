package utility;

import org.openqa.selenium.WebDriver;

public abstract class BasePage {
	
	protected WebDriver driver;
	protected ActionHelper actions;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		actions = new ActionHelper(driver);
	}

}
