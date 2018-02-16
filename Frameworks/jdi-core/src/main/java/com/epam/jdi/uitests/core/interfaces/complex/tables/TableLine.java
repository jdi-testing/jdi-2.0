package com.epam.jdi.uitests.core.interfaces.complex.tables;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.map.MapArray;

import java.util.List;

public class TableLine extends MapArray<String, ICell> {
    TableLine(List<String> keys, List<ICell> values) {
        super(keys, values);
    }
    TableLine() {}
}
