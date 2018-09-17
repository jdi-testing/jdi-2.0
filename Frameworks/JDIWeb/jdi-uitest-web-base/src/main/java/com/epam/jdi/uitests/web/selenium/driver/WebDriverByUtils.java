package com.epam.jdi.uitests.web.selenium.driver;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.uitests.web.selenium.exceptions.LocatorException;
import org.openqa.selenium.By;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.jdi.tools.LinqUtils.first;
import static com.epam.jdi.tools.LinqUtils.select;
import static com.epam.jdi.tools.PrintUtils.print;
import static java.lang.String.format;

public final class WebDriverByUtils {

    private static MapArray<String, String> byReplace = new MapArray<>(new Object[][] {
            {"cssSelector", "css"},
            {"tagName", "tag"},
            {"className", "class"}
    });


    private WebDriverByUtils() { }

    /**
     * Returns function
     * @param by - by
     * @return Function
     */
    public static Function<String, By> getByFunc(By by) {
        return first(getMapByTypes(), key -> by.toString().contains(key));
    }

    /**
     * Creates message for bad locator
     * @param byLocator - locator
     * @param args - args
     * @return String - message for bad locator
     */
    private static String getBadLocatorMsg(String byLocator, Object... args) {
        return "Bad locator template '" + byLocator + "'. Args: " + print(select(args, Object::toString), ", ", "'%s'") + ".";
    }

    /**
     * Fills by template
     * @param by - by
     * @param args - args
     * @return By - locator
     */
    public static By fillByTemplate(By by, Object... args) {
        String byLocator = getByLocator(by);
        if (!byLocator.contains("%"))
            throw new LocatorException(getBadLocatorMsg(byLocator, args));
        try {
            byLocator = format(byLocator, args);
        } catch (Exception ex) {
            throw new LocatorException(getBadLocatorMsg(byLocator, args));
        }
        return getByFunc(by).apply(byLocator);
    }

    /**
     * Checks if locator contains 'root'
     * @param by - locator
     * @return boolean
     */
    public static boolean containsRoot(By by) {
        return by != null && by.toString().contains(": *root*");
    }

    /**
     * Trims root
     * @param by - locator
     * @return By - trimmed locator
     */
    public static By trimRoot(By by) {
        String byLocator = getByLocator(by).replace("*root*", " ").trim();
        return getByFunc(by).apply(byLocator);
    }

    /**
     * Fills locator with message
     * @param by - locator
     * @param args - args
     * @return By - filled locator
     */
    public static By fillByMsgTemplate(By by, Object... args) {
        String byLocator = getByLocator(by);
        try {
            byLocator = MessageFormat.format(byLocator, args);
        } catch (Exception ex) {
            throw new LocatorException(getBadLocatorMsg(byLocator, args));
        }
        return getByFunc(by).apply(byLocator);
    }

    /**
     * Copies locator
     * @param by - locator
     * @return By - copied locator
     */
    public static By copyBy(By by) {
        String byLocator = getByLocator(by);
        return getByFunc(by).apply(byLocator);
    }

    /**
     * Returns locator as string
     * @param by - locator
     * @return String
     */
    public static String getByLocator(By by) {
        String byAsString = by.toString();
        int index = byAsString.indexOf(": ") + 2;
        return byAsString.substring(index);
    }

    /**
     *  Returns locator as String by it's name
     * @param by - locator
     * @return String
     */
    public static String getByName(By by) {
        Matcher m = Pattern.compile("By\\.(?<locator>.*):.*").matcher(by.toString());
        if (m.find()) {
            String result = m.group("locator");
            return byReplace.keys().contains(result) ? byReplace.get(result) : result;
        }
        throw new LocatorException("Can't get By name for: " + by);
    }

    /**
     * Corrects xpath
     * @param byValue - locator
     * @return By - corrected locator
     */
    public static By correctXPaths(By byValue) {
        return byValue.toString().contains("By.xpath: //")
                ? getByFunc(byValue).apply(getByLocator(byValue)
                .replaceFirst("/", "./"))
                : byValue;
    }

    /**
     * Returns shorter locator
     * @param by - locator
     * @return String
     */
    public static String shortBy(By by) {
        return by == null
                ? "No locator"
                : format("%s='%s'", getByName(by), getByLocator(by));
    }

    /**
     * Gets locator from string
     * @param stringLocator
     * @return By
     */
    public static By getByFromString(String stringLocator) {
        if (stringLocator == null || stringLocator.equals(""))
            throw new LocatorException("Can't get By locator from string empty or null string");
        String[] split = stringLocator.split("(^=)*=.*");
        if (split.length == 1)
            return By.cssSelector(split[0]);
        switch (split[0]) {
            case "css": return By.cssSelector(split[1]);
            case "xpath": return By.xpath(split[1]);
            case "class": return By.className(split[1]);
            case "name": return By.name(split[1]);
            case "id": return By.id(split[1]);
            case "tag": return By.tagName(split[1]);
            case "link": return By.partialLinkText(split[1]);
            default: throw new LocatorException(
                    String.format("Can't get By locator from string: %s. Bad suffix: %s. (available: css, xpath, class, id, name, link, tag)",
                            stringLocator, split[0]));
        }
    }

    /**
     * Returns map with locators
     * @return Map<String, Function<String, By>>
     */
    private static Map<String, Function<String, By>> getMapByTypes() {
        Map<String, Function<String, By>> map = new HashMap<>();
        map.put("By.cssSelector", By::cssSelector);
        map.put("By.className", By::className);
        map.put("By.id", By::id);
        map.put("By.linkText", By::linkText);
        map.put("By.name", By::name);
        map.put("By.partialLinkText", By::partialLinkText);
        map.put("By.tagName", By::tagName);
        map.put("By.xpath", By::xpath);
        return map;
    }
}