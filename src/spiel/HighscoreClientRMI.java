/**
 * RMI-Client zur Highscore-Verwaltung.
 */
package spiel;

/**
 * @author clecon
 * @version 1.0; 02/2012 - 08.02.2012
 *
 */
import java.rmi.Naming;
import java.rmi.RemoteException;

// import com.sun.corba.se.spi.activation.Server;

public class HighscoreClientRMI {

	public static String URL = "//127.0.0.1/Highscore";
	private HighscoreInterfaceRMI server = null;
	
	/**
	 * Aufbau der Verbindung zum Highscore-Server.
	 * @param url URL des Highscore-Servers.
	 */
	public HighscoreClientRMI(String url) {
		URL = url;
		try {
			server = (HighscoreInterfaceRMI) Naming.lookup(url);
		} catch (Exception ex) {
			System.err.println("Fehler bei clientseitigem RMI-Aufruf.");
			System.out.println("Highscore wird wegen Netzproblem genutzt.");
			// ex.printStackTrace();
		} // catch
		
	} // Konstruktor
	
	/**
	 * Liest die Highscore-Liste und speichert die Werte in einem Array.
	 * @return Array mit den Highscore-Listen-Eintraegen.
	 */
	public HighscoreEintrag[] getHighscore() {
		if (server == null) {
			System.err.println("Highscore: Keine Neztverbindung zu " + URL
					+ " existiert.");
			return new HighscoreEintrag[0];
		} else {
			try {
				return server.getHighscore();
			} catch (RemoteException ex) {
				System.err.println("Highscore: Fehler beim Aufbau der Client-Netzwerbindung.");
				ex.printStackTrace();
			} // if (else)
			return new HighscoreEintrag[0];
		} // if (else)
	} // getHighscore
	
	/**
	 * Hinzuf&uuml;gen eines Highscore-Eintrags.
	 * Es wird allerdings nicht garantiert, dass der Eintrag
	 * im Highscore erscheint, da nur die besten Eintr&auml;ge
	 * dort aufgef&uuml;ert werden.
	 * @param punkte Erreichte Punkte.
	 */
	public void neuerEintrag(int punkte) {
		if (server == null) {
			System.out.println("Highscore nicht verfuegbar.");
			return;
		}
		try {
			server.neuerEintrag(punkte);
		} catch (RemoteException ex) {
			ex.printStackTrace();
		} // catch
	} // neuerEintrag
	
	/**
	 * Debug-Methode.
	 * @param url URL des Highscore-Servers.
	 */
	public void runClient(String url) {
		HighscoreEintrag[] hs = new HighscoreEintrag[0];
		try {
			HighscoreInterfaceRMI server = (HighscoreInterfaceRMI) Naming.lookup(url);
			System.out.println("Highscore: ");
			hs = server.getHighscore();
			System.out.println(hs[0].punkte);
		} catch (Exception ex) {
			System.err.println("Fehler bei clientseitigem RMI-Aufruf.");
			ex.printStackTrace();
		} // catch
	} // runClient

	/**
	 * Testprogramm.
	 * @param args
	 */
	public static void main(String[] args) {
		HighscoreClientRMI client = new HighscoreClientRMI(HighscoreClientRMI.URL);
		System.out.println("Neuer Eintrag 999");
		client.neuerEintrag(999);
		client.neuerEintrag(1001);
		client.neuerEintrag(998);
		client.neuerEintrag(1);
		client.neuerEintrag(3);
		client.neuerEintrag(5);
		client.neuerEintrag(7);
		client.neuerEintrag(9);
		client.neuerEintrag(11);
		client.neuerEintrag(12);
		client.neuerEintrag(13);
		HighscoreEintrag[] hs = client.getHighscore();
		System.out.println("Highscore:");
		for (int i=0; i<hs.length; i++) {
			HighscoreEintrag he = hs[i];
			System.out.println("Datum: " + he.date.toGMTString() + ", Punkte: " + he.punkte);
		} // for
		
	} // main

} // class HighscoreRMI
