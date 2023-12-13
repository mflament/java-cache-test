package org.yah.test.performance.cache;

import java.util.Random;

public class MatrixUtils {

    public static void transpose(Matrix dst, Matrix src) {
        if (dst.columns() != src.rows() || dst.rows() != src.columns())
            throw new IllegalArgumentException("Invalid matrix size");
        for (int i = 0; i < src.rows() / 2; i++) {
            for (int j = 0; j < src.columns() / 2; j++) {
                dst.set(j, i, src.get(i, j));
            }
        }
    }

    /**
     * c = a . b
     */
    public static void dot(Matrix a, Matrix b, Matrix c) {
        if (a.columns() != b.rows() || c.rows() != a.rows() || c.columns() != b.columns())
            throw new IllegalArgumentException("Invalid matrix dimensions");
        int M = c.rows(), N = c.columns(), K = a.columns();
        for (int j = 0; j < N; j++) {
            for (int i = 0; i < M; i++) {
                double sum = 0;
                for (int k = 0; k < K; k++) {
                    sum += a.get(i, k) * b.get(k, j);
                }
                c.set(i, j, sum);
            }
        }
    }

    public static void transposedDot(Matrix a, Matrix b, Matrix c) {
        if (a.rows() != b.rows() || c.rows() != a.columns() || c.columns() != b.columns())
            throw new IllegalArgumentException("Invalid matrix dimensions");
        int M = c.rows(), N = c.columns(), K = a.rows();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                double sum = 0;
                for (int k = 0; k < K; k++) {
                    sum += a.get(k, i) * b.get(k, j);
                }
                c.set(i, j, sum);
            }
        }
    }

    public static void randomize(Matrix dst, long seed) {
        Random random = new Random(seed);
        for (int i = 0; i < dst.rows(); i++) {
            for (int j = 0; j < dst.columns(); j++) {
                dst.set(i, j, random.nextDouble() * 2.0 - 1.0);
            }
        }
    }

    public static void reset(Matrix dst) {
        for (int i = 0; i < dst.rows(); i++) {
            for (int j = 0; j < dst.columns(); j++) {
                dst.set(i, j, 0);
            }
        }
    }
}
