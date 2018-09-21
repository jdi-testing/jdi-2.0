package com.epam.jdi.uitests.web.selenium.preconditions;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.preconditions.IPreconditions;

import static com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory.getDriver;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.domain;

/**
 * Override of IPreconditions for Web
 */
public interface WebPreconditions extends IPreconditions {
    /**
     * Checks whether current URL matches specified value
     * @param uri URL to match
     * @return true if current URL matches specified value
     */
    static boolean checkUrl(String uri) {
        return getDriver().getCurrentUrl().matches(".*/" + uri + "(\\?.*)?");
    }

    /**
     * Openes specified URL
     * @param uri URL to open
     */
    static void openUri(String uri) {
        getDriver().navigate().to(getUrlByUri(uri));
    }

    /**
     * Returns URL from specified uri
     * @param uri uri
     * @return URL
     */
    static String getUrlByUri(String uri) {
        return domain.replaceAll("/*$", "") + "/" + uri.replaceAll("^/*", "");
    }

    /**
     * Moves to action
     */
    default void open() {
        moveToAction();
    }
}