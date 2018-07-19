package com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.AnnotationsUtil;
import com.epam.jdi.uitests.core.interfaces.complex.tables.CheckTypes;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.simple.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Quotes;

import java.util.function.Consumer;

import static com.epam.jdi.tools.StringUtils.format;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.CheckTypes.CONTAINS;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.CheckTypes.EQUAL;
import static com.epam.jdi.uitests.web.selenium.settings.WebSettings.domain;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class WebAnnotationsUtil extends AnnotationsUtil {

    public static String getUrlFromUri(String uri, Class<?> parentClass) {
        String siteDomain = domain;
        if (parentClass != null && parentClass.isAnnotationPresent(JSite.class)) {
            siteDomain = parentClass.getAnnotation(JSite.class).value();
            if (isBlank(siteDomain))
                siteDomain = domain;
            else domain = siteDomain;
        }
        if (isBlank(siteDomain)) return "";
        if (isBlank(uri)) return siteDomain;
        return siteDomain.replaceAll("/*$", "") + "/" + uri.replaceAll("^/*", "");
    }

    public static void fillPageFromAnnotaiton(WebPage page, JPage pageAnnotation, Class<?> parentClass) {
        String url = !isBlank(pageAnnotation.value())
                ? pageAnnotation.value()
                : pageAnnotation.url();
        if (!url.contains("://"))
            url = getUrlFromUri(url, parentClass);
        String title = pageAnnotation.title();
        String urlTemplate = pageAnnotation.urlTemplate();
        CheckTypes urlCheckType = pageAnnotation.urlCheckType();
        CheckTypes titleCheckType = pageAnnotation.titleCheckType();
        if (urlCheckType == EQUAL || urlCheckType == CONTAINS && urlTemplate.equals(""))
            urlTemplate = url;
        page.updatePageData(url, title, urlCheckType, titleCheckType, urlTemplate);
    }

    public static By getFrame(Frame frame) {
        if (frame == null) return null;

        if (!"".equals(frame.xpath()))
            return By.xpath(frame.xpath());
        if (!"".equals(frame.css()))
            return By.cssSelector(frame.css());
        if (!"".equals(frame.linkText()))
            return By.linkText(frame.linkText());
        if (!"".equals(frame.partialLinkText()))
            return By.partialLinkText(frame.partialLinkText());
        if (!"".equals(frame.tagName()))
            return By.tagName(frame.tagName());

        if (!"".equals(frame.text()))
            return By.xpath(".//*/text()[normalize-space(.) = " +
                    Quotes.escape(frame.text()) + "]/parent::*");

        if (!"".equals(frame.id()))
            return By.id(frame.id());
        if (!"".equals(frame.className()))
            return By.className(frame.className());
        if (!"".equals(frame.name()))
            return By.name(frame.name());
        if (!"".equals(frame.value()))
            return getAttribute("value", frame.value());
        if (!"".equals(frame.title()))
            return getAttribute("title", frame.title());

        if (!"".equals(frame.model()))
            return By.cssSelector(format("[ng-model='%s']", frame.model()));
        if (!"".equals(frame.binding()))
            return By.cssSelector(format("[ng-binding='%s']", frame.binding()));
        if (!"".equals(frame.repeat()))
            return By.cssSelector(format("[ng-repeat='%s']", frame.repeat()));

        return null;
    }

    public static By findByToBy(org.openqa.selenium.support.FindBy locator) {
        if (locator == null) return null;
        if (!locator.id().isEmpty())
            return By.id(locator.id());
        if (!locator.className().isEmpty())
            return By.className(locator.className());
        if (!locator.xpath().isEmpty())
            return By.xpath(locator.xpath());
        if (!locator.css().isEmpty())
            return By.cssSelector(locator.css());
        if (!locator.linkText().isEmpty())
            return By.linkText(locator.linkText());
        if (!locator.name().isEmpty())
            return By.name(locator.name());
        if (!locator.partialLinkText().isEmpty())
            return By.partialLinkText(locator.partialLinkText());
        if (!locator.tagName().isEmpty())
            return By.tagName(locator.tagName());
        return null;
    }

    public static void fillLocator(org.openqa.selenium.support.FindBy value, Consumer<By> action) {
        By by = findByToBy(value);
        if (by != null)
            action.accept(by);
    }

    public static By findByToBy(FindBy locator) {
        if (locator == null) return null;

        if (!"".equals(locator.xpath()))
            return By.xpath(locator.xpath());
        if (!"".equals(locator.css()))
            return By.cssSelector(locator.css());
        if (!"".equals(locator.linkText()))
            return By.linkText(locator.linkText());
        if (!"".equals(locator.partialLinkText()))
            return By.partialLinkText(locator.partialLinkText());
        if (!"".equals(locator.tagName()))
            return By.tagName(locator.tagName());

        if (!"".equals(locator.text()))
            return By.xpath(".//*/text()[normalize-space(.) = " +
                Quotes.escape(locator.text()) + "]/parent::*");

        if (!"".equals(locator.id()))
            return By.id(locator.id());
        if (!"".equals(locator.className()))
            return By.className(locator.className());
        if (!"".equals(locator.name()))
            return By.name(locator.name());
        if (!"".equals(locator.value()))
            return getAttribute("value", locator.value());
        if (!"".equals(locator.title()))
            return getAttribute("title", locator.title());

        if (!"".equals(locator.model()))
            return By.cssSelector(format("[ng-model='%s']", locator.model()));
        if (!"".equals(locator.binding()))
            return By.cssSelector(format("[ng-binding='%s']", locator.binding()));
        if (!"".equals(locator.repeat()))
            return By.cssSelector(format("[ng-repeat='%s']", locator.repeat()));

        return null;
    }

    private static By getAttribute(String name, String value) {
        return By.xpath(".//*[@" + name + '=' + Quotes.escape(value) + ']');
    }

    public static By findByToBy(Css locator){
        if (locator == null) return null;
        return By.cssSelector(locator.value());
    }

    public static By findByToBy(ById locator){
        if (locator == null) return null;
        return By.id(locator.value());
    }

    public static By findByToBy(ByText locator){
        if (locator == null) return null;
        return By.xpath(".//*/text()[normalize-space(.) = " +
                Quotes.escape(locator.value()) + "]/parent::*");
    }

    public static By findByToBy(WithText locator){
        if (locator == null) return null;
        return By.xpath(".//*/text()[contains(normalize-space(.), "+
                Quotes.escape(locator.value())+")]/parent::*");
    }

    public static By findByToBy(XPath locator){
        if (locator == null) return null;
        return By.xpath(locator.value());
    }
}