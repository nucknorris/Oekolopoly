package okoelopoly;

import java.io.Serializable;

/**
 * 
 * Dieses Interface muss durch die Individuen implementiert werden.
 * 
 * Es gibt keine Einschraenkungen hinsichtlich der EA-Eigenschaften, wie z.B.
 * Genotyp, Bewertungfunktion, Mutation und Rekombination.
 * 
 * Wichtig sind drei Punkte:
 * <ul>
 * <li>Die Klasse hat ausschliesslich einen oeffentlichen, argumentlosen
 * Konstruktor.
 * <li>Die Serialisierung wird benoetigt, dass Sie den inneren Zustand eines
 * Ihrer guten Individuen so speichern koennen, dass ich es wieder einlesen
 * kann, um Ihr Individuum zu testen.
 * <li>Der einzigen Methode dieses Interface wird ein Objekt uebergeben, das
 * quasi als Platzhalter fuer den inneren Zustand des Simulators dient. Aktionen
 * werden auf dem uebergebenen Objekt durchgefuehrt, welches dann vom Simulator
 * spaeter ausgelesen wird. Hier an dieser Stelle muss die Logik und Intelligenz
 * Ihrer evolvierten Strategie greifen.
 * </ul>
 * 
 * @author Karsten Weicker
 * @version 12-12-08 22:34
 */

public interface Individuum extends Serializable {

    /**
     * Der Methode wird in dem Objekt simulatorstatus die aktuelle Situation
     * uebergeben. In dem Objekt werden die Aktionspunkte durch geeignete
     * Methodenaufrufe verteilt und spaeter extern vom Simulator wieder
     * ausgelesen.
     * 
     * @param simulatorstatus
     *            Objekt fuer die Ein- und Ausgabe
     */
    public void wendeDieStrategieAn(Punktverteilung simulatorstatus);
}
