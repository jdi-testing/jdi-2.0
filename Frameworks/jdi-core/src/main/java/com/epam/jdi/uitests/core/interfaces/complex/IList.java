package com.epam.jdi.uitests.core.interfaces.complex;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

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

import static com.epam.jdi.uitests.core.logger.LogLevels.DEBUG;
import static com.epam.jdi.uitests.core.actions.base.ElementActions.getElements;

public interface IList<T> extends List<T>, IBaseElement {

    /**
     * @return a list of all application elements
     */
    @JDIAction(level = DEBUG)
    default <E> List<E> getElements() {
        return LinqUtils.map(getElements.execute(this, null), el -> (E) el);
    }

    /**
     * @return MapArray<String, T> of all application elements and their titles
     */
    @JDIAction(level = DEBUG)
    MapArray<String, T> getAll();

    /**
     * @return a list of all list options
     */
    @JDIAction(level = DEBUG)
    List<T> values();

    /**
     * @return The last element of MapArray of all application elements
     */
    default T last() {
        return getAll().last().value;
    }

    /**
     * @return The first element of MapArray of all application elements
     */
    default T first() {
        return getAll().first().value;
    }

    /**
     * @param func a rule how to filter elements
     * @return a list of elements filtered by {@code func}
     */
    default List<T> where(JFunc2<String, T, Boolean> func) {
        return getAll().filter(func).values();
    }

    /**
     * @param func a rule how to filter elements
     * @return a list of elements filtered by {@code func}
     */
    default List<T> filter(JFunc2<String, T, Boolean> func) {
        return where(func);
    }

    /**
     * @param func an action to invoke for all application elements
     * @param <R> a type that the {@code func} invocation returns
     * @return a list with results of {@code func} invocation
     */
    default <R> List<R> select(JFunc2<String, T, R> func) {
        return getAll().select(func);
    }

    default <R> List<R> map(JFunc2<String, T, R> func) {
        return select(func);
    }

    /**
     * @param func the condition for selecting a element
     * @return the first element that satisfies condition {@code func}
     */
    default T first(JFunc2<String, T, Boolean> func) {
        return getAll().first(func);
    }

    /**
     * @param func the condition for selecting a element
     * @return the last element that satisfies condition {@code func}
     */
    default T last(JFunc2<String, T, Boolean> func) {
        return getAll().last(func);
    }

    /**
     * @return number of all application elements
     */
    @Override
    default int size() {
        return getAll().size();
    }

    /**
     * @return boolean Returns 'true' if MapArray with all elements is empty
     */
    @JDIAction(level = DEBUG)
    default boolean isEmpty() {
        return getAll().isEmpty();
    }

    /**
     * @return boolean Returns 'true' if MapArray with all elements has at least one element
     */
    @JDIAction(level = DEBUG)
    default boolean any() {
        return !isEmpty();
    }

    /**
     * @param o object to check presence of
     * @return boolean Returns 'true' if list contains the object
     */
    default boolean contains(Object o) {
        return values().contains(o);
    }

    /**
     * @return an iterator over the list options
     */
    default Iterator<T> iterator() {
        return values().iterator();
    }

    /**
     * @return an Object[] array containing all of the list options
     */
    default Object[] toArray() {
        return values().toArray();
    }

    /**
     * @param a the array into which the elements of the list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @param <T1> array elements type
     * @return an array containing the elements of the list of type {@code T1}
     */
    default <T1> T1[] toArray(T1[] a) {
        return values().toArray(a);
    }

    default boolean add(T t) {
        throw new NotImplementedException();
    }

    default boolean remove(Object o) {
        throw new NotImplementedException();
    }

    default boolean containsAll(Collection<?> c) {
        return values().containsAll(c);
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

    default void clear() {
        throw new NotImplementedException();
    }

    /**
     * @param index option index
     * @return an element from the list of options by index
     */
    default T get(int index) {
        return values().get(index);
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

    /**
     * @param o an object with the list option
     * @return an index of the first occurrence of the option in the list
     */
    default int indexOf(Object o) {
        return values().indexOf(o);
    }

    /**
     * @param o an object with the list option
     * @return an index of the last occurrence of the option in the list
     */
    default int lastIndexOf(Object o) {
        return values().lastIndexOf(o);
    }

    /**
     * @return a ListIterator iterator over the list options
     */
    default ListIterator<T> listIterator() {
        return values().listIterator();
    }

    /**
     * @param index index of the first element to be returned from the list iterator
     * @return a ListIterator iterator over the list option, starting at the specified position in the list.
     */
    default ListIterator<T> listIterator(int index) {
        return values().listIterator(index);
    }

    /**
     * @param fromIndex int
     * @param toIndex int
     * @return a sub list of options between the specified fromIndex, inclusive, and toIndex, exclusive.
     */
    default List<T> subList(int fromIndex, int toIndex) {
        return values().subList(fromIndex, toIndex);
    }
}
