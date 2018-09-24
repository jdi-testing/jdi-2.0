package com.epam.jdi.uitests.core.interfaces.complex.tables;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.CacheValue;
import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.common.IText;
import com.epam.jdi.uitests.core.utils.common.IFilter;

import java.util.ArrayList;
import java.util.List;

import static com.epam.jdi.tools.LinqUtils.*;

/**
 * Represents columns or rows in table
 */
public class TableRow {
    private ITable table;
    private JFunc1<Integer, List<ICell>> getLine;
    private JFunc<List<ICell>> getHeader;
    private JFunc<List<ICell>> getFooter;
    private TableRow otherTRow;
    private CacheValue<List<ICell>> headersCells = new CacheValue<>(
            () -> getHeader.execute());
    private CacheValue<List<ICell>> footerCells = new CacheValue<>(
            () -> getFooter.execute());
    private CacheValue<List<String>> headers;
    private CacheValue<TableLine> header =
            new CacheValue<>(() ->
                    new TableLine(headers.get(ArrayList::new), headersCells.get()));
    private CacheValue<List<String>> footerValues =
            new CacheValue<>(() -> map(footerCells.get(ArrayList::new),
                    IText::getText));
    private CacheValue<TableLine> footer =
            new CacheValue<>(() ->
                    new TableLine(headers.get(ArrayList::new), footerCells.get()));
    private CacheValue<Integer> count =
            new CacheValue<>(() -> getHeader.execute().size());
    private boolean foundAll = false;
    private TableLines foundLines;
    private int startIndex;

    public void setStart(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * {@code TableRow} constructor
     * @param table table
     * @param getLine action to get a row/column
     * @param getHeader action to get the header
     * @param named if named is 'true', header cells names are saved to headers list,
     *              otherwise the headers list will be filled with header cells indexes
     * @param useCache 'true' if the cache should be used to store values, 'false' otherwise
     * @param getFooter action to get the footer
     */
    public TableRow(ITable table,
                    JFunc1<Integer, List<ICell>> getLine,
                    JFunc<List<ICell>> getHeader,
                    boolean named, boolean useCache, JFunc<List<ICell>> getFooter) {
        this.table = table;
        this.getLine = getLine;
        this.getHeader = getHeader;
        this.getFooter = getFooter;
        count.useCache(useCache);
        headers = new CacheValue<>(named
                ? () -> map(headersCells.get(ArrayList::new), IText::getText)
                : () -> map(listOfRange(1, count()), i -> i + ""));
        headers.useCache(useCache);
        foundLines = new TableLines();
    }

    /**
     * Link the current table row/column with another one
     * @param otherTRow another table row/column
     */
    public void set(TableRow otherTRow) {
        this.otherTRow = otherTRow;
    }

    /**
     * @return number of cells in the header
     */
    @JDIAction
    public int count() {
        return count.get(() -> 0);
    }

    /**
     * Sets table row/column size
     * @param size number of cells in the header
     */
    public void setSize(int size) {
        count.set(size);
    }

    /**
     * @return number of cells in the header
     */
    @JDIAction
    public int size() {
        return count();
    }

    /**
     * @return TableLines Returns all table lines
     */
    @JDIAction
    public TableLines getAll() {
        if (!foundAll) {
            foundLines = new TableLines(
                    table.allCells(), headers(), otherTRow.headers());
        }
        return foundLines;
    }

    /**
     * @param name row/column name
     * @return a table row/column by name
     */
    @JDIAction
    public TableLine get(String name) {
        if (!foundLines.keys().contains(name))
            foundLines.add(name, new TableLine(otherTRow.headers(),
                    getLine.execute(headers().indexOf(name) + startIndex)));
        return foundLines.get(name);
    }

    /**
     * @param index table row/column index
     * @return a table row/column index
     */
    @JDIAction
    public TableLine get(int index) {
        return get(headers().get(index));
    }

    /**
     * @param name name of a line in the table
     * @return map with cells names and text values of the table line found by its name
     */
    @JDIAction
    public MapArray<String, String> getAsText(String name) {
        return get(name).toMapArray((k, v) -> k, (k, v) -> v.getText());
    }

    /**
     * @param num index of a line in the table
     * @return map with cells names and text values of the table line found by its index
     */
    @JDIAction
    public MapArray<String, String> getAsText(int num) {
        return get(num).toMapArray((k, v) -> k, (k, v) -> v.getText());
    }

    /**
     *
     * @param filter text filtering criteria
     * @return table lines with at least one cell with text that satisfies filtering criteria
     */
    public TableLines get(IFilter<String> filter) {
        return (TableLines) table.validation(
                () -> where(getAll(),
                        line -> line.value.any(
                                cell -> filter.execute(cell.getText()))));
    }

    /**
     * @return map with all cells names and text values
     */
    @JDIAction
    public String getAsText() {
        return getAll().toString();
    }

    /**
     * @return a list of columns/rows headers
     */
    @JDIAction
    public List<String> headers() {
        return headers.get(ArrayList::new);
    }

    /**
     * @param headers a list of headers to set
     */
    public void setHeaders(List<String> headers) {
        this.headers.set(headers);
    }

    /**
     * @return the line of header cells
     */
    @JDIAction
    public TableLine header() {
        return header.get(TableLine::new);
    }

    /**
     * @return a list of columns/rows footer values
     */
    public List<String> footerValues() {
        return footerValues.get(ArrayList::new);
    }

    /**
     * @return the line of footer cells
     */
    public TableLine footer() {
        return footer.get(TableLine::new);
    }

    public void clear() {
    }
}