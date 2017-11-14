package com.epam.jdi.uitests.web.robot;
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

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import static com.epam.jdi.tools.Timer.sleep;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static java.awt.event.KeyEvent.*;

public final class JRobot {
    private JRobot() { }

    public static void pasteText(CharSequence text) {
        try {
            Robot robot;
            try {
                robot = new Robot();
            } catch (Exception ex) {
                throw exception("Can't instantiate Robot");
            }
            StringSelection stringSelection = new StringSelection(text.toString());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, (clipboard1, contents) -> {
            });
            sleep(1000);
            robot.keyPress(VK_CONTROL);
            robot.keyPress(VK_V);

            robot.keyRelease(VK_CONTROL);
            robot.keyPress(VK_ENTER);
            robot.keyRelease(VK_ENTER);
        } catch (Exception ex) {
            throw exception("Robot Input exception");
        }
    }
}