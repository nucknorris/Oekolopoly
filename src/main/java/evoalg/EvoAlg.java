/*
 * HTWK Leipzig
 * Evolutionaere Algorithmen 
 * Projekt Aufgabe Drei
 * @author Oliver Plewnia
 * 
 */
package evoalg;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import loesung.OplewniaIndividuum;
import okoelopoly.Kybernetien;

import org.apache.log4j.Logger;

import util.SimuPipelineGenerator;
import util.Utilities;

// TODO: Auto-generated Javadoc
/**
 * The Class EvoAlg.
 */
public class EvoAlg {

	/** The logger. */
	private static Logger logger = Logger.getLogger(EvoAlg.class);

	/** The Constant timestamp. */
	private static final String timestamp = new Timestamp(new Date().getTime())
			.toString().replaceAll("\\s", "_");

	/** The Constant TERMINATION_ROUND. */
	private static final int TERMINATION_ROUND = 30;

	/** The Constant POP_SIZE. */
	private static final int POP_SIZE = 100;

	/** The Constant MAX_GENERATION. */
	private static final int MAX_GENERATION = 5000;

	/** The Constant NO_TERMINATION_OVER. */
	private static final int NO_TERMINATION_OVER = 24;

	/** The Constant LAYER. */
	private static final int LAYER = 6;

	/** The Constant NEURONS. */
	private static final int NEURONS = 10;

	/** The Constant ALPHA. */
	private static final double ALPHA = 0.1;

	/** The Constant EPSILON. */
	private static final double EPSILON = 0.00001;

	/** The max rounds. */
	private int maxRounds;

	/** The termination. */
	boolean termination = false;

	/** The generation. */
	int generation = 0;

	/** The filename. */
	private String filename;

	/** The simu pipeline. */
	private List<Kybernetien> simuPipeline;

	/** The rnd. */
	private Random rnd;

	private SimuPipelineGenerator spg;

	/**
	 * Instantiates a new evo alg.
	 */
	public EvoAlg() {
		rnd = new Random();
	}

	/**
	 * Run evo alg.
	 * 
	 * @return the string
	 */
	public String runEvoAlg() {
		spg = new SimuPipelineGenerator();

		double[][] weightsSParams;
		double[][] thresholdsSParams;
		List<OplewniaIndividuum> currentPop = new ArrayList<OplewniaIndividuum>();
		weightsSParams = Utilities.genStartWeightStratParam();
		thresholdsSParams = Utilities.genStartThresholdStratParam();
		currentPop = genStartPopulation(currentPop);
		logger.info("#### ALGO START");
		while (!termination) {
			if (generation % 1000 == 0) {
				if (generation > MAX_GENERATION
						&& maxRounds < NO_TERMINATION_OVER) {
					logger.info("## ALGO RESTART AFTER " + generation
							+ " GENERATIONS");
					weightsSParams = Utilities.genRndWeights(rnd);
					thresholdsSParams = Utilities.genRndThresholds(rnd);
					currentPop.clear();
					currentPop = genStartPopulation(currentPop);
					generation = 0;
					rnd = new Random();
				}
				logger.info("## I am at Generation: " + generation);
			}
			List<OplewniaIndividuum> newPop = mutateAndRatePopulation(
					currentPop, weightsSParams, thresholdsSParams);
			currentPop = BestSelection.selection(currentPop, newPop, POP_SIZE);
			generation++;
		}
		return filename;
	}

	/**
	 * Gen start population.
	 * 
	 * @param startPopulation
	 *            the start population
	 * @return the list
	 */
	private List<OplewniaIndividuum> genStartPopulation(
			List<OplewniaIndividuum> startPopulation) {
		List<OplewniaIndividuum> list = new ArrayList<OplewniaIndividuum>();
		for (int i = 0; i < POP_SIZE; i++) {
			OplewniaIndividuum ind = new OplewniaIndividuum();
			ind.setWeights(Utilities.genRndWeights(rnd));
			ind.setThresholds(Utilities.genRndThresholds(rnd));
			ind.setResult(runSimulationPipeline(ind));
			list.add(ind);
		}
		return list;
	}

	/**
	 * Run simulation pipeline.
	 * 
	 * @param ind
	 *            the ind
	 * @return the int
	 */
	private int runSimulationPipeline(OplewniaIndividuum ind) {
		simuPipeline = genSimuPipeline();
		int totalRounds = 0;
		for (Kybernetien kyber : simuPipeline) {
			kyber.bewerteEineStrategie(ind);
			totalRounds += kyber.getRundenzahl();
		}
		totalRounds = totalRounds / simuPipeline.size();
		return totalRounds;
	}

	/**
	 * Gen simu pipeline.
	 * 
	 * @return the list
	 */
	public List<Kybernetien> genSimuPipeline() {
		List<Kybernetien> simuPipeline;
		simuPipeline = spg.getSimuPipeline();
		// List<Kybernetien> simuPipeline = new ArrayList<Kybernetien>();
		// simuPipeline.add(new Kybernetien(8, 1, 12, 13, 4, 10, 20, 21, 0));
		// // simuPipeline.add(new Kybernetien(2, 2, 6, 13, 3, 12, 14, 21, 6));
		// // simuPipeline.add(new Kybernetien(2, 4, 7, 6, 6, 7, 16, 15, 5));
		// // simuPipeline.add(new Kybernetien(3, 4, 7, 6, 6, 4, 12, 15, 6));
		// // simuPipeline.add(new Kybernetien(10, 6, 10, 8, 10, 8, 13, 22, 3));
		// // simuPipeline.add(new Kybernetien(12, 5, 10, 9, 10, 7, 13, 20, 3));
		return simuPipeline;
	}

	/**
	 * Mutate and rate population.
	 * 
	 * @param startPopulation
	 *            the start population
	 * @param weightsSParams
	 *            the weights s params
	 * @param thresholdsSParams
	 *            the thresholds s params
	 * @return the list
	 */
	private List<OplewniaIndividuum> mutateAndRatePopulation(
			List<OplewniaIndividuum> startPopulation,
			double[][] weightsSParams, double[][] thresholdsSParams) {
		List<OplewniaIndividuum> newPop = new ArrayList<OplewniaIndividuum>();
		for (OplewniaIndividuum ind : startPopulation) {
			OplewniaIndividuum newInd = new OplewniaIndividuum();
			SelfAdaptEpMutator m = new SelfAdaptEpMutator(ind.getWeights(),
					ind.getThresholds(), weightsSParams, thresholdsSParams);
			m.runMutation();
			newInd.setWeights(m.getWeights());
			newInd.setThresholds(m.getThresholds());
			weightsSParams = m.getWeightsStratParam();
			thresholdsSParams = m.getTresholdsStratParam();
			int result = runSimulationPipeline(newInd);
			newInd.setResult(result);
			if (result > maxRounds) {
				maxRounds = result;
				logger.info("## BEST :" + maxRounds + " - Generation :"
						+ generation);
			}
			if (result == TERMINATION_ROUND) {
				writeToFile(newInd);
				logger.info("### ALGO END");
				termination = true;
			}
			newPop.add(newInd);
		}
		return newPop;
	}

	/**
	 * Write to file.
	 * 
	 * @param ind
	 *            the ind
	 */
	private void writeToFile(OplewniaIndividuum ind) {
		try {
			logger.info("## OUTPUT TO FILE");
			filename = timestamp + "_oplewnia" + ".ser";
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(ind);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * Gets the layer.
	 * 
	 * @return the layer
	 */
	public static int getLayer() {
		return LAYER;
	}

	/**
	 * Gets the alpha.
	 * 
	 * @return the alpha
	 */
	public static double getAlpha() {
		return ALPHA;
	}

	/**
	 * Gets the epsilon.
	 * 
	 * @return the epsilon
	 */
	public static double getEpsilon() {
		return EPSILON;
	}

	/**
	 * Gets the neurons.
	 * 
	 * @return the neurons
	 */
	public static int getNeurons() {
		return NEURONS;
	}
}