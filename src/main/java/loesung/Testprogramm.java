package loesung;

import okoelopoly.Kybernetien;
import java.io.*;

public class Testprogramm {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Kybernetien sim = new Kybernetien(8, 1, 12, 13, 4, 10, 20, 21, 0);
        MeinIndividuum ind = new MeinIndividuum(0.5, 0.5, 0.2, 0.25, 0.15);
        sim.bewerteEineStrategie(ind);
        printResult(sim);

        // Serialisiere
        System.out.println("Strategie schreiben ...");
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
        System.out.println("Strategie wieder einlesen ...");
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
        System.out.println("Wird sie wieder gleich bewertet?");
        Kybernetien sim2 = new Kybernetien(8, 1, 12, 13, 4, 10, 20, 21, 0);
        sim2.bewerteEineStrategie(deserial);
        printResult(sim2);
    }

    private static void printResult(Kybernetien sim) {
        System.out.println("Rundenzahl: " + sim.getRundenzahl());
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
