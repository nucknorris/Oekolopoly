/*
 * HTWK Leipzig
 * Evolutionaere Algorithmen 
 * Projekt Aufgabe Drei
 * @author Oliver Plewnia
 * 
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

import loesung.OplewniaIndividuum;
import evoalg.EvoAlg;

/**
 * The Class Utilities.
 */
public class Utilities {

	/**
	 * Gen rnd weights.
	 * 
	 * @param random
	 *            the random
	 * @return the double[][]
	 */
	public static double[][] genRndWeights(Random random) {
		double weights[][] = new double[EvoAlg.LAYER][EvoAlg.NEURONS];
		for (int i = 0; i < EvoAlg.LAYER; i++) {
			for (int j = 0; j < EvoAlg.NEURONS; j++) {
				weights[i][j] = random.nextGaussian();
			}
		}
		return weights;
	}

	public static double[][] genRndWeights(MersenneTwisterFast random) {
		double weights[][] = new double[EvoAlg.LAYER][EvoAlg.NEURONS];
		for (int i = 0; i < EvoAlg.LAYER; i++) {
			for (int j = 0; j < EvoAlg.NEURONS; j++) {
				weights[i][j] = random.nextGaussian();
			}
		}
		return weights;
	}

	/**
	 * Gen rnd thresholds.
	 * 
	 * @param random
	 *            the random
	 * @return the double[][]
	 */
	public static double[][] genRndThresholds(Random random) {
		double thresholds[][] = new double[EvoAlg.LAYER][EvoAlg.NEURONS];
		for (int i = 0; i < EvoAlg.LAYER; i++) {
			for (int j = 0; j < EvoAlg.NEURONS; j++) {
				thresholds[i][j] = random.nextGaussian();
			}
		}
		return thresholds;
	}

	public static double[][] genRndThresholds(MersenneTwisterFast random) {
		double thresholds[][] = new double[EvoAlg.LAYER][EvoAlg.NEURONS];
		for (int i = 0; i < EvoAlg.LAYER; i++) {
			for (int j = 0; j < EvoAlg.NEURONS; j++) {
				thresholds[i][j] = random.nextGaussian();
			}
		}
		return thresholds;
	}

	/**
	 * Gen start weight sp.
	 * 
	 * @return the double[][]
	 */
	public static double[][] genStartWeightSP() {
		double strategyParams[][] = new double[EvoAlg.LAYER][EvoAlg.NEURONS];
		for (int i = 0; i < EvoAlg.LAYER; i++) {
			for (int j = 0; j < EvoAlg.NEURONS; j++) {
				strategyParams[i][j] = 1;
			}
		}
		return strategyParams;
	}

	/**
	 * Gen start threshold sp.
	 * 
	 * @return the double[][]
	 */
	public static double[][] genStartThresholdSP() {
		double strategyParams[][] = new double[EvoAlg.LAYER][EvoAlg.NEURONS];
		for (int i = 0; i < EvoAlg.LAYER; i++) {
			for (int j = 0; j < EvoAlg.NEURONS; j++) {
				strategyParams[i][j] = 1;
			}
		}
		return strategyParams;
	}

	/**
	 * Pretty print.
	 * 
	 * @param m
	 *            the m
	 */
	public static void prettyPrint(int[][] m) {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				System.out.print(m[i][j] + "\t");
			}
			System.out.print("\n");
		}
	}

	/**
	 * Pretty print.
	 * 
	 * @param m
	 *            the m
	 */
	public static void prettyPrint(double[][] m) {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				System.out.printf("%.3f\t", m[i][j]);
			}
			System.out.print("\n");
		}
	}

	/**
	 * Gen gaussian.
	 * 
	 * @param rnd
	 *            the rnd
	 * @return the double
	 */
	@Deprecated
	public static double genGaussian(Random rnd) {
		double g = Math.abs(rnd.nextGaussian());
		while (g >= 1) {
			g = Math.abs(rnd.nextGaussian());
		}
		return Math.abs(g);
	}

	public static double genGaussian(MersenneTwisterFast rnd) {
		double g = Math.abs(rnd.nextGaussian());
		while (g >= 1) {
			g = Math.abs(rnd.nextGaussian());
		}
		return Math.abs(g);
	}

	/**
	 * Gen random.
	 * 
	 * @param rnd
	 *            the rnd
	 * @return the double
	 */
	public static double genRandom(Random rnd) {
		return rnd.nextDouble();
	}

	/**
	 * Sum.
	 * 
	 * @param i
	 *            the i
	 * @return the int
	 */
	public static int sum(int i[]) {
		int sum = 0;
		for (int j : i) {
			sum += j;
		}
		return sum;
	}

	/**
	 * Factorial.
	 * 
	 * @param n
	 *            the n
	 * @return the int
	 */
	public static int factorial(int n) {
		int fact = 1;
		for (int i = 1; i <= n; i++) {
			fact *= i;
		}
		return fact;
	}

	/**
	 * Load individual.
	 * 
	 * @param s
	 *            the s
	 * @return the oplewnia individuum
	 */
	public static OplewniaIndividuum loadIndividual(String s) {

		OplewniaIndividuum deserial = null;
		try {
			FileInputStream fileIn = new FileInputStream(new File(s));
			ObjectInputStream in = new ObjectInputStream(fileIn);
			deserial = (OplewniaIndividuum) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("MeinIndividuum class not found");
			c.printStackTrace();
		}
		return deserial;
	}
}
