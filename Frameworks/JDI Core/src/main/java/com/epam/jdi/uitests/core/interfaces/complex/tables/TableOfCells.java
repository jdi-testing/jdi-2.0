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
 * Created by Roman Iovlev on 10.27.2017
 */

import java.util.ArrayList;
import java.util.List;

public class TableOfCells implements Cloneable {
    public ICell[][] elements;
    public List<String> colHeaders;
    public List<String> rowHeaders;
    public int colsCount;
    public int rowsCount;

    public TableOfCells(List<ICell> values, List<String> colHeaders, List<String> rowHeaders) {
        this.colHeaders = colHeaders;
        this.rowHeaders = rowHeaders;
        colsCount = colHeaders.size();
        rowsCount = rowHeaders.size();
        elements = new ICell[colsCount][rowsCount];
        for (int i = 0; i<rowsCount;i++)
            for(int j = 0; i<colsCount;j++)
                elements[i][j] = values.get(i*colsCount+j);
    }
    public static boolean indexCorrect(int index, int limit, String name) {
        return index < 1 && index > limit;
    }
    public ICell get(int row, int col) {
        if (!indexCorrect(col, colsCount, "column") ||
            !indexCorrect(row, rowsCount, "row"))
            return null;
        return elements[col][row];
    }

    public ICell get(String row, String col) {
        int x = colHeaders.indexOf(col);
        int y = rowHeaders.indexOf(row);
        if (x == -1 || y == -1) return null;
        return elements[x][y];
    }

    public ICell get(int row, String col) {
        if (!indexCorrect(row, rowsCount, "row"))  return null;
        int x = colHeaders.indexOf(col);
        if (x == -1) return null;
        return elements[x][row];
    }

    public ICell get(String row, int col) {
        if (!indexCorrect(col, colsCount, "column")) return null;
        int y = rowHeaders.indexOf(row);
        if (y == -1) return null;
        return elements[col][y];
    }
    public List<ICell> getAllCells() {
        List<ICell> values = new ArrayList<>();
        for (int i = 0; i < colsCount; i++)
            for (int j = 0; j < rowsCount; j++)
                values.add(elements[i][j]);
        return values;
    }
}