package com.ftninformatika.bisis.circ.view;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import com.ftninformatika.bisis.circ.Cirkulacija;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class SearchUsersResults {

	private JLabel lUserNum= new JLabel();
	private JScrollPane scrollPane = null;
	private JTable tblResults = null;
	private JButton btnShow = null;
	private JButton btnCancel = null;
	private JButton btnPrint = null;
	private PanelBuilder pb = null;
	private JPanel buttonPanel = null;
	private SearchUsersTableModel  suser = null;
  private String query;

	public SearchUsersResults() {
		makePanel();
	}
	
	
	public JPanel getPanel(){
		return pb.getPanel();
	}

	private void makePanel() {
		FormLayout layout = new FormLayout(
		        "2dlu, 100dlu:grow, 2dlu",  //$NON-NLS-1$
		        "10dlu, pref, 2dlu, pref, 5dlu, 20dlu:grow, 5dlu, pref, 5dlu"); //$NON-NLS-1$
		CellConstraints cc = new CellConstraints();
		pb = new PanelBuilder(layout);
		pb.setDefaultDialogBorder();
		pb.add(lUserNum,cc.xy(2,4));
		pb.add(getScrollPane(),cc.xy(2,6, "fill, fill")); //$NON-NLS-1$
		pb.add(getButtonPanel(),cc.xy(2,8));
		
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTblResults());
		}
		return scrollPane;
	}

	public JTable getTblResults() {
		if (tblResults == null) {
			tblResults = new JTable(getSearchUsersTableModel());
      tblResults.setAutoCreateRowSorter(true);
      tblResults.addMouseListener(new MouseAdapter(){
      	public void mouseClicked(MouseEvent e) {
          if(e.getClickCount()==2){
            getBtnShow().doClick();
          }       
        }  
      });
		}
		return tblResults;
	}
	public void setQuery(String str){
		query=str;
	}
  
	public String getQuery(){
		return query;
	}
	
  public SearchUsersTableModel getSearchUsersTableModel() {
		if (suser == null) {
			suser = new SearchUsersTableModel();
		}
		return suser;
  }
    

  private JButton getBtnShow() {
  	if (btnShow == null) {
  		btnShow = new JButton();
  		btnShow.setText(Messages.getString("circulation.show")); //$NON-NLS-1$
  		btnShow.setIcon(new ImageIcon(getClass().getResource("/circ-images/user16.png"))); //$NON-NLS-1$
  		btnShow.addActionListener(new java.awt.event.ActionListener() {
  			public void actionPerformed(java.awt.event.ActionEvent e) {
  				if(getTblResults().getSelectedRow()!=-1){
  				  String userID = getSearchUsersTableModel().getUser(getTblResults().convertRowIndexToModel(getTblResults().getSelectedRow()));
  				  Cirkulacija.getApp().getUserManager().showUser(Cirkulacija.getApp().getMainFrame().getUserPanel(), userID);
  				  if (Cirkulacija.getApp().getMainFrame().getRequestedPanel() == 3){
  				  	Cirkulacija.getApp().getMainFrame().getUserPanel().showLending();
  				  } else {
  				  	Cirkulacija.getApp().getMainFrame().getUserPanel().showData();
  				  }
            Cirkulacija.getApp().getMainFrame().showPanel("userPanel"); //$NON-NLS-1$
  				}
  			}
      });
  	}
  	return btnShow;
  }

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText(Messages.getString("circulation.cancel")); //$NON-NLS-1$
			btnCancel.setIcon(new ImageIcon(getClass().getResource("/circ-images/Delete16.png"))); //$NON-NLS-1$
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getSearchUsersTableModel().removeAll();
					Cirkulacija.getApp().getMainFrame().previousPanel();
				}
			});
		}
		return btnCancel;
	}
	
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText(Messages.getString("circulation.print")); //$NON-NLS-1$
			btnPrint.setFocusable(false);
			btnPrint.setIcon(new ImageIcon(getClass().getResource("/circ-images/print16.png"))); //$NON-NLS-1$
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					Cirkulacija.getApp().getMainFrame().getReportResults().setJasper(SearchReport.setPrint(getSearchUsersTableModel(),getQuery()));
//					Cirkulacija.getApp().getMainFrame().showPanel("reportResultsPanel"); //$NON-NLS-1$
				}
			});
		}
		return btnPrint;
	}
	
	
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(30);
			buttonPanel = new JPanel(flowLayout);
			buttonPanel.add(getBtnShow());
			buttonPanel.add(getBtnPrint());
			buttonPanel.add(getBtnCancel());
		}
		return buttonPanel;
	}
  
	public void setResult(List l, String q){
		getSearchUsersTableModel().removeAll();
		setQuery(q);
		if (l != null){
			lUserNum.setText(Messages.getString("circulation.hitsnumber")+l.size()); //$NON-NLS-1$
			getSearchUsersTableModel().setData(l);
		} else {
			lUserNum.setText(Messages.getString("circulation.hitsnumberzero")); //$NON-NLS-1$
		}
	}


}
