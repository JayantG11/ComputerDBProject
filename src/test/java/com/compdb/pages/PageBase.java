package com.compdb.pages;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.compdb.base.WebDriverBase;
import com.compdb.utils.WebUIUtil;

public abstract class PageBase extends WebDriverBase {

	public WebElement findElement(By locator, boolean avoidVisibilityCheck) {
		WebElement element = null;
		try {
			if (!avoidVisibilityCheck) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			}
			element = getWebDriver().findElement(locator);
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Element not found", t);
			WebUIUtil.captureScreenShot("ElementNotFound_" + System.currentTimeMillis());
		}
		return element;
	}

	public WebElement findElement(By locator) {
		return findElement(locator, false);
	}

	public List<WebElement> findElements(By locator) {
		List<WebElement> elements = new ArrayList<WebElement>();
		try {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
			elements = getWebDriver().findElements(locator);
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Element not found: " + locator, t);
		}
		return elements;
	}

	public void click(By locator) {
		try {
			findElement(locator).click();
			s_logs.log(Level.INFO, "Clicked on locator: " + locator);
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Unable to click" + locator, t);
			String screenShotName = "ElementNotClickable" + System.currentTimeMillis();
			WebUIUtil.captureScreenShot(screenShotName);
			Assert.fail("Unable to Click on locator : " + locator + " , kindly refer screenshot : " + screenShotName);
		}
	}

	public void enter(By locator, String value) {
		try {
			clear(locator);
			findElement(locator).sendKeys(value);
			s_logs.log(Level.INFO, "Entered " + value + " in locator: " + locator);
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Unable to Enter: " + value + " on locator: " + locator, t);
			String screenShotName = "UnableToEnterText" + System.currentTimeMillis();
			WebUIUtil.captureScreenShot(screenShotName);
			Assert.fail("Unable to Enter: " + value + " on locator : " + locator + " , kindly refer screenshot : "
					+ screenShotName);
		}
	}

	public void clear(By locator) {
		try {
			findElement(locator).clear();
			s_logs.log(Level.INFO, "Cleared value of locator: " + locator);
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Unable to Clear", t);
			String screenShotName = "UnableToClear" + System.currentTimeMillis();
			WebUIUtil.captureScreenShot(screenShotName);
			Assert.fail("Unable to Clear value from locator: " + locator + " , kindly refer screenshot : "
					+ screenShotName);
		}
	}

	public String getAttribute(By locator, String attribute) {
		String attributeValue = "No Attribute";
		try {
			attributeValue = findElement(locator).getAttribute(attribute);
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Element not found", t);
		}
		if (attributeValue == null) {
			Assert.fail(locator + " does not have an attribute: " + attribute);
		}
		s_logs.log(Level.INFO, "attribute value is " + attributeValue);
		return attributeValue;
	}

	public boolean isDisplayed(By locator) {
		boolean isDisplayed = false;
		try {
			isDisplayed = getWebDriver().findElement(locator).isDisplayed();
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Element not found");
		}
		if (isDisplayed)
			s_logs.log(Level.INFO, locator + " is displayed");
		else
			s_logs.log(Level.INFO, locator + " is not displayed");
		return isDisplayed;
	}
}
