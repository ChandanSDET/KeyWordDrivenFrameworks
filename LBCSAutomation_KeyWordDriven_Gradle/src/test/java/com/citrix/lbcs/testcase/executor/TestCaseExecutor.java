package com.citrix.lbcs.testcase.executor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.citrix.lbcs.utils.SeleniumAction;
import com.citrix.lbcs.utils.TestBase;
import com.citrix.lbcs.utils.XlsUtil;

public class TestCaseExecutor extends TestBase {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestCaseExecutor.class);

	public XlsUtil testingPhase;
	public XlsUtil testType;
	public XlsUtil controller;

	public String currentTestType;
	public String currentScenario;
	public String currentTestCase;
	public String keyword;
	public int testStep;

	public FileInputStream OrfileReader;
	public FileInputStream ConfigfileReader;

	@BeforeClass
	public void initialize() throws IOException {
		try {
			objectRepository = new Properties();
			OrfileReader = new FileInputStream(
					System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
					+ "resources" + File.separator + "objectRepository" + File.separator + "xpaths.properties");
			objectRepository.load(OrfileReader);

			configurationBundle = ResourceBundle.getBundle("application");

			testingPhase = new XlsUtil(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
					+ File.separator + "resources" + File.separator + "keywordDrivenTestPhaseSelection" + File.separator
					+ "TestMaster.xls");
		} catch (FileNotFoundException e) {

			LOGGER.error("File is not found in specified Location.\n",e);
		}
	}

	@Test
	public void testCaseExecutor() {
		try{
			LOGGER.info("<<<<<<<< LBCS Application is being tested with below URL >>>>>>>>\n" 
					+"\t\t\t\t\t\t\t\t\t\t"+ configurationBundle.getString("EnvironmentURL"));
			for (int testTypeid = 2; testTypeid <= testingPhase.getRowCount("TestType"); testTypeid++) {
				currentTestType = testingPhase.getCellData("TestType", "Test Type", testTypeid);

				if (testingPhase.getCellData("TestType", "Runmode", testTypeid).equalsIgnoreCase("Y")) {
					testType = new XlsUtil(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
							+ File.separator + "resources" + File.separator + currentTestType.toLowerCase()
							+ File.separator + currentTestType + ".xls");

					for (int scenarioid = 2; scenarioid <= testType.getRowCount("Scenario"); scenarioid++) {
						currentScenario = testType.getCellData("Scenario", "Scenarios", scenarioid);
						driver = null;

						if (testType.getCellData("Scenario", "Runmode", scenarioid).equalsIgnoreCase("Y")) {
							controller = new XlsUtil(System.getProperty("user.dir") + File.separator + "src"
									+ File.separator + "test" + File.separator + "resources" + File.separator
									+ currentTestType.toLowerCase() + File.separator + currentScenario + ".xls");
							initailizeBrowser(configurationBundle.getString("Browser"));

							for (int tcid = 2; tcid <= controller.getRowCount("Suite1"); tcid++) {
								// driver=null;
								currentTestCase = controller.getCellData("Suite1", "Test Case", tcid);
								if (controller.getCellData("Suite1", "Runmode", tcid).equalsIgnoreCase("Y")) {

									extentReport = extent.startTest(currentTestCase, "");
									// initailizeBrowser();
									LOGGER.info("##### Executing \"" + currentTestCase + "\" TestCase #####\n");
									for (testStep = 2; testStep <= controller.getRowCount(currentTestCase); testStep++) {

										keyword = controller.getCellData(currentTestCase, "Keyword", testStep);

										keyword = Character.toLowerCase(keyword.charAt(0))
												+ (keyword.length() > 1 ? keyword.substring(1) : "");
			
										object = controller.getCellData(currentTestCase, "UI Element", testStep);

										data = controller.getCellData(currentTestCase, "Data", testStep);

										expectedValue = controller.getCellData(currentTestCase, "Expected Data", testStep);

										SeleniumAction seleniumAction = new SeleniumAction(object,extentReport,driver,objectRepository,configurationBundle,wait);

										try {
											if(data.equals("")&&expectedValue.equals(""))
												seleniumAction.executeKeyword(keyword);
											else
												seleniumAction.executeKeyword(keyword,data,expectedValue);
											extent.endTest(extentReport);
											extent.flush();
										} catch (Throwable e) {
											// driver.quit();
											LOGGER.error("Error While Executing Keyword "+keyword);
											extent.endTest(extentReport);
											extent.flush();
											break;
										}

									}
									/*
									 * if(driver!=null){ Each test case closing
									 * Browser. //driver.quit(); }
									 */
								}

							}

						}

						if (driver != null) {
							driver.quit();
						}

					}

				}

			}

		}catch(Exception e){
			LOGGER.error("File path is wrong Or There is some wrong entry in the .xls file.",e);
			driver.quit();
		}
	}
}
