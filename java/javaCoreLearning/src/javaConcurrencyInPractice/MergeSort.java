package javaConcurrencyInPractice;

import java.util.Arrays;

public class MergeSort {
    public void sort(int[] nums) throws InterruptedException {
        if (nums == null) {
            return;
        }

        partitionAndMerge(nums, 0, nums.length);
    }

    public void partitionAndMerge(int[] nums, int start, int end) throws InterruptedException {
        if (end - start > 2) {
            int mid = (start + end) / 2;
            partitionAndMerge(nums, start, mid);
            partitionAndMerge(nums, mid, end);
        }

        // 此时能够保证 [start, mid) 和 [mid, end) 是排好序的了，用 mergeArrays
        // 方法来合并
        mergeArrays(nums, start, end);
    }

    /*
    in-place algorithm:
    不包括end;
    int mid = (start + end) / 2;
    区间 [start, mid) 和 [mid, end) 是已排序好的
     */
    public void mergeArrays(int[] nums, int start, int end) {
        if (end - start == 1) {
            return;
        }

        int mid = (start + end) / 2;
        int i = start;
        int j = mid;

        while (i < mid && j < end) {
            if (nums[i] > nums[j]) {
                int temp = nums[j];

                // 把数组的区间 [i, j - 1] 整体向右移动一位
                for (int k = j; k >= i + 1; k--) {
                    nums[k] = nums[k - 1];
                }
                nums[i] = temp;

                mid++;
                j++;
            }
            i++;
        }
    }

    public static void main(String[] args) {
    }
}
