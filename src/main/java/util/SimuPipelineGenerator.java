package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okoelopoly.Kybernetien;

import org.apache.log4j.Logger;

public class SimuPipelineGenerator {

	// private static int EPSILON = 2;
	// private static final int START_VARIABLES = 9;
	private static int pipelineSize = 100;
	private static final String filename = "simuPipeline.dat";
	private boolean isLoaded;
	private int[][] matrix;
	private Logger logger = Logger.getLogger(SimuPipelineGenerator.class);

	public SimuPipelineGenerator() {
		logger.info("### START PIPELINEGENERATOR");
		isLoaded = false;
	}

	public void genNewSimuPipeline() {
		logger.info("# GENERATE NEW PIPELINE");
		// matrix = new int[pipelineSize][START_VARIABLES];
		// matrix[0] = new int[] { 8, 1, 12, 13, 4, 10, 20, 21, 0 };
		// // matrix[1] = new int[] { 2, 2, 6, 13, 3, 12, 14, 21, 6 };
		// matrix[1] = new int[] { 2, 4, 7, 6, 6, 7, 16, 15, 5 };
		// // matrix[3] = new int[] { 3, 4, 7, 6, 6, 4, 12, 15, 6 };
		// // matrix[4] = new int[] { 10, 6, 10, 8, 10, 8, 13, 22, 3 };
		// // matrix[5] = new int[] { 12, 5, 10, 9, 10, 7, 13, 20, 3 };

		matrix = generator(pipelineSize);

		savePipeline();
	}

	private static List<Kybernetien> convert(int[][] js) {
		List<Kybernetien> list = new ArrayList<Kybernetien>();
		for (int i = 0; i < pipelineSize; i++) {
			list.add(new Kybernetien(js[i][0], js[i][1], js[i][2], js[i][3],
					js[i][4], js[i][5], js[i][6], js[i][7], js[i][8]));
		}
		return list;
	}

	public List<Kybernetien> getSimuPipeline() {
		if (!isLoaded) {
			loadPipeline();
		}
		return convert(matrix);
	}

	private void savePipeline() {
		logger.info("# SAVE PIPELINE TO FILE");
		SimuPipeline sp = new SimuPipeline();
		sp.setSimuPipeline(matrix);
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(sp);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	private void loadPipeline() {
		logger.info("# LOAD PIPELINE FROM FILE");
		SimuPipeline sp = new SimuPipeline();
		try {
			FileInputStream fileInStream = new FileInputStream(filename);
			ObjectInputStream objInStream = new ObjectInputStream(fileInStream);
			sp = (SimuPipeline) objInStream.readObject();
			objInStream.close();
			fileInStream.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		}
		matrix = sp.getSimuPipeline();
		Utilities.prettyPrint(matrix);
		logger.info("# FOUND " + matrix.length + " PIPELINE(s)");
		isLoaded = true;
	}

	public static int[][] generator(int x) {
		Random rnd = new Random();
		int[] us = { 5, 1, 5, 5, 1, 5, 8, 10, -5 };
		int[] os = { 12, 15, 25, 25, 20, 20, 22, 25, 20 };
		int epsilon = 1;
		int count = x;
		int[][] matrix = new int[count][us.length];

		int p1[] = genVal(us[0], os[0], epsilon);
		int p2[] = genVal(us[1], os[1], epsilon);
		int p3[] = genVal(us[2], os[2], epsilon);
		int p4[] = genVal(us[3], os[3], epsilon);
		int p5[] = genVal(us[4], os[4], epsilon);
		int p6[] = genVal(us[5], os[5], epsilon);
		int p7[] = genVal(us[6], os[6], epsilon);
		int p8[] = genVal(us[7], os[7], epsilon);
		int p9[] = genVal(us[8], os[8], epsilon);

		for (int i = 0; i < count; i++) {
			matrix[i] = new int[] {
					p1[(int) (((p1.length - 1) * Utilities.genGaussian(rnd)))],
					p2[(int) (((p2.length - 1) * Utilities.genGaussian(rnd)))],
					p3[(int) (((p3.length - 1) * Utilities.genGaussian(rnd)))],
					p4[(int) (((p4.length - 1) * Utilities.genGaussian(rnd)))],
					p5[(int) (((p5.length - 1) * Utilities.genGaussian(rnd)))],
					p6[(int) (((p6.length - 1) * Utilities.genGaussian(rnd)))],
					p7[(int) (((p7.length - 1) * Utilities.genGaussian(rnd)))],
					p8[(int) (((p8.length - 1) * Utilities.genGaussian(rnd)))],
					p9[(int) (((p9.length - 1) * Utilities.genGaussian(rnd)))] };
		}
		return matrix;
	}

	public static int[] genVal(int start, int end, int epsilon) {
		int[] p = new int[(end - start)];
		for (int i = 0; i < (end - start); i++) {
			p[i] = start + i;
		}
		return p;
	}

}
