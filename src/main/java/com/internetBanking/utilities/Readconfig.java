package com.internetBanking.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.internetBankings.testData.Base;

public class Readconfig {
	
	public static Properties prop;
	
	public  Readconfig() throws IOException
	{
		prop = new Properties();
		String filePath ="./Configuration\\data.properties";
		FileInputStream fin = new FileInputStream(filePath);
		prop.load(fin);
	}
	public String browserName()
	{
		String browser = prop.getProperty("browser");
		return browser;
	}
	public String applicationURL()
	{
		String url = prop.getProperty("url");
		return url;
	}

	public String chromePath()
	{
		String chromePath = prop.getProperty("chromepath");
		return chromePath;
	}
	public String iePath()
	{
		String iePath = prop.getProperty("IEpath");
		return iePath;
	}
	public String usernameValue()
	{
		String uname = prop.getProperty("username");
		return uname;
	}
	public String passwordValue()
	{
		String pword = prop.getProperty("password");
		return pword;
	}

	public String driverValue()
	{
		String driverValue = prop.getProperty("driver");
		return driverValue;
	}
	
	public  void saveIEPathLocation() throws IOException
	{
		
		String msid=Base.userMSID();
		String location = "C:\\Users\\"+msid+"\\Documents\\IDRS Documents\\IEDriverServer.exe";
		prop.setProperty("IEpath", location);
		String filePath ="./Configuration\\data.properties";
		FileOutputStream fout = new FileOutputStream(filePath);
		prop.store(fout,"IEPath updated");	
	
	}



}
