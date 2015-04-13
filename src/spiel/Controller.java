/**
 * Controller zur Spiel-Interaktion.
 */
package spiel;

/**
 * @author clecon
 * @version 1.0; 09/2011 - 30.01.2012
 *
 */
import java.awt.event.*;
import java.util.*;

public class Controller implements KeyListener {

	static final public int TASTE_CURSOR_UP = 38;
	static final public int TASTE_CURSOR_DOWN = 40;
	static final public int TASTE_CURSOR_LEFT = 37;
	static final public int TASTE_CURSOR_RIGHT = 39;
	static final public int TASTE_X = 88;
	static final public int TASTE_ENTER = 10;
	static final public int TASTE_M = 77;
	
	static int[] ERLAUBTE_TASTEN = {TASTE_CURSOR_UP, TASTE_CURSOR_DOWN, TASTE_CURSOR_LEFT,
		TASTE_CURSOR_RIGHT, TASTE_X, TASTE_ENTER, TASTE_M};
	
	
	private Ticker model;
	
	public Controller(Ticker model, Spiel view) {
		this.model = model;
	} // Konstruktor
	
	/**
	 * Reaktion auf das Druecken einer Taste.
	 */
    public void keyPressed(KeyEvent k) {
    	int keyCode;
    	// Debug: System.out.println("+++ TASTE: " + k.getKeyCode());
    	while (Spiel.getInstance().locked()) {
    		try {
    			// Solange gezeichnet wird, sollte keine Interaktion stattfinden.
    			Thread.sleep(100);
    		} catch (InterruptedException e) {
    			System.err.println("Komischer Fehler in Controller-Warteschleife.");
    		} // catch
    	} // while 
    	keyCode = k.getKeyCode();
    	// Behandelte Taste?:
    	for (int i=0; i<ERLAUBTE_TASTEN.length; i++) {
    		if (keyCode == ERLAUBTE_TASTEN[i]) {
    	    	model.change(keyCode);
    			return;
    		} // if
    	} // for
    	System.out.println("+++ Nicht behandelte Taste: " + keyCode);
    } // keyPressed

	public void keyReleased(KeyEvent k) {
		// nicht behandelt.
	} // keyReleased

	public void keyTyped(KeyEvent k) {
		// nicht behandelt.
	} // keyTyped

} // class Controller
