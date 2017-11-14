package com.ftninformatika.bisis.circ.warnings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;

import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.manager.WarningsManager;
import com.ftninformatika.bisis.circ.view.CmbKeySelectionManager;
import com.ftninformatika.bisis.circ.view.ComboBoxRenderer;
import com.ftninformatika.bisis.circ.view.RecordBean;
import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.WarningType;
import com.ftninformatika.bisis.circ.pojo.CircLocation;
import com.ftninformatika.bisis.circ.pojo.Warning;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.string.StringUtils;
import com.ftninformatika.utils.xml.XMLUtils;
import net.sf.jasperreports.swing.JRViewer;
import org.apache.xmlbeans.XmlOptions;

import com.toedter.calendar.JDateChooser;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import warning.RootDocument;
import javax.swing.JCheckBox;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;


public class WarningsFrame extends JInternalFrame {

	private JPanel jContentPane = null;
	private JPanel mainPanel = null;
	private JPanel jBottom = null;
	private JButton btnPrev = null;
	private JButton btnNext = null;
	private JPanel firstPanel = null;
	private JPanel listPanel = null;
	private JScrollPane jScrollPane = null;
	private JTable tbList = null;
	private JLabel lType = null;
	private JComboBox cmbType = null;
	private JLabel lBranch = null;
	private JComboBox cmbBranch = null;
	private JLabel lBegDate = null;
	private JDateChooser tfBegDate = null;
	private JLabel lEndDate = null;
	private JDateChooser tfEndDate = null;
	private JButton btnEdit = null;
	private CardLayout mCardLayout = null;
	private WarningTableModel warningTableModel = null;
	private WarningFrameTableModel warningFrameTableModel = null;
	private int visPanel = 1;
	private RootDocument doc = null;
	private JPanel reportPanel = null;
	private JCheckBox chbSave = null;
	private JLabel lSave = null;
	private JPanel reportListPanel = null;
	private JPanel changePanel = null;
	private ChangeTemplate changeTemplate = null;
	private JButton btnHistory = null;
	private JPanel historyPanel = null;
	private JScrollPane historyScrollPane = null;
	private JTable tbHistory = null;
	private HistoryTableModel historyTableModel = null;
	private Counters counters = null;
	private ComboBoxRenderer cmbRenderer = null;
	private CmbKeySelectionManager cmbKeySelectionManager = null;
	private WarningsManager manager = null;
	private List<MemberData> users = null;
	private List<Warning> warn_to_save = null;
	
	
	public WarningsFrame() {
		super("Opomene", true, true, true, true);
		initialize();
		try {
		getManager().loadCombos(this);
		} catch (Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage(), "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void initialize() {
		this.setSize(new java.awt.Dimension(780,550));
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	    this.addInternalFrameListener(new InternalFrameAdapter(){
	      public void internalFrameClosing(InternalFrameEvent e){
	        handleClose();
	      }
	    });   
		Dimension screen = getToolkit().getScreenSize();
	    this.setLocation((screen.width - getSize().width) / 2,
	        (screen.height - getSize().height) / 2);
	}

  private WarningsManager getManager(){
    if (manager == null){
      manager = new WarningsManager();
    }
    return manager;
  }
  
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setPreferredSize(new java.awt.Dimension(780,550));
			jContentPane.add(getMainPanel(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJBottom(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	private JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanel();
			mCardLayout = new CardLayout();
			mainPanel.setLayout(mCardLayout);
			mainPanel.setPreferredSize(new java.awt.Dimension(780,510));
			mainPanel.add(getFirstPanel(), getFirstPanel().getName());
			mainPanel.add(getListPanel(), getListPanel().getName());
			mainPanel.add(getReportPanel(), getReportPanel().getName());
			mainPanel.add(getReportListPanel(), getReportListPanel().getName());
			mainPanel.add(getChangePanel(), getChangePanel().getName());
			mainPanel.add(getHistoryPanel(), getHistoryPanel().getName());
		}
		return mainPanel;
	}

	private JPanel getJBottom() {
		if (jBottom == null) {
			FlowLayout flowLayout2 = new FlowLayout();
			jBottom = new JPanel();
			jBottom.setLayout(flowLayout2);
			jBottom.setPreferredSize(new java.awt.Dimension(780,40));
			flowLayout2.setVgap(5);
			flowLayout2.setHgap(200);
			jBottom.add(getBtnPrev(), null);
			jBottom.add(getBtnNext(), null);
		}
		return jBottom;
	}

	private JButton getBtnPrev() {
		if (btnPrev == null) {
			btnPrev = new JButton();
			btnPrev.setText("<< Nazad");
			btnPrev.setEnabled(false);
			btnPrev.setPreferredSize(new java.awt.Dimension(180,26));
			btnPrev.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					handleBtnPrev();
				}
			});
		}
		return btnPrev;
	}

	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("Prika\u017ei korisnike >>");
			btnNext.setPreferredSize(new java.awt.Dimension(180,26));
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					handleBtnNext();
				}
			});
		}
		return btnNext;
	}

	private JPanel getFirstPanel() {
		if (firstPanel == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 2;
			gridBagConstraints11.insets = new java.awt.Insets(10,10,10,100);
			gridBagConstraints11.gridy = 2;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 2;
			gridBagConstraints8.insets = new java.awt.Insets(50,10,10,100);
			gridBagConstraints8.gridy = 0;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints7.gridy = 3;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.insets = new java.awt.Insets(10,0,50,200);
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints6.insets = new java.awt.Insets(10,50,50,10);
			gridBagConstraints6.gridy = 3;
			lEndDate = new JLabel();
			lEndDate.setText("Do datuma");
			lEndDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.insets = new java.awt.Insets(10,0,10,200);
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints4.insets = new java.awt.Insets(10,50,10,10);
			gridBagConstraints4.gridy = 2;
			lBegDate = new JLabel();
			lBegDate.setText("Od datuma");
			lBegDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.insets = new java.awt.Insets(10,0,10,100);
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints2.insets = new java.awt.Insets(10,50,10,10);
			gridBagConstraints2.gridy = 1;
			lBranch = new JLabel();
			lBranch.setText("Ogranak");
			lBranch.setHorizontalTextPosition(javax.swing.SwingConstants.TRAILING);
			lBranch.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.insets = new java.awt.Insets(50,0,10,100);
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints.insets = new java.awt.Insets(50,50,10,10);
			gridBagConstraints.gridy = 0;
			lType = new JLabel();
			lType.setText("Vrsta opomene");
			lType.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			firstPanel = new JPanel();
			firstPanel.setLayout(new GridBagLayout());
			firstPanel.setName("firstPanel");
			firstPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			firstPanel.setPreferredSize(new java.awt.Dimension(780,510));
			firstPanel.add(lType, gridBagConstraints);
			firstPanel.add(getCmbType(), gridBagConstraints1);
			firstPanel.add(lBranch, gridBagConstraints2);
			firstPanel.add(getCmbBranch(), gridBagConstraints3);
			firstPanel.add(lBegDate, gridBagConstraints4);
			firstPanel.add(getTfBegDate(), gridBagConstraints5);
			firstPanel.add(lEndDate, gridBagConstraints6);
			firstPanel.add(getTfEndDate(), gridBagConstraints7);
			firstPanel.add(getBtnEdit(), gridBagConstraints8);
			firstPanel.add(getBtnHistory(), gridBagConstraints11);
		}
		return firstPanel;
	}

	private JPanel getListPanel() {
		if (listPanel == null) {
			lSave = new JLabel();
			FlowLayout flowLayout1 = new FlowLayout();
			listPanel = new JPanel();
			listPanel.setLayout(flowLayout1);
			listPanel.setName("listPanel");
			listPanel.setPreferredSize(new java.awt.Dimension(780,510));
			listPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			lSave.setText("Sa\u010duvaj podatke o poslanim opomenama u bazu podataka");
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			listPanel.add(getJScrollPane(), null);
			listPanel.add(getChbSave(), null);
			listPanel.add(lSave, null);
		}
		return listPanel;
	}

	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbList());
			jScrollPane.setPreferredSize(new java.awt.Dimension(750,440));
		}
		return jScrollPane;
	}

	private JTable getTbList() {
		if (tbList == null) {
			tbList = new JTable();
			tbList.setShowGrid(true);
			tbList.setModel(getWarningFrameTableModel());
			tbList.setPreferredScrollableViewportSize(new java.awt.Dimension(750,440));
		}
		return tbList;
	}

	public JComboBox getCmbType() {
		if (cmbType == null) {
			cmbType = new JComboBox();
			cmbType.setRenderer(getCmbRenderer());
      cmbType.setKeySelectionManager(getCmbKeySelectionManager());
		}
		return cmbType;
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
  
	public JComboBox getCmbBranch() {
		if (cmbBranch == null) {
			cmbBranch = new JComboBox();
      cmbBranch.setRenderer(getCmbRenderer());
      cmbBranch.setKeySelectionManager(getCmbKeySelectionManager());
		}
		return cmbBranch;
	}

	private JDateChooser getTfBegDate() {
		if (tfBegDate == null) {
			tfBegDate = new JDateChooser();
		}
		return tfBegDate;
	}

	private JDateChooser getTfEndDate() {
		if (tfEndDate == null) {
			tfEndDate = new JDateChooser();
		}
		return tfEndDate;
	}

	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("Izmeni tekst...");
			btnEdit.setPreferredSize(new java.awt.Dimension(150,25));
			btnEdit.setMinimumSize(new java.awt.Dimension(150,25));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					handleBtnEdit();
				}
			});
		}
		return btnEdit;
	}

	private WarningTableModel getWarningTableModel() {
		if (warningTableModel == null) {
			warningTableModel = new WarningTableModel();
		}
		return warningTableModel;
	}
  
  private WarningFrameTableModel getWarningFrameTableModel() {
    if (warningFrameTableModel == null) {
      warningFrameTableModel = new WarningFrameTableModel();
    }
    return warningFrameTableModel;
  }
	
	private JPanel getReportPanel() {
		if (reportPanel == null) {
			reportPanel = new JPanel();
			reportPanel.setLayout(new BorderLayout());
			reportPanel.setName("reportPanel");
			reportPanel.setPreferredSize(new java.awt.Dimension(780,510));
			reportPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
		}
		return reportPanel;
	}
	
	private JCheckBox getChbSave() {
		if (chbSave == null) {
			chbSave = new JCheckBox();
			chbSave.setSelected(true);
		}
		return chbSave;
	}
	
	private JPanel getReportListPanel() {
		if (reportListPanel == null) {
			reportListPanel = new JPanel();
			reportListPanel.setLayout(new BorderLayout());
			reportListPanel.setName("reportListPanel");
			reportListPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			reportListPanel.setPreferredSize(new java.awt.Dimension(780,510));
		}
		return reportListPanel;
	}

	private JPanel getChangePanel() {
		if (changePanel == null) {
			changePanel = new JPanel();
			changePanel.setName("changePanel");
			changePanel.setPreferredSize(new java.awt.Dimension(780,510));
			changePanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			changePanel.add(getChangeTemplate(), null);
		}
		return changePanel;
	}
	
	private ChangeTemplate getChangeTemplate(){
		if (changeTemplate == null) {
			changeTemplate = new ChangeTemplate(getManager());
		}
		return changeTemplate;
	}

	private JButton getBtnHistory() {
		if (btnHistory == null) {
			btnHistory = new JButton();
			btnHistory.setText("Istorija...");
			btnHistory.setPreferredSize(new java.awt.Dimension(150,25));
			btnHistory.setMinimumSize(new java.awt.Dimension(150,25));
			btnHistory.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					handleBtnHistory();
				}
			});
		}
		return btnHistory;
	}
	
	private JPanel getHistoryPanel() {
		if (historyPanel == null) {
			historyPanel = new JPanel();
			historyPanel.setName("historyPanel");
			historyPanel.setPreferredSize(new java.awt.Dimension(780,510));
			historyPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			historyPanel.add(getHistoryScrollPane(), null);
		}
		return historyPanel;
	}

	private JScrollPane getHistoryScrollPane() {
		if (historyScrollPane == null) {
			historyScrollPane = new JScrollPane();
			historyScrollPane.setPreferredSize(new java.awt.Dimension(750,470));
			historyScrollPane.setViewportView(getTbHistory());
		}
		return historyScrollPane;
	}

	private JTable getTbHistory() {
		if (tbHistory == null) {
			tbHistory = new JTable(getHistoryTableModel());
      tbHistory.setAutoCreateRowSorter(true);
		}
		return tbHistory;
	}

	private HistoryTableModel getHistoryTableModel() {
		if (historyTableModel == null) {
			historyTableModel = new HistoryTableModel();
		}
		return historyTableModel;
	}
	
	
	//ponalazi korisnike kojima je rok vracanja istekao u zadatom periodu i prosledjuje ih WarningFrameTableModel-u
	private void getUsers(){
	  if ((getTfBegDate().getDate() != null || getTfEndDate().getDate() != null) && !getCmbType().getSelectedItem().equals("")){
  		CircLocation branch = (CircLocation) Utils.getCmbValue(getCmbBranch());
  		users = getManager().getUsers(getTfBegDate().getDate(), getTfEndDate().getDate(), branch);
  		getWarningFrameTableModel().setData(users, getTfBegDate().getDate(), getTfEndDate().getDate());
	  }else{
		  JOptionPane.showMessageDialog(this,"Datum i vrsta opomene nisu zadati!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
	  }
	}
  
  //pravi od liste usera doc fajl za generisanje opomena
  private RootDocument makeDoc(Date startDate, Date endDate){
    try{
      int last = 0, cur_no=0;
      RootDocument temp = null;
      RootDocument.Root.Opomena template = null;
      Calendar maxEndDate = null;
      double trostruko = 0;
      SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
      String today = sdf.format(new Date());
      boolean cyr = false;
      String wtext = "";
      int warn_no = -1;
      int cur_year = -1;
      Calendar now = Calendar.getInstance();
      Warning warning = null;
      List<Warning> warn_books = null;
      warn_to_save = new ArrayList<Warning>();
      
      if (startDate == null) {
        endDate = Utils.setMaxDate(endDate);
        startDate = Utils.setMinDate(endDate);
      } else if (endDate == null) {
        endDate = Utils.setMaxDate(startDate);
        startDate = Utils.setMinDate(startDate);
      } else {
        startDate = Utils.setMinDate(startDate);
        endDate = Utils.setMaxDate(endDate);
      }
      
      WarningType warn_type = (WarningType)getCmbType().getSelectedItem();
      wtext = warn_type.getTemplate();
  
      counters = getManager().getCounters(warn_type);
      getWarningFrameTableModel().cleanModel();
      Iterator it = users.iterator();
      
      while (it.hasNext()){
          MemberData memberData = (MemberData)it.next();
          Member user = memberData.getMember();
          List<Lending> books = memberData.getLendings();
          warn_books = new ArrayList<Warning>();
          maxEndDate = null;
          trostruko = 0;
          cur_year = -1;
          
          if (doc == null){
            temp = RootDocument.Factory.parse(wtext);
            template = temp.getRoot().getOpomenaArray(0);
            doc = RootDocument.Factory.newInstance();
            doc.addNewRoot().addNewOpomena();
            doc.getRoot().setOpomenaArray(0,template);
            doc.getRoot().setCirilica(temp.getRoot().getCirilica());
            cur_no = 0;
            if (temp.getRoot().getCirilica() == 1){
              cyr = true;
            }
          
          }else{
            cur_no = doc.getRoot().sizeOfOpomenaArray();
            doc.getRoot().addNewOpomena();
            doc.getRoot().setOpomenaArray(cur_no,template);
          }
                  
//          cur_year = maxEndDate.get(Calendar.YEAR) % 100;
//          warn_no = counters.getNext(cur_year);
//          
//          if (!doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().getBropomenetext().equals(""))
//            doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().setBropomene(warn_no + "/" +StringUtils.padZeros(cur_year,2));
//          if (!doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().getRoktext().equals(""))
//            doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().setRok(" "+ sdf.format(maxEndDate.getTime()));
          doc.getRoot().getOpomenaArray(cur_no).getBody().setDodatuma(doc.getRoot().getOpomenaArray(cur_no).getBody().getDodatuma() + " " + today);
          doc.getRoot().getOpomenaArray(cur_no).getPodaci().setPrezime(Utils.convert(user.getLastName(),cyr));
          doc.getRoot().getOpomenaArray(cur_no).getPodaci().setIme(Utils.convert(user.getFirstName(),cyr));
          if (!doc.getRoot().getOpomenaArray(cur_no).getPodaci().getRoditelj().equals("") && user.getParentName() != null)
            doc.getRoot().getOpomenaArray(cur_no).getPodaci().setImeroditelja(Utils.convert(user.getParentName(),cyr));
          doc.getRoot().getOpomenaArray(cur_no).getPodaci().setAdresa(Utils.convert(user.getAddress(),cyr));
          doc.getRoot().getOpomenaArray(cur_no).getPodaci().setZip((user.getZip() != null ? user.getZip().toString() : ""));
          doc.getRoot().getOpomenaArray(cur_no).getPodaci().setMesto(Utils.convert(user.getCity(),cyr));
          doc.getRoot().getOpomenaArray(cur_no).getPodaci().setUserid(user.getUserId());
          if (!doc.getRoot().getOpomenaArray(cur_no).getPodaci().getDocno().equals("") && user.getDocNo() != null)
            doc.getRoot().getOpomenaArray(cur_no).getPodaci().setDocno(doc.getRoot().getOpomenaArray(cur_no).getPodaci().getDocno() + user.getDocNo());
          if (!doc.getRoot().getOpomenaArray(cur_no).getPodaci().getDocmesto().equals("") && user.getDocCity() != null)
            doc.getRoot().getOpomenaArray(cur_no).getPodaci().setDocmesto(doc.getRoot().getOpomenaArray(cur_no).getPodaci().getDocmesto() + Utils.convert(user.getDocCity(),cyr));
          if (!doc.getRoot().getOpomenaArray(cur_no).getPodaci().getJmbg().equals("") && user.getJmbg() != null)
            doc.getRoot().getOpomenaArray(cur_no).getPodaci().setJmbg(doc.getRoot().getOpomenaArray(cur_no).getPodaci().getJmbg() + user.getJmbg());
                
                
                
          Iterator itbooks = books.iterator();
          
          while (itbooks.hasNext()){
            Lending book = (Lending)itbooks.next();
            
            if (book.getDeadline().compareTo(startDate) >= 0 && book.getDeadline().compareTo(endDate) <= 0){
            
              Record record = getManager().getRecord(book.getCtlgNo());
              Primerak primerak = null;
              RecordBean bean = new RecordBean(record);
              if (record != null){
                List<Primerak> primerci = record.getPrimerci();
                for (Primerak p : primerci){
                  if (p.getInvBroj().equals(book.getCtlgNo())){
                    primerak = p;
                  }
                }
              }
            
            
              Calendar deadline = Calendar.getInstance();
              deadline.setTime(book.getDeadline());
              if (maxEndDate == null || deadline.after(maxEndDate)){
                  maxEndDate =  deadline;
              }
  
              if (cur_year != maxEndDate.get(Calendar.YEAR) % 100){
                if (cur_year != -1)
                  counters.turnBack(cur_year);
                cur_year =  maxEndDate.get(Calendar.YEAR) % 100;
                warn_no = counters.getNext(cur_year);
              }
              if (!doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().getBropomenetext().equals(""))
                doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().setBropomene(warn_no + "/" +StringUtils.padZeros(cur_year,2));
              if (!doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().getRoktext().equals(""))
                doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().setRok(" "+ sdf.format(maxEndDate.getTime()));
                
              doc.getRoot().getOpomenaArray(cur_no).getBody().addNewTabela();
              last = doc.getRoot().getOpomenaArray(cur_no).getBody().getTabelaArray().length ;
              doc.getRoot().getOpomenaArray(cur_no).getBody().getTabelaArray()[last-1].setNaslov(Utils.convert(bean.getNaslov(),cyr));
              doc.getRoot().getOpomenaArray(cur_no).getBody().getTabelaArray()[last-1].setAutor(Utils.convert(bean.getAutor(),cyr));
              doc.getRoot().getOpomenaArray(cur_no).getBody().getTabelaArray()[last-1].setInvbroj(book.getCtlgNo());
              doc.getRoot().getOpomenaArray(cur_no).getBody().getTabelaArray()[last-1].setSignatura(bean.getSignatura(book.getCtlgNo()));
              doc.getRoot().getOpomenaArray(cur_no).getBody().getTabelaArray()[last-1].setBrdana(Utils.dayDistance(deadline,now));
              if (primerak != null){
                if (!doc.getRoot().getOpomenaArray(cur_no).getFooter().getPojedinacno().equals(""))
                  doc.getRoot().getOpomenaArray(cur_no).getFooter().setPojedinacno(doc.getRoot().getOpomenaArray(cur_no).getFooter().getPojedinacno() + " " + primerak.getCena() + ";");
                if (!doc.getRoot().getOpomenaArray(cur_no).getFooter().getTrostrukotext().equals("")){
                  try{
                    trostruko = trostruko + primerak.getCena().doubleValue()*3;
                    doc.getRoot().getOpomenaArray(cur_no).getFooter().setTrostruko(new Double(trostruko));
                  }catch (Exception e){
                    doc.getRoot().getOpomenaArray(cur_no).getFooter().setTrostruko(new Double(0));
                  }
                }
              }
              
              warning = new Warning();
              book.getWarnings().add(warning);
              warn_books.add(warning);
              warn_to_save.add(warning);
            }
          }
          Iterator itwarn = warn_books.iterator();
          while (itwarn.hasNext()){
            warning = (Warning)itwarn.next();
            warning.setDeadline(maxEndDate.getTime());
            warning.setWarningType(warn_type.getDescription());
            warning.setWarnNo(warn_no + "/" + StringUtils.padZeros(cur_year,2));
            warning.setWarningDate(new Date());
          }
      }
      return doc;
    }catch(Exception e){
      e.printStackTrace();
      return null;
    }
  }
	
	//pravi jasper fajlove opomena i spiska opomena
	private void getReport(){
		try{
      doc = makeDoc(getTfBegDate().getDate(), getTfEndDate().getDate());
      if (doc != null){
        if (getChbSave().isSelected()){
          getManager().saveWarnings(warn_to_save, counters);
        }
  			boolean cyr = false;
  			if (doc.getRoot().getCirilica() == 1){
  				cyr = true;
  			}
  			
  			StringWriter sw = new StringWriter();
  			XmlOptions xmlOptions = new XmlOptions();
        xmlOptions.setSavePrettyPrint();
        doc.save(sw,xmlOptions);
        JasperReport subreport = (JasperReport)JRLoader.loadObject(
            WarningsFrame.class.getResource(
              "/com/gint/app/bisis4/client/circ/warnings/jaspers/details.jasper").openStream());
         JasperReport warning = (JasperReport)JRLoader.loadObject(
              WarningsFrame.class.getResource(
                "/com/gint/app/bisis4/client/circ/warnings/jaspers/warning.jasper").openStream());
  		   Map params = new HashMap(2);
  		   params.put("sub", subreport);
  		   params.put("warning", warning);
  	   
//             JRXmlDataSource ds = new JRXmlDataSource(XMLUtils
//                     .getDocumentFromString(sw.toString()), "/root/opomena");
             //JRXmlDataSource ds = new JRXmlDataSource(new ByteArrayInputStream((sw.toString().getBytes())), "/root/opomena");
             JRXmlDataSource ds = new JRXmlDataSource(doc.newInputStream(), "/root/opomena");
             JasperPrint jp = JasperFillManager.fillReport(
                     WarningsFrame.class.getResource(
                         "/com/gint/app/bisis4/client/circ/warnings/jaspers/all.jasper").openStream(), 
                         params, ds);   
             JRViewer jr = new JRViewer(jp);
             getReportPanel().add(jr, java.awt.BorderLayout.CENTER);
             
             
             String naslov = Utils.convert("Spisak " + ((WarningType)getCmbType().getSelectedItem()).getDescription() + " posaltih na dan " + new SimpleDateFormat("dd.MM.yyyy.").format(new Date()), cyr);
             String rbr = Utils.convert("R.br.", cyr);
             String bropomene = Utils.convert("Br.opomene", cyr);
             String brclana = Utils.convert("Br.\u010dlana", cyr);
             String ime = Utils.convert("Prezime i ime", cyr);
             String datum = Utils.convert("Rok vra\u0107anja", cyr);
             //String napomena = Utils.convert("Napomena", cyr);
             Map paramslist = new HashMap(7);
      	   paramslist.put("naslov", naslov);
      	   paramslist.put("rbr", rbr);
      	   paramslist.put("bropomene", bropomene);
      	   paramslist.put("brclana", brclana);
      	   paramslist.put("ime", ime);
      	   paramslist.put("datum", datum);
      	   //paramslist.put("napomena", napomena);
      	   
      	   JRXmlDataSource dslist = new JRXmlDataSource(XMLUtils
                     .getDocumentFromString(sw.toString()), "/root/opomena");
           JasperPrint jplist = JasperFillManager.fillReport(
                   WarningsFrame.class.getResource(
                       "/com/gint/app/bisis4/client/circ/warnings/jaspers/list.jasper").openStream(), 
                       paramslist, dslist);           
           JRViewer jrlist = new JRViewer(jplist);
           getReportListPanel().add(jrlist, java.awt.BorderLayout.CENTER);
      }    
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	//uzima spisak korisnika iz HistoryTableModela i napravi jasper fajl koji predstavlja registar utuzenja
	private void getHistoryList(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
		boolean cyr = false;
    Date startDate = getTfEndDate().getDate();
    Date endDate = getTfBegDate().getDate();
		if (startDate == null) {
      endDate = Utils.setMaxDate(endDate);
      startDate = Utils.setMinDate(endDate);
    } else if (endDate == null) {
      endDate = Utils.setMaxDate(startDate);
      startDate = Utils.setMinDate(startDate);
    } else {
      startDate = Utils.setMinDate(startDate);
      endDate = Utils.setMaxDate(endDate);
    }
		String naslov = Utils.convert("Registar " + ((WarningType)getCmbType().getSelectedItem()).getDescription(), cyr);
		String period = Utils.convert("period: "+ sdf.format(startDate)+ " - "+sdf.format(endDate),cyr);
    String rbr = Utils.convert("R.br.", cyr);
    String bropomene = Utils.convert("Br.opomene", cyr);
    String brclana = Utils.convert("Br.\u010dlana", cyr);
    String ime = Utils.convert("Prezime i ime", cyr);
    String datum = Utils.convert("Dat. vra\u0107anja", cyr);
    String napomena = Utils.convert("Napomena", cyr);
    String ctlgno = Utils.convert("Inv. broj", cyr);
    
    Map paramslist = new HashMap(7);
    paramslist.put("naslov", naslov);
    paramslist.put("period", period);
    paramslist.put("rbr", rbr);
    paramslist.put("bropomene", bropomene);
    paramslist.put("brclana", brclana);
    paramslist.put("ime", ime);
    paramslist.put("datum", datum);
    paramslist.put("napomena", napomena);
    paramslist.put("ctlgno", ctlgno);
 	   
 	    try{
 	       String brop = "";
 	       list.RootDocument doc = list.RootDocument.Factory.newInstance();
 	       doc.addNewRoot();
 	       int curNo1 = 0;
 	       int curNo2 = 0;
 	       for (int i = 0; i< getHistoryTableModel().getRowCount(); i++){
 	 	      	if (getHistoryTableModel().getValueAt(i,0)==null || getHistoryTableModel().getValueAt(i,0).equals("") || !brop.equals(getHistoryTableModel().getValueAt(i,0))){
 	 	      		doc.getRoot().addNewOpomena();
 	 	      		curNo1 = doc.getRoot().getOpomenaArray().length - 1;
 	 	      		doc.getRoot().getOpomenaArray(curNo1).setBropomene((String)getHistoryTableModel().getValueAt(i,0));
 	 	      	    doc.getRoot().getOpomenaArray(curNo1).setUserid((String)getHistoryTableModel().getValueAt(i,4));
 	 	      	    doc.getRoot().getOpomenaArray(curNo1).setIme((String)getHistoryTableModel().getValueAt(i,5));
 	 	      	    brop = (String)getHistoryTableModel().getValueAt(i,0);
 	 	      	}
 	 	        doc.getRoot().getOpomenaArray(curNo1).addNewKnjiga();
 	 	        curNo2 = doc.getRoot().getOpomenaArray(curNo1).getKnjigaArray().length - 1;
 	 	        doc.getRoot().getOpomenaArray(curNo1).getKnjigaArray(curNo2).setCtlgno((String)getHistoryTableModel().getValueAt(i,6));
 	 	        if (getHistoryTableModel().getValueAt(i,7) != null){
 	 	        	doc.getRoot().getOpomenaArray(curNo1).getKnjigaArray(curNo2).setDatum(sdf.format(getHistoryTableModel().getValueAt(i,7)));
 	 	        }
 	 	        if (getHistoryTableModel().getValueAt(i,8) != null){
 	 	        	doc.getRoot().getOpomenaArray(curNo1).getKnjigaArray(curNo2).setNapomena((String)getHistoryTableModel().getValueAt(i,8));
 	 	        }
 	 	        
 	       }
 	       
 	      StringWriter sw = new StringWriter();
	      XmlOptions xmlOptions = new XmlOptions();
	      xmlOptions.setSavePrettyPrint();
	      doc.save(sw,xmlOptions);
	      

	    JasperReport subreport = (JasperReport)JRLoader.loadObject(
	            WarningsFrame.class.getResource(
	              "/com/gint/app/bisis4/client/circ/warnings/jaspers/subHistoryList.jasper").openStream());
	    paramslist.put("subreport", subreport);
	    
 	    JRXmlDataSource dslist = new JRXmlDataSource(XMLUtils
                .getDocumentFromString(sw.toString()), "/root/opomena");
 	    
        JasperPrint jplist = JasperFillManager.fillReport(
                WarningsFrame.class.getResource(
                    "/com/gint/app/bisis4/client/circ/warnings/jaspers/historyList.jasper").openStream(), 
                    paramslist, dslist);           
        JRViewer jrlist = new JRViewer(jplist);
        getReportListPanel().add(jrlist, java.awt.BorderLayout.CENTER);
        
 	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	private void handleBtnHistory(){
		if ((getTfBegDate().getDate() != null || getTfEndDate().getDate() != null ) && !getCmbType().getSelectedItem().equals("")){
			List<Object[]> list = getManager().getHistory(getTfBegDate().getDate(),getTfEndDate().getDate(), (WarningType)getCmbType().getSelectedItem());
			getHistoryTableModel().setData(list);
			getBtnNext().setText("Registar");
			getBtnPrev().setEnabled(true);
			visPanel = 6;
			showPanel("historyPanel");
		}else{
			JOptionPane.showMessageDialog(this,"Datum i vrsta opomene nisu zadati!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void handleBtnPrev(){
		if (visPanel == 5 && getChangeTemplate().isDirty()){
			int answer = JOptionPane.showConfirmDialog(
		              getMainPanel(), 
		              "Menjali ste podatke! Da li \u017eelite da ih sa\u010duvate?", 
		              "Info", JOptionPane.YES_NO_OPTION, 
		              JOptionPane.QUESTION_MESSAGE);
		    if (answer == JOptionPane.YES_OPTION)
		    	getChangeTemplate().save();
		}
		btnPrev.setEnabled(false);
		getBtnNext().setEnabled(true);
		getBtnNext().setText("Prika\u017ei korisnike >>");
		getWarningFrameTableModel().resetModel();
		getHistoryTableModel().resetModel();
		doc = null;
		getReportPanel().removeAll();
		getReportListPanel().removeAll();
		showPanel("firstPanel");
		visPanel = 1;
	}
	
	private void handleBtnNext(){
		switch (visPanel){
		case 1:	getUsers();
				getBtnPrev().setEnabled(true);
				btnNext.setText("Prika\u017ei opomene >>");
				showPanel("listPanel");
				visPanel = 2;
				break;
		case 2: getReport();
				btnNext.setText("Spisak");
				showPanel("reportPanel");
		    visPanel = 3;
				break;
		case 3: btnNext.setText("Opomene");
			    showPanel("reportListPanel");
			    visPanel = 4;
			    break;
		case 4: btnNext.setText("Spisak");
			    showPanel("reportPanel");
			    visPanel = 3;
			    break;
		case 5: if (getChangeTemplate().isDirty()){
					getChangeTemplate().save();
				}
				break;
		case 6: getHistoryList();
			    showPanel("reportListPanel");
			    getBtnNext().setEnabled(false);
	    		visPanel = 3;
	    		break;
		}
	}
	
	private void handleBtnEdit(){
    if (!getCmbType().getSelectedItem().equals("")){
  		getChangeTemplate().setContent((WarningType)getCmbType().getSelectedItem());
  		getBtnNext().setText("Sa\u010duvaj");
  		getBtnPrev().setEnabled(true);
  		visPanel = 5;
  		showPanel("changePanel");
    }
	}
	
	public void showPanel(String name){
		mCardLayout.show(getMainPanel(),name);
	}

  public void handleClose(){
    this.setVisible(false);
  }
	
 }
