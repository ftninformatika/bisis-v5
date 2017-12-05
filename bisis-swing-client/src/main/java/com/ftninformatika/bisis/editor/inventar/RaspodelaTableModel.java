/**
 * 
 */
package com.ftninformatika.bisis.editor.inventar;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.bisis.editor.recordtree.CurrRecord;
import com.ftninformatika.bisis.editor.recordtree.RecordUtils;
import com.ftninformatika.bisis.format.HoldingsDataCoders;
import com.ftninformatika.bisis.format.UValidatorException;
import com.ftninformatika.bisis.records.Primerak;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


/**
 * @author Bojana
 *
 */
public class RaspodelaTableModel extends AbstractTableModel {

	// klasa definise model tabele raspodele inv brojeva, na osnovu koje ce se generisati
	// skup inventarnih brojeva
	
	private String[] columns;
	private List<Object[]> raspodela = new ArrayList<Object[]>();
	
	
	private RaspodelaFrame frame;
	
	
	public RaspodelaTableModel(RaspodelaFrame rf) {
		super();
		frame = rf;
		columns = new String[4];
		columns[0] = "Одељење";
		columns[1] = "Инвентарна књига";
		columns[2] = "Подлокација";
		columns[3] = "Број примерака";
	}

	
	public int getRowCount() {		
		return raspodela.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {		
		return columns.length;
	}
	
	public String getColumnName(int column){
		return columns[column];
	}


	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		Primerak p = (Primerak)raspodela.get(rowIndex)[0];
		switch(columnIndex){
		case 0: return p.getOdeljenje()+"-"+ HoldingsDataCoders.getValue(HoldingsDataCoders.ODELJENJE_CODER, p.getOdeljenje());
		case 1: return p.getInvBroj().substring(2,4);
		case 2: return p.getSigPodlokacija()==null ? "" : p.getSigPodlokacija()+"-"+HoldingsDataCoders.getValue(HoldingsDataCoders.PODLOKACIJA_CODER, p.getSigPodlokacija());
		case 3: return raspodela.get(rowIndex)[1];		
		}
		
		return null;
	}
	
	public void initialize(){
		raspodela.clear();
	}	
	
	public void updatePrimerak(Primerak p, Integer brPrimeraka) throws RaspodelaException {
		String odeljenje = p.getOdeljenje();
		String invKnj = p.getInvBroj().substring(2,4);
		String podlokacija = p.getSigPodlokacija();
		Object[] novaRaspodela = {p,brPrimeraka};
		
		boolean saPodlokacijom = p.getSigPodlokacija()!=null && !p.getSigPodlokacija().equals("") ;
		
		boolean found = false;
		int index = -1;
		int i = 0;
		while(!found && i<raspodela.size()){
			Primerak pr = (Primerak)raspodela.get(i)[0];
			if(!saPodlokacijom)
				found = pr.getOdeljenje().equals(odeljenje) && pr.getInvBroj().substring(2,4).equals(invKnj);
			else
				found = pr.getOdeljenje().equals(odeljenje) && pr.getInvBroj().substring(2,4).equals(invKnj) && pr.getSigPodlokacija().equals(podlokacija);
			if(found)
				index = i;
			else i++;			
		}
		int preostalo = frame.getPreostalo();
		if(found){
			// update
			int prethodniBrojPrimeraka = ((Integer)raspodela.get(index)[1]).intValue();
			int razlika = brPrimeraka-prethodniBrojPrimeraka;		
			if(razlika>preostalo) throw new RaspodelaException("Прекорачен број књига!");
			raspodela.remove(index);
			raspodela.add(index,novaRaspodela);	
			this.fireTableRowsUpdated(0,index);		
			frame.setPreostalo(preostalo-razlika);
		}else{
			//insert
			if(brPrimeraka>preostalo) throw new RaspodelaException("Прекорачен број књига!");
			raspodela.add(novaRaspodela);		
			frame.setPreostalo(preostalo-brPrimeraka.intValue());			
			this.fireTableRowsInserted(0,raspodela.size()-1);
		}		
	}
	
	public void deletePrimerak(int redniBroj){
		int brPrim = ((Integer)raspodela.get(redniBroj)[1]).intValue();
		raspodela.remove(redniBroj);
		this.fireTableRowsDeleted(0,raspodela.size());
		int preostalo = frame.getPreostalo();
		frame.setPreostalo(preostalo+brPrim);
	}
	
	public Object[] getRow(int rowIndex){
		return raspodela.get(rowIndex);
	}
	
	public void generisiPrimerkePremaRaspodeli() throws UValidatorException {
		if(raspodela.size()>0){
      int broj;
			Primerak p;
			int brPrimeraka;
			for(int i=0;i<raspodela.size();i++){
				p = (Primerak) raspodela.get(i)[0];
				brPrimeraka = (Integer) raspodela.get(i)[1];				
				String brojac = p.getInvBroj()
					.substring(InventarConstraints.startPos-1, InventarConstraints.endPos);
				String pocetak = ""; // prve cetiri cifre
				if(InventarConstraints.startPos==3 && InventarConstraints.endPos==4)
					pocetak = p.getOdeljenje()+brojac;
				else
					pocetak = brojac;				
				for(int j=0;j<brPrimeraka;j++){            
					broj = //BisisApp.getRecordManager().getNewID(brojac); TODO-hardcoded
							0;
          String prefiks = pocetak+"000000000".substring(0,InventarConstraints.duzinaInventarnogBroja-pocetak.length()-String.valueOf(broj).length());
          String invBroj = prefiks+broj; 
          if(InventarValidation.isDuplicatedInvBroj(invBroj))
          		throw new UValidatorException("Инвентарни број је заузет, \n морате подесити бројач!");
          Primerak newp = RecordUtils.copyPrimerakBezInv(p);
          newp.setInvBroj(invBroj);                             
          CurrRecord.addPrimerak(newp);
				}		
			}
			
   frame.updatePrimerciTable();
   Obrada.editorFrame.recordUpdated();
		}		
	}
	
	public int getZbirRaspodeljenih(){
		int rez = 0;
		for(Object[] obj:raspodela){
			rez = rez + (Integer)obj[1];			
		}
		return rez;
		
	}
	

}
