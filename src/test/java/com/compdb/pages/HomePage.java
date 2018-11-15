package com.compdb.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.compdb.utils.ConfigUtil;

public class HomePage extends PageBase{

	By addNewComputer = By.xpath("//a[@id='add']");
	By computerName = By.xpath("//input[@id='name']");
	By introducedDate = By.xpath("//input[@id='introduced']");
	By discontinuedDate = By.xpath("//input[@id='discontinued']");
	By company = By.xpath("//select[@id='company']");
	By createNewComputerButton = By.xpath("//input[@class='btn primary'][@type='submit']");
	By searchBox = By.xpath("//input[@id='searchbox']");
	By submitSearch = By.xpath("//input[@id='searchsubmit']");
	By computerNameResult = By.xpath("//a[contains(@href,'computers')]");

	public void addNewComputer() {
		clickAddNewComputer();
		enter(computerName, ConfigUtil.getProperty("compName"));
		enter(introducedDate, "2018-10-01");
		enter(discontinuedDate, "2018-11-01");		
		Select oSelect = new Select(findElement(company));
		oSelect.selectByVisibleText("Apple Inc.");
		click(createNewComputerButton);
	}

	public void editComputer(String name) {
		searchAndClickComputer(name);
		clear(computerName);
		enter(computerName, ConfigUtil.getProperty("updatedCompName"));
		click(createNewComputerButton);
	}
	
	public void searchComputer(String name) {
		enter(searchBox, name);
		click(submitSearch);
	}
	
	public List<WebElement> getComputerList() {
		return findElements(computerNameResult);
	}
	
	public void clickAddNewComputer() {
		click(addNewComputer);
	}
	
	public void searchAndClickComputer(String name) {
		searchComputer(name);
		List<WebElement> computerList = getComputerList();
		computerList.get(5).click();
	}
	
	public void clickComputer(List<WebElement> result) {
		result.get(5).click();
	}	
}