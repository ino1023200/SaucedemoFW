package org.runewriters.webdrivers.model;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class ChromeWebDriver extends WebDriverManager{

    private ChromeDriverService chromeDriverService;

    @Override
    protected void startService() {
        if (null == chromeDriverService) {
            try {
                //System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");
                chromeDriverService = new ChromeDriverService.Builder()
                        .usingDriverExecutable(new File("src/test/resources/chromedriver"))
                        .usingAnyFreePort()
                        .build();
                chromeDriverService.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void stopService() {
        if (null != chromeDriverService && chromeDriverService.isRunning())
            chromeDriverService.stop();
    }

    @Override
    protected void createDriver() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary("src/test/resources/chrome-linux64/chrome");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(chromeDriverService, options);
    }
}
