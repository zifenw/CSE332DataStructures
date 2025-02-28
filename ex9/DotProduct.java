import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class DotProduct {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.dotProduct
    // Your implementation must have linear work and log(n) span
    public static int dotProduct(int[] a, int[]b, int cutoff){
        DotProduct.CUTOFF = cutoff;
        return POOL.invoke(new DotProductTask(a, b, 0, a.length, cutoff));
    }

    private static class DotProductTask extends RecursiveTask<Integer>{
        private final int[] a;
        private final int[] b;
        private final int start;
        private final int end;
        private final int cutoff;

        public DotProductTask(int[] a, int[] b, int start, int end, int cutoff){
            this.a = a;
            this.b = b;
            this.start = start;
            this.end = end;
            this.cutoff = cutoff;
        }

        public Integer compute(){
            if (end - start <= cutoff) {
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += a[i]*b[i];
                }
                return sum;
            } else {
                int mid = start + (end - start) / 2;
                DotProductTask left = new DotProductTask(a, b, start, mid, cutoff);
                DotProductTask right = new DotProductTask(a, b, mid, end, cutoff);
                left.fork();
                int rightResult = right.compute();
                int leftResult = left.join();
                return leftResult + rightResult;
            }
        }
    }
    
}
