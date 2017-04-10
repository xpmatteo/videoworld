package cucumberjava;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(format = {"pretty",  "html:target/cucumber" }, features= {"src/cucumber/src/test/resources/"})
public class RunTest {

}
