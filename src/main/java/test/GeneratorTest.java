package test;

import org.junit.Test;

import util.SimuPipelineGenerator;
import util.Utilities;

public class GeneratorTest {

	@Test
	public void test() {

		int matrix[][] = new int[3][3];
		matrix[0] = new int[] { 1, 2, 3 };
		matrix[1] = new int[] { 1, 2, 3 };
		matrix[2] = new int[] { 1, 2, 3 };
		Utilities.prettyPrint(matrix);
		int matrix2[][] = SimuPipelineGenerator.generator(100);
		Utilities.prettyPrint(matrix2);
	}

}
