package com.ftninformatika.bisis.editor.inventar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ftninformatika.bisis.format.HoldingsDataCoders;
import com.ftninformatika.bisis.format.UItem;
import net.miginfocom.swing.MigLayout;


public class CodedValuePanel extends JPanel {
	
	/**
   * bdimic
   */ 
  private String labelStr;	
	private JTextField codeTxtFld;
	private JTextField valueTxtFld;
	private JButton coderButton;	
	private int sifType;
	
	
	private List<UItem> codesList;
	
	// dodatni simboli koji su dozvoljeni za kucice
	// i koji se nece bijiti u crveno ako se unesu
	// potrebno kod pronalazenja rupa u inventarnim brojevima
	// dozvoljena je i *
	private List<String> allowedSymbols = new ArrayList<String>();
  
  private JPanel parent;
	
	public CodedValuePanel(int sifType,JPanel parent) {
	  this.sifType = sifType;
    this.parent = parent;
    allowedSymbols.clear();
		switch(sifType){
			case(HoldingsDataCoders.NACINNABAVKE_CODER):
				labelStr = "Na\u010din nabavke";
				codesList = HoldingsDataCoders.getCoder(HoldingsDataCoders.NACINNABAVKE_CODER);
				break;
			case(HoldingsDataCoders.POVEZ_CODER):
				labelStr = "Povez";
				codesList = HoldingsDataCoders.getCoder(HoldingsDataCoders.POVEZ_CODER);
				break;
			case(HoldingsDataCoders.PODLOKACIJA_CODER):
				labelStr = "Podlokacija";
				codesList = HoldingsDataCoders.getCoder(HoldingsDataCoders.PODLOKACIJA_CODER);
				break;
			case(HoldingsDataCoders.FORMAT_CODER):
				labelStr = "Format";
				codesList = HoldingsDataCoders.getCoder(HoldingsDataCoders.FORMAT_CODER);
				break;
			case(HoldingsDataCoders.INTERNAOZNAKA_CODER):
				labelStr = "Interna oznaka";
				codesList = HoldingsDataCoders.getCoder(HoldingsDataCoders.INTERNAOZNAKA_CODER);
				break;
			case(HoldingsDataCoders.ODELJENJE_CODER):
				labelStr = "Odeljenje";
				codesList = HoldingsDataCoders.getCoder(HoldingsDataCoders.ODELJENJE_CODER);
				break;
			case(HoldingsDataCoders.STATUS_CODER):
				labelStr = "Status";
				codesList = HoldingsDataCoders.getCoder(HoldingsDataCoders.STATUS_CODER);
				break;
			case(HoldingsDataCoders.INVENTARNAKNJIGA_CODER):
				labelStr = "Inventarna knjiga";
				codesList = HoldingsDataCoders.getCoder(HoldingsDataCoders.INVENTARNAKNJIGA_CODER);		
        break;
      case(HoldingsDataCoders.DOSTUPNOST_CODER):
        labelStr = "Stepen dostupnosti/nedostupnosti";
      codesList = HoldingsDataCoders.getCoder(HoldingsDataCoders.DOSTUPNOST_CODER);
		}		
		create();    
	}

	private void create() {			
		codeTxtFld = new JTextField(2);
		valueTxtFld = new JTextField(18);
		valueTxtFld.setEditable(false);		
		valueTxtFld.setCaretPosition(0);
		coderButton = new JButton(new ImageIcon(getClass().getResource(
        	"/icons/coder.gif")));
		coderButton.setFocusable(false);
		coderButton.setSize(new Dimension(2,2));
		valueTxtFld.setFocusable(false);		
		MigLayout layout = new MigLayout("insets 0 0 0 0","","[b]");
		setLayout(layout);	
		this.add(codeTxtFld, "grow");		
		this.add(valueTxtFld,"grow");		
		this.add(coderButton);	
		coderButton.setToolTipText("\u0160ifanik");
		coderButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleOpenCoder();				
			}			
		});		
		codeTxtFld.addFocusListener(new FocusListener(){
			public void focusLost(FocusEvent e){
				handleUpdateValue();				
			}

			public void focusGained(FocusEvent e) {
				handleUpdateValue();				
			}			
		});    
		codeTxtFld.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				handleKeys(e);
			}
		});		
	}
	
	private void handleUpdateValue() {
		String code = codeTxtFld.getText();
		if(!code.equals("")){
			if(!getValue(code).equals("") || allowedSymbols.contains(code)){
				valueTxtFld.setText(getValue(code));
				valueTxtFld.setCaretPosition(0);
				codeTxtFld.setForeground(Color.BLACK);
				setForInv(code);					
			}else{			
				codeTxtFld.setForeground(Color.RED);
								
			}
		}else{
			//valueTxtFld.setText("");
			codeTxtFld.setForeground(Color.BLACK);
			//setForInv("");			
		}		
	}

	private String getValue(String code){
		for(int i=0;i<codesList.size();i++){
			if(codesList.get(i).getCode().equals(code))
				return codesList.get(i).getValue();
		}
		return "";
	}
	
	private void handleOpenCoder(){
		InventarCodeChoiceDialog ccd = null;
		ccd = new InventarCodeChoiceDialog(labelStr+"- \u0161ifanik",codesList);
		if(ccd!=null){
			ccd.setVisible(true);
			if(ccd.getSelectedCode()!=null){
				codeTxtFld.setText(ccd.getSelectedCode());
				valueTxtFld.setText(getValue(ccd.getSelectedCode()));
				valueTxtFld.setCaretPosition(0);
				codeTxtFld.setForeground(Color.BLACK);
				setForInv(ccd.getSelectedCode());
			}	
			else{
			//	codeTxtFld.setText("");
				valueTxtFld.setText("");
			//	setForInv("");
				
			}
			ccd.setVisible(false);
		}
	}
	
	private void handleKeys(KeyEvent ke){
		codeTxtFld.setForeground(Color.black);
		valueTxtFld.setText("");
		if(ke.getKeyCode()==KeyEvent.VK_F9)
			handleOpenCoder();
	}
	
	public String getCode(){		
		return codeTxtFld.getText();		
	}
  
  //ako nema odeljenje stavljamo neku default vrednost
  public void setDefaultOdeljenje(){
    if(sifType==HoldingsDataCoders.ODELJENJE_CODER 
        && !InventarConstraints.imaOdeljenja){
      codeTxtFld.setText("00");   
      valueTxtFld.setText("Nema odeljenja");
      codeTxtFld.setForeground(Color.BLACK);
      setForInv("00");
      setEnabled(false);
    }
  }
	
	public void setCode(String code){
    if(sifType==HoldingsDataCoders.ODELJENJE_CODER 
        && !InventarConstraints.imaOdeljenja){
      setDefaultOdeljenje();      
    }else{
  		codeTxtFld.setText(code);		
  		valueTxtFld.setText(getValue(code));
  		valueTxtFld.setCaretPosition(0);
  		setForInv(code);
  		if(getValue(code).equals("") && !allowedSymbols.contains(code))
  			codeTxtFld.setForeground(Color.RED);
  		else
  			codeTxtFld.setForeground(Color.BLACK);
     }
	}
	
	private void setForInv(String code){		
    if(parent instanceof InventarPanel){
    	if(sifType==HoldingsDataCoders.ODELJENJE_CODER)       
       if(((InventarPanel)parent).getInventarniBrojPanel().getOdeljenje().equals("")){
       		((InventarPanel)parent).getInventarniBrojPanel().setOdeljenje(code);
       }
    	if(sifType==HoldingsDataCoders.INVENTARNAKNJIGA_CODER)
        ((InventarPanel)parent).getInventarniBrojPanel().setInvKnj(code);        
    }else if(parent instanceof SveskePanel){
      if(sifType==HoldingsDataCoders.ODELJENJE_CODER)       
        ((SveskePanel)parent).getInvBrojPanel().setOdeljenje(code);      
      if(sifType==HoldingsDataCoders.INVENTARNAKNJIGA_CODER)
      	((SveskePanel)parent).getInvBrojPanel().setInvKnj(code);     
    }
	}	
  
  // vraca true ako nista nije uneto i ako je uneta dobrasifra
  public boolean isValidCode(){
    if(codeTxtFld.getText().equals("")) return true;
    return !getValue(codeTxtFld.getText()).equals("");
  }
	
  
  public void setEnabled(boolean enabled){ 
    codeTxtFld.setEnabled(enabled);
    valueTxtFld.setEnabled(enabled);
    coderButton.setEnabled(enabled);    
  }
  
  public Component getComponentForFocus(){
    return codeTxtFld;
  }  
  
  public synchronized void addKeyListener(KeyListener arg0) {
    codeTxtFld.addKeyListener(arg0);    
  }
  
  
  public void requestFocus() {   
    codeTxtFld.requestFocus();
  }  
  
  public boolean isEnabled() {    
    return codeTxtFld.isEnabled();
  }
  
  public void addAllowedSymbol(String symbol){
  	allowedSymbols.add(symbol);
  	
  }
  

}
