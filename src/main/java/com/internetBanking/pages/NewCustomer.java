package com.internetBanking.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class NewCustomer {
	public WebDriver driver;

	public NewCustomer(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="//a[text()='New Customer']")
	private WebElement newCustomer;
	
	@FindBy(name="name")
	private WebElement customerName;
	
	@FindBys(@FindBy(xpath="//td[text()='Gender']//following::input[@type='radio']"))
	private List<WebElement> gender;
	
	@FindBy(name="dob")
	private WebElement DOB;
	
	@FindBy(xpath="//td[text()='Address']//following::textarea[1]")
	private WebElement address;
	
	@FindBy(name="city")
	private WebElement city;
	
	@FindBy(name="state")
	private WebElement state;
	
	@FindBy(name="pinno")
	private WebElement PIN;
	
	@FindBy(name="telephoneno")
	private WebElement mobileNumber;
	
	@FindBy(name="emailid")
	private WebElement emailID;
	
	@FindBy(name="password")
	private WebElement password;
	
	@FindBy(name="sub")
	private WebElement submitButton;
	
	@FindBy(xpath="//td[text()='Customer ID']//following::td[1]")
	private WebElement customerId;
	
	
	public void newCustomerClick()
	{
		newCustomer.click();
	}
	
	public void setCustomerName(String value)
	{
		customerName.sendKeys(value);
	}
	
	public void clickGender(String genderprefered)
	{
		int size = gender.size();
		for(int i=0;i<size;i++)
		{
			if(gender.get(i).getAttribute("value").equals(genderprefered))
			{
				gender.get(i).click();
				break;
			}
		}
	}
	
	public void setDOB(String value)
	{
		DOB.sendKeys(value);
	}
	public void setAddress(String value)
	{
		address.sendKeys(value);
	}
	public void setCity(String value)
	{
		city.sendKeys(value);
	}
	public void setState(String value)
	{
		state.sendKeys(value);
	}
	public void setPIN(String value)
	{
		PIN.sendKeys(value);
	}
	public void setMobileNumber(String value)
	{
		mobileNumber.sendKeys(value);
	}
	public void setEmail(String value)
	{
	   emailID.sendKeys(value);
	}
	public void setPassword(String value)
	{
		password.sendKeys(value);
	}	
	
	public void submitButtonClick()
	{
		submitButton.click();
	}
	
	public boolean checkCustomerRegisteredSuccessfully(String value)
	{
		if(driver.getPageSource().contains(value))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//Clear flow
	public void clearAddress()
	{
		address.clear();
	}
	public void clearCity()
	{
		city.clear();
	}
	public void clearState()
	{
		state.clear();
	}
	public void clearPIN()
	{
		PIN.clear();
	}
	public void clearMobileNumber()
	{
		mobileNumber.clear();
	}
	public void clearEmail()
	{
	   emailID.clear();
	}
	
	public String generatedCustomerId()
	{
		String cid = customerId.getText();
		return cid;
	}
}
