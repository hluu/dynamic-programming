import org.junit.Assert;

import java.util.Arrays;

/**
 * Given a row of coins, whose values are not necessarily distinct.
 *
 * The goal is to pick up the maximum amount of money subject to the
 * constraint that no two coins adjacent in the initial row can be picked up.
 *
 * a.k.a house robber problem
 *
 * Example
 *   {5, 22, 26, 15, 4, 3,11} max => 22 + 15 + 11 = 48
 *   {3,10,3,1,2} => max = > 12
 *
 * Resources:
 *  - https://medium.com/@avik.das/a-graphical-introduction-to-dynamic-programming-2e981fa7ca2
 */
public class CoinRow {
    public static void main(String[] args) {
        System.out.println(CoinRow.class.getName());

        test(new int[] {3,10,3,1,2}, 12);
        test(new int[] {5, 22, 26, 15, 4, 3,11}, 48);
        test(new int[] {5,1,2,9,6,2}, 16);
        test(new int[] {1,2,3,1}, 4);
        test(new int[] {2,7,9,3,1}, 12);
    }

    private static int callCount = 0;
    private static int cacheHit = 0;

    public static void test(int[] coins, int expected) {
        System.out.printf("\n==> test coins: %s\n",
                Arrays.toString(coins));

        int actual1 = topDownBF(coins.length-1, coins);
        printStats("top down BF");

        int actual2 = topDownDP(coins, coins.length-1, new int[coins.length]);
        printStats("top down DP");


        int actual3 = bottomUp(coins);
        printStats("bottom up");

        int actual4 = topDownDP2(coins);

        System.out.printf("==> result: expected: %d, actual1: %d, actual2: %d, actual3: %d, actual4: %d\n",
                expected, actual1, actual2, actual3, actual4);

        Assert.assertEquals(expected, actual1);
        Assert.assertEquals(expected, actual2);
        Assert.assertEquals(expected, actual3);
        Assert.assertEquals(expected, actual4);

    }

    private static int topDownBF(int idx, int[] coins ) {
        callCount++;

        if (idx == 0) {
            return coins[idx];
        }

        if (idx < 0) {
            return 0;
        }

        int maxOfPrevious = topDownBF(idx-1, coins);
        int maxOfCurrent = coins[idx] + topDownBF(idx-2, coins);
        return Math.max(maxOfPrevious, maxOfCurrent);
    }

    private static int topDownDP(int[] coins, int idx, int[] cache) {

        if (idx == 0) {
            return coins[idx];
        }

        if (idx < 0) {
            return 0;
        }

        callCount++;

        if (cache[idx] != 0) {
            cacheHit++;
            return cache[idx];
        }

        int maxOfPrevious = topDownDP(coins, idx-1, cache);
        int maxOfCurrent = coins[idx] + topDownDP(coins, idx-2, cache);
        cache[idx] = Math.max(maxOfPrevious, maxOfCurrent);
        return cache[idx];
    }

    /**
     * Using only 2 variables to store previous 2 values
     *
     * @param coins
     * @return
     */
    private static int topDownDP2(int[] coins) {

        int maxOfPrevious = coins[0];
        int maxOfCurrent = Math.max(coins[1], coins[0]);

        int max = -1;
        for (int i = 2; i < coins.length; i++) {
            int tmp = Math.max(maxOfCurrent, coins[i] + maxOfPrevious);
            maxOfPrevious = maxOfCurrent;
            maxOfCurrent = tmp;

            max = Math.max(max, tmp);
        }
        return  max;

    }

    private static int bottomUp(int[] coins) {
        int[] cache = new int[coins.length];

        cache[0] = coins[0];
        cache[1] = Math.max(coins[0], coins[1]);

        if (coins.length > 2) {
            for (int i = 2; i < coins.length; i++) {
                callCount++;
                cache[i] = Math.max(cache[i-1], coins[i] + cache[i-2]);
            }
        }

        System.out.println("bottom up table: " + Arrays.toString(cache));
        return cache[coins.length-1];
    }

    private static void printStats(String prefix) {
        System.out.printf("\ncallCount - %s, %d, cacheHit: %d\n",
                prefix, callCount,  cacheHit);

        callCount = 0;
        cacheHit = 0;
    }
}
