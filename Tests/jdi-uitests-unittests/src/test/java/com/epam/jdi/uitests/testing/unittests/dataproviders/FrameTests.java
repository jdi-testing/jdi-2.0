package com.epam.jdi.uitests.testing.unittests.dataproviders;

import com.epam.jdi.uitests.testing.unittests.W3CInit;
import com.epam.matcher.testng.Assert;
import org.testng.annotations.Test;

import static com.epam.jdi.uitests.testing.unittests.pageobjects.w3cSite.W3cSite.framePage;

/**
 * Created by Dmitry_Lebedev1 on 10/15/2015.
 */
public class FrameTests extends W3CInit {
    @Test
    public void fillTest() {
        framePage.tryIt.click();
        Assert.areEquals(() -> framePage.frame.label.getText(), "Click Me!");
    }

}