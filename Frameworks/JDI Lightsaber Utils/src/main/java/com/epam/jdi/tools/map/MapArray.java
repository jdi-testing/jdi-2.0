package com.epam.jdi.tools.map;
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
 * Created by Roman Iovlev on 10.27.2017
 */
import com.epam.jdi.tools.LinqUtils;
import com.epam.jdi.tools.func.JAction2;
import com.epam.jdi.tools.func.JFunc1;
import com.epam.jdi.tools.func.JFunc2;
import com.epam.jdi.tools.pairs.Pair;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.epam.jdi.tools.PrintUtils.print;
import static com.epam.jdi.tools.TryCatchUtil.throwRuntimeException;
import static java.lang.String.format;

public class MapArray<K, V> implements Collection<Pair<K, V>>, Cloneable {
    public List<Pair<K, V>> pairs;

    public MapArray() {
        pairs = new ArrayList<>();
    }

    public MapArray(K key, V value) {
        this();
        add(key, value);
    }

    public <T> MapArray(Collection<T> collection, JFunc1<T, K> key, JFunc1<T, V> value) {
        this();
        try {
            for (T t : collection)
                add(key.invoke(t), value.invoke(t));
        } catch (Exception ex) {
            throw new RuntimeException("Can't create MapArray"); }
    }

    public MapArray(Collection<K> collection, JFunc1<K, V> value) {
        this();
        try {
            for (K k : collection)
                add(k, value.invoke(k));
        } catch (Exception ex) {
            throw new RuntimeException("Can't create MapArray"); }
    }

    public <T> MapArray(T[] array, JFunc1<T, K> key, JFunc1<T, V> value) {
        this();
        try {
            for (T t : array)
                add(key.invoke(t), value.invoke(t));
        } catch (Exception ex) {
            throw new RuntimeException("Can't create MapArray"); }
    }

    public MapArray(K[] array, JFunc1<K, V> value) {
        this();
        try {
        for (K k : array)
            add(k, value.invoke(k));
        } catch (Exception ex) {
            throw new RuntimeException("Can't create MapArray"); }
    }

    public MapArray(int count, JFunc1<Integer, K> key, JFunc1<Integer, V> value) {
        this();
        try {
        for (int i = 0; i < count; i++)
            add(key.invoke(i), value.invoke(i));
        } catch (Exception ex) {
            throw new RuntimeException("Can't create MapArray"); }
    }
    public MapArray(int count, JFunc1<Integer, Pair<K, V>> pairFunc) {
        this();
        try {
        for (int i = 0; i < count; i++)
            add(pairFunc.invoke(i));
        } catch (Exception ex) {
            throw new RuntimeException("Can't create MapArray"); }
    }

    public MapArray(MapArray<K, V> mapArray) {
        this();
        addAll(new ArrayList<>(mapArray));
    }
    public MapArray(Map<K, V> map) {
        this();
        for (Entry<K, V> entry : map.entrySet())
            add(entry.getKey(), entry.getValue());
    }

    public MapArray(Object[][] objects) {
        this();
        add(objects);
    }
    public MapArray(List<K> keys, List<V> values) {
        this();
        if (keys == null || values == null ||
            keys.size() == 0 || keys.size() != values.size())
            throw new RuntimeException("keys and values count not equal");
        for (int i = 0; i < keys.size(); i++)
            add(keys.get(i), values.get(i));
    }

    public static <T> MapArray<Integer, T> toMapArray(Collection<T> collection) {
        MapArray<Integer, T> mapArray = new MapArray<>();
        int i = 0;
        for (T t : collection)
            mapArray.add(i++, t);
        return mapArray;
    }

    public static <T> MapArray<Integer, T> toMapArray(T[] array) {
        Set<T> mySet = new HashSet<>();
        Collections.addAll(mySet, array);
        return toMapArray(mySet);
    }

    public static <Key, Value> MapArray<Key, Value> toMapArray(Map<Key, Value> map) {
        MapArray<Key, Value> mapArray = new MapArray<>();
        for (Entry<Key, Value> e : map.entrySet())
            mapArray.add(e.getKey(), e.getValue());
        return mapArray;
    }

    public <KResult, VResult> MapArray<KResult, VResult> toMapArray(
            JFunc2<K, V, KResult> key, JFunc2<K, V, VResult> value) {
        MapArray<KResult, VResult> result = new MapArray<>();
        try {
            for (Pair<K, V> pair : pairs)
                result.add(key.invoke(pair.key, pair.value), value.invoke(pair.key, pair.value));
        } catch (Exception ex) {
            throw new RuntimeException("Can't convert toMapArray"); }
        return result;
    }

    public <VResult> MapArray<K, VResult> toMapArray(JFunc1<V, VResult> value) {
        MapArray<K, VResult> result = new MapArray<>();
        try {
        for (Pair<K, V> pair : pairs)
            result.add(pair.key, value.invoke(pair.value));
        return result;
        } catch (Exception ex) {
            throw new RuntimeException("Can't convert toMapArray"); }
    }

    public Map<K, V> toMap() {
        return toMap(v -> v);
    }
    public <VResult> Map<K, VResult> toMap(JFunc1<V, VResult> value) {
        return toMap((k, v) -> k, (k,v) -> value.invoke(v));
    }
    public <KResult, VResult> Map<KResult, VResult> toMap(
            JFunc2<K, V, KResult> key, JFunc2<K, V, VResult> value) {
        Map<KResult, VResult> result = new HashMap<>();
        try {
            for (Pair<K, V> pair : pairs)
                result.put(key.invoke(pair.key, pair.value),
                        value.invoke(pair.key, pair.value));
        } catch (Exception ex) {
            throw new RuntimeException("Can't convert toMap"); }
        return result;
    }

    public MapArray<K, V> add(K key, V value) {
        if (!hasKey(key))
            pairs.add(new Pair<>(key, value));
        return this;
    }
    public MapArray<K, V> update(K key, V value) {
        if (hasKey(key))
            removeByKey(key);
        pairs.add(new Pair<>(key, value));
        return this;
    }


    public MapArray<K, V> update(K key, JFunc1<V, V> func) {
        V value = null;
        if (hasKey(key)) {
            value = get(key);
            removeByKey(key);
        }
        try {
            pairs.add(new Pair<>(key, func.invoke(value)));
        } catch (Exception ex) {
            throw new RuntimeException("Can't do update"); }
        return this;
    }

    public void add(Object[][] pairs) {
        for (Object[] pair : pairs)
            if (pair.length == 2)
                add(cast(pair[0]), cast(pair[1]));
    }
    private <R> R cast(Object obj) {
        try {
            return (R) obj;
        } catch (ClassCastException ex) {
            throw new ClassCastException(format("Can't cast element '%s' in MapArray", obj));
        }
    }

    public void addOrReplace(K key, V value) {
        if (hasKey(key))
            removeByKey(key);
        add(key, value);
    }

    public void addOrReplace(Object[][] pairs) {
        for (Object[] pair : pairs)
            if (pair.length == 2)
                addOrReplace((K) pair[0], (V) pair[1]);
    }

    private boolean hasKey(K key) {
        return keys().contains(key);
    }

    public MapArray<K, V> addFirst(K key, V value) {
        if (hasKey(key))
            throw new RuntimeException(format(
                "Can't addFirst element for key '%s'. MapArray already element with this key.", key));
        List<Pair<K, V>> result = new CopyOnWriteArrayList<>();
        result.add(new Pair<>(key, value));
        result.addAll(pairs);
        pairs = result;
        return this;
    }

    public V get(K key) {
        Pair<K, V> first = null;
        try {
            first = LinqUtils.first(pairs, pair -> pair.key.equals(key));
        } catch (Exception ignore) {
        }
        return (first != null) ? first.value : null;
    }

    public Pair<K, V> get(int i) {
        int index = i >= 0 ? i : pairs.size() + i;
        return index >= 0 && index < pairs.size()
            ? pairs.get(index)
            : null;
    }

    public K key(int index) {
        return get(index).key;
    }

    public V value(int index) {
        return get(index).value;
    }

    public List<K> keys() {
        return LinqUtils.map(pairs, pair -> pair.key);
    }

    public List<V> values() {
        return LinqUtils.map(pairs, pair -> pair.value);
    }

    public List<V> values(JFunc1<V, Boolean> condition) {
        return LinqUtils.filter(values(), condition);
    }

    public int size() {
        return pairs.size();
    }

    public int count() {
        return size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean any() {
        return size() > 0;
    }
    public boolean any(JFunc1<V, Boolean> func) {
        return LinqUtils.any(this.values(), func);
    }

    public Pair<K, V> first() {
        return get(0);
    }

    public Pair<K, V> last() {
        return get(-1);
    }

    public MapArray<K, V> revert() {
        List<Pair<K, V>> result = new CopyOnWriteArrayList<>();
        for (int i = size() - 1; i >= 0; i--)
            result.add(get(i));
        pairs = result;
        return this;
    }

    public boolean contains(Object o) {
        return values().contains(o);
    }

    public Iterator<Pair<K, V>> iterator() {
        return pairs.iterator();
    }

    public Object[] toArray() {
        return pairs.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return pairs.toArray(a);
    }

    public boolean add(Pair<K, V> kv) {
        return pairs.add(kv);
    }

    public boolean remove(Object o) {
        boolean isRemoved = false;
        for (Object kv : pairs)
            if (kv.equals(o)) {
                pairs.remove(kv);
                isRemoved = true;
            }
        return isRemoved;
    }

    public void removeByKey(K key) {
        pairs.remove(
            LinqUtils.firstIndex(pairs, pair -> pair.key.equals(key)));
    }

    public void removeAllValues(V value) {
        pairs.removeAll(LinqUtils.where(pairs,
                p -> p.value.equals(value)));
    }

    public boolean containsAll(Collection<?> c) {
        for (Object o : c)
            if (!contains(o))
                return false;
        return true;
    }

    public boolean addAll(Collection<? extends Pair<K, V>> c) {
        for (Pair<K, V> pair : c)
            add(pair);
        return true;
    }

    public boolean removeAll(Collection<?> c) {
        for (Object o : c)
            if (!remove(o))
                return false;
        return true;
    }

    public boolean retainAll(Collection<?> c) {
        for (Pair pair : pairs)
            if (!c.contains(pair))
                if (!remove(pair))
                    return false;
        return true;
    }

    public void clear() {
        pairs.clear();
    }

    @Override
    public String toString() {
        return print(LinqUtils.select(pairs, pair -> pair.key + ":" + pair.value));
    }

    @Override
    public MapArray<K, V> clone() {
        return new MapArray<>(this);
    }

    public MapArray<K, V> copy() {
        return clone();
    }

    public <T1> List<T1> select(JFunc2<K, V, T1> func) {
        try {
            List<T1> result = new ArrayList<>();
            for (Pair<K,V> pair : pairs)
                result.add(func.invoke(pair.key, pair.value));
            return result;
        } catch (Exception ignore) {
            throwRuntimeException(ignore);
            return new ArrayList<>();
        }
    }
    public <T1> List<T1> map(JFunc2<K, V, T1> func) {
        return select(func);
    }

    public MapArray<K, V> filter(JFunc2<K, V, Boolean> func) {
        return where(func);
    }
    public MapArray<K, V> where(JFunc2<K, V, Boolean> func) {
        try {
            MapArray<K, V> result = new MapArray<>();
            for (Pair<K,V> pair : pairs)
                if (func.invoke(pair.key, pair.value))
                    result.add(pair);
            return result;
        } catch (Exception ignore) {
            throwRuntimeException(ignore);
            return null;
        }
    }

    public V first(JFunc2<K, V, Boolean> func) {
        try {
            for (Pair<K, V> pair : pairs)
                if (func.invoke(pair.key, pair.value))
                    return pair.value;
            return null;
        } catch (Exception ignore) {
            throwRuntimeException(ignore);
            return null;
        }
    }
    public V last(JFunc2<K, V, Boolean> func) {
        V result = null;
        try {
            for (Pair<K, V> pair : pairs)
                if (func.invoke(pair.key, pair.value))
                    result = pair.value;
            return result;
        } catch (Exception ignore) {
            throwRuntimeException(ignore);
            return null;
        }
    }

    public void foreach(JAction2<K, V> action) {
        try {
            for (Pair<K, V> pair : pairs)
                action.invoke(pair.key, pair.value);
        } catch (Exception ignore) {
            throwRuntimeException(ignore);
        }
    }

    public <R> List<R> selectMany(JFunc2<K, V, List<R>> func) {
        try {
            List<R> result = new ArrayList<>();
            for (Pair<K, V> pair : pairs)
                result.addAll(func.invoke(pair.key, pair.value));
            return result;
        } catch (Exception ignore) {
            throwRuntimeException(ignore);
            return null;
        }
    }
}