package com.epam.jdi.uitests.core.interfaces.composite;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.uitests.core.annotations.JDIAction;
import com.epam.jdi.uitests.core.interfaces.base.IComposite;
import com.epam.jdi.uitests.core.interfaces.base.INamed;

import static com.epam.jdi.uitests.core.actions.base.ElementActions.assertTrue;
import static com.epam.jdi.uitests.core.actions.composite.PageActions.isOpened;
import static com.epam.jdi.uitests.core.actions.composite.PageActions.openPage;
import static com.epam.jdi.uitests.core.settings.JDISettings.*;
import static java.lang.String.format;

/**
 * Interface declares methods that are common for major amount of pages (check if opened, open...)
 */
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
    default void open() {
        openPage.execute(this);
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