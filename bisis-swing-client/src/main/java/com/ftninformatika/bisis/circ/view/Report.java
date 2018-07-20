package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.circ.report.*;
import com.ftninformatika.utils.Messages;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.common.Utils;

import com.ftninformatika.bisis.circ.pojo.CircLocation;
import com.ftninformatika.bisis.circ.validator.Validator;
import com.ftninformatika.utils.date.DateUtils;
import net.sf.jasperreports.engine.JasperPrint;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;

public class Report {

	private JComboBox cmbReport = null;
	private JComboBox cmbLocation = null;
	private JDateChooser tfStartDate = null;
	private JDateChooser tfEndDate = null;
	private JButton btnSearch = null;
	private JButton btnCancel = null;
	private PanelBuilder pb = null;
	private JPanel buttonPanel = null;
	private JLabel lName = null;
	private JLabel lTfCmb = null;
	private JTextField tfNumber = null;
	private JComboBox cmbGroup = null;
	private CmbKeySelectionManager cmbKeySelectionManager = null;
	private ComboBoxRenderer cmbRenderer = null;
	private List<String> listReports = new Vector<String>();


	public Report() {
		makePanel();
	}

	private void makePanel() {

		FormLayout layout = new FormLayout(
				"2dlu:grow, right:80dlu, 5dlu, 70dlu, 10dlu, 70dlu, 5dlu, 30dlu, 50dlu, 2dlu:grow",
				"30dlu, pref, 20dlu, pref, 20dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref, 30dlu, pref, 2dlu:grow");
		CellConstraints cc = new CellConstraints();
		pb = new PanelBuilder(layout);
		pb.setDefaultDialogBorder();

		pb.add(getCmbReport(), cc.xyw(4, 2, 3));
		pb.add(getLName(), cc.xyw(2, 4, 8, "center, center"));

		pb.addSeparator("", cc.xyw(4, 6, 3));
		pb.add(getLTfCmb(), cc.xy(2, 8));
		pb.add(getTfNumber(), cc.xyw(4, 8, 3));
		pb.add(getCmbGroup(), cc.xyw(4, 8, 3));
		pb.addLabel(Messages.getString("circulation.date"), cc.xy(2, 10));
		pb.add(getTfStartDate(), cc.xy(4, 10));
		pb.addLabel("-", cc.xy(5, 10, "center, center"));
		pb.add(getTfEndDate(), cc.xy(6, 10));

		pb.addLabel(Messages.getString("circulation.location"), cc.xy(2, 12));
		pb.add(getCmbLocation(), cc.xyw(4, 12, 3));

		pb.add(getButtonPanel(), cc.xyw(2, 14, 8));
	}

	public JPanel getPanel() {
		return pb.getPanel();
	}
	private List init() {
		listReports.add(Messages.getString("circulation.librarianbook"));
		listReports.add(Messages.getString("circulation.catbook"));
		listReports.add(Messages.getString("circulation.signtypebook"));
		listReports.add(Messages.getString("circulation.structsignedmembers"));
		listReports.add(Messages.getString("circulation.visitorsstructure"));
		listReports.add(Messages.getString("circulation.visitors"));
		listReports.add(Messages.getString("circulation.zbreport"));
		listReports.add(Messages.getString("circulation.bestreaders"));
		listReports.add(Messages.getString("circulation.blockedmembers"));
		listReports.add(Messages.getString("circulation.colectivemembers"));
		listReports.add(Messages.getString("circulation.signedbook"));
		listReports.add(Messages.getString("circulation.individualbycollective"));
		listReports.add(Messages.getString("circulation.memberhistory"));
		listReports.add(Messages.getString("circulation.bookcard"));
		listReports.add(Messages.getString("circulation.lendbyudc"));
		listReports.add(Messages.getString("circulation.bestbooks"));
		listReports.add(Messages.getString("circulation.besbooksudc"));
		listReports.add(Messages.getString("circulation.lendreturnudc"));
		listReports.add(Messages.getString("circulation.lendreturnlan"));
		listReports.add(Messages.getString("circulation.picturebooksreport"));
		listReports.add(Messages.getString("circulation.librarianstatistic"));
		return listReports;
	}
	private JComboBox getCmbReport() {
		if (cmbReport == null) {
			cmbReport = new JComboBox();
			cmbReport.setRenderer(getCmbRenderer());
			cmbReport.setKeySelectionManager(getCmbKeySelectionManager());
			Utils.loadCombo(cmbReport, init());
			cmbReport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					comboChange(e);
				}
			});
		}

		return cmbReport;
	}
	void comboChange(ActionEvent e) {
		int value = cmbReport.getSelectedIndex();
		switch (value) {
			case 1 :
				getLName().setText(Messages.getString("circulation.case1"));
				getTfNumber().setVisible(false);
				getTfStartDate().setVisible(true);
				getTfEndDate().setVisible(false);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;

			case 2 :
				getLName().setText(Messages.getString("circulation.case2"));
				getTfNumber().setVisible(false);
				getTfStartDate().setVisible(true);
				getTfEndDate().setVisible(false);
				getLTfCmb().setVisible(false);
				 getCmbGroup().setVisible(false);
				break;

			case 3 :
				getLName().setText(Messages.getString("circulation.case3"));
				getTfStartDate().setVisible(true);
				getTfNumber().setVisible(false);
				getTfEndDate().setVisible(false);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;
				
			case 4 :
				getLName().setText(Messages.getString("circulation.case4"));
				getTfNumber().setVisible(false);
				getTfStartDate().setVisible(true);
				getTfEndDate().setVisible(true);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;
				
			case 5 :
				getLName().setText(Messages.getString("circulation.case5"));
				getTfNumber().setVisible(false);
				getTfStartDate().setVisible(true);
				getTfEndDate().setVisible(true);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;
			
			case 6 :
				getLName().setText(Messages.getString("circulation.case6"));
				getTfNumber().setVisible(false);
				getTfStartDate().setVisible(true);
				getTfEndDate().setVisible(false);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;
				
			case 7 :
				getLName().setText(Messages.getString("circulation.case7"));
				getTfNumber().setVisible(false);
				getTfStartDate().setVisible(true);
				getTfEndDate().setVisible(true);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;
			
			case 8 :
				getLName().setText(Messages.getString("circulation.case8"));
				getTfNumber().setVisible(false);
				getTfStartDate().setVisible(true);
				getTfEndDate().setVisible(true);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;
				
			case 9 :
				getLName().setText(Messages.getString("circulation.case9"));
				getTfNumber().setVisible(false);
				getTfEndDate().setVisible(false);
				getTfStartDate().setVisible(false);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;
			case 10 :
				getLName().setText(Messages.getString("circulation.case10"));
				getTfNumber().setVisible(false);
				getTfEndDate().setVisible(false);
				getTfStartDate().setVisible(false);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;
			case 11 :
				getLName().setText(Messages.getString("circulation.case11"));
				getTfNumber().setVisible(false);
				getTfEndDate().setVisible(true);
				getTfStartDate().setVisible(true);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;
			case 12 :
				getLName().setText(Messages.getString("circulation.case12"));
				getTfNumber().setVisible(false);
				getTfEndDate().setVisible(true);
				getTfStartDate().setVisible(true);
				getLTfCmb().setText(Messages.getString("circulation.group"));
				getLTfCmb().setVisible(true);
				setCmbModel();
		        getCmbGroup().setVisible(true);
				break;
			case 13 :
				getLName().setText(Messages.getString("circulation.case13"));
				getTfNumber().setText("");
				getTfNumber().setVisible(true);
				getTfEndDate().setVisible(true);
				getTfStartDate().setVisible(true);
				getLTfCmb().setText(Messages.getString("circulation.membernum"));
				getLTfCmb().setVisible(true);
				getCmbGroup().setVisible(false);
				break;
			case 14 :
				getLName().setText(Messages.getString("circulation.case14"));
				getTfNumber().setText("");
				getTfNumber().setVisible(true);
				getTfEndDate().setVisible(true);
				getTfStartDate().setVisible(true);
				getLTfCmb().setText(Messages.getString("circulation.invnum"));
				getLTfCmb().setVisible(true);
				getCmbGroup().setVisible(false);
				break;
			case 15 :
				getLName().setText(Messages.getString("circulation.case15"));
				getTfNumber().setVisible(false);
				getTfEndDate().setVisible(true);
				getTfStartDate().setVisible(true);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;
				
			case 16:
				getLName().setText(Messages.getString("circulation.case16"));
				getTfNumber().setVisible(false);
				getTfEndDate().setVisible(true);
				getTfStartDate().setVisible(true);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;
			case 17:
				getLName().setText(Messages.getString("circulation.case17"));
				getTfNumber().setText("");
				getTfNumber().setVisible(true);
				getTfEndDate().setVisible(true);
				getTfStartDate().setVisible(true);
				getLTfCmb().setText(Messages.getString("circulation.udc"));
				getLTfCmb().setVisible(true);
				getCmbGroup().setVisible(false);
				break;
			case 18:
				getLName().setText(Messages.getString("circulation.case18"));
				getTfNumber().setVisible(false);
				getTfEndDate().setVisible(true);
				getTfStartDate().setVisible(true);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;
			case 19:
				getLName().setText(Messages.getString("circulation.case19"));
				getTfNumber().setVisible(false);
				getTfEndDate().setVisible(true);
				getTfStartDate().setVisible(true);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;
			case 20:
				getLName().setText(Messages.getString("circulation.case20"));
				getTfNumber().setVisible(false);
				getTfEndDate().setVisible(true);
				getTfStartDate().setVisible(true);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;
			case 21:
				getLName().setText(Messages.getString("circulation.case21"));
				getTfNumber().setVisible(false);
				getTfEndDate().setVisible(true);
				getTfStartDate().setVisible(true);
				getLTfCmb().setVisible(false);
				getCmbGroup().setVisible(false);
				break;
			default :

		}

	}
	private JComboBox getCmbLocation() {
		if (cmbLocation == null) {
			cmbLocation = new JComboBox();
			cmbLocation.setFocusable(false);
			cmbLocation.setRenderer(getCmbRenderer());
			cmbLocation.setKeySelectionManager(getCmbKeySelectionManager());
		}
		return cmbLocation;
	}
  
  public void loadCmbLocation(List data){
    Utils.loadCombo(getCmbLocation(), data);
  }

	private CmbKeySelectionManager getCmbKeySelectionManager() {
		if (cmbKeySelectionManager == null) {
			cmbKeySelectionManager = new CmbKeySelectionManager();
		}
		return cmbKeySelectionManager;
	}

	private ComboBoxRenderer getCmbRenderer() {
		if (cmbRenderer == null) {
			cmbRenderer = new ComboBoxRenderer();
		}
		return cmbRenderer;
	}

	private JComboBox getCmbGroup() {
		if (cmbGroup == null) {
			cmbGroup = new JComboBox();
			cmbGroup.setRenderer(getCmbRenderer());
			cmbGroup.setKeySelectionManager(getCmbKeySelectionManager());
			cmbGroup.setVisible(false);
		}
		return cmbGroup;
	}

	// pri prvom prikazivanju comboboxa pozvati setCmbModel()
	private void setCmbModel() {
		getCmbGroup().setModel(
				Cirkulacija.getApp().getMainFrame().getUserPanel()
						.getMmbrship().getGroupsModel());
		
	}

	private JTextField getTfNumber() {
		if (tfNumber == null) {
			tfNumber = new JTextField();
		}
		return tfNumber;
	}

	private JDateChooser getTfStartDate() {
		if (tfStartDate == null) {
			tfStartDate = new JDateChooser();
		}
		return tfStartDate;
	}

	private JDateChooser getTfEndDate() {
		if (tfEndDate == null) {
			tfEndDate = new JDateChooser();
		}
		return tfEndDate;
	}

	private boolean isValidDateRange(){
		if (tfStartDate.isVisible() && tfEndDate.isVisible() && cmbReport.getSelectedIndex() != 14 && cmbReport.getSelectedIndex() != 13) {
			if (!DateUtils.inCircReportDateRange(tfStartDate.getDate(), tfEndDate.getDate())){
				JOptionPane.showMessageDialog(null,
						Messages.getString("circulation.wrongdates"),
						Messages.getString("circulation.error"),
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText(Messages.getString("circulation.search"));
			btnSearch.setIcon(new ImageIcon(getClass().getResource(
					"/circ-images/find16.png")));
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (isValidDateRange()) {
						try {
							int value = cmbReport.getSelectedIndex();

							switch (value) {
								case 1:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(Librarian.setPrint(getTfStartDate().getDate(), getCmbLocation().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 2:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(UserCategSigning.setPrint(getTfStartDate().getDate(), Utils.getCmbValue(getCmbLocation().getSelectedItem())));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 3:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(MmbrType.setPrint(getTfStartDate().getDate(), getCmbLocation().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 4:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(Structure.setPrint(getTfStartDate().getDate(), getTfEndDate().getDate(), getCmbLocation().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 5:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(VisitorStructure.setPrint(getTfStartDate().getDate(), getTfEndDate().getDate(), getCmbLocation().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 6:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(Visitors.setPrint(getTfStartDate().getDate(), getCmbLocation().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 7:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(ZbStatistic.setPrint(getTfStartDate().getDate(), getTfEndDate().getDate(), getCmbLocation().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 8:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(BestReader.setPrint(getTfStartDate().getDate(), getTfEndDate().getDate(), getCmbLocation().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 9:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(Blocked.setPrint(getCmbLocation().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 10:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(GroupsReport.setPrint(getCmbLocation().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 11:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(MemberBook.setPrint(getTfStartDate().getDate(), getTfEndDate().getDate(), getCmbLocation().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 12:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(MemberByGroup.setPrint(getTfStartDate().getDate(), getTfEndDate().getDate(), getCmbLocation().getSelectedItem(), getCmbGroup().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 13:
									String userid = Validator.convertUserId2DB(getTfNumber().getText());
									if (!userid.equals("")) {
										JasperPrint jp = MemberHistory.setPrint(userid, getTfStartDate().getDate(), getTfEndDate().getDate(), Utils.getCmbValue(getCmbLocation().getSelectedItem()));
										if (jp != null) {
											Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(jp);
											Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
										} else {
											JOptionPane.showMessageDialog(Cirkulacija.getApp().getMainFrame().getReport().getPanel(), Messages.getString("circulation.memberdoesntexist"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE,
													new ImageIcon(getClass().getResource("/circ-images/x32.png")));
										}
									} else {
										JOptionPane.showMessageDialog(Cirkulacija.getApp().getMainFrame().getReport().getPanel(), Messages.getString("circulation.membernuminvalid"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE,
												new ImageIcon(getClass().getResource("/circ-images/x32.png")));
									}
									break;
								case 14:
									String ctlgno = Validator.convertCtlgNo2DB(getTfNumber().getText().trim());
									if (!ctlgno.equals("")) {
										Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(BookHistory.setPrint(ctlgno, getTfStartDate().getDate(), getTfEndDate().getDate(), getCmbLocation().getSelectedItem()));
										Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									} else {
										JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.invnumnotvalid"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE,
												new ImageIcon(getClass().getResource("/circ-images/x32.png")));
									}
									break;
								case 15:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(CtgrUdk.setPrint(getTfStartDate().getDate(), getTfEndDate().getDate(), getCmbLocation().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 16:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(BestBook.setPrint(getTfStartDate().getDate(), getTfEndDate().getDate(), getCmbLocation().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 17:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(BestBookUdk.setPrint(getTfStartDate().getDate(), getTfEndDate().getDate(), getCmbLocation().getSelectedItem(), getTfNumber().getText()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 18:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(LendReturn.setPrint(getTfStartDate().getDate(), getTfEndDate().getDate(), getCmbLocation().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 19:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(LendReturnLanguage.setPrint(getTfStartDate().getDate(), getTfEndDate().getDate(), getCmbLocation().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 20:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(com.ftninformatika.bisis.circ.report.Picturebooks.setPrint(getTfStartDate().getDate(), getTfEndDate().getDate()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								case 21:
									Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(LibrarianStatistic.setPrint(getTfStartDate().getDate(), getTfEndDate().getDate(), getCmbLocation().getSelectedItem()));
									Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel");
									break;
								default:
									JOptionPane.showMessageDialog(null, Messages.getString("circulation.nodataentered"),
											Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE);

							}
							//clear();

						} catch (Exception exc1) {
							exc1.printStackTrace();
							JOptionPane.showMessageDialog(null, Messages.getString("circulation.error"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE);
						}
				}
				}
			});
				
		}
		return btnSearch;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText(Messages.getString("circulation.cancel"));
			btnCancel.setIcon(new ImageIcon(getClass().getResource(
					"/circ-images/Delete16.png")));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Cirkulacija.getApp().getMainFrame().previousPanel();
					clear();
				}
			});
		}
		return btnCancel;
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

	private JLabel getLName() {
		if (lName == null) {
			lName = new JLabel();
			lName.setForeground(Color.blue);
			lName.setText(Messages.getString("circulation.fullreportname"));
		}
		return lName;
	}

	private JLabel getLTfCmb() {
		if (lTfCmb == null) {
			lTfCmb = new JLabel();
			lTfCmb.setText(Messages.getString("circulation.partnumber"));
		}
		return lTfCmb;
	}

    private void clear(){
    	getLName().setText(" ");
		getTfNumber().setVisible(false);
		getTfStartDate().setVisible(true);
		getTfEndDate().setVisible(true);
		getLTfCmb().setVisible(false);
		getCmbGroup().setVisible(false);
		Utils.clear(getPanel());
    }
    
    public void selectMemberHistory(String userId){
      getCmbReport().setSelectedIndex(13);
      getTfNumber().setText(userId);
    }
}
