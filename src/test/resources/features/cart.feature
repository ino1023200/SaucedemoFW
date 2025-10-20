Feature: Navigating through Cart page

  Background: Using the cart page
    Given I am on the cart page

  Scenario: Clicking on the  title of the product
    When I click on the  title of the product
    Then I will go to the inventory item page of that product

