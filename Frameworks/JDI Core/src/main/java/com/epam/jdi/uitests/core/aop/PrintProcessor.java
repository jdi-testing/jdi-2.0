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
import com.epam.jdi.tools.func.JFunc2;
import com.epam.jdi.uitests.core.annotations.Print;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.text.MessageFormat;

import static com.epam.jdi.uitests.core.annotations.AnnotationsUtil.splitCamelCase;

@Aspect
public class PrintProcessor {
    public static JFunc2<String, ProceedingJoinPoint, Object> action = (text, action) -> {
        System.out.println(text);
        try {
            return action.proceed();
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    };
    public static String DEFAULT_PRINT = "Do %s action";

    @Around("execution(* *(..)) && @annotation(com.epam.jdi.uitests.core.annotations.Print)")
    public Object action(ProceedingJoinPoint joinPoint) {
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        Print printAnnotation = method.getMethod().getAnnotation(Print.class);
        String actionName = printAnnotation.value().equals("")
                ? String.format(DEFAULT_PRINT, splitCamelCase(method.getName()))
                : printAnnotation.value();
        if (joinPoint.getArgs().length > 0)
            actionName = MessageFormat.format(actionName, joinPoint.getArgs());
        return action.execute(actionName, joinPoint);
    }

}
