package org.learning.dp;

/**
 * Given a string s and a string t, check if s is subsequence of t.
 *
 * You may assume that there is only lower case English letters in both s and t.
 * t is potentially a very long (length ~= 500,000) string, and s is a short string (<=100).
 *
 * A subsequence of a string is a new string which is formed from the original string
 * by deleting some (can be none) of the characters without disturbing the relative
 * positions of the remaining characters. (ie, "ace" is a subsequence of "abcde"
 * while "aec" is not).
 *
 * Example 1:
 * s = "abc", t = "ahbgdc"
 *
 * Return true.
 *
 * Example 2:
 * s = "axc", t = "ahbgdc"
 *
 * Return false.
 *
 * Follow up:
 * If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and
 * you want to check one by one to see if T has its subsequence. In this scenario,
 * how would you change your code?
 *
 * Approaches:
 *
 * Brute force
 * - generate all the subsequences and look for the ones with same length and
 *   compare the string
 */
public class IsSubsequence {

    public static void main(String[] args) {
        System.out.println(IsSubsequence.class.getName());

        test("ahbgdc", "abc", true);
        test("ahbgdc", "axc", false);

        //test("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxtxxxxxxxxxxxxxxxxxxxxwxxxxxxxxxxxxxxxxxxxxxxxxxn", "twn", true);
    }

    private static void test(String t, String s, boolean expected) {
        System.out.printf("\n=====> test t: '%s', s: '%s'\n", t,s);

        boolean actual = bruteFoce(t,s);
        //boolean actual = true;
        boolean actual2 = usingLoops(t,s);

        System.out.printf("expected: %b, actual: %b, actual2: %b\n", expected, actual,
                actual2);

    }

    private static boolean usingLoops(String t, String s) {
        int index = 0;

        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            while (index < t.length()) {
                if (t.charAt(index) == c) {
                    index++;
                    count++;
                    break;
                }
                index++;
            }

            if (count < s.length() && index >= t.length()) {
                return false;
            }
        }

        return true;
    }

    private static boolean bruteFoce(String t, String subSequence) {

        return recursionApproach(t, subSequence, "");
    }

    /**
     * Runtime O(2^n)
     *
     * @param remaining
     * @param subSequence
     * @param soFar
     * @return
     */
    private static boolean recursionApproach(String remaining, String subSequence, String soFar) {
        if (remaining.isEmpty()) {
            if (subSequence.equals(soFar)) {
                return true;
            } else {
                return false;
            }
        }

        // include first character
        boolean foundIt = recursionApproach(remaining.substring(1),
                subSequence,soFar + remaining.charAt(0) );

        if (foundIt) {
            return  foundIt;
        }
        // don't include first character

        return recursionApproach(remaining.substring(1), subSequence, soFar);
    }
}
