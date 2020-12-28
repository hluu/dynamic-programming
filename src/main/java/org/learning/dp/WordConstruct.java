package org.learning.dp;

import java.util.*;

/**
 * This is a version of the word break.  This problem asks to return a
 * 2d array containing all the ways that the target can be constructed by
 * concatenating elements of the 'dictionary'.  Each element in the 2d
 * should represent one combination that constructs the 'target'
 */
public class WordConstruct {
    public static void main(String[] args) {
        System.out.println("AllConstruct.main");

        test("purple", Arrays.asList("purp", "p", "ur", "le", "purpl"), new ArrayList<>());
        test("skateboard", Arrays.asList("bo", "rd", "ate", "t", "ska", "sk", "boar"), new ArrayList<>());
        test("abcdef", Arrays.asList("ab", "abc", "cd", "def", "abcd", "ef", "c"), new ArrayList<>());
        test("aaaaaaaaaaaaaaaaaaaaaaaaaz", Arrays.asList("a", "aa", "aaa", "aaaa", "aaaaa"), new ArrayList<>());
        test("aaaaa", Arrays.asList("a", "aa", "aaa", "aaaa", "aaaaa"), new ArrayList<>());
    }

    private static void test(String target, List<String> dict,
                             List<List<String>> expected) {

        System.out.printf("target: %s\n", target);

        System.out.println("----- canConstruct: " + canConstruct(target, dict, new HashMap<>()));

        System.out.println("----- countConstruct --------");
        int actualCount = countConstruct(target, dict, new HashMap<>());
        System.out.printf("actualCount: %d\n", actualCount);

        long startTime = System.nanoTime();
        List<List<String>> actual = allConstruct(target, dict, new HashMap<>());
        long endTime = System.nanoTime();

        System.out.printf("expected: %s\n", expected);
        System.out.printf("actual size: %d\n", actual.size());
        System.out.printf("actual: %s\n", actual);
        System.out.printf("time: (%d)\n", endTime-startTime);
        System.out.println();
    }

    /**
     * Top down implementation with memoization.
     * This approach reduces the target to smaller size by stripping out
     * the prefix that matches one of words in the dictionary.
     *
     *
     * @param target
     * @param dict
     * @param cache
     * @return
     */
    private static List<List<String>> allConstruct(String target, List<String> dict,
                                                   Map<String, List<List<String>>> cache) {

        if (cache.containsKey(target)) {
            return cache.get(target);
        }

        if (target.isEmpty()) {
            List<List<String>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }

        List<List<String>> result = new ArrayList<>();
        for (String word : dict) {
            if (target.startsWith(word)) {
                String suffix = target.substring(word.length());
                List<List<String>> parts = allConstruct(suffix, dict, cache);
                if (!parts.isEmpty()) {
                    for (List<String> part : parts) {
                        part.add(0, word);
                        result.add(part);
                    }
                }
            }
        }
        cache.put(target, result);
        return result;
    }

    /**
     * Return the number of ways to construct the target w/ the word list.
     *
     * Base case: when target is empty, then return 1;
     * - explore all the choices at each level
     *
     * @param target
     * @param dict
     * @param cache
     * @return
     */
    private static int countConstruct(String target, List<String> dict, Map<String, Integer> cache) {
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
                total += countConstruct(suffix, dict, cache);
            }
        }
        cache.put(target, total);
        return total;
    }

    /**
     * This method determines whether it is possible to construct the target
     * using the given list of words.
     *
     *
     * @param target
     * @param dict
     * @param cache
     * @return
     */
    private static boolean canConstruct(String target, List<String> dict, Map<String, Boolean> cache) {
        if (cache.containsKey(target)) {
            return cache.get(target);
        }
        if (target.isEmpty()) {
            return true;
        }


        for (String word : dict) {
            if (target.startsWith(word)) {
                String suffix = target.substring(word.length());
                if (canConstruct(suffix, dict, cache)) {
                    cache.put(target, true);
                    return true;
                }
            }
        }
        cache.put(target, false);
        return false;
    }
}
