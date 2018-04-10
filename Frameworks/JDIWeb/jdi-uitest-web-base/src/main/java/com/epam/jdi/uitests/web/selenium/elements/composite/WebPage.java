package com.epam.jdi.uitests.web.selenium.elements.composite;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.CacheValue;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.complex.tables.CheckTypes;
import com.epam.jdi.uitests.core.interfaces.composite.IPage;
import com.epam.jdi.uitests.web.selenium.elements.base.BaseElement;
import com.epam.jdi.uitests.web.selenium.settings.WebSettings;
import org.openqa.selenium.Cookie;

import java.util.function.Supplier;

import static com.epam.jdi.tools.LinqUtils.Switch;
import static com.epam.jdi.tools.Switch.Else;
import static com.epam.jdi.tools.Switch.Value;
import static com.epam.jdi.tools.logger.LogLevels.STEP;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.CheckTypes.*;
import static com.epam.jdi.uitests.core.settings.JDISettings.*;
import static java.lang.String.format;

public class WebPage extends BaseElement implements IPage {
    public static boolean checkAfterOpen = false;
    public String url;
    public String title;
    public CheckTypes checkUrlType = EQUAL;
    public CheckTypes checkTitleType = EQUAL;
    public String urlTemplate;
    public static WebPage currentPage;

    public WebPage() {
    }

    public WebPage(String url) {
        this.url = url;
    }

    public WebPage(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public static void openUrl(String url) {
        new WebPage(url).open();
    }

    public static String getUrl() {
        return WebSettings.getDriver().getCurrentUrl();
    }

    public static String getTitle() {
        return WebSettings.getDriver().getTitle();
    }

    public void updatePageData(String url, String title, CheckTypes checkUrlType, CheckTypes checkTitleType, String urlTemplate) {
        if (this.url == null)
            this.url = url;
        if (this.title == null)
            this.title = title;
        this.checkUrlType = checkUrlType;
        this.checkTitleType = checkTitleType;
        this.urlTemplate = urlTemplate;
    }

    public StringCheckType url() {
        return new StringCheckType(getDriver()::getCurrentUrl, url, urlTemplate, "url");
    }

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
    @Override
    public boolean isOpened() {
        if (!WebSettings.getDriverFactory().hasRunDrivers())
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

    @Override
    public String toString() {
        String result = getName();
        if (!logger.getLogLevel().equalOrMoreThan(STEP))
            result += format(" (url=%s, title=%s)", url, title);
        return result;
    }

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