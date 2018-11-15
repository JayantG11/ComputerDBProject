package com.compdb.pages;

import org.openqa.selenium.By;

public class AddDetailsPage extends PageBase{
	By computerName = By.xpath("//input[@id='name']");
	By introducedDate = By.xpath("//input[@id='introduced']");
	By discontinuedDate = By.xpath("//input[@id='discontinued']");
	By createNewComputerButton = By.xpath("//input[@class='btn primary'][@type='submit']");
	By deleteComputerButton = By.xpath("//input[@class='btn danger'][@type='submit']");
	By editWindowTitle = By.xpath("//section[@id='main']/h1");

	public boolean validateComputerName() {
		clear(computerName);
		click(createNewComputerButton);
		return isDisplayed(createNewComputerButton);
	}
	
	public boolean validateInsuredDate() {
		enter(computerName,"test");
		enter(introducedDate, "2018-10-0111");
		click(createNewComputerButton);
		return isDisplayed(createNewComputerButton);
	}

	public boolean validateDiscontinuedDate() {
		enter(introducedDate, "2018-10-01");
		enter(discontinuedDate, "2018-11-0111");
		click(createNewComputerButton);
		return isDisplayed(createNewComputerButton);
	}
	
	public String getComputerName() {
		return getAttribute(computerName, "value");
	}
	
	public String getIntroducedDate() {
		return getAttribute(introducedDate, "value");
	}

	public String getDiscontinuedDate() {
		return getAttribute(discontinuedDate, "value");		
	}
	
	public void clickDeleteComputer() {
		click(deleteComputerButton);
	}
}
