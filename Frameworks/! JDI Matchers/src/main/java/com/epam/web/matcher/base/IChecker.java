package com.epam.web.matcher.base;
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

/**
 * Created by Roman Iovlev on 10.03.2017
 */
import com.epam.jdi.tools.func.JAction;
import com.epam.jdi.tools.map.MapArray;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import static com.epam.jdi.tools.LinqUtils.toIntArray;
import static com.epam.jdi.tools.ReflectionUtils.getFields;
import static java.util.Arrays.asList;

public interface IChecker {
    <T> void areEquals(T actual, T expected, String failMessage);

    default <T> void areEquals(T actual, T expected) { areEquals(actual, expected, null); }

    void matches(String actual, String regEx, String failMessage);

    default void matches(String actual, String expected) { matches(actual, expected, null); }

    void contains(String actual, String expected, String failMessage);

    default void contains(String actual, String expected) { contains(actual, expected, null); }

    void isTrue(Boolean condition, String failMessage);

    default void isTrue(Boolean condition) { isTrue(condition, null); }

    void isFalse(Boolean condition, String failMessage);

    default void isFalse(Boolean condition) { isFalse(condition, null); }
    <E extends Exception> void  throwException(String actionName, JAction action, Class<E> exceptionClass, String exceptionText);

    default void throwException(String actionName, JAction action, String exceptionText) {
        throwException(action, null, exceptionText);
    }
    default <E extends Exception> void throwException(String actionName, JAction action, Class<E> exceptionClass) {
        throwException(action, exceptionClass, "");
    }
    <E extends Exception> void throwException(JAction action, Class<E> exceptionClass, String exceptionText);

    default void throwException(JAction action, String exceptionText) {
        throwException(action, null, exceptionText);
    }
    default <E extends Exception> void throwException(JAction action, Class<E> exceptionClass) {
        throwException(action, exceptionClass, "");
    }
    void hasNoExceptions(String actionName, JAction action);

    void hasNoExceptions(JAction action);

    void isEmpty(Object obj, String failMessage);

    default void isEmpty(Object obj) { isEmpty(obj, null); }

    void isNotEmpty(Object obj, String failMessage);

    default void isNotEmpty(Object obj) { isNotEmpty(obj, null); }

    <T> void areDifferent(T actual, T expected, String failMessage);

    default <T> void areDifferent(T actual, T expected) { areDifferent(actual, expected, null); }

    <T> void listEquals(Collection<T> actual, Collection<T> expected, String failMessage);

    default <T> void listEquals(Collection<T> actual, Collection<T> expected) { listEquals(actual, expected, null); }

    default <T> void listContains(Collection<T> actual, T expected, String failMessage) { listContains(actual, asList(expected), failMessage);};

    default <T> void listContains(Collection<T> actual, T expected) { listContains(actual, asList(expected), null); }

    <T> void listContains(Collection<T> actual, Collection<T> expected, String failMessage);

    default <T> void listContains(Collection<T> actual, Collection<T> expected) { listContains(actual, expected, null); }

    <T> void arrayEquals(T actual, T expected, String failMessage);

    default <T> void arrayEquals(T actual, T expected) {
        arrayEquals(actual, expected, null);
    }

    <T> void entityIncludeMapArray(T entity, MapArray<String, String> actual, String failMessage);

    default <T> void entityIncludeMapArray(T entity, MapArray<String, String> expected) {
        entityIncludeMapArray(entity, expected, null);
    }

    <T> void entityEqualsToMapArray(T entity, MapArray<String, String> expected, String failMessage);

    default <T> void entityEqualsToMapArray(T entity, MapArray<String, String> expected) {
        entityEqualsToMapArray(entity, expected, null);
    }

    <T> void entityIncludeMap(T entity, Map<String, String> expected, String failMessage);

    default <T> void entityIncludeMap(T entity, Map<String, String> expected) {
        entityIncludeMap(entity, expected, null);
    }

    <T> void entityEqualsToMap(T entity, Map<String, String> expected, String failMessage);

    default <T> void entityEqualsToMap(T entity, Map<String, String> expected) {
        entityEqualsToMap(entity, expected, null);
    }

    void isSortedByAsc(int[] array, String failMessage);

    default void isSortedByAsc(int[] array) {
        isSortedByAsc(array, null);
    }

    default void isSortedByAsc(List<Integer> array, String failMessage) {
        isSortedByAsc(toIntArray(array), failMessage);
    }

    default void isSortedByAsc(List<Integer> array) {
        isSortedByAsc(toIntArray(array));
    }

    void isSortedByDesc(int[] array, String failMessage);

    default void isSortedByDesc(int[] array) {
        isSortedByDesc(array, null);
    }

    default void isSortedByDesc(List<Integer> array, String failMessage) {
        isSortedByDesc(toIntArray(array), failMessage);
    }

    default void isSortedByDesc(List<Integer> array) {
        isSortedByDesc(toIntArray(array));
    }

    <T> void areEquals(Supplier<T> actual, T expected, String failMessage);

    <T> void areEquals(Supplier<T> actual, T expected);

    void matches(Supplier<String> actual, String regEx, String failMessage);

    default void matches(Supplier<String> actual, String regEx) {
        matches(actual, regEx, null);
    }

    void contains(Supplier<String> actual, String expected, String failMessage);

    default void contains(Supplier<String> actual, String expected) {
        contains(actual, expected, null);
    }

    void isTrue(BooleanSupplier condition, String failMessage);

    default void isTrue(BooleanSupplier condition) {
        isTrue(condition, null);
    }

    void isFalse(BooleanSupplier condition, String failMessage);

    default void isFalse(BooleanSupplier condition) {
        isFalse(condition, null);
    }

    void isEmpty(Supplier<Object> obj, String failMessage);

    default void isEmpty(Supplier<Object> obj) {
        isEmpty(obj, null);
    }

    void isNotEmpty(Supplier<Object> obj, String failMessage);

    default void isNotEmpty(Supplier<Object> obj) {
        isNotEmpty(obj, null);
    }

    <T> void areSame(Supplier<T> actual, T expected, String failMessage);

    default <T> void areSame(Supplier<T> actual, T expected) {
        areSame(actual, expected, null);
    }

    <T> void areDifferent(Supplier<T> actual, T expected, String failMessage);

    default <T> void areDifferent(Supplier<T> actual, T expected) {
        areDifferent(actual, expected, null);
    }

    <T> void listEquals(Supplier<Collection<T>> actual, Collection<T> expected, String failMessage);

    default <T> void listEquals(Supplier<Collection<T>> actual, Collection<T> expected) {
        listEquals(actual, expected, null);
    }

    <T> void arrayEquals(Supplier<T> actual, T expected, String failMessage);

    default <T> void arrayEquals(Supplier<T> actual, T expected) {
        arrayEquals(actual, expected, null);
    }

    default <T> void entitiesAreEquals(T actual, T expected, String failMessage) {
        listEquals(getFields(actual.getClass()), getFields(expected.getClass()), failMessage);
    }
    default  <T> void entitiesAreEquals(T actual, T expected) {
        entitiesAreEquals(actual, expected, null);
    }
    <T> void entityIncludeMapArray(T entity, Supplier<MapArray<String, String>> expected, String failMessage);

    default <T> void entityIncludeMapArray(T entity, Supplier<MapArray<String, String>> expected) {
        entityIncludeMapArray(entity, expected, null);
    }

    <T> void entityEqualsToMapArray(T entity, Supplier<MapArray<String, String>> expected, String failMessage);

    default <T> void entityEqualsToMapArray(T entity, Supplier<MapArray<String, String>> expected) {
        entityEqualsToMapArray(entity, expected, null);
    }

    <T> void entityIncludeMap(T entity, Supplier<Map<String, String>> expected, String failMessage);

    default <T> void entityIncludeMap(T entity, Supplier<Map<String, String>> expected) {
        entityIncludeMap(entity, expected, null);
    }

    <T> void entityEqualsToMap(T entity, Supplier<Map<String, String>> expected, String failMessage);

    default <T> void entityEqualsToMap(T entity, Supplier<Map<String, String>> expected) {
        entityEqualsToMap(entity, expected, null);
    }

    <T> BaseMatcher.ListChecker eachElementOf(Collection<T> list);

    <T> BaseMatcher.ListChecker eachElementOf(T[] array);
}