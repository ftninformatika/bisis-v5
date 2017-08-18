package com.ftninformatika.bisis.circ.view;


import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.models.circ.EducationLvl;
import com.ftninformatika.bisis.models.circ.Language;
import com.ftninformatika.bisis.models.circ.pojo.Organization;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class UserData {
	
	private PanelBuilder pMain0 = null;
	private PanelBuilder pMain1 = null;
	private JCheckBox chkWarning = null;
	private JTextField tfFirstName = null;
  	private JLabel lFirstName = null;
	private JTextField tfLastName = null;
  	private JLabel lLastName = null;
	private JTextField tfParentName = null;
  	private JLabel lParentName = null;
	private JTextField tfAddress = null;
  	private JLabel lAddress = null;
	private JTextField tfZip = null;
  	private JLabel lZip = null;
	private JTextField tfCity = null;
  	private JLabel lCity = null;
	private JTextField tfPhone = null;
  	private JLabel lPhone = null;
	private JTextField tfEmail = null;
  	private JLabel lEmail = null;
	private JRadioButton rbGenderM = null;
	private JRadioButton rbGenderF = null;
	private JRadioButton rbAgeA = null;
	private JRadioButton rbAgeC = null;
	private JTextField tfTmpAddress = null;
  	private JLabel lTmpAddress = null;
	private JTextField tfTmpCity = null;
  	private JLabel lTmpCity = null;
	private JTextField tfTmpZip = null;
  	private JLabel lTmpZip = null;
	private JTextField tfTmpPhone = null;
 	private JLabel lTmpPhone = null;
	private JTextField tfJmbg = null;
	private JLabel lJmbg = null;
	private JTextField tfDocNo = null;
  	private JLabel lDocNo = null;
	private JTextField tfDocCity = null;
  	private JLabel lDocCity = null;
	private JTextField tfCountry = null;
  	private JLabel lCountry = null;
	private JTextField tfTitle = null;
  	private JLabel lTitle = null;
	private JTextField tfOccupation = null;
  	private JLabel lOccupation = null;
	private JTextField tfIndexNo = null;
  	private JLabel lIndexNo = null;
	private JComboBox cmbOrg = null;
  	private JLabel lOrg = null;
	private JComboBox cmbEduLevel = null;
  	private JLabel lEduLevel = null;
	private JComboBox cmbLanguage = null;
  	private JLabel lLanguage = null;
	private JTextArea tfNote = null;
  	private JScrollPane scrollTextArea = null;
  	private JLabel lNote = null;
	private JTextField tfInterests = null;
  	private JLabel lInterests = null;
	private JComboBox cmbClass = null;
  	private JLabel lClass = null;
	private JComboBox cmbDocID = null;
	private ButtonGroup rbGrpGender = null;
	private ButtonGroup rbGrpAge = null;
	private JScrollPane jScrollPane = null;
	private JTable tblDuplicate = null;
	//private DuplicateTableModel duplicateTableModel = null;
	private JButton btnAdd = null;
	private JButton btnRemove = null;
	private PanelBuilder pDup = null;
	private JButton btnBlock = null;
	private JButton btnPrint = null;
	private JButton btnPin = null;
	private User parent = null;
	private ComboBoxRenderer cmbRenderer = null;
  	/*private ZipPlaceDlg zipplace = null;*/
  	private CmbKeySelectionManager cmbKeySelectionManager = null;
  	private boolean blocked;
  	//private Note note;
  	private String blockedReason;
  	private String pincode;
	
	public UserData(User parent){
		this.parent = parent;
		makePMain0();
		makePDup();
		makePMain1();
    getPMain0().addComponentListener(new ComponentAdapter() {
      public void componentShown(ComponentEvent e){
        getTfFirstName().requestFocusInWindow();
      }
    });
    getPMain1().addComponentListener(new ComponentAdapter() {
      public void componentShown(ComponentEvent e){
        getTfTmpAddress().requestFocusInWindow();
      }
    });
	}
	
	private void makePMain0(){
		if (pMain0 == null) {
			FormLayout layout = new FormLayout(
			        "2dlu:grow, right:55dlu, 3dlu, 100dlu, 35dlu, right:40dlu, 3dlu, 30dlu, 7dlu, right:20dlu, 3dlu, 70dlu, 2dlu:grow",  //$NON-NLS-1$
			        "5dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 40dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu:grow"); //$NON-NLS-1$
			CellConstraints cc = new CellConstraints();
			pMain0 = new PanelBuilder(layout);
			pMain0.setDefaultDialogBorder();
			
			pMain0.addSeparator("",cc.xyw(2,2,3)); //$NON-NLS-1$
            pMain0.add(getFirstNameLabel(), cc.xy(2,4));
			pMain0.add(getTfFirstName(), cc.xy(4,4));
			pMain0.add(getLastNameLabel(), cc.xy(2,6));
			pMain0.add(getTfLastName(), cc.xy(4,6));
			pMain0.add(getParentNameLabel(), cc.xy(2,8));
			pMain0.add(getTfParentName(), cc.xy(4,8));
			
			pMain0.addSeparator("", cc.xyw(6,2,7)); //$NON-NLS-1$
			pMain0.add(getAddressLabel(), cc.xy(6,4));
			pMain0.add(getTfAddress(), cc.xyw(8,4,5));
			pMain0.add(getZipLabel(), cc.xy(6,6));
			pMain0.add(getTfZip(), cc.xy(8,6));
			pMain0.add(getCityLabel(), cc.xy(10,6));
			pMain0.add(getTfCity(), cc.xy(12,6));
			pMain0.add(getPhoneLabel(), cc.xy(6,8));
			pMain0.add(getTfPhone(), cc.xyw(8,8,5));
			pMain0.add(getEmailLabel(), cc.xy(6,10));
			pMain0.add(getTfEmail(), cc.xyw(8,10,5));
			
			pMain0.addSeparator(Messages.getString("circulation.gender"), cc.xyw(6,12,3)); //$NON-NLS-1$
			pMain0.addSeparator(Messages.getString("circulation.age"), cc.xyw(11,12,2)); //$NON-NLS-1$
			pMain0.add(getRbGenderM(), cc.xyw(6,14,3));
			pMain0.add(getRbGenderF(), cc.xyw(6,16,3));
			pMain0.add(getRbAgeA(), cc.xyw(11,14,2));
			pMain0.add(getRbAgeC(), cc.xyw(11,16,2));
			
			pMain0.addSeparator("", cc.xyw(2,12,3)); //$NON-NLS-1$
			pMain0.add(getJmbgLabel(), cc.xy(2,14));
			pMain0.add(getTfJmbg(), cc.xy(4,14));
			pMain0.addLabel(Messages.getString("circulation.document"), cc.xy(2,16)); //$NON-NLS-1$
			pMain0.add(getCmbDocID(), cc.xy(4,16,"fill, fill")); //$NON-NLS-1$
			pMain0.add(getDocNoLabel(), cc.xy(2,18));
			pMain0.add(getTfDocNo(), cc.xy(4,18));
			pMain0.add(getDocCityLabel(), cc.xy(2,20));
			pMain0.add(getTfDocCity(), cc.xy(4,20));
			pMain0.add(getCountryLabel(), cc.xy(2,22));
			pMain0.add(getTfCountry(), cc.xy(4,22));
			
			pMain0.addSeparator("", cc.xyw(6,20,7)); //$NON-NLS-1$
			pMain0.addLabel(Messages.getString("circulation.indicator"), cc.xyw(6,22,3)); //$NON-NLS-1$
			pMain0.add(getChkWarning(), cc.xyw(9,22,2));
			pMain0.add(getBtnPrint(), cc.xy(12,22, "right, center")); //$NON-NLS-1$
		
		}
	}
	
	public JPanel getPMain0(){
		return pMain0.getPanel();
	}

	private JCheckBox getChkWarning() {
		if (chkWarning == null) {
			chkWarning = new JCheckBox();
      chkWarning.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          handleKeyTyped();
        }
      });
		}
		return chkWarning;
	}

	private JTextField getTfFirstName() {
		if (tfFirstName == null) {
			tfFirstName = new JTextField();
      tfFirstName.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfFirstName, e);
        }
      });
		}
		return tfFirstName;
	}
  
  public JLabel getFirstNameLabel() {
    if (lFirstName == null) {
      lFirstName = new JLabel(Messages.getString("circulation.firstname")); //$NON-NLS-1$
    }
    return lFirstName;
  }

	private JTextField getTfLastName() {
		if (tfLastName == null) {
			tfLastName = new JTextField();
      tfLastName.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfLastName, e);
        }
      });
		}
		return tfLastName;
	}
  
  public JLabel getLastNameLabel() {
    if (lLastName == null) {
      lLastName = new JLabel(Messages.getString("circulation.lastname")); //$NON-NLS-1$
    }
    return lLastName;
  }

	private JTextField getTfParentName() {
		if (tfParentName == null) {
			tfParentName = new JTextField();
      tfParentName.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfParentName, e);
        }
      });
		}
		return tfParentName;
	}
  
  public JLabel getParentNameLabel() {
    if (lParentName == null) {
      lParentName = new JLabel(Messages.getString("circulation.parentname")); //$NON-NLS-1$
    }
    return lParentName;
  }

	private JTextField getTfAddress() {
		if (tfAddress == null) {
			tfAddress = new JTextField();
      tfAddress.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfAddress, e);
        }
      });
		}
		return tfAddress;
	}
  
  public JLabel getAddressLabel() {
    if (lAddress == null) {
      lAddress = new JLabel(Messages.getString("circulation.address")); //$NON-NLS-1$
    }
    return lAddress;
  }

	private JTextField getTfZip() {
		if (tfZip == null) {
			tfZip = new JTextField();
      tfZip.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          if(e.getKeyCode()==KeyEvent.VK_F9){
            //getZipPlace().setVisible(true);
            //tfZip.setText(getZipPlace().getZip());
            //getTfCity().setText(getZipPlace().getPlace());
          }else{
            handleKeys(tfZip, e);
          }
        }
      });
		}
		return tfZip;
	}
  
  public JLabel getZipLabel() {
    if (lZip == null) {
      lZip = new JLabel(Messages.getString("circulation.zipcode")); //$NON-NLS-1$
    }
    return lZip;
  }

	private JTextField getTfCity() {
		if (tfCity == null) {
			tfCity = new JTextField();
      tfCity.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          if(e.getKeyCode()==KeyEvent.VK_F9){
            //getZipPlace().setVisible(true);
            //getTfZip().setText(getZipPlace().getZip());
            //tfCity.setText(getZipPlace().getPlace());
          }else{
            handleKeys(tfCity, e);
          }
        }
      });
		}
		return tfCity;
	}
  
  public JLabel getCityLabel() {
    if (lCity == null) {
      lCity = new JLabel(Messages.getString("circulation.place")); //$NON-NLS-1$
    }
    return lCity;
  }

	private JTextField getTfPhone() {
		if (tfPhone == null) {
			tfPhone = new JTextField();
      tfPhone.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfPhone, e);
        }
      });
		}
		return tfPhone;
	}
  
  public JLabel getPhoneLabel() {
    if (lPhone == null) {
      lPhone = new JLabel(Messages.getString("circulation.phone")); //$NON-NLS-1$
    }
    return lPhone;
  }

	private JTextField getTfEmail() {
		if (tfEmail == null) {
			tfEmail = new JTextField();
      tfEmail.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfEmail, e);
        }
      });
		}
		return tfEmail;
	}
  
  public JLabel getEmailLabel() {
    if (lEmail == null) {
      lEmail = new JLabel(Messages.getString("circulation.email")); //$NON-NLS-1$
    }
    return lEmail;
  }

	private JRadioButton getRbGenderM() {
		if (rbGenderM == null) {
			rbGenderM = new JRadioButton();
			rbGenderM.setText(Messages.getString("circulation.male")); //$NON-NLS-1$
			getRbGrpGender().add(rbGenderM);
      rbGenderM.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          handleKeyTyped();
        }
      });
		}
		return rbGenderM;
	}

	private JRadioButton getRbGenderF() {
		if (rbGenderF == null) {
			rbGenderF = new JRadioButton();
			rbGenderF.setText(Messages.getString("circulation.female")); //$NON-NLS-1$
			getRbGrpGender().add(rbGenderF);
      rbGenderF.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          handleKeyTyped();
        }
      });
		}
		return rbGenderF;
	}

	private JRadioButton getRbAgeA() {
		if (rbAgeA == null) {
			rbAgeA = new JRadioButton();
			rbAgeA.setText(Messages.getString("circulation.adult")); //$NON-NLS-1$
			getRbGrpAge().add(rbAgeA);
      rbAgeA.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          handleKeyTyped();
        }
      });
		}
		return rbAgeA;
	}

	private JRadioButton getRbAgeC() {
		if (rbAgeC == null) {
			rbAgeC = new JRadioButton();
			rbAgeC.setText(Messages.getString("circulation.child")); //$NON-NLS-1$
			getRbGrpAge().add(rbAgeC);
      rbAgeC.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          handleKeyTyped();
        }
      });
		}
		return rbAgeC;
	}

	private void makePMain1() {
		if (pMain1 == null) {
			FormLayout layout = new FormLayout(
			        "2dlu:grow, right:40dlu, 3dlu, 30dlu, 7dlu, right:25dlu, 3dlu, 70dlu, 35dlu, right:55dlu, 3dlu, 40dlu, 60dlu, 3dlu:grow",  //$NON-NLS-1$
			        "5dlu, pref, 2dlu, 15dlu, 2dlu, pref, 2dlu, pref, 20dlu, 20dlu, pref, 2dlu, 15dlu, 2dlu, 15dlu, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu:grow"); //$NON-NLS-1$
			CellConstraints cc = new CellConstraints();
			pMain1 = new PanelBuilder(layout);
			pMain1.setDefaultDialogBorder();
			pMain1.addSeparator(Messages.getString("circulation.additionaladdress"), cc.xyw(2,2,7)); //$NON-NLS-1$
			pMain1.add(getTmpAddressLabel(), cc.xy(2,4));
			pMain1.add(getTfTmpAddress(), cc.xyw(4,4,5));
			pMain1.add(getTmpZipLabel(), cc.xy(2,6));
			pMain1.add(getTfTmpZip(), cc.xy(4,6));
			pMain1.add(getTmpCityLabel(), cc.xy(6,6));
			pMain1.add(getTfTmpCity(), cc.xy(8,6));
			pMain1.add(getTmpPhoneLabel(), cc.xy(2,8));
			pMain1.add(getTfTmpPhone(), cc.xyw(4,8,5));
			
			pMain1.addSeparator("", cc.xyw(2,11,7)); //$NON-NLS-1$
			pMain1.add(getOrganizationLabel(), cc.xy(2,13));
			pMain1.add(getCmbOrg(), cc.xyw(4,13,5,"fill, fill")); //$NON-NLS-1$
			pMain1.add(getEduLvlLabel(), cc.xy(2,15));
			pMain1.add(getCmbEduLevel(), cc.xyw(4,15,5,"fill, fill")); //$NON-NLS-1$
			pMain1.add(getOccupationLabel(), cc.xy(2,17));
			pMain1.add(getTfOccupation(), cc.xyw(4,17,5));
			pMain1.add(getTitleLabel(), cc.xy(2,19));
			pMain1.add(getTfTitle(),  cc.xyw(4,19,5));
			pMain1.add(getIndexNoLabel(), cc.xy(2,21));
			pMain1.add(getTfIndexNo(), cc.xyw(4,21,4));
			pMain1.add(getClassNoLabel(), cc.xy(2,23));
			pMain1.add(getCmbClass(), cc.xyw(4,23,4,"fill, fill")); //$NON-NLS-1$
			
			pMain1.addSeparator("", cc.xyw(10,2,4)); //$NON-NLS-1$
			pMain1.add(getLanguagesLabel(), cc.xy(10,4));
			pMain1.add(getCmbLanguage(), cc.xyw(12,4,2,"fill, fill")); //$NON-NLS-1$
			pMain1.add(getInterestsLabel(), cc.xy(10,6));
			pMain1.add(getTfInterests(), cc.xyw(12,6,2));
			pMain1.add(getNoteLabel(), cc.xy(10,8));
			pMain1.add(getScrollTextArea(), cc.xywh(12,8,2,2));
			
			pMain1.addSeparator(Messages.getString("circulation.duplicates"), cc.xyw(10,11,4)); //$NON-NLS-1$
			pMain1.add(getPDup(), cc.xywh(10,13,4,7));
			pMain1.addSeparator("", cc.xyw(10,21,4)); //$NON-NLS-1$
			pMain1.add(getBtnBlock(), cc.xy(10,23,"fill, center")); //$NON-NLS-1$
			if (/*BisisApp.getINIFile().getBoolean("pincode", "enabled")*/true){//TODO-hardcoded
				pMain1.add(getBtnPin(), cc.xy(13,23,"fill, center")); //$NON-NLS-1$
			}	
		}
	}
	
	public JPanel getPMain1(){
		return pMain1.getPanel();
	}

	private JTextField getTfTmpAddress() {
		if (tfTmpAddress == null) {
			tfTmpAddress = new JTextField();
      tfTmpAddress.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfTmpAddress, e);
        }
      });
		}
		return tfTmpAddress;
	}
  
  public JLabel getTmpAddressLabel() {
    if (lTmpAddress == null) {
      lTmpAddress = new JLabel(Messages.getString("circulation.address")); //$NON-NLS-1$
    }
    return lTmpAddress;
  }

	private JTextField getTfTmpCity() {
		if (tfTmpCity == null) {
			tfTmpCity = new JTextField();
      tfTmpCity.addKeyListener(new KeyAdapter(){
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          if(e.getKeyCode()==KeyEvent.VK_F9){
            //getZipPlace().setVisible(true);
            //getTfTmpZip().setText(getZipPlace().getZip());
            //tfTmpCity.setText(getZipPlace().getPlace());
          }else{
            handleKeys(tfTmpCity, e);
          }
        }
      });
		}
		return tfTmpCity;
	}
  
  public JLabel getTmpCityLabel() {
    if (lTmpCity == null) {
      lTmpCity = new JLabel(Messages.getString("circulation.place")); //$NON-NLS-1$
    }
    return lTmpCity;
  }

	private JTextField getTfTmpZip() {
		if (tfTmpZip == null) {
			tfTmpZip = new JTextField();
      tfTmpZip.addKeyListener(new KeyAdapter(){
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          if(e.getKeyCode()==KeyEvent.VK_F9){
            //getZipPlace().setVisible(true);
            //tfTmpZip.setText(getZipPlace().getZip());
            //getTfTmpCity().setText(getZipPlace().getPlace());
          }else{
            handleKeys(tfTmpZip, e);
          }
        }
      });
		}
		return tfTmpZip;
	}
  
  public JLabel getTmpZipLabel() {
    if (lTmpZip == null) {
      lTmpZip = new JLabel(Messages.getString("circulation.zipcode")); //$NON-NLS-1$
    }
    return lTmpZip;
  }

	private JTextField getTfTmpPhone() {
		if (tfTmpPhone == null) {
			tfTmpPhone = new JTextField();
      tfTmpPhone.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfTmpPhone, e);
        }
      });
		}
		return tfTmpPhone;
	}
  
  public JLabel getTmpPhoneLabel() {
    if (lTmpPhone == null) {
      lTmpPhone = new JLabel(Messages.getString("circulation.phone")); //$NON-NLS-1$
    }
    return lTmpPhone;
  }

	private JTextField getTfJmbg() {
		if (tfJmbg == null) {
			tfJmbg = new JTextField();
      tfJmbg.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfJmbg, e);
        }
      });
		}
		return tfJmbg;
	}
  
  public JLabel getJmbgLabel() {
    if (lJmbg == null) {
      lJmbg = new JLabel(Messages.getString("circulation.umcn")); //$NON-NLS-1$
    }
    return lJmbg;
  }

	private JTextField getTfDocNo() {
		if (tfDocNo == null) {
			tfDocNo = new JTextField();
      tfDocNo.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfDocNo, e);
        }
      });
		}
		return tfDocNo;
	}
  
  public JLabel getDocNoLabel() {
    if (lDocNo == null) {
      lDocNo = new JLabel(Messages.getString("circulation.documentnumber")); //$NON-NLS-1$
    }
    return lDocNo;
  }

	private JTextField getTfDocCity() {
		if (tfDocCity == null) {
			tfDocCity = new JTextField();
      tfDocCity.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfDocCity, e);
        }
      });
		}
		return tfDocCity;
	}
  
  public JLabel getDocCityLabel() {
    if (lDocCity == null) {
      lDocCity = new JLabel(Messages.getString("circulation.documentplace")); //$NON-NLS-1$
    }
    return lDocCity;
  }

	private JTextField getTfCountry() {
		if (tfCountry == null) {
			tfCountry = new JTextField();
      tfCountry.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfCountry, e);
        }
      });
		}
		return tfCountry;
	}
  
  public JLabel getCountryLabel() {
    if (lCountry == null) {
      lCountry = new JLabel(Messages.getString("circulation.documentcountry")); //$NON-NLS-1$
    }
    return lCountry;
  }

	private JTextField getTfTitle() {
		if (tfTitle == null) {
			tfTitle = new JTextField();
      tfTitle.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfTitle, e);
        }
      });
		}
		return tfTitle;
	}
  
  public JLabel getTitleLabel() {
    if (lTitle == null) {
      lTitle = new JLabel(Messages.getString("circulation.title")); //$NON-NLS-1$
    }
    return lTitle;
  }

	private JTextField getTfOccupation() {
		if (tfOccupation == null) {
			tfOccupation = new JTextField();
      tfOccupation.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfOccupation, e);
        }
      });
		}
		return tfOccupation;
	}
  
  public JLabel getOccupationLabel() {
    if (lOccupation == null) {
      lOccupation = new JLabel(Messages.getString("circulation.occupation")); //$NON-NLS-1$
    }
    return lOccupation;
  }

	private JTextField getTfIndexNo() {
		if (tfIndexNo == null) {
			tfIndexNo = new JTextField();
      tfIndexNo.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfIndexNo, e);
        }
      });
		}
		return tfIndexNo;
	}
  
  public JLabel getIndexNoLabel() {
    if (lIndexNo == null) {
      lIndexNo = new JLabel(Messages.getString("circulation.studentcard")); //$NON-NLS-1$
    }
    return lIndexNo;
  }
	
	private ComboBoxRenderer getCmbRenderer(){
		if (cmbRenderer == null){
			cmbRenderer = new ComboBoxRenderer();
		}
		return cmbRenderer;
	}
  
  private CmbKeySelectionManager getCmbKeySelectionManager(){
    if (cmbKeySelectionManager == null){
      cmbKeySelectionManager = new CmbKeySelectionManager();
    }
    return cmbKeySelectionManager;
  }

	private JComboBox getCmbOrg() {
		if (cmbOrg == null) {
			cmbOrg = new JComboBox();
			cmbOrg.setRenderer(getCmbRenderer());
      cmbOrg.setKeySelectionManager(getCmbKeySelectionManager());
      cmbOrg.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          handleKeyTyped();
        }
      });
		}
		return cmbOrg;
	}
  
  public JLabel getOrganizationLabel() {
    if (lOrg == null) {
      lOrg = new JLabel(Messages.getString("circulation.organization")); //$NON-NLS-1$
    }
    return lOrg;
  }

	private JComboBox getCmbEduLevel() {
		if (cmbEduLevel == null) {
			cmbEduLevel = new JComboBox();
			cmbEduLevel.setRenderer(getCmbRenderer());
      cmbEduLevel.setKeySelectionManager(getCmbKeySelectionManager());
      cmbEduLevel.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          handleKeyTyped();
        }
      });
		}
		return cmbEduLevel;
	}
  
  public JLabel getEduLvlLabel() {
    if (lEduLevel == null) {
      lEduLevel = new JLabel(Messages.getString("circulation.education")); //$NON-NLS-1$
    }
    return lEduLevel;
  }

	private JComboBox getCmbLanguage() {
		if (cmbLanguage == null) {
			cmbLanguage = new JComboBox();
			cmbLanguage.setRenderer(getCmbRenderer());
      cmbLanguage.setKeySelectionManager(getCmbKeySelectionManager());
      cmbLanguage.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          handleKeyTyped();
        }
      });
		}
		return cmbLanguage;
	}
  
  public JLabel getLanguagesLabel() {
    if (lLanguage == null) {
      lLanguage = new JLabel(Messages.getString("circulation.language")); //$NON-NLS-1$
    }
    return lLanguage;
  }
  
  private JScrollPane getScrollTextArea() {
    if (scrollTextArea == null) {
      scrollTextArea = new JScrollPane();
      scrollTextArea.setViewportView(getTfNote());
    }
    return scrollTextArea;
  }

	private JTextArea getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextArea();
      tfNote.setLineWrap(true);
      tfNote.setWrapStyleWord(true);
      tfNote.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfNote, e);
        }
      });
		}
		return tfNote;
	}
  
  public JLabel getNoteLabel() {
    if (lNote == null) {
      lNote = new JLabel(Messages.getString("circulation.note")); //$NON-NLS-1$
    }
    return lNote;
  }

	private JTextField getTfInterests() {
		if (tfInterests == null) {
			tfInterests = new JTextField();
      tfInterests.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
          handleKeyTyped();
        }
        public void keyReleased(KeyEvent e){
          handleKeys(tfInterests, e);
        }
      });
		}
		return tfInterests;
	}
  
  public JLabel getInterestsLabel() {
    if (lInterests == null) {
      lInterests = new JLabel(Messages.getString("circulation.interests")); //$NON-NLS-1$
    }
    return lInterests;
  }

	private JComboBox getCmbClass() {
		if (cmbClass == null) {
			cmbClass = new JComboBox();
			cmbClass.addItem(""); //$NON-NLS-1$
			cmbClass.addItem("1"); //$NON-NLS-1$
			cmbClass.addItem("2"); //$NON-NLS-1$
			cmbClass.addItem("3"); //$NON-NLS-1$
			cmbClass.addItem("4"); //$NON-NLS-1$
			cmbClass.addItem("5"); //$NON-NLS-1$
			cmbClass.addItem("6"); //$NON-NLS-1$
			cmbClass.addItem("7"); //$NON-NLS-1$
			cmbClass.addItem("8"); //$NON-NLS-1$
      cmbClass.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          handleKeyTyped();
        }
      });
		}
		return cmbClass;
	}
  
  public JLabel getClassNoLabel() {
    if (lClass == null) {
      lClass = new JLabel(Messages.getString("circulation.year")); //$NON-NLS-1$
    }
    return lClass;
  }
  
  public JLabel getDupDateLabel() {
    return new ColumnNameLabel(0);
  }
  
  public JLabel getDupNoLabel() {
    return new ColumnNameLabel(1);
  }

	private JComboBox getCmbDocID() {
		if (cmbDocID == null) {
			cmbDocID = new JComboBox();
			cmbDocID.addItem(Messages.getString("circulation.identificationcard")); //$NON-NLS-1$
			cmbDocID.addItem(Messages.getString("circulation.passport")); //$NON-NLS-1$
			cmbDocID.addItem(Messages.getString("circulation.other")); //$NON-NLS-1$
      cmbDocID.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          handleKeyTyped();
        }
      });
		}
		return cmbDocID;
	}
	
	private ButtonGroup getRbGrpGender() {
		if (rbGrpGender == null) {
			rbGrpGender = new ButtonGroup();
		}
		return rbGrpGender;
	}
	
	private ButtonGroup getRbGrpAge() {
		if (rbGrpAge == null) {
			rbGrpAge = new ButtonGroup();
		}
		return rbGrpAge;
	}
	
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTblDuplicate());
		}
		return jScrollPane;
	}

	private JTable getTblDuplicate() {
		if (tblDuplicate == null) {
			tblDuplicate = new JTable();
			//tblDuplicate.setModel(getDuplicateTableModel());
      //tblDuplicate.setDefaultEditor(Date.class, new JDateChooserCellEditor());
		}
		return tblDuplicate;
	}

	/*private DuplicateTableModel getDuplicateTableModel() {
		if (duplicateTableModel == null) {
      duplicateTableModel = new DuplicateTableModel();
//      duplicateTableModel.addTableModelListener(new TableModelListener() {
//        public void tableChanged(TableModelEvent e){
//          handleKeyTyped();
//        }
//      });
		}
		return duplicateTableModel;
	}*/
	
	public void setDupTableModel(Set duplicates){
		//getDuplicateTableModel().setData(duplicates);
	}
  
  public void fixTable(){
    //getTblDuplicate().setDefaultEditor(Date.class, new JDateChooserCellEditor());
  }
	
	private JButton getBtnAdd() {
		if (btnAdd == null){
			btnAdd = new JButton();
			btnAdd.setIcon(new ImageIcon(getClass().getResource("/circ-images/plus16.png"))); //$NON-NLS-1$
			btnAdd.setToolTipText(Messages.getString("circulation.add")); //$NON-NLS-1$
			btnAdd.setFocusable(false);
			//btnAdd.setPreferredSize(new java.awt.Dimension(28,28));
			/*btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getDuplicateTableModel().addRow();
				}
			});
		}*/}
		return btnAdd;
	}
	
	private JButton getBtnRemove() {
		if (btnRemove == null){
			btnRemove = new JButton();
			btnRemove.setIcon(new ImageIcon(getClass().getResource("/circ-images/minus16.png"))); //$NON-NLS-1$
			btnRemove.setToolTipText(Messages.getString("circulation.delete")); //$NON-NLS-1$
			btnRemove.setFocusable(false);
			//btnRemove.setPreferredSize(new java.awt.Dimension(28,28));
			btnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getTblDuplicate().getSelectedRow() != -1){
						if (getTblDuplicate().getCellEditor() != null)
							getTblDuplicate().getCellEditor().stopCellEditing();
						//getDuplicateTableModel().removeRow(getTblDuplicate().getSelectedRow());
					}
				}
			});
		}
		return btnRemove;
	}
	
	private void makePDup() {
		if (pDup == null) {
			FormLayout layout = new FormLayout(
			        "132dlu, 5dlu, 18dlu",  //$NON-NLS-1$
			        "8dlu, 18dlu, 6dlu, 18dlu, 7dlu"); //$NON-NLS-1$
			CellConstraints cc = new CellConstraints();
			pDup = new PanelBuilder(layout);
			pDup.add(getJScrollPane(), cc.xywh(1,1,1,5));
			pDup.add(getBtnAdd(), cc.xy(3,2,"fill, fill")); //$NON-NLS-1$
			pDup.add(getBtnRemove(), cc.xy(3,4,"fill, fill")); //$NON-NLS-1$
		}
	}
	
	private JPanel getPDup(){
		return pDup.getPanel();
	}
	
	private JButton getBtnBlock() {
		if (btnBlock == null) {
			btnBlock = new JButton();
			btnBlock.setText(Messages.getString("circulation.block")); //$NON-NLS-1$
			btnBlock.setFocusable(false);
			btnBlock.setIcon(new ImageIcon(getClass().getResource("/circ-images/block16.png"))); //$NON-NLS-1$
      btnBlock.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          handleKeyTyped();
          if(blocked) {
            btnBlock.setText(Messages.getString("circulation.block")); //$NON-NLS-1$
            blocked = false;
          }else{
           //note = new Note(null,true);
            //note.setLocationRelativeTo(null);
            //note.setVisible(true);
            if (!/*note.getValue().equals("")*/true){ //$NON-NLS-1$ //TODO-hardcoded
              //blockedReason = note.getValue();
              btnBlock.setText(Messages.getString("circulation.activate")); //$NON-NLS-1$
              //note.dispose();
              blocked = true;
            }
          }
        }
      });
		}
		return btnBlock;
	}
	
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText(Messages.getString("circulation.print")); //$NON-NLS-1$
			btnPrint.setToolTipText(Messages.getString("circulation.print")); //$NON-NLS-1$
			btnPrint.setFocusable(false);
			btnPrint.setIcon(new ImageIcon(getClass().getResource("/circ-images/print16.png"))); //$NON-NLS-1$
			btnPrint.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parent.printCard();
				}
			});
		}
		return btnPrint;
	}
	
	private JButton getBtnPin() {
		if (btnPin == null) {
			btnPin = new JButton();
			btnPin.setText(Messages.getString("circulation.pin")); //$NON-NLS-1$
			btnPin.setToolTipText(Messages.getString("circulation.pin")); //$NON-NLS-1$
			btnPin.setFocusable(false);
			btnPin.setIcon(new ImageIcon(getClass().getResource("/circ-images/print16.png"))); //$NON-NLS-1$
			btnPin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parent.printPin();
				}
			});
		}
		return btnPin;
	}
  
  /*private ZipPlaceDlg getZipPlace() {
    if (zipplace == null) {
      zipplace = new ZipPlaceDlg(BisisApp.getMainFrame());
      zipplace.setLocationRelativeTo(null);
    }
    return zipplace;
  }*/
	
	public String getFirstName(){
		return getTfFirstName().getText();
	}
	
	public String getLastName(){
		return getTfLastName().getText();
	}
	
	public String getParentName(){
		return getTfParentName().getText();
	}
	
	public String getAddress(){
		return getTfAddress().getText();
	}
	
	public String getAddressRevers(){
		return getTfAddress().getText() + ", " + getTfCity().getText();
	}
	
	public String getZip(){
		return getTfZip().getText();
	}
	
	public String getCity(){
		return getTfCity().getText();
	}
	
	public String getPhone(){
		return getTfPhone().getText();
	}
	
	public String getEmail(){
		return getTfEmail().getText();
	}
	
	public String getTmpAddress(){
		return getTfTmpAddress().getText();
	}
	
	public String getTmpZip(){
		return getTfTmpZip().getText();
	}
	
	public String getTmpCity(){
		return getTfTmpCity().getText();
	}
	
	public String getTmpPhone(){
		return getTfTmpPhone().getText();
	}
	
	public String getJmbg(){
		return getTfJmbg().getText();
	}
	
	public String getDocNo(){
		return getTfDocNo().getText();
	}
	
	public String getDocCity(){
		return getTfDocCity().getText();
	}
	
	public String getCountry(){
		return getTfCountry().getText();
	}
	
	public String getTitle(){
		return getTfTitle().getText();
	}
	
	public String getOccupation(){
		return getTfOccupation().getText();
	}
	
	public String getIndexNo(){
		return getTfIndexNo().getText();
	}
	
	public String getIndexNoRevers(){
		if (!getIndexNo().equals("")){
			return "Broj indeksa: " + getIndexNo();
		} else {
			return "";
		}
		
	}
	
	public String getNote(){
		return getTfNote().getText();
	}
	
	public String getInterests(){
		return getTfInterests().getText();
	}
	
	public String getDupDate(){
		try{
			return getTblDuplicate().getModel().getValueAt(getTblDuplicate().getModel().getRowCount()-1, 0).toString();
		}catch (Exception e){
			return null;
		}
	}
	
	public String getDupNo(){
		try{
			return getTblDuplicate().getModel().getValueAt(getTblDuplicate().getModel().getRowCount()-1, 1).toString();
		}catch (Exception e){
			return null;
		}
	}
	
	public int getWarning(){
		if (getChkWarning().isSelected()){
			return 1;
		} else {
			return 0;
		}
	}
	
	public int getDocId(){
		return getCmbDocID().getSelectedIndex();
	}
	
	public String getClassNo(){
		return (String)getCmbClass().getSelectedItem();
	}
	
	public Organization getOrganization(){
		return (Organization) Utils.getCmbValue(getCmbOrg());
	}
	
	public EducationLvl getEduLvl(){
		return (EducationLvl)Utils.getCmbValue(getCmbEduLevel());
	}
	
	public Language getLanguages(){
		return (Language)Utils.getCmbValue(getCmbLanguage());
	}

	public String getGender(){
		if (getRbGenderF().isSelected()){
			return "F"; //$NON-NLS-1$
		} else {
			return "M"; //$NON-NLS-1$
		}
	}
	
	public String getAge(){
		if (getRbAgeC().isSelected()){
			return "C"; //$NON-NLS-1$
		} else {
			return "A"; //$NON-NLS-1$
		}
	}
  
  public boolean getBlocked(){
    return blocked;
  }
  
  public String getBlockedReason(){
    return blockedReason;
  }
  
  public String getPinCode(){
    return pincode;
  }
  
  public void setPinCode(String pin){
	  pincode = pin;
  }
	
	public void loadDefault(){
		getChkWarning().setSelected(true);
		getRbGenderM().setSelected(true);
		getRbAgeA().setSelected(true);
		this.blocked = false;
	    this.blockedReason = "";
	    this.pincode="";
	    getBtnBlock().setText(Messages.getString("circulation.block"));
		if (Cirkulacija.getApp().getEnvironment().getDefaultZipCity()){
			getTfZip().setText(Cirkulacija.getApp().getEnvironment().getDefaultZip());
			getTfCity().setText(Cirkulacija.getApp().getEnvironment().getDefaultCity());
		}
	}
	
	public void loadUser(String firstName, String lastName, String parentName,
						 String address, String zip, String city, String phone, String email,
						 String gender, String age, String tmpAddress, String tmpCity, String tmpZip,
						 String tmpPhone, String jmbg, int docId, String docNo, String docCity, String country,
						 String title, String occupation, String indexNo, String classNo, Organization org,
						 EducationLvl eduLvl, Language languages, String note, String interests, int warn, boolean blocked, String blockedReason, Set duplicates, String pincode){
		getTfFirstName().setText(firstName);
		getTfLastName().setText(lastName);
		getTfParentName().setText(parentName);
		getTfAddress().setText(address);
		getTfZip().setText(zip);
		getTfCity().setText(city);
		getTfPhone().setText(phone);
		getTfEmail().setText(email);
		if (gender.equalsIgnoreCase("f")){ //$NON-NLS-1$
			getRbGenderF().setSelected(true);
		} else {
			getRbGenderM().setSelected(true);
		}
		if (age.equalsIgnoreCase("c")){ //$NON-NLS-1$
			getRbAgeC().setSelected(true);
		} else {
			getRbAgeA().setSelected(true);
		}
		getTfTmpAddress().setText(tmpAddress);
		getTfTmpCity().setText(tmpCity);
		getTfTmpZip().setText(tmpZip);
		getTfTmpPhone().setText(tmpPhone);
		getTfJmbg().setText(jmbg);
		getCmbDocID().setSelectedIndex(docId);
		getTfDocNo().setText(docNo);
		getTfDocCity().setText(docCity);
		getTfCountry().setText(country);
		getTfTitle().setText(title);
		getTfOccupation().setText(occupation);
		getTfIndexNo().setText(indexNo);
		//Utils.setComboItem(getCmbClass(), classNo);
		//Utils.setComboItem(getCmbOrg(), org);
		//Utils.setComboItem(getCmbEduLevel(), eduLvl);
		//Utils.setComboItem(getCmbLanguage(), languages);
		getTfNote().setText(note);
		getTfInterests().setText(interests);
		if (warn == 1){
			getChkWarning().setSelected(true);
		} else {
			getChkWarning().setSelected(false);
		}
    this.blocked = blocked;
    this.blockedReason = blockedReason;
    if (blocked){
      getBtnBlock().setText(Messages.getString("circulation.activate")); //$NON-NLS-1$
    }else{
      getBtnBlock().setText(Messages.getString("circulation.block")); //$NON-NLS-1$
    }
    //getDuplicateTableModel().setData(duplicates);
    this.pincode = pincode;
	}
	
	/*public void loadEduLvl(List data){
		Utils.loadCombo(getCmbEduLevel(), data);
	}
	
	public void loadLanguage(List data){
		Utils.loadCombo(getCmbLanguage(), data);
	}
	
	public void loadOrganization(List data){
		Utils.loadCombo(getCmbOrg(), data);
	}
  */
  public void clear(){
    //Utils.clear(getPMain0());
    //Utils.clear(getPMain1());
    if (getTblDuplicate().getCellEditor() != null)
    	getTblDuplicate().getCellEditor().stopCellEditing();
  }
  
  public ComboBoxModel getOrgModel(){
    return getCmbOrg().getModel();
  }
  
  public ComboBoxModel getLanguageModel(){
    return getCmbLanguage().getModel();
  }
  
  public ComboBoxModel getEduLvlModel(){
    return getCmbEduLevel().getModel();
  }
  
  public ComboBoxModel getClassNoModel(){
    return getCmbClass().getModel();
  }
  
  class ColumnNameLabel extends JLabel{
    private int col;
    
    public ColumnNameLabel(int col){
      super();
      this.col = col;
      //this.setText(getDuplicateTableModel().getColumnName(col));
      this.addPropertyChangeListener("text", new PropertyChangeListener(){ //$NON-NLS-1$
        public void propertyChange(PropertyChangeEvent evt) {
          //getDuplicateTableModel().setColumnName(getCol(), getText());
        }
      });
    }
    
    public int getCol(){
      return col;
    }

  }
  
  public void handleKeys(JComponent comp, KeyEvent e){
    switch (e.getKeyCode()) {
    case KeyEvent.VK_F12:
      if (comp != tfNote)
        parent.save();
      break;
    case KeyEvent.VK_ESCAPE:
      parent.cancel();
      break;
    case KeyEvent.VK_DOWN:
      if (comp == tfFirstName)
        tfLastName.requestFocusInWindow();
      else if (comp == tfLastName)
        tfParentName.requestFocusInWindow();
      else if (comp == tfParentName)
        tfJmbg.requestFocusInWindow();
      else if (comp == tfJmbg)
        cmbDocID.requestFocusInWindow();
      else if (comp == tfDocNo)
        tfDocCity.requestFocusInWindow();
      else if (comp == tfDocCity)
        tfCountry.requestFocusInWindow();
      else if (comp == tfCountry)
        tfAddress.requestFocusInWindow();
      else if (comp == tfAddress)
        tfZip.requestFocusInWindow();
      else if (comp == tfZip)
        tfPhone.requestFocusInWindow();
      else if (comp == tfCity)
        tfPhone.requestFocusInWindow();
      else if (comp == tfPhone)
        tfEmail.requestFocusInWindow();
      else if (comp == tfEmail)
        tfFirstName.requestFocusInWindow();   
      else if (comp == tfTmpAddress)
        tfTmpZip.requestFocusInWindow();
      else if (comp == tfTmpZip)
        tfTmpPhone.requestFocusInWindow();
      else if (comp == tfTmpCity)
        tfTmpPhone.requestFocusInWindow();
      else if (comp == tfTmpPhone)
        cmbOrg.requestFocusInWindow();
      else if (comp == tfOccupation)
        tfTitle.requestFocusInWindow();
      else if (comp == tfTitle)
        tfIndexNo.requestFocusInWindow();
      else if (comp == tfIndexNo)
        cmbClass.requestFocusInWindow();
      else if (comp == tfInterests)
        tfNote.requestFocusInWindow();
      else if (comp == tfNote)
        tfTmpAddress.requestFocusInWindow();
      
      break;
    case KeyEvent.VK_UP:
      if (comp == tfFirstName)
        tfEmail.requestFocusInWindow();
      else if (comp == tfEmail)
        tfPhone.requestFocusInWindow();
      else if (comp == tfPhone)
        tfZip.requestFocusInWindow();
      else if (comp == tfZip)
        tfAddress.requestFocusInWindow();
      else if (comp == tfCity)
        tfAddress.requestFocusInWindow();
      else if (comp == tfAddress)
        tfCountry.requestFocusInWindow();
      else if (comp == tfCountry)
        tfDocCity.requestFocusInWindow();
      else if (comp == tfDocCity)
        tfDocNo.requestFocusInWindow();
      else if (comp == tfDocNo)
        cmbDocID.requestFocusInWindow();
      else if (comp == tfJmbg)
        tfParentName.requestFocusInWindow();
      else if (comp == tfParentName)
        tfLastName.requestFocusInWindow();
      else if (comp == tfLastName)
        tfFirstName.requestFocusInWindow();
      else if (comp == tfTmpAddress)
        tfNote.requestFocusInWindow();
      else if (comp == tfNote)
        tfInterests.requestFocusInWindow();
      else if (comp == tfInterests)
        cmbLanguage.requestFocusInWindow();
      else if (comp == tfIndexNo)
        tfTitle.requestFocusInWindow();
      else if (comp == tfTitle)
        tfOccupation.requestFocusInWindow();
      else if (comp == tfOccupation)
        cmbEduLevel.requestFocusInWindow();
      else if (comp == tfTmpPhone)
        tfTmpZip.requestFocusInWindow();
      else if (comp == tfTmpZip)
        tfTmpAddress.requestFocusInWindow();
      else if (comp == tfTmpCity)
        tfTmpAddress.requestFocusInWindow();
      
      break;  
  }
  }
  
  private void handleKeyTyped(){
  
    if (!parent.getDirty())
      parent.setDirty(true);
  	
  }
  
  private void handleKeyTyped(KeyEvent e){
  	System.out.println(e.getKeyCode());
  	System.out.println(KeyEvent.VK_ESCAPE);
  	if (e.getKeyCode() != KeyEvent.VK_ESCAPE){
    if (!parent.getDirty())
      parent.setDirty(true);
  	}
  }

}

