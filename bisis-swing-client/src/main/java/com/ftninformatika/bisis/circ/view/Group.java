package com.ftninformatika.bisis.circ.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.models.circ.pojo.CircLocation;
import com.ftninformatika.utils.string.StringUtils;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;

import javax.swing.ImageIcon;


public class Group extends JPanel {

	private JPanel pSouth = null;
	private JButton btnSave = null;
	private JButton btnCancel = null;
	private PanelBuilder pMain = null;
	private JComboBox cmbBranchID = null;
	private JTextField tfBranch = null;
	private JTextField tfUserID = null;
	private JButton btnAuto = null;
	private JDateChooser tfSignDate = null;
	private JTextField tfContactFirstName = null;
	private JTextField tfContactEmail = null;
	private JTextField tfOrganization = null;
	private JTextField tfFax = null;
	private JTextField tfContactLastName = null;
	private JTextField tfAddress = null;
	private JTextField tfZip = null;
	private JTextField tfCity = null;
	private JTextField tfPhone = null;
	private JTextField tfEmail = null;
	private JTextField tfTmpAddress = null;
	private JTextField tfTmpCity = null;
	private JTextField tfTmpZip = null;
	private JTextField tfTmpPhone = null;
  private ComboBoxRenderer cmbRenderer = null;
  private CmbKeySelectionManager cmbKeySelectionManager = null;

	public Group() {
		super();
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setBounds(new java.awt.Rectangle(0,0,700,400));
		this.add(getPSouth(), BorderLayout.SOUTH);
		this.add(getPMain(), BorderLayout.CENTER);
	}

	private JPanel getPSouth() {
		if (pSouth == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(50);
			pSouth = new JPanel(flowLayout);
			pSouth.add(getBtnSave(), null);
			pSouth.add(getBtnCancel(), null);
		}
		return pSouth;
	}
  
  private Group getGroup(){
    return this;
  }

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText(Messages.getString("circulation.save")); //$NON-NLS-1$
			btnSave.setIcon(new ImageIcon(getClass().getResource("/circ-images/Check16.png"))); //$NON-NLS-1$
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getUserID() == null || getUserID().equals("")){
						JOptionPane.showMessageDialog(Cirkulacija.getApp().getMainFrame(), Messages.getString("circulation.usernumberisnotvalid") , Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$
								new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
					}else {
						String message = Cirkulacija.getApp().getUserManager().saveGroup(getGroup());
			            if (message.equals("ok")){ //$NON-NLS-1$
			              JOptionPane.showMessageDialog(null, Messages.getString("circulation.ok"), Messages.getString("circulation.info"), JOptionPane.INFORMATION_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
			                new ImageIcon(getClass().getResource("/circ-images/hand32.png"))); //$NON-NLS-1$
			            }else{
			              JOptionPane.showMessageDialog(null, Messages.getString("circulation.saveerror") + message, Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
			                new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
			            }
					}
				}
			});
		}
		return btnSave;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText(Messages.getString("circulation.cancel")); //$NON-NLS-1$
			btnCancel.setIcon(new ImageIcon(getClass().getResource("/circ-images/Delete16.png"))); //$NON-NLS-1$
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
		          Cirkulacija.getApp().getUserManager().releaseGroup();
		          Cirkulacija.getApp().getMainFrame().previousPanel();
		          clear();
				}
			});
		}
		return btnCancel;
	}

	private JPanel getPMain() {
		if (pMain == null) {
			FormLayout layout = new FormLayout(
			        "2dlu:grow, right:60dlu, 3dlu, 15dlu, 2dlu, 40dlu, 7dlu, 40dlu, 35dlu, right:40dlu, 3dlu, 30dlu, 7dlu, right:20dlu, 3dlu, 60dlu, 2dlu:grow",  //$NON-NLS-1$
			        "15dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 40dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu:grow"); //$NON-NLS-1$
			CellConstraints cc = new CellConstraints();
			pMain = new PanelBuilder(layout);
			pMain.setDefaultDialogBorder();
			
			pMain.addSeparator("",cc.xyw(2,2,7)); //$NON-NLS-1$
			pMain.addLabel(Messages.getString("circulation.organizationname"), cc.xy(2,4)); //$NON-NLS-1$
			pMain.add(getTfOrganization(), cc.xyw(4,4,5));
			pMain.addSeparator(Messages.getString("circulation.usernumber"), cc.xyw(2,12,7)); //$NON-NLS-1$
			pMain.addLabel(Messages.getString("circulation.location"), cc.xy(2,14)); //$NON-NLS-1$
			pMain.add(getCmbBranchID(), cc.xyw(4,14,5));
			pMain.addLabel(Messages.getString("circulation.usernumbermandatory"), cc.xy(2,16)); //$NON-NLS-1$
			pMain.add(getTfBranch(), cc.xy(4,16));
			pMain.add(getTfUserID(), cc.xy(6,16));
			pMain.add(getBtnAuto(), cc.xy(8,16));
			pMain.addLabel(Messages.getString("circulation.registeringdate"), cc.xy(2,18)); //$NON-NLS-1$
			pMain.add(getTfSignDate(), cc.xyw(4,18,5));
			
			
			pMain.addSeparator("", cc.xyw(10,2,7)); //$NON-NLS-1$
			pMain.addLabel(Messages.getString("circulation.addressmandatory"), cc.xy(10,4)); //$NON-NLS-1$
			pMain.add(getTfAddress(), cc.xyw(12,4,5));
			pMain.addLabel(Messages.getString("circulation.zipcodemandatory"), cc.xy(10,6)); //$NON-NLS-1$
			pMain.add(getTfZip(), cc.xy(12,6));
			pMain.addLabel(Messages.getString("circulation.placemandatory"), cc.xy(14,6)); //$NON-NLS-1$
			pMain.add(getTfCity(), cc.xy(16,6));
			pMain.addLabel(Messages.getString("circulation.phone"), cc.xy(10,8)); //$NON-NLS-1$
			pMain.add(getTfPhone(), cc.xyw(12,8,5));
			pMain.addLabel(Messages.getString("circulation.fax"), cc.xy(10,10)); //$NON-NLS-1$
			pMain.add(getTfFax(), cc.xyw(12,10,5));
			pMain.addLabel(Messages.getString("circulation.email"), cc.xy(10,12)); //$NON-NLS-1$
			pMain.add(getTfEmail(), cc.xyw(12,12,5));
			
			pMain.addSeparator(Messages.getString("circulation.additionaladdress"), cc.xyw(10,20,7)); //$NON-NLS-1$
			pMain.addLabel(Messages.getString("circulation.address"), cc.xy(10,22)); //$NON-NLS-1$
			pMain.add(getTfTmpAddress(), cc.xyw(12,22,5));
			pMain.addLabel(Messages.getString("circulation.zipcode"), cc.xy(10,24)); //$NON-NLS-1$
			pMain.add(getTfTmpZip(), cc.xy(12,24));
			pMain.addLabel(Messages.getString("circulation.place"), cc.xy(14,24)); //$NON-NLS-1$
			pMain.add(getTfTmpCity(), cc.xy(16,24));
			pMain.addLabel(Messages.getString("circulation.phone"), cc.xy(10,26)); //$NON-NLS-1$
			pMain.add(getTfTmpPhone(), cc.xyw(12,26,5));
			
			pMain.addSeparator(Messages.getString("circulation.contact"), cc.xyw(2,20,7)); //$NON-NLS-1$
			pMain.addLabel(Messages.getString("circulation.firstnamemandatory"), cc.xy(2,22)); //$NON-NLS-1$
			pMain.add(getTfContactFirstName(), cc.xyw(4,22,5));
			pMain.addLabel(Messages.getString("circulation.lastnamemandatory"), cc.xy(2,24)); //$NON-NLS-1$
			pMain.add(getTfContactLastName(), cc.xyw(4,24,5));
			pMain.addLabel(Messages.getString("circulation.emailmandatory"), cc.xy(2,26)); //$NON-NLS-1$
			pMain.add(getTfContactEmail(), cc.xyw(4,26,5));
		
		}
		return pMain.getPanel();
	}

	private JComboBox getCmbBranchID() {
		if (cmbBranchID == null) {
			cmbBranchID = new JComboBox();
			cmbBranchID.setRenderer(getCmbRenderer());
			cmbBranchID.setKeySelectionManager(getCmbKeySelectionManager());
			if (!Cirkulacija.getApp().getEnvironment().getUseridPrefix())
		        cmbBranchID.setEnabled(false);
	      	cmbBranchID.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	          if (Cirkulacija.getApp().getEnvironment().getUseridPrefix()){
	            if (Utils.getCmbValue(cmbBranchID) != null){
	              getTfBranch().setText(StringUtils.padZeros(Integer.parseInt(((CircLocation)cmbBranchID.getSelectedItem()).getLocationCode()),Cirkulacija.getApp().getEnvironment().getUseridPrefixLength()));
	              getTfUserID().setText(""); //$NON-NLS-1$
	            }
	          }
	        }
	      });
		}
		return cmbBranchID;
	}
  
  private CmbKeySelectionManager getCmbKeySelectionManager(){
    if (cmbKeySelectionManager == null){
      cmbKeySelectionManager = new CmbKeySelectionManager();
    }
    return cmbKeySelectionManager;
  }
  
  private ComboBoxRenderer getCmbRenderer(){
    if (cmbRenderer == null){
      cmbRenderer = new ComboBoxRenderer();
    }
    return cmbRenderer;
  }

	private JTextField getTfBranch() {
		if (tfBranch == null) {
			tfBranch = new JTextField();
			tfBranch.setEditable(false);
		}
		return tfBranch;
	}

	private JTextField getTfUserID() {
		if (tfUserID == null) {
			tfUserID = new JTextField();
		}
		return tfUserID;
	}

	private JButton getBtnAuto() {
		if (btnAuto == null) {
			btnAuto = new JButton();
			btnAuto.setText(Messages.getString("circulation.auto")); //$NON-NLS-1$
			btnAuto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getTfUserID().getText().equals("")){ //$NON-NLS-1$
						String id = Cirkulacija.getApp().getUserManager().getUserId(getTfBranch().getText());
						if (id != null){
							getTfUserID().setText(id);
						} else {
							JOptionPane.showMessageDialog(null, "Gre\u0161ka pri konekciji sa bazom podataka!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		}
		return btnAuto;
	}

	private JDateChooser getTfSignDate() {
		if (tfSignDate == null) {
			tfSignDate = new JDateChooser();
		}
		return tfSignDate;
	}

	private JTextField getTfContactFirstName() {
		if (tfContactFirstName == null) {
			tfContactFirstName = new JTextField();
		}
		return tfContactFirstName;
	}

	private JTextField getTfOrganization() {
		if (tfOrganization == null) {
			tfOrganization = new JTextField();
		}
		return tfOrganization;
	}

	private JTextField getTfFax() {
		if (tfFax == null) {
			tfFax = new JTextField();
		}
		return tfFax;
	}

	private JTextField getTfContactLastName() {
		if (tfContactLastName == null) {
			tfContactLastName = new JTextField();
		}
		return tfContactLastName;
	}
	
	private JTextField getTfContactEmail() {
		if (tfContactEmail == null) {
			tfContactEmail = new JTextField();
		}
		return tfContactEmail;
	}

	private JTextField getTfAddress() {
		if (tfAddress == null) {
			tfAddress = new JTextField();
		}
		return tfAddress;
	}

	private JTextField getTfZip() {
		if (tfZip == null) {
			tfZip = new JTextField();
		}
		return tfZip;
	}

	private JTextField getTfCity() {
		if (tfCity == null) {
			tfCity = new JTextField();
		}
		return tfCity;
	}

	private JTextField getTfPhone() {
		if (tfPhone == null) {
			tfPhone = new JTextField();
		}
		return tfPhone;
	}

	private JTextField getTfEmail() {
		if (tfEmail == null) {
			tfEmail = new JTextField();
		}
		return tfEmail;
	}

	private JTextField getTfTmpAddress() {
		if (tfTmpAddress == null) {
			tfTmpAddress = new JTextField();
		}
		return tfTmpAddress;
	}

	private JTextField getTfTmpCity() {
		if (tfTmpCity == null) {
			tfTmpCity = new JTextField();
		}
		return tfTmpCity;
	}

	private JTextField getTfTmpZip() {
		if (tfTmpZip == null) {
			tfTmpZip = new JTextField();
		}
		return tfTmpZip;
	}

	private JTextField getTfTmpPhone() {
		if (tfTmpPhone == null) {
			tfTmpPhone = new JTextField();
		}
		return tfTmpPhone;
	}
  
  public String getUserID(){ 
	  return Utils.makeUserId(getTfBranch().getText().trim(), getTfUserID().getText().trim()); 
  }
  
  public Date getSignDate() {
    return getTfSignDate().getDate();
  }

  public String getContactFirstName() {
    return getTfContactFirstName().getText();
  }

  public String getOrganization() {
    return getTfOrganization().getText();
  }

  public String getFax() {
    return getTfFax().getText();
  }

  public String getContactLastName() {
    return getTfContactLastName().getText();
  }
  
  public String getContactEmail() {
    return getTfContactEmail().getText();
  }

  public String getAddress() {
    return getTfAddress().getText();
  }

  public String getZip() {
    return getTfZip().getText();
  }

  public String getCity() {
    return getTfCity().getText();
  }

  public String getPhone() {
    return getTfPhone().getText();
  }

  public String getEmail() {
    return getTfEmail().getText();
  }

  public String getTmpAddress() {
    return getTfTmpAddress().getText();
  }

  public String getTmpCity() {
    return getTfTmpCity().getText();
  }

  public String getTmpZip() {
    return getTfTmpZip().getText();
  }

  public String getTmpPhone() {
    return getTfTmpPhone().getText();
  }
  
  
	
	public void loadDefault(){
		if (Cirkulacija.getApp().getEnvironment().getUseridPrefix()){
	      int loc =  Cirkulacija.getApp().getEnvironment().getUseridDefaultPrefix();
	      for (int i = 1; i < getCmbBranchID().getModel().getSize(); i++) {
	        if (Integer.parseInt(((CircLocation)getCmbBranchID().getModel().getElementAt(i)).getLocationCode()) == loc) {
	          getCmbBranchID().setSelectedIndex(i);
	        }
	      } 
	    }
	}
  
  public void loadGroup(String userID, String instName, Date signDate, String address, String city, String zip,
      String phone, String email, String fax, String tmpAddress, String tmpZip, String tmpCity,
      String tmpPhone, String contFname, String contLname, String contEmail){
	  
	  if (userID.length() == Cirkulacija.getApp().getEnvironment().getUseridLength() && Cirkulacija.getApp().getEnvironment().getUseridPrefix()){
  		getTfBranch().setText(userID.substring(0,Cirkulacija.getApp().getEnvironment().getUseridPrefixLength()));
  		int loc = Integer.parseInt(getTfBranch().getText());
  		for (int i = 1; i < getCmbBranchID().getModel().getSize(); i++) {
  			if (Integer.parseInt(((CircLocation)getCmbBranchID().getModel().getElementAt(i)).getLocationCode()) == loc) {
  				getCmbBranchID().setSelectedIndex(i);
  			}
  		} 
  			getTfUserID().setText(userID.substring(Cirkulacija.getApp().getEnvironment().getUseridPrefixLength()));
	  } else {
		  getTfUserID().setText(userID);
	  }
	    getTfOrganization().setText(instName);
	    getTfSignDate().setDate(signDate);
	    getTfAddress().setText(address);
	    getTfCity().setText(city);
	    getTfZip().setText(zip);
	    getTfPhone().setText(phone);
	    getTfEmail().setText(email);
	    getTfFax().setText(fax);
	    getTfTmpAddress().setText(tmpAddress);
	    getTfTmpZip().setText(tmpZip);
	    getTfTmpCity().setText(tmpCity);
	    getTfTmpPhone().setText(tmpPhone);
	    getTfContactFirstName().setText(contFname);
	    getTfContactLastName().setText(contLname);
	    getTfContactEmail().setText(contEmail);
  }
  
  public void clear(){
    Utils.clear(getPMain());
  }
  
  public void loadBranchID(List data){
    Utils.loadCombo(getCmbBranchID(), data); 
  }

}
