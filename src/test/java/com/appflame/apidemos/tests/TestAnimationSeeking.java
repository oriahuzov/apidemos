package com.appflame.apidemos.tests;

import com.appflame.apidemos.pages.AnimationSeekingPage;
import com.appflame.apidemos.pages.NavigationMenuPage;
import com.appflame.apidemos.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Test class that verifies the animated circle on the “Animation → Seeking” screen moves
 * when the SeekBar thumb is swiped.
 */
@Slf4j
public class TestAnimationSeeking extends BaseTest {

    @Test
    public void testCircleMovesWhenSeekBarChanged() throws IOException {
        NavigationMenuPage menu = new NavigationMenuPage(driver);

        AnimationSeekingPage animation = menu.goToAnimationSeeking();

        BufferedImage beforeImg = ImageUtils.takeScreenshotAsImage(driver);

        double percentage = 0.5; // = 50%
        animation.setSeekBarToPercentageViaSwipe(percentage);

        BufferedImage afterImg = ImageUtils.takeScreenshotAsImage(driver);

        int circleXAfter = ImageUtils.findMovementX(beforeImg, afterImg);
        log.info("Circle X after swipe: {}", circleXAfter);

        Assert.assertTrue(circleXAfter > 0,
                "Expected circle “after” X to be positive (movement detected), but got: " + circleXAfter);
    }
}
