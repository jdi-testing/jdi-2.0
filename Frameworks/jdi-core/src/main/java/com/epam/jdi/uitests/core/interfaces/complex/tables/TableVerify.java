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
    public boolean size(IFilter<Integer>filter) {
        return verifier.execute(filter.execute(table.rows().size()));
    }
    public boolean hasCell(IFilter<String>filter, Row row) {
        List<String> headers = table.rows().headers();
        return verifier.execute(any(table.row(row.getIndex(headers)).values(),
                v -> filter.execute(v.getText())));
    }
    public boolean hasCell(IFilter<String>filter, Column column) {
        List<String> headers = table.columns().headers();
        return verifier.execute(any(table.column(column.getIndex(headers)).values(),
                v -> filter.execute(v.getText())));
    }
    public boolean hasNoCell(IFilter<String>filter, Row row) {
        List<String> headers = table.rows().headers();
        return verifier.execute(!any(table.row(row.getIndex(headers)).values(),
                v -> filter.execute(v.getText())));
    }
    public boolean hasNoCell(IFilter<String>filter, Column column) {
        List<String> headers = table.columns().headers();
        return verifier.execute(!any(table.column(column.getIndex(headers)).values(),
                v -> filter.execute(v.getText())));
    }
    public boolean hasHeader(String name) {
        return verifier.execute(table.headers().contains(name));
    }
    public boolean isEmpty() {
        return verifier.execute(table.isEmpty());
    }
    public boolean isNotEmpty() {
        return verifier.execute(!table.isEmpty());
    }
}
