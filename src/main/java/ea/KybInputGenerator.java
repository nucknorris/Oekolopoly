package ea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import okoelopoly.Kybernetien;

import org.apache.log4j.Logger;

/*
 * Value  |  MaxRange  |  Def.Range |  Def.Val. |  Range    |
 * ----------------------------------------------------------
 * AP     |    1..36   |  5..12     |    8      |   7-9
 * Sani   |    1..29   |  1..15     |    1      |   1-3
 * Prod   |    1..29   |  1..25     |   12      |  11-13
 * Umweb  |    1..29   |  1..25     |   13      |  12-14
 * Aufkl  |    1..29   |  1..20     |    4      |   3-5
 * Lebnsq |    1..29   |  5..20     |   10      |   9-11
 * Vermr  |    1..29   |  8..22     |   20      |  18-20
 * Bev    |    1..48   | 10..35     |   21      |  20-22
 * Polit  |  -10..37   | -5..20     |    0      |  -1-1
 */

public class KybInputGenerator {

    // epsilon
    private int e;

    private int steps;
    List<KybInputs> listOfKypInputs;
    private static Logger logger = Logger.getLogger(KybInputGenerator.class);
    private Random rand;
    private static int ap = KybDefVal.AP.getOptDefVal();
    private static int sa = KybDefVal.SA.getOptDefVal();
    private static int pr = KybDefVal.PR.getOptDefVal();
    private static int um = KybDefVal.UM.getOptDefVal();
    private static int au = KybDefVal.AU.getOptDefVal();
    private static int lq = KybDefVal.LQ.getOptDefVal();
    private static int vr = KybDefVal.VR.getOptDefVal();
    private static int be = KybDefVal.BE.getOptDefVal();
    private static int po = KybDefVal.PO.getOptDefVal();

    public KybInputGenerator(int epsilon, int steps) {
        this.e = epsilon;
        this.steps = steps;
        // this.listOfKypInputs = genListOfKybs();
        rand = new Random();
    }

    public List<KybInputs> getList(int amount) {
        List<KybInputs> list = new ArrayList<KybInputs>();
        for (int i = 0; i < amount; i++) {
            list.add(new KybInputs(
                    getRandInt(ap - e, ap + e),
                    getRandInt(sa - e, sa + e),
                    getRandInt(pr - e, pr + e),
                    getRandInt(um - e, um + e),
                    getRandInt(au - e, au + e),
                    getRandInt(lq - e, lq + e),
                    getRandInt(vr - e, vr + e),
                    getRandInt(be - e, be + e),
                    getRandInt(po - e, po + e)));
        }
        // list.add(new KybInputs(10, 1, 12, 13, 4, 10, 20, 21, 0));
        // list.add(new KybInputs(6, 1, 12, 13, 4, 10, 20, 21, 0));
        // list.add(new KybInputs(8, 1, 12, 13, 4, 10, 20, 21, 0));
        // list.add(new KybInputs(8, 10, 12, 13, 4, 10, 20, 21, 0));
        // list.add(new KybInputs(8, 1, 5, 13, 4, 10, 20, 21, 0));
        list.add(new KybInputs(8, 1, 20, 13, 4, 10, 20, 21, 0));
        // list.add(new KybInputs(8, 1, 12, 5, 4, 10, 20, 21, 0));
        // list.add(new KybInputs(8, 1, 12, 20, 4, 10, 20, 21, 0));
        // list.add(new KybInputs(8, 1, 12, 13, 1, 10, 20, 21, 0));
        // list.add(new KybInputs(8, 1, 12, 13, 15, 10, 20, 21, 0));
        // list.add(new KybInputs(8, 1, 12, 13, 4, 7, 20, 21, 0));
        // list.add(new KybInputs(8, 1, 12, 13, 4, 15, 20, 21, 0));
        // list.add(new KybInputs(8, 1, 12, 13, 4, 10, 21, 21, 0));
        list.add(new KybInputs(8, 1, 12, 13, 4, 10, 12, 21, 0));
        // list.add(new KybInputs(8, 1, 12, 13, 4, 10, 20, 15, 0));
        // list.add(new KybInputs(8, 1, 12, 13, 4, 10, 20, 30, 0));
        // list.add(new KybInputs(8, 1, 12, 13, 4, 10, 20, 21, -4));
        // list.add(new KybInputs(8, 1, 12, 13, 4, 10, 20, 21, 15));

        return list;
    }

    private int getRandInt(int min, int max) {
        return min + (int) (rand.nextDouble() * ((max - min) + 1));
    }

    public List<KybInputs> getCompleteList() {
        return this.listOfKypInputs;
    }

    public List<KybInputs> getXRandomKybInputs(int amount) {
        List<KybInputs> listToBeReturned = new ArrayList<KybInputs>();
        Collections.shuffle(listOfKypInputs);
        for (int i = 0; i < amount; i++) {
            listToBeReturned.add(this.listOfKypInputs.get(i));
        }
        return listToBeReturned;
    }

    public List<Kybernetien> getXRandomKybernetien(int amount) {
        List<Kybernetien> listToBeReturned = new ArrayList<Kybernetien>();
        Collections.shuffle(listOfKypInputs);
        for (int i = 0; i < amount; i++) {
            listToBeReturned.add(new Kybernetien(
                    this.listOfKypInputs.get(i).getAktionsp(),
                    this.listOfKypInputs.get(i).getSanierung(),
                    this.listOfKypInputs.get(i).getProduktion(),
                    this.listOfKypInputs.get(i).getUmweltbelast(),
                    this.listOfKypInputs.get(i).getAufklaerung(),
                    this.listOfKypInputs.get(i).getLebensqual(),
                    this.listOfKypInputs.get(i).getVermehrungsrate(),
                    this.listOfKypInputs.get(i).getBevoelkerung(),
                    this.listOfKypInputs.get(i).getPolitik()));
        }
        return listToBeReturned;
    }

}
