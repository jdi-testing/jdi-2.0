package com.epam.jdi.uitests.core.aop;

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

import com.epam.jdi.tools.func.JAction3;
import com.epam.jdi.tools.func.JAction4;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import static com.epam.jdi.uitests.core.annotations.AnnotationsUtil.splitCamelCase;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static com.epam.jdi.uitests.core.settings.JDISettings.logger;
import static java.lang.String.format;

@SuppressWarnings("unused")
@Aspect
public class ActionProcessor {
    public static JAction3<String, Object, Method> jdiBefore = (actionName, element, action) -> {
        logger.info(format("%s for %s", actionName, element.toString()));
    };
    public static JAction4<String, Object, Object, Method> jdiAfter = (actionName, result, element, action) -> {
        if (result != null)
            logger.info("Get result: " + result);
        logger.debug("Done");
    };
    public static JAction4<String, Throwable, Object, Method> jdiError = (actionName, error, element, action) -> {
        throw exception("Do action %s failed. Can't get result. Reason: %s", actionName, error.getMessage());
    };

    @Before("execution(* *(..)) && @annotation(com.epam.jdi.uitests.core.annotations.JDIAction)")
    public void before(JoinPoint joinPoint) {
        jdiBefore.execute(getActionName(joinPoint), joinPoint.getThis(), getMethod(joinPoint));
    }
    @AfterReturning(pointcut = "execution(* *(..)) && @annotation(com.epam.jdi.uitests.core.annotations.JDIAction)", returning = "result")
    public void after(JoinPoint joinPoint, Object result) {
        jdiAfter.execute(getActionName(joinPoint), result, joinPoint.getThis(), getMethod(joinPoint));
    }
    @AfterThrowing(pointcut = "execution(* *(..)) && @annotation(com.epam.jdi.uitests.core.annotations.JDIAction)", throwing = "error")
    public void error(JoinPoint joinPoint, Throwable error) {
        jdiError.execute(getActionName(joinPoint), error, joinPoint.getThis(), getMethod(joinPoint));
    }

    private String getActionName(JoinPoint joinPoint) {
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        JDIAction jdiAnnotation = method.getMethod().getAnnotation(JDIAction.class);
        String actionName = jdiAnnotation.value().equals("")
                ? "Do " + splitCamelCase(method.getName()) + " action"
                : jdiAnnotation.value();
        if (joinPoint.getArgs().length > 0)
            actionName = MessageFormat.format(actionName, joinPoint.getArgs());
        return actionName;
    }

    private Method getMethod(JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod();
    }

}
