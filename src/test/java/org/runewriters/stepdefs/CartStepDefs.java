
package org.runewriters.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.runewriters.config.DriverManager;
import org.runewriters.pom.CartPage;
import org.runewriters.pom.CheckoutOnePage;
import org.runewriters.pom.InventoryPage;
import org.runewriters.pom.InventoryItemPage;
import org.runewriters.pom.LoginPage;

public class CartStepDefs {

    private WebDriver webDriver;
    private LoginPage loginPage;
    private CartPage cartPage;
    private InventoryPage inventory;
    private InventoryItemPage inventoryItemPage;
    private CheckoutOnePage checkoutOnePage;

    // ========== EXISTING STEPS ==========

    @Given("I am on the Your Cart page")
    public void iAmOnTheYourCartPage() {
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
    }

    @When("I click on the Checkout button")
    public void iClickOnTheCheckoutButton() {
        webDriver = DriverManager.getDriver();
        cartPage.clickCheckoutButton();
    }

    @Then("I will go to the Checkout One Page")
    public void iWillGoToTheCheckoutOnePage() {
        webDriver = DriverManager.getDriver();
        checkoutOnePage = new CheckoutOnePage(webDriver);
        Assertions.assertEquals("https://www.saucedemo.com/checkout-step-one.html", checkoutOnePage.getCurrentURL());
    }

    @When("I click on Continue Shopping button")
    public void iClickOnContinueShoppingButton() {
        webDriver = DriverManager.getDriver();
        cartPage.clickContinueShoppingButton();
    }

    @Then("I will go to the Inventory Pge")
    public void iWillGoToTheInventoryPge() {
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", inventory.getCurrentURL());
    }

    @When("I click on Cart Icon in Cart page")
    public void iClickOnCartIconInCartPage() {
        webDriver = DriverManager.getDriver();
        cartPage.clickCartIcon();
    }

    @Then("I will stay on the Your Cart Page")
    public void iWillStayOnTheYourCartPage() {
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals("https://www.saucedemo.com/cart.html", cartPage.getCurrentURL());
    }

    @And("I can remove an item from the cart")
    public void iCanRemoveAnItemFromTheCart() {
        webDriver = DriverManager.getDriver();
        cartPage.clickRemoveItemAtIndex(0);
    }

    // ========== NEW STEPS FOR cart.feature ==========

    @Given("I am on the cart page")
    public void iAmOnTheCartPage() {
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
    }

    @When("I click on the  title of the product")
    public void iClickOnTheTitleOfTheProduct() {
        webDriver = DriverManager.getDriver();
        cartPage.clickItemAtIndex(0);
    }

    @Then("I will go to the inventory item page of that product")
    public void iWillGoToTheInventoryItemPageOfThatProduct() {
        webDriver = DriverManager.getDriver();
        inventoryItemPage = new InventoryItemPage(webDriver);
        Assertions.assertTrue(inventoryItemPage.getCurrentURL().contains("inventory-item.html?id="));
    }

    @When("I click on {string} in cart page")
    public void iClickOnInCartPage(String buttonText) {
        webDriver = DriverManager.getDriver();
        if (buttonText.equalsIgnoreCase("continue shopping")) {
            cartPage.clickContinueShoppingButton();
        } else if (buttonText.equalsIgnoreCase("Checkout")) {
            cartPage.clickCheckoutButton();
        }
    }

    @Then("I will go back to the Inventory page")
    public void iWillGoBackToTheInventoryPage() {
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", inventory.getCurrentURL());
    }

    @Then("I will go the {string} page")
    public void iWillGoThePage(String pageName) {
        webDriver = DriverManager.getDriver();
        if (pageName.equalsIgnoreCase("checkout step one")) {
            checkoutOnePage = new CheckoutOnePage(webDriver);
            Assertions.assertEquals("https://www.saucedemo.com/checkout-step-one.html", checkoutOnePage.getCurrentURL());
        }
    }

    @When("An item is displayed inside the cart")
    public void anItemIsDisplayedInsideTheCart() {
        webDriver = DriverManager.getDriver();
        // Kiểm tra có ít nhất 1 item trong cart
        Assertions.assertTrue(cartPage.getCartSize() > 0, "Cart should have at least one item");
    }

    @Then("I will have an option to remove that item from the cart")
    public void iWillHaveAnOptionToRemoveThatItemFromTheCart() {
        webDriver = DriverManager.getDriver();
        // Kiểm tra remove button có thể click được
        int cartSizeBefore = cartPage.getCartSize();
        cartPage.clickRemoveItemAtIndex(0);
        // Có thể thêm verify cart size giảm nếu cần
        System.out.println("Remove button is clickable");
    }

    @When("An item is displayed in the cart page")
    public void anItemIsDisplayedInTheCartPage() {
        webDriver = DriverManager.getDriver();
        // Kiểm tra có item trong cart
        Assertions.assertTrue(cartPage.getCartSize() > 0, "Cart should have at least one item");
    }

    @Then("I will see the price of the product")
    public void iWillSeeThePriceOfTheProduct() {
        webDriver = DriverManager.getDriver();
        String price = cartPage.getItemPriceAtIndex(0);
        Assertions.assertNotNull(price, "Price should be visible");
        Assertions.assertTrue(price.startsWith("$"), "Price should start with $");
        System.out.println("✓ Product price is visible: " + price);
    }

}