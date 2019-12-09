import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Longest common subsequence of given X and Y strings.
 *
 * Analyze:
 *  - brute force
 *      - take each subsequence of X and see if they exist in Y
 *        - and maintain the longest length
 *      - there are O(2^n) subsequences in X if its length is n
 *      - total runtime O(2^n * m) m is length of Y
 *  - Recursive decomposition
 *      - assuming there is an LCS(i,j) at i,j
 *          - if character at X(i) == Y(j) then LCS(i,j) = 1 + LCS(i-1, j-1)
 *          - if character at X(i) != Y(j) then there are 2 choices
 *            - LCS(i-1, j) and LCS(i, j-1)
 *            - since we don't know which one is longer therefore
 *            - LCS(i,j) = max{LCS(i-1, j), LCS(i, j-1)}
 *
 */
public class LCS {
    public static void main(String[] args) {
        System.out.println(LCS.class.getName());

        test("ABCDGH", "AEDFHR", 3);
        test("ABAZDC", "BACBAD", 4);
        test("AGGTAB", "GXTXAYB", 4);
        test("abcdef", "a", 1);
        test("ab", "xyz", 0);

        test("spanking", "amputation", 4);

    }

    private static void test(String x, String y, int expected) {
        System.out.printf("\n===> test: x = %s, y = %s <====\n",
                x, y);

        int actual1 = lcsTopdown(x, y, x.length()-1, y.length()-1);

        printCallCount("top down");

        int[][] cache = new int[x.length()+1][y.length()+1];
        populateDefaultCacheValue(cache, -1);

        int actual2 = lcsTopdownMemoize(x, y, x.length(), y.length(), cache);
        printCallCount("top down memoize");

        int actual3 = lcsBottomUp(x, y);

        System.out.printf("actual1: %d, actual2: %d, actual3: %d, expected: %d\n",
                actual1, actual2, actual3, expected);

        printCallCount("bottom up");

        Assert.assertEquals(expected, actual1);
        Assert.assertEquals(expected, actual2);
        Assert.assertEquals(expected, actual3);
    }

    private static int callCount = 0;

    private static int lcsTopdown(String x, String y, int xPos, int yPos) {
        //System.out.printf("xPos: %d, yPos: %d\n", xPos, yPos);

        callCount++;

        if (xPos < 0 || yPos < 0) {
            return 0;
        }



        if (x.charAt(xPos) == y.charAt(yPos)) {
            return 1 + lcsTopdown(x, y, xPos-1, yPos-1);
        } else {
            // guess and take max
            int xMinusOne = lcsTopdown(x, y, xPos-1, yPos);
            int yMinusOne = lcsTopdown(x, y, xPos, yPos-1);
            return Math.max(xMinusOne, yMinusOne);
        }

    }

    private static int lcsTopdownMemoize(String x, String y, int xPos, int yPos, int[][] cache) {
        //System.out.printf("xPos: %d, yPos: %d\n", xPos, yPos);

        if (xPos == 0 || yPos == 0) {
            cache[xPos][yPos] = 0;
            return 0;
        }

        if (cache[xPos-1][yPos-1] != -1) {
            return cache[xPos-1][yPos-1];
        }

        callCount++;


        int lcs = -1;
        if (x.charAt(xPos-1) == y.charAt(yPos-1)) {
            lcs =  1 + lcsTopdownMemoize(x, y, xPos-1, yPos-1, cache);
        } else {
            // guess and take max
            int xMinusOne = lcsTopdownMemoize(x, y, xPos-1, yPos, cache);
            int yMinusOne = lcsTopdownMemoize(x, y, xPos, yPos-1, cache);
            lcs = Math.max(xMinusOne, yMinusOne);
        }

        cache[xPos-1][yPos-1] = lcs;

        return lcs;


    }

    /**
     * Bottom up - runtime O(n * m)
     *
     * - use cache to store the value of subproblem
     * - use metadata to store the metadata to collect the common letters
     *
     * @param x
     * @param y
     * @return
     */
    private static int lcsBottomUp(String x, String y) {
        int[][] cache = new int[x.length()+1][y.length()+1];
        String[][] metadata = new String[x.length()+1][y.length()+1];


        for (int row = 1; row <= x.length(); row++) {
            for (int col = 1; col <= y.length(); col++) {
                callCount++;
                if (x.charAt(row-1) == y.charAt(col-1)) {
                    cache[row][col] = 1 + cache[row-1][col-1];
                    metadata[row][col] = "d";
                } else if (cache[row-1][col] > cache[row][col-1]) {
                    cache[row][col] = cache[row-1][col];
                    metadata[row][col] = "u";
                } else {
                    cache[row][col] = cache[row][col-1];
                    metadata[row][col] = "l";
                }
            }
        }

        List<Character> collector = new ArrayList<>();
        collectChar(x, metadata, collector, x.length(), y.length());

        System.out.println("LCS: " + collector);

        return cache[x.length()][y.length()];
    }

    /**
     * Collect the character for the common subsequence by looking for
     * cells in metadata with "d";
     *
     * @param metadata
     * @param collector
     * @param row
     * @param col
     */
    private static void collectChar(String x, String[][] metadata,
                                    List<Character> collector, int row, int col) {
        if (row == 0 || col ==0) {
            return;
        }

        if (metadata[row][col].equals("d")) {
            // since we want to collect the character in right order
            // so we recurse first and then collect the data
            // i.e. post order in binary tree
            collectChar(x,  metadata, collector, row-1, col-1);
            collector.add(x.charAt(row-1));
        } else if (metadata[row][col].equals("u")) {
            collectChar(x,  metadata, collector, row-1, col);
        } else if (metadata[row][col].equals("l")) {
            collectChar(x,  metadata, collector, row, col-1);
        }
    }

    private static void printCallCount(String prefix) {
        System.out.printf("\ncallCount - %s, %d\n",
                prefix, callCount);

        callCount = 0;
    }

    private static void populateDefaultCacheValue(int[][] cache, int defaultValue) {

        for (int[] row : cache) {
            Arrays.fill(row, defaultValue);
        }
        /*
        for (int row = 0; row < cache.length; row++) {
            for (int col = 0; col < cache[0].length; col++) {
                cache[row][col] = defaultValue;
            }
        }*/
    }
}
