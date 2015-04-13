/**
 * Hauptklasse fuer das Spiel
 * Hier erfolgt die graphische Ausgabe.
 */
package spiel;

/**
 * @author clecon
 * @version 1.0; 09/2011 - 17.02.2012
 *
 */
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class Spiel extends Frame implements Observer {
	public static final long serialVersionUID = 1000L;

	public final static int MODUS_START = 0;
	public final static int MODUS_SPIEL = 1;
	public final static int MODUS_ZUSAMMENSTOSS = 2;
	public final static int MODUS_GAME_OVER = 3;
	public final static int MODUS_FINAL = 4;
	
	private static int modus = MODUS_START;
	private boolean erfolgreich = false;
	
	protected Observable ticker;
	protected Figur[] figur;
	protected Spieler[] spieler;
	private int kollisionen = 0;
	private long startZeit, zielZeit, spieldauer;
	private boolean lock = false;
	private int runde = 0; // Anzahl der Spielrunden
	
	private long punkte = 0;
	private int versuche = 0;
	private int level = 1;
	private Vector<Long> spielzeiten = new Vector<Long>();
	
	// Hintergrundmusik:
	Thread audio = new Audio(Konfiguration.AUDIO_DATEINAME);

	// Verhalten bei Zusammenstoss:
	Date zusammentreffenBeginn;

	// Highscore:
	HighscoreClientRMI highscore = new HighscoreClientRMI(Konfiguration.HIGHSCORE_URL);
	
	// Singleton:
	static Spiel spielInstanz = null;
	
	/**
	 * Konstruktor
	 * Erstellen der Oberflaeche
	 */
	private Spiel(Observable ticker) {
		super("Spiel");
		
		// Observer:
		this.ticker = ticker;
		ticker.addObserver(this);
		
		// Groesse der Zeichenflaeche bestimmen:
		int dimX = Konfiguration.RAND_X + (Konfiguration.N * Konfiguration.ZELLENGROESSE) + Konfiguration.RAND_X;
		int dimY = Konfiguration.RAND_Y + (Konfiguration.M * Konfiguration.ZELLENGROESSE) + Konfiguration.RAND_Y;
		setSize(dimX, dimY);
		
		// Initialsierung von Feldern, Passanten, Spieler:
		init();
		setVisible(true);
		
		// Fenster schliessbar machen:
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				dispose();
				System.exit(0);
			} // windowClosing
		}); // WindowAdapter
		
		// Controller anschliessen:
		addKeyListener(new Controller((Ticker) ticker, this));
	} // Konstuktor
	
	/**
	 * Liefert die (einzige) Instanz der Klasse Spiel.
	 * @param ticker Observable
	 * @return Referenz auf die Instanz der Klasse Spiel.
	 */
	public static Spiel getInstance(Observable ticker) {
		if (spielInstanz == null) {
			spielInstanz = new Spiel(ticker);
		} // if
		return spielInstanz;
	} // if
	
	/**
	 * Liefert die (einzige) Instanz der Klasse Spiel.
	 * @return Referenz auf die Instanz der Klasse Spiel.
	 */
	public static Spiel getInstance() {
		if (spielInstanz == null) {
			System.err.println("Noch keine Spiel-Instanz erstellt!");
			System.exit(-1);
		} // if
		return spielInstanz;
	} // if
	
	/**
	 * Liefert den aktuellen Spiel-Modus (Start, Spiel, Game Over, Ende).
	 * @return Modus des Spiels.
	 */
	public int getModus() {
		return modus;
	} // getModus
	
	public boolean locked() {
		return lock;
	} // locked

	/**
	 * Initialisierung der Datenstrukturen Spielfeld, Felder, Passanten, Spieler.
	 */
	protected void init() {
		
		// Ggf. Schwierigkeitsgrad erhoehen:
		if ( (runde>=1) && (erfolgreich)) {
			Konfiguration.ANZAHL_FIGUREN += Konfiguration.ANZAHL_FIGUREN_ERHOEHUNG;
			Konfiguration.AENDERUNGS_FREQUENZ -= Konfiguration.AENDERUNGSFREQUENZ_ERHOEHUNG;
			// Wenn Takt = 0 --> Korrektur:
			if (Konfiguration.AENDERUNGS_FREQUENZ == 0) {
				Konfiguration.AENDERUNGS_FREQUENZ += Konfiguration.AENDERUNGSFREQUENZ_ERHOEHUNG;
			} // if
		} // if
		
		// Feld-Elemente initialisieren:
		Spielfeld.getInstance().initFeld();
		Spielfeld.getInstance().bestimmeStartUndZielFelder();
		
		// Figuren initialisieren:
		// Test, ob Spielfeld fuer die Anzahl der Figuren ausreicht:
		if (Konfiguration.ANZAHL_FIGUREN > (Konfiguration.N * Konfiguration.M)-Konfiguration.ANZAHL_SPIELER-2) {
			// -2: Position von Start- und Zielfeld
			System.err.println("FEHLER: Zu viele Passanten fuer die Spielfeldgroesse!");
			System.out.println("Anzahl Passanten: " + Konfiguration.ANZAHL_FIGUREN);
			System.out.println("Anzahl Felder: " + Konfiguration.N*Konfiguration.M);
			System.out.println("Das Programm wird nun beendet.");
			System.exit(-1);
		} // if
		
		// Anbindung an Observer aktualisieren:
		ticker.deleteObservers();
		ticker.addObserver(this);

		figur = new Figur[Konfiguration.ANZAHL_FIGUREN];
		for (int i=0; i<figur.length; i++) {
			figur[i] = new Figur(ticker, Color.red);
		} // for
		
		// Spieler initialisieren:
		spieler = new Spieler[Konfiguration.ANZAHL_SPIELER];
		for (int i=0; i<Konfiguration.ANZAHL_SPIELER; i++) {
			spieler[i] = new Spieler(ticker);
		} // for
		startZeit = new Date().getTime();
		erfolgreich = false;
		kollisionen = 0;
		
		runde++; // Die naechste Runde beginnt...
		
	} // init
	

	/**
	 * Signalisiert, dass eine Kollision erfolgte.
	 */
	public void setKollision() {
		kollisionen++;
		if (kollisionen >= 0) {
			modus = MODUS_GAME_OVER;
			modus = MODUS_ZUSAMMENSTOSS;
			erfolgreich = false;
		} // if
	} // setKollision
	
	/**
	 * Signalisiert, dass eine Kollision zwischen Passant und Spieler
	 * erfolgt ist.
	 * @param x x-Koordinate des Feldes, auf dem der Zusammenprall geschah.
	 * @param y y-Koordinate des Feldes, auf dem der Zusammenprall geschah.
	 */
	public void setKollision(int x, int y) {
		/*
		kollisionen++;
		if (kollisionen >= 0) {
			modus = MODUS_GAME_OVER;
			modus = MODUS_ZUSAMMENSTOSS;
			erfolgreich = false;
			zusammentreffenBeginn = new Date();
			spieler[0].bild2Zusammenstoss();
		} // if
		*/
	} // setKollision
	
	/**
	 * Signalisiert, dass eine Kollision zwischen Passant und Spieler
	 * erfolgt ist.
	 * @param x x-Koordinate des Feldes, auf dem der Zusammenprall geschah.
	 * @param y y-Koordinate des Feldes, auf dem der Zusammenprall geschah.
	 */
	public void setKollision(int zeile) {
		kollisionen++;
		if (kollisionen >= 0) {
			modus = MODUS_GAME_OVER;
			modus = MODUS_ZUSAMMENSTOSS;
			erfolgreich = false;
			zusammentreffenBeginn = new Date();
			spieler[0].bild2Zusammenstoss();
		} // if

	} // setKollision
	
	boolean first = true;

	/**
	 * Zeichnnen des Start-Bildschirms.
	 * @param g Graphics-Objekt zum Zeichnen.
	 */
	private void zeichneStart(Graphics g) {
		Font font = new Font("Arial", Font.PLAIN, 40);
	    g.setFont(font);
	    g.setColor(Color.green);
		g.drawString("Start des Spiels", 200, 400);
		g.drawString("durch Drücken von <ENTER>", 200, 440);
		
		font = new Font("Arial", Font.PLAIN, 20);
	    g.setFont(font);
	    g.setColor(Color.black);
		g.drawString("Musik an/aus durch Drücken von <m>", 200, 480);
	} // zeichneStart
	
	/**
	 * Zeichnen des Ende-Bildschirms mit der Möglichkeit, fortzufahren.
	 * @param g Graphics-Objekt zum Zeichnen.
	 */
	private void zeichneGameOver(Graphics g) {
		String s1 = new String(), s2=new String(), s3=new String();
		int restVersuche = Konfiguration.MAX_ANZAHL_VERSUCHE - versuche;
		
		Font fontRunde = new Font("Arial", Font.PLAIN, 24);
	    g.setFont(fontRunde);
	    g.setColor(Color.black);
	    s3 = "Runde" + runde;
	    s3 += ", Level " + level;
	    s3 += " (noch " + restVersuche + ((restVersuche>1)?" Versuche)":" Versuch)");
		g.drawString(s3, 200, 300);
		if (erfolgreich) {
			level++;
		} // if
	    
		Font font = new Font("Arial", Font.PLAIN, 60);
	    g.setFont(font);
	    g.setColor(Color.red);
	    s1 = (erfolgreich? "GESCHAFFT!" : "Runde vorbei");
		g.drawString(s1, 200, 400);
		
		Font font2 = new Font("Arial", Font.PLAIN, 24);
		g.setFont(font2);
		g.setColor(Color.blue);
		s2 = (erfolgreich ? "Ziel erreicht!" : "Leider verloren!");
		g.drawString(s2, 200, 480);
		g.drawString("Anzahl Züge: " + spieler[0].getAnzahlZuege(), 200, 520);
		
		zielZeit = new Date().getTime();
		spieldauer = zielZeit- startZeit;
		spielzeiten.add(new Long(spieldauer));
		
		g.drawString("Zeit: " + ((zielZeit-startZeit)/1000) + " Sekunden.", 200, 560);
		
		// Forfuehrung:
		g.drawString("Noch mal: (ENTER)", 200, 600);
		g.drawString("Ende: (X)", 200, 630);
		
	} // zeichneGameOver

	/**
	 * Berechnung der erreichten Punkte.
	 * @return Erreichte Punkte.
	 */
	private long berechnePunkte() {
		/* Die Punkte berechnen sich aus erreichten Level * 1000 
		 * geteilt durch die durchschnittliche Zeit zur 
		 * Bewaeltigung eines Spiels.
		 */
		long punkte = level*1000L, zeit=0L;
		
		// Ausrechnen Gesamtspielzeit:
		for (int i=0; i<spielzeiten.size(); i++) {
			zeit += spielzeiten.elementAt(i).longValue();
		} // for
		
		zeit /= spielzeiten.size(); // Durchschnitt aller Zeiten
		punkte /= (zeit/1000); // durch Sekunden teilen
		return punkte;
	} // berechnePunkte
	
	/**
	 * Zeichnen des Ende-Bildschirms. Hier endet das Spiel.
	 * @param g Graphics-Objekt zum Zeichnen.
	 */
	private void zeichneGameFinal(Graphics g) {
		String s1 = new String();
		Font font = new Font("Arial", Font.PLAIN, 60);
	    g.setFont(font);
	    g.setColor(Color.red);
	    s1 = "GAME OVER";
		g.drawString(s1, 200, 400);

		font = new Font("Arial", Font.PLAIN, 30);
	    g.setFont(font);
	    g.setColor(Color.blue);
		punkte = berechnePunkte();
		g.drawString("Punkte: " + punkte, 200, 440);
	    
		// Highscore:
		highscore.neuerEintrag((int) punkte);
		font = new Font("Arial", Font.PLAIN, 20);
	    g.setFont(font);
		g.drawString("Highscore", 200, 480);
	    g.setColor(Color.black);
		HighscoreEintrag[] he = highscore.getHighscore();
		if (he.length == 0) {
			g.drawString("(Highscore-Liste nicht vorhanden)", 200, 510);
		} else {
			for (int i = 0; i < he.length; i++) {
				g.drawString(new Integer(he[i].punkte).toString(), 200,
						510 + i * 20);
			} // for
		} // if (else)
		
		// Forfuehrung:
		g.drawString("Ende: (X)", 200, 510+he.length*20+40);
		
	} // zeichneGameFinal

	/**
	 * Schaltet Hintergrundmusik aus bzw. wieder an.
	 */
	private void soundAnAus() {
		if (audio.isAlive()) {
			((Audio) audio).stoppen();
		} else {
			audio = new Audio(Konfiguration.AUDIO_DATEINAME);
			audio.start();
		} // if (else)
	} // soundAnAus
	
	/**
	 * Hauptzeichnen-Methode.
	 * Diese Methode wird staendig aufgerufen.
	 */
	public void paint(Graphics g) {
		lock = true; // Interaktion unterbinden
		
		// Zeichnen des Spielfeldes:
		if (first) {
			Zeichenobjekt.setGraphics(g);
		} // if

		if (modus == MODUS_GAME_OVER) {
			if (!erfolgreich) {
				versuche++;
			} // if
			if (versuche >= Konfiguration.MAX_ANZAHL_VERSUCHE) {
				modus = MODUS_FINAL;
				zeichneGameFinal(g);
			} else {
				zeichneGameOver(g);
			} // if (else)
			lock = false;
			return;
		} // if

		// Zustand Start?
		if (modus == MODUS_START) {
			zeichneStart(g);
			repaint();
			lock = false;
			return;
		} // if

		// Kollisionsdetektion:
		if (modus == MODUS_ZUSAMMENSTOSS) {
			// Wird schon behandelt...
		} else {
			int zeile = Spielfeld.getInstance().isKollision();
			if (zeile != -1) {
				setKollision(zeile);
			} // if
		} // if (else)
		
		// Ziel erreicht?
		if (Spielfeld.getInstance().zielErreicht(spieler[0].x, spieler[0].y)) {
			erfolgreich = true;
			modus = MODUS_GAME_OVER;
			lock = false;
			repaint();
			return;
		} // if

		// Zelle fuer Spieler frei?
		if (modus == MODUS_ZUSAMMENSTOSS) {
			// Einmal Kollision behandeln, reicht...
		} else {
			boolean ok = (!(Spielfeld.getInstance().feld[spieler[0].x][spieler[0].y].typ == Feld.TYP_PASSANT));
			if (!ok) {
				//// setKollision(spieler[0].x, spieler[0].y);
			} // if
		} // if
		
		// Zustand Spiel?
		if ( (modus == MODUS_SPIEL) || (modus == MODUS_ZUSAMMENSTOSS)) {
			// if (modus == MODUS_SPIEL) {

			// Zeichnen des Spielfeldes:
			Spielfeld.getInstance().zeichneFeld(g);

			// Zeichnen der Figuren:
			for (int i = 0; i < figur.length; i++) {
				figur[i].zeichne();
			} // for

			// Zeichnen der Spieler:
			for (int i = 0; i < spieler.length; i++) {
				spieler[i].zeichne();
			} // for

			// Zusammenstoss nur begrenzte Zeit anzeigen:
			if (modus == MODUS_ZUSAMMENSTOSS) {
				Date date = new Date();
				if (date.getTime()-zusammentreffenBeginn.getTime()
						>= Konfiguration.ZUSAMMENSTOSS_ANZEIGEDAUER) {
					modus = MODUS_GAME_OVER;
					spieler[0].bildNormal();
				} // if
			} // if
		} // if (modus)

		lock = false; // Interaktion wieder zulassen
		
	} // paint
	
	// --- Double-Buffering fuer flackerlose Darstellung:
	private Image dbImage;
	private Graphics dbGraphics;

	/**
	 * &Uuml;berschreiben der update-Methode
	 * (f&uuml;r Double Buffering)
	 */
	public void update(Graphics g) {
	   //Double-Buffer initialisieren
	   if (dbImage == null) {
	      dbImage = createImage(this.size().width,this.size().height);
	      dbGraphics = dbImage.getGraphics();
	   }
	   //Hintergrund löschen
	   dbGraphics.setColor(getBackground());
	   dbGraphics.fillRect(0,0,this.size().width,this.size().height);
	   //Vordergrund zeichnen
	   dbGraphics.setColor(getForeground());
	   paint(dbGraphics);
	   //Offscreen anzeigen
	   g.drawImage(dbImage,0,0,this);
	} // update
	
	/**
	 * Observer-Information
	 * @param o: Zu beobachtendes Objekt
	 * @param obj: (Geaenderter) Datenwert
	 */
	public void update(Observable o, Object obj) {
		String className = obj.getClass().getSimpleName();
		int i;
		if (className.equals("Integer")) {
			i = ((Integer) obj).intValue();
			behandleEvent(i);
		} else {
			// betrifft mich nicht...
		} // if (else)
	} // update

	/**
	 * Massnahmen, falls eine Taste gedrueckt worden ist.
	 * @param taste ASCII-Wert der gedrueckten Taste.
	 */
	private void behandleEvent(int taste) {
		switch(modus) {
		case MODUS_START:
			modus = MODUS_SPIEL;
			audio.start();
			break;
		case MODUS_SPIEL:
			if (taste == Controller.TASTE_M) {
				soundAnAus();
				break;
			} // if
			if (taste == Controller.TASTE_X) {
				System.out.println("ENDE!");
				System.out.println("Anzahl Zuege: "
						+ spieler[0].getAnzahlZuege());
				System.out.println("Anzahl Kollisionen: " + kollisionen);
				zielZeit = new Date().getTime();
				spieldauer = zielZeit - startZeit;
				System.out.println("Zeit: " + (zielZeit - startZeit) / 1000
						+ " Sekunden.");
				System.exit(0);
			} // if
		case MODUS_GAME_OVER:
			if (taste == Controller.TASTE_ENTER) {
				init();
				modus = MODUS_SPIEL;
				repaint();
				break;
			} // if
			if (taste == Controller.TASTE_X) {
				System.exit(0);
			} // if
			break;
		case MODUS_FINAL:
			if (taste == Controller.TASTE_X) {
				System.exit(0);
			} // if
		} // case
	} // behandleEvent
	
	/**
	 * Liefert die Anzahl der Kollisionen.
	 * @return Anzahl der Kollisionen.
	 */
	public int getKollisionen() {
		return kollisionen;
	} // getKollisionen
	
	/**
	 * Liefert die Spieldauer (in Sekunden).
	 * @return Spieldauer (in Sekunden).
	 */
	public long getSpieldauer() {
		return spieldauer / 1000;
	} // getSpieldaeuer
	
} // class Spiel
