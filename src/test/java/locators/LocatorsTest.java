package locators;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class LocatorsTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EdgeOptions options = new EdgeOptions();
		options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        
        WebDriver driver = new EdgeDriver(options);
        
        driver.get("https://automationexercise.com/");
        

	}

}
