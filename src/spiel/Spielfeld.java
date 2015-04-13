/**
 * Spielfeld fuer das Spiel (GUI).
 */
package spiel;

/**
 * @author clecon
 * @version 1.0; 09/2011 - 03.02.2012
 *
 */
import java.awt.*;

public class Spielfeld {
	
	private static Spielfeld spielfeldInstanz = null;
	public /*private*/  Feld[][] feld = new Feld[Konfiguration.N][Konfiguration.M];
	public int startX, startY, zielX, zielY;
	private int kollision_vorher_x = -1, kollision_vorher_y = -1;

	/**
	 * Konstruktor.
	 */
	private Spielfeld() {
	} // Konstruktor

	/**
	 * Liefert die (einzige) Instanz der Klasse Spielfeld.
	 * @return Referenz auf die Instanz der Klasse Spielfeld.
	 */
	static public Spielfeld getInstance() {
		if (spielfeldInstanz == null) {
			spielfeldInstanz = new Spielfeld();
		} // if
		return spielfeldInstanz;
	} // getInstance


	/**
	 * Zerstoert die Instanz des Spielfeldes.
	 */
	public void destroy() {
		spielfeldInstanz = null;
	} // destroy
	
	/**
	 * Initialisierung des Spielfeldes mit Feldern.
	 * Alle Felder sind anfangs leer.
	 */
	public void initFeld() {
		feld = new Feld[Konfiguration.N][Konfiguration.M];
		for (int i=0; i<Konfiguration.N; i++) {
			for (int j=0; j<Konfiguration.M; j++) {
				feld[i][j] = new Feld();
				feld[i][j].setXY(i, j);
				feld[i][j].typ = Feld.TYP_FREI;
			} // for (j)
		} // for (i)
	} // initFeld
	
	/**
	 * Prueft, ob das angebenen Feld frei ist.
	 * @param x x-Koordinate.
	 * @param y y-Koordinate.
	 * @return true: Feld ist frei; sonst false.
	 */
	public boolean isFrei(int x, int y) {
		return (feld[x][y].typ == Feld.TYP_FREI);
	} // isFrei
	
	/**
	 * Prueft, ob das Feld an der Position (x,y) ein Start-
	 * oder Zielfeld ist.
	 * (Wird benoetigt, wenn die Passanten zufaellig auf
	 * dem Spielfeld verteilt werden; die Passanten sollten
	 * anfangs nicht auf einem Start- oder Zielfeld stehen.
	 * @param x 
	 * @param y
	 * @return true: Es handelt sich um ein Start- oder Zielfeld; sonst false.
	 */
	public boolean isStartOderZielfeld(int x, int y) {
		boolean b1, b2;
		b1 = (x == startX);
		b1 &= (y == startY);
		
		b2 = (x == zielX);
		b2 &= (y == zielY);
		
		return b1 || b2;
	} // isStartOderZielfeld
	
	/**
	 * Die Start- und Zielfelder werden zufaellig ausgewaehlt.
	 * Das Startfeld befindet sich immer in der ersten, das
	 * Zielfeld immer in der letzten Zeile.
	 */
	public void bestimmeStartUndZielFelder() {
		startX = (int) (Math.random()* Konfiguration.N);
		startY = 0;
		zielX =  (int) (Math.random()* Konfiguration.N);
		zielY = Konfiguration.M -1;
		feld[startX][startY].color = Konfiguration.FARBE_STARTFELD;
		feld[startX][startY].bild = null;
		feld[zielX][zielY].color = Konfiguration.FARBE_ZIELFELD;
		feld[zielX][zielY].bild = null;
		feld[startX][startY].typ = Feld.TYP_START_ZIEL;
		feld[zielX][zielY].typ = Feld.TYP_START_ZIEL;
		// Spieler startet an Startfeld:
	} // bestimmeStartUndZielFelder
	
	
	/**
	 * Prueft, ob das Feld an der Position (x,y)
	 * das Zielfeld ist.
	 * @param x
	 * @param y
	 * @return true: Das Feld ist das Zielfeld; sonst false.
	 */
	public boolean isZielfeld(int x, int y) {
		return ( (x==zielX) && (y == zielY) );
	} // isZielfeld
	
	public boolean zielErreicht(int spielerX, int spielerY) {
		return ( (spielerX == zielX) && (spielerY == zielY) );
	} // zielErreicht
	
	/**
	 * Liefert die x-Koordinate des Startfeldes.
	 * @return x-Koordinate des Startfeldes.
	 */
	public int getStartX() {
		return startX;
	} // getStartX
	
	/**
	 * Liefert die y-Koordinate des Startfeldes.
	 * @return y-Koordinate des Startfeldes.
	 */
	public int getStartY() {
		return startY;
	} // getStartY
	
	/**
	 * Liefert die x-Koordinate des Zielfeldes.
	 * @return x-Koordinate des Zielfeldes.
	 */
	public int getZielX() {
		return zielX;
	} // getZielX
	
	/**
	 * Liefert die y-Koordinate des Zielfeldes.
	 * @return y-Koordinate des Zielfeldes.
	 */
	public int getZielY() {
		return zielY;
	} // getZielY
	
	/**
	 * Zeichenmethode: Zeichnet alle Felder des Spielfeldes. 
	 * @param g Graphics-Objekt.
	 */
	public void zeichneFeld(Graphics g) {
		// public void zeichneFeld(Graphics g, Feld[][] feld) {
		for (int i=0; i<Konfiguration.N; i++) {
			for (int j=0; j<Konfiguration.M; j++) {
				feld[i][j].zeichne();
			} // for (j)
		} // for (i)
		Spiel.getInstance().repaint();
	} // zeichneFeld

	/**
	 * Prueft, ob auf dem Feld an der Position (x,y)
	 * ein Passant steht. 
	 * @param x x-Koordinate.
	 * @param y y-Koordinate.
	 * @return true: Auf dem Feld befindet sich ein Passant; false: sonst.
	 */
	public boolean isPassant(int x, int y) {
		if ((feld[x][y].typ & Feld.TYP_PASSANT) != 0) {
			System.out.println("+++ Typ: " + feld[x][y].typ);
		} // if
		return ((feld[x][y].typ & Feld.TYP_PASSANT) != 0);
	} // isPassant
	
	/**
	 * Prueft, ob auf dem Feld an der Position (x,y)
	 * ein Passant steht. 
	 * @param x x-Koordinate.
	 * @param y y-Koordinate.
	 * @return true: Auf dem Feld befindet sich ein Passant; false: sonst.
	 */
	public boolean isSpieler(int x, int y) {
		return ((feld[x][y].typ & Feld.TYP_SPIELER) != 0);
	} // isSpieler
	
	/**
	 * Legt fest, was auf dem Feld fuer ein Objekt steht (Typ).
	 * @param x x-Koordinate im Spielfeld.
	 * @param y y-Koordinate im Spielfeld.
	 * @param typ (Die moeglichen Werte befinden sich als
	 * Konstanten in der Klasse Zeichenobjekt.)
	 */
	public void setTyp(int x, int y, byte typ) {
		if (typ == Feld.TYP_FREI) {
			// Wenn das Feld frei gegeben wird, werden alle Belegungen
			// geloescht.
			setTypFrei(x, y);
		} else {
			feld[x][y].typ |= typ;
		} // if (else)
	} // setBelegung

	/**
	 * Markiert das Feld an der Position (x,y) als freies Feld.
	 * @param x x-Koordinate.
	 * @param y y-Koordinate.
	 */
	public void setTypFrei(int x, int y) {
		feld[x][y].typ = Feld.TYP_FREI;
	} // setBelegungFrei
	
	/**
	 * Prueft, ob es irgendwo im Feld eine Kollision
	 * zwischen Passant und Spieler gibt.
	 * @return Zeile, in der die Kollision stattfunden hat;
	 * -1, falls keine Kollision detektiert wurde.
	 */
	public int isKollision() {
		for (int i=0; i<feld.length; i++) {
			for (int j=0; j<feld[i].length; j++) {
				if (feld[i][j].typ == (Feld.TYP_PASSANT | Feld.TYP_SPIELER)) {
					System.out.println("+++ isKollision bei " + i +"," + j);
					System.out.println("+++ typ: " + feld[i][j].typ);
					return i;
				} // if
			} // for (j)
		} // for (i)
		return -1;
	} // isKollision
	
	/**
	 * Prueft, ob Spieler mit Passant zusammengestossen ist.
	 * Wenn zweimal hintereinander die gleiche Kollisiondetektion
	 * erfolgt, wird nur die erste gezaehlt. 
	 * @return
	 */
	public boolean isSpielerkollision() {
		for (int i = 0; i < feld.length; i++) {
			for (int j = 0; j < feld[i].length; j++) {
				if ((feld[i][j].typ & Feld.TYP_SPIELER) != 0) {
					// System.out.println("+++ Spielerfeld an " + i + "," + j);
					if ((feld[i][j].typ & Feld.TYP_PASSANT) != 0) {
						if ((i == kollision_vorher_x)
								&& (j == kollision_vorher_y)) {
							return false;
						} else {
							kollision_vorher_x = i;
							kollision_vorher_y = j;
							return true;
						}
					} else {
						return false;
					}
				} // if
			} // for (j)
		} // for (i)
		return false;
	} // isSpielerkollision
	
	/**
	 * Prueft, ob Spieler mit Passant zusammengestossen ist.
	 * Wenn zweimal hintereinander die gleiche Kollisiondetektion
	 * erfolgt, wird nur die erste gezaehlt. 
	 * @return
	 */
	public boolean _isSpielerkollision(int spielerX, int spielerY) {
		for (int i = 0; i < feld.length; i++) {
			for (int j = 0; j < feld[i].length; j++) {
				if ((feld[i][j].typ & Feld.TYP_PASSANT) != 0) {
					if ((i == spielerX)
							&& (j == spielerY)) {
						System.out.println("+++ An Position (" + i +"," + j +") ist Spieler("
								+spielerX + "," + spielerY +")");
						System.out.println("+++ typ: " + feld[i][j].typ);
						return true;
					} // if
				} // if
			} // for (j)
		} // for (i)
		return false;
	} // _isSpielerkollison

	/**
	 * Liefert eine formatierte Zeichenkette, um 
	 * Zahlen mit gleich vielen Zeichen darstellen zu k&ouml;nnen.
	 * @param i Zahl, die dargestellt werden soll.
	 * @param stellen Anzahl der Stellen.
	 * @return Formatierte Zahl als Zeichenkette.
	 */
	private String format(int i, int stellen) {
		String result = new String();
		result = new Integer(i).toString();
		while (result.length() < stellen) {
			result = " " + result;
		} // while
		return result;
	} // format
	
	/**
	 * Debug:
	 * Gibt den Inhalt des Spielfeldes aus.
	 */
	public void ausgabeFeld() {
		for (int i = 0; i < feld.length; i++) {
			for (int j = 0; j < feld[i].length; j++) {
				System.out.print(feld[i][j].typ+" ");
			} // for (j)
			System.out.println();
		} // for (i)
	} // ausgabeFeld
	
	/**
	 * Debug:
	 * Gibt den Inhalt der Spielfeldes mit formatierten
	 * Zahlenwerten aus.
	 */
	public void ausgabeFeldFormat() {
		int anzahl=0;
		System.out.print("    ");
		for (int i=0; i<feld[0].length; i++) {
			System.out.print(format(i,2) + " ");
		} // for
		System.out.println();
		
		for (int i = 0; i < feld.length; i++) {
			System.out.print(format(i,2) + ": ");
			for (int j = 0; j < feld[i].length; j++) {
				System.out.print(format(feld[i][j].typ,2)+" ");
				if (feld[i][j].typ == Feld.TYP_PASSANT) {
					anzahl++;
				} // if
			} // for (j)
			System.out.println();
		} // for (i)
		System.out.println("Anzahl Passantenfelder: " + anzahl);
	} // ausgabeFeld
	
	/**
	 * Test-Routine.
	 * @param args
	 */
	public static void main(String args[]) {
	} // main
	
} // class Spielfeld
