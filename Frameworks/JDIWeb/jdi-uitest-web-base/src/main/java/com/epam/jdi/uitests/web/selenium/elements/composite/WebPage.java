package com.epam.jdi.uitests.web.selenium.elements.composite;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.CacheValue;
import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.complex.tables.CheckTypes;
import com.epam.jdi.uitests.core.interfaces.composite.IPage;
import com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory;
import com.epam.jdi.uitests.web.selenium.elements.base.BaseElement;
import com.epam.jdi.uitests.web.selenium.settings.WebSettings;
import org.openqa.selenium.Cookie;

import java.util.function.Supplier;

import static com.epam.jdi.tools.switcher.SwitchActions.Else;
import static com.epam.jdi.tools.switcher.SwitchActions.Switch;
import static com.epam.jdi.tools.switcher.SwitchActions.Value;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.CheckTypes.*;
import static com.epam.jdi.uitests.core.logger.LogLevels.STEP;
import static com.epam.jdi.uitests.core.settings.JDISettings.*;
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory.getJSExecutor;
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory.hasRunDrivers;
import static com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory.jsExecute;
import static java.lang.String.format;

/**
 * WebPage composite element
 */
public class WebPage extends BaseElement implements IPage {
    public static boolean checkAfterOpen = false;
    public String url;
    public String title;
    public CheckTypes checkUrlType = EQUAL;
    public CheckTypes checkTitleType = EQUAL;
    public String urlTemplate;
    public static WebPage currentPage;

    /**
     * Constructs WebPage
     */
    public WebPage() {
    }

    /**
     * Constructs WebPage with URL
     * @param url URL
     */
    public WebPage(String url) {
        this.url = url;
    }

    /**
     * Constructs WebPage with URL and title
     * @param url URL
     * @param title title
     */
    public WebPage(String url, String title) {
        this.url = url;
        this.title = title;
    }

    /**
     * Opens URL
     * @param url URL
     */
    public static void openUrl(String url) {
        new WebPage(url).open();
    }

    /**
     * Gets currently opened URL
     * @return URL
     */
    public static String getUrl() {
        return WebDriverFactory.getDriver().getCurrentUrl();
    }

    /**
     * Gets current title
     * @return title
     */
    public static String getTitle() {
        return WebDriverFactory.getDriver().getTitle();
    }

    /**
     * Updates Page data with corresponding values
     * @param url URL
     * @param title title
     * @param checkUrlType check URL type
     * @param checkTitleType check title type
     * @param urlTemplate url template
     */
    public void updatePageData(String url, String title, CheckTypes checkUrlType, CheckTypes checkTitleType, String urlTemplate) {
        if (this.url == null)
            this.url = url;
        if (this.title == null)
            this.title = title;
        this.checkUrlType = checkUrlType;
        this.checkTitleType = checkTitleType;
        this.urlTemplate = urlTemplate;
    }

    /**
     * Checks URL by url template
     * @return StringCheckType
     */
    public StringCheckType url() {
        return new StringCheckType(getDriver()::getCurrentUrl, url, urlTemplate, "url");
    }

    /**
     * Checks title
     * @return StringCheckType
     */
    public StringCheckType title() {
        return new StringCheckType(getDriver()::getTitle, title, title, "title");
    }


    /**
     * Check that page opened
     */
    @JDIAction("Check opened")
    public void checkOpened() {
        logger.logOff();
        asserter.isTrue(isOpened(), format("Page '%s' is not opened", toString()));
        logger.logOn();
    }

    /**
     * Checks that page is opened
     * @return
     */
    @Override
    public boolean isOpened() {
        if (!hasRunDrivers())
            return false;
        boolean result = Switch(checkUrlType).get(
            Value(EQUAL, url().check()),
            Value(MATCH, url().match()),
            Value(CONTAINS, url().contains()),
            Else(false)
        );
        if (!result) return false;
        return Switch(checkTitleType).get(
            Value(EQUAL, title().check()),
            Value(MATCH, title().match()),
            Value(CONTAINS, title().contains()),
            Else(false)
        );
    }

    /**
     * Opens url specified for page
     */
    @JDIAction("open {url}")
    public void open() {
        CacheValue.reset();
        try {
            getDriver().navigate().to(url);
        } catch (Exception ex) {
            logger.debug("Second try open page: " + toString());
            getDriver().navigate().to(url);
        }
        if (checkAfterOpen)
            checkOpened();
        currentPage = this;
    }

    /**
     * Opens page by url
     */
    public void shouldBeOpened() {
        try {
            logger.info(format("Page '%s' should be opened", getName()));
            logger.logOff(() -> {
                if (isOpened()) return;
                open();
                checkOpened();
            });
        } catch (Exception ex) {
            throw exception(format("Can't open page '%s'. Reason: %s", getName(), ex.getMessage()));
        }
    }

    /**
     * Reload current page
     */
    @JDIAction("Reload current page '{name}'")
    public void refresh() {
        getDriver().navigate().refresh();
    }

    /**
     * Reloads current page
     */
    public void reload() { refresh(); }

    /**
     * Go back to previous page
     */
    @JDIAction("Go back to previous page")
    public void back() {
        getDriver().navigate().back();
    }


    /**
     * Go forward to next page
     */
    @JDIAction("Go forward to next page")
    public void forward() {
        getDriver().navigate().forward();
    }

    /**
     * @param cookie Specify cookie
     *               Add cookie in browser
     */
    @JDIAction
    public void addCookie(Cookie cookie) {
        getDriver().manage().addCookie(cookie);
    }

    /**
     * Clear browsers cache
     */
    @JDIAction("Delete all cookies")
    public void clearCache() {
        getDriver().manage().deleteAllCookies();
    }

    /**
     * Zooms
     * @param factor factor
     */
    @JDIAction
    public static void zoom(double factor) {
        WebDriverFactory.jsExecute("document.body.style.transform = 'scale(' + arguments[0] + ')';" +
                "document.body.style.transformOrigin = '0 0';", factor);
    }

    /**
     * Gets HTML
     * @return Page source HTML
     */
    @JDIAction
    public static String getHtml() {
        return WebDriverFactory.getDriver().getPageSource();
    }

    /**
     * Scrolls to coordinates
     * @param x X coordinate
     * @param y Y coordinate
     */
    private static void scroll(int x, int y) {
        WebDriverFactory.jsExecute("window.scrollBy("+x+","+y+")");
    }

    /**
     * Scrolls down
     * @param value value
     */
    @JDIAction
    public static void  scrollDown(int value) {
        scroll(0,value);
    }

    /**
     * Scrolls up
     * @param value value
     */
    @JDIAction
    public static void  scrollUp(int value) {
        scroll(0,-value);
    }

    /**
     * Scrolls right
     * @param value value
     */
    @JDIAction
    public static void  scrollRight(int value) {
        scroll(value,0);
    }

    /**
     * Scrolls left
     * @param value left
     */
    @JDIAction
    public static void scrollLeft(int value) {
        scroll(-value,0);
    }
    private static MapArray<String, WebPage> pages = new MapArray<>();

    /**
     * Adds page
     * @param page page
     */
    public static void addPage(WebPage page) {
        pages.update(page.getName(), page);
    }

    /**
     * Gets page
     * @param name name
     * @param <T> type
     * @return WebPage
     */
    public static <T extends WebPage> T getPage(String name) {
        WebPage page = pages.get(name);
        return (T) (page == null ? pages.get(name + " Page") : page);
    }

    /**
     * Returns page name
     * @return name
     */
    @Override
    public String toString() {
        String result = getName();
        if (!logger.getLogLevel().equalOrMoreThan(STEP))
            result += format(" (url=%s, title=%s)", url, title);
        return result;
    }

    /**
     * Utility to check page's url and title
     */
    public class StringCheckType {
        private Supplier<String> actual;
        private String equals;
        private String template;
        private String what;

        StringCheckType(Supplier<String> actual, String equals, String template, String what) {
            this.actual = actual;
            this.equals = equals;
            this.template = template;
            this.what = what;
        }

        /**
         * Check that current page url/title equals to expected url/title
         */
        @JDIAction
        public boolean check() {
            logger.info(format("Check that page %s equals to '%s'", what, equals));
            return equals == null
                || equals.equals("")
                || actual.get().equals(equals);
        }

        /**
         * Check that current page url/title matches to expected url/title-matcher
         */
        @JDIAction
        public boolean match() {
            logger.info(format("Check that page %s matches to '%s'", what, template));
            return template == null
                || template.equals("")
                || actual.get().matches(template);
        }

        /**
         * Check that current page url/title contains expected url/title-matcher
         */
        @JDIAction
        public boolean contains() {
            logger.info(format("Check that page %s contains to '%s'", what, template));
            return template == null
                    || template.equals("")
                    || actual.get().contains(template);
        }
    }
}