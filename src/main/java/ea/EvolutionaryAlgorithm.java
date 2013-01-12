package ea;

import java.util.Arrays;

import loesung.SnuckIndividuumNeuronal;
import okoelopoly.Kybernetien;

import org.apache.log4j.Logger;

public class EvolutionaryAlgorithm {

    private Kybernetien sim;
    private SnuckIndividuumNeuronal startInd;

    private static Logger logger = Logger.getLogger(EvolutionaryAlgorithm.class);

    private double runLifecycle(SnuckIndividuumNeuronal ind, double weights[][],
            double thresholds[][]) {
        sim = new Kybernetien(8, 1, 12, 13, 4, 10, 20, 21, 0);
        ind.setWeights(weights);
        ind.setThresholds(thresholds);
        sim.bewerteEineStrategie(ind);
        return sim.getGesamtbilanz();
    }

    public void start() {
        startInd = new SnuckIndividuumNeuronal();
        double[][] weights = Util.generateRandomWeights();
        double[][] thresholds = Util.generateRandomThresholds();
        double[][] strategyParams = Util.generateRandomStrategyParameters();
        double result = runLifecycle(startInd, weights, thresholds);
        logger.info("result: " + result);
        logger.info("***** " + Arrays.deepToString(weights) + "\n***** "
                + Arrays.deepToString(thresholds));

        Mutator m = new Mutator(weights, thresholds, strategyParams);
        m.runSelfAdaptiveEpMutation();
        result = runLifecycle(startInd, m.getWeights(), m.getThresholds());
        logger.info("result: " + result);
        logger.info("***** " + Arrays.deepToString(weights) + "\n***** "
                + Arrays.deepToString(thresholds));

    }

    public void printResult() {
        logger.info("\n\n#### Auswertung #### \nRundenzahl: " + this.sim.getRundenzahl());
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

    public SnuckIndividuumNeuronal getStartInd() {
        return startInd;
    }

}