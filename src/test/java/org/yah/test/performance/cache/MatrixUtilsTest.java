package org.yah.test.performance.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatrixUtilsTest {

    @Test
    void dot() {
        int rows = 2, cols = 2;
        ArraysMatrix m1 = new ArraysMatrix(rows, cols);
        ArraysMatrix m2 = new ArraysMatrix(rows, cols);
        m1.set(0, 0, 1);
        m1.set(1, 0, 2);
        m1.set(0, 1, 3);
        m1.set(1, 1, 4);

        m2.set(0, 0, 5);
        m2.set(1, 0, 6);
        m2.set(0, 1, 7);
        m2.set(1, 1, 8);

        ArraysMatrix m3 = new ArraysMatrix(rows, cols);
        MatrixUtils.dot(m1, m2, m3);
        assertEquals(1.0 * 5 + 3 * 6, m3.get(0, 0));
        assertEquals(2.0 * 5 + 4 * 6, m3.get(1, 0));
        assertEquals(1.0 * 7 + 3 * 8, m3.get(0, 1));
        assertEquals(2.0 * 7 + 4 * 8, m3.get(1, 1));
    }

    @Test
    void transposedDot() {
        int M = 300, N = 275, K = 200;
        LinearMatrix a = new LinearMatrix(M, K);
        LinearMatrix b = new LinearMatrix(K, N);
        LinearMatrix expectedC = new LinearMatrix(M, N);

        LinearMatrix.dot(a, b, expectedC);

        LinearMatrix ta = new LinearMatrix(K, M);
        MatrixUtils.transpose(ta, a);
        LinearMatrix actualC = new LinearMatrix(M, N);
        MatrixUtils.transposedDot(ta, b, actualC);
        assertMatrixEquals(expectedC, actualC);

        LinearMatrix.transposedDot(ta, b, actualC);
        assertMatrixEquals(expectedC, actualC);
    }

    @Test
    void transpose() {
        int rows = 300, cols = 256;
        ArraysMatrix m1 = new ArraysMatrix(rows, cols);
        LinearMatrix m2 = new LinearMatrix(rows, cols);
        ArraysMatrix tm1 = new ArraysMatrix(cols, rows);
        LinearMatrix tm2 = new LinearMatrix(cols, rows);
        MatrixUtils.transpose(tm1, m1);
        MatrixUtils.transpose(tm2, m2);
        assertMatrixEquals(tm1, tm2);
    }

    @Test
    void randomize() {
        int rows = 300, cols = 256;
        ArraysMatrix m1 = new ArraysMatrix(rows, cols);
        LinearMatrix m2 = new LinearMatrix(rows, cols);
        MatrixUtils.randomize(m1, 12345);
        MatrixUtils.randomize(m2, 12345);
        assertMatrixEquals(m1, m2);
    }

    private static void assertMatrixEquals(Matrix a, Matrix b) {
        assertEquals(a.rows(), b.rows());
        assertEquals(a.columns(), b.columns());
        for (int i = 0; i < a.rows(); i++) {
            for (int j = 0; j < a.columns(); j++) {
                assertEquals(a.get(i, j), b.get(i, j));
            }
        }
    }


}