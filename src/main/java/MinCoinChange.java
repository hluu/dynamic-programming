import org.junit.Assert;

import java.util.Arrays;

/**
 *
 * You are given coins of different denominations and a total amount of money amount.
 * Write a function to compute the fewest number of coins that you need to make up
 * that amount. If that amount of money cannot be made up by any combination of the
 * coins, return -1.
 *
 * Input: coins = [1, 2, 5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 *
 * Analysis:
 *  - minCoin(n) = { 1 + min{minCoin(n- c}} among all the coins
 *    - for n > 0
 *    - for n == 0 => 0
 *    - for n < 0 => -1
 */
public class MinCoinChange {
    public static void main(String[] args) {
        System.out.println(MinCoinChange.class.getName());

        test(21, new int[] {1, 6, 10}, 3);
      /*  test(3, new int[] {2}, -1);
        test(3, new int[] {1}, 3);
        test(7, new int[] {1, 6, 10}, 2);
        test(12, new int[] {1, 6, 10}, 2);
        test(19, new int[] {1, 6, 10}, 4);
        test(21, new int[] {1, 6, 10}, 3);
        test(50, new int[] {1, 6, 10}, 5);
        test(50, new int[] {10, 6, 1}, 5);
        test(11, new int[] {1, 2, 5}, 3);*/
    }

    private static int callCount = 0;

    private static void test(int amt, int[] coins, int expected) {
        System.out.printf("\n==> test amt: %d, coins: %s\n",
                amt, Arrays.toString(coins));

        int actual1 = topDownBF(amt, coins);
        printCallCount("topDownBF");

        int actual2 = topDownDP(amt, coins, new int[amt+1]);
        printCallCount("topDownDP");

        int actual3 = bottomUp(amt, coins);
        printCallCount("bottomUp");

        System.out.printf("expected: %d, actual1: %d, actual2: %d, actual3: %d\n",
                expected, actual1, actual2, actual3);

        Assert.assertEquals(expected, actual1);
        Assert.assertEquals(expected, actual2);
        Assert.assertEquals(expected, actual3);
    }

    private static int topDownBF(int amt, int[] coins) {
        callCount++;

        if (amt < 0) {
            return -1;
        }
        if (amt == 0) {
            return 0;
        }

        int coinCnt = Integer.MAX_VALUE;
        for (int coin : coins) {

            if (amt >= coin) {
                int tmpMin =  topDownBF(amt - coin, coins);
                if (tmpMin >= 0) {
                    coinCnt = Math.min(coinCnt, 1 + tmpMin);
                }
            }
        }


        return (coinCnt == Integer.MAX_VALUE) ? -1 : coinCnt;
    }

    private static int topDownDP(int amt, int[] coins, int[] cache) {

        if (amt < 0) {
            return -1;
        }

        if (cache[amt] != 0) {
            return cache[amt];
        }

        callCount++;

        if (amt == 0) {
            return 0;
        }

        int coinCnt = Integer.MAX_VALUE;
        for (int coin : coins) {
            if (amt >= coin) {
                int tmpMin =  topDownDP(amt - coin, coins, cache);
                if (tmpMin >= 0) {
                    coinCnt = Math.min(coinCnt, 1 + tmpMin);
                }
            }
        }


        cache[amt] = (coinCnt == Integer.MAX_VALUE) ? -1 : coinCnt;

        return cache[amt];
    }

    /**
     * Calculate for every single amount  from 1 to amt.
     *
     * Runtime is: O(totalAmt * number of coins)
     * Space: O(totalAmt)
     *
     * @param totalAmt
     * @param coins
     * @return
     */
    private static int bottomUp(int totalAmt, int[] coins) {
        if (totalAmt < 0) {
            return -1;
        }
        if (totalAmt == 0) {
            return 0;
        }

        int maxValueToSeedArray = totalAmt+1;
        int[] cache = new int[totalAmt+1];
        // the minimum of coins can't be greater than the totalAmt + 1
        Arrays.fill(cache, maxValueToSeedArray);
        // when amount is 0, there is 0 coins to make change to
        cache[0] = 0;

        for (int coin : coins) {
            for (int amt = coin; amt <= totalAmt; amt++) {
                callCount++;
                cache[amt] =  Math.min(cache[amt], 1 + cache[amt-coin]);
            }
        }

        System.out.println("bp cache: " + Arrays.toString(cache));
        return (cache[totalAmt] == maxValueToSeedArray) ? -1 : cache[totalAmt];
    }

    private static int bottomUp2(int totalAmt, int[] coins) {
        if (totalAmt < 0) {
            return -1;
        }
        if (totalAmt == 0) {
            return 0;
        }

        int maxValueToSeedArray = totalAmt+1;
        int[] cache = new int[totalAmt+1];
        // the minimum of coins can't be greater than the totalAmt + 1
        Arrays.fill(cache, maxValueToSeedArray);
        // when amount is 0, there is 0 coins to make change to
        cache[0] = 0;

        for (int amt = 1; amt <= totalAmt; amt++) {
            for (int coin : coins) {
                callCount++;
                if (amt >= coin) {
                    cache[amt] =  Math.min(cache[amt], 1 + cache[amt-coin]);
                }
            }
        }

        //System.out.println("cache: " + Arrays.toString(cache));
        return (cache[totalAmt] == maxValueToSeedArray) ? -1 : cache[totalAmt];
    }

    private static void printCallCount(String prefix) {
        System.out.printf("\ncallCount - %s, %d\n",
                prefix, callCount);

        callCount = 0;
    }
}
