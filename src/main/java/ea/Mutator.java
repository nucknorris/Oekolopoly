package ea;

import java.util.Random;

public class Mutator {

	private final double[][] weights;
	private final double[][] thresholds;
	private double[][] weightsStratParameters;
	private double[][] thresholdsStratParameters;

	private double[][] newWeights;
	private double[][] newThresholds;
	private double[][] newWeightsStratParameters;
	private double[][] newThresholdsStratParameters;

	private static final double ALPHA = EvoAlg.getAlpha();
	private static final double EPSILON = EvoAlg.getEpsilon();
	private final Random random = new Random();

	public Mutator(double[][] weights, double[][] thresholds,
			double[][] weightsStratParameters,
			double[][] tresholdsStratParameters) {
		this.weights = weights.clone();
		this.thresholds = thresholds.clone();
		this.weightsStratParameters = weightsStratParameters.clone();
		this.thresholdsStratParameters = tresholdsStratParameters.clone();
	}

	void runMutation() {
		newWeights = new double[EvoAlg.getLayer()][10];
		newThresholds = new double[EvoAlg.getLayer()][10];
		newWeightsStratParameters = new double[EvoAlg.getLayer()][10];
		newThresholdsStratParameters = new double[EvoAlg.getLayer()][10];
		for (int i = 0; i < EvoAlg.getLayer(); i++) {
			for (int j = 0; j < 10; j++) {
				mutateWeights(i, j);
				mutateThresholds(i, j);
			}
		}
	}

	private void mutateWeights(int i, int j) {

		double uPipe = 0.0;
		double u = 0.0;
		double aS = weightsStratParameters[i][j];
		double bS;
		double aG = weights[i][j];
		double bG;

		uPipe = random.nextGaussian() * aS * ALPHA;
		bS = aS + uPipe;
		bS = Math.max(bS, EPSILON);
		u = random.nextGaussian() * aS;
		bG = aG + u;

		newWeights[i][j] = bG;
		newWeightsStratParameters[i][j] = bS;

	}

	private void mutateThresholds(int i, int j) {

		double uPipe = 0.0;
		double u = 0.0;
		double aS = thresholdsStratParameters[i][j];
		double bS;
		double aG = thresholds[i][j];
		double bG;

		uPipe = random.nextGaussian() * aS * ALPHA;
		bS = aS + uPipe;
		bS = Math.max(bS, EPSILON);
		u = random.nextGaussian() * aS;
		bG = aG + u;

		newThresholds[i][j] = bG;
		newThresholdsStratParameters[i][j] = bS;
	}

	public double[][] getWeights() {
		return newWeights;
	}

	public double[][] getThresholds() {
		return newThresholds;
	}

	public double[][] getWeightsStratParameters() {
		return newWeightsStratParameters;
	}

	public void setWeightsStratParameters(double[][] weightsStratParameters) {
		this.weightsStratParameters = weightsStratParameters;
	}

	public double[][] getTresholdsStratParameters() {
		return newThresholdsStratParameters;
	}

	public void setTresholdsStratParameters(double[][] tresholdsStratParameters) {
		this.thresholdsStratParameters = tresholdsStratParameters;
	}
}
