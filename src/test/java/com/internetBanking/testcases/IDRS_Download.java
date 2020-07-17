package com.internetBanking.testcases;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.internetBankings.testData.Base;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.internetBanking.utilities.Readconfig;
import com.internetBanking.utilities.XLUtils;

public class IDRS_Download {

	public static WebDriver driver ;
	WebDriverWait wait;
	public static int count = 1,rcount = 1,ecount = 1;
	File src, dest,datfile,srcDirectory,destDirectory;
	Robot rob;
	StringSelection selection;
	String excelPath,dateFile,destDirectoryLocation,fileLocation,destLocation;
	SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
	String mailFormattedDate = dateFormat1.format(new Date()).toString();
	Base b;
	Readconfig conf;

	@BeforeClass
	public void setUp() throws Exception {
		
		b=new Base();
		conf = new Readconfig();
        driver=b.setupDriver(); 
		String username=Base.userName();	
		String msid=Base.userMSID();
		destDirectoryLocation ="\\\\nasv0502\\automation\\Claims\\IDRS\\New\\EOB_attched\\"+username+"\\"+mailFormattedDate;
		excelPath = "C:\\Users\\"+msid+"\\Documents\\IDRS Documents\\IDRS_TestData.xlsx";
		System.out.println(excelPath);
		dateFile = "C:\\Users\\"+msid+"\\Documents\\IDRS Documents\\"+mailFormattedDate;

		datfile = new File(dateFile);
		boolean b = datfile.mkdirs();
		if (b) {
			System.out.println("Directory created successfully");
		}
		wait = new WebDriverWait(driver, 20);
		rob = new Robot();

		WebElement userID = driver.findElement(By.id("userid"));
		userID.sendKeys(conf.usernameValue());

		WebElement pwd = driver.findElement(By.id("password"));
		pwd.sendKeys(conf.passwordValue());

		WebElement submit = driver.findElement(By.id("submit"));
		submit.click();

		WebElement selectBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("webSelection")));
		Select drpClaim = new Select(selectBox);
		drpClaim.selectByVisibleText("Claims & Correspondence");
	}

	@Test(dataProvider = "LoginData")
	public void IDRSDownload(String rownum, String Flndcc) throws InterruptedException, AWTException {

		Base.setRowcount(rcount);
		rcount = rcount + 1;

		WebElement inputClaim = driver.findElement(By.id("id"));
		String FLNDCC = Flndcc;
		char ch[]=FLNDCC.toCharArray();
		
		if(FLNDCC.length()==15)
		{
			FLNDCC="";		
			for(int i=0;i<ch.length-2;i++)
			{
				FLNDCC=FLNDCC+ch[i];
			}
			
		}
		System.out.println(FLNDCC);
		inputClaim.sendKeys(FLNDCC);

		fileLocation = "C:\\Users\\da4\\Documents\\IDRS Documents\\" + FLNDCC + ".tif";
		destLocation = dateFile+"\\"+FLNDCC+".tif";
		selection = new StringSelection(fileLocation);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

		WebElement viewButton = driver.findElement(By.id("view_image"));
		viewButton.click();

		List<WebElement> errorMessageEle = driver
				.findElements(By.xpath("//div[@class='alert alert-danger alert-dismissible ng-scope']/div"));

		if (errorMessageEle.size() > 0) {
			String errorMessag = errorMessageEle.get(0).getText();
			try {
				XLUtils.setCellData(excelPath, "Sheet1", Base.getRowcount(), 2, "FAILED", 1);
				XLUtils.setCellData(excelPath, "Sheet1", Base.getRowcount(), 3, errorMessag, 2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputClaim.clear();
			Assert.assertTrue(false);
		} else {
			int temp = rcount;
			temp = temp - 1;
			System.out.println("No error element displayed for : " + temp);
		}

		if (count == 1 || ecount % 5 == 0) {
			Thread.sleep(25000);
			count++;
		}

		rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		rob.keyPress(KeyEvent.VK_F);
		rob.keyRelease(KeyEvent.VK_F);
		if (ecount == 1) {
			Thread.sleep(30000);
		} else {
			Thread.sleep(20000);
		}
		rob.keyPress(KeyEvent.VK_BACK_SPACE);
		rob.keyRelease(KeyEvent.VK_BACK_SPACE);
		rob.keyPress(KeyEvent.VK_CONTROL);
		rob.keyPress(KeyEvent.VK_V);
		rob.keyRelease(KeyEvent.VK_CONTROL);
		rob.keyRelease(KeyEvent.VK_V);
		rob.keyPress(KeyEvent.VK_TAB);
		rob.keyRelease(KeyEvent.VK_TAB);
		rob.keyPress(KeyEvent.VK_TAB);
		rob.keyRelease(KeyEvent.VK_TAB);
		rob.keyPress(KeyEvent.VK_ENTER);
		rob.keyRelease(KeyEvent.VK_ENTER);

		WebElement removeButton = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("(//a/i[@class='glyphicon glyphicon-remove'])[1]")));
		removeButton.click();

		inputClaim.clear();
		ecount = ecount + 1;

		src = new File(fileLocation);
		dest = new File(destLocation);
		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			try {
				XLUtils.setCellData(excelPath, "Sheet1", Base.getRowcount(), 2, "FAILED", 1);
				XLUtils.setCellData(excelPath, "Sheet1", Base.getRowcount(), 3, "File not saved-Retry again", 2);
				Assert.assertTrue(false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		src.delete();
		System.out.println("File is copied successfully");
		destLocation=destDirectoryLocation+"\\"+FLNDCC+".tif";

		try {
			XLUtils.setCellData(excelPath, "Sheet1", Base.getRowcount(), 2, "Completed", 1);
			XLUtils.setCellData(excelPath, "Sheet1", Base.getRowcount(), 3, destLocation, 2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@DataProvider(name = "LoginData")
	public Object[][] dataRepository() throws IOException

	{
		int rowcount = XLUtils.getRowCount(excelPath, "Sheet1");
		int colcount = XLUtils.getCellCount(excelPath, "Sheet1", 2);
		System.out.println("Column count" + colcount);
		System.out.println("row count" + rowcount);
		Object data[][] = new Object[rowcount][colcount];

		for (int i = 1; i <= rowcount; i++) {
			for (int j = 0; j <= colcount - 1; j++) {
				data[i - 1][j] = XLUtils.getCellData(excelPath, "Sheet1", i, j);
			}
		}
		return data;
	}

	@AfterClass()
	public void TearDown() {
		driver.close();
		File f = new File(destDirectoryLocation);
		boolean b = f.mkdirs();
		if (b) {
			System.out.println("Directory created successfully in nas location");
		}
		srcDirectory = new File(dateFile);
		destDirectory = new File(destDirectoryLocation);
		try {
			FileUtils.copyDirectory(srcDirectory, destDirectory);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		try {
			FileUtils.deleteDirectory(srcDirectory);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
