package com.epam.jdi.uitests.core.interfaces.complex;
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
import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IBaseElement;
import com.epam.jdi.uitests.core.interfaces.base.IClickable;
import com.epam.jdi.uitests.core.interfaces.base.IComposite;

public interface IPagination extends IBaseElement, IComposite {
    /**
     * Choose Next page
     */
    @JDIAction
    default void next() { ((IClickable)linked().get("next")).click(); }

    /**
     * Choose Previous page
     */
    @JDIAction
    default void previous() { ((IClickable)linked().get("prev")).click(); }

    /**
     * hoose First page
     */
    @JDIAction
    default void first() { ((IClickable)linked().get("first")).click(); }

    /**
     * Choose Last page
     */
    @JDIAction
    default void last() { ((IClickable)linked().get("last")).click(); }

    /**
     * @param index Specify page index
     *              Choose page by index
     */
    @JDIAction
    default void selectPage(int index) {
        ((ISelector)linked().get("page")).select(index);
    }
}