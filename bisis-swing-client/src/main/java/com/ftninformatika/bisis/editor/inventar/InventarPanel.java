package com.ftninformatika.bisis.editor.inventar;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ftninformatika.bisis.coders.CodersHelper;
import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.bisis.editor.recordtree.CurrRecord;
import net.miginfocom.swing.MigLayout;


public class InventarPanel extends JPanel{
  
  protected JPanel invPanel;  
  protected JPanel signaturaPanel;
  protected JPanel buttonsPanel;
  protected JPanel nabavkaPanel;
  protected JPanel ostaloPanel;
  protected JTabbedPane tabbedPane;
  
  protected final int tfLength = 20;   
  protected InventarniBrojPanel invBrojPanel; 
  protected JTextField datumInvTxtFld;  
  protected JTextField datumStatusaTxtFld;
  protected JTextField inventatorTxtFld;
  protected JTextField datumRacunaTxtFld; 
  protected JTextField brojRacunaTxtFld;
  protected JTextField cenaTxtFld;
  protected JTextField dobavljacTxtFld;
  protected JTextField finansijerTxtFld;  
  protected JTextField usmeravanjeTxtFld;
  //napomena  
  protected JTextArea napomeneTxtArea;
  protected JScrollPane napomeneScrollPane;
  
  protected JButton sacuvajButton;
  protected JButton ponistiButton;
  protected JButton raspodelaButton;
  protected JButton printBarcodeButton;
  
  //signatura
  protected JTextField sigDubletTxtFld = new JTextField();
  // signature po UDK - mogucnost preuzimanja podatka iz 675b
  protected JTextField sigUDKTxtFld = new JTextField(tfLength+1);
  protected JButton sigUDK675bButton = new JButton("675b");
  protected JPanel sigUDK675bPanel = new JPanel();
  // numerus curens dugme za preuzimanje iz inventarnog broja 
  protected JTextField sigNumerusCurensTxtFld = new JTextField(tfLength+1);
  protected JButton sigNumerusCurensButton = new JButton();
  protected JPanel sigNumerusCurensPanel = new JPanel();  
  
  // sifrirane vrednosti
  protected CodedValuePanel nacinNabavkePanel;
  protected CodedValuePanel povezPanel;  
  protected CodedValuePanel podlokacijaPanel;
  protected CodedValuePanel formatPanel;
  protected CodedValuePanel intOznakaPanel;
  protected CodedValuePanel odeljenjePanel;
  protected CodedValuePanel invKnjPanel;
  protected CodedValuePanel statusPanel; 
  protected CodedValuePanel dostupnostPanel;
  
  public InventarPanel(){
    setName("inventarPanel");
    tabbedPane = new JTabbedPane();
    invPanel = new JPanel();    
    signaturaPanel = new JPanel();  
    nabavkaPanel = new JPanel();    
    ostaloPanel = new JPanel();
    invBrojPanel = new InventarniBrojPanel();
    datumRacunaTxtFld = new JTextField(tfLength);   
    brojRacunaTxtFld = new JTextField(tfLength);    
    cenaTxtFld = new JTextField(tfLength);    
    datumInvTxtFld = new JTextField(tfLength);
    datumStatusaTxtFld = new JTextField(tfLength);
    inventatorTxtFld = new JTextField(tfLength);
    dobavljacTxtFld = new JTextField(tfLength);   
    finansijerTxtFld = new JTextField(tfLength);
    usmeravanjeTxtFld = new JTextField(tfLength);
    
    
    nacinNabavkePanel = 
      new CodedValuePanel(CodersHelper.NACINNABAVKE_CODER,this);
    povezPanel = 
      new CodedValuePanel(CodersHelper.POVEZ_CODER,this);
    podlokacijaPanel =
      new CodedValuePanel(CodersHelper.PODLOKACIJA_CODER,this);
    formatPanel =
      new CodedValuePanel(CodersHelper.FORMAT_CODER,this);
    intOznakaPanel =
      new CodedValuePanel(CodersHelper.INTERNAOZNAKA_CODER,this);
    odeljenjePanel =
      new CodedValuePanel(CodersHelper.ODELJENJE_CODER,this);
    invKnjPanel =
      new CodedValuePanel(CodersHelper.INVENTARNAKNJIGA_CODER,this);
    statusPanel =
      new CodedValuePanel(CodersHelper.STATUS_CODER,this);
    dostupnostPanel =
      new CodedValuePanel(CodersHelper.DOSTUPNOST_CODER,this);
    
    sigNumerusCurensButton.setIcon(new ImageIcon(InventarPanel.class
				.getResource("/icons/Check16.png")));
    sigNumerusCurensPanel.setLayout(new MigLayout("insets 0 0 0 0","","[]5[]"));
    sigNumerusCurensPanel.add(sigNumerusCurensTxtFld,"grow");
    sigNumerusCurensPanel.add(sigNumerusCurensButton,"grow");
    
    sigUDK675bPanel.setLayout(new MigLayout("insets 0 0 0 0","","[]5[]"));
    sigUDK675bPanel.add(sigUDKTxtFld,"grow");
    sigUDK675bPanel.add(sigUDK675bButton,"grow");
    
    napomeneTxtArea = new JTextArea();    
    
    napomeneScrollPane = new JScrollPane(napomeneTxtArea);
    napomeneScrollPane.setPreferredSize(new Dimension(200,100));
    napomeneScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    napomeneScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    napomeneTxtArea.setLineWrap(true); 
    
    tabbedPane.addTab("Inventar",invPanel);    
    tabbedPane.addTab("Signatura",signaturaPanel);
    tabbedPane.addTab("Nabavka",nabavkaPanel);   
    tabbedPane.addTab("Ostalo", ostaloPanel);
    tabbedPane.setMnemonicAt(0, KeyEvent.VK_I);
    tabbedPane.setMnemonicAt(1, KeyEvent.VK_S);
    tabbedPane.setMnemonicAt(2, KeyEvent.VK_N); 
    tabbedPane.setMnemonicAt(3, KeyEvent.VK_O);
    
    layoutPanels();   
    
    addComponentListener(new ComponentAdapter(){
      public void componentShown(ComponentEvent arg0) {
        tabbedPane.setSelectedIndex(0);    
      }      
    });
    
    nacinNabavkePanel.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(nacinNabavkePanel,arg0);        
      }
    });
    povezPanel.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(povezPanel,arg0);        
      }
    });
    brojRacunaTxtFld.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(brojRacunaTxtFld,arg0);        
      }
    });
    datumRacunaTxtFld.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(datumRacunaTxtFld,arg0);        
      }
    });
    finansijerTxtFld.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(finansijerTxtFld,arg0);        
      }
    });
    dobavljacTxtFld.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(dobavljacTxtFld,arg0);        
      }
    });
    cenaTxtFld.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(cenaTxtFld,arg0);        
      }
    });
    sigUDKTxtFld.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(sigUDKTxtFld,arg0);        
      }
    });
   
    podlokacijaPanel.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(podlokacijaPanel,arg0);        
      }
    });
    sigNumerusCurensTxtFld.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(sigNumerusCurensTxtFld,arg0);        
      }
    });
    formatPanel.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(formatPanel,arg0);        
      }
    });
    intOznakaPanel.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(intOznakaPanel,arg0);        
      }
    });
    sigDubletTxtFld.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(sigDubletTxtFld,arg0);        
      }
    });
    datumInvTxtFld.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(datumInvTxtFld,arg0);        
      }
    });
    odeljenjePanel.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(odeljenjePanel,arg0);        
      }
    });
    invKnjPanel.addKeyListener(new KeyAdapter(){     
      public void keyReleased(KeyEvent arg0) {
        handleKeys(invKnjPanel,arg0);        
      }
    });
    invBrojPanel.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(invBrojPanel,arg0);        
      }
    });
    statusPanel.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(statusPanel,arg0);        
      }
    });
    datumStatusaTxtFld.addKeyListener(new KeyAdapter(){
    	public void keyReleased(KeyEvent arg0) {
        handleKeys(datumStatusaTxtFld,arg0);        
      }    	
    });
    inventatorTxtFld.addKeyListener(new KeyAdapter(){
    	public void keyReleased(KeyEvent arg0) {
        handleKeys(inventatorTxtFld,arg0);        
      }
    });    
    dostupnostPanel.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent arg0) {
        handleKeys(dostupnostPanel,arg0);        
      }
    });
    napomeneTxtArea.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(napomeneTxtArea,arg0);        
      }
    });
    usmeravanjeTxtFld.addKeyListener(new KeyAdapter(){      
      public void keyReleased(KeyEvent arg0) {
        handleKeys(usmeravanjeTxtFld,arg0);        
      }
    });
    
    sigNumerusCurensButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						sigNumerusCurensTxtFld.setText(invBrojPanel.getBroj());				
					}    	
    });    
    
    sigUDK675bButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {				
						sigUDKTxtFld.setText(CurrRecord.getSigUdk());
					}    	
    });
  }
  
  public void initializeForm(){};
  
  public void refreshView(){};
  
  public InventarniBrojPanel getInventarniBrojPanel(){
    return invBrojPanel;
  }
  
  public JTextField getCenaTxtFld() {
    return cenaTxtFld;
  }

  public JTextField getDatumInvTxtFld() {
    return datumInvTxtFld;
  }

  public JTextField getDatumRacunaTxtFld() {
    return datumRacunaTxtFld;
  }

  public CodedValuePanel getFormatPanel() {
    return formatPanel;
  }

  public CodedValuePanel getIntOznakaPanel() {
    return intOznakaPanel;
  }

  public InventarniBrojPanel getInvBrojPanel() {
    return invBrojPanel;
  }

  public CodedValuePanel getInvKnjPanel() {
    return invKnjPanel;
  }

  public CodedValuePanel getNacinNabavkePanel() {
    return nacinNabavkePanel;
  }

  public CodedValuePanel getOdeljenjePanel() {
    return odeljenjePanel;
  }

  public CodedValuePanel getPodlokacijaPanel() {
    return podlokacijaPanel;
  }

  public CodedValuePanel getPovezPanel() {
    return povezPanel;
  }

  public JTextField getSigDubletTxtFld() {
    return sigDubletTxtFld;
  }

  public JTextField getSigNumerusCurensTxtFld() {
    return sigNumerusCurensTxtFld;
  }

  public JTextField getSigUDKTxtFld() {
    return sigUDKTxtFld;
  }

  public CodedValuePanel getStatusPanel() {
    return statusPanel;
  }
  
  public JTextField getDatumStatusaTxtFld(){
  	return datumStatusaTxtFld;
  }
  
  public JTextField getInventatorTxtFld(){
  	return inventatorTxtFld;
  }
  
  public CodedValuePanel getDostupnostPanel() {
    return dostupnostPanel;
  }
  protected void createNabavkaPanel(){   
    nabavkaPanel = new JPanel();
    
    JPanel racunPanel = new JPanel();
    JPanel osnovnoPanel = new JPanel();
    MigLayout racunLayout = new MigLayout("","","[]0[]5[]0[]5[]0[]");
    
    racunPanel.setLayout(racunLayout);
    
    racunPanel.add(new JLabel("Broj ra\u010duna:"),"wrap");
    racunPanel.add(brojRacunaTxtFld,"wrap, span 2, grow");
    
    racunPanel.add(new JLabel("Datum ra\u010duna:"),"wrap");
    racunPanel.add(datumRacunaTxtFld,"wrap,span 2, grow");
    
    racunPanel.add(new JLabel("Cena:"),"wrap");   
    racunPanel.add(cenaTxtFld,"wrap, grow"); 
    
     
    MigLayout layout = new MigLayout("","","[]0[]5[]0[]5[]0[]");
    nabavkaPanel.setLayout(layout);
    
    osnovnoPanel.setLayout(layout);
    osnovnoPanel.add(new JLabel("Na\u010din nabavke:"),"wrap, grow");
    osnovnoPanel.add(nacinNabavkePanel,"wrap, grow");  
    
    osnovnoPanel.add(new JLabel("Finansijer:"),"wrap");
    osnovnoPanel.add(finansijerTxtFld,"wrap, grow");
    
    osnovnoPanel.add(new JLabel("Dobavlja\u010d:"),"wrap");
    osnovnoPanel.add(dobavljacTxtFld,"wrap, grow");
    
    nabavkaPanel.setLayout(new MigLayout("","[]20[]",""));
    nabavkaPanel.add(osnovnoPanel,"grow");
    nabavkaPanel.add(racunPanel,"grow");
    tabbedPane.setComponentAt(2, nabavkaPanel);    
    nabavkaPanel.setFocusCycleRoot(true);  
  } 
  
  protected void createOstaloPanel(){
  	
  	JPanel prviPanel = new JPanel();
  	prviPanel.setLayout(new MigLayout("","","[]0[]10[]0[]"));
  	prviPanel.add(new JLabel("Dostupnost:"),"wrap");
  	prviPanel.add(dostupnostPanel,"grow, wrap");
  	prviPanel.add(new JLabel("Povez:"),"wrap");
    prviPanel.add(povezPanel,"wrap, grow");
    
    JPanel drugiPanel = new JPanel();
    drugiPanel.setLayout(new MigLayout("","","[]0[]"));
    drugiPanel.add(new JLabel("Napomene:"),"wrap");
    drugiPanel.add(napomeneScrollPane,"wrap, grow");
  	
  	ostaloPanel.setLayout(new MigLayout("","[]10[]",""));  	    
    ostaloPanel.add(prviPanel,"grow");
    ostaloPanel.add(drugiPanel,"wrap");
  }

  protected void initializeOdeljenje(){
    odeljenjePanel.setDefaultOdeljenje();
  }

  protected void setEnabledInvBroj(boolean enabled){
    invBrojPanel.setEnabled(enabled);    
    invKnjPanel.setEnabled(enabled);
    //sigNumerusCurensTxtFld.setEnabled(enabled);
  }

  protected void handleKeys(Component comp,KeyEvent ke){
    switch(ke.getKeyCode()){
    case (KeyEvent.VK_ESCAPE):
      Obrada.editorFrame.handleCloseEditor();
      break;
    case(KeyEvent.VK_F2):      
      if(comp instanceof JTextField){
        Date today = new Date();
        String date = InventarConstraints.sdf.format(today);
        ((JTextField)comp).setText(((JTextField)comp).getText()+date);
      }else if(comp instanceof JTextArea){
      Date today = new Date();
      String date = InventarConstraints.sdf.format(today);
     ((JTextArea)comp).setText(((JTextArea)comp).getText()+date);
   }
      break;
    }   
  }
  
  public void loadItem(){};

  private void layoutPanels(){}
  
  
  
}
