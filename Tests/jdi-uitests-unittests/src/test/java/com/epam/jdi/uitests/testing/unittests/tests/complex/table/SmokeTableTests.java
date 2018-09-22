package com.epam.jdi.uitests.testing.unittests.tests.complex.table;

import com.epam.jdi.uitests.testing.unittests.tests.complex.table.base.SupportTableTestsBase;
import com.epam.matcher.testng.TestNG;
import org.testng.annotations.Test;

import static com.epam.jdi.tools.PrintUtils.print;
import static com.epam.matcher.testng.Assert.isFalse;

/**
 * Created by Natalia_Grebenshchik on 10/21/2015.
 */
public class SmokeTableTests extends SupportTableTestsBase {

    private static final String tableAsText = "||X||Type|Now|Plans||\n" +
            "||1||Drivers|Selenium, Custom|JavaScript, Appium, WinAPI, Sikuli||\n" +
            "||2||Test Runner|TestNG, JUnit, Custom|MSTest, NUnit, Epam||\n" +
            "||3||Asserter|TestNG, JUnit, Custom|MSTest, NUnit, Epam||\n" +
            "||4||Logger|Log4J, TestNG log, Custom|Epam, XML/Json logging, Hyper logging||\n" +
            "||5||Reporter|Jenkins, Allure, Custom|EPAM Report portal, Serenity, TimCity, Hudson||\n" +
            "||6||BDD/DSL|Custom|Cucumber, Jbehave, Thucydides, SpecFlow||";

    @Test
    public void getValueTest() {
        new TestNG("Table print").areEquals(table().getValue(), tableAsText);
    }

    @Test
    public void getTextTest() {
        new TestNG("Table print").areEquals(table().getText(), tableAsText);
    }

    @Test
    public void tableDimensionTest() {
        new TestNG("Dimensions").areEquals("3/6", table().columns().count() + "/" + table().rows().count());
    }

    @Test
    public void tableColumnHeadersTest() {
        new TestNG("Columns headers").areEquals("Type, Now, Plans", print(table().columns().headers()));
    }

    @Test
    public void tableHeadersTest() {
        new TestNG("Table header").areEquals("Type, Now, Plans", print(table().headers()));
    }

    @Test
    public void tableHeadersAsTextTest() {
        new TestNG("Table header as text").areEquals("Type, Now, Plans", print(table().header().select((name, value) -> value.getText())));
    }

    @Test
    public void tableRowHeadersTest() {
        new TestNG("Rows header").areEquals("1, 2, 3, 4, 5, 6", print(table().rows().headers()));
    }

    @Test
    public void tableIsNotEmptyTest() {
        isFalse(table().isEmpty());
    }
}
