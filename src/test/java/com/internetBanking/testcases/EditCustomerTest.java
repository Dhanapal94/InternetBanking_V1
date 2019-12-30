package com.internetBanking.testcases;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.internetBanking.pages.EditCustomer;
import com.internetBanking.pages.LoginPage;
import com.internetBanking.pages.LogoutPage;
import com.internetBanking.pages.NewCustomer;
import com.internetBanking.utilities.Readconfig;
import com.internetBankings.testData.Base;

public class EditCustomerTest extends Base {
	WebDriver driver;
	LoginPage l;
	LogoutPage logout;
	NewCustomer cust;
	EditCustomer editcust;
	Readconfig con ;
	@BeforeClass
	public void setup() throws IOException
	{
		con =  new Readconfig();
		driver = setupDriver();
		driver.get(con.applicationURL());
	}
	@AfterTest
	public void closeBrowser()
	{
		closeDriver();
		driver=null;
	}
	@Test()
	public void editCustomerTest() throws Exception
	{
		l = new LoginPage(driver);
		logout = new LogoutPage(driver);
		cust = new NewCustomer(driver);
		editcust = new EditCustomer(driver);
		//String customerIDValue = cust.generatedCustomerId();
		String emailvalue = randomString()+"@gmail.com";
		
		//Login
		l.setUserName(con.usernameValue());
		l.setPassword(con.passwordValue());
		l.loginClick();
		
		//Edit Existing customer
		editcust.editCustomerClick();
		editcust.setCustomerID(con.customerID());
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
		cust.setEmail(emailvalue);
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


}
