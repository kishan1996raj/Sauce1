package stepdefination;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import PageObjects.Cart;
import PageObjects.LoginPage;
import PageObjects.Product;
import io.cucumber.java.en.*;
import util.TestContextSetup;

public class CartSD {
    public WebDriver driver;
    public LoginPage loginpage;
    public Product productPage;
    public Cart cartPage;
    public TestContextSetup testContextSetup;

    private static final Logger logger = LogManager.getLogger(CartSD.class);

    public CartSD(TestContextSetup testContextSetup) {
        this.testContextSetup = testContextSetup;
        loginpage = testContextSetup.pageObjectManager.getLoginPage();
        productPage = testContextSetup.pageObjectManager.getProductPage();
        cartPage = testContextSetup.pageObjectManager.getCartPage();
    }

    @Given("I am on the product page")
    public void i_am_on_the_product_page() {
        logger.info("Navigating to the login page");
        loginpage.navigateToLoginPage();
        loginpage.enterLoginCredentials("standard_user", "secret_sauce");
        loginpage.clickLoginButton();
        
        if (!loginpage.verifyLogin()) {
            logger.warn("Login verification failed! Check credentials or page load.");
        }
        Assert.assertTrue(loginpage.verifyLogin());
        logger.info("Successfully logged in and navigated to product page");
    }

    @When("I add a product {string} to the cart")
    public void i_add_a_product_to_the_cart(String product) {
        logger.info("Adding product {} to the cart", product);
        boolean isAdded = productPage.addToCartProduct(product);
        
        if (!isAdded) {
            logger.warn("Failed to add product {} to the cart. Possible UI or backend issue.", product);
        }
        
        Assert.assertTrue(isAdded);
        logger.info("Product {} added to the cart", product);
    }

    @When("I click on the cart icon")
    public void i_click_on_the_cart_icon() {
        logger.info("Clicking on the cart icon");
        boolean isNavigated = cartPage.clickOnCartIcon();
        
        if (!isNavigated) {
            logger.warn("Failed to navigate to the cart. Cart icon may not be clickable.");
        }
        
        Assert.assertTrue(isNavigated);
        logger.info("Navigated to the cart");
    }

    @Then("I should see the product {string} in the cart")
    public void i_should_see_the_product_in_the_cart(String product) {
        logger.info("Verifying the product {} is in the cart", product);
        boolean isProductInCart = cartPage.verifyCartItem(product);
        
        if (!isProductInCart) {
            logger.warn("Product {} is missing in the cart. Cart sync issue?", product);
        }
        
        Assert.assertTrue(isProductInCart);
        logger.info("Product {} found in the cart", product);
    }

    @Given("I have a product {string} in the cart")
    public void i_have_a_product_in_the_cart(String product) {
        logger.info("Logging in and adding product {} to the cart", product);
        loginpage.navigateToLoginPage();
        loginpage.enterLoginCredentials("standard_user", "secret_sauce");
        loginpage.clickLoginButton();
        Assert.assertTrue(loginpage.verifyLogin());
        boolean isAdded = productPage.addToCartProduct(product);
        
        if (!isAdded) {
            logger.warn("Product {} failed to be added to the cart. Action may not have completed.", product);
        }
        
        Assert.assertTrue(isAdded);
        logger.info("Product {} is added to the cart", product);
    }

    @When("I remove the product {string} from the cart")
    public void i_remove_the_product_from_the_cart(String product) {
        logger.info("Removing product {} from the cart", product);
        boolean isNavigated = cartPage.clickOnCartIcon();
        
        if (!isNavigated) {
            logger.warn("Cart icon click failed. Navigation to cart page unsuccessful.");
        }
        
        boolean isRemoved = cartPage.removeItemAndVerifyCartCount(product);
        
        if (!isRemoved) {
            logger.warn("Failed to remove product {} from the cart.", product);
        }
        
        Assert.assertTrue(isNavigated && isRemoved);
        logger.info("Product {} removed from the cart", product);
    }

    @Then("the product {string} should no longer be in the cart")
    public void the_product_should_no_longer_be_in_the_cart(String product) {
        logger.info("Verifying product {} is no longer in the cart", product);
        boolean isProductInCart = cartPage.verifyCartItem(product);
        
        if (isProductInCart) {
            logger.warn("Product {} is still present in the cart after removal.", product);
        }
        
        Assert.assertFalse(isProductInCart);
        logger.info("Product {} successfully removed from the cart", product);
    }

    @Then("the cart count should be updated")
    public void the_cart_count_should_be_updated() {
        logger.info("Verifying the cart count is updated");
        if (cartPage.updatedCount != 0) {
            logger.warn("Cart count is incorrect, expected 0 but got {}", cartPage.updatedCount);
        }
        Assert.assertEquals(cartPage.updatedCount, 0);
        logger.info("Cart count successfully updated");
    }

    @When("I click on the Checkout button")
    public void i_click_on_the_button() {
        logger.info("Clicking on the Checkout button");
        cartPage.clickOnCartIcon();
        boolean isCheckoutPage = cartPage.clickCheckoutButton();
        
        if (!isCheckoutPage) {
            logger.warn("Checkout button click failed. Could not navigate to checkout page.");
        }
        
        Assert.assertTrue(isCheckoutPage);
        logger.info("Navigated to checkout page");
    }

    @When("I enter valid checkout information with first name {string}, last name {string}, and postal code {string}")
    public void i_enter_valid_checkout_information_with_first_name_last_name_and_postal_code(String firstName, String lastName, String postalCode) {
        logger.info("Entering checkout information: {}, {}, {}", firstName, lastName, postalCode);
        boolean isInfoEntered = cartPage.enterCheckoutInformation(firstName, lastName, postalCode);
        
        if (!isInfoEntered) {
            logger.warn("Checkout information entry failed for {}, {}, {}", firstName, lastName, postalCode);
        }
        
        Assert.assertTrue(isInfoEntered);
        logger.info("Checkout information entered successfully");
    }

    @When("I complete the checkout process")
    public void i_complete_the_checkout_process() {
        logger.info("Completing the checkout process");
        cartPage.completeCheckout();
        logger.info("Checkout process completed");
    }

    @Then("I should see the order confirmation page")
    public void i_should_see_the_order_confirmation_page() {
        logger.info("Verifying the order confirmation page is displayed");
        boolean isOrderConfirmed = cartPage.verifyOrderConfirmation();
        
        if (!isOrderConfirmed) {
            logger.warn("Order confirmation page verification failed.");
        }
        
        Assert.assertTrue(isOrderConfirmed);
        logger.info("Order confirmation page verified");
    }
}
