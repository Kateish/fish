import org.junit.Test;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


/**
 * CucumberRunner: parse features, run CucumberSteps
 */

@RunWith(Cucumber.class)
@CucumberOptions(
	features = {"src/test/resources/feature"}, // Features path
	glue = {"cuke/steps"}, // Steps package
	plugin = {"pretty", "html:target/cucumber"} // Reports output path
//		tags = {"@success", "@fail"}
	)
	
public class CucumberRunner {
}
