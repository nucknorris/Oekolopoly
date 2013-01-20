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

	/**
	 * Instantiates a new sAEP mutator.
	 *
	 * @param weights the weights
	 * @param thresholds the thresholds
	 * @param weightsStrategyParams the weights strategy params
	 * @param tresholdsStrategyParams the tresholds strategy params
	 */
	public SAEPMutator(double[][] weights, double[][] thresholds,
			double[][] weightsStrategyParams, double[][] tresholdsStrategyParams) {
		this.weights = weights.clone();
		this.thresholds = thresholds.clone();
		this.weightsStrategyParams = weightsStrategyParams.clone();
		this.thresholdsStrategyParams = tresholdsStrategyParams.clone();
	}

	/**
	 * Run mutation.
	 */
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

	/**
	 * Mutate weights.
	 *
	 * @param i the i
	 * @param j the j
	 */
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

	/**
	 * Mutate thresholds.
	 *
	 * @param i the i
	 * @param j the j
	 */
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

	/**
	 * Gets the weights.
	 *
	 * @return the weights
	 */
	public double[][] getWeights() {
		return newWeights;
	}

	/**
	 * Gets the thresholds.
	 *
	 * @return the thresholds
	 */
	public double[][] getThresholds() {
		return newThresholds;
	}

	/**
	 * Gets the weights sp.
	 *
	 * @return the weights sp
	 */
	public double[][] getWeightsSP() {
		return newWeightsStrategyParams;
	}

	/**
	 * Sets the weights strategy params.
	 *
	 * @param weightsStrategyParams the new weights strategy params
	 */
	public void setWeightsStrategyParams(double[][] weightsStrategyParams) {
		this.weightsStrategyParams = weightsStrategyParams;
	}

	/**
	 * Gets the tresholds sp.
	 *
	 * @return the tresholds sp
	 */
	public double[][] getTresholdsSP() {
		return newThresholdsStrategyParams;
	}

	/**
	 * Sets the tresholds strategy params.
	 *
	 * @param tresholdsStrategyParams the new tresholds strategy params
	 */
	public void setTresholdsStrategyParams(double[][] tresholdsStrategyParams) {
		this.thresholdsStrategyParams = tresholdsStrategyParams;
	}
}