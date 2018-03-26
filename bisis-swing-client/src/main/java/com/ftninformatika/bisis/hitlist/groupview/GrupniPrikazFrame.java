package com.ftninformatika.bisis.hitlist.groupview;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.html.HTMLEditorKit;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.cards.Report;
import com.ftninformatika.bisis.circ.view.ReportResults;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordFactory;
import com.ftninformatika.utils.sort.SortUtils;
import net.miginfocom.swing.MigLayout;


public class GrupniPrikazFrame extends JInternalFrame {
	
	private static final String branchesString = "Branches";
	private static final String branchesNabavkaString = "Izve\u0161taj po nabavci";
	private static final String branchesDatumInvString = "Izve\u0161taj po datumu inventarisanja";
	private static final String branchesPovezString = "Izve\u0161taj po vrsti poveza";
	private static final String branchesJezikString = "Izve\u0161taj po jezicima";
	private static final String branchesPeriodString = "Izve\u0161taj po periodu";
	private static final String branchesRevizijaString = "Izve\u0161taj za reviziju";
	private static final String allCardsString = "Listi\u0107";
	private static final String allCardsWithoutInvString = "Listi\u0107 bez inventara";	
	private static final String fullFormatString = "Full format";
	private static final String sort1= "Naslov";
	private static final String sort2= "Autor";
	private static final String sort3= "Godina";
	private static final String sort4= "Lokacija";
	private static final String countString = "Brojanje polja";
	
	private static final int MAX_RECORDS = 100;

	public GrupniPrikazFrame(String query, String [] hits){
		super("Grupni prikaz na osnovu upita "+query, true, true, true, true);
		this.hits=hits;
		this.query=query;
		initialize();
	}
	
	public GrupniPrikazFrame(){
		
	}	
	
	private void initialize(){
		    setResizable(false); 
		    setSize(900,660);
	      showButton = new JButton("Prika\u017ei");    
		    choicePanel = new JPanel();		  
		    odTxtFld.setText("1");
		    doTxtFld.setText(String.valueOf(hits.length));
		    MigLayout migLayout = new MigLayout("","","[]10[]20[]");
		    choicePanel.setLayout(migLayout);		    
		    choicePanel.add(new JLabel("Vrsta prikaza: "),"split 7");
		    loadReports();
		    choicePanel.add(reportTypesCmbBox);
		    choicePanel.add(new JLabel("od"));
		    choicePanel.add(odTxtFld);
		    choicePanel.add(new JLabel("do"));
		    choicePanel.add(doTxtFld);		    	      
		    choicePanel.add(showButton,"wrap");
		    choicePanel.add(criteriaLabel,"span 1, split 5");		    
		    choicePanel.add(getCmbsort1());
		    choicePanel.add(getCmbsort2());
		    choicePanel.add(getCmbsort3());
		    choicePanel.add(getCmbsort4(),"wrap");		    
		    loadSortCriteriaCombos();
		    choicePanel.add(fieldNameLabel,"span 1, split 2");
		    choicePanel.add(fieldNameText);	
		    editorPane = new JEditorPane();	
		    editorPane.setEditable(false);
		    editorPane.setEditorKit(new HTMLEditorKit());
		    cardsScrollPane = new JScrollPane(editorPane);
		    
		    
		    setLayout(new GridBagLayout());
		    GridBagConstraints c = new GridBagConstraints();
		    c.gridx = 0;
		    c.gridy = 0;
		    c.weightx = 1;
		    c.weighty = 0.01;
		    c.fill = GridBagConstraints.BOTH;
		    add(choicePanel,c);
		    c.gridy = 1;
		    c.weighty = 0.99;
		    add(jasperPanel,c);
		    add(cardsScrollPane,c);    
		    prikaziPanel(false);		        
		    setEnabledSort(false);		    
	  	  showButton.addActionListener(new ActionListener(){
	  	  	public void actionPerformed(ActionEvent arg0) {
	  	  		handleGrupniPrikaz();		    	
	  	  	} 		 
	  	  });	  
	  	  reportTypesCmbBox. addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {						
						handleComboStatusChanged();
					}	  	  	
	  	  });
	}
	
	private void prikaziPanel(boolean jasper){
		if(jasper){
			jasperPanel.setVisible(true);
			cardsScrollPane.setVisible(false);
		}else{
			jasperPanel.setVisible(false);
			cardsScrollPane.setVisible(true);
			editorPane.setCaretPosition(0);
		}
	}
	
	private void setEnabledSort(boolean enabled){
		cmbsort1.setVisible(enabled);
		cmbsort2.setVisible(enabled);
		cmbsort3.setVisible(enabled);
		cmbsort4.setVisible(enabled);
		criteriaLabel.setVisible(enabled);
	
	}
	
	/*
	 * proverava isptavnost brojeva u tekstualnim 
	 * poljima za unos opsega zapisa
	 * i postavlja difoltne vrednosti ako 
	 * nije unet dobar opseg
	 */
	private void validateOdDo(){
		//od
		String tekstOd = odTxtFld.getText().trim();
		int intOd = 1;
		try{
			intOd = Integer.parseInt(tekstOd);
		}catch(NumberFormatException e){
			odTxtFld.setText("1");
		}
		if(intOd<1)
			odTxtFld.setText("1");		
		//do
		String tekstDo = doTxtFld.getText().trim();
		int intDo = 1;
		try{
			intDo = Integer.parseInt(tekstDo);
		}catch(NumberFormatException e){
			doTxtFld.setText(String.valueOf(hits.length));
		}
		System.out.println("intOd "+intOd);
		System.out.println("intDo "+intDo);
		
		if(intDo>hits.length)
			doTxtFld.setText(String.valueOf(hits.length));
		
		if(intDo-intOd>MAX_RECORDS)
			doTxtFld.setText(String.valueOf(intOd+MAX_RECORDS));
		
		if(intOd>intDo){
			odTxtFld.setText("1");
			doTxtFld.setText(String.valueOf(getMin(hits.length, MAX_RECORDS)));
		}
		
			
	}
	
	private static int getMin(int prvi, int drugi){
		if(prvi>drugi) return drugi;
		else return prvi;
	}
	
  
	private void handleGrupniPrikaz(){
		validateOdDo();
		String selectedReport = reportTypesCmbBox.getSelectedItem().toString();
		int odInt = Integer.parseInt(odTxtFld.getText().trim());
		int doInt = Integer.parseInt(doTxtFld.getText().trim());
		Record[] records = new Record[doInt-odInt+1];
		int j=0;
		for(int i=odInt-1;i<doInt;i++){
			try {
				records[j] = BisisApp.getRecordManager().getRecord(hits[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			j++;  			
		}
  	if(selectedReport.equals(branchesString)){  
  	  jasperPanel.setJasper(PogodciPoOgrancima.execute(hits, query));
      prikaziPanel(true);  
      validate(); 
  	}else if(selectedReport.equals(branchesNabavkaString)){  	  
      	  jasperPanel.setJasper(NabavkaPoNacinu.execute(hits, query));
          prikaziPanel(true);
          validate(); 
  	}else if(selectedReport.equals(branchesDatumInvString)){  	  
      	  jasperPanel.setJasper(PogodciPoDatumuInventara.execute(hits, query));
          prikaziPanel(true);        
          validate(); 
  	}else if(selectedReport.equals(branchesPovezString)){  	  
          jasperPanel.setJasper(PogodciPoPovezu.execute(hits, query));
          prikaziPanel(true);
          validate(); 
  	}else if(selectedReport.equals(branchesJezikString)){  	  
    	  jasperPanel.setJasper(PogodciPoJezicima.execute(hits, query));
          prikaziPanel(true);
          validate(); 
  	}else if(selectedReport.equals(branchesPeriodString)){  	  
        	  jasperPanel.setJasper(PogodciPoPeriodu.execute(hits, query));
              prikaziPanel(true);
              validate(); 
      	
  	}else if(selectedReport.equals(branchesRevizijaString)){  	  
  	  jasperPanel.setJasper(PogodciPoInvNapomeni.execute(hits, query));
      prikaziPanel(true);
      validate(); 
  	}else if(selectedReport.equals(countString)){  
    	  
      	 jasperPanel.setJasper(CountFields.execute(hits, query,fieldNameText.getText()));
          prikaziPanel(true);
          validate(); 
  	}else{
  		Vector <String> pref=getSortPrefix();
  		records = SortUtils.sortRecord(records, pref);
  		if(selectedReport.equals(allCardsString))
  			editorPane.setText(Report.makeMulti(records,true));
  		else if (selectedReport.equals(allCardsWithoutInvString))
  			editorPane.setText(Report.makeMulti(records,false));  		  		
  	  else if(selectedReport.equals(fullFormatString)){  		
  	  	String allFullFormat = "";
  	  	for(Record rec:records){
  	  		allFullFormat += RecordFactory.toFullFormat(rec.getPubType(), rec, true);
  	  		allFullFormat +="<BR><BR>";
  	  	}
  	  	editorPane.setText(allFullFormat);
  	  }
  		prikaziPanel(false);  		
  	}
	}	
	public Vector getSortPrefix(){
		Vector<String> pref=new Vector <String>();
		String sort1=(String)getCmbsort1().getSelectedItem();
		String sort2=(String)getCmbsort2().getSelectedItem();
		String sort3=(String)getCmbsort3().getSelectedItem();
		String sort4=(String)getCmbsort4().getSelectedItem();
		pref.add(convertToPrefix(sort1));
		pref.add(convertToPrefix(sort2));
		pref.add(convertToPrefix(sort3));
		pref.add(convertToPrefix(sort4));
		return pref;
	}
	public String convertToPrefix(String str){
		if (str.equals(sort1))
			return "TI";
		else if (str.equals(sort2))
			return "AU";
		else if (str.equals(sort3))
			return "PY";
		
			return "SG";
		
	}
	private JComboBox getCmbsort1() {
		if (cmbsort1 == null) {
			cmbsort1 = new JComboBox();
			cmbsort1.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent arg0) { 
		    	  cmbsort2.removeAllItems();
		    	    for (int i=0;i<lista.length;i++) {
		    	      if (lista[i].equals(cmbsort1.getSelectedItem()));
		    	      else
		    	    	  cmbsort2.addItem(lista[i]);
		    	    }
				
		      }
			});
			//cmbsort1.setVisible(false);			
		}
		return cmbsort1;
	}
	
	private JComboBox getCmbsort2() {
		if (cmbsort2 == null) {
			cmbsort2 = new JComboBox();
			cmbsort2.addActionListener(new ActionListener(){
				 public void actionPerformed(ActionEvent arg0) { 
			    	  cmbsort3.removeAllItems();
			    	   for (int i=0;i<lista.length;i++) {
				    	      if (lista[i].equals(cmbsort1.getSelectedItem()) ||
				    	        lista[i].equals(cmbsort2.getSelectedItem()));
				    	      else
				    	    	  cmbsort3.addItem(lista[i]);
					         }
				 	}
				});
			//cmbsort2.setVisible(false);			
		}
		return cmbsort2;
	}
	
	private JComboBox getCmbsort3() {
		if (cmbsort3 == null) {
			cmbsort3 = new JComboBox();
			cmbsort3.addActionListener(new ActionListener(){
			      public void actionPerformed(ActionEvent arg0) { 
			    	  cmbsort4.removeAllItems();
			    	    for (int i=0;i<lista.length;i++) {
			    	      if (lista[i].equals(cmbsort1.getSelectedItem()) ||
			    	        lista[i].equals(cmbsort2.getSelectedItem()) ||
			    	        lista[i].equals(cmbsort3.getSelectedItem()));
			    	      else
			    	    	  cmbsort4.addItem(lista[i]);
			    	    }
				}
				});
			//cmbsort3.setVisible(false);			
		}
		return cmbsort3;
	}
	
	private JComboBox getCmbsort4() {
		if (cmbsort4 == null) {
			cmbsort4 = new JComboBox();
			//cmbsort4.setVisible(false);
			
		}
		return cmbsort4;
	}
	private void loadSortCriteriaCombos(){
		
		for (int i=0;i<lista.length;i++) {
			 cmbsort1.addItem(lista[i]); 
		   if (i!=0)
		     cmbsort2.addItem(lista[i]); 
		   if (i!=0 && i!= 1)
		  	  cmbsort3.addItem(lista[i]); 
		   if (i!=0 && i!= 1 && i!=2)
		  	  cmbsort4.addItem(lista[i]);
		   }
	}
		
	
	
	private void loadReports(){
		reportTypesCmbBox=new JComboBox();
		reportTypesCmbBox.addItem(countString);
		reportTypesCmbBox.addItem(branchesString);
		reportTypesCmbBox.addItem(branchesNabavkaString);
		reportTypesCmbBox.addItem(branchesDatumInvString);
		reportTypesCmbBox.addItem(branchesPovezString);
		reportTypesCmbBox.addItem(branchesJezikString);
		reportTypesCmbBox.addItem(branchesPeriodString);
		reportTypesCmbBox.addItem(branchesRevizijaString);
		reportTypesCmbBox.addItem(allCardsString);
		reportTypesCmbBox.addItem(allCardsWithoutInvString);
		reportTypesCmbBox.addItem(fullFormatString);		
	}
	
	private void showReport(){	
				
	}
	
	private void handleComboStatusChanged(){		
		String value = reportTypesCmbBox.getSelectedItem().toString();
		if(value.startsWith("Izve\u0161taj")||value.startsWith("Branches")){
			setEnabledSort(false);
			fieldNameLabel.setVisible(false);
  	        fieldNameText.setVisible(false);
		}else if(value.startsWith("Brojanje")){
			fieldNameLabel.setVisible(true);
  	        fieldNameText.setVisible(true);
  	        setEnabledSort(false);
		}
		else{	
			setEnabledSort(true);
			fieldNameLabel.setVisible(false);
  	        fieldNameText.setVisible(false);
		}
	}

	private ReportResults jasperPanel = new ReportResults();
	private JScrollPane cardsScrollPane;
	private JComboBox reportTypesCmbBox;
	private JPanel choicePanel= new JPanel();	
	private JTextField odTxtFld = new JTextField(3);
	private JTextField doTxtFld = new JTextField(3);
	
	private String query;
	private String []hits;
	
	private JEditorPane editorPane;	
	private JButton showButton;
	private JComboBox cmbsort1 = null;
	private JComboBox cmbsort2 = null;
	private JComboBox cmbsort3 = null;
	private JComboBox cmbsort4 = null;
	private JLabel criteriaLabel=new JLabel("Kriterijumi za sortiranje: ");
	private JLabel fieldNameLabel=new JLabel("Nazivi polja koja se broje: ");
	private JTextField fieldNameText=new JTextField(25);
	private String[] lista = {sort1,sort2,sort3,sort4};
}