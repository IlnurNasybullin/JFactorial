/*
 * Copyright 2021 Ilnur Nasybullin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jdevtools.factorial;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Testing static method of class {@link Factorials}
 */
public class FactorialsTest {

    @ParameterizedTest
    @MethodSource("_longValue_Success_DataSet")
    public void getLongValue_Success(int value, Optional<Long> expectedFactorial) {
        Assertions.assertEquals(expectedFactorial, Factorials.longFactorial(value));
    }

    @ParameterizedTest
    @MethodSource("_longValue_Exception_DataSet")
    public <X extends Exception> void getLongValue_Exception(int value, Class<X> expectedException) {
        Assertions.assertThrows(expectedException, () -> Factorials.longFactorial(value));
    }

    @ParameterizedTest
    @MethodSource("_factorial_Success_DataSet")
    public void getFactorial_Success(int value, BigInteger expectedFactorial) {
        Assertions.assertEquals(expectedFactorial, Factorials.factorial(value));
    }

    @ParameterizedTest
    @MethodSource("_factorial_Exception_DataSet")
    public <X extends Exception> void getFactorial_Exception(int value, Class<X> expectedException) {
        Assertions.assertThrows(expectedException, () -> Factorials.factorial(value));
    }

    public static Stream<Arguments> _factorial_Exception_DataSet() {
        return Stream.of(
                Arguments.of(-1, IllegalArgumentException.class),
                Arguments.of(-100, IllegalArgumentException.class)
        );
    }

    public static Stream<Arguments> _factorial_Success_DataSet() {
        return Stream.of(
                Arguments.of(0, BigInteger.ONE),
                Arguments.of(1, BigInteger.ONE),
                Arguments.of(2, BigInteger.TWO),
                Arguments.of(6, BigInteger.valueOf(720L)),
                Arguments.of(12, BigInteger.valueOf(479_001_600L)),
                Arguments.of(15, BigInteger.valueOf(1_307_674_368_000L)),
                Arguments.of(20, BigInteger.valueOf(2_432_902_008_176_640_000L)),
                Arguments.of(21, new BigInteger("51090942171709440000")),
                Arguments.of(25, new BigInteger("15511210043330985984000000")),
                Arguments.of(30, new BigInteger("265252859812191058636308480000000")),
                Arguments.of(48, new BigInteger("12413915592536072670862289047373375038521486354677760000000000")),
                Arguments.of(100, new BigInteger("93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000"))
        );
    }

    public static Stream<Arguments> _longValue_Exception_DataSet() {
        return Stream.of(
                Arguments.of(-4, IllegalArgumentException.class),
                Arguments.of(-1, IllegalArgumentException.class)
        );
    }

    public static Stream<Arguments> _longValue_Success_DataSet() {
        return Stream.of(
                Arguments.of(0, Optional.of(1L)),
                Arguments.of(1, Optional.of(1L)),
                Arguments.of(2, Optional.of(2L)),
                Arguments.of(6, Optional.of(720L)),
                Arguments.of(12, Optional.of(479_001_600L)),
                Arguments.of(15, Optional.of(1_307_674_368_000L)),
                Arguments.of(20, Optional.of(2_432_902_008_176_640_000L)),
                Arguments.of(21, Optional.empty()),
                Arguments.of(25, Optional.empty())
        );
    }

    @ParameterizedTest
    @MethodSource("_testCombinations_Success_DataSet")
    public void testCombinations_Success(int n, int k, BigInteger expectedResult) {
        Assertions.assertEquals(expectedResult, Factorials.combinations(n, k));
    }

    public static Stream<Arguments> _testCombinations_Success_DataSet() {
        return Stream.of(
                Arguments.of(0, 0, BigInteger.ONE),
                Arguments.of(1, 0, BigInteger.ONE),
                Arguments.of(1, 1, BigInteger.ONE),
                Arguments.of(15, 4, BigInteger.valueOf(1_365L)),
                Arguments.of(15, 7, BigInteger.valueOf(6_435L)),
                Arguments.of(50, 15, BigInteger.valueOf(2_250_829_575_120L)),
                Arguments.of(50, 25, BigInteger.valueOf(126_410_606_437_752L)),
                Arguments.of(100, 40, new BigInteger("13746234145802811501267369720")),
                Arguments.of(100, 50, new BigInteger("100891344545564193334812497256"))
        );
    }

    @ParameterizedTest
    @MethodSource("_testCombinations_Exception_DataSet")
    public <X extends Exception> void testCombinations_Exception(int n, int k, Class<X> expectedException) {
        Assertions.assertThrows(expectedException, () -> Factorials.combinations(n, k));
    }

    public static Stream<Arguments> _testCombinations_Exception_DataSet() {
        return Stream.of(
                Arguments.of(0, 4, IllegalArgumentException.class),
                Arguments.of(-3, -4, IllegalArgumentException.class),
                Arguments.of(4, -3, IllegalArgumentException.class),
                Arguments.of(-6, 0, IllegalArgumentException.class)
        );
    }
}
