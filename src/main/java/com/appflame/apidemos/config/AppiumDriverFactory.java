package com.appflame.apidemos.config;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.time.Duration;
import java.util.Optional;

/**
 * A singleton‐style factory for AndroidDriver, with programmatic Appium server startup.
 */
@Slf4j
public class AppiumDriverFactory {
    public static final int DEFAULT_TIMEOUT = 20; // in seconds
    public static final Duration AVD_LAUNCH_TIMEOUT = Duration.ofSeconds(120);
    public static final String EXTERNAL_APPIUM_ENV = "EXTERNAL_APPIUM";
    public static final String AVD_NAME_ENV = "AVD_NAME";
    public static final String AVD_DEFAULT_NAME = "Pixel_9";
    public static final String APPIUM_DEFAULT_SERVER_URL = "http://127.0.0.1:4723/wd/hub";
    public static final String PLATFORM_NAME = "Android";
    public static final String AUTOMATION_NAME = "UiAutomator2";
    public static final File APP_PATH = new File(System.getProperty("user.dir") + "/app/ApiDemos.apk");
    private static AndroidDriver driver;
    private static AppiumDriverLocalService service;

    /**
     * Create (or return existing) AndroidDriver. If driver==null:
     * 1) Start Appium server programmatically (unless EXTERNAL_APPIUM env var is set).
     * 2) Build UiAutomator2Options (including AVD).
     * 3) Launch driver.
     */
    public static AndroidDriver createDriver() {
        if (driver != null) {
            return driver;
        }

        if (!Boolean.parseBoolean(System.getenv().getOrDefault(EXTERNAL_APPIUM_ENV, "false"))) {
            service = startAppiumServer();
        } else {
            log.info("EXTERNAL_APPIUM=true, skipping programmatic server startup.");
        }

        UiAutomator2Options options = buildOptions();

        java.net.URL serverUrl;
        try {
            if (service != null && service.isRunning()) {
                serverUrl = service.getUrl();
                log.info("Using programmatic Appium server at: {}", serverUrl);
            } else {
                serverUrl = new java.net.URL(APPIUM_DEFAULT_SERVER_URL);
                log.info("Using default Appium server URL: {}", serverUrl);
            }

            driver = new AndroidDriver(serverUrl, options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_TIMEOUT));
        } catch (Exception e) {
            log.error("Failed to initialize the Appium driver: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize Appium driver", e);
        }

        return driver;
    }

    /**
     * Quit the driver and stop the programmatic Appium server (if one was started).
     */
    public static void stopServer() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                log.warn("Error while quitting driver: {}", e.getMessage());
            } finally {
                driver = null;
            }
            log.info("AndroidDriver has been quit.");
        }
        if (service != null && service.isRunning()) {
            service.stop();
            log.info("Programmatic Appium server stopped.");
        }
    }

    /**
     * Builds UiAutomator2Options instance.
     */
    private static UiAutomator2Options buildOptions() {
        var avdName = Optional.ofNullable(System.getenv(AVD_NAME_ENV))
                .filter(name -> !name.isBlank())
                .orElse(AVD_DEFAULT_NAME);

        if (!APP_PATH.exists()) {
            throw new IllegalStateException("Could not find ApiDemos.apk at: " + APP_PATH.getAbsolutePath());
        }

        return new UiAutomator2Options()
                .setPlatformName(PLATFORM_NAME)
                .setAutomationName(AUTOMATION_NAME)
                .setAvd(avdName)
                .setAvdLaunchTimeout(AVD_LAUNCH_TIMEOUT)
                .setApp(APP_PATH.getAbsolutePath())
                .setNoReset(false)
                .setSkipDeviceInitialization(true);
    }

    /**
     * Starts a local Appium server (on any free port) with session override and INFO log level.
     * You can skip calling this if you prefer to run `appium` manually beforehand.
     */
    private static AppiumDriverLocalService startAppiumServer() {
        log.info("Starting Appium server programmatically...");
        AppiumDriverLocalService server = new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                .build();
        server.start();
        log.info("Appium server started at: {}", server.getUrl());
        return server;
    }
}
