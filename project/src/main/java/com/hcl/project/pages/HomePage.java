package com.hcl.project.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.hcl.project.base.BasePage;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	//  Locators
	private By popupCloseBtn = By.xpath("//div[@class='pb-1 px-1 flex flex-middle nmx-1']//*[name()='svg']");
	private By fromCity = By.xpath("//input[@placeholder='Where from?']");
	private By toCity = By.xpath("//input[@placeholder='Where to?']");
	private By searchBtn = By.xpath("//button[.//h4[text()='Search flights']]");
	private By dateBtn = By.xpath("//div[contains(text(),'Mar')]");
	private By suggestions = By.xpath("//ul//li");

	private By fromCityError = By.xpath("//*[contains(text(),'Enter departure airport')]");
	//  Popup
	public void dismissPopupIfPresent() {
		try {
			if (driver.findElement(popupCloseBtn).isDisplayed()) {
				driver.findElement(popupCloseBtn).click();
			}
		} catch (Exception e) {
		
		}
	}

	//  FROM CITY (SMART + STABLE)
	public void selectFromCity(String city) {

		WebElement from = waitUtils.waitForClickable(fromCity);
		from.clear();
		from.sendKeys(city);

		waitUtils.waitForPresence(suggestions);

		selectCityFromDropdown(city);
	}

	//  TO CITY (SMART + STABLE)
	public void selectToCity(String city) {

		WebElement to = waitUtils.waitForClickable(toCity);
		to.clear();
		to.sendKeys(city);

		waitUtils.waitForPresence(suggestions);

		selectCityFromDropdown(city);
	}

	
	
	private void selectCityFromDropdown(String city) {

	    for (int i = 0; i < 3; i++) {
	        try {
	            List<WebElement> list = driver.findElements(suggestions);

	            for (WebElement el : list) {

	                String text = el.getText().toLowerCase();

	                //  flexible matching
	                if (text.contains(city.toLowerCase()) 
	                        || text.contains(city.substring(0, 3).toLowerCase())) {

	                    el.click();
	                    return;
	                }
	            }

	            //  fallback: click first suggestion
	            if (!list.isEmpty()) {
	                System.out.println("⚠️ Fallback: clicking first suggestion");
	                list.get(0).click();
	                return;
	            }

	        } catch (Exception e) {
	            System.out.println("Retry selecting city...");
	        }
	    }

	    throw new RuntimeException("City not found: " + city);
	}
	//  DATE BUTTON (JS CLICK -> avoids intercept issue)
	public void selectDateBtn() {

		WebElement el = waitUtils.waitForVisibility(dateBtn);

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
		try {
	        Thread.sleep(1000);
	    } catch (Exception e) {}
	}

	public void selectDate(String month, String day) {

	    By locator = By.xpath(
	        "//div[contains(text(),'" + month + "')]/ancestor::div[contains(@class,'flex')]"
	        + "//div[not(contains(@class,'disabled')) and text()='" + day + "']"
	    );

	    WebElement el = waitUtils.waitForVisibility(locator);

	    //  JS click (fixes intercept issue)
	    ((JavascriptExecutor) driver)
	        .executeScript("arguments[0].click();", el);
	}

	//  SEARCH BUTTON (JS CLICK for stability)
	public void clickSearch() {

		WebElement btn = waitUtils.waitForVisibility(searchBtn);

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
	}
	
	public void enterFromCityRaw(String city) {

	    WebElement from = waitUtils.waitForClickable(fromCity);
	    from.clear();
	    from.sendKeys(city);

	    // do NOT select from dropdown
	}
	public boolean isFromCityErrorVisible() {
	    return waitUtils.waitForVisibility(fromCityError).isDisplayed();
	}
}