package utility;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
	
	private static ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();
	
	public static void createDriver() {
		
		String browser = System.getProperty("browser", 
				ConfigReader.getProperty("browser"));
		
		Boolean headless = Boolean.parseBoolean(System.getProperty("headless", 
				ConfigReader.getProperty("headless")));
		
		WebDriver webDriver;
		
		switch (browser.toLowerCase()) {

        case "chrome":

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

            if (headless) {
                chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--window-size=1920,1080");
            }

            webDriver = new ChromeDriver(chromeOptions);
            break;

        case "firefox":

            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

            if (headless) {
            	firefoxOptions.addArguments("--headless=new");
            	firefoxOptions.addArguments("--window-size=1920,1080");
            }

            webDriver = new FirefoxDriver(firefoxOptions);
            break;

        case "edge":

            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

            if (headless) {
                edgeOptions.addArguments("--headless=new");
                edgeOptions.addArguments("--window-size=1920,1080");
            }

            webDriver = new EdgeDriver(edgeOptions);
            break;

        default:
            throw new IllegalArgumentException(
                    "Unsupported browser: " + browser);
        }
		
		webDriver.manage().window().maximize();
		
		driver.set(webDriver);
	}
	
	public static WebDriver getDriver() {

        return driver.get();
    }

    public static void quitDriver() {

        if (driver.get() != null) {

            driver.get().quit();

            driver.remove();
        }
    }

}
