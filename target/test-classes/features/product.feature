Feature: Product page Functionality

Scenario: Verify Product Visibility
Given User should login 
When User should land on product page 
Then User should see product list displayed
And User should see the following products on the page:
     | Sauce Labs Backpack  |
     | Sauce Labs Bike Light |

Scenario: Verify Product Details
Given User should login
When  User clicks on the product "Sauce Labs Backpack"
Then User should see the product details page
And  Product name should be "Sauce Labs Backpack"
And  Product description should be "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection."
And  Product price should be "$29.99"

Scenario: Verify Add to Cart
Given User should login 
When User clicks on add to cart icon for product "Sauce Labs Backpack"
Then Verify that the cart count increases 
And Verify the "Sauce Labs Backpack" is listed in the cart
