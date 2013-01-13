package ea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import loesung.SnuckIndividuum;

import org.apache.log4j.Logger;

public class Selector {
    private static Logger logger = Logger.getLogger(Selector.class);

    public static List<SnuckIndividuum> selectBest(List<SnuckIndividuum> startPop,
            List<SnuckIndividuum> newPop, int limit) {
        newPop.addAll(cloneList(startPop));
        // for (SnuckIndividuum snuckIndividuum : newPop) {
        // logger.info(snuckIndividuum.getResult());
        // }
        // Collections.sort(newPop, new IndividuumComparator());
        Collections.sort(newPop);

        List<SnuckIndividuum> sortedList = new ArrayList<SnuckIndividuum>();
        for (int i = 0; i < limit; i++) {
            sortedList.add(newPop.get(i).clone());
        }
        // for (SnuckIndividuum snuckIndividuum : sortedList) {
        // logger.info(snuckIndividuum.getResult());
        // }
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
