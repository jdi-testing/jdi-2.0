package com.epam.jdi.uitests.web.testng.testRunner;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */


import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

import static com.epam.jdi.uitests.core.settings.JDIData.testName;
import static com.epam.jdi.uitests.core.settings.JDISettings.logger;
import static com.epam.matcher.base.Verify.getFails;
import static org.testng.ITestResult.FAILURE;

public class TestNGListener implements IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (iInvokedMethod.isTestMethod()) {
            Method testMethod = iInvokedMethod.getTestMethod().getConstructorOrMethod().getMethod();
            if (testMethod.isAnnotationPresent(Test.class)) {
                testName = testMethod.getName();
                logger.info("== Test '%s' started ==", testName);
            }
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        if (method.isTestMethod()) {
            List<String> fails = getFails();
            if (!fails.isEmpty()) {
                fails.forEach(logger::error);
                result.setStatus(FAILURE);
            }
            logger.info("=== Test '%s' %s ===", testName, getTestResult(result));
        }
    }

    private String getTestResult(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                return "passed";
            case ITestResult.SKIP:
                return "skipped";
            default:
                return "failed";
        }
    }


}