package com.appflame.apidemos.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Page Object for the main navigation menu in the ApiDemos app.
 */
public class NavigationMenuPage extends BasePage {

    public NavigationMenuPage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(accessibility = "App")
    private WebElement appMenu;

    @AndroidFindBy(accessibility = "Preference")
    private WebElement preferenceMenu;

    @AndroidFindBy(accessibility = "Animation")
    private WebElement animationMenu;

    @AndroidFindBy(accessibility = "Loader")
    private WebElement loaderOption; // App -> Loader

    @AndroidFindBy(accessibility = "Custom")
    private WebElement customOption; // Loader -> Custom

    @AndroidFindBy(accessibility = "5. Preferences from code")
    private WebElement preferencesFromCodeOption;

    @AndroidFindBy(accessibility = "2. Launching preferences")
    private WebElement launchingPreferencesOption;

    @AndroidFindBy(accessibility = "3. Preference dependencies")
    private WebElement preferenceDependenciesOption;

    @AndroidFindBy(accessibility = "Seeking")
    private WebElement seekingOption; // Animation -> Seeking

    /**
     * Navigates to App → Loader → Custom.
     * <p>
     * Clicks the “App” menu, then “Loader” sub‐option, then “Custom” sub‐option.
     */
    public CustomLoaderPage goToAppLoaderCustom() {
        click(appMenu);
        click(loaderOption);
        click(customOption);
        return new CustomLoaderPage(driver);
    }

    /**
     * Navigates to Preference → Preferences from code.
     * <p>
     * Clicks the “Preference” menu, then “Preferences from code” sub‐option.
     */
    public PreferencesFromCodePage goToPreferencesFromCode() {
        click(preferenceMenu);
        click(preferencesFromCodeOption);
        return new PreferencesFromCodePage(driver);
    }

    /**
     * Navigates to Preference → Launching preferences.
     * <p>
     * Clicks the “Preference” menu, then “Launching preferences” sub‐option.
     */
    public LaunchingPreferencesPage goToLaunchingPreferences() {
        click(preferenceMenu);
        click(launchingPreferencesOption);
        return new LaunchingPreferencesPage(driver);
    }

    /**
     * Navigates to Preference → Preference dependencies.
     * <p>
     * Clicks the “Preference” menu, then “Preference dependencies” sub‐option.
     */
    public PreferenceDependenciesPage goToPreferenceDependencies() {
        click(preferenceMenu);
        click(preferenceDependenciesOption);
        return new PreferenceDependenciesPage(driver);
    }

    /**
     * Navigates to Animation → Seeking.
     * <p>
     * Clicks the “Animation” menu, then “Seeking” sub‐option.
     */
    public AnimationSeekingPage goToAnimationSeeking() {
        click(animationMenu);
        click(seekingOption);
        return new AnimationSeekingPage(driver);
    }
}
