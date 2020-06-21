package org.learning.dp;

import org.testng.Assert;

import java.util.Arrays;

/**
 * Football scoring
 * 2 points for safety
 * 3 points for field goal
 * 7 points for touch down
 *
 * Given a final score of a game, compute # of combinations of 2,3,7 point plays
 * could make up this score.
 *
 * Find the # of combinations of plays that result in the aggregate score of s.
 * Compute different # of distinct sequence of individual plays that result in a score
 *
 * Example: 12 points
 *
 * 1) 6 safeties
 * 2) 2 safeties and 2 field goals
 * 3) 1 safety, 1 field goal, 1 touch down
 * 4) 4 field goals
 *
 * Approach:
 *   Count # of combinations in which result the score in 0 score, 1 score
 *
 *   [0,1,2,3,4,5,6,7,8,9,10,11,12]
 *   [0,0,1,
 *   What if score is 2?
 *   What if score is 3?
 *
 * @author hluu
 *
 */
public class CountScoreCombination {

    public static void main(String[] args) {
        int[] playScores = {2,3,7};

        test(playScores, 12, 4);
        test(playScores, 10, 3);
    }

    private static void test(int[] playScores, int score, int expected) {
        System.out.printf("playScores: %s, score: %d\n", Arrays.toString(playScores), score);

        int actual = countScoreCombo(playScores, score);
        int actual2 = countScoreRecursion(playScores, 0, score);

        System.out.printf("expected: %d, actual: %d, actual2: %d\n", expected, actual, actual2);

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, actual2);
    }

    /**
     * This version uses the bottom up approach and using a 1-d array to keep track of the combinations
     *
     * @param playScores
     * @param score
     * @return
     */
    private static int countScoreCombo(int[] playScores, int score) {
        int combo[] = new int[score+1];
        combo[0] = 1;  // there is 1 combination for 0 score
        for (int i = 0; i < playScores.length; i++) {
            int playScore = playScores[i];
            System.out.println("PlayScore: " + playScore);
            for (int j = playScore; j <= score; j++) {
                combo[j] += combo[j-playScore];
                System.out.println("j: " + j + "\t" + Arrays.toString(combo));
            }
        }
        return combo[score];
    }

    /**
     * This implementation uses recursion.
     * - The base case is when score reach 0, then that is combination
     * - For each level of recursion we don't want the for loop to start beginning,
     *   but we want to exhaust the score the current position to the remaining scores
     *
     *
     * @param playScores
     * @param score
     * @return
     */
    private static int countScoreRecursion(int[] playScores, int idx,  int score) {
        System.out.printf("idx: %d, score: %d\n", idx, score);
        if (score == 0) {
            return 1;
        }
        int count = 0;
        for (int i = idx; i < playScores.length; i++) {
            int playScore = playScores[i];
            if (score >= playScore) {
                count += countScoreRecursion(playScores, i, score - playScore);
            }
        }

        return count;
    }

}