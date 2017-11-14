package com.epam.jdi.uitests.core.interfaces.complex.tables;

/**
 * Created by 12345 on 09.05.2016.
 */
public enum TableHeaderTypes {
    ALL_HEADERS(true, true),
    NO_HEADERS(false, false),
    COLUMNS_HEADERS(false, true),
    ROWS_HEADERS(true, false);
    public boolean row;
    public boolean column;
    TableHeaderTypes(boolean row, boolean column) {
        this.row = row;
        this.column = column;
    }
}
