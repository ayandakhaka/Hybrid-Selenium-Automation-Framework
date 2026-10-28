package utility;

import java.time.Duration;
import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
	
	protected WebDriver driver;
	protected final WebDriverWait wait;
	protected ActionHelper actions;
	
	public BasePage(WebDriver driver) {
		this.driver = Objects.requireNonNull(
	            driver, "WebDriver must not be null");
		actions = new ActionHelper(driver);
		
		this.wait = new WebDriverWait(
	            driver, Duration.ofSeconds(10)
	        );
	}

}
