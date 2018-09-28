package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.utils.Messages;
import javax.swing.JPanel;

import java.awt.*;
import javax.swing.JTextField;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.validator.Validator;
import com.ftninformatika.bisis.libenv.LibEnvironment;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.circ.pojo.CircLocation;
import com.ftninformatika.bisis.search.*;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;

public class SearchBooks {

	private JTextField tfPref1 = null;
	private JTextField tfPref2 = null;
	private JTextField tfPref3 = null;
	private JTextField tfPref4 = null;
	private JTextField tfPref5 = null;
	private JComboBox cmbOper1 = null;
	private JComboBox cmbOper2 = null;
	private JComboBox cmbOper3 = null;
	private JComboBox cmbOper4 = null;
	private JDateChooser tfStartDateL = null;
	private JDateChooser tfStartDateR = null;
	private JDateChooser tfEndDateL = null;
	private JDateChooser tfEndDateR = null;
	private JButton btnSearch = null;
	private JButton btnCancel = null;
	private PanelBuilder pb = null;
	private JLabel lPref1 = null;
	private JLabel lPref2 = null;
	private JLabel lPref3 = null;
	private JLabel lPref4 = null;
	private JLabel lPref5 = null;
	private JButton btnPref1 = null;
	private JButton btnPref2 = null;
	private JButton btnPref3 = null;
	private JButton btnPref4 = null;
	private JButton btnPref5 = null;
	private CodedPrefPanel codedPref1 = null;
	private CodedPrefPanel codedPref2 = null;
	private CodedPrefPanel codedPref3 = null;
	private CodedPrefPanel codedPref4 = null;
	private CodedPrefPanel codedPref5 = null;
	private JPanel buttonPanel = null;
	private JComboBox cmbLocL = null;
	private JComboBox cmbLocR = null;
	private PrefixListDlg prefixListDlg = null;
	private ComboBoxRenderer cmbRenderer = null;
	private CmbKeySelectionManager cmbKeySelectionManager = null;
	private boolean dirtyPrefixSet = false;
	private JLabel lFilter = null;
	private JComboBox cmbFilter = null;
	String filter = null;
	
	public SearchBooks() {
		makePanel();
		init();
	}

	private void makePanel() {
		
		FormLayout layout = new FormLayout(
		        "2dlu:grow, right:70dlu, 5dlu, 20dlu, 5dlu, 70dlu, 10dlu, 70dlu, 5dlu, 40dlu, 50dlu, 2dlu:grow",  //$NON-NLS-1$
		        "20dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 20dlu, pref, 10dlu, pref, 2dlu, pref, 10dlu, pref, 20dlu, pref, 2dlu:grow"); //$NON-NLS-1$
		CellConstraints cc = new CellConstraints();
		pb = new PanelBuilder(layout);
		pb.setDefaultDialogBorder();

		pb.add(getLPref1(), cc.xy(2,2));
		pb.add(getBtnPref1(), cc.xy(4,2));
		pb.add(getTfPref1(), cc.xyw(6,2,3));
		pb.add(getCodedPref1(), cc.xyw(6,2,3));
		pb.add(getCmbOper1(), cc.xy(10,2));
		pb.add(getLPref2(), cc.xy(2,4));
		pb.add(getBtnPref2(), cc.xy(4,4));
		pb.add(getTfPref2(), cc.xyw(6,4,3));
		pb.add(getCodedPref2(), cc.xyw(6,4,3));
		pb.add(getCmbOper2(), cc.xy(10,4));
		pb.add(getLPref3(), cc.xy(2,6));
		pb.add(getBtnPref3(), cc.xy(4,6));
		pb.add(getTfPref3(), cc.xyw(6,6,3));
		pb.add(getCodedPref3(), cc.xyw(6,6,3));
		pb.add(getCmbOper3(), cc.xy(10,6));
		pb.add(getLPref4(), cc.xy(2,8));
		pb.add(getBtnPref4(), cc.xy(4,8));
		pb.add(getTfPref4(), cc.xyw(6,8,3));
		pb.add(getCodedPref4(), cc.xyw(6,8,3));
		pb.add(getCmbOper4(), cc.xy(10,8));
		pb.add(getLPref5(), cc.xy(2,10));
		pb.add(getBtnPref5(), cc.xy(4,10));
		pb.add(getTfPref5(), cc.xyw(6,10,3));
		pb.add(getCodedPref5(), cc.xyw(6,10,3));



		pb.addSeparator("", cc.xyw(2,12,10)); //$NON-NLS-1$
		pb.addLabel(Messages.getString("circulation.chargingdate"), cc.xyw(2,14,3,"right, center")); //$NON-NLS-1$ //$NON-NLS-2$
		pb.add(getTfStartDateL(), cc.xy(6,14));
		pb.addLabel("-", cc.xy(7,14, "center, center")); //$NON-NLS-1$ //$NON-NLS-2$
		pb.add(getTfEndDateL(), cc.xy(8,14));
		pb.add(getCmbLocL(), cc.xyw(10,14,2));
		pb.addLabel(Messages.getString("circulation.dischargingdate"), cc.xyw(2,16,3,"right, center")); //$NON-NLS-1$ //$NON-NLS-2$
		pb.add(getTfStartDateR(), cc.xy(6,16));
		pb.addLabel("-", cc.xy(7,16, "center, center")); //$NON-NLS-1$ //$NON-NLS-2$
		pb.add(getTfEndDateR(), cc.xy(8,16));
		pb.add(getCmbLocR(), cc.xyw(10,16,2));
		if (BisisApp.appConfig.getLibrary().equals("bgb")) {
			pb.addLabel(Messages.getString("circulation.filter"), cc.xyw(2,18,3,"right, center"));
			pb.add(getCmbFilter(), cc.xyw(6, 18, 3));
		}

		pb.add(getButtonPanel(), cc.xyw(2,20,10));
		
	}
	
	public JPanel getPanel(){
		return pb.getPanel();
	}

	private JTextField getTfPref1() {
		if (tfPref1 == null) {
			tfPref1 = new JTextField();
			tfPref1.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e){
					handleKeys(getLPref1(), tfPref1, getCodedPref1(), e);
				}
			});
		}
		return tfPref1;
	}

	private JTextField getTfPref2() {
		if (tfPref2 == null) {
			tfPref2 = new JTextField();
			tfPref2.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e){
					handleKeys(getLPref2(), tfPref2, getCodedPref2(), e);
				}
			});
		}
		return tfPref2;
	}

	private JTextField getTfPref3() {
		if (tfPref3 == null) {
			tfPref3 = new JTextField();
			tfPref3.addKeyListener(new KeyAdapter() {
		        public void keyPressed(KeyEvent e){
		          handleKeys(getLPref3(), tfPref3, getCodedPref3(), e);
		        }
		      });
		}
		return tfPref3;
	}

	private JTextField getTfPref4() {
		if (tfPref4 == null) {
			tfPref4 = new JTextField();
			tfPref4.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e){
					handleKeys(getLPref4(), tfPref4, getCodedPref4(), e);
				}
			});
		}
		return tfPref4;
	}

	private JTextField getTfPref5() {
		if (tfPref5 == null) {
			tfPref5 = new JTextField();
			tfPref5.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e){
					handleKeys(getLPref5(), tfPref5, getCodedPref5(), e);
				}
			});
		}
		return tfPref5;
	}
	
	private JButton getBtnPref1() {
		if (btnPref1 == null) {
			btnPref1 = new JButton();
			btnPref1.setText("..."); //$NON-NLS-1$
			btnPref1.setFocusable(false);
			btnPref1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
			       choosePrefix(getLPref1(), getTfPref1(), getCodedPref1());
				}
			});
		}
		return btnPref1;
	}

	private JButton getBtnPref2() {
		if (btnPref2 == null) {
			btnPref2 = new JButton();
			btnPref2.setText("..."); //$NON-NLS-1$
			btnPref2.setFocusable(false);
			btnPref2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					choosePrefix(getLPref2(), getTfPref2(), getCodedPref2());
				}
			});
		}
		return btnPref2;
	}

	private JButton getBtnPref3() {
		if (btnPref3 == null) {
			btnPref3 = new JButton();
			btnPref3.setText("..."); //$NON-NLS-1$
			btnPref3.setFocusable(false);
			btnPref3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					choosePrefix(getLPref3(), getTfPref3(), getCodedPref3());
				}
			});
		}
		return btnPref3;
	}

	private JButton getBtnPref4() {
		if (btnPref4 == null) {
			btnPref4 = new JButton();
			btnPref4.setText("..."); //$NON-NLS-1$
			btnPref4.setFocusable(false);
			btnPref4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					choosePrefix(getLPref4(), getTfPref4(), getCodedPref4());
				}
			});
		}
		return btnPref4;
	}

	private JButton getBtnPref5() {
		if (btnPref5 == null) {
			btnPref5 = new JButton();
			btnPref5.setText("..."); //$NON-NLS-1$
			btnPref5.setFocusable(false);
			btnPref5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					choosePrefix(getLPref5(), getTfPref5(), getCodedPref5());
				}
			});
		}
		return btnPref5;
	}
	
	private CodedPrefPanel getCodedPref1() {
		if (codedPref1 == null){
			codedPref1 = new CodedPrefPanel();
			codedPref1.getTextField().addKeyListener(new KeyAdapter(){
		  	public void keyPressed(KeyEvent e) {
		  		handleKeys(getLPref1(), getTfPref1(), codedPref1, e);
		    }
		  });
		}
		return codedPref1;
	}
	
	private CodedPrefPanel getCodedPref2() {
		if (codedPref2 == null){
			codedPref2 = new CodedPrefPanel();
			codedPref2.getTextField().addKeyListener(new KeyAdapter(){
		  	public void keyPressed(KeyEvent e) {
		  		handleKeys(getLPref2(), getTfPref2(), codedPref2, e);
		    }
		  });
		}
		return codedPref2;
	}
	
	private CodedPrefPanel getCodedPref3() {
		if (codedPref3 == null){
			codedPref3 = new CodedPrefPanel();
			codedPref3.getTextField().addKeyListener(new KeyAdapter(){
		  	public void keyPressed(KeyEvent e) {
		  		handleKeys(getLPref3(), getTfPref3(), codedPref3, e);
		    }
		  });
		}
		return codedPref3;
	}
	
	private CodedPrefPanel getCodedPref4() {
		if (codedPref4 == null){
			codedPref4 = new CodedPrefPanel();
			codedPref4.getTextField().addKeyListener(new KeyAdapter(){
		  	public void keyPressed(KeyEvent e) {
		  		handleKeys(getLPref4(), getTfPref4(), codedPref4, e);
		    }
		  });
		}
		return codedPref4;
	}
	
	private CodedPrefPanel getCodedPref5() {
		if (codedPref5 == null){
			codedPref5 = new CodedPrefPanel();
			codedPref5.getTextField().addKeyListener(new KeyAdapter(){
		  	public void keyPressed(KeyEvent e) {
		  		handleKeys(getLPref5(), getTfPref5(), codedPref5, e);
		    }
		  });
		}
		return codedPref5;
	}

	private JComboBox getCmbOper1() {
		if (cmbOper1 == null) {
			cmbOper1 = new JComboBox();
			cmbOper1.addItem("and"); //$NON-NLS-1$
			cmbOper1.addItem("or"); //$NON-NLS-1$
			cmbOper1.addItem("not"); //$NON-NLS-1$
		  //cmbOper1.setFocusable(false);
		}
		return cmbOper1;
	}

	private JComboBox getCmbOper2() {
		if (cmbOper2 == null) {
			cmbOper2 = new JComboBox();
			cmbOper2.addItem("and"); //$NON-NLS-1$
			cmbOper2.addItem("or"); //$NON-NLS-1$
			cmbOper2.addItem("not"); //$NON-NLS-1$
		  //cmbOper2.setFocusable(false);
		}
		return cmbOper2;
	}

	private JComboBox getCmbOper3() {
		if (cmbOper3 == null) {
			cmbOper3 = new JComboBox();
			cmbOper3.addItem("and"); //$NON-NLS-1$
			cmbOper3.addItem("or"); //$NON-NLS-1$
			cmbOper3.addItem("not"); //$NON-NLS-1$
		  //cmbOper3.setFocusable(false);
		}
		return cmbOper3;
	}

	private JComboBox getCmbOper4() {
		if (cmbOper4 == null) {
			cmbOper4 = new JComboBox();
			cmbOper4.addItem("and"); //$NON-NLS-1$
			cmbOper4.addItem("or"); //$NON-NLS-1$
			cmbOper4.addItem("not"); //$NON-NLS-1$
		  //cmbOper4.setFocusable(false);
		}
		return cmbOper4;
	}

	private JDateChooser getTfStartDateL() {
		if (tfStartDateL == null) {
			tfStartDateL = new JDateChooser();
			tfStartDateL.getDateEditor().getUiComponent().addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent ev) {
					handleKeys(null, tfStartDateL, null, ev);
				}
			});
		}
		return tfStartDateL;
	}

	private JDateChooser getTfStartDateR() {
		if (tfStartDateR == null) {
			tfStartDateR = new JDateChooser();
			tfStartDateR.getDateEditor().getUiComponent().addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent ev) {
					handleKeys(null, tfStartDateR, null, ev);
				}
			});
		}
		return tfStartDateR;
	}

	private JDateChooser getTfEndDateL() {
		if (tfEndDateL == null) {
			tfEndDateL = new JDateChooser();
			tfEndDateL.getDateEditor().getUiComponent().addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent ev) {
					handleKeys(null, tfEndDateL, null, ev);
				}
			});
		}
		return tfEndDateL;
	}

	private JDateChooser getTfEndDateR() {
		if (tfEndDateR == null) {
			tfEndDateR = new JDateChooser();
			tfEndDateR.getDateEditor().getUiComponent().addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent ev) {
					handleKeys(null, tfEndDateR, null, ev);
				}		
			});
		}
		return tfEndDateR;
	}
  
  private ComboBoxRenderer getCmbRenderer() {
    if (cmbRenderer == null) {
      cmbRenderer = new ComboBoxRenderer();
    }
    return cmbRenderer;
  }

  private CmbKeySelectionManager getCmbKeySelectionManager() {
    if (cmbKeySelectionManager == null) {
      cmbKeySelectionManager = new CmbKeySelectionManager();
    }
    return cmbKeySelectionManager;
  }
	
	private JComboBox getCmbLocL() {
		if (cmbLocL == null) {
			cmbLocL = new JComboBox();
		  //cmbLocL.setFocusable(false);
			cmbLocL.setRenderer(getCmbRenderer());
			cmbLocL.setKeySelectionManager(getCmbKeySelectionManager());
		}
		return cmbLocL;
	}
	
	private JComboBox getCmbLocR() {
		if (cmbLocR == null) {
			cmbLocR = new JComboBox();
		  //cmbLocR.setFocusable(false);
			cmbLocR.setRenderer(getCmbRenderer());
			cmbLocR.setKeySelectionManager(getCmbKeySelectionManager());
		}
		return cmbLocR;
	}

	private JComboBox getCmbFilter() {
		if (cmbFilter == null) {
			cmbFilter = new JComboBox();
			//cmbLocR.setFocusable(false);
			cmbFilter.setRenderer(getCmbRenderer());
			cmbFilter.setKeySelectionManager(getCmbKeySelectionManager());
		}
		return cmbFilter;
	}

	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText(Messages.getString("circulation.search")); //$NON-NLS-1$
			btnSearch.setFocusable(false);
			btnSearch.setIcon(new ImageIcon(getClass().getResource("/circ-images/find16.png"))); //$NON-NLS-1$
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					handleSearch();
				}
			});
		}
		return btnSearch;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText(Messages.getString("circulation.cancel")); //$NON-NLS-1$
			btnCancel.setFocusable(false);
			btnCancel.setIcon(new ImageIcon(getClass().getResource("/circ-images/Delete16.png"))); //$NON-NLS-1$
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					closeSearchFrame();
					Cirkulacija.getApp().getMainFrame().previousPanel();
				}
			});
		}
		return btnCancel;
	}
	
	private JLabel getLPref1() {
		if (lPref1 == null) {
			lPref1 = new JLabel();
		}
		return lPref1;
	}
	
	private JLabel getLPref2() {
		if (lPref2 == null) {
			lPref2 = new JLabel();
		}
		return lPref2;
	}
	
	private JLabel getLPref3() {
		if (lPref3 == null) {
			lPref3 = new JLabel();
		}
		return lPref3;
	}
	
	private JLabel getLPref4() {
		if (lPref4 == null) {
			lPref4 = new JLabel();
		}
		return lPref4;
	}
	
	private JLabel getLPref5() {
		if (lPref5 == null) {
			lPref5 = new JLabel();
		}
		return lPref5;
	}

	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(30);
			buttonPanel = new JPanel(flowLayout);
			buttonPanel.add(getBtnSearch());
			buttonPanel.add(getBtnCancel());
		}
		return buttonPanel;
	}
  
  private PrefixListDlg getPrefixListDlg(){
    if (prefixListDlg == null){
      prefixListDlg = new PrefixListDlg(BisisApp.getMainFrame());
    }
    return prefixListDlg;
  }
  
  private void handleKeys(JLabel label, JComponent tfPref, CodedPrefPanel codedPref, KeyEvent ev){
    switch (ev.getKeyCode()) {
    case KeyEvent.VK_ENTER:
    	getBtnSearch().doClick();
      break;
    case KeyEvent.VK_ESCAPE:
      getBtnCancel().doClick();
      break;
    case KeyEvent.VK_F9:
      if (label != null)
        choosePrefix(label, tfPref, codedPref);
      break;
    case KeyEvent.VK_DOWN:
      if (tfPref == tfPref1)
      	if(tfPref2.isVisible())
      		tfPref2.requestFocus();
      	else
      		codedPref2.requestFocus();
      else if (tfPref == tfPref2)
      	if(tfPref3.isVisible())
      		tfPref3.requestFocus();
      	else
      		codedPref3.requestFocus();
      else if (tfPref == tfPref3)
      	if(tfPref4.isVisible())
      		tfPref4.requestFocus();
      	else
      		codedPref4.requestFocus(); 
      else if (tfPref == tfPref4)
      	if(tfPref5.isVisible())
      		tfPref5.requestFocus();
      	else
      		codedPref5.requestFocus();
      else if (tfPref == tfPref5)
        tfStartDateL.getDateEditor().getUiComponent().requestFocusInWindow();
      else if (tfPref == tfStartDateL)
        tfStartDateR.getDateEditor().getUiComponent().requestFocusInWindow();
      else if (tfPref == tfEndDateL)
        tfEndDateR.getDateEditor().getUiComponent().requestFocusInWindow();
      else if (tfPref == tfStartDateR)
      	if(tfPref1.isVisible())
      		tfPref1.requestFocus();
      	else
      		codedPref1.requestFocus();
      else if (tfPref == tfEndDateR)
      	if(tfPref1.isVisible())
      		tfPref1.requestFocus();
      	else
      		codedPref1.requestFocus();
        
      break;
    case KeyEvent.VK_UP:
      if (tfPref == tfPref1)
        tfStartDateR.getDateEditor().getUiComponent().requestFocusInWindow();
      else if (tfPref == tfPref2)
      	if(tfPref1.isVisible())
      		tfPref1.requestFocus();
      	else
      		codedPref1.requestFocus();
      else if (tfPref == tfPref3)
      	if(tfPref2.isVisible())
      		tfPref2.requestFocus();
      	else
      		codedPref2.requestFocus();
      else if (tfPref == tfPref4)
      	if(tfPref3.isVisible())
      		tfPref3.requestFocus();
      	else
      		codedPref3.requestFocus();
      else if (tfPref == tfPref5)
      	if(tfPref4.isVisible())
      		tfPref4.requestFocus();
      	else
      		codedPref4.requestFocus(); 
      else if (tfPref == tfStartDateL)
      	if(tfPref5.isVisible())
      		tfPref5.requestFocus();
      	else
      		codedPref5.requestFocus();
      else if (tfPref == tfEndDateL)
      	if(tfPref5.isVisible())
      		tfPref5.requestFocus();
      	else
      		codedPref5.requestFocus();
      else if (tfPref == tfStartDateR)
        tfStartDateL.getDateEditor().getUiComponent().requestFocusInWindow();
      else if (tfPref == tfEndDateR)
        tfEndDateL.getDateEditor().getUiComponent().requestFocusInWindow();
      
      break;  
  }
  }
  
  private void choosePrefix(JLabel label, JComponent tfPref, CodedPrefPanel codedPref) {
    getPrefixListDlg().moveTo(label.getText());
    getPrefixListDlg().setVisible(true);
    if (getPrefixListDlg().isSelected()){
      label.setText(getPrefixListDlg().getSelectedPrefix());
      if(CodedPrefUtils.isPrefCoded(label.getText())){
      	tfPref.setVisible(false);
    	codedPref.setVisible(true);
    	codedPref.setPref(label.getText());
    	codedPref.requestFocus();
      } else {
      	tfPref.setVisible(true);
    	codedPref.setVisible(false);
    	tfPref.requestFocus();
      }
      dirtyPrefixSet = true;
    }
  }
  
  private void handleSearch() {
    String ctlgno = "ctlgno"; //$NON-NLS-1$
    String text1 = ""; //$NON-NLS-1$
  	String text2 = ""; //$NON-NLS-1$
  	String text3 = ""; //$NON-NLS-1$
  	String text4 = ""; //$NON-NLS-1$
  	String text5 = ""; //$NON-NLS-1$
    if(getTfPref1().isVisible())
  		text1 = getTfPref1().getText();
  	else
  		text1 = getCodedPref1().getText();
  	if(getTfPref2().isVisible())
  		text2 = getTfPref2().getText();
  	else
  		text2 = getCodedPref2().getText();
  	if(getTfPref3().isVisible())
  		text3 = getTfPref3().getText();
  	else
  		text3 = getCodedPref3().getText();  	
  	if(getTfPref4().isVisible())
  		text4 = getTfPref4().getText();
  	else
  		text4 = getCodedPref4().getText();
  	if(getTfPref5().isVisible())
  		text5 = getTfPref5().getText();
  	else
  		text5 = getCodedPref5().getText(); 
  	
    if (getLPref1().getText().equals("IN") && !getTfPref1().getText().trim().equals("")){ //$NON-NLS-1$ //$NON-NLS-2$
      ctlgno = Validator.convertCtlgNo2DB(getTfPref1().getText());
    }else if (getLPref2().getText().equals("IN") && !getTfPref2().getText().trim().equals("")){ //$NON-NLS-1$ //$NON-NLS-2$
      ctlgno = Validator.convertCtlgNo2DB(getTfPref2().getText());
    }else if (getLPref3().getText().equals("IN") && !getTfPref3().getText().trim().equals("")){ //$NON-NLS-1$ //$NON-NLS-2$
      ctlgno = Validator.convertCtlgNo2DB(getTfPref3().getText());
    }else if (getLPref4().getText().equals("IN") && !getTfPref4().getText().trim().equals("")){ //$NON-NLS-1$ //$NON-NLS-2$
      ctlgno = Validator.convertCtlgNo2DB(getTfPref4().getText());
    }else if (getLPref5().getText().equals("IN") && !getTfPref5().getText().trim().equals("")){ //$NON-NLS-1$ //$NON-NLS-2$
      ctlgno = Validator.convertCtlgNo2DB(getTfPref5().getText());
    }
    
	  if (!ctlgno.equals("")){ //$NON-NLS-1$
		  String pref1 = getLPref1().getText();
		  String pref2 = getLPref2().getText();
		  String pref3 = getLPref3().getText();
		  String pref4 = getLPref4().getText();
		  String pref5 = getLPref5().getText();
		  text1 = (getLPref1().getText().equals("IN") && !ctlgno.equals("ctlgno") ? ctlgno : text1);
		  text2 = (getLPref2().getText().equals("IN") && !ctlgno.equals("ctlgno") ? ctlgno : text2);
		  text3 = (getLPref3().getText().equals("IN") && !ctlgno.equals("ctlgno") ? ctlgno : text3);
		  text4 = (getLPref4().getText().equals("IN") && !ctlgno.equals("ctlgno") ? ctlgno : text4);
		  text5 = (getLPref5().getText().equals("IN") && !ctlgno.equals("ctlgno") ? ctlgno : text5);
		  String oper1 = getCmbOper1().getSelectedItem().toString().toUpperCase();
		  String oper2 = getCmbOper2().getSelectedItem().toString().toUpperCase();
		  String oper3 = getCmbOper3().getSelectedItem().toString().toUpperCase();
		  String oper4 = getCmbOper4().getSelectedItem().toString().toUpperCase();

		  Date startL = null;
		  Date endL = null;
		  if (tfStartDateL.getDate() != null){
			  startL = Utils.setMinDate(tfStartDateL.getDate());
			  if (tfEndDateL.getDate() != null){
				  endL = Utils.setMaxDate(tfEndDateL.getDate());
			  }else{
				  endL = Utils.setMaxDate(tfStartDateL.getDate());
			  }
		  }

		  String location = null;
		  if (Utils.getCmbValue(cmbLocL) != null){
			  location = ((CircLocation) Utils.getCmbValue(cmbLocL)).getDescription();
		  }else if (Utils.getCmbValue(cmbLocR) != null){
			  location = ((CircLocation) Utils.getCmbValue(cmbLocR)).getDescription();
		  }

		  Date startR = null;
		  Date endR = null;
		  if (tfStartDateR.getDate() != null){
			  startR = Utils.setMinDate(tfStartDateR.getDate());
			  if (tfEndDateR.getDate() != null){
				  endR = Utils.setMaxDate(tfEndDateR.getDate());
			  }else{
				  endR = Utils.setMaxDate(tfStartDateR.getDate());
			  }
		  }

		  filter = null;
		  if (BisisApp.appConfig.getLibrary().equals("bgb")) {
			  if (!cmbFilter.getSelectedItem().equals(" ")) {
				  filter = BisisApp.appConfig.getCodersHelper().getLocationCodeByNameExtended(cmbFilter.getSelectedItem().toString());
			  }
		  }

		  SearchModelCirc searchModel= new SearchModelCirc();
		  searchModel.setPref1(pref1);
		  searchModel.setPref2(pref2);
		  searchModel.setPref3(pref3);
		  searchModel.setPref4(pref4);
		  searchModel.setPref5(pref5);
		  searchModel.setText1(text1);
		  searchModel.setText2(text2);
		  searchModel.setText3(text3);
		  searchModel.setText4(text4);
		  searchModel.setText5(text5);
		  searchModel.setOper1(oper1);
		  searchModel.setOper2(oper2);
		  searchModel.setOper3(oper3);
		  searchModel.setOper4(oper4);
		  searchModel.setStartDateLend(startL);
		  searchModel.setEndDateLend(endL);
		  searchModel.setStartDateRet(startR);
		  searchModel.setEndDateRet(endR);
		  searchModel.setLocation(location);
		  if (BisisApp.appConfig.getLibrary().equals("bgb") && filter != null) {
			  searchModel.getDepartments().add(filter);
		  }

//		  List list = null;
//		  if (tfStartDateL.getDate() != null || tfStartDateR.getDate() != null){
//				 list = Cirkulacija.getApp().getUserManager().getCtlgNos(tfStartDateL.getDate(), tfEndDateL.getDate(), (CircLocation) Utils.getCmbValue(cmbLocL),
//				  tfStartDateR.getDate(), tfEndDateR.getDate(), (CircLocation)Utils.getCmbValue(cmbLocR));
//			   if (list == null)
//				 list = new ArrayList();
//		  }
    //System.out.println(q.toString());
  //    StopWatch clock = new StopWatch();
  //    clock.start();
      int res = 0;
      if (searchModel != null){
        res = Cirkulacija.getApp().getRecordsManager().getRecords(searchModel);
      }else{
        return;
      }
      
  //    clock.stop();
  //    System.out.println("clock:"+clock.getTime());
    
      if (res == 0){
      	 JOptionPane.showMessageDialog(BisisApp.getMainFrame(), 
      	          Messages.getString("circulation.resultsettoobig"), Messages.getString("circulation.searching"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
      }else if(res == 1){
      	 JOptionPane.showMessageDialog(BisisApp.getMainFrame(), 
      	          Messages.getString("circulation.noresults"), Messages.getString("circulation.searching"), JOptionPane.INFORMATION_MESSAGE);  //$NON-NLS-1$ //$NON-NLS-2$
      }
    }else{
      JOptionPane.showMessageDialog(BisisApp.getMainFrame(), Messages.getString("circulation.acquisitnowarning"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
          new ImageIcon(getClass().getResource("/com/gint/app/bisis4/client/circ/images/x32.png"))); //$NON-NLS-1$
    }
  }

  public String getFilter() {
		return filter;
  }
  
  public void loadCmbLocL(List data){
    Utils.loadCombo(getCmbLocL(), data);
  }
  
  public void loadCmbLocR(List data){
    Utils.loadCombo(getCmbLocR(), data);
  }

	public void loadCmbFilter(List data){
		Utils.loadCombo(getCmbFilter(), data);
		if (BisisApp.appConfig.getLibrarian().getDefaultDepartment() != null)
			getCmbFilter().setSelectedItem(BisisApp.appConfig.getLibrarian().getDefaultDepartment());
	}
  
  public void updatePrefixes() {
    Librarian lib = Cirkulacija.getApp().getLibrarian();
    
    getLPref1().setText(lib.getContext().getPref1());
    if(CodedPrefUtils.isPrefCoded(getLPref1().getText())){
    	getTfPref1().setVisible(false);
  		getCodedPref1().setVisible(true);
  		getCodedPref1().setPref(getLPref1().getText());
    } else {
    	getTfPref1().setVisible(true);
    	getCodedPref1().setVisible(false);
    }
    
    getLPref2().setText(lib.getContext().getPref2());
    if(CodedPrefUtils.isPrefCoded(getLPref2().getText())){
    	getTfPref2().setVisible(false);
  		getCodedPref2().setVisible(true);
  		getCodedPref2().setPref(getLPref2().getText());
    } else {
    	getTfPref2().setVisible(true);
    	getCodedPref2().setVisible(false);
    }
    
    getLPref3().setText(lib.getContext().getPref3());
    if(CodedPrefUtils.isPrefCoded(getLPref3().getText())){
    	getTfPref3().setVisible(false);
  		getCodedPref3().setVisible(true);
  		getCodedPref3().setPref(getLPref3().getText());
    } else {
    	getTfPref3().setVisible(true);
    	getCodedPref3().setVisible(false);
    }
    
    getLPref4().setText(lib.getContext().getPref4());
    if(CodedPrefUtils.isPrefCoded(getLPref4().getText())){
    	getTfPref4().setVisible(false);
  		getCodedPref4().setVisible(true);
  		getCodedPref4().setPref(getLPref4().getText());
    } else {
    	getTfPref4().setVisible(true);
    	getCodedPref4().setVisible(false);
    }
    
    getLPref5().setText(lib.getContext().getPref5());
    if(CodedPrefUtils.isPrefCoded(getLPref5().getText())){
    	getTfPref5().setVisible(false);
  		getCodedPref5().setVisible(true);
  		getCodedPref5().setPref(getLPref5().getText());
    } else {
    	getTfPref5().setVisible(true);
    	getCodedPref5().setVisible(false);
    }
  }  
  
  public void init() {
//		getLPref1().setText("AU"); //$NON-NLS-1$
//		getTfPref1().setVisible(true);
//		getCodedPref1().setVisible(false);
//		getLPref2().setText("TI"); //$NON-NLS-1$
//		getTfPref2().setVisible(true);
//		getCodedPref2().setVisible(false);
//		getLPref3().setText("PU"); //$NON-NLS-1$
//		getTfPref3().setVisible(true);
//		getCodedPref3().setVisible(false);
//		getLPref4().setText("PY"); //$NON-NLS-1$
//		getTfPref4().setVisible(true);
//		getCodedPref4().setVisible(false);
//		getLPref5().setText("KW"); //$NON-NLS-1$
//		getTfPref5().setVisible(false);
//		getCodedPref5().setVisible(true);
//		getCodedPref5().setPref("KW"); //$NON-NLS-1$
  	
  	updatePrefixes();

    getPanel().addComponentListener(new ComponentAdapter() {
      public void componentShown(ComponentEvent e){
        getTfPref1().requestFocusInWindow();
      }
    });
	}
  
  public void closeSearchFrame() {
		if(dirtyPrefixSet){
			Librarian lib = Cirkulacija.getApp().getLibrarian();
			lib.getContext().setPref1(getLPref1().getText());
			lib.getContext().setPref2(getLPref2().getText());
			lib.getContext().setPref3(getLPref3().getText());
			lib.getContext().setPref4(getLPref4().getText());
			lib.getContext().setPref5(getLPref5().getText());
			LibEnvironment.updateLibrarian(lib);
			dirtyPrefixSet = false;
		}		
	}  

}
