package org.learning.dp;

import org.testng.Assert;

import java.util.*;


/**
 * Give change for the amount n using the MINIMUM number of coins
 * of given denominations.
 *
 * The assumption is each denomination has unlimited quantities
 *
 * Let F(n) be the minimum of coins for the given amount where F(0) = 0
 *
 * For each amount and for each denomination, we need to find the minimum # of coins,
 * therefore F(n) = min { F(n-d(j), minSoFar } + 1.
 *
 * Time complexity is O(nm) => n is amount, m is the # of denominations
 * Space complexity is O(n) => n is the amount
 *
 *  https://leetcode.com/problems/coin-change/
 *
 * @author hluu
 *
 */
public class CoinChange {

    public static void main(String[] args) {


        test(6, new int[] {1,3,4}, 2);
        test(4, new int[] {1,5}, 4);
        test(0, new int[] {1,3,4}, 0);
        test(10, new int[] {1,5,7}, 2);
        test(8, new int[] {1,5,7}, 2);
        test(11, new int[] {1,5,7}, 3);
        test(12, new int[] {1,5,7}, 2);
        test(2, new int[] {5,7}, -1);
        test(11, new int[] {5,7}, -1);
        test(3, new int[] {2,4}, -1);
        test(7, new int[] {2,4}, -1);

    }

    private static void test(int amount, int[] coins, int expected) {
        System.out.printf("\namount: %d, coins: %s\n", amount, Arrays.toString(coins));

        int actual = coinChange(amount, coins);

        System.out.printf("expected: %d, actual: %d\n", expected, actual);

        Assert.assertEquals(actual, expected);
    }

    public static int coinChange(int amount, int[] coins) {
        int table[] = new int[amount+1];
        int coin_tracker[] = new int[amount+1];

        Arrays.fill(table, Integer.MAX_VALUE);

        table[0] = 0; // base case
        for (int amt = 1; amt <= amount; amt++) {
            int minSoFar = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (amt >= coin) {
                    if (table[amt - coin] < minSoFar) {
                        minSoFar = table[amt - coin];
                        coin_tracker[amt] = coin;
                    }

                }
            }
            table[amt] = (minSoFar < Integer.MAX_VALUE) ? minSoFar + 1 : Integer.MAX_VALUE;

        }
        System.out.println("table: " + Arrays.toString(table));

        // which coins were used
        System.out.println("coin_tracker: " + Arrays.toString(coin_tracker));
        System.out.println("coins: " + extractCoins(coin_tracker));

        return (table[amount] == Integer.MAX_VALUE) ? -1 : table[amount];

    }

    private static List<Integer> extractCoins(int[] coin_trackder) {
        int amount = coin_trackder.length - 1;

        if (coin_trackder[amount] == 0) {
            return Collections.emptyList();
        }
        List<Integer> result = new ArrayList<>();
        int prevIdx = amount;
        while (coin_trackder[prevIdx] != 0) {
            result.add(coin_trackder[prevIdx]);
            prevIdx = prevIdx - coin_trackder[prevIdx];
        }

        return  result;
    }

}