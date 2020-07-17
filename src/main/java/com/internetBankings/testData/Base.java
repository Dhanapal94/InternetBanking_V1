package com.internetBankings.testData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.internetBanking.utilities.Readconfig;

public class Base  {
	public static RemoteWebDriver reDriver;
	public static DesiredCapabilities dc;
	public static URL url;
	public static WebDriver driver;
	public static ExtentHtmlReporter htmlreporter;
	public static ExtentReports extent;
	public static ExtentTest parentTest;
	public static ExtentTest test;		
	public Logger log; 
	public static int rowCount;
	public static String destination ;
	public static File src ;
	public static File dest ;
	public static TakesScreenshot ts;
	String homeProject = System.getProperty("user.dir");
	public   WebDriver setupDriver() throws Exception
	{		

		Readconfig conf = new Readconfig();
		log = Logger.getLogger("IDRS Download");
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
				conf.saveIEPathLocation();
				System.setProperty("webdriver.ie.driver", conf.iePath());
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("ignoreZoomSetting", true);
				caps.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
				caps.setCapability("requireWindowFocus", true);
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				driver = new InternetExplorerDriver(caps);
				driver.manage().window().maximize();
				driver.get(conf.applicationURL());
				driver.manage().window().maximize();
			}		
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			return driver;
		}
	
	public  void closeDriver()
	{
		driver.close();
	}

	public String randomString()
	{
		String randomname = RandomStringUtils.randomAlphabetic(5);
		return randomname;
	}

	public static void setRowcount(int val) {
		rowCount = val;

	}

	public static int getRowcount() {
		return rowCount;
	}

	public void dockerSetup() throws IOException, InterruptedException
	{
		Runtime.getRuntime().exec("cmd /c start docker-up.bat");
		Thread.sleep(40000);
	}

	public void dockerTearDown() throws IOException, InterruptedException
	{
		Runtime.getRuntime().exec("cmd /c start docker-down.bat");
		Thread.sleep(30000);
		Runtime.getRuntime().exec("taskkill /f /im cmd.exe"); // closes  command prompts
	}

	public static String userName()
	{
		String msid = null;
		String userHome =System.getProperty("user.home");
		String separator = "\\";
		String[] str_arr=userHome.replaceAll(Pattern.quote(separator), "\\\\").split("\\\\");
		userHome=str_arr[2];
		System.out.println(userHome);
		
		if(userHome.equalsIgnoreCase("da4"))
		{
			msid="Dhanapal";
		}
		else if(userHome.equalsIgnoreCase("npatekar"))
		{
			msid="Nayna";
		}
		return msid;

	}
	

	public static String userMSID()
	{
		String userHome =System.getProperty("user.home");
		String separator = "\\";
		String[] str_arr=userHome.replaceAll(Pattern.quote(separator), "\\\\").split("\\\\");
		userHome=str_arr[2];
		return userHome;
		
	}

}
