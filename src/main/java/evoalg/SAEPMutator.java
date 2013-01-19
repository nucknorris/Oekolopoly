/*
 * HTWK Leipzig
 * Evolutionaere Algorithmen 
 * Projekt Aufgabe Drei
 * @author Oliver Plewnia
 * 
 */
package evoalg;

import java.util.Random;

/**
 * The Class SelfAdaptEpMutator.
 */
public class SAEPMutator {

	private double[][] weights;
	private double[][] thresholds;
	private double[][] weightsStrategyParams;
	private double[][] thresholdsStrategyParams;

	private double[][] newWeights;
	private double[][] newThresholds;
	private double[][] newWeightsStrategyParams;
	private double[][] newThresholdsStrategyParams;

	private Random random = new Random();

	public SAEPMutator(double[][] weights, double[][] thresholds,
			double[][] weightsStrategyParams, double[][] tresholdsStrategyParams) {
		this.weights = weights.clone();
		this.thresholds = thresholds.clone();
		this.weightsStrategyParams = weightsStrategyParams.clone();
		this.thresholdsStrategyParams = tresholdsStrategyParams.clone();
	}

	void runMutation() {
		newWeights = new double[EvoAlg.LAYER][EvoAlg.NEURONS];
		newThresholds = new double[EvoAlg.LAYER][EvoAlg.NEURONS];
		newWeightsStrategyParams = new double[EvoAlg.LAYER][EvoAlg.NEURONS];
		newThresholdsStrategyParams = new double[EvoAlg.LAYER][EvoAlg.NEURONS];
		for (int i = 0; i < EvoAlg.LAYER; i++) {
			for (int j = 0; j < EvoAlg.NEURONS; j++) {
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

		uPipe = random.nextGaussian() * aS * EvoAlg.ALPHA;
		bS = aS + uPipe;
		bS = Math.max(bS, EvoAlg.EPSILON);
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

		uPipe = random.nextGaussian() * aS * EvoAlg.ALPHA;
		bS = aS + uPipe;
		bS = Math.max(bS, EvoAlg.EPSILON);
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

	public double[][] getWeightsSP() {
		return newWeightsStrategyParams;
	}

	public void setWeightsStrategyParams(double[][] weightsStrategyParams) {
		this.weightsStrategyParams = weightsStrategyParams;
	}

	public double[][] getTresholdsSP() {
		return newThresholdsStrategyParams;
	}

	public void setTresholdsStrategyParams(double[][] tresholdsStrategyParams) {
		this.thresholdsStrategyParams = tresholdsStrategyParams;
	}
}