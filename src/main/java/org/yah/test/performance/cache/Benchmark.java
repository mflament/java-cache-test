package org.yah.test.performance.cache;

public class Benchmark {

    private static final int M = 1000;
    private static final int N = 1000;
    private static final int K = 1000;

    private static final int WARMUP = 3;
    private static final int RUN = 5;

    private static final long SEED = 12345;

    public static void main(String[] args) {

        System.out.printf("matrix dot product : %dx%d . %dx%d%n", M, K, K, N);

        bench("ArraysMatrix (generic dot product)", ArraysMatrix::new, MatrixUtils::dot);
        bench("LinearMatrix (generic dot product)", LinearMatrix::new, MatrixUtils::dot);

        bench("ArraysMatrix (typed dot product)", ArraysMatrix::new, ArraysMatrix::dot);
        bench("LinearMatrix (typed dot product)", LinearMatrix::new, LinearMatrix::dot);

        bench("ArraysMatrix (transposed dot product)", ArraysMatrix::new, ArraysMatrix::transposedDot);
        bench("LinearMatrix (transposed dot product)", LinearMatrix::new, LinearMatrix::transposedDot);
    }

    @FunctionalInterface
    interface MatrixFactory<M> {
        M create(int rows, int cols);
    }

    @FunctionalInterface
    interface DotProduct<M extends Matrix> {
        void dot(M a, M b, M c);
    }

    private static <M extends Matrix> void bench(String title, MatrixFactory<M> matrixFactory, DotProduct<M> dotProduct) {
        M a = matrixFactory.create(M, K);
        M b = matrixFactory.create(K, N);
        M c = matrixFactory.create(M, N);
        MatrixUtils.randomize(a, SEED);
        MatrixUtils.randomize(b, SEED);
        for (int i = 0; i < WARMUP; i++) {
            dotProduct.dot(a, b, c);
            MatrixUtils.reset(c);
        }
        double total = 0;
        for (int i = 0; i < RUN; i++) {
            long start = System.nanoTime();
            dotProduct.dot(a, b, c);
            total += (System.nanoTime() - start) * 1E-6;
            MatrixUtils.reset(c);
        }
        System.out.printf("%-20s: total=%.2fms  avg=%.2fms%n", title, total, total / RUN);
    }

}
