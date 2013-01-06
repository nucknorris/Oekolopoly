package loesung;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okoelopoly.Individuum;
import okoelopoly.Punktverteilung;

/**
 * @author sebastian
 * 
 */

public class SnuckIndividuumNeuronal implements Individuum, Serializable {

    private double weights[][];
    private double thresholds[][];
    private double lq; // 0 Lebensqualitaet
    private double au; // 1 Aufklaerung
    private double pr; // 2 Produktion
    private double sa; // 3 Sanierung
    private double vr; // 4 Vermehrungsrate
    private double ub; // 5 Umweltbelastung
    private double bv; // 6 Bevšlkerung
    private double po; // 7 Politik
    private double ap; // 8 Aktionspunkte

    private double lqPart; // 0 Lebensqualitaet
    private double auPart; // 1 Aufklaerung
    private double prPart; // 2 Produktion
    private double saPart; // 3 Sanierung
    private double vrPart; // 4 Vermehrungsrate
    private Punktverteilung sim;
    /**
     * 
     */
    private static final long serialVersionUID = -5383465147228204495L;

    public SnuckIndividuumNeuronal() {

    }

    public double[][] getWeights() {
        return weights;
    }

    public void setWeights(double[][] weights) {
        this.weights = weights;
    }

    public double[][] getThresholds() {
        return thresholds;
    }

    public void setThresholds(double[][] thresholds) {
        this.thresholds = thresholds;
    }

    public void wendeDieStrategieAn(Punktverteilung simulatorstatus) {
        init(simulatorstatus);
        ArrayList<Neuron> inputLayerNeurons = initInputNeurons();
        for (Neuron neuron : inputLayerNeurons) {
            System.out.println("i: " + neuron.getValue());
        }
        ArrayList<Neuron> hiddenLayerNeurons = initHiddenLayer(inputLayerNeurons);
        for (Neuron neuron : hiddenLayerNeurons) {
            System.out.println("h: " + neuron.getValue());
        }
        ArrayList<Neuron> outputLayer = initOutputLayer(hiddenLayerNeurons);
        double sum = 0.0;

        System.out.println("\nNeurons: ");
        for (Neuron neuron : outputLayer) {
            System.out.println("o: " + neuron.getValue());
            sum += neuron.getValue();
        }
        System.out.println("sum: " + sum);

        System.out.println("ap: " + ap);
        lqPart = (outputLayer.get(0).getValue() / sum) * ap;
        auPart = (outputLayer.get(1).getValue() / sum) * ap;
        prPart = (outputLayer.get(2).getValue() / sum) * ap;
        saPart = (outputLayer.get(3).getValue() / sum) * ap;
        vrPart = (outputLayer.get(4).getValue() / sum) * ap;

        System.out.println(lqPart + ", " + auPart + ", " + prPart + ", " + saPart + ", " + vrPart);

        sim.investiereInLebensqualitaet((int) Math.round(lqPart));
        sim.investiereInAufklaerung((int) Math.round(auPart));
        sim.investiereInProduktion((int) Math.round(prPart));
        sim.investiereInSanierung((int) Math.round(saPart));
        sim.investiereInVermehrungsrate((int) Math.round(vrPart));

    }

    /**
     * @param simulatorstatus
     */
    private void init(Punktverteilung simulatorstatus) {
        this.sim = simulatorstatus;
        this.ap = sim.getAktionspunkte();
        this.au = sim.getAufklaerung();
        this.lq = sim.getLebensqualitaet();
        this.pr = sim.getProduktion();
        this.sa = sim.getSanierung();
        this.vr = sim.getVermehrungsrate();
        this.ub = sim.getUmweltbelastung();
        this.bv = sim.getBevoelkerung();
        this.po = sim.getPolitik();
    }

    /**
     * @return
     */
    private ArrayList<Neuron> initInputNeurons() {
        Neuron lqInput = new Neuron(lq, weights[0][0]);
        Neuron auInput = new Neuron(au, weights[0][1]);
        Neuron prInput = new Neuron(pr, weights[0][2]);
        Neuron saInput = new Neuron(sa, weights[0][3]);
        Neuron vrInput = new Neuron(vr, weights[0][4]);
        Neuron ubInput = new Neuron(ub, weights[0][5]);
        Neuron bvInput = new Neuron(bv, weights[0][6]);
        Neuron poInput = new Neuron(po, weights[0][7]);
        Neuron apInput = new Neuron(ap, weights[0][8]);

        ArrayList<Neuron> inputNeurons = new ArrayList<Neuron>();
        inputNeurons.add(lqInput);
        inputNeurons.add(auInput);
        inputNeurons.add(prInput);
        inputNeurons.add(saInput);
        inputNeurons.add(vrInput);
        inputNeurons.add(ubInput);
        inputNeurons.add(bvInput);
        inputNeurons.add(poInput);
        inputNeurons.add(apInput);
        return inputNeurons;
    }

    private ArrayList<Neuron> initOutputLayer(ArrayList<Neuron> inputNeurons) {
        ArrayList<Neuron> outputLayerNeurons = new ArrayList<Neuron>();
        Neuron n0 = new Neuron(weights[2][0], thresholds[2][0], inputNeurons);
        Neuron n1 = new Neuron(weights[2][1], thresholds[2][1], inputNeurons);
        Neuron n2 = new Neuron(weights[2][2], thresholds[2][2], inputNeurons);
        Neuron n3 = new Neuron(weights[2][3], thresholds[2][3], inputNeurons);
        Neuron n4 = new Neuron(weights[2][4], thresholds[2][4], inputNeurons);

        outputLayerNeurons.add(n0);
        outputLayerNeurons.add(n1);
        outputLayerNeurons.add(n2);
        outputLayerNeurons.add(n3);
        outputLayerNeurons.add(n4);
        return outputLayerNeurons;
    }

    /**
     * Inits the hidden layer with weights, thresholds and inputNeurons;
     * 
     * @param inputNeurons
     */
    private ArrayList<Neuron> initHiddenLayer(ArrayList<Neuron> inputNeurons) {
        ArrayList<Neuron> hiddenLayerNeurons = new ArrayList<Neuron>();
        Neuron n0 = new Neuron(weights[1][0], thresholds[1][0], inputNeurons);
        Neuron n1 = new Neuron(weights[1][1], thresholds[1][1], inputNeurons);
        Neuron n2 = new Neuron(weights[1][2], thresholds[1][2], inputNeurons);
        Neuron n3 = new Neuron(weights[1][3], thresholds[1][3], inputNeurons);
        Neuron n4 = new Neuron(weights[1][4], thresholds[1][4], inputNeurons);
        Neuron n5 = new Neuron(weights[1][5], thresholds[1][5], inputNeurons);
        Neuron n6 = new Neuron(weights[1][6], thresholds[1][6], inputNeurons);
        Neuron n7 = new Neuron(weights[1][7], thresholds[1][7], inputNeurons);
        Neuron n8 = new Neuron(weights[1][8], thresholds[1][8], inputNeurons);
        Neuron n9 = new Neuron(weights[1][9], thresholds[1][9], inputNeurons);

        hiddenLayerNeurons.add(n0);
        hiddenLayerNeurons.add(n1);
        hiddenLayerNeurons.add(n2);
        hiddenLayerNeurons.add(n3);
        hiddenLayerNeurons.add(n4);
        hiddenLayerNeurons.add(n5);
        hiddenLayerNeurons.add(n6);
        hiddenLayerNeurons.add(n7);
        hiddenLayerNeurons.add(n8);
        hiddenLayerNeurons.add(n9);
        return hiddenLayerNeurons;
    }

    public class Neuron {
        private List<Neuron> inputs;
        private double weight;
        private double threshold;
        private double value;

        public Neuron(double weight, double value) {
            this.weight = weight;
            this.value = value;
            this.inputs = new ArrayList<Neuron>();
        }

        public Neuron(double weight, double threshold, List<Neuron> inputs) {
            this.weight = weight;
            this.threshold = threshold;
            this.inputs = inputs;
            calc();
        }

        private void calc() {
            double sum = 0.0;
            for (Neuron n : inputs) {
                sum += n.getWeight() * n.getValue();
            }
            sum -= this.threshold;

            this.value = sigmoid(sum);
            System.out.println("#######" + this.value);
        }

        public double sigmoid(double x) {
            return (1 / (1 + Math.pow(Math.E, (-1 * x))));
        }

        public double getThreshold() {
            return threshold;
        }

        public double getWeight() {
            return weight;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public void setThreshold(double threshold) {
            this.threshold = threshold;
        }

        public void setWeight(double newWeight) {
            weight = newWeight;
        }

        public void connect(Neuron... ns) {
            for (Neuron n : ns)
                inputs.add(n);
        }

        public void connect(ArrayList<Neuron> inputs) {
            this.inputs = inputs;
        }

    }
}
