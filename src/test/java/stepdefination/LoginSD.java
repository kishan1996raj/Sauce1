package stepdefination;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import PageObjects.LoginPage;
import io.cucumber.java.en.*;
import util.TestContextSetup;

public class LoginSD {

    public WebDriver driver;
    public LoginPage loginpage;
    public TestContextSetup testContextSetup;

    private static final Logger logger = LogManager.getLogger(LoginSD.class);

    public LoginSD(TestContextSetup testContextSetup) {
        this.testContextSetup = testContextSetup;
        this.loginpage = testContextSetup.pageObjectManager.getLoginPage();
    }

    @Given("The user should be on login page")
    public void the_user_should_be_on_login_page() {
        logger.info("Navigating to the login page");
        if (!loginpage.navigateToLoginPage()) {
            logger.warn("Failed to navigate to the login page");
            Assert.fail("Navigation to the login page failed");
        }
    }

    @When("The user enters {string} as username and {string} as password")
    public void the_user_enters_as_username_and_as_password(String username, String password) throws InterruptedException {
        logger.info("Entering username: {} and password: {}", username, password);
        if (!loginpage.enterLoginCredentials(username, password)) {
            logger.warn("Failed to enter login credentials for username: {} and password: {}", username, password);
            Assert.fail("Failed to enter login credentials");
        }
    }

    @When("The users clicks login button")
    public void the_users_clicks_login_button() {
        logger.info("Clicking the login button");
        loginpage.clickLoginButton();
    }

    @Then("Verify error message is displayed for wrong credentials")
    public void verify_error_message_is_displayed_for_wrong_credentials() {
        logger.info("Verifying if error message is displayed for wrong credentials");
        if (!loginpage.verifyLogin()) {
            logger.warn("Error message for wrong credentials is not displayed");
            Assert.fail("Error message for wrong credentials not displayed");
        }
    }

    @Then("Verify error message is displayed for empty fields")
    public void verify_error_message_is_displayed_for_empty_fields() {
        logger.info("Verifying if error message is displayed for empty fields");
        if (!loginpage.verifyLogin()) {
            logger.warn("Error message for empty fields is not displayed");
            Assert.fail("Error message for empty fields not displayed");
        }
    }

    @Then("Verify user is redirect to product page")
    public void verify_user_is_redirect_to_product_page() {
        logger.info("Verifying if user is redirected to the product page");
        if (!loginpage.verifyLogin()) {
            logger.warn("User was not redirected to the product page");
            Assert.fail("User was not redirected to the product page");
        }
    }
}
