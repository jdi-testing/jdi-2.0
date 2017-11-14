package com.epam.jdi.uitests.core.interfaces.complex;

import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.tools.func.JFunc2;
import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.epam.jdi.uitests.core.actions.base.ElementActions.getElements;
import static com.epam.jdi.uitests.core.logger.LogLevels.DEBUG;

/**
 * Created by Roman_Iovlev on 10/24/2017.
 */
public interface IList<T> extends List<T>, IBaseElement {

    /**
     *  Get specified application elements
     */
    @JDIAction(level = DEBUG)
    default <E> List<E> getElements() {
        return LinqUtils.map(getElements.execute(this, null), el -> (E)el); }
    /**
     *  Get specified application elements
     */
    @JDIAction(level = DEBUG)
    MapArray<String, T> getAll();
    default T last() {
        return getAll().last().value;
    }
    default T first() {
        return getAll().first().value;
    }
    default List<T> where(JFunc2<String, T, Boolean> func) {
        return getAll().filter(func).values();
    }
    default List<T> filter(JFunc2<String, T, Boolean> func) {
        return where(func);
    }
    default <R> List<R> select(JFunc2<String, T, R> func) {
        return getAll().select(func);
    }
    default <R> List<R> map(JFunc2<String, T, R> func) {
        return select(func);
    }
    default T first(JFunc2<String, T, Boolean> func) {
        return getAll().first(func);
    }
    default T last(JFunc2<String, T, Boolean> func) {
        return getAll().last(func);
    }
    @JDIAction
    default int size() {
        return getAll().size();
    }
    @JDIAction
    default boolean isEmpty() {
        return getAll().isEmpty();
    }

    // List methods
    default boolean contains(Object o) {
        return getAll().values().contains(o);
    }
    default Iterator<T> iterator() {
        return getAll().values().iterator();
    }
    default Object[] toArray() {
        return getAll().values().toArray();
    }
    default <T1> T1[] toArray(T1[] a) {
        return getAll().values().toArray(a);
    }
    default boolean add(T t) {
        throw new NotImplementedException();
    }
    default boolean remove(Object o) {
        throw new NotImplementedException();
    }
    default boolean containsAll(Collection<?> c) {
        return getAll().values().containsAll(c);
    }
    default boolean addAll(Collection<? extends T> c) {
        throw new NotImplementedException();
    }
    default boolean addAll(int index, Collection<? extends T> c) {
        throw new NotImplementedException();
    }
    default boolean removeAll(Collection<?> c) {
        throw new NotImplementedException();
    }
    default boolean retainAll(Collection<?> c) {
        throw new NotImplementedException();
    }
    default void clear() { throw new NotImplementedException(); }
    default T get(int index) {
        return getAll().values().get(index);
    }
    default T set(int index, T element) {
        throw new NotImplementedException();
    }
    default void add(int index, T element) {
        throw new NotImplementedException();
    }
    default T remove(int index) {
        throw new NotImplementedException();
    }
    default int indexOf(Object o) {
        return getAll().values().indexOf(o);
    }
    default int lastIndexOf(Object o) {
        return getAll().values().lastIndexOf(o);
    }
    default ListIterator<T> listIterator() {
        return getAll().values().listIterator();
    }
    default ListIterator<T> listIterator(int index) {
        return getAll().values().listIterator(index);
    }
    default List<T> subList(int fromIndex, int toIndex) {
        return getAll().values().subList(fromIndex, toIndex);
    }
}
