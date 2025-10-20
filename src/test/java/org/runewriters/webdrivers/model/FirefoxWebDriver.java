package org.runewriters.webdrivers.model;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;

import java.io.File;

public class FirefoxWebDriver extends WebDriverManager{

    private GeckoDriverService firefoxDriverService;

    @Override
    protected void startService() {
        if (null == firefoxDriverService) {
            try {
                firefoxDriverService = new GeckoDriverService.Builder()
                        .usingDriverExecutable(new File("src/test/resources/geckodriver"))
                        .usingAnyFreePort()
                        .build();
                firefoxDriverService.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void stopService() {
        if (null != firefoxDriverService && firefoxDriverService.isRunning())
            firefoxDriverService.stop();
    }

    @Override
    protected void createDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("src/test/resources/firefox/firefox");
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        options.setHeadless(true);
        driver = new FirefoxDriver(firefoxDriverService,options);
    }
}
