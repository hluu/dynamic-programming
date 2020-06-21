package org.learning.dp;

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
 *  https://leetcode.com/problems/unique-paths/discuss/405983/Easy-understand-Java-Solutions-with-Explanations-(DP-Top-down-Bottom-up-Linear-Space)
 */
public class UniquePaths {
    public static void main(String[] args) {
        System.out.println(UniquePaths.class.getName());

        test(3, 2, 3);
        test(3, 7, 28);
    }

    private static void test(int row, int col, int expected) {
        System.out.printf("\n====> test row: %d, col: %d <==== \n",
                row, col);

        int actual = bruteForce(row-1, col-1);

        System.out.printf("expected: %d, actual: %d\n",
                expected, actual);
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
}
