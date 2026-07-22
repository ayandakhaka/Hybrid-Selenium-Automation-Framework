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
	
	public static WebDriver createDriver() {
		
		WebDriver driver;
		
		String browser = System.getProperty("browser", 
				ConfigReader.getProperty("browser"));
		
		Boolean headless = Boolean.parseBoolean(System.getProperty("headless", 
				ConfigReader.getProperty("headless")));
		
		switch (browser.toLowerCase()) {

        case "chrome":

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

            if (headless) {
                chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--window-size=1920,1080");
            }

            driver = new ChromeDriver(chromeOptions);
            break;

        case "firefox":

            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

            if (headless) {
            	firefoxOptions.addArguments("--headless=new");
            	firefoxOptions.addArguments("--window-size=1920,1080");
            }

            driver = new FirefoxDriver(firefoxOptions);
            break;

        case "edge":

            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

            if (headless) {
                edgeOptions.addArguments("--headless=new");
                edgeOptions.addArguments("--window-size=1920,1080");
            }

            driver = new EdgeDriver(edgeOptions);
            break;

        default:
            throw new IllegalArgumentException(
                    "Unsupported browser: " + browser);
        }
		
		driver.manage().window().maximize();
		
		return driver;
	}

}
