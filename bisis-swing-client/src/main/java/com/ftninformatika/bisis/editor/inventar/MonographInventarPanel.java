package com.ftninformatika.bisis.editor.inventar;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.MessageFormat;import java.text.ParseException;
import java.util.Date;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.bisis.editor.recordtree.RecordUtils;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.utils.Messages;
import net.miginfocom.swing.MigLayout;


public class MonographInventarPanel extends InventarPanel {
	
  
  private RaspodelaFrame raspodelaFrame;  
  // tabela primeraka
  private PrimerciTableModel primerciTableModel;
  private JTable primerciTable; 
  private JScrollPane primerciScrollPane; 
  private ListSelectionModel listSelModel;
  private PrintersDlg printersDlg;
  private boolean changeInvBr = false; //dodato zbog provere duplih inventarnih brojeva pri promeni postojeceg invetarnog broja
  
	
	public MonographInventarPanel() {
		super();    
		layoutPanels();	
		initializeForm();	
	}
  
	protected void handleListSelectionChanged() {
    if(primerciTable.getSelectedRowCount()<=1){      
      handleLoadPrimerak();
      setEnabledInvBroj(true);
    }else{      
      initializePrimerak();
      setEnabledInvBroj(false);   
    }    
  }
  
  public void initializePrimerak(){
    Primerak p = new Primerak();
    loadPrimerak(p);   
  }
  
  public void initializeForm() {
    initializePrimerak();
    invBrojPanel.setInvKnj(InventarConstraints.defaultPrimerakInvKnj);
    invKnjPanel.setCode(InventarConstraints.defaultPrimerakInvKnj);
    invBrojPanel.setOdeljenje(InventarConstraints.defaultOdeljenje);
    odeljenjePanel.setCode(InventarConstraints.defaultOdeljenje);
    
    Date today = new Date();
    datumInvTxtFld.setText(InventarConstraints.sdf.format(today));
    
    primerciTableModel.initializeModel(); 
    super.initializeOdeljenje();
  }	
  
  
  public InventarniBrojPanel getInvbrojPanel() {
  	return invBrojPanel;
  }

  public Primerak getPrimerakFromForm(){  				
  	Primerak p = new Primerak();    
		if(!nacinNabavkePanel.getCode().equals(""))
			p.setNacinNabavke(nacinNabavkePanel.getCode());
		if(!povezPanel.getCode().equals(""))
			p.setPovez(povezPanel.getCode());
		if(!brojRacunaTxtFld.getText().equals(""))
			p.setBrojRacuna(brojRacunaTxtFld.getText());
		if(!datumRacunaTxtFld.getText().equals("")){
			try {
				p.setDatumRacuna(InventarConstraints.sdf.parse(datumRacunaTxtFld.getText()));
			} catch (ParseException e) {
				// ne desava se nikada
			}
		}
		if(!finansijerTxtFld.getText().equals(""))
			p.setFinansijer(finansijerTxtFld.getText());
		if(!dobavljacTxtFld.getText().equals(""))
			p.setDobavljac(dobavljacTxtFld.getText());
		if(!cenaTxtFld.getText().equals("")){
			p.setCena(new BigDecimal(cenaTxtFld.getText()));
		}		
		//signatura
		if(!sigUDKTxtFld.getText().equals(""))
			p.setSigUDK(sigUDKTxtFld.getText());
		if(!podlokacijaPanel.getCode().equals(""))
			p.setSigPodlokacija(podlokacijaPanel.getCode());
		if(!sigNumerusCurensTxtFld.getText().equals(""))
			p.setSigNumerusCurens(sigNumerusCurensTxtFld.getText());
		if(!formatPanel.getCode().equals(""))
			p.setSigFormat(formatPanel.getCode());
		if(!intOznakaPanel.getCode().equals(""))
			p.setSigIntOznaka(intOznakaPanel.getCode());
		if(!sigDubletTxtFld.getText().equals(""))
			p.setSigDublet(sigDubletTxtFld.getText());		
		//inventar		
		if(!datumInvTxtFld.getText().equals("")){
			try {
				p.setDatumInventarisanja(InventarConstraints.sdf.parse(datumInvTxtFld.getText()));
			} catch (ParseException e) {
				// ne desava se nikad
			}
		}
    //odeljenje
    if(InventarConstraints.imaOdeljenja)
      if(!odeljenjePanel.getCode().equals(""))
        p.setOdeljenje(odeljenjePanel.getCode());      
		//inventarna knjiga
		if(!invBrojPanel.getInventarniBroj().equals(""))
			p.setInvBroj(invBrojPanel.getInventarniBroj());
		changeInvBr = invBrojPanel.isChangeInvBr();
		if(!statusPanel.getCode().equals(""))
			p.setStatus(statusPanel.getCode());
    if(!dostupnostPanel.getCode().equals("")){
      p.setDostupnost(dostupnostPanel.getCode());
    }
    if(!datumStatusaTxtFld.getText().equals("")){
    	try {
				p.setDatumStatusa(InventarConstraints.sdf.parse(datumStatusaTxtFld.getText()));
			} catch (ParseException e) {
				// ne desava se nikad
			}
    }
    if(!inventatorTxtFld.getText().equals("")){
    	p.setInventator(inventatorTxtFld.getText());
    }
		if(!napomeneTxtArea.getText().equals(""))
			p.setNapomene(napomeneTxtArea.getText());
		if(!usmeravanjeTxtFld.getText().equals(""))
			p.setUsmeravanje(usmeravanjeTxtFld.getText());
		return p;
  }

  public void refreshView() {    
    primerciTableModel.fireTableDataChanged();
  }
  
  public void loadItem(){
	  Primerak p = RecordUtils.getPrimerakPoInv(invBrojPanel.getInventarniBroj()); //TODO-???
	  
	  try{
		  loadPrimerak(p);
	  }catch(NullPointerException e){
		  //TODO poruka
	  }
	  int index = RecordUtils.getIndexForPrimerak(p);
	  listSelModel.setSelectionInterval(index,index);	 
	  primerciTable.scrollRectToVisible
	  	(new Rectangle(primerciTable.getCellRect(index, 0, false)));
  }
	
  private void handleLoadPrimerak() {  
  	if(primerciTable.getSelectedRow()>-1 
  			&& primerciTable.getSelectedRow()<primerciTableModel.getRowCount()){					
      loadPrimerak(primerciTableModel
          .getRow(primerciTable.convertRowIndexToModel(primerciTable.getSelectedRow())));
  	}	
  }

  private void handleOpenRaspodelaFrame(){
  	if(!handleValidateFormData(false).equals(""))			
  		JOptionPane.showMessageDialog(BisisApp.getMainFrame(),handleValidateFormData(false), Messages.getString("EDITOR_ERROR"),JOptionPane.ERROR_MESSAGE);
  	else{
  		raspodelaFrame = new RaspodelaFrame(this);		
  		BisisApp.getMainFrame().insertFrame(raspodelaFrame);
  		raspodelaFrame.setVisible(true);
  	}
  }

  private void handleSacuvajPrimerak(){
    Obrada.editorFrame.recordUpdated();
    if(primerciTable.getSelectedRowCount()<=1){
  		if(!handleValidateFormData(true).equals(""))			
  			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),handleValidateFormData(true), Messages.getString("EDITOR_ERROR"),JOptionPane.ERROR_MESSAGE);
  		else{
  			Primerak p = getPrimerakFromForm();       
  			try {
          primerciTableModel.updatePrimerak(p, changeInvBr);
        } catch (InventarniBrojException e) {
            JOptionPane.showMessageDialog(BisisApp.getMainFrame(),e.getMessage(), Messages.getString("EDITOR_ERROR"),JOptionPane.ERROR_MESSAGE);
        }
  		changeInvBr = false;
  		invBrojPanel.setChangeInvBr(false);
  		}
    }else{
      // menjamo vrednost za vise primeraka
      if(!handleValidateFormData(false).equals(""))      
        JOptionPane.showMessageDialog(BisisApp.getMainFrame(),handleValidateFormData(true), Messages.getString("EDITOR_ERROR"),JOptionPane.ERROR_MESSAGE);
      else{
      	int[] rowIndexes = new int[primerciTable.getSelectedRowCount()];
      	for(int i=0;i<primerciTable.getSelectedRowCount();i++){
      		int index = primerciTable.getSelectedRows()[i];
      		rowIndexes[i] = primerciTable.convertRowIndexToModel(index);
      	}
        primerciTableModel.updatePrimerci(rowIndexes,getPrimerakFromForm());
      }
    }
  }

  private String handleValidateFormData(boolean all){
  	return InventarValidation.validateInventarPanelData(this,all);
  }

  private void handleKeys(KeyEvent ke) {   
    if(ke.getKeyCode()==KeyEvent.VK_DELETE){
    	deleteSelectedPrimerak();
    }    
  }
  
  private void deleteSelectedPrimerak(){
  	Primerak p = primerciTableModel.getRow(primerciTable.getSelectedRow());
  	Object[] options = {Messages.getString("EDITOR_DELETE"), Messages.getString("EDITOR_BUTTONCANCEL") };
  	String message = MessageFormat.format(Messages.getString("ARE_U_SURE_U_WANT_TO_DELETE.0"), p.getInvBroj());
		int ret = JOptionPane.showOptionDialog(null, message , Messages.getString("DELETING"),
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
				null, options, options[0]);
		if(ret==0){
			primerciTableModel.deleteRow(primerciTable.getSelectedRow());
			Obrada.editorFrame.recordUpdated();
		}
  }

  private void createinvPanel(){    
    JPanel glavniPanel = new JPanel();
    JPanel ostaloPanel = new JPanel();
    
    glavniPanel.setLayout(new MigLayout("","","[]0[]5[]0[]5[]0[]"));
    
    glavniPanel.add(new JLabel(Messages.getString("LOCATION:")),"wrap");
    glavniPanel.add(odeljenjePanel,"wrap, grow");
        
    glavniPanel.add(new JLabel(Messages.getString("INV_BOOK:")),"wrap");
    glavniPanel.add(invKnjPanel,"wrap, grow");   
            
    glavniPanel.add(new JLabel(Messages.getString("INV_NUM:")),"wrap");
    glavniPanel.add(invBrojPanel,"wrap, grow");
    
   /* glavniPanel.add(new JLabel("Datum inventarisanja:"),"wrap");
    glavniPanel.add(datumInvTxtFld,"wrap, grow");*/ 
    
    ostaloPanel.setLayout(new MigLayout("","","[]0[]5[]0[]5[]0[]5[]0[]"));
    
    ostaloPanel.add(new JLabel(Messages.getString("INV_DATE:")),"wrap");
    ostaloPanel.add(datumInvTxtFld,"wrap, grow");
    ostaloPanel.add(new JLabel(Messages.getString("INVENTOR:")),"wrap");
    ostaloPanel.add(inventatorTxtFld,"wrap, grow");    
    ostaloPanel.add(new JLabel(Messages.getString("STATUS:")),"wrap");
    ostaloPanel.add(statusPanel,"wrap, grow");
    ostaloPanel.add(new JLabel(Messages.getString("STATUS_DATE:")),"wrap");
    ostaloPanel.add(datumStatusaTxtFld,"wrap, grow");  
 
    
    
    
    invPanel.setLayout(new MigLayout("","[]40[]",""));
    invPanel.add(glavniPanel);
    invPanel.add(ostaloPanel);
    
    invPanel.setFocusCycleRoot(true);
    invPanel.addComponentListener(new ComponentAdapter(){
      public void componentShown(ComponentEvent arg0) {  
        if(InventarConstraints.imaOdeljenja)
          odeljenjePanel.requestFocus();
        else
          invKnjPanel.requestFocus();
        }      
    });
  }
	
	private void createSignaturaPanel(){   
    
    JPanel numCurPanel = new JPanel();
    JPanel ostaloPanel = new JPanel();
    
    numCurPanel.setLayout(new MigLayout("","","[]0[]5[]0[]20[]0[]"));
    
    numCurPanel.add(new JLabel(Messages.getString("SUBLOCATION")),"wrap");
    numCurPanel.add(podlokacijaPanel,"wrap, grow");  
        
    numCurPanel.add(new JLabel(Messages.getString("NUMERUS_CURENS")),"wrap");
    numCurPanel.add(sigNumerusCurensPanel,"wrap, grow");
    
    numCurPanel.add(new JLabel(Messages.getString("INTERNAL_MARK:")),"wrap");
    numCurPanel.add(intOznakaPanel,"wrap, grow");
    
    ostaloPanel.setLayout(new MigLayout("","","[]0[]7[]0[]7[]0[]"));
    
    ostaloPanel.add(new JLabel(Messages.getString("UDK:")),"wrap");
    ostaloPanel.add(sigUDK675bPanel,"wrap, grow");
    
    ostaloPanel.add(new JLabel(Messages.getString("FORMAT:")),"wrap");
    ostaloPanel.add(formatPanel,"wrap, grow");
    
    ostaloPanel.add(new JLabel(Messages.getString("DOUBLET:")),"wrap");
    ostaloPanel.add(sigDubletTxtFld,"wrap, grow");
    
    signaturaPanel.setLayout(new MigLayout("","[center]20[center]","[center]"));
    signaturaPanel.add(numCurPanel,"grow");
    signaturaPanel.add(ostaloPanel,"grow");
    
    signaturaPanel.setFocusCycleRoot(true);	
	}	
	
	private void createButtonsPanel(){
		buttonsPanel = new JPanel();
		sacuvajButton = new JButton(Messages.getString("EDITOR_SAVE_BUTTON"));
		sacuvajButton.setIcon(new ImageIcon(getClass().getResource(
        "/icons/ok.gif")));
		ponistiButton = new JButton(Messages.getString("EDITOR_CANCEL_BUTTON"));
		ponistiButton.setIcon(new ImageIcon(getClass().getResource(
        "/icons/remove.gif")));
		ponistiButton.setSelected(false);	
		raspodelaButton = new JButton(Messages.getString("DISTRIBUTION"));
		raspodelaButton.setIcon(new ImageIcon(getClass().getResource(
        "/icons/edit.gif")));
		
		printBarcodeButton = new JButton(Messages.getString("BARCODE"));
		
		MigLayout layout = new MigLayout("","5[]5[]300[right]5[right]","");
		buttonsPanel.setLayout(layout);		
		buttonsPanel.add(raspodelaButton);
		buttonsPanel.add(printBarcodeButton);
  buttonsPanel.add(Box.createGlue());
		buttonsPanel.add(sacuvajButton);   
		buttonsPanel.add(ponistiButton);		
		
		sacuvajButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {				
					handleSacuvajPrimerak();						
			}			
		});
		
		ponistiButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				initializeForm();
			}			
		});
		
		raspodelaButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				handleOpenRaspodelaFrame();				
			}			
		});		
		
		printBarcodeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {			
				handlePrintBarcode();
			}			
		});
	}	

	//ako je selektovan jedan primerak vraca njegovu
  //instancu inace vraca null
  private Primerak getSelectedPrimerak(){
    if(primerciTable.getSelectedRowCount()==1){
      return primerciTableModel.getRow(primerciTable.getSelectedRow());
    }
    return null;    
  }
  
 protected void createOstaloPanel(){
  	
  	JPanel prviPanel = new JPanel();
  	prviPanel.setLayout(new MigLayout("","","[]0[]5[]0[]5[]0[]"));
  	prviPanel.add(new JLabel(Messages.getString("AVAILABILITY:")),"wrap");
  	prviPanel.add(dostupnostPanel,"grow, wrap");
  	prviPanel.add(new JLabel(Messages.getString("BINDING:")),"wrap");
    prviPanel.add(povezPanel,"wrap, grow");
    prviPanel.add(new JLabel(Messages.getString("ROUTING")),"wrap, grow");
    prviPanel.add(usmeravanjeTxtFld,"wrap, grow");
    
    JPanel drugiPanel = new JPanel();
    drugiPanel.setLayout(new MigLayout("","","[]0[]"));
    drugiPanel.add(new JLabel(Messages.getString("NOTES:")),"wrap");
    drugiPanel.add(napomeneScrollPane,"wrap, grow");
  	
  	ostaloPanel.setLayout(new MigLayout("","[]10[]",""));  	    
    ostaloPanel.add(prviPanel,"grow");
    ostaloPanel.add(drugiPanel,"wrap");
  }
	
	private void loadPrimerak(Primerak p){		
		if(p.getNacinNabavke()!=null) nacinNabavkePanel.setCode(p.getNacinNabavke());
		else nacinNabavkePanel.setCode("");
		if(p.getPovez()!=null) povezPanel.setCode(p.getPovez());
		else povezPanel.setCode("");
		if(p.getBrojRacuna()!=null) brojRacunaTxtFld.setText(p.getBrojRacuna());
		else brojRacunaTxtFld.setText("");
		if(p.getDatumRacuna()!=null) datumRacunaTxtFld.setText(InventarConstraints.sdf.format(p.getDatumRacuna()));
		else datumRacunaTxtFld.setText("");
		if(p.getFinansijer()!=null) finansijerTxtFld.setText(p.getFinansijer());
		else finansijerTxtFld.setText("");
		if(p.getDobavljac()!=null) dobavljacTxtFld.setText(p.getDobavljac());
		else dobavljacTxtFld.setText("");		
		if(p.getCena()!=null) cenaTxtFld.setText(p.getCena().toString());
		else cenaTxtFld.setText("");
		if(p.getSigUDK()!=null) sigUDKTxtFld.setText(p.getSigUDK());
		else sigUDKTxtFld.setText("");
		if(p.getSigPodlokacija()!=null) podlokacijaPanel.setCode(p.getSigPodlokacija());
		else podlokacijaPanel.setCode("");
		if(p.getSigNumerusCurens()!=null) sigNumerusCurensTxtFld.setText(p.getSigNumerusCurens());
		else sigNumerusCurensTxtFld.setText("");
		if(p.getSigFormat()!=null) formatPanel.setCode(p.getSigFormat());
		else formatPanel.setCode("");
		if(p.getSigIntOznaka()!=null) intOznakaPanel.setCode(p.getSigIntOznaka());
		else intOznakaPanel.setCode("");
		if(p.getSigDublet()!=null) sigDubletTxtFld.setText(p.getSigDublet());
		else sigDubletTxtFld.setText("");
		if(p.getDatumInventarisanja()!=null) datumInvTxtFld.setText(InventarConstraints.sdf.format(p.getDatumInventarisanja()));
		else datumInvTxtFld.setText("");
  if(p.getOdeljenje()!=null) odeljenjePanel.setCode(p.getOdeljenje());
  else odeljenjePanel.setCode("");  
		if(p.getInvBroj()!=null && p.getInvBroj().length()>3)     
    invKnjPanel.setCode(p.getInvBroj().substring(2,4));   
		else invKnjPanel.setCode("");
		if(p.getInvBroj()!=null) invBrojPanel.setInventarniBroj(p.getInvBroj());
		else invBrojPanel.setInventarniBroj("");
		if(p.getStatus()!=null) statusPanel.setCode(p.getStatus());
		else statusPanel.setCode("");
    if(p.getDostupnost()!=null) dostupnostPanel.setCode(p.getDostupnost());
    else dostupnostPanel.setCode("");
		if(p.getNapomene()!=null) napomeneTxtArea.setText(p.getNapomene());
		else napomeneTxtArea.setText("");		
		if(p.getDatumStatusa()!=null) datumStatusaTxtFld.setText(InventarConstraints.sdf.format(p.getDatumStatusa()));
		else datumStatusaTxtFld.setText("");
		if(p.getInventator()!=null) inventatorTxtFld.setText(p.getInventator());
		else inventatorTxtFld.setText("");
		if(p.getUsmeravanje()!=null) usmeravanjeTxtFld.setText(p.getUsmeravanje());
		else usmeravanjeTxtFld.setText("");
			
	}
	
	private void layoutPanels(){		
  	createPrimerciTable();
  	createinvPanel();
  	createNabavkaPanel();	
  	createSignaturaPanel();	
  	createOstaloPanel();
  	createButtonsPanel();	
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    setLayout(layout);
    c.weightx = 1.0;
    c.weighty = 0.6;    
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 0;
    c.gridy = 0;  
    c.ipady = 2;
  	this.add(primerciScrollPane,c);
    c.gridy = 1;
    c.weighty = 0.35;
    c.ipady = 0;
    this.add(tabbedPane,c);
    c.gridy = 2;
    c.weighty = 0.05;
  	this.add(buttonsPanel,c);
    
    this.addComponentListener(new ComponentAdapter(){
      public void componentShown(ComponentEvent arg0) {      
        if(InventarConstraints.imaOdeljenja)
          odeljenjePanel.requestFocus();
        else
          invKnjPanel.requestFocus();
      }      
    });
  }

  private void createPrimerciTable(){  	
  	primerciTableModel = new PrimerciTableModel();
    primerciTable = new JTable(primerciTableModel);
    primerciTable.setRowSorter(new TableRowSorter<PrimerciTableModel>(primerciTableModel));
  	
  	primerciScrollPane = new JScrollPane(primerciTable);		
  	listSelModel = primerciTable.getSelectionModel();
  	adjustInventarColumnWidth();
  	
  	listSelModel.addListSelectionListener(new ListSelectionListener(){
  		public void valueChanged(ListSelectionEvent e) {
        handleListSelectionChanged();				
  		}			
  	});
    primerciTable.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent ke) {
        handleKeys(ke);      
      }      
    });    
  }
  
  private void adjustInventarColumnWidth(){
 		TableColumn column = null;		
 		int napomenaColumnIndex = primerciTableModel.getColumnIndex(Messages.getString("NOTE"));
 		int invBrojColumnIndex = primerciTableModel.getColumnIndex(Messages.getString("INV_NUM"));
 		for(int i=0;i<primerciTableModel.getColumnCount();i++){
 			column = primerciTable.getColumnModel().getColumn(i);				
 			if(primerciTableModel.isSifriranaKolona(i))
 				column.setPreferredWidth(30);			
 			else if(i==napomenaColumnIndex || i==invBrojColumnIndex)
 				column.setPreferredWidth(100);
 			else
 				column.setPreferredWidth(80);		
 			}
  }
  
  private void handlePrintBarcode() {
  	int[] selectedPrimerci =  primerciTable.getSelectedRows();
  	for(int i:selectedPrimerci){
  		Primerak p = primerciTableModel.getRow(primerciTable.convertRowIndexToModel(i)); 
//  		if (PrintBarcode.multiplePrinters()){
//  			getPrintersDlg().setPrinters(PrintBarcode.getPrinters());
//  			getPrintersDlg().setVisible(true);
//  			if (getPrintersDlg().isOK())
//  				PrintBarcode.printBarcodeForPrimerak(p, getPrintersDlg().getPrinter()); 
//  		} else {
//  			PrintBarcode.printBarcodeForPrimerak(p, null); 
//  		}
  		PrintBarcode.printBarcodeForPrimerak(p, null); 
  		 		
  	} 		
  }
  
  private PrintersDlg getPrintersDlg() {
	    if (printersDlg == null) {
	    	printersDlg = new PrintersDlg(BisisApp.getMainFrame());
	    	printersDlg.setLocationRelativeTo(null);
	    }
	    return printersDlg;
	  }
}
