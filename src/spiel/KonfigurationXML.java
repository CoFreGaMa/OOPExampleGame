/**
 * Lesen von Konfigurationsdaten aus einer XML-Datei.
 */
package spiel;

/**
 * @author clecon
 * @version 1.0; 02/2012
 *
 */
import javax.xml.parsers.*;
// DocumentBuilder, DocumentBuilderFactory,
// ParserConfigurationException
import java.io.*; // File
import org.w3c.dom.*; // Document
import org.xml.sax.*; // SAXParseException, SAXException

public class KonfigurationXML {

	public void leseKonf() {
		NodeList ndList = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder
					.parse(Konfiguration.KONFIGURATION_DATEINAME);

			// Lesen von ANZAHL_FIGUREN:
			ndList = document.getElementsByTagName("ANZAHL_FIGUREN");
			if (ndList != null) {
				Konfiguration.ANZAHL_FIGUREN = new Integer(ndList.item(0)
						.getAttributes().item(0).getNodeValue()).intValue();
				System.out.println("+++ Wert von Anzahl Figuren:");
				System.out.println(ndList.item(0).getAttributes().item(0)
						.getNodeValue());
			} // if

			// Lesen von ZUSAMMENSTOSS_ANZEIGEDAUER:
			ndList = document
					.getElementsByTagName("ZUSAMMENSTOSS_ANZEIGEDAUER");
			if (ndList != null) {
				Konfiguration.ZUSAMMENSTOSS_ANZEIGEDAUER = new Long(ndList
						.item(0).getAttributes().item(0).getNodeValue())
						.longValue();
				System.out.println("+++ Wert von Zusammenstoss Anzeigedauer:");
				System.out.println(ndList.item(0).getAttributes().item(0)
						.getNodeValue());
			} // if

			// Lesenvon HIGHSCORE_URL:
			ndList = document.getElementsByTagName("HIGHSCORE_URL");
			if (ndList != null) {
				Konfiguration.HIGHSCORE_URL = ndList.item(0).getAttributes()
						.item(0).getNodeValue();
				System.out.println("+++ Wert von Highscore-URL:");
				System.out.println(ndList.item(0).getAttributes().item(0)
						.getNodeValue());
			} // if
		} catch (ParserConfigurationException ex1) {
			ex1.printStackTrace();
		} catch (IOException ex2) {
			System.err.println("Warnung: Fehler beim Lesen der XML-Datei "
					+ Konfiguration.KONFIGURATION_DATEINAME
					+ ", vielleicht existiert sie nicht.");
			System.out.println("Es werden die Standardwerte verwendet.");
			// ex2.printStackTrace();
		} catch (SAXException ex3) {
			ex3.printStackTrace();
		} // catch

	} // leseKonf
	
	/**
	 * Testprogramm
	 * @param args
	 */
	public static void main(String[] args) {
		KonfigurationXML kxml = new KonfigurationXML();
		kxml.leseKonf();
	} // main

} // class KonfigurationXML
