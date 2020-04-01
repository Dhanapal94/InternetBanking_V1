package com.internetBanking.testcases;

import java.io.IOException;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.internetBanking.pages.LoginPage;
import com.internetBanking.pages.LogoutPage;
import com.internetBanking.utilities.Readconfig;
import com.internetBanking.utilities.Reporting;
import com.internetBanking.utilities.XLUtils;
import com.internetBankings.testData.Base;

public class LoginTestDataDrivenTest extends Base {
	WebDriver driver;
	LoginPage l;
	LogoutPage logout;
	Readconfig con ;
	public static int count =1;
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
	}
	@AfterClass
	public void closeBrowser()
	{
		closeDriver();
		driver=null;

	}
	@Test(dataProvider="LoginData")
	public void logindataproviderTest(String usname,String pass) throws Exception
	{
		l = new LoginPage(driver);
		logout = new LogoutPage(driver);
		l.setUserName(usname);
		log.info("successfully passed login name");
		l.setPassword(pass);
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
		setRowcount(count);
		count=count+1;
		Assert.assertEquals(driver.getTitle(), expectedTitle,"Title is mismatched");
		log.info("Login is valid- success");
		logout.logoutClick();
		driver.switchTo().alert().accept();
	}
	@DataProvider(name="LoginData")
	public Object[][] dataRepository() throws IOException
	//public void data() throws IOException
	{
		String excelPath = System.getProperty("user.dir")+"\\src\\main\\java\\com\\internetBankings\\testData\\Testdata.xlsx";
		int rowcount = XLUtils.getRowCount(excelPath, "Sheet1");
		int colcount = XLUtils.getCellCount(excelPath, "Sheet1", 2);
		System.out.println("Column count"+colcount);
		System.out.println("row count"+rowcount);
		if(colcount>2)
		{
			colcount=colcount-1;
		}
		Object data[][] = new Object[rowcount][colcount];
		for(int i=1;i<=rowcount;i++)
		{
			for(int j=0;j<=colcount-1;j++)
			{
				data[i-1][j]=XLUtils.getCellData(excelPath, "Sheet1", i, j);
			}
		}
		return data;
	}
	@AfterSuite
	public void flushingReport()
	{
		removeExtent();
	}

}
