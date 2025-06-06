package com.appflame.apidemos.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

import static com.appflame.apidemos.config.AppiumDriverFactory.DEFAULT_TIMEOUT;

/**
 * Page Object for the “Preference → Preferences from code” screen in the ApiDemos app.
 */
public class PreferencesFromCodePage extends BasePage {
    private static final By CHECKBOXES_LOCATOR =
            By.xpath("//android.widget.CheckBox[@resource-id=\"android:id/checkbox\"]");

    public PreferencesFromCodePage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "android:id/switch_widget")
    private WebElement preferenceSwitch;

    /**
     * Enables the preference switch by tapping it if it is not already checked.
     */
    public void enablePreferenceSwitch() {
        if (!isChecked(preferenceSwitch)) {
            click(preferenceSwitch);
        }
    }

    /**
     * Locates all checkboxes on the screen and enables each one if it is not already checked.
     */
    public void enableCheckboxes() {
        int count = driver.findElements(CHECKBOXES_LOCATOR).size();

        IntStream.range(0, count)
                .forEach(i -> {
                    var checkbox = getCheckboxes().get(i);
                    if (!isChecked(checkbox)) {
                        click(checkbox);
                    }
                });
    }

    /**
     * Verifies that the switch and all checkboxes are enabled (i.e., checked).
     *
     * @return true if the switch is on and all checkboxes are checked; false otherwise
     */
    public boolean areElementsEnabled() {
        boolean isSwitchOn = isChecked(preferenceSwitch);
        boolean allChecked = getCheckboxes().stream()
                .allMatch(this::isChecked);

        return isSwitchOn && allChecked;
    }

    /**
     * Helper method to retrieve all checkbox WebElements on the screen.
     *
     * @return a List of WebElements matching the checkbox locator
     */
    private List<WebElement> getCheckboxes() {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(CHECKBOXES_LOCATOR, 0));
        return driver.findElements(CHECKBOXES_LOCATOR);
    }
}
