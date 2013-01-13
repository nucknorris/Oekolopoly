package ea;

import java.util.Random;

public class Util {

    public static double[][] generateRandomWeights(Random random) {
        double weights[][] = new double[3][10];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                weights[i][j] = random.nextGaussian();
            }
        }
        return weights;
    }

    public static double[][] generateRandomThresholds(Random random) {
        double thresholds[][] = new double[3][10];

        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                thresholds[i][j] = random.nextGaussian();
            }
        }
        return thresholds;
    }

    public static double[][] generateStartWeightStrategyParameters() {
        double strategyParams[][] = new double[3][10];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                strategyParams[i][j] = 1;
            }
        }
        return strategyParams;
    }

    public static double[][] generateStartThresholdStrategyParameters() {
        double strategyParams[][] = new double[3][10];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                strategyParams[i][j] = 1;
            }
        }
        return strategyParams;
    }

}
