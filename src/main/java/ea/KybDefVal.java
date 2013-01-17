package ea;

public enum KybDefVal {
    AP(8, 8),
    SA(1, 3), // min 1
    PR(12, 12),
    UM(13, 13),
    AU(4, 4),
    LQ(10, 10),
    VR(20, 20), // 19
    BE(21, 21),
    PO(0, 0);

    // default value
    private int defVal;

    // optimal/used default value
    private int optDefVal;

    private KybDefVal(int defVal, int optDefVal) {
        this.defVal = defVal;
        this.optDefVal = optDefVal;
    }

    public int getDefVal() {
        return defVal;
    }

    public void setDefVal(int defVal) {
        this.defVal = defVal;
    }

    public int getOptDefVal() {
        return optDefVal;
    }

    public void setOptDefVal(int optDefVal) {
        this.optDefVal = optDefVal;
    }

}
