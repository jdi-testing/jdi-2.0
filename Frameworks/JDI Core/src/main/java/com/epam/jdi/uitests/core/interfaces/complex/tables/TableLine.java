package com.epam.jdi.uitests.core.interfaces.complex.tables;

import com.epam.jdi.tools.map.MapArray;

import java.util.List;

/**
 * Created by Roman_Iovlev on 10/31/2017.
 */
public class TableLine extends MapArray<String, ICell> {
    TableLine(List<String> keys, List<ICell> values) {
        super(keys, values);
    }
    TableLine() {}
}
