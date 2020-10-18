package org.learning.dp;

import org.learning.common.PrintUtils;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Count how many phone numbers there are of a given length L, so that each
 * two digits are adjacent to each other.
 *
 *    { 1, 2, 3 }
 *    { 4, 5, 6 }
 *    { 7, 8, 9 }
 *    { *, 0, # }
 *
 *  Based on the above table, a few neighbor examples are:
 *  1)  1 -> 2,4
 *  2)  5 -> 2,4,6,8
 *  3)  8 -> 5,7,9,0
 *  4)  0 -> 8
 *  5)  9 -> 6,8
 */
public class CountingPhoneNumber {
    public static void main(String[] args) {
        System.out.println("CountingPhoneNumber.main");
        int[][] neighbors = {
                {8},      // 0
                {2,4},    // 1
                {1,3,5},  // 2
                {2,6},    // 3
                {1,5},    // 4
                {2,4,6,8},// 5
                {3,5},    // 6
                {4,8},    // 7
                {5,7,9,0},// 8
                {6,8},    // 9
        };

         test(1, 10, neighbors);
         test(2, 24, neighbors);
         test(3, 66, neighbors);
    }

    private static void test(int numDigits, int expected,  int[][] neighbors) {
        System.out.printf("test: numDigits: %d\n", numDigits);

        int actual = countPhoneNumbersTopDown(numDigits, neighbors);

        System.out.printf("topDown: expected: %d, actual: %d\n", expected, actual);

        int actual2 = bottomUp(numDigits, neighbors);
        System.out.printf("bottomUp: expected: %d, actual: %d\n", expected, actual2);

        int actual3 = bottomUp2(numDigits, neighbors);
        System.out.printf("bottomUp2: expected: %d, actual: %d\n", expected, actual3);
        System.out.println("");

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actual2);
        Assert.assertEquals(expected, actual3);
    }

    private static int countPhoneNumbersTopDown(int numDigits, int[][] neighbors) {


        if (numDigits <= 0) {  // negative and 0
            return 0;
        }

        // a digit has 10 possible values from 0 -> 9
        int count = 0;
        Map<String,Integer> cache = new HashMap<>();
        StringBuilder buf = new StringBuilder();
        for (int digit = 0; digit <= 9; digit++) {
            buf.append(digit);
            count += helper(numDigits, digit, neighbors, 0, cache, buf);
            buf.deleteCharAt(buf.length()-1);
        }
        return count;
    }

    /**
     * This is a top down approach
     * - in order to find the total count for a length of n digits
     *   - for each of the possible last digit
     *     - we sum up the # count of all its possible neighbors
     * f(numDigits, digit) = sum ( f(numDigits-1, digit-neighbors) )
     *
     * The progression of solving this problem:
     * - straight recursion
     * - recursion w/ memoization
     *
     * Where is the redundancy?
     *  - for example we could get to 5 from {2,4,6}, so that is where the redundancy comes from
     *
     * for memoization, it is necessary to use the combinations of (numDigits,digit) as the unique key
     *    - for some problems only one piece of info is needed
     *
     *
     * @param numDigits
     * @param digit
     * @param neighbors
     * @param level
     * @return
     */
    private static int helper(int numDigits, int digit, int[][] neighbors, int level,
                              Map<String, Integer> cache, StringBuilder buf) {


        String key = numDigits + "," + digit;

        if (cache.containsKey(key)) {
            //System.out.printf("**** found key: %s\n", key );
            System.out.println(buf.toString());
            return cache.get(key);
        }

        //PrintUtils.printWithLevel(level, String.format("numDigits: %d, digit: %d", numDigits, digit));
        if (numDigits == 1) {
            System.out.println(buf.toString());
            cache.put(key, 1);  // make sure to cache the count for the base case as well
            return 1;
        }

        int count = 0;
        for (int neighbor : neighbors[digit] ) {
            buf.append(neighbor);
            int tmpCount = helper(numDigits-1, neighbor, neighbors, level+1, cache, buf);
            count += tmpCount;
            buf.deleteCharAt(buf.length()-1);
        }

        //System.out.printf("*** placing key: %s\n",key);
        cache.put(key, count);
        return count;
    }

    /**
     * Bottom up is a direct implementation of the recurrence
     * - f(numDigits, digit) = sum ( f(numDigits-1, digit-neighbors) )
     * - bottom up meaning build the solution from the smallest sub-problem to the largest sub-problem
     *   in a sequential manner
     * - because it requires looking back, therefore we need a table (1-d or 2-d array)
     * - if the look up is shallow,  meaning looking back only the immediate previous sub-problem
     *   then we can further optimize the space
     *
     * - there is always a base case, identify that first
     *   - base case provides the start point (we need start from a well known state)
     *
     *  Time complexity:  3 loops => O(n * 10 digits * 4 (max neighbors)
     *  Space complexity: table of size => O(10 * n)
     *
     * @param n
     * @param neighbors
     * @return
     */
    private static int bottomUp(int n, int[][] neighbors) {
        // first dimension is the # of digits, second dimension is the number of digits
        // good to draw this out
        //
        int[][] table = new int[10][n+1];

        for (int len = 1; len <= n; len++) {
            for (int digit = 0; digit <= 9; digit++) {
                if (len == 1) {  // same as len of 1
                    table[digit][len] = 1;
                } else {
                    for (int neighbor : neighbors[digit]) {
                        table[digit][len]  += table[neighbor][len-1];
                    }
                }
            }
        }

        int finalTotal = 0;
        for (int digit = 0; digit <= 9; digit++) {
            finalTotal += table[digit][n];
        }

        return finalTotal;
    }

    /**
     * Bottom up is a direct implementation of the recurrence
     * - f(numDigits, digit) = sum ( f(numDigits-1, digit-neighbors) )
     * - bottom up meaning build the solution from the smallest sub-problem to the largest sub-problem
     *   in a sequential manner
     * - because it requires looking back, therefore we need a table (1-d or 2-d array)
     * - if the look up is shallow,  meaning looking back only the immediate previous sub-problem
     *   then we can further optimize the space
     *
     * - there is always a base case, identify that first
     *   - base case provides the start point (we need start from a well known state)
     *
     * Time complexity:  3 loops => O(n * 10 digits * 4 (max neighbors)
     * Space complexity: table of size => O(10 * 2) => O(1)
     *
     * @param n
     * @param neighbors
     * @return
     */
    private static int bottomUp2(int n, int[][] neighbors) {
        // first dimension is the # of digits, second dimension is the number of digits
        // good to draw this out

        // using only tw columns and we will switch back and forth
        // using the modulo technique
        int[][] table = new int[10][2];

        for (int len = 1; len <= n; len++) {
            for (int digit = 0; digit <= 9; digit++) {
                if (len == 1) {  // same as len of 1
                    table[digit][len % 2] = 1;
                } else {
                    int totalCount = 0;
                    for (int neighbor : neighbors[digit]) {
                        totalCount  += table[neighbor][(len-1) % 2];
                    }
                    table[digit][len % 2] = totalCount;

                }
            }
        }

        int finalTotal = 0;
        for (int digit = 0; digit <= 9; digit++) {
            finalTotal += table[digit][n % 2];
        }

        return finalTotal;
    }
}
