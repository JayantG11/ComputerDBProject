package com.compdb.pages;

import org.openqa.selenium.By;

public class LoginPage extends PageBase {

	By Input_Email = By.xpath("//input[@name='em'][@placeholder='Email Address']");
	By Input_password = By.xpath("//input[@name='pw'][@placeholder='Password']");
	By Button_Login = By.xpath("//button[contains(@data-hm-tap,'doLogin')]");
	By Login_Tab = By.xpath("//nav[contains(@class,'loggedOut')]/a[@class='login']");
	
	private void enterUsername(String userName){
		enter(Input_Email, userName);
	}

	private void enterPassword(String password){
		enter(Input_password, password);
	}

	private void clickLoginButton(){
		click(Button_Login);
	}
	
	private void navigateToLoginPage(){
		click(Login_Tab);
	}
	
	///////////////////// Consolidate Methods/////////////////////
	public void launchApplication(String url) {
		getWebDriver().get(url);
	}
	
	public void navigateTo(String url) {
		getWebDriver().navigate().to(url);
	}

	public void login(String userName, String password) {
		navigateToLoginPage();
		enterUsername(userName);
		enterPassword(password);
		clickLoginButton();
	}
	
	public void cleanup() {
		getWebDriver().quit();
	}
}
