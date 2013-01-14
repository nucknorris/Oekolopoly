/*
 * HTWK Leipzig
 * Evolutionaere Algorithmen 
 * Projekt Aufgabe Drei
 * @author Oliver Plewnia
 * 
 */
package evoalg;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class SelfAdaptEpMutator.
 */
public class SelfAdaptEpMutator {

	/** The weights. */
	private final double[][] weights;
	
	/** The thresholds. */
	private final double[][] thresholds;
	
	/** The weights strat param. */
	private double[][] weightsStratParam;
	
	/** The thresholds strat param. */
	private double[][] thresholdsStratParam;

	/** The new weights. */
	private double[][] newWeights;
	
	/** The new thresholds. */
	private double[][] newThresholds;
	
	/** The new weights strat param. */
	private double[][] newWeightsStratParam;
	
	/** The new thresholds strat param. */
	private double[][] newThresholdsStratParam;

	/** The random. */
	private final Random random = new Random();

	/**
	 * Instantiates a new self adapt ep mutator.
	 *
	 * @param weights the weights
	 * @param thresholds the thresholds
	 * @param oldWeightsStratParam the old weights strat param
	 * @param oldTresholdsStratParam the old tresholds strat param
	 */
	public SelfAdaptEpMutator(double[][] weights, double[][] thresholds,
			double[][] oldWeightsStratParam, double[][] oldTresholdsStratParam) {
		this.weights = weights.clone();
		this.thresholds = thresholds.clone();
		this.weightsStratParam = oldWeightsStratParam.clone();
		this.thresholdsStratParam = oldTresholdsStratParam.clone();
	}

	/**
	 * Run mutation.
	 */
	void runMutation() {
		newWeights = new double[EvoAlg.getLayer()][EvoAlg.getNeurons()];
		newThresholds = new double[EvoAlg.getLayer()][EvoAlg.getNeurons()];
		newWeightsStratParam = new double[EvoAlg.getLayer()][EvoAlg
				.getNeurons()];
		newThresholdsStratParam = new double[EvoAlg.getLayer()][EvoAlg
				.getNeurons()];
		for (int i = 0; i < EvoAlg.getLayer(); i++) {
			for (int j = 0; j < EvoAlg.getNeurons(); j++) {
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
		double aS = weightsStratParam[i][j];
		double bS;
		double aG = weights[i][j];
		double bG;

		uPipe = random.nextGaussian() * aS * EvoAlg.getAlpha();
		bS = aS + uPipe;
		bS = Math.max(bS, EvoAlg.getAlpha());
		u = random.nextGaussian() * aS;
		bG = aG + u;

		newWeights[i][j] = bG;
		newWeightsStratParam[i][j] = bS;

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
		double aS = thresholdsStratParam[i][j];
		double bS;
		double aG = thresholds[i][j];
		double bG;

		uPipe = random.nextGaussian() * aS * EvoAlg.getAlpha();
		bS = aS + uPipe;
		bS = Math.max(bS, EvoAlg.getAlpha());
		u = random.nextGaussian() * aS;
		bG = aG + u;

		newThresholds[i][j] = bG;
		newThresholdsStratParam[i][j] = bS;
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
	 * Gets the weights strat param.
	 *
	 * @return the weights strat param
	 */
	public double[][] getWeightsStratParam() {
		return newWeightsStratParam;
	}

	/**
	 * Sets the weights strat param.
	 *
	 * @param weightsStratParam the new weights strat param
	 */
	public void setWeightsStratParam(double[][] weightsStratParam) {
		this.weightsStratParam = weightsStratParam;
	}

	/**
	 * Gets the tresholds strat param.
	 *
	 * @return the tresholds strat param
	 */
	public double[][] getTresholdsStratParam() {
		return newThresholdsStratParam;
	}

	/**
	 * Sets the tresholds strat param.
	 *
	 * @param tresholdsStratParam the new tresholds strat param
	 */
	public void setTresholdsStratParam(double[][] tresholdsStratParam) {
		this.thresholdsStratParam = tresholdsStratParam;
	}
}
