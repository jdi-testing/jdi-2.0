package com.epam.jdi.uitests.core.interfaces.complex.tables;
/* The MIT License
 *
 * Copyright 2004-2017 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:

 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */

/**
 * Created by Roman Iovlev on 10.03.2017
 */

import com.epam.jdi.tools.CacheValue;
import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.common.IText;
import com.epam.jdi.uitests.core.utils.common.IFilter;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.*;

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
                new TableLine(headers.get(), headersCells.get()));
    private CacheValue<List<String>> footerValues =
            new CacheValue<>(() -> map(footerCells.get(),
                    IText::getText));
    private CacheValue<TableLine> footer =
            new CacheValue<>(() ->
                    new TableLine(headers.get(), footerCells.get()));
    private CacheValue<Integer> count =
        new CacheValue<>(() -> getHeader.execute().size());
    private boolean foundAll = false;
    private TableLines foundLines;
    private int startIndex;
    public void setStart(int startIndex) { this.startIndex = startIndex; }

    public TableRow(ITable table,
             JFunc1<Integer, List<ICell>> getLine,
             JFunc<List<ICell>> getHeader,
             boolean named, boolean useCache, JFunc<List<ICell>> getFooter) {
        this.table = table;
        this.getLine = getLine;
        this.getHeader = getHeader;
        this.getFooter = getFooter;
        count.useCache = useCache;
        headers = new CacheValue<>(() -> map(headersCells.get(),
                IText::getText));
        if (!named) headers.set(map(listOfRange(1,count()), i -> i+""));
        headers.useCache = useCache;
        foundLines = new TableLines();
    }
    public void set(TableRow otherTRow) {
        this.otherTRow = otherTRow;
    }
    /**
     * Get Columns/Rows count
     */
    @JDIAction
    public int count() { return count.get(); }
    public void setSize(int size) { count.set(size); }
    @JDIAction
    public int size() { return count(); }

    @JDIAction
    public TableLines getAll() {
        if (!foundAll) {
            foundLines = new TableLines(
                table.allCells(), headers(), otherTRow.headers());
        }
        return foundLines;
    }

    @JDIAction
    public TableLine get(String name) {
        if (!foundLines.keys().contains(name))
            foundLines.add(name, new TableLine(otherTRow.headers(),
                getLine.execute(headers().indexOf(name) + startIndex)));
        return foundLines.get(name);
    }
    @JDIAction
    public TableLine get(int index) {
        return get(headers().get(index));
    }
    @JDIAction
    public MapArray<String, String> getAsText(String name) {
        return get(name).toMapArray((k,v) -> k, (k,v) -> v.getText());
    }
    @JDIAction
    public MapArray<String, String> getAsText(int num) {
        return get(num).toMapArray((k,v) -> k, (k,v) -> v.getText());
    }
    public TableLines get(IFilter<String> filter) {
        return (TableLines) table.validation(
            () -> where(getAll(),
                line -> line.value.any(
                cell -> filter.execute(cell.getText()))));
    }

    @JDIAction
    public String getAsText() { return getAll().toString(); }

    /**
     * Get Columns/Rows headers
     */
    @JDIAction
    public List<String> headers() { return headers.get(); }
    public void setHeaders(List<String> headers) { this.headers.set(headers); }
    @JDIAction
    public TableLine header() { return header.get(); }

    /**
     * Get Columns/Rows headers
     */
    public List<String> footerValues() { return footerValues.get(); }
    public TableLine footer() { return footer.get(); }

    public void clear() { }
}