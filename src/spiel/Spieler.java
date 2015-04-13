/**
 * Spielerfigur.
 */
package spiel;

import java.util.Observable;

/**
 * @author clecon
 * @version 1.0; 09/2011 - 02.02.2011
 *
 */
public class Spieler extends Figur {
	static final long serialVersionUID = 1L;

	static private int lfdNr = 0;
	private int nr;
	private int anzahlZuege = 0;
	
	public Spieler(Observable ticker) {
		nr = ++lfdNr;
		ticker.addObserver(this);
		this.ticker = ticker;
		color = Konfiguration.FARBE_SPIELER_DEFAULT;
		bild = Konfiguration.BILD_SPIELER;
		// Startposition wird in Spielfeld festgelegt (=Startfeld)
		bestimmeStartPosition();
		oval = false;
	} // Konstruktor

	/**
	 * Tauscht das normale Spielerbild mit einem Bild aus,
	 * das den Zusammenstoss darstellt.
	 */
	public void bild2Zusammenstoss() {
		bild = Konfiguration.BILD_ZUSAMMENSTOSS;
		img = null; // damit neues Image-Objekt erzeugt werden kann
		System.out.println("+++ Spieler.bild: " + bild);
	} // bild2Zusammenstoss
	
	/**
	 * Setzt das Bild fuer den Spieler wieder
	 * auf den Standard-Wert.
	 */
	public void bildNormal() {
		bild = Konfiguration.BILD_SPIELER;
		img = null; // damit neues Image-Objekt erzeugt werden kann
		System.out.println("+++ Spieler.bild: " + bild);
	} // bildNormal
	
	/**
	 * Die Startposition wird in der Klasse Spielfeld ermittelt und wird
	 * von dort abgerufen.
	 * des Spielfeldes (Klasse Spielfeld) bestimmt.
	 */
	private void bestimmeStartPosition() {
		x = Spielfeld.getInstance().getStartX();
		y = Spielfeld.getInstance().getStartY();
	} // bestimmeStartPosition

	/**
	 * Ueberschriebene Methode zur Steuerung des Spielers.
	 * @param taste: ASCII-Code der gedrueckten Taste.
	 */
	protected void bewegung(int taste) {
		switch (taste) {
		case 38: // Controller.TASTE_CURSOR_UP: // Cursor up
			if ((y - 1) >= 0) {
				Spielfeld.getInstance().setTyp(x, y, Feld.TYP_FREI);
				y--;
				Spielfeld.getInstance().setTyp(x, y, Feld.TYP_SPIELER);
				anzahlZuege++;
			} // if
			break;
		case 40: // Cursor down
			if ((y + 1) < Konfiguration.M) {
				Spielfeld.getInstance().setTyp(x, y, Feld.TYP_FREI);
				y++;
				Spielfeld.getInstance().setTyp(x, y, Feld.TYP_SPIELER);
				anzahlZuege++;
			} // if
			break;
		case 37: // Cursor left
			if ((x - 1) >= 0) {
				Spielfeld.getInstance().setTyp(x, y, Feld.TYP_FREI);
				x--;
				Spielfeld.getInstance().setTyp(x, y, Feld.TYP_SPIELER);
				anzahlZuege++;
			} // if
			break;
		case 39: // Cursor right
			if ((x + 1) < Konfiguration.N) {
				Spielfeld.getInstance().setTyp(x, y, Feld.TYP_FREI);
				x++;
				Spielfeld.getInstance().setTyp(x, y, Feld.TYP_SPIELER);
				anzahlZuege++;
			} // if
			break;
		default:
			// keine Bewegung
		} // case
		
		/* (Pruefung auf Kollision nun in Klasse Spiel)
 		if (Spielfeld.getInstance().isPassant(x,y)) {  
			System.out.println("+++ Kolllission von Spieler!");
			Spiel.getInstance().setKollision(x,y);
		} // if
		*/
		
		Spiel.getInstance().repaint();
	} // bewegung
	

	/**
	 * Liefert die Anzahl der Zuege.
	 * Ein Zug wird gemacht, wenn eine Taste gedrueckt und in der
	 * Methode bewegung bearbeitet worden ist. 
	 * Bleibt die Figur trotz Tastendruck an der gleichen Stelle stehen,
	 * wird die Anzahl der Zuege nicht hochgezaehlt.
	 * @return
	 */
	public int getAnzahlZuege() {
		return anzahlZuege;
	} // getAnzahlZuege
 
} // class Spieler
