package ea;

import java.util.Comparator;

import loesung.SnuckIndividuum;

public class IndividuumComparator implements Comparator<SnuckIndividuum> {

    @Override
    public int compare(SnuckIndividuum o1, SnuckIndividuum o2) {
        return o1.getResult() > o2.getResult() ? 1
                : (o1.getResult() == o2.getResult() ? 0 : -1);
    }
}