package com.appflame.apidemos.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;

/**
 * Page Object representing the “Animation → Seeking” screen in the ApiDemos app.
 */
@Slf4j
public class AnimationSeekingPage extends BasePage {

    public AnimationSeekingPage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "io.appium.android.apis:id/seekBar")
    private WebElement seekBar;

    /**
     * Swipe the SeekBar thumb from left toward right by the given percentage,
     * using Appium’s mobile: swipeGesture API.
     *
     * @param percentOfBar a value between 0.0 and 1.0 (e.g. 0.8 for 80%)
     */
    public void setSeekBarToPercentageViaSwipe(double percentOfBar) {
        if (percentOfBar < 0) percentOfBar = 0;
        if (percentOfBar > 1) percentOfBar = 1;

        var elementId = ((RemoteWebElement) seekBar).getId();

        Map<String, Object> args = new HashMap<>();
        args.put("elementId", elementId);
        args.put("direction", "right");
        args.put("percent", percentOfBar);

        log.debug("Executing the script with args {} to set the seek bar to percentage {}", args, percentOfBar);
        driver.executeScript("mobile: swipeGesture", args);
    }
}
