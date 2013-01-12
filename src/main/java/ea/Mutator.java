package ea;

import org.apache.log4j.Logger;

public class Mutator {
    private static Logger logger = Logger.getLogger(Mutator.class);

    private double[][] weights;
    private double[][] thresholds;
    private double[][] strategyParams;
    private static final double ALPHA = 0.1;

    public Mutator(double[][] weights, double[][] thresholds, double[][] strategyParams) {
        this.weights = weights;
        this.thresholds = thresholds;
        this.strategyParams = strategyParams;
    }

    void runSelfAdaptiveEpMutation() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                mutate(i, j);
            }
        }
    }

    private void mutate(int i, int j) {
        double uAlpha = Util.getRnd(strategyParams[i][j] * ALPHA);
        double u = Util.getRnd(strategyParams[i][j]);
        weights[i][j] += u;
        thresholds[i][j] += u;
        strategyParams[i][j] += uAlpha;
    }

    public double[][] getWeights() {
        return weights;
    }

    public double[][] getThresholds() {
        return thresholds;
    }
}
