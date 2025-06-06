package com.appflame.apidemos.tests;

import com.appflame.apidemos.config.AppiumDriverFactory;
import com.appflame.apidemos.listeners.ExtentReportsListener;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * BaseTest is the superclass for all TestNG test classes.
 */
@Listeners(ExtentReportsListener.class)
public class BaseTest {
    public static final String BUNDLE_ID = "io.appium.android.apis";
    public AndroidDriver driver;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver = AppiumDriverFactory.createDriver();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        AppiumDriverFactory.stopServer();
    }
}
