Feature: Navigation and Logout Functionality

  Scenario: Verify Navigation Between Pages
    Given I am on the homepage
    When I navigate to the product page
    Then I should be on the product page
    When I navigate to the cart page
    Then I should be on the cart page
    When I navigate to the checkout page
    Then I should be on the checkout page
  

  Scenario: Verify Logout Functionality
    Given I am logged in
    When I click on the "Logout" button
    Then I should be redirected to the login page
    
