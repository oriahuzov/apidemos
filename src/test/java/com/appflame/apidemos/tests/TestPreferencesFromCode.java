package com.appflame.apidemos.tests;

import com.appflame.apidemos.pages.NavigationMenuPage;
import com.appflame.apidemos.pages.PreferencesFromCodePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class that verifies the “Preference → Preferences from code” screen.
 */
public class TestPreferencesFromCode extends BaseTest {

    @Test
    public void testEnableSwitchAndCheckboxes() {
        NavigationMenuPage menu = new NavigationMenuPage(driver);

        PreferencesFromCodePage preference = menu.goToPreferencesFromCode();

        preference.enablePreferenceSwitch();

        preference.enableCheckboxes();

        Assert.assertTrue(preference.areElementsEnabled(),
                "Either the switch or one/more checkboxes were not enabled correctly");
    }
}
