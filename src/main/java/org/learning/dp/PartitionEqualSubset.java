package org.learning.dp;

import org.testng.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Given a non-empty array containing only positive integers, find if the array can be partitioned into two
 * subsets such that the sum of elements in both subsets is equal.
 *
 * https://leetcode.com/problems/partition-equal-subset-sum/
 *
 * Example #1:
 * - Input: [1, 5, 11, 5]
 * - Output: true
 * - Explanation: The array can be partitioned as [1, 5, 5] and [11].
 *
 * Example #2:
 * - Input: [1, 2, 3, 5]
 * - Output: false
 * - Explanation: The array cannot be partitioned into equal sum subsets.
 *
 * Approach
 * - this is an exhaustive combinatorial problem (subset) with a constraint
 *  - where a subset's sum equals to half of the total sum of the given array
 */
public class PartitionEqualSubset {
    public static void main(String[] args) {
        System.out.println("org.learning.dp.PartitionEqualSubset.main");

        test(new int[] {1,5,11,5}, true);
        test(new int[] {1,2,3,5}, false);
        test(new int[] {4,6}, false);
        test(new int[] {4,6,8, 2}, true);

        int[] bigInput = new int[]
            {
                    100,100,100,100,100,100,100,100,100,100,
                    100,100,100,100,100,100,100,100,100,100,
                    100,100,100,100,100,100,100,100,100,100,
                    100,100,100,100,100,100,100,100,100,100,
                    100,100,100,100,100,100,100,100,100,100,
                    100,100,100,100,100,100,100,100,100,100,
                    100,100,100,100,100,100,100,100,100,100,
                    100,100,100,100,100,100,100,100,100,100,
                    100,100,100,100,100,100,100,100,100,100,
                    100,100,100,100,100,100,100,100,100,100
            };

        test(bigInput, true);

    }

    private static void test(int[] input, boolean expected) {
        System.out.println("input = [" + Arrays.toString(input) + "]");

        boolean actual = (input.length < 50) ? canPartitionBF(input) :
                canPartitionDP(input);

        System.out.printf("expected: %b, actual: %b\n", expected, actual);

        Assert.assertEquals(actual, expected);
    }

    private static boolean canPartitionDP(int[] nums) {
        int sum = IntStream.of(nums).sum();

        if (sum % 2 != 0) {
            return false;
        }

        int halfSum = sum / 2;

        return canPartitionDPHelper(nums, 0, 0, halfSum,
                new HashMap<String, Boolean>());
    }

    private static boolean canPartitionDPHelper(int[] input,
                                                int idx,
                                                int sumSoFar,
                                                int targetSum,
                                                Map<String,Boolean> cache) {

        String state = idx + ":" + sumSoFar;

        if (cache.containsKey(state)) {
            return cache.get(state);
        }
        // base cases
        if (sumSoFar == targetSum) {
            return true;
        }

        if (idx == input.length || sumSoFar > targetSum) {
            return false;
        }

        boolean result = canPartitionDPHelper(input, idx+1, sumSoFar, targetSum, cache) ||
                canPartitionDPHelper(input, idx+1, sumSoFar + input[idx],
                        targetSum, cache);

        cache.put(state, result);

        return  result;
    }

    private static boolean canPartitionBF(int[] nums) {
        int sum = IntStream.of(nums).sum();

        if (sum % 2 != 0) {
            return false;
        }

        int halfSum = sum / 2;

        return canPartitionBFHelper(nums, 0, 0, halfSum);
    }

    private static boolean canPartitionBFHelper(int[] input,
                                                int idx,
                                                int sumSoFar,
                                                int targetSum) {

        // base cases
        if (sumSoFar == targetSum) {
            return true;
        }

        if (idx == input.length || sumSoFar > targetSum) {
            return false;
        }

        return canPartitionBFHelper(input, idx+1, sumSoFar, targetSum) ||
                canPartitionBFHelper(input, idx+1, sumSoFar + input[idx],
                        targetSum);
    }
}
