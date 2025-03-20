import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * parity returns true if there are even number of even numbers and false otherwise.
 * For example, if arr is [1, 7, 4, 3, 6], then parity(arr) == true.
 * But, if arr is [6, 5, 4, 3, 2, 1], parity(arr) == false.
 * Your code must have O(n) work, O(lg(n)) span, where n is the length of arr
 */
public class Parity {
    private static final ForkJoinPool POOL = new ForkJoinPool();
    private static int CUTOFF;

    public static boolean parallelParityTask(int[] arr, int cutoff) {
        // TODO: Invoke the ForkJoinPool to call the LessThan7Task
        Parity.CUTOFF = cutoff;
        return POOL.invoke(new ParityTask(arr, 0, arr.length));
    }

    public static boolean sequentialParityTask(int[] arr, int lo, int hi) {
        // TODO: Step 1. Base Case (i.e. Sequential Case)
        int count = 0;
        for (int i = lo; i < hi; i++) {
            if (arr[i] % 2 == 0) {
                count++;
            }
        }
        return count % 2 == 0;
    }

    private static class ParityTask extends RecursiveTask<Boolean> {
        private final int[] arr;
        private final int lo, hi;

        public ParityTask(int[] arr, int lo, int hi) {
            this.arr = arr;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        protected Boolean compute() {
            if (hi - lo <= CUTOFF) {
                // TODO: Step 1. Base Case (i.e. Sequential Case)
                return sequentialParityTask(arr, lo, hi);
            } else {
                // TODO: Step 2. Recursive Case (i.e. Parallel/Forking case)
                int mid = lo + (hi - lo) / 2; // The same as (lo + hi) / 2

                ParityTask left = new ParityTask(arr, lo, mid);
                ParityTask right = new ParityTask(arr, mid, hi);

                left.fork();                           // 1. Make sure to fork() the left task first
                boolean rightResult = right.compute(); // 2. Then compute() the right task
                boolean leftResult = left.join();      // 3. Then wait for the leftResult by calling join()
                //    on the left task before combining results

                // TODO: Step 3. Combining the left and right tasks' results
                return (leftResult && rightResult) || (!leftResult && !rightResult);
            }
        }
    }
}