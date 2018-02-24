package com.epam.jdi.uitests.core.aop;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.tools.func.JAction1;
import com.epam.jdi.tools.func.JAction2;
import com.epam.jdi.tools.logger.LogLevels;
import com.epam.jdi.tools.map.MapArray;
import com.epam.jdi.uitests.core.annotations.JDIAction;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import ru.yandex.qatools.allure.annotations.Step;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static com.epam.jdi.tools.ReflectionUtils.getFields;
import static com.epam.jdi.tools.ReflectionUtils.getValueField;
import static com.epam.jdi.tools.StringUtils.msgFormat;
import static com.epam.jdi.tools.StringUtils.splitLowerCase;
import static com.epam.jdi.tools.logger.LogLevels.*;
import static com.epam.jdi.tools.map.MapArray.pairs;
import static com.epam.jdi.uitests.core.settings.JDISettings.exception;
import static com.epam.jdi.uitests.core.settings.JDISettings.logger;
import static java.lang.Character.toUpperCase;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@SuppressWarnings("unused")
@Aspect
public class ActionProcessor {
    public static String SHORT_TEMPLATE = "{element} {action}";
    public static String DEFAULT_TEMPLATE = "{action} ({element})";

    private static String getTemplate(LogLevels level) {
        return level.equalOrMoreThan(STEP) ? SHORT_TEMPLATE : DEFAULT_TEMPLATE;
    }

    public static JAction1<JoinPoint> jdiBefore = joinPoint -> {
        if (logger.getLogLevel() != OFF) {
            String actionName = getActionName(joinPoint);
            String logString = joinPoint.getThis() == null
                ? actionName
                : msgFormat(getTemplate(logger.getLogLevel()), pairs(new Object[][]{
                    {"action", actionName },
                    {"element", getElementName(joinPoint) }}));
            logString = toUpperCase(logString.charAt(0)) + logString.substring(1);
            logger.toLog(logString, logLevel(joinPoint));
        }
        logger.logOff();
    };
    public static JAction2<JoinPoint, Object> jdiAfter = (joinPoint, result) -> {
        logger.logOn();
        if (logger.getLogLevel() == OFF) return;
        if (result != null && logLevel(joinPoint).equalOrMoreThan(INFO))
            logger.info(">>> " + result);
        logger.debug("Done");
    };
    public static JAction2<JoinPoint, Throwable> jdiError = (joinPoint, error) -> {
        throw exception("Action %s failed. Can't get result. Reason: %s", getActionName(joinPoint), error.getMessage());
    };

    @Before("execution(* *(..)) && @annotation(com.epam.jdi.uitests.core.annotations.JDIAction)")
    public void before(JoinPoint joinPoint) {
        jdiBefore.execute(joinPoint);
    }

    @AfterReturning(pointcut = "execution(* *(..)) && @annotation(com.epam.jdi.uitests.core.annotations.JDIAction)", returning = "result")
    public void after(JoinPoint joinPoint, Object result) {
        jdiAfter.execute(joinPoint, result);
    }

    @AfterThrowing(pointcut = "execution(* *(..)) && @annotation(com.epam.jdi.uitests.core.annotations.JDIAction)", throwing = "error")
    public void error(JoinPoint joinPoint, Throwable error) {
        jdiError.execute(joinPoint, error);
    }

    @Before("execution(* *(..)) && @annotation(ru.yandex.qatools.allure.annotations.Step)")
    public void beforeStep(JoinPoint joinPoint) {
        jdiBefore.execute(joinPoint);
    }

    @AfterReturning(pointcut = "execution(* *(..)) && @annotation(ru.yandex.qatools.allure.annotations.Step)", returning = "result")
    public void afterStep(JoinPoint joinPoint, Object result) {
        jdiAfter.execute(joinPoint, result);
    }

    @AfterThrowing(pointcut = "execution(* *(..)) && @annotation(ru.yandex.qatools.allure.annotations.Step)", throwing = "error")
    public void errorStep(JoinPoint joinPoint, Throwable error) {
        jdiError.execute(joinPoint, error);
    }

    static MethodSignature getMethod(JoinPoint joinPoint) {
        return (MethodSignature) joinPoint.getSignature();
    }

    static String methodNameTemplate(MethodSignature method) {
        try {
            Method m = method.getMethod();
            return m.isAnnotationPresent(JDIAction.class)
                    ? m.getAnnotation(JDIAction.class).value()
                    : m.getAnnotation(Step.class).value();
        } catch (Exception ex) {
            throw new RuntimeException("Surround method issue: " +
                    "Can't get method name template: " + ex.getMessage());
        }
    }

    static LogLevels logLevel(JoinPoint joinPoint) {
        Method m = getMethod(joinPoint).getMethod();
        return m.isAnnotationPresent(JDIAction.class)
                ? m.getAnnotation(JDIAction.class).level()
                : STEP;
    }

    static String getActionName(JoinPoint joinPoint) {
        try {
            MethodSignature method = getMethod(joinPoint);
            return getActionName(method, methodNameTemplate(method),
                    new MapArray<>("this", getElementName(joinPoint)),
                    new MapArray<>(method.getParameterNames(), joinPoint.getArgs()),
                    new MapArray<>(getThisFields(joinPoint), Field::getName, value -> getValueField(value, joinPoint.getThis())));
        } catch (Exception ex) {
            throw new RuntimeException("Surround method issue: " +
                    "Can't get action name: " + ex.getMessage());
        }
    }
    static String getElementName(JoinPoint joinPoint) {
        Object obj = joinPoint.getThis();
        return obj != null
                ? obj.toString()
                : joinPoint.getSignature().getDeclaringType().getSimpleName();
    }
    static List<Field> getThisFields(JoinPoint joinPoint) {
        Object obj = joinPoint.getThis();
        return obj != null
                ? getFields(obj)
                : asList(joinPoint.getSignature().getDeclaringType().getFields());
    }
    static String getActionName(MethodSignature method, String value,
                                MapArray<String, Object>... args) {
        String result;
        try {
            if (isEmpty(value)) {
                result = splitLowerCase(method.getMethod().getName());
                if (args[1].size() == 1)
                    result += " '" + args[1].values().get(0) + "'";
            } else {
                result = value;
                result = msgFormat(result, args[1].values());
                for (MapArray<String, Object> params : args)
                    result = msgFormat(result, params);
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Surround method issue: " +
                    "Can't get action name: " + ex.getMessage());
        }
    }
}
/*    private String processNameTemplate(
            String template, MapArray<String, Object> params, MapArray<String, Object> fields) {
        final Matcher matcher = Pattern.compile("\\{([^}]*)}").matcher(template);
        final StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            final String pattern = matcher.group(1);
            String replacement = processPattern(pattern, params);
            if (replacement == null)
                replacement = processPattern(pattern, fields);
            if (replacement == null) replacement = matcher.group();
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    @SuppressWarnings("ReturnCount")
    private static String processPattern(String pattern, MapArray<String, Object> params) {
        final String[] parts = pattern.split("\\.");
        final String parameterName = parts[0];
        if (!params.keys().contains(parameterName))
            return null;
        final Object param = params.get(parameterName);
        return extractProperties(param, parts, 1);
    }

    @SuppressWarnings("ReturnCount")
    private static String extractProperties(final Object object, final String[] parts, final int index) {
        if (Objects.isNull(object)) {
            return "null";
        }
        if (index < parts.length) {
            if (object instanceof Object[]) {
                return Stream.of((Object[]) object)
                        .map(child -> extractProperties(child, parts, index))
                        .collect(JOINER);
            }
            if (object instanceof Iterable) {
                final Spliterator<?> iterator = ((Iterable) object).spliterator();
                return StreamSupport.stream(iterator, false)
                        .map(child -> extractProperties(child, parts, index))
                        .collect(JOINER);
            }
            final Object child = on(object).get(parts[index]);
            return extractProperties(child, parts, index + 1);
        }
        if (object instanceof Object[]) {
            return Arrays.toString((Object[]) object);
        }
        return String.valueOf(object);
    }
*/
