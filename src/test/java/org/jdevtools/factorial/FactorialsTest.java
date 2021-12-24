package org.jdevtools.factorial;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing static method of class {@link Factorials}
 */
public class FactorialsTest {

    @ParameterizedTest
    @MethodSource("_longValue_Success_DataSet")
    public void getLogValue_Success(int[] array, Optional<Long>[] expectedValues) {
        int n;
        Optional<Long> result;
        Optional<Long> factorial;

        for (int i = 0; i < array.length; i++) {
            n = array[i];
            result = expectedValues[i];
            factorial = Factorials.longFactorial(n);

            assertEquals(result, factorial);
        }
    }

    @ParameterizedTest
    @MethodSource("_longValue_Exception_DataSet")
    public <X extends Exception> void getLongValue_Exception(int[] array, Class<X>[] exceptionTypes) {
        Class<X> exception;
        final int[] n = new int[1];

        for (int i = 0; i < array.length; i++) {
            n[0] = array[i];
            exception = exceptionTypes[i];

            Assertions.assertThrows(exception, () -> Factorials.longFactorial(n[0]));
        }
    }

    @ParameterizedTest
    @MethodSource("_factorial_Success_DataSet")
    public void getFactorial_Success(int[] array, BigInteger[] expectedValues) {
        int n;
        BigInteger expected;

        for (int i = 0; i < array.length; i++) {
            n = array[i];
            expected = expectedValues[i];
            Assertions.assertEquals(expected, Factorials.factorial(n));
        }
    }

    @ParameterizedTest
    @MethodSource("_factorial_Exception_DataSet")
    public <X extends Exception> void getFactorial_Exception(int[] array, Class<X>[] exceptionTypes) {
        Class<X> exception;
        final int[] n = new int[1];

        for (int i = 0; i < array.length; i++) {
            n[0] = array[i];
            exception = exceptionTypes[i];

            Assertions.assertThrows(exception, () -> Factorials.factorial(n[0]));
        }
    }

    public static Stream<Arguments> _factorial_Exception_DataSet() {
        int[] array = {-1, -4, -22, -100};
        Class<IllegalArgumentException> exceptionType = IllegalArgumentException.class;
        Class<IllegalArgumentException>[] exceptionTypes = new Class[]
                {exceptionType, exceptionType, exceptionType, exceptionType};

        return Stream.of(Arguments.of(
                array, exceptionTypes
        ));
    }

    public static Stream<Arguments> _factorial_Success_DataSet() {
        int[] array = {0, 1, 2, 6, 12, 15, 20, 25, 30, 48, 100};
        BigInteger[] values = new BigInteger[]{
                BigInteger.ONE,
                BigInteger.ONE,
                BigInteger.TWO,
                BigInteger.valueOf(720),
                BigInteger.valueOf(479_001_600),
                BigInteger.valueOf(1_307_674_368_000L),
                BigInteger.valueOf(2_432_902_008_176_640_000L),
                new BigInteger("15511210043330985984000000"),
                new BigInteger("265252859812191058636308480000000"),
                new BigInteger("12413915592536072670862289047373375038521486354677760000000000"),
                new BigInteger("93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000")
        };

        return Stream.of(Arguments.of(
                array, values
        ));
    }

    public static Stream<Arguments> _longValue_Exception_DataSet() {
        int[] array = {-4, -1, -12};
        Class<IllegalArgumentException> exceptionType = IllegalArgumentException.class;
        Class<IllegalArgumentException>[] exceptionTypes = new Class[]{exceptionType, exceptionType, exceptionType};

        return Stream.of(Arguments.of(
                array, exceptionTypes
        ));
    }

    public static Stream<Arguments> _longValue_Success_DataSet() {
        int[] array = {0, 1, 2, 6, 12, 15, 20, 21, 25};

        Optional<Long>[] results = getOptionalsArray(
                Optional.of(1L),
                Optional.of(1L),
                Optional.of(2L),
                Optional.of(720L),
                Optional.of(479_001_600L),
                Optional.of(1_307_674_368_000L),
                Optional.of(2_432_902_008_176_640_000L),
                Optional.empty(),
                Optional.empty()
        );

        return Stream.of(Arguments.of(
                array, results
        ));
    }

    @SuppressWarnings("unchecked")
    public static Optional<Long>[] getOptionalsArray(Optional<Long> ... values) {
        return values;
    }
}
