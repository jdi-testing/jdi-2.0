package com.epam.matcher.junit;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

public final class Assertions {
    private Assertions() { }

    private static JUnit matcher = new JUnit();

    public static <V> V fail(String message) {
        matcher.fail(message); return null;
    }
    public static <V> V fail() {
        return fail("Fail");
    }
    public static <V> V fail(String message, Throwable cause) {
        return fail(message);
    }
    public static <V> V fail(Throwable cause) {
        return fail(cause.toString());
    }

    public static <V> V fail(Supplier<String> messageSupplier) {
        return fail(messageSupplier.get());
    }

    public static void assertTrue(boolean condition) {
        matcher.isTrue(condition);
    }

    public static void assertTrue(boolean condition, Supplier<String> messageSupplier) {
        matcher.isTrue(condition, messageSupplier.get());
    }

    public static void assertTrue(BooleanSupplier booleanSupplier) {
        matcher.isTrue(booleanSupplier.getAsBoolean());
    }
    // TODO override all JUnit Assertions and add IChecker in same way
    /*
    public static void assertTrue(BooleanSupplier booleanSupplier, String message) {
        AssertTrue.assertTrue(booleanSupplier, message);
    }

    public static void assertTrue(boolean condition, String message) {
        AssertTrue.assertTrue(condition, message);
    }

    public static void assertTrue(BooleanSupplier booleanSupplier, Supplier<String> messageSupplier) {
        AssertTrue.assertTrue(booleanSupplier, messageSupplier);
    }

    public static void assertFalse(boolean condition) {
        AssertFalse.assertFalse(condition);
    }

    public static void assertFalse(boolean condition, String message) {
        AssertFalse.assertFalse(condition, message);
    }

    public static void assertFalse(boolean condition, Supplier<String> messageSupplier) {
        AssertFalse.assertFalse(condition, messageSupplier);
    }

    public static void assertFalse(BooleanSupplier booleanSupplier) {
        AssertFalse.assertFalse(booleanSupplier);
    }

    public static void assertFalse(BooleanSupplier booleanSupplier, String message) {
        AssertFalse.assertFalse(booleanSupplier, message);
    }

    public static void assertFalse(BooleanSupplier booleanSupplier, Supplier<String> messageSupplier) {
        AssertFalse.assertFalse(booleanSupplier, messageSupplier);
    }

    public static void assertNull(Object actual) {
        AssertNull.assertNull(actual);
    }

    public static void assertNull(Object actual, String message) {
        AssertNull.assertNull(actual, message);
    }

    public static void assertNull(Object actual, Supplier<String> messageSupplier) {
        AssertNull.assertNull(actual, messageSupplier);
    }

    public static void assertNotNull(Object actual) {
        AssertNotNull.assertNotNull(actual);
    }

    public static void assertNotNull(Object actual, String message) {
        AssertNotNull.assertNotNull(actual, message);
    }

    public static void assertNotNull(Object actual, Supplier<String> messageSupplier) {
        AssertNotNull.assertNotNull(actual, messageSupplier);
    }

    public static void assertEquals(short expected, short actual) {
        AssertEquals.assertEquals(expected, actual);
    }

    public static void assertEquals(short expected, short actual, String message) {
        AssertEquals.assertEquals(expected, actual, message);
    }

    public static void assertEquals(short expected, short actual, Supplier<String> messageSupplier) {
        AssertEquals.assertEquals(expected, actual, messageSupplier);
    }

    public static void assertEquals(byte expected, byte actual) {
        AssertEquals.assertEquals(expected, actual);
    }

    public static void assertEquals(byte expected, byte actual, String message) {
        AssertEquals.assertEquals(expected, actual, message);
    }

    public static void assertEquals(byte expected, byte actual, Supplier<String> messageSupplier) {
        AssertEquals.assertEquals(expected, actual, messageSupplier);
    }

    public static void assertEquals(int expected, int actual) {
        AssertEquals.assertEquals(expected, actual);
    }

    public static void assertEquals(int expected, int actual, String message) {
        AssertEquals.assertEquals(expected, actual, message);
    }

    public static void assertEquals(int expected, int actual, Supplier<String> messageSupplier) {
        AssertEquals.assertEquals(expected, actual, messageSupplier);
    }

    public static void assertEquals(long expected, long actual) {
        AssertEquals.assertEquals(expected, actual);
    }

    public static void assertEquals(long expected, long actual, String message) {
        AssertEquals.assertEquals(expected, actual, message);
    }

    public static void assertEquals(long expected, long actual, Supplier<String> messageSupplier) {
        AssertEquals.assertEquals(expected, actual, messageSupplier);
    }

    public static void assertEquals(char expected, char actual) {
        AssertEquals.assertEquals(expected, actual);
    }

    public static void assertEquals(char expected, char actual, String message) {
        AssertEquals.assertEquals(expected, actual, message);
    }

    public static void assertEquals(char expected, char actual, Supplier<String> messageSupplier) {
        AssertEquals.assertEquals(expected, actual, messageSupplier);
    }

    public static void assertEquals(float expected, float actual) {
        AssertEquals.assertEquals(expected, actual);
    }

    public static void assertEquals(float expected, float actual, String message) {
        AssertEquals.assertEquals(expected, actual, message);
    }

    public static void assertEquals(float expected, float actual, Supplier<String> messageSupplier) {
        AssertEquals.assertEquals(expected, actual, messageSupplier);
    }

    public static void assertEquals(float expected, float actual, float delta) {
        AssertEquals.assertEquals(expected, actual, delta);
    }

    public static void assertEquals(float expected, float actual, float delta, String message) {
        AssertEquals.assertEquals(expected, actual, delta, message);
    }

    public static void assertEquals(float expected, float actual, float delta, Supplier<String> messageSupplier) {
        AssertEquals.assertEquals(expected, actual, delta, messageSupplier);
    }

    public static void assertEquals(double expected, double actual) {
        AssertEquals.assertEquals(expected, actual);
    }

    public static void assertEquals(double expected, double actual, String message) {
        AssertEquals.assertEquals(expected, actual, message);
    }

    public static void assertEquals(double expected, double actual, Supplier<String> messageSupplier) {
        AssertEquals.assertEquals(expected, actual, messageSupplier);
    }

    public static void assertEquals(double expected, double actual, double delta) {
        AssertEquals.assertEquals(expected, actual, delta);
    }

    public static void assertEquals(double expected, double actual, double delta, String message) {
        AssertEquals.assertEquals(expected, actual, delta, message);
    }

    public static void assertEquals(double expected, double actual, double delta, Supplier<String> messageSupplier) {
        AssertEquals.assertEquals(expected, actual, delta, messageSupplier);
    }

    public static void assertEquals(Object expected, Object actual) {
        AssertEquals.assertEquals(expected, actual);
    }

    public static void assertEquals(Object expected, Object actual, String message) {
        AssertEquals.assertEquals(expected, actual, message);
    }

    public static void assertEquals(Object expected, Object actual, Supplier<String> messageSupplier) {
        AssertEquals.assertEquals(expected, actual, messageSupplier);
    }

    public static void assertArrayEquals(boolean[] expected, boolean[] actual) {
        AssertArrayEquals.assertArrayEquals(expected, actual);
    }

    public static void assertArrayEquals(boolean[] expected, boolean[] actual, String message) {
        AssertArrayEquals.assertArrayEquals(expected, actual, message);
    }

    public static void assertArrayEquals(boolean[] expected, boolean[] actual, Supplier<String> messageSupplier) {
        AssertArrayEquals.assertArrayEquals(expected, actual, messageSupplier);
    }

    public static void assertArrayEquals(char[] expected, char[] actual) {
        AssertArrayEquals.assertArrayEquals(expected, actual);
    }

    public static void assertArrayEquals(char[] expected, char[] actual, String message) {
        AssertArrayEquals.assertArrayEquals(expected, actual, message);
    }

    public static void assertArrayEquals(char[] expected, char[] actual, Supplier<String> messageSupplier) {
        AssertArrayEquals.assertArrayEquals(expected, actual, messageSupplier);
    }

    public static void assertArrayEquals(byte[] expected, byte[] actual) {
        AssertArrayEquals.assertArrayEquals(expected, actual);
    }

    public static void assertArrayEquals(byte[] expected, byte[] actual, String message) {
        AssertArrayEquals.assertArrayEquals(expected, actual, message);
    }

    public static void assertArrayEquals(byte[] expected, byte[] actual, Supplier<String> messageSupplier) {
        AssertArrayEquals.assertArrayEquals(expected, actual, messageSupplier);
    }

    public static void assertArrayEquals(short[] expected, short[] actual) {
        AssertArrayEquals.assertArrayEquals(expected, actual);
    }

    public static void assertArrayEquals(short[] expected, short[] actual, String message) {
        AssertArrayEquals.assertArrayEquals(expected, actual, message);
    }

    public static void assertArrayEquals(short[] expected, short[] actual, Supplier<String> messageSupplier) {
        AssertArrayEquals.assertArrayEquals(expected, actual, messageSupplier);
    }

    public static void assertArrayEquals(int[] expected, int[] actual) {
        AssertArrayEquals.assertArrayEquals(expected, actual);
    }

    public static void assertArrayEquals(int[] expected, int[] actual, String message) {
        AssertArrayEquals.assertArrayEquals(expected, actual, message);
    }

    public static void assertArrayEquals(int[] expected, int[] actual, Supplier<String> messageSupplier) {
        AssertArrayEquals.assertArrayEquals(expected, actual, messageSupplier);
    }

    public static void assertArrayEquals(long[] expected, long[] actual) {
        AssertArrayEquals.assertArrayEquals(expected, actual);
    }

    public static void assertArrayEquals(long[] expected, long[] actual, String message) {
        AssertArrayEquals.assertArrayEquals(expected, actual, message);
    }

    public static void assertArrayEquals(long[] expected, long[] actual, Supplier<String> messageSupplier) {
        AssertArrayEquals.assertArrayEquals(expected, actual, messageSupplier);
    }

    public static void assertArrayEquals(float[] expected, float[] actual) {
        AssertArrayEquals.assertArrayEquals(expected, actual);
    }

    public static void assertArrayEquals(float[] expected, float[] actual, String message) {
        AssertArrayEquals.assertArrayEquals(expected, actual, message);
    }

    public static void assertArrayEquals(float[] expected, float[] actual, Supplier<String> messageSupplier) {
        AssertArrayEquals.assertArrayEquals(expected, actual, messageSupplier);
    }

    public static void assertArrayEquals(float[] expected, float[] actual, float delta) {
        AssertArrayEquals.assertArrayEquals(expected, actual, delta);
    }

    public static void assertArrayEquals(float[] expected, float[] actual, float delta, String message) {
        AssertArrayEquals.assertArrayEquals(expected, actual, delta, message);
    }

    public static void assertArrayEquals(float[] expected, float[] actual, float delta, Supplier<String> messageSupplier) {
        AssertArrayEquals.assertArrayEquals(expected, actual, delta, messageSupplier);
    }

    public static void assertArrayEquals(double[] expected, double[] actual) {
        AssertArrayEquals.assertArrayEquals(expected, actual);
    }

    public static void assertArrayEquals(double[] expected, double[] actual, String message) {
        AssertArrayEquals.assertArrayEquals(expected, actual, message);
    }

    public static void assertArrayEquals(double[] expected, double[] actual, Supplier<String> messageSupplier) {
        AssertArrayEquals.assertArrayEquals(expected, actual, messageSupplier);
    }

    public static void assertArrayEquals(double[] expected, double[] actual, double delta) {
        AssertArrayEquals.assertArrayEquals(expected, actual, delta);
    }

    public static void assertArrayEquals(double[] expected, double[] actual, double delta, String message) {
        AssertArrayEquals.assertArrayEquals(expected, actual, delta, message);
    }

    public static void assertArrayEquals(double[] expected, double[] actual, double delta, Supplier<String> messageSupplier) {
        AssertArrayEquals.assertArrayEquals(expected, actual, delta, messageSupplier);
    }

    public static void assertArrayEquals(Object[] expected, Object[] actual) {
        AssertArrayEquals.assertArrayEquals(expected, actual);
    }

    public static void assertArrayEquals(Object[] expected, Object[] actual, String message) {
        AssertArrayEquals.assertArrayEquals(expected, actual, message);
    }

    public static void assertArrayEquals(Object[] expected, Object[] actual, Supplier<String> messageSupplier) {
        AssertArrayEquals.assertArrayEquals(expected, actual, messageSupplier);
    }

    public static void assertIterableEquals(Iterable<?> expected, Iterable<?> actual) {
        AssertIterableEquals.assertIterableEquals(expected, actual);
    }

    public static void assertIterableEquals(Iterable<?> expected, Iterable<?> actual, String message) {
        AssertIterableEquals.assertIterableEquals(expected, actual, message);
    }

    public static void assertIterableEquals(Iterable<?> expected, Iterable<?> actual, Supplier<String> messageSupplier) {
        AssertIterableEquals.assertIterableEquals(expected, actual, messageSupplier);
    }

    public static void assertLinesMatch(List<String> expectedLines, List<String> actualLines) {
        AssertLinesMatch.assertLinesMatch(expectedLines, actualLines);
    }

    public static void assertNotEquals(Object unexpected, Object actual) {
        AssertNotEquals.assertNotEquals(unexpected, actual);
    }

    public static void assertNotEquals(Object unexpected, Object actual, String message) {
        AssertNotEquals.assertNotEquals(unexpected, actual, message);
    }

    public static void assertNotEquals(Object unexpected, Object actual, Supplier<String> messageSupplier) {
        AssertNotEquals.assertNotEquals(unexpected, actual, messageSupplier);
    }

    public static void assertSame(Object expected, Object actual) {
        AssertSame.assertSame(expected, actual);
    }

    public static void assertSame(Object expected, Object actual, String message) {
        AssertSame.assertSame(expected, actual, message);
    }

    public static void assertSame(Object expected, Object actual, Supplier<String> messageSupplier) {
        AssertSame.assertSame(expected, actual, messageSupplier);
    }

    public static void assertNotSame(Object unexpected, Object actual) {
        AssertNotSame.assertNotSame(unexpected, actual);
    }

    public static void assertNotSame(Object unexpected, Object actual, String message) {
        AssertNotSame.assertNotSame(unexpected, actual, message);
    }

    public static void assertNotSame(Object unexpected, Object actual, Supplier<String> messageSupplier) {
        AssertNotSame.assertNotSame(unexpected, actual, messageSupplier);
    }

    public static void assertAll(Executable... executables) throws MultipleFailuresError {
        AssertAll.assertAll(executables);
    }

    public static void assertAll(String heading, Executable... executables) throws MultipleFailuresError {
        AssertAll.assertAll(heading, executables);
    }

    public static void assertAll(Collection<Executable> executables) throws MultipleFailuresError {
        AssertAll.assertAll(executables);
    }

    public static void assertAll(String heading, Collection<Executable> executables) throws MultipleFailuresError {
        AssertAll.assertAll(heading, executables);
    }

    public static void assertAll(Stream<Executable> executables) throws MultipleFailuresError {
        AssertAll.assertAll(executables);
    }

    public static void assertAll(String heading, Stream<Executable> executables) throws MultipleFailuresError {
        AssertAll.assertAll(heading, executables);
    }

    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable) {
        return AssertThrows.assertThrows(expectedType, executable);
    }

    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable, String message) {
        return AssertThrows.assertThrows(expectedType, executable, message);
    }

    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable, Supplier<String> messageSupplier) {
        return AssertThrows.assertThrows(expectedType, executable, messageSupplier);
    }

    public static void assertDoesNotThrow(Executable executable) {
        AssertDoesNotThrow.assertDoesNotThrow(executable);
    }

    public static void assertDoesNotThrow(Executable executable, String message) {
        AssertDoesNotThrow.assertDoesNotThrow(executable, message);
    }

    public static void assertDoesNotThrow(Executable executable, Supplier<String> messageSupplier) {
        AssertDoesNotThrow.assertDoesNotThrow(executable, messageSupplier);
    }

    public static <T> T assertDoesNotThrow(ThrowingSupplier<T> supplier) {
        return AssertDoesNotThrow.assertDoesNotThrow(supplier);
    }

    public static <T> T assertDoesNotThrow(ThrowingSupplier<T> supplier, String message) {
        return AssertDoesNotThrow.assertDoesNotThrow(supplier, message);
    }

    public static <T> T assertDoesNotThrow(ThrowingSupplier<T> supplier, Supplier<String> messageSupplier) {
        return AssertDoesNotThrow.assertDoesNotThrow(supplier, messageSupplier);
    }

    public static void assertTimeout(Duration timeout, Executable executable) {
        AssertTimeout.assertTimeout(timeout, executable);
    }

    public static void assertTimeout(Duration timeout, Executable executable, String message) {
        AssertTimeout.assertTimeout(timeout, executable, message);
    }

    public static void assertTimeout(Duration timeout, Executable executable, Supplier<String> messageSupplier) {
        AssertTimeout.assertTimeout(timeout, executable, messageSupplier);
    }

    public static <T> T assertTimeout(Duration timeout, ThrowingSupplier<T> supplier) {
        return AssertTimeout.assertTimeout(timeout, supplier);
    }

    public static <T> T assertTimeout(Duration timeout, ThrowingSupplier<T> supplier, String message) {
        return AssertTimeout.assertTimeout(timeout, supplier, message);
    }

    public static <T> T assertTimeout(Duration timeout, ThrowingSupplier<T> supplier, Supplier<String> messageSupplier) {
        return AssertTimeout.assertTimeout(timeout, supplier, messageSupplier);
    }

    public static void assertTimeoutPreemptively(Duration timeout, Executable executable) {
        AssertTimeout.assertTimeoutPreemptively(timeout, executable);
    }

    public static void assertTimeoutPreemptively(Duration timeout, Executable executable, String message) {
        AssertTimeout.assertTimeoutPreemptively(timeout, executable, message);
    }

    public static void assertTimeoutPreemptively(Duration timeout, Executable executable, Supplier<String> messageSupplier) {
        AssertTimeout.assertTimeoutPreemptively(timeout, executable, messageSupplier);
    }

    public static <T> T assertTimeoutPreemptively(Duration timeout, ThrowingSupplier<T> supplier) {
        return AssertTimeout.assertTimeoutPreemptively(timeout, supplier);
    }

    public static <T> T assertTimeoutPreemptively(Duration timeout, ThrowingSupplier<T> supplier, String message) {
        return AssertTimeout.assertTimeoutPreemptively(timeout, supplier, message);
    }

    public static <T> T assertTimeoutPreemptively(Duration timeout, ThrowingSupplier<T> supplier, Supplier<String> messageSupplier) {
        return AssertTimeout.assertTimeoutPreemptively(timeout, supplier, messageSupplier);
    }*/

}