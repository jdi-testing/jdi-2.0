package com.epam.jdi.tools.pairs;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.epam.jdi.tools.LinqUtils.select;
import static com.epam.jdi.tools.PrintUtils.print;

public class Pairs<TValue1, TValue2> extends ArrayList<Pair<TValue1, TValue2>> {
    // TODO update pairs to MapArray level
    // Tuple
    // Pull Tuple any get removes element
    public Pairs() {
    }

    public Pairs(List<Pair<TValue1, TValue2>> pairs) {
        if (pairs == null) return;
        pairs.forEach(this::add);
    }

    public Pairs(TValue1 value1, TValue2 value2, Collection<Pair<TValue1, TValue2>> pairs) {
        if (pairs != null)
            pairs.forEach(this::add);
        add(value1, value2);
    }

    public static <T, TValue1, TValue2> Pairs<TValue1, TValue2> toPairs(Iterable<T> list,
                Function<T, TValue1> selectorValue1, Function<T, TValue2> selectorValue2) {
        Pairs<TValue1, TValue2> pairs = new Pairs<>();
        for (T element : list)
            pairs.add(selectorValue1.apply(element), selectorValue2.apply(element));
        return pairs;
    }

    public Pairs<TValue1, TValue2> add(TValue1 value1, TValue2 value2) {
        this.add(new Pair(value1, value2));
        return this;
    }

    public Pairs<TValue1, TValue2> add(Pairs<TValue1, TValue2> pairs) {
        pairs.foreach(this::add);
        return this;
    }

    public void addNew(TValue1 value1, TValue2 value2) {
        clear();
        add(new Pair(value1, value2));
    }

    public void foreach(Consumer<Pair<TValue1, TValue2>> action) {
        this.forEach(action::accept);
    }

    public Pairs<TValue1, TValue2> subList(int from) {
        return new Pairs<>(subList(from, size()));
    }

    @Override
    public String toString() {
        return print(select(this, pair -> pair.key + ":" + pair.value));
    }

    @Override
    public Pairs<TValue1, TValue2> clone() {
        return new Pairs<>(this);
    }

    public Pairs<TValue1, TValue2> copy() {
        return clone();
    }
}