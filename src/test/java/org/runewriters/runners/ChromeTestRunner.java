
package org.runewriters.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"org.runewriters.stepdefs", "org.runewriters.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/chrome/cucumber.html",
                "json:target/cucumber-reports/chrome/cucumber.json",
                "junit:target/cucumber-reports/chrome/cucumber.xml"
        },
        publish = true
)
public class ChromeTestRunner {

    // Static block để set browser type
    // Chạy trước khi JUnit khởi tạo runner
    static {
        System.setProperty("browser", "chrome");
    }
}