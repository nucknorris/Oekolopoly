package loesung;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import okoelopoly.Kybernetien;

import org.apache.log4j.Logger;

public class Testprogramm {
    private static Logger logger = Logger.getLogger(Testprogramm.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        Kybernetien sim = new Kybernetien(8, 1, 12, 13, 4, 10, 20, 21, 0);
        // SnuckIndividuumNeuronal ind = new SnuckIndividuumNeuronal();
        // Oplewnia ind = new Oplewnia(0.1, 0.1, 0.1, 0.2, 0.1);
        MeinIndividuum ind = new MeinIndividuum(0.1, 0.1, 0.1, 0.1, 0.1);
        sim.bewerteEineStrategie(ind);
        printResult(sim);

        // Serialisiere
        logger.info("Strategie schreiben ...");
        try
        {
            FileOutputStream fileOut =
                    new FileOutputStream("meinlogin.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(ind);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }

        // Wieder einlesen
        logger.info("Strategie wieder einlesen ...");
        MeinIndividuum deserial = null;
        try
        {
            FileInputStream fileIn =
                    new FileInputStream("meinlogin.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            deserial = (MeinIndividuum) in.readObject();
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
