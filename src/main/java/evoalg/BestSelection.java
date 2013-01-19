/*
 * HTWK Leipzig
 * Evolutionaere Algorithmen 
 * Projekt Aufgabe Drei
 * @author Oliver Plewnia
 * 
 */
package evoalg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import loesung.OplewniaIndividuum;

/**
 * The Class BestSelection.
 */
public class BestSelection {

	/**
	 * Selection.
	 * 
	 * @param startPop
	 *            the start pop
	 * @param newPop
	 *            the new pop
	 * @param limit
	 *            the limit
	 * @return the list
	 */
	public static List<OplewniaIndividuum> selection(
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

	/**
	 * Clone.
	 * 
	 * @param startPop
	 *            the start pop
	 * @return the list
	 */
	private static List<OplewniaIndividuum> clone(
			List<OplewniaIndividuum> startPop) {
		List<OplewniaIndividuum> clone = new ArrayList<OplewniaIndividuum>();
		for (OplewniaIndividuum ind : startPop) {
			clone.add(ind.clone());
		}
		return clone;
	}

}
