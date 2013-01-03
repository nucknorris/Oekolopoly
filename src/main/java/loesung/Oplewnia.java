package loesung;

import java.io.Serializable;

import okoelopoly.Individuum;
import okoelopoly.Kybernetien;
import okoelopoly.Punktverteilung;

public class Oplewnia implements Individuum, Serializable {

    private static final long serialVersionUID = 1169885659413200081L;
    double a = 5;
    double b = 15;
    double c = 10;
    double d = 15;
    double e = 6;
    private double aBefor;
    private double bBefor;
    private double cBefor;
    private double dBefor;
    private double eBefor;
    private double aAfer;
    private double bAfer;
    private double cAfer;
    private double dAfer;
    private double eAfer;
    private Punktverteilung simu;
    double ap;
    double sum;
    double base = 0.8;
    boolean chinainc = false;
    boolean uberpop = false;
    double uberpopfac = 2;

    public Oplewnia(double a, double b, double c, double d, double e) {
        this.aBefor = a;
        this.bBefor = b;
        this.cBefor = c;
        this.dBefor = d;
        this.eBefor = e;

    }

    @Override
    public void wendeDieStrategieAn(Punktverteilung simulatorstatus) {
        // setze simulator
        this.simu = simulatorstatus;
        ap = simu.getAktionspunkte();
        System.out.println("AP: " + ap);
        // print();

        extendetRules();
        calcWeights();
        investAll();

        System.out.println(simu.getAktionspunkte());
    }

    // regelsaetzr
    private void calcWeights() {

        // aufklaerung>
        if (chinainc) {
            aAfer = calcPart(simu.getAufklaerung(), a);
            if (aAfer > 0) {
                // TODO ueberpopfac berechnen
                aAfer = aAfer * uberpopfac;
            } else {
                aAfer = 8;
            }
        } else {
            aAfer = calcPart(simu.getAufklaerung(), a);
        }
        // lebensqualitaet
        bAfer = calcPart(simu.getLebensqualitaet(), b);
        // production
        if (chinainc) {
            cAfer = calcPart(c, simu.getProduktion());
        } else {
            cAfer = calcPart(simu.getProduktion(), c);
        }
        // sanierung
        dAfer = calcPart(simu.getSanierung(), d);
        // vermehrungsrate
        if (uberpop) {
            // uberpop
            eAfer = 0;
        } else if (!uberpop) {
            // unterpop

        } else {
            // normal
            eAfer = calcPart(simu.getVermehrungsrate(), e);
        }

        sum = aAfer + bAfer + cAfer + cAfer + dAfer + eAfer;
        System.out.println("Summe: " + sum);
    }

    private void extendetRules() {

        if (simu.getVermehrungsrate() >= e) {
            System.out.println("TREIBE AB");
            // TODO SMOOTH IT
            simu.nutzeAufklaerungFuerBevoelkerungsWachstum(-1);
            uberpop = true;
        } else {
            simu.nutzeAufklaerungFuerBevoelkerungsWachstum(1);
            uberpop = false;
        }

        if (simu.getProduktion() >= c) {
            System.out.println("China Inc!");
            chinainc = true;
        } else {
            chinainc = false;
        }

    }

    // invest methodes
    private void investAll() {
        aufklaerung();
        lebensqualitaet();
        produktion();
        sanierung();
        vermehrungsrate();
    }

    private void aufklaerung() {
        int invest = calcInvest(aAfer);
        System.out.println("AU:" + invest);
        simu.investiereInAufklaerung(invest);
    }

    // invest methodes
    private void lebensqualitaet() {
        int invest = calcInvest(bAfer);
        System.out.println("LQ:" + invest);
        simu.investiereInLebensqualitaet(invest);
    }

    private void produktion() {
        int invest = calcInvest(cAfer);
        System.out.println("PR:" + invest);
        if (chinainc) {
            simu.investiereGegenProduktion(invest);
        } else {
            simu.investiereInProduktion(invest);
        }

    }

    private void sanierung() {
        int invest = calcInvest(dAfer);
        System.out.println("SA:" + invest);
        simu.investiereInSanierung(invest);
    }

    private void vermehrungsrate() {
        int invest = calcInvest(eAfer);
        System.out.println("VE:" + invest);
        simu.investiereInVermehrungsrate(invest);
    }

    // math funktions
    private int calcInvest(double part) {
        // System.out.println(part);
        // return (int) Math.ceil(((ap / sum) * part));
        return (int) Math.round(((ap / sum) * part));
    }

    private double calcLog(double base, double num) {
        return Math.log(num) / Math.log(base);
    }

    private double calcPart(double x, double y) {
        if (x > y) {
            return 0;
        }
        double part = calcLog(base, x / y);
        return part;
    }

    private void print() {
        System.out.println("Sanierung: " + simu.getSanierung());
        System.out.println("Produktion: " + simu.getProduktion());
        System.out.println("Umweltbelastung: " + simu.getUmweltbelastung());
        System.out.println("Aufklaerung: " + simu.getAufklaerung());
        System.out.println("Lebensqualitaet: " + simu.getLebensqualitaet());
        System.out.println("Vermehrungsrate: " + simu.getVermehrungsrate());
        System.out.println("Bevoelkerung: " + simu.getBevoelkerung());
        System.out.println("Politik: " + simu.getPolitik());
        ;
    }
}