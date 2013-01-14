package ea;

import java.util.Random;

import org.apache.log4j.Logger;

public class Mutator {
    private static Logger logger = Logger.getLogger(Mutator.class);

    private static final double ALPHA = 0.1;
    private static final double EPSILON = 0.00001;

    private double[][] weights;
    private double[][] thresholds;
    private double[][] weightsStrategyParams;
    private double[][] thresholdsStrategyParams;

    private double[][] newWeights;
    private double[][] newThresholds;
    private double[][] newWeightsStrategyParams;
    private double[][] newThresholdsStrategyParams;

    private Random random = new Random();

    public Mutator(double[][] weights, double[][] thresholds, double[][] weightsStrategyParams,
            double[][] tresholdsStrategyParams) {
        this.weights = weights.clone();
        this.thresholds = thresholds.clone();
        this.weightsStrategyParams = weightsStrategyParams.clone();
        this.thresholdsStrategyParams = tresholdsStrategyParams.clone();
    }

    void runSelfAdaptiveEpMutation() {
        newWeights = new double[EvoAlg.TOTAL_LAYERS + 1][EvoAlg.getHiddenLayerNeurons()];
        newThresholds = new double[EvoAlg.TOTAL_LAYERS + 1][EvoAlg.getHiddenLayerNeurons()];
        newWeightsStrategyParams = new double[EvoAlg.TOTAL_LAYERS + 1][EvoAlg
                .getHiddenLayerNeurons()];
        newThresholdsStrategyParams = new double[EvoAlg.TOTAL_LAYERS + 1][EvoAlg
                .getHiddenLayerNeurons()];
        for (int i = 0; i <= EvoAlg.TOTAL_LAYERS; i++) {
            for (int j = 0; j < EvoAlg.getHiddenLayerNeurons(); j++) {
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
