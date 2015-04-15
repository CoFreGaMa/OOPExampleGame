/**
 * Hauptpgrogramm zum Starten des Spiels.
 */
package spiel;

/**
 * @author mueller
 * @version 1.0; 09/2011
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Konfiguration:");
		Konfiguration.ausgabe();
		System.out.println("---------------------------------------------");
		Ticker ticker = new Ticker();
		// Spiel spiel = new Spiel(ticker);
		Spiel spiel = Spiel.getInstance(ticker);
		// Uhr starten:
		ticker.tick();
	} // main

} // class Main
