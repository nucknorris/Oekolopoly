package loesung;

import org.apache.log4j.Logger;

import ea.EvolutionaryAlgorithm;

public class NeuronalTest {

    private static Logger logger = Logger.getLogger(NeuronalTest.class);

    public static void main(String[] args) {
        EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm();
        ea.start();
        ea.printResult();
    }

    public static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}