package org.runewriters.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.runewriters.config.DriverManager;
import org.runewriters.pom.*;

public class CheckoutTwoStepdefs {

    private WebDriver webDriver;
    private LoginPage loginPage;
    private CartPage cartPage;
    private InventoryPage inventory;
    private CheckoutOnePage checkoutOnePage;
    private CheckoutTwoPage checkoutTwoPage;
    private CheckoutCompletePage checkoutCompletePage;

    @Given("I am on the Checkout Two page")
    public void iAmOnTheCheckoutTwoPage() {
        webDriver = DriverManager.getDriver();
        webDriver.get("https://www.saucedemo.com");

        loginPage = new LoginPage(webDriver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogInButton();

        inventory = new InventoryPage(webDriver);
        inventory.clickAddToCartOrRemoveButtonAtIndex(0);
        inventory.clickCartIcon();

        cartPage = new CartPage(webDriver);
        cartPage.clickCheckoutButton();

        checkoutOnePage = new CheckoutOnePage(webDriver);
        checkoutOnePage.enterFirstNameInTextBox("Suyash");
        checkoutOnePage.enterLastNameInTextBox("Srivastava");
        checkoutOnePage.enterZipCodeInTextBox("E14 NS");
        checkoutOnePage.clickContinueButton();

        checkoutTwoPage = new CheckoutTwoPage(webDriver);
    }

    @When("I click on the finish button")
    public void iClickOnTheFinishButton() {
        webDriver = DriverManager.getDriver();
        checkoutTwoPage.clickFinishButton();
    }

    @Then("I will go to the checkout complete page")
    public void iWillGoToTheCheckoutCompletePage() {
        webDriver = DriverManager.getDriver();
        checkoutCompletePage = new CheckoutCompletePage(webDriver);
        Assertions.assertEquals("https://www.saucedemo.com/checkout-complete.html", checkoutCompletePage.getCurrentURL());
    }

    @When("I click on cancel button")
    public void iClickOnCancelButton() {
        webDriver = DriverManager.getDriver();
        checkoutTwoPage.clickCancelButton();
    }

    @Then("I will go to the Inventory Pagee")
    public void iWillGoToTheInventoryPagee() {
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", inventory.getCurrentURL());
    }

    @When("I click on Cart icone")
    public void iClickOnCartIcone() {
        webDriver = DriverManager.getDriver();
        checkoutTwoPage.clickCartIcon();
    }

    @Then("I will go to the Your Cart pagee")
    public void iWillGoToTheYourCartPagee() {
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals("https://www.saucedemo.com/cart.html", cartPage.getCurrentURL());
    }

}