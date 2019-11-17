package javaConcurrencyInPractice;

public class ParallelMergeSort extends MergeSort {
    @Override
    public void partitionAndMerge(int[] nums, int start, int end) throws InterruptedException {
        if (end - start > 2) {
            int mid = (start + end) / 2;

            // 如果 end - start >= 500 就用两个线程分别进行处理
            if (end -start >= 500) {
                Thread t1 = new Thread(() -> {
                    try {
                        partitionAndMerge(nums, start, mid);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                Thread t2 = new Thread(() -> {
                    try {
                        partitionAndMerge(nums, mid, end);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                t1.start();
                t2.start();

                t1.join();
                t2.join();

            } else {
                partitionAndMerge(nums, start, mid);
                partitionAndMerge(nums, mid, end);
            }
        }

        // 此时能够保证 [start, mid) 和 [mid, end) 是排好序的了，用 mergeArrays
        // 方法来合并
        mergeArrays(nums, start, end);
    }
}
