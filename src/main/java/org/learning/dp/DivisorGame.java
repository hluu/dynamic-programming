package org.learning.dp;

/**
 * Alice and Bob take turns playing a game, with Alice starting first.
 *
 * Initially, there is a number N on the chalkboard.  On each player's turn,
 * that player makes a move consisting of:
 *
 * Choosing any x with 0 < x < N and N % x == 0.
 * Replacing the number N on the chalkboard with N - x.
 * Also, if a player cannot make a move, they lose the game.
 *
 * Return True if and only if Alice wins the game, assuming both players play optimally.
 *
 *
 * Example 1:
 *
 * Input: 2
 * Output: true
 * Explanation: Alice chooses 1, and Bob has no more moves.
 * Example 2:
 *
 * Input: 3
 * Output: false
 * Explanation: Alice chooses 1, Bob chooses 1, and Alice has no more moves.
 */
public class DivisorGame {

    public static void main(String[] args) {
        test(2, true);
        test(3, false);
        test(4, true);
    }

    private static void test(int n, boolean expected) {
        System.out.printf("\ntest: n = %d\n",n);

        boolean actual = divisorGame(n);
        System.out.printf("expected: %b, actual: %b\n", expected, actual);
    }

    public static boolean divisorGame(int N) {
        return helper(N, true);
    }

    /**
     * Explanation:
     *   if you carefully obeserve you lose when N=1 and win when N=2;
     *   so from N=3 just apply induction whenever you have odd value
     *   it can only have odd divisors so when you subtract odd divisor
     *   from N then N will become even for your opponent so he wins and
     *   when N is even you are always able to create a situation of N is
     *   odd for your opponent by just subtracting 1 as 1 ia a divisor of
     *   all positive numbers. So the bottom line is when N is odd you will
     *   lose and when n is even you make N odd by subtracting 1 leaving your
     *   opponent with odd value of N
     *
     * @param n
     * @param aliceTurn
     * @return
     */
    private static boolean helper(int n, boolean aliceTurn) {
        return n%2==0;

        /*if (aliceTurn) {
            if (n <= 1) {
                // alice loses
                return false;
            }

            for (int x = 1; x < n; x++) {
                if ((n % x) == 0) {
                    if (!helper(n-x, false)) {
                        return true;
                    }
                }
            }
            return false;

        } else { // bob's turn
            if (n <= 1) {
                // bob loses, alices win
                return false;
            }

            for (int x = 1; x < n; x++) {
                if ((n % x) == 0) {
                    if (!helper(n - x, true)) {
                        return true;
                    }
                }
            }

            return false;
        }*/

    }
}
