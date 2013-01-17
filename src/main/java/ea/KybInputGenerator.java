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
        return list;
    }

    private List<KybInputs> genListOfKybs() {

        List<KybInputs> listOfKypInputs = new ArrayList<KybInputs>();
        logger.info("start generating kybernetien inputs");
        for (int ap = KybDefVal.AP.getOptDefVal() - e; ap <= KybDefVal.AP.getOptDefVal() + e; ap = ap
                + steps) {
            for (int sa = KybDefVal.SA.getOptDefVal() - e; sa <= KybDefVal.SA.getOptDefVal() + e; sa = sa
                    + steps) {
                for (int pr = KybDefVal.PR.getOptDefVal() - e; pr <= KybDefVal.PR.getOptDefVal()
                        + e; pr = pr + steps) {
                    for (int um = KybDefVal.UM.getOptDefVal() - e; um <= KybDefVal.UM
                            .getOptDefVal() + e; um = um + steps) {
                        for (int au = KybDefVal.AU.getOptDefVal() - e; au <= KybDefVal.AU
                                .getOptDefVal() + e; au = au + steps) {
                            for (int lq = KybDefVal.LQ.getOptDefVal() - e; lq <= KybDefVal.LQ
                                    .getOptDefVal() + e; lq = lq + steps) {
                                for (int vr = KybDefVal.VR.getOptDefVal() - e; vr <= KybDefVal.VR
                                        .getOptDefVal() + e; vr = vr + steps) {
                                    for (int be = KybDefVal.BE.getOptDefVal() - e; be <= KybDefVal.BE
                                            .getOptDefVal()
                                            + e; be = be + steps) {
                                        for (int po = KybDefVal.PO.getOptDefVal() - e; po <= KybDefVal.PO
                                                .getOptDefVal()
                                                + e; po = po + steps) {
                                            listOfKypInputs.add(new KybInputs(ap, sa, pr, um, au,
                                                    lq, vr, be, po));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        logger.info("finish generating kybernetien inputs");
        return listOfKypInputs;
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
