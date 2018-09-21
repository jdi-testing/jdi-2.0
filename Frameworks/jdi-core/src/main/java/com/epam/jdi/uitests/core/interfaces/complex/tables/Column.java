package com.epam.jdi.uitests.core.interfaces.complex.tables;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import static com.epam.jdi.tools.EnumUtils.getEnumValue;

public class Column extends NameNum {
    /**
     * @param num index
     * @return a column set by index
     */
    public static Column column(int num) {
        return (Column) new Column().set(column -> column.num = num);
    }

    /**
     * @param name name
     * @return a column set by name
     */
    public static Column column(String name) {
        return (Column) new Column().set(column -> column.name = name);
    }

    /**
     * @param name Enum with column name
     * @return a column set by name
     */
    public static Column column(Enum name) {
        return column(getEnumValue(name));
    }

    /**
     * @param num column index
     * @return a column to search into
     */
    public static Column inColumn(int num) {
        return column(num);
    }

    /**
     * @param name column name
     * @return a column to search into
     */
    public static Column inColumn(String name) {
        return column(name);
    }

    /**
     * @param name Enum with the column name
     * @return a column to search into
     */
    public static Column inColumn(Enum name) {
        return column(getEnumValue(name));
    }

}