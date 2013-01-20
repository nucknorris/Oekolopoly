/*
 * HTWK Leipzig
 * Evolutionaere Algorithmen 
 * Projekt Aufgabe Drei
 * @author Oliver Plewnia
 */
package util;

import java.io.Serializable;

public class SimuPipeline implements Serializable, Cloneable {

	private static final long serialVersionUID = -6565312862296033853L;
	private int[][] matrix;

	/**
	 * Instantiates a new simu pipeline.
	 */
	public SimuPipeline() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SimuPipeline clone() {
		try {
			return (SimuPipeline) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

	/**
	 * Gets the simu pipeline.
	 * 
	 * @return the simu pipeline
	 */
	public int[][] getSimuPipeline() {
		int[][] m = matrix.clone();
		return m;
	}

	/**
	 * Sets the simu pipeline.
	 * 
	 * @param matrix
	 *            the new simu pipeline
	 */
	public void setSimuPipeline(int[][] matrix) {
		int[][] m = matrix.clone();
		this.matrix = m;
	}
}
