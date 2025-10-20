package org.runewriters.config;

import org.openqa.selenium.WebDriver;
import org.runewriters.webdrivers.WebDriverFactory;
import org.runewriters.webdrivers.model.WebDriverManager;
import org.runewriters.webdrivers.model.WebDriverType;

/**
 * Singleton class để quản lý WebDriver tập trung
 * Đảm bảo chỉ có 1 driver instance trong mỗi scenario
 */
public class DriverManager {

    // ThreadLocal để support parallel execution trong tương lai
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<WebDriverManager> manager = new ThreadLocal<>();

    // Private constructor để prevent instantiation
    private DriverManager() {}

    /**
     * Lấy hoặc tạo WebDriver instance
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            // Đọc browser type từ system property
            // Default: chrome
            String browserType = System.getProperty("browser", "chrome");
            WebDriverType type = getBrowserType(browserType);

            // Tạo driver qua existing factory
            WebDriverManager webDriverManager = WebDriverFactory.getManager(type);
            WebDriver webDriver = webDriverManager.getDriver();

            // Lưu vào ThreadLocal
            driver.set(webDriver);
            manager.set(webDriverManager);

            System.out.println("Created new " + browserType.toUpperCase() + " driver");
        }
        return driver.get();
    }

    /**
     * Lấy driver hiện tại (không tạo mới nếu chưa có)
     * Dùng cho Hooks để screenshot
     * @return WebDriver instance hoặc null
     */
    public static WebDriver getCurrentDriver() {
        return driver.get();
    }

    /**
     * Đóng driver và cleanup
     */
    public static void quitDriver() {
        WebDriverManager webDriverManager = manager.get();
        if (webDriverManager != null && driver.get() != null) {
            webDriverManager.quitDriver();
            driver.remove();
            manager.remove();
            System.out.println("Driver closed and cleaned up");
        }
    }

    /**
     * Convert string browser name to WebDriverType enum
     * @param browserName tên browser từ system property
     * @return WebDriverType enum
     */
    private static WebDriverType getBrowserType(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                return WebDriverType.CHROME;
            case "firefox":
                return WebDriverType.FIREFOX;
            case "opera":
                return WebDriverType.OPERA;
            case "safari":
                return WebDriverType.SAFARI;
            default:
                System.out.println("⚠ Unknown browser: " + browserName + ", defaulting to CHROME");
                return WebDriverType.CHROME;
        }
    }
}