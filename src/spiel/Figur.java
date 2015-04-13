/**
 * Spielfigur.
 */
package spiel;

import java.awt.Color;

/**
 * @author clecon
 * @version 1.0; 09/2011; 03.02.2012
 *
 */
import java.util.*;

public class Figur extends Zeichenobjekt implements Observer {
	static final long serialVersionUID = 1L;

	private static int lfdNr = 0;
	private int nr;
	private int diffX, diffY;
	protected Observable ticker;
	private int speed = 0;
	private int speedZaehler = 0;

	public Figur() {
		color = Konfiguration.FARBE_PASSANT_DEFAULT;
	} // Konstruktor 0
	
	public Figur(Observable ticker) {
		this();
		lfdNr++;
		nr = lfdNr;
		ticker.addObserver(this);
		this.ticker = ticker;
		oval = true;
		// Startposition bestimmen:
	} // Konstruktor 1
	
	public Figur(Observable ticker, Color color) {
		this(ticker);
		boolean fertig = true;
		// Ein freies Feld suchen:
		do {
			bestimmeStartPosition();
			// fertig = Spielfeld.getInstance().isFrei(x, y);
			fertig = Spielfeld.getInstance().feld[x][y].typ == Feld.TYP_FREI;
			// Start- und Zielzeile ausblenden:
			if ( (y==Spielfeld.getInstance().getStartY()) 
					|| (y==Spielfeld.getInstance().getZielY()) ) {
				fertig = false;
			} // if
		} while(!fertig);
		Spielfeld.getInstance().setTyp(x, y, Feld.TYP_PASSANT);
		bestimmeRichtung();
		bestimmeSpeed();
	} // Konstruktor 2
	
	/**
	 * Ermittlung einer zufaelligen Startposition.
	 */
	private void bestimmeStartPosition() {
		x= (int) (Math.random() * Konfiguration.N);
		y= (int) (Math.random() * Konfiguration.M);
	} // bestimmeStartPosition
	
	/**
	 * Bestimmt die Richtung, in der sich die Figur bewegt:
	 * 0 1 2
	 * 3 * 4
	 * 5 6 7
	 */
	private void bestimmeRichtung() {
		boolean ok = true;
		int zufall;
		do {
			ok = true;
			zufall = (int) (Math.random() * 8);
			if (Konfiguration.NUR_VERTIKAL_UND_HORIZONTAL_DEFAULT) {
				// Bewegung nur in vertikaler und horizontaler Richtung
				if ((zufall == 0) || (zufall == 2) || (zufall == 5)
						|| (zufall == 7)) {
					ok = false;
				} // if
			} // if
			if (Konfiguration.NUR_HORIZONTAL) {
				// Bewegung nur in horizontaler Richtung
				if ((zufall != 3) && (zufall != 4)) {
					ok = false;
				} // if
			} // if
		} while (!ok);

		// System.out.println("+++ Zufall: " + zufall);
		switch (zufall) {
		case 0:
			diffX = -1;
			diffY = -1;
			this.color = Color.yellow;
			bild = Konfiguration.BILD_PASSANT_LINKS;
			break;
		case 1:
			diffX = 0;
			diffY = -1;
			bild = Konfiguration.BILD_PASSANT_OBEN;
			break;
		case 2:
			diffX = +1;
			diffY = -1;
			this.color = Color.yellow;
			bild = Konfiguration.BILD_PASSANT_RECHTS;
			break;
		case 3:
			diffX = -1;
			diffY = 0;
			bild = Konfiguration.BILD_PASSANT_LINKS;
			break;
		case 4:
			diffX = +1;
			diffY = 0;
			bild = Konfiguration.BILD_PASSANT_RECHTS;
			break;
		case 5:
			diffX = -1;
			diffY = +1;
			this.color = Color.yellow;
			bild = Konfiguration.BILD_PASSANT_LINKS;
			break;
		case 6:
			diffX = 0;
			diffY = +1;
			bild = Konfiguration.BILD_PASSANT_LINKS_UNTEN;
			break;
		case 7:
			diffX = +1;
			diffY = +1;
			this.color = Color.yellow;
			bild = Konfiguration.BILD_PASSANT_RECHTS;
			break;
		} // case
	} // bestimmeRichtung

	/**
	 * Zufaellige Festlegung der Geschwindigkeit.
	 */
	private void bestimmeSpeed() {
		speed = (int) (Math.random() * Konfiguration.MAX_SPEED_STUFEN);
		if (speed==0) {
			color = Color.CYAN;
		} // if
	} // bestimmeSpeed

	/**
	 * Setzen der diff-Werte, die die Richtung der Bewegung spezifizieren.
	 * @param diffX Aenderung in x-Richtung (-1, 0, +1).
	 * @param diffY Aenderung in y-Richtung (-1, 0, +1).
	 */
	public void setDiff(int diffX, int diffY) {
		this.diffX = diffX;
		this.diffY = diffY;
	} // setDiff
	
	/**
	 * Setzen der Geschwindigkeit.
	 * (Wird momentan nicht genutzt.)
	 * @param speed Geschwindigkeit (0..Konfiguration.MAX_SPEED_STUFEN-1)
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	} // setSpeed
	
	/**
	 * Liefert die ID (Nr) des Passanten.
	 * @return ID (Nr) des Passanten.
	 */
	public int getNr() {
		return nr;
	} // getNur
	
	/**
	 * Ausgabe der Richtung, in der sich der Passant bewegt,
	 * auf dem Bildschirm.
	 */
	public void ausgabeRichtung() {
		System.out.println("Richtung x/y: " + diffX + "/" + diffY);
	} // ausgabeRichtung
	
	/**
	 * Liefert den Farbwert.
	 * @return Farbwert.
	 */
	public Color getColor() {
		return color;
	} // getColor
	
	/**
	 * Signalisiert, dass eine Kollision zwischen Passant und Spieler
	 * erfolgt ist.
	 * @param x x-Koordinate des Feldes, auf dem der Zusammenprall geschah.
	 * @param y y-Koordinate des Feldes, auf dem der Zusammenprall geschah.
	 */
	public void setKollision(int x, int y) {
		/*
		kollisionen++;
		System.out.println("+++ Anzahl Kollisionen: " + kollisionen);
		if (kollisionen >= 0) {
			modus = MODUS_GAME_OVER;
			modus = MODUS_ZUSAMMENSTOSS;
			erfolgreich = false;
			zusammentreffenBeginn = new Date();
			spieler[0].bild2Zusammenstoss();
			System.out.println("+++ Spieler-Pos: " + spieler[0].x + "," + spieler[0].y);
			System.out.println("+++ Anzahl Passanten: " + figur.length);
		} // if
		*/
	} // setKollision

	/**
	 * Observer-Information
	 * @param o: Zu beobachtendes Objekt
	 * @param obj: (Geaenderter) Datenwert
	 */
	public void update(Observable o, Object obj) {
		if (Spiel.getInstance().getModus() != Spiel.getInstance().MODUS_SPIEL) {
			return;
		} // if
		
		String className = obj.getClass().getSimpleName();
		int i;
		if (className.equals("Integer")) {
			// Relevant fuer Spieler-Interaktion (Unterklasse von Figur):
			i = ((Integer) obj).intValue();
			bewegung(i);
		} else {
			// Bewegung der Figuren:
			update(o);
		} // if
	}

	/**
	 * Observer-Information
	 * @param o: Zu beobachtendes Objekt
	 */
	public void update(Observable o) {
		if (Spiel.getInstance().getModus() != Spiel.getInstance().MODUS_SPIEL) {
			return;
		} // if

		// Instanzen von Spieler ignorieren:
		if (this.getClass().getCanonicalName().endsWith("Spieler")) {
			// System.out.println("+++ update: Spieler-Instanz ignorieren.");
			return;
		} // if

		if (speedZaehler <= 0) {
			
			x += diffX;
			y += diffY;

			// Bisherige Zelle freigeben:
			x -= diffX;
			y -= diffY;
			if ((x >= 0) && (x < Konfiguration.N) && (y >= 0)
					&& (y < Konfiguration.M)) {
				Spielfeld.getInstance().setTyp(x, y, Feld.TYP_FREI);
			} // if
			x += diffX;
			y += diffY;

			// Zelle als belegt kennzeichnen:
			if ((x >= 0) && (x < Konfiguration.N) && (y >= 0)
					&& (y < Konfiguration.M)) {
				Spielfeld.getInstance().setTyp(x, y, Feld.TYP_PASSANT);
			} // if

			// Wenn die Koordinaten aus dem Sichtbereich sind -->
			// Richtungswechsel
			if ((x < 0) || (x > Konfiguration.N) || (y < 0)
					|| (y > Konfiguration.M)) {
				// Richtungswechsel:
				diffX = -diffX;
				diffY = -diffY;
			} // if

			// Kollissionsabfrage:
			if ((x >= 0) && (x <= (Konfiguration.N - 1)) && (y >= 0)
					&& (y <= (Konfiguration.M) - 1)) {
				if ((Spiel.getInstance().spieler[0].x == x)
						&& (Spiel.getInstance().spieler[0].y == y)) {
					// if ((Spielfeld.getInstance().feld[x][y].typ &
					// Feld.TYP_SPIELER) != 0) {
					System.out.println("+++ KOLLISSION");
					System.out.println("+++ An Pos. " + x + "," + y);
					//// Spiel.getInstance().setKollision(x, y);
				} // if
			} // if

			speedZaehler = speed;
		} else {
			speedZaehler--;
		} // if (else)
	} // update

	/**
	 * Aktion, die eintritt, falls bei update ein INTEGER-Datenwert
	 * uebergeben wurde.
	 * (Ist in dieser Klasse nicht relevant.) 
	 * @param taste ASCII-Wert der gedrueckten Taste.
	 */
	protected void bewegung(int taste) {
		// Wird in Subklasse Spieler genutzt.
	} // bewegung
	
} // class Figur
