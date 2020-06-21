package org.learning.dp;

import java.util.Arrays;

/**
 * Given a non negative integer number num. For every numbers i in the range
 * 0 ≤ i ≤ num calculate the number of 1's in their binary representation and return them as an array.
 *
 * Example 1:
 *
 * Input: 2
 * Output: [0,1,1]
 * Example 2:
 *
 * Input: 5
 * Output: [0,1,1,2,1,2]
 */
public class CountBits {
    public static void main(String[] args) {
        System.out.println(CountBits.class.getName());

        test(2, new int[] {0,1,1});
        test(5, new int[] {0,1,1,2,1,2});
    }

    private static void test(int n, int[] expected) {
        System.out.println("=====> test: " + n);

        int[] actual = dp(n);

        System.out.printf("expected: %s, actual: %s\n",
                Arrays.toString(expected), Arrays.toString(actual));
    }

    /**
     * // if i is even, reuse result from result[i/2]
     * // if i is odd, reuse result from result[i/2]+1
     *
     * @param n
     * @return
     */
    private static int[] dp(int num) {
        // because 0 ≤ i ≤ num
        int[] result = new int[num+1];

        // result[0] = 0 bits
        for (int i = 1; i <= num; i++) {
            // if i is even, reuse result from result[i/2]
            // if i is odd, reuse result from result[i/2]+1
            result[i] = (i%2 == 0) ? result[i/2] : result[i/2] + 1;
        }

        return result;
    }
}
