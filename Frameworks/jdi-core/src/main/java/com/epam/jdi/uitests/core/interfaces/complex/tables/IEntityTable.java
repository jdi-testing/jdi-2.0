package com.epam.jdi.uitests.core.interfaces.complex.tables;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.utils.common.IFilter;

import java.util.List;

import static com.epam.jdi.tools.LinqUtils.*;
import static com.epam.jdi.uitests.core.actions.complex.TableActions.castToRow;
import static com.epam.jdi.uitests.core.actions.complex.TableActions.rowToEntity;
import static com.epam.jdi.uitests.core.settings.JDISettings.logger;

public interface IEntityTable<Data, Row> extends ITable, List<Data> {
    /**
     * @return a list with rows
     */
    @JDIAction("Get table lines")
    default List<Row> getLines() {
        return map(rows().getAll(), row -> (Row) castToRow.execute(this, row.value));
    }

    /**
     * @return number of rows
     */
    default int size() {
        return rows().size();
    }

    /**
     * @param filter row filter
     * @return a list of rows that match the filter
     */
    @JDIAction("Get table lines matches filter")
    default List<Row> getLines(IFilter<Row> filter) {
        List<Row> rows = filter(getLines(), filter);
        if (rows.size() == 0)
            logger.info("Can't find any rows that meet filter");
        return rows;
    }

    /**
     * @param filter row filter
     * @return the first row that match the filter
     */
    @JDIAction("Get first line in table")
    default Row firstLine(IFilter<Row> filter) {
        for (String header : rows().headers()) {
            Row row = (Row) castToRow.execute(this, row(header));
            if (filter.execute(row)) return row;
        }
        return null;
    }

    /**
     * @param filter text filter
     * @param column a column set by index or name
     * @return the first row that match the filter in column
     */
    @JDIAction("Get lines matches in column {1}")
    default Row firstLine(IFilter<String> filter, Column column) {
        return (Row) castToRow.execute(this, row(filter, column));
    }

    /**
     * @param index row index
     * @return a row by index
     */
    @JDIAction("Get line {0}")
    default Row line(int index) {
        return (Row) castToRow.execute(this, row(index));
    }

    /**
     * @param name row name
     * @return a row by name
     */
    @JDIAction("Get line {0}")
    default Row line(String name) {
        return (Row) castToRow.execute(this, row(name));
    }

    /**
     * @param filter text filter
     * @param column a column set by index or name
     * @return the first row that match the filter in column
     */
    @JDIAction("Get line {0}")
    default Row line(IFilter<String> filter, Column column) {
        return (Row) castToRow.execute(this, row(filter, column));
    }

    /**
     * @param colNames columns names
     * @return a list of columns as entities selected by columns names
     */
    @JDIAction("Get all entities")
    default List<Data> entities(String... colNames) {
        return select(colNames, colName
                -> (Data) rowToEntity.execute(this, column(colName)));

    }

    /**
     * @return a list of all rows as entities
     */
    default List<Data> entities() {
        return getAll();
    }

    /**
     * @param filter row filter
     * @return the first row as entity that matches the filter
     */
    @JDIAction("Get entity matches filter ")
    default Data firstEntity(IFilter<Data> filter) {
        for (String header : rows().headers()) {
            Data row = (Data) rowToEntity.execute(this, row(header));
            if (filter.execute(row)) return row;
        }
        return null;
    }

    /**
     * @param filter cell text filtering criterion
     * @param column a column set by name or index
     * @return the first row match in column as entity
     */
    @JDIAction("Get entity matches filter in column {0}")
    default Data entity(IFilter<String> filter, Column column) {
        return (Data) rowToEntity.execute(this, row(filter, column));
    }

    /**
     * @param name row name
     * @return a row by name as entity
     */
    @JDIAction("Get entity {0}")
    default Data entity(String name) {
        return (Data) rowToEntity.execute(this, row(name));
    }

    /**
     * @param index row index
     * @return a row by index as entity
     */
    @JDIAction("Get entity {0}")
    default Data entity(int index) {
        return (Data) rowToEntity.execute(this, row(index));
    }

    /**
     * @return a list of all rows as entities
     */
    @JDIAction("Get all entities")
    default List<Data> getAll() {
        return map(rows().getAll(), row -> (Data) rowToEntity.execute(this, row.value));
    }

    /**
     * @return the first row as entity
     */
    default Data first() {
        return entity(1);
    }

    /**
     * @return the last row as entity
     */
    default Data last() {
        return entity(size());
    }

    /**
     * Checks immediately if the table is empty
     * @return 'true' if there are no rows in table, 'false' otherwise.
     */
    @JDIAction("Is table empty")
    default boolean isEmpty() {
        return validation(() -> rows().size() == 0);
    }

    /**
     * Similar to {@code clean()}
     */
    default void clear() {
        clean();
    }
}
