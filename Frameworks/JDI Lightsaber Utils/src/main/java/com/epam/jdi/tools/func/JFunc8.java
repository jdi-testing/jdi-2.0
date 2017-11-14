package com.epam.jdi.tools.func;
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
@FunctionalInterface
public interface JFunc8<T1, T2, T3, T4, T5, T6, T7, T8, R> {
    R invoke(T1 val1, T2 val2, T3 val3, T4 val4, T5 val5,
             T6 val6, T7 val7, T8 val8) throws Exception;

    default R execute(T1 val1, T2 val2, T3 val3, T4 val4, T5 val5,
                      T6 val6, T7 val7, T8 val8) {
        try {
            return invoke(val1, val2, val3, val4, val5, val6, val7, val8);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}