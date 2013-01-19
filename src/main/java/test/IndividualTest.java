package test;

import java.util.List;

import loesung.OplewniaIndividuum;
import okoelopoly.Kybernetien;

import org.junit.Test;

import util.SimuPipelineGenerator;
import util.Utilities;

public class IndividualTest {

	@Test
	public void test() {
		int testsize = 5000;
		SimuPipelineGenerator spg = new SimuPipelineGenerator(testsize, false);
		spg.genNewSimuPipeline();

		OplewniaIndividuum ind = Utilities
				.loadIndividual("/home/olitologie/Workspace/ae_project3/best/30.0_2013-01-19_12:27:08.425.ser");

		List<Kybernetien> simuPipeline = spg.getSimuPipeline();
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
