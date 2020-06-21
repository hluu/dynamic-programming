package org.learning.dp;

import org.junit.Assert;


import java.util.Arrays;

/**
 * You are given coins of different denominations and a total amount of money.
 * Write a function to compute the number of combinations that make up that
 * amount. You may assume that you have infinite number of each kind of coin.
 *
 * https://leetcode.com/problems/coin-change-2/
 *
 * Input: amount = 5, coins = [1, 2, 5]
 * Output: 4
 * Explanation: there are four ways to make up the amount:
 *   - 5=5
 *   - 5=2+2+1
 *   - 5=2+1+1+1
 *   - 5=1+1+1+1+1
 *
 * Input: amount = 3, coins = [2]
 * Output: 0
 * Explanation: the amount of 3 cannot be made up just with coins of 2.
 *
 * Input: amount = 10, coins = [10]
 * Output: 1
 */
public class NumWayCoinChange {
    public static void main(String[] args) {
        System.out.println(NumWayCoinChange.class.getName());

        test(0, new int[] {}, 1);
        test(3, new int[] {2}, 0);
        test(10, new int[] {10}, 1);

        test(7, new int[] {1,2,5}, 6);
        test(7, new int[] {5,2,1}, 6);
        test(7, new int[] {5,1,2}, 6);
        test(100, new int[] {5,1,2}, 541);
        test(100, new int[] {3,5,7,8,9,10,11}, 6606);

        // football score
        test(12, new int[] {2,3,7}, 4);

        test(8, new int[] {1,5,10}, 2);
        test(10, new int[] {1,5,10}, 4);


        test(1, new int[]{1, 2, 5}, 1);
        test(2, new int[]{1, 2, 5}, 2);
        test(3, new int[]{1, 2, 5}, 2);
        test(4, new int[]{1, 2, 5}, 3);
        test(5, new int[]{1, 2, 5}, 4);
        test(6, new int[]{1, 2, 5}, 5);
        test(7, new int[]{1, 2, 5}, 6);
        test(8, new int[]{1, 2, 5}, 7);
        test(9, new int[]{1, 2, 5}, 8);
        test(10, new int[]{1, 2, 5}, 10);
        test(11, new int[]{1, 2, 5}, 11);
        test(12, new int[]{1, 2, 5}, 13);
        test(13, new int[]{1, 2, 5}, 14);
        test(15, new int[]{1, 2, 5}, 18);

    }

    private static int callCount = 0;
    private static int cacheHit = 0;

    public static void test(int amt, int[] coins, int expected) {
        System.out.printf("\n==> test amt: %d, coins: %s\n",
                amt, Arrays.toString(coins));

        int actual1 = topDownBF(amt, coins, 0);
        printStats("top down BF");


        int[][] cache = new int[amt+1][coins.length];
        int actual2 = topDownDP(amt, coins, 0, cache);
        printStats("top down DP");

        int actual3 = bottomUp(amt, coins);
        printStats("bottom up");

        int actual4 = bottomUp2(amt, coins);
        printStats("bottom up2");

        System.out.printf("==> result: expected: %d, actual1: %d, actual2: %d, actual3: %d, actual4: %d\n",
                expected, actual1, actual2, actual3, actual4);

        Assert.assertEquals(expected, actual1);
        Assert.assertEquals(expected, actual2);
        Assert.assertEquals(expected, actual3);
        Assert.assertEquals(expected, actual4);
    }

    /**
     * https://www.geeksforgeeks.org/coin-change-dp-7/
     *
     * @param amt
     * @param coins
     * @param idx
     * @return
     */
    private static int topDownBF(int amt, int[] coins, int idx) {
        callCount++;

        if (amt == 0) {
            return 1;
        }

        if (amt < 0 || idx >= coins.length) {
            return 0;
        }

        // include
        int total = topDownBF(amt - coins[idx], coins, idx);

        // not include
        total += topDownBF(amt, coins, idx+1);

        return total;
    }

    /**
     * TopDown with memoization using a 2-d array, could have used a hashmap as well
     *
     * The key thing is to recognize is what are the changing input parameters
     *  - amt and idx
     *  - therefore the 2-d is based on that
     *
     * @param amt
     * @param coins
     * @param idx
     * @param cache
     * @return
     */
    private static int topDownDP(int amt, int[] coins, int idx, int[][] cache) {


        if (amt < 0) {
            return 0;
        }
        if (amt == 0) {
            return 1;
        }

        if (idx >= coins.length) {
            return 0;
        }

        if (cache[amt][idx] > 0) {
            cacheHit++;
            return cache[amt][idx];
        }

        callCount++;

        if (amt == 0) {
            cache[amt][idx] = 1;
            return 1;
        }

        if (idx >= coins.length) {
            return 0;
        }

        // include
        int total = topDownDP(amt - coins[idx], coins, idx, cache);

        // not include
        total += topDownDP(amt, coins, idx+1, cache);

        cache[amt][idx] = total;
        return total;
    }

    /**
     * Build a two dimensional matrix to store the previously computed values
     *
     * Notes:
     *  - when creating cache, it goes to amt or coins, therefore you would create
     *    an array of  int[coins.length+1][amt+1]
     *  - because of the zero based index
     *
     * Recurrent:
     *                      (not using current coin) + (using current coin)
     *   cache[coin][amt] = cache[coin-1][amt] + cache[coin][amt - coin]
     *
     * @param amt
     * @param coins
     * @return
     */
    private static int bottomUp(int amt, int[] coins) {
        // columms are the amount from 0 to amt
        // rows are the number of coins
        int[][] cache = new int[coins.length+1][amt+1];

        for (int coinIdx = 0; coinIdx <= coins.length; coinIdx++) { // row
            for (int currAmt = 0; currAmt <= amt; currAmt++) { // column
                callCount++;
                if (currAmt == 0) { // for 0 amount, there is 1 way of making change (nothing)
                    cache[coinIdx][currAmt] = 1;
                } else if (coinIdx > 0) {
                    int withOutCurrentCoin = cache[coinIdx - 1][currAmt];
                    int withCurrentCoin = 0;
                    if (currAmt >= coins[coinIdx-1]) {
                        withCurrentCoin = cache[coinIdx][currAmt - coins[coinIdx - 1]];
                    }
                    cache[coinIdx][currAmt] =
                            withCurrentCoin + withOutCurrentCoin;
                }
            }
        }

        printCache(cache);
        return cache[coins.length][amt];

    }


    /**
     * Based on the https://www.geeksforgeeks.org/understanding-the-coin-change-problem-with-dynamic-programming/
     *
     * Why can we get away if a single dimension array?
     *
     *
     * @param amt
     * @param coins
     * @return
     */
    private static int bottomUp2(int amt, int[] coins) {
        if (amt == 0) {
            return 1;
        }

        int[] cache = new int[amt+1];
        cache[0] = 1; // for 0 amount, there is 1 way of making change (nothing)

        for (int coin : coins) {
            for (int currAmt = coin; currAmt <= amt; currAmt++) {
                callCount++;
                cache[currAmt] = cache[currAmt] + cache[currAmt - coin];
            }
            System.out.println("coinValue: " + coin + " table: " + Arrays.toString(cache));
        }

        System.out.println("cache: " + Arrays.toString(cache));
        return cache[amt];

    }

    private static void printCache(int[][] cache) {
        System.out.println("====== cache content =======");
        System.out.print(" ");
        for (int i = 0; i < cache[0].length; i++) {
            System.out.print(i + "  ");
        }
        System.out.println();

        for (int[] row : cache) {
            System.out.println(Arrays.toString(row));
        }
    }

    private static void printStats(String prefix) {
        System.out.printf("\ncallCount - %s, %d, cacheHit: %d\n",
                prefix, callCount,  cacheHit);

        callCount = 0;
        cacheHit = 0;
    }
}
