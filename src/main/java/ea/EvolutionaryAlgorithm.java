package ea;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import loesung.SnuckIndividuum;
import okoelopoly.Kybernetien;

import org.apache.log4j.Logger;

public class EvolutionaryAlgorithm {

    private static Logger logger = Logger.getLogger(EvolutionaryAlgorithm.class);
    private static final String timestamp = new Timestamp(new Date().getTime()).toString()
            .replaceAll("\\s", "");

    private static final int TERMINATION_CONDITION = 30;
    private static final int POPULATION_SIZE = 10;
    private static final int MAX_ITERATIONS = 5000;
    private static final int TERMINATION_LEVEL = 26;

    private int bestRounds; // stores the currently best number of rounds
    boolean isTerminated = false;

    int iteration = 0;
    private String filename;
    private List<Kybernetien> listOfKybernetien;
    private Random random;

    public EvolutionaryAlgorithm() {
        random = new Random();
    }

    /**
     * Starts the algorithm and returns the filename for further purposes.
     * 
     * @return
     */
    public String run() {
        double[][] weightsSParams; // strategic parameters for weights
        double[][] thresholdsSParams; // strategic parameters for thresholds
        List<SnuckIndividuum> currentPop = new ArrayList<SnuckIndividuum>();

        // filling the strategy parameters with start values
        weightsSParams = Util.generateStartWeightStrategyParameters();
        thresholdsSParams = Util.generateStartThresholdStrategyParameters();

        // populating the start population with random values
        currentPop = populateStartPopulation(currentPop);

        logger.info("\nstart");

        while (!isTerminated) {
            if (iteration % 1000 == 0) {

                // if the max number of iterations is reached or still is no
                // higher numer of rounds reached, the EA will reset its start
                // values.
                if (iteration > MAX_ITERATIONS && bestRounds < TERMINATION_LEVEL) {
                    logger.info("NO BEST RESULT FOUND, RESTARTING (" + bestRounds + ")");
                    weightsSParams = Util.generateRandomWeights(random);
                    thresholdsSParams = Util.generateRandomThresholds(random);
                    currentPop.clear();
                    currentPop = populateStartPopulation(currentPop);
                    iteration = 0;
                    random = new Random();
                }

                logger.info("STILL WORKING ... iteration: " + iteration + "(" + bestRounds + ")");
            }

            // the new population, consisting of the mutated individuals of the
            // start population
            List<SnuckIndividuum> newPop = mutateAndAssessPopulation(currentPop, weightsSParams,
                    thresholdsSParams);

            // the selector will clone the start population and add it to the
            // new population. afterwards it will select the best elements to be
            // the new start/current population.
            currentPop = Selector.mergeAndSelectBest(currentPop, newPop, POPULATION_SIZE);
            iteration++;
        }
        return filename;

    }

    /**
     * Creates a list containing the start population of indiviuals. Those will
     * be randomly filled.
     * 
     * @param startPopulation
     * @return
     */
    private List<SnuckIndividuum> populateStartPopulation(
            List<SnuckIndividuum> startPopulation) {
        List<SnuckIndividuum> list = new ArrayList<SnuckIndividuum>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            SnuckIndividuum ind = new SnuckIndividuum();
            ind.setWeights(Util.generateRandomWeights(random));
            ind.setThresholds(Util.generateRandomThresholds(random));
            ind.setResult(runLifecycle(ind));
            list.add(ind);
        }
        return list;
    }

    /**
     * Runs a full lifecycle. It will asses the value of the given indiviudal
     * using multiple instances of Kybernetien. More precisely the number of
     * rounds will be summed and an average will be calculated.
     * 
     * @param ind
     * @return
     */
    private int runLifecycle(SnuckIndividuum ind) {
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
     * Returns a list with multiple instances of Kybernetien.
     * 
     * @return
     */
    public List<Kybernetien> genListOfKypernetien() {
        List<Kybernetien> listOfKybernetien = new ArrayList<Kybernetien>();
        listOfKybernetien.add(new Kybernetien(8, 1, 12, 13, 4, 10, 20, 21, 0));
        // listOfKybernetien.add(new Kybernetien(2, 2, 6, 13, 3, 12, 14, 21,
        // 6));
        // listOfKybernetien.add(new Kybernetien(2, 4, 7, 6, 6, 7, 16, 15, 5));
        // listOfKybernetien.add(new Kybernetien(3, 4, 7, 6, 6, 4, 12, 15, 6));
        // listOfKybernetien.add(new Kybernetien(10, 6, 10, 8, 10, 8, 13, 22,
        // 3));
        // listOfKybernetien.add(new Kybernetien(12, 5, 10, 9, 10, 7, 13, 20,
        // 3));
        return listOfKybernetien;
    }

    /**
     * First mutates the population. Afterwards it will assess each individual
     * and terminate the programm in case of accessing the termination
     * condition.
     * 
     * @param startPopulation
     * @param weightsSParams
     * @param thresholdsSParams
     * @return
     */
    private List<SnuckIndividuum> mutateAndAssessPopulation(List<SnuckIndividuum> startPopulation,
            double[][] weightsSParams, double[][] thresholdsSParams) {
        List<SnuckIndividuum> newPop = new ArrayList<SnuckIndividuum>();
        for (SnuckIndividuum ind : startPopulation) {
            SnuckIndividuum newInd = new SnuckIndividuum();
            Mutator m = new Mutator(ind.getWeights(), ind.getThresholds(), weightsSParams,
                    thresholdsSParams);
            m.runSelfAdaptiveEpMutation();
            newInd.setWeights(m.getWeights());
            newInd.setThresholds(m.getThresholds());
            weightsSParams = m.getWeightsStrategyParams();
            thresholdsSParams = m.getTresholdsStrategyParams();
            int result = runLifecycle(newInd);
            newInd.setResult(result);
            if (result > bestRounds) {
                bestRounds = result;
                logger.info("new best round found: " + bestRounds);
            }
            if (result == TERMINATION_CONDITION) {
                writeToFile(newInd);
                logger.info("TERMINATION");
                isTerminated = true;
            }
            newPop.add(newInd);
        }
        return newPop;
    }

    /**
     * Writes a given individual to a file.
     * 
     * @param ind
     */
    private void writeToFile(SnuckIndividuum ind) {
        try
        {
            logger.info("writing element with rounds: " + ind.getResult());
            // filename = rounds + "_" + bilance + "_("
            // + timestamp + ").ser";
            filename = ind.getResult() + timestamp + ".ser";
            FileOutputStream fileOut =
                    new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(ind);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    public void printResult(Kybernetien kybernetien) {
        logger.info("\n#### Auswertung #### \nRundenzahl: " + kybernetien.getRundenzahl());
        logger.info("Sanierung: " + kybernetien.getSanierung());
        logger.info("Produktion: " + kybernetien.getProduktion());
        logger.info("Umweltbelastung: " + kybernetien.getUmweltbelastung());
        logger.info("Aufklaerung: " + kybernetien.getAufklaerung());
        logger.info("Lebensqualitaet: " + kybernetien.getLebensqualitaet());
        logger.info("Vermehrungsrate: " + kybernetien.getVermehrungsrate());
        logger.info("Bevoelkerung: " + kybernetien.getBevoelkerung());
        logger.info("Politik: " + kybernetien.getPolitik());
        logger.info("Bilanz: " + kybernetien.getGesamtbilanz());
    }

    // public SnuckIndividuum getStartInd() {
    // return currentInd;
    // }

    // public double getResult() {
    // return result;
    // }

    // public double getRundenzahl() {
    // return this.sim.getRundenzahl();
    // }

}