package com.internetBanking.testcases;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.internetBanking.pages.LoginPage;
import com.internetBanking.utilities.Readconfig;
import com.internetBankings.testData.Base;

public class LoginTest extends Base {

	WebDriver driver;
	LoginPage l;
	Readconfig con ;
	@BeforeSuite
	public void creatingReport()
	{
		setExtent();
	}
	@BeforeClass
	public void setup() throws IOException
	{
		con =  new Readconfig();
		driver = setupDriver();
		driver.get(con.applicationURL());
<<<<<<< HEAD
	}
	@Parameters({"username"})
	@Test(enabled=true)
	public void loginTest(String usname) throws InterruptedException
	{
		l = new LoginPage(driver);
		//l.closeVideoAd();
		l.setUserName(usname);
=======
		
	}
	//@Parameters({"username"})
	@Test(enabled=true)
	public void loginTest() throws InterruptedException
	{
		l = new LoginPage(driver);
		//l.closeVideoAd();
		l.setUserName(con.usernameValue());
>>>>>>> 62d1f656e8977f071172b532901f678195de67ec
		log.info("successfully passed login name");
		l.setPassword(con.passwordValue());
		log.debug("successfully passed password");
		l.loginClick();
		log.info("successfully clicked login button");
		String expectedTitle = "Guru99 Bank Manager HomePage";
		try
		{
			driver.switchTo().alert().accept();
		}
		catch(NoAlertPresentException e)
		{
			System.out.println("No alert found "+e);
			log.warn("no alert present");
		}
		Assert.assertEquals(driver.getTitle(), expectedTitle,"Title is mismatched");
		System.out.println("Test case for login is passed successfully");		
	}
	@AfterClass
	public void tearDown()
	{
		closeDriver();
<<<<<<< HEAD
=======
		driver=null;
>>>>>>> 62d1f656e8977f071172b532901f678195de67ec
	}
	@AfterSuite
	public void flushingReport()
	{
		removeExtent();
	}
}
