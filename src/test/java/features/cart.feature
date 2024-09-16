Feature: Cart Functionality

 Scenario: Verify View Cart
    Given I am on the product page
    When I add a product "Sauce Labs Backpack" to the cart
    And I click on the cart icon
    Then I should see the product "Sauce Labs Backpack" in the cart

  Scenario: Verify Remove Item from Cart
    Given I have a product "Sauce Labs Backpack" in the cart
    When I remove the product "Sauce Labs Backpack" from the cart
    Then the product "Sauce Labs Backpack" should no longer be in the cart
    And the cart count should be updated

 Scenario: Verify Checkout Process
    Given I have a product "Sauce Labs Backpack" in the cart
    When I click on the Checkout button
    And I enter valid checkout information with first name "John", last name "Doe", and postal code "12345"
    And I complete the checkout process
    Then I should see the order confirmation page