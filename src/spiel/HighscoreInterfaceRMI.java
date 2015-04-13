/**
 * Schnittstelle, in der die Funktionalitaet 
 * des Highscore-Servers spezifziert ist.
 */
package spiel;

/**
 * @author clecon
 * @version 1.0; 02/2012 - 07.02.2012
 *
 */
import java.rmi.*;

public interface HighscoreInterfaceRMI extends Remote {
	
	/**
	 * Lesen des Highscores aus der Datei.
	 * @return Liste mit den Highscore-Eintr&auml;gen.
	 * @throws RemoteException
	 */
	public HighscoreEintrag[] getHighscore() throws RemoteException;
	
	/**
	 * Hinzuf&uuml;gen eines neuen Eintrags f&uum;r den Highscore.
	 * @param punkte Erreichte Punkte.
	 * @throws RemoteException
	 */
	public void neuerEintrag(int punkte) throws RemoteException;

	
} // Interface RMIInterface
