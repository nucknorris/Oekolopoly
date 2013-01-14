/*
 * HTWK Leipzig
 * Evolutionaere Algorithmen 
 * Projekt Aufgabe Drei
 * @author Oliver Plewnia
 * 
 */
package loesung;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import okoelopoly.Kybernetien;

import org.apache.log4j.Logger;
import org.junit.Test;

import evoalg.EvoAlg;

// TODO: Auto-generated Javadoc
/**
 * The Class NeuronalNetworkTest.
 */
public class NeuronalNetworkTest {

	/** The logger. */
	private static Logger logger = Logger.getLogger(NeuronalNetworkTest.class);
	
	/** The filename. */
	static String filename;

	/**
	 * Start test.
	 */
	@Test
	public void startTest() {
		logger.info("### TEST THE INDIVIDUAL");
		EvoAlg ea = new EvoAlg();
		filename = ea.runEvoAlg();
		if (filename != null) {
			objectDeserialization();
		}
	}

	/**
	 * Object deserialization.
	 */
	public void objectDeserialization() {
		logger.info("## Deserial Ind. Object");
		OplewniaIndividuum deserialObject;
		List<Kybernetien> simuPipeline = genSimuPipeline();
		try {
			FileInputStream fileInStream = new FileInputStream(filename);
			ObjectInputStream objInStream = new ObjectInputStream(fileInStream);
			deserialObject = (OplewniaIndividuum) objInStream.readObject();
			objInStream.close();
			fileInStream.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("MeinIndividuum class not found");
			c.printStackTrace();
			return;
		}
		logger.info("### RUNNING TESTS SIMU PIPELINE ###");
		for (Kybernetien kybernetien : simuPipeline) {
			kybernetien.bewerteEineStrategie(deserialObject);
			printResult(kybernetien);
		}
		logger.info("### TEST SIMU PIPELINE END ###");
	}

	/**
	 * Prints the result.
	 *
	 * @param kybernetien the kybernetien
	 */
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

	/**
	 * Gen simu pipeline.
	 *
	 * @return the list
	 */
	public List<Kybernetien> genSimuPipeline() {
		List<Kybernetien> simuPipeline = new ArrayList<Kybernetien>();
		simuPipeline.add(new Kybernetien(8, 1, 12, 13, 4, 10, 20, 21, 0));
		// simuPipeline.add(new Kybernetien(2, 2, 6, 13, 3, 12, 14, 21, 6));
		simuPipeline.add(new Kybernetien(2, 4, 7, 6, 6, 7, 16, 15, 5));
		simuPipeline.add(new Kybernetien(3, 4, 7, 6, 6, 4, 12, 15, 6));
		simuPipeline.add(new Kybernetien(10, 6, 10, 8, 10, 8, 13, 22, 3));
		simuPipeline.add(new Kybernetien(12, 5, 10, 9, 10, 7, 13, 20, 3));
		return simuPipeline;
	}
}