package com.ftninformatika.bisis.actions;

import com.ftninformatika.bisis.BisisApp;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
public class SearchAction extends AbstractAction {

  public SearchAction() {
    putValue(SHORT_DESCRIPTION, "Pretra\u017eivanje baze zapisa");
    putValue(NAME, "Pretra\u017eivanje");
    putValue(SMALL_ICON, new ImageIcon(getClass().getResource(
        "/icons/search.gif")));
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_F3));
  }
  
  public void actionPerformed(ActionEvent ev) {
    BisisApp.getMainFrame().showSearchFrame();
  }
}