package com.internetBanking.testcases;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.internetBanking.pages.EditCustomer;
import com.internetBanking.pages.LoginPage;
import com.internetBanking.pages.LogoutPage;
import com.internetBanking.pages.NewCustomer;
import com.internetBanking.utilities.Readconfig;
import com.internetBankings.testData.*;

public class NewEditCustomer extends Base{
	WebDriver driver;
	LoginPage l;
	LogoutPage logout;
	NewCustomer cust;
	Readconfig con ;
	EditCustomer editcust;
	@BeforeTest
	public void setup() throws IOException
	{
		con =  new Readconfig();
		driver = setupDriver();
		driver.get(con.applicationURL());
	}
	@Test()
	public void combineNewEditTest() throws Exception
	{
		l = new LoginPage(driver);
		logout = new LogoutPage(driver);
		cust = new NewCustomer(driver);
		editcust = new EditCustomer(driver);
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
	
	//Edit customer
	String customerIDValue = cust.generatedCustomerId();
	System.out.println("Customer Id value generated is :"+customerIDValue);
	String emailvalue1 = randomString()+"@gmail.com";
	editcust.editCustomerClick();
	editcust.setCustomerID(customerIDValue);
	editcust.customerIDSubmit();
	cust.clearAddress();
	cust.setAddress("mylapore east coastal road");
	cust.clearCity();
	cust.setCity("Chennai");
	cust.clearState();
	cust.setState("Tamilnadu");
	cust.clearPIN();
	cust.setPIN("641049");
	cust.clearMobileNumber();
	cust.setMobileNumber("9655993280");
	cust.clearEmail();
	cust.setEmail(emailvalue1);
	cust.submitButtonClick();
	boolean alertcheck = alertPresent();

	//Verification-Result
	if(alertcheck)
	{
		log.error("Unexpected alert encountered - Customer details are not updated succcessfully-Failed ==>"+driver.switchTo().alert().getText());
		driver.switchTo().alert().accept();
		Assert.assertTrue(false,"Customer details are not updated succcessfully-Failed");
	}
	
	boolean updateMsg = cust.checkCustomerRegisteredSuccessfully("Customer details updated Successfully!!!");
	if(updateMsg)
	{
		Assert.assertTrue(true);
		log.info("Customer Details updated Successfully");
	}

	else
	{
		log.error("Page is not responding after submit click ");
		Assert.assertTrue(false);
	}
	logout.logoutClick();
	driver.switchTo().alert().accept();
}
	@AfterTest
	public void tearDown() throws IOException
	{
		driver.close();
		driver=null;
	}
}
