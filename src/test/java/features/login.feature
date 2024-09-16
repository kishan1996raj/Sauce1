Feature: Login Flow Scenarios

Scenario: Verify valid login
Given The user should be on login page
When The user enters "standard_user" as username and "secret_sauce" as password
And The users clicks login button
Then Verify user is redirect to product page

Scenario: Verify invalid login with incorrect credentials
Given The user should be on login page
When The user enters "standard_use11r" as username and "secret_sauce111" as password
And The users clicks login button
Then Verify error message is displayed for wrong credentials 

Scenario: Verify invalid login with empty fields
Given The user should be on login page
When The user enters "" as username and "" as password
And The users clicks login button
Then Verify error message is displayed for empty fields