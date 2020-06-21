package org.learning.dp;

import org.testng.Assert;

import java.util.Arrays;

/**
 * Give a set of wine bottle and associated sale price for each bottom.
 * Determine the maximum profit of selling the wine bottles based on the constraints below
 * - can only sale wine bottle from either end
 * - the order of the bottles can't be altered
 * - the price of that sale is bottle price * year
 *
 * For example:
 *   [2,3,5,1,4] => 50
 *   [1,4,2,3] => 29
 *
 * Resources:
 *  * https://coderworld109.blogspot.com/2018/01/introduction-to-dynamic-programming.html
 */
public class SellWineBottles {
    public static void main(String[] args) {
        System.out.println(SellWineBottles.class.getName());

        test(new int[] {1,4,2,3}, 29);
        test(new int[] {2,3,5,1,4}, 50);
    }

    private static void test(int[] wineBottles, int expected) {
        System.out.println("\n==> test: " + Arrays.toString(wineBottles) + " <===");

        int actual1 = topDown(wineBottles, 0, wineBottles.length-1, 1);
        printCallCount("topDown");

        int actual2 = topDownMemoize(wineBottles, 0, wineBottles.length-1, 1);
        printCallCount("topDownMemoize");

        System.out.printf("expected: %d, actual1: %d, actual2: %d\n", expected,
                actual1, actual2);

        Assert.assertEquals(actual1, expected);
        Assert.assertEquals(actual2, expected);
    }

    /**
     * Brute force - try selling from both ends and take the max
     *
     * Recurrence sellWine(n) = [priceLeft|priceRight] * year +
     *                             max{ sellWine[n-1] of left, sellWine[n-1] of right }
     *
     * Runtime: O(2^n) because at each level, try both options.  Depth of tree is the number
     * of bottles.
     *
     * @param wineBottles
     * @param leftIdx
     * @param rightIdx
     * @param year
     * @return
     */
    private static int topDown(int[] wineBottles, int leftIdx, int rightIdx, int year) {

        if (leftIdx > rightIdx) {
            return 0;
        }

        int leftPrice = wineBottles[leftIdx] * year + topDown(wineBottles, leftIdx+1, rightIdx,
                year+1);

        int rightPrice = wineBottles[rightIdx] * year + topDown(wineBottles, leftIdx, rightIdx-1,
                year + 1);

        callCount++;
        return Math.max(leftPrice, rightPrice);
    }

    private static int topDownMemoize(int[] wineBottles, int leftIdx, int rightIdx, int year) {
        int[][] cache = new int[wineBottles.length][wineBottles.length];

        for (int[] row : cache) {
            Arrays.fill(row,-1);
        }

        return topDownMemoizeHelper(wineBottles, leftIdx, rightIdx, year, cache);
    }


    private static int topDownMemoizeHelper(int[] wineBottles, int leftIdx, int rightIdx, int year,
                                            int[][] cache) {

        if (leftIdx > rightIdx) {
            return 0;
        }

        if (cache[leftIdx][rightIdx] > -1) {
            return cache[leftIdx][rightIdx];
        }

        int leftPrice = wineBottles[leftIdx] * year +
                            topDownMemoizeHelper(wineBottles, leftIdx+1, rightIdx,
                                year+1, cache);

        int rightPrice = wineBottles[rightIdx] * year +
                            topDownMemoizeHelper(wineBottles, leftIdx, rightIdx-1,
                                year + 1, cache);

        cache[leftIdx][rightIdx] = Math.max(leftPrice, rightPrice);
        callCount++;
        return cache[leftIdx][rightIdx];
    }

    private static int callCount = 0;

    private static void printCallCount(String prefix) {
        System.out.printf("\ncallCount - %s, %d\n",
                prefix, callCount);

        callCount = 0;
    }
}
