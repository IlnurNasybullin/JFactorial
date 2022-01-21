/*
 * Copyright 2021-2022 Ilnur Nasybullin
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
import java.util.Optional;

/**
 * Utility class for calculating factorials. <b>This class is thread-safe</b>, because it's not possible to create an object
 * this class (see {@link #Factorials()}) and all methods of this class are static and don't change states of class members
 * @author Ilnur Nasybullin
 */
public final class Factorials {

    /**
     * All long factorials values (n!, n &isin [0...20])
     */
    private final static long[] factorials = {
            1L, 1L, 2L, 6L, 24L, 120L, 720L, 5_040L, 40_320L, 362_880L, 3_628_800L, 39_916_800L, 479_001_600L,
            6_227_020_800L, 87_178_291_200L, 1_307_674_368_000L, 20_922_789_888_000L, 355_687_428_096_000L,
            6_402_373_705_728_000L, 121_645_100_408_832_000L, 2_432_902_008_176_640_000L
    };

    /**
     * Maximal factorial digit, that can be calculated, using class {@link BigInteger}. This value has been calculated
     * with method {@link #calcMaxBigIntegerFactorialDigit()}.
     * @implNote This value has been calculated based on the knowledge of the internal representation of class
     * {@link BigInteger}. In JavaDoc for class {@link BigInteger} written that object of class {@link BigInteger} can
     * represent number in the range -2<sup>{@link Integer#MAX_VALUE}</sup> (exclusive) to 2<sup>{@link Integer#MAX_VALUE}</sup>
     * (exclusive). It's mean, that in binary representation maximal factorial value can have up to {@link Integer#MAX_VALUE}
     * (exclusive) bits. Method {@link #calcMaxBigIntegerFactorialDigit()} calculate maximal factorial value by counting
     * the number of bits required for representation for factorial value.
     * <p>
     *     On practice, this value is optimistic for two reasons:
     *     <ol>
     *        <li>For the calculating this factorial value is needed to create array of bytes length &asymp
     *        {@link Integer#MAX_VALUE} / 8 (2<sup>28</sup>). For the different JVM is might need to allocate more memory
     *        in heap (otherwise, error {@link OutOfMemoryError} is thrown)</li>
     *        <li>For the calculating this factorial value is might take a very long time (see {@link #factorial(int)})</li>
     *     </ol>
     * </p>
     * @since 9
     * @see BigInteger
     * @see #calcMaxBigIntegerFactorialDigit()
     */
    private final static int MAX_BIGINTEGER_FACTORIAL_DIGIT = 86_181_406;

    /**
     * Private constructor for inability to create an object of this class
     */
    private Factorials() {}

    /**
     * Returns long representative of factorial digit. If digit &lt; 21 (fits into long) will be returned {@link Optional} object
     * with factorial value inside, otherwise, will be returned {@link Optional#empty()} object
     * @param n digit of factorial
     * @return {@link Optional} object with long factorial value, if it's exist, or {@link Optional#empty()} object
     * @throws IllegalArgumentException if n is negative number
     */
    public static Optional<Long> longFactorial(int n) {
        checkOnNegative(n, String.format("digit is negative number! (n = %d)", n));
        return n < factorials.length ? Optional.of(factorials[n]) : Optional.empty();
    }

    private static void checkOnNegative(int n, String errorMessage) {
        if (n < 0) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Returns {@link BigInteger} representative of factorial digit.
     * @param n digit of factorial
     * @return {@link BigInteger} factorial value
     * @throws IllegalArgumentException if n is negative
     * @implNote For calculating is used native multiplication of natural series from 21 to n {@link #multiplyRange(int, int)}.
     * Calculations are performed in a loop first over long type variable and then, if possible overflow of long type,
     * multiplies {@link BigInteger} value. In future version, for increasing the speed of calculations, can be used
     * parallel multiplication with partition of natural series
     */
    public static BigInteger factorial(int n) {
        checkOnNegative(n, String.format("digit is negative number! (n = %d)", n));

        if (n < factorials.length) {
            return BigInteger.valueOf(factorials[n]);
        }

        int k = maxLongFactorialDigit();
        return BigInteger.valueOf(factorials[k]).multiply(multiplyRange(k + 1, n + 1));
    }

    private static boolean isLongMultiplyExact(long longValue, int i) {
        return bitLength(longValue) + bitLength(i + 1) <= 63;
    }

    private static int bitLength(long longValue) {
        int bitLength = 0;
        while (longValue != 0) {
            bitLength += 1;
            longValue >>= 1;
        }

        return bitLength;
    }

    /**
     * Returns max digit for calculating of factorial value with long type
     * @return max digit for of long factorial value
     */
    public static int maxLongFactorialDigit() {
        return factorials.length - 1;
    }

    /**
     * Returns max digit for calculating of factorial value with type {@link BigInteger}.
     * @return max digit for calculating of factorial value
     * @see #MAX_BIGINTEGER_FACTORIAL_DIGIT
     */
    public static int getMaxBigIntegerFactorialDigit() {
        return MAX_BIGINTEGER_FACTORIAL_DIGIT;
    }

    /**
     * Calculation max digit for factorial, that can be representative by {@link BigInteger}. For calculations is used
     * logarithm property of the multiplication:
     * <p>log<sub>2</sub>n! = log<sub>2</sub>(1 * 2 * ... * n) = log<sub>2</sub>1 + log<sub>2</sub>2 + ... + log<sub>2</sub>n</p>
     * log<sub>2</sub>n! - is a number of bits required for the representation factorial value in binary number system
     * @see #MAX_BIGINTEGER_FACTORIAL_DIGIT
     */
    private static void calcMaxBigIntegerFactorialDigit() {
        double factSum = 0;
        int n = 1;

        double log2 = Math.log(2);
        while (factSum < Integer.MAX_VALUE) {
            factSum += Math.log(n)/ log2;
            n++;
        }
    }

    /**
     * Return {@link BigInteger} representative combination of n things taken k at a time without repetition
     * @param n - things count
     * @param k - taken things count
     * @return {@link BigInteger} combination value.
     * @throws IllegalArgumentException - if k &lt; 0 or n &lt; k
     * @implNote For calculating combination C(n,k) = n!/(s!(n-s)!) (s = min(k, n-k) are calculating the numerator
     * n!/(n-s)! = (n-s+1)*(n-s+2)*...*(n-1)*n {@link #multiplyRange(int, int)} and denominator s! {@link #factorial(int)}
     * and the numerator is divided by the denominator. In future versions, faster ways of calculating combinations will
     * be considered
     */
    public static BigInteger combinations(int n, int k) {
        checkCombinations(n, k);

        if (k == 0) {
            return BigInteger.ONE;
        }

        int denValue = Math.min(k, n - k);

        BigInteger numerator = multiplyRange(n - denValue + 1, n + 1);
        BigInteger denominator = factorial(denValue);

        return numerator.divide(denominator);
    }

    private static void checkCombinations(int n, int k) {
        checkRangeClosed(k, n, String.format("k = %d is more than n = %d!", k, n));
        checkOnNegative(k, String.format("k = %d is negative number!", k));
    }

    private static void checkRangeClosed(int start, int end, String errorMessage) {
        if (end < start) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private static BigInteger multiplyRange(int startInclusive, int endExclusive) {
        if (startInclusive <= 0 && endExclusive > 0) {
            return BigInteger.ZERO;
        }

        BigInteger result = BigInteger.ONE;
        long termResult = 1L;

        for (int i = startInclusive; i < endExclusive; i++) {
            termResult *= i;
            if (!isLongMultiplyExact(termResult, i + 1)) {
                result = result.multiply(BigInteger.valueOf(termResult));
                termResult = 1L;
            }
        }

        if (termResult != 1L) {
            result = result.multiply(BigInteger.valueOf(termResult));
        }

        return result;
    }
}
