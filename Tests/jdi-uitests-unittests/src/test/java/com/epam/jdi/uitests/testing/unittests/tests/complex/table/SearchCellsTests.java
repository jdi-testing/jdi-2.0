package com.epam.jdi.uitests.testing.unittests.tests.complex.table;

import com.epam.jdi.uitests.core.interfaces.complex.tables.ICell;
import com.epam.jdi.uitests.testing.unittests.tests.complex.table.base.SupportTableTestsBase;
import com.epam.matcher.testng.TestNG;
import org.testng.annotations.Test;

import java.util.List;

import static com.epam.jdi.uitests.core.interfaces.complex.tables.Column.column;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.Row.row;
import static com.epam.jdi.uitests.core.utils.common.Filters.equalsTo;
import static com.epam.jdi.uitests.core.utils.common.Filters.match;
import static com.epam.matcher.testng.Assert.areEquals;
import static java.lang.String.format;

/**
 * Created by Natalia_Grebenshchikova on 10/21/2015.
 */
public class SearchCellsTests extends SupportTableTestsBase {
    @Test
    public void cellsEqualsTest() {
        checkCells(table().cells(equalsTo("MSTest, NUnit, Epam")));
    }

    @Test
    public void cellsMatchTest() {
        checkCells(table().cells(match(".*Test, NUnit, Epam")));
    }

    @Test
    public void cellEqualsTest() {
        checkCell(table().cell(equalsTo("MSTest, NUnit, Epam")));
    }

    @Test
    public void cellMatchTest() {
        checkCell(table().cell(match(".*Test, NUnit, Epam")));
    }

    @Test
    public void cellInColumnNumEqualsTest() {
        checkCell(table().cell(equalsTo("MSTest, NUnit, Epam"), column(3)));
    }

    @Test
    public void cellInColumnNameEqualsTest() {
        checkCell(table().cell(equalsTo("MSTest, NUnit, Epam"), column("Plans")));
    }

    @Test
    public void cellInRowNumEqualsTest() {
        checkCell(table().cell(equalsTo("MSTest, NUnit, Epam"), row(2)));
    }

    @Test
    public void cellInRowNameEqualsTest() {
        checkCell(table().cell(equalsTo("MSTest, NUnit, Epam"), row("2")));
    }

    @Test
    public void cellsMatchInColumnNumEqualsTest() {
        checkCells(table().cells(match(".*Test, NUnit, Epam"), column(3)));
    }

    @Test
    public void cellsMatchInColumnNameEqualsTest() {
        checkCells(table().cells(match(".*MSTest, NUnit, Epam"), column("Plans")));
    }

    @Test
    public void cellsMatchInRowNumEqualsTest() {
        List<ICell> cells = table().cells(match(".*MSTest, NUnit, Epam"), row(2));
        areEquals(cells.size(), 1);
        checkCell(cells.get(0));
    }

    @Test
    public void cellsMatchInRowNameEqualsTest() {
        List<ICell> cells = table().cells(match(".*MSTest, NUnit, Epam"), row("2"));
        areEquals(cells.size(), 1);
        checkCell(cells.get(0));
    }

    private void checkCell(ICell cell) {
        areEquals(format("Value: %s; Text: %s; %s/%s; %s/%s",
                cell.getValue(), cell.getText(), cell.column().name, cell.column().name,
                cell.column().num, cell.row().num), "Value: MSTest, NUnit, Epam; Text: MSTest, NUnit, Epam; Plans/2; 3/2");
    }

    private void checkCells(List<ICell> cells) {
        new TestNG("Cells size").areEquals(cells.size(), 2);
        new TestNG("Cell 1 coordinates").areEquals(format("Value: %s; %s/%s; %s/%s",
                cells.get(0).getValue(), cells.get(0).column().name, cells.get(0).row().name,
                cells.get(0).column().num, cells.get(0).row().num), "Value: MSTest, NUnit, Epam; Plans/2; 3/2");
        new TestNG("Cell 2 coordinates").areEquals(format("Value: %s; %s/%s; %s/%s",
                cells.get(1).getValue(), cells.get(1).column().name, cells.get(1).row().name,
                cells.get(1).column().num, cells.get(1).row().num), "Value: MSTest, NUnit, Epam; Plans/3; 3/3");
    }
}
