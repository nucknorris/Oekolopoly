package ea;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import loesung.SnuckIndividuum;
import okoelopoly.Kybernetien;

import org.apache.log4j.Logger;

public class EvolutionaryAlgorithm {

    private static final int TERMINATION_CONDITION = 300;
    private static final int POPULATION_SIZE = 10;
    private Kybernetien sim;
    private SnuckIndividuum currentInd;
    private int result;
    boolean isTerminated = false;
    boolean alreadyWritten = false;
    String timestamp = new Timestamp(new Date().getTime()).toString().replaceAll("\\s", "");
    int bestResult;
    int bestBilance;
    double[][] weightsSParams;
    double[][] thresholdsSParams;
    private static Logger logger = Logger.getLogger(EvolutionaryAlgorithm.class);
    private SnuckIndividuum bestInd;
    int iteration = 0;
    int temp = 0;

    public void start() {
        logger.info("start");
        currentInd = new SnuckIndividuum();
        double[][] weights = Util.generateRandomWeights();
        double[][] thresholds = Util.generateRandomThresholds();
        double[][] weightsSParams = Util.generateRandomWeightStrategyParameters();
        double[][] thresholdsSParams = Util.generateRandomThresholdStrategyParameters();
        currentInd.setWeights(weights);
        currentInd.setThresholds(thresholds);
        result = runLifecycle(currentInd);
        printResult();
        int bestResult = result;
        int round = 0;

        while (!isTerminated) {
            if (round++ % 1000 == 1) {
                logger.info("\n" + round);
                logger.info(Arrays.deepToString(weights));
                logger.info(Arrays.deepToString(thresholds));
                logger.info(Arrays.deepToString(weightsSParams));
                logger.info(Arrays.deepToString(thresholdsSParams));
                logger.info("rounds: " + sim.getRundenzahl());
                logger.info("bilanz: " + sim.getGesamtbilanz() + "\n********\n");
            }
            List<SnuckIndividuum> listOfInd = new ArrayList<SnuckIndividuum>();
            for (int i = 0; i < 100; i++) {
                Mutator m = new Mutator(weights, thresholds, weightsSParams,
                        thresholdsSParams);
                SnuckIndividuum ind = new SnuckIndividuum();
                m.runSelfAdaptiveEpMutation();
                ind.setWeights(m.getWeights());
                ind.setThresholds(m.getThresholds());
                listOfInd.add(ind);
            }

            for (SnuckIndividuum ind : listOfInd) {
                int interimResult = runLifecycle(ind);
                if (interimResult > bestResult) {
                    bestResult = interimResult;
                    currentInd = ind;
                    logger.info("\n\nbetter result found!: " + bestResult + ", rounds: "
                            + sim.getRundenzahl());
                    if (sim.getRundenzahl() > 10) {
                        writeToFile(ind, sim.getRundenzahl(), sim.getGesamtbilanz());
                        logger.info(Arrays.deepToString(weights));
                        logger.info(Arrays.deepToString(thresholds));
                        logger.info(Arrays.deepToString(weightsSParams));
                        logger.info(Arrays.deepToString(thresholdsSParams) + "\n");
                        printResult();
                    }
                }
            }
        }
    }

    public void startWithTournierSelection() {

        logger.info("\nstart");
        List<SnuckIndividuum> currentPop = new ArrayList<SnuckIndividuum>();
        weightsSParams = Util.generateRandomWeightStrategyParameters();
        thresholdsSParams = Util.generateRandomThresholdStrategyParameters();
        currentPop = populateStartPopulation(currentPop);

        while (!isTerminated) {
            if (iteration % 10000 == 1) {
                logger.info("iteration: " + iteration);
                logger.info("bestind: ");
                logger.info(Arrays.deepToString(bestInd.getWeights()));
                logger.info(Arrays.deepToString(bestInd.getThresholds()));
                logger.info("rounds: " + bestResult);

                logger.info("currend ind: ");
                logger.info("rounds: " + currentInd.getResult());
                logger.info(Arrays.deepToString(currentInd.getWeights()));
                logger.info(Arrays.deepToString(currentInd.getThresholds()) +
                        "\n\n");
            }
            List<SnuckIndividuum> newPop = mutatePopulation(currentPop, weightsSParams,
                    thresholdsSParams);
            currentPop = Selector.selectBest(currentPop, newPop, POPULATION_SIZE);
            iteration++;
        }

    }

    private List<SnuckIndividuum> populateStartPopulation(
            List<SnuckIndividuum> startPopulation) {
        List<SnuckIndividuum> list = new ArrayList<SnuckIndividuum>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            SnuckIndividuum ind = new SnuckIndividuum();
            ind.setWeights(Util.generateRandomWeights());
            ind.setThresholds(Util.generateRandomThresholds());
            ind.setResult(runLifecycle(ind));
            list.add(ind);
        }
        return list;
    }

    private int runLifecycle(SnuckIndividuum ind) {
        // TODO remove
        currentInd = ind.clone();

        sim = new Kybernetien(8, 1, 12, 13, 4, 10, 20, 21, 0);
        sim.bewerteEineStrategie(ind);
        if (sim.getRundenzahl() >= TERMINATION_CONDITION) {
            logger.info("TERMINATION");
            printResult();
            isTerminated = true;
        }
        return sim.getRundenzahl();
    }

    private List<SnuckIndividuum> mutatePopulation(List<SnuckIndividuum> startPopulation,
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
            if (result > bestResult) {
                bestResult = result;
                bestInd = newInd;
                logger.info("better result found!: " + bestResult + ", rounds: "
                        + sim.getRundenzahl() + ", iteration: " + iteration);
                if (sim.getRundenzahl() >= 30) {
                    writeToFile(ind, sim.getRundenzahl(), sim.getGesamtbilanz());
                    logger.info(Arrays.deepToString(m.getWeights()));
                    logger.info(Arrays.deepToString(m.getThresholds()));
                    logger.info(Arrays.deepToString(weightsSParams));
                    logger.info(Arrays.deepToString(thresholdsSParams) + "\n");
                    printResult();
                }
            }
            newPop.add(newInd);
        }
        return newPop;
    }

    private void writeToFile(SnuckIndividuum ind, int rounds, double bilanz) {
        try
        {
            FileOutputStream fileOut =
                    new FileOutputStream(rounds + "_" + bilanz + "_(" + timestamp + ").ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(ind);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    public void printResult() {
        logger.info("\n#### Auswertung #### \nRundenzahl: " + this.sim.getRundenzahl());
        logger.info("Sanierung: " + this.sim.getSanierung());
        logger.info("Produktion: " + this.sim.getProduktion());
        logger.info("Umweltbelastung: " + this.sim.getUmweltbelastung());
        logger.info("Aufklaerung: " + this.sim.getAufklaerung());
        logger.info("Lebensqualitaet: " + this.sim.getLebensqualitaet());
        logger.info("Vermehrungsrate: " + this.sim.getVermehrungsrate());
        logger.info("Bevoelkerung: " + this.sim.getBevoelkerung());
        logger.info("Politik: " + this.sim.getPolitik());
        logger.info("Bilanz: " + this.sim.getGesamtbilanz());
    }

    public SnuckIndividuum getStartInd() {
        return currentInd;
    }

    public double getResult() {
        return result;
    }

    public double getRundenzahl() {
        return this.sim.getRundenzahl();
    }

}