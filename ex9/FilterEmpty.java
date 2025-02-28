import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FilterEmpty {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.filterEmpty
    // Ignoring the initialization of arrays, your implementation must have linear work and log(n) span
    public static String[] filterEmpty(String[] arr, int cutoff){
        FilterEmpty.CUTOFF = cutoff;
        // Reminder: the main steps are:
        // 1) do a map on the arr of strings
        int[] binary = new int[arr.length];
        POOL.invoke(new BinaryMapTask(arr, binary, 0, arr.length));
        // 2) do prefix sum on the map result (implementation provided for you in ParallelPrefix.java)
        int[] prefixSum = ParallelPrefix.prefixSum(binary, cutoff);
        // 3) initialize and array whose length matches the last value in the prefix sum result
        int total = prefixSum[prefixSum.length - 1];
        String[] result = new String[total];
        // 4) do a map to populate that new array.
        if (total > 0) {
            POOL.invoke(new PopulateResultTask(arr, binary, prefixSum, result, 0, arr.length));
        }

        return result;
    }

    private static class BinaryMapTask extends RecursiveAction {
        private final String[] arr;
        private final int[] binary;
        private final int start;
        private final int end;

        BinaryMapTask(String[] arr, int[] binary, int start, int end) {
            this.arr = arr;
            this.binary = binary;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= CUTOFF) {
                for (int i = start; i < end; i++) {
                    binary[i] = (arr[i] != null && !arr[i].isEmpty()) ? 1 : 0;
                }
            } else {
                int mid = start + (end - start) / 2;
                BinaryMapTask left = new BinaryMapTask(arr, binary, start, mid);
                BinaryMapTask right = new BinaryMapTask(arr, binary, mid, end);
                invokeAll(left, right);
            }
        }
    }

    private static class PopulateResultTask extends RecursiveAction {
        private final String[] arr;
        private final int[] binary;
        private final int[] prefixSum;
        private final String[] result;
        private final int start;
        private final int end;

        PopulateResultTask(String[] arr, int[] binary, int[] prefixSum, String[] result, int start, int end) {
            this.arr = arr;
            this.binary = binary;
            this.prefixSum = prefixSum;
            this.result = result;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= CUTOFF) {
                for (int i = start; i < end; i++) {
                    if (binary[i] == 1) {
                        result[prefixSum[i] - 1] = arr[i];
                    }
                }
            } else {
                int mid = start + (end - start) / 2;
                PopulateResultTask left = new PopulateResultTask(arr, binary, prefixSum, result, start, mid);
                PopulateResultTask right = new PopulateResultTask(arr, binary, prefixSum, result, mid, end);
                invokeAll(left, right);
            }
        }
    }

}
