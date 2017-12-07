package com.ftninformatika.bisis.search;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;

import com.ftninformatika.bisis.editor.Messages;
import com.ftninformatika.utils.WindowUtils;
import net.miginfocom.swing.MigLayout;

public class ExpandListDlg extends JDialog {

	public ExpandListDlg(Frame owner) {
		super(owner, Messages.getString("SEARCH_EXPAND_PREFIX"), true);
		
		spExpandList.setViewportView(lbExpandList);
		spExpandList.setFocusable(false);
		lbExpandList.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnOK.doClick();
					return;
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					btnCancel.doClick();
					return;
				}

			}
		});
		lbExpandList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() > 1)
					btnOK.doClick();
			}
		});

		btnOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				selected = true;
				setVisible(false);
			}
		});
		btnOK.setFocusable(false);
		getRootPane().setDefaultButton(btnOK);
		btnOK.setIcon(new ImageIcon(getClass().getResource(
				"/icons/ok.gif")));

		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				selected = false;
				setVisible(false);
			}
		});
		btnCancel.setFocusable(false);
		btnCancel.setIcon(new ImageIcon(getClass().getResource(
				"/icons/remove.gif")));

		MigLayout layout = new MigLayout("insets dialog, wrap", "[270lp]",
				"[300lp][]");
		setLayout(layout);
		add(spExpandList, "grow, wrap");
		add(btnCancel, "gaptop para, split 2, tag cancel");
		add(btnOK, "gaptop para, wrap, tag ok");
		pack();
		WindowUtils.centerOnScreen(this);
	}

	public boolean isSelected() {
		return selected;
	}
    public void setList(List exp){
    	plm = new ExpandListModel(exp);
    	lbExpandList.setModel(plm);
    }
	public String getSelectedItem() {
		return (String) plm.getElementAt(lbExpandList.getSelectedIndex());
	}

	private JList lbExpandList = new JList();

	private JScrollPane spExpandList = new JScrollPane();

	private JButton btnOK = new JButton(Messages.getString("SEARCH_CHOSE"));

	private JButton btnCancel = new JButton(Messages.getString("SEARCH_CANCEL"));

	private boolean selected = false;

	private ExpandListModel plm;

}