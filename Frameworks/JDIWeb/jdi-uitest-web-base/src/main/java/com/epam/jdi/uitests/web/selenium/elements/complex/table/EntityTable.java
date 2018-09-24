package com.epam.jdi.uitests.web.selenium.elements.complex.table;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc;
import com.epam.jdi.uitests.core.interfaces.complex.tables.*;
import com.epam.jdi.uitests.core.templates.base.ETable;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * EntityTable complex element
 * @param <Data> Data
 * @param <Row> Row
 */
public class EntityTable<Data, Row> extends Element implements IEntityTable<Data, Row> {
    private ETable eTable;
    private Class<Row> rowClass;
    private Class<Data> dataClass;

    /**
     * Constructs EntityTable
     * @param rowClass Row
     * @param dataClass Data
     */
    public EntityTable(Class<Row> rowClass, Class<Data> dataClass) {
        this.dataClass = dataClass;
        this.rowClass = rowClass;
        eTable = new ETable<>(dataClass, rowClass);
    }

    /**
     * Gets all cells
     * @return list of cells
     */
    public List<ICell> allCells() {
        return eTable.allCells();
    }

    /**
     * Performs validation
     * @param results results
     * @param <T> type
     * @return validation results
     */
    public <T> T validation(JFunc<T> results) {
        return eTable.validation(results);
    }

    /**
     * Waits for specified number of seconds
     * @param timeoutSec timeout in seconds
     * @return TableVerify
     */
    public TableVerify waitWhile(int timeoutSec) {
        return eTable.waitWhile(timeoutSec);
    }

    /**
     * Performs assertion
     * @param timeoutSec timeout in seconds
     * @return TableVerify
     */
    public TableVerify assertThat(int timeoutSec) {
        return eTable.assertThat(timeoutSec);
    }

    /**
     * Returns rows
     * @return rows
     */
    public TableRow rows() {
        return eTable.rows();
    }

    /**
     * Returns columns
     * @return columns
     */
    public TableRow columns() {
        return eTable.columns();
    }

    /**
     * Cleans EntityTable
     */
    public void clean() {
        eTable.clean();
    }

    /**
     * Sets useCache value
     * @param value value
     * @return Table
     */
    public ITable useCache(boolean value) {
        eTable.setUseCache(value);
        return eTable;
    }

    /**
     * Returns size
     * @return size
     */
    public int size() {
        return eTable.size();
    }

    /**
     * Checks whether EntityTable contains specified object
     * @param o object
     * @return true if EntityTable contains specified object, otherwise false
     */
    public boolean contains(Object o) {
        return eTable.contains(o);
    }

    /**
     * Returns iterator
     * @return Iterator
     */
    public Iterator<Data> iterator() {
        return eTable.iterator();
    }

    /**
     * Converts EntityTable to array of objects
     * @return array
     */
    public Object[] toArray() {
        return eTable.toArray();
    }

    /**
     * Converts EntityTable to array of specified type
     * @param a EntityTable
     * @param <T> type
     * @return array
     */
    public <T> T[] toArray(T[] a) {
        return (T[]) eTable.toArray(a);
    }

    /**
     * Adds data to EntityTable
     * @param data data
     * @return true if operation successful, otherwise false
     */
    public boolean add(Data data) {
        throw new UnsupportedOperationException();
    }

    /**
     * Remove data from EntityTable
     * @param o object
     * @return true if operation successful, otherwise false
     */
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if EntityTable contains collection
     * @param c collection
     * @return true if EntityTable contains collection, otherwise false
     */
    public boolean containsAll(Collection<?> c) {
        return eTable.containsAll(c);
    }

    /**
     * Adds collection to EntityTable
     * @param c collection
     * @return true if operation successful, otherwise false
     */
    public boolean addAll(Collection<? extends Data> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds collection to EntityTable after index
     * @param index index
     * @param c collection
     * @return true if operation successful, otherwise false
     */
    public boolean addAll(int index, Collection<? extends Data> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes collection from EntityTable
     * @param c collection
     * @return true if operation successful, otherwise false
     */
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Retains collection
     * @param c collection
     * @return true is operation successful, otherwise false
     */
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets data by index
     * @param index index
     * @return Data
     */
    public Data get(int index) {
        return (Data) eTable.get(index);
    }

    /**
     * Sets Data
     * @param index index
     * @param element element
     * @return Data
     */
    public Data set(int index, Data element) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds Data
     * @param index index
     * @param element element
     */
    public void add(int index, Data element) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes Data
     * @param index index
     * @return Data
     */
    public Data remove(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns index of object
     * @param o object
     * @return index
     */
    public int indexOf(Object o) {
        return eTable.indexOf(o);
    }

    /**
     * Returns last index of object
     * @param o object
     * @return last index
     */
    public int lastIndexOf(Object o) {
        return eTable.lastIndexOf(o);
    }

    /**
     * Returns list iterator
     * @return ListIterator
     */
    public ListIterator<Data> listIterator() {
        return eTable.listIterator();
    }

    /**
     * Returns list iterator
     * @param index index
     * @return ListIterator
     */
    public ListIterator<Data> listIterator(int index) {
        return eTable.listIterator(index);
    }

    /**
     * Returns list od Data
     * @param fromIndex from index
     * @param toIndex to index
     * @return list of data
     */
    public List<Data> subList(int fromIndex, int toIndex) {
        return eTable.subList(fromIndex, toIndex);
    }
}
