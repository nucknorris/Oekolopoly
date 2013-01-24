/*
 * HTWK Leipzig
 * Evolutionaere Algorithmen 
 * Projekt Aufgabe Drei
 * @author Oliver Plewnia
 */
package test;

import java.util.List;

import loesung.OplewniaIndividuum;
import okoelopoly.Kybernetien;

import org.junit.Test;

import util.SimuPipelineGenerator;
import util.Utilities;

public class IndividualTest2 {

	/**
	 * Test.
	 */
	@Test
	public void test() {
		int testsize = 50000;
		SimuPipelineGenerator spg = new SimuPipelineGenerator(testsize, true);
		spg.genNewSimuPipeline();

		OplewniaIndividuum ind = Utilities
				.loadIndividual("/home/olitologie/Workspace/ae_project3/best/28.0_2013-01-20_18:49:05.289.ser");

		List<Kybernetien> simuPipeline = spg.getSimuPipeline();

		// List<Kybernetien> simuPipeline = new ArrayList<Kybernetien>();
		// simuPipeline.add(new Kybernetien(8, 1, 12, 13, 4, 10, 20, 21, 0));

		int totalRounds = 0;
		int totalResult = 0;
		System.out.println("################################");
		System.out.println("## TEST START :");
		for (Kybernetien kyber : simuPipeline) {
			kyber.bewerteEineStrategie(ind);
			totalRounds += kyber.getRundenzahl();
			totalResult += kyber.getGesamtbilanz();

			System.out.println("TEST: R:" + kyber.getRundenzahl() + " B:"
					+ kyber.getGesamtbilanz());
		}
		System.out.println("################################");
		System.out.println("## TEST END");
		System.out.println("## ROUNDS: " + (totalRounds / testsize)
				+ " RESULT: " + (totalResult / testsize));
	}
}
