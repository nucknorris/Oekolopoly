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

/**
 * The Class EvoAlg.
 */
public class EvoAlg {

	private static Logger logger = Logger.getLogger(EvoAlg.class);
	static final int TERMINATION_ROUND = 30;
	static final int POP_SIZE = 100;
	static final int MAX_GENERATION = 5000;
	static final int NO_TERMINATION_OVER = 20;
	public static final int LAYER = 7;
	public static final int NEURONS = 10;
	public static final double ALPHA = 0.1;
	public static final double EPSILON = 0.00001;

	private int bestRounds;
	boolean isTerminated = false;

	private int generation = 0;
	private String filename;
	private Random random;
	private SimuPipelineGenerator spg;

	public EvoAlg() {
		spg = new SimuPipelineGenerator();
		random = new Random();
	}

	public String run() {
		double[][] weightsSP;
		double[][] thresholdsSP;

		weightsSP = Utilities.genStartWeightSP();
		thresholdsSP = Utilities.genStartThresholdSP();
		List<OplewniaIndividuum> oldPop = populateStartPop();

		while (!isTerminated && generation < 10000) {
			if (generation % 1000 == 0) {
				if (generation > MAX_GENERATION
						&& bestRounds < NO_TERMINATION_OVER) {
					logger.info("## RESTARTING");
					weightsSP = Utilities.genStartWeightSP();
					thresholdsSP = Utilities.genStartThresholdSP();
					oldPop = populateStartPop();
					generation = 0;
					random = new Random();
					bestRounds = 0;
				}
				logger.info("## " + generation);
			}

			List<OplewniaIndividuum> newPop = mutatePopulation(oldPop,
					weightsSP, thresholdsSP);

			oldPop = BestSelection.selection(oldPop, newPop, POP_SIZE);
			generation++;
		}
		return filename;
	}

	private List<OplewniaIndividuum> populateStartPop() {
		List<OplewniaIndividuum> list = new ArrayList<OplewniaIndividuum>();
		for (int i = 0; i < POP_SIZE; i++) {
			OplewniaIndividuum ind = new OplewniaIndividuum();
			ind.setWeights(Utilities.genRndWeights(random));
			ind.setThresholds(Utilities.genRndThresholds(random));
			ind.setResult(runPipeline(ind));
			list.add(ind);
		}
		return list;
	}

	private int runPipeline(OplewniaIndividuum ind) {
		List<Kybernetien> simuPipeline = spg.getSimuPipeline();
		int totalRounds = 0;
		for (Kybernetien kyber : simuPipeline) {
			kyber.bewerteEineStrategie(ind);
			totalRounds += kyber.getRundenzahl();
		}
		totalRounds = totalRounds / simuPipeline.size();
		return totalRounds;
	}

	private List<OplewniaIndividuum> mutatePopulation(
			List<OplewniaIndividuum> startPopulation, double[][] weightsSP,
			double[][] thresholdsSP) {
		List<OplewniaIndividuum> newPop = new ArrayList<OplewniaIndividuum>();
		for (OplewniaIndividuum ind : startPopulation) {
			OplewniaIndividuum newInd = new OplewniaIndividuum();

			SAEPMutator m = new SAEPMutator(ind.getWeights(),
					ind.getThresholds(), weightsSP, thresholdsSP);
			m.runMutation();

			newInd.setWeights(m.getWeights());
			newInd.setThresholds(m.getThresholds());

			weightsSP = m.getWeightsSP();
			thresholdsSP = m.getTresholdsSP();

			int result = runPipeline(newInd);
			newInd.setResult(result);

			if (result > bestRounds) {
				bestRounds = result;
				writeToFile(newInd);
				logger.info("## NEW BEST: " + bestRounds);
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

	private void writeToFile(OplewniaIndividuum ind) {
		try {
			logger.info("## SAVE INDIVIDUAL TO FILE");
			String timestamp = new Timestamp(new Date().getTime()).toString()
					.replaceAll("\\s", "_");
			filename = ind.getResult() + "_" + timestamp + ".ser";
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(ind);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	public void printResult(Kybernetien kybernetien) {
		logger.info("##### Auswertung #####");
		logger.info("## Rundenzahl: " + kybernetien.getRundenzahl());
		logger.info("## Sanierung: " + kybernetien.getSanierung());
		logger.info("## Produktion: " + kybernetien.getProduktion());
		logger.info("## Umweltbelastung: " + kybernetien.getUmweltbelastung());
		logger.info("## Aufklaerung: " + kybernetien.getAufklaerung());
		logger.info("## Lebensqualitaet: " + kybernetien.getLebensqualitaet());
		logger.info("## Vermehrungsrate: " + kybernetien.getVermehrungsrate());
		logger.info("## Bevoelkerung: " + kybernetien.getBevoelkerung());
		logger.info("## Politik: " + kybernetien.getPolitik());
		logger.info("## Bilanz: " + kybernetien.getGesamtbilanz());
		logger.info("#####################");
	}

}
