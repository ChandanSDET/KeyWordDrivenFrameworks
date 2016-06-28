package com.citrix.lbcs.utils;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

	private static ExtentReports extent;

	private ExtentManager() {
	}

	public static ExtentReports getInstance() {
		if (extent == null) {
			extent = new ExtentReports(System.getProperty("user.dir") + "/report/LBCSAutomationReport.html", true);
			extent.loadConfig(
					new File(System.getProperty("user.dir") + "/src/test/resources/config/extent-config.xml"));
			extent.addSystemInfo("Selenium Version", "2.48").addSystemInfo("Java Version", "1.8")
					.addSystemInfo("Environment", "QA");
		}
		return extent;
	}
}