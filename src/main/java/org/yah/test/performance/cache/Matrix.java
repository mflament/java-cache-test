package org.yah.test.performance.cache;

public interface Matrix<M extends Matrix<M>> {

    int dimension();

    double get(int row, int col);

    void set(int row, int col, double val);

    void dot(M b, M c);

}
