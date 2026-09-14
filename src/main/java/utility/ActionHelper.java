package utility;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionHelper {

	private final WebDriver driver;
	private final WebDriverWait wait;
	private final WebDriverWait pageLoadWait;

	public ActionHelper(WebDriver driver) {

		this.driver = driver;

		// Explicit wait for elements
		this.wait = new WebDriverWait(
				driver,
				Duration.ofSeconds(
						Long.parseLong(
								ConfigReader.getProperty("explicitWait")
								)
						)
				);

		// Separate wait for page loading
		this.pageLoadWait = new WebDriverWait(
				driver,
				Duration.ofSeconds(
						Long.parseLong(
								ConfigReader.getProperty(
										"waitForPageToLoad"
										)
								)
						)
				);
	}

	// Click element
	public ActionHelper click(By locator) {

		WebElement element = wait.until(
				ExpectedConditions.elementToBeClickable(locator)
				);

		FrameworkLogger.info("Clicking an element: " + locator);

		element.click();

		return this;
	}

	// Clear and insert text
	public ActionHelper typeText(By locator, String text) {

		WebElement element = wait.until(
				ExpectedConditions.visibilityOfElementLocated(locator)
				);

		FrameworkLogger.info("Clearing a text field: " + locator);

		element.clear();

		FrameworkLogger.info("Setting text into a text field: " + locator);

		element.sendKeys(text);

		return this;
	}

	// Get text
	public String getText(By locator) {

		String text = wait.until(
				ExpectedConditions.visibilityOfElementLocated(locator)
				).getText();

		FrameworkLogger.info(
				"Returned text is: " + text
				);

		return text;
	}

	// Select dropdown by visible text
	public void selectByVisibleText(
			By locator,
			String text) {

		Select select = new Select(
				wait.until(
						ExpectedConditions.visibilityOfElementLocated(
								locator
								)
						)
				);

		select.selectByVisibleText(text);

		FrameworkLogger.info(
				"Selected text from dropdown: " + text
				);
	}

	// Select dropdown by value
	public void selectByValue(
			By locator,
			String value) {

		Select select = new Select(
				wait.until(
						ExpectedConditions.visibilityOfElementLocated(
								locator
								)
						)
				);

		select.selectByValue(value);

		FrameworkLogger.info(
				"Selected value from dropdown: " + value
				);
	}

	// Hover over an element
	public void hoverOverElement(By locator) {

		Actions actions = new Actions(driver);

		FrameworkLogger.info(
				"Hovering over a web element: " + locator
				);

		actions.moveToElement(
				wait.until(
						ExpectedConditions.visibilityOfElementLocated(
								locator
								)
						)
				).perform();
	}

	// Double click
	public void doubleClick(By locator) {

		Actions actions = new Actions(driver);

		FrameworkLogger.info(
				"Double clicking on locator: " + locator
				);

		actions.doubleClick(
				wait.until(
						ExpectedConditions.elementToBeClickable(locator)
						)
				).perform();
	}

	// Right click
	public void rightClick(By locator) {

		Actions actions = new Actions(driver);

		FrameworkLogger.info(
				"Right clicking on web element: " + locator
				);

		actions.contextClick(
				wait.until(
						ExpectedConditions.visibilityOfElementLocated(
								locator
								)
						)
				).perform();
	}

	// Switch to iframe
	public void switchToFrame(By locator) {

		WebElement frame = wait.until(
				ExpectedConditions.presenceOfElementLocated(locator)
				);

		driver.switchTo().frame(frame);

		FrameworkLogger.info(
				"Switched to iframe: " + locator
				);
	}

	// Switch back to default content
	public void switchToDefaultContent() {

		driver.switchTo().defaultContent();

		FrameworkLogger.info(
				"Switched to default content"
				);
	}

	// Switch to window by title
	public void switchToWindow(String title) {

		Set<String> windows = driver.getWindowHandles();

		for (String window : windows) {

			driver.switchTo().window(window);

			FrameworkLogger.info(
					"Checking window title: " + driver.getTitle()
					);

			if (driver.getTitle().contains(title)) {

				FrameworkLogger.info(
						"Switched to window containing title: "
								+ title
						);

				return;
			}
		}

		throw new IllegalArgumentException(
				"No window found containing title: " + title
				);
	}

	// Accept alert
	public void acceptAlert() {

		wait.until(
				ExpectedConditions.alertIsPresent()
				).accept();

		FrameworkLogger.info(
				"Accepted alert"
				);
	}

	// Dismiss alert
	public void dismissAlert() {

		wait.until(
				ExpectedConditions.alertIsPresent()
				).dismiss();

		FrameworkLogger.info(
				"Dismissed alert"
				);
	}

	// Get alert text
	public String getAlertText() {

		FrameworkLogger.info(
				"Getting alert text"
				);

		return wait.until(
				ExpectedConditions.alertIsPresent()
				).getText();
	}

	// Upload file
	public void uploadFile(
			By locator,
			String filePath) {

		wait.until(
				ExpectedConditions.presenceOfElementLocated(locator)
				).sendKeys(filePath);

		FrameworkLogger.info(
				"File uploaded: " + filePath
				);
	}

	// Wait for element
	public WebElement waitForElement(By locator) {

		FrameworkLogger.info(
				"Waiting for an element: " + locator
				);

		return wait.until(
				ExpectedConditions.visibilityOfElementLocated(locator)
				);
	}

	// Is displayed
	public boolean isDisplayed(By locator) {

		FrameworkLogger.info(
				"Checking if element is displayed: " + locator
				);

		return wait.until(
				ExpectedConditions.visibilityOfElementLocated(locator)
				).isDisplayed();
	}

	// Scroll to bottom
	public void scrollToBottom() {

		FrameworkLogger.info(
				"Scrolling to the bottom of the page"
				);

		JavascriptExecutor js =
				(JavascriptExecutor) driver;

		js.executeScript(
				"window.scrollTo(0, document.body.scrollHeight);"
				);
	}

	// Scroll to element
	public void scrollToElement(By locator) {

		WebElement element =
				waitForElement(locator);

		FrameworkLogger.info(
				"Scrolling to element: " + locator
				);

		JavascriptExecutor js =
				(JavascriptExecutor) driver;

		js.executeScript(
				"arguments[0].scrollIntoView({block: 'center'});",
				element
				);
	}

	// Wait for page to load
	public void waitForPageLoad() {

		FrameworkLogger.info("Waiting for page to load");

		pageLoadWait.until(webDriver -> {

			String readyState = ((JavascriptExecutor) webDriver)
					.executeScript("return document.readyState")
					.toString();

			FrameworkLogger.info("Current document.readyState: " + readyState);

			return readyState.equals("interactive")
					|| readyState.equals("complete");
		});

		FrameworkLogger.info("Page load condition satisfied.");
	}

	// Get current URL
	public String getCurrentUrl() {

		String currentUrl =
				driver.getCurrentUrl();

		FrameworkLogger.info(
				"Current URL is: " + currentUrl
				);

		return currentUrl;
	}

	// Navigate to URL
	public void navigateTo(String url) {

		FrameworkLogger.info(
				"Navigating to: " + url
				);

		driver.get(url);
	}

	// Get page source
	public String getPageSource() {

		FrameworkLogger.info(
				"Returning current page source"
				);

		return driver.getPageSource();
	}

	// Validate text
	public boolean validateText(
			By element,
			String expectedText,
			String description) {

		try {

			wait.until(
					ExpectedConditions.visibilityOfElementLocated(
							element
							)
					);

			String actualText =
					getText(element).trim();

			if (actualText.equals(expectedText.trim())) {

				FrameworkLogger.info(
						"Text validation passed for "
								+ description
								+ ": "
								+ actualText
						);

				return true;

			} else {

				FrameworkLogger.error(
						"Text validation failed for "
								+ description
								+ ". Expected: '"
								+ expectedText
								+ "', but found: '"
								+ actualText
								+ "'"
						);

				return false;
			}

		} catch (TimeoutException e) {

			FrameworkLogger.error(
					"Timeout waiting for element to be visible: "
							+ description,
							e
					);

			return false;

		} catch (Exception e) {

			FrameworkLogger.error(
					"Error validating text for "
							+ description,
							e
					);

			return false;
		}
	}
}

