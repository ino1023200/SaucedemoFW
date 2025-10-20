package org.runewriters.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.runewriters.config.DriverManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Cucumber Hooks - Quản lý lifecycle của test scenarios
 * Enhanced cho multi-browser parallel execution
 */
public class Hooks {


    @Before
    public void beforeScenario(Scenario scenario) {
        String browser = System.getProperty("browser", "chrome");
        String threadName = Thread.currentThread().getName();
        long threadId = Thread.currentThread().getId();


    }
    @After(order = 1)
    public void takeScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriver driver = DriverManager.getCurrentDriver();

            if (driver != null) {
                try {
                    String browser = System.getProperty("browser", "chrome");
                    String threadId = String.valueOf(Thread.currentThread().getId());

                    // Screenshot filename: browser-threadId-scenarioName-timestamp.png
                    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
                    String fileName = String.format("%s-thread%s-%s-%s.png",
                            browser, threadId, scenarioName, timestamp);

                    // Lưu vào thư mục theo browser
                    String screenshotDir = "target/screenshots/" + browser;
                    File directory = new File(screenshotDir);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    File destination = new File(screenshotDir + "/" + fileName);
                    FileUtils.copyFile(screenshot, destination);

                    System.out.println(String.format(
                            "[%s] [Thread-%d] Screenshot saved: %s",
                            browser.toUpperCase(),
                            Thread.currentThread().getId(),
                            destination.getAbsolutePath()
                    ));

                    // Attach to Cucumber report
                    byte[] screenshotBytes = FileUtils.readFileToByteArray(screenshot);
                    scenario.attach(screenshotBytes, "image/png", fileName);

                } catch (Exception e) {
                    System.err.println("Failed to capture screenshot: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.err.println("Cannot take screenshot - Driver is null");
            }
        }
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        String browser = System.getProperty("browser", "chrome");
        String threadName = Thread.currentThread().getName();
        long threadId = Thread.currentThread().getId();

        // Cleanup driver
        DriverManager.quitDriver();

    }
}