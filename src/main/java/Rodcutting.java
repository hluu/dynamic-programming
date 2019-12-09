import java.util.Arrays;

/**
 * Give a rod of length n and a price table for price of a smaller rod length n.
 * Give the best way to cut the rod such that would give you the maximum revenue.
 *
 * Example:
 *   length:  1  2  3  4  5  6  7  8
 *    price:  1  5  8  9 10 17 17 20
 *
 *  Given a rod of length 8, what is best way to cut to give the max revenue
 *
 * Analysis:
 *   - Given a rod of size l => mp(l)
 *   - subproblems: if we can optimally cut the rod into 2 pieces
 *     - l => l1 + l2
 *       - l1 => l11 + l12
 *       - l2 => l21 + l22
 *   - mp(l) = max(p(l), mp(1) + mp(l-1), mp(2) + mp(l-2), ...., mp(l-1) + mp(1)
 *     - all the possible ways of cutting them into two pieces
 *     - we don't know which one gives us the max price, so check all of them
 *     - mp(l) => max(p(i), mp(l-i)) for i from 0 to n
 *
 *   - top down runtime => O(n^2) where n is the length of the rod
 *
 * Resources:
 *   - https://www.youtube.com/watch?v=RYPsOJmhwgE
 */
public class Rodcutting {
    public static void main(String[] args) {
        System.out.println(Rodcutting.class.getName());

        test(new int[] {0, 1, 5, 8, 9, 10, 17, 17, 20}, 8, 22);

    }

    private static void test(int[] prices, int len, int expected) {
        System.out.printf("==> test: prices: %s, len: %d <===\n",
                Arrays.toString(prices), len);



        int topDownActual = topDown(prices, len);

        printCallCount("topDown");

        int[] cache = new int[len+1];
        Arrays.fill(cache, -1);


        int topDownMemoizeActual = topDownMemoize(prices, len, cache);
        printCallCount("memoize");

        int bottomUpActual = bottomUp(prices, len);
        printCallCount("bottomUp");

        System.out.printf("actual1: %d, actualMemoize: %d, actualBottomup: %d, expected: %d\n",
                topDownActual, topDownMemoizeActual, bottomUpActual, expected);



    }

    private static int topDownCallCount = 0;

    /**
     * Top down - brute force - trying out all the possible combinations
     *
     * 8 -> 7, 6, 5, 4, 3, 2, 1
     *      7
     *
     * @param prices
     * @param len
     * @return
     */
    private static int topDown(int[] prices, int len) {

        if (len == 0) {
            return 0;
        }

        int maxPrice = -1;

        for (int sLen = 1; sLen <= len; sLen++) {
            maxPrice = Math.max(maxPrice,
                    prices[sLen] + topDown(prices, len-sLen));
        }

        topDownCallCount++;
        return maxPrice;
    }

    private static int topDownMemoize(int[] prices, int len, int[] cache) {

        if (len == 0) {
            cache[len] = 0;
            return 0;
        }

        if (cache[len] >= 0) {
            return cache[len];
        }

        topDownCallCount++;

        int maxPrice = -1;

        for (int sLen = 1; sLen <= len; sLen++) {
            int priceAtsLen = prices[sLen];
            int priceAtRemaining = topDownMemoize(prices, len-sLen, cache);

            int combinedPrice = priceAtsLen + priceAtRemaining;
            maxPrice = Math.max(maxPrice,combinedPrice);
        }

        cache[len] = maxPrice;
        return maxPrice;
    }

    /**
     * Runtime:
     *  - 1..1, 1..2, 1..3, 1..4, 1..5, 1..6, 1..n
     *  - arithmetic series O(n^2)
     *
     *
     * @param prices
     * @param len
     * @return
     */
    private static int bottomUp(int[] prices, int len) {
        int[] cache = new int[len+1];
        Arrays.fill(cache, 0);

        int[] bestCutSize = new int[len+1];
        Arrays.fill(bestCutSize, 0);

        for (int sLen = 1; sLen <= len; sLen++) {
            int maxPrice = -1;
            for (int ssLen = 1; ssLen <= sLen; ssLen++) {
                topDownCallCount++;

                int newPrice = prices[ssLen] + cache[sLen-ssLen];
                if (newPrice > maxPrice) {
                    maxPrice = newPrice;
                    // store the ssLen that gives us the best price
                    // if we know ssLen then we can figure out the other length
                    // since the rod is cut into only 2 pieces since len = sLen + ssLen
                    bestCutSize[sLen] = ssLen;
                }

            }
            cache[sLen] = maxPrice;
        }

        System.out.println(Arrays.toString(cache));
        System.out.println(Arrays.toString(bestCutSize));

        int tmpLen = len;
        System.out.println("====> Rod sizes:");
        while (tmpLen > 0) {
            System.out.print(bestCutSize[tmpLen] + " ");
            tmpLen = tmpLen - bestCutSize[tmpLen];
        }
        return cache[len];
    }



    private static void printCallCount(String prefix) {
        System.out.printf("\ntopDownCallCount - %s, %d\n",
                prefix, topDownCallCount);

        topDownCallCount = 0;
    }
}
