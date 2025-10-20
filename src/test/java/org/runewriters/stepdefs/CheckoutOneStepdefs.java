
package org.runewriters.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.runewriters.config.DriverManager;
import org.runewriters.pom.*;

public class CheckoutOneStepdefs {

    private WebDriver webDriver;
    private LoginPage loginPage;
    private CartPage cartPage;
    private InventoryPage inventory;
    private CheckoutOnePage checkoutOnePage;
    private CheckoutTwoPage checkoutTwoPage;

    @Given("I am on the Checkout One page")
    public void iAmOnTheCheckoutOnePage() {
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
    }

    @When("I fill the form with proper credentials")
    public void iFillTheFormWithProperCredentials() {
        webDriver = DriverManager.getDriver();
        checkoutOnePage.enterFirstNameInTextBox("Suyash");
        checkoutOnePage.enterLastNameInTextBox("Srivastava");
        checkoutOnePage.enterZipCodeInTextBox("E14 NS");
    }

    @When("I click on Continue button")
    public void iClickOnContinueButton() {
        webDriver = DriverManager.getDriver();
        checkoutOnePage.clickContinueButton();
    }

    @Then("I will go to the Checkout Two page")
    public void iWillGoToTheCheckoutTwoPage() {
        webDriver = DriverManager.getDriver();
        checkoutTwoPage = new CheckoutTwoPage(webDriver);
        Assertions.assertEquals("https://www.saucedemo.com/checkout-step-two.html", checkoutTwoPage.getCurrentURL());
    }

    @When("I click on Cancel button in checkout one page")
    public void iClickOnCancelButtonInCheckoutOnePage() {
        webDriver = DriverManager.getDriver();
        checkoutOnePage.clickCancelButton();
    }

    @Then("I will go to the Your Cart Pag")
    public void iWillGoToTheYourCartPag() {
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals("https://www.saucedemo.com/cart.html", cartPage.getCurrentURL());
    }

    @When("I click on Cart Icone in checkout one page")
    public void iClickOnCartIconeInCheckoutOnePage() {
        webDriver = DriverManager.getDriver();
        checkoutOnePage.clickCartIcon();
    }

    @Then("I will go to the Your Cart Pge")
    public void iWillGoToTheYourCartPge() {
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals("https://www.saucedemo.com/cart.html", cartPage.getCurrentURL());
    }

}