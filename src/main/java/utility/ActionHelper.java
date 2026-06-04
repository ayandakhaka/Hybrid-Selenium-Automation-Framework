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
	public void click(By locator) {
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));		
		element.click();
	}

	// Clear and insert text
	public void typeText(By locator, String text) {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		element.clear();
		element.sendKeys(text);
	}

	// Get a text
	public String getText(By locator) {
		String text = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
		return text;
	}

	// Select dropdown method by text
	public void selectByVisibleText(By locator, String text) {
		Select select = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
		select.selectByVisibleText(text);
	}

	// Select dropdown method by text

	public void selectByValue(By locator, String value) {
		Select select = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
		select.selectByValue(value);
	}

	// Hover over an element
	public void hoverOverElement(By locator) {
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(
				ExpectedConditions.visibilityOfElementLocated(locator)))
		.perform();
	}

	// Double click to a button
	public void doubleClick(By locator) {
		Actions actions = new Actions(driver);
		actions.doubleClick(wait.until(
				ExpectedConditions.elementToBeClickable(locator)))
		.perform();
	}

	// Right click on element
	public void rightClick(By locator) {
		Actions actions = new Actions(driver);
		actions.contextClick(wait.until(
				ExpectedConditions.visibilityOfElementLocated(locator)))
		.perform();
	}

	// This take a screen
	public void takeScreenshot(String screenshotName) {

		try {
			File source = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);

			File destination = new File(
					"test-output/screenshots/" + screenshotName + ".png");

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
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	// This switch to window
	public void switchToWindow(String title) {

		Set<String> windows = driver.getWindowHandles();

		for (String window : windows) {

			driver.switchTo().window(window);

			if (driver.getTitle().contains(title)) {
				break;
			}
		}
	}

	// This accept the alert
	public void acceptAlert() {
		wait.until(ExpectedConditions.alertIsPresent()).accept();
	}

	// This dismiss the alert
	public void dismissAlert() {
		wait.until(ExpectedConditions.alertIsPresent()).dismiss();
	}

	// This get text from the alert
	public String getAlertText() {
		return wait.until(ExpectedConditions.alertIsPresent()).getText();
	}

	// This upload the file
	public void uploadFile(By locator, String filePath) {
		wait.until(ExpectedConditions.presenceOfElementLocated(locator))
		.sendKeys(filePath);
	}

	// Wait For Element
	public WebElement waitForElement(By locator) {
		return wait.until(
				ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// Is Displayed
	public boolean isDisplayed(By locator) {
		return wait.until(
				ExpectedConditions.visibilityOfElementLocated(locator))
				.isDisplayed();
	}

	// This scroll to the bottom
	public void scrollToBottom() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"window.scrollTo(0, document.body.scrollHeight);");
	}

	// Scroll To Element
	public void scrollToElement(By locator) {
		WebElement element = waitForElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	// Wait for a page to load
	public void waitForPageLoad() {
		wait = new WebDriverWait(driver,
				Duration.ofSeconds(30));

		wait.until(webDriver ->
		((JavascriptExecutor) webDriver)
		.executeScript("return document.readyState")
		.equals("complete"));
	}

	// Get Current URL
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	// Navigate To URL
	public void navigateTo(String url) {
		driver.get(url);
	}
}
