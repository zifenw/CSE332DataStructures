import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class MatrixMultiply {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.multiply.
    // Ignoring the initialization of arrays, your implementation should have n^3 work and log(n) span
    public static int[][] multiply(int[][] a, int[][] b, int cutoff){
        MatrixMultiply.CUTOFF = cutoff;
        int[][] product = new int[a.length][b[0].length];
        POOL.invoke(new MatrixMultiplyAction(a, b, product, 0, a.length, 0, b[0].length, cutoff)); // TODO: add parameters to match your constructor
        return product;
    }

    // Behavior should match the 2d version of Sequential.dotProduct.
    // Your implementation must have linear work and log(n) span
    public static int dotProduct(int[][] a, int[][] b, int row, int col, int cutoff){
        MatrixMultiply.CUTOFF = cutoff;
        return POOL.invoke(new DotProductTask(a, b, row, col, 0, a[row].length, cutoff));
    }

    private static class MatrixMultiplyAction extends RecursiveAction{
        int[][] a, b, product;
        int rowStart, rowEnd, colStart, colEnd, cutoff;

        public MatrixMultiplyAction(int[][] a, int[][] b, int[][] product,
                                    int rowStart, int rowEnd, int colStart, int colEnd, int cutoff) {
            this.a = a;
            this.b = b;
            this.product = product;
            this.rowStart = rowStart;
            this.rowEnd = rowEnd;
            this.colStart = colStart;
            this.colEnd = colEnd;
            this.cutoff = cutoff;
        }

        protected void compute() {
            int rows = rowEnd - rowStart;
            int cols = colEnd - colStart;
            
            // Base case: Switch to sequential if below cutoff
            if (rows <= CUTOFF) {
                for (int i = rowStart; i < rowEnd; i++) {
                    for (int j = colStart; j < colEnd; j++) {
                        product[i][j] = POOL.invoke(new DotProductTask(a, b, i, j, 0, a[0].length, cutoff));
                    }
                }
                return;
            }

            // Recursive case: Split into quadrants
            int midRow = rowStart + rows / 2;
            int midCol = colStart + cols / 2;

            invokeAll(
                new MatrixMultiplyAction(a, b, product, rowStart, midRow, colStart, midCol, cutoff),
                new MatrixMultiplyAction(a, b, product, rowStart, midRow, midCol, colEnd, cutoff),
                new MatrixMultiplyAction(a, b, product, midRow, rowEnd, colStart, midCol, cutoff),
                new MatrixMultiplyAction(a, b, product, midRow, rowEnd, midCol, colEnd, cutoff)
            );
        }

    }

    private static class DotProductTask extends RecursiveTask<Integer>{
        private final int[][] a, b;
        private final int row, col;
        private final int start, end;
        private final int cutoff;

        public DotProductTask(int[][] a, int[][] b, int row, int col, int start, int end, int cutoff){
            this.a = a;
            this.b = b;
            this.row = row;
            this.col = col;
            this.start = start;
            this.end = end;
            this.cutoff = cutoff;
        }

        public Integer compute(){
            if (end - start <= cutoff) {
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += a[row][i] * b[i][col];
                }
                return sum;
            } else {
                int mid = start + (end - start) / 2;
                DotProductTask left = new DotProductTask(a, b, row, col, start, mid, cutoff);
                DotProductTask right = new DotProductTask(a, b, row, col, mid, end, cutoff);
                left.fork();
                int rightResult = right.compute();
                int leftResult = left.join();
                return leftResult + rightResult;
            }
        }

    }
    
}
