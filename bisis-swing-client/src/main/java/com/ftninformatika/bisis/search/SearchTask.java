package com.ftninformatika.bisis.search;

import com.ftninformatika.bisis.BisisApp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.ftninformatika.bisis.records.BriefInfoModel;
import com.ftninformatika.bisis.search.net.NetHitListFrame;
import com.ftninformatika.utils.Messages;

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
 private String oper5;
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
 private String queryString="";
 private SearchStatusDlg statusDlg;
 private SearchModel searchModel;
 private OtherLibsSearch otherLibsSearch;
 private Vector<BriefInfoModel> resultsForeignSearch;
 private Result queryResult;
 private SearchTaskType type = SearchTaskType.MAIN_SEARCH;

 public SearchTask(OtherLibsSearch otherLibsSearch, SearchStatusDlg statusDlg) {
     this.type = SearchTaskType.FOREIGN_SEARCH;
     this.statusDlg=statusDlg;
     this.otherLibsSearch = otherLibsSearch;
 }
 
  public SearchTask(String pref1, String oper1, String text1, 
					String pref2, String oper2, String text2,
					String pref3, String oper3, String text3,
					String pref4, String oper4, String text4,
					String pref5,  String oper5, String text5, String sort,
					SearchStatusDlg statusDlg, String locId) {
      this.type = SearchTaskType.MAIN_SEARCH;
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
      this.oper5=oper5;
      this.text1=text1;
	  this.text2=text2;
	  this.text3=text3;
	  this.text4=text4;
	  this.text5=text5;
	  this.sort=sort;

	  List<String> departments = null;
	  if (locId != null) {
	      departments = new ArrayList<>();
          departments.add(locId);
      }
	  this.searchModel = new SearchModel(pref1,pref2,pref3,pref4,pref5,text1,text2,text3,text4,text5,oper1,oper2,oper3,oper4,oper5,sort, departments, null);
  }
  public SearchTask(String queryString, SearchStatusDlg statusDlg){
	  this.statusDlg=statusDlg;
	  this.queryString=queryString;
  }
  @Override
  public Integer  doInBackground() {
    if (type == SearchTaskType.MAIN_SEARCH) {
        if (this.searchModel != null) {
            try {
                this.queryResult = BisisApp.recMgr.searchRecords(searchModel);
                if (queryResult != null) {
                    return this.queryResult.getResultCount();
                } else {
                    this.connError = true;
                    return -1;
                }
            } catch (IOException e) {
                e.printStackTrace();
                this.connError = true;
                return -1;
            } catch (Exception e2) {
                e2.printStackTrace();
                this.bigSet = true;
                return -1;
            }
        }
        return -1;
    }
    // Foreign search
    else if (type == SearchTaskType.FOREIGN_SEARCH) {
        try {
            resultsForeignSearch = BisisApp.bisisService.searchIdsMutlipleLibs(otherLibsSearch).execute().body();
            if (resultsForeignSearch == null){
                JOptionPane.showMessageDialog(BisisApp.mf, "Greska u pretrazi, formulisite bolji upit!", "Pretraga u mreži", JOptionPane.INFORMATION_MESSAGE);
                return -1;
            }
            return 1;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(BisisApp.mf, "Greska u pretrazi!", "Pretraga u mreži", JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
            return -1;
        }
    }
    return -1;
  }
  
  @Override
  protected void process(List<Integer> tableCount) {
   
  }
  
  @Override
  protected void done() {
     if (type == SearchTaskType.MAIN_SEARCH) {
         if (!isCancelled) {
             if (connError) {
                 JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
                         Messages.getString("SEARCH_CONNECTION_REFUSED_TALK_TO_ADMIN"), Messages.getString("SEARCH_ERROR"), JOptionPane.INFORMATION_MESSAGE);
             } else if (queryResult.getResultCount() == 0) {
                 JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
                         Messages.getString("SEARCH_NO_HITS"), Messages.getString("SEARCH_SEARCHING"), JOptionPane.INFORMATION_MESSAGE);
             } else if (bigSet) {
                 JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
                         Messages.getString("SEARCH_LARGE_RESULTSET_REQUERY"), Messages.getString("SEARCH_ERROR"), JOptionPane.INFORMATION_MESSAGE);
             } else {
                 BisisApp.getMainFrame().addHitListFrame(queryResult, searchModel.toString());
             }
         }
     }
     else if (type == SearchTaskType.FOREIGN_SEARCH) {
         NetHitListFrame netSearchResultFrame = new NetHitListFrame(otherLibsSearch.getSearchModel().toString(),resultsForeignSearch);
         BisisApp.getMainFrame().addNetHitListFrame(netSearchResultFrame);
        }
	     statusDlg.dispose();  
	 }
	      

  
  public void setIsCancelled(boolean cancelled){
	  isCancelled=cancelled;
  }
	   
}
