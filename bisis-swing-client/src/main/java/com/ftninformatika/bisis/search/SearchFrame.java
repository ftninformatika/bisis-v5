package com.ftninformatika.bisis.search;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.text.JTextComponent;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.hitlist.HitListFrame;
import com.ftninformatika.bisis.prefixes.PrefixConfigFactory;
import net.miginfocom.swing.MigLayout;


public class SearchFrame extends JInternalFrame /*implements XMLMessagingProcessor*/{

  public SearchFrame() {
    super("Pretra\u017eivanje zapisa", true, true, false, true);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setPreferredSize(new Dimension(700,310));    
    //td=MessagingEnvironment.getThreadDispatcher();
    addInternalFrameListener(new InternalFrameAdapter(){
      public void internalFrameActivated(InternalFrameEvent e){
      	setDefaultFocus();        
      }    
      public void internalFrameClosing(InternalFrameEvent e){
      	closeSearchFrame();
      }      
      
    }); 
    
    addComponentListener(new ComponentAdapter(){
    	public void componentShown(ComponentEvent e){    		
    		setDefaultFocus();
    	}
    	
    });

  //  for (String sp : PrefixConfigFactory.getPrefixConfig().getSortPrefixes())
    //  cbSort.addItem(new SortPrefix(sp));
    
    btnSearch.setIcon(new ImageIcon(getClass().getResource(
        "/icons/search.gif")));
    
    btnPref1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        choosePrefix(btnPref1);
      }
    });
    btnPref2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        choosePrefix(btnPref2);
      }
    });
    btnPref3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        choosePrefix(btnPref3);
      }
    });
    btnPref4.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        choosePrefix(btnPref4);
      }
    });
    btnPref5.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        choosePrefix(btnPref5);
      }
    });
    tfPref1.addKeyListener(new KeyAdapter() {    
    	public void keyPressed(KeyEvent ev) {
        handleKeys(btnPref1, tfPref1, ev);
      }    	
    });
    tfPref2.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ev) {
        handleKeys(btnPref2, tfPref2, ev);
      }      
    });
    tfPref3.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ev) {
        handleKeys(btnPref3, tfPref3, ev);
      }      
    });
    tfPref4.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ev) {
        handleKeys(btnPref4, tfPref4, ev);
      }      
    });
    tfPref5.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ev) {
        handleKeys(btnPref5, tfPref5, ev);
      }     
    });    
   /* codedPref1.getTextField().addKeyListener(new KeyAdapter(){
    	public void keyPressed(KeyEvent ev) {
        handleKeys(btnPref1, codedPref1.getTextField(), ev);
      }
    });
    
    codedPref2.getTextField().addKeyListener(new KeyAdapter(){
    	public void keyPressed(KeyEvent ev) {
        handleKeys(btnPref2, codedPref2.getTextField(), ev);
      }
    });
    
    codedPref3.getTextField().addKeyListener(new KeyAdapter(){
    	public void keyPressed(KeyEvent ev) {
        handleKeys(btnPref3, codedPref3.getTextField(), ev);
      }
    });
    
    codedPref4.getTextField().addKeyListener(new KeyAdapter(){
    	public void keyPressed(KeyEvent ev) {
        handleKeys(btnPref4, codedPref4.getTextField(), ev);
      }
    });
    
    codedPref5.getTextField().addKeyListener(new KeyAdapter(){
    	public void keyPressed(KeyEvent ev) {
        handleKeys(btnPref5, codedPref5.getTextField(), ev);
      }
    });    */
    
    btnSearch.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        if (rbLocalSearch.isSelected())
          handleLocalSearch();
        else
          handleNetSearch();
      }
    });
    rbLocalSearch.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        if (rbLocalSearch.isSelected()){
        	Component []itemsInList=spServerList.getComponents();
        	for(int i=0;i<itemsInList.length;i++)
        		itemsInList[i].setEnabled(false);
        	spServerList.getViewport().setVisible(false);
        	rbZipNetSearch.setEnabled(false);
        }
      }
    });
    rbNetSearch.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        if (rbNetSearch.isSelected()){
        	if(spServerList.getViewport().getView()==null)
        		populateServerList();
        	else{
        		Component []itemsInList=spServerList.getComponents();
            	for(int i=0;i<itemsInList.length;i++)
            		itemsInList[i].setEnabled(true);
            	spServerList.getViewport().setVisible(true);
            	rbZipNetSearch.setEnabled(true);
        	}
        	
        }
      }
    });


    MigLayout layout = new MigLayout(
        "insets dialog, wrap",
        "[left]rel[300lp]rel[left]para[300lp]",
        "[]rel[]rel[]rel[]rel[]");
    //setLayout(layout);

    prefPanel.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;
    c.insets = new Insets(5,5,5,5);
    c.weightx = 0.01;
    c.weighty = 0.7;
    c.gridx = 0;
    c.gridy = 0;        
    prefPanel.add(btnPref1, c);
    c.weightx = 1;
    c.gridx = 1;   
   // prefPanel.add(codedPref1,c);
    prefPanel.add(tfPref1, c);
    c.weightx = 0.01;    
    c.gridx = 2;
    prefPanel.add(cbOper1, c);   
    c.gridy = 1;
    c.gridx = 0;
    prefPanel.add(btnPref2,c);
    c.weightx = 0.1;
    c.gridx = 1;
    c.weightx = 1;    
    //prefPanel.add(codedPref2,c);
    prefPanel.add(tfPref2, c);
    c.weightx = 0.01;
    c.gridx = 2;
    prefPanel.add(cbOper2, c);
    c.weightx = 0.01;
    c.gridy = 2;
    c.gridx = 0;
    prefPanel.add(btnPref3,c);
    c.weightx = 1;
    c.gridx = 1;
    //prefPanel.add(codedPref3,c);
    prefPanel.add(tfPref3, c);
    c.weightx = 0.01;
    c.gridx = 2;
    prefPanel.add(cbOper3, c);    
    c.weightx = 0.05;
    c.gridy = 3;
    c.gridx = 0;
    prefPanel.add(btnPref4,c);
    c.weightx = 1;
    c.gridx = 1;
    //prefPanel.add(codedPref4,c);
    prefPanel.add(tfPref4, c);
    c.weightx = 0.01;
    c.gridx = 2;
    prefPanel.add(cbOper4, c);
    c.weightx = 0.01;
    c.gridy = 4;
    c.gridx = 0;
    prefPanel.add(btnPref5,c);
    c.weightx = 1;
    c.gridx = 1;
    //prefPanel.add(codedPref5,c);
    prefPanel.add(tfPref5, c);    
    prefPanel.setFocusCycleRoot(true);    
    searchPlacePanel.setLayout(new GridBagLayout());
    c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 1;
    c.weighty = 0.1;
    searchPlacePanel.add(rbLocalSearch, c);
    c.gridy = 1;
    searchPlacePanel.add(rbNetSearch, c);
    c.gridy = 2;
    c.weighty = 0.7;
    searchPlacePanel.add(spServerList, c);
    c.gridy = 4;
    c.gridx=0;
    c.weighty = 0.1;
    searchPlacePanel.add(rbZipNetSearch, c);
    
    
    
    sortPanel.setLayout(new GridBagLayout());
    c = new GridBagConstraints();    
    c.weightx = 1;
    c.weighty = 1;
    c.gridx = 0;
    c.gridy = 0;
    c.anchor = GridBagConstraints.LINE_END;
    sortPanel.add(new JLabel("Sortiraj po"), c);
    c.gridx = 1;    
    c.insets = new Insets(0,5,0,50);
    c.anchor = GridBagConstraints.LINE_START;
    sortPanel.add(cbSort, c);
    
    setLayout(new GridBagLayout());
    c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;
    c.insets = new Insets(20,10,30,0);
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 0.7;
    c.weighty = 0.6;
    add(prefPanel,c);
    c.insets = new Insets(20,50,30,10);
    c.gridx = 1;
    c.weightx = 0.3;
    c.weighty = 0.7;
    add(searchPlacePanel, c);
    c.gridx = 0;
    c.gridy = 1;
    c.weightx = 0.3;
    c.weighty = 0.4;
    c.insets = new Insets(10,80,10,20);
    add(sortPanel,c);    
    c.fill = GridBagConstraints.NONE;
    c.gridx = 1;
    btnSearch.setPreferredSize(new Dimension(50,30));
    add(btnSearch, c);
    
   /* codedPref1.setVisible(false);
    codedPref2.setVisible(false);
    codedPref3.setVisible(false);
    codedPref4.setVisible(false);
    codedPref5.setVisible(false);  
    */
    pack();
    
    btnPref1.setFocusable(false);
    btnPref2.setFocusable(false);
    btnPref3.setFocusable(false);
    btnPref4.setFocusable(false);
    btnPref5.setFocusable(false);
    btnSearch.setFocusable(false);
    rbLocalSearch.setFocusable(false);
    rbNetSearch.setFocusable(false);
    cbSort.setFocusable(false);
    //getRootPane().setDefaultButton(btnSearch);
    ButtonGroup btnGroup = new ButtonGroup();
    btnGroup.add(rbLocalSearch);
    btnGroup.add(rbNetSearch);
    rbLocalSearch.setSelected(true);
    rbZipNetSearch.setSelected(true);
    rbZipNetSearch.setEnabled(false);
  }  

	public void setVisible(boolean visible) {
    if (visible)
      updatePrefixes();
    super.setVisible(visible);
  }
  
  public void updatePrefixes() {
    /*Librarian lib = BisisApp.getLibrarian();
    btnPref1.setText(lib.getContext().getPref1());
    btnPref2.setText(lib.getContext().getPref2());
    btnPref3.setText(lib.getContext().getPref3());
    btnPref4.setText(lib.getContext().getPref4());
    btnPref5.setText(lib.getContext().getPref5());
    setPrefPanels();*/
  }  
  
  public void setDefaultFocus(){
  	if(tfPref1.isVisible())
  		tfPref1.requestFocusInWindow();
  	//else if(codedPref1.isVisible())
  	//	codedPref1.requestFocusInWindow();
  	}
  
  
  
  public void closeSearchFrame() {
		/*if(dirtyPrefixSet){
			Librarian lib = BisisApp.getLibrarian();
			lib.getContext().setPref1(btnPref1.getText());
			lib.getContext().setPref2(btnPref2.getText());
			lib.getContext().setPref3(btnPref3.getText());
			lib.getContext().setPref4(btnPref4.getText());
			lib.getContext().setPref5(btnPref5.getText());
			LibEnvironment.updateLibrarian(lib);
		}
		setVisible(false);	*/
	}  
  
  /*
   * stavlja odgovarajuce panela za unos teksta za pretragu
   * u zavisnosti od toga da li se radi o sifriranom ili
   * obicnom prefiksu 
   */
  private void setPrefPanels(){
  /*	if(CodedPrefUtils.isPrefCoded(btnPref1.getText())){
  		tfPref1.setVisible(false);
  		codedPref1.setVisible(true);
  		codedPref1.setPref(btnPref1.getText());
  	}else{
  		tfPref1.setVisible(true);
  		codedPref1.setVisible(false);  		
  	}
  	if(CodedPrefUtils.isPrefCoded(btnPref2.getText())){
  		tfPref2.setVisible(false);
  		codedPref2.setVisible(true);
  		codedPref2.setPref(btnPref2.getText());
  	}else{
  		tfPref2.setVisible(true);
  		codedPref2.setVisible(false);
  	}
  	if(CodedPrefUtils.isPrefCoded(btnPref3.getText())){
  		tfPref3.setVisible(false);
  		codedPref3.setVisible(true);
  		codedPref3.setPref(btnPref3.getText());
  	}else{
  		tfPref3.setVisible(true);
  		codedPref3.setVisible(false);
  	}
  	if(CodedPrefUtils.isPrefCoded(btnPref4.getText())){
  		tfPref4.setVisible(false);
  		codedPref4.setVisible(true);
  		codedPref4.setPref(btnPref4.getText());
  	}else{
  		tfPref4.setVisible(true);
  		codedPref4.setVisible(false);
  	}
  	if(CodedPrefUtils.isPrefCoded(btnPref5.getText())){
  		tfPref5.setVisible(false);
  		codedPref5.setVisible(true);
  		codedPref5.setPref(btnPref5.getText());
  	}else{
  		tfPref5.setVisible(true);
  		codedPref5.setVisible(false);
  	}  			*/
  }
  
  private void choosePrefix(JButton btn) {
    prefixListDlg.moveTo(btn.getText());
    prefixListDlg.setVisible(true);
   /* if (prefixListDlg.isSelected()){
      btn.setText(prefixListDlg.getSelectedPrefix());
      if(CodedPrefUtils.isPrefCoded(btn.getText())){      	
      	if(btn.equals(btnPref1)){
      		tfPref1.setVisible(false);
      		codedPref1.setVisible(true);
      		codedPref1.requestFocus();
      		codedPref1.setPref(btn.getText());
      	}else if(btn.equals(btnPref2)){
      		tfPref2.setVisible(false);
      		codedPref2.setVisible(true);
      		codedPref2.requestFocus();
      		codedPref2.setPref(btn.getText());
      	}else if(btn.equals(btnPref3)){
      		tfPref3.setVisible(false);
      		codedPref3.setVisible(true); 
      		codedPref3.requestFocus();
      		codedPref3.setPref(btn.getText());
      	}else if(btn.equals(btnPref4)){
      		tfPref4.setVisible(false);
      		codedPref4.setVisible(true);
      		codedPref4.requestFocus();
      		codedPref4.setPref(btn.getText());
      	}else if(btn.equals(btnPref5)){
      		tfPref5.setVisible(false);
      		codedPref5.setVisible(true);   
      		codedPref5.requestFocus();
      		codedPref5.setPref(btn.getText());
      	}      		
      }else{
      	if(btn.equals(btnPref1)){
      		tfPref1.setVisible(true);
      		tfPref1.requestFocus();
      		codedPref1.setVisible(false);
      	}else if(btn.equals(btnPref2)){
      		tfPref2.setVisible(true);
      		tfPref2.requestFocus();
      		codedPref2.setVisible(false);      		
      	}else if(btn.equals(btnPref3)){
      		tfPref3.setVisible(true);
      		tfPref3.requestFocus();
      		codedPref3.setVisible(false);      		
      	}else if(btn.equals(btnPref4)){
      		tfPref4.setVisible(true);
      		tfPref4.requestFocus();
      		codedPref4.setVisible(false);      		
      	}else if(btn.equals(btnPref5)){
      		tfPref5.setVisible(true);
      		tfPref5.requestFocus();
      		codedPref5.setVisible(false);      		
      	}      	
      }
      */
      dirtyPrefixSet = true;
   // }
  }
  
  private void chooseExpand(JTextField tfPref,JButton btn ){
	/*  List<String> exp= getExpand(tfPref,btn);
	  if(exp!=null){
		  Collections.sort(exp);
		  expandListDlg = new ExpandListDlg(BisisApp.getMainFrame());
		  expandListDlg.setList(exp);
		  expandListDlg.setVisible(true);
		  if (expandListDlg.isSelected()){    	
			  tfPref.setText(expandListDlg.getSelectedItem());
		  }
	  }	*/
  }
  
  private List<String> getExpand(JTextField tfPref,JButton btn) {
	 /*   String text=tfPref.getText();
	    text=StringUtils.clearDelimiters(text, delims);
	    String expandQuery="";
	    String prefix="";
	    List <String> expList=null;
	    if(!text.isEmpty()){
	       prefix=btn.getText();
	       String textWithOutAccent=LatCyrUtils.toLatinUnaccented(text);
	       expandQuery=prefix+" : "+textWithOutAccent+"*";
	       expList=BisisApp.getRecordManager().selectExp(expandQuery, prefix,LatCyrUtils.toLatin(text));	
	       }
	    return expList;	    */
	 return null;
  }

  private void handleKeys(JButton btn, JComponent compPref, KeyEvent ev) {
   /* switch (ev.getKeyCode()) {
      case KeyEvent.VK_F9:
        choosePrefix(btn);
        break;
      case KeyEvent.VK_F12:
      	if(compPref instanceof JTextField)
          chooseExpand((JTextField)compPref,btn);
          break;
      case KeyEvent.VK_F1:
      	if(compPref instanceof JTextField)      		
      		handleLookup((JTextField)compPref);
      	else if(compPref instanceof CodedPrefPanel)
      		handleLookup(((CodedPrefPanel)compPref).getTextField());      					
        break;
      case KeyEvent.VK_ESCAPE:
        setVisible(false);
        break;
      case KeyEvent.VK_ENTER:
        btnSearch.doClick();
        break;
      case KeyEvent.VK_DOWN:
        if (compPref == tfPref1 || compPref == codedPref1.getTextField())
        	if(tfPref2.isVisible())
        		tfPref2.requestFocus();
        	else
        		codedPref2.requestFocus();
        else if (compPref == tfPref2 || compPref == codedPref2.getTextField())
        	if(tfPref3.isVisible())
        		tfPref3.requestFocus();
        	else
        		codedPref3.requestFocus();
        else if (compPref == tfPref3 || compPref == codedPref3.getTextField())
        	if(tfPref4.isVisible())
        		tfPref4.requestFocus();
        	else
        		codedPref4.requestFocus();        	
        else if (compPref == tfPref4 || compPref == codedPref4.getTextField())
        	if(tfPref5.isVisible())
        		tfPref5.requestFocus();
        	else
        		codedPref5.requestFocus();
        else if (compPref == tfPref5 || compPref == codedPref5.getTextField())
        	if(tfPref1.isVisible())
        		tfPref1.requestFocus();
        	else
        		codedPref1.requestFocus();
        break;
      case KeyEvent.VK_UP:
        if (compPref == tfPref1 || compPref == codedPref1.getTextField())
        	if(tfPref5.isVisible())
        		tfPref5.requestFocus();
        	else
        		codedPref5.requestFocus();
        else if (compPref == tfPref2 || compPref == codedPref2.getTextField())
        	if(tfPref1.isVisible())
        		tfPref1.requestFocus();
        	else
        		codedPref1.requestFocus();
        else if (compPref == tfPref3 || compPref == codedPref3.getTextField())
        	if(tfPref2.isVisible())
        		tfPref2.requestFocus();
        	else
        		codedPref2.requestFocus();
        else if (compPref == tfPref4 || compPref == codedPref4.getTextField())
        	if(tfPref3.isVisible())
        		tfPref3.requestFocus();
        	else
        		codedPref3.requestFocus();
        else if (compPref == tfPref5 || compPref == codedPref5.getTextField())
        	if(tfPref4.isVisible())
        		tfPref4.requestFocus();
        	else
        		codedPref4.requestFocus();      
        break;     
    }
    if(ev.getModifiers() == InputEvent.CTRL_MASK 
    		&& ev.getKeyCode()==KeyEvent.VK_F6)
    	BisisApp.getMainFrame().selectNextInternalFrame();
    if(ev.getKeyCode() == KeyEvent.VK_F2){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	if(compPref instanceof JTextField){
    		((JTextField)compPref).setText(sdf.format(new Date()));
    	}    	
    }*/
  }
  
  private void handleLocalSearch() {

	 String text1 = "";
	 String text2 = "";
	 String text3 = "";
	 String text4 = "";
	 String text5 = "";

  	/*if(tfPref1.isVisible())
  		text1 = tfPref1.getText();
  	else
  		text1 = codedPref1.getText();
  	
  	if(tfPref2.isVisible())
  		text2 = tfPref2.getText();
  	else
  		text2 = codedPref2.getText();
  	
  	if(tfPref3.isVisible())
  		text3 = tfPref3.getText();
  	else
  		text3 = codedPref3.getText();  	
  	if(tfPref4.isVisible())
  		text4 = tfPref4.getText();
  	else
  		text4 = codedPref4.getText();
  	
  	if(tfPref5.isVisible())
  		text5 = tfPref5.getText();
  	else
  		text5 = codedPref5.getText(); 
  	    */
  	    btnSearch.setEnabled(false);
  	    
  	 //	String sortPrefix = ((SortPrefix)cbSort.getSelectedItem()).name;
  	 	//sortPrefix = ((SortPrefix)cbSort.getSelectedItem()).name;
  	    SearchStatusDlg statusDlg = new SearchStatusDlg();
    	
  	 	SearchTask task = new SearchTask( btnPref1.getText(), cbOper1.getSelectedItem().toString(), text1,
  	 	        btnPref2.getText(), cbOper2.getSelectedItem().toString(), text2, 
  	 	        btnPref3.getText(), cbOper3.getSelectedItem().toString(), text3, 
  	 	        btnPref4.getText(), cbOper4.getSelectedItem().toString(), text4, 
  	 	        btnPref5.getText(), text5, /*sortPrefix*/ "neki sort prefix",statusDlg);
  	 	statusDlg.addActionListener(task);
  	 	task.execute();
  	 	statusDlg.setVisible(true);
  	 	btnSearch.setEnabled(true);

  }

  private void handleNetSearch() {
	/*String convID = "" + (MessagingEnvironment.getMyLibServer()).getLibId() + "_"
    + new Date().getTime();
	
	//String convID ="test";
  	String text1 = "";
	String text2 = "";
	String text3 = "";
	String text4 = "";
	String text5 = "";
	if(tfPref1.isVisible())
		text1 = tfPref1.getText();
	else
		text1 = codedPref1.getText();
	
	if(tfPref2.isVisible())
		text2 = tfPref2.getText();
	else
		text2 = codedPref2.getText();
	
	if(tfPref3.isVisible())
		text3 = tfPref3.getText();
	else
		text3 = codedPref3.getText();  	
	if(tfPref4.isVisible())
		text4 = tfPref4.getText();
	else
		text4 = codedPref4.getText();
	
	if(tfPref5.isVisible())
		text5 = tfPref5.getText();
	else
		text5 = codedPref5.getText();
	
	if(text1.equals("") && text2.equals("") && text3.equals("") && text4.equals("") && text5.equals("")){
		JOptionPane.showMessageDialog(BisisApp.getMainFrame(), 
		          "Niste postavili nijedan kriterijum za pretragu!", "Pretraga", JOptionPane.INFORMATION_MESSAGE);
	}else{
	
		String[] prefixes = new String[5];
	    String[] values = new String[5];
	    String[] operators = new String[4];
	
	    prefixes[0] = btnPref1.getText();
	    prefixes[1] = btnPref2.getText();
	    prefixes[2] = btnPref3.getText();
	    prefixes[3] = btnPref4.getText();
	    prefixes[4] = btnPref5.getText();
	
	    values[0] = text1;
	    values[1] = text2;
	    values[2] = text3;
	    values[3] = text4;
	    values[4] = text5;
	
	    operators[0] = cbOper1.getSelectedItem().toString();
	    operators[1] = cbOper2.getSelectedItem().toString();
	    operators[2] = cbOper3.getSelectedItem().toString();
	    operators[3] = cbOper4.getSelectedItem().toString();
	
	    // pack parameters for query remote source
	    LinkedHashMap<String, LibraryServerDesc> libServersToSearch=new LinkedHashMap<String, LibraryServerDesc>();
	    JCheckList list=(JCheckList)spServerList.getViewport().getView();
		for (int i = 0; i < list.getModel().getSize(); i++) {
	      CheckableItem ci = (CheckableItem)list.getModel().getElementAt(i);
		  if (ci.isSelected()){
			  libServersToSearch.put(((LibraryServerDesc)ci.getObject()).getUrlAddress(), (LibraryServerDesc)ci.getObject());
		  }
		}
		Query query=QueryUtils.makeLuceneAPIQuery( btnPref1.getText(), cbOper1.getSelectedItem().toString(), text1, 
		        btnPref2.getText(), cbOper2.getSelectedItem().toString(), text2, 
		        btnPref3.getText(), cbOper3.getSelectedItem().toString(), text3, 
		        btnPref4.getText(), cbOper4.getSelectedItem().toString(), text4, 
		        btnPref5.getText(), text5);
		
		//System.out.println(query);
	    LinkedHashMap reqParams = new LinkedHashMap();
	    reqParams.put("libs", libServersToSearch);
	    reqParams.put("prefixes", prefixes);
	    reqParams.put("values", values);
	    reqParams.put("operators", operators);
	    reqParams.put("convId", convID);
	    reqParams.put("compress", rbZipNetSearch.isSelected());
	    reqParams.put("query", query.toString());
	    td.invokeThreads(this, "PerformSearch", reqParams);
	}*/
  }
  
  
  //added by Miroslav Zaric
  private void refreshServerList(/*Vector<LibraryServerDesc> results*/) {
  /*  if (results.size() > 0) {
      LinkedHashMap<String, LibraryServerDesc> libServers = MessagingEnvironment.getLibServers();
      libServers.clear();
      //re-populate master list
      for (int i = 0; i < results.size(); i++) {
        LibraryServerDesc oneLib = results.get(i);
        if (MessagingEnvironment.DEBUG == 1)
        	System.out.println("Dodajem server u listu");
        libServers.put(oneLib.getUrlAddress(), oneLib);
      }
      JPanel pServers=new JPanel();
      pServers.setLayout(new BoxLayout(pServers, BoxLayout.Y_AXIS));
      spServerList.setViewportView(pServers);
      
      Iterator<LibraryServerDesc> libs=(MessagingEnvironment.getLibServers().values()).iterator();
      Vector<CheckableItem> items=new Vector<CheckableItem>();
      while (libs.hasNext()) {
    	CheckableItem item = new CheckableItem(libs.next());
    	item.setSelected(true);
        items.add(item);
      }
      CheckableItem[] arr=new CheckableItem[items.size()];
      arr=items.toArray(arr);
      JCheckList list = new JCheckList(arr);
      spServerList.getViewport().setView(list); 
      rbZipNetSearch.setEnabled(true);
    }*/
  }
  
  private void populateServerList() {
	 /* Vector<LibraryServerDesc> receivedList = ServerListReader.prepareServerList();
	  if (receivedList != null) {
		  refreshServerList(receivedList);
	  }else{
		  JOptionPane.showMessageDialog(BisisApp.mf,
		          "Lista servera trenutno nedostupna!", "Pretraga", JOptionPane.ERROR_MESSAGE);
	  }*/
  }
  
  /**
   * Support function used from processResponse and accessible from runners
   * inside threads
   */
  public synchronized void setErrors(String text) {
	  JOptionPane.showMessageDialog(BisisApp.mf,
			  text, "Pretraga", JOptionPane.INFORMATION_MESSAGE);
  }
  /*
  public synchronized void processResponse(Document resp,
		  AbstractRemoteCall caller, LibraryServerDesc lib) {
	  //System.out.println(resp.asXML());
	  MessagingError me = new MessagingError();
	  Vector result = caller.processResponse(resp, lib, me);
	  if (result == null) {
		  if (me.isActive())
			  setErrors(me.getCode());
	  } else {
		  if (caller instanceof com.gint.app.bisis4.xmlmessaging.actions.SearchRequestActionCall){
			  NetHitListFrame handler=netSearchResultFrames.get(caller.getConvId());
			  if(handler==null)
				  netSearchResultFrames.put(caller.getConvId(), BisisApp.getMainFrame().addNetHitListFrame(((SearchRequestActionCall)caller).getQuery(),caller.getConvId(),rbZipNetSearch.isSelected(),((BriefInfoModel)result.get(0)).getLibServ(), result));
			  else
				  handler.appendResults(result);
		  }else{
			  setErrors("Wrong handler");
		  }
	  }
  }
  */
  /*public void removeNetSearchFrame(String convId){
	  netSearchResultFrames.remove(convId);
  }*/
  
  private void clearServerList() {
    spServerList.setViewportView(new JPanel());
  }
  
  private void handleLookup(JTextComponent tf) {
   /* lookup.setVisible(true);
    if (lookup.isSelected()) {
      char c = lookup.getSelectedChar();
      int pos = tf.getCaretPosition();
      String s1 = tf.getText().substring(0, pos);
      String s2 = (pos == tf.getText().length()) ? "" : tf.getText().substring(pos);
      tf.setText(s1 + c + s2);
      tf.setCaretPosition(pos+1);
    }*/
  }

  
  private JPanel prefPanel = new JPanel();  
  private JButton btnPref1 = new JButton("AU");
  private JButton btnPref2 = new JButton("AU");
  private JButton btnPref3 = new JButton("AU");
  private JButton btnPref4 = new JButton("AU");
  private JButton btnPref5 = new JButton("AU");
  private JTextField tfPref1 = new JTextField();
  private JTextField tfPref2 = new JTextField();
  private JTextField tfPref3 = new JTextField();
  private JTextField tfPref4 = new JTextField();
  private JTextField tfPref5 = new JTextField();
  // panel za unos sifriranih vrednosti  
 /* private CodedPrefPanel codedPref1 = new CodedPrefPanel();
  private CodedPrefPanel codedPref2 = new CodedPrefPanel();
  private CodedPrefPanel codedPref3 = new CodedPrefPanel();
  private CodedPrefPanel codedPref4 = new CodedPrefPanel();
  private CodedPrefPanel codedPref5 = new CodedPrefPanel();*/
  // operatori
  private JComboBox cbOper1 = new JComboBox(new String[] {"AND", "OR", "NOT"});
  private JComboBox cbOper2 = new JComboBox(new String[] {"AND", "OR", "NOT"});
  private JComboBox cbOper3 = new JComboBox(new String[] {"AND", "OR", "NOT"});
  private JComboBox cbOper4 = new JComboBox(new String[] {"AND", "OR", "NOT"});
    
  private JButton btnSearch = new JButton("Pretra\u017ei");
  private JPanel searchPlacePanel = new JPanel();
  private JRadioButton rbLocalSearch = new JRadioButton("Pretraga u lokalu");
  private JRadioButton rbNetSearch = new JRadioButton("Pretraga na mre\u017ei");
  private JScrollPane spServerList = new JScrollPane();
  private JRadioButton rbZipNetSearch = new JRadioButton("Koristi kompresiju za prenos");
  private JPanel pServerList = new JPanel();
  
  private JPanel sortPanel = new JPanel();
  private JComboBox cbSort = new JComboBox();
  //private CharacterLookup lookup = new CharacterLookup(BisisApp.getMainFrame());
  
  private PrefixListDlg prefixListDlg = new PrefixListDlg(BisisApp.mf);
  //private ExpandListDlg expandListDlg=new ExpandListDlg(BisisApp.getMainFrame());
  private boolean dirtyPrefixSet = false;
  
  //added by Miroslav Zaric -  required for NetSearch
  //private ThreadDispatcher td;
//  private LinkedHashMap<String, NetHitListFrame> netSearchResultFrames=new LinkedHashMap<String, NetHitListFrame>();
  //
  
  private static final Dimension textPanelDim = new Dimension(200,20);
  private static final Dimension prefButtonDim = new Dimension(70,20);
  private static String delims = ", ;:\"()[]{}-+!\t\r\n\f";
  // private static String delims = ", ;:()[]{}-+/.!\t\r\n\f";

	 
  public class SortPrefix {
    public String name;
    public String caption;
    public SortPrefix() {
    }
    public SortPrefix(String name, String caption) {
      this.name = name;
      this.caption = caption;
    }
    public SortPrefix(String name) {
      this.name = name + "_sort";
      this.caption = PrefixConfigFactory.getPrefixConfig().getPrefixName(name);
    }
    public String toString() {
      return caption;
    }
  }
  
}
          