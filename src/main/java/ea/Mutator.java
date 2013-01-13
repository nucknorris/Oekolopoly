package ea;

import java.util.Random;

import org.apache.log4j.Logger;

public class Mutator {
    private static Logger logger = Logger.getLogger(Mutator.class);

    private double[][] weights;
    private double[][] thresholds;
    private double[][] weightsStrategyParams;
    private double[][] thresholdsStrategyParams;

    private double[][] newWeights;
    private double[][] newThresholds;
    private double[][] newWeightsStrategyParams;
    private double[][] newThresholdsStrategyParams;

    private static final double ALPHA = 0.1;
    private static final double EPSILON = 0.00001;
    private Random random = new Random();

    public Mutator(double[][] weights, double[][] thresholds, double[][] weightsStrategyParams,
            double[][] tresholdsStrategyParams) {
        this.weights = weights.clone();
        this.thresholds = thresholds.clone();
        this.weightsStrategyParams = weightsStrategyParams.clone();
        this.thresholdsStrategyParams = tresholdsStrategyParams.clone();
    }

    void runSelfAdaptiveEpMutation() {
        newWeights = new double[3][10];
        newThresholds = new double[3][10];
        newWeightsStrategyParams = new double[3][10];
        newThresholdsStrategyParams = new double[3][10];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                mutateWeights(i, j);
                mutateThresholds(i, j);
            }
        }
    }

    private void mutateWeights(int i, int j) {

        double uPipe = 0.0;
        double u = 0.0;
        double aS = weightsStrategyParams[i][j];
        double bS;
        double aG = weights[i][j];
        double bG;

        uPipe = random.nextGaussian() * aS * ALPHA;
        bS = aS + uPipe;
        bS = Math.max(bS, EPSILON);
        u = random.nextGaussian() * aS;
        bG = aG + u;

        newWeights[i][j] = bG;
        newWeightsStrategyParams[i][j] = bS;

        // double uAlphaWeights = random.nextGaussian() *
        // weightsStrategyParams[i][j] * ALPHA;
        // double uWeights = random.nextGaussian() *
        // weightsStrategyParams[i][j];
        // double uAlphaThresholds = random.nextGaussian() *
        // tresholdsStrategyParams[i][j] * ALPHA;
        // double uThresholds = random.nextGaussian() *
        // tresholdsStrategyParams[i][j];
        //
        // weights[i][j] += uWeights;
        // thresholds[i][j] += uThresholds;
        //
        // double weightsStrategyParam = Math.max((weightsStrategyParams[i][j] +
        // uAlphaWeights),
        // EPSILON);
        // double tresholdsStrategyParam = Math.max(
        // (tresholdsStrategyParams[i][j] + uAlphaThresholds), EPSILON);
        //
        // weightsStrategyParams[i][j] = weightsStrategyParam;
        // tresholdsStrategyParams[i][j] = tresholdsStrategyParam;
        //
        // // logger.info("weights[i][j]: " + weights[i][j] +
        // // ", thresholds[i][j]: "
        // // + thresholds[i][j]);

    }

    private void mutateThresholds(int i, int j) {

        double uPipe = 0.0;
        double u = 0.0;
        double aS = thresholdsStrategyParams[i][j];
        double bS;
        double aG = thresholds[i][j];
        double bG;

        uPipe = random.nextGaussian() * aS * ALPHA;
        bS = aS + uPipe;
        bS = Math.max(bS, EPSILON);
        u = random.nextGaussian() * aS;
        bG = aG + u;

        newThresholds[i][j] = bG;
        newThresholdsStrategyParams[i][j] = bS;
    }

    public double[][] getWeights() {
        return newWeights;
    }

    public double[][] getThresholds() {
        return newThresholds;
    }

    public double[][] getWeightsStrategyParams() {
        return newWeightsStrategyParams;
    }

    public void setWeightsStrategyParams(double[][] weightsStrategyParams) {
        this.weightsStrategyParams = weightsStrategyParams;
    }

    public double[][] getTresholdsStrategyParams() {
        return newThresholdsStrategyParams;
    }

    public void setTresholdsStrategyParams(double[][] tresholdsStrategyParams) {
        this.thresholdsStrategyParams = tresholdsStrategyParams;
    }
}
