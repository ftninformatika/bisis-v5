package com.ftninformatika.bisis.circ.view;

import javax.swing.JPanel;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.common.UsersPrefix;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.models.coders.Location;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;

public class SearchUsers {

	private JTextField tfPref1 = null;
	private JTextField tfPref2 = null;
	private JTextField tfPref3 = null;
	private JTextField tfPref4 = null;
	private JTextField tfPref5 = null;
	private JComboBox cmbPref1 = null;
	private JComboBox cmbPref2 = null;
	private JComboBox cmbPref3 = null;
	private JComboBox cmbPref4 = null;
	private JComboBox cmbPref5 = null;
	private JComboBox cmbOper1 = null;
	private JComboBox cmbOper2 = null;
	private JComboBox cmbOper3 = null;
	private JComboBox cmbOper4 = null;
	private JDateChooser tfStartDate1 = null;
	private JDateChooser tfStartDate2 = null;
	private JDateChooser tfEndDate1 = null;
	private JDateChooser tfEndDate2 = null;
	private JButton btnSearch = null;
	private JButton btnCancel = null;
	private JButton btnPref1 = null;
	private JButton btnPref2 = null;
	private JButton btnPref3 = null;
	private JButton btnPref4 = null;
	private JButton btnPref5 = null;
	private JButton btnDate1 = null;
	private JButton btnDate2 = null;
	private PanelBuilder pb = null;
	private JLabel lPref1 = null;
	private JLabel lPref2 = null;
	private JLabel lPref3 = null;
	private JLabel lPref4 = null;
	private JLabel lPref5 = null;
	private JLabel lDate1 = null;
	private JLabel lDate2 = null;
	private JPanel buttonPanel = null;
	private JComboBox cmbLoc1 = null;
	private JComboBox cmbLoc2 = null;
	private UsersPrefixPanel usersPrefixPanel = null;
	private UsersPrefixPanel datePrefixPanel = null;
	private ComboBoxRenderer cmbRenderer = null;
	private CmbKeySelectionManager cmbKeySelectionManager = null;

	public SearchUsers() {
		makePanel();
		init();
	}

	private SearchUsers getSearchUsers() {
		return this;
	}

	private void makePanel() {

		FormLayout layout = new FormLayout(
				"2dlu:grow, right:70dlu, 5dlu, 10dlu, 5dlu, 70dlu, 10dlu, 70dlu, 5dlu, 30dlu, 55dlu, 2dlu:grow", //$NON-NLS-1$
				"20dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 20dlu, pref, 10dlu, pref, 2dlu, pref, 2dlu, pref, 20dlu, pref, 2dlu:grow"); //$NON-NLS-1$
		CellConstraints cc = new CellConstraints();
		pb = new PanelBuilder(layout);
		pb.setDefaultDialogBorder();

		pb.add(getLPref1(), cc.xy(2, 2));
		pb.add(getBtnPref1(), cc.xy(4, 2));
		pb.add(getTfPref1(), cc.xyw(6, 2, 3));
		pb.add(getCmbPref1(), cc.xyw(6, 2, 3));
		pb.add(getCmbOper1(), cc.xy(10, 2));
		pb.add(getLPref2(), cc.xy(2, 4));
		pb.add(getBtnPref2(), cc.xy(4, 4));
		pb.add(getTfPref2(), cc.xyw(6, 4, 3));
		pb.add(getCmbPref2(), cc.xyw(6, 4, 3));
		pb.add(getCmbOper2(), cc.xy(10, 4));
		pb.add(getLPref3(), cc.xy(2, 6));
		pb.add(getBtnPref3(), cc.xy(4, 6));
		pb.add(getTfPref3(), cc.xyw(6, 6, 3));
		pb.add(getCmbPref3(), cc.xyw(6, 6, 3));
		pb.add(getCmbOper3(), cc.xy(10, 6));
		pb.add(getLPref4(), cc.xy(2, 8));
		pb.add(getBtnPref4(), cc.xy(4, 8));
		pb.add(getTfPref4(), cc.xyw(6, 8, 3));
		pb.add(getCmbPref4(), cc.xyw(6, 8, 3));
		pb.add(getCmbOper4(), cc.xy(10, 8));
		pb.add(getLPref5(), cc.xy(2, 10));
		pb.add(getBtnPref5(), cc.xy(4, 10));
		pb.add(getTfPref5(), cc.xyw(6, 10, 3));
		pb.add(getCmbPref5(), cc.xyw(6, 10, 3));

		pb.addSeparator("", cc.xyw(2, 12, 10)); //$NON-NLS-1$
		pb.add(getLDate1(), cc.xy(2, 14));
		pb.add(getBtnDate1(), cc.xy(4, 14));
		pb.add(getTfStartDate1(), cc.xy(6, 14));
		pb.addLabel("-", cc.xy(7, 14, "center, center")); //$NON-NLS-1$ //$NON-NLS-2$
		pb.add(getTfEndDate1(), cc.xy(8, 14));
		pb.add(getCmbLoc1(), cc.xyw(10, 14, 2));
		pb.add(getLDate2(), cc.xy(2, 16));
		pb.add(getBtnDate2(), cc.xy(4, 16));
		pb.add(getTfStartDate2(), cc.xy(6, 16));
		pb.addLabel("-", cc.xy(7, 16, "center, center")); //$NON-NLS-1$ //$NON-NLS-2$
		pb.add(getTfEndDate2(), cc.xy(8, 16));
		pb.add(getCmbLoc2(), cc.xyw(10, 16, 2));

		pb.add(getButtonPanel(), cc.xyw(2, 20, 10));
	}

	public JPanel getPanel() {
		return pb.getPanel();
	}

	public JTextField getTfPref1() {
		if (tfPref1 == null) {
			tfPref1 = new JTextField();
			tfPref1.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent ev) {
          handleKeys(getLPref1(), tfPref1, getCmbPref1(), ev);
				}
			});
		}
		return tfPref1;
	}

	public JTextField getTfPref2() {
		if (tfPref2 == null) {
			tfPref2 = new JTextField();
			tfPref2.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent ev) {
				  handleKeys(getLPref2(), tfPref2, getCmbPref2(), ev);
				}
			});
		}
		return tfPref2;
	}

	public JTextField getTfPref3() {
		if (tfPref3 == null) {
			tfPref3 = new JTextField();
			tfPref3.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent ev) {
          handleKeys(getLPref3(), tfPref3, getCmbPref3(), ev);
				}
			});
		}
		return tfPref3;
	}

	public JTextField getTfPref4() {
		if (tfPref4 == null) {
			tfPref4 = new JTextField();
			tfPref4.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent ev) {
          handleKeys(getLPref4(), tfPref4, getCmbPref4(), ev);
				}
			});
		}
		return tfPref4;
	}

	public JTextField getTfPref5() {
		if (tfPref5 == null) {
			tfPref5 = new JTextField();
			tfPref5.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent ev) {
          handleKeys(getLPref5(), tfPref5, getCmbPref5(), ev);
				}
			});
		}
		return tfPref5;
	}

	public JComboBox getCmbPref1() {
		if (cmbPref1 == null) {
			cmbPref1 = new JComboBox();
			cmbPref1.setRenderer(getCmbRenderer());
			cmbPref1.setKeySelectionManager(getCmbKeySelectionManager());
		}
		return cmbPref1;
	}

	public JComboBox getCmbPref2() {
		if (cmbPref2 == null) {
			cmbPref2 = new JComboBox();
			cmbPref2.setRenderer(getCmbRenderer());
			cmbPref2.setKeySelectionManager(getCmbKeySelectionManager());
		}
		return cmbPref2;
	}

	public JComboBox getCmbPref3() {
		if (cmbPref3 == null) {
			cmbPref3 = new JComboBox();
			cmbPref3.setRenderer(getCmbRenderer());
			cmbPref3.setKeySelectionManager(getCmbKeySelectionManager());
		}
		return cmbPref3;
	}

	public JComboBox getCmbPref4() {
		if (cmbPref4 == null) {
			cmbPref4 = new JComboBox();
			cmbPref4.setRenderer(getCmbRenderer());
			cmbPref4.setKeySelectionManager(getCmbKeySelectionManager());
		}
		return cmbPref4;
	}

	public JComboBox getCmbPref5() {
		if (cmbPref5 == null) {
			cmbPref5 = new JComboBox();
			cmbPref5.setRenderer(getCmbRenderer());
			cmbPref5.setKeySelectionManager(getCmbKeySelectionManager());
		}
		return cmbPref5;
	}

	private JButton getBtnPref1() {
		if (btnPref1 == null) {
			btnPref1 = new JButton();
			btnPref1.setText("..."); //$NON-NLS-1$
			btnPref1.setFocusable(false);
			btnPref1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
          choosePrefix(getLPref1(), getTfPref1(), getCmbPref1());
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
          choosePrefix(getLPref2(), getTfPref2(), getCmbPref2());
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
          choosePrefix(getLPref3(), getTfPref3(), getCmbPref3());
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
          choosePrefix(getLPref4(), getTfPref4(), getCmbPref4());
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
          choosePrefix(getLPref5(), getTfPref5(), getCmbPref5());
				}
			});
		}
		return btnPref5;
	}

	public JComboBox getCmbOper1() {
		if (cmbOper1 == null) {
			cmbOper1 = new JComboBox();
			cmbOper1.addItem("and"); //$NON-NLS-1$
			cmbOper1.addItem("or"); //$NON-NLS-1$
			cmbOper1.addItem("not"); //$NON-NLS-1$
			//cmbOper1.setFocusable(false);
		}
		return cmbOper1;
	}

	public JComboBox getCmbOper2() {
		if (cmbOper2 == null) {
			cmbOper2 = new JComboBox();
			cmbOper2.addItem("and"); //$NON-NLS-1$
			cmbOper2.addItem("or"); //$NON-NLS-1$
			cmbOper2.addItem("not"); //$NON-NLS-1$
			//cmbOper2.setFocusable(false);
		}
		return cmbOper2;
	}

	public JComboBox getCmbOper3() {
		if (cmbOper3 == null) {
			cmbOper3 = new JComboBox();
			cmbOper3.addItem("and"); //$NON-NLS-1$
			cmbOper3.addItem("or"); //$NON-NLS-1$
			cmbOper3.addItem("not"); //$NON-NLS-1$
			//cmbOper3.setFocusable(false);
		}
		return cmbOper3;
	}

	public JComboBox getCmbOper4() {
		if (cmbOper4 == null) {
			cmbOper4 = new JComboBox();
			cmbOper4.addItem("and"); //$NON-NLS-1$
			cmbOper4.addItem("or"); //$NON-NLS-1$
			cmbOper4.addItem("not"); //$NON-NLS-1$
			//cmbOper4.setFocusable(false);
		}
		return cmbOper4;
	}

	private JDateChooser getTfStartDate1() {
		if (tfStartDate1 == null) {
			tfStartDate1 = new JDateChooser();
      tfStartDate1.getDateEditor().getUiComponent().addKeyListener(new KeyAdapter() {
        public void keyReleased(KeyEvent ev) {
          handleKeys(getLDate1(), tfStartDate1, null, ev);
        }

      });
		}
		return tfStartDate1;
	}

	public Date getStartDate1() {
		Date d = getTfStartDate1().getDate();
		return d;
	}

	private JDateChooser getTfStartDate2() {
		if (tfStartDate2 == null) {
			tfStartDate2 = new JDateChooser();
      tfStartDate2.getDateEditor().getUiComponent().addKeyListener(new KeyAdapter() {
        public void keyReleased(KeyEvent ev) {
          handleKeys(getLDate2(),tfStartDate2, null, ev);
        }
      });
		}
		return tfStartDate2;
	}

	public Date getStartDate2() {
		Date d = getTfStartDate2().getDate();
		return d;
	}

	private JDateChooser getTfEndDate1() {
		if (tfEndDate1 == null) {
			tfEndDate1 = new JDateChooser();
      tfEndDate1.getDateEditor().getUiComponent().addKeyListener(new KeyAdapter() {
        public void keyReleased(KeyEvent ev) {
          handleKeys(getLDate1(), tfEndDate1, null, ev);
        }
      });
		}
		return tfEndDate1;
	}

	public Date getEndDate1() {
		Date d = getTfEndDate1().getDateEditor().getDate();
		return d;
	}

	private JDateChooser getTfEndDate2() {
		if (tfEndDate2 == null) {
			tfEndDate2 = new JDateChooser();
      tfEndDate2.getDateEditor().getUiComponent().addKeyListener(new KeyAdapter() {
        public void keyReleased(KeyEvent ev) {
          handleKeys(getLDate2(), tfEndDate2, null, ev);
        }

      });
		}
		return tfEndDate2;
	}

	public Date getEndDate2() {
		Date d = getTfEndDate2().getDateEditor().getDate();
		return d;
	}

	private JButton getBtnDate1() {
		if (btnDate1 == null) {
			btnDate1 = new JButton();
			btnDate1.setText("..."); //$NON-NLS-1$
			btnDate1.setFocusable(false);
			btnDate1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
          chooseDatePrefix(getLDate1());
				}
			});
		}
		return btnDate1;
	}

	private JButton getBtnDate2() {
		if (btnDate2 == null) {
			btnDate2 = new JButton();
			btnDate2.setText("..."); //$NON-NLS-1$
			btnDate2.setFocusable(false);
			btnDate2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
          chooseDatePrefix(getLDate2());
				}
			});
		}
		return btnDate2;
	}

	public JComboBox getCmbLoc1() {
		if (cmbLoc1 == null) {
			cmbLoc1 = new JComboBox();
			//cmbLoc1.setFocusable(false);
			cmbLoc1.setRenderer(getCmbRenderer());
			cmbLoc1.setKeySelectionManager(getCmbKeySelectionManager());
		}
		return cmbLoc1;
	}

	public JComboBox getCmbLoc2() {
		if (cmbLoc2 == null) {
			cmbLoc2 = new JComboBox();
			//cmbLoc2.setFocusable(false);
			cmbLoc2.setRenderer(getCmbRenderer());
			cmbLoc2.setKeySelectionManager(getCmbKeySelectionManager());
		}
		return cmbLoc2;
	}
  
  public void loadCmbLoc1(List data){
    Utils.loadCombo(getCmbLoc1(), data);
  }
  
  public void loadCmbLoc2(List data){
    Utils.loadCombo(getCmbLoc2(), data);
  }

	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText(Messages.getString("circulation.search")); //$NON-NLS-1$
			btnSearch.setFocusable(false);
			btnSearch.setIcon(new ImageIcon(getClass().getResource(
					"/circ-images/find16.png"))); //$NON-NLS-1$
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//Cirkulacija.getApp().getSearchUsersManager().executeSearch(getSearchUsers());
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
			btnCancel.setIcon(new ImageIcon(getClass().getResource(
					"/circ-images/Delete16.png"))); //$NON-NLS-1$
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Cirkulacija.getApp().getMainFrame().previousPanel();
					Utils.clear(getPanel());
				}
			});
		}
		return btnCancel;
	}

	public JLabel getLPref1() {
		if (lPref1 == null) {
			lPref1 = new JLabel();
		}
		return lPref1;
	}

	public JLabel getLPref2() {
		if (lPref2 == null) {
			lPref2 = new JLabel();
		}
		return lPref2;
	}

	public JLabel getLPref3() {
		if (lPref3 == null) {
			lPref3 = new JLabel();
		}
		return lPref3;
	}

	public JLabel getLPref4() {
		if (lPref4 == null) {
			lPref4 = new JLabel();
		}
		return lPref4;
	}

	public JLabel getLPref5() {
		if (lPref5 == null) {
			lPref5 = new JLabel();
		}
		return lPref5;
	}

	public JLabel getLDate1() {
		if (lDate1 == null) {
			lDate1 = new JLabel();
		}
		return lDate1;
	}

	public JLabel getLDate2() {
		if (lDate2 == null) {
			lDate2 = new JLabel();
		}
		return lDate2;
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

	private UsersPrefixPanel getUsersPrefixPanel() {
		if (usersPrefixPanel == null) {
			usersPrefixPanel = new UsersPrefixPanel(BisisApp.getMainFrame());
			usersPrefixPanel.initUsersPrefix();
		}
		return usersPrefixPanel;

	}

	private UsersPrefixPanel getDatePrefixPanel() {
		if (datePrefixPanel == null) {
			datePrefixPanel = new UsersPrefixPanel(BisisApp.getMainFrame());
			datePrefixPanel.initDatePrefix();
		}
		return datePrefixPanel;

	}

	private void choosePrefix(JLabel lPref, JTextField tfPref, JComboBox cmbPref) {
    getUsersPrefixPanel().setVisible(true);
    if (getUsersPrefixPanel().isSelected()){
  		UsersPrefix up = (UsersPrefix) getUsersPrefixPanel().getSelectedItem();
  		changePrefix(up, lPref, tfPref, cmbPref);
    }
	}

	private void chooseDatePrefix(JLabel lDate) {
    getDatePrefixPanel().setVisible(true);
    if (getDatePrefixPanel().isSelected()){
  		UsersPrefix up = (UsersPrefix) getDatePrefixPanel().getSelectedItem();
  		lDate.setText(up.getName());
    }
	}
  
  private void handleKeys(JLabel lPref, JComponent tfPref, JComboBox cmbPref, KeyEvent ev) {
    switch (ev.getKeyCode()) {
      case KeyEvent.VK_ENTER:
        btnSearch.doClick();
        break;
      case KeyEvent.VK_ESCAPE:
        btnCancel.doClick();
        break;
      case KeyEvent.VK_F9:
      	if (tfPref instanceof JTextField){
          choosePrefix(lPref, (JTextField)tfPref, cmbPref);
      	} else {
      		chooseDatePrefix(lPref);
      	}
        break;
      case KeyEvent.VK_DOWN:
        if (tfPref == tfPref1){
          tfPref2.requestFocusInWindow();
          cmbPref2.requestFocusInWindow();
        }
        else if (tfPref == tfPref2){
          tfPref3.requestFocusInWindow();
          cmbPref3.requestFocusInWindow();
        }
        else if (tfPref == tfPref3){
          tfPref4.requestFocusInWindow();
          cmbPref4.requestFocusInWindow();
        }
        else if (tfPref == tfPref4){
          tfPref5.requestFocusInWindow();
          cmbPref5.requestFocusInWindow();
        }
        else if (tfPref == tfPref5)
          tfStartDate1.getDateEditor().getUiComponent().requestFocusInWindow();
        else if (tfPref == tfStartDate1)
          tfStartDate2.getDateEditor().getUiComponent().requestFocusInWindow();
        else if (tfPref == tfEndDate1)
          tfEndDate2.getDateEditor().getUiComponent().requestFocusInWindow();
        else if (tfPref == tfStartDate2){
          tfPref1.requestFocusInWindow();
          cmbPref1.requestFocusInWindow();
        }
        else if (tfPref == tfEndDate2){
          tfPref1.requestFocusInWindow();
          cmbPref1.requestFocusInWindow();
        }
          
        break;
      case KeyEvent.VK_UP:
        if (tfPref == tfPref1)
          tfStartDate2.getDateEditor().getUiComponent().requestFocusInWindow();
        else if (tfPref == tfPref2){
          tfPref1.requestFocusInWindow();
          cmbPref1.requestFocusInWindow();
        }
        else if (tfPref == tfPref3){
          tfPref2.requestFocusInWindow();
          cmbPref2.requestFocusInWindow();
        }
        else if (tfPref == tfPref4){
          tfPref3.requestFocusInWindow();
          cmbPref3.requestFocusInWindow();
        }
        else if (tfPref == tfPref5){
          tfPref4.requestFocusInWindow();
          cmbPref4.requestFocusInWindow();
        }
        else if (tfPref == tfStartDate1){
          tfPref5.requestFocusInWindow();
          cmbPref5.requestFocusInWindow();
        }
        else if (tfPref == tfEndDate1){
          tfPref5.requestFocusInWindow();
          cmbPref5.requestFocusInWindow();
        }
        else if (tfPref == tfStartDate2){
          tfStartDate1.getDateEditor().getUiComponent().requestFocusInWindow();
        }
        else if (tfPref == tfEndDate2){
          tfEndDate1.getDateEditor().getUiComponent().requestFocusInWindow();
        }
        
        break;  
    }
  }

	private void changePrefix(UsersPrefix up, JLabel lPref, JTextField tfPref, JComboBox cmbPref) {
		ComboBoxModel comboModel = null;
		/*if (up.getDbname().equals("userCategs")) { //$NON-NLS-1$
			comboModel = Cirkulacija.getApp().getMainFrame().getUserPanel().getMmbrship().getUserCategsModel();
			cmbPref.setModel(comboModel);
			cmbPref.setVisible(true);
			tfPref.setVisible(false);
			lPref.setText(up.getName());
			cmbPref.requestFocus();
		} else if (up.getDbname().equals("mmbrTypes")) { //$NON-NLS-1$
			comboModel = Cirkulacija.getApp().getMainFrame().getUserPanel().getMmbrship().getMmbrTypesModel();
			cmbPref.setModel(comboModel);
			cmbPref.setVisible(true);
			tfPref.setVisible(false);
			lPref.setText(up.getName());
			cmbPref.requestFocus();
		} else if (up.getDbname().equals("groups")) { //$NON-NLS-1$
			comboModel = Cirkulacija.getApp().getMainFrame().getUserPanel().getMmbrship().getGroupsModel();
			cmbPref.setModel(comboModel);
			cmbPref.setVisible(true);
			tfPref.setVisible(false);
			lPref.setText(up.getName());
			cmbPref.requestFocus();
		} else */if (up.getDbname().equals("organization")) { //$NON-NLS-1$
			comboModel = Cirkulacija.getApp().getMainFrame().getUserPanel().getUserData().getOrgModel();
			cmbPref.setModel(comboModel);
			cmbPref.setVisible(true);
			tfPref.setVisible(false);
			lPref.setText(up.getName());
			cmbPref.requestFocus();
		} else if (up.getDbname().equals("eduLvl")) { //$NON-NLS-1$
			comboModel = Cirkulacija.getApp().getMainFrame().getUserPanel().getUserData().getEduLvlModel();
			cmbPref.setModel(comboModel);
			cmbPref.setVisible(true);
			tfPref.setVisible(false);
			lPref.setText(up.getName());
			cmbPref.requestFocus();
		} else if (up.getDbname().equals("language")) { //$NON-NLS-1$
			comboModel = Cirkulacija.getApp().getMainFrame().getUserPanel().getUserData().getLanguageModel();
			cmbPref.setModel(comboModel);
			cmbPref.setVisible(true);
			tfPref.setVisible(false);
			lPref.setText(up.getName());
			cmbPref.requestFocus();
		} else if (up.getDbname().equals("classNo")) { //$NON-NLS-1$
			comboModel = Cirkulacija.getApp().getMainFrame().getUserPanel().getUserData().getClassNoModel();
			cmbPref.setModel(comboModel);
			cmbPref.setVisible(true);
			tfPref.setVisible(false);
			lPref.setText(up.getName());
			cmbPref.requestFocus();
		} else {
			cmbPref.setVisible(false);
			tfPref.setVisible(true);
			lPref.setText(up.getName());
			tfPref.requestFocus();
		}
	}

	public void init() {
		getCmbPref1().setVisible(false);
		getCmbPref2().setVisible(false);
		getCmbPref3().setVisible(false);
		getCmbPref4().setVisible(false);
		getCmbPref5().setVisible(false);
		getLPref1().setText(Messages.getString("circulation.firstname")); //$NON-NLS-1$
		getTfPref1().setVisible(true);
		getLPref2().setText(Messages.getString("circulation.lastname")); //$NON-NLS-1$
		getTfPref2().setVisible(true);
		getLPref3().setText(Messages.getString("circulation.umcn")); //$NON-NLS-1$
		getTfPref3().setVisible(true);
		getLPref4().setText(Messages.getString("circulation.address")); //$NON-NLS-1$
		getTfPref4().setVisible(true);
		getLPref5().setText(Messages.getString("circulation.usernumber")); //$NON-NLS-1$
		getTfPref5().setVisible(true);

		getLDate1().setText(Messages.getString("circulation.chargingdate")); //$NON-NLS-1$
		getLDate2().setText(Messages.getString("circulation.dischargingdate")); //$NON-NLS-1$
    getPanel().addComponentListener(new ComponentAdapter() {
      public void componentShown(ComponentEvent e){
        getTfPref1().requestFocusInWindow();
      }
    });
	}
  
	public String getSearchQuery( String pref1, String oper1, String text1,
		      String pref2, String oper2, String text2,
		      String pref3, String oper3, String text3,
		      String pref4, String oper4, String text4,
		      String pref5, String text5,
		      String pref6, String startd1,String endd1,Object loc1,
		      String pref7, String startd2,String endd2,Object loc2){
		String q=" "; //$NON-NLS-1$
		if (!text1.equals("")){ //$NON-NLS-1$
			q=q+pref1+"="+text1+" "; //$NON-NLS-1$ //$NON-NLS-2$
			if (!text2.equals("")||(!text3.equals(""))||(!text4.equals(""))||(!text5.equals(""))){ //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				q=q+oper1+" "; //$NON-NLS-1$
			}
		}
		if (!text2.equals("")){ //$NON-NLS-1$
			q=q+pref2+"="+text2+" "; //$NON-NLS-1$ //$NON-NLS-2$
			if (!text3.equals("")||(!text4.equals(""))||(!text5.equals(""))){ //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				q=q+oper2+" "; //$NON-NLS-1$
			}
		}
		if (!text3.equals("")){ //$NON-NLS-1$
			q=q+pref3+"="+text3+" "; //$NON-NLS-1$ //$NON-NLS-2$
			if ((!text4.equals(""))||(!text5.equals(""))){ //$NON-NLS-1$ //$NON-NLS-2$
				q=q+oper3+" "; //$NON-NLS-1$
			}
		}
		if (!text4.equals("")){ //$NON-NLS-1$
			q=q+pref4+"="+text4+" "; //$NON-NLS-1$ //$NON-NLS-2$
			if (!text5.equals("")){ //$NON-NLS-1$
				q=q+oper4+" "; //$NON-NLS-1$
			}
		}
		if (!text5.equals("")){ //$NON-NLS-1$
			q=q+pref5+"="+text5+" "; //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		if (!startd1.equals("")&&(!endd1.equals(""))){ //$NON-NLS-1$ //$NON-NLS-2$
			q=q+"and "+ pref6+"="+startd1+" do "+endd1+" "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		else if (!startd1.equals("")&&(endd1.equals(""))){ //$NON-NLS-1$ //$NON-NLS-2$
			q=q+"and "+pref6+"="+startd1+" "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		else if (startd1.equals("")&&(!endd1.equals(""))){ //$NON-NLS-1$ //$NON-NLS-2$
			q=q+"and "+pref6+"="+endd1+" "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		if(!loc1.equals(" ")){ //$NON-NLS-1$
			q=q+" and lokacija "+((Location)loc1).getDescription()+" "; //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (!startd2.equals("")&&(!endd2.equals(""))){ //$NON-NLS-1$ //$NON-NLS-2$
			q=q+"and "+pref7+"="+startd2+" do "+endd2+" "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		else if (!startd2.equals("")&&(endd2.equals(""))){ //$NON-NLS-1$ //$NON-NLS-2$
			q=q+"and "+pref7+"="+startd2+" "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		else if (startd2.equals("")&&(!endd2.equals(""))){ //$NON-NLS-1$ //$NON-NLS-2$
			q=q+"and "+pref7+"="+endd2+" "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		if(!loc2.equals(" ")){ //$NON-NLS-1$
			q=q+" and lokacija "+((Location)loc2).getDescription();//TODO-coderId???? getName(); //$NON-NLS-1$
		}

		return q;
		
	}
  
  public String getSearchQuery(){
    return getSearchQuery(getLPref1().getText(),getCmbOper1().getSelectedItem().toString(),getTfPref1().getText(),
        getLPref2().getText(),getCmbOper2().getSelectedItem().toString(),getTfPref2().getText(),
        getLPref3().getText(),getCmbOper3().getSelectedItem().toString(),getTfPref3().getText(),
        getLPref4().getText(),getCmbOper4().getSelectedItem().toString(),getTfPref4().getText(),
        getLPref5().getText(),getTfPref5().getText(),
        getLDate1().getText(),Utils.toLocaleDate(getStartDate1()),Utils.toLocaleDate(getEndDate1()),getCmbLoc1().getSelectedItem(),
        getLDate2().getText(),Utils.toLocaleDate(getStartDate2()),Utils.toLocaleDate(getEndDate2()),getCmbLoc2().getSelectedItem());
  }

}
