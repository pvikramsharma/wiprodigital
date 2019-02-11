package org.openweather.stepdefinitions;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openweather.selenium.DriverManager;
import org.openweather.selenium.HomePageObjects;

import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.qameta.allure.Attachment;

public class BaseFunctions extends DriverManager {

	HomePageObjects hPG;

	public BaseFunctions() {
		hPG = new HomePageObjects();
	}

	@Given("^the chrome browser is opened$")
	public void the_chrome_browser_is_opened() throws Throwable {
		hPG.createDriver();
	}

	@When("^user navigates to HomePage \"(.*?)\"$")
	public void user_navigates_to_HomePage(String arg1) throws Throwable {
		hPG.getHomepageURL(arg1);
	}

	@Then("^user should be displayed with five day forecast for the \"(.*?)\" city$")

	public void user_should_be_displayed_with_five_day_forecast_for_the_city(String arg1) throws Throwable {
		hPG.getAllWeatherDaysCount();
	}

	@And("^user verify if the date starting order is correct$")
	public void userVerifyIfTheDateStartingOrderIsCorrect() throws Throwable {
		Boolean currentDate = hPG.getWeatherForecastDate();
		if (!currentDate)
			throw new Exception("Date does not start in correct order");
	}

	@Given("^user is on the home page$")
	public void user_is_on_the_home_page() throws Throwable {
		hPG.createDriver(hPG.baseURL);
	}

	@When("^user enters Edinburgh on the home page$")
	public void user_enters_Edinburgh_on_the_home_page() throws Throwable {
		Reporter.addStepLog("Adding EDINURGH to city field");
		hPG.inputCityName("Edinburgh");
	}

	@Then("^user should be displayed with five day forecast for the Edinburgh city$")
	public void user_should_be_displayed_with_five_day_forecast_for_the_Edinburgh_city() throws Throwable {
		hPG.getAllWeatherDaysCount();
	}

	@When("^user enters Glasgow on the home page$")
	public void user_enters_Glassgow_on_the_home_page() throws Throwable {
		hPG.inputCityName("Glasgow");
	}

	@Then("^user should be displayed with five day forecast for the Glasgow city$")
	public void user_should_be_displayed_with_five_day_forecast_for_the_Glassgow_city() throws Throwable {
		hPG.getAllWeatherDaysCount();
	}

	@Then("^user should be displayed with Unable to fetch the value$")
	public void user_should_be_displayed_with_Unable_to_fetch_the_value() throws Throwable {
		if (hPG.getErrorMessage().equalsIgnoreCase("Error retrieving the forecast")) {
			System.out.println("error present");
		} else
			throw new Exception("Error not displayed");
	}

	@When("^user enters \"([^\"]*)\" on the home page$")
	public void user_enters_on_the_home_page(String arg1) throws Throwable {
		hPG.inputCityName(arg1);
	}

	@When("^user enters sasdad on the home page$")
	public void user_enters_sasdad_on_the_home_page() throws Throwable {
		hPG.inputCityName("sasdad");
	}

	@When("^user enters (\\d+) on the home page$")
	public void user_enters_on_the_home_page(int arg1) throws Throwable {
		Reporter.addStepLog("Adding city Perth");

		hPG.inputCityName("Perth");
	}

	@When("^Clicked on the first date displyed for Perth$")
	public void Clicked_on_the_first_date_displyed_for_Perth() {
		hPG.clickOnFirstDate();

	};

	@Then("^user should be displayed with all details of the forecast for every (\\d+) hourly forecast entry for the selected day$")
	public void user_should_be_displayed_with_all_details_of_the_forecast_for_every_hourly_forecast_entry_for_the_selected_day(
			int arg1) throws Throwable {
		hPG.checkForThreeHours();
	}

	@After
	public void afterScenario(Scenario scenario) throws IOException {
		Date d = new Date();

		if (driver != null && scenario.isFailed()) {
			
			// THis below 1 line for cucumber embed
			scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
			

			// For extent report
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenshotName = d.toString().replace(":", "_").replace(" ", "") + ".jpg";
			String screenshotPath = System.getProperty("user.dir") + "\\target\\reports\\imgs\\" + screenshotName;
			FileUtils.copyFile(srcFile,
					new File(System.getProperty("user.dir") + "\\target\\reports\\imgs\\" + screenshotName));
			Reporter.addStepLog("Taking a screenshot!");
			Reporter.addStepLog("<br>");
			// screenshot image click opens in a new page.
			Reporter.addStepLog("<a target=\"_blank\", href=" + screenshotPath + "><img src=" + screenshotPath
					+ " height=200 width=300></img></a>");

			// Allure
			saveScreenshot(scenario.getName(), driver);

		}
		hPG.teardown();

	}

//Allure

	@Attachment(value = "Screenshot of {0}", type = "image/png")
	public byte[] saveScreenshot(String name, WebDriver driver) {
		return ((TakesScreenshot) DriverManager.driver).getScreenshotAs(OutputType.BYTES);
	}

}
