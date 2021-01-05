package org.learning.dp;

import org.testng.Assert;

import java.util.*;

/**
 * This problem is similar to the coin change problem, however it has 3 variations
 * 1) canSum(targetSum, inputs)
 * 2) howSum(targetSum, inputs)
 * 3) bestSum(targetSum, inputs)
 *
 * This class contains both top and bottom up implementations of the
 * above variations.
 *
 * This the Java adaptation of the JavaScript implementation of a great video from freeCodeCamp.org
 * - https://www.youtube.com/watch?v=oBt53YbR9Kk&t=13073s
 *
 */
public class SumConstruct {
    public static void main(String[] args) {
        System.out.println("SumConstruct.main");

        testCanSum(7, new int[] {2,3}, true);
        testCanSum(7, new int[] {5,3,4,7}, true);
        testCanSum(7, new int[] {2,4}, false);
        testCanSum(8, new int[] {2,3,5}, true);
        testCanSum(300, new int[] {7,14}, false);
        testCanSum(100, new int[] {1,2,5,25}, true);

        System.out.println(" ===================");
        testCountSum(7, new int[] {2,3}, 3);
        testCountSum(7, new int[] {5,3,4,7}, 3);
        testCountSum(7, new int[] {2,4}, 0);
        testCountSum(8, new int[] {2,3,5}, 6);

        System.out.println(" ===================");
        testHowSum(7, new int[] {2,3}, Arrays.asList(3,2,2));
        testHowSum(7, new int[] {5,3,4,7}, Arrays.asList(4,3));
        testHowSum(7, new int[] {2,4}, null);
        testHowSum(8, new int[] {2,3,5}, Arrays.asList(2,2,2,2));
        testHowSum(300, new int[] {7,14}, null);
        testHowSum(100, new int[] {1,2,5,25}, Arrays.asList(25,25,25,25));

        System.out.println(" ===================");
        testBestSum(7, new int[] {2,3}, Arrays.asList(3,2,2));
        testBestSum(7, new int[] {5,3,4,7}, Arrays.asList(7));
        testBestSum(7, new int[] {2,4}, null);
        testBestSum(8, new int[] {2,3,5}, Arrays.asList(3,5));
        testBestSum(20, new int[] {1,2,5,10}, Arrays.asList(10, 10));
        testBestSum(50, new int[] {25,1,2,5,25, 30, 36}, Arrays.asList(25,25));
        //testBestSum(100, new int[] {1,2,5,25, 30, 36}, Arrays.asList(25,25,25,25));
    }

    private static void testCountSum(int targetSum, int[] inputs, int expected) {
        System.out.println("========== testCountSum ==========");
        System.out.printf("targetSum: %d, input: %s\n", targetSum, Arrays.toString(inputs));

        int actualTopDown = countSumTopDown(targetSum, inputs, new HashMap<>());
        System.out.printf("expected: %s, actualTopDown: %s\n", expected, actualTopDown);

        Assert.assertEquals(expected, actualTopDown);
        //List<Integer> actualBottomUp = bestSumBottomUp(targetSum, inputs);
        //System.out.printf("expected: %s, actualBottomUp: %s\n", expected, actualBottomUp);

    }

    private static void testBestSum(int targetSum, int[] inputs, List<Integer> expected) {
        System.out.println("========== testBestSum ==========");
        System.out.printf("targetSum: %d, input: %s\n", targetSum, Arrays.toString(inputs));

        List<Integer> actualTopDown = bestSumTopDown(targetSum, inputs, new HashMap<>());
        System.out.printf("expected: %s, actualTopDown: %s\n", expected, actualTopDown);

        List<Integer> actualBottomUp = bestSumBottomUp(targetSum, inputs);
        System.out.printf("expected: %s, actualBottomUp: %s\n", expected, actualBottomUp);

    }

    private static void testHowSum(int targetSum, int[] inputs, List<Integer> expected) {
        System.out.println("========== testHowSum ==========");
        System.out.printf("targetSum: %d, input: %s\n", targetSum, Arrays.toString(inputs));

        List<Integer> actualTopDown = howSumToDown(targetSum, inputs, new HashMap<>());

        List<Integer> actualBottomUp = howSumBottomUp(targetSum, inputs);

        System.out.printf("expected: %s, actualTopDown: %s\n", expected, actualTopDown);
        System.out.printf("expected: %s, actualBottomUp: %s\n", expected,
                actualBottomUp);
    }

    private static void testCanSum(int targetSum, int[] inputs, boolean expected) {
        System.out.println("========== testCanSum ==========");
        System.out.printf("targetSum: %d, input: %s\n", targetSum, Arrays.toString(inputs));

        boolean actualTopDown = canSumTopDown(targetSum, inputs, new HashMap<Integer, Boolean>());
        boolean actualBottomUp = canSumBottomUp(targetSum, inputs);
        System.out.printf("expected: %b, actualTopDown: %b, actualBottomUp: %b\n ",
                expected, actualTopDown, actualBottomUp);
        System.out.println();

        Assert.assertEquals(expected, actualTopDown);
        Assert.assertEquals(expected, actualBottomUp);
    }

    /**
     * Top down with memoization approach to explore targetSum sub-problems
     * - the target sum is getting smaller as it is be reduced from each of the given integer in the inputs
     * - base case is when we can deduce it down to 0 (using a combination of the inputs w/ repetition)
     * - runtime: m = targetSum, n = length of inputs
     *   - T(m,n) = O(n^m)
     *   - S(m,n) = O(m)
     *
     * @param targetSum
     * @param inputs
     * @param cache
     * @return
     */
    private static boolean canSumTopDown(int targetSum, int[] inputs, Map<Integer, Boolean> cache) {
        if (cache.containsKey(targetSum)) {
            return cache.get(targetSum);
        }

        if (targetSum == 0) {
            return true;
        }

        if (targetSum < 0) {
            return false;
        }

        for (int val : inputs) {
            int remaining = targetSum-val;
            if (canSumTopDown(remaining, inputs, cache)) {
                cache.put(remaining, true);
                return true;
            }
        }
        cache.put(targetSum, false);
        return false;
    }

    private static int countSumTopDown(int targetSum, int[] inputs, Map<Integer, Integer> cache) {
        if (cache.containsKey(targetSum)) {
            return cache.get(targetSum);
        }
        if (targetSum == 0) {
            return 1;
        }
        if (targetSum < 0) {
            return 0;
        }
        int totalSum = 0;
        for (int val : inputs) {
            int remaining = targetSum-val;
            totalSum += countSumTopDown(remaining, inputs, cache);
        }
        cache.put(targetSum, totalSum);
        return totalSum;
    }

    /**
     * Bottom up approach by build a table of targetSum from 1..targetSum
     *
     *
     *
     * @param targetSum
     * @param inputs
     * @return
     */
    private static boolean canSumBottomUp(int targetSum, int[] inputs) {
        // create a cache with targetSum+1 because at the end
        // we will return cache[targetSum]
        boolean[] cache = new boolean[targetSum+1];

        cache[0] = true;

        // two different ways of thinking
        // for each smaller target sum, iterate through the inputs
        for (int i = 1; i <= targetSum; i++) {
            for (int val : inputs) {
                if ((i >= val) && cache[i - val]) {
                    cache[i] = cache[i - val];
                }
            }
        }
        /*
        for (int val : inputs) {
            // start with i = val
            for (int i = val; i <= targetSum; i++) {
                if (cache[i - val]) {
                    cache[i] = cache[i - val];
                }
            }
        }*/
        //System.out.println("cache: " + Arrays.toString(cache));
        return cache[targetSum];
    }

    /**
     * Return an array containing any combination of elements
     * that add up to exactly the targetSum.  Return null if no
     * such combination.
     *
     * @param targetSum
     * @param inputs
     * @param cache
     * @return
     */
    private static List<Integer> howSumToDown(int targetSum, int[] inputs, Map<Integer, List<Integer>> cache) {
        if (cache.containsKey(targetSum)) {
            return cache.get(targetSum);
        }
        if (targetSum == 0) {  // base case of 1 way
            return new ArrayList<>();
        }

        if (targetSum < 0) {  // base case of 0 way
            return null;
        }

        List<Integer> list = null;
        for (int val : inputs) {
            list = howSumToDown(targetSum - val, inputs, cache);
            if (list != null) {
                list.add(val);
                break;
            }
        }
        cache.put(targetSum, list);
        return list;
    }

    /**
     * Return an array containing any combination of elements
     * that add up to exactly the targetSum.  Return null if no
     * such combination.
     *
     * @param targetSum
     * @param inputs
     * @return List<Integer>
     */
    private static List<Integer> howSumBottomUp(int targetSum, int[] inputs) {
        List<Integer>[] cache = new List[targetSum+1];
        cache[0] = new ArrayList<>();

        for (int i = 1; i <= targetSum; i++) {
            for (int val : inputs) {
                if ((i >= val) && cache[i - val] != null) {
                    List<Integer> newList = new ArrayList<>(cache[i - val]);
                    newList.add(val);
                    cache[i] = newList;
                    continue;
                }
            }
        }

        return cache[targetSum];
    }

    private static List<Integer> bestSumTopDown(int targetSum, int[] inputs,
                                                Map<Integer, List<Integer>> cache) {
        if (cache.containsKey(targetSum)) {
            return cache.get(targetSum);
        }
        if (targetSum == 0) {  // base case of 1 way
            return new ArrayList<>();
        }

        if (targetSum < 0) {  // base case of 0 way
            return null;
        }

        List<Integer> list = null;
        for (int val : inputs) {
            List<Integer> tmpList = bestSumTopDown(targetSum - val, inputs, cache);
            if (tmpList != null) {
                if (list == null || tmpList.size() + 1 < list.size()) {
                    // should be very careful to add val only to the new list
                    list = new ArrayList<>(tmpList);
                    list.add(val);
                }
            }
        }
        cache.put(targetSum, list);
        return list;
    }

    /**
     * Bottom up approach to best sum, which is a slight variation of t he howSumBottomUp, where
     * we pick the shortest list for each of the targetSum from 1..targetSum
     *
     * @param targetSum
     * @param inputs
     * @return
     */
    private static List<Integer> bestSumBottomUp(int targetSum, int[] inputs) {
        List<Integer>[] cache = new List[targetSum+1];
        cache[0] = new ArrayList<>();

        for (int i = 1; i <= targetSum; i++) {
            List<Integer> list = null; // list == null is when first time through the loop
            for (int val : inputs) {
                if ((i >= val) && cache[i - val] != null) {
                    List<Integer> prevList = cache[i-val];
                    // only update list of prevList size is smaller than current list (when not null)
                    if (list == null || (prevList.size() + 1) < list.size()) {
                        List<Integer> newList = new ArrayList<>(prevList);
                        newList.add(val);
                        list = newList;
                    }
                }
            }
            cache[i] = list;
        }

        /*
        System.out.println("bestSumBottomUp cache");
        int ts = 0;
        for (List<Integer> list : cache) {
            System.out.println(ts++ + ": " + list);
        }*/
        return cache[targetSum];
    }
}
