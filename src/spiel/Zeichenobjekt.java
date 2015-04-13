/**
 * Oberklasse fuer Zeichenobjekte (Felder, Passanten, Spieler).
 * Enthaelt allgemeine Attribute (Position, Farbe, Typ, ...)
 * Bietet Methode zum Zeichnen an.
 */
package spiel;

/**
 * @author clecon
 * @version 1.0; 09/2011 - 29.01.2012
 *
 */
import java.awt.*;
public class Zeichenobjekt extends Component {
	static final long serialVersionUID = 1000L;

	
	// private String bild = Konfiguration.IMAGE_PFLASTERSTEIN;
	protected int x,y;
	/*protected*/ public byte typ = Feld.TYP_FREI;
	protected Color color = Color.DARK_GRAY;
	protected String bild = null;
	protected boolean oval = false; // statt Rechteck Oval zeichnen?
	static protected Graphics graphics = null;
	protected Image img = null;
	
	private Toolkit toolkit;
	
	public Zeichenobjekt() {
		toolkit = getToolkit();
		// Bei Verwendung dieses Konstruktors muss 
		// setGraphics aufgerufen werden!
	} // Konstruktor
	
	public Zeichenobjekt(Graphics g) {
		this();
		if (graphics == null) {
			graphics = g;
		} // if
	} // Konstruktor

	/**
	 * (Wird momentan nicht genutzt.)
	 * @param g
	 */
	static public void setGraphics(Graphics g) {
		graphics = g;
	} // setGraphics
	
	/**
	 * Setzt die x- und y-Koordinaten.
	 * @param x x-Koordinate des grafischen Elements.
	 * @param y y-Koordinate des grafischen Elements.
	 */
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	} // setXY
	
	/**
	 * Liefert den Namen der Bilddatei.
	 * @return Name der Billdatei.
	 */
	public String getBild() {
		return bild;
	} // getBild
	
	/**
	 * Definiert den Namen der Bilddatei.
	 * @param bild Name der Bilddatei.
	 */
	public void setBild(String bild) {
		this.bild = bild;
	} // setBild
	
	/**
	 * Erstellt in Image-Objekt, welches dann
	 * mittels g.drawImage angezeigt werden kann.
	 * @param dateiname Name der Bilddatei.
	 */
	public void erzeugeImageObjekt(String dateiname) {
		img = getToolkit().getImage(dateiname);
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(img, 0);
		try {
			// Warten, bis das Image vollständig geladen ist,
			mt.waitForAll();
		} catch (InterruptedException e) {
			// nothing
		}
		// repaint();
	} // erzeugeImageObjekt

	/**
	 * Hauptmethode zum Zeichnen des grafischen Elements.
	 * Normalerweise wird ein ausgefuelltes Rechteck gezeichnet.
	 * Wenn oval=true, wird ein ausgefuellter Kreis gezeichnet.
	 * Wenn bild!=null, wird anstelle eines Rechtecks bzw. Kreises ein Bild gezeichnet.
	 */
	public void zeichne() {
		// Aus den x- und y-Koordinaten die echten Koordinaten auf dem Bildschirm berechnen:
		int posX = Konfiguration.RAND_X + x * Konfiguration.ZELLENGROESSE;
		int posY = Konfiguration.RAND_Y + y * Konfiguration.ZELLENGROESSE;
		
		// Pruefen ob Rand ueberschritten:
		if ( (x<0) || (x>=Konfiguration.N) || (y<0) || (y>=Konfiguration.M)) {
			// System.out.println("+++ Randueberschreitung!");
			// Nicht zeichnen
			return;
		} else {
			// keine Randueberschreitung
		}
		if (bild != null) {
			if (img == null) {
				// beim ersten Mal Image-Objekt erstellen:
				erzeugeImageObjekt(bild);
			} // if
		} // if
			
		// Bild zeichnen:
		if (bild != null) {
			// Das Bild wird auf die Zellengroesse skaliert:
			graphics.drawImage(img, posX, posY, Konfiguration.ZELLENGROESSE, Konfiguration.ZELLENGROESSE, this);
			// graphics.drawImage(img, posX, posY, this);
		} else {
			graphics.setColor(color);
			if (oval) {
				graphics.fillOval(posX, posY, Konfiguration.ZELLENGROESSE,
						Konfiguration.ZELLENGROESSE);
			} else {
				graphics.fill3DRect(posX, posY, Konfiguration.ZELLENGROESSE,
						Konfiguration.ZELLENGROESSE, true);
			} // if (else)
		} // if (else)
		
		// DEBUG: Ausgabe Feldtypen:
		/*
		Byte by=new Byte(typ);
		Font font = new Font("Arial", Font.PLAIN, 12);
	    graphics.setFont(font);
	    graphics.setColor(Color.red);
		graphics.drawString(by.toString(), posX, posY+Konfiguration.ZELLENGROESSE);
		*/
		Spiel.getInstance().repaint();
	} // zeichne
	
} // class Zeichenobjekt
