package org.learning.dp;

import org.testng.Assert;


import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * Classic org.learning.dp.Fibonacci sequence:
 *
 * f(n) = f(n-1) + f(n-2) for n > 2 else n
 *
 * Runtime : O(2^n)
 */
public class Fibonacci {
    public static void main(String[] args) {
        System.out.println(Fibonacci.class.getName());

        /*for (int i = 0; i <= 30; i++) {
            test(i);
        }*/

        test(8);
    }

    private static void test(int n) {
        // for recursion
        Instant start1 = Instant.now();

        long fibValue1 = fibRecursion(n, 0);
        Instant end1 = Instant.now();

        Duration interval1 = Duration.between(start1, end1);

        // for dp
        Instant start2 = Instant.now();
        System.out.println("======== fibDP");
        long fibValue2 = fibDP(n);
        Instant end2 = Instant.now();

        Duration interval2 = Duration.between(start2, end2);

        long fibValue3 = fibBottomup(n);

        System.out.printf("n: %d, fibValue1: %d, fibValue2: %d, fibValue3: %d, time1: %d, time2: %d\n",
                n, fibValue1, fibValue2, fibValue3,
                interval1.getNano(), interval2.getNano());


        Assert.assertEquals(fibValue1, fibValue2);
        Assert.assertEquals(fibValue1, fibValue3);
    }

    public static long fibRecursion(int n, int level) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be a positive value");
        }

        long result = -1;
        if (n == 0 || n == 1) {
            result = n;
        } else {
            System.out.printf("level: %d, fib(%d)+fib(%d)\n",
                    level, n-1, n-2);
            result = fibRecursion(n-1, level+1) + fibRecursion(n-2, level+1);
        }
        return result;
    }

    private static final int NO_CACHE = -1;

    public static long fibDP(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be a positive value");
        }


        long[] cache = new long[n+1];
        Arrays.fill(cache, NO_CACHE);
        fibDPHelper(n, cache, 0);

        return cache[n];

    }

    private static long fibDPHelper(int n, long[] cache, int level) {
        if (cache[n] != NO_CACHE) {
            return cache[n];
        }

        if (n == 0 || n == 1) {
            cache[n] = n;
            return n;
        }

        System.out.printf("level: %d, fib(%d)+fib(%d)\n",
                level, n-1, n-2);

        long result = fibDPHelper(n-1, cache, level+1) + fibDPHelper(n-2, cache, level+1);
        cache[n] = result;

        return cache[n];
    }

    private static long fibBottomup(int n) {
        if (n == 0) return 0;

        long[] cache = new long[n+1];
        cache[0] = 0;
        cache[1] = 1;

        // filling cache from the bottom up
        for (int i = 2; i <= n; i++) {
            cache[i] = cache[i-1] + cache[i-2];
        }
        return cache[n];

    }
}
