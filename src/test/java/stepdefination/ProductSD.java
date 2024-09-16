package stepdefination;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import PageObjects.Cart;
import PageObjects.LoginPage;
import PageObjects.Product;
import io.cucumber.java.en.*;
import util.TestContextSetup;

public class ProductSD {
    public WebDriver driver;
    public LoginPage loginpage;
    public Product productPage;
    public Cart cartPage;
    public TestContextSetup testContextSetup;

    private static final Logger logger = LogManager.getLogger(ProductSD.class);

    public ProductSD(TestContextSetup testContextSetup) {
        this.testContextSetup = testContextSetup;
        loginpage = testContextSetup.pageObjectManager.getLoginPage();
        productPage = testContextSetup.pageObjectManager.getProductPage();	
        cartPage = testContextSetup.pageObjectManager.getCartPage();
    }

    @Given("User should login")
    public void user_should_login() {
        logger.info("Navigating to the login page");
        loginpage.navigateToLoginPage();
        loginpage.enterLoginCredentials("standard_user", "secret_sauce");
        loginpage.clickLoginButton();
        logger.info("Login successful");
    }

    @When("User should land on product page")
    public void user_should_land_on_product_page() {
        logger.info("Verifying if user landed on the product page");
        if (!loginpage.verifyLogin()) {
            logger.warn("User failed to land on the product page");
        }
        Assert.assertTrue(loginpage.verifyLogin());
    }

    @Then("User should see product list displayed")
    public void user_should_see_product_list_displayed() {
        logger.info("Verifying product list display");
        if (!productPage.countProductsDisplayed(6)) {
            logger.warn("Product list not displayed as expected");
        }
        Assert.assertTrue(productPage.countProductsDisplayed(6));
    }

    @Then("User should see the following products on the page:")
    public void user_should_see_the_following_products_on_the_page(List<String> data) {
        logger.info("Verifying product list content");
        if (!productPage.verifyProducts(data)) {
            logger.warn("Product list does not contain the expected products");
        }
        Assert.assertTrue(productPage.verifyProducts(data));
    }

    @Then("Product name should be {string}")
    public void product_name_should_be(String product) {
        logger.info("Verifying product name: {}", product);
        productPage.clickProduct(product);
        productPage.verifyProductDisplayPage(product);
        if (!productPage.searchedProduct.equals(product)) {
            logger.warn("Product name mismatch: Expected {}, Found {}", product, productPage.searchedProduct);
        }
        Assert.assertEquals(productPage.searchedProduct, product);
    }

    @Then("Product description should be {string}")
    public void product_description_should_be(String desc) {
        logger.info("Verifying product description");
        if (!productPage.searchedProductDescription.equals(desc)) {
            logger.warn("Product description mismatch: Expected {}, Found {}", desc, productPage.searchedProductDescription);
        }
        Assert.assertEquals(productPage.searchedProductDescription, desc);
    }

    @Then("Product price should be {string}")
    public void product_price_should_be(String price) {
        logger.info("Verifying product price");
        if (!productPage.searchedProductPrice.equals(price)) {
            logger.warn("Product price mismatch: Expected {}, Found {}", price, productPage.searchedProductPrice);
        }
        Assert.assertEquals(productPage.searchedProductPrice, price);
    }

    @When("User clicks on add to cart icon for product {string}")
    public void user_clicks_on_add_to_cart_icon_for_product(String product) {
        logger.info("Adding product {} to the cart", product);
        Assert.assertTrue(productPage.addToCartProduct(product));
    }

    @Then("Verify that the cart count increases")
    public void verify_that_the_cart_count_increases() {
        logger.info("Verifying cart count increase");
        if (!(productPage.updatedCount > productPage.intialCount)) {
            logger.warn("Cart count did not increase as expected");
        }
        Assert.assertTrue(productPage.updatedCount > productPage.intialCount);
    }

    @Then("Verify the {string} is listed in the cart")
    public void verify_the_is_listed_in_the_cart(String product) {
        logger.info("Verifying product {} is listed in the cart", product);
        cartPage.clickOnCartIcon();
        if (!cartPage.verifyCartItem(product)) {
            logger.warn("Product {} is not listed in the cart", product);
        }
        Assert.assertTrue(cartPage.verifyCartItem(product));
    }

    @When("User clicks on the product {string}")
    public void user_clicks_on_the_product(String product) {
        logger.info("Clicking on product {}", product);
        if (!productPage.clickProduct(product)) {
            logger.warn("Failed to click on product {}", product);
        }
        Assert.assertTrue(!productPage.clickProduct(product));
    }

    @Then("User should see the product details page")
    public void user_should_see_the_product_details_page() {
        logger.info("Verifying product details page display");
        if (!productPage.verifyCurrentPageIsProduct()) {
            logger.warn("Failed to display product details page");
        }
        Assert.assertTrue(productPage.verifyCurrentPageIsProduct());
    }
}
