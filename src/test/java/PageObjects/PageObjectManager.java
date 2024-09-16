package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PageObjectManager {
	public WebDriver driver;
	public WebElement element;
	public Cart cartPage;
	public LoginPage loginPage;
	public Product productPage;
	
	
	public PageObjectManager(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public LoginPage getLoginPage(){
	      
		loginPage = new LoginPage(driver);
		 return loginPage;
	        
	}
	
	public Product getProductPage() {
		 
		productPage = new Product(driver);
		return productPage;
		
		}
	
	public Cart getCartPage() {
		
		cartPage = new Cart(driver);
        return cartPage;
	}

	
	
	
	
	
	
}
