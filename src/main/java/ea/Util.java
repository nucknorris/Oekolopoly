package ea;

import java.util.Random;

public class Util {

    private static final double UPPER_BOUND = .01;

    public static double[][] generateRandomWeights() {
        double weights[][] = new double[3][10];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                weights[i][j] = getRnd(UPPER_BOUND);
            }
        }
        return weights;
    }

    public static double[][] generateRandomThresholds() {
        double thresholds[][] = new double[3][10];

        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                thresholds[i][j] = getRnd(UPPER_BOUND);
            }
        }
        return thresholds;
    }

    public static double[][] generateRandomStrategyParameters() {
        double strategyParams[][] = new double[3][10];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                strategyParams[i][j] = getRnd(UPPER_BOUND);
            }
        }
        return strategyParams;
    }

    public static double getRnd(double high) {
        return new Random().nextGaussian();
    }
}
