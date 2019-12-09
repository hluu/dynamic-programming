

import org.testng.Assert;

import java.util.Arrays;

/**
 * Given an integer array nums, find the sum of the elements
 * between indices i and j (i ≤ j), inclusive.
 *
 * Example:
 * Given nums = [-2, 0, 3, -5, 2, -1]
 *
 * sumRange(0, 2) -> 1
 * sumRange(2, 5) -> -1
 * sumRange(0, 5) -> -3
 */
public class NumArray {

    public static void main(String[] args) {
        System.out.println(NumArray.class.getName());

        test(new int[] {}, 0, 2, 1);
        test(new int[] {-2, 0, 3, -5, 2, -1}, 0, 2, 1);
        test(new int[] {-2, 0, 3, -5, 2, -1}, 2, 5, -1);
        test(new int[] {-2, 0, 3, -5, 2, -1}, 0, 5, -3);
        test(new int[] {-2, 0, 3, -5, 2, -1}, 3, 4, -3);
        test(new int[] {-2, 0, 3, -5, 2, -1}, 2, 4, 0);
    }

    private static void test(int[] nums, int i, int j, int expected) {
        System.out.printf("\n===> test: nums: %s\n", Arrays.toString(nums));
        NumArray numArray = new NumArray(nums);

        int actual = numArray.sumRange(i,j);

        System.out.printf("expected: %d, actual: %d\n",
                expected, actual);

        Assert.assertEquals(expected, actual);
    }


    private int[] partialSum;
    public NumArray(int[] nums) {
        partialSum = new int[nums.length];

        partialSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            partialSum[i] = nums[i] + partialSum[i-1];
        }
    }

    public int sumRange(int i, int j) {
        int upToJ = partialSum[j];
        int upToI = (i > 0) ? partialSum[i-1] : 0;

        return upToJ - upToI;
    }
}
