/**
 * Audio-Klasse (Begleitmusik)
 */
package spiel;

/**
 * @author clecon
 * @version 1.0; 04.02.2012 - 05.02.2012
 *
 */
import sun.audio.*; 
import java.io.*;
import java.util.*;

public class Audio extends Thread {

	private static final long DAUER_DEFAULT = 28000; // in Millisekunden
	private String dateiname = new String();
	private long dauer = DAUER_DEFAULT; // Dauer der Musik (in Millisekunden)
	private boolean stop = false;
	private boolean running = false;
	private AudioStream audioStream;

	
	public Audio(String dateiname) {
		this.dateiname = dateiname;
	} // Konstruktor I
	
	public Audio(String filename, long dauer) {
		this.dateiname = filename;
		this.dauer = dauer;
	} // Konstruktor II
	
	public void run() {
		if (!Konfiguration.AUDIO_ABSPIELEN) {
			return;
		} // if
		
		Date beginnZeit, date;
		long diff;
		// Erneutes Starten unterbinden:
		if (running) {
			return;
		} else {
			running = true;
		} // if (else)
		while (!stop) {
			beginnZeit = new Date();
			playAudio(dateiname);
			do {
				date = new Date();
				diff = date.getTime() - beginnZeit.getTime();
			} while ((diff < dauer) && (!stop));
			stopAudio();
		} // while
	} // run
	
	public void stoppen() {
		stop = true;
	} // stoppen
	
	public void stopAudio() {
		if (!Konfiguration.AUDIO_ABSPIELEN) {
			AudioPlayer.player.stop(audioStream);
		} // if
	} // stopAudio
	
	public void playAudio(String filename) {
		String soundFile = filename;
		System.out.println("+++ Soundfile: \"" + soundFile + "\"");
		try {
			InputStream in = new FileInputStream(soundFile);
			audioStream = new AudioStream(in);
			AudioPlayer.player.start(audioStream);
		} catch (Exception e) {
			e.printStackTrace();
		} // catch
	} // playAudio

	/**
	 * Testprogramm
	 * @param args
	 */
	public static void main(String[] args) {
	    Audio audio = new Audio("spiel_mir_das_lied_vom_tod.wav");
	    audio.run();
	} // main

} // class Audio
