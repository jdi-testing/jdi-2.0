package com.epam.jdi.uitests.web.selenium.preconditions;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.preconditions.IPreconditions;

import static com.epam.jdi.uitests.web.selenium.driver.WebDriverFactory.getDriver;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.domain;

public interface WebPreconditions extends IPreconditions {
    static boolean checkUrl(String uri) {
        return getDriver().getCurrentUrl().matches(".*/" + uri + "(\\?.*)?");
    }

    static void openUri(String uri) {
        getDriver().navigate().to(getUrlByUri(uri));
    }

    static String getUrlByUri(String uri) {
        return domain.replaceAll("/*$", "") + "/" + uri.replaceAll("^/*", "");
    }

    default void open() {
        moveToAction();
    }
}