package org.learning.dp;

import org.learning.common.ArrayUtils;

/**
 * https://leetcode.com/problems/edit-distance/
 *
 * Given two words word1 and word2, find the minimum number of operations required to convert word1 to word2.
 *
 * You have the following 3 operations permitted on a word:
 *
 * Insert a character
 * Delete a character
 * Replace a character
 *
 *
 * Example 1:
 *
 * Input: word1 = "horse", word2 = "ros"
 * Output: 3
 * Explanation:
 * horse -> rorse (replace 'h' with 'r')
 * rorse -> rose (remove 'r')
 * rose -> ros (remove 'e')
 *
 *
 * Example 2:
 *
 * Input: word1 = "intention", word2 = "execution"
 * Output: 5
 * Explanation:
 * intention -> inention (remove 't')
 * inention -> enention (replace 'i' with 'e')
 * enention -> exention (replace 'n' with 'x')
 * exention -> exection (replace 'n' with 'c')
 * exection -> execution (insert 'u')
 */
public class EditDistance {
    public static void main(String[] args) {
        System.out.println("EditDistance.main");

        test("horse", "ros", 3);
        test("intention", "execution", 5);
        test("hien", "luu", 4);
    }

    private static void test(String word1, String word2, int expected) {
        System.out.printf("word1: %s, word2: %s\n", word1, word2);

        int actual = editDist(word1, word2);

        System.out.printf("expected: %d, actual: %d\n", expected, actual);
    }

    /**
     * Calculate the minimum amount of cost to transform word1 into word2
     * - assuming the cost of operation insertion, deletion, and transformation is 1
     *
     * @param word1
     * @param word2
     * @return
     */
    private static int editDist(String word1, String word2) {

        int[][] table = new int[word1.length()+1][word2.length() + 1];

        // initialize the first row
        for  (int col = 1; col <= word2.length();  col++) {
            table[0][col] = col;
        }

        // initialize the first column
        for (int row = 1; row <= word1.length(); row++) {
            table[row][0] = row;
        }

        for (int row = 1; row <= word1.length(); row++) {
            for  (int col = 1; col <= word2.length();  col++) {
                int cost = (word1.charAt(row-1) == word2.charAt(col-1)) ? 0 : 1;

                table[row][col] = Math.min(
                        table[row-1][col] + 1,
                        Math.min(table[row][col-1] + 1,
                                 table[row-1][col-1] + cost)
                );

            }
        }

        ArrayUtils.printMatrix(table);
        return table[word1.length()][word2.length()];
    }


}
