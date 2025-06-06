package com.appflame.apidemos.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.util.stream.IntStream;

/**
 * Page Object for the “Preference → Launching preferences” screen in the ApiDemos app.
 */
public class LaunchingPreferencesPage extends BasePage {

    public LaunchingPreferencesPage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Launch PreferenceActivity\")")
    private WebElement launchPreferenceActivityButton;

    @AndroidFindBy(xpath = "//android.widget.ListView[@resource-id=\"android:id/list\"]" +
            "/android.widget.LinearLayout[1]/android.widget.RelativeLayout")
    private WebElement myPreference;

    @AndroidFindBy(uiAutomator = "new UiSelector().textStartsWith(\"The counter value is \")")
    private WebElement mainCounterValue;

    @AndroidFindBy(id = "io.appium.android.apis:id/mypreference_widget")
    private WebElement prefScreenCounterValue;

    /**
     * Reads the counter value displayed on the main “Launching preferences” screen.
     * The text is expected to be in the format “The counter value is X”.
     *
     * @return the integer value X parsed from the main counter TextView
     */
    public int getMainCounterValue() {
        var text = mainCounterValue.getText();
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }

    /**
     * Reads the counter value displayed within the PreferenceActivity screen.
     * The TextView’s text is expected to be a plain integer string.
     *
     * @return the integer value displayed in the preference screen’s counter widget
     */
    public int getPrefScreenCounter() {
        var text = prefScreenCounterValue.getText();
        return Integer.parseInt(text);
    }

    /**
     * Taps the “Launch PreferenceActivity” button to open the preference screen.
     */
    public void clickLaunchPreferenceActivity() {
        click(launchPreferenceActivityButton);
    }

    /**
     * Taps the “My preference” button the specified number of times.
     *
     * @param times the number of times to tap the “My preference” entry
     */
    public void clickMyPreferenceNTimes(int times) {
        IntStream.range(0, times)
                .forEach(i -> click(myPreference));
    }

    /**
     * Navigates back from the PreferenceActivity screen to the “Launching preferences” main screen.
     */
    public void backToLaunchPreferenceActivity() {
        pressBack();
    }
}
