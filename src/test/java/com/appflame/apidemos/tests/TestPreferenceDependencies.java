package com.appflame.apidemos.tests;

import com.appflame.apidemos.pages.NavigationMenuPage;
import com.appflame.apidemos.pages.PreferenceDependenciesPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class that verifies the behavior of the “Preference → Preference dependencies” screen.
 */
public class TestPreferenceDependencies extends BaseTest {

    @Test
    public void testWifiDependencyPersistence() {
        NavigationMenuPage menu = new NavigationMenuPage(driver);

        PreferenceDependenciesPage preference = menu.goToPreferenceDependencies();

        Assert.assertFalse(preference.isWifiSettingsClickable(),
                "'WiFi settings' should NOT be clickable before WiFi checkbox is checked");

        preference.toggleWifiCheckbox();

        Assert.assertTrue(preference.isWifiSettingsClickable(),
                "'WiFi settings' should be clickable after enabling WiFi checkbox");

        preference.openWifiSettings();

        var text = "MyNetwork_" + System.currentTimeMillis();
        preference.enterText(text);

        preference.pressOkInDialog();

        driver.terminateApp(BUNDLE_ID);
        driver.activateApp(BUNDLE_ID);

        PreferenceDependenciesPage preference1 = menu.goToPreferenceDependencies();

        Assert.assertTrue(preference1.isWifiCheckboxChecked(),
                "WiFi checkbox did not remain checked after app restart");

        preference1.openWifiSettings();

        var persistedText = preference1.getWifiDialogText();

        Assert.assertTrue(persistedText.isEmpty(),
                "WiFi settings text persisted unexpectedly: " + persistedText);
    }
}
