package com.ftninformatika.bisis.search.net;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.bisis.hitlist.formatters.RecordFormatterFactory;
import com.ftninformatika.bisis.records.BriefInfoModel;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.search.SearchFrame;
import com.ftninformatika.bisis.xmlmessaging.communication.LibraryServerDesc;
import net.miginfocom.swing.MigLayout;

import org.dom4j.Document;


public class NetHitListFrame extends JInternalFrame {

  public static final int PAGE_SIZE = 10;

  public NetHitListFrame(String query, Vector<BriefInfoModel> hits) {
    super("Rezultati pretrage", true, true, true, true);
    this.searchHits = hits;
    this.receivedRecords=new Vector<Record>();
    this.selectedHits=new LinkedHashMap();
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    netHitListModel = new NetHitListModel();
    renderer = new NetHitListRenderer(selectedHits);
   
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    btnPrev.setIcon(new ImageIcon(getClass().getResource(
        "/icons/prev.gif")));
    btnNext.setIcon(new ImageIcon(getClass().getResource(
        "/icons/next.gif")));
    btnNew.setIcon(new ImageIcon(getClass().getResource(
        "/icons/copy.gif")));
    btnBrief.setEnabled(false);
    lbHitList.setModel(netHitListModel);
    lbHitList.setCellRenderer(renderer);
    spHitList.setViewportView(lbHitList);
    spHitList.setPreferredSize(new Dimension(500, 350));
    MigLayout layout = new MigLayout(
        "insets dialog, wrap",
        "[]rel[]rel[grow]rel[]rel[]",
        "[]rel[]rel[]rel[grow]para[]");
    setLayout(layout);
    add(lQuery, "span 5, wrap");
    add(lFromTo, "span 5, wrap");
    add(btnPrev, "");
    add(btnNext, "");
    add(new JLabel(""), "growx");
    add(btnBrief, "");
    add(btnFull, "wrap");
    add(spHitList, "span 5, grow, wrap");
    add(btnNew, "");
    pack();    
    if(!BisisApp.appConfig.getLibrarian().isCataloguing()){
      btnNew.setEnabled(false);
    }
    
    addInternalFrameListener(new InternalFrameAdapter() {
      public void internalFrameClosing(InternalFrameEvent e) {
        shutdown();
      }
    });

    btnPrev.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        if (page > 0) {
          page--;
          updateAvailability();
          displayPage();
        }
      }
    });
    
    btnNext.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        if (page < pageCount() - 1) {
          page++;
          updateAvailability();
          displayPage();
        }
      }
    });
    
    btnBrief.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent ev) {
    		recordMode=false;
    		receivedRecords.clear();
    		page=briefPage;
	  		renderer.setFormatter(RecordFormatterFactory.FORMAT_BRIEF);
	  		btnFull.setEnabled(true);
	  		btnBrief.setEnabled(false);
	  		updateAvailability();
	  		displayPage();
    	}
    });
    
    btnFull.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent ev) {
    		if(selectedHits.size()>0){
    		    // TODO - preview UNIMARC
    		}
    	}
    });
    
    lbHitList.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent ev) {
        switch (ev.getKeyCode()) {
          case KeyEvent.VK_ENTER:
            btnNew.doClick();
            break;
          case KeyEvent.VK_ESCAPE:
            shutdown();
            break;
        }
      }
    });
    
    lbHitList.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e) {
        if(e.getClickCount()==2){
          btnNew.doClick();
        }       
      }     
    });
    
    btnNew.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent ev) {
    		if(recordMode){
    			Record rec = (Record)lbHitList.getSelectedValue();
    			if(rec!=null) Obrada.newRecord(rec);
    		}else{
    			selectedHits.clear();
    			BriefInfoModel recBrief=(BriefInfoModel)lbHitList.getSelectedValue();
    			String thisLib=recBrief.getLibFullName();
    			LinkedHashMap hitsToThisAddress=(LinkedHashMap)selectedHits.get(thisLib);
    			if(hitsToThisAddress==null){
    				hitsToThisAddress=new LinkedHashMap();
    			}
    			String  recId=recBrief.getBriefInfo().get_id();
    			// TODO - uzeti taj rec
    		}
        }
    });
    btnBrief.setFocusable(false);
    btnFull.setFocusable(false);
    btnPrev.setFocusable(false);
    btnNext.setFocusable(false);
    btnNew.setFocusable(false);
    ButtonGroup btnGroup = new ButtonGroup();
    btnGroup.add(btnBrief);
    btnGroup.add(btnFull);
    btnBrief.setSelected(true);

    lQuery.setText("<html>Upit: <b>" + query + "</b></html>");
    updateAvailability();
    displayPage();
  }
  
  public void appendResults(Vector<BriefInfoModel> newHits){
	  searchHits.addAll(newHits);
	  updateAvailability();
	  displayPage();
  }
  
  public void appendRecords(Vector<Record> newRecs){
	  if(!recordMode){
		  recordMode=true;
		  briefPage=page;
		  page=0;
		  renderer.setFormatter(RecordFormatterFactory.FORMAT_FULL);
		  btnFull.setEnabled(false);
		  btnBrief.setEnabled(true);
	  }
	  receivedRecords.addAll(newRecs);
	  updateAvailability();
	  displayPage();
  }
  
  private void shutdown() { 	
  	BisisApp.getMainFrame().getSearchFrame().show();
  	try {
			SearchFrame activeSearch=BisisApp.getMainFrame().getSearchFrame();
			activeSearch.setSelected(true);
		} catch (PropertyVetoException e) {		
		}
  	BisisApp.getMainFrame().getSearchFrame().setDefaultFocus();
  	setVisible(false);
  	
  }
  
  private void updateAvailability() {
    btnPrev.setEnabled(page != 0);
    btnNext.setEnabled(page != pageCount() - 1);
  }
  
  private void displayPage() {
	Vector hits=searchHits;
	if(recordMode)
		hits=receivedRecords;
    if (hits == null || hits.size() == 0)
      return;
    int count = PAGE_SIZE;
   
    if (page == pageCount()-1 ){  //ako je poslednja stranica
    	if (hits.size() % PAGE_SIZE==0){
    		count=PAGE_SIZE;
    	}
    	else{
         count = hits.size() % PAGE_SIZE;
    	}
    }
    
    Object[] recs = new Object[count];
    for (int i = 0; i < count; i++)
      recs[i] = hits.get(page*PAGE_SIZE + i);
    netHitListModel.setHits(recs);
    lbHitList.setSelectedIndex(0);
    lFromTo.setText("<html>Pogoci: <b>" + (page*PAGE_SIZE+1) + " - " + 
        (page*PAGE_SIZE+count) + "</b> od <b>" + 
        hits.size() + "</b></html>");
  }


    private Vector getRecordsForPage(int page, Vector<BriefInfoModel> hits) {
      Vector<Record> retVal = new Vector<>();

        int count = PAGE_SIZE;

        if (page == pageCount()-1 ){  //ako je poslednja stranica
            if (hits.size() % PAGE_SIZE==0){
                count=PAGE_SIZE;
            }
            else{
                count = hits.size() % PAGE_SIZE;
            }
        }

        Object[] recs = new Object[count];
        for (int i = 0; i < count; i++)
            recs[i] = hits.get(page*PAGE_SIZE + i);

      return retVal;
    }

  private int pageCount() {
	Vector hits=searchHits;
	if(recordMode)
		hits=receivedRecords;
    if (hits == null || hits.size() == 0)
     return 0;
     return hits.size() / PAGE_SIZE + (hits.size() % PAGE_SIZE > 0 ? 1 : 0);
   
   
  }

  private JLabel lQuery = new JLabel();
  private JLabel lFromTo = new JLabel();
  private JToggleButton btnBrief = new JToggleButton("Sa\u017eeti");
  private JToggleButton btnFull = new JToggleButton("Puni");
  private JButton btnPrev = new JButton("Prethodni");
  private JButton btnNext = new JButton("Slede\u0107i");
  private JButton btnNew = new JButton("Novi");
  
  private JScrollPane spHitList = new JScrollPane();
  private JList lbHitList = new JList();
  private NetHitListModel netHitListModel;
  private NetHitListRenderer renderer;
  
  private Vector<BriefInfoModel> searchHits;
  private Vector<Record> receivedRecords;
  private int page = 0;
  private int briefPage = 0;
  private boolean recordMode=false;
  private LinkedHashMap selectedHits;
}
