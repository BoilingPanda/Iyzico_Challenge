import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/", plugin = {
        "json:target/cucumber.json", "pretty",
        "html:target/cucumber-reports" },
        glue={"StepDefinitions"})
public class TestRunner {
}
