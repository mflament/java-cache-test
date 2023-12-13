package org.yah.test.performance.cache;

/**
 * column major using a single array column per column (stride = rows)
 */
public class LinearMatrix implements Matrix {

    private final int rows, columns;
    private final double[] data;

    public LinearMatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.data = new double[rows * columns];
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int columns() {
        return columns;
    }

    @Override
    public double get(int row, int col) {
        return data[index(row, col)];
    }

    @Override
    public void set(int row, int col, double val) {
        data[index(row, col)] = val;
    }

    private int index(int row, int col) {
        return col * rows + row;
    }

    public static void dot(LinearMatrix a, LinearMatrix b, LinearMatrix c) {
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

    public static void transposedDot(LinearMatrix a, LinearMatrix b, LinearMatrix c) {
        if (a.rows() != b.rows() || c.rows() != a.columns() || c.columns() != b.columns())
            throw new IllegalArgumentException("Invalid matrix dimensions");
        int M = c.rows(), N = c.columns(), K = a.rows();
        for (int j = 0; j < N; j++) {
            for (int i = 0; i < M; i++) {
                double sum = 0;
                for (int k = 0; k < K; k++) {
                    sum += a.get(k, i) * b.get(k, j);
                }
                c.set(i, j, sum);
            }
        }
    }

}
