import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * secondSmallest returns the second-smallest integer in arr.
 * Assume arr contains only unique elements and has at least 2 integers.
 * For example, if arr is [1, 7, 4, 3, 6], then secondSmallest(arr) == 3.
 * But, if arr is [6, 1, 4, 3, 5, 2], secondSmallest(arr) == 2.
 * Your code must have O(n) work, O(lg(n)) span, where n is the length of arr
 */
public class SecondSmallest {
    private static final ForkJoinPool POOL = new ForkJoinPool();
    private static int CUTOFF;

    public static int parallelSecondSmallest(int[] arr, int cutoff) {
        // TODO: Invoke the ForkJoinPool to call the LessThan7Task
        SecondSmallest.CUTOFF = cutoff;
        TwoSmallest result = POOL.invoke(new SecondSmallestTask(arr, 0, arr.length));
        return result.secondSmallest;
    }

    public static TwoSmallest sequentialSecondSmallest(int[] arr, int lo, int hi) {
        // TODO: Step 1. Base Case (i.e. Sequential Case)
        int smallest = Integer.MAX_VALUE;
        int secondSmallest = Integer.MAX_VALUE;

        for (int i = lo; i < hi; i++) {
            int currElement = arr[i];
            if (currElement < smallest) { // If current element is smaller than the smallest, replace both
                secondSmallest = smallest;
                smallest = currElement;
            } else if (currElement < secondSmallest) { // If current element is smaller than the second smallest, replace
                secondSmallest = currElement;
            }
        }

        TwoSmallest result = new TwoSmallest();
        result.smallest = smallest;
        result.secondSmallest = secondSmallest;
        return result;
    }

    private static class SecondSmallestTask extends RecursiveTask<TwoSmallest> {
        private final int[] arr;
        private final int lo, hi;

        public SecondSmallestTask(int[] arr, int lo, int hi) {
            this.arr = arr;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        protected TwoSmallest compute() {
            if (hi - lo <= SecondSmallest.CUTOFF) {
                // TODO: Step 1. Base Case (i.e. Sequential Case)
                return sequentialSecondSmallest(arr, lo, hi);
            } else {
                // TODO: Step 2. Recursive Case (i.e. Parallel/Forking case)
                int mid = lo + (hi - lo) / 2; // The same as (lo + hi) / 2

                SecondSmallestTask left = new SecondSmallestTask(arr, lo, mid);
                SecondSmallestTask right = new SecondSmallestTask(arr, mid, hi);

                left.fork();                               // 1. Make sure to fork() the left task first
                TwoSmallest rightResult = right.compute(); // 2. Then compute() the right task
                TwoSmallest leftResult = left.join();      // 3. Then wait for the leftResult by calling join()
                                                           //    on the left task before combining results

                // TODO: Step 3. Combining the left and right tasks' results
                int[] values = new int[]{leftResult.smallest, rightResult.smallest, leftResult.secondSmallest, rightResult.secondSmallest};
                return sequentialSecondSmallest(values, 0, values.length);
            }
        }
    }

    public static class TwoSmallest {
        public int smallest;
        public int secondSmallest;

        public TwoSmallest() {
            smallest = secondSmallest = Integer.MAX_VALUE;
        }
    }
}