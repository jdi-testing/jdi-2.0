package com.epam.jdi.uitests.core.interfaces.complex.tables;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.map.MapArray;

import java.util.List;

/**
 * {@code TableLine} is a horiontal or vertical line in table.
 * <p>Each line is a map: (columnName : cell) or (rowName : cell)
 *
 */
public class TableLine extends MapArray<String, ICell> {
    /**
     * {@code TableLine} constructor
     * @param keys cells keys
     * @param values values in cells
     */
    TableLine(List<String> keys, List<ICell> values) {
        super(keys, values);
    }

    TableLine() {
    }
}
