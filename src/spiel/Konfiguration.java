/**
 * Festlegung von Konfiguration des Spiels
 */
package spiel;

import java.awt.Color;

/**
 * @author clecon
 * @version 1.0; 09/2011 - 15.02.2012
 *
 */
public class Konfiguration {

	// ------------------------ Default-Werte: ----------------------------------
	// XML-Datei:
	final public static String KONFIGURATION_DATEINAME_DEFAULT = "konf.xml";
	
	// Spielfeld:
	final public static int N_DEFAULT = 30; // 10;
	final public static int M_DEFAULT = 30; // 10;
	final public static int RAND_X_DEFAULT = 50;
	final public static int RAND_Y_DEFAULT = 50;
	final public static int ZELLENGROESSE_DEFAULT = 25; // 100;
	
	// Anzahl Passanten/Spieler:
	final public static int ANZAHL_FIGUREN_DEFAULT = 10; // 50; // 5;
	final public static int ANZAHL_SPIELER_DEFAULT = 1;
	
	// Aktualisierungsgeschwindigkeit (Millisekunden):
	final public static int AENDERUNGS_FREQUENZ_DEFAULT = 300; // 100; //1000;
	final public static int MAX_SPEED_STUFEN_DEFAULT = 3;
	
	final public static long ZUSAMMENSTOSS_ANZEIGEDAUER_DEFAULT = 3000;
	
	// Erhoehung des Schwierigkeitsgrades:
	final public static int AENDERUNGSFREQUENZ_ERHOEHUNG_DEFALUT = 50;
	final public static int ANZAHL_FIGUREN_ERHOEHUNG_DEFAULT = 5;
		
	// Texturen:
	final public static String BILD_PFLASTERSTEIN_DEFAULT ="pflasterstein.jpg";
	final public static String BILD_SPIELER_DEFAULT ="smiley.gif";
	final public static String BILD_SPIELER2_DEFAULT ="smiley.gif";

	final public static String BILD_STARTFELD_DEFAULT = null;
	final public static String BILD_ZIELFELD_DEFAULT = null;

	// Bilder: 20x20 Pixel (0,71cm x 0,71cm)
	/*
	 * 0 1 2 
	 * 3 * 4
	 * 5 6 7
	 */
	final public static String BILD_PASSANT_LINKS_OBEN_DEFAULT ="bartsimpson.gif"; // Richtung 0
	final public static String BILD_PASSANT_OBEN_DEFAULT = null; // Richtung 1
	final public static String BILD_PASSANT_RECHTS_OBEN_DEFAULT ="bartsimpson2.gif"; // Richtung 2
	// final public static String BILD_PASSANT_LINKS_DEFAULT ="bartsimpson.gif"; // Richtung 3
	final public static String BILD_PASSANT_LINKS_DEFAULT ="pacman_links_gruen.gif"; // Richtung 3
	// final public static String BILD_PASSANT_RECHTS_DEFAULT ="bartsimpson2.gif"; // Richtung 4
	final public static String BILD_PASSANT_RECHTS_DEFAULT ="pacman_rechts_rot.gif"; // Richtung 4
	final public static String BILD_PASSANT_LINKS_UNTEN_DEFAULT ="bartsimpson.gif"; // Richtung 5
	final public static String BILD_PASSANT_UNTEN_DEFAULT = null; // Richtung 6
	final public static String BILD_PASSANT_RECHTS_UNTEN_DEFAULT ="bartsimpson2.gif"; // Richtung 7
	final public static String BILD_ZUSAMMENSTOSS_DEFAULT = "explosion.gif";
	/* Bildquelle fuer Pacman-Figuren: http://4.bp.blogspot.com/
	 * _-zU-QRb1LsE/TUp-CVPuONI/AAAAAAAAAak/QOrOeej3Vt4/s1600/pacman.png */
	
	// Richtungen:
	final public static boolean NUR_VERTIKAL_UND_HORIZONTAL_DEFAULT = true;
	final public static boolean NUR_HORIZONTAL_DEFAULT = true;
		
	// Farben:
	final public static Color FARBE_FELD_DEFAULT = Color.DARK_GRAY;
	final public static Color FARBE_PASSANT_DEFAULT = Color.RED;
	final public static Color FARBE_SPIELER_DEFAULT = Color.GREEN;
	final public static Color FARBE_STARTFELD_DEFAULT = Color.YELLOW;
	final public static Color FARBE_ZIELFELD_DEFAULT = Color.PINK;
	
	final public static boolean FELD_TYP_SPIELER_NICHT_LOESCHEN_DEFAULT = false;
	
	// Audio:
	final public static String AUDIO_DATEINAME_DEFAULT = "spiel_mir_das_lied_vom_tod.wav";
	final public static boolean AUDIO_ABSPIELEN_DEFAULT = true;

	// Versuche:
	final public static int MAX_ANZAHL_VERSUCHE_DEFAULT = 3;
	
	// Highscore:
	final public static String HIGHSCORE_URL_DEFAULT = "//127.0.0.1/Highscore";

	// ------------------------ Konfigurationen: ----------------------------------
	// XML-Datei:
	final public static String KONFIGURATION_DATEINAME = KONFIGURATION_DATEINAME_DEFAULT;
	
	// Spielfeld:
	public static int N = N_DEFAULT;
	public static int M = M_DEFAULT;
	public static int RAND_X = RAND_X_DEFAULT;
	public static int RAND_Y = RAND_Y_DEFAULT;
	public static int ZELLENGROESSE = ZELLENGROESSE_DEFAULT;
	
	// Anzahl Passanten/Spieler:
	public static int ANZAHL_FIGUREN = ANZAHL_FIGUREN_DEFAULT;
	public static int ANZAHL_SPIELER = ANZAHL_SPIELER_DEFAULT;
	
	// Aktualisierungsgeschwindigkeit (Millisekunden):
	public static int AENDERUNGS_FREQUENZ = AENDERUNGS_FREQUENZ_DEFAULT;
	public static int MAX_SPEED_STUFEN = MAX_SPEED_STUFEN_DEFAULT;
	public static long ZUSAMMENSTOSS_ANZEIGEDAUER = ZUSAMMENSTOSS_ANZEIGEDAUER_DEFAULT;

	// Richtungen:
	public static boolean NUR_VERRTIKAL_UND_HORIZONTAL = NUR_VERTIKAL_UND_HORIZONTAL_DEFAULT;
	public static boolean NUR_HORIZONTAL = NUR_HORIZONTAL_DEFAULT;

	// Texturen:
	public static String BILD_PFLASTERSTEIN = BILD_PFLASTERSTEIN_DEFAULT;
	public static String BILD_SPIELER = BILD_SPIELER_DEFAULT;
	public static String BILD_SPIELER2 = BILD_SPIELER2_DEFAULT;

	public static String BILD_STARTFELD = BILD_STARTFELD_DEFAULT;
	public static String BILD_ZIELFELD = BILD_ZIELFELD_DEFAULT;

	public static String BILD_PASSANT_LINKS_OBEN = BILD_PASSANT_LINKS_OBEN_DEFAULT; // Richtung 0
	public static String BILD_PASSANT_OBEN = BILD_PASSANT_OBEN_DEFAULT; // Richtung 1
	public static String BILD_PASSANT_RECHTS_OBEN = BILD_PASSANT_RECHTS_OBEN_DEFAULT; // Richtung 2
	public static String BILD_PASSANT_LINKS = BILD_PASSANT_LINKS_DEFAULT; // Richtung 3
	public static String BILD_PASSANT_RECHTS = BILD_PASSANT_RECHTS_DEFAULT; // Richtung 4
	public static String BILD_PASSANT_LINKS_UNTEN = BILD_PASSANT_LINKS_UNTEN_DEFAULT; // Richtung 5
	public static String BILD_PASSANT_UNTEN = BILD_PASSANT_UNTEN_DEFAULT; // Richtung 6
	public static String BILD_PASSANT_RECHTS_UNTEN = BILD_PASSANT_RECHTS_UNTEN_DEFAULT; // Richtung 7

	public static String BILD_ZUSAMMENSTOSS = BILD_ZUSAMMENSTOSS_DEFAULT; 

	// Farben:
	public static Color FARBE_FELD = FARBE_FELD_DEFAULT;
	public static Color FARBE_PASSANT = FARBE_PASSANT_DEFAULT;
	public static Color FARBE_SPIELER = FARBE_SPIELER_DEFAULT;
	public static Color FARBE_STARTFELD = FARBE_STARTFELD_DEFAULT;
	public static Color FARBE_ZIELFELD = FARBE_ZIELFELD_DEFAULT;
	
	// Erhoehung des Schwierigkeitsgrades:
	final public static int AENDERUNGSFREQUENZ_ERHOEHUNG = AENDERUNGSFREQUENZ_ERHOEHUNG_DEFALUT;
	final public static int ANZAHL_FIGUREN_ERHOEHUNG = ANZAHL_FIGUREN_ERHOEHUNG_DEFAULT;
		

	public static boolean FELD_BELEGUNG_SPIELER_NICHT_LOESCHEN = FELD_TYP_SPIELER_NICHT_LOESCHEN_DEFAULT;
	// Zeigt an, ob ein Feld, auf dem sich ein Passant und ein Spieler aufhalten,
	// bei der Freigabe die Belegung "Spieler" behaelt.
	
	// Audio:
	public static String AUDIO_DATEINAME = AUDIO_DATEINAME_DEFAULT;
	public static boolean AUDIO_ABSPIELEN = AUDIO_ABSPIELEN_DEFAULT;

	// Versuche:
	final public static int MAX_ANZAHL_VERSUCHE = MAX_ANZAHL_VERSUCHE_DEFAULT;

	// Highscore:
	public static String HIGHSCORE_URL = HIGHSCORE_URL_DEFAULT;
	
	/**
	 * Gibt einige Konfigurationsdaten aus.
	 */
	static public void ausgabe() {
		System.out.println("N: " + N);
		System.out.println("M: " + M);
		System.out.println("RAND_X: " + RAND_X);
		System.out.println("RAND_Y " + RAND_Y);
		System.out.println("ZELLENGROESSE: " + ZELLENGROESSE);
		
		System.out.println("ANZAHL_FIGUREN: " + ANZAHL_FIGUREN);
		System.out.println("ANZAHL_SPIELER: " + ANZAHL_SPIELER);
		
		System.out.println("AENDERUNGS_FREQUENZ: " + AENDERUNGS_FREQUENZ);
		System.out.println("MAX_SPEED_STUFEN: " + MAX_SPEED_STUFEN);
		
		
		System.out.println("BILD_PFLASTERSTEIN: " + BILD_PFLASTERSTEIN);
		System.out.println("BILD_SPIELER: " + BILD_SPIELER);
		System.out.println("BILD_PASSANT_LINKS: " + BILD_PASSANT_LINKS);
		System.out.println("BILD_PASSANT_RECHTS: " + BILD_PASSANT_RECHTS);
		
		System.out.println("FARBE_PASSANT: " +  FARBE_PASSANT);
		System.out.println("FARBE_SPIELER: " + FARBE_SPIELER);
		System.out.println("FARBE_STARTFELD: " + FARBE_STARTFELD);
		System.out.println("FARBE_ZIELFELD: " + FARBE_ZIELFELD);
		
		System.out.println("FELD_BELEGUNG_SPIELER_NICHT_LOESCHEN: "+FELD_BELEGUNG_SPIELER_NICHT_LOESCHEN);
		
	} // ausgabe
	
} // class Konfiguration
