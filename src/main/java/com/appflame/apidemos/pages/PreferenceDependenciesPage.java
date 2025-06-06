package com.appflame.apidemos.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Page Object for the “Preference → Preference dependencies” screen in the ApiDemos app.
 */
public class PreferenceDependenciesPage extends BasePage {

    public PreferenceDependenciesPage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "android:id/checkbox")
    private WebElement wifiCheckbox;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/title\").text(\"WiFi settings\")")
    private WebElement wifiSettings;

    @AndroidFindBy(id = "android:id/edit")
    private WebElement wifiEditText;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement okButton;

    /**
     * Checks whether the “WiFi settings” option is currently clickable (enabled and visible).
     *
     * @return true if the WiFi settings text is both enabled and displayed; false otherwise
     */
    public boolean isWifiSettingsClickable() {
        return wifiSettings.isEnabled() && wifiSettings.isDisplayed();
    }

    /**
     * Toggles the WiFi checkbox (checks it if unchecked, unchecks if checked).
     */
    public void toggleWifiCheckbox() {
        click(wifiCheckbox);
    }

    /**
     * Opens the WiFi settings dialog by tapping its title.
     */
    public void openWifiSettings() {
        click(wifiSettings);
    }

    /**
     * Enters the provided text into the WiFi settings dialog’s EditText field.
     *
     * @param text the WiFi SSID or any input to send to the EditText
     */
    public void enterText(String text) {
        enterText(wifiEditText, text);
    }

    /**
     * Presses the OK button in the WiFi settings dialog to confirm the entered text.
     */
    public void pressOkInDialog() {
        click(okButton);
    }

    /**
     * Returns whether the WiFi checkbox is currently checked.
     *
     * @return true if the checkbox’s “checked” attribute is true; false otherwise
     */
    public boolean isWifiCheckboxChecked() {
        return isChecked(wifiCheckbox);
    }

    /**
     * Retrieves the current text from the WiFi settings dialog’s EditText field.
     *
     * @return the string contained in the EditText
     */
    public String getWifiDialogText() {
        return wifiEditText.getText();
    }
}
