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

import java.math.BigInteger;
import java.util.stream.IntStream;

/**
 * Utility class for converting numbers from decimal number system to
 * <a href=https://en.wikipedia.org/wiki/Factorial_number_system>factorial number system</a>.
 * <p>
 *     <b>This class is thread-safe,</b> because it's not possible to construct object of this class, and this class hasn't
 *     anything states.
 * </p>
 * @author Ilnur Nasybullin
 */
public class FactorialNumberSystems {

    /**
     * Private constructor for the inability to construct an object of this class.
     */
    private FactorialNumberSystems() {}

    /**
     * Return array of positive numbers, that uniquely represent a decimal number in the
     * <a href=https://en.wikipedia.org/wiki/Factorial_number_system>factorial number system</a>. Every <i>i</i>-th element
     * of array represents digit in (<i>i+1</i>)-th position of number in
     * <a href=https://en.wikipedia.org/wiki/Factorial_number_system>factorial number system</a>. For example:
     * <p>
     *     10<sub>10</sub> = 1 * 3! + 2 * 2! + 0 * 1! + 0 * 0! = 1200<sub>Fact</sub>
     * </p>, and for this number will be returned array: [1, 2, 0] (digit for 0! position is skipped because it's always equal to 0).
     * For 0<sub>10</sub> will be returned empty array.
     * @param value decimal number
     * @return array of positive numbers that uniquely represent number in the
     * <a href=https://en.wikipedia.org/wiki/Factorial_number_system>factorial number system</a>
     * @throws NullPointerException if value is null
     * @apiNote
     * <ol>
     *     <li>Positive factorial numbers, except for the absence of negative sign for every digit, don't differ in any
     *     way from negative factorial numbers (-10<sub>10</sub> = -1 * 3! - 2 * 2! - 0 * 1! - 0 * 0! =
     *     = -(1 * 3! + 2 * 2! + 0 * 1! + 0 * 0!) = -1200<sub>Fact</sub></li>
     *     <li>From a technical point of view, array is the remainders of the division by 2, 3, 4 and so on until the value
     *     is reset to zero, i.e. every <i>i</i>-th element of array is the remainder of the division by (<i>i+2</i>)-th</li>
     * </ol>
     */
    public static int[] decimal2IntFactorials(BigInteger value) {
        if (value == null) {
            throw new NullPointerException("Value is null!");
        }

        IntStream.Builder builder = IntStream.builder();

        BigInteger dividend = value;
        BigInteger[] dividerAndRemainder;
        BigInteger divider = BigInteger.TWO;
        while (!dividend.equals(BigInteger.ZERO)) {
            dividerAndRemainder = dividend.divideAndRemainder(divider);
            builder.add(dividerAndRemainder[1].intValue());

            dividend = dividerAndRemainder[0];
            divider = divider.add(BigInteger.ONE);
        }

        return builder.build().toArray();
    }

}
