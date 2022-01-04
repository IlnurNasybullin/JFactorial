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
import java.util.stream.Stream;

import static org.jdevtools.factorial.FactorialNumberSystems.decimal2IntFactorials;

/**
 * Testing class for {@link FactorialNumberSystems}.
 */
public class FactorialNumberSystemsTest {

    @ParameterizedTest
    @MethodSource({
            "_decimal2IntFactorials_Success_DataSet"
    })
    public void decimal2IntFactorials_Success(BigInteger value, int[] expectedResult) {
        Assertions.assertArrayEquals(expectedResult, decimal2IntFactorials(value));
    }

    public static Stream<Arguments> _decimal2IntFactorials_Success_DataSet() {
        return Stream.of(
                Arguments.of(BigInteger.ZERO, new int[]{}),
                Arguments.of(BigInteger.ONE, new int[]{1}),
                Arguments.of(BigInteger.ONE.negate(), new int[]{-1}),
                Arguments.of(BigInteger.TWO, new int[]{0, 1}),
                Arguments.of(BigInteger.TWO.negate(), new int[]{0, -1}),
                Arguments.of(BigInteger.TEN, new int[]{0, 2, 1}),
                Arguments.of(BigInteger.TEN.negate(), new int[]{0, -2, -1}),
                Arguments.of(BigInteger.valueOf(12), new int[]{0, 0, 2}),
                Arguments.of(BigInteger.valueOf(-12), new int[]{0, 0, -2}),
                Arguments.of(BigInteger.valueOf(20), new int[]{0, 1, 3}),
                Arguments.of(BigInteger.valueOf(-20), new int[]{0, -1, -3}),
                Arguments.of(BigInteger.valueOf(24), new int[]{0, 0, 0, 1}),
                Arguments.of(BigInteger.valueOf(-24), new int[]{0, 0, 0, -1}),
                Arguments.of(BigInteger.valueOf(100), new int[]{0, 2, 0, 4}),
                Arguments.of(BigInteger.valueOf(-100), new int[]{0, -2, 0, -4}),
                Arguments.of(BigInteger.valueOf(120), new int[]{0, 0, 0, 0, 1}),
                Arguments.of(BigInteger.valueOf(-120), new int[]{0, 0, 0, 0, -1}),
                Arguments.of(BigInteger.valueOf(1_000), new int[]{0, 2, 2, 1, 2, 1}),
                Arguments.of(BigInteger.valueOf(-1_000), new int[]{0, -2, -2, -1, -2, -1}),
                Arguments.of(BigInteger.valueOf(10_000), new int[]{0, 2, 2, 1, 5, 6, 1}),
                Arguments.of(BigInteger.valueOf(-10_000), new int[]{0, -2, -2, -1, -5, -6, -1}),
                Arguments.of(BigInteger.valueOf(100_000), new int[]{0, 2, 2, 1, 5, 5, 3, 2}),
                Arguments.of(BigInteger.valueOf(-100_000), new int[]{0, -2, -2, -1, -5, -5, -3, -2})
        );
    }

    @ParameterizedTest
    @MethodSource({
           "_decimal2IntFactorials_Exception_DataSet"
    })
    public <X extends Exception> void decimal2IntFactorials_Exception(BigInteger value, Class<X> expectedException) {
        Assertions.assertThrows(expectedException, () -> decimal2IntFactorials(value));
    }

    public static Stream<Arguments> _decimal2IntFactorials_Exception_DataSet() {
        return Stream.of(
                Arguments.of(null, NullPointerException.class)
        );
    }
}
