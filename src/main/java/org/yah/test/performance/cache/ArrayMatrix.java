package org.yah.test.performance.cache;

public class ArrayMatrix implements Matrix<ArrayMatrix> {
    private final int dimension;
    private final double[][] data;

    public ArrayMatrix(int dimension) {
        this.dimension = dimension;
        this.data = new double[dimension][dimension];
    }

    @Override
    public int dimension() {
        return dimension;
    }

    @Override
    public double get(int row, int col) {
        return data[row][col];
    }

    @Override
    public void set(int row, int col, double val) {
        data[row][col] = val;
    }

    @Override
    public void dot(ArrayMatrix b, ArrayMatrix c) {
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                double result = 0;
                for (int i = 0; i < dimension; i++) {
                    result += get(row, i) * b.get(i, col);
                }
                c.set(row, col, result);
            }
        }
    }

}
