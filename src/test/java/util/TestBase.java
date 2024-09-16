package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class TestBase {
public WebDriver driver;
	
	public WebDriver WebDriverManager() throws IOException {
	FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//test//resourses//global.properties");
	Properties prop = new Properties();
	prop.load(fis);
	String url = prop.getProperty("QAUrl");
	String browser_properties = prop.getProperty("browser");

	if(driver == null)
	{
		if(browser_properties.equalsIgnoreCase("chrome"))
		{
	       io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
	       ChromeOptions options = new ChromeOptions();
	       options.addArguments("--incognito");
	       driver = new ChromeDriver(options);
	   
		}
		if(browser_properties.equalsIgnoreCase("firefox"))
		{
			io.github.bonigarcia.wdm.WebDriverManager.edgedriver();
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--inprivate");
			driver = new EdgeDriver(options);
			
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    driver.get(url);
	}
	
	return driver;
		
}
}
