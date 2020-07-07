package org.learning.dp;

import org.testng.Assert;

import java.util.Arrays;


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
 *
 * @author hluu
 *
 */
public class CoinChange {

    public static void main(String[] args) {


        test(6, new int[] {1,3,4}, 2);
        test(0, new int[] {1,3,4}, 0);
        test(10, new int[] {1,5,7}, 2);
        test(8, new int[] {1,5,7}, 2);
        test(11, new int[] {1,5,7}, 3);
        test(12, new int[] {1,5,7}, 2);

    }

    private static void test(int amount, int[] coins, int expected) {
        System.out.printf("\namount: %d, coins: %s\n", amount, Arrays.toString(coins));

        int actual = coinChange(amount, coins);

        System.out.printf("expected: %d, actual: %d\n", expected, actual);

        Assert.assertEquals(actual, expected);
    }

    public static int coinChange(int amount, int[] denominations) {
        int minCoin[] = new int[amount+1];

        for (int amt = 1; amt <= amount; amt++) {
            int minSoFar = Integer.MAX_VALUE;
            for (int j = 0; j < denominations.length; j++) {
                // make sure the denomination is less than or equal to amount
                int denomination = denominations[j];
                if (amt >= denomination) {
                    //if (denomination <= amt) {
                    minSoFar = Math.min(minCoin[amt - denominations[j]], minSoFar);
                }
            }
            minCoin[amt] = minSoFar + 1;
            System.out.println("amount: " + amt + " minCoin: " + Arrays.toString(minCoin));
        }

        return minCoin[amount];
    }

}