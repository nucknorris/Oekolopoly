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
 * The Class Utilities.
 */
public class Utilities {

	/**
	 * Gen rnd weights.
	 *
	 * @param random the random
	 * @return the double[][]
	 */
	public static double[][] genRndWeights(Random random) {
		double weights[][] = new double[EvoAlg.getLayer()][EvoAlg.getNeurons()];
		for (int i = 0; i < EvoAlg.getLayer(); i++) {
			for (int j = 0; j < EvoAlg.getNeurons(); j++) {
				weights[i][j] = random.nextGaussian();
			}
		}
		return weights;
	}

	/**
	 * Gen rnd thresholds.
	 *
	 * @param random the random
	 * @return the double[][]
	 */
	public static double[][] genRndThresholds(Random random) {
		double thresholds[][] = new double[EvoAlg.getLayer()][EvoAlg
				.getNeurons()];
		for (int i = 1; i < EvoAlg.getLayer(); i++) {
			for (int j = 0; j < EvoAlg.getNeurons(); j++) {
				thresholds[i][j] = random.nextGaussian();
			}
		}
		return thresholds;
	}

	/**
	 * Gen start weight strat param.
	 *
	 * @return the double[][]
	 */
	public static double[][] genStartWeightStratParam() {
		double strategyParams[][] = new double[EvoAlg.getLayer()][EvoAlg
				.getNeurons()];
		for (int i = 0; i < EvoAlg.getLayer(); i++) {
			for (int j = 0; j < EvoAlg.getNeurons(); j++) {
				strategyParams[i][j] = 1;
			}
		}
		return strategyParams;
	}

	/**
	 * Gen start threshold strat param.
	 *
	 * @return the double[][]
	 */
	public static double[][] genStartThresholdStratParam() {
		double strategyParams[][] = new double[EvoAlg.getLayer()][EvoAlg
				.getNeurons()];
		for (int i = 0; i < EvoAlg.getLayer(); i++) {
			for (int j = 0; j < EvoAlg.getNeurons(); j++) {
				strategyParams[i][j] = 1;
			}
		}
		return strategyParams;
	}
}
