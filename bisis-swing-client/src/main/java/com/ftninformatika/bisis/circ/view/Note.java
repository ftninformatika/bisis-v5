package com.ftninformatika.bisis.circ.view;

import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

import java.awt.Dialog;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class Note extends JDialog {

	private JPanel jContentPane = null;
	private JTextField jTextField = null;
  	private PanelBuilder pb;

	public Note(Dialog owner, boolean modal) {
		super(owner, modal);
		initialize();
	}

	private void initialize() {
		this.setSize(250, 100);
		this.setContentPane(getJContentPane());
		this.setTitle(""); //$NON-NLS-1$
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.pack();
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
      FormLayout layout = new FormLayout(
          "2dlu:grow ,left:100dlu, 2dlu:grow",  //$NON-NLS-1$
          "5dlu, pref, 2dlu, pref, 2dlu:grow"); //$NON-NLS-1$
      CellConstraints cc = new CellConstraints();
      pb = new PanelBuilder(layout);
      pb.setDefaultDialogBorder();
      pb.addLabel(Messages.getString("circulation.note"), cc.xy(2,2)); //$NON-NLS-1$
      pb.add(getJTextField(), cc.xy(2,4,"fill, fill")); //$NON-NLS-1$
			jContentPane = pb.getPanel();
		}
		return jContentPane;
	}

	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
      jTextField.addKeyListener(new KeyAdapter(){
        public void keyReleased(java.awt.event.KeyEvent e) {
          if(e.getKeyCode()==KeyEvent.VK_ENTER){
            setVisible(false);
          }
        }
      });
		}
		return jTextField;
	}
	
	public String getValue(){
		return getJTextField().getText().trim();
	}
	
	public void addListener(java.awt.event.KeyAdapter ka){
		jTextField.addKeyListener(ka);
	}

}
