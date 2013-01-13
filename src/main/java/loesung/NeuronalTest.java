package loesung;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import okoelopoly.Kybernetien;

import org.apache.log4j.Logger;

import ea.EvolutionaryAlgorithm;

public class NeuronalTest {

    private static Logger logger = Logger.getLogger(NeuronalTest.class);

    public static void main(String[] args) {
        EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm();
        ea.startWithTournierSelection();
    }

    public void deserialize() {
        SnuckIndividuum deserial;
        try
        {
            FileInputStream fileIn =
                    new FileInputStream("best.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            deserial = (SnuckIndividuum) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i)
        {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c)
        {
            System.out.println("MeinIndividuum class not found");
            c.printStackTrace();
            return;
        }

        // Wieder bewerten
        logger.info("Wird sie wieder gleich bewertet?");
        Kybernetien sim2 = new Kybernetien(8, 1, 12, 13, 4, 10, 20, 21, 0);
        sim2.bewerteEineStrategie(deserial);
        printResult(sim2);
    }

    private static void printResult(Kybernetien sim) {
        logger.info("\n\n#### Auswertung #### \nRundenzahl: " + sim.getRundenzahl());
        logger.info("Sanierung: " + sim.getSanierung());
        logger.info("Produktion: " + sim.getProduktion());
        logger.info("Umweltbelastung: " + sim.getUmweltbelastung());
        logger.info("Aufklaerung: " + sim.getAufklaerung());
        logger.info("Lebensqualitaet: " + sim.getLebensqualitaet());
        logger.info("Vermehrungsrate: " + sim.getVermehrungsrate());
        logger.info("Bevoelkerung: " + sim.getBevoelkerung());
        logger.info("Politik: " + sim.getPolitik());
        logger.info("Bilanz: " + sim.getGesamtbilanz());
    }
}