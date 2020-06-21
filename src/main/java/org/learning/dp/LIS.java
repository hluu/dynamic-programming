package org.learning.dp;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an unsorted array of integers, find the length of longest increasing subsequence.
 *
 * Resources:
 * - https://www.youtube.com/watch?v=fV-TF4OvZpk
 * - https://github.com/bephrem1/backtobackswe/blob/master/Dynamic%20Programming,%20Recursion,%20&%20Backtracking/longestIncreasingSubsequence.java
 * - https://www.geeksforgeeks.org/longest-increasing-subsequence-dp-3/
 * - https://github.com/mission-peace/interview/tree/master/src/com/interview/dynamic
 * - https://leetcode.com/problems/longest-increasing-subsequence/solution/
 * - https://leetcode.com/articles/longest-increasing-subsequence/
 *
 *
 * Example:
 *  - [10,9,2,5,3,7,101,18] => 4
 *  - [2,3,7,101] => 4
 *
 * Analysis
 *  - input: [-1, 3, 4, 5, 2, 2, 2]
 *           [ 1, 2, 3, 4, 2, 2, 2]
 *
 *  - what are the choices that we have to make for org.learning.dp.LIS(i)?
 *    - essentially all the org.learning.dp.LIS of all the digits before i
 *    - this is an implicit selection
 *
 *  - assuming we have org.learning.dp.LIS(i) at index i in the array
 *  - can we extend the increase subsequence among all the previous values up to i
 *  - we get here by org.learning.dp.LIS(i) = 1 + max (org.learning.dp.LIS(i-1))
 *    - for all j from 0 to i - 1 and arr[j] < arr[i]
 *
 */
public class LIS {
    public static void main(String[] args) {
        System.out.println(LIS.class.getName());

        //test(new int[] {10, 22, 9, 33, 21, 50, 41, 60, 80}, 6);
        test(new int[] {50, 3, 10, 7, 40, 80}, 4);
        /*test(new int[] {3, 10, 2, 1, 20}, 3);
        test(new int[] {5,4,3,2,1}, 1);
        test(new int[] {1,2,3,4,5}, 5);
        test(new int[] {10,9,2,5,3,7,101,18}, 4);

        test(new int[] {4,10,4,3,8,9}, 3);*/
        //test(new int[] {1,2,3,4,5,6,7}, 7);
        //test(new int[] {3,2,6,4,5,1}, 3);
        /*
        test(new int[] {0,4,12,2,10,6,9,13,3,11,7,15}, 6);
        */
    }

    private static void test(int[] arr, int expected) {
        System.out.println("\n===> test: => " + Arrays.toString(arr) + " size: " +
                arr.length);

        int actual1 = topDownPickOrNot(arr, Integer.MIN_VALUE, 0);
        printCallCount("brute force subsequence");

        int[] globalMax = new int[1];
        topDown(arr,  arr.length-1, globalMax);
        int actual2 = globalMax[0];
        printCallCount("topDown");


        int[] globalMax2 = new int[1];
        topDownWithMemoize(arr, arr.length-1, globalMax2, new int[arr.length]);
        int actual4 = globalMax2[0];
        printCallCount("topDown with memoize");


        int actual3 = bottomUpDP(arr);
        printCallCount("bottom up");


        int actual5 = dpWithBinarySearch(arr);

        System.out.printf("-- expected: %d, actual1: %d, actual2: %d, actual3: %d, actual5: %d\n",
                expected, actual1, actual2, actual3, actual5);

        Assert.assertEquals(expected, actual1);
        Assert.assertEquals(expected, actual2);
        Assert.assertEquals(expected, actual3);
        Assert.assertEquals(expected, actual4);
        Assert.assertEquals(expected, actual5);
    }


    private static int callCount = 0;

    /**
     * This approach uses a common way of generating subsequences
     * - pick and not pick
     * - and get the max of two
     *
     * - the constraint here is only select the value at idx only when its
     *   value is greater than the previous value, meaning extend the org.learning.dp.LIS
     *
     * Runtime: O(2^n)
     * Space: O(n) - depth of the tree
     *
     *
     * @param arr
     * @param prevValue
     * @param idx
     * @return
     */
    private static int topDownPickOrNot(int[] arr, int prevValue, int idx) {

        if (idx == arr.length) {
            return 0;
        }

        callCount++;

        int maxWith = -1;
        if (arr[idx] > prevValue) {
            // only go in here if current value > prevValue
            // to add 1 to it
            maxWith =  1 + topDownPickOrNot(arr, arr[idx], idx + 1);
        }
        int maxWithout = topDownPickOrNot(arr, prevValue, idx + 1);


        return Math.max(maxWith, maxWithout);
    }


    /**
     *
     * This approach is a translation of the recurrence below using recursion
     *  - org.learning.dp.LIS(i) = 1 + max (org.learning.dp.LIS(j))
     *  - for all j from 0 to i - 1 and arr[j] < arr[i]
     *
     * The intuition is if you are standing at position i and look back at all the
     * number before you, you see many increasing subsequences, therefore you want to find
     * the ones that you can be part of or can extend that subsequence and among those,
     * find the one with maximum length.
     *
     *
     *  org.learning.dp.LIS[i] = 1 + max(org.learning.dp.LIS[j]) for all  0<=j<i and A[j]<A[i]
     *         = 1 if no such element exists where j< i and A[j]<A[i]
     *
     *
     * Only +1 for the max of all the org.learning.dp.LIS(j) for j from 0 to i-1
     *
     * - need to keep track of the max among each of the index
     * - need to keep track of the max among all the previous indexes when at index i
     *
     * What is the runtime O(n) = n^2
     * Space: O(n)
     *
     * @param arr
     * @param idx
     * @return
     */
    private static int topDown(int[] arr, int idx, int[] globalMax) {
        //System.out.println("topDown(" + idx + ")");

        callCount++;

        if (idx == 0) {
            return 1;
        }


        int maxSoFar = 1;
        for (int i = 0; i < idx; i++) {
            int tmp = topDown(arr, i, globalMax);
            if (arr[idx] > arr[i]) {
                maxSoFar = Math.max(maxSoFar, tmp+1);
            }
        }

        if (maxSoFar > globalMax[0]) {
            //System.out.println("******** updating globalMax to: " + maxSoFar);
            globalMax[0] =  maxSoFar;
        }

        return maxSoFar;

    }

    /**
     * This approach is a translation of the recurrence below using recursion
     * and add the memoization
     *
     *  - org.learning.dp.LIS(i) = 1 + max (org.learning.dp.LIS(j))
     *  - for all j from 0 to i - 1 and arr[j] < arr[i]
     *
     * Only +1 for the max of all the org.learning.dp.LIS(j) for j from 0 to i-1
     *
     * - need to keep track of the max among each of the index
     * - need to keep track of the max among all the previous indexes when at index i
     *
     * @param arr
     * @param idx
     * @return
     */
    private static int topDownWithMemoize(int[] arr, int idx, int[] globalMax, int[] cache) {

        if (cache[idx] > 0) {
            return cache[idx];
        }

        callCount++;

        if (idx == 0) {
            cache[idx] = 1;
            return 1;
        } else {
            int maxSoFar = 1;
            for (int i = 0; i < idx; i++) {
                int tmp = topDownWithMemoize(arr, i, globalMax, cache);
                if (arr[idx] > arr[i]) {
                    maxSoFar = Math.max(maxSoFar, tmp + 1);
                }
            }


            if (maxSoFar > globalMax[0]) {
                globalMax[0] = maxSoFar;
            }

            cache[idx] = maxSoFar;
            return maxSoFar;
        }

    }

    /**
     *
     * Imagine there is an array keeps track of the org.learning.dp.LIS of all the elements from 0 to i.
     *      * If we have that information, can we calculate org.learning.dp.LIS(i+1)?
     *      * - starting at i+1, we go back until 0 and extend the org.learning.dp.LIS(i) by 1 if the value
     *      *   at num[i+1] > num[j] for (j from i to 0
     *
     * Build a table of org.learning.dp.LIS(j).
     *
     * Runtime: O(n^2)
     * Space: O(n) - size of the cache
     *
     * @param arr
     * @return
     */
    private static int bottomUpDP(int[] arr) {
        int[] cache = new int[arr.length];
        Arrays.fill(cache, 1);

        int[] subSeqTrail = new int[arr.length];
        Arrays.fill(subSeqTrail, -1);

        // start from 1 because org.learning.dp.LIS(0) = 1;

        for (int idx = 1; idx < arr.length; idx++) {
            // from 0 to idx-1;
            for (int left = 0; left < idx; left++) {
                callCount++;
                if (arr[idx] > arr[left]) {
                    cache[idx] =  Math.max(cache[idx],  1 + cache[left]);
                    subSeqTrail[idx] = left;
                }
            }
            //System.out.println("cache(" + idx + ") is " + Arrays.toString(cache));

        }

      //  System.out.println("cache: " + Arrays.toString(cache));
      //  System.out.println("subSeqTrail: " + Arrays.toString(subSeqTrail));

        int max = -1;
        int maxIdx = -1;
        for (int idx = 0; idx < cache.length; idx++) {
            if (cache[idx] > max) {
                max = cache[idx];
                maxIdx = idx;
            }
        }

        List<Integer> seq = new ArrayList<>();
        collectSeq(arr, subSeqTrail, maxIdx, seq);

        System.out.println("maxIdx: " + maxIdx);
        System.out.println("seq: " + seq);

        return max;
    }

    /**
     * This is used to build the longest increasing subsequence
     * @param arr
     * @param trail
     * @param idx
     * @param collector
     */
    private static void collectSeq(int[] arr, int[] trail, int idx, List<Integer> collector) {
        if (idx == -1) {
            return;
        }

        collectSeq(arr, trail, trail[idx], collector);
        collector.add(arr[idx]);
    }

    private static void printCallCount(String prefix) {
        System.out.printf("\ncallCount - %s, %d\n",
                prefix, callCount);

        callCount = 0;
    }


    /**
     * https://leetcode.com/articles/longest-increasing-subsequence/?page=3
     * https://algorithmsandme.com/longest-increasing-subsequence-in-onlogn/
     *
     * Good one to read
     * https://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/
     *
     * - iterate from left to right of an array of size of nums -  call org.learning.dp.LIS array
     * - build the org.learning.dp.LIS array in increasing order
     *
     * @param nums
     * @return
     */
    private static int dpWithBinarySearch(int[] nums) {
        // Add boundary case, when array size is one

        int[] tailTable = new int[nums.length];
        int len; // always points empty slot

        tailTable[0] = nums[0];
        len = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < tailTable[0])
                // new smallest value
                tailTable[0] = nums[i];

            else if (nums[i] > tailTable[len - 1])
                // A[i] wants to extend largest subsequence
                tailTable[len++] = nums[i];

            else
                // A[i] wants to be current end candidate of an existing
                // subsequence. It will replace ceil value in tailTable
                tailTable[CeilIndex(tailTable, -1, len - 1, nums[i])] = nums[i];
        }

        return len;
    }

    // Binary search (note boundaries in the caller)
    // A[] is ceilIndex in the caller
    private static int CeilIndex(int A[], int l, int r, int key)
    {
        while (r - l > 1) {
            int m = l + (r - l) / 2;
            if (A[m] >= key)
                r = m;
            else
                l = m;
        }

        return r;
    }
}
