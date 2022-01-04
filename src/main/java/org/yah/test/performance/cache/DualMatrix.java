package org.yah.test.performance.cache;

public class DualMatrix implements Matrix<DualMatrix> {
    private final double[] rowMajor;
    private final double[] colMajor;
    private final int dimension;

    public DualMatrix(int dimension) {
        this.dimension = dimension;
        this.rowMajor = new double[dimension * dimension];
        this.colMajor = new double[dimension * dimension];
    }

    @Override
    public int dimension() {
        return dimension;
    }

    @Override
    public double get(int row, int col) {
        return getRow(row, col);
    }

    public double getRow(int row, int col) {
        return rowMajor[row * dimension + col];
    }

    public double getCol(int col, int row) {
        return colMajor[col * dimension + row];
    }

    public void set(int row, int col, double val) {
        rowMajor[row * dimension + col] = val;
        colMajor[col * dimension + row] = val;
    }

    /**
     * c = this . b
     */
    @Override
    public void dot(DualMatrix b, DualMatrix c) {
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                double result = 0;
                for (int dot = 0; dot < dimension; dot++) {
                    result += getRow(row, dot) * b.getCol(col, dot);
                }
                c.set(row, col, result);
            }
        }
    }

}
