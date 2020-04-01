package com.internetBankings.testData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.internetBanking.utilities.Readconfig;

public class Base  {
	public static WebDriver driver;
	public static ExtentHtmlReporter htmlreporter;
	public static ExtentReports extent;
	public static ExtentTest test;		
	public Logger log; 
	public static int rowCount;
	String homeProject = System.getProperty("user.dir");
	public  WebDriver setupDriver() throws IOException
	{		
	
		Readconfig conf = new Readconfig();
		log = Logger.getLogger("InternetBanking");
		PropertyConfigurator.configure("log4j.properties");
		if(conf.browserName().equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver",conf.chromePath());
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
			options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
		}
		else if(conf.browserName().equalsIgnoreCase("internet explorer"))
		{
			System.setProperty("webdriver.ie.driver", conf.iePath());
			//Initialize InternetExplorerDriver Instance.
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
		}		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		return driver;
	}
	public  void closeDriver()
	{
		driver.close();
	}
	public String getScreenshot(String tname) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		Date d = new Date();
		String datename = d.toString().replaceAll(":", "-");
		String destination = System.getProperty("user.dir")+"\\Screenshots\\"+tname+" " +datename+"-screenshot.png";
		File dest = new File(destination);
		FileUtils.copyFile(src, dest);		
		return destination;
	}
	public void setExtent()
	{
		String timeStamp = new  SimpleDateFormat("M/d/yyyy hh:mm:ss").format(new Date());
		timeStamp= timeStamp.replaceAll(":", "-");
		String reportname = "Test-Report "+timeStamp.replaceAll("/", "-")+".html";
		System.out.println(reportname);
		htmlreporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\test-output\\"+reportname);
		htmlreporter.config().setDocumentTitle("Automation Report");//Title of the report
		htmlreporter.config().setReportName("Functional Internet Banking Report");//Name of the report
		htmlreporter.config().setTheme(Theme.DARK);
	

		extent = new ExtentReports();
		extent.attachReporter(htmlreporter);
		extent.setSystemInfo("OS", "WINDOWS10");
		extent.setSystemInfo("Tester Name", "Dhanapal");
		extent.setSystemInfo("Browser", "Chrome");	
	}	
	public void removeExtent()
	{
		extent.flush();
	}
	
	public boolean alertPresent()
	{
		try
		{
			driver.switchTo().alert();
			return true;
		
		}
		catch(NoAlertPresentException e)
		{
			return false;	
		}
	}
	
	public String randomString()
	{
		String randomname = RandomStringUtils.randomAlphabetic(5);
		return randomname;
	}
	
	public static void setRowcount(int val) {
		rowCount = val;
		
	}
	
	public int getRowcount() {
		return rowCount;
	}


}
