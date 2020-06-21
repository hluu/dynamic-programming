package org.learning.dp;

import java.util.Arrays;

/**
 * Given a square array of integers A, we want the minimum sum of a falling path
 * through A.
 *
 * A falling path starts at any element in the first row, and chooses one element
 * from each row.  The next row's choice must be in a column that is different
 * from the previous row's column by at most one.
 *
 *
 *
 * Example 1:
 *
 * Input: [[1,2,3],[4,5,6],[7,8,9]]
 * Output: 12
 * Explanation:
 * The possible falling paths are:
 * [1,4,7], [1,4,8], [1,5,7], [1,5,8], [1,5,9]
 * [2,4,7], [2,4,8], [2,5,7], [2,5,8], [2,5,9], [2,6,8], [2,6,9]
 * [3,5,7], [3,5,8], [3,5,9], [3,6,8], [3,6,9]
 * The falling path with the smallest sum is [1,4,7], so the answer is 12.
 */
public class MinFallPathSum {
    public static void main(String[] args) {
        System.out.println(MinFallPathSum.class.getName());

        test(new int[][] {{69}}, 69);
        test(new int[][] {{1,2,3},{4,5,6},{7,8,9}}, 12);
        test(new int[][] {{-62,-63,23,31},{-5,-82,52,76},{85,69,80,85},{8,-22,41,-45}}, -110);

    }

    private static void test(int[][] square, int expected) {
        System.out.println("====> test ");
        for (int[] row : square){
            System.out.println(Arrays.toString(row));
        }

        int actual = minFallPathSumBF(square);
        int actual2 = minFallPathSumMem(square);


        System.out.printf("expected: %d, actual: %d, actual: %d\n", expected,
                actual, actual2);
    }

    private static int minFallPathSumBF(int[][] square) {
        int min = Integer.MAX_VALUE;
        for (int col = 0; col < square[0].length; col++) {
            min = Math.min(min, minFallPathSumHelper(square, square[0][col],
                    col, 1));
        }

        return min;
    }

    private static int minFallPathSumMem(int[][] square) {
        int[][] cache = new int[square.length][square[0].length];
        boolean[][] flag = new boolean[square.length][square[0].length];
        int min = Integer.MAX_VALUE;
        for (int col = 0; col < square[0].length; col++) {
            min = Math.min(min, minFallPathSumHelperMem(square, square[0][col],
                    col, 1, cache, flag));
        }

        //print out the cache
        System.out.println("=========== flags ===========");
        for (boolean[] cacheRow : flag) {
            System.out.println(Arrays.toString(cacheRow));
        }

        System.out.println("=========== cache ===========");
        for (int[] cacheRow : cache) {
            System.out.println(Arrays.toString(cacheRow));
        }

        return min;
    }


    private static int minFallPathSumHelper(int[][] square, int soFar, int currCol, int nextRow) {
        if (nextRow == square.length) {
            return soFar;
        }
        if (nextRow == square.length-1) {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < square[nextRow].length; i++) {
                if (Math.abs(currCol-i) < 2) {
                    min = Math.min(min, soFar + square[nextRow][i]);
                }
            }

            return min;
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < square[nextRow].length; i++) {
            if (Math.abs(currCol-i) < 2) {
                int tmp = minFallPathSumHelper(square,  soFar + square[nextRow][i], i, nextRow+1);
                min = Math.min(min, tmp);
            }
        }

        return min;
    }

    private static int minFallPathSumHelperMem(int[][] square, int soFar, int currCol, int nextRow,
                                               int[][] cache, boolean[][] flag) {
        if (nextRow == square.length) {
            return soFar;
        }

        if (flag[nextRow][currCol]) {
            return cache[nextRow][currCol];
        }

        if (nextRow == square.length-1) {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < square[nextRow].length; i++) {
                if (Math.abs(currCol-i) < 2) {
                    min = Math.min(min, soFar + square[nextRow][i]);
                    flag[nextRow][currCol] = true;
                    cache[nextRow][currCol] = min;
                }
            }

            return min;
        }


        int min = Integer.MAX_VALUE;
        for (int i = 0; i < square[nextRow].length; i++) {
            if (Math.abs(currCol-i) < 2) {
                int tmp = minFallPathSumHelperMem(square,  soFar + square[nextRow][i],
                        i, nextRow+1, cache, flag);
                min = Math.min(min, tmp);

                flag[nextRow][currCol] = true;
                cache[nextRow][currCol] = min;

            }
        }

        return min;
    }
}
