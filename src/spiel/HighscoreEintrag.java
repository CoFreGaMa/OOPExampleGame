/**
 * Ein Eintrag in der Highscore-Liste.
 */
package spiel;

/**
 * @author clecon
 * @version 1.0; 06.02.2012
 *
 */
import java.util.Date;
import java.io.Serializable;

public class HighscoreEintrag implements Serializable {
	static final long serialVersionUID = 1L;

	public Date date;
	public int punkte;
	
	/**
	 * Erzeugung eines neuen Highscore-Eintrags.
	 * @param date Erzeugungsdatum.
	 * @param punkte Erreichte Punkte.
	 */
	public HighscoreEintrag(Date date, int punkte) {
		this.date = date;
		this.punkte = punkte;
	} // Konstruktor
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	} // main

} // class HighscoreEintrag
