package okoelopoly;

/**
 * 
 * Diese Klasse simuliert einen Staat ueber 30 Jahre.
 * 
 * Die Ausgangssituation wird bei der Erstellung der Klasse dem Konstruktor
 * uebergeben. Ein Individuum kann die Regierung des Staats mit der durch ihn
 * realisierten Strategie beeinflussen. Der Simulator versucht bis zu 30 Jahre
 * mit der Strategie des Individuums zu regieren. Nach diesen 30 Jahren (oder
 * einem frueheren Abbruch z.B. aufgrund des Verlusts aller Lebensqualitaet oder
 * eines Putschs) koennen die Kennzahlen des Zustands unseres fiktiven Staats
 * abgefragt werden, um beispielsweise eine Bewertung vorzunehmen. Die Kennzahl
 * der Gesamtbilanz stammt vom Schoepfer der Simulation und wird zur Bewertung
 * im Rahmen der Benotung benutzt. Eine Instanz dieser Klasse ist nur fuer einen
 * Simulationslauf geeignet.
 * 
 * @author Karsten Weicker
 * @version 12-12-08 22:38
 */

public class Kybernetien {
    private boolean ersteraufruf = true;
    private int aktionsp = 0;
    private int sanierung = 0;
    private int produktion = 0;
    private int umweltbelast = 0;
    private int aufklaerung = 0;
    private int lebensqual = 0;
    private int vermehrungsrate = 0;
    private int bevoelkerung = 0;
    private int politik = 0;
    private int rundenzahl = 0;
    private final int politik_ug = -10;
    private final int politik_og = 37;
    private int[] pol2_b = { -5, -2, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 3, 3, 3, 3, 3, 3, 3 };
    private final int bevoel_ug = 1;
    private final int bevoel_og = 48;
    private int[] bev2_a = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2,
            2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8,
            8, 9, 9, 9 };
    private int[] bev2_wachsfak = { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3,
            3, 3, 3, 3, 3, 3, 3, 3 };
    private int[] bev2_14 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2, -3, -3, -3,
            -3, -3, -3, -3, -3, -4, -4, -4, -4, -5, -5, -6, -7, -8, -10 };
    private final int verm_ug = 1;
    private final int verm_og = 29;
    private int[] verm2_13 = { -4, -4, -3, -3, -3, -2, -2, -2, -2, -1, -1, -1,
            -1, -1, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3 };
    private final int sani_ug = 1;
    private final int sani_og = 29;
    private int[] san2_1 = { 0, -1, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2,
            -3, -3, -3, -3, -3, -4, -4, -4, -5, -5, -5, -6, -6, -7, -7,
            -8, -9 };
    private int[] san2_2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, -1, -1, -2, -2, -3, -4, -5, -6 };
    private final int prod_ug = 1;
    private final int prod_og = 29;
    private int[] prod2_c = { -4, -3, -2, -1, 0, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5,
            6, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11 };
    private int[] prod2_verfak = { -4, -3, -2, -2, -1, -1, 0, 0, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
    private int[] prod2_3 = { 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1 - 2, 2, 2, 2, 2, 0, 0, 0 };
    private int[] prod2_4 = { 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 4, 4, 5, 5, 6,
            6, 7, 7, 8, 9, 10, 12, 14, 18, 22 };
    private final int lebq_ug = 1;
    private final int lebq_og = 29;
    private int[] lebq2_10 = { 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 0, 0,
            -1, -1, -1, -1, -1, -2, -2, -2, -1, -1, -1, 0 };
    private int[] lebq2_11 = { -15, -8, -6, -4, -3, -2, -1, 0, 1, 2, 2, 2, 1, 1,
            1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private int[] lebq2_12 = { -10, -8, -6, -3, -2, -1, -1, -1, -1, 0, 0, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 5 };
    private int[] lebq2_d = { -6, -4, -2, 0, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            3, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5 };
    private final int umw_ug = 1;
    private final int umw_og = 29;
    private int[] umw2_5 = { 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2,
            -2, -2, -2, -2, -2, -3, -3, -3, -3, -4, -4, -3, -2, -1, 0 };
    private int[] umw2_6 = { 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -2, -2, -2, -2, -3,
            -3, -3, -4, -4, -5, -5, -6, -7, -8, -10, -12, -14, -18, -25 };
    private final int aufk_ug = 1;
    private final int aufk_og = 29;
    private int[] aufk2_7 = { 0, 0, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1,
            1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 0 };
    private int[] aufk2_8 = { -2, -2, -2, -2, -2, -1, -1, -1, -1, 0, 1, 1, 1, 2,
            2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 6, 6 };
    private int[] aufk2_9 = { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3,
            4, 4, 4, 4, 5, 5 };
    private final int aufk2_9_choice_geq = 21;

    /**
     * Der Konstruktor initialisiert den fiktiven Staat. So koennen schwierigere
     * oder einfachere Situationen fuer eine Regierung vorgegeben werden. Sehr
     * extrem gewaehlte Beispiele koennen auch unloesbar sein.
     * 
     * Setzt bei ungueltigen Parameterwerten eine RuntimeException ab.
     * 
     * @param ap
     *            Aktionspunkte in der ersten Runde (1..36)
     * @param sani
     *            Sanierung (1..29)
     * @param prod
     *            Produktion/Wirtschaft (1..29)
     * @param umwbe
     *            Umweltbelastung (1..29)
     * @param aufkl
     *            Aufklaerung/Bildung (1..29)
     * @param lebnsq
     *            Lebensqualitaet (1..29)
     * @param vermrat
     *            Vermehrungsrate (1..29)
     * @param bev
     *            Bevoelkerung (1..48)
     * @param polit
     *            Politik (-10..37)
     */
    public Kybernetien(int ap, int sani, int prod, int umwbe,
            int aufkl, int lebnsq, int vermrat, int bev, int polit) {
        aktionsp = ap;
        try {
            checkInRange(aktionsp, 1, 36);
            add2sanierung(sani);
            add2produktion(prod);
            add2umweltbelast(umwbe);
            add2aufklaerung(aufkl);
            add2lebensqual(lebnsq);
            add2vermehrungsrate(vermrat);
            add2bevoelkerung(bev);
            add2politik(polit);
        } catch (SimulationException e) {
            throw new RuntimeException("illegal initialization of the simulator");
        }
    }

    /**
     * Ein Individuum wird durch eine Simulation von bis zu 30 Jahren bewertet.
     * 
     * Falls in einer Instanz diese Methode ein zweites Mal aufgerufen wird,
     * wird eine RuntimeException geworfen.
     * 
     * @param strat
     *            Das zu bewertende Individuum
     */
    public void bewerteEineStrategie(Individuum strat) {
        int einsatz;
        if (ersteraufruf) {
            ersteraufruf = false;
            try {
                for (int i = 0; i < 30; i++) {
                    Punktverteilung pkt = new Punktverteilung(aktionsp, sanierung,
                            produktion, aufklaerung, lebensqual, vermehrungsrate,
                            umweltbelast, bevoelkerung, politik);
                    strat.wendeDieStrategieAn(pkt);
                    einsatz = pkt.getPunkteSanierung();
                    add2sanierung(einsatz);
                    aktionsp -= einsatz;
                    einsatz = pkt.getPunkteProduktion();
                    add2produktion(einsatz);
                    aktionsp -= einsatz;
                    einsatz = pkt.getPunkteAufklaerung();
                    add2aufklaerung(einsatz);
                    aktionsp -= einsatz;
                    einsatz = pkt.getPunkteLebensqualitaet();
                    add2lebensqual(einsatz);
                    aktionsp -= einsatz;
                    einsatz = pkt.getPunkteVermehrungsrate();
                    add2vermehrungsrate(einsatz);
                    aktionsp -= einsatz;
                    simuliereEinJahr(pkt.getBevSollWachsen());
                }
            } catch (SimulationException e) {
            }
        } else
            throw new RuntimeException("illegal second simulation run in one instance");
    }

    public double getGesamtbilanz() {
        if (lebensqual >= lebq_ug && lebensqual <= lebq_og)
            return (10.0 * (politik + 3 * lebq2_d[lebensqual - lebq_ug])) / (3 + rundenzahl);
        else
            return (10.0 * politik) / (3 + rundenzahl);
    }

    public int getRundenzahl() {
        return rundenzahl;
    }

    public int getAktionspunkte() {
        return aktionsp;
    }

    public int getSanierung() {
        return sanierung;
    }

    public int getProduktion() {
        return produktion;
    }

    public int getUmweltbelastung() {
        return umweltbelast;
    }

    public int getAufklaerung() {
        return aufklaerung;
    }

    public int getLebensqualitaet() {
        return lebensqual;
    }

    public int getVermehrungsrate() {
        return vermehrungsrate;
    }

    public int getBevoelkerung() {
        return bevoelkerung;
    }

    public int getPolitik() {
        return politik;
    }

    private void add2sanierung(int wert) throws SimulationException {
        sanierung += wert;
        checkInRange(sanierung, sani_ug, sani_og);
    }

    private void add2umweltbelast(int wert) throws SimulationException {
        umweltbelast += wert;
        checkInRange(umweltbelast, umw_ug, umw_og);
    }

    private void add2produktion(int wert) throws SimulationException {
        produktion += wert;
        checkInRange(produktion, prod_ug, prod_og);
    }

    private void add2lebensqual(int wert) throws SimulationException {
        lebensqual += wert;
        checkInRange(lebensqual, lebq_ug, lebq_og);
    }

    private void add2aufklaerung(int wert) throws SimulationException {
        aufklaerung += wert;
        checkInRange(aufklaerung, aufk_ug, aufk_og);
    }

    private void add2vermehrungsrate(int wert) throws SimulationException {
        vermehrungsrate += wert;
        checkInRange(vermehrungsrate, verm_ug, verm_og);
    }

    private void add2politik(int wert) throws SimulationException {
        politik += wert;
        checkInRange(politik, politik_ug, politik_og);
    }

    private void add2bevoelkerung(int wert) throws SimulationException {
        bevoelkerung += wert;
        checkInRange(bevoelkerung, bevoel_ug, bevoel_og);
    }

    private void checkInRange(int wert, int ug, int og) throws SimulationException {
        if (wert < ug || wert > og)
            throw new SimulationException();
    }

    private void simuliereEinJahr(double bevSollWachsen) throws SimulationException {
        rundenzahl++;
        add2umweltbelast(san2_1[sanierung - sani_ug]);
        add2sanierung(san2_2[sanierung - sani_ug]);
        add2produktion(prod2_3[produktion - prod_ug]);
        add2umweltbelast(prod2_4[produktion - prod_ug]);
        add2umweltbelast(umw2_5[umweltbelast - umw_ug]);
        add2lebensqual(umw2_6[umweltbelast - umw_ug]);
        add2aufklaerung(aufk2_7[aufklaerung - aufk_ug]);
        add2lebensqual(aufk2_8[aufklaerung - aufk_ug]);
        if (aufklaerung < aufk2_9_choice_geq)
            add2vermehrungsrate(aufk2_9[aufklaerung - aufk_ug]);
        else {
            double wert = bevSollWachsen * aufk2_9[aufklaerung - aufk_ug];
            if (wert >= 0.0)
                wert += 0.5;
            else
                wert -= 0.5;
            add2vermehrungsrate((int) wert);
        }
        add2lebensqual(lebq2_10[lebensqual - lebq_ug]);
        add2vermehrungsrate(lebq2_11[lebensqual - lebq_ug]);
        add2politik(lebq2_12[lebensqual - lebq_ug]);
        add2bevoelkerung(bev2_wachsfak[bevoelkerung - bevoel_ug] *
                verm2_13[vermehrungsrate - verm_ug]);
        add2lebensqual(bev2_14[bevoelkerung - bevoel_ug]);
        aktionsp += prod2_verfak[produktion - prod_ug] * bev2_a[bevoelkerung - bevoel_ug];
        aktionsp += pol2_b[politik - politik_ug];
        aktionsp += prod2_c[produktion - prod_ug];
        aktionsp += lebq2_d[lebensqual - lebq_ug];
        if (aktionsp < 0)
            aktionsp = 0;
        if (aktionsp > 36)
            aktionsp = 36;
    }

    private class SimulationException extends Exception {
        private static final long serialVersionUID = 4149527809880063398L;
    };

}
