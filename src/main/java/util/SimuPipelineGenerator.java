package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import okoelopoly.Kybernetien;

import org.apache.log4j.Logger;

public class SimuPipelineGenerator {

	public static int EPSILON = 2;
	private static String filename = "simuPipeline.dat";
	private boolean isLoaded;
	int[][] matrix;
	private static Logger logger = Logger
			.getLogger(SimuPipelineGenerator.class);

	public SimuPipelineGenerator() {
		logger.info("### START PIPELINEGENERATOR");
		isLoaded = false;
	}

	public void genNewSimuPipeline() {
		logger.info("# GENERATE NEW PIPELINE");
		List<Kybernetien> pipeline = new ArrayList<Kybernetien>();
		pipeline.add(new Kybernetien(8, 1, 12, 13, 4, 10, 20, 21, 0));
		pipeline.add(new Kybernetien(2, 2, 6, 13, 3, 12, 14, 21, 6));
		pipeline.add(new Kybernetien(2, 4, 7, 6, 6, 7, 16, 15, 5));
		pipeline.add(new Kybernetien(3, 4, 7, 6, 6, 4, 12, 15, 6));
		pipeline.add(new Kybernetien(10, 6, 10, 8, 10, 8, 13, 22, 3));
		pipeline.add(new Kybernetien(12, 5, 10, 9, 10, 7, 13, 20, 3));

		matrix = new int[pipeline.size()][9];
		matrix[0] = new int[] { 8, 1, 12, 13, 4, 10, 20, 21, 0 };
		matrix[1] = new int[] { 2, 2, 6, 13, 3, 12, 14, 21, 6 };
		matrix[2] = new int[] { 2, 4, 7, 6, 6, 7, 16, 15, 5 };
		matrix[3] = new int[] { 3, 4, 7, 6, 6, 4, 12, 15, 6 };
		matrix[4] = new int[] { 10, 6, 10, 8, 10, 8, 13, 22, 3 };
		matrix[5] = new int[] { 12, 5, 10, 9, 10, 7, 13, 20, 3 };

		savePipeline();
	}

	private List<Kybernetien> convert(int[][] js) {
		List<Kybernetien> list = new ArrayList<Kybernetien>();
		for (int i = 0; i < js.length; i++) {
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

	public void savePipeline() {
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

	public void loadPipeline() {
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
		logger.info("# FOUND " + matrix.length + " PIPELINE(s)");
		isLoaded = true;
	}
}
