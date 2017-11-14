package com.epam.jdi.uitests.web.selenium.elements.complex.table;

/* The MIT License
 *
 * Copyright 2004-2017 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:

 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

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
 * Created by Roman Iovlev on 10.03.2017
 */
public class EntityTable<Data, Row> extends Element implements IEntityTable<Data, Row> {
    private ETable eTable;
    private Class<Row> rowClass;
    private Class<Data> dataClass;
    public EntityTable(Class<Row> rowClass, Class<Data> dataClass) {
        this.dataClass = dataClass;
        this.rowClass = rowClass;
        eTable = new ETable<>(dataClass, rowClass);
    }

    public List<ICell> allCells() {
        return eTable.allCells();
    }
    public <T> T validation(JFunc<T> results) {
        return eTable.validation(results);
    }
    public TableVerify waitWhile(int timeoutSec) {
        return eTable.waitWhile(timeoutSec);
    }
    public TableVerify assertThat(int timeoutSec) {
        return eTable.assertThat(timeoutSec);
    }
    public TableRow rows() {
        return eTable.rows();
    }
    public TableRow columns() {
        return eTable.columns();
    }
    public void clean() {
        eTable.clean();
    }
    public ITable useCache(boolean value) {
        eTable.setUseCache(value);
        return eTable;
    }
    public int size() {
        return eTable.size();
    }
    public boolean contains(Object o) {
        return eTable.contains(o);
    }
    public Iterator<Data> iterator() {
        return eTable.iterator();
    }
    public Object[] toArray() {
        return eTable.toArray();
    }
    public <T> T[] toArray(T[] a) {
        return (T[]) eTable.toArray(a);
    }
    public boolean add(Data data) {
        throw new UnsupportedOperationException();
    }
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }
    public boolean containsAll(Collection<?> c) {
        return eTable.containsAll(c);
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
        return (Data) eTable.get(index);
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
        return eTable.indexOf(o);
    }
    public int lastIndexOf(Object o) {
        return eTable.lastIndexOf(o);
    }
    public ListIterator<Data> listIterator() {
        return eTable.listIterator();
    }
    public ListIterator<Data> listIterator(int index) {
        return eTable.listIterator(index);
    }
    public List<Data> subList(int fromIndex, int toIndex) {
        return eTable.subList(fromIndex, toIndex);
    }
}
