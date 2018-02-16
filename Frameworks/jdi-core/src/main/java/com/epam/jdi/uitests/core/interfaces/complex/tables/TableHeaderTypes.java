package com.epam.jdi.uitests.core.interfaces.complex.tables;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
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
