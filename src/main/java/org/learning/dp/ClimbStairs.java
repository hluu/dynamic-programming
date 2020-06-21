package org.learning.dp;

import org.junit.Assert;

/**
 * You are climbing a stair case. It takes n steps to reach to the top.
 *
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 *
 * Note: Given n will be a positive integer.
 *
 * Example 1:
 *
 * Input: 2
 * Output: 2
 * Explanation: There are two ways to climb to the top.
 * 1. 1 step + 1 step
 * 2. 2 steps
 *
 * Example 2:
 *
 * Input: 3
 * Output: 3
 * Explanation: There are three ways to climb to the top.
 * 1. 1 step + 1 step + 1 step
 * 2. 1 step + 2 steps
 * 3. 2 steps + 1 step
 */
public class ClimbStairs {
    public static void main(String[] args) {
        System.out.println(ClimbStairs.class.getName());

        for (int numSteps = 1; numSteps < 10; numSteps++) {
            System.out.printf("Numsteps: %d, num ways: %d\n",
                    numSteps, bruteForce(numSteps, 0));
        }

        test(2,2);
        test(3,3);
        test(4,5);
        test(9,55);
    }

    private static void test(int stairs, int expected) {
        System.out.printf("\n===> test: stairs: %d\n", stairs);

        int actual = bruteForce(stairs, 0);
        int actual2 = topDownMem(stairs, 0, new int[stairs]);

        System.out.printf("expected: %d, actual: %d, actual2: %d\n",
                expected, actual, actual2);

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actual2);
    }


    private static int bruteForce(int numSteps, int currentStep) {
        if (currentStep == numSteps) {
            return 1;
        }

        if (currentStep > numSteps) {
            return 0;
        }

        return bruteForce(numSteps, currentStep+1) +
                bruteForce(numSteps, currentStep+2);
    }

    private static int topDownMem(int numSteps, int currentStep, int[] cache) {
        if (currentStep == numSteps) {
            return 1;
        }

        if (currentStep > numSteps) {
            return 0;
        }

        if (cache[currentStep] != 0) {
            return cache[currentStep];
        }

        cache[currentStep] = topDownMem(numSteps, currentStep+1, cache) +
                topDownMem(numSteps, currentStep+2, cache);

        return cache[currentStep];
    }
}
