/**
 * Taktgeber fuer das Spiel.
 */
package spiel;

/**
 * @author clecon
 * @version 1.0; 09/2011 - 04.02.2012
 *
 */

import java.util.*;

public class Ticker extends Observable {

	// Daten:
	private Date date = new Date();
	private boolean stop = false;
	
	public Ticker() {
	} // Konstruktor
	
	/**
	 * &Uuml;berschreibt die Methode der abstrakten Klasse Observable.
	 */
	public void addObserver(Observer o) {
		super.addObserver(o);
	} // addObserver

	/**
	 * Entfernen des Observers aus der Liste.
	 * @override
	 */
	public void deleteObserver(Observer o) {
		if (o==null) {
			System.err.println("Observer ist null!");
			return;
		} // if
		super.deleteObserver(o);
	} // deleteObserver
	
	/**
	 * Wird aufgerufen (vom Controller), wenn eine Taste gedr&uuml;ckt worden ist.
	 * @param keyCode ASCII-Code der gedr&uuml;ckten Taste.
	 */
	public void change(int keyCode) {
		setChanged();
		notifyObservers(new Integer(keyCode));
	} // change
	
	/**
	 * Wird aufgerufen (von der Methode tick), falls
	 * eine bestimmte Zeitspanne verstrichen ist.
	 * @param date Date-Objekt.
	 */
	public void change(Date date) {
		this.date = date;
		setChanged();
		notifyObservers(date);
	} // change
	
	/**
	 * Stoppen des Tickers.
	 */
	public void stoppen() {
		stop = true;
	} // stop
	
	/**
	 * Uhr. Nach einer bestimmten Zeitspanne wird eine 
	 * Benachrichtigung an die Observer geschickt.
	 */
	public void tick() {
		// System.out.println("+++ Anzahl Observer: " + countObservers());
		stop = false;

		while (!stop) {
			try {
				Thread.sleep(Konfiguration.AENDERUNGS_FREQUENZ/Konfiguration.MAX_SPEED_STUFEN);
				change(new Date());
				// System.out.println("+++ Tick");
			} catch (InterruptedException e) {
				System.err.println("FEHLER bei der Uhr-Warteschleife.");
			} // catch
		} // while
	} // tick
	
} // class Ticker
