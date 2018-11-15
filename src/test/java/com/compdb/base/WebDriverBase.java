package com.compdb.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.compdb.utils.ConfigUtil;
import com.compdb.utils.LoggerUtil;

public class WebDriverBase {
	private static WebDriver driver = null;
	public static WebDriverWait wait = null;
	public Logger s_logs = LoggerUtil.logger();

	public void loadWebDriver(String browser) {
		switch (browser.toUpperCase()) {
		case "FIREFOX":
			driver = new FirefoxDriver();
			s_logs.log(Level.INFO, "Launched Firefox browser");
			break;

		case "CHROME":
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--test-type");
			options.addArguments("--disable-notifications");
			System.setProperty("webdriver.chrome.driver",
					ConfigUtil.getRootDir() + "/src/test/resources/driver/chromedriver.exe");
			driver = new ChromeDriver(options);
			s_logs.log(Level.INFO, "Launched Chrome browser");
			break;

		case "INTERNET EXPLORER":
			System.setProperty("webdriver.ie.driver", ConfigUtil.getRootDir()
					+ "/src/test/resources/driver/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			s_logs.log(Level.INFO, "Launched Internet explorer");
			break;
		}
		driver.manage().deleteAllCookies();
		s_logs.log(Level.INFO, "Deleted all browser cookies.");
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 30);
	}

	public void cleanUP() {
		driver.close();
		s_logs.log(Level.INFO, "closed web browser");
	}

	public WebDriver getWebDriver() {
		if (driver == null) {
			loadWebDriver(ConfigUtil.getProperty("browser"));
		}
		return driver;
	}

	public void setWebDriver(WebDriver newDriver) {
		driver = newDriver;
		wait = new WebDriverWait(driver, 30);
	}
}
