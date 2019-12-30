package com.internetBanking.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	public WebDriver driver;

	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(name="uid")
	private WebElement usname;

	@FindBy(name="password")
	private WebElement password;

	@FindBy(xpath="//input[@name='btnLogin']")
	private WebElement loginButton;

	@FindBy(xpath="//div[@id='closeBtn']")
	private WebElement closeVideo;

	public void setUserName(String user)
	{
		usname.sendKeys(user);
	}

	public void setPassword(String pass)
	{
		password.sendKeys(pass);
	}

	public void loginClick()
	{
		loginButton.click();
	}
	public void closeVideoAd()
	{
		try {
			int size = driver.findElements(By.tagName("iframe")).size();
			System.out.println(size);
			WebElement frameclose = driver.findElement(By.xpath("//iframe[@id='flow_close_btn_iframe']"));
            driver.switchTo().frame(frameclose);
            System.out.println("successfully switched into frame");
			closeVideo.click();
			Thread.sleep(2000);
			 System.out.println("successfully switched close frame");
			driver.switchTo().defaultContent();
		}
		catch(Exception e)
		{
			System.out.println("No close video found"+e);
			driver.switchTo().defaultContent();
		}
	}

}
