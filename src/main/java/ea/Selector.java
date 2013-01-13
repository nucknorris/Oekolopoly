package ea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import loesung.OplewniaIndividuum;

public class Selector {

	public static List<OplewniaIndividuum> mergeAndSelectBest(
			List<OplewniaIndividuum> startPop, List<OplewniaIndividuum> newPop,
			int limit) {
		newPop.addAll(clone(startPop));
		Collections.sort(newPop);

		List<OplewniaIndividuum> sortedList = new ArrayList<OplewniaIndividuum>();
		for (int i = 0; i < limit; i++) {
			sortedList.add(newPop.get(i).clone());
		}
		return sortedList;
	}

	private static List<OplewniaIndividuum> clone(
			List<OplewniaIndividuum> startPop) {
		List<OplewniaIndividuum> clone = new ArrayList<OplewniaIndividuum>();
		for (OplewniaIndividuum ind : startPop) {
			clone.add(ind.clone());
		}
		return clone;
	}

}
