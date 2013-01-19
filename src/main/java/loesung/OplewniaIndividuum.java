/*
 * HTWK Leipzig
 * Evolutionaere Algorithmen 
 * Projekt Aufgabe Drei
 * @author Oliver Plewnia
 * 
 */
package loesung;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okoelopoly.Individuum;
import okoelopoly.Punktverteilung;
import evoalg.EvoAlg;

/**
 * The Class OplewniaIndividuum.
 */
public class OplewniaIndividuum implements Individuum, Serializable, Cloneable,
		Comparable<OplewniaIndividuum> {

	private static final long serialVersionUID = 5211822430302842323L;
	private double weights[][];
	private double thresholds[][];
	private double lebq; // 0 Lebensqualitaet
	private double aufk; // 1 Aufklaerung
	private double prod; // 2 Produktion
	private double sani; // 3 Sanierung
	private double vemr; // 4 Vermehrungsrate
	private double uweb; // 5 Umweltbelastung
	private double bevo; // 6 Bevoelkerung
	private double poli; // 7 Politik
	private double aktp; // 8 Aktionspunkte
	private double lebqPart; // 0 Lebensqualitaet
	private double aufkPart; // 1 Aufklaerung
	private double prodPart; // 2 Produktion
	private double saniPart; // 3 Sanierung
	private double vemrPart; // 4 Vermehrungsrate
	private double result;

	/**
	 * Gets the result.
	 * 
	 * @return the result
	 */
	public double getResult() {
		return result;
	}

	/**
	 * Sets the result.
	 * 
	 * @param result
	 *            the new result
	 */
	public void setResult(double result) {
		this.result = result;
	}

	/**
	 * Instantiates a new oplewnia individuum.
	 */
	public OplewniaIndividuum() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public OplewniaIndividuum clone() {
		try {
			return (OplewniaIndividuum) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

	/**
	 * Gets the weights.
	 * 
	 * @return the weights
	 */
	public double[][] getWeights() {
		return weights;
	}

	/**
	 * Sets the weights.
	 * 
	 * @param weights
	 *            the new weights
	 */
	public void setWeights(double[][] weights) {
		this.weights = weights;
	}

	/**
	 * Gets the thresholds.
	 * 
	 * @return the thresholds
	 */
	public double[][] getThresholds() {
		return thresholds;
	}

	/**
	 * Sets the thresholds.
	 * 
	 * @param thresholds
	 *            the new thresholds
	 */
	public void setThresholds(double[][] thresholds) {
		this.thresholds = thresholds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * okoelopoly.Individuum#wendeDieStrategieAn(okoelopoly.Punktverteilung)
	 */
	@Override
	public void wendeDieStrategieAn(Punktverteilung simu) {
		init(simu);

		ArrayList<Neuron> inputLayer = initInputNeurons();
		ArrayList<Neuron> hiddenLayerOne = initHiddenLayer(inputLayer, 1);
		ArrayList<Neuron> hiddenLayerTwo = initHiddenLayer(hiddenLayerOne, 2);
		ArrayList<Neuron> hiddenLayerTree = initHiddenLayer(hiddenLayerTwo, 3);
		ArrayList<Neuron> hiddenLayerFour = initHiddenLayer(hiddenLayerTree, 4);
		ArrayList<Neuron> hiddenLayerFive = initHiddenLayer(hiddenLayerFour, 5);
		ArrayList<Neuron> hiddenLayerSix = initHiddenLayer(hiddenLayerFive, 6);
		ArrayList<Neuron> outputLayer = initOutputLayer(hiddenLayerSix);

		double sum = 0.0;
		for (int i = 0; i < 5; i++) {
			sum += outputLayer.get(i).getValue();
		}

		lebqPart = (outputLayer.get(0).getValue() / sum) * aktp;
		aufkPart = (outputLayer.get(1).getValue() / sum) * aktp;
		double production = outputLayer.get(2).getValue();
		prodPart = (production / sum) * aktp;
		saniPart = (outputLayer.get(3).getValue() / sum) * aktp;
		vemrPart = (outputLayer.get(4).getValue() / sum) * aktp;

		simu.investiereInLebensqualitaet((int) Math.round(lebqPart));
		simu.investiereInAufklaerung((int) Math.round(aufkPart));
		if (production > 0) {
			simu.investiereInProduktion((int) Math.round(prodPart));
		} else {
			simu.investiereGegenProduktion((int) Math.round(prodPart));
		}
		simu.investiereInSanierung((int) Math.round(saniPart));
		simu.investiereInVermehrungsrate((int) Math.round(vemrPart));
		simu.nutzeAufklaerungFuerBevoelkerungsWachstum(outputLayer.get(5)
				.getValue());
	}

	/**
	 * Inits the.
	 * 
	 * @param simu
	 *            the simu
	 */
	private void init(Punktverteilung simu) {
		this.aktp = simu.getAktionspunkte();
		this.aufk = simu.getAufklaerung();
		this.lebq = simu.getLebensqualitaet();
		this.prod = simu.getProduktion();
		this.sani = simu.getSanierung();
		this.vemr = simu.getVermehrungsrate();
		this.uweb = simu.getUmweltbelastung();
		this.bevo = simu.getBevoelkerung();
		this.poli = simu.getPolitik();
	}

	/**
	 * Inits the input neurons.
	 * 
	 * @return the array list
	 */
	private ArrayList<Neuron> initInputNeurons() {
		ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
		inputLayer.add(new Neuron(lebq, weights[0][0]));
		inputLayer.add(new Neuron(aufk, weights[0][1]));
		inputLayer.add(new Neuron(prod, weights[0][2]));
		inputLayer.add(new Neuron(sani, weights[0][3]));
		inputLayer.add(new Neuron(vemr, weights[0][4]));
		inputLayer.add(new Neuron(uweb, weights[0][5]));
		inputLayer.add(new Neuron(bevo, weights[0][6]));
		inputLayer.add(new Neuron(poli, weights[0][7]));
		inputLayer.add(new Neuron(aktp, weights[0][8]));
		return inputLayer;
	}

	/**
	 * Inits the output layer.
	 * 
	 * @param inputNeurons
	 *            the input neurons
	 * @return the array list
	 */
	private ArrayList<Neuron> initOutputLayer(ArrayList<Neuron> inputNeurons) {
		ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
		outputLayer.add(new Neuron(weights[EvoAlg.LAYER - 1][0],
				thresholds[EvoAlg.LAYER - 1][0], inputNeurons));
		outputLayer.add(new Neuron(weights[EvoAlg.LAYER - 1][1],
				thresholds[EvoAlg.LAYER - 1][1], inputNeurons));
		outputLayer.add(new Neuron(weights[EvoAlg.LAYER - 1][2],
				thresholds[EvoAlg.LAYER - 1][2], inputNeurons, true));
		outputLayer.add(new Neuron(weights[EvoAlg.LAYER - 1][3],
				thresholds[EvoAlg.LAYER - 1][3], inputNeurons));
		outputLayer.add(new Neuron(weights[EvoAlg.LAYER - 1][4],
				thresholds[EvoAlg.LAYER - 1][4], inputNeurons));
		outputLayer.add(new Neuron(weights[EvoAlg.LAYER - 1][5],
				thresholds[EvoAlg.LAYER - 1][5], inputNeurons, true));
		return outputLayer;
	}

	/**
	 * Inits the hidden layer.
	 * 
	 * @param iN
	 *            the i n
	 * @param layer
	 *            the layer
	 * @return the array list
	 */
	private ArrayList<Neuron> initHiddenLayer(ArrayList<Neuron> iN, int layer) {
		ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
		for (int i = 0; i < EvoAlg.NEURONS; i++) {
			hiddenLayer.add(new Neuron(weights[layer][i], thresholds[layer][i],
					iN));
		}
		return hiddenLayer;
	}

	/**
	 * The Class Neuron.
	 */
	public class Neuron {

		/** The inputs. */
		private final List<Neuron> inputs;

		/** The weight. */
		private double weight;

		/** The threshold. */
		private double threshold;

		/** The value. */
		private double value;

		/** The sigmoid two. */
		private boolean sigmoidTwo;

		/**
		 * Instantiates a new neuron.
		 * 
		 * @param weight
		 *            the weight
		 * @param value
		 *            the value
		 */
		public Neuron(double weight, double value) {
			this.weight = weight;
			this.value = value;
			this.inputs = new ArrayList<Neuron>();
		}

		/**
		 * Instantiates a new neuron.
		 * 
		 * @param weight
		 *            the weight
		 * @param threshold
		 *            the threshold
		 * @param inputs
		 *            the inputs
		 */
		public Neuron(double weight, double threshold, List<Neuron> inputs) {
			this.weight = weight;
			this.threshold = threshold;
			this.inputs = inputs;
			calcValue();
		}

		/**
		 * Instantiates a new neuron.
		 * 
		 * @param weight
		 *            the weight
		 * @param threshold
		 *            the threshold
		 * @param inputs
		 *            the inputs
		 * @param isExtended
		 *            the is extended
		 */
		public Neuron(double weight, double threshold, List<Neuron> inputs,
				boolean isExtended) {
			this.weight = weight;
			this.threshold = threshold;
			this.inputs = inputs;
			this.sigmoidTwo = isExtended;
			calcValue();
		}

		/**
		 * Calc value.
		 */
		private void calcValue() {
			double sum = 0.0;
			for (Neuron n : inputs) {
				sum += n.getWeight() * n.getValue();
			}
			sum = sum - threshold;
			if (!sigmoidTwo) {
				this.value = sigmoidOne(sum);
			} else {
				this.value = sigmoidTwo(sum);
			}
		}

		/**
		 * Sigmoid one.
		 * 
		 * @param x
		 *            the x
		 * @return the double
		 */
		public double sigmoidOne(double x) {
			return 1 / (1 + Math.exp(-x));
		}

		/**
		 * Sigmoid two.
		 * 
		 * @param x
		 *            the x
		 * @return the double
		 */
		public double sigmoidTwo(double x) {
			return Math.tanh(x);
		}

		/**
		 * Gets the threshold.
		 * 
		 * @return the threshold
		 */
		public double getThreshold() {
			return threshold;
		}

		/**
		 * Gets the weight.
		 * 
		 * @return the weight
		 */
		public double getWeight() {
			return weight;
		}

		/**
		 * Gets the value.
		 * 
		 * @return the value
		 */
		public double getValue() {
			return value;
		}

		/**
		 * Sets the value.
		 * 
		 * @param value
		 *            the new value
		 */
		public void setValue(double value) {
			this.value = value;
		}

		/**
		 * Sets the threshold.
		 * 
		 * @param threshold
		 *            the new threshold
		 */
		public void setThreshold(double threshold) {
			this.threshold = threshold;
		}

		/**
		 * Sets the weight.
		 * 
		 * @param newWeight
		 *            the new weight
		 */
		public void setWeight(double newWeight) {
			weight = newWeight;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(OplewniaIndividuum ind) {
		return ind.getResult() > result ? 1 : (ind.getResult() == result ? 0
				: -1);
	}
}
