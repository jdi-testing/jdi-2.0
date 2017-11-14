package com.epam.jdi.http.requests;

import com.epam.jdi.http.ExceptionHandler;
import com.epam.jdi.http.annotations.*;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.jdi.tools.LinqUtils.where;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.asList;

/**
 * Created by Roman_Iovlev on 12/19/2016.
 */
public class ServiceInit {
    public static <T> T init(Class<T> c) {
        List<Field> methods = where(c.getDeclaredFields(),
                f -> f.getType().equals(RestMethod.class));
        for (Field method: methods) {
            try {
                method.setAccessible(true);
                if (isStatic(method.getModifiers()))
                    method.set(null, getRestMethod(method, c));
                if (!isStatic(method.getModifiers()) && method.get(getService(c)) == null)
                    method.set(getService(c), getRestMethod(method, c));
            } catch (IllegalAccessException ex) {
                throw ExceptionHandler.exception("Can't init method %s for class %s", method.getName(), c.getName()); }
        }
        return getService(c);
    }
    private static Object service;
    private static <T> T getService(Class<T> c) {
        if (service != null) return (T) service;
        try {
            return (T) (service = c.newInstance());
        } catch (IllegalAccessException|InstantiationException ex) {
            throw ExceptionHandler.exception(
                "Can't instantiate class %s, Service class should have empty constructor",
                    c.getSimpleName()); }
    }
    private static <T> RestMethod getRestMethod(Field field, Class<T> c) {
        MethodData mad = getMethodData(field);
        String url = getUrlFromDomain(getDomain(c), mad.getUrl(), field.getName(), c.getSimpleName());
        RestMethod method = new RestMethod(url, mad.getType());
        if (field.isAnnotationPresent(ContentType.class))
            method.spec.contentType(field.getAnnotation(ContentType.class).value());
        if (field.isAnnotationPresent(Header.class))
            method.addHeader(field.getAnnotation(Header.class));
        if (field.isAnnotationPresent(Headers.class)) {
            List<Header> headers = asList(
                field.getAnnotation(Headers.class).value());
            method.addHeaders(headers);
        }
        return method;
    }

    private static MethodData getMethodData(Field method) {
        if (method.isAnnotationPresent(GET.class))
            return new MethodData(method.getAnnotation(GET.class).value(), RestMethodTypes.GET);
        if (method.isAnnotationPresent(POST.class))
            return new MethodData(method.getAnnotation(POST.class).value(), RestMethodTypes.POST);
        if (method.isAnnotationPresent(PUT.class))
            return new MethodData(method.getAnnotation(PUT.class).value(), RestMethodTypes.PUT);
        if (method.isAnnotationPresent(DELETE.class))
            return new MethodData(method.getAnnotation(DELETE.class).value(), RestMethodTypes.DELETE);
        if (method.isAnnotationPresent(PATCH.class))
            return new MethodData(method.getAnnotation(PATCH.class).value(), RestMethodTypes.PATCH);
        return new MethodData(null, RestMethodTypes.GET);
    }
    private static String getUrlFromDomain(String domain, String uri, String methodName, String className) {
        if (uri == null)
            return null;
        if (uri.contains("://"))
            return uri;
        if (domain == null)
            throw ExceptionHandler.exception(
            "Can't instantiate method '%s' for service '%s'. " +
                    "Domain undefined and method url not contains '://'",
                    methodName, className);
        return domain.replaceAll("/*$", "") + "/" + uri.replaceAll("^/*", "");
    }
    private static <T> String getDomain(Class<T> c) {
        return c.isAnnotationPresent(ServiceDomain.class)
                ? c.getAnnotation(ServiceDomain.class).value()
                : null;
    }
}
