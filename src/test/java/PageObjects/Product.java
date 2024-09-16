package PageObjects;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class Product {
    private WebDriver driver;
    private static final String URL_STRING = "https://www.saucedemo.com/inventory.html";
    public String searchedProduct = "";
    public String searchedProductDescription = "";
    public  String searchedProductPrice = "";
    public int intialCount = 0;
    public int updatedCount = 0; 

    private By listOfProductsBy = By.xpath("//div[@class='inventory_item']");
    private By cartCountBy = By.xpath("//span[@class='shopping_cart_badge']");
    private By productDescBy = By.xpath("//div[@class='inventory_details_desc large_size']");
    private By productPriceBy = By.xpath("//div[@class='inventory_details_price']");

    public Product(WebDriver driver) {
        this.driver = driver;
    }

  
    public boolean navigateToProductPage() {
        try {
            if (!driver.getCurrentUrl().contentEquals(URL_STRING)) {
                driver.get(URL_STRING);
            }
            return true;
        } catch (WebDriverException e) {
            System.out.println("Failed to navigate to the product page: " + e.getMessage());
            return false;
        }
    }

   
    public boolean countProductsDisplayed(int expectedCount) {
        try {
            List<WebElement> productsElements = driver.findElements(listOfProductsBy);
            for (WebElement productElement : productsElements) {
                System.out.println(productElement.getText());
            }
            return productsElements.size() == expectedCount;
        } catch (NoSuchElementException e) {
            System.out.println("Failed to count products displayed: " + e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

  
    public boolean addToCartProduct(String productName) {
        try {
          
        	try {
                WebElement cartCountElement = driver.findElement(cartCountBy);
               intialCount  = Integer.parseInt(cartCountElement.getText());
            } catch (NoSuchElementException e) {
                System.out.println("Cart is initially empty.");
            }

           
            WebElement addToCartButton = driver.findElement(By.xpath("//div[normalize-space()='" + productName + "']/ancestor::div[@class='inventory_item_description']/div[@class='pricebar']/button"));
            addToCartButton.click();

          
            WebElement cartCountElement = driver.findElement(cartCountBy);
            updatedCount = Integer.parseInt(cartCountElement.getText());

            return updatedCount > intialCount;
        } catch (NoSuchElementException e) {
            System.out.println("Failed to find the product or cart count element: " + e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean verifyProducts(List<String> expectedProducts) {
        try {
            for (String productName : expectedProducts) {
                WebElement productElement = driver.findElement(By.xpath("//div[normalize-space()='" + productName + "']"));
                if (!productElement.isDisplayed()) {
                    return false;
                }
            }
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Failed to find one or more products: " + e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

  
    public boolean clickProduct(String productName) {
        try {
            WebElement productElement = driver.findElement(By.xpath("//div[normalize-space()='" + productName + "']"));
            productElement.click();
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Failed to find or click on product: " + e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
   public boolean verifyCurrentPageIsProduct() {
	   if(driver.getCurrentUrl().contains("id")) {
		   return true;
	   }else return false;
   }
   
    public boolean verifyProductDisplayPage(String productName) {
        try {
            if (driver.getCurrentUrl().contains("id")) {
                WebElement productElement = driver.findElement(By.xpath("//div[@class='inventory_details_name large_size' and text()='" + productName + "']"));
                WebElement productDescElement = driver.findElement(productDescBy);
                WebElement productPriceElement = driver.findElement(productPriceBy);

                if (productElement.isDisplayed()) {
                    searchedProduct = productElement.getText();
                    searchedProductDescription = productDescElement.getText();
                    searchedProductPrice = productPriceElement.getText();
                    return true;
                }
            }
            return false;
        } catch (NoSuchElementException e) {
            System.out.println("Failed to verify product details on product page: " + e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
