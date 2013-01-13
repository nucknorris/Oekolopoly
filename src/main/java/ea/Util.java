package ea;

import java.util.Random;

public class Util {

    public static double[][] generateRandomWeights() {
        double weights[][] = new double[3][10];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                weights[i][j] = getRnd();
            }
        }
        return weights;
    }

    public static double[][] generateRandomThresholds() {
        double thresholds[][] = new double[3][10];

        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                thresholds[i][j] = getRnd();
            }
        }
        return thresholds;
    }

    public static double[][] generateRandomWeightStrategyParameters() {
        double strategyParams[][] = new double[3][10];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                strategyParams[i][j] = 1;
            }
        }
        return strategyParams;
    }

    public static double[][] generateRandomThresholdStrategyParameters() {
        double strategyParams[][] = new double[3][10];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                strategyParams[i][j] = 1;
            }
        }
        return strategyParams;
    }

    public static double getRnd() {
        return new Random().nextGaussian();
    }
}
