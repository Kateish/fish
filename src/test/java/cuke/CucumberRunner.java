package cuke;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


/**
 * CucumberRunner: parse features, run CucumberSteps
 */

@RunWith(Cucumber.class)
@CucumberOptions(
	features = {"src/test/java/cuke"}, // Features path
	glue = {"cuke/steps"}, // Steps package
	plugin = {"pretty", "html:target/cucumber"} // Reports output path
	)
	
public class CucumberRunner {
}
