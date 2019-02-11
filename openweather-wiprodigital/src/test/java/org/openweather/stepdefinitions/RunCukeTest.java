package org.openweather.stepdefinitions;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/org/openweather/features", glue = "org/openweather/stepdefinitions", monochrome = true, plugin = {
		"pretty", "html:target/reports/test-report", 
		"json:target/reports/test-report.json",
		"com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:target/reports/test-report.html", }
// , tags = "@one3"

)
public class RunCukeTest extends AbstractTestNGCucumberTests {
}