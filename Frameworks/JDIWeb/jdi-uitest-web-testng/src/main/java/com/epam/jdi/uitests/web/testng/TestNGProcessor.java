package com.epam.jdi.uitests.web.testng;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.Timer;
import com.epam.jdi.tools.func.JAction1;
import com.epam.jdi.tools.map.MapArray;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import static com.epam.jdi.tools.logger.LogLevels.STEP;
import static com.epam.jdi.uitests.web.settings.JDITestNGSettings.*;

@SuppressWarnings("unused")
@Aspect
public class TestNGProcessor {
    private static ThreadLocal<Timer> timer = new ThreadLocal<>();
    public static JAction1<JoinPoint> testNGBefore = joinPoint -> {
        MethodSignature method = getMethod(joinPoint);
        timer.set(new Timer());
        String paramsString = "";
        if (!logger.getLogLevel().equalOrMoreThan(STEP)) {
            MapArray params = new MapArray<>(method.getParameterNames(), joinPoint.getArgs());
            if (params.any())
                paramsString = params.toString() + " ";
        }
        logger.step("=== START %s %s===", getTestName(joinPoint), paramsString);
    };
    public static JAction1<JoinPoint> testNGAfter = joinPoint -> {
        String time = timer.get().timePassed();
        MethodSignature method = getMethod(joinPoint);
        String testName = method.getName();
        logger.step("=== END %s (%s) ===", getTestName(joinPoint), time);
    };

    @Before("execution(* *(..)) && @annotation(org.testng.annotations.Test)")
    public void before(JoinPoint joinPoint) {
        if (testNGBefore != null)
            testNGBefore.execute(joinPoint);
    }
    @After("execution(* *(..)) && @annotation(org.testng.annotations.Test)")
    public void after(JoinPoint joinPoint) {
        if (testNGAfter != null)
            testNGAfter.execute(joinPoint);
    }
    static String getTestName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringType().getSimpleName()
                + "." + getMethod(joinPoint).getName();
    }

    static MethodSignature getMethod(JoinPoint joinPoint) {
        return (MethodSignature) joinPoint.getSignature();
    }

}
