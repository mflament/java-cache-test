package org.yah.test.performance.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DualMatrixTest {

    private static final int DIMENSION = 10;

    private static DualMatrix newDualMatrix() {
        return Benchmark.randomMatrix(new DualMatrix(DIMENSION));
    }

    private static SingleMatrix newSingleMatrix(DualMatrix dm) {
        final SingleMatrix sm = new SingleMatrix(dm.dimension());
        for (int row = 0; row < dm.dimension(); row++) {
            for (int col = 0; col < dm.dimension(); col++) {
                sm.set(row, col, dm.get(row, col));
            }
        }
        return sm;
    }

    @Test
    void get() {
        final DualMatrix a = newDualMatrix();
        Benchmark.randomMatrix(a);
        for (int row = 0; row < DIMENSION; row++) {
            for (int col = 0; col < DIMENSION; col++) {
                assertThat(a.get(row, col)).isEqualTo(a.getRow(row, col));
                assertThat(a.get(row, col)).isEqualTo(a.getCol(col, row));
            }
        }
    }

    @Test
    void dot() {
        final DualMatrix da = newDualMatrix();
        final DualMatrix db = newDualMatrix();
        final DualMatrix dc = new DualMatrix(DIMENSION);
        da.dot(db, dc);

        final SingleMatrix sa = newSingleMatrix(da);
        final SingleMatrix sb = newSingleMatrix(db);
        final SingleMatrix sc = new SingleMatrix(DIMENSION);
        sa.dot(sb, sc);

        for (int row = 0; row < DIMENSION; row++) {
            for (int col = 0; col < DIMENSION; col++) {
                assertThat(da.get(row, col)).isEqualTo(sa.get(row, col));
                assertThat(db.get(row, col)).isEqualTo(sb.get(row, col));
                assertThat(dc.get(row, col)).isEqualTo(sc.get(row, col));
            }
        }
    }
}