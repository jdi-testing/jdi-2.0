package com.epam.jdi.uitests.core.interfaces.composite;
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
import com.epam.jdi.uitests.core.interfaces.base.IComposite;
import com.epam.jdi.uitests.core.interfaces.base.INamed;

import static com.epam.jdi.uitests.core.actions.base.ElementActions.assertTrue;
import static com.epam.jdi.uitests.core.actions.composite.PageActions.isOpened;
import static com.epam.jdi.uitests.core.actions.composite.PageActions.openPage;
import static com.epam.jdi.uitests.core.settings.JDISettings.*;
import static java.lang.String.format;

public interface IPage extends IComposite, INamed {
    /**
     * Wait while page become opened.
     * If not throws error
     */
    @JDIAction
    default void waitOpened() {
        assertTrue.execute(this, isOpened.execute(this));
    }
    /**
     * Check that page is opened
     */
    @JDIAction
    boolean isOpened();
    /**
     * Check that page is opened
     * If not then open it
     */
    @JDIAction
    default void shouldBeOpened() {
        try {
            logger.info(format("Page '%s' should be opened", getName()));
            if (isOpened()) return;
            open();
            assertOpened();
        } catch (Exception ex) {
            throw exception(format("Can't open page '%s'. Reason: %s", getName(), ex.getMessage()));
        }
    }
    /**
     * Opens Application page
     */
    @JDIAction
    default <T extends IPage> T open() {
        openPage.execute(this);
        return (T) this;
    }
    /**
     * Assert that this page opened
     */
    @JDIAction
    default void assertOpened() {
        asserter.isTrue(isOpened(), format("Page '%s' is not opened", toString()));
    }
    /**
     * Assert that this page opened
     */
    @JDIAction
    default void checkOpened() { assertOpened(); }

}