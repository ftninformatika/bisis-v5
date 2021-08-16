package com.ftninformatika.bisis.circ.view;


import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.ecard.ElCardInfo;
import com.ftninformatika.bisis.ecard.ElCardReader;
import com.ftninformatika.utils.Messages;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.Frame;
import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.event.*;

public class UserID extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextField tfUserID = null;
	private JPanel pSouth = null;
	private JButton btnOK = null;
	private JButton btnSerachECard = null;
	private PanelBuilder pCenter = null;
	private JButton btnCancel = null;
	private JButton btnSearch = null;

	public UserID(Frame owner) {
		super(owner, true);
		initialize();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Cirkulacija.getApp().getUserManager().setReserveBook(null);
				Cirkulacija.getApp().getUserManager().setChargeBook("");
			}
		});
	}

	private void initialize() {
		this.setSize(350, 150);
		this.setContentPane(getJContentPane());
    this.pack();
    this.addComponentListener(new ComponentAdapter() {
      public void componentShown(ComponentEvent e){
        getTfUserID().requestFocusInWindow();
      }
    });
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getPSouth(), BorderLayout.SOUTH);
			jContentPane.add(getPCenter(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	private JTextField getTfUserID() {
		if (tfUserID == null) {
			tfUserID = new JTextField();
			tfUserID.addKeyListener(new KeyAdapter(){
				public void keyPressed(KeyEvent e){
					if(e.getKeyCode()==KeyEvent.VK_ENTER)
						btnOK.doClick();
          else if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
            btnCancel.doClick();
				}
			});
		}
		return tfUserID;
	}

	private JPanel getPSouth() {
		if (pSouth == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(20);
			pSouth = new JPanel(flowLayout);
			pSouth.add(getBtnOK(), null);
			pSouth.add(getBtnCancel(), null);
			pSouth.add(getBtnSearch(), null);
			pSouth.add(getBtnSerachECard(), null);
		}
		return pSouth;
	}

	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setText(Messages.getString("circulation.ok")); //$NON-NLS-1$
			btnOK.setIcon(new ImageIcon(getClass().getResource("/circ-images/Check16.png"))); //$NON-NLS-1$
		}
		return btnOK;
	}

	private JButton getBtnSerachECard() {
		if (btnSerachECard == null) {
			btnSerachECard = new JButton();
			btnSerachECard.setText(Messages.getString("circulation.btnReadFromECard"));
			btnSerachECard.setIcon(new ImageIcon(getClass().getResource("/icons/chip16.png")));
		}
		return btnSerachECard;
	}

	private JPanel getPCenter() {
		if (pCenter == null) {
			FormLayout layout = new FormLayout(
			        "2dlu:grow, right:65dlu, 10dlu, 80dlu, 2dlu:grow",  //$NON-NLS-1$
			        "15dlu, pref, 15dlu, pref, 15dlu:grow"); //$NON-NLS-1$
			CellConstraints cc = new CellConstraints();
			pCenter = new PanelBuilder(layout);
			pCenter.setDefaultDialogBorder();
			pCenter.addLabel(Messages.getString("circulation.usernumber"), cc.xy(2,2)); //$NON-NLS-1$
			pCenter.add(getTfUserID(), cc.xy(4,2));
			pCenter.addSeparator("",cc.xyw(2,4,3)); //$NON-NLS-1$
		}
		return pCenter.getPanel();
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText(Messages.getString("circulation.cancel")); //$NON-NLS-1$
			btnCancel.setIcon(new ImageIcon(getClass().getResource("/circ-images/Delete16.png"))); //$NON-NLS-1$
		}
		return btnCancel;
	}

	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText(Messages.getString("circulation.find")); //$NON-NLS-1$
			btnSearch.setIcon(new ImageIcon(getClass().getResource("/circ-images/find16.png"))); //$NON-NLS-1$
		}
		return btnSearch;
	}
	
	public void addOKListener(ActionListener l){
		getBtnOK().addActionListener(l);
	}
	
	public void addCancelListener(ActionListener l){
		getBtnCancel().addActionListener(l);
	}
	
	public void addSearchListener(ActionListener l){
		getBtnSearch().addActionListener(l);
	}

	public void addEsearchListener(ActionListener l){
		getBtnSerachECard().addActionListener(l);
	}
	
	public void clear(){
		getTfUserID().setText(""); //$NON-NLS-1$
	}
	
	public String getValue(){
		return getTfUserID().getText();
	}

}
