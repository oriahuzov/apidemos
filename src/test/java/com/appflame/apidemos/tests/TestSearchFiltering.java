package com.appflame.apidemos.tests;

import com.appflame.apidemos.pages.CustomLoaderPage;
import com.appflame.apidemos.pages.NavigationMenuPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test class that verifies the search filtering behavior on the “App → Loader → Custom” screen.
 * <p>
 * This test is data-driven: for each search term provided by {@link #searchData()}.
 * The app is terminated and re-activated between each data-driven iteration to ensure a clean state.
 */
public class TestSearchFiltering extends BaseTest {

    @DataProvider(name = "searchTexts")
    public static Object[][] searchData() {
        return new Object[][]{
                {"Android"},
                {"Service"},
                {"Bluetooth"},
                {"Settings"},
                {"Google"}
        };
    }

    @BeforeMethod(alwaysRun = true)
    public void activateApp() {
        driver.activateApp(BUNDLE_ID);
    }

    @AfterMethod(alwaysRun = true)
    public void terminateApp() {
        driver.terminateApp(BUNDLE_ID);
    }

    @Test(dataProvider = "searchTexts")
    public void testSearchFiltering(String text) {
        NavigationMenuPage menu = new NavigationMenuPage(driver);

        CustomLoaderPage loader = menu.goToAppLoaderCustom();

        loader.openSearch();

        loader.typeSearchText(text);

        List<String> results = loader.getAllResultTexts();
        Assert.assertFalse(results.isEmpty(), "No results found for text: " + text);
        Assert.assertTrue(loader.isResultsFilteredCorrectly(text),
                "Search results NOT filtered correctly for text: " + text);

        loader.tapClearQueryButton();

        Assert.assertTrue(loader.isFilterFieldEmpty(), "Filter did not reset after clearing search field");
    }
}
