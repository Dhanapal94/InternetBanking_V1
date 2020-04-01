package com.internetBanking.testcases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.internetBanking.utilities.XLUtils;

public class RightExcelTest {
	
	String excelPath = System.getProperty("user.dir")+"\\src\\main\\java\\com\\internetBankings\\testData\\Testdata.xlsx";
	@Test
	public void writeExcel() throws IOException
	{
		XLUtils.setCellData(excelPath, "Sheet1", 1, 2, "Pass");
		System.out.println("Success");
	}
	

	

}
