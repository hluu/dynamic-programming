package org.learning.dp;

import org.learning.common.ArrayUtils;
import org.testng.Assert;

/**
 * https://leetcode.com/problems/unique-paths/
 *
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 *
 * The robot can only move either down or right at any point in time. The robot is trying
 * to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 *
 * How many possible unique paths are there?
 *
 * Example 1:
 *
 * Input: m = 3, n = 2
 * Output: 3
 * Explanation:
 * From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
 * 1. Right -> Right -> Down
 * 2. Right -> Down -> Right
 * 3. Down -> Right -> Right
 *
 * Example 2:
 * Input: m = 7, n = 3
 * Output: 28
 */
public class CountUniquePaths {
    public static void main(String[] args) {
        System.out.println(CountUniquePaths.class.getName());

        test(3,2,3);
        test(3,3,6);
        test(5,5,70);
        test(7,3,28);
    }

    private static void test(int m, int n, int expected) {
        System.out.printf("m: %d, n: %d\n",m,n);

        int actual = countUniquePaths(n,m);

        System.out.printf("expected: %d, actual: %d\n", expected, actual);

        Assert.assertEquals(actual, expected);
    }

    /**
     * Bottom up approach
     *
     * Recurrence
     * - f(numRow, numCol) = f(numRow-1, numCol) + f(numRow, numCol-1)
     *
     * Number of subproblems
     * - count the # of vertices in the DAG
     * - in a table of numRow and numCol, there are (numRow*numCol) cells
     *
     * @param numRow
     * @param numCol
     * @return
     */
    private static int countUniquePaths(int numRow, int numCol) {
        int[][] table = new int[numRow][numCol];

        for (int col = 0; col < numCol; col++) {
            table[0][col] = 1;
        }

        for (int row = 0; row < numRow; row++) {
            table[row][0] = 1;
        }

        for (int row = 1; row < numRow; row++) {
            for (int col = 1; col < numCol; col++) {
                table[row][col] = table[row-1][col] + table[row][col-1];
            }
        }

        ArrayUtils.printMatrix(table);
        return table[numRow-1][numCol-1];
    }

    /**
     * Top down recursive approach
     * @param cache
     * @param row
     * @param col
     * @return
     */
    private static int uniquePathsHelper(int[][] cache, int row, int col) {
        if (row == 0 || col == 0) {
            return 1;
        }

        if (cache[row][col] == 0) {
            // compute the value and then cache
            int cnt = uniquePathsHelper(cache, row-1, col) + uniquePathsHelper(cache, row, col-1);
            cache[row][col] = cnt;
        }

        return cache[row][col];


    }
}
