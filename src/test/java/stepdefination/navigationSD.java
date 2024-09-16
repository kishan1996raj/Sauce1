package stepdefination;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import PageObjects.Cart;
import PageObjects.LoginPage;
import PageObjects.Product;
import io.cucumber.java.en.*;
import util.TestContextSetup;

public class navigationSD {

    public WebDriver driver;
    public LoginPage loginpage;
    public Product productPage;
    public Cart cartPage;
    public TestContextSetup testContextSetup;

    private static final Logger logger = LogManager.getLogger(navigationSD.class);

    public navigationSD(TestContextSetup testContextSetup) {
        this.testContextSetup = testContextSetup;
        loginpage = testContextSetup.pageObjectManager.getLoginPage();
        productPage = testContextSetup.pageObjectManager.getProductPage();
        cartPage = testContextSetup.pageObjectManager.getCartPage();
    }

    @Given("I am on the homepage")
    public void i_am_on_the_homepage() {
        logger.info("Navigating to the homepage and logging in");
        loginpage.navigateToLoginPage();
        loginpage.enterLoginCredentials("standard_user", "secret_sauce");
        loginpage.clickLoginButton();
    }

    @When("I navigate to the product page")
    public void i_navigate_to_the_product_page() {
        logger.info("Navigating to the product page");
        if (!productPage.navigateToProductPage()) {
            logger.warn("Failed to navigate to the product page");
            Assert.fail("Navigation to the product page failed");
        }
    }

    @Then("I should be on the product page")
    public void i_should_be_on_the_product_page() throws IOException {
        logger.info("Verifying the current URL is the product page");
        WebDriver driver = testContextSetup.testBase.WebDriverManager();
        if (!driver.getCurrentUrl().equalsIgnoreCase("https://www.saucedemo.com/inventory.html")) {
            logger.warn("User is not on the product page");
            Assert.fail("User is not on the product page");
        }
    }

    @When("I navigate to the cart page")
    public void i_navigate_to_the_cart_page() {
        logger.info("Navigating to the cart page");
        if (!cartPage.clickOnCartIcon()) {
            logger.warn("Failed to navigate to the cart page");
            Assert.fail("Navigation to the cart page failed");
        }
    }

    @Then("I should be on the cart page")
    public void i_should_be_on_the_cart_page() throws IOException {
        logger.info("Verifying the current URL is the cart page");
        WebDriver driver = testContextSetup.testBase.WebDriverManager();
        if (!driver.getCurrentUrl().equalsIgnoreCase("https://www.saucedemo.com/cart.html")) {
            logger.warn("User is not on the cart page");
            Assert.fail("User is not on the cart page");
        }
    }

    @When("I navigate to the checkout page")
    public void i_navigate_to_the_checkout_page() {
        logger.info("Navigating to the checkout page");
        if (!cartPage.clickCheckoutButton()) {
            logger.warn("Failed to navigate to the checkout page");
            Assert.fail("Navigation to the checkout page failed");
        }
    }

    @Then("I should be on the checkout page")
    public void i_should_be_on_the_checkout_page() throws IOException {
        logger.info("Verifying the current URL is the checkout page");
        WebDriver driver = testContextSetup.testBase.WebDriverManager();
        if (!driver.getCurrentUrl().equalsIgnoreCase("https://www.saucedemo.com/checkout-step-one.html")) {
            logger.warn("User is not on the checkout page");
            Assert.fail("User is not on the checkout page");
        }
    }

    @Given("I am logged in")
    public void i_am_logged_in() {
        logger.info("Logging in to the application");
        loginpage.navigateToLoginPage();
        loginpage.enterLoginCredentials("standard_user", "secret_sauce");
        loginpage.clickLoginButton();
    }

    @When("I click on the {string} button")
    public void i_click_on_the_button(String button) {
        logger.info("Clicking on the {} button", button);
        if (!loginpage.performLogOut()) {
            logger.warn("Failed to log out of the application");
            Assert.fail("Logout operation failed");
        }
    }

    @Then("I should be redirected to the login page")
    public void i_should_be_redirected_to_the_login_page() throws IOException {
        logger.info("Verifying if user is redirected to the login page");
        WebDriver driver = testContextSetup.testBase.WebDriverManager();
        if (!driver.getCurrentUrl().equalsIgnoreCase("https://www.saucedemo.com/")) {
            logger.warn("User was not redirected to the login page");
            Assert.fail("User was not redirected to the login page");
        }
    }
}
