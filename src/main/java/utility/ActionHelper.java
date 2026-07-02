package utility;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionHelper {

	private WebDriver driver;
	private WebDriverWait wait;

	public ActionHelper(WebDriver driver) {

		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("explicitWaitTimeout"))));
	}

	// Click element
	public ActionHelper click(By locator) {
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));	
		FrameworkLogger.info("Clicking an element");
		element.click();
		return this;
	}

	// Clear and insert text
	public ActionHelper typeText(By locator, String text) {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		FrameworkLogger.info("Clearing a text field");
		element.clear();
		FrameworkLogger.info("Setting text to a text field");
		element.sendKeys(text);
		return this;
	}

	// Get a text
	public String getText(By locator) {
		String text = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
		FrameworkLogger.info("Returned text is : " + text);
		return text;
	}

	// Select dropdown method by visible text
	public void selectByVisibleText(By locator, String text) {
		Select select = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
		select.selectByVisibleText(text);
		FrameworkLogger.info("Selected text from a dropdown : " + text);
	}

	// Select dropdown method by text

	public void selectByValue(By locator, String value) {
		Select select = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
		select.selectByValue(value);
		FrameworkLogger.info("Selected value from a dropdown : " + value);

	}

	// Hover over an element
	public void hoverOverElement(By locator) {
		Actions actions = new Actions(driver);
		FrameworkLogger.info("Hovering over a web element + " + locator);
		actions.moveToElement(wait.until(
				ExpectedConditions.visibilityOfElementLocated(locator)))
		.perform();
	}

	// Double click to a button
	public void doubleClick(By locator) {
		Actions actions = new Actions(driver);
		FrameworkLogger.info("Double clicking on a locator : " + locator);
		actions.doubleClick(wait.until(
				ExpectedConditions.elementToBeClickable(locator)))
		.perform();
	}

	// Right click on element
	public void rightClick(By locator) {
		Actions actions = new Actions(driver);
		FrameworkLogger.info("Right clicking on web element : " + locator);
		actions.contextClick(wait.until(
				ExpectedConditions.visibilityOfElementLocated(locator)))
		.perform();
	}

	// This take a screen
	public void takeScreenshot(String screenshotName) {

		try {
			FrameworkLogger.info("screenshot taken");
			File source = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);

			File destination = new File(
					"test-output/screenshots/" + screenshotName + ".png");
			FrameworkLogger.info("File destination is : " + destination);

			FileUtils.copyFile(source, destination);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// This switch to iFrame
	public void switchToFrame(By locator) {
		WebElement frame = wait.until(
				ExpectedConditions.presenceOfElementLocated(locator));

		driver.switchTo().frame(frame);
		FrameworkLogger.info("Switched to an iframe");
	}

	public void switchToDefaultContent() {
		FrameworkLogger.info("Switched to a default content");
		driver.switchTo().defaultContent();
	}

	// This is a switch to window
	public void switchToWindow(String title) {

		Set<String> windows = driver.getWindowHandles();

		for (String window : windows) {
			
			FrameworkLogger.info("Switching to window");
			driver.switchTo().window(window);

			if (driver.getTitle().contains(title)) {
				break;
			}
		}
	}

	// This accept the alert
	public void acceptAlert() {
		wait.until(ExpectedConditions.alertIsPresent()).accept();
		FrameworkLogger.info("Accepting an alert");
	}

	// This dismiss the alert
	public void dismissAlert() {
		wait.until(ExpectedConditions.alertIsPresent()).dismiss();
		FrameworkLogger.info("Dismissing an alert");

	}

	// This get text from the alert
	public String getAlertText() {
		
		FrameworkLogger.info("Getting an alert text");
		return wait.until(ExpectedConditions.alertIsPresent()).getText();
	}

	// This upload the file
	public void uploadFile(By locator, String filePath) {
		wait.until(ExpectedConditions.presenceOfElementLocated(locator))
		.sendKeys(filePath);
		FrameworkLogger.info("File uploaded");
	}

	// Wait For Element
	public WebElement waitForElement(By locator) {
		FrameworkLogger.info("Waiting for an element : " + locator);
		return wait.until(
				ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// Is Displayed
	public boolean isDisplayed(By locator) {
		FrameworkLogger.info("Checking if the element " + locator + " is displayed.");
		return wait.until(
				ExpectedConditions.visibilityOfElementLocated(locator))
				.isDisplayed();
	}

	// This scroll to the bottom
	public void scrollToBottom() {
		FrameworkLogger.info("Scrolling to the bottom of the page");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
	}

	// Scroll To Element
	public void scrollToElement(By locator) {
		WebElement element = this.waitForElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		FrameworkLogger.info("Scroll to an element " + element);
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	// Wait for a page to load
	public void waitForPageLoad() {
		wait = new WebDriverWait(driver,
				Duration.ofSeconds(Integer.parseInt(ConfigReader.getProperty("waitForPageToLoad"))));
		
		FrameworkLogger.info("Waiting for a page to load");
		wait.until(webDriver ->
		((JavascriptExecutor) webDriver)
		.executeScript("return document.readyState")
		.equals("complete"));
	}

	// Get Current URL
	public String getCurrentUrl() {
		FrameworkLogger.info("Current URL is " + driver.getCurrentUrl());
		return driver.getCurrentUrl();
	}

	// Navigate To URL
	public void navigateTo(String url) {
		FrameworkLogger.info("Navigating to a " + url);
		driver.get(url);
	}

	public boolean validateText(By element, String expectedText, String description) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));

			String actualText = this.getText(element).trim();
			FrameworkLogger.info(actualText.trim() + " = " + expectedText.trim());

			if (actualText.equals(expectedText.trim())) {
				FrameworkLogger.info("Text validation passed for " + description + ": " + actualText);
				return true;
			} else {
				FrameworkLogger.error("Text validation failed for " + description + 
						". Expected: '" + expectedText + "', but found: '" + actualText + "'");
				
				FrameworkLogger.error("Text validation failed for " + description + 
						". Expected: '" + expectedText + "', but found: '" + actualText + "'");
				return false;
			}

		} catch (TimeoutException e) {
			FrameworkLogger.error(
					"Timeout waiting for element to be visible: " + description,
					e
					);
			return false;
		} catch (Exception e) {
			FrameworkLogger.error(
					"Error validating text for " + description,
					e
					);
			return false;
		}
	}
}
