package org.yah.test.performance.cache;

public interface Matrix {

    int rows();

    int columns();

    double get(int row, int col);

    void set(int row, int col, double val);

}
