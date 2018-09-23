package com.epam.jdi.uitests.core.templates.base;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.complex.tables.ICell;
import com.epam.jdi.uitests.core.interfaces.complex.tables.IEntityTable;
import com.epam.jdi.uitests.core.interfaces.complex.tables.TableLine;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

import static com.epam.jdi.tools.LinqUtils.filter;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static org.apache.commons.lang3.reflect.FieldUtils.writeField;

public class ETable<Row, Data> extends TTable implements IEntityTable<Data, Row> {
    public Class<Row> rowClass;
    public Class<Data> dataClass;
    public ETable(Class<Row> rowClass, Class<Data> dataClass) {
        this.dataClass = dataClass;
        this.rowClass = rowClass;
    }

    /**
     * Creates new instance of row
     * @return Row - new row
     */
    private Row newRow(){
        if (rowClass == null)
            throw exception("Row class was not specified");
        try {
            return rowClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw exception("Can't Instantiate row: " + rowClass.getName());
        }
    }

    /**
     * Casts TableLine row into exact class
     * @param row
     * @return Row - casted row
     */
    public Row castToRow(TableLine row)
    {
        Row newRow = newRow();
        row.pairs.forEach(pair ->
                setRowField(newRow, filter(newRow.getClass().getFields(),
                    f -> f.getType().isAssignableFrom(IBaseElement.class)),
                        pair.key, pair.value));
        return newRow;
    }

    /**
     * Set value to field
     * @param row - row with field
     * @param fields - list of row fields
     * @param fieldName - name of field
     * @param valueFunc - rule for field set
     */
    private void setField(Object row, List<Field> fields, String fieldName,
                          Function<Field, Object> valueFunc)
    {
        Field field = LinqUtils.first(fields,
            f -> f.getName().equalsIgnoreCase(fieldName));
        if (field == null) return;
        try {
            writeField(field, row, valueFunc.apply(field), true);
        } catch (IllegalAccessException e) {
            throw exception("Can't write field with name: " + fieldName);
        }
    }

    /**
     * Set value to field
     * @param row - row with field
     * @param fields - list of row fields
     * @param fieldName - name of field
     * @param cell - ICell
     */
    private void setRowField(Row row, List<Field> fields, String fieldName, ICell cell)
    {
        setField(row, fields, fieldName, field -> {
            Class clazz = field.getType();
            if (clazz == null) return null;
            IBaseElement value;
            try {
                value = cell.getAs(clazz);
            } catch (Exception e) {
                throw exception("Can't Instantiate row element: " + fieldName);
            }
            return value;
        });
    }

    public Data rowToEntity(TableLine row) {
        return null;
    }

    /**
     * Gets count of rows
     * @return int
     */
    public int size() {
        return rows().count();
    }

    /**
     * Checks if table contains object
     * @param o - object
     * @return boolean
     */
    public boolean contains(Object o) {
        return o.getClass().isAssignableFrom(rowClass)
            && LinqUtils.first(rows().headers(),
                h -> line(h).equals(o)) == null;
    }

    /**
     * Gets iterator
     * @return Iterator<Data>
     */
    public Iterator<Data> iterator() {
        return getAll().iterator();
    }

    /**
     * Returns table as array of Objects
     * @return Object[]
     */
    public Object[] toArray() {
        return getAll().toArray();
    }

    /**
     * Returns table as array of <T>
     * @param a - array of <T>
     * @param <T>
     * @return <T>T[]
     */
    public <T> T[] toArray(T[] a) {
        return getAll().toArray(a);
    }

    /**
     * Throws UnsupportedOperationException
     */
    public boolean add(Data data) {
        throw new UnsupportedOperationException();
    }

    /**
     * Throws UnsupportedOperationException
     */
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if table contains collection
     * @param c - collection
     * @return - boolean
     */
    public boolean containsAll(Collection<?> c) {
        return allCells().containsAll(c);
    }

    /**
     * Throws UnsupportedOperationException
     */
    public boolean addAll(Collection<? extends Data> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Throws UnsupportedOperationException
     */
    public boolean addAll(int index, Collection<? extends Data> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Throws UnsupportedOperationException
     */
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Throws UnsupportedOperationException
     */
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns Data by index
     * @param index - int
     * @return Data
     */
    public Data get(int index) {
        return entity(index);
    }

    /**
     * Throws UnsupportedOperationException
     */
    public Data set(int index, Data element) {
        throw new UnsupportedOperationException();
    }

    /**
     * Throws UnsupportedOperationException
     */
    public void add(int index, Data element) {
        throw new UnsupportedOperationException();
    }

    /**
     * Throws UnsupportedOperationException
     */
    public Data remove(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns index of object
     * @param o - object
     * @return int
     */
    public int indexOf(Object o) {
        return getAll().indexOf(o);
    }

    /**
     * Returns last index of object
     * @param o - object
     * @return int
     */
    public int lastIndexOf(Object o) {
        return getAll().lastIndexOf(o);
    }

    /**
     * Returns ListIterator
     * @return ListIterator<Data>
     */
    public ListIterator<Data> listIterator() {
        return getAll().listIterator();
    }

    /**
     * Returns ListIterator
     * @return ListIterator<Data>
     */
    public ListIterator<Data> listIterator(int index) {
        return getAll().listIterator();
    }

    /**
     * Returns sublist started from fromIndex and ended with toIndex
     * @param fromIndex - first element of sublist
     * @param toIndex - last index of sublist
     * @return List<Data>
     */
    public List<Data> subList(int fromIndex, int toIndex) {
        return getAll().subList(fromIndex, toIndex);
    }
}
