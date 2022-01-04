package org.yah.test.performance.loopunroll;

import java.util.Random;

public class LoopUnroll {

    private static double simpleSum(double[] data) {
        double res = 0;
        for (int i = 0; i < data.length; i++) {
            res += data[i];
        }
        return res;
    }

    private static double unrolledSum(double[] data) {
        double res0 = 0;
        double res1 = 0;
        double res2 = 0;
        double res3 = 0;

        for (int i = 0; i < data.length; i += 4) {
            res0 += data[i];
            res1 += data[i + 1];
            res2 += data[i + 2];
            res3 += data[i + 3];
        }
        return (res0 + res1) + (res2 + res3);
    }

    private static double[] randomize(int count) {
        final Random random = new Random();
        final double[] res = new double[count];
        for (int i = 0; i < count; i++) {
            res[i] = random.nextDouble();
        }
        return res;
    }

    private static void bench(String name, int count, int iteration, SumProducer task) {
        final double[] data = randomize(count);
        double total = 0;
        System.out.printf("%s (%,d elements)%n", name, count);
        for (int i = 0; i < iteration; i++) {
            final long start = System.nanoTime();
            total += task.sum(data);
            final double time = (System.nanoTime() - start) * 1e-9;
            System.out.printf("  %.3f ms%n", time);
        }
        System.out.println(total);
    }

    public static void main(String[] args) {
        final int count = 160_000_000;
        double[] data = randomize(count);
        bench("simple", count, 5, LoopUnroll::simpleSum);
        bench("unroll", count, 5, LoopUnroll::unrolledSum);
    }

    interface SumProducer {
        double sum(double[] data);
    }
}
