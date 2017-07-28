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
import java.text.ParseException;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.bisis.editor.recordtree.RecordUtils;
import com.ftninformatika.bisis.records.Godina;
import net.miginfocom.swing.MigLayout;


public class SerialInventarPanel extends InventarPanel {

  
  private GodineTableModel godineTableModel;
  private JTable godineTable;
  private JScrollPane godineScrollPane;
  
  private ListSelectionModel listSelModel;
  
  private RaspodelaFrame raspodelaFrame;  
  
  private final int tfSmallLength = 8;
  //razlike u odnosu na primerke
  
  //signatura
  private JTextField sigNumeracijaTxtFld;
  
  //godina
  private JTextField godisteTxtFld;
  private JTextField godinaTxtFld;
  private JTextField brojTxtFld;
  
  //sveske
  private SveskePanel sveskePanel;
  
  private boolean changeInvBr = false; //dodato zbog provere duplih inventarnih brojeva pri promeni postojeceg invetarnog broja
  
  public SerialInventarPanel(){    
    super();
    sigNumeracijaTxtFld = new JTextField(tfLength);
    godisteTxtFld = new JTextField(tfSmallLength);
    godinaTxtFld = new JTextField(tfSmallLength);
    brojTxtFld = new JTextField(20);
    sveskePanel = new SveskePanel(this);
    layoutSerialPanels(); 
    initializeForm(); 
  }
 
  
  public void initializeForm() {
    initializeGodina();
    Date today = new Date();
    invBrojPanel.setInvKnj(InventarConstraints.defaultGodinaInvKnj);
    invKnjPanel.setCode(InventarConstraints.defaultGodinaInvKnj);
    invBrojPanel.setOdeljenje(InventarConstraints.defaultOdeljenje);
    odeljenjePanel.setCode(InventarConstraints.defaultOdeljenje);
    datumInvTxtFld.setText(InventarConstraints.sdf.format(today));
    godineTableModel.initializeModel(); 
    super.initializeOdeljenje();    
    sveskePanel.initializeSveskePanel();
    //sveske se mogu unositi samo ako je selektovana neka godina
    enableSveske(false);
  }


  public void refreshView(){    
    godineTableModel.fireTableDataChanged();
  }


  public String getOdeljenje(){
    return odeljenjePanel.getCode();
  }


  public Godina getSelectedGodina(){
    if(godineTable.getSelectedRowCount()==1)
      return godineTableModel.getRow(godineTable.getSelectedRow());
    else return null;
  }
  
  public void loadItem(){
	  Godina g = RecordUtils.getGodinaPoInv(invBrojPanel.getInventarniBroj());
	  try{
		  loadGodina(g);
	  }catch(NullPointerException e){
		  
	  }
	  int index = RecordUtils.getIndexForGodina(g);
	  listSelModel.setSelectionInterval(index, index);
	  godineTable.scrollRectToVisible
  	(new Rectangle(godineTable.getCellRect(index, 0, false)));
  }

  private void createGodineTable(){
    godineTableModel = new GodineTableModel();
    godineTable = new JTable(godineTableModel);
    godineTable.setRowSorter(new TableRowSorter<GodineTableModel>(godineTableModel));    
    godineScrollPane = new JScrollPane(godineTable);
    adjustInventarColumnWidth();
    listSelModel = godineTable.getSelectionModel();
    listSelModel.addListSelectionListener(new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e) {
        handleListSelectionChanged();       
      }     
    });
    godineTable.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent ke) {
        handleKeys(ke);      
      }      
    });
  }
  
  private void adjustInventarColumnWidth(){
 		TableColumn column = null;		
 		int napomenaColumnIndex = godineTableModel.getColumnIndex("Napomena");
 		int invBrojColumnIndex = godineTableModel.getColumnIndex("Inventarni broj");
 		for(int i=0;i<godineTableModel.getColumnCount();i++){
 			column = godineTable.getColumnModel().getColumn(i);				
 			if(godineTableModel.isSifriranaKolona(i))
 				column.setPreferredWidth(30);			
 			else if(i==napomenaColumnIndex || i==invBrojColumnIndex)
 				column.setPreferredWidth(100);
 			else
 				column.setPreferredWidth(80);		
 			}
  }
  
  private void loadGodina(Godina g){
    if(g.getNacinNabavke()!=null) nacinNabavkePanel.setCode(g.getNacinNabavke());
    else nacinNabavkePanel.setCode("");
    if(g.getPovez()!=null) povezPanel.setCode(g.getPovez());
    else povezPanel.setCode("");
    if(g.getBrojRacuna()!=null) brojRacunaTxtFld.setText(g.getBrojRacuna());
    else brojRacunaTxtFld.setText("");
    if(g.getDatumRacuna()!=null) datumRacunaTxtFld.setText(InventarConstraints.sdf.format(g.getDatumInventarisanja()));
    else datumRacunaTxtFld.setText("");
    if(g.getFinansijer()!=null) finansijerTxtFld.setText(g.getFinansijer());
    else finansijerTxtFld.setText("");
    if(g.getDobavljac()!=null) dobavljacTxtFld.setText(g.getDobavljac());
    if(g.getCena()!=null) cenaTxtFld.setText(g.getCena().toString());
    if(g.getSigUDK()!=null) sigUDKTxtFld.setText(g.getSigUDK());
    else sigUDKTxtFld.setText("");
    if(g.getSigPodlokacija()!=null) podlokacijaPanel.setCode(g.getSigPodlokacija());
    else podlokacijaPanel.setCode("");
    if(g.getSigNumerusCurens()!=null) sigNumerusCurensTxtFld.setText(g.getSigNumerusCurens());
    else sigNumerusCurensTxtFld.setText("");
    if(g.getSigFormat()!=null) formatPanel.setCode(g.getSigFormat());
    else formatPanel.setCode("");
    if(g.getSigIntOznaka()!=null) intOznakaPanel.setCode(g.getSigIntOznaka());
    else intOznakaPanel.setCode("");
    if(g.getSigDublet()!=null) sigDubletTxtFld.setText(g.getSigDublet());
    else sigDubletTxtFld.setText("");
    if(g.getSigNumeracija()!=null) sigNumeracijaTxtFld.setText(g.getSigNumeracija());
    else sigNumeracijaTxtFld.setText(g.getSigNumeracija());
    if(g.getDatumInventarisanja()!=null) datumInvTxtFld.setText(InventarConstraints.sdf.format(g.getDatumInventarisanja()));
    else datumInvTxtFld.setText("");
    if(InventarConstraints.imaOdeljenja){
      if(g.getOdeljenje()!=null) odeljenjePanel.setCode(g.getOdeljenje());
      else odeljenjePanel.setCode("");
    }else{
      odeljenjePanel.setCode("00");
    }
    if(g.getInvBroj()!=null && g.getInvBroj().length()>3){
      if(InventarConstraints.imaOdeljenja)
        invKnjPanel.setCode(g.getInvBroj().substring(2,4));
      else
        invKnjPanel.setCode(g.getInvBroj().substring(0,2));        
    }
    else invKnjPanel.setCode("");
    if(g.getInvBroj()!=null) invBrojPanel.setInventarniBroj(g.getInvBroj());
    else invBrojPanel.setInventarniBroj("");    
    if(g.getNapomene()!=null) napomeneTxtArea.setText(g.getNapomene());
    else napomeneTxtArea.setText("");   
    if(g.getGodina()!=null) godinaTxtFld.setText(g.getGodina());
    else godinaTxtFld.setText("");
    if(g.getGodiste()!=null) godisteTxtFld.setText(g.getGodiste());
    else godisteTxtFld.setText(g.getGodiste());
    if(g.getBroj()!=null) brojTxtFld.setText(g.getBroj());
    else brojTxtFld.setText("");    
    if(g.getDostupnost()!=null) dostupnostPanel.setCode(g.getDostupnost());
    else dostupnostPanel.setCode("");
    if(g.getInventator()!=null) inventatorTxtFld.setText(g.getInventator());
    else inventatorTxtFld.setText("");    
    sveskePanel.setSveske(g);
  }
  
  private Godina getGodinaFromForm(){    
    Godina g = new Godina();    
    if(!nacinNabavkePanel.getCode().equals(""))
      g.setNacinNabavke(nacinNabavkePanel.getCode());
    if(!povezPanel.getCode().equals(""))
      g.setPovez(povezPanel.getCode());
    if(!brojRacunaTxtFld.getText().equals(""))
      g.setBrojRacuna(brojRacunaTxtFld.getText());
    if(!datumRacunaTxtFld.getText().equals("")){
      try {
        g.setDatumRacuna(InventarConstraints.sdf.parse(datumRacunaTxtFld.getText()));
      } catch (ParseException e) {
        // ne desava se nikada
      }
    }
    if(!finansijerTxtFld.getText().equals(""))
      g.setFinansijer(finansijerTxtFld.getText());
    if(!dobavljacTxtFld.getText().equals(""))
      g.setDobavljac(dobavljacTxtFld.getText());
    if(!cenaTxtFld.getText().equals("")){
      g.setCena(new BigDecimal(cenaTxtFld.getText()));
    }   
//  signatura
    if(!sigUDKTxtFld.getText().equals(""))
      g.setSigUDK(sigUDKTxtFld.getText());
    if(!podlokacijaPanel.getCode().equals(""))
      g.setSigPodlokacija(podlokacijaPanel.getCode());
    if(!sigNumerusCurensTxtFld.getText().equals(""))
      g.setSigNumerusCurens(sigNumerusCurensTxtFld.getText());
    if(!formatPanel.getCode().equals(""))
      g.setSigFormat(formatPanel.getCode());
    if(!intOznakaPanel.getCode().equals(""))
      g.setSigIntOznaka(intOznakaPanel.getCode());
    if(!sigDubletTxtFld.getText().equals(""))
      g.setSigDublet(sigDubletTxtFld.getText());
    if(!sigNumeracijaTxtFld.getText().equals(""))
      g.setSigNumeracija(sigNumeracijaTxtFld.getText());
    if(!datumInvTxtFld.getText().equals("")){
      try {
        g.setDatumInventarisanja(InventarConstraints.sdf.parse(datumInvTxtFld.getText()));
      } catch (ParseException e) {
        // ne desava se nikad
      }
    }
    if(InventarConstraints.imaOdeljenja)
      if(!odeljenjePanel.getCode().equals(""))
        g.setOdeljenje(odeljenjePanel.getCode());
    //inventarna knjiga
    if(!invBrojPanel.getInventarniBroj().equals(""))
      g.setInvBroj(invBrojPanel.getInventarniBroj()); 
    changeInvBr = invBrojPanel.isChangeInvBr();
    if(!napomeneTxtArea.getText().equals(""))
      g.setNapomene(napomeneTxtArea.getText());
    if(!godinaTxtFld.getText().equals(""))
      g.setGodina(godinaTxtFld.getText());
    if(!godisteTxtFld.getText().equals(""))
      g.setGodiste(godisteTxtFld.getText());
    if(!brojTxtFld.getText().equals(""))
      g.setBroj(brojTxtFld.getText());
    if(!dostupnostPanel.getCode().equals(""))
      g.setDostupnost(dostupnostPanel.getCode());
    if(!inventatorTxtFld.getText().equals(""))
    	g.setInventator(inventatorTxtFld.getText());
    //sveske
    g.setSveske(sveskePanel.getSveske());   
    return g;    
  }
  
  private void handleSacuvajGodinu() {
    Obrada.editorFrame.recordUpdated();
    if(godineTable.getSelectedRowCount()<=1){
      if(!handleValidateFormData(true).equals(""))      
        JOptionPane.showMessageDialog(BisisApp.getMainFrame(),handleValidateFormData(true),"Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
      else{
        Godina g = getGodinaFromForm();        
        try {
          godineTableModel.updateGodina(g, changeInvBr);
        } catch (InventarniBrojException e) {
          JOptionPane.showMessageDialog(BisisApp.getMainFrame(),e.getMessage(),"Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
        }
      		changeInvBr = false;
      		invBrojPanel.setChangeInvBr(false);
      }
    }else{
      if(!handleValidateFormData(false).equals(""))      
        JOptionPane.showMessageDialog(BisisApp.getMainFrame(),handleValidateFormData(true),"Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
      else
        godineTableModel.updateGodine(godineTable.getSelectedRows(), getGodinaFromForm());
    }   
  } 
  
  private void handleOpenRaspodelaFrame(){
    if(!handleValidateFormData(false).equals(""))     
      JOptionPane.showMessageDialog(BisisApp.getMainFrame(),handleValidateFormData(false),"Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
    else{
      raspodelaFrame = new RaspodelaFrame(this);    
      BisisApp.getMainFrame().insertFrame(raspodelaFrame);
      raspodelaFrame.setVisible(true);
    }
  }
 
  private void initializeGodina(){
    Godina g = new Godina();
    loadGodina(g);    
  }  
  
  private void handleListSelectionChanged() {
    if(godineTable.getSelectedRowCount()==1){
      handleLoadGodina();
      setEnabledInvBroj(true);
      enableSveske(true);
    }else{ 
    	enableSveske(false);
      initializeGodina();
      enableSveske(false);   
  }    
  }
  
  private void enableSveske(boolean enabled){
    tabbedPane.setEnabledAt(4, enabled);
    if(!enabled && sveskePanel.isVisible())
      tabbedPane.setSelectedIndex(0);
    
  }
  
  private String handleValidateFormData(boolean all){    
      return InventarValidation.validateInventarPanelData(this, all);   
  }
  
  private void handleLoadGodina(){
    if(godineTable.getSelectedRow()>-1 
        && godineTable.getSelectedRow()<godineTableModel.getRowCount()){
      loadGodina(godineTableModel
          .getRow(godineTable.convertRowIndexToModel(godineTable.getSelectedRow())));      
    } 
    
  }

  private void handleKeys(KeyEvent ke) {
    if(ke.getKeyCode()==KeyEvent.VK_DELETE){
    	deleteSelectedGodina();
    }    
  }
  
  private void deleteSelectedGodina(){
  	Godina g = godineTableModel.getRow(godineTable.getSelectedRow());
  	Object[] options = { "Obri\u0161i", "Odustani" };
  	String message = "Da li ste sigurni da \u017eelite da obri\u0161ete godinu, \n" +
			"inventarni broj: "+g.getInvBroj()+"?";  	
  	int ret = JOptionPane.showOptionDialog(null, message , "Brisanje", 
		JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
			null, options, options[0]);
  	if(ret==0){  	
  		godineTableModel.deleteRow(godineTable.getSelectedRow());
  		Obrada.editorFrame.recordUpdated();
  	}
  }
  
  private void layoutSerialPanels() {
    createGodineTable();
    createInventarPanel();
    createNabavkaPanel();
    createSignaturaPanel();
    createOstaloPanel();
    createButtonsPanel();    
    tabbedPane.addTab("Sveske", sveskePanel);
    tabbedPane.setMnemonicAt(4, KeyEvent.VK_V);
    
  //  MigLayout layout = new MigLayout("","[]10[]10[]","");
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    setLayout(layout);
    
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 1;
    c.weighty = 0.7;
    c.fill = GridBagConstraints.BOTH;
    add(godineScrollPane,c);
    c.gridy = 1;
    c.weighty = 0.2;    
    add(tabbedPane,c);
    c.gridy = 2;
    c.weighty = 0.1;
    add(buttonsPanel,c);    
  }

  private void createInventarPanel(){
    JPanel glavniPanel = new JPanel();    
    JPanel godistePanel = new JPanel();
    
    glavniPanel.setLayout(new MigLayout("","","[]0[]5[]0[]5[]0[]5[]0[]"));
    
    glavniPanel.add(new JLabel("Odeljenje:"),"wrap");
    glavniPanel.add(odeljenjePanel,"wrap, grow");
        
    glavniPanel.add(new JLabel("Inventarna knjiga:"),"wrap");
    glavniPanel.add(invKnjPanel,"wrap, grow");   
            
    glavniPanel.add(new JLabel("Inventarni broj:"),"wrap");
    glavniPanel.add(invBrojPanel,"wrap, grow");
    
    
      
    
    godistePanel.setLayout(new MigLayout("","[]10[]","[]0[]5[]0[]10[]0[]5[]0[]"));
    
    godistePanel.add(new JLabel("Datum inventarisanja:"),"span 2, wrap");
    godistePanel.add(datumInvTxtFld,"wrap, grow, span 2");
    
    godistePanel.add(new JLabel("Inventarisao:"), "span 2, wrap");
    godistePanel.add(inventatorTxtFld, "span 2, wrap");
    
    godistePanel.add(new JLabel("Godina:"),"grow");
    godistePanel.add(new JLabel("Godi\u0161te:"),"grow, wrap");   
    
    godistePanel.add(godinaTxtFld,"grow");
    godistePanel.add(godisteTxtFld,"grow, wrap");
    godistePanel.add(new JLabel("Broj:"),"wrap, grow");
    godistePanel.add(brojTxtFld,"grow, span 2, wrap");
    
    
   
   
   
    
    
    
    invPanel.setLayout(new MigLayout("","[]20[]",""));
    invPanel.add(glavniPanel);
    invPanel.add(godistePanel);
    //invPanel.add(godistePanel);    
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

 
  private void createSignaturaPanel() {
    JPanel numCurPanel = new JPanel();
    JPanel ostaloPanel = new JPanel();
    
    numCurPanel.setLayout(new MigLayout("","","[]0[]5[]0[]20[]0[]5[]0[]"));
    
    numCurPanel.add(new JLabel("Podlokacija"),"wrap");
    numCurPanel.add(podlokacijaPanel,"wrap, grow");  
        
    numCurPanel.add(new JLabel("Numerus curens:"),"wrap");
    numCurPanel.add(sigNumerusCurensTxtFld,"wrap, grow");
    
    numCurPanel.add(new JLabel("Interna oznaka:"),"wrap");
    numCurPanel.add(intOznakaPanel,"wrap, grow");
    
    numCurPanel.add(new JLabel("Numeracija:"),"wrap");
    numCurPanel.add(sigNumeracijaTxtFld,"wrap, grow");
    
    ostaloPanel.setLayout(new MigLayout("","","[]0[]7[]0[]7[]0[]"));
    
    ostaloPanel.add(new JLabel("UDK:"),"wrap");
    ostaloPanel.add(sigUDK675bPanel,"wrap, grow");
    
    ostaloPanel.add(new JLabel("Format:"),"wrap");
    ostaloPanel.add(formatPanel,"wrap, grow");
    
    ostaloPanel.add(new JLabel("Dublet:"),"wrap");   
    ostaloPanel.add(sigDubletTxtFld,"wrap, grow");
    
    
    
    signaturaPanel.setLayout(new MigLayout("","[]20[]",""));
    signaturaPanel.add(numCurPanel);
    signaturaPanel.add(ostaloPanel);
    
    signaturaPanel.setFocusCycleRoot(true);
  }
  
  private void createButtonsPanel(){
    buttonsPanel = new JPanel();
    sacuvajButton = new JButton("Sa\u010duvaj");
    sacuvajButton.setIcon(new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/ok.gif")));
    ponistiButton = new JButton("Poni\u0161ti");
    ponistiButton.setIcon(new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/remove.gif")));
    ponistiButton.setSelected(false); 
    raspodelaButton = new JButton("Raspodela");
    raspodelaButton.setIcon(new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/edit.gif")));    
    
    MigLayout layout = new MigLayout("","5[]300[right]5[right]","");
    buttonsPanel.setLayout(layout);   
    buttonsPanel.add(raspodelaButton);
    //buttonsPanel.add(Box.createGlue());
    buttonsPanel.add(sacuvajButton);   
    buttonsPanel.add(ponistiButton);    
    
    sacuvajButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {        
          handleSacuvajGodinu();            
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
  }


  public JTextField getBrojTxtFld() {
    return brojTxtFld;
  }


  public JTextField getGodinaTxtFld() {
    return godinaTxtFld;
  }


  public JTextField getGodisteTxtFld() {
    return godisteTxtFld;
  }


  public JTextField getSigNumeracijaTxtFld() {
    return sigNumeracijaTxtFld;
  }


  public SveskePanel getSveskePanel() {
    return sveskePanel;
  }
 


}
