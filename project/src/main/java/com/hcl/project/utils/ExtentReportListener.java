package com.hcl.project.utils;


import java.awt.Desktop; // // Used to open report folder automatically after execution
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat; // For timestamp-based report naming
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;   // Provides test execution context (suite, parameters, groups)
import org.testng.ITestListener; // Interface to listen test events
import org.testng.ITestResult;   // Contains result of each test (pass/fail/skip)

import com.aventstack.extentreports.ExtentReports;        // Main report class
import com.aventstack.extentreports.ExtentTest;           // Represents each test in report
import com.aventstack.extentreports.reporter.ExtentSparkReporter; // HTML report generator
import com.aventstack.extentreports.reporter.configuration.Theme; // Theme config

public class ExtentReportListener implements ITestListener {

	// Single report instance for entire execution (shared across tests)
	private static ExtentReports extent;
	
	// Thread-safe storage -> each test gets its own ExtentTest instance
	// Important for parallel execution
	private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

	
    // ========================================
    // 🔹 EXECUTES ONCE BEFORE TESTS START
    // ========================================
	@Override
	public void onStart(ITestContext context) {

		// Create unique timestamp to avoid overwriting reports
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

		// Define report file path
		ExtentSparkReporter spark = new ExtentSparkReporter(
				System.getProperty("user.dir") + "/reports/report-" + timeStamp + ".html");

		// Configure report UI
        spark.config().setDocumentTitle("Automation Report"); // Browser tab title
        spark.config().setReportName("Functional Testing");   // Report heading
        spark.config().setTheme(Theme.DARK);                  // DARK / STANDARD theme

        // Initialize ExtentReports and attach reporter
		extent = new ExtentReports();
		extent.attachReporter(spark);

		// Add system/environment details to report
		extent.setSystemInfo("User", System.getProperty("user.name"));

		// Fetch parameters from testng.xml
		String browser = context.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);

		String os = context.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("OS", os);

		// Capture test groups (if any)
		List<String> groups = context.getCurrentXmlTest().getIncludedGroups();
		if (!groups.isEmpty()) {
			extent.setSystemInfo("Groups", groups.toString());
		}
	}
	
	
    // =======================================
    //  EXECUTES BEFORE EACH TEST METHOD
    // =======================================
	@Override
	public void onTestStart(ITestResult result) {
		
		// Create a new test entry in report using method name
		ExtentTest test = extent.createTest(result.getMethod().getMethodName());
		
		// Assign TestNG groups as categories in report
		test.assignCategory(result.getMethod().getGroups());
		
		// Store test instance in ThreadLocal (for parallel safety)
		testThread.set(test);
	}

	// Returns current thread’s ExtentTest instance
	public static ExtentTest getTest() {
		return testThread.get();
	}
	
	
    // ================================
    //  EXECUTES WHEN TEST PASSES
    // ================================
	@Override
	public void onTestSuccess(ITestResult result) {
		getTest().pass(" Test Passed");
	}
	
	
    // ================================
    //  EXECUTES WHEN TEST FAILS
    // ================================
	@Override
	public void onTestFailure(ITestResult result) {
		getTest().fail(" Test Failed");
		getTest().fail(result.getThrowable());
	}
	
	
    // ================================
    //  EXECUTES WHEN TEST IS SKIPPED
    // ================================
	@Override
	public void onTestSkipped(ITestResult result) {
		getTest().skip(" Test Skipped");
		getTest().skip(result.getThrowable());
	}
	
	
    // ================================
    //  EXECUTES AFTER ALL TESTS COMPLETE
    // ================================
	@Override
	public void onFinish(ITestContext context) {

		// Write all logs to report file
		extent.flush();

		// Open reports folder automatically
		String path = System.getProperty("user.dir") + "/reports";
		File folder = new File(path);

		try {
			Desktop.getDesktop().open(folder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}