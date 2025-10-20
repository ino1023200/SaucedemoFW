Feature: Able to verify personal details and navigate to different pages on the website

  Background: Setting up the Browser
    Given I am on the Checkout Two page

  Scenario: Getting to the Checkout Complete page from the Checkout Two page
    When I click on the finish button
    Then I will go to the checkout complete page

