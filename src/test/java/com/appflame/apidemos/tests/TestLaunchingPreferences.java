package com.appflame.apidemos.tests;

import com.appflame.apidemos.pages.LaunchingPreferencesPage;
import com.appflame.apidemos.pages.NavigationMenuPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Test class that verifies the counter behavior on the “Preference → Launching preferences” screen.
 */
@Slf4j
public class TestLaunchingPreferences extends BaseTest {

    @Test
    public void testCounterIncrements() {
        NavigationMenuPage menu = new NavigationMenuPage(driver);

        LaunchingPreferencesPage preference = menu.goToLaunchingPreferences();

        int mainCounterBefore = preference.getMainCounterValue();

        preference.clickLaunchPreferenceActivity();

        int times = ThreadLocalRandom.current().nextInt(1, 11); // [1,10]

        int prefScreenCounterBefore = preference.getPrefScreenCounter();

        preference.clickMyPreferenceNTimes(times);

        int prefScreenCounterAfter = preference.getPrefScreenCounter();

        Assert.assertEquals(prefScreenCounterAfter, prefScreenCounterBefore + times,
                "Preference screen counter did not increase by " + times);

        preference.backToLaunchPreferenceActivity();

        int mainCounterAfter = preference.getMainCounterValue();

        Assert.assertEquals(mainCounterAfter, mainCounterBefore + times,
                "Main screen counter did not increase by " + times + " after returning");
    }
}
