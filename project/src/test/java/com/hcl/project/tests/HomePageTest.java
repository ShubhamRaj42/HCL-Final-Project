package com.hcl.project.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.hcl.project.base.BaseTest;
import com.hcl.project.pages.FlightListingPage;
import com.hcl.project.pages.HomePage;
import com.hcl.project.utils.LogUtil;

@Listeners(com.hcl.project.utils.ExtentReportListener.class)
public class HomePageTest extends BaseTest {

    HomePage home;
    FlightListingPage flightPage;

    @BeforeMethod
    public void initPages() {
        home = new HomePage(driver);
        flightPage = new FlightListingPage(driver);
   
        
    }

    // TC_CLR_001
    @Test
    public void verifyHomePageLoads() {
    	LogUtil.info("Initialized HomePage & FlightListingPage");
       
        Assert.assertTrue(driver.getTitle().contains("Cleartrip"),
                "Home page not loaded");
        
        LogUtil.info("Verifying home page load");
    }

    // TC_CLR_002
    @Test
    public void verifyPopupDismiss() {
       
        LogUtil.info("Handling login popup");

        home.dismissPopupIfPresent();

        LogUtil.pass("Popup handled successfully");
        //Assert.assertTrue(true, "Popup handled");
    }

    // TC_CLR_003
    @Test
    public void verifyFlightTabDefault() {
    	   LogUtil.info("Verifying Flights tab default selection");

           Assert.assertTrue(driver.getPageSource().contains("Flights"),
                   "Flights tab not selected");

           LogUtil.pass("Flights tab is selected by default");
    }

    // TC_CLR_004
    @Test
    public void verifySourceCitySelection() {
    	 LogUtil.info("Selecting source city: Bengaluru");

        home.selectFromCity("Bengaluru");

        String value = driver.findElement(By.xpath("//input[@placeholder='Where from?']")).getAttribute("value");

        LogUtil.info("Selected From City: " + value);

        Assert.assertTrue(value.toLowerCase().contains("beng"),
                "Source city not selected");
        
        LogUtil.pass("Source city selected successfully");
    }

    // TC_CLR_005
    @Test
    public void verifyDestinationCitySelection() {
    	LogUtil.info("Selecting destination city: Mumbai");

        home.selectToCity("Mumbai");

        String value = driver.findElement(By.xpath("//input[@placeholder='Where to?']")).getAttribute("value");

        LogUtil.info("Selected To City: " + value);
        Assert.assertTrue(value.toLowerCase().contains("mum")
                || value.toLowerCase().contains("bom"),
                "Destination city not selected");
        LogUtil.pass("Destination city selected successfully");
    }

    // TC_CLR_006
    @Test
    public void verifyDateSelection() {
        LogUtil.info("Selecting future date");

        home.selectDateBtn();
        home.selectDate("April 2026", "23");

        Assert.assertTrue(driver.getPageSource().contains("23"),
                "Date not selected");

        LogUtil.pass("Date selected successfully");
    }

    // TC_CLR_007
    @Test
    public void verifyValidSearch() {
        LogUtil.info("Performing valid search");

        home.selectFromCity("Bengaluru");
        home.selectToCity("Mumbai");
        home.selectDateBtn();
        home.selectDate("March 2026", "23");
        home.clickSearch();

        home.waitUtils.waitForUrlContains("results");

        LogUtil.info("Navigated to results page");

        Assert.assertTrue(driver.getCurrentUrl().contains("results"),
                "Search failed");

        LogUtil.pass("Search executed successfully");
    }

  

    // TC_CLR_010
    @Test
    public void verifyNoSourceCityValidation() {
        LogUtil.info("Validating search without source city");

        home.selectToCity("Mumbai");
        home.selectDateBtn();
        home.selectDate("March 2026", "23");
        home.clickSearch();

        Assert.assertFalse(driver.getCurrentUrl().contains("results"));

        LogUtil.pass("Validation for missing source city works");
    }

    // TC_CLR_011
    @Test
    public void verifyNoDestinationCityValidation() {
        LogUtil.info("Validating search without destination city");

        home.selectFromCity("Bengaluru");
        home.selectDateBtn();
        home.selectDate("March 2026", "23");
        home.clickSearch();

        Assert.assertFalse(driver.getCurrentUrl().contains("results"));

        LogUtil.pass("Validation for missing destination city works");
    }

    // TC_CLR_012
    @Test
    public void verifySameCityValidation() {
        LogUtil.info("Validating same source & destination");

        home.selectFromCity("Bengaluru");
        home.selectToCity("Bengaluru");
        home.selectDateBtn();
        home.selectDate("March 2026", "23");
        home.clickSearch();

        Assert.assertFalse(driver.getCurrentUrl().contains("results"));

        LogUtil.pass("Same city validation works");
    }

    // TC_CLR_013
    @Test
    public void verifyNoDateValidation() {
        LogUtil.info("Validating search without date");

        home.selectFromCity("Bengaluru");
        home.selectToCity("Mumbai");
        home.clickSearch();

        Assert.assertFalse(driver.getCurrentUrl().contains("results"));

        LogUtil.pass("Date validation works");
    }

    // TC_CLR_014
    @Test
    public void verifyPastDateNotAllowed() {
        LogUtil.info("Validating past date restriction");

        home.selectDateBtn();

        boolean disabled = driver.getPageSource().contains("disabled");

        Assert.assertTrue(disabled);

        LogUtil.pass("Past date restriction validated");
    }


    @Test
    public void verifyInvalidCity() {

        LogUtil.info("Validating invalid city input");

        home.enterFromCityRaw("XYZ123");
        home.selectToCity("Mumbai");
        home.selectDateBtn();
        home.selectDate("March 2026", "23");
        home.clickSearch();

        boolean error = home.isFromCityErrorVisible();

        LogUtil.info("Error displayed: " + error);

        Assert.assertTrue(error);

        LogUtil.pass("Invalid city validation works");
    }
    // TC_CLR_016
    @Test
    public void verifyContinueAfterPopup() {
        LogUtil.info("Handling popup and continuing");

        home.dismissPopupIfPresent();

        home.selectFromCity("Bengaluru");
        home.selectToCity("Mumbai");

        LogUtil.pass("User can continue after popup");
    }

    // TC_CLR_017
    @Test
    public void verifyAutoSuggestion() {
        LogUtil.info("Validating auto suggestion");

        home.selectFromCity("Ben");

        boolean suggestionVisible = driver.getPageSource().toLowerCase().contains("bengaluru");

        Assert.assertTrue(suggestionVisible);

        LogUtil.pass("Auto suggestion works correctly");
    }
}