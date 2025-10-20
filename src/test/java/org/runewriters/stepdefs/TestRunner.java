package org.runewriters.stepdefs;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"org.runewriters.stepdefs", "org.runewriters.hooks"},  // ← THÊM hooks package
        plugin = {
                "pretty",
                "html:target/testReport.html",
                "json:target/jsonReport.json"
        },
        //tags can be used to run tests based on the individual scenarios
        //one scenario at a time
        //tags = "@user1",
        publish = true
)
public class TestRunner {
    // Browser type có thể được set qua:
    // 1. System property: mvn test -Dbrowser=chrome
    // 2. System property: mvn test -Dbrowser=firefox
    // 3. Default: chrome (nếu không set)
}