package com.ftninformatika.bisis.hitlist;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.text.html.HTMLEditorKit;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.hitlist.formatters.RecordFormatter;
import com.ftninformatika.bisis.hitlist.formatters.RecordFormatterFactory;
import com.ftninformatika.bisis.records.Record;
import net.miginfocom.swing.MigLayout;


public class HitListFrame extends JInternalFrame {

  public static final int PAGE_SIZE = 10;

  public HitListFrame(String query /*Result queryResult*/) {
    super("Rezultati pretrage", true, true, true, true);    
    //this.queryResult = queryResult;
    //renderer.setResults(queryResult);
    this.query=query;    
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    btnFirst.setIcon(new ImageIcon(getClass().getResource(
    "/icons/first.gif")));
    btnPrev.setIcon(new ImageIcon(getClass().getResource(
        "/icons/prev.gif")));
    btnNext.setIcon(new ImageIcon(getClass().getResource(
        "/icons/next.gif")));
    btnLast.setIcon(new ImageIcon(getClass().getResource(
    "/icons/last.gif")));
    btnEdit.setIcon(new ImageIcon(getClass().getResource(
        "/icons/edit.gif")));
    btnDelete.setIcon(new ImageIcon(getClass().getResource(
        "/icons/remove.gif")));
    btnNew.setIcon(new ImageIcon(getClass().getResource(
        "/icons/copy.gif")));
    /*btnInventar.setIcon(new ImageIcon(EditorFrame.class
        .getResource("/com/gint/app/bisis4/client/images/book16.png")));
    btnAnalitika.setIcon(new ImageIcon(EditorFrame.class
        .getResource("/com/gint/app/bisis4/client/images/doc_rich16.png")));*/
    
    lbHitList.setModel(hitListModel);
    lbHitList.setCellRenderer(renderer);
    spHitList.setViewportView(lbHitList);    
    spHitList.setPreferredSize(new Dimension(500, 500));
    idLabel.setText("<html><B>ID</B>");
    idTxtFld.setEditable(false);
    rnLabel.setText("<html><B>RN</B>");
    rnTxtFld.setEditable(false);
    cardPane.setEditorKit(new HTMLEditorKit());
			cardPane.setEditable(false);		
			fullFormatPane.setEditorKit(new HTMLEditorKit());
			formatter = RecordFormatterFactory.getFormatter(RecordFormatterFactory.FORMAT_FULL);
			fullFormatPane.setEditable(false);
			JScrollPane cardPaneScroll = new JScrollPane(cardPane);			
			cardPaneScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			JScrollPane fullFormatPaneScroll = new JScrollPane(fullFormatPane);		
			inventarTable.setModel(inventarTableModel);
			inventarTable.setAutoCreateRowSorter(true);
			inventarTable.setCellSelectionEnabled(true);
			inventarTable.setDefaultRenderer(inventarTable.getColumnClass(0), inventartTableRenderer);
			inventarTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			adjustInventarColumnWidth();
			
			//uploadedFilesTable.setModel(uploadedFilesTableModel);
			
			JScrollPane inventarScrollPane = new JScrollPane(inventarTable);
			JScrollPane uploadedFilesScrollPane =  new JScrollPane(uploadedFilesTable);
		
			inventarScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			createMetaDataPanel();
			
			tabbedPane.setPreferredSize(new Dimension(400,500));			
			JPanel inventarTabPanel = new JPanel();
			inventarTabPanel.setLayout(new BorderLayout());
			inventarTabPanel.add(inventarScrollPane, BorderLayout.CENTER);
			tabbedPane.addTab("Full format", fullFormatPaneScroll);
			tabbedPane.addTab("Listi\u0107", cardPaneScroll);
			tabbedPane.addTab("Inventar", inventarScrollPane);
			tabbedPane.addTab("Dokumenta", uploadedFilesScrollPane);			
			/*if(!BisisApp.isFileMgrEnabled()){
				tabbedPane.setEnabledAt(3, false);
			}else{
				tabbedPane.setEnabledAt(3, true);
			}*/
			
			tabbedPane.addTab("Meta podaci", metaDataPanel);
			pageTxtFld.setPreferredSize(new Dimension(35,25));   
    
    // panel na kom su prikazani svi pogoci
    MigLayout layout = new MigLayout(
        "",
        "[][][][]",
        "[]rel[]rel[grow]para[]");
    JPanel labelsPanel = new JPanel();
    labelsPanel.setLayout(new GridLayout(3,1));
    labelsPanel.add(lQuery);
    labelsPanel.add(lFromTo);
    labelsPanel.add(lBrPrimeraka);    
    allResultsPanel.setLayout(layout);    
    allResultsPanel.add(labelsPanel,"span 5,wrap");
    allResultsPanel.add(btnFirst,"span 5, split 5");
    allResultsPanel.add(btnPrev, "");
    allResultsPanel.add(pageTxtFld,""); 
    allResultsPanel.add(btnNext, "");
    allResultsPanel.add(btnLast,"wrap");     
    allResultsPanel.add(spHitList, "span 5, grow, wrap");
    allResultsPanel.add(btnBranches,"span 5, left, wrap");
    
    
    oneResultPanel.setLayout(new MigLayout("insets dialog, wrap","[]rel[]rel[grow]rel[]","[]10[grow]15[]"));    
    
    oneResultPanel.add(idLabel,"span 4, split 5");
    oneResultPanel.add(idTxtFld,"");
    oneResultPanel.add(rnLabel,"");
    oneResultPanel.add(rnTxtFld,"");
    oneResultPanel.add(pubTypeLabel,"wrap");
    oneResultPanel.add(tabbedPane,"span 4, split 1, wrap, grow");
    oneResultPanel.add(btnAnalitika,"span 5, split 5, right");
    oneResultPanel.add(btnDelete,"");
    oneResultPanel.add(btnInventar, "");
    oneResultPanel.add(btnNew,"");
    oneResultPanel.add(btnEdit,"wrap");    
    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
        allResultsPanel, oneResultPanel);        
    splitPane.setOneTouchExpandable(true);
    //int loc = (int)((splitPane.getBounds().getWidth()-splitPane.getDividerSize())/2);
    splitPane.setDividerLocation(500);    
    add(splitPane);    
    pack();        
    if(!BisisApp.appConfig.getLibrarian().isObrada()){
      btnEdit.setEnabled(false);
      btnNew.setEnabled(false);
      btnDelete.setEnabled(false);
      btnInventar.setEnabled(false);
    }   
    addInternalFrameListener(new InternalFrameAdapter() {
      public void internalFrameClosing(InternalFrameEvent e) {
        shutdown();
      }
    });   
    listSelModel = lbHitList.getSelectionModel();
    listSelModel.addListSelectionListener(new ListSelectionListener(){
  		public void valueChanged(ListSelectionEvent e) {
  			handleListSelectionChanged(); 							
  		}			
  	});
   
    btnPrev.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        if (page > 0) {
          page--;
          updateAvailability();
          displayPage();
        }
      }
    });
    
    btnNext.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        if (page < pageCount() - 1) {
          page++;
          updateAvailability();
          displayPage();
        }
      }
    });
    
    btnFirst.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {				
				page = 0;
				updateAvailability();
        displayPage();
			}    	
    });
    
    btnLast.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {				
				page = pageCount()-1;
				updateAvailability();
        displayPage();
			}    	
    });
    
    btnBrief.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        renderer.setFormatter(RecordFormatterFactory.FORMAT_BRIEF);
        hitListModel.refresh();
          System.out.println("Action preformed!");
      }
    });
    
    pageTxtFld.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ev) {
      	if(ev.getKeyCode()==KeyEvent.VK_ENTER){
      	// skok na odgovarajucu stranicu
      	try{
      		int pageNum = Integer.parseInt(pageTxtFld.getText());
      		if(pageNum>=0 && pageNum<pageCount()){
      			page = pageNum;
      			updateAvailability();
            displayPage();
            lbHitList.grabFocus();
      		}
      	}catch(NumberFormatException e){}
      	}
      }
    });
    
    btnFull.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        renderer.setFormatter(RecordFormatterFactory.FORMAT_FULL);
        hitListModel.refresh();
          System.out.println("Action preformed!");
      }
    });
    
    lbHitList.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ev) {
      	handleKeys(ev);      	
      }
    });
    
    lbHitList.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e) {
        if(e.getClickCount()==2){
          btnEdit.doClick();
        }       
      }     
    });
    
    btnDelete.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
      	handleDeleteRecord();        
      }
    });
    
    btnEdit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
          System.out.println("Usao u neki listener");
    	/*	try {
    			int recordId = ((Record)lbHitList.getSelectedValue()).getRecordID();
       Record rec = BisisApp.getRecordManager().getAndLock(recordId,BisisApp.getLibrarian().getUsername());
    			Obrada.editRecord(rec);
    		} catch (LockException e) {
    			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),e.getMessage(),"Zaklju\u010dan zapis",JOptionPane.INFORMATION_MESSAGE);
    		} catch (NullPointerException e) {
    			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Morate selektovati zapis","Gre\u0161ka",JOptionPane.INFORMATION_MESSAGE);
    			
				}       */
          System.out.println("Action preformed!");
       }
    });
    
    btnNew.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent ev) {       
       /* Record rec = ((Record)lbHitList.getSelectedValue()).copyWithoutHoldings();
        if(rec!=null) Obrada.newRecord(rec);*/
            System.out.println("Action preformed!");
       }
    });
    
    btnInventar.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ev) {				
						int recordId = ((Record)lbHitList.getSelectedValue()).getRecordID();
		    Record rec;
						try {
							//rec = BisisApp.getRecordManager().getAndLock(recordId,BisisApp.getLibrarian().getUsername());
							//Obrada.editInventar(rec);
						//} catch (LockException e) {
							//JOptionPane.showMessageDialog(BisisApp.getMainFrame(),e.getMessage(),"Zaklju\u010dan zapis",JOptionPane.INFORMATION_MESSAGE);
		    } catch (NullPointerException e) {
		    	JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Morate selektovati zapis","Gre\u0161ka", JOptionPane.INFORMATION_MESSAGE);
						}
                        System.out.println("Action preformed!");
					}
		  });
    
    btnAnalitika.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {						
				/*		Record rec = ((Record)lbHitList.getSelectedValue()).copyWithoutHoldings();
      if(rec!=null) Obrada.editAnalitika(rec);*/
                        System.out.println("Action preformed!");
					}    	
    });
    
    btnBranches.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {				
						handleBranches();
					}    	
    });
    
    tabbedPane.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e) {	
						handleLoadTabs();
					}    	
    });
    
    ListSelectionModel tableSelModel = inventarTable.getSelectionModel();
    tableSelModel.addListSelectionListener(new ListSelectionListener(){
					public void valueChanged(ListSelectionEvent e) {			
						inventarTable.repaint();
					}    	
    });
    
    uploadedFilesTable.addMouseListener(new MouseAdapter(){
     public void mouseClicked(MouseEvent e) {
      if(e.getClickCount()==2){
        handleOpenFile();
      }       
    }     
    });
    
    btnBrief.setFocusable(false);
    btnFull.setFocusable(false);
    btnPrev.setFocusable(false);
    btnNext.setFocusable(false);
    btnFirst.setFocusable(false);
    btnLast.setFocusable(false);
    btnDelete.setFocusable(false);
    btnEdit.setFocusable(false);
    btnNew.setFocusable(false);     
    ButtonGroup btnGroup = new ButtonGroup();
    btnGroup.add(btnBrief);
    btnGroup.add(btnFull);
    btnBrief.setSelected(true);   
    lQuery.setText("<html>Upit: <b>" + query + "</b></html>");
    updateAvailability();
    displayPage();    
    lbHitList.requestFocus();    
  }
  
  private void shutdown() { 	
  	BisisApp.mf.getSearchFrame().show();
  	try {
			BisisApp.mf.getSearchFrame().setSelected(true);
		} catch (PropertyVetoException e) {		
		}
  	BisisApp.mf.getSearchFrame().setDefaultFocus();
  	setVisible(false); 	
  }
  
  private void updateAvailability() {
    btnPrev.setEnabled(page != 0);
    btnNext.setEnabled(page != pageCount() - 1);
  }
  
  private void displayPage() {
   // if (queryResult == null || queryResult.getRecords().length == 0)
 //     return;
    int count = //PAGE_SIZE;
                1;
   // if (page == pageCount()-1 ){  //ako je poslednja stranica
   // 	if (queryResult.getResultCount() % PAGE_SIZE==0){
    //		count=PAGE_SIZE;
  //  	}
    //	else{
    //     count = queryResult.getResultCount() % PAGE_SIZE;
   // 	}
   // }
    pageTxtFld.setText(String.valueOf(page));
    int[] recIDs = new int[count];
   // for (int i = 0; i < count; i++)
     // recIDs[i] = queryResult.getRecords()[page*PAGE_SIZE + i];

      /////ovde ubacujemo neki record
      Record zapis = null;
      try {
          zapis = BisisApp.bisisService.getOneRecord().execute().body();
      } catch (IOException e) {
          e.printStackTrace();
      }



    hitListModel.setHits(/*recIDs*/zapis);
    lbHitList.setSelectedIndex(0);
    handleListSelectionChanged();    
    lFromTo.setText("<html>Pogoci: <b>" + (page*PAGE_SIZE+1) + " - " + 
        (page*PAGE_SIZE+count) + "</b> od <b>" + 
        /*queryResult.getResultCount()*/ zapis.getPrimerci().size() + "</b></html>");
    lBrPrimeraka.setText("<html>Broj primeraka: <b>"/*+queryResult.getInvs().size()+*/+"nesto"+"</b></html>");
  }
  
  private int pageCount() {
   /* if (queryResult == null || queryResult.getResultCount() == 0)
     return 0;
     return queryResult.getResultCount() / PAGE_SIZE + (queryResult.getResultCount() % PAGE_SIZE > 0 ? 1 : 0);   */
    return 0;
  }
  
  
  private void handleKeys(KeyEvent ev){
  	switch (ev.getKeyCode()) {
    case KeyEvent.VK_ENTER:
      btnEdit.doClick();
      break;
    case KeyEvent.VK_ESCAPE:
      shutdown();
      break;
    case KeyEvent.VK_LEFT:
    	btnPrev.doClick();
    	break;
    case KeyEvent.VK_RIGHT:
    	btnNext.doClick();
  	}
    if(ev.getKeyCode()== KeyEvent.VK_F6 &&
    	ev.getModifiers()==InputEvent.CTRL_MASK){
       	 BisisApp.mf.selectNextInternalFrame();
    	}
  }
  
  private void handleListSelectionChanged(){
  	int recordId = ((Record)lbHitList.getSelectedValue()).getRecordID();//--------------
      Record zapis = null;
      try {
          zapis = BisisApp.bisisService.getOneRecord().execute().body();
      } catch (IOException e) {
          e.printStackTrace();
      }
   selectedRecord = //BisisApp.getRecordManager().getRecord(recordId);
                    zapis;
   idTxtFld.setText(String.valueOf(selectedRecord.getRecordID()));//-----------------------
   rnTxtFld.setText(String.valueOf(selectedRecord.getRN()));
   String pubTypeStr = "";
   if(selectedRecord.getPubType()==2)
   	pubTypeStr = "serijska";
   else if(selectedRecord.getPubType()==3)
   	pubTypeStr = "analitika";
   else
   	pubTypeStr = "monografska";		
   pubTypeLabel.setText("<html><B>"+pubTypeStr+"</B>");	
   if(selectedRecord.getPubType()==3)
   	btnAnalitika.setEnabled(false);
   else
   	btnAnalitika.setEnabled(true);   		
   handleLoadTabs();
  }
  
  private void handleLoadTabs(){
  	if(tabbedPane.getSelectedIndex()==0){
  		fullFormatPane.setText(formatter.toHTML(selectedRecord, "sr"));
  		fullFormatPane.setCaretPosition(0);
		}else if(tabbedPane.getSelectedIndex()==1){
				//loadCard(selectedRecord);
                System.out.println("loadCard");
		}else if(tabbedPane.getSelectedIndex()==2){
				int recordId = ((Record)lbHitList.getSelectedValue()).getRecordID();
				/*selectedRecord = BisisApp.getRecordManager().getRecord(recordId);*/
				inventarTableModel.setRecord(selectedRecord);
				System.out.println("inventar");
				adjustInventarColumnWidth();
		}else if(tabbedPane.getSelectedIndex()==3){
			int recordId = ((Record)lbHitList.getSelectedValue()).getRecordID();
   //selectedRecord = BisisApp.getRecordManager().getRecord(recordId);
   //uploadedFilesTableModel.setDocFiles(BisisApp.getRecordManager().getDocFiles(selectedRecord.getRN()));
        System.out.println("getDocFiles");
 	}else if(tabbedPane.getSelectedIndex()==4){
 		int recordId = ((Record)lbHitList.getSelectedValue()).getRecordID();
        Record zapis = null;
        try {
            zapis = BisisApp.bisisService.getOneRecord().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
   selectedRecord //= BisisApp.getRecordManager().getRecord(recordId);
                    = zapis;
   loadMetaData(selectedRecord);
	  }
  }
  
  //private void handleTab
  
  private void handleBranches(){
	//  BisisApp.mf.addBranchesFrame(query, queryResult.getRecords());
  }
  
	/*
	 * ucitava listic za zapis record
	 * ucitava se default listic za tip publikacije zapisa
	 * TODO 
	 */
	/*private void loadCard(Record record){
		String html = Report.makeOne(record, true);    
  cardPane.setText(html);
  cardPane.setCaretPosition(0);
	}*/
	
	private void createMetaDataPanel(){
		metaDataPanel.setLayout(new MigLayout("","","[]10[]20[]10[]"));
		metaDataPanel.add(new JLabel("<html><b>Kreirao:</b></html>"));
		metaDataPanel.add(recCreatorLabel,"wrap");
		
		metaDataPanel.add(new JLabel("<html><b>Modifikovao:</b></html>"));
		metaDataPanel.add(recModifierLabel,"wrap");
		
		metaDataPanel.add(new JLabel("<html><b>Datum kreiranja:</b></html>"));
		metaDataPanel.add(recCreationDateLabel,"wrap");
		
		metaDataPanel.add(new JLabel("<html><b>Datum izmene:</b></html>"));
		metaDataPanel.add(recModificationDateLabel,"wrap");	
	}
	
	private void loadMetaData(Record rec){
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    if(rec.getCreator()!=null)   
    	recCreatorLabel.setText(rec.getCreator().getCompact());
    else
    	recCreatorLabel.setText("");
    
    if(rec.getModifier()!=null)
    	recModifierLabel.setText(rec.getModifier().getCompact());
    else
    	recModifierLabel.setText("");
    
    if(rec.getCreationDate()!=null)
    	recCreationDateLabel.setText(/*sdf.format(rec.getCreationDate())*/rec.getCreationDate());
    else
    	recCreationDateLabel.setText("");
    
    if(rec.getLastModifiedDate()!=null)	
    	recModificationDateLabel.setText(/*sdf.format(rec.getLastModifiedDate())*/rec.getLastModifiedDate());
    else
    	recModificationDateLabel.setText("");
 	}
	
	private void loadUploadedFiles(Record rec){
		
		
	}
	
	
	private void handleDeleteRecord(){
	/*	Record rec = (Record)lbHitList.getSelectedValue();
		Object[] options = { "Obri\u0161i", "Odustani" };
		StringBuffer messBuff = new StringBuffer();
		messBuff.append("Da li ste sigurni da \u017eelite da obri\u0161ete zapis:\n");
		messBuff.append(RecordUtils.getDeleteRecordReport(rec));		
		int ret = JOptionPane.showOptionDialog(null, messBuff.toString() , "Brisanje", 
		JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
		null, options, options[1]);
		if(ret==0){
			// jos jednom pitamo
			Object[] options1 = { "Odustani", "Potvrdi" };
			messBuff = new StringBuffer();
			messBuff.append("potvrda o brisanju zapisa\n");
			messBuff.append("<html><b>ID="+rec.getRecordID()+"</b></html>\n");
			messBuff.append("<html><b>RN="+rec.getRN()+"</b></html>");			
			ret = JOptionPane.showOptionDialog(null, messBuff.toString(), "Brisanje", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					null, options1, options[0]);
			if(ret == 1){				
				boolean deleted = hitListModel.remove(lbHitList.getSelectedIndex());
				hitListModel.refresh();
				if(deleted)
					JOptionPane.showMessageDialog(null, "Zapis je uspe\u0161no obrisan!");
				else
					JOptionPane.showMessageDialog(null, "Gre\u0161ka prilikom brisanja zapisa!");				
			}			
		}		*/
	}
 
	private void adjustInventarColumnWidth(){
		TableColumn column = null;
		int napomenaColumnIndex = inventarTableModel.getColumnIndex("Napomena");
		int invBrojColumnIndex = inventarTableModel.getColumnIndex("Inventarni broj");
		for(int i=0;i<inventarTableModel.getColumnCount();i++){
			column = inventarTable.getColumnModel().getColumn(i);				
			if(inventarTableModel.isSifriranaKolona(i))
				column.setPreferredWidth(30);			
			else if(i==napomenaColumnIndex || i==invBrojColumnIndex)
				column.setPreferredWidth(100);
			else
				column.setPreferredWidth(80);		
			}
	}
	
	public void setQueryResults(String query/*, Result queryResults*/){
		//this.queryResult = queryResults;
      this.query=query;
      //renderer.setResults(queryResults);
      updateAvailability();
      page = 0;
      lQuery.setText("<html>Upit: <b>" + query + "</b></html>");
      displayPage();
	}
	
	private void handleOpenFile(){
		/*DocFile docFile = uploadedFilesTableModel.getFileForRow(uploadedFilesTable.getSelectedRow());
		File f = FileManagerClient.download(BisisApp.getFileManagerURL(), docFile);
		try {
			Desktop.getDesktop().open(f);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Gre\u0161ka prilikim otvaranja dokumenta.\n","Gre\u0161ka",JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			e.printStackTrace();
		}*/
	}


		private JLabel lQuery = new JLabel();
  private JLabel lFromTo = new JLabel();
  private JLabel lBrPrimeraka = new JLabel();
  private JToggleButton btnBrief = new JToggleButton("Sa\u017eeti");
  private JToggleButton btnFull = new JToggleButton("Puni");
  private JButton btnPrev = new JButton(/*"Prethodni"*/);
  private JButton btnNext = new JButton(/*"Slede\u0107i"*/);
  private JButton btnFirst = new JButton(/*"Prvi"*/);
  private JButton btnLast = new JButton(/*"Poslednji"*/);
  private JTextField pageTxtFld = new JTextField(3);
  
  private JButton btnDelete = new JButton("Obri\u0161i");
  private JButton btnEdit = new JButton("Otvori");
  private JButton btnNew = new JButton("Novi");
  private JButton btnInventar = new JButton("Inventar");
  private JButton btnAnalitika = new JButton("Analitika");
  
  private JButton btnBranches = new JButton("Grupni prikaz");
  
  private JScrollPane spHitList = new JScrollPane();
  private JList lbHitList = new JList();
  private HitListModel hitListModel = new HitListModel();
  private ListSelectionModel listSelModel;
  private HitListRenderer renderer = new HitListRenderer();
  
  private JPanel allResultsPanel = new JPanel();
  private JPanel oneResultPanel = new JPanel(); 
  private JSplitPane splitPane;
  
  private JTabbedPane tabbedPane = new JTabbedPane();
  private JLabel idLabel = new JLabel();
  private JTextField idTxtFld = new JTextField(5);
  private JLabel rnLabel = new JLabel();
  private JTextField rnTxtFld = new JTextField(5);
  
  private JLabel pubTypeLabel = new JLabel();
  private JEditorPane fullFormatPane = new JEditorPane();	
		private JEditorPane cardPane = new JEditorPane();
		private JPanel metaDataPanel = new JPanel();
		private JLabel recCreatorLabel = new JLabel("");
		private JLabel recModifierLabel = new JLabel("");
		private JLabel recCreationDateLabel = new JLabel("");
		private JLabel recModificationDateLabel = new JLabel("");
		
		private JTable inventarTable = new JTable();
		private InventarTabTableModel inventarTableModel = new InventarTabTableModel();
		private InventarTabTableCellRenderer inventartTableRenderer = new InventarTabTableCellRenderer();
		
		private JTable uploadedFilesTable = new JTable();
		//private UploadedFilesTableModel uploadedFilesTableModel = new UploadedFilesTableModel();
		
		
		private RecordFormatter formatter;
		private Record selectedRecord = null;
  
 // private Result queryResult;
  private String query;
  private int page = 0;
}
