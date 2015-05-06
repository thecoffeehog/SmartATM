/*
 * This program is developed by Udit Chugh at Symbiosis Institute of Technology.
 * This program is developed as a mini project and should be used at your own risk.
 * Text to speech service used in this program is provided by http://www.oddcast.com/home/demos/tts/tts_example.php
 * .mp3 to .wav conversion service is provided by Media.io
 */
import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Main {
	
	public static void main(String args[]) {
	
		setLookToSystem();
		setUIFont (new javax.swing.plaf.FontUIResource(new Font("Verdana",Font.PLAIN, 12)));
	
		InitialInput initialInput = new InitialInput();
		//Welcome welcome = new Welcome();
	}
	public static void setLookToSystem() {
		try {
            // Set System L&F
        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
    } 
    catch (Exception e) {
    	System.out.println("Exception Arised in setting system look and feel!");
    }
	}
	private static void setUIFont(javax.swing.plaf.FontUIResource f)
	{
	    java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements())
	    {
	        Object key = keys.nextElement();
	        Object value = UIManager.get(key);
	        if (value instanceof javax.swing.plaf.FontUIResource)
	        {
	            UIManager.put(key, f);
	        }
	    }
	}
	
}
