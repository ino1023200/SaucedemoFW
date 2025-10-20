package org.runewriters.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.runewriters.config.DriverManager;
import org.runewriters.pom.InventoryPage;
import org.runewriters.pom.LoginPage;

public class LoginStepDefs {

    // Không cần static, không cần WebDriverManager
    private WebDriver webDriver;
    private LoginPage loginPage;
    private InventoryPage inventory;

    @Given("I am on the Login Page")
    public void iAmOnTheLoginPage() {
        webDriver = DriverManager.getDriver();
        webDriver.get("https://www.saucedemo.com");
        loginPage = new LoginPage(webDriver);
    }

    @When("I insert {string} and secret_sauce")
    public void iInsertAndSecret_sauce(String username) {
        webDriver = DriverManager.getDriver();
        loginPage.enterUsername(username);
        loginPage.enterPassword("secret_sauce");
    }

    @And("I click on the login button")
    public void iClickOnTheLoginButton() {
        webDriver = DriverManager.getDriver();
        loginPage.clickLogInButton();
        inventory = new InventoryPage(webDriver);
    }

    @Then("I will go to the InventoryPage")
    public void iWillGoToTheInventoryPage() {
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", inventory.getCurrentURL());
    }

    @When("I insert an invalid username and a valid password")
    public void iInsertAnInvalidUsernameAndAValidPassword() {
        webDriver = DriverManager.getDriver();
        loginPage.enterUsername("invalid_user");
        loginPage.enterPassword("secret_sauce");
    }

    @Then("I will stay in the Login Page")
    public void iWillStayInTheLoginPage() {
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals("https://www.saucedemo.com/", loginPage.getCurrentURL());
    }

    @When("I insert a valid username and an invalid password")
    public void iInsertAValidUsernameAndAnInvalidPassword() {
        webDriver = DriverManager.getDriver();
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("invalid_password");
    }

}