/**
 * Ein Feld des Spielfeldes (als graphische Darstellung).
 */
package spiel;

/**
 * @author clecon
 * @version 1.0; 09/2011 - 17.02.2012
 *
 */
public class Feld extends Zeichenobjekt {
	static final long serialVersionUID = 1L;
	
	String name = new String();

	// Konstanten:
	static protected byte TYP_FREI = 0;
	static protected byte TYP_PASSANT =1;
	static protected byte TYP_SPIELER =2;
	static protected byte TYP_START_ZIEL = 4;
	
	public Feld() {
		color = Konfiguration.FARBE_FELD;
		typ = TYP_FREI;
		bild = Konfiguration.BILD_PFLASTERSTEIN;
	} // Konstruktor
	
	public Feld(String name) {
		this();
		this.name = name;
	} // Konstruktor
	
} // class Feld
