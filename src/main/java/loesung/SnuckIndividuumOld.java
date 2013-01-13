package loesung;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okoelopoly.Individuum;
import okoelopoly.Punktverteilung;

import org.apache.log4j.Logger;

/**
 * @author sebastian
 * 
 */

public class SnuckIndividuumOld implements Individuum, Serializable {

    public enum StrategyValue {
        AU("au"), LQ("lq"), PR("pr"), SA("sa"), VR("vr");
        private String name;

        private StrategyValue(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    };

    private static final long serialVersionUID = 1169885659413200081L;
    private double au; // Aufklaerung
    private double lq; // Lebensqualitaet
    private double pr; // Produktion
    private double sa; // Sanierung
    private double vr; // Vermehrungsrate

    private boolean ueberproduktion = false;

    /*
     * Target values
     */
    private static final double AU_TARGET = 5; // 15;
    private static final double LQ_TARGET = 15; // 29;
    private static final double PR_TARGET = 15; // 15;
    private static final double SA_TARGET = 20; // 29;
    private static final double VR_TARGET = 5; // 15;

    private static final double AU_UPPER_BOUND = 25;
    private static final double LQ_UPPER_BOUND = 22;
    private static final double PR_UPPER_BOUND = 27;
    private static final double SA_UPPER_BOUND = 22;
    private static final double VR_UPPER_BOUND = 22;

    private Punktverteilung sim;
    private static double ap;

    private static Logger logger = Logger.getLogger(SnuckIndividuumOld.class);

    public SnuckIndividuumOld(double au, double lq, double pr, double sa, double vr) {
        this.au = au;
        this.lq = lq;
        this.pr = pr;
        this.sa = sa;
        this.vr = vr;
    }

    @Override
    public void wendeDieStrategieAn(Punktverteilung simulatorstatus) {
        this.sim = simulatorstatus;
        this.ap = sim.getAktionspunkte();

        logger.info("\n ---- AP: " + ap + " ---- ");
        print();

        Map<String, Double> distanceMap = buildDistanceMap();

        distributeActionPointsAsRequiered(distanceMap);
    }

    private void distributeActionPointsAsRequiered(Map<String, Double> distanceMap) {
        double totalValues = sumCurrentValues(distanceMap);
        for (Map.Entry<String, Double> element : distanceMap.entrySet()) {
            double proportion =
                    (int) Math.round((((element.getValue() * 100.0f) / totalValues) * ap) / 100);
            logger.info(element.getKey() + ":" + proportion);
            investInAufklaerung(distanceMap.get("au").intValue());
            investInLebensqualitaet(distanceMap.get("lq").intValue());
            investInSanierung(distanceMap.get("sa").intValue());
            if (ueberproduktion) {
                investGegenProduktion((distanceMap.get("pr").intValue()) * -1);
                ueberproduktion = false;
            } else {
                investInProduktion(distanceMap.get("pr").intValue());
            }
            investInVermehrungsrate(distanceMap.get("vr").intValue());
        }
    }

    private void investInAufklaerung(int investment) {
        if (sim.getAufklaerung() <= AU_UPPER_BOUND) {
            sim.investiereInAufklaerung(investment);
        }
    }

    private void investInLebensqualitaet(int investment) {
        if (sim.getLebensqualitaet() <= LQ_UPPER_BOUND) {
            sim.investiereInLebensqualitaet(investment);
        }
    }

    private void investInProduktion(int investment) {
        if (sim.getProduktion() <= PR_UPPER_BOUND) {
            sim.investiereInProduktion(investment);
        }
    }

    private void investGegenProduktion(int investment) {
        System.out.println("gegen");
        sim.investiereGegenProduktion(investment);
    }

    private void investInSanierung(int investment) {
        if (sim.getSanierung() <= SA_UPPER_BOUND) {
            sim.investiereInSanierung(investment);
        }
    }

    private void investInVermehrungsrate(int investment) {
        int be = sim.getBevoelkerung();
        if ((be >= 0) && (be <= 14)) {
            sim.nutzeAufklaerungFuerBevoelkerungsWachstum(1);
        } else if ((be >= 10) && (be <= 14)) {
            sim.nutzeAufklaerungFuerBevoelkerungsWachstum(0.2);
        } else if ((be >= 15) && (be <= 25)) {
            sim.nutzeAufklaerungFuerBevoelkerungsWachstum(0);
        } else if ((be >= 26) && (be <= 30)) {
            sim.nutzeAufklaerungFuerBevoelkerungsWachstum(-0.5);
        } else if (be >= 31) {
            sim.nutzeAufklaerungFuerBevoelkerungsWachstum(-1);
            System.out.println("!");

        }
        if (sim.getVermehrungsrate() <= VR_UPPER_BOUND) {
            sim.investiereInVermehrungsrate(investment);
        }
    }

    private double sumCurrentValues(Map<String, Double> distanceMap) {
        int result = 0;
        for (Map.Entry<String, Double> element : distanceMap.entrySet()) {
            result += element.getValue();
        }
        return result;
    }

    /**
     * Returns a sorted map containing all distances.
     */
    private Map<String, Double> buildDistanceMap() {
        Map<String, Double> distanceMap = new HashMap<String, Double>();
        distanceMap.put("au", calcDistance(sim.getAufklaerung(), AU_TARGET, false));
        distanceMap.put("lq", calcDistance(sim.getLebensqualitaet(), LQ_TARGET, false));
        distanceMap.put("pr", calcDistance(sim.getProduktion(), PR_TARGET, true));
        distanceMap.put("sa", calcDistance(sim.getSanierung(), SA_TARGET, false));
        distanceMap.put("vr", calcDistance(sim.getVermehrungsrate(), VR_TARGET, false));
        return sortByValue(distanceMap);
    }

    /**
     * Calculates the distance between the value and the target value
     * 
     * @param value
     * @param target
     * @return
     */
    private Double calcDistance(int value, double target, boolean isProduktion) {
        if (target > value) {
            return target - value;
        } else if (isProduktion) {
            ueberproduktion = true;
            System.out.println("ueberproduktion");
            return target;
        } else {
            return 0.0;
        }
    }

    /**
     * Prints the output in a human readably format.
     */
    private void print() {
        StringBuilder output = new StringBuilder();

        output.append(String.format("investments: au:%s \t lq:%s \t pr:%s \t sa:%s \t vr:%s",
                au, lq, pr, sa, vr));
        logger.info(output.toString());

        output.setLength(0);

        output.append(String
                .format("results    : au:%s \t lq:%s \t\t pr:%s \t\t sa:%s \t\t vr:%s",
                        sim.getAufklaerung(), sim.getLebensqualitaet(),
                        sim.getProduktion(), sim.getSanierung(),
                        sim.getVermehrungsrate()));
        logger.info(output.toString());

        output.setLength(0);

        output.append(String
                .format("other      : um:%s \t be:%s \t\t po:%s",
                        sim.getUmweltbelastung(), sim.getBevoelkerung(), sim.getPolitik()));
        logger.info(output.toString());
    }

    /**
     * Sorts a generic map by its value.
     * 
     * @param map
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V>
            sortByValue(Map<K, V> map)
    {
        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>()
        {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

}