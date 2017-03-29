package cucumberjava;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(format = {"pretty"}, features= {"src/cucumber/src/test/java/resources/Google.feature"})
public class RunTest {
}
