package com.epam.jdi.uitests.core.interfaces.complex.tables;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.uitests.core.utils.common.IFilter;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.any;

public class TableVerify {
    private ITable table;
    private JFunc1<Boolean, Boolean> verifier;

    public TableVerify(ITable table, JFunc1<Boolean, Boolean> verifier) {
        this.table = table;
        this.verifier = verifier;
    }

    /**
     * @param filter a filtering criterion on size
     * @return 'true' if size satisfies the criterion, 'false' otherwise
     */
    public boolean size(IFilter<Integer> filter) {
        return verifier.execute(filter.execute(table.rows().size()));
    }

    /**
     * @param filter a filtering criterion on a cell
     * @param row the row to check
     * @return 'true' if the row has a cell that satisfies the criterion, 'false' otherwise
     */
    public boolean hasCell(IFilter<String> filter, Row row) {
        List<String> headers = table.rows().headers();
        return verifier.execute(any(table.row(row.getIndex(headers)).values(),
                v -> filter.execute(v.getText())));
    }

    /**
     * @param filter a filtering criterion on a cell
     * @param column the column to check
     * @return 'true' if the column has a cell that satisfies the criterion, 'false' otherwise
     */
    public boolean hasCell(IFilter<String> filter, Column column) {
        List<String> headers = table.columns().headers();
        return verifier.execute(any(table.column(column.getIndex(headers)).values(),
                v -> filter.execute(v.getText())));
    }

    /**
     * @param filter a filtering criterion on a cell
     * @param row the row to check
     * @return 'true' if the row has no cell that satisfies the criterion, 'false' otherwise
     */
    public boolean hasNoCell(IFilter<String> filter, Row row) {
        List<String> headers = table.rows().headers();
        return verifier.execute(!any(table.row(row.getIndex(headers)).values(),
                v -> filter.execute(v.getText())));
    }

    /**
     * @param filter a filtering criterion on a cell
     * @param column the column to check
     * @return 'true' if the column has no cell that satisfies the criterion, 'false' otherwise
     */
    public boolean hasNoCell(IFilter<String> filter, Column column) {
        List<String> headers = table.columns().headers();
        return verifier.execute(!any(table.column(column.getIndex(headers)).values(),
                v -> filter.execute(v.getText())));
    }

    /**
     * @param name the header name
     * @return 'true' if headers contains the header, 'false' otherwise
     */
    public boolean hasHeader(String name) {
        return verifier.execute(table.headers().contains(name));
    }

    /**
     * @return 'true' if the table is empty, 'false' otherwise
     */
    public boolean isEmpty() {
        return verifier.execute(table.isEmpty());
    }

    /**
     * @return 'true' if the table is not empty, 'false' otherwise
     */
    public boolean isNotEmpty() {
        return verifier.execute(!table.isEmpty());
    }
}
