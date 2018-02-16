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
    private Row newRow(){
        if (rowClass == null)
            throw exception("Row class was not specified");
        try {
            return rowClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw exception("Can't Instantiate row: " + rowClass.getName());
        }
    }

    public Row castToRow(TableLine row)
    {
        Row newRow = newRow();
        row.pairs.forEach(pair ->
                setRowField(newRow, filter(newRow.getClass().getFields(),
                    f -> f.getType().isAssignableFrom(IBaseElement.class)),
                        pair.key, pair.value));
        return newRow;
    }

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
    public int size() {
        return rows().count();
    }
    public boolean contains(Object o) {
        return o.getClass().isAssignableFrom(rowClass)
            && LinqUtils.first(rows().headers(),
                h -> line(h).equals(o)) == null;
    }
    public Iterator<Data> iterator() {
        return getAll().iterator();
    }
    public Object[] toArray() {
        return getAll().toArray();
    }
    public <T> T[] toArray(T[] a) {
        return getAll().toArray(a);
    }
    public boolean add(Data data) {
        throw new UnsupportedOperationException();
    }
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }
    public boolean containsAll(Collection<?> c) {
        return allCells().containsAll(c);
    }
    public boolean addAll(Collection<? extends Data> c) {
        throw new UnsupportedOperationException();
    }
    public boolean addAll(int index, Collection<? extends Data> c) {
        throw new UnsupportedOperationException();
    }
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
    public Data get(int index) {
        return entity(index);
    }
    public Data set(int index, Data element) {
        throw new UnsupportedOperationException();
    }
    public void add(int index, Data element) {
        throw new UnsupportedOperationException();
    }
    public Data remove(int index) {
        throw new UnsupportedOperationException();
    }
    public int indexOf(Object o) {
        return getAll().indexOf(o);
    }
    public int lastIndexOf(Object o) {
        return getAll().lastIndexOf(o);
    }
    public ListIterator<Data> listIterator() {
        return getAll().listIterator();
    }
    public ListIterator<Data> listIterator(int index) {
        return getAll().listIterator();
    }
    public List<Data> subList(int fromIndex, int toIndex) {
        return getAll().subList(fromIndex, toIndex);
    }
}
