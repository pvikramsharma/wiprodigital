Feature: Home page weather forecast summary

@positive
  Scenario: Open the weather home page should show default weather details of Glasgow
    	Given the chrome browser is opened
    	When user navigates to HomePage "http://localhost:3000/"
    	Then user should be displayed with five day forecast for the "Glasgow" city
    	And user verify if the date starting order is correct


@positive
  Scenario Outline: when the user is the home page and try different cities in the search box
    	Given user is on the home page
   		When user enters <city_name> on the home page
    	Then user should be displayed with five day forecast for the <city_name> city


    Examples: 
      | city_name |
      | Edinburgh |
      | Glasgow  |

 @negative
  Scenario Outline: when the user is the home page, try differnt alphanumeric and invalid city in text box
    	Given user is on the home page
   		When user enters <city_name> on the home page
   		Then user should be displayed with <error_message>

    Examples: 
      | city_name | error_message             |
      | "234324"  | Unable to fetch the value |
      | sasdad    | Unable to fetch the value |
 
 
 @positive
    Scenario: when the user is one the home, try different cities text box
    	Given user is on the home page
    	When Clicked on the first date displyed for Perth
    	Then user should be displayed with all details of the forecast for every 3 hourly forecast entry for the selected day



