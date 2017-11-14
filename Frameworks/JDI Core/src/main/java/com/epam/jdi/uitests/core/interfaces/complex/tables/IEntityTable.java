package com.epam.jdi.uitests.core.interfaces.complex.tables;

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.utils.common.IFilter;
import com.epam.jdi.uitests.core.utils.common.IStringFilter;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.*;
import static com.epam.jdi.uitests.core.actions.complex.TableActions.castToRow;
import static com.epam.jdi.uitests.core.actions.complex.TableActions.rowToEntity;
import static com.epam.jdi.uitests.core.settings.JDISettings.logger;

/**
 * Created by Roman_Iovlev on 11/12/2017.
 */
public interface IEntityTable<Data, Row> extends ITable, List<Data> {
    @JDIAction("Get table lines")
    default List<Row> getLines() {
        return map(rows().getAll(), row -> (Row) castToRow.execute(this, row.value));
    }

    @JDIAction("Get table lines matches filter")
    default List<Row> getLines(IFilter<Row> filter) {
        List<Row> rows = filter(getLines(), filter);
        if (rows.size() == 0)
            logger.info("Can't find any rows that meat filter");
        return rows;
    }

    @JDIAction("Get first line in table")
    default Row firstLine(IFilter<Row> filter) {
        for(String header : rows().headers()) {
            Row row = (Row) castToRow.execute(this, row(header));
            if (filter.execute(row)) return row;
        }
        return null;
    }
    @JDIAction("Get lines matches in column {1}")
    default Row firstLine(IStringFilter filter, Column column) {
        return (Row) castToRow.execute(this, row(filter, column));
    }
    @JDIAction("Get line {0}")
    default Row getLine(int index) {
        return (Row) castToRow.execute(this, row(index));
    }
    @JDIAction("Get line {0}")
    default Row getLine(String name) {
        return (Row) castToRow.execute(this, row(name));
    }

    @JDIAction("Get all entities")
    default List<Data> entities(String... colNames) {
        return select(colNames, colName
                -> (Data) rowToEntity.execute(this, column(colName)));

    }
    default List<Data> entities(){
        return getAll();
    }
    @JDIAction("Get entity matches filter ")
    default Data firstEntity(IFilter<Data> filter) {
        for(String header : rows().headers()) {
            Data row = (Data) rowToEntity.execute(this, row(header));
            if (filter.execute(row)) return row;
        }
        return null;
    }
    @JDIAction("Get entity matches filter in column {0}")
    default Data entity(IStringFilter filter, Column column) {
        return (Data) rowToEntity.execute(this, row(filter, column));
    }
    @JDIAction("Get entity {0}")
    default Data entity(String name) {
        return (Data) rowToEntity.execute(this, row(name));
    }
    @JDIAction("Get entity {0}")
    default Data entity(int index) {
        return (Data) rowToEntity.execute(this, row(index));
    }
    @JDIAction("Get all entites")
    default List<Data> getAll(){
        return map(rows().getAll(), row -> (Data) rowToEntity.execute(this, row.value));
    }
    default Data first(){
        return entity(1);
    }
    default Data last(){
        return entity(size());
    }

    @JDIAction("Is table empty")
    default boolean isEmpty() { return validation(() -> rows().size() == 0); }
    default void clear() { clean(); }
}
