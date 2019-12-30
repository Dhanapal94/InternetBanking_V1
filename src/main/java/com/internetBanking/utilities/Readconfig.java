package com.internetBanking.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

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
	public String customerID()
	{
		String customerid = prop.getProperty("customerId");
		return customerid;
	}
	


}
