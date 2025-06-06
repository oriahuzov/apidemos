package com.appflame.apidemos.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.appflame.apidemos.config.AppiumDriverFactory.DEFAULT_TIMEOUT;

/**
 * BasePage provides common WebElement interactions (clicking, typing, waiting, etc.)
 * and holds a reference to the AndroidDriver for all page objects to inherit.
 */
@Slf4j
public class BasePage {
    protected AndroidDriver driver;

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     * Clicks on the given element after waiting for it to become visible.
     *
     * @param element the WebElement to click
     */
    protected void click(WebElement element) {
        waitForVisibility(element).click();
    }

    /**
     * Clears any existing text and enters the provided text into the given element,
     * after waiting for it to become visible.
     *
     * @param element the WebElement (e.g., a text field) into which to enter text
     * @param text    the String value to send
     */
    protected void enterText(WebElement element, String text) {
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Simulates pressing the Android "Back" button.
     */
    protected void pressBack() {
        driver.navigate().back();
    }

    /**
     * Returns whether the given element’s “checked” attribute is true.
     * Useful for verifying the state of checkboxes or switches.
     *
     * @param element the WebElement representing a checkbox or switch
     * @return true if the element’s “checked” attribute is “true,” false otherwise
     */
    protected boolean isChecked(WebElement element) {
        return Boolean.parseBoolean(element.getAttribute("checked"));
    }

    /**
     * Waits until the given WebElement is visible on the screen, then returns it.
     * If the element does not become visible within TIMEOUT_SECONDS, a TimeoutException is thrown.
     *
     * @param element the WebElement to wait for
     * @return the same WebElement once it is visible
     */
    protected WebElement waitForVisibility(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOf(element));
    }
}
