package util;

import java.io.Serializable;

public class SimuPipeline implements Serializable, Cloneable {

	private static final long serialVersionUID = -6565312862296033853L;
	private int[][] matrix;

	public SimuPipeline() {
	}

	@Override
	public SimuPipeline clone() {
		try {
			return (SimuPipeline) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

	public int[][] getSimuPipeline() {
		int[][] m = matrix.clone();
		return m;
	}

	public void setSimuPipeline(int[][] matrix) {
		int[][] m = matrix.clone();
		this.matrix = m;
	}
}
