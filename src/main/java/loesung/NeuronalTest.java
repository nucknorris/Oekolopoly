package loesung;

import okoelopoly.Kybernetien;

public class NeuronalTest {
    public static void main(String[] args) {
        SnuckIndividuumNeuronal ind = new SnuckIndividuumNeuronal();
        Kybernetien sim = new Kybernetien(8, 1, 12, 13, 4, 10, 20, 21, 0);
        double weights[][] = new double[3][10];
        double thresholds[][] = new double[3][10];

        weights[0][0] = 23;
        weights[0][1] = 654;
        weights[0][2] = 2;
        weights[0][3] = 43;
        weights[0][4] = 3;
        weights[0][5] = 3434;
        weights[0][6] = 54;
        weights[0][7] = 342;
        weights[0][8] = 43;

        weights[1][0] = 23;
        weights[1][1] = 654;
        weights[1][2] = 2;
        weights[1][3] = 43;
        weights[1][4] = 3;
        weights[1][5] = 3434;
        weights[1][6] = 54;
        weights[1][7] = 342;
        weights[1][8] = 43;
        weights[1][9] = 43;

        weights[2][0] = 23;
        weights[2][1] = 654;
        weights[2][2] = 2;
        weights[2][3] = 43;
        weights[2][4] = 3;

        thresholds[1][0] = 23;
        thresholds[1][1] = 654;
        thresholds[1][2] = 2;
        thresholds[1][3] = 43;
        thresholds[1][4] = 3;
        thresholds[1][5] = 3434;
        thresholds[1][6] = 54;
        thresholds[1][7] = 342;
        thresholds[1][8] = 43;
        thresholds[1][9] = 43;

        thresholds[2][0] = 23;
        thresholds[2][1] = 654;
        thresholds[2][2] = 2;
        thresholds[2][3] = 43;
        thresholds[2][4] = 3;

        ind.setWeights(weights);
        ind.setThresholds(thresholds);

        sim.bewerteEineStrategie(ind);
        printResult(sim);
    }

    private static void printResult(Kybernetien sim) {
        System.out.println("\n\n#### Auswertung #### \nRundenzahl: " + sim.getRundenzahl());
        System.out.println("Sanierung: " + sim.getSanierung());
        System.out.println("Produktion: " + sim.getProduktion());
        System.out.println("Umweltbelastung: " + sim.getUmweltbelastung());
        System.out.println("Aufklaerung: " + sim.getAufklaerung());
        System.out.println("Lebensqualitaet: " + sim.getLebensqualitaet());
        System.out.println("Vermehrungsrate: " + sim.getVermehrungsrate());
        System.out.println("Bevoelkerung: " + sim.getBevoelkerung());
        System.out.println("Politik: " + sim.getPolitik());
        System.out.println("Bilanz: " + sim.getGesamtbilanz());
    }
}