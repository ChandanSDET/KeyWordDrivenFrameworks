package com.citrix.lbcs.utils;

import java.io.File;
import java.util.Properties;
import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


public class TestBase {

	public  WebDriver driver;
	public  ExtentReports extent= ExtentManager.getInstance();
	public  ExtentTest extentReport=null;
	public  WebDriverWait wait;
	public String data;
	public String expectedValue;
	public String object;
	public Properties objectRepository;
	public ResourceBundle configurationBundle;
	
	public void initailizeBrowser(String browser){

		if(browser.equalsIgnoreCase("firefox")){
			driver = new FirefoxDriver();

		}
		else if(browser.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+File.separator+"src"+
					File.separator+"test"+File.separator+"resources"+
					File.separator+"drivers"+File.separator+"chromedriver.exe");
			driver = new ChromeDriver();

		}
		driver.manage().window().maximize();
		//driver.manage().window().setSize(new Dimension(1366, 2800));
		wait = new WebDriverWait(driver, 20);
		
	}

}
