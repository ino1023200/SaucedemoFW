Feature: Able to verify if the order has been placed and navigate to different pages on the website

  Background: Setting up the Browser
    Given I am on the Checkout Complete page


  Scenario: Getting to the Inventory page via back home button from the Checkout Complete page
    When I click on back home button
    Then I will go to the Inventory Page

