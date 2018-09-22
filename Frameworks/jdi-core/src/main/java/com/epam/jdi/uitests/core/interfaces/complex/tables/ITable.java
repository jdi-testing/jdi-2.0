package com.epam.jdi.uitests.core.interfaces.complex.tables;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IElement;
import com.epam.jdi.uitests.core.interfaces.common.IText;
import com.epam.jdi.uitests.core.utils.common.IFilter;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.*;
import static com.epam.jdi.uitests.core.logger.LogLevels.DEBUG;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.TableLines.filterTable;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.TableOfCells.indexCorrect;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static com.epam.jdi.uitests.core.settings.JDISettings.timeouts;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public interface ITable extends IText {
    /**
     * Get Cell by column/row index (Int) or name(String)
     */
    @JDIAction("Get cell({0}, {1})")
    default ICell cell(Column column, Row row) {
        return cell(row.getIndex(rows().headers()), column.getIndex(headers()));
    }

    @JDIAction("Get cell({0}, {1})")
    default ICell cell(String columnName, String rowName) {
        int y = rows().headers().indexOf(rowName);
        int x = headers().indexOf(columnName);
        if (x == -1 || y == -1)
            throw exception("Can't find cell with names (%s, %s). " +
                "Available row names '%s'. Available column names '%s'.",
                    rowName, columnName, rows().headers(), headers());
        return cell(x, y);
    }

    @JDIAction("Get cell({0}, {1})")
    default ICell cell(int columnIndex, int rowIndex) {
        indexCorrect(rowIndex, rows().count(), "row");
        indexCorrect(columnIndex, columns().count(), "column");
        return ((IElement)linked().get("cell")).getElement(rowIndex, columnIndex);
    }

    @JDIAction("Get cell({0}, {1})")
    default ICell cell(int columnIndex, String rowName) {
        int y = rows().headers().indexOf(rowName);
        indexCorrect(columnIndex, columns().count(), "column");
        if (y == -1)
            throw exception("Can't find cell with names (%s, %s). " +
                    "Available row names '%s'.",
                    rowName, columnIndex, rows().headers());
        return cell(y, columnIndex);
    }

    @JDIAction("Get cell({0}, {1})")
    default ICell cell(String columnName, int rowIndex) {
        indexCorrect(rowIndex, rows().count(), "row");
        int x = headers().indexOf(columnName);
        if (x == -1)
            throw exception("Can't find cell with names (%s, %s). " +
                    "Available column names '%s'.",
                    rowIndex, x, headers());
        return cell(rowIndex, x);
    }
    /**
     * Get all Cells with values equals to searched value
     */
    @JDIAction("Get all cells")
    List<ICell> allCells();
    /**
     * Get all Cells with values satisfied to filter
     */
    @JDIAction("Get cells matches")
    default List<ICell> cells(IFilter<String> filter) {
        return validation(() -> where(allCells(),
                cell -> filter.execute(cell.getText())));
    }
    <T> T validation(JFunc<T> results);
    /**
     * Get all Cells with values matches to searched in Row by index (Int) or name(String) <br>
     * e.g. cells(matchesRegEx(".*uccess.*"), row("Result")) <br>
     * or   cells(matchesRegEx(".*uccess.*"), row(5))
     */
    @JDIAction("Get cells in row {1}")
    default List<ICell> cells(IFilter<String> filter, Row row) {
        return validation(() -> where(rows().get(row.getIndex(rows().headers())).values(),
                cell -> filter.execute(cell.getText())));
    }

    /**
     * Get all Cells with values matches to searched in Column by index (Int) or name(String) <br>
     * e.g. cells(matchesRegEx("Roma.*"), column("Name")) <br>
     * or   cells(matchesRegEx("Roma.*"), column(3))
     */
    @JDIAction("Get cells in column {1}")
    default List<ICell> cells(IFilter<String> filter, Column column){
        return validation(() -> where(columns().get(column.getIndex(headers())).values(),
                cell -> filter.execute(cell.getText())));
    }
    /**
     * Get first Cell with values satisfied to filter
     */
    @JDIAction("Get first cell match")
    default ICell cell(IFilter<String> filter) {
        return validation(() -> first(allCells(),
                cell -> filter.execute(cell.getText())));
    }

    /**
     * Get first Cell with searched value in row by index (Int) or name(String)<br>
     * e.g. cell(equalsTo("100"), inRow("Total")) <br>
     * or   cell(equalsTo("100"), inRow(5))
     */
    @JDIAction("Get first cell match in row {1}")
    default ICell cell(IFilter<String> filter, Row row) {
        return validation(() -> first(rows().get(row.getIndex(rows().headers())).values(),
                cell -> filter.execute(cell.getText())));
    }

    /**
     * Get first Cell with searched value in column by index (Int) or name(String)<br>
     * e.g. cell(equalsTo("Roman"), inColumn("Name")) <br>
     * or   cell(equalsTo("Roman"), inColumn(3))
     */
    @JDIAction("Get first cell match in column {1}")
    default ICell cell(IFilter<String> filter, Column column) {
        return validation(() -> first(columns().get(column.getIndex(headers())).values(),
                cell -> filter.execute(cell.getText())));
    }

    /**
     * Searches Rows in table matches specified criteria colNameValues - list of search criteria in format columnName=columnValue<br>
     * = - Equals
     * ~= - Contains
     * *= - Match RegEx
     * e.g. rows("Name=Roman", "Profession=QA") <br>
     * e.g. rows("Name*=.* +*", "Profession~=Test") <br>
     * Each Row is map: columnName:cell
     */
    @JDIAction("Get rows using rules {0}")
    default TableLines rows(String... colNameValues) {
        return validation(() -> filterTable(colNameValues, this::getRows, "column"));
    }

    @JDIAction("Get rows matches in column {1}")
    default TableLines rows(IFilter<String> filter, Column column) {
        TableLine colLine = column(column.getIndex(columns().headers()));
        TableLines result = new TableLines();
        for (ICell cell : colLine.values())
            if (filter.execute(cell.getText()))
                result.add(cell.row().name, row(cell.row().name));
        return result;
    }
    /**
     * Searches Columns in table matches specified criteria rowNameValues - list of search criteria in format rowName=rowValue<br>
     * = - Equals
     * ~= - Contains
     * *= - Match RegEx
     * e.g. columns("Total=100", "Count=10") <br>
     * e.g. columns("Total*=\\d+", "Profession~=QA") <br>
     * Each Column is map: rowName:cell
     */
    @JDIAction("Get columns using rules {0}")
    default TableLines columns(String... rowNameValues) {
        return validation(() -> filterTable(rowNameValues, this::getColumns, "row"));
    }

    @JDIAction("Get columns matches in row {1}")
    default TableLines columns(IFilter<String> filter, Row row) {
        return validation(() -> {
            TableLine column = row(row.getIndex(rows().headers()));
            TableLines result = new TableLines();
            for (ICell cell : column.values())
                if (filter.execute(cell.getText())) {
                    String name = cell.column().name;
                    result.add(name, column(name));
                }
            return result;
        });
    }

    /**
     * Waits while value appear in Row <br>
     * e.g. waitWhile().colums(equalsTo("100"), row("Total")) <br>
     * or   waitWhile().rows(equalsTo("100"), row(5))
     */
    default TableVerify waitWhile() { return waitWhile(timeouts.getCurrentTimeoutSec()); }
    TableVerify waitWhile(int timeoutSec);

    default TableVerify assertThat() { return waitWhile(timeouts.getCurrentTimeoutSec()); }
    TableVerify assertThat(int timeoutSec);
    /**
     * Indicates that no rows in table. Check immediately
     */
    @JDIAction("Is table empty")
    default boolean isEmpty() { return validation(() -> rows().size() == 0); }
    /**
     * Indicates table size
     */
    @JDIAction("Is table empty")
    default int size() { return rows().size(); }

    /**
     * Indicates are any rows in table. Check immediately
     */
    @JDIAction("Is table has any rows")
    default boolean hasRows() { return validation(() -> !isEmpty()); }

    /**
     * Get first Row match in Column by index(Int) or name(String) <br>
     * e.g. row(equalsTo("Roman"), column("Name")) <br>
     * or   row(equalsTo("Roman"), column(3)) <br>
     * Each Row is map: columnName:cell
     */
    @JDIAction("Get first row match in column {1}")
    default TableLine row(IFilter<String> filter, Column column) {
        TableLine colLine = column(column.getIndex(columns().headers()));
        ICell cell = first(colLine.values(), c -> filter.execute(c.getText()));
        return cell != null ? row(cell.row().name) : null;
    }

    /**
     * Get first Column match in Row by index(Int) or name(String) <br>
     * e.g. column("100", row("Total") <br>
     * or   column("100", row(5)) <br>
     * Each Column is map: rowName:cell
     */
    @JDIAction("Get first column match in row {1}")
    default TableLine column(IFilter<String> filter, Row row){
        TableLine rowLine = row(row.getIndex(rows().headers()));
        ICell cell = first(rowLine.values(), c -> filter.execute(c.getText()));
        return cell != null ? column(cell.column().name) : null;
    }

    TableRow rows();

    @JDIAction("Get all rows")
    default TableLines getRows() {
        return new TableLines(headers(), this::row);
    }

    /**
     * Get Row with index <br>
     * Each Row is map: columnName:cell
     */
    @JDIAction("Get row {0}")
    default TableLine row(int index) {
        return rows().get(index);
    }

    /**
     * Get Row with name <br>
     * Each Row is map: columnName:cell
     */
    @JDIAction("Get row {0}")
    default TableLine row(String name) {
        return rows().get(name);
    }

    /**
     * Get Row values
     */
    @JDIAction("Get row {0} values")
    default List<String> rowValues(int index) {
        return map(row(index), pair -> pair.value.getText());
    }

    /**
     * Get Row values
     */
    @JDIAction("Get row {0} values")
    default List<String> rowValues(String name) {
        return map(row(name), pair -> pair.value.getText());
    }

    TableRow columns();
    @JDIAction("Get all columns")
    default TableLines getColumns() {
        return new TableLines(headers(), this::column);
    }

    /**
     * Get Column with index <br>
     * Each Column is map: rowName:cell
     */
    @JDIAction("Get column {0}")
    default TableLine column(int index) {
        return columns().get(index);
    }

    /**
     * Get Column with name <br>
     * Each Column is map: rowName:cell
     */
    @JDIAction("Get column {0}")
    default TableLine column(String name) {
        return column(firstIndex(headers(), h -> equalsIgnoreCase(h, name)));
    }

    /**
     * Get Column value
     */
    @JDIAction("Get column {0} values")
    default List<String> columnValues(int index) {
        return map(column(index), pair -> pair.value.getText());
    }

        /**
         * Get Column value
         */
    @JDIAction("Get column {0} values")
    default List<String> columnValues(String name) {
        return map(column(name), pair -> pair.value.getText());
    }

    /**
     * Get Header
     */
    @JDIAction("Get all headers")
    default TableLine header() {
        return columns().header();
    }

    /**
     * Get Header
     */
    @JDIAction("Get all headers values")
    default List<String> headers() {
        return columns().headers();
    }
    @JDIAction("Get ")
    default TableLine footer() { return columns().footer(); }
    /**
     * Get Header
     */
    @JDIAction("Get all headers values")
    default List<String> footerValues() {
        return columns().footerValues();
    }
    /**
     * Clean all already founded Cells
     */
    @JDIAction(value = "Clean table cache", level = DEBUG)
    void clean();

    /**
     * Similar to clean
     */
    default void clear() { clean(); }
    @Override
    default String getValue() {
        return rows().getAll().toString();
    }
}