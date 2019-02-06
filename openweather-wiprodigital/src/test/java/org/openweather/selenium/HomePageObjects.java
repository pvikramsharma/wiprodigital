package org.openweather.selenium;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;



public class HomePageObjects extends DriverManager{
	
	private static final long timeoutInSeconds = 10;
	String cityXpath = "//input[@id='city']";
	public static WebDriverWait waitTime;
	
	//will check if the home is displayed correctly based of the title of the page
	public Boolean ishomepageDisplayed(String title) {
		String homePageTitle = driver.getTitle();
		if(title.equalsIgnoreCase(homePageTitle)){
			return true;
		}
		return false;
	}

	
	//input the city based on the value passed by
	public void inputCityName(String cCityName) {
		String cityXpath = "//input[@id='city']";
		driver.findElement(By.xpath(cityXpath)).clear();
		driver.findElement(By.xpath(cityXpath)).sendKeys(cCityName);
		driver.findElement(By.xpath(cityXpath)).sendKeys(Keys.RETURN);
		
	}
	
	public String getErrorMessage(){
		return driver.findElement(By.xpath("//*[@id='root']/div/div")).getText();
	}
	
	//this function will return either current date dd or current Hour HH based on the parameter date or time
	public String getCurrentDateorTime(String pDateorTime) throws Exception{
		
			DateFormat getCurrentDate = new SimpleDateFormat("dd");
			DateTimeFormatter getCurrentHour = DateTimeFormatter.ofPattern("HH");
			if(pDateorTime.equalsIgnoreCase("date")){
				Date date = new Date();
				return getCurrentDate.format(date);
			}
			else if(pDateorTime.equalsIgnoreCase("time")){
				LocalDateTime now = LocalDateTime.now();
				return getCurrentDate.format(getCurrentHour.format(now));
			}else
				throw new Exception("Please pass either date or time");
	}
	
	//based on first forecast date and if is not matching with the currentdate then return false 
	public Boolean getWeatherForecastDate() throws Exception{
	
			if(getCurrentDateorTime("date").equalsIgnoreCase(driver.findElement(By.
					xpath("//*[@id='root']/div/div[1]/div[1]/span[1]/span[2]")).getText())){
				return true;
			}
			
			return false;	
	}
	
	
	//This function will check if all the 5 days weather details are returned
	public void getAllWeatherDaysCount() throws Exception{
		List<WebElement> values = driver.findElements(By.xpath("//*[@id='root']/div/div/div[1]/span[1]/span[2]"));
		if(values.size() == 5){
			System.out.println("All 5 days are returned");
		}else
			throw new Exception("Not all the 5 days are fetched");
	}

	
	public void clickOnFirstDate() {
		driver.findElement(By.xpath("//span[@data-test='day-1']")).click();
		int visibityOfFirstRecord = driver.findElements(By.xpath(
				"//div[@class='summary']//span[@data-test='date-1']/../../..//div[@class='details'][contains(@style,'max-height: 2000px')]")
				).size();
		Assert.assertEquals(visibityOfFirstRecord, 1);
		
	}
	
	public void checkForThreeHours()
	{
		List<WebElement> timeForTheSelectedDate = driver.findElements(By.xpath(
				"//div[@class='summary']//span[@data-test='date-1']/../../..//div[@class='details'][contains(@style,'max-height: 2000px')]//child::span[starts-with(@data-test,'hour-1')]"));
	
		List<Integer> allTimesFromUI = new ArrayList<>();
		List<Integer> expectedTimes = new ArrayList<>();

		for (WebElement w1 : timeForTheSelectedDate) {
			System.out.println(w1.getText());
			allTimesFromUI.add(Integer.parseInt(w1.getText()) / 100);
		}
	
		int firstItem = allTimesFromUI.get(0);
		expectedTimes.add(0, firstItem);
		for (int i = 0; i < timeForTheSelectedDate.size() - 1; i++) {

			expectedTimes.add(i + 1, expectedTimes.get(i) + 3);

		}
		
		Assert.assertEquals(true, (allTimesFromUI.equals(expectedTimes)));
	}
	
	public void waituntillElementVisible(String xpathvalue){
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathvalue)));
	}
	
	public void waituntillElementPresent(String xpathvalue){
		waitTime.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathvalue)));
	}


	
	
	

}
