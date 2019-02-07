package com.ftninformatika.bisis.editor.invholes;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.inventar.CodedValuePanel;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.swing.CCPUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.miginfocom.swing.MigLayout;

public class InvNumberHolesFrame extends JInternalFrame {
	
	public InvNumberHolesFrame(){
		super(Messages.getString("INV_HOLES_TITLE"), false, true, false, true);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setSize(new Dimension(340,550));
		initialize();
		
	}
	
	private void initialize(){		
		//add(mainPanel);
		odeljenjePanel = new CodedValuePanel(BisisApp.appConfig.getCodersHelper().ODELJENJE_CODER,mainPanel);
		invKnjPanel = new CodedValuePanel(BisisApp.appConfig.getCodersHelper().INVENTARNAKNJIGA_CODER,mainPanel);
		odeljenjePanel.addAllowedSymbol("*");
		invKnjPanel.addAllowedSymbol("*");		
		executeButton = new JButton(new ImageIcon(getClass().getResource(
        	"/icons/Check16.png")));
		executeButton.setText(Messages.getString("INV_HOLES_FIND"));
		viewTxtArea = new JTextArea(20,20);
		viewTxtArea.setEditable(false);
		viewTxtArea.setComponentPopupMenu(CCPUtil.getCCPPopupMenu());
		scrollPane = new JScrollPane(viewTxtArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		setLayout(new MigLayout("insets 10 10 10 10","[][][][][]","[]5[]10[]5[]20[]20[]"));
		add(new JLabel(Messages.getString("INV_HOLES_DEPARTMENT")),"span 5, wrap");
		add(odeljenjePanel,"wrap");		
		add(new JLabel(Messages.getString("INV_HOLES_INVBOOK")),"wrap");
		add(invKnjPanel,"wrap");
		add(new JLabel(Messages.getString("INV_HOLES_FROM")),"split 5");
		add(odTxtFld,"");
		add(new JLabel(Messages.getString("INV_HOLES_TO")),"");
		add(doTxtFld,"");
		add(executeButton,"wrap");
		add(scrollPane,"wrap, grow");
		
		executeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {				
				handleFindHoles();
			}			
		});
	}
	
	private void handleFindHoles() {
		String retVal = null;
		viewTxtArea.setText("");

		if (validateInputs()){ int odInt = Integer.parseInt(odTxtFld.getText());
			int doInt = Integer.parseInt(doTxtFld.getText());
			String odeljenje = odeljenjePanel.getCode();
			String invKnj = invKnjPanel.getCode();

			retVal = InvNumberHolesFinder.getInvHolesfromIndex(odeljenje, invKnj, odInt, doInt);

			if (retVal != null && retVal.length() == 0) viewTxtArea.setText(Messages.getString("INV_HOLES_NEGATIVE"));
			viewTxtArea.setText(retVal);
		}
		else {
			showError();
		}
	}

	private boolean validateInputs() {
		if (BisisApp.appConfig.getCodersHelper().getLocations().get(odeljenjePanel.getCode()) == null)
			return false;
		if (BisisApp.appConfig.getCodersHelper().getAccessionRegs().get(invKnjPanel.getCode()) == null)
			return false;
		try {
			int from = Integer.parseInt(odTxtFld.getText());
			int to = Integer.parseInt(doTxtFld.getText());
			if (from < 1 || from > 9999999)
				return false;
			if (to < 2 || to > 9999999)
				return false;
			if (from >= to)
				return false;
			if ((to - from) > 100000)
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private void showError() {
		JOptionPane.showMessageDialog(BisisApp.getMainFrame(), Messages.getString("INV_HOLES_MESSAGE"), Messages.getString("INV_HOLES_ERROR"), JOptionPane.ERROR_MESSAGE);
	}
	
	private static Log log = LogFactory.getLog(InvNumberHolesFrame.class);
	
	private JPanel mainPanel = new JPanel();	
	private CodedValuePanel odeljenjePanel;
	private CodedValuePanel invKnjPanel;
	private JTextField odTxtFld = new JTextField(6);
	private JTextField doTxtFld = new JTextField(6);
	private JTextArea viewTxtArea;
	private JScrollPane scrollPane;
	private JButton executeButton;
	

}
