package org.yah.test.performance.cache;

import java.util.Random;
import java.util.function.IntFunction;

public class Benchmark {

    private static final Random random = new Random();

    public static void main(String[] args) {
        final int iteration = 3;
        final int dimension = 1500;
        System.out.format("%dx%d Matrix dot product %n", dimension, dimension);
        new MatrixBenchmark<>(ArrayMatrix::new, dimension, iteration).run();
        new MatrixBenchmark<>(SingleMatrix::new, dimension, iteration).run();
        new MatrixBenchmark<>(DualMatrix::new, dimension, iteration).run();
    }

    public static <M extends Matrix<M>> M randomMatrix(M matrix) {
        for (int row = 0; row < matrix.dimension(); row++) {
            for (int col = 0; col < matrix.dimension(); col++) {
                matrix.set(row, col, random.nextDouble());
            }
        }
        return matrix;
    }

    public static final class MatrixBenchmark<M extends Matrix<M>> {
        private final IntFunction<M> factory;
        private final int dimension;
        private final int iteration;

        public MatrixBenchmark(IntFunction<M> factory, int dimension, int iteration) {
            this.factory = factory;
            this.dimension = dimension;
            this.iteration = iteration;
        }

        public void run() {
            final M a = randomMatrix(factory.apply(dimension));
            final M b = randomMatrix(factory.apply(dimension));
            final M c = factory.apply(dimension);
            System.out.println("benching " + a.getClass().getSimpleName());
            long total = 0;
            for (int i = 0; i < iteration; i++) {
                final long start = System.currentTimeMillis();
                a.dot(b, c);
                long elapsed = System.currentTimeMillis() - start;
                total += elapsed;
//                System.out.println("Run " + i + " : " + elapsed + " ms");
            }
            System.out.printf("Total %s : %d ms, average: %.2f%n", a.getClass().getSimpleName(), total, total / (double) iteration);
        }

    }

}
