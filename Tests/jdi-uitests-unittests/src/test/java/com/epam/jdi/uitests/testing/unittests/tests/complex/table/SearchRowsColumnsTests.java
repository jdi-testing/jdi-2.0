package com.epam.jdi.uitests.testing.unittests.tests.complex.table;

import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ICell;
import com.epam.jdi.uitests.core.interfaces.complex.tables.TableLines;
import com.epam.jdi.uitests.testing.unittests.tests.complex.table.base.SupportTableTestsBase;
import com.epam.matcher.testng.TestNG;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.epam.jdi.tools.PrintUtils.print;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.Column.inColumn;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.Row.inRow;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.Row.row;
import static com.epam.jdi.uitests.core.utils.common.Filters.equalsTo;
import static com.epam.matcher.testng.Assert.areEquals;
import static java.lang.String.format;

/**
 * Created by Natalia_Grebenshchikova on 10/21/2015.
 */
public class SearchRowsColumnsTests extends SupportTableTestsBase {

    private static final String expectedColumn =
            "1:Selenium, Custom, " +
                    "2:TestNG, JUnit, Custom, " +
                    "3:TestNG, JUnit, Custom, " +
                    "4:Log4J, TestNG log, Custom, " +
                    "5:Jenkins, Allure, Custom, " +
                    "6:Custom";
    private final static String expectedRow =
            "Type:Asserter, " +
                    "Now:TestNG, JUnit, Custom, " +
                    "Plans:MSTest, NUnit, Epam";

    @Test
    public void columnByNumTest() {
        MapArray<String, ICell> column = table().column(2);
        areEquals(print(column.select(
                        (rowK, rowV) -> format("%s:%s", rowK, rowV.getText()))),
                "1:Selenium, Custom, " +
                        "2:TestNG, JUnit, Custom, " +
                        "3:TestNG, JUnit, Custom, " +
                        "4:Log4J, TestNG log, Custom, " +
                        "5:Jenkins, Allure, Custom, " +
                        "6:Custom");
    }

    @Test
    public void columnByNameTest() {
        MapArray<String, ICell> column = table().column("Now");
        areEquals(print(column.select(
                        (rowK, rowV) -> format("%s:%s", rowK, rowV.getText()))),
                "1:Selenium, Custom, " +
                        "2:TestNG, JUnit, Custom, " +
                        "3:TestNG, JUnit, Custom, " +
                        "4:Log4J, TestNG log, Custom, " +
                        "5:Jenkins, Allure, Custom, " +
                        "6:Custom");
    }

    @Test
    public void rowByNumTest() {
        MapArray<String, ICell> row = table().row(2);
        areEquals(print(row.select(
                        (rowK, rowV) -> format("%s:%s", rowK, rowV.getText()))),
                "Type:Test Runner, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam");
    }

    @Test
    public void rowByNameTest() {
        MapArray<String, ICell> row = table().row("2");
        areEquals(print(row.select(
                        (rowK, rowV) -> format("%s:%s", rowK, rowV.getText()))),
                "Type:Test Runner, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam");
    }

    @Test
    public void columnByCriteriaIntTest() {
        MapArray<String, ICell> column = table().column(equalsTo("TestNG, JUnit, Custom"), row(3));
        areEquals(print(column.select(
                        (rowK, rowV) -> format("%s:%s", rowK, rowV.getText()))),
                "1:Selenium, Custom, " +
                        "2:TestNG, JUnit, Custom, " +
                        "3:TestNG, JUnit, Custom, " +
                        "4:Log4J, TestNG log, Custom, " +
                        "5:Jenkins, Allure, Custom, " +
                        "6:Custom");
    }

    @Test
    public void columnByCriteriaStringTest() {
        MapArray<String, ICell> column = table().column(equalsTo("TestNG, JUnit, Custom"), inRow("3"));
        areEquals(print(column.select(
                        (rowK, rowV) -> format("%s:%s", rowK, rowV.getText()))),
                "1:Selenium, Custom, " +
                        "2:TestNG, JUnit, Custom, " +
                        "3:TestNG, JUnit, Custom, " +
                        "4:Log4J, TestNG log, Custom, " +
                        "5:Jenkins, Allure, Custom, " +
                        "6:Custom");
    }

    @Test
    public void rowByCriteriaIntTest() {
        MapArray<String, ICell> row = table().row(equalsTo("MSTest, NUnit, Epam"), inColumn(3));
        areEquals(print(row.select(
                        (rowK, rowV) -> format("%s:%s", rowK, rowV.getText()))),
                "Type:Test Runner, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam");
    }

    @Test
    public void rowByCriteriaStringTest() {
        MapArray<String, ICell> row = table().row(equalsTo("MSTest, NUnit, Epam"), inColumn("Plans"));
        areEquals(print(row.select(
                        (rowK, rowV) -> format("%s:%s", rowK, rowV.getText()))),
                "Type:Test Runner, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam");
    }

    @Test
    public void rowsByCriteriaTest() {
        TableLines rows = table().rows("Plans=MSTest, NUnit, Epam");
        new TestNG("Rows count").areEquals(rows.size(), 2);
        new TestNG("Rows content").areEquals(print(rows.select(
                        (k, v) -> format("%s:%s", k, v.select(
                                (rowK, rowV) -> format("%s:%s", rowK, rowV.getText()))))),
                "2:[Type:Test Runner, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam], " +
                        "3:[Type:Asserter, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam]");
    }

    @Test
    public void rowsByTwoCriteriasTest() {
        TableLines rows = table().rows("Plans=MSTest, NUnit, Epam", "Type=Asserter");
        new TestNG("Rows count").areEquals(rows.size(), 1);
        new TestNG("Rows content").areEquals(print(rows.select(
                        (k, v) -> format("%s:%s", k, v.select(
                                (rowK, rowV) -> format("%s:%s", rowK, rowV.getText()))))),
                "3:[Type:Asserter, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam]");
    }

    @Test
    public void columnsByCriteriaTest() {
        TableLines columns = table().columns("1=Selenium, Custom");
        new TestNG("Columns count").areEquals(columns.size(), 1);
        new TestNG("Columns content").areEquals(print(columns.select(
                        (k, v) -> format("%s:%s", k, v.select(
                                (rowK, rowV) -> format("%s:%s", rowK, rowV.getText()))))),
                "Now:[1:Selenium, Custom, " +
                        "2:TestNG, JUnit, Custom, " +
                        "3:TestNG, JUnit, Custom, " +
                        "4:Log4J, TestNG log, Custom, " +
                        "5:Jenkins, Allure, Custom, " +
                        "6:Custom]");
    }

    @Test
    public void columnsByTwoCriteriasTest() {
        TableLines columns = table().columns("2=Test Runner", "4=Logger");
        new TestNG("Columns count").areEquals(columns.size(), 1);
        new TestNG("Columns content").areEquals(print(columns.select(
                        (k, v) -> format("%s:%s", k, v.select(
                                (rowK, rowV) -> format("%s:%s", rowK, rowV.getText()))))),
                "Type:[1:Drivers, " +
                        "2:Test Runner, " +
                        "3:Asserter, " +
                        "4:Logger, " +
                        "5:Reporter, " +
                        "6:BDD/DSL]");
    }

    @Test
    public void columnsGetTest() {
        TableLines columns = table().columns().getAll();
        new TestNG("Columns count").areEquals(columns.size(), 3);

        new TestNG("Columns content").areEquals(print(columns.select(
                        (k, v) -> format("%s:%s", k, v.select(
                                (rowK, rowV) -> format("%s:%s", rowK, rowV.getText()))))),
                "Type:[1:Drivers, " +
                        "2:Test Runner, " +
                        "3:Asserter, " +
                        "4:Logger, " +
                        "5:Reporter, " +
                        "6:BDD/DSL], " +
                        "Now:[1:Selenium, Custom, " +
                        "2:TestNG, JUnit, Custom, " +
                        "3:TestNG, JUnit, Custom, " +
                        "4:Log4J, TestNG log, Custom, " +
                        "5:Jenkins, Allure, Custom, " +
                        "6:Custom], " +
                        "Plans:[1:JavaScript, Appium, WinAPI, Sikuli, " +
                        "2:MSTest, NUnit, Epam, " +
                        "3:MSTest, NUnit, Epam, " +
                        "4:Epam, XML/Json logging, Hyper logging, " +
                        "5:EPAM Report portal, Serenity, TimCity, Hudson, " +
                        "6:Cucumber, Jbehave, Thucydides, SpecFlow]");
    }

    @Test
    public void columnsGetAsTextTest() {
        TableLines columns = table().columns().getAll();
        new TestNG("Columns count").areEquals(columns.size(), 3);
        new TestNG("Columns content").areEquals(print(columns.select(
                        (k, v) -> format("%s:%s", k, v.select(
                                (rowK, rowV) -> format("%s:%s", rowK, rowV))))),
                "Type:[1:Drivers, " +
                        "2:Test Runner, " +
                        "3:Asserter, " +
                        "4:Logger, " +
                        "5:Reporter, " +
                        "6:BDD/DSL], " +
                        "Now:[1:Selenium, Custom, " +
                        "2:TestNG, JUnit, Custom, " +
                        "3:TestNG, JUnit, Custom, " +
                        "4:Log4J, TestNG log, Custom, " +
                        "5:Jenkins, Allure, Custom, " +
                        "6:Custom], " +
                        "Plans:[1:JavaScript, Appium, WinAPI, Sikuli, " +
                        "2:MSTest, NUnit, Epam, " +
                        "3:MSTest, NUnit, Epam, " +
                        "4:Epam, XML/Json logging, Hyper logging, " +
                        "5:EPAM Report portal, Serenity, TimCity, Hudson, " +
                        "6:Cucumber, Jbehave, Thucydides, SpecFlow]");
    }

    @Test
    public void columnsGetByNumTest() {
        MapArray<String, ICell> column = table().columns().get(2);
        new TestNG("Column content").areEquals(print(column.select(
                (rowK, rowV) -> format("%s:%s", rowK, rowV.getValue()))), expectedColumn);
    }

    @Test
    public void columnsGetByNameTest() {
        MapArray<String, ICell> column = table().columns().get("Now");
        new TestNG("Column content").areEquals(print(column.select(
                (rowK, rowV) -> format("%s:%s", rowK, rowV.getValue()))), expectedColumn);
    }

    @Test
    public void columnsGetByNumAsTextTest() {
        MapArray<String, String> column = table().columns().getAsText(2);
        new TestNG("Column content").areEquals(print(column.select(
                (rowK, rowV) -> format("%s:%s", rowK, rowV))), expectedColumn);
    }

    @Test
    public void columnsGetByNameAsTextTest() {
        MapArray<String, String> column = table().columns().getAsText("Now");
        new TestNG("Column content").areEquals(print(column.select(
                (rowK, rowV) -> format("%s:%s", rowK, rowV))), expectedColumn);
    }

    @Test
    public void rowsGetTest() {
        TableLines rows = table().rows().getAll();
        new TestNG("Rows count").areEquals(rows.size(), 6);
        new TestNG("Rows content").areEquals(print(rows.select(
                        (k, v) -> format("%s:%s", k, v.select(
                                (rowK, rowV) -> format("%s:%s", rowK, rowV.getText()))))),
                "1:[Type:Drivers, " +
                        "Now:Selenium, Custom, " +
                        "Plans:JavaScript, Appium, WinAPI, Sikuli], " +
                        "2:[Type:Test Runner, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam], " +
                        "3:[Type:Asserter, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam], " +
                        "4:[Type:Logger, " +
                        "Now:Log4J, TestNG log, Custom, " +
                        "Plans:Epam, XML/Json logging, Hyper logging], " +
                        "5:[Type:Reporter, " +
                        "Now:Jenkins, Allure, Custom, " +
                        "Plans:EPAM Report portal, Serenity, TimCity, Hudson], " +
                        "6:[Type:BDD/DSL, " +
                        "Now:Custom, " +
                        "Plans:Cucumber, Jbehave, Thucydides, SpecFlow]");
    }

    @Test
    public void rowsGetAsTextTest() {
        TableLines rows = table().rows().getAll();
        new TestNG("Rows count").areEquals(rows.size(), 6);
        new TestNG("Rows content").areEquals(print(rows.select(
                        (k, v) -> format("%s:%s", k, v.select(
                                (rowK, rowV) -> format("%s:%s", rowK, rowV))))),
                "1:[Type:Drivers, " +
                        "Now:Selenium, Custom, " +
                        "Plans:JavaScript, Appium, WinAPI, Sikuli], " +
                        "2:[Type:Test Runner, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam], " +
                        "3:[Type:Asserter, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam], " +
                        "4:[Type:Logger, " +
                        "Now:Log4J, TestNG log, Custom, " +
                        "Plans:Epam, XML/Json logging, Hyper logging], " +
                        "5:[Type:Reporter, " +
                        "Now:Jenkins, Allure, Custom, " +
                        "Plans:EPAM Report portal, Serenity, TimCity, Hudson], " +
                        "6:[Type:BDD/DSL, " +
                        "Now:Custom, " +
                        "Plans:Cucumber, Jbehave, Thucydides, SpecFlow]");
    }

    @Test
    public void rowsGetByNumTest() {
        MapArray<String, ICell> row = table().rows().get(3);
        new TestNG("Row content").areEquals(print(row.select(
                (rowK, rowV) -> format("%s:%s", rowK, rowV.getValue()))), expectedRow);
    }

    @Test
    public void rowsGetByNameTest() {
        MapArray<String, ICell> row = table().rows().get("3");
        new TestNG("Row content").areEquals(print(row.select(
                (rowK, rowV) -> format("%s:%s", rowK, rowV.getValue()))), expectedRow);
    }

    @Test
    public void rowsGetByNumAsTextTest() {
        MapArray<String, String> row = table().rows().getAsText(3);
        new TestNG("Row content").areEquals(print(row.select(
                (rowK, rowV) -> format("%s:%s", rowK, rowV))), expectedRow);
    }

    @Test
    public void rowsGetByNameAsTextTest() {
        MapArray<String, String> row = table().rows().getAsText("3");
        new TestNG("Row content").areEquals(print(row.select(
                (rowK, rowV) -> format("%s:%s", rowK, rowV))), expectedRow);
    }

    @Test
    public void rowValueByNameTests() {
        new TestNG("Row Value").areEquals(table().row("2"), Arrays.asList("Test Runner", "TestNG, JUnit, Custom", "MSTest, NUnit, Epam"));
    }

    @Test
    public void rowValueByNumberTests() {
        new TestNG("Row Value").areEquals(table().row(2), Arrays.asList("Test Runner", "TestNG, JUnit, Custom", "MSTest, NUnit, Epam"));
    }

    @Test
    public void columnValueByNameTests() {
        new TestNG("Column Value").areEquals(table().column("Now"), Arrays.asList("Selenium, Custom", "TestNG, JUnit, Custom", "TestNG, JUnit, Custom", "Log4J, TestNG log, Custom", "Jenkins, Allure, Custom", "Custom"));
    }

    @Test
    public void columnValueByNumberTests() {
        new TestNG("Column Value").areEquals(table().column(2), Arrays.asList("Selenium, Custom", "TestNG, JUnit, Custom", "TestNG, JUnit, Custom", "Log4J, TestNG log, Custom", "Jenkins, Allure, Custom", "Custom"));
    }

}
