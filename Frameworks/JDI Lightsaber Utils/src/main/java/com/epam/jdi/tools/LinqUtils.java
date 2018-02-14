package com.epam.jdi.tools;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction1;
import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.tools.func.JFunc2;
import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.tools.pairs.Pair;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.IntStream.rangeClosed;

public final class LinqUtils {
    private LinqUtils() {
    }

    public static <T> List<T> copyList(Collection<T> list) {
        List<T> result = new ArrayList<>();
        result.addAll(list);
        return result;
    }

    public static <T, TR> List<TR> select(Collection<T> list, JFunc1<T, TR> func) {
        if (list == null)
            throw new RuntimeException("Can't do select. Collection is Null");
        try {
            List<TR> result = new CopyOnWriteArrayList<>();
            for (T el : list)
                result.add(func.invoke(el));
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do select. Exception: " + ex.getMessage());
        }
    }
    public static <T, TR> List<TR> map(Collection<T> list, JFunc1<T, TR> func) { return select(list, func); }
    public static <T, TR> List<TR> select(T[] array, JFunc1<T, TR> func) {
        return select(asList(array), func);
    }
    public static <T, TR> List<TR> map(T[] array, JFunc1<T, TR> func) { return select(array, func); }

    public static <K, V, R> List<R> selectMap(Map<K, V> map, JFunc1<Map.Entry<K, V>, R> func) {
        if (map == null)
            throw new RuntimeException("Can't do selectMap. Collection is Null");
        try {
            List<R> result = new CopyOnWriteArrayList<>();
            for (Map.Entry<K,V> el : map.entrySet())
                result.add(func.invoke(el));
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do select. Exception: " + ex.getMessage());
        }
    }

    public static <K, V, TR> Map<K, TR> select(Map<K, V> map, JFunc1<V, TR> func) {
        if (map == null)
            throw new RuntimeException("Can't do select. Collection is Null");
        try {
            Map<K, TR> result = new HashMap<>();
            for (Map.Entry<K, V> el : map.entrySet())
                result.put(el.getKey(), func.invoke(el.getValue()));
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do select. Exception: " + ex.getMessage());
        }
    }
    public static <K, V, TR> Map<K, TR> map(Map<K, V> map, JFunc1<V, TR> func) {
        return select(map, func);
    }
    public static <N, T> Map<N, T> toMap(List<T> list, JFunc1<T, N> nameFunc) {
        Map<N, T> map = new HashMap<>();
        for(T el : list)
            map.put(nameFunc.execute(el), el);
        return map;
    }
    public static <K, V, T> Map<K, V> toMap(List<T> list, JFunc1<T, K> key, JFunc1<T, V> value) {
        Map<K, V> map = new HashMap<>();
        for(T el : list)
            map.put(key.execute(el), value.execute(el));
        return map;
    }
    public static <N, T> Map<N, T> toMap(T[] list, JFunc1<T, N> nameFunc) {
        return toMap(asList(list), nameFunc);
    }
    public static <K, V, T> Map<K, V> toMap(T[] list, JFunc1<T, K> key, JFunc1<T, V> value) {
        return toMap(asList(list), key, value);
    }
    public static <K, V, TR> List<TR> toList(Map<K, V> map, JFunc2<K, V, TR> func) {
        if (map == null)
            throw new RuntimeException("Can't do select. Collection is Null");
        try {
            List<TR> result = new CopyOnWriteArrayList<>();
            for (Map.Entry<K,V> el : map.entrySet())
                result.add(func.invoke(el.getKey(), el.getValue()));
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do select. Exception: " + ex.getMessage());
        }
    }

    public static <T> List<T> where(Collection<T> list, JFunc1<T, Boolean> func) {
        if (list == null)
            throw new RuntimeException("Can't do where. Collection is Null");
        try {
            List<T> result = new ArrayList<>();
            for (T el : list)
                if (func.invoke(el))
                    result.add(el);
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do where. Exception: " + ex.getMessage());
        }
    }
    public static <T> List<T> filter(Collection<T> list, JFunc1<T, Boolean> func) {
        return where(list, func);
    }
    public static <T> List<T> where(T[] list, JFunc1<T, Boolean> func) {
        return where(asList(list), func);
    }
    public static <T> List<T> filter(T[] list, JFunc1<T, Boolean> func) {
        return where(list, func);
    }
    public static <K, V> Map<K, V> where(Map<K, V> map, JFunc1<Map.Entry<K, V>, Boolean> func) {
        if (map == null)
            throw new RuntimeException("Can't do where. Collection is Null");
        try {
            Map<K, V> result = new HashMap<>();
            for (Map.Entry<K,V> el : map.entrySet())
                if (func.invoke(el))
                    result.put(el.getKey(), el.getValue());
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do where. Exception: " + ex.getMessage());
        }
    }
    public static <K, V> Map<K, V> filter(Map<K, V> map, JFunc1<Map.Entry<K, V>, Boolean> func) {
        return where(map, func);
    }
    public static <T> void foreach(Collection<T> list, JAction1<T> action) {
        if (list == null)
            throw new RuntimeException("Can't do foreach. Collection is Null");
        try {
            for (T el : list)
                action.invoke(el);
        } catch (Exception ex) {
            throw new RuntimeException("Can't do foreach. Exception: " + ex.getMessage());
        }
    }

    public static <T> void foreach(T[] list, JAction1<T> action) {
        foreach(asList(list), action);
    }

    public static <K, V> void foreach(Map<K, V> map, JAction1<Map.Entry<K, V>> action) {
        if (map == null)
            throw new RuntimeException("Can't do foreach. Collection is Null");
        try {
            for (Map.Entry e : map.entrySet())
                action.invoke(e);
        } catch (Exception ex) {
            throw new RuntimeException("Can't do foreach. Exception: " + ex.getMessage());
        }
    }

    public static <T> boolean any(Collection<T> list, JFunc1<T, Boolean> func) {
        return first(list, func) != null;
    }
    public static <T> boolean any(T[] list, JFunc1<T, Boolean> func) {
        return first(list, func) != null;
    }
    public static <T> int firstIndex(List<T> list, JFunc1<T, Boolean> func) {
        if (list == null || list.size() == 0)
            throw new RuntimeException("Can't get firstIndex. Collection is Null or empty");
        try {
            for (int i = 0; i < list.size(); i++)
                if (func.invoke(list.get(i)))
                    return i;
        } catch (Exception ex) {
            throw new RuntimeException("Can't get firstIndex. Exception: " + ex.getMessage());
        }
        return -1;
    }

    public static <T> int firstIndex(T[] array, JFunc1<T, Boolean> func) {
        try {
            if (array == null || array.length == 0)
                return -1;
            for (int i = 0; i < array.length; i++)
                if (func.invoke(array[i]))
                    return i;
            return -1;
        } catch (Exception ex) {
            throw new RuntimeException("Can't get firstIndex. Exception: " + ex.getMessage());
        }
    }

    public static <T> T first(Collection<T> list) {
        if (list == null || list.size() == 0)
            throw new RuntimeException("Can't do first. Collection is Null or empty");
        return list.iterator().next();
    }

    public static <T> T first(T[] list) {
        return first(asList(list));
    }

    public static <K, V> V first(Map<K, V> map) {
        if (map == null || map.size() == 0)
            throw new RuntimeException("Can't do first. Collection is Null");
        return map.entrySet().iterator().next().getValue();
    }

    public static <T> T first(Collection<T> list, JFunc1<T, Boolean> func) {
        if (list == null || list.size() == 0)
            return null;
        try {
            for (T el : list)
                if (func.invoke(el))
                    return el;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do first. Exception: " + ex.getMessage());
        }
        return null;
    }

    public static <T> T first(T[] list, JFunc1<T, Boolean> func) {
        return first(asList(list), func);
    }

    public static <K, V> V first(Map<K, V> map, JFunc1<K, Boolean> func) {
        if (map == null || map.size() == 0)
            throw new RuntimeException("Can't do first. Collection is Null or empty");
        try {
            for (Map.Entry<K, V> el : map.entrySet())
                if (func.invoke(el.getKey()))
                    return el.getValue();
        } catch (Exception ex) {
            throw new RuntimeException("Can't do first. Exception: " + ex.getMessage());
        }
        return null;
    }

    public static <K, V> V first(MapArray<K, V> map, JFunc1<K, Boolean> func) {
        if (map == null || map.size() == 0)
            throw new RuntimeException("Can't do first. Collection is Null or empty");
        try {
            for (Pair<K, V> pair : map.pairs)
                if (func.invoke(pair.key))
                    return pair.value;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do first. Exception: " + ex.getMessage());
        }
        return null;
    }

    public static <T> T last(Collection<T> list) {
        if (list == null || list.size() == 0)
            throw new RuntimeException("Can't do last. Collection is Null or empty");
        T result = null;
        for (T el : list)
            result = el;
        return result;
    }

    public static <T> T last(T[] list) {
        return last(asList(list));
    }

    public static <T> T last(Collection<T> list, JFunc1<T, Boolean> func) {
        if (list == null || list.size() == 0)
            throw new RuntimeException("Can't do last. Collection is Null");
        T result = null;
        try {
            for (T el : list)
                if (func.invoke(el))
                    result = el;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do last. Exception: " + ex.getMessage());
        }
        return result;
    }

    public static <T> T last(T[] list, JFunc1<T, Boolean> func) {
        return last(asList(list), func);
    }

    public static String[] toStringArray(Collection<String> collection) {
        if (collection == null)
            throw new RuntimeException("Can't do toStringArray. Collection is Null");
        return collection.toArray(new String[collection.size()]);
    }

    public static Integer[] toIntegerArray(Collection<Integer> collection) {
        if (collection == null)
            throw new RuntimeException("Can't do toIntegerArray. Collection is Null");
        Integer[] result = new Integer[collection.size()];
        Integer i = 0;
        for (Integer el : collection)
            result[i++] = el;
        return result;
    }
    public static int[] toIntArray(Collection<Integer> collection) {
        if (collection == null)
            throw new RuntimeException("Can't do toIntArray. Collection is Null");
        int[] result = new int[collection.size()];
        Integer i = 0;
        for (int el : collection)
            result[i++] = el;
        return result;
    }

    public static int getIndex(String[] array, String value) {
        if (array == null)
            throw new RuntimeException("Can't do index. Collection is Null");
        for (int i = 0; i < array.length; i++)
            if (array[i].equals(value))
                return i;
        return -1;
    }

    public static int getIndex(List<String> list, String value) {
        if (list == null)
            throw new RuntimeException("Can't do index. Collection is Null");
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).equals(value))
                return i;
        return -1;
    }

    public static <T> List<T> listCopy(List<T> list, int from) {
        return listCopy(list, from, list.size() - 1);
    }

    public static <T> List<T> listCopy(List<T> list, int from, int to) {
        if (from*to < 0)
            throw new RuntimeException(format("from and to should have same sign (%s, %s)", from, to));
        if (from < 0)
            from = list.size() + from - 1;
        if (to < 0)
            to = list.size() + to - 1;
        List<T> result = new ArrayList<>();
        for (int i = from; i <= to; i++)
            result.add(list.get(i));
        return result;
    }
    public static List<Integer> listOfRange(int start, int end) {
        return rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

    public static <T> List<T> listCopyUntil(List<T> list, int to) {
        return listCopy(list, 0, to);
    }

    public static <T, R> List<R> selectMany(List<T> list, JFunc1<T, List<R>> func) {
        try {
            List<R> result = new ArrayList<>();
            for (T el : list)
                result.addAll(func.invoke(el));
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do selectMany. Exception: " + ex.getMessage());
        }
    }

    public static <T> List<T> selectManyArray(List<T> list, JFunc1<T, T[]> func) {
        try {
                List<T> result = new ArrayList<>();
            for (T el : list)
                result.addAll(Arrays.asList(func.invoke(el)));
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do selectMany. Exception: " + ex.getMessage());
        }
    }

    public static <T> boolean listEquals(List<T> list1, List<T> list2) {
        if (list1 == null && list2 == null)
            return true;
        if (list1 == null || list2 == null
            || list1.size() != list2.size())
            return false;
        List<T> expectedList = new ArrayList<>(list2);
        for (T el1 : list1) {
            boolean removed = false;
            for (T el2 : expectedList) {
                if (el1.equals(el2)) {
                    removed = expectedList.remove(el2);
                    break;
                }
            }
            if (!removed)
                return false;
        }
        return true;
    }
    public static <T> boolean listEquals(List<T> list, T[] array) {
        return listEquals(list, asList(array));
    }
    public static <T> T get(List<T> list, int i) {
        int index = i >= 0 ? i : list.size() + i;
        return index >= 0 && index < list.size()
                ? list.get(index)
                : null;
    }
    public static <T> T get(T[] array, int i) {
        return asList(array).get(i);
    }

    public static <T> Switch<T> Switch() {
        return new Switch<>();
    }
    public static <T> Switch<T> Switch(T value) {
        return new Switch<>(value);
    }
    public static <T> boolean contains(List<T> list, T t) {
        return first(list, el -> el.equals(t)) != null;
    }
    public static <T> boolean contains(T[] list, T t) {
        return first(list, el -> el.equals(t)) != null;
    }
}