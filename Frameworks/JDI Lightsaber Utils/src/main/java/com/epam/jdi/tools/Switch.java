package com.epam.jdi.tools;

import com.epam.jdi.tools.func.JFunc1;

import static com.epam.jdi.tools.LinqUtils.filter;
import static com.epam.jdi.tools.LinqUtils.foreach;

/**
 * Created by Roman_Iovlev on 1/14/2018.
 */
public class Switch<T> {
    private T value;
    Switch() { }
    Switch(T value) { this.value = value; }
    public void of(Case<T>... pairs) {
        foreach(filter(pairs,
            p -> p.condition.execute(value)),
            p -> p.action.execute(value));
    }
    public <R> R get(CaseR<T, R>... pairs) {
        return LinqUtils.first(pairs, p -> p.condition.execute(value))
                .result.execute(value);
    }
    public static <T,R> CaseR<T,R> Case(JFunc1<T, Boolean> value, JFunc1<T, R> result) {
        return new CaseR<>(value, result);
    }
    public static <T,R> CaseR<T,R> Case(JFunc1<T, Boolean> value, R result) {
        return new CaseR<>(value, t -> result);
    }
    public static <T,R> CaseR<T,R> Condition(Boolean value, JFunc1<T, R> result) {
        return new CaseR<>(t -> value, result);
    }
    public static <T,R> CaseR<T,R> Condition(Boolean value, R result) {
        return new CaseR<>(t -> value, t -> result);
    }
    public static <T,R> CaseR<T,R> Value(T value, JFunc1<T, R> result) {
        return new CaseR<>(t -> t.equals(value), result);
    }
    public static <T,R> CaseR<T,R> Value(T value, R result) {
        return new CaseR<>(t -> t.equals(value), t -> result);
    }
    public static <T,R> CaseR<T,R> Default(JFunc1<T, R> result) {
        return new CaseR<>(t -> true, result);
    }
    public static <T,R> CaseR<T,R> Default(R result) {
        return new CaseR<>(t -> true, t -> result);
    }
    public static <T,R> CaseR<T,R> Else(JFunc1<T, R> result) {
        return new CaseR<>(t -> true, result);
    }
    public static <T,R> CaseR<T,R> Else(R result) {
        return new CaseR<>(t -> true, t -> result);
    }
}
