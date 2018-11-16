package com.compdb.tests;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.compdb.pages.AddDetailsPage;
import com.compdb.pages.HomePage;
import com.compdb.pages.LoginPage;
import com.compdb.utils.ConfigUtil;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class CompDBTest {		
	LoginPage loginPage = new LoginPage();
	HomePage homePage = new HomePage();
	AddDetailsPage addDetailsPage = new AddDetailsPage();
	
	@BeforeClass
	public void beforeClass() {
		loginPage.launchApplication(ConfigUtil.getProperty("compdb.url"));
	}
	
	@BeforeMethod
	public void beforeTest() throws InterruptedException {
    	System.out.println("-------------------------------------");
    	System.out.println("---Regression Test Suite for Computer Database Application---");
    	System.out.println("-------------------------------------");
    	loginPage.navigateTo(ConfigUtil.getProperty("compdb.url"));
	}

    @Test(priority=1, description = "TC001: Create Computer Name Scenario : Field Validation Test")
	public void fieldCreateValidation() {
    	homePage.clickAddNewComputer();
		Assert.assertEquals(addDetailsPage.validateComputerName(), true);
		Assert.assertEquals(addDetailsPage.validateInsuredDate(), true);
		Assert.assertEquals(addDetailsPage.validateDiscontinuedDate(), true);
	}

    @Test(priority=2, description = "TC002: Create Computer Name Scenario : Happy WF Test with Valid Set of Data")
	public void CreateComputer() {
    	homePage.addNewComputer();
    	homePage.searchComputer(ConfigUtil.getProperty("compName"));
		List<WebElement> results = homePage.getComputerList();
		Assert.assertEquals(results.get(5).getText(), ConfigUtil.getProperty("compName"));
	}	

	@Test(priority=3, description = "TC003: Read Computer Details Scenario")
	public void readComputer() {
		homePage.searchAndClickComputer(ConfigUtil.getProperty("compName"));
		Assert.assertEquals(addDetailsPage.getComputerName(), ConfigUtil.getProperty("compName"));
		Assert.assertEquals(addDetailsPage.getIntroducedDate(), "2018-10-01");
		Assert.assertEquals(addDetailsPage.getDiscontinuedDate(), "2018-11-01");
	}

	@Test(priority=4, description = "TC004: Edit/Update Computer Name Scenario : Field Validation Test")
	public void fieldEditValidation() {
		homePage.searchAndClickComputer(ConfigUtil.getProperty("compName"));
		Assert.assertEquals(addDetailsPage.validateComputerName(), true);
		Assert.assertEquals(addDetailsPage.validateInsuredDate(), true);
		Assert.assertEquals(addDetailsPage.validateDiscontinuedDate(), true);
	}
    
    @Test(priority=5, description = "TC005: Edit/Update Computer Name Scenario : Happy WF Test with Valid Set of Data")
    public void editComputer() {
    	homePage.editComputer(ConfigUtil.getProperty("compName"));
    	homePage.searchComputer(ConfigUtil.getProperty("updatedCompName"));
		List<WebElement> results = homePage.getComputerList();
		Assert.assertEquals(results.get(5).getText(), ConfigUtil.getProperty("updatedCompName"));
    }

	@Test(priority=6, description = "TC006: Delete Computer Name Scenario")
	public void deleteComputer() {
    	homePage.searchComputer(ConfigUtil.getProperty("updatedCompName"));
		List<WebElement> oldResults = homePage.getComputerList();
		homePage.clickComputer(oldResults);
		addDetailsPage.clickDeleteComputer();

	   	homePage.searchComputer(ConfigUtil.getProperty("updatedCompName"));
		List<WebElement> newResults = homePage.getComputerList();

		Assert.assertEquals(oldResults.size() > newResults.size(), true);
	}	

	@AfterClass
	public void afterClass() {
		loginPage.cleanup();			
	}		
}	
