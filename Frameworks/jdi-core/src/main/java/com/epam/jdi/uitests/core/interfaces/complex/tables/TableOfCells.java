package com.epam.jdi.uitests.core.interfaces.complex.tables;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
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