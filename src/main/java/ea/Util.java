package ea;

import java.util.Random;

public class Util {

	public static double[][] genRndWeights(Random random) {
		double weights[][] = new double[EvoAlg.getLayer()][10];

		for (int i = 0; i < EvoAlg.getLayer(); i++) {
			for (int j = 0; j < 10; j++) {
				weights[i][j] = random.nextGaussian();
			}
		}
		return weights;
	}

	public static double[][] genRndThresholds(Random random) {
		double thresholds[][] = new double[EvoAlg.getLayer()][10];

		for (int i = 1; i < EvoAlg.getLayer(); i++) {
			for (int j = 0; j < 10; j++) {
				thresholds[i][j] = random.nextGaussian();
			}
		}
		return thresholds;
	}

	public static double[][] genStartWeightStratParameters() {
		double strategyParams[][] = new double[EvoAlg.getLayer()][10];
		for (int i = 0; i < EvoAlg.getLayer(); i++) {
			for (int j = 0; j < 10; j++) {
				strategyParams[i][j] = 1;
			}
		}
		return strategyParams;
	}

	public static double[][] genStartThresholdStratParameters() {
		double strategyParams[][] = new double[EvoAlg.getLayer()][10];
		for (int i = 0; i < EvoAlg.getLayer(); i++) {
			for (int j = 0; j < 10; j++) {
				strategyParams[i][j] = 1;
			}
		}
		return strategyParams;
	}

}
