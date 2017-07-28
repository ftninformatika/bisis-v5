/**
 * 
 */
package com.ftninformatika.bisis.editor.inventar;




import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.format.HoldingsDataCoders;
import com.ftninformatika.bisis.format.UValidatorException;
import com.ftninformatika.bisis.records.Primerak;
import net.miginfocom.swing.MigLayout;


/**
 * @author Bojana
 *
 */
public class RaspodelaFrame extends JInternalFrame {

	
	private JTable raspodelaTable;	
	private JScrollPane raspodelaScrollPane;
	
	private JPanel raspodelaButtonsPanel;	
	private JButton sacuvajButton;
	private JButton odustaniButton;
	
	private JTextField brojPrimTxtFld;
	private JTextField preostaloTxtFld;
	private CodedValuePanel odeljenjePanel;
	private CodedValuePanel invKnjPanel;
	private CodedValuePanel podlokacijaPanel;
	
	private JSpinner raspodelaSpinner;
	private JButton dodajButton;
	
	private InventarPanel inventarPanel;	
	private RaspodelaTableModel raspodelaTableModel;
	
  private boolean monograph = false;
	
	
	public RaspodelaFrame(InventarPanel mp) {
    super("Raspodela primeraka", true, true, false, false);
    this.monograph = mp instanceof MonographInventarPanel;
    if(!monograph) setTitle("Raspodela godina");
		
		this.inventarPanel = mp;
		this.setSize(new Dimension(800,400));
		create();		
	}
	
	private void create(){		
		raspodelaTable = new JTable();
		brojPrimTxtFld = new JTextField();
		preostaloTxtFld = new JTextField();
		dodajButton = new JButton(new ImageIcon(RaspodelaFrame.class
				.getResource("/icons/Check16.png")));
		preostaloTxtFld.setEditable(false);
		preostaloTxtFld.setFocusable(false);    
		odeljenjePanel = new CodedValuePanel(HoldingsDataCoders.ODELJENJE_CODER,null);
		odeljenjePanel.setDefaultOdeljenje();
		invKnjPanel = new CodedValuePanel(HoldingsDataCoders.INVENTARNAKNJIGA_CODER,null);
		podlokacijaPanel = new CodedValuePanel(HoldingsDataCoders.PODLOKACIJA_CODER, null);
		raspodelaSpinner = new JSpinner();		
		raspodelaSpinner.setValue(new Integer(1));
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0,0,1000,1);
		raspodelaSpinner.setModel(spinnerModel);
		raspodelaSpinner.setPreferredSize(new Dimension(40,20));
		
		//raspodelaSpinner.set
		
		
		raspodelaTableModel = new RaspodelaTableModel(this);
		raspodelaTable.setModel(raspodelaTableModel);		
		raspodelaTable.doLayout();
		raspodelaScrollPane = new JScrollPane(raspodelaTable);
		
		raspodelaButtonsPanel = new JPanel();
		sacuvajButton = new JButton("Raspodeli");
		sacuvajButton.setIcon(new ImageIcon(getClass().getResource(
        "/icons/ok.gif")));
		sacuvajButton.setEnabled(false);
		odustaniButton = new JButton("Odustani");
		odustaniButton.setIcon(new ImageIcon(getClass().getResource(
        "/icons/remove.gif")));
		raspodelaButtonsPanel.setLayout(new GridBagLayout());
		GridBagConstraints cB = new GridBagConstraints();
		cB.gridx = 0;
		cB.gridy = 0;
		cB.weightx = 0.1;
		raspodelaButtonsPanel.add(sacuvajButton,cB);
		initialize();
		
		MigLayout layout = new MigLayout("","[][]20[]","[][]30[]0[]10[]0[]10[][]");
		setLayout(layout);
		
		add(new JLabel("Broj knjiga za raspodelu:"),"align right");		
		add(brojPrimTxtFld,"wrap, width :30: ");		
		add(new JLabel("Preostalo:"),"align right");		
		add(preostaloTxtFld,"wrap, width :30:");
	
		add(new JLabel("Odeljenje:"),"cell 0 2 2 1");
		add(odeljenjePanel,"cell 0 3 2 1");
		add(new JLabel("Inventarna knjiga:"),"cell 0 4 2 1");
		add(invKnjPanel,   "cell 0 5 2 1");
		add(new JLabel("Podlokacija:"),"cell 0 6 2 1");
		add(podlokacijaPanel,   "cell 0 7 2 1");
		
		JPanel brPrim = new JPanel();
		brPrim.setLayout(new MigLayout());
		brPrim.add(new JLabel("Broj primeraka:"));
		brPrim.add(raspodelaSpinner,"growy");
		brPrim.add(dodajButton);		
		add(brPrim,"cell 0 8 2 1");		
		add(raspodelaScrollPane,"cell 2 0 1 7, grow");		
		JPanel buttonsPanel = new JPanel();		
		buttonsPanel.add(sacuvajButton);
		buttonsPanel.add(odustaniButton);
		add(buttonsPanel,"cell 2 9 1 1, align right");
    
    RaspodelaFocusTraversalPolicy policy = new RaspodelaFocusTraversalPolicy();
    setFocusTraversalPolicy(policy);
		
		
		
		//actions
		brojPrimTxtFld.addFocusListener(new FocusAdapter(){
			public void focusLost(FocusEvent e) {
				handleSetPreostalo();
				
			}			
		});
		
		dodajButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleAddPrimerak();			
			}
			
		});
		
		ListSelectionModel lSelModel = raspodelaTable.getSelectionModel();
		lSelModel.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {				
					handleLoadPrimerak();				
			}			
		});
		
		
		raspodelaTable.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				handleKeys(e);
			}			
		});		
		
		sacuvajButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleRaspodeli();				
			}			
		});	
		
		odustaniButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleClose();				
			}			
		});
	}
	
	private void initialize(){
		brojPrimTxtFld.setText("0");
	}
	

	private void handleAddPrimerak() {	
		Primerak primerak = ((MonographInventarPanel)inventarPanel).getPrimerakFromForm();
		try {
			if (odeljenjePanel.getCode().equals("") || odeljenjePanel.getCode().equals("")) 
				throw new RaspodelaException("Nisu uneti svi podaci za raspodelu!");
			primerak.setOdeljenje(odeljenjePanel.getCode());
			String invKnj = invKnjPanel.getCode();
			primerak.setInvBroj(primerak.getOdeljenje()+invKnj+"#######");		
			if(!podlokacijaPanel.getCode().equals(""))
				primerak.setSigPodlokacija(podlokacijaPanel.getCode());
			addPrimerak(primerak,(Integer)raspodelaSpinner.getValue());
		} catch (RaspodelaException e) {
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
					e.getMessage(),"Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
			raspodelaSpinner.setValue(new Integer(1));
		}		
	}

	private void handleSetPreostalo() {
		try{
			
			int brPrimeraka = Integer.parseInt(brojPrimTxtFld.getText());
			preostaloTxtFld.setText(brPrimeraka-raspodelaTableModel.getZbirRaspodeljenih()+"");
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Unesite broj knjiga za raspodelu!","Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
		}	
	}

	private void handleLoadPrimerak(){
		if(raspodelaTable.getSelectedRow()!=-1 && raspodelaTable.getSelectedRow()<raspodelaTableModel.getRowCount()){			
			Primerak p = (Primerak)raspodelaTableModel.getRow(raspodelaTable.getSelectedRow())[0];							
			raspodelaSpinner.setValue(raspodelaTableModel.getRow(raspodelaTable.getSelectedRow())[1]);
			odeljenjePanel.setCode(p.getOdeljenje());
			invKnjPanel.setCode(p.getInvBroj().substring(2,4));
		}
		
	}
	
	public int getPreostalo(){
		return Integer.parseInt(preostaloTxtFld.getText());
	}
	
	public void setPreostalo(int preostalo){
		preostaloTxtFld.setText(""+preostalo);
	}
	
	public void setVisible(boolean visible){
		if(!this.isVisible())
			if(visible){
				Dimension screen = getToolkit().getScreenSize();
				setLocation((screen.width - getSize().width) / 2,
						(screen.height - getSize().height) / 2);
			}
	    super.setVisible(visible);
	}

	private void addPrimerak(Primerak p, Integer brPrimeraka) throws RaspodelaException{		
		raspodelaTableModel.updatePrimerak(p,brPrimeraka);
		if(getPreostalo()==0)
			sacuvajButton.setEnabled(true);
		else sacuvajButton.setEnabled(false);	
	}
	
	private void handleRaspodeli(){
		try {
			raspodelaTableModel.generisiPrimerkePremaRaspodeli();
		} catch (UValidatorException e) {
			e.printStackTrace();
		}
		dispose();
	}
  
  public void updatePrimerciTable(){
    inventarPanel.refreshView();
  }
	
	private void handleDeletePrimerak(){
		if(raspodelaTable.getSelectedRowCount()==0) return;
		else{
			int[] rows = raspodelaTable.getSelectedRows();
			for(int i=rows.length-1;i>=0;i--){
				raspodelaTableModel.deletePrimerak(rows[i]);				
			}
		}			
	}
	
	private void handleKeys(KeyEvent e){
		switch(e.getKeyCode()){
		case(KeyEvent.VK_DELETE): handleDeletePrimerak();
		case(KeyEvent.VK_ESCAPE): raspodelaTable.clearSelection();
		}
	}
	
	protected void handleClose() {
		this.setVisible(false);
		this.dispose();		
	}
  
  class RaspodelaFocusTraversalPolicy extends FocusTraversalPolicy{
   
    public Component getComponentAfter(Container arg0, Component arg1) {
      if(arg1.equals(brojPrimTxtFld))
        if(InventarConstraints.imaOdeljenja)
          return odeljenjePanel.getComponentForFocus();
        else
          return invKnjPanel.getComponentForFocus();
      if(arg1.equals(odeljenjePanel.getComponentForFocus()))
        return invKnjPanel.getComponentForFocus();
      if(arg1.equals(invKnjPanel.getComponentForFocus()))
        return raspodelaSpinner.getEditor();
      if(arg1.equals(raspodelaSpinner))
        return brojPrimTxtFld;
      return null;
    }
  
    public Component getComponentBefore(Container arg0, Component arg1) {
      if(arg1.equals(brojPrimTxtFld))        
        return raspodelaSpinner.getEditor();        
      if(arg1.equals(odeljenjePanel.getComponentForFocus()))
        return brojPrimTxtFld;
      if(arg1.equals(invKnjPanel.getComponentForFocus()))
        if(InventarConstraints.imaOdeljenja)
          return odeljenjePanel.getComponentForFocus();         
        else
          return brojPrimTxtFld;          
      if(arg1.equals(raspodelaSpinner))
        return invKnjPanel.getComponentForFocus();        
      return null;
    }
    
    public Component getDefaultComponent(Container arg0) {      
      return brojPrimTxtFld;
    }
    
    public Component getFirstComponent(Container arg0) {      
      return brojPrimTxtFld;
    }
  
    public Component getLastComponent(Container arg0) {     
      return null;
    }
    
  }
	
	
	

}
