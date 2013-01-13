package loesung;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import okoelopoly.Kybernetien;

import org.apache.log4j.Logger;
import org.junit.Test;

import ea.EvoAlg;

public class NeuronalTest {

	private static Logger logger = Logger.getLogger(NeuronalTest.class);
	static String filename;

	@Test
	public void startTest() {

		EvoAlg ea = new EvoAlg();
		filename = ea.run();
		logger.info("## Test Individual: " + filename);
		if (filename != null) {
			deserialize();
		}
	}

	public void deserialize() {
		// rate IT
		logger.info("### TEST THE INDIVIDUAL");

		logger.info("## ... deserial it");
		OplewniaIndividuum deserial;
		List<Kybernetien> listOfK = genListOfKypernetien();
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			deserial = (OplewniaIndividuum) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("MeinIndividuum class not found");
			c.printStackTrace();
			return;
		}

		logger.info("### RUNNING TESTS ###");

		for (Kybernetien kybernetien : listOfK) {
			kybernetien.bewerteEineStrategie(deserial);
			printResult(kybernetien);
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
}