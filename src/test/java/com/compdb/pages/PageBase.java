package com.compdb.pages;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.compdb.base.WebDriverBase;
import com.compdb.utils.WebUIUtil;

public abstract class PageBase extends WebDriverBase {

	public void waitTillElementVisible(By locator, int timeout) {
		s_logs.log(Level.INFO, "Waiting for locator " + locator);
		WebDriverWait waitLong = new WebDriverWait(getWebDriver(), timeout);
		waitLong.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitUntilElementInvisible(By locator, int timeout) {
		try {
			s_logs.log(Level.INFO, "Waiting for element " + locator + " to disappear");
			WebDriverWait waitLong = new WebDriverWait(getWebDriver(), timeout);
			waitLong.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			s_logs.log(Level.INFO, "Wait condition satisfied for time :" + timeout + " for locator :" + locator);
		} catch (NoSuchElementException e) {
			s_logs.log(Level.ERROR, "Element not visible");
		}
	}

	public void sleep(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, t);
		}
	}

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

	public String getText(By locator) {
		String text = "";
		try {
			text = findElement(locator).getText().trim();
			s_logs.log(Level.INFO, "Text for locator: " + locator + " : " + text);
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Unable to get Text", t);
			String screenShotName = "UnableToGetText" + System.currentTimeMillis();
			WebUIUtil.captureScreenShot(screenShotName);
			Assert.fail(
					"Unable to get Text from locator: " + locator + " , kindly refer screenshot : " + screenShotName);
		}
		return text;
	}

	public List<String> getTexts(By locator) {
		List<String> texts = new ArrayList<String>();
		List<WebElement> webElement = new ArrayList<WebElement>();
		try {
			webElement = findElements(locator);
			for (WebElement we : webElement) {
				texts.add(we.getText());
			}
			s_logs.log(Level.INFO, "Texts for locator: " + locator + " : " + texts);
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Unable to get Texts", t);
			String screenShotName = "UnableToGetTexts" + System.currentTimeMillis();
			WebUIUtil.captureScreenShot(screenShotName);
			Assert.fail(
					"Unable to get Texts from locator: " + locator + " , kindly refer screenshot : " + screenShotName);
		}
		return texts;
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

	public boolean isSelected(By locator) {
		boolean isSelected = false;
		try {
			isSelected = findElement(locator).isSelected();
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Element not found", t);
		}
		if (isSelected)
			s_logs.log(Level.INFO, locator + " is selected");
		else
			s_logs.log(Level.INFO, locator + " is not selected");
		return isSelected;
	}

	public boolean isEnabled(By locator) {
		boolean isEnabled = false;
		try {
			isEnabled = findElement(locator).isEnabled();
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Element not found", t);
		}
		if (isEnabled)
			s_logs.log(Level.INFO, locator + " is enabled");
		else
			s_logs.log(Level.INFO, locator + " is not enabled");
		return isEnabled;
	}

	public boolean isDisplayed(By locator, boolean avoidVisibilityCheck) {
		boolean isDisplayed = false;
		try {
			WebElement element = findElement(locator, avoidVisibilityCheck);
			if (element != null) {
				isDisplayed = element.isDisplayed();
			} else
				return false;
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Element not found", t);
		}
		if (isDisplayed)
			s_logs.log(Level.INFO, locator + " is displayed");
		else
			s_logs.log(Level.INFO, locator + " is not displayed");
		return isDisplayed;
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

	public void select(By locator, boolean selected) {
		try {
			boolean status = isSelected(locator);
			if (status != selected) {
				findElement(locator).click();
			}
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Unable to Select/Deselect checkbox", t);
			String screenShotName = "UnableToSelect" + System.currentTimeMillis();
			WebUIUtil.captureScreenShot(screenShotName);
			Assert.fail(
					"Unable to Select/Deselect checkbox " + locator + " , kindly refer screenshot : " + screenShotName);
		}
		if (selected)
			s_logs.log(Level.INFO, "Selected checkbox for " + locator);
		else {
			s_logs.log(Level.INFO, "De-selected checkbox for " + locator);
		}
	}

	public void contextClick(By locator) {
		WebElement subTab;
		try {
			subTab = findElement(locator);
			Actions builder = new Actions(getWebDriver()).contextClick(subTab);
			builder.build().perform();
			s_logs.log(Level.INFO, "Right Clicked on locator : " + locator);
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Unable to Right Click", t);
			String screenShotName = "UnableToRightClick" + System.currentTimeMillis();
			WebUIUtil.captureScreenShot(screenShotName);
			Assert.fail(
					"Unable to Right Click on locator " + locator + " , kindly refer screenshot : " + screenShotName);
		}
	}

	public void closeTab(By locator) {
		WebElement subTab;
		try {
			subTab = findElement(locator);
			Actions builder = new Actions(getWebDriver());
			builder.contextClick(subTab).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
			s_logs.log(Level.INFO, "Closed the Tab :" + locator);
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Element not found", t);
		}
	}

	public void waitTillElementVisible(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			s_logs.log(Level.INFO, "Wait condition satisfied for locator :" + locator);
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Element not visible");
		}
	}

	public boolean hasElement(By locator) {
		boolean flag = false;
		try {
			if (getWebDriver().findElements(locator).size() != 0)
				flag = true;
		} catch (Throwable e) {
			s_logs.log(Level.ERROR, "Element not found", e);
		}
		return flag;
	}

	public void waitUntilElementIsClickable(By locator) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Element not found", t);
		}
	}

	public void waitUntilElementInvisible(By locator) {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Element not found", t);
		}
	}

	public void waitTillListOfElementVisible(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		} catch (NoSuchElementException e) {
			s_logs.log(Level.ERROR, "Element not visible", e);
		}
	}

	public void mouseHover(By hoverLocator, By clickLocator) {
		try {
			Actions action = new Actions(getWebDriver());
			action.moveToElement(getWebDriver().findElement(hoverLocator)).perform();
			action.moveToElement(getWebDriver().findElement(clickLocator)).click().perform();
		} catch (Throwable t) {
			s_logs.log(Level.ERROR, "Unable to do mouse hover", t);
		}
	}
	
}
