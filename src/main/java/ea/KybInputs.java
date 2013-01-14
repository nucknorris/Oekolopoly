package ea;

public class KybInputs {

    private int aktionsp = 0;
    private int sanierung = 0;
    private int produktion = 0;
    private int umweltbelast = 0;
    private int aufklaerung = 0;
    private int lebensqual = 0;
    private int vermehrungsrate = 0;
    private int bevoelkerung = 0;
    private int politik = 0;

    public KybInputs(int aktionsp, int sanierung, int produktion, int umweltbelast,
            int aufklaerung, int lebensqual, int vermehrungsrate, int bevoelkerung, int politik) {
        this.aktionsp = aktionsp;
        this.sanierung = sanierung;
        this.produktion = produktion;
        this.umweltbelast = umweltbelast;
        this.aufklaerung = aufklaerung;
        this.lebensqual = lebensqual;
        this.vermehrungsrate = vermehrungsrate;
        this.bevoelkerung = bevoelkerung;
        this.politik = politik;
    }

    public int getAktionsp() {
        return aktionsp;
    }

    public void setAktionsp(int aktionsp) {
        this.aktionsp = aktionsp;
    }

    public int getSanierung() {
        return sanierung;
    }

    public void setSanierung(int sanierung) {
        this.sanierung = sanierung;
    }

    public int getProduktion() {
        return produktion;
    }

    public void setProduktion(int produktion) {
        this.produktion = produktion;
    }

    public int getUmweltbelast() {
        return umweltbelast;
    }

    public void setUmweltbelast(int umweltbelast) {
        this.umweltbelast = umweltbelast;
    }

    public int getAufklaerung() {
        return aufklaerung;
    }

    public void setAufklaerung(int aufklaerung) {
        this.aufklaerung = aufklaerung;
    }

    public int getLebensqual() {
        return lebensqual;
    }

    public void setLebensqual(int lebensqual) {
        this.lebensqual = lebensqual;
    }

    public int getVermehrungsrate() {
        return vermehrungsrate;
    }

    public void setVermehrungsrate(int vermehrungsrate) {
        this.vermehrungsrate = vermehrungsrate;
    }

    public int getBevoelkerung() {
        return bevoelkerung;
    }

    public void setBevoelkerung(int bevoelkerung) {
        this.bevoelkerung = bevoelkerung;
    }

    public int getPolitik() {
        return politik;
    }

    public void setPolitik(int politik) {
        this.politik = politik;
    }

    public String toString() {
        return String.format(
                "ap:%s - sa:%s - pr:%s - um:%s - au:%s - lq:%s - vr:%s - be:%s - po:%s",
                this.aktionsp, this.sanierung, this.produktion, this.umweltbelast,
                this.aufklaerung, this.lebensqual, this.vermehrungsrate, this.bevoelkerung,
                this.politik);
    }

}
