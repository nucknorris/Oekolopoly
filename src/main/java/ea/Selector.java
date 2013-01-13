package ea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import loesung.SnuckIndividuum;

public class Selector {

    public static List<SnuckIndividuum> mergeAndSelectBest(List<SnuckIndividuum> startPop,
            List<SnuckIndividuum> newPop, int limit) {
        newPop.addAll(cloneList(startPop));
        Collections.sort(newPop);

        List<SnuckIndividuum> sortedList = new ArrayList<SnuckIndividuum>();
        for (int i = 0; i < limit; i++) {
            sortedList.add(newPop.get(i).clone());
        }
        return sortedList;
    }

    private static List<SnuckIndividuum> cloneList(List<SnuckIndividuum> startPop) {
        List<SnuckIndividuum> clone = new ArrayList<SnuckIndividuum>();
        for (SnuckIndividuum ind : startPop) {
            clone.add(ind.clone());
        }
        return clone;
    }

}
