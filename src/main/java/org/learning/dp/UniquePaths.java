package org.learning.dp;

import org.learning.common.ArrayUtils;
import org.testng.Assert;

/**
 * A robot is located at the top-left corner of a m x n grid
 * (marked 'Start' in the diagram below).
 *
 * The robot can only move either down or right at any point in time.
 * The robot is trying to reach the bottom-right corner of the grid
 * (marked 'Finish' in the diagram below).
 *
 * How many possible unique paths are there?
 *
 * - the definition of a path is a series of steps taken from point a to point b
 *   - meaning only when it gets to point B, then that is considered as a path
 *
 * Input: m = 3, n = 2
 * Output: 3
 * Explaination:
 *  - From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
 *  - 1. Right -> Right -> Down
 *  - 2. Right -> Down -> Right
 *  - 3. Down -> Right -> Right
 *
 * Example 2:
 *
 * Input: m = 7, n = 3
 * Output: 28
 *
 * Analysis:
 *   - Assuming that up(row, col) for all the cells except the last cell
 *   - what would be the answer for up(row-1, col) + up(row, col-1)
 *     - base case (row=0, col=0) => then 1
 *
 * https://leetcode.com/problems/unique-paths/
 *  https://leetcode.com/problems/unique-paths/discuss/405983/Easy-understand-Java-Solutions-with-Explanations-(DP-Top-down-Bottom-up-Linear-Space)
 */
public class UniquePaths {
    public static void main(String[] args) {
        System.out.println(UniquePaths.class.getName());

        test(1, 1, 1);
        test(3, 2, 3);
        test(2, 3, 3);
        test(3, 3, 6);
        test(3, 7, 28);
        test(1, 7, 1);
        test(7, 1, 1);
        test(1, 1, 1);

    }

    private static void test(int row, int col, int expected) {
        System.out.printf("\n====> test row: %d, col: %d <==== \n",
                row, col);

        int actual = bruteForce(row-1, col-1);

        int actual2 = uniqPathsBottomUp(row, col);

        System.out.printf("expected: %d, actual: %d, actual2: %d\n",
                expected, actual, actual2);

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actual2);
    }

    private static int bruteForce(int row, int col) {

        // the reason it is || because it is a path, not exact cell
        if (row == 0 || col == 0) {
            return 1;
        }


        return bruteForce(row-1, col) +
                bruteForce( row, col-1);
    }

    /**
     * The bottom up approach uses 2-d array to keep track of the paths
     *            go down     go to the right
     * f(n,m) = f(n-1, m) + f(n, m-1)
     * - base case when either m or n is 1, then there is only one path
     *
     * @param m
     * @param n
     * @return
     */
    private static int uniqPathsBottomUp(int m, int n) {
        int[][] table = new int[m+1][n+1];

        // first column
        for (int row = 1; row <= m; row++) {
            table[row][1] = 1;
        }

        // first row
        for (int col = 1; col <= n; col++) {
            table[1][col] = 1;
        }

        for (int row = 2; row <= m; row++) {
            for (int col = 2; col <= n; col++) {
                table[row][col] = table[row-1][col] + table[row][col-1];
            }
        }

        ArrayUtils.printMatrix(table);
        return table[m][n];
    }
}
