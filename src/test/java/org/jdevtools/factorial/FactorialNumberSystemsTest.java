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
    public void decimal2IntFactorials_Success(BigInteger[] values, int[][] expectedResults) {
        for (int i = 0; i < values.length; i++) {
            Assertions.assertArrayEquals(expectedResults[i], decimal2IntFactorials(values[i]));
        }
    }

    public static Stream<Arguments> _decimal2IntFactorials_Success_DataSet() {
        BigInteger[] values = new BigInteger[] {
                BigInteger.ZERO,
                BigInteger.ONE,
                BigInteger.ONE.negate(),
                BigInteger.TWO,
                BigInteger.TWO.negate(),
                BigInteger.TEN,
                BigInteger.TEN.negate(),
                BigInteger.valueOf(12),
                BigInteger.valueOf(12).negate(),
                BigInteger.valueOf(20),
                BigInteger.valueOf(20).negate(),
                BigInteger.valueOf(24),
                BigInteger.valueOf(24).negate(),
                BigInteger.valueOf(100),
                BigInteger.valueOf(100).negate(),
                BigInteger.valueOf(120),
                BigInteger.valueOf(120).negate(),
                BigInteger.valueOf(1_000),
                BigInteger.valueOf(1_000).negate(),
                BigInteger.valueOf(10_000),
                BigInteger.valueOf(10_000).negate(),
                BigInteger.valueOf(100_000),
                BigInteger.valueOf(100_000).negate()
        };

        int[][] expectedResults = new int[][] {
                {},
                {1},
                {-1},
                {0, 1},
                {0, -1},
                {0, 2, 1},
                {0, -2, -1},
                {0, 0, 2},
                {0, 0, -2},
                {0, 1, 3},
                {0, -1, -3},
                {0, 0, 0, 1},
                {0, 0, 0, -1},
                {0, 2, 0, 4},
                {0, -2, 0, -4},
                {0, 0, 0, 0, 1},
                {0, 0, 0, 0, -1},
                {0, 2, 2, 1, 2, 1},
                {0, -2, -2, -1, -2, -1},
                {0, 2, 2, 1, 5, 6, 1},
                {0, -2, -2, -1, -5, -6, -1},
                {0, 2, 2, 1, 5, 5, 3, 2},
                {0, -2, -2, -1, -5, -5, -3, -2}
        };

        return Stream.of(Arguments.of(
                values, expectedResults
        ));
    }

    @ParameterizedTest
    @MethodSource({
           "_decimal2IntFactorials_Exception_DataSet"
    })
    public <X extends Exception> void decimal2IntFactorials_Exception(BigInteger[] values, Class<X>[] expectedExceptions) {
        int[] i = {0};
        for (; i[0] < values.length; i[0]++) {
            Assertions.assertThrows(expectedExceptions[i[0]], () -> decimal2IntFactorials(values[i[0]]));
        }
    }

    public static Stream<Arguments> _decimal2IntFactorials_Exception_DataSet() {
        BigInteger[] values = new BigInteger[]{
                null
        };

        Class<?>[] expectedExceptions = new Class[] {
                NullPointerException.class
        };

        return Stream.of(Arguments.of(
                values, expectedExceptions
        ));
    }
}
