package com.internetBanking.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.internetBanking.pages.LoginPage;
import com.internetBanking.pages.LogoutPage;
import com.internetBanking.pages.NewCustomer;
import com.internetBanking.utilities.Readconfig;
import com.internetBankings.testData.Base;

public class NewCustomerTest  extends Base{
	WebDriver driver;
	LoginPage l;
	LogoutPage logout;
	NewCustomer cust;
	Readconfig con ;
	@BeforeClass
	public void setup() throws IOException
	{
		con =  new Readconfig();
		driver = setupDriver();
		driver.get(con.applicationURL());
	}
	@AfterClass
	public void closeBrowser()
	{
		closeDriver();
<<<<<<< HEAD
=======
		driver=null;
>>>>>>> 62d1f656e8977f071172b532901f678195de67ec
	}
	@Test()
	public void newCustomerTest() throws Exception
	{
		l = new LoginPage(driver);
		logout = new LogoutPage(driver);
		cust = new NewCustomer(driver);
		String emailvalue = randomString()+"@gmail.com";
		l.setUserName(con.usernameValue());
		log.info("successfully passed login name");
		l.setPassword(con.passwordValue());
		log.info("successfully passed password");
		l.loginClick();
		log.info("successfully clicked login button");
		String expectedTitle = "Guru99 Bank Manager HomePage";
		boolean alertErrorFound = alertPresent();
		//Failure Login
		if(alertErrorFound)
		{
			driver.switchTo().alert().accept();
			log.error("Username or password is invalid - Alert found");
		}
		//Succcess Login
		Assert.assertEquals(driver.getTitle(), expectedTitle,"Title is mismatched");
		log.info("Login is valid- success");
		
		//New customer flow
		cust.newCustomerClick();
		cust.setCustomerName("Dhanapal");
		cust.clickGender("m");
		cust.setDOB("12012020");
		cust.setAddress("1 Vasantham Nagar chinnavedampatti");
		cust.setCity("Coimbatore");
		cust.setState("Tamilnadu");
		cust.setPIN("641049");
		cust.setMobileNumber("9655993280");
		cust.setEmail(emailvalue);
		cust.setPassword("Dhana-07");
		cust.submitButtonClick();
		
		boolean customerCreatedMessage = cust.checkCustomerRegisteredSuccessfully("Customer Registered Successfully!!!");
		
		if(customerCreatedMessage)
		{
			Assert.assertTrue(true);
			log.info("successfully registered new customer");
		}
		else
		{
			log.error("Not successfully registered new customer");
			Assert.assertTrue(false);
		}
				
		//closepopup Window	
	    l.closeVideoAd();
	    
	    //Logout flow
	    
	    log.info("came into logout flow");
	    logout.logoutClick();	   
	    log.info("successfully clicked on logout");
	    driver.switchTo().alert().accept();	
	   
	    String logoutExpected= "Guru99 Bank Home Page";
	    Assert.assertEquals(driver.getTitle(), logoutExpected,"Failed to Logout after added a new customer");
	    
	}

}
