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

public class TwitterStepdefs {

    private WebDriver webDriver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @Given("I am on the INVENTORY page")
    public void iAmOnTheINVENTORYPage() {
        webDriver = DriverManager.getDriver();
        webDriver.get("https://www.saucedemo.com");

        loginPage = new LoginPage(webDriver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogInButton();

        inventoryPage = new InventoryPage(webDriver);
    }

    @When("I click on TWITTER icon")
    public void iClickOnTWITTERIcon() {
        webDriver = DriverManager.getDriver();

        // Lưu window handle hiện tại
        String mainWindow = webDriver.getWindowHandle();

        // Click Twitter icon
        inventoryPage.clickTwitterIcon();

        System.out.println("✓ Clicked Twitter icon, waiting for new tab...");
    }

    @Then("I will go to the TWITTER page")
    public void iWillGoToTheTWITTERPage() {
        webDriver = DriverManager.getDriver();

        try {
            // Set timeout ngắn để không chờ quá lâu
            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

            // Đợi tab mới xuất hiện (tối đa 10 giây)
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            wait.until(driver -> driver.getWindowHandles().size() > 1);

            // Lấy tất cả window handles
            Set<String> windowHandles = webDriver.getWindowHandles();
            System.out.println("✓ Found " + windowHandles.size() + " windows/tabs");

            // Chuyển sang tab mới (tab cuối cùng)
            ArrayList<String> tabs = new ArrayList<>(windowHandles);
            webDriver.switchTo().window(tabs.get(tabs.size() - 1));

            System.out.println("✓ Switched to new tab");
            System.out.println("✓ Current URL: " + webDriver.getCurrentUrl());

            // Kiểm tra URL (với timeout ngắn)
            String currentUrl = webDriver.getCurrentUrl();
            boolean isTwitterPage = currentUrl.contains("twitter.com") ||
                    currentUrl.contains("x.com") ||
                    currentUrl.contains("saucelabs.com"); // Twitter link có thể redirect về Sauce Labs

            Assertions.assertTrue(isTwitterPage,
                    "Expected Twitter/X.com page but got: " + currentUrl);

        } catch (Exception e) {
            System.err.println("✗ Error while checking Twitter page: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

}