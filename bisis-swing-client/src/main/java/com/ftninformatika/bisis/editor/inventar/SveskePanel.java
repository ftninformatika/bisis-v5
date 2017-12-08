package com.ftninformatika.bisis.editor.inventar;

import com.ftninformatika.utils.Messages;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

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

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.bisis.format.HoldingsDataCoders;
import com.ftninformatika.bisis.records.Godina;
import com.ftninformatika.bisis.records.Sveska;
import com.ftninformatika.utils.string.Signature;
import net.miginfocom.swing.MigLayout;


public class SveskePanel extends JPanel {  
 
  private JTable sveskeTable;
  private SveskeTableModel sveskeTableModel;
  private JScrollPane sveskeScrollPane;
 
  private CodedValuePanel invKnjPanel;
  private CodedValuePanel statusPanel;
  private InventarniBrojPanel invBrojPanel;
  private JTextField cenaTxtFld;
  private JTextField brojTxtFld;
  private JTextField knjigaTxtFld;
  private JTextField datumStatusaTxtFld;
  
  private JButton dodajSveskuButton;
  private SerialInventarPanel parent;  
  
  public SveskePanel(SerialInventarPanel parent){
    sveskeTableModel = new SveskeTableModel(null);    
    invKnjPanel = new CodedValuePanel(HoldingsDataCoders.INVENTARNAKNJIGA_CODER,this);
    statusPanel = new CodedValuePanel(HoldingsDataCoders.STATUS_CODER,this);
    invBrojPanel = new InventarniBrojPanel();   
    this.parent = parent;
    cenaTxtFld = new JTextField(10);
    brojTxtFld = new JTextField(10); 
    knjigaTxtFld = new JTextField(10);
    datumStatusaTxtFld = new JTextField(10);
    dodajSveskuButton = new JButton(Messages.getString("EDITOR_ADD_BUTTON"));
    dodajSveskuButton.setIcon(new ImageIcon(getClass().getResource(
    "/icons/plus16.png")));
    createSveskeTable();
    layoutPanels();   
    initializeSveskePanel();
    addComponentListener(new ComponentAdapter(){
      public void componentShown(ComponentEvent arg0) {
    	  handleComponentShown();       
      }      
    });       
    //actions
    dodajSveskuButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        handleAddSveska();        
      }     
    }); 
    invKnjPanel.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent arg0) {
        handleKeys(invKnjPanel,arg0);        
      }
    });
    statusPanel.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent arg0) {
        handleKeys(statusPanel,arg0);        
      }
    });
    invBrojPanel.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent arg0) {
        handleKeys(invBrojPanel,arg0);        
      }
    });
    cenaTxtFld.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent arg0) {
        handleKeys(cenaTxtFld,arg0);        
      }
    });    
    brojTxtFld.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent arg0) {
        handleKeys(brojTxtFld,arg0);        
      }
    }); 
    dodajSveskuButton.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent arg0) {
        handleKeys(dodajSveskuButton,arg0);        
      }
    });  
  } 
  
  protected void handleComponentShown() {
	  brojTxtFld.requestFocus();
	  initializeSveskePanel();
  }

  public void initializeSveskePanel(){  		
    loadSveska(new Sveska());
    invBrojPanel.setOdeljenje(parent.getOdeljenje());
    invBrojPanel.setInvKnj(InventarConstraints.defaultSveskaInvKnj);
  }  
  
  public List<Sveska> getSveske(){  	
  	return sveskeTableModel.getSveske();       
  }
  
  public void setSveske(Godina g){
  	// kopiramo sve sveske iz godine
  	// jer se nesto jako brljavi sa objektima
  	List<Sveska> sveske = new ArrayList<Sveska>();  
  	for(Sveska s:g.getSveske())
  		sveske.add(s.copy());
    sveskeTableModel.setSveske(sveske); 
    initializeSveskePanel();
  }
  
  private void setEnabledInvBroj(boolean enabled){
    invBrojPanel.setEnabled(enabled);    
  }
  
  private void createSveskeTable() {    
    TableSorter tsorter = new TableSorter(sveskeTableModel);
    sveskeTable = new JTable(tsorter);    
    TableColumn columnInvBroj = sveskeTable.getColumnModel().getColumn(0);    
    tsorter.setTableHeader(sveskeTable.getTableHeader());  
    sveskeScrollPane = new JScrollPane(sveskeTable);    
    ListSelectionModel lSelModel = sveskeTable.getSelectionModel();
    lSelModel.addListSelectionListener(new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e) {
        handleListSelectionChanged();       
      }     
    });
    sveskeTable.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent ke) {
        handleKeys(sveskeTable,ke);      
      }      
    });    
  }  
 
  private void handleListSelectionChanged() {
    if(sveskeTable.getSelectedRowCount()<=1){
      handleLoadSveska();
      setEnabledInvBroj(true);
    }else{
      initializeSveskePanel();
      setEnabledInvBroj(false);   
    }    
  }
 
  private void handleLoadSveska() {   
    if(sveskeTable.getSelectedRow()>-1 
        && sveskeTable.getSelectedRow()<sveskeTableModel.getRowCount()){          
      loadSveska(sveskeTableModel
          .getRow(sveskeTable.convertRowIndexToModel(sveskeTable.getSelectedRow())));
    }else{
      initializeSveskePanel();
    }    
  }

  private void loadSveska(Sveska s){    
    if(s!=null){
      if(s.getInvBroj()!=null){
        invBrojPanel.setInventarniBroj(s.getInvBroj());          
                 
      }else{
        invBrojPanel.setInventarniBroj("");        
      }
      if(s.getBrojSveske()!=null) brojTxtFld.setText(s.getBrojSveske());
      else brojTxtFld.setText("");      
      if(s.getStatus()!=null) statusPanel.setCode(s.getStatus());
      else statusPanel.setCode("");      
      if(s.getKnjiga()!=null) knjigaTxtFld.setText(s.getKnjiga());
      else knjigaTxtFld.setText("");
    }    
  }
  
  private Sveska getSveskaFromForm(){
    Sveska s = new Sveska();    
    if(!invBrojPanel.getInventarniBroj().equals(""))
      s.setInvBroj(invBrojPanel.getInventarniBroj());
    if(!brojTxtFld.getText().equals(""))
      s.setBrojSveske(brojTxtFld.getText());   
    if(!statusPanel.getCode().equals(""))
      s.setStatus(statusPanel.getCode());   
    if(!knjigaTxtFld.getText().equals(""))
    	s.setKnjiga(knjigaTxtFld.getText());
    return s;
  }
  
  private Sveska getSelectedSveska(){
    if(sveskeTable.getSelectedRowCount()==1){
      return sveskeTableModel.getRow(sveskeTable.getSelectedRow());
    }
    return null;
  }
  
  private void handleAddSveska() {
    String validationMessage;
    if(sveskeTable.getSelectedRowCount()<=1){
      validationMessage = InventarValidation.validateSveskeFormData(this,true); 
      if(validationMessage.equals("")){
        Sveska s = getSveskaFromForm();
        if(getSelectedSveska()!=null)s.setSveskaID(getSelectedSveska().getSveskaID());
        Godina godina = parent.getSelectedGodina();        
        s.setParent(godina);
        s.setSignatura(Signature.format(godina));
        try {
          sveskeTableModel.updateSveska(s);
        } catch (InventarniBrojException e) {
          JOptionPane.showMessageDialog(BisisApp.getMainFrame(),e.getMessage(),"",JOptionPane.ERROR_MESSAGE);
        } 
      }else{
        JOptionPane.showMessageDialog(BisisApp.getMainFrame(),validationMessage,"",JOptionPane.ERROR_MESSAGE);
      }     
    }else{
      validationMessage = InventarValidation.validateSveskeFormData(this, false);
      if(validationMessage.equals(""))
        sveskeTableModel.updateSveske(sveskeTable.getSelectedRows(), getSveskaFromForm());
      else
       JOptionPane.showMessageDialog(BisisApp.getMainFrame(),validationMessage,"",JOptionPane.ERROR_MESSAGE); 
    }
  }  
 
  private void handleKeys(Component comp, KeyEvent ke) {
    if(ke.getKeyCode()==KeyEvent.VK_DELETE)
      deleteSelectedSveska(); 
    if(ke.getKeyCode()==KeyEvent.VK_ESCAPE)
      Obrada.editorFrame.handleCloseEditor();
    //if(ke.getKeyCode()==KeyEvent.VK_ENTER && !comp.equals(invBrojPanel))
      //dodajSveskuButton.doClick();     
  }
  
  private void deleteSelectedSveska(){
  	if(sveskeTable.getSelectedRow()>=0){
	  	Sveska s = sveskeTableModel.getRow(sveskeTable.getSelectedRow());
	  	Object[] options = {Messages.getString("EDITOR_DELETE"), Messages.getString("EDITOR_BUTTONCANCEL") };
	  	String message = MessageFormat.format(Messages.getString("ARE_U_SURE_TO_DELETE_NOTEBOOK_FOR_INV_NUM.0"), s.getInvBroj());
			int ret = JOptionPane.showOptionDialog(null, message , "Брисање",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					null, options, options[0]); 
			if(ret==0)
				sveskeTableModel.deleteRow(sveskeTable.getSelectedRow());  
  	}
  }
  
  private void layoutPanels() {
    setLayout(new MigLayout("","[]10[]",""));
    JPanel inputPanel = new JPanel();
    //inputPanel.setLayout(new MigLayout("","[][]","[]0[]5[]0[]5[]0[]5[]0[]5[]0[]"));    
    inputPanel.setLayout(new MigLayout("","[]5[]","[]0[]5[]0[]5[]0[]10[]"));
    //inputPanel.setLayout(new MigLayout("","[]5[]","[]0[]5[]0[]5[]"));
    inputPanel.add(new JLabel(Messages.getString("NOTEBOOK_NUM:")),"");
    inputPanel.add(new JLabel(Messages.getString("BOOK_YEARING")),"wrap");
    inputPanel.add(brojTxtFld,"");
    inputPanel.add(knjigaTxtFld,"wrap");   
    inputPanel.add(new JLabel(Messages.getString("INV_NUM")),"wrap, span 2");
    inputPanel.add(invBrojPanel,"wrap, span 2");    
    inputPanel.add(new JLabel(Messages.getString("STATUS:")),"wrap, span 2");
    inputPanel.add(statusPanel,"wrap, span 2");   
    //inputPanel.add(new JLabel("Datum statusa:"),"wrap, span 2");
    //inputPanel.add(datumStatusaTxtFld, "wrap, span 2");
    inputPanel.add(dodajSveskuButton);
    add(inputPanel);    
    add(sveskeScrollPane,"grow");
    inputPanel.setFocusCycleRoot(true);    
  }

  public JTextField getBrojTxtFld() {
    return brojTxtFld;
  }

  public JTextField getCenaTxtFld() {
    return cenaTxtFld;
  }

  public InventarniBrojPanel getInvBrojPanel() {
    return invBrojPanel;
  }

  
  public CodedValuePanel getStatusPanel() {
    return statusPanel;
  }  
  
  
  

}
