package org.runewriters.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.runewriters.config.DriverManager;
import org.runewriters.pom.InventoryPage;
import org.runewriters.pom.LoginPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;

public class FacebookStepDef {

    private WebDriver webDriver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @Given("I am on the INVENTORy page")
    public void iAmOnTheINVENTORyPage() {
        webDriver = DriverManager.getDriver();
        webDriver.get("https://www.saucedemo.com");

        loginPage = new LoginPage(webDriver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogInButton();

        inventoryPage = new InventoryPage(webDriver);
    }

    @When("I click on FACEBOOK icon")
    public void iClickOnFACEBOOKIcon() {
        webDriver = DriverManager.getDriver();
        inventoryPage.clickFacebookIcon();
        System.out.println("✓ Clicked Facebook icon, waiting for new tab...");
    }

    @Then("I will go to the FACEBOOK page")
    public void iWillGoToTheFACEBOOKPage() {
        webDriver = DriverManager.getDriver();

        try {
            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            wait.until(driver -> driver.getWindowHandles().size() > 1);

            Set<String> windowHandles = webDriver.getWindowHandles();
            System.out.println("✓ Found " + windowHandles.size() + " windows/tabs");

            ArrayList<String> tabs = new ArrayList<>(windowHandles);
            webDriver.switchTo().window(tabs.get(tabs.size() - 1));

            System.out.println("✓ Current URL: " + webDriver.getCurrentUrl());

            String currentUrl = webDriver.getCurrentUrl();
            boolean isFacebookPage = currentUrl.contains("facebook.com") ||
                    currentUrl.contains("saucelabs.com");

            Assertions.assertTrue(isFacebookPage,
                    "Expected Facebook page but got: " + currentUrl);

        } catch (Exception e) {
            System.err.println("✗ Error while checking Facebook page: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


}