package com.citrix.lbcs.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SeleniumAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(SeleniumAction.class);

	WebDriver driver;
	ExtentTest extentReport;
	WebDriverWait wait;
	ResourceBundle configurationBundle;
	String uIElementXpath;
	String uIElementName;

	public SeleniumAction(String uIElementName,String uIElementXpath,ExtentTest extentReport, WebDriver driver, 
			ResourceBundle configurationBundle, WebDriverWait wait) {
		this.uIElementName = uIElementName;
		this.uIElementXpath = uIElementXpath;
		this.extentReport = extentReport;
		this.driver = driver;
		this.configurationBundle = configurationBundle;
		this.wait = wait;
	}

	static String filePath = System.getProperty("user.dir") + File.separator + "report" + File.separator
			+ "failedScreens" + File.separator;
	static String testConfigFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "resources" + File.separator + "testDataForTestConfiguration" + File.separator;

	public void clickLink() throws Exception {
		try {
			extentReport.log(LogStatus.INFO, "Clicking on <b>" + uIElementName + "</b> Link.");
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(uIElementXpath)));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.xpath(uIElementXpath)).click();
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			//Thread.sleep(300);
		} catch (Exception e) {
			extentReport.log(LogStatus.FAIL, "<b style=\"color:red\">Error while Clicking on " + uIElementName + " Link.</b>\n"
					+ "<pre>" + e.toString() + "</pre>");
			takeScreenShot(uIElementName);
			LOGGER.error("Unable to Click on " + uIElementName + " Link.", e);
			throw e;
		}
	}

	public void clickButton() throws Exception {
		try {
			extentReport.log(LogStatus.INFO, "Clicking on <b>" + uIElementName + "</b> Button.");
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(uIElementXpath)));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.xpath(uIElementXpath)).click();
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			//Thread.sleep(300);
		} catch (Exception e) {
			extentReport.log(LogStatus.FAIL, "<b style=\"color:red\">Error while Clicking on " + uIElementName + " button.</b>\n"
					+ "<pre>" + e.getMessage() + "</pre>");
			takeScreenShot(uIElementName);
			LOGGER.error("Unable to Click on " + uIElementName + " Button.", e);
			throw e;
		}
	}

	public void clickRadioButton() throws Exception {
		try {
			extentReport.log(LogStatus.INFO, "Clicking on <b>" + uIElementName + "</b> Button.");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(uIElementXpath)));
			driver.findElement(By.xpath(uIElementXpath)).click();
			//Thread.sleep(300);
		} catch (Exception e) {
			extentReport.log(LogStatus.FAIL, "<b style=\"color:red\">Error while Clicking on " + uIElementName
					+ " radion button.</b>\n" + "<pre>" + e.getMessage() + "</pre>");
			takeScreenShot(uIElementName);
			LOGGER.error("Unable to Click on " + uIElementName + " radion button.", e);
			throw e;
		}
	}

	public void setText(String data) throws Exception {
		try {
			extentReport.log(LogStatus.INFO, "Writing in <b>" + uIElementName + "<b>.");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(uIElementXpath)));
			driver.findElement(By.xpath(uIElementXpath)).clear();
			driver.findElement(By.xpath(uIElementXpath)).sendKeys(data);
			//Thread.sleep(300);
		} catch (Exception e) {
			extentReport.log(LogStatus.FAIL, "<pre>" + e.getMessage() + "</pre>");
			takeScreenShot(uIElementName);
			LOGGER.error("Unable to write in " + uIElementName + " field.", e);
			throw e;
		}
	}

	public void verifyText(String expectedValue) throws Exception {
		try {
			extentReport.log(LogStatus.INFO, "Verifying the presence of <b>" + uIElementName + "</b> text.");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(uIElementXpath)));
			Assert.assertEquals(driver.findElement(By.xpath(uIElementXpath)).getText(),
					expectedValue);
			extentReport.log(LogStatus.PASS, "<b style=\"color:green\">" + uIElementName + " text filed is present in UI.</b>");
			//Thread.sleep(300);
		} catch (Throwable e) {
			extentReport.log(LogStatus.FAIL, "<pre>" + e.getMessage() + "</pre>");
			takeScreenShot(uIElementName);
			LOGGER.error("Expected Value and Actual value are not matching", e);
			throw e;
		}
	}

	public void verifyPlaceHolderText(String expectedValue) throws Exception {
		try {
			extentReport.log(LogStatus.INFO, "Verifying the presence of Placeholder <b>" + uIElementName + "</b> text.");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(uIElementXpath)));
			Assert.assertEquals(driver.findElement(By.xpath(uIElementXpath)).getAttribute("placeholder"),
					expectedValue);
			extentReport.log(LogStatus.PASS, "<b style=\"color:green\">" + uIElementName + " Placeholder filed is present in UI.</b>");
			//Thread.sleep(300);
		} catch (Throwable e) {
			extentReport.log(LogStatus.FAIL, "<pre>" + e.getMessage() + "</pre>");
			takeScreenShot(uIElementName);
			LOGGER.error("Expected Value and Actual value are not matching", e);
			throw e;
		}
	}

	public void verifyPresence() throws Exception {
		try {
			extentReport.log(LogStatus.INFO, "Verifying the presence of <b>" + uIElementName + "</b>.");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(uIElementXpath)));
			Assert.assertEquals(driver.findElement(By.xpath(uIElementXpath)).isDisplayed(), true);
			extentReport.log(LogStatus.PASS, "<b style=\"color:green\">" + uIElementName + " is displayed in UI.</b>");
			//Thread.sleep(300);
		} catch (Throwable e) {
			extentReport.log(LogStatus.FAIL, "<pre>" + e.getMessage() + "</pre>");
			takeScreenShot(uIElementName);
			LOGGER.error("Element " + uIElementName + " is not present in UI", e);
			throw e;
		}
	}

	public void navigate() throws Exception {
		try {
			String url = configurationBundle.getString("EnvironmentURL");
			extentReport.log(LogStatus.INFO, "Navigating to LBCS Application");
			driver.navigate().to(url);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		} catch (Exception e) {
			extentReport.log(LogStatus.FAIL, "<pre>" + e.getMessage() + "</pre>");
			takeScreenShot(uIElementName);
			LOGGER.error("Unable to navigate", e);
			throw e;
		}

	}

	public void selectFromDropDown(String data) throws Exception {
		try {
			extentReport.log(LogStatus.INFO, "Selecting value from " + uIElementName + " drop down list.");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(uIElementXpath)));
			Select select = new Select(driver.findElement(By.xpath(uIElementXpath)));
			select.selectByVisibleText(data);
			//Thread.sleep(300);
		} catch (Exception e) {
			extentReport.log(LogStatus.FAIL, "<pre>" + e.getMessage() + "</pre>");
			takeScreenShot(uIElementName);
			LOGGER.error("Unable to select value from drop down", e);
			throw e;
		}
	}

	public void takeScreenShot(String imageName) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String imageNameWithTime = imageName+new SimpleDateFormat("MMddhhmmss").format(new Date());
			FileUtils.copyFile(scrFile, new File(filePath + imageNameWithTime + ".png"));
			String image = extentReport.addScreenCapture(filePath + imageNameWithTime + ".png");
			extentReport.log(LogStatus.INFO, "Image", "<b style=\"color:red\">Failed On Screen:</b>" + image);
		} catch (IOException e) {
			LOGGER.error("Unable to capture screen shot.", e);
		}
	}

	public void closeBrowser() {
		driver.close();
		driver.quit();
	}

	public void hardPause(String data) throws InterruptedException {

		int second = (Integer.parseInt(data) * 1000);
		Thread.sleep(second);
	}

	public void explicitWaitLocate(String timeInSec){
		try{
			WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(timeInSec));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(uIElementXpath)));
		}catch(Exception e){
			LOGGER.error("Faild to locate the Element", e);
		}

	}

	public void scrollDown() {

		((JavascriptExecutor) driver).executeScript("scroll(0,200)");
	}

	public void scrollUp() {

		((JavascriptExecutor) driver).executeScript("scroll(200,0)");
	}

	public void scrollUpToElement() throws Exception {
		try{
			WebElement element = driver.findElement(By.xpath(uIElementXpath));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
		}
		catch(Exception e){
			LOGGER.error("Faild to scroll up to element", e);
		}
	}

	public void setLargeText(String data) throws Exception{
		try{
			int len;
			char[] chr = new char[4096];
			final StringBuilder buffer = new StringBuilder();
			final FileReader reader = new FileReader(testConfigFilePath+data+".txt");
			try {
				while ((len = reader.read(chr)) > 0) {
					buffer.append(chr, 0, len);
				}
			} finally {
				reader.close();
			}
			extentReport.log(LogStatus.INFO, "Writing in <b>" + uIElementName + "<b>.");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(uIElementXpath)));
			driver.findElement(By.xpath(uIElementXpath)).clear();
			driver.findElement(By.xpath(uIElementXpath)).sendKeys(buffer.toString());
		}catch(Exception e){
			extentReport.log(LogStatus.FAIL, "<pre>" + e.getMessage() + "</pre>");
			takeScreenShot(uIElementName);
			LOGGER.error("Unable to write in " + uIElementName + " field.", e);
			throw e;
		}

	}

	/**
	 *
	 * Application Specific Keyword
	 *
	 */
	public void verifyErrorMessage(String expectedValue) throws Exception {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(uIElementXpath)));
			extentReport.log(LogStatus.INFO, "<b style=\"color:orange\">Verifying error message is displaying or not.</b>");
			Assert.assertEquals(expectedValue, driver.findElement(By.xpath(uIElementXpath))
					.getText().replaceFirst("×", "").trim());
			extentReport.log(LogStatus.PASS, "<b style=\"color:green\">Error message is displaying properly.</b>");
		} catch (Throwable  e) {
			extentReport.log(LogStatus.FAIL, "<pre>" + e.getMessage() + "</pre>");
			takeScreenShot(uIElementName);
			LOGGER.error("Error message is not displaying", e);
			throw e;
		}
	}

	public void executeKeyword(String Keyword) throws Exception {

		switch (Keyword) {

		case "clickLink":
			clickLink();
			break;

		case "clickButton":
			clickButton();
			break;

		case "clickRadioButton":
			clickRadioButton();
			break;

		case "verifyPresence":
			verifyPresence();
			break;

		case "navigate":
			navigate();
			break;

		case "scrollDown":
			scrollDown();
			break;

		case "scrollUp":
			scrollUp();
			break;

		case "scrollUpToElement":
			scrollUpToElement();
			break;

		default:
			break;
		}
	}

	public void executeKeyword(String Keyword, String data, String expectedValue) throws Exception {

		switch (Keyword) {

		case "setText":
			setText(data);
			break;

		case "setLargeText":
			setLargeText(data);
			break;

		case "verifyText":
			verifyText(expectedValue);
			break;

		case "verifyPlaceHolderText":
			verifyPlaceHolderText(expectedValue);
			break;

		case "selectFromDropDown":
			selectFromDropDown(data);
			break;

		case "hardPause":
			hardPause(data);
			break;

		case "explicitWaitLocate":
			explicitWaitLocate(data);
			break;

		case "verifyErrorMessage":
			verifyErrorMessage(expectedValue);
			break;

		default:
			break;
		}
	}
}