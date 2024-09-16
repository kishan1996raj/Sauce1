package PageObjects;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriverException;

public class LoginPage {
    private WebDriver driver;
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final String INVENTORY_URL = BASE_URL + "inventory.html";
    private static final String ERROR_MESSAGE = "Epic sadface: Username is required";
    private static final String ERROR_MESSAGE2 = "Epic sadface: Username and password do not match any user in this service";
    
    private By username = By.xpath("//input[@id='user-name']");
    private By password = By.xpath("//input[@id='password']");
    private By login    = By.xpath("//input[@id='login-button']");
    private By errorBy  = By.xpath("//h3[@data-test='error']");
    private By hamburger = By.xpath("//button[@id='react-burger-menu-btn']");
    private By logoutBy = By.xpath("//a[@id='logout_sidebar_link']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean navigateToLoginPage() {
        try {
            if(!this.driver.getCurrentUrl().equals(BASE_URL)) {
                this.driver.get(BASE_URL);
                return true;
            }
            else return true;
        } catch (WebDriverException e) {
            System.out.println("Failed to navigate to the login page: " + e.getMessage());
            return false;
        }
    }

    public boolean enterLoginCredentials(String Username, String Password) {
        try {
            FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(600))
                .ignoring(NoSuchElementException.class);

            WebElement usernameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(username));
            usernameElement.sendKeys(Username);

            WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(password));
            passwordElement.sendKeys(Password);
            return true;
        } catch (TimeoutException e) {
            System.out.println("Timeout while waiting for login elements: " + e.getMessage());
            return false;
        } catch (NoSuchElementException e) {
            System.out.println("Unable to locate login elements: " + e.getMessage());
            return false;
        } catch (WebDriverException e) {
            System.out.println("WebDriver error occurred during login: " + e.getMessage());
            return false;
        }
    }

    public boolean clickLoginButton() {
        try {
            WebElement loginButton = driver.findElement(login);
            loginButton.click();

            FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(600))
                .ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.invisibilityOf(loginButton));
          return true;
        } catch (NoSuchElementException e) {
            System.out.println("Login button not found: " + e.getMessage());
            return false;
        } catch (TimeoutException e) {
            System.out.println("Timeout while waiting for login button to disappear: " + e.getMessage());
            return false;
        } catch (WebDriverException e) {
            System.out.println("Error while clicking the login button: " + e.getMessage());
            return false;
        }
    }

    public boolean verifyLogin() {
        try {
            if (driver.getCurrentUrl().equals(INVENTORY_URL)) {
                return true;
            } else {
                WebElement errorElement = driver.findElement(errorBy);
                String errorMessage = errorElement.getText();
                if (errorMessage.equals(ERROR_MESSAGE)) {
                    System.out.println("Username or Password Field empty");
                    return true;
                } else if(errorMessage.equals(ERROR_MESSAGE2)){
                    System.out.println("Entered Wrong Credentials");
                    return true;
                }
                else return false;
            }
        } catch (NoSuchElementException e) {
            System.out.println("Error message element not found: " + e.getMessage());
            return false;
        } catch (WebDriverException e) {
            System.out.println("Error occurred during login verification: " + e.getMessage());
            return false;
        }
    }
    
    public boolean performLogOut(){
    	try {
    		WebElement hamburgerElement = driver.findElement(hamburger);
    		hamburgerElement.click();
    		WebElement logoutElement = driver.findElement(logoutBy);
    		logoutElement.click();
    		return driver.getCurrentUrl().equalsIgnoreCase("https://www.saucedemo.com/");
    	}catch (NoSuchElementException e) {
            System.out.println("Error message element not found: " + e.getMessage());
            return false;
        } catch (WebDriverException e) {
            System.out.println("Error occurred during login verification: " + e.getMessage());
            return false;
        }

    }
}
