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

        test(3, 2, 3);
        test(3, 7, 28);
        test(1, 7, 1);
        test(7, 1, 1);
        test(1, 1, 1);

    }

    private static void test(int row, int col, int expected) {
        System.out.printf("\n====> test row: %d, col: %d <==== \n",
                row, col);

        int actual = bruteForce(row-1, col-1);

        int actual2 = uniqPaths(row, col);

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

    private static int numPaths(int i, int j) {
        if (i == 0 || j == 0) { // includes the row 0 and col 0
            return 1;
        }
        return numPaths(i - 1, j) + numPaths(i, j - 1);
    }

    private static int uniqPaths(int m, int n) {
        int[][] table = new int[m][n];

        // first column
        for (int row = 0; row < m; row++) {
            table[row][0] = 1;
        }

        // first row
        for (int col = 0; col < n; col++) {
            table[0][col] = 1;
        }

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                table[row][col] = table[row-1][col] + table[row][col-1];
            }
        }

        ArrayUtils.printMatrix(table);
        return table[m-1][n-1];
    }
}
