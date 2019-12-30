package com.internetBanking.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EditCustomer {
	public WebDriver driver;

	public EditCustomer(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="//a[text()='Edit Customer']")
	private WebElement editCustomer;
	
	@FindBy(name="cusid")
	private WebElement customerID;
	
	@FindBy(name="AccSubmit")
	private WebElement customrtIDSubmit;
	
	public void editCustomerClick()
	{
		editCustomer.click();
	}
	
	public void setCustomerID(String val)
	{
		customerID.sendKeys(val);
	}
	
	public void customerIDSubmit()
	{
		customrtIDSubmit.click();
	}
	
	

}
