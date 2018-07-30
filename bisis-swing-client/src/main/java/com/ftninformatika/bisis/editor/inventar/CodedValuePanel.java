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
import java.text.MessageFormat;import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.coders.CodersHelper;
import com.ftninformatika.bisis.format.UItem;
import com.ftninformatika.utils.Messages;
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
    	initCodesLists();
		create();    
	}

	private void initCodesLists() {

		switch(sifType) {
			case (CodersHelper.NACINNABAVKE_CODER):
				labelStr = Messages.getString("EDITOR_ACQ_TYPE");
				codesList = BisisApp.appConfig.getCodersHelper().getCoder(CodersHelper.NACINNABAVKE_CODER);
				codesList.sort(Comparator.comparing(c -> c.getCode()));
				break;
			case (CodersHelper.POVEZ_CODER):
				labelStr = Messages.getString("BINDING");
				codesList = BisisApp.appConfig.getCodersHelper().getCoder(CodersHelper.POVEZ_CODER);
				codesList.sort(Comparator.comparing(c -> c.getCode()));
				break;
			case (CodersHelper.PODLOKACIJA_CODER):
				labelStr = Messages.getString("SUBLOCATION");
				codesList = BisisApp.appConfig.getCodersHelper().getCoder(CodersHelper.PODLOKACIJA_CODER);
				codesList.sort(Comparator.comparing(c -> c.getCode()));
				break;
			case (CodersHelper.FORMAT_CODER):
				labelStr = Messages.getString("FORMAT");
				codesList = BisisApp.appConfig.getCodersHelper().getCoder(CodersHelper.FORMAT_CODER);
				codesList.sort(Comparator.comparing(c -> c.getCode()));
				break;
			case (CodersHelper.INTERNAOZNAKA_CODER):
				labelStr = Messages.getString("INTERNAL_MARK");
				codesList = BisisApp.appConfig.getCodersHelper().getCoder(CodersHelper.INTERNAOZNAKA_CODER);
				codesList.sort(Comparator.comparing(c -> c.getCode()));
				break;
			case (CodersHelper.ODELJENJE_CODER):
				labelStr = Messages.getString("LOCATION");
				codesList = BisisApp.appConfig.getCodersHelper().getCoder(CodersHelper.ODELJENJE_CODER);
				codesList.sort(Comparator.comparing(c -> c.getCode()));
				break;
			case (CodersHelper.STATUS_CODER):
				labelStr = Messages.getString("STATUS");
				codesList = BisisApp.appConfig.getCodersHelper().getCoder(CodersHelper.STATUS_CODER);
				codesList.sort(Comparator.comparing(c -> c.getCode()));
				break;
			case (CodersHelper.INVENTARNAKNJIGA_CODER):
				labelStr = Messages.getString("INV_BOOK");
				codesList = BisisApp.appConfig.getCodersHelper().getCoder(CodersHelper.INVENTARNAKNJIGA_CODER);
				codesList.sort(Comparator.comparing(c -> c.getCode()));
				break;
			case (CodersHelper.DOSTUPNOST_CODER):
				labelStr = Messages.getString("AVAILABILITY");
				codesList = BisisApp.appConfig.getCodersHelper().getCoder(CodersHelper.DOSTUPNOST_CODER);
				codesList.sort(Comparator.comparing(c -> c.getCode()));
		}

	}

	private void create() {			
		codeTxtFld = new JTextField(3);
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
		coderButton.setToolTipText(Messages.getString("CODER"));
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
		initCodesLists();
		InventarCodeChoiceDialog ccd = null;
		ccd = new InventarCodeChoiceDialog(MessageFormat.format(Messages.getString("0.CODER"), labelStr),codesList);
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
    if(sifType==CodersHelper.ODELJENJE_CODER
        && !InventarConstraints.imaOdeljenja){
      codeTxtFld.setText("00");   
      valueTxtFld.setText(Messages.getString("NO_LOCATION"));
      codeTxtFld.setForeground(Color.BLACK);
      setForInv("00");
      setEnabled(false);
    }
  }
	
	public void setCode(String code){
    if(sifType==CodersHelper.ODELJENJE_CODER
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
    	if(sifType==CodersHelper.ODELJENJE_CODER)
		   if(((InventarPanel)parent).getInventarniBrojPanel().getOdeljenje().equals("")){
				((InventarPanel)parent).getInventarniBrojPanel().setOdeljenje(code);
		   }
    	if(sifType==CodersHelper.INVENTARNAKNJIGA_CODER)
        	((InventarPanel)parent).getInventarniBrojPanel().setInvKnj(code);
    }else if(parent instanceof SveskePanel){
      if(sifType==CodersHelper.ODELJENJE_CODER)
        ((SveskePanel)parent).getInvBrojPanel().setOdeljenje(code);      
      if(sifType==CodersHelper.INVENTARNAKNJIGA_CODER)
      	((SveskePanel)parent).getInvBrojPanel().setInvKnj(code);     
    }
	}	
  
  // vraca true ako nista nije uneto i ako je uneta dobra sifra
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
