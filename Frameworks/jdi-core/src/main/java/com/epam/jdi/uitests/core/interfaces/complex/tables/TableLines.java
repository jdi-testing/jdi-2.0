package com.epam.jdi.uitests.core.interfaces.complex.tables;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.tools.pairs.Pair;

import java.util.Collection;
import java.util.List;

import static com.epam.jdi.tools.PrintUtils.print;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.TableFilter.getFilters;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static java.lang.String.format;

public class TableLines extends MapArray<String, TableLine> {

    /**
     * {@code TableLines} constructor
     * @param headers table line headers
     * @param func action to get table lines
     */
    TableLines(Collection<String> headers, JFunc1<String, TableLine> func) {
        super(headers, h -> h, func);
    }

    /**
     * {@code TableLines} constructor by cells, rows and columns headers
     * @param cells table cells
     * @param rowHeaders rows headers
     * @param colHeaders columns headers
     */
    TableLines(List<ICell> cells, List<String> rowHeaders, List<String> colHeaders) {
        int i = 0;
        for (String rowHeader : rowHeaders) {
            TableLine row = new TableLine();
            for (String colHeader : colHeaders)
                row.add(colHeader, cells.get(i++));
            add(rowHeader, row);
        }
    }

    TableLines() {
    }

    /**
     * Searches lines in table that match specified criteria 
     * @param lines a list of search criteria
     * @param getAll an action to get all table lines to filter
     * @param what description of {@code getAll} result
     * @return TableLines
     */
    static TableLines filterTable(String[] lines, JFunc<TableLines> getAll, String what) {
        if (lines.length == 0)
            return getAll.execute();
        List<TableFilter> filters = getFilters(lines);
        TableLines result = new TableLines();
        for (Pair<String, TableLine> line : getAll.execute()) {
            boolean matches = false;
            for (TableFilter filter : filters) {
                ICell cell = line.value.get(filter.getName());
                if (cell == null)
                    throw exception(format("Search cell for '%s' failed. Can't find %s named %s",
                            print(lines), what, filter.getName()));
                matches = filter.getFilter().execute(cell.getValue());
                if (!matches) break;
            }
            if (matches) result.add(line);
        }
        return result;
    }
}
