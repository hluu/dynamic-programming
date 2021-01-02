package org.learning.dp;

import org.testng.Assert;

import java.util.*;

/**
 * This is a version of the word break.  This problem asks to return a
 * 2d array containing all the ways that the target can be constructed by
 * concatenating elements of the 'dictionary'.  Each element in the 2d
 * should represent one combination that constructs the 'target'
 *
 *  * This the Java adaptation of the JavaScript implementation of an awesome video from freeCodeCamp.org
 *  * - https://www.youtube.com/watch?v=oBt53YbR9Kk
 */
public class WordConstruct {
    public static void main(String[] args) {
        System.out.println("WordConstruct.main");

        System.out.println("======= testCanConstruct ======");
        testCanConstruct("abcdef", Arrays.asList("ab", "abc", "cd", "def", "abcd"), true);
        testCanConstruct("purple", Arrays.asList("purp", "p", "ur", "le", "purpl"), true);
        testCanConstruct("skateboard", Arrays.asList("bo", "rd", "ate", "t", "ska", "sk", "boar"), false);
        testCanConstruct("enterapotentpot", Arrays.asList("a","p", "ent", "enter", "ot", "o", "t"), true);
        testCanConstruct("abcdef", Arrays.asList("ab", "abc", "cd", "def", "abcd", "ef", "c"), true);
        testCanConstruct("aaaaaaaaaaaaaaaaaaaaaaaaaz", Arrays.asList("a", "aa", "aaa", "aaaa", "aaaaa"), false);
        testCanConstruct("aaaaa", Arrays.asList("a", "aa", "aaa", "aaaa", "aaaaa"), true);

        System.out.println("======= testCountConstruct ======");
        testCountConstruct("abcdef", Arrays.asList("ab", "abc", "cd", "def", "abcd"), 1);
        testCountConstruct("purple", Arrays.asList("purp", "p", "ur", "le", "purpl"), 2);
        testCountConstruct("skateboard", Arrays.asList("bo", "rd", "ate", "t", "ska", "sk", "boar"), 0);
        testCountConstruct("enterapotentpot", Arrays.asList("a","p", "ent", "enter", "ot", "o", "t"), 4);
        testCountConstruct("aaaaaaaaaaaaaaaaaaaaaaaaaz", Arrays.asList("a", "aa", "aaa", "aaaa", "aaaaa"), 0);
        testCountConstruct("aaaaa", Arrays.asList("a", "aa", "aaa", "aaaa", "aaaaa"), 16);

        System.out.println("======= testAllConstruct ======");
        List<List<String>> purpleExpected = new ArrayList<>();
        purpleExpected.add(Arrays.asList("purp","le"));
        purpleExpected.add(Arrays.asList("p","ur", "p", "le"));
        testAllConstruct("purple", Arrays.asList("purp", "p", "ur", "le", "purpl"), purpleExpected);

        testAllConstruct("skateboard", Arrays.asList("bo", "rd", "ate", "t", "ska", "sk", "boar"), new ArrayList<>());

        List<List<String>> abcdefExpected = new ArrayList<>();
        abcdefExpected.add(Arrays.asList("ab","cd","ef"));
        abcdefExpected.add(Arrays.asList("ab","c","def"));
        abcdefExpected.add(Arrays.asList("abc","def"));
        abcdefExpected.add(Arrays.asList("abcd","ef"));

        testAllConstruct("abcdef", Arrays.asList("ab", "abc", "cd", "def", "abcd", "ef", "c"), abcdefExpected);
        //testAllConstruct("aaaaaaaaaaaaaaaaaaaaaaaaaz", Arrays.asList("a", "aa", "aaa", "aaaa", "aaaaa"), new ArrayList<>());
       // testAllConstruct("aaaaa", Arrays.asList("a", "aa", "aaa", "aaaa", "aaaaa"), new ArrayList<>());
    }

    private static void testCanConstruct(String target, List<String> dict, boolean expected) {
        System.out.printf("target: %s, dict: %s\n", target, dict);
        boolean actualTopDown = canConstructTopDown(target, dict, new HashMap<>());
        boolean actualBottomUp = canConstructBottomUp(target, dict);

        System.out.printf("expected: %b, actualTopDown: %b, actualBottomUp: %b\n",
                expected, actualTopDown, actualBottomUp);

        Assert.assertEquals(actualTopDown, expected);
        Assert.assertEquals(actualBottomUp, expected);
        System.out.println();
    }

    private static void testCountConstruct(String target, List<String> dict, int expected) {
        System.out.printf("target: %s, dict: %s\n", target, dict);
        int actualTopDown = countConstructTopDown(target, dict, new HashMap<>());

        int actualBottomUp = countConstructBottomUp(target, dict);

        System.out.printf("expected: %d, actualTopDown: %d, actualBottomUp: %d\n",
                expected, actualTopDown, actualBottomUp);

        Assert.assertEquals(actualTopDown, expected);
        Assert.assertEquals(actualBottomUp, expected);
        System.out.println();
    }


    private static void testAllConstruct(String target, List<String> dict,
                             List<List<String>> expected) {
        System.out.printf("target: %s, dict: %s\n", target, dict);

        long startTime = System.nanoTime();
        List<List<String>> actualTopDown = allConstructTopDown(target, dict, new HashMap<>());
        long endTime = System.nanoTime();

        System.out.printf("expected: %s\n", expected);
        System.out.printf("actualTopDown size: %d, actualTopDown: %s\n", actualTopDown.size(), actualTopDown);
        System.out.printf("time: (%d)\n", endTime-startTime);

        List<List<String>> actualBottomUp = allConstructBottomUp(target, dict);
        System.out.printf("actualBottomUp size: %d, actualBottomUp: %s\n", actualBottomUp.size(), actualBottomUp);

        Assert.assertEquals(expected.size(), actualTopDown.size());
        Assert.assertEquals(expected.size(), actualBottomUp.size());
        System.out.println();
    }


    /**
     * Return the number of ways to construct the target w/ the word list.
     *
     * Base case: when target is empty, then return 1;
     * - explore all the choices at each level
     *
     * Complexity analysis: m = length of target, n = # of words in dict
     * Runtime: O(m * n * m)
     *  - worst case of reducing target by a single character
     *  - for each of those characters, we iterate through n words in the dict
     *  - if the word matches the prefix, we reduce the target
     * Space: O(m * m)
     *  - tree height: m, each level incur m to create a string
     * @param target
     * @param dict
     * @return
     */
    private static int countConstructTopDown(String target, List<String> dict, Map<String, Integer> cache) {
        if (cache.containsKey(target)) {
            return cache.get(target);
        }
        if (target.isEmpty()) {
            return 1;
        }

        int total = 0;
        for (String word : dict) {
            if (target.startsWith(word)) {
                String suffix = target.substring(word.length());
                total += countConstructTopDown(suffix, dict, cache);
            }
        }
        cache.put(target, total);
        return total;
    }

    /**
     * Return the number of ways to construct the target w/ the word list.
     *  "purple" -> [purp, p, ur, le, purpl]
     *                    purp + le
     *                    p + ur + p + le
     *
     * Complexity analysis: m = length of target, n = # of words in dict
     * Runtime: T(m,n) = O(m * n * m)
     *  - first m because we are looping through each character in target
     *  - n because we iterate through each word in the dict each time we visit a character in target
     *  - second m, because we create a substring of maximum size of m each time to compare with word
     *
     *  Space: T(m,n) = O(m)  - boolean table up to size of m
     *
     * @param target
     * @param dict
     * @return
     */
    private static int countConstructBottomUp(String target, List<String> dict) {
        int[] cache = new int[target.length()+1];
        cache[0] = 1;

        for (int i = 0; i <= target.length(); i++) {

            for (String word : dict) {
                // if the word matches the characters starting at index i
                if (i + word.length() <= target.length()) {  // this check ensure substring doesn't go out of bound
                    String prefix = target.substring(i, i + word.length());
                    if (prefix.equals(word)) {
                        cache[i + word.length()] += cache[i];
                    }
                }
            }
        }
        //System.out.println(Arrays.toString(cache));
        return cache[target.length()];

    }

    /**
     * This method determines whether it is possible to construct the target
     * using the given list of words.
     *
     * This approach loops through the dictionary to see if any word matches the prefix
     * of the target, if so, reduce the target by removing that prefix.  Therefore the
     * subproblem will be smaller until target is an empty string.
     *
     * Complexity analysis: m = length of target, n = # of words in dict
     * Runtime: T(m,n) = O(m * n * m)
     *   - m for the depth of the tree
     *   - n for choices at each subproblem
     *   - m for creating a suffix substring of maximum of size m or reduce by one character at a time
     * Space: T(m,n) = O(m * m)
     *   - first m for cache of size m
     *   - second m for creating creating a suffix substring of maximum of size m or reduce by one character at a time
     *
     *
     * @param target
     * @param dict
     * @param cache
     * @return
     */
    private static boolean canConstructTopDown(String target, List<String> dict, Map<String, Boolean> cache) {
        if (cache.containsKey(target)) {
            return cache.get(target);
        }
        if (target.isEmpty()) {
            return true;
        }


        for (String word : dict) {
            if (target.startsWith(word)) {
                String suffix = target.substring(word.length());
                if (canConstructTopDown(suffix, dict, cache)) {
                    cache.put(target, true);
                    return true;
                }
            }
        }
        cache.put(target, false);
        return false;
    }

    /**
     * This is the bottom up of the canConstructTopDown.
     *
     * This approach uses a boolean table to capture whether target can
     * be constructed up to a particular character in the target.
     * - As the algorithm goes through each character (from left to right), it asks
     *   whether there was a word, to enable it to jump to that point, if so then
     *   it will ask whether there is a word to jum from that point to the next point the target,
     *   if so, set that point to true to indicate so.
     *
     * Complexity analysis: m = length of target, n = # of words in dict
     * Runtime: T(m,n) = O(m * n * m)
     *  - first m because we are looping through each character in target
     *  - n because we iterate through each word in the dict each time we visit a character in target
     *  - second m, because we create a substring of maximum size of m each time to compare with word
     *
     * Space: T(m,n) = O(m)  - boolean table up to size of m
     *
     *
     * @param target
     * @param dict
     * @return boolean
     */
    private static boolean canConstructBottomUp(String target, List<String> dict) {
        boolean[] cache = new boolean[target.length()+1];
        // base case when an empty string, then it is possible to construct
        cache[0] = true;  // canConstructBottomUp('', dict)

        for (int i = 0; i <= target.length(); i++) {
            if (cache[i]) {  // continue from only the point we it was possible to get here
                for (String word : dict) {
                    // if the word matches the characters starting at index i
                    if (i + word.length() <= target.length()) { // this check ensures substring doesn't go out of bound
                        String prefix = target.substring(i, i + word.length());
                        if (prefix.equals(word)) {
                            cache[i + word.length()] = true;
                        }
                    }
                }
            }
        }

        //System.out.println(Arrays.toString(cache));
        return cache[target.length()];
    }

    /**
     * Top down implementation with memoization.
     * This approach reduces the target to smaller size by stripping out
     * the prefix that matches one of words in the dictionary.
     *
     * Important way to distinguish the difference between 2 base cases
     * - allConstructTopDown('', [cat,dog]) => [ [] ]  // one result w/ an empty string
     * - allConstructTopDown('birds', [cat,dog]) => [ ]  // no possible construction
     *
     * @param target
     * @param dict
     * @param cache
     * @return
     */
    private static List<List<String>> allConstructTopDown(String target, List<String> dict,
                                                          Map<String, List<List<String>>> cache) {

        if (cache.containsKey(target)) {
            return cache.get(target);
        }

        // base case to return a List<List<String>> w/ an empty string
        if (target.isEmpty()) {
            List<List<String>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }

        // another base case is to return an empty List<List<String>>
        List<List<String>> result = new ArrayList<>();
        for (String word : dict) {
            if (target.startsWith(word)) {
                String suffix = target.substring(word.length());
                List<List<String>> parts = allConstructTopDown(suffix, dict, cache);
                if (!parts.isEmpty()) {
                    // this is the tricky part of copying the partial result from parts into
                    // the newPart
                    for (List<String> part : parts) {
                        List<String> newPart = new ArrayList<>();
                        // recursion bubbles up, we add the word (prefix) first, then suffix
                        newPart.add(word);
                        newPart.addAll(part);
                        result.add(newPart);
                    }
                }
            }
        }
        cache.put(target, result);
        return result;
    }

    /**
     * Bottom up implementation of allConstruct
     *
     * @param target
     * @param dict
     * @return
     */
    private static List<List<String>> allConstructBottomUp(String target, List<String> dict) {
        List<List<String>>[] cache = new List[target.length()+1];
        // initialize each cell with an empty array list (base case when no match)
        for (int i = 0; i < cache.length; i++) {
            cache[i] = new ArrayList<>();
        }
        // initialize the first cell with one empty string (base case with target as empty string)
        cache[0].add(new ArrayList<>());

        for (int i = 0; i <= target.length(); i++) {
            for (String word : dict) {
                // if the word matches the characters starting at index i
                if (i + word.length() <= target.length()) {  // this check ensure substring doesn't go out of bound
                    String prefix = target.substring(i, i + word.length());
                    if (prefix.equals(word)) {
                        // holder is either an empty ArrayList or whatever it was at cache[i + word.length()]
                        List<List<String>> holder = cache[i + word.length()];
                        // now copy all the arrays from cache[i] to cache[i + word.length()]
                        for (List<String> part : cache[i]) {
                            List<String> newPart = new ArrayList<>();
                            // as we go from left to right, therefore add prefix first, and then suffix
                            // prefix is in the part
                            newPart.addAll(part);
                            // suffix is the word
                            newPart.add(word);
                            holder.add(newPart);
                        }
                    }
                }
            }
        }
        /*for (int i = 0; i < cache.length; i++) {
            System.out.println(i + ": " + cache[i]);
        }*/
        return cache[target.length()];
    }
}
