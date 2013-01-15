/*
 * HTWK Leipzig
 * Evolutionaere Algorithmen 
 * Projekt Aufgabe Drei
 * @author Oliver Plewnia
 * 
 */
package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import loesung.OplewniaIndividuum;
import okoelopoly.Kybernetien;

import org.apache.log4j.Logger;
import org.junit.Test;

import util.SimuPipelineGenerator;
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
	private SimuPipelineGenerator spg;

	/**
	 * Start test.
	 */
	@Test
	public void startTest() {
		logger.info("### TEST THE INDIVIDUAL");
		spg = new SimuPipelineGenerator();
		spg.genNewSimuPipeline();

		EvoAlg ea = new EvoAlg();
		filename = ea.run();
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
	 * @param kybernetien
	 *            the kybernetien
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
		List<Kybernetien> simuPipeline = spg.getSimuPipeline();
		return simuPipeline;
	}
}