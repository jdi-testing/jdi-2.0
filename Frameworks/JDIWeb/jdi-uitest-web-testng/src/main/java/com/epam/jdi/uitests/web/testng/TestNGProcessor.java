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

import static com.epam.jdi.uitests.core.logger.LogLevels.STEP;
import static com.epam.jdi.uitests.web.settings.JDITestNGSettings.logger;

/**
 * Class for TestNG Processor, includes before and after calls and timer
 */
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

    /**
     * Calls joinpoint from testNGBefore, if the latest is not null
     * @param joinPoint JoinPoint to call
     */
    @Before("execution(* *(..)) && @annotation(org.testng.annotations.Test)")
    public void before(JoinPoint joinPoint) {
        if (testNGBefore != null)
            testNGBefore.execute(joinPoint);
    }

    /**
     * Calls joinpoint from testNGAfter, if the latest is not null
     * @param joinPoint JoinPoint to call
     */
    @After("execution(* *(..)) && @annotation(org.testng.annotations.Test)")
    public void after(JoinPoint joinPoint) {
        if (testNGAfter != null)
            testNGAfter.execute(joinPoint);
    }

    /**
     * Returns name of test for JoinPoint
     * @param joinPoint JoinPoint to get test name from
     * @return test name: SimpleName + name.
     */
    static String getTestName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringType().getSimpleName()
                + "." + getMethod(joinPoint).getName();
    }

    /**
     * Returns Signature of passed joinpoint
     * @param joinPoint joinpoint to get signature from
     * @return Signature of joinpoint
     */
    static MethodSignature getMethod(JoinPoint joinPoint) {
        return (MethodSignature) joinPoint.getSignature();
    }

}
