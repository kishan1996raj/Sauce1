package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Cart {
    private WebDriver driver;
    private static final String CARTURL_STRING = "https://www.saucedemo.com/cart.html";

    private By cartIcon = By.xpath("//a[@class='shopping_cart_link']");
    private By cartItem = By.xpath("//div[@class='inventory_item_name']");
    private By cartCountBy = By.xpath("//span[@class='shopping_cart_badge']");
    private By checkoutBy = By.xpath("//button[@id='checkout']");
    private By firstNameBy = By.xpath("//input[@id='first-name']");
    private By secondNameBy = By.xpath("//input[@id='last-name']");
    private By addressBy = By.xpath("//input[@id='postal-code']");
    private By continueButtonBy = By.xpath("//input[@id='continue']");
    private By finishBy = By.xpath("//button[@id='finish']");
    private By confirmationBy = By.xpath("//h2[@class='complete-header']");

    public int initialCount = 0;
    public int updatedCount = 0;

    public Cart(WebDriver driver) {
        this.driver = driver;
    }

    private WebElement waitForElement(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean clickOnCartIcon() {
        try {
            waitForElement(cartIcon, 10).click();
            if (driver.getCurrentUrl().equalsIgnoreCase(CARTURL_STRING)) {
                return true;
            } else {
                System.out.println("Failed to navigate to the cart page.");
                return false;
            }
        } catch (WebDriverException e) {
            System.out.println("Error interacting with cart: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean verifyCartItem(String product) {
        try {
            WebElement cartItemElement = waitForElement(cartItem, 10);
            String cartItemString = cartItemElement.getText();
            System.out.println("Cart Item name : "+cartItemString);
            return cartItemString.equalsIgnoreCase(product);
        } catch (NoSuchElementException e) {
            System.out.println("Product not found in cart: " + e.getMessage());
            return false;
        } catch (WebDriverException e) {
            System.out.println("Error interacting with cart items: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeItemAndVerifyCartCount(String product) {
        try {
            WebElement cartCountElement = waitForElement(cartCountBy, 10);
            int initialCount = Integer.parseInt(cartCountElement.getText());

            WebElement cartRemoveElement = waitForElement(
                By.xpath("//div[text()='" + product + "']/ancestor::div[@class='cart_item']//button[text()='Remove']"), 
                10);
            cartRemoveElement.click();

            // Try to locate the cart count element after removal
            try {
                cartCountElement = waitForElement(cartCountBy, 10);
                int updatedCount = Integer.parseInt(cartCountElement.getText());
                return updatedCount < initialCount;
            } catch (Exception e) {
                // If the cart count element is not found, assume the cart is empty or count element is not visible
                System.out.println("Cart count element not found after removal, assuming empty or updated.");
                return true;
            }

        } catch (NoSuchElementException e) {
            System.out.println("Product not found in cart: " + e.getMessage());
            return false;
        } catch (WebDriverException e) {
            System.out.println("Error interacting with cart: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean clickCheckoutButton() {
        try {
            WebElement checkoutElement = waitForElement(checkoutBy, 10);
            checkoutElement.click();
            return true;
        } catch (Exception e) {
            System.out.println("Error clicking checkout button: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean enterCheckoutInformation(String firstName, String secondName, String address) {
        try {
            WebElement firstNameElement = waitForElement(firstNameBy, 10);
            WebElement secondNameElement = waitForElement(secondNameBy, 10);
            WebElement addressElement = waitForElement(addressBy, 10);
            WebElement continueButtonElement = waitForElement(continueButtonBy, 10);

            firstNameElement.sendKeys(firstName);
            secondNameElement.sendKeys(secondName);
            addressElement.sendKeys(address);
            continueButtonElement.click();
            return driver.getCurrentUrl().equalsIgnoreCase("https://www.saucedemo.com/checkout-step-two.html");
        } catch (Exception e) {
            System.out.println("Error entering checkout information: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean completeCheckout() {
        try {
            WebElement finishElement = waitForElement(finishBy, 10);
            finishElement.click();
            return driver.getCurrentUrl().equalsIgnoreCase("https://www.saucedemo.com/checkout-complete.html");
        } catch (Exception e) {
            System.out.println("Error completing checkout process: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean verifyOrderConfirmation() {
        try {
            WebElement confirmationElement = waitForElement(confirmationBy, 10);
            return confirmationElement.isDisplayed();
        } catch (Exception e) {
            System.out.println("Error verifying order confirmation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
  



}
