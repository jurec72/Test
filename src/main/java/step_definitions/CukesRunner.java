package step_definitions;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/main/resources/features", plugin = { "pretty:target/cucumber-pretty.txt",
        "json:target/cucumber.json"}, tags = { "@apiTest" })
public class CukesRunner {

}
