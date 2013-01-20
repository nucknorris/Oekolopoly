/*
 * HTWK Leipzig
 * Evolutionaere Algorithmen 
 * Projekt Aufgabe Drei
 * @author Oliver Plewnia
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okoelopoly.Kybernetien;

import org.apache.log4j.Logger;

public class SimuPipelineGenerator {

	private int pipelineSize = 50;
	private boolean gauss = false;
	private static final String filename = "simuPipeline.dat";
	private boolean isLoaded;
	private int[][] matrix;
	private Logger logger = Logger.getLogger(SimuPipelineGenerator.class);

	/**
	 * Instantiates a new simu pipeline generator.
	 */
	public SimuPipelineGenerator() {
		logger.info("### START PIPELINEGENERATOR");
		isLoaded = false;
	}

	/**
	 * Instantiates a new simu pipeline generator.
	 * 
	 * @param i
	 *            the i
	 * @param b
	 *            the b
	 */
	public SimuPipelineGenerator(int i, boolean b) {
		logger.info("### START PIPELINEGENERATOR");
		this.gauss = b;
		this.isLoaded = false;
		this.pipelineSize = i;
	}

	/**
	 * Gen new simu pipeline.
	 */
	public void genNewSimuPipeline() {
		logger.info("# GENERATE NEW PIPELINE");
		matrix = generator(pipelineSize);
		saveArrayToFile(matrix);
		savePipeline();
	}

	/**
	 * Convert.
	 * 
	 * @param js
	 *            the js
	 * @return the list
	 */
	private List<Kybernetien> convert(int[][] js) {
		List<Kybernetien> list = new ArrayList<Kybernetien>();
		for (int i = 0; i < pipelineSize; i++) {
			list.add(new Kybernetien(js[i][0], js[i][1], js[i][2], js[i][3],
					js[i][4], js[i][5], js[i][6], js[i][7], js[i][8]));
		}
		return list;
	}

	/**
	 * Gets the simu pipeline.
	 * 
	 * @return the simu pipeline
	 */
	public List<Kybernetien> getSimuPipeline() {
		if (!isLoaded) {
			loadPipeline();
		}
		return convert(matrix);
	}

	/**
	 * Save pipeline.
	 */
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

	/**
	 * Load pipeline.
	 */
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

	/**
	 * Generator.
	 * 
	 * @param x
	 *            the x
	 * @return the int[][]
	 */
	public int[][] generator(int x) {
		Random rnd2 = new Random();
		MersenneTwisterFast rnd = new MersenneTwisterFast();
		// test 1
		// int[] us = { 5, 1, 5, 5, 1, 5, 8, 10, -5 };
		// int[] os = { 12, 15, 25, 25, 20, 20, 22, 25, 20 };
		// test 2
		// int[] us = { 6, 4, 8, 8, 4, 9, 12, 14, 0 };
		// int[] os = { 10, 11, 20, 20, 16, 17, 20, 20, 16 };
		// test 3
		// int[] us = { 5, 5, 9, 9, 5, 10, 13, 15, 2 };
		// int[] os = { 9, 10, 19, 19, 15, 16, 19, 19, 14 };
		// 8, 1, 12, 13, 4, 10, 20, 21, 0
		// best 1
		// int[] us = { 7, 1, 11, 11, 3, 9, 19, 20, 0 };
		// int[] os = { 9, 3, 13, 14, 5, 11, 21, 22, 2 };
		// best 2
		// int[] us = { 7, 1, 11, 11, 3, 9, 18, 19, 0 };
		// int[] os = { 10, 5, 14, 14, 6, 12, 21, 22, 3 };
		// test 3 +4
		int[] us = { 7, 1, 11, 11, 2, 9, 16, 17, 0 };
		int[] os = { 11, 6, 15, 15, 7, 14, 21, 22, 5 };
		// test 5
		// int[] us = { 5, 1, 5, 5, 1, 5, 8, 10, -5 };
		// int[] os = { 12, 15, 25, 25, 20, 20, 22, 35, 20 };
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
		if (gauss) {
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
		} else {
			for (int i = 0; i < count; i++) {
				matrix[i] = new int[] {
						p1[(int) (((p1.length - 1) * Utilities.genRandom(rnd2)))],
						p2[(int) (((p2.length - 1) * Utilities.genRandom(rnd2)))],
						p3[(int) (((p3.length - 1) * Utilities.genRandom(rnd2)))],
						p4[(int) (((p4.length - 1) * Utilities.genRandom(rnd2)))],
						p5[(int) (((p5.length - 1) * Utilities.genRandom(rnd2)))],
						p6[(int) (((p6.length - 1) * Utilities.genRandom(rnd2)))],
						p7[(int) (((p7.length - 1) * Utilities.genRandom(rnd2)))],
						p8[(int) (((p8.length - 1) * Utilities.genRandom(rnd2)))],
						p9[(int) (((p9.length - 1) * Utilities.genRandom(rnd2)))] };
			}
		}
		return matrix;
	}

	/**
	 * Gen val.
	 * 
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @param epsilon
	 *            the epsilon
	 * @return the int[]
	 */
	public static int[] genVal(int start, int end, int epsilon) {
		int[] p = new int[(end - start + 2)];
		for (int i = 0; i <= (end - start + 1); i++) {
			p[i] = start + i;
		}
		return p;
	}

	/**
	 * Load array from file.
	 * 
	 * @param file
	 *            the file
	 */
	public void loadArrayFromFile(String file) {
		logger.info("# LOAD PIPELINE FROM FILE");
		SimuPipeline sp = new SimuPipeline();
		try {
			FileInputStream fileInStream = new FileInputStream(file);
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

	/**
	 * Save array to file.
	 * 
	 * @param m
	 *            the m
	 */
	private void saveArrayToFile(int[][] m) {
		String timestamp = new Timestamp(new Date().getTime()).toString()
				.replaceAll("\\s", "_");
		File file = new File(timestamp + "_" + filename);
		try {
			FileOutputStream fop = new FileOutputStream(file);
			if (!file.exists()) {
				file.createNewFile();
			}
			for (int i = 0; i < m.length; i++) {
				for (int j = 0; j < m[i].length; j++) {
					fop.write((" " + m[i][j]).getBytes());
				}
				fop.write("\n".getBytes());
			}
			fop.flush();
			fop.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
