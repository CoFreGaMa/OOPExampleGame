/**
 * RMI-Server fuer Highscore.
 * Aufbau Highscore-Datei:
 * Pro Zeile ein Eintrag
 * 1. Zeile "Highscore"
 * 2.+3. Zeile: Datum, Punkte
 * 4.+5. Zeile: Datum, Punkte
 * ...
 */
package spiel;

/**
 * @author clecon
 * @version 1.0; 02/2012 - 17.02.2012
 *
 */
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.io.*;

public class HighscoreServerRMI extends UnicastRemoteObject implements HighscoreInterfaceRMI {
	static final long serialVersionUID = 1L;

	private final String HIGHSCORE_DATEINAME = "highscore.txt";
	public static final int MAX_EINTRAEGE = 10;
	int anzahlEintraege;
	HighscoreEintrag[] highscore = new HighscoreEintrag[MAX_EINTRAEGE];

	HighscoreServerRMI() throws RemoteException {
		super();
		// Hierdurch wird signalisiert, dass
		// das Objekt uber das Netzwkerk erreichbar ist.
	} // Konstruktor

	/**
	 * Schreibt den Highscore in eine Textdatei.
	 */
	public void schreibeHighscore() {

		BufferedWriter bufferedWriter = null;
		HighscoreEintrag he;

		try {
			bufferedWriter = new BufferedWriter(new FileWriter(
					HIGHSCORE_DATEINAME));
			
			bufferedWriter.write("Highscore");
			bufferedWriter.newLine();

			for (int i = 0; i < anzahlEintraege; i++) {
				he = highscore[i];
				bufferedWriter.write(new Long(he.date.getTime()).toString());
				bufferedWriter.newLine();
				bufferedWriter.write(new Integer(he.punkte).toString());
				bufferedWriter.newLine();
			} // for

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			// Datenstrom schlie&szl;en
			try {
				if (bufferedWriter != null) {
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} // catch
		} // finally
	} // schreibeHighscore

	/**
	 * Fuegt einen neuen Eintrag in die Highscore-Liste ein.
	 * Als Datum wird das aktuelle Datum verwendet.
	 * @punkte Punkte.
	 */
	public void neuerEintrag(int punkte) throws RemoteException {
		neuerEintrag(new HighscoreEintrag(new Date(), punkte));
	} // neuerEintrag


	/**
	 * F&uuml;gt einen neuen Eintrag in die Highscore-Liste ein.
	 * @param he Highscore-Eintrag (mit Datum und Punkte).
	 */
	public void neuerEintrag(HighscoreEintrag he) {
		int i;

		// Position in bisheriger Highscore-Liste suchen:
		for (i = 0; i < anzahlEintraege; i++) {
			if (he.punkte > highscore[i].punkte) {
				anzahlEintraege++;
				if (anzahlEintraege > MAX_EINTRAEGE) {
					anzahlEintraege--;
				} // if
					// Rest nach hinten verschieben:
				for (int j = anzahlEintraege - 1; j > i; j--) {
					highscore[j] = highscore[j - 1];
				} // for
				highscore[i] = he;
				schreibeHighscore();
				return;
			} // if
		} // for
		if (anzahlEintraege < MAX_EINTRAEGE) {
			highscore[i] = he;
			anzahlEintraege++;
			schreibeHighscore();
		} else {
			// Nicht Highscore-tauglich
		} // if (else)
	} // neuerEintrag

	/**
	 * Lesen der Highscore-Datei.
	 */
	public void leseHighscore() {
		String line, sDate, sPunkte;
		Date date;
		int punkte;
		File file;

		// Falls Datei nicht vorhanden: Neue Datei erstellen:
		file = new File(HIGHSCORE_DATEINAME);
		if (!file.exists()) {
			schreibeHighscore();
			return;
		} // if
		
		anzahlEintraege = 0;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(HIGHSCORE_DATEINAME)));
			try {
				line = in.readLine(); // Erste Zeile mit Ueberschrift ueberlesen
				while ((line = in.readLine()) != null) {
					sDate = line;
					sPunkte = in.readLine();
					date = new Date(new Long(sDate).longValue());
					punkte = new Integer(sPunkte).intValue();
					highscore[anzahlEintraege] = new HighscoreEintrag(date,
							punkte);
					anzahlEintraege++;
				} // while
			} finally {
				in.close();
			} // finally
		} catch (IOException ex) {
			System.err.println("Highscore: Fehler beim Lesen aus Datei.");
			ex.printStackTrace();
		} // catch
	} // leseHighscore

	/**
	 * Uebermitteln der Eintraege der Highscore-Liste.
	 * Diese Daten werden direkt aus der Datei gelesen.
	 */
	public HighscoreEintrag[] getHighscore() throws RemoteException {
		leseHighscore();

		HighscoreEintrag[] hs = new HighscoreEintrag[anzahlEintraege];
		for (int i = 0; i < hs.length; i++) {
			hs[i] = highscore[i];
		} // for
		return hs;
	} // getHighscore

	/**
	 * Test-Methode.
	 * @param args (keine Argumente)
	 */
	public static void main(String[] args) {
		try {
			// Anlegen des Namensdienstes (Registry):
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
		} // catch
		try {
			// Namen an die Registry uebermitteln:
			Naming.rebind("Highscore", new HighscoreServerRMI());
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
		} // catch
	} // main

} // class HighscoreServerRMI
