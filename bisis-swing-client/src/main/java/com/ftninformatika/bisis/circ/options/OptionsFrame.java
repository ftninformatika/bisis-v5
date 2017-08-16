package com.ftninformatika.bisis.circ.options;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.UIManager.LookAndFeelInfo;

import com.ftninformatika.bisis.circ.validator.Validate;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.FlowLayout;

public class OptionsFrame extends JInternalFrame {

	private JPanel jContentPane = null;
	private JTabbedPane tpOpt = null;
	private PanelBuilder mandatoryPanel = null;
	private JCheckBox chkFirstName = null;
	private JCheckBox chkLastName = null;
	private JCheckBox chkParentName = null;
	private JCheckBox chkAddress = null;
	private JCheckBox chkZip = null;
	private JCheckBox chkCity = null;
	private JCheckBox chkPhone = null;
	private JCheckBox chkEmail = null;
	private JCheckBox chkJmbg = null;
	private JCheckBox chkDocNo = null;
	private JCheckBox chkDocCity = null;
	private JCheckBox chkCountry = null;
	private JCheckBox chkTmpAddress = null;
	private JCheckBox chkTmpZip = null;
	private JCheckBox chkTmpCity = null;
	private JCheckBox chkTmpPhone = null;
	private JCheckBox chkOccupation = null;
	private JCheckBox chkTitle = null;
	private JCheckBox chkIndexNo = null;
	private JCheckBox chkNote = null;
	private JCheckBox chkInterests = null;
	private JCheckBox chkUserID = null;
	private JCheckBox chkSignDate = null;
	private JCheckBox chkCost = null;
	private JCheckBox chkUntilDate = null;
	private JCheckBox chkReceiptID = null;
  private JCheckBox chkOrganization = null;
  private JCheckBox chkEduLvl = null;
  private JCheckBox chkLanguage = null;
  private JCheckBox chkClass = null;
  private JCheckBox chkMmbrType = null;
  private JCheckBox chkUserCateg = null;
	private PanelBuilder userIDPanel = null;
	private JTextField tfUserIDLength = null;
	private JCheckBox chkPrefix = null;
	private JTextField tfPrefixLength = null;
	private JTextField tfDefaultPrefix = null;
	private JCheckBox chkSeparator = null;
	private JTextField tfSeparator = null;
	private JRadioButton rbNumSepPre = null;
	private JRadioButton rbPreSepNum = null;
	private ButtonGroup useridGroup = null;
	private PanelBuilder ctlgNoPanel = null;
	private JTextField tfCtlgNoLength = null;
	private JCheckBox chkLocation = null;
	private JTextField tfLocationLength = null;
	private JTextField tfDefaultLocation = null;
	private JCheckBox chkBook = null;
	private JTextField tfBookLength = null;
	private JTextField tfDefaultBook = null;
	private JTextField tfSeparator1 = null;
	private JTextField tfSeparator2 = null;
	private JRadioButton rbNumS1LocS2Book = null;
	private JRadioButton rbLocS1BookS2Num = null;
	private ButtonGroup ctlgnoGroup = null;
	private JCheckBox chkSeparators = null;
	private JComboBox cmbSize = null;
	private JComboBox cmbLookAndFeel = null;
	private JComboBox cmbTheme = null;
	private JCheckBox chkMaximize = null;
	private PanelBuilder generalPanel = null;
	private JCheckBox chkNonCtlgNo = null;
	private JCheckBox chkBlockedCard = null;
	private JCheckBox chkAutoReturn = null;
	private PanelBuilder reversPanel = null;
	private JTextField tfLibraryName = null;
	private JTextField tfLibraryAddress = null;
  private JCheckBox chkReversSelected = null;
	private JTextField tfReversHeight = null;
	private JTextField tfReversWidth = null;
	private JTextField tfReversRowCount = null;
	private JTextField tfReversCount = null;
	private JCheckBox chkDefaultZipCity = null;
	private JTextField tfDefaultCity = null;
	private JTextField tfDefaultZip = null;
	private JTextField tfLocation = null;
	private JPanel bottomPanel = null;
  private PanelBuilder topPanel = null;
	private JButton btnSave = null;
	private JButton btnCancel = null;
  private JTextField tfAddress = null;
  private JTextField tfNote = null;
	private LookAndFeelInfo[] arrayLAF = null;
  private int row = 0;
  private OptionsMainFrame parent;

	public OptionsFrame(OptionsMainFrame parent) {
		super("Opcije", true, true, true, true);
    this.parent = parent;
		initialize();
	}
  
  public void loadData(int row){
    this.row = row;
    if (row != -1){
      EnvironmentOptions.loadOptions(this, row);
    }else{
      EnvironmentOptions.loadDefaults(this);
    }
    ValidatorOptions.loadOptions(this);
    this.setVisible(true);
  }

	private void initialize() {
		this.setPreferredSize(new Dimension(700, 600));
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    Dimension screen = getToolkit().getScreenSize();
    this.setLocation((screen.width - getPreferredSize().width) / 2,
          (screen.height - getPreferredSize().height) / 2);
    this.pack();
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
      jContentPane.add(getTopPanel(), BorderLayout.NORTH);
			jContentPane.add(getTpOpt(), BorderLayout.CENTER);
			jContentPane.add(getBottomPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	private JTabbedPane getTpOpt() {
		if (tpOpt == null) {
			tpOpt = new JTabbedPane();
			tpOpt.addTab("Generalno", null, getGeneralPanel(), null);
			tpOpt.addTab("Inventarni broj", null, getCtlgNoPanel(), null);
			tpOpt.addTab("Broj korisnika", null, getUserIDPanel(), null);
			tpOpt.addTab("Obavezna polja", null, getMandatoryPanel(), null);
			tpOpt.addTab("Revers", null, getReversPanel(), null);
		}
		return tpOpt;
	}

	private JPanel getMandatoryPanel() {
		if (mandatoryPanel == null) {
			FormLayout layout = new FormLayout(
			        "2dlu:grow, left:70dlu, 2dlu, 30dlu, left:70dlu, 2dlu, 30dlu, left:70dlu, 2dlu, 2dlu:grow", 
			        "5dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu:grow");
			CellConstraints cc = new CellConstraints();
			mandatoryPanel = new PanelBuilder(layout);
			mandatoryPanel.setDefaultDialogBorder();
			
			mandatoryPanel.add(getChkFirstName(), cc.xy(2,2));
			mandatoryPanel.add(getChkLastName(), cc.xy(2,4));
			mandatoryPanel.add(getChkParentName(), cc.xy(2,6));
			
			mandatoryPanel.addSeparator("", cc.xyw(2,8,2));
			mandatoryPanel.add(getChkAddress(), cc.xy(2,10));
			mandatoryPanel.add(getChkZip(), cc.xy(2,12));
			mandatoryPanel.add(getChkCity(), cc.xy(2,14));
			mandatoryPanel.add(getChkPhone(), cc.xy(2,16));
			mandatoryPanel.add(getChkEmail(), cc.xy(2,18));
			
      mandatoryPanel.addSeparator("", cc.xyw(5,10,2));
			mandatoryPanel.add(getChkUserID(), cc.xy(5,12));
      mandatoryPanel.add(getChkMmbrType(), cc.xy(5,14));
      mandatoryPanel.add(getChkUserCateg(), cc.xy(5,16));
			mandatoryPanel.add(getChkSignDate(), cc.xy(5,18));
			mandatoryPanel.add(getChkCost(), cc.xy(5,20));
			mandatoryPanel.add(getChkUntilDate(), cc.xy(5,22));
			mandatoryPanel.add(getChkReceiptID(), cc.xy(5,24));
      
      mandatoryPanel.add(getChkJmbg(), cc.xy(5,2));
      mandatoryPanel.add(getChkDocNo(), cc.xy(5,4));
      mandatoryPanel.add(getChkDocCity(), cc.xy(5,6));
      mandatoryPanel.add(getChkCountry(), cc.xy(5,8));
						
			mandatoryPanel.add(getChkTmpAddress(), cc.xy(8,2));
			mandatoryPanel.add(getChkTmpZip(), cc.xy(8,4));
			mandatoryPanel.add(getChkTmpCity(), cc.xy(8,6));
			mandatoryPanel.add(getChkTmpPhone(), cc.xy(8,8));
			mandatoryPanel.add(getChkOccupation(), cc.xy(8,10));
			mandatoryPanel.add(getChkTitle(), cc.xy(8,12));
      mandatoryPanel.add(getChkOrganization(), cc.xy(8,14));
      mandatoryPanel.add(getChkEduLvl(), cc.xy(8,16));
      mandatoryPanel.add(getChkClass(), cc.xy(8,18));
			mandatoryPanel.add(getChkIndexNo(), cc.xy(8,20));
			mandatoryPanel.add(getChkNote(), cc.xy(8,22));
			mandatoryPanel.add(getChkInterests(), cc.xy(8,24));
      mandatoryPanel.add(getChkLanguage(), cc.xy(8,26));
      
		}
		return mandatoryPanel.getPanel();
	}

	private JCheckBox getChkFirstName() {
		if (chkFirstName == null) {
			chkFirstName = new JCheckBox();
			chkFirstName.setText("Ime");
		}
		return chkFirstName;
	}
	
	public void setChkdFirstName(boolean value){
		getChkFirstName().setSelected(value);
	}
	
	public boolean isChkdFirstName(){
		return getChkFirstName().isSelected();
	}

	private JCheckBox getChkLastName() {
		if (chkLastName == null) {
			chkLastName = new JCheckBox();
			chkLastName.setText("Prezime");
		}
		return chkLastName;
	}
	
	public void setChkdLastName(boolean value){
		getChkLastName().setSelected(value);
	}
	
	public boolean isChkdLastName(){
		return getChkLastName().isSelected();
	}

	private JCheckBox getChkParentName() {
		if (chkParentName == null) {
			chkParentName = new JCheckBox();
			chkParentName.setText("Ime roditelja");
		}
		return chkParentName;
	}
	
	public void setChkdParentName(boolean value){
		getChkParentName().setSelected(value);
	}
	
	public boolean isChkdParentName(){
		return getChkParentName().isSelected();
	}

	private JCheckBox getChkAddress() {
		if (chkAddress == null) {
			chkAddress = new JCheckBox();
			chkAddress.setText("Adresa");
		}
		return chkAddress;
	}
	
	public void setChkdAddress(boolean value){
		getChkAddress().setSelected(value);
	}
	
	public boolean isChkdAddress(){
		return getChkAddress().isSelected();
	}

	private JCheckBox getChkZip() {
		if (chkZip == null) {
			chkZip = new JCheckBox();
			chkZip.setText("Broj po\u0161te");
		}
		return chkZip;
	}
	
	public void setChkdZip(boolean value){
		getChkZip().setSelected(value);
	}
	
	public boolean isChkdZip(){
		return getChkZip().isSelected();
	}

	private JCheckBox getChkCity() {
		if (chkCity == null) {
			chkCity = new JCheckBox();
			chkCity.setText("Grad");
		}
		return chkCity;
	}
	
	public void setChkdCity(boolean value){
		getChkCity().setSelected(value);
	}
	
	public boolean isChkdCity(){
		return getChkCity().isSelected();
	}

	private JCheckBox getChkPhone() {
		if (chkPhone == null) {
			chkPhone = new JCheckBox();
			chkPhone.setText("Telefon");
		}
		return chkPhone;
	}
	
	public void setChkdPhone(boolean value){
		getChkPhone().setSelected(value);
	}
	
	public boolean isChkdPhone(){
		return getChkPhone().isSelected();
	}

	private JCheckBox getChkEmail() {
		if (chkEmail == null) {
			chkEmail = new JCheckBox();
			chkEmail.setText("E-mail");
		}
		return chkEmail;
	}
	
	public void setChkdEmail(boolean value){
		getChkEmail().setSelected(value);
	}
	
	public boolean isChkdEmail(){
		return getChkEmail().isSelected();
	}

	private JCheckBox getChkJmbg() {
		if (chkJmbg == null) {
			chkJmbg = new JCheckBox();
			chkJmbg.setText("JMBG");
		}
		return chkJmbg;
	}
	
	public void setChkdJmbg(boolean value){
		getChkJmbg().setSelected(value);
	}
	
	public boolean isChkdJmbg(){
		return getChkJmbg().isSelected();
	}

	private JCheckBox getChkDocNo() {
		if (chkDocNo == null) {
			chkDocNo = new JCheckBox();
			chkDocNo.setText("Broj dokumenta");
		}
		return chkDocNo;
	}
	
	public void setChkdDocNo(boolean value){
		getChkDocNo().setSelected(value);
	}
	
	public boolean isChkdDocNo(){
		return getChkDocNo().isSelected();
	}

	private JCheckBox getChkDocCity() {
		if (chkDocCity == null) {
			chkDocCity = new JCheckBox();
			chkDocCity.setText("Mesto izdavanja");
		}
		return chkDocCity;
	}
	
	public void setChkdDocCity(boolean value){
		getChkDocCity().setSelected(value);
	}
	
	public boolean isChkdDocCity(){
		return getChkDocCity().isSelected();
	}

	private JCheckBox getChkCountry() {
		if (chkCountry == null) {
			chkCountry = new JCheckBox();
			chkCountry.setText("Zemlja izdavanja");
		}
		return chkCountry;
	}
	
	public void setChkdCountry(boolean value){
		getChkCountry().setSelected(value);
	}
	
	public boolean isChkdCountry(){
		return getChkCountry().isSelected();
	}

	private JCheckBox getChkTmpAddress() {
		if (chkTmpAddress == null) {
			chkTmpAddress = new JCheckBox();
			chkTmpAddress.setText("Dodatna adresa");
		}
		return chkTmpAddress;
	}
	
	public void setChkdTmpAddress(boolean value){
		getChkTmpAddress().setSelected(value);
	}
	
	public boolean isChkdTmpAddress(){
		return getChkTmpAddress().isSelected();
	}

	private JCheckBox getChkTmpZip() {
		if (chkTmpZip == null) {
			chkTmpZip = new JCheckBox();
			chkTmpZip.setText("Broj po\u0161te");
		}
		return chkTmpZip;
	}
	
	public void setChkdTmpZip(boolean value){
		getChkTmpZip().setSelected(value);
	}
	
	public boolean isChkdTmpZip(){
		return getChkTmpZip().isSelected();
	}

	private JCheckBox getChkTmpCity() {
		if (chkTmpCity == null) {
			chkTmpCity = new JCheckBox();
			chkTmpCity.setText("Grad");
		}
		return chkTmpCity;
	}
	
	public void setChkdTmpCity(boolean value){
		getChkTmpCity().setSelected(value);
	}
	
	public boolean isChkdTmpCity(){
		return getChkTmpCity().isSelected();
	}

	private JCheckBox getChkTmpPhone() {
		if (chkTmpPhone == null) {
			chkTmpPhone = new JCheckBox();
			chkTmpPhone.setText("Telefon");
		}
		return chkTmpPhone;
	}
	
	public void setChkdTmpPhone(boolean value){
		getChkTmpPhone().setSelected(value);
	}
	
	public boolean isChkdTmpPhone(){
		return getChkTmpPhone().isSelected();
	}

	private JCheckBox getChkOccupation() {
		if (chkOccupation == null) {
			chkOccupation = new JCheckBox();
			chkOccupation.setText("Zanimanje");
		}
		return chkOccupation;
	}
	
	public void setChkdOccupation(boolean value){
		getChkOccupation().setSelected(value);
	}
	
	public boolean isChkdOccupation(){
		return getChkOccupation().isSelected();
	}

	private JCheckBox getChkTitle() {
		if (chkTitle == null) {
			chkTitle = new JCheckBox();
			chkTitle.setText("Zvanje");
		}
		return chkTitle;
	}
	
	public void setChkdTitle(boolean value){
		getChkTitle().setSelected(value);
	}
	
	public boolean isChkdTitle(){
		return getChkTitle().isSelected();
	}

	private JCheckBox getChkIndexNo() {
		if (chkIndexNo == null) {
			chkIndexNo = new JCheckBox();
			chkIndexNo.setText("Broj indeksa");
		}
		return chkIndexNo;
	}
	
	public void setChkdIndexNo(boolean value){
		getChkIndexNo().setSelected(value);
	}
	
	public boolean isChkdIndexNo(){
		return getChkIndexNo().isSelected();
	}

	private JCheckBox getChkNote() {
		if (chkNote == null) {
			chkNote = new JCheckBox();
			chkNote.setText("Napomena");
		}
		return chkNote;
	}
	
	public void setChkdNote(boolean value){
		getChkNote().setSelected(value);
	}
	
	public boolean isChkdNote(){
		return getChkNote().isSelected();
	}

	private JCheckBox getChkInterests() {
		if (chkInterests == null) {
			chkInterests = new JCheckBox();
			chkInterests.setText("Interesovanja");
		}
		return chkInterests;
	}
	
	public void setChkdInterests(boolean value){
		getChkInterests().setSelected(value);
	}
	
	public boolean isChkdInterests(){
		return getChkInterests().isSelected();
	}

	private JCheckBox getChkUserID() {
		if (chkUserID == null) {
			chkUserID = new JCheckBox();
			chkUserID.setText("Broj korisnika");
		}
		return chkUserID;
	}
	
	public void setChkdUserID(boolean value){
		getChkUserID().setSelected(value);
	}
	
	public boolean isChkdUserID(){
		return getChkUserID().isSelected();
	}

	private JCheckBox getChkSignDate() {
		if (chkSignDate == null) {
			chkSignDate = new JCheckBox();
			chkSignDate.setText("Datum upisa");
		}
		return chkSignDate;
	}
	
	public void setChkdSignDate(boolean value){
		getChkSignDate().setSelected(value);
	}
	
	public boolean isChkdSignDate(){
		return getChkSignDate().isSelected();
	}

	private JCheckBox getChkCost() {
		if (chkCost == null) {
			chkCost = new JCheckBox();
			chkCost.setText("\u010clanarina");
		}
		return chkCost;
	}
	
	public void setChkdCost(boolean value){
		getChkCost().setSelected(value);
	}
	
	public boolean isChkdCost(){
		return getChkCost().isSelected();
	}

	private JCheckBox getChkUntilDate() {
		if (chkUntilDate == null) {
			chkUntilDate = new JCheckBox();
			chkUntilDate.setText("Va\u017ei do");
		}
		return chkUntilDate;
	}
	
	public void setChkdUntilDate(boolean value){
		getChkUntilDate().setSelected(value);
	}
	
	public boolean isChkdUntilDate(){
		return getChkUntilDate().isSelected();
	}

	private JCheckBox getChkReceiptID() {
		if (chkReceiptID == null) {
			chkReceiptID = new JCheckBox();
			chkReceiptID.setText("Br. priznanice");
		}
		return chkReceiptID;
	}
	
	public void setChkdReceiptID(boolean value){
		getChkReceiptID().setSelected(value);
	}
	
	public boolean isChkdReceiptID(){
		return getChkReceiptID().isSelected();
	}
  
  private JCheckBox getChkOrganization() {
    if (chkOrganization == null) {
      chkOrganization = new JCheckBox();
      chkOrganization.setText("Organizacija");
    }
    return chkOrganization;
  }
  
  public void setChkdOrganization(boolean value){
    getChkOrganization().setSelected(value);
  }
  
  public boolean isChkdOrganization(){
    return getChkOrganization().isSelected();
  }
  
  private JCheckBox getChkEduLvl() {
    if (chkEduLvl == null) {
      chkEduLvl = new JCheckBox();
      chkEduLvl.setText("Obrazivanje");
    }
    return chkEduLvl;
  }
  
  public void setChkdEduLvl(boolean value){
    getChkEduLvl().setSelected(value);
  }
  
  public boolean isChkdEduLvl(){
    return getChkEduLvl().isSelected();
  }
  
  private JCheckBox getChkClass() {
    if (chkClass == null) {
      chkClass = new JCheckBox();
      chkClass.setText("Godina");
    }
    return chkClass;
  }
  
  public void setChkdClass(boolean value){
    getChkClass().setSelected(value);
  }
  
  public boolean isChkdClass(){
    return getChkClass().isSelected();
  }
  
  private JCheckBox getChkLanguage() {
    if (chkLanguage == null) {
      chkLanguage = new JCheckBox();
      chkLanguage.setText("Maternji jezik");
    }
    return chkLanguage;
  }
  
  public void setChkdLanguage(boolean value){
    getChkLanguage().setSelected(value);
  }
  
  public boolean isChkdLanguage(){
    return getChkLanguage().isSelected();
  }
  
  private JCheckBox getChkMmbrType() {
    if (chkMmbrType == null) {
      chkMmbrType = new JCheckBox();
      chkMmbrType.setText("Vrsta uclanjenja");
    }
    return chkMmbrType;
  }
  
  public void setChkdMmbrType(boolean value){
    getChkMmbrType().setSelected(value);
  }
  
  public boolean isChkdMmbrType(){
    return getChkMmbrType().isSelected();
  }
  
  private JCheckBox getChkUserCateg() {
    if (chkUserCateg == null) {
      chkUserCateg = new JCheckBox();
      chkUserCateg.setText("Kategorija korisnika");
    }
    return chkUserCateg;
  }
  
  public void setChkdUserCateg(boolean value){
    getChkUserCateg().setSelected(value);
  }
  
  public boolean isChkdUserCateg(){
    return getChkUserCateg().isSelected();
  }

	private JPanel getUserIDPanel() {
		if (userIDPanel == null) {
			FormLayout layout = new FormLayout(
			        "2dlu:grow, right:60dlu, 3dlu, 60dlu, 70dlu, 2dlu:grow", 
			        "10dlu, pref, 20dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 30dlu, pref, 2dlu, pref, 2dlu, pref, 30dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu:grow");
			CellConstraints cc = new CellConstraints();
			userIDPanel = new PanelBuilder(layout);
			userIDPanel.setDefaultDialogBorder();
			
			userIDPanel.addLabel("Du\u017eina broja", cc.xy(2,2));
			userIDPanel.add(getTfUserIDLength(), cc.xy(4,2));
			
			userIDPanel.addSeparator("", cc.xyw(2,4,4));
			userIDPanel.add(getChkPrefix(), cc.xyw(2,6,4));
			userIDPanel.addLabel("Du\u017eina prefiksa", cc.xy(2,8));
			userIDPanel.add(getTfPrefixLength(), cc.xy(4,8));
			userIDPanel.addLabel("Default vrednost", cc.xy(2,10));
			userIDPanel.add(getTfDefaultPrefix(), cc.xy(4,10));
			
			userIDPanel.addSeparator("", cc.xyw(2,12,4));
			userIDPanel.add(getChkSeparator(), cc.xyw(2,14,4));
			userIDPanel.addLabel("Separator", cc.xy(2,16));
			userIDPanel.add(getTfSeparator(), cc.xy(4,16));
			
			userIDPanel.addSeparator("Na\u010din unosa", cc.xyw(2,18,4));
			userIDPanel.add(getRbNumSepPre(), cc.xyw(2,20,4));
			userIDPanel.add(getRbPreSepNum(), cc.xyw(2,22,4));
		}
		return userIDPanel.getPanel();
	}

	private JTextField getTfUserIDLength() {
		if (tfUserIDLength == null) {
			tfUserIDLength = new JTextField();
		}
		return tfUserIDLength;
	}
	
	public String getUserIDLength(){
		return getTfUserIDLength().getText();
	}
	
	public void setUserIDLength(String text){
		getTfUserIDLength().setText(text);
	}

	private JCheckBox getChkPrefix() {
		if (chkPrefix == null) {
			chkPrefix = new JCheckBox();
			chkPrefix.setText("Prefiks oznaka odeljenja");
			chkPrefix.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.DESELECTED){
						getTfPrefixLength().setText("");
						getTfPrefixLength().setEnabled(false);
						getTfDefaultPrefix().setText("");
						getTfDefaultPrefix().setEnabled(false);
					} else {
						getTfPrefixLength().setEnabled(true);
						getTfDefaultPrefix().setEnabled(true);
					}
				}
			});
		}
		return chkPrefix;
	}
	
	public void setChkdPrefix(boolean value){
		getChkPrefix().setSelected(value);
	}
	
	public boolean isChkdPrefix(){
		return getChkPrefix().isSelected();
	}

	private JTextField getTfPrefixLength() {
		if (tfPrefixLength == null) {
			tfPrefixLength = new JTextField();
		}
		return tfPrefixLength;
	}
	
	public String getPrefixLength(){
		return getTfPrefixLength().getText();
	}
	
	public void setPrefixLength(String text){
		getTfPrefixLength().setText(text);
	}
	
	public boolean getPrefixLengthEnabled(){
		return getTfPrefixLength().isEnabled();
	}

	private JTextField getTfDefaultPrefix() {
		if (tfDefaultPrefix == null) {
			tfDefaultPrefix = new JTextField();
		}
		return tfDefaultPrefix;
	}
	
	public String getDefaultPrefix(){
		return getTfDefaultPrefix().getText();
	}
	
	public void setDefaultPrefix(String text){
		getTfDefaultPrefix().setText(text);
	}
	
	public boolean getDefaultPrefixEnabled(){
		return getTfDefaultPrefix().isEnabled();
	}

	private JCheckBox getChkSeparator() {
		if (chkSeparator == null) {
			chkSeparator = new JCheckBox();
			chkSeparator.setText("Separator");
			chkSeparator.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.DESELECTED){
						getTfSeparator().setText("");
						getTfSeparator().setEnabled(false);
					} else {
						getTfSeparator().setEnabled(true);
					}
				}
			});
		}
		return chkSeparator;
	}
	
	public void setChkdSeparator(boolean value){
		getChkSeparator().setSelected(value);
	}
	
	public boolean isChkdSeparator(){
		return getChkSeparator().isSelected();
	}

	private JTextField getTfSeparator() {
		if (tfSeparator == null) {
			tfSeparator = new JTextField();
		}
		return tfSeparator;
	}
	
	public String getSeparator(){
		return getTfSeparator().getText();
	}
	
	public void setSeparator(String text){
		getTfSeparator().setText(text);
	}
	
	public boolean getSeparatorEnabled(){
		return getTfSeparator().isEnabled();
	}

	private JRadioButton getRbNumSepPre() {
		if (rbNumSepPre == null) {
			rbNumSepPre = new JRadioButton();
			rbNumSepPre.setText("Broj-Separator-Prefiks");
			getUseridGroup().add(rbNumSepPre);
		}
		return rbNumSepPre;
	}

	private JRadioButton getRbPreSepNum() {
		if (rbPreSepNum == null) {
			rbPreSepNum = new JRadioButton();
			rbPreSepNum.setText("Prefiks-Separator-Broj");
			getUseridGroup().add(rbPreSepNum);
		}
		return rbPreSepNum;
	}
	
	private ButtonGroup getUseridGroup() {
		if (useridGroup == null) {
			useridGroup = new ButtonGroup();
		}
		return useridGroup;
	}
	
	public String getUserIDInput(){
		if (getRbNumSepPre().isSelected()){
			return "1";
		}else {
			return "2";
		}
	}
	
	public void setUserIDInput(String input){
		if (input.equals("1")){
			getRbNumSepPre().setSelected(true);
		}else {
			getRbPreSepNum().setSelected(true);
		}
	}

	private JPanel getCtlgNoPanel() {
		if (ctlgNoPanel == null) {
			FormLayout layout = new FormLayout(
			        "2dlu:grow, right:60dlu, 3dlu, 60dlu, 70dlu, 2dlu:grow", 
			        "5dlu, pref, 2dlu, pref, 10dlu, pref, 10dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 10dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 10dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 10dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu:grow");
			CellConstraints cc = new CellConstraints();
			ctlgNoPanel = new PanelBuilder(layout);
			ctlgNoPanel.setDefaultDialogBorder();
			
			ctlgNoPanel.addLabel("Model inventarnog broja:", cc.xyw(2,2,4));
			ctlgNoPanel.addLabel("oznaka odeljenja+oznaka inventarne knjige+broj", cc.xyw(2,4,4));
			ctlgNoPanel.addLabel("Du\u017eina broja", cc.xy(2,6));
			ctlgNoPanel.add(getTfCtlgNoLength(), cc.xy(4,6));
			
			ctlgNoPanel.addSeparator("", cc.xyw(2,8,4));
			ctlgNoPanel.add(getChkLocation(), cc.xyw(2,10,4));
			ctlgNoPanel.addLabel("Du\u017eina oznake", cc.xy(2,12));
			ctlgNoPanel.add(getTfLocationLength(), cc.xy(4,12));
			ctlgNoPanel.addLabel("Default vrednost", cc.xy(2,14));
			ctlgNoPanel.add(getTfDefaultLocation(), cc.xy(4,14));
			
			ctlgNoPanel.addSeparator("", cc.xyw(2,16,4));
			ctlgNoPanel.add(getChkBook(), cc.xyw(2,18,4));
			ctlgNoPanel.addLabel("Du\u017eina oznake", cc.xy(2,20));
			ctlgNoPanel.add(getTfBookLength(), cc.xy(4,20));
			ctlgNoPanel.addLabel("Default vrednost", cc.xy(2,22));
			ctlgNoPanel.add(getTfDefaultBook(), cc.xy(4,22));
			
			ctlgNoPanel.addSeparator("", cc.xyw(2,24,4));
			ctlgNoPanel.add(getChkSeparators(), cc.xyw(2,26,4));
			ctlgNoPanel.addLabel("Separator1", cc.xy(2,28));
			ctlgNoPanel.add(getTfSeparator1(), cc.xy(4,28));
			ctlgNoPanel.addLabel("Separator2", cc.xy(2,30));
			ctlgNoPanel.add(getTfSeparator2(), cc.xy(4,30));
			
			ctlgNoPanel.addSeparator("Na\u010din unosa", cc.xyw(2,32,4));
			ctlgNoPanel.add(getRbNumS1LocS2Book(), cc.xyw(2,34,4));
			ctlgNoPanel.add(getRbLocS1BookS2Num(), cc.xyw(2,36,4));
		}
		return ctlgNoPanel.getPanel();
	}

	private JTextField getTfCtlgNoLength() {
		if (tfCtlgNoLength == null) {
			tfCtlgNoLength = new JTextField();
		}
		return tfCtlgNoLength;
	}
	
	public String getCtlgNoLength(){
		return getTfCtlgNoLength().getText();
	}
	
	public void setCtlgNoLength(String text){
		getTfCtlgNoLength().setText(text);
	}

	private JCheckBox getChkLocation() {
		if (chkLocation == null) {
			chkLocation = new JCheckBox();
			chkLocation.setText("Oznaka odeljenja");
			chkLocation.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.DESELECTED){
						getTfLocationLength().setEnabled(false);
						getTfLocationLength().setText("");
						getTfDefaultLocation().setEnabled(false);
						getTfDefaultLocation().setText("");
						if (getTfSeparator2().isEnabled()){
							getTfSeparator2().setEnabled(false);
							getTfSeparator2().setText("");
						} else {
							getTfSeparator1().setEnabled(false);
							getTfSeparator1().setText("");
						}
					} else {
						getTfLocationLength().setEnabled(true);
						getTfDefaultLocation().setEnabled(true);
						if (getTfSeparator1().isEnabled()){
							getTfSeparator2().setEnabled(true);
						} else {
							getTfSeparator1().setEnabled(true);
						}
					}
				}
			});
		}
		return chkLocation;
	}
	
	public void setChkdLocation(boolean value){
		getChkLocation().setSelected(value);
	}
	
	public boolean isChkdLocation(){
		return getChkLocation().isSelected();
	}

	private JTextField getTfLocationLength() {
		if (tfLocationLength == null) {
			tfLocationLength = new JTextField();
		}
		return tfLocationLength;
	}
	
	public String getLocationLength(){
		return getTfLocationLength().getText();
	}
	
	public void setLocationLength(String text){
		getTfLocationLength().setText(text);
	}
	
	public boolean getLocationLengthEnabled(){
		return getTfLocationLength().isEnabled();
	}

	private JTextField getTfDefaultLocation() {
		if (tfDefaultLocation == null) {
			tfDefaultLocation = new JTextField();
		}
		return tfDefaultLocation;
	}
	
	public String getDefaultLocation(){
		return getTfDefaultLocation().getText();
	}
	
	public void setDefaultLocation(String text){
		getTfDefaultLocation().setText(text);
	}
	
	public boolean getDefaultLocationEnabled(){
		return getTfDefaultLocation().isEnabled();
	}

	private JCheckBox getChkBook() {
		if (chkBook == null) {
			chkBook = new JCheckBox();
			chkBook.setText("Oznaka inventarne knjige");
			chkBook.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.DESELECTED){
						getTfBookLength().setEnabled(false);
						getTfBookLength().setText("");
						getTfDefaultBook().setEnabled(false);
						getTfDefaultBook().setText("");
						if (getTfSeparator2().isEnabled()){
							getTfSeparator2().setEnabled(false);
							getTfSeparator2().setText("");
						} else {
							getTfSeparator1().setEnabled(false);
							getTfSeparator1().setText("");
						}
					} else {
						getTfBookLength().setEnabled(true);
						getTfDefaultBook().setEnabled(true);
						if (getTfSeparator1().isEnabled()){
							getTfSeparator2().setEnabled(true);
						} else {
							getTfSeparator1().setEnabled(true);
						}
					}
				}
			});
		}
		return chkBook;
	}
	
	public void setChkdBook(boolean value){
		getChkBook().setSelected(value);
	}
	
	public boolean isChkdBook(){
		return getChkBook().isSelected();
	}

	private JTextField getTfBookLength() {
		if (tfBookLength == null) {
			tfBookLength = new JTextField();
		}
		return tfBookLength;
	}
	
	public String getBookLength(){
		return getTfBookLength().getText();
	}
	
	public void setBookLength(String text){
		getTfBookLength().setText(text);
	}
	
	public boolean getBookLengthEnabled(){
		return getTfBookLength().isEnabled();
	}

	private JTextField getTfDefaultBook() {
		if (tfDefaultBook == null) {
			tfDefaultBook = new JTextField();
		}
		return tfDefaultBook;
	}
	
	public String getDefaultBook(){
		return getTfDefaultBook().getText();
	}
	
	public void setDefaultBook(String text){
		getTfDefaultBook().setText(text);
	}
	
	public boolean getDefaultBookEnabled(){
		return getTfDefaultBook().isEnabled();
	}

	private JTextField getTfSeparator1() {
		if (tfSeparator1 == null) {
			tfSeparator1 = new JTextField();
		}
		return tfSeparator1;
	}
	
	public String getSeparator1(){
		return getTfSeparator1().getText();
	}
	
	public void setSeparator1(String text){
		getTfSeparator1().setText(text);
	}
	
	public boolean getSeparator1Enabled(){
		return getTfSeparator1().isEnabled();
	}

	private JTextField getTfSeparator2() {
		if (tfSeparator2 == null) {
			tfSeparator2 = new JTextField();
		}
		return tfSeparator2;
	}
	
	public String getSeparator2(){
		return getTfSeparator2().getText();
	}
	
	public void setSeparator2(String text){
		getTfSeparator2().setText(text);
	}
	
	public boolean getSeparator2Enabled(){
		return getTfSeparator2().isEnabled();
	}

	private JRadioButton getRbNumS1LocS2Book() {
		if (rbNumS1LocS2Book == null) {
			rbNumS1LocS2Book = new JRadioButton();
			rbNumS1LocS2Book.setText("Broj-S1-Odeljenje-S2-Inv.knjiga");
			getCtlgnoGroup().add(rbNumS1LocS2Book);
		}
		return rbNumS1LocS2Book;
	}

	private JRadioButton getRbLocS1BookS2Num() {
		if (rbLocS1BookS2Num == null) {
			rbLocS1BookS2Num = new JRadioButton();
			rbLocS1BookS2Num.setText("Odeljenje-S1-Inv.knjiga-S2-Broj");
			getCtlgnoGroup().add(rbLocS1BookS2Num);
		}
		return rbLocS1BookS2Num;
	}
	
	private ButtonGroup getCtlgnoGroup() {
		if (ctlgnoGroup == null) {
			ctlgnoGroup = new ButtonGroup();
		}
		return ctlgnoGroup;
	}
	
	public String getCtlgnoInput(){
		if (getRbNumS1LocS2Book().isSelected()){
			return "1";
		}else {
			return "2";
		}
	}
	
	public void setCtlgnoInput(String input){
		if (input.equals("1")){
			getRbNumS1LocS2Book().setSelected(true);
		}else {
			getRbLocS1BookS2Num().setSelected(true);
		}
	}

	private JCheckBox getChkSeparators() {
		if (chkSeparators == null) {
			chkSeparators = new JCheckBox();
			chkSeparators.setText("Separatori");
			chkSeparators.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.DESELECTED){
						getTfSeparator1().setEnabled(false);
						getTfSeparator1().setText("");
						getTfSeparator2().setEnabled(false);
						getTfSeparator2().setText("");
					} else {
						if (getChkLocation().isSelected() && getChkBook().isSelected()){
							getTfSeparator1().setEnabled(true);
							getTfSeparator2().setEnabled(true);
						} else if (getChkLocation().isSelected() || getChkBook().isSelected()){
							getTfSeparator1().setEnabled(true);
						}
						
					}
				}
			});
		}
		return chkSeparators;
	}
	
	public void setChkdSeparators(boolean value){
		getChkSeparators().setSelected(value);
	}
	
	public boolean isChkdSeparators(){
		return getChkSeparators().isSelected();
	}

	private JComboBox getCmbSize() {
		if (cmbSize == null) {
			cmbSize = new JComboBox();
			cmbSize.addItem("10");
			cmbSize.addItem("11");
			cmbSize.addItem("12");
			cmbSize.addItem("13");
			cmbSize.addItem("14");
			cmbSize.addItem("15");
			cmbSize.addItem("16");
			cmbSize.addItem("17");
			cmbSize.addItem("18");
			cmbSize.addItem("19");
			cmbSize.addItem("20");
		}
		return cmbSize;
	}
	
	public String getCmbSizeValue(){
		return (String) getCmbSize().getSelectedItem();
	}
	
	public void setCmbSizeValue(String value){
		getCmbSize().setSelectedItem(value);
	}
	
	private JComboBox getCmbLookAndFeel() {
		if (cmbLookAndFeel == null) {
			cmbLookAndFeel = new JComboBox();
			cmbLookAndFeel.addItem("default");
			arrayLAF = UIManager.getInstalledLookAndFeels();
			for (int i = 0; i < arrayLAF.length; i++){
				cmbLookAndFeel.addItem(arrayLAF[i].getName());
			}
		}
		return cmbLookAndFeel;
	}
	
	public String getCmbLookAndFeelValue(){
		if(((String)getCmbLookAndFeel().getSelectedItem()).equals("default")){
			return "default";
		} else {
			int i = 0;
			boolean found = false;
			while (!found){
				if (arrayLAF[i].getName().equals((String)getCmbLookAndFeel().getSelectedItem())){
					found = true;
				}
				i++;
			}
			return arrayLAF[i-1].getClassName();
		}
	}
	
	public void setCmbLookAndFeelValue(String value){
		int i = 0;
		boolean found = false;
		while (i < arrayLAF.length && !found){
			if (arrayLAF[i].getClassName().equals(value)){
				found = true;
			}
			i++;
		}
		if (found){
			getCmbLookAndFeel().setSelectedItem(arrayLAF[i-1].getName());
		}else {
			getCmbLookAndFeel().setSelectedItem("default");
		}
	}
	
	private JComboBox getCmbTheme() {
		if (cmbTheme == null) {
			cmbTheme = new JComboBox();
			cmbTheme.addItem("BrownSugar");
			cmbTheme.addItem("DarkStar");
			cmbTheme.addItem("DesertBlue");
			cmbTheme.addItem("DesertBluer");
			cmbTheme.addItem("DesertGreen");
			cmbTheme.addItem("DesertRed");
			cmbTheme.addItem("DesertYellow");
			cmbTheme.addItem("ExperienceBlue");
			cmbTheme.addItem("ExperienceGreen");
			cmbTheme.addItem("ExperienceRoyale");
			cmbTheme.addItem("LightGray");
			cmbTheme.addItem("Silver");
			cmbTheme.addItem("SkyBlue");
			cmbTheme.addItem("SkyBluer");
			cmbTheme.addItem("SkyGreen");
			cmbTheme.addItem("SkyKrupp");
			cmbTheme.addItem("SkyPink");
			cmbTheme.addItem("SkyRed");
			cmbTheme.addItem("SkyYellow");
			
		}
		return cmbTheme;
	}
	
	public String getCmbThemeValue(){
		return "com.jgoodies.looks.plastic.theme."+(String)getCmbTheme().getSelectedItem();
	}
	
	public void setCmbThemeValue(String value){
		int ind = value.lastIndexOf(".");
		if (ind != -1){
			getCmbTheme().setSelectedItem(value.substring(ind+1));
		}
	}

	private JCheckBox getChkMaximize() {
		if (chkMaximize == null) {
			chkMaximize = new JCheckBox();
			chkMaximize.setText("Maksimizirano");
		}
		return chkMaximize;
	}
	
	public void setChkdMaximize(boolean value){
		getChkMaximize().setSelected(value);
	}
	
	public boolean isChkdMaximize(){
		return getChkMaximize().isSelected();
	}

	private JPanel getGeneralPanel() {
		if (generalPanel == null) {
			FormLayout layout = new FormLayout(
			        "2dlu:grow, right:60dlu, 3dlu, 50dlu, 30dlu, 50dlu, 2dlu:grow", 
			        "10dlu, pref, 2dlu, pref, 2dlu, pref, 23dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 23dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 23dlu, pref, 2dlu, pref, 2dlu:grow");
			CellConstraints cc = new CellConstraints();
			
			generalPanel = new PanelBuilder(layout);
			generalPanel.setDefaultDialogBorder();
			
			generalPanel.add(getChkNonCtlgNo(), cc.xyw(2,2,5));
			generalPanel.add(getChkBlockedCard(), cc.xyw(2,4,5));
			generalPanel.add(getChkAutoReturn(), cc.xyw(2,6,5));
			
			generalPanel.addSeparator("", cc.xyw(2,8,5));
			generalPanel.add(getChkDefaultZipCity(), cc.xyw(2,10,5));
			generalPanel.addLabel("Grad", cc.xy(2,12));
			generalPanel.add(getTfDefaultCity(), cc.xyw(4,12,2));
			generalPanel.addLabel("Broj po\u0161te", cc.xy(2,14));
			generalPanel.add(getTfDefaultZip(), cc.xyw(4,14,2));
			
			generalPanel.addSeparator("", cc.xyw(2,16,5));
			generalPanel.addLabel("Veli\u010dina slova", cc.xy(2,20));
			generalPanel.add(getCmbSize(), cc.xyw(4,20,2));
			generalPanel.add(getChkMaximize(), cc.xyw(2,18,5));
			generalPanel.addLabel("Izgled", cc.xy(2,22));
			generalPanel.add(getCmbLookAndFeel(), cc.xyw(4,22,2));
			generalPanel.addLabel("Tema", cc.xy(2,24));
			generalPanel.add(getCmbTheme(), cc.xyw(4,24,2));
			
			generalPanel.addSeparator("", cc.xyw(2,26,5));
			generalPanel.addLabel("Lokacija ra\u010dunara", cc.xy(2,28));
			generalPanel.add(getTfLocation(), cc.xyw(4,28,2));
			
		}
		return generalPanel.getPanel();
	}

	private JCheckBox getChkNonCtlgNo() {
		if (chkNonCtlgNo == null) {
			chkNonCtlgNo = new JCheckBox();
			chkNonCtlgNo.setText("Zadu\u017eivanje neobra\u0111enih knjiga");
		}
		return chkNonCtlgNo;
	}
	
	public void setChkdNonCtlgNo(boolean value){
		getChkNonCtlgNo().setSelected(value);
	}
	
	public boolean isChkdNonCtlgNo(){
		return getChkNonCtlgNo().isSelected();
	}

	private JCheckBox getChkBlockedCard() {
		if (chkBlockedCard == null) {
			chkBlockedCard = new JCheckBox();
			chkBlockedCard.setText("Zadu\u017eivanje knjiga na blokiranu karticu");
		}
		return chkBlockedCard;
	}
	
	public void setChkdBlockedCard(boolean value){
		getChkBlockedCard().setSelected(value);
	}
	
	public boolean isChkdBlockedCard(){
		return getChkBlockedCard().isSelected();
	}

	private JCheckBox getChkAutoReturn() {
		if (chkAutoReturn == null) {
			chkAutoReturn = new JCheckBox();
			chkAutoReturn.setText("Razdu\u017eenje zadu\u017eene knjige pri ponovnom zadu\u017eenju");
		}
		return chkAutoReturn;
	}
	
	public void setChkdAutoReturn(boolean value){
		getChkAutoReturn().setSelected(value);
	}
	
	public boolean isChkdAutoReturn(){
		return getChkAutoReturn().isSelected();
	}
	
	private JTextField getTfLocation() {
		if (tfLocation == null) {
			tfLocation = new JTextField();
		}
		return tfLocation;
	}
	
	public String getLocationValue(){
		return getTfLocation().getText();
	}
	
	public void setLocationValue(String text){
		getTfLocation().setText(text);
	}

	private JPanel getReversPanel() {
		if (reversPanel == null) {
			FormLayout layout = new FormLayout(
			        "2dlu:grow, right:60dlu, 3dlu, 150dlu, 2dlu:grow", 
			        "20dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 2dlu:grow");
			CellConstraints cc = new CellConstraints();
			reversPanel = new PanelBuilder(layout);
			reversPanel.setDefaultDialogBorder();
			
			reversPanel.addLabel("Naziv biblioteke", cc.xy(2,2));
			reversPanel.add(getTfLibraryName(), cc.xy(4,2));
			reversPanel.addLabel("Adresa biblioteke", cc.xy(2,4));
			reversPanel.add(getTfLibraryAddress(), cc.xy(4,4));
      reversPanel.addSeparator("", cc.xyw(2,6,3));
      reversPanel.add(getChkReversSelected(), cc.xyw(2,8,3));
			reversPanel.addLabel("Zaglavlje SU", cc.xy(2,10));
			reversPanel.add(getTfReversHeight(), cc.xy(4,10));
			reversPanel.addLabel("\u0160irina papira", cc.xy(2,12));
			reversPanel.add(getTfReversWidth(), cc.xy(4,12));
			reversPanel.addLabel("Broj redova", cc.xy(2,14));
			reversPanel.add(getTfReversRowCount(), cc.xy(4,14));
			reversPanel.addLabel("Broj primeraka", cc.xy(2,16));
			reversPanel.add(getTfReversCount(), cc.xy(4,16));
		}
		return reversPanel.getPanel();
	}

	private JTextField getTfLibraryName() {
		if (tfLibraryName == null) {
			tfLibraryName = new JTextField();
		}
		return tfLibraryName;
	}
	
	public String getLibraryName(){
		return getTfLibraryName().getText();
	}
	
	public void setLibraryName(String text){
		getTfLibraryName().setText(text);
	}

	private JTextField getTfLibraryAddress() {
		if (tfLibraryAddress == null) {
			tfLibraryAddress = new JTextField();
		}
		return tfLibraryAddress;
	}
	
	public String getLibraryAddress(){
		return getTfLibraryAddress().getText();
	}
	
	public void setLibraryAddress(String text){
		getTfLibraryAddress().setText(text);
	}
  
  private JCheckBox getChkReversSelected() {
    if (chkReversSelected == null) {
      chkReversSelected = new JCheckBox();
      chkReversSelected.setText("Samo selektovano");
    }
    return chkReversSelected;
  }
  
  public void setChkdReversSelected(boolean value){
    getChkReversSelected().setSelected(value);
  }
  
  public boolean isChkdReversSelected(){
    return getChkReversSelected().isSelected();
  }

	private JTextField getTfReversHeight() {
		if (tfReversHeight == null) {
			tfReversHeight = new JTextField();
		}
		return tfReversHeight;
	}
	
	public String getReversHeight(){
		return getTfReversHeight().getText();
	}
	
	public void setReversHeight(String text){
		getTfReversHeight().setText(text);
	}

	private JTextField getTfReversWidth() {
		if (tfReversWidth == null) {
			tfReversWidth = new JTextField();
		}
		return tfReversWidth;
	}
	
	public String getReversWidth(){
		return getTfReversWidth().getText();
	}
	
	public void setReversWidth(String text){
		getTfReversWidth().setText(text);
	}

	private JTextField getTfReversRowCount() {
		if (tfReversRowCount == null) {
			tfReversRowCount = new JTextField();
		}
		return tfReversRowCount;
	}
	
	public String getReversRowCount(){
		return getTfReversRowCount().getText();
	}
	
	public void setReversRowCount(String text){
		getTfReversRowCount().setText(text);
	}

	private JTextField getTfReversCount() {
		if (tfReversCount == null) {
			tfReversCount = new JTextField();
		}
		return tfReversCount;
	}
	
	public String getReversCount(){
		return getTfReversCount().getText();
	}
	
	public void setReversCount(String text){
		getTfReversCount().setText(text);
	}

	private JCheckBox getChkDefaultZipCity() {
		if (chkDefaultZipCity == null) {
			chkDefaultZipCity = new JCheckBox();
			chkDefaultZipCity.setText("Default vrednosti za grad i broj po\u0161te");
			chkDefaultZipCity.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.DESELECTED){
						getTfDefaultCity().setEnabled(false);
						getTfDefaultCity().setText("");
						getTfDefaultZip().setEnabled(false);
						getTfDefaultZip().setText("");
					} else {
						getTfDefaultCity().setEnabled(true);
						getTfDefaultZip().setEnabled(true);
					}
				}
			});
		}
		return chkDefaultZipCity;
	}
	
	public void setChkdDefaultZipCity(boolean value){
		getChkDefaultZipCity().setSelected(value);
	}
	
	public boolean isChkdDefaultZipCity(){
		return getChkDefaultZipCity().isSelected();
	}

	private JTextField getTfDefaultCity() {
		if (tfDefaultCity == null) {
			tfDefaultCity = new JTextField();
		}
		return tfDefaultCity;
	}
	
	public String getDefaultCity(){
		return getTfDefaultCity().getText();
	}
	
	public void setDefaultCity(String text){
		getTfDefaultCity().setText(text);
	}
	
	public boolean getDefaultCityEnabled(){
		return getTfDefaultCity().isEnabled();
	}

	private JTextField getTfDefaultZip() {
		if (tfDefaultZip == null) {
			tfDefaultZip = new JTextField();
		}
		return tfDefaultZip;
	}
	
	public String getDefaultZip(){
		return getTfDefaultZip().getText();
	}
	
	public void setDefaultZip(String text){
		getTfDefaultZip().setText(text);
	}
	
	public boolean getDefaultZipEnabled(){
		return getTfDefaultZip().isEnabled();
	}

	private JPanel getBottomPanel() {
		if (bottomPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(50);
			bottomPanel = new JPanel();
			bottomPanel.setLayout(flowLayout);
			bottomPanel.add(getBtnSave(), null);
			bottomPanel.add(getBtnCancel(), null);
		}
		return bottomPanel;
	}

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("Sa\u010duvaj");
			btnSave.setIcon(new ImageIcon(getClass().getResource("/circ-images/Check16.png")));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					handle_save();
				}
			});
		}
		return btnSave;
	}
	
	private void handle_save(){
		String result = Validate.validateOptions(this);
		if (result != null){
			JOptionPane.showMessageDialog(null, result, "Greska", JOptionPane.ERROR_MESSAGE,
					new ImageIcon(getClass().getResource("/circ-images/x32.png")));
		} else {
			ValidatorOptions.saveOptions(this);
			EnvironmentOptions.saveOptions(this, row);
			if (ValidatorOptions.save() && EnvironmentOptions.save()){
				JOptionPane.showMessageDialog(null, "OK", "Info", JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(getClass().getResource("/circ-images/hand32.png")));
				parent.save();
	      Exit();
			} else {
				JOptionPane.showMessageDialog(null, "Greska pri snimanju!", "Greska", JOptionPane.ERROR_MESSAGE,
						new ImageIcon(getClass().getResource("/circ-images/x32.png")));
			}
      
		}
		
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("Odustani");
			btnCancel.setIcon(new ImageIcon(getClass().getResource("/circ-images/Delete16.png")));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
          Exit();
					//TODO log...
				}
			});
		}
		return btnCancel;
	}
	
	private void Exit(){
		this.setVisible(false);
		//this.dispose();
	}
  
  private JPanel getTopPanel() {
    if (topPanel == null) {
      FormLayout layout = new FormLayout(
              "2dlu, left:50dlu, 2dlu, 100dlu, 2dlu:grow", 
              "2dlu, pref, 2dlu, pref, 2dlu");
      CellConstraints cc = new CellConstraints();
      topPanel = new PanelBuilder(layout);
      topPanel.setDefaultDialogBorder();
      
      topPanel.addLabel("Mac adresa:", cc.xy(2,2));
      topPanel.add(getTfAddress(), cc.xy(4,2));
      topPanel.addLabel("Napomena:", cc.xy(2,4));
      topPanel.add(getTfNote(), cc.xy(4,4));
    }
    return topPanel.getPanel();
  }
  
  private JTextField getTfAddress(){
    if (tfAddress == null){
      tfAddress = new JTextField();
    }
    return tfAddress;
  }
  
  private JTextField getTfNote(){
    if (tfNote == null){
      tfNote = new JTextField();
    }
    return tfNote;
  }
  
  public String getMacAddress(){
    return getTfAddress().getText();
  }
  
  public void setMacAddress(String text){
    getTfAddress().setText(text);
  }
  
  public String getNote(){
    return getTfNote().getText();
  }
  
  public void setNote(String text){
    getTfNote().setText(text);
  }
	

}
