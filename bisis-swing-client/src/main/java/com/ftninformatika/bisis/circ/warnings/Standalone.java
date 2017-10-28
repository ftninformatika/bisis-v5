package com.ftninformatika.bisis.circ.warnings;

import com.ftninformatika.bisis.circ.warnings.WarningsFrame;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class Standalone extends JFrame {
  
  static Standalone standalone;

	public static void main(String[] args) {
		standalone = new Standalone();
    standalone.setVisible(true);
		//standalone.init();
	}
  
  public Standalone(){
    super();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setSize(Toolkit.getDefaultToolkit().getScreenSize());
    JDesktopPane desktop = new JDesktopPane();
    add(desktop, BorderLayout.CENTER);
    WarningsFrame w = new WarningsFrame();
    desktop.add(w);
    w.setVisible(true);
    desktop.setVisible(true);
  }
	
	public void init(){
    JDesktopPane desktop = new JDesktopPane();
    add(desktop, BorderLayout.CENTER);
		WarningsFrame w = new WarningsFrame();
    desktop.add(w);
    w.setVisible(true);
    desktop.setVisible(true);
	}

}
