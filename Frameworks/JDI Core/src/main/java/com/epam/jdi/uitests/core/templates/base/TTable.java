package com.epam.jdi.uitests.core.templates.base;

import com.epam.jdi.tools.CacheValue;
import com.epam.jdi.tools.Timer;
import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.uitests.core.interfaces.base.IElement;
import com.epam.jdi.uitests.core.interfaces.complex.IBaseSelector;
import com.epam.jdi.uitests.core.interfaces.complex.tables.*;
import com.epam.jdi.uitests.core.templates.base.TBaseElement;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.map;
import static com.epam.jdi.uitests.core.actions.complex.SelectActions.toCell;
import static com.epam.jdi.uitests.core.interfaces.complex.tables.TableHeaderTypes.COLUMNS_HEADERS;

/**
 * Created by Roman_Iovlev on 11/5/2017.
 */
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
            headerType.row, isUseCache(), null);
    protected TableRow columns = new TableRow(this,
            i -> getCells("column", i), () -> getCells("columnHeader"),
            headerType.column, isUseCache(), () -> getCells("footer"));
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
        return allCells.get().getAllCells();
    }

    @Override
    public ICell cell(int rowIndex, int columnIndex) {
        ICell result = null;
        if (allCells.hasValue())
            result = allCells.get().get(rowIndex, columnIndex);
        if (result != null) return result;
        return getCell( rowIndex, columnIndex);
    }
    public ITable waitWhile(int timeoutSec) {
        validationTimeout = timeoutSec;
        return this;
    }
    @Override
    public ITable waitWhile() {
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
