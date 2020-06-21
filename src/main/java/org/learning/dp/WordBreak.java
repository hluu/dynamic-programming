package org.learning.dp;

import java.util.Set;

/**
 * https://leetcode.com/problems/word-break/
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words.
 *
 * Note:
 *
 * The same word in the dictionary may be reused multiple times in the segmentation.
 * You may assume the dictionary does not contain duplicate words.
 * Example 1:
 *
 * Input: s = "leetcode", wordDict = ["leet", "code"]
 * Output: true
 * Explanation: Return true because "leetcode" can be segmented as "leet code".
 * Example 2:
 *
 * Input: s = "applepenapple", wordDict = ["apple", "pen"]
 * Output: true
 * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 *              Note that you are allowed to reuse a dictionary word.
 * Example 3:
 *
 * Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * Output: false
 */
public class WordBreak {
    public static void main(String[] args) {

    }

    /**
     * Bottom up implementation using 1-D array
     *
     * @param input
     * @param dict
     * @return
     */
    private static boolean wordBreakBottomUp(String input, Set<String> dict) {
        boolean[] markerArr = new boolean[input.length()+1];
        markerArr[0] = true;

        // right <= input.length() because the right part is exclusive
        for (int right = 1; right <= input.length(); right++) {
            for (int left = 0; left < right; left++) {
                // don't bother with marker with false value
                if (markerArr[left]) {
                    // callCount++;

                    String subString = input.substring(left, right);
                    if (dict.contains(subString)) {
                        // advance the marker to right position
                        markerArr[right] = true;
                        break;
                    }
                }
            }
        }

        //System.out.println(Arrays.toString(markerArr));
        return markerArr[input.length()];
    }

    /**
     * Inefficient approach of iterative through the words in the dictionary to
     * compare w/ the prefix of the string
     *
     * @param input
     * @param dict
     * @return
     */
    private static boolean wordBreakDF(String input, Set<String> dict) {
        boolean[] markerArr = new boolean[input.length()+1];
        markerArr[0] = true;

        for (int i = 0; i < input.length(); i++) {
            if (!markerArr[i]) {
                // already checked
                continue;
            }

            for (String word : dict) {


                int end = i + word.length();

                if (end > input.length()) {
                    continue;
                }

                if (input.substring(i, end).equals(word)) {
                    markerArr[end] = true;
                }
            }
        }

        return markerArr[input.length()];

    }

    /**
     * Town down using recursion w/o memoization
     *
     * @param input
     * @param dict
     * @return
     */
    private static boolean wordBreakBF(String input, Set<String> dict) {
        if (input.isEmpty()) {
            return true;
        }

        for (String word : dict) {
            if (input.length() >= word.length()) {
                if (input.startsWith(word)) {
                    // chop off the prefix
                    String newInput = input.substring(word.length());

                    // what if there is no match
                    if (wordBreakBF(newInput, dict)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }
}
