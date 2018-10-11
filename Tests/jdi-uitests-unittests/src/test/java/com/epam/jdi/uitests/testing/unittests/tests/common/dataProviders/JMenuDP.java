package com.epam.jdi.uitests.testing.unittests.tests.common.dataProviders;

import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.jdi.uitests.testing.unittests.pageobjects.EpamJDISite.homePage;

public class JMenuDP {


    public static List<String> listMenu = asList("HOME", "CONTACT FORM", "SERVICE", "METALS & COLORS");

    public static String optionsAsText = "HOME\n" +
        "CONTACT FORM\n" +
        "SERVICE\n" +
        "METALS & COLORS";

    @DataProvider(name = "menuData")
    public static Object[][] inputData() {
        return new Object[][]{
                {homePage.menu, listMenu, optionsAsText}
        };
    }
}
