package utility;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PopupHandler {

	private final WebDriver driver;

    private final By closePopup =
        By.cssSelector("div.continue-prompt-text");

    public PopupHandler(WebDriver driver) {
        this.driver = driver;
    }

    public void closeUnexpectedPopup() {
        try {
            new WebDriverWait(
                driver, Duration.ofSeconds(3)
            ).until(
                ExpectedConditions.elementToBeClickable(closePopup)
            ).click();

            FrameworkLogger.info("Closed unexpected popup.");

        } catch (org.openqa.selenium.TimeoutException e) {
            FrameworkLogger.info("No popup appeared.");
        }
    }
}
