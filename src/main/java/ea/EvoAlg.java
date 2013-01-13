package ea;

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

public class EvoAlg {

	private static Logger logger = Logger.getLogger(EvoAlg.class);
	private static final String timestamp = new Timestamp(new Date().getTime())
			.toString().replaceAll("\\s", "_");

	private static final int TERMINATION_ROUND = 30;
	private static final int POPULATION_SIZE = 100;
	private static final int MAX_GENERATION = 5000;
	private static final int NOT_TERMINATION_OVER = 24;
	private static final int LAYER = 4;
	private static final double ALPHA = 0.1;
	private static final double EPSILON = 0.00001;

	private int bestRounds;
	boolean isTerminated = false;

	int generation = 0;
	private String filename;
	private List<Kybernetien> listOfKybernetien;
	private Random random;

	public EvoAlg() {
		random = new Random();
	}

	/**
	 * Starts the Algorithm and return the Individual in a file
	 * 
	 * @return
	 */
	public String run() {
		double[][] weightsSParams; // strat parameters for weights
		double[][] thresholdsSParams; // strat parameters for thresholds
		List<OplewniaIndividuum> currentPop = new ArrayList<OplewniaIndividuum>();

		// filling the strategy parameters with start values
		weightsSParams = Util.genStartWeightStratParameters();
		thresholdsSParams = Util.genStartThresholdStratParameters();

		// populating the start population with random values
		currentPop = genStartPopulation(currentPop);

		logger.info("#### ALGO START");

		while (!isTerminated) {
			if (generation % 1000 == 0) {

				/*
				 * look while round 30 or max iteration reached
				 */
				if (generation > MAX_GENERATION
						&& bestRounds < NOT_TERMINATION_OVER) {
					logger.info("## ALGO RESTART AFTER" + generation);
					weightsSParams = Util.genRndWeights(random);
					thresholdsSParams = Util.genRndThresholds(random);
					currentPop.clear();
					currentPop = genStartPopulation(currentPop);
					generation = 0;
					random = new Random();
				}

				logger.info("## I am at Generation: " + generation);
			}

			// initial a list of all test cases
			List<OplewniaIndividuum> newPop = mutateAndRatePopulation(
					currentPop, weightsSParams, thresholdsSParams);

			currentPop = Selector.mergeAndSelectBest(currentPop, newPop,
					POPULATION_SIZE);
			generation++;
		}
		return filename;

	}

	/**
	 * Creates a start Population.
	 * 
	 * @param startPopulation
	 * @return
	 */
	private List<OplewniaIndividuum> genStartPopulation(
			List<OplewniaIndividuum> startPopulation) {
		List<OplewniaIndividuum> list = new ArrayList<OplewniaIndividuum>();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			OplewniaIndividuum ind = new OplewniaIndividuum();
			ind.setWeights(Util.genRndWeights(random));
			ind.setThresholds(Util.genRndThresholds(random));
			ind.setResult(runSimulationPipeline(ind));
			list.add(ind);
		}
		return list;
	}

	/**
	 * Runs the simulation pipeline.
	 * 
	 * @param ind
	 * @return
	 */
	private int runSimulationPipeline(OplewniaIndividuum ind) {
		listOfKybernetien = genListOfKypernetien();
		int totalRounds = 0;
		for (Kybernetien kybernetien : listOfKybernetien) {
			kybernetien.bewerteEineStrategie(ind);
			totalRounds += kybernetien.getRundenzahl();
		}
		totalRounds = totalRounds / listOfKybernetien.size();
		return totalRounds;
	}

	/**
	 * Return all test cases.
	 * 
	 * @return
	 */
	public List<Kybernetien> genListOfKypernetien() {
		List<Kybernetien> listOfKybernetien = new ArrayList<Kybernetien>();
		listOfKybernetien.add(new Kybernetien(8, 1, 12, 13, 4, 10, 20, 21, 0));
		// listOfKybernetien.add(new Kybernetien(2, 2, 6, 13, 3, 12, 14, 21,
		// 6));
		listOfKybernetien.add(new Kybernetien(2, 4, 7, 6, 6, 7, 16, 15, 5));
		listOfKybernetien.add(new Kybernetien(3, 4, 7, 6, 6, 4, 12, 15, 6));
		listOfKybernetien.add(new Kybernetien(10, 6, 10, 8, 10, 8, 13, 22, 3));
		listOfKybernetien.add(new Kybernetien(12, 5, 10, 9, 10, 7, 13, 20, 3));
		return listOfKybernetien;
	}

	/**
	 * Mutate und rate Population.
	 * 
	 * @param startPopulation
	 * @param weightsSParams
	 * @param thresholdsSParams
	 * @return
	 */
	private List<OplewniaIndividuum> mutateAndRatePopulation(
			List<OplewniaIndividuum> startPopulation,
			double[][] weightsSParams, double[][] thresholdsSParams) {
		List<OplewniaIndividuum> newPop = new ArrayList<OplewniaIndividuum>();
		for (OplewniaIndividuum ind : startPopulation) {
			OplewniaIndividuum newInd = new OplewniaIndividuum();
			Mutator m = new Mutator(ind.getWeights(), ind.getThresholds(),
					weightsSParams, thresholdsSParams);
			m.runMutation();
			newInd.setWeights(m.getWeights());
			newInd.setThresholds(m.getThresholds());
			weightsSParams = m.getWeightsStratParameters();
			thresholdsSParams = m.getTresholdsStratParameters();
			int result = runSimulationPipeline(newInd);
			newInd.setResult(result);
			if (result > bestRounds) {
				bestRounds = result;
				logger.info("## BEST :" + bestRounds);
			}
			if (result == TERMINATION_ROUND) {
				writeToFile(newInd);
				logger.info("### ALGO END");
				isTerminated = true;
			}
			newPop.add(newInd);
		}
		return newPop;
	}

	/**
	 * Writes individual to a file.
	 * 
	 * @param ind
	 */
	private void writeToFile(OplewniaIndividuum ind) {
		try {
			logger.info("## OUTPUT TO FILE");
			filename = ind.getResult() + timestamp + "_oplewnia" + ".ser";
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(ind);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	/*
	 * Return the number of all Neuron layer.
	 */
	public static int getLayer() {
		return LAYER;
	}

	public static double getAlpha() {
		return ALPHA;
	}

	public static double getEpsilon() {
		return EPSILON;
	}
}