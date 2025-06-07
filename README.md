### This repository contains an automated test suite for the API Demos Android sample app, using Java 17, Appium 2.19.0, TestNG, and Gradle. It follows the Page Object Model (POM) and includes:

- Custom Loader Search Filtering: Data-driven search tests.

- Preferences from Code: Switch and checkboxes enabling.

- Launching Preferences Counter: Counter increment verification across activities.

- Preference Dependencies (WiFi): Checkbox dependency & persistence testing.

- Animation → Seeking: Pixel-based motion detection for SeekBar.

### Prerequisites

- Java 17

- Android SDK 35.0.0 & Command-Line Tools (adb, emulator, uiautomator2 driver) 

- Appium 2.19.0

- Android Virtual Device (AVD) (Pixel_9 is used by default for all tests, hence it should be installed on a device)

### Environment Variables
| Variable       | Description | Default        |
|----------------|:-----------:|----------------|
| AVD_NAME       |  Name of the emulator to launch | Pixel_9    |
| EXTERNAL_APPIUM        |  true to use an external Appium server; else code starts one | false|

Set them in your shell before running tests:

```export AVD_NAME=your_device (if not specified, Pixel_9 will be used by default)```

```export EXTERNAL_APPIUM=true (if needed)```

### Installation & Setup

- Clone the repository

```git clone https://github.com/oriahuzov/apidemos.git```

- Export required environment variables as shown above
- Run all tests

```./gradlew clean test```

- To run a single test class:

```../gradlew test --tests "com.appflame.apidemos.tests.TestAnimationSeeking"```

### Viewing Reports

After execution, reports and screenshots are generated under ```test-output/```:

```bash
test-output/
├─ reports/                # ExtentReports HTML files
│   └─ ExtentReport_<timestamp>.html
└─ screenshots/            # Screenshots on failure
    └─ <testMethodName>.png```




