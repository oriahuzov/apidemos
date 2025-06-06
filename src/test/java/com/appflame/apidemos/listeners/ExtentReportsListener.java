package com.appflame.apidemos.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TestNG listener that integrates ExtentReports for HTML reporting.
 */
public class ExtentReportsListener implements ITestListener {
    private static final String OUTPUT_FOLDER = System.getProperty("user.dir") + "/test-output/";
    private static final String REPORT_NAME = "ExtentReport_" +
            new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".html";

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    /**
     * Initializes the ExtentReports and SparkReporter before any tests run.
     *
     * @param context the TestNG context for the suite
     */
    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter spark = new ExtentSparkReporter(OUTPUT_FOLDER + "reports/" + REPORT_NAME);
        spark.config().setDocumentTitle("API Demos Automation Report");
        spark.config().setReportName("Regression Suite");
        spark.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(spark);

        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Appium Version", "2.19.0");
    }

    /**
     * Flushes and writes the ExtentReports to disk after all tests have finished.
     *
     * @param context the TestNG context for the suite
     */
    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

    /**
     * Creates a new ExtentTest entry when each individual test starts.
     *
     * @param result the ITestResult for the test method
     */
    @Override
    public void onTestStart(ITestResult result) {
        var method = result.getMethod().getConstructorOrMethod().getMethod();
        var className = result.getTestClass().getName();
        var methodName = method.getName();
        ExtentTest test = extent.createTest(className + " :: " + methodName);
        testThread.set(test);
        test.info("Test started: " + methodName);
    }

    /**
     * Logs a pass status to the report when a test succeeds.
     *
     * @param result the ITestResult for the passed test
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().log(Status.PASS, "Test passed");
    }

    /**
     * Logs a failure to the report, captures a screenshot, and attaches it when a test fails.
     *
     * @param result the ITestResult for the failed test
     */
    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = testThread.get();
        test.log(Status.FAIL, "Test failed: " + result.getThrowable());

        Object currentClass = result.getInstance();
        AndroidDriver driver = ((com.appflame.apidemos.tests.BaseTest) currentClass).driver;

        var screenshotsDir = OUTPUT_FOLDER + "screenshots/";
        new File(screenshotsDir).mkdirs();

        var screenshotPath = screenshotsDir + result.getName() + ".png";
        try {
            var src = driver.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(screenshotPath));
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (IOException e) {
            test.log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
        }
    }

    /**
     * Logs a skip status to the report when a test is skipped.
     *
     * @param result the ITestResult for the skipped test
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        testThread.get().log(Status.SKIP, "Test skipped");
    }
}
