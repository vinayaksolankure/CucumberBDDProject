package TestRunner;

import org.junit.runner.RunWith;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
//import io.cucumber.junit.Cucumber;
//import io.cucumber.junit.CucumberOptions;


//@RunWith(Cucumber.class)
@CucumberOptions(
		features = {".//Features/Customers.feature",".//Features/LoginFeature.feature"}, // Executes specific files separated by comma
		//features = ".//Features/", //executes all the files from the folder
		glue = "StepDefinition",
		dryRun = false,
		monochrome = true,
		tags = "@Sanity", //tags = or, and, and not
		//plugin = {"pretty","junit:target/cucumber-reports/report_xml.xml","json:target/cucumber-reports/report_json.json","html:target/cucumber-reports/report_html.html"}
		plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
		)
// JSON Report
//plugin = {"pretty","json:target/cucumber-reports/report_json.json"}
// HTML Report
//plugin = {"pretty","html:target/cucumber-reports/report_html.html"}

public class Run extends AbstractTestNGCucumberTests{
	/*This class will be empty*/
}
