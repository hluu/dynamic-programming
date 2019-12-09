import org.junit.Assert;

import java.util.Arrays;

/**
 * Give an number, figure out the mininum # of steps to reduce it down to 1
 * - divide by 3
 * - divide by 2
 * - subtract by 1
 *
 * - recurrence
 *   - reduce(n) = 1+ min{ reduce(n/3) if divisible, reduce(n/2) if divisible , reduce(n-1)}
 *   - base case reduce(1) = 0
 *
 *
 * Try all possible guesses and determine the minimum
 */
public class MinStepToOne {
    public static void main(String[] args) {
        System.out.println(MinStepToOne.class.getName());

        test(1, 0);
        test(2, 1);
        test(3, 1);
        test(4, 2);
        test(10, 3);
        test(21, 4);

        test(500, 9);
        //test(1000, 9);
        //test(10000, 14);
    }

    private static void test(int n, int expected) {
        System.out.println("\n===> test: " + n);
        int actual1 = topDownBF(n);

        printCallCount("topDownBF");

        int[] cache = new int[n+1];
        Arrays.fill(cache,-1);

        int actual2 = topDownMemoize(n, cache);
        printCallCount("topDownMemoize");

        int actual3 = bottomUp(n);
        printCallCount("bottomUp");

        System.out.printf("expected: %d, actual1: %d, actual2: %d, actual3: %d\n",
                expected, actual1, actual2, actual3);

        Assert.assertEquals(expected, actual1);
        Assert.assertEquals(expected, actual2);
        Assert.assertEquals(expected, actual3);

    }

    private static int callCount = 0;

    /**
     *
     * recurrence
     * - reduce(n) = 1+ min{ reduce(n/3) if divisible, reduce(n/2) if divisible , reduce(n-1)}
     * - base case reduce(1) = 0
     * @param n
     * @return
     */
    private static int topDownBF(int n) {

        // base case
        if (n == 1) {
            return 0;
        }

        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;

        if (n % 3 == 0) {
            min1 = topDownBF(n/3);
        }

        if (n % 2 == 0) {
            min2 = topDownBF(n/2);
        }

        int min3 = topDownBF(n-1);

        callCount++;
        return (1 + min(min1, min2, min3));

    }

    /**
     *
     * recurrence
     * - reduce(n) = 1+ min{ reduce(n/3) if divisible, reduce(n/2) if divisible , reduce(n-1)}
     * - base case reduce(1) = 0
     * @param n
     * @return
     */
    private static int topDownMemoize(int n, int[] cache) {

        callCount++;

        if (cache[n] >= 0) {
            return cache[n];
        }

        // base case
        if (n == 1) {
            cache[n] = 0;
            return 0;
        }

        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;

        if (n % 3 == 0) {
            min1 = topDownMemoize(n/3, cache);
        }

        if (n % 2 == 0) {
            min2 = topDownMemoize(n/2, cache);
        }

        int min3 = topDownMemoize(n-1, cache);

        cache[n] = (1 + min(min1, min2, min3));
        return cache[n];

    }

    /**
     * Bottom up - goes from 1 to n
     * @param n
     * @return
     */
    private static int bottomUp(int n) {


        int[] cache = new int[n+1];

        // base case
        if (n <=1) {
            return 0;
        }
        cache[2] = 1;

        // going from 1 to n
        for (int i = 3; i <= n; i++) {
            callCount++;

            int min1 = Integer.MAX_VALUE;
            int min2 = Integer.MAX_VALUE;

            if (i % 3 == 0) {
                min1 = cache[i / 3];
            }

            if (i % 2 == 0) {
                min2 = cache[i / 2];
            }

            int min3 = cache[i - 1];

            cache[i] =  1 + min(min1, min2, min3);
        }

        return cache[n];

    }

    private static int min(int min1, int min2, int min3) {
        return Math.min(min1, Math.min(min2, min3));
    }

    private static void printCallCount(String prefix) {
        System.out.printf("\ncallCount - %s, %d\n",
                prefix, callCount);

        callCount = 0;
    }
}
