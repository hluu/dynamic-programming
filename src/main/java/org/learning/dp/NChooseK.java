package org.learning.dp;

import org.learning.common.ArrayUtils;

/**
 * What is n choose k?
 *
 *           exclude     include
 * f(n,k) = f(n-1,k) + f(n-1,k-1)
 *
 *
 *
 * Time complexity: T(n) = O(nk)
 * Space complexity: O(nk) table
 *  - notice the dependency graph
 *    - top -> bottom and left > right
 *  - could optimize by maintaining only 2 rows
 */
public class NChooseK {
    public static void main(String[] args) {
        System.out.println(NChooseK.class.getName());

        test(2,2,1);
        test(0,2,1);
        test(5,2,10);  // 5!/2!(3!)
    }

    private static void test(int n, int k, int expected) {
        System.out.printf("n: %d, k:%d\n",n,k);

        int actual = nChoosek(n,k);

        System.out.printf("expected: %d, actual: %d\n", expected, actual);
    }

    private static int nChoosek(int n, int k) {
        // base case
        if (k == 0 || (k == n) || n == 0) {
            // n choose 0 -> there is only one possibility
            // n choose k -> there is only one possibility
            return 1;
        }

        int[][] table = new int[n+1][k+1];

        // n choose 0 : only 1 possibility
        for (int row = 0; row <= n; row++) {
            table[row][0] = 1;
        }

        // n choose n, : only 1 possibility
        for (int col = 0; col <= k; col++) {
            table[col][col] = 1;
        }

        // left to right and top to bottom traversal
        for (int row = 2; row <= n; row++) {
            // maximum col that we want to reach is when it is equals to row
            // so we take the min of (row, k)
            // row goes from 0 to n and n > k
            for (int col = 1; col <= Math.min(k, row); col++) {
                table[row][col] = table[row-1][col] + table[row-1][col-1];
            }
        }

        ArrayUtils.printMatrix(table);
        return table[n][k];
    }
}
