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

import static com.epam.jdi.tools.EnumUtils.getEnumValue;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

public class Row extends NameNum {
    /**
     * @param num index
     * @return a row set by index
     */
    public static Row row(int num) {
        return (Row) new Row().set(row -> row.num = num);
    }

    /**
     * @param name name
     * @return a row set by name
     */
    public static Row row(String name) {
        return (Row) new Row().set(row -> row.name = name);
    }
    
    /**
     * @param name Enum with row name
     * @return a row set by name
     */
    public static Row row(Enum name) {
        return row(getEnumValue(name));
    }

    /**
     * @param num row index
     * @return a row to search into 
     */
    public static Row inRow(int num) {
        return row(num);
    }

    /**
     * @param name row name
     * @return a row to search into 
     */
    public static Row inRow(String name) {
        return row(name);
    }

    /**
     * @param name Enum with the row name
     * @return a row to search into 
     */
    public static Row inRow(Enum name) {
        return row(getEnumValue(name));
    }
}