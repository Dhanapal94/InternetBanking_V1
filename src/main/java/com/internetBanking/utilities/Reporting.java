package com.internetBanking.utilities;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.internetBankings.testData.Base;

public class Reporting  extends Base implements ITestListener {
		

		public void onTestStart(ITestResult result) {
			// TODO Auto-generated method stub

		}

		public void onTestSuccess(ITestResult result) {
			test=extent.createTest(result.getName());
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName(), ExtentColor.GREEN));
			
		}

		public void onTestFailure(ITestResult result) {
			String tcname = result.getName();
			try {
				String screenPath = getScreenshot(tcname);
				test=extent.createTest(tcname);
				test.log(Status.FAIL, MarkupHelper.createLabel(result.getName(), ExtentColor.RED));
				test.log(Status.FAIL, "Test case Failed is-" +result.getThrowable());
				test.addScreenCaptureFromPath(screenPath);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void onTestSkipped(ITestResult result) {
			test=extent.createTest(result.getName());
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName(), ExtentColor.ORANGE));
		}

		public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
			// TODO Auto-generated method stub

		}

		public void onTestFailedWithTimeout(ITestResult result) {
			// TODO Auto-generated method stub

		}

		public void onStart(ITestContext context) {
			// TODO Auto-generated method stub

		}

		public void onFinish(ITestContext context) {
			// TODO Auto-generated method stub

		}



}
