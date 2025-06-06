package com.appflame.apidemos.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static com.appflame.apidemos.config.AppiumDriverFactory.DEFAULT_TIMEOUT;

/**
 * Page Object for the “App → Loader → Custom” screen in the ApiDemos app.
 */
@Slf4j
public class CustomLoaderPage extends BasePage {

    public CustomLoaderPage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(accessibility = "Search")
    private WebElement searchIcon;

    @AndroidFindBy(id = "android:id/search_src_text")
    private WebElement searchInput;

    @AndroidFindBy(accessibility = "Clear query")
    private WebElement clearQueryButton;

    @AndroidFindBy(xpath = "//android.widget.ListView[@resource-id='android:id/list']" +
            "//android.widget.TextView")
    private List<WebElement> searchResultRows;

    /**
     * Taps the search icon to reveal the input field.
     */
    public void openSearch() {
        click(searchIcon);
    }

    /**
     * Enters the specified search term into the loader’s search input.
     *
     * @param term the text to enter into the search field
     */
    public void typeSearchText(String term) {
        enterText(searchInput, term);
    }

    /**
     * Retrieves the visible text of all result rows in the list view.
     *
     * @return a List of Strings, each representing one result row’s text
     */
    public List<String> getAllResultTexts() {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(driver -> !searchResultRows.isEmpty());

        return searchResultRows.stream()
                .map(WebElement::getText)
                .toList();
    }

    /**
     * Verifies that every result row’s text contains the given search text (case‐insensitive).
     *
     * @param searchText the substring that each result row’s text should contain
     * @return true if all items contain searchText; false otherwise
     */
    public boolean isResultsFilteredCorrectly(String searchText) {
        var lower = searchText.toLowerCase();

        return getAllResultTexts().stream()
                .allMatch(t -> t.toLowerCase().contains(lower));
    }

    /**
     * Taps the “Clear query” button to reset the search input field.
     */
    public void tapClearQueryButton() {
        log.debug("Clearing search query");
        click(clearQueryButton);
    }

    /**
     * Returns whether the search input field is currently blank (indicating filters are reset).
     *
     * @return true if the search input’s text is blank; false otherwise
     */
    public boolean isFilterFieldEmpty() {
        return searchInput.getText().isBlank();
    }
}
