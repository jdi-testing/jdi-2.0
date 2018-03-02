package com.epam.jdi.uitests.core.templates.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.CacheValue;
import com.epam.jdi.tools.Timer;
import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.uitests.core.interfaces.base.IElement;
import com.epam.jdi.uitests.core.interfaces.complex.IBaseSelector;
import com.epam.jdi.uitests.core.interfaces.complex.tables.*;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.map;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.toCell;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.TableHeaderTypes.COLUMNS_HEADERS;
import static com.epam.jdi.uitests.core.settings.JDISettings.asserter;

public class TTable extends TBaseElement implements ITable {
    public TableHeaderTypes headerType = COLUMNS_HEADERS;
    protected ICell getCell(int x, int y) { return toCell.execute(
            this, ((IElement)linked().get("cell")).getElement(x, y), x, y); }
    protected List<ICell> getCells(String name) { return getCells(name, null); }
    protected List<ICell> getCells(String name, Integer index) {
        return map(((IBaseSelector)linked().get(name)).getElements(index),
            el -> toCell.execute(this, el, 0, 0)); }
    protected TableRow rows = new TableRow(this,
            i -> getCells("row", i), () -> getCells("rowHeader"),
            headerType.row, false, null);
    protected TableRow columns = new TableRow(this,
            i -> getCells("column", i), () -> getCells("columnHeader"),
            headerType.column, false, () -> getCells("footer"));
    protected CacheValue<TableOfCells> allCells = new CacheValue<>(
            () -> new TableOfCells(getCells("cells"),
                rows().headers(),
                columns().headers()));
    protected int validationTimeout = 0;

    public TTable() {
        rows.set(columns);
        columns.set(rows);
    }
    public <T> T validation(JFunc<T> results) {
        return new Timer(validationTimeout).getResultByCondition(
                results::execute,
                this::accepted);
    }
    private boolean accepted(Object o) {
        if (o instanceof List)
            return ((List)o).size() > 0;
        if (o instanceof Boolean)
            return (Boolean) o;
        return o != null;
    }
    @Override
    public List<ICell> allCells() {
        return allCells.get(TableOfCells::new).getAllCells();
    }

    @Override
    public ICell cell(int rowIndex, int columnIndex) {
        ICell result = null;
        if (allCells.hasValue())
            result = allCells.get(TableOfCells::new).get(rowIndex, columnIndex);
        if (result != null) return result;
        return getCell( rowIndex, columnIndex);
    }
    public TableVerify waitWhile(int timeoutSec) {
        validationTimeout = timeoutSec;
        return new TableVerify(this, r -> validation(() -> r));
    }
    public TableVerify waitWhile() {
        return waitWhile(waitTimeout);
    }

    public TableVerify assertThat(int timeoutSec) {
        validationTimeout = timeoutSec;
        return new TableVerify(this, r -> { asserter.isTrue(validation(() -> r)); return r; });
    }
    public TableVerify assertThat() {
        return waitWhile(waitTimeout);
    }
    public TableRow rows() {
        return rows;
    }
    public TableRow columns() {
        return columns;
    }
    public void clean() {
        rows().clear();
        columns().clear();
        allCells.clear();
    }
}
