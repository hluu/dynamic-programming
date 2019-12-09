/**
 * Given a string, your task is to count how many palindromic substrings in this string.
 *
 * The substrings with different start indexes or end indexes are counted as
 * different substrings even they consist of same characters.
 *
 * Example 1:
 *
 * Input: "abc"
 * Output: 3
 * Explanation: Three palindromic strings: "a", "b", "c".
 *
 *
 * Example 2:
 *
 * Input: "aaa"
 * Output: 6
 * Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
 */
public class PalindromicSubStrings {
    public static void main(String[] args) {
        System.out.println(PalindromicSubStrings.class.getName());

        test("a", 1);
        test("aa", 3);
        test("aaa", 6);
        test("aabaa", 9);
        test("aaaaa", 15);
        test("abc", 3);
    }

    private static void test(String str, int expected) {
        System.out.println("\n======> " + str + " <=========");

        int actual = bruteForce(str);
        int actual2 = bruteForce2(str);

        System.out.printf("expected: %d, actual: %d, actual: %d\n",
                expected, actual, actual2);
    }

    private static int bruteForce(String str) {
        int count = 0;

        for (int i = 0; i < str.length(); i++) {

            int left = i; int right = i;
            while (left >= 0 && right < str.length() &&
                    (str.charAt(left) == str.charAt(right))) {

                count++;

                left--;
                right++;
            }

            left = i;  right = i+1;
            while (left >= 0 && right < str.length() &&
                    (str.charAt(left) == str.charAt(right))) {

                count++;

                left--; right++;
            }

        }

        return count;
    }

    public static int bruteForce2(String str) {
        if(str == null || str.length() < 1) return 0;
        int count = 0;
        for(int i=0;i<str.length();i++){
            count += countPalindromes(str, i, i); //Count even sized
            count += countPalindromes(str, i, i+1); //Count odd sized
        }
        return count;
    }

    private static int countPalindromes(String str, int s, int e){
        int count = 0;
        while(s>=0 && e<str.length() && str.charAt(s) == str.charAt(e)){
            s--;
            e++;
            count++;
        }
        return count;
    }


}
