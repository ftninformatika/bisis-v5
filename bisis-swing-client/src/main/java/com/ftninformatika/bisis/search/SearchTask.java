package com.ftninformatika.bisis.search;

import com.ftninformatika.bisis.BisisApp;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;


public class SearchTask extends SwingWorker<Integer, Integer> {
	
 private String pref1;
 private String pref2;
 private String pref3;
 private String pref4;
 private String pref5;
 private String oper1;
 private String oper2;
 private String oper3;
 private String oper4;
 private String text1;
 private String text2;
 private String text3;
 private String text4;
 private String text5;
 private String sort;
 private boolean isCancelled=false;
 private boolean bigSet=false;
 private boolean connError=false;
 private boolean sintaxError=false;
 /*private Result queryResult;
 private Query query;*/
 private String queryString="";
 private SearchStatusDlg statusDlg;
 
  public SearchTask(String pref1, String oper1, String text1, 
					String pref2, String oper2, String text2,
					String pref3, String oper3, String text3,
					String pref4, String oper4, String text4,
					String pref5,  String text5, String sort,
					SearchStatusDlg statusDlg) {
	  this.statusDlg=statusDlg;
	  this.pref1=pref1;
	  this.pref2=pref2;
	  this.pref3=pref3;
	  this.pref4=pref4;
	  this.pref5=pref5;
	  this.oper1=oper1;
	  this.oper2=oper2;
	  this.oper3=oper3;
	  this.oper4=oper4;
	  this.text1=text1;
	  this.text2=text2;
	  this.text3=text3;
	  this.text4=text4;
	  this.text5=text5;
	  this.sort=sort;
  }
  public SearchTask(String queryString, SearchStatusDlg statusDlg){
	  this.statusDlg=statusDlg;
	  this.queryString=queryString;
  }
  @Override
  public Integer  doInBackground() {
    
/*	  try {
		 if (queryString.equals("")){
           query=QueryUtils.makeLuceneAPIQuery( pref1,oper1, text1, 
    										  pref2,oper2, text2, 
    										  pref3,oper3, text3, 
    										  pref4,oper4, text4, 
    										  pref5, text5);
           
         if (query==null)
       	     return -1;	   

  	      if(BisisApp.isStandalone()){
	    	queryResult =BisisApp.getRecordManager().selectAll2(query, sort);
	    	queryString=query.toString();
	      }else{
	    	  queryResult =BisisApp.getRecordManager().selectAll2x(SerializationUtils.serialize(query), sort);
	    	  queryString=query.toString();
	      }
          return queryResult.getRecords().length;
		}else{  //deo koji obradjuje naprednu pretragu
			queryResult =BisisApp.getRecordManager().selectAll1(queryString, null);
			return queryResult.getRecords().length;
		}
      } catch(ParseException e1){
    	  statusDlg.dispose();
	    	JOptionPane.showMessageDialog(BisisApp.getMainFrame(), 
		      	      e1.getMessage(), "Gre\u0161ka", JOptionPane.INFORMATION_MESSAGE);
	    	return -1;
	    }catch(BurlapRuntimeException e){
	        	connError=true;
	        	return  -1;
	  } catch (Exception e) {
		    	 bigSet=true;
		    	 return  -1;
	   }	*/
	return -1;
  }
  
  @Override
  protected void process(List<Integer> tableCount) {
   
  }
  
  @Override
  protected void done() {  
	     if (!isCancelled){
	       if (connError){
	    	 JOptionPane.showMessageDialog(BisisApp.getMainFrame(), 
	       	         "Konekcija na server nije uspela!"+" \n"+" Obratite se administratoru!", "Gre\u0161ka", JOptionPane.INFORMATION_MESSAGE);
	       }else if(bigSet){
	      	 JOptionPane.showMessageDialog(BisisApp.getMainFrame(), 
	      	          "Prevelik skup pogodaka. Preformulisati upit!", "Gre\u0161ka", JOptionPane.INFORMATION_MESSAGE);
	       }/*else if(queryResult==null){
	    	   JOptionPane.showMessageDialog(BisisApp.getMainFrame(), 
	    	           "Nema pogodaka!", "Pretraga", JOptionPane.INFORMATION_MESSAGE);
	       }
	       else if (queryResult.getRecords().length == 0){
	        JOptionPane.showMessageDialog(BisisApp.getMainFrame(), 
	           "Nema pogodaka!", "Pretraga", JOptionPane.INFORMATION_MESSAGE);
	       }*/
	       else{
	        BisisApp.getMainFrame().addHitListFrame(/*queryString, queryResult*/null);
	       }
	     } 
	     statusDlg.dispose();  
	 }
	      

  
  public void setIsCancelled(boolean cancelled){
	  isCancelled=cancelled;
  }
	   
}
