package com.epam.jdi.uitests.core.aop;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JFunc2;
import com.epam.jdi.uitests.core.annotations.Print;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.text.MessageFormat;

import static com.epam.jdi.tools.StringUtils.splitCamelCase;

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
