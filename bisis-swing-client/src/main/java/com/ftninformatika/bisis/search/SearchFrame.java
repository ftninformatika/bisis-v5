package com.ftninformatika.bisis.search;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.checklist.CheckableItem;
import com.ftninformatika.bisis.checklist.JCheckList;
import com.ftninformatika.bisis.editor.editorutils.CodeChoiceDialog;
import com.ftninformatika.bisis.format.UItem;
import com.ftninformatika.bisis.libenv.LibEnvProxy;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.prefixes.PrefixConfigFactory;
import com.ftninformatika.utils.CharacterLookup;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.string.StringUtils;
import com.ftninformatika.utils.swing.CCPUtil;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class SearchFrame extends JInternalFrame /*implements XMLMessagingProcessor*/ {

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
  private CodedPrefPanel codedPref1 = new CodedPrefPanel();
  private CodedPrefPanel codedPref2 = new CodedPrefPanel();
  private CodedPrefPanel codedPref3 = new CodedPrefPanel();
  private CodedPrefPanel codedPref4 = new CodedPrefPanel();
  private CodedPrefPanel codedPref5 = new CodedPrefPanel();
  private JButton btnCoder1 = new JButton();
  private JButton btnCoder2 = new JButton();
  private JButton btnCoder3 = new JButton();
  private JButton btnCoder4 = new JButton();
  private JButton btnCoder5 = new JButton();
  // operatori
  private JComboBox<String> cbOper1 = new JComboBox<>(new String[] {"AND", "OR", "NOT"});
  private JComboBox<String> cbOper2 = new JComboBox<>(new String[] {"AND", "OR", "NOT"});
  private JComboBox<String> cbOper3 = new JComboBox<>(new String[] {"AND", "OR", "NOT"});
  private JComboBox<String> cbOper4 = new JComboBox<>(new String[] {"AND", "OR", "NOT"});
  private JButton btnSearch = new JButton(Messages.getString("SEARCH"));
  private JRadioButton rbLocalSearch = new JRadioButton(Messages.getString("SEARCH_LOCALE"));
  private JRadioButton rbNetSearch = new JRadioButton(Messages.getString("SEARCH_NET"));
  private JScrollPane spServerList = new JScrollPane();
  private JRadioButton rbZipNetSearch = new JRadioButton(Messages.getString("SEARCH_USE_COMPRESSION"));

  private JComboBox cbSort = new JComboBox();

  private JComboBox cbOdlj = new JComboBox();


  public SearchFrame() {
    super(Messages.getString("SEARCH_SEARCHING_RECORD"), true, true, false, true);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    //td=MessagingEnvironment.getThreadDispatcher();
    for (String sp: PrefixConfigFactory.getPrefixConfig().getSortPrefixes())
      cbSort.addItem(new SortPrefix(sp));

    btnSearch.setIcon(new ImageIcon(getClass().getResource(
        "/icons/search.gif")));
    btnCoder1.setIcon(new ImageIcon(getClass().getResource(
        "/icons/coder.gif")));
    btnCoder2.setIcon(new ImageIcon(getClass().getResource(
        "/icons/coder.gif")));
    btnCoder3.setIcon(new ImageIcon(getClass().getResource(
        "/icons/coder.gif")));
    btnCoder4.setIcon(new ImageIcon(getClass().getResource(
        "/icons/coder.gif")));
    btnCoder5.setIcon(new ImageIcon(getClass().getResource(
        "/icons/coder.gif")));


    MigLayout layout = new MigLayout(
        "insets dialog, wrap",
        "[left]rel[300lp]rel[left]rel[left]para[300lp]",
        "[]rel[]rel[]rel[]rel[]rel[]rel[30lp]");
    setLayout(layout);
    add(btnPref1, "growx");
    add(tfPref1, "growx, hidemode 3");
    add(btnCoder1, "");
    add(cbOper1, "");
    add(rbLocalSearch, "wrap");
    add(btnPref2, "growx");
    add(tfPref2, "growx");
    add(btnCoder2, "");
    add(cbOper2, "");
    add(rbNetSearch, "wrap");
    add(btnPref3, "growx");
    add(tfPref3, "growx");
    add(btnCoder3, "");
    add(cbOper3, "");
    add(spServerList, "span 1 3, growx, growy, wrap");
    add(btnPref4, "growx");
    add(tfPref4, "growx");
    add(btnCoder4, "");
    add(cbOper4, "wrap");
    add(btnPref5, "growx");
    add(tfPref5, "growx");
    add(btnCoder5, "wrap");

    tfPref1.setComponentPopupMenu(CCPUtil.getCCPPopupMenu());
    tfPref2.setComponentPopupMenu(CCPUtil.getCCPPopupMenu());
    tfPref3.setComponentPopupMenu(CCPUtil.getCCPPopupMenu());
    tfPref4.setComponentPopupMenu(CCPUtil.getCCPPopupMenu());
    tfPref5.setComponentPopupMenu(CCPUtil.getCCPPopupMenu());

    if (BisisApp.appConfig.getLibrary().equals("bgb")) {
        cbOdlj.addItem(" ");
        for (String l :BisisApp.appConfig.getCodersHelper().getLocationsList()){
            cbOdlj.addItem(l);
            if (BisisApp.appConfig.getLibrarian().getDefaultDepartment() != null
                && BisisApp.appConfig.getCodersHelper().getLocationCodeByNameExtended(l).equals(BisisApp.appConfig.getLibrarian().getDefaultDepartment()))
              cbOdlj.setSelectedItem(l);
              locIdIndex = cbOdlj.getSelectedIndex();

        }
        add(new JLabel(Messages.getString("SEARCH_FILTER_BY")),"growx");
        add(cbOdlj,"wrap");

//        if (BisisApp.appConfig.getLibrarian().getDefaultDepartment() != null)
//            cbOdlj.setSelectedItem(BisisApp.appConfig.getLibrarian().getDefaultDepartment());

        cbOdlj.addActionListener(new ActionListener () {
          public void actionPerformed(ActionEvent e) {
            SearchFrame.locId = BisisApp.appConfig.getCodersHelper().getLocationCodeByNameExtended(cbOdlj.getSelectedItem().toString());
            SearchFrame.locIdIndex = cbOdlj.getSelectedIndex();
            BisisApp.appConfig.getCodersHelper().filterCodersByDepartment(locId);
          }
        });

    }
    //add(new JLabel(" "), "span 3, growx");
    add(new JLabel(Messages.getString("SEARCH_SORT_BY")), "");
    add(cbSort, "wrap");
    add(btnSearch, "span 5, tag ok, growy");
    pack();

    //zbog seaglass teme
    Font f = new Font("TimesNewRoman", Font.PLAIN, 13);
    cbOper1.setFont(f);
    cbOper2.setFont(f);
    cbOper3.setFont(f);
    cbOper4.setFont(f);

    btnPref1.setFocusable(false);
    btnPref2.setFocusable(false);
    btnPref3.setFocusable(false);
    btnPref4.setFocusable(false);
    btnPref5.setFocusable(false);
    btnSearch.setFocusable(false);
    btnCoder1.setFocusable(false);
    btnCoder2.setFocusable(false);
    btnCoder3.setFocusable(false);
    btnCoder4.setFocusable(false);
    btnCoder5.setFocusable(false);
    rbLocalSearch.setFocusable(false);
    rbNetSearch.setFocusable(false);
    cbSort.setFocusable(false);
    getRootPane().setDefaultButton(btnSearch);
    ButtonGroup btnGroup = new ButtonGroup();
    btnGroup.add(rbLocalSearch);
    btnGroup.add(rbNetSearch);
    rbLocalSearch.setSelected(true);
    rbZipNetSearch.setSelected(true);
    rbZipNetSearch.setEnabled(false);

    addInternalFrameListener(new InternalFrameAdapter(){
      public void internalFrameActivated(InternalFrameEvent e){
        setDefaultFocus();
      }
      public void internalFrameClosing(InternalFrameEvent e){
        closeSearchFrame();
      }

    });

    btnPref1.addActionListener(ev -> choosePrefix(btnPref1, btnCoder1));
    btnPref2.addActionListener(ev -> choosePrefix(btnPref2, btnCoder2));
    btnPref3.addActionListener(ev -> choosePrefix(btnPref3, btnCoder3));
    btnPref4.addActionListener(ev -> choosePrefix(btnPref4, btnCoder4));
    btnPref5.addActionListener(ev -> choosePrefix(btnPref5, btnCoder5));
    tfPref1.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ev) {
        handleKeys(btnPref1, tfPref1, btnCoder1, ev);
      }
    });
    tfPref2.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ev) {
        handleKeys(btnPref2, tfPref2, btnCoder1, ev);
      }
    });
    tfPref3.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ev) {
        handleKeys(btnPref3, tfPref3, btnCoder1, ev);
      }
    });
    tfPref4.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ev) {
        handleKeys(btnPref4, tfPref4, btnCoder1, ev);
      }
    });
    tfPref5.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ev) {
        handleKeys(btnPref5, tfPref5, btnCoder1, ev);
      }
    });
    btnCoder1.addActionListener(ev -> chooseFromCoder(btnPref1, tfPref1, btnCoder1));
    btnCoder2.addActionListener(ev -> chooseFromCoder(btnPref2, tfPref2, btnCoder2));
    btnCoder3.addActionListener(ev -> chooseFromCoder(btnPref3, tfPref3, btnCoder3));
    btnCoder4.addActionListener(ev -> chooseFromCoder(btnPref4, tfPref4, btnCoder4));
    btnCoder5.addActionListener(ev -> chooseFromCoder(btnPref5, tfPref5, btnCoder5));

    btnSearch.addActionListener(ev -> {
      if (rbLocalSearch.isSelected())
        handleLocalSearch();
      else
        handleNetSearch();
    });

    rbLocalSearch.addActionListener(ev -> {
      if (rbLocalSearch.isSelected()){
        Component[] itemsInList = spServerList.getComponents();
        for(int i = 0; i < itemsInList.length; i++)
          itemsInList[i].setEnabled(false);
        spServerList.getViewport().setVisible(false);
        rbZipNetSearch.setEnabled(false);
      }
    });

    rbNetSearch.addActionListener(ev -> {
      if (rbNetSearch.isSelected()){
        if (spServerList.getViewport().getView()==null)
          populateServerList();
        else {
          Component[] itemsInList = spServerList.getComponents();
          for(int i = 0; i < itemsInList.length; i++)
            itemsInList[i].setEnabled(true);
          spServerList.getViewport().setVisible(true);
          rbZipNetSearch.setEnabled(true);
        }
      }
    });
  }

	public void setVisible(boolean visible) {
    if (visible)
      updatePrefixes();
    super.setVisible(visible);
  }
  
  public void updatePrefixes() {
    Librarian lib = BisisApp.appConfig.getLibrarian();
    btnPref1.setText(lib.getContext().getPref1());
    btnPref2.setText(lib.getContext().getPref2());
    btnPref3.setText(lib.getContext().getPref3());
    btnPref4.setText(lib.getContext().getPref4());
    btnPref5.setText(lib.getContext().getPref5());
    setCoderButtons();
  }  
  
  public void setDefaultFocus() {
    tfPref1.requestFocusInWindow();
  }
  
  public void closeSearchFrame() {
		if (dirtyPrefixSet) {
			Librarian lib = BisisApp.appConfig.getLibrarian();
			lib.getContext().setPref1(btnPref1.getText());
			lib.getContext().setPref2(btnPref2.getText());
			lib.getContext().setPref3(btnPref3.getText());
			lib.getContext().setPref4(btnPref4.getText());
			lib.getContext().setPref5(btnPref5.getText());
			LibEnvProxy.updateLibrarian(lib);
		}
		setVisible(false);
	}  
  
  private void setCoderButtons() {
    btnCoder1.setEnabled(CodedPrefUtils.isPrefCoded(btnPref1.getText()));
    btnCoder2.setEnabled(CodedPrefUtils.isPrefCoded(btnPref2.getText()));
    btnCoder3.setEnabled(CodedPrefUtils.isPrefCoded(btnPref3.getText()));
    btnCoder4.setEnabled(CodedPrefUtils.isPrefCoded(btnPref4.getText()));
    btnCoder5.setEnabled(CodedPrefUtils.isPrefCoded(btnPref5.getText()));
  }
  
  private void choosePrefix(JButton btnPref, JButton btnCoder) {
    prefixListDlg.moveTo(btnPref.getText());
    prefixListDlg.setVisible(true);
    if (prefixListDlg.isSelected()) {
      String chosenPrefix = prefixListDlg.getSelectedPrefix();
      btnPref.setText(chosenPrefix);
      if (CodedPrefUtils.isPrefCoded(chosenPrefix))
        btnCoder.setEnabled(true);
      else
        btnCoder.setEnabled(false);
      dirtyPrefixSet = true;
    }
  }

  private void chooseFromCoder(JButton btnPref, JTextField tfPref, JButton btnCoder) {
    String prefix = btnPref.getText();
    List<UItem> codesList = CodedPrefUtils.getCodesForPrefix(prefix);
    if (codesList != null) {
      CodeChoiceDialog ccd = new CodeChoiceDialog(BisisApp.getMainFrame(),
              Messages.getString("SEARCH_CODER"), codesList, MessageFormat.format(Messages.getString("SEARCH_CODER_FOR_PREFIX.0"), prefix),"");
      ccd.setVisible(true);
      if(ccd.getSelectedCode()!=null)
        tfPref.setText(ccd.getSelectedCode());
      ccd.setVisible(false);
    }

  }
  
  private void chooseExpand(JTextField tfPref, String prefix) {
	  List<String> exp = getExpand(tfPref, prefix);
	  if (exp != null) {
		  Collections.sort(exp);
		  //expandListDlg = new ExpandListDlg(BisisApp.getMainFrame());
		  expandListDlg.setList(exp);
		  expandListDlg.setVisible(true);
		  if (expandListDlg.isSelected()) {
			  tfPref.setText(expandListDlg.getSelectedItem());
		  }
	  }
  }


  private List<String> getExpand(JTextField tfPref, String prefix) {
    String text = tfPref.getText();
    text = StringUtils.clearDelimiters(text, delims);
    List<String> expList = new ArrayList<>();
    try {
      expList = BisisApp.recMgr.selectExp(prefix, text);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return expList;
  }

  private void handleKeys(JButton btnPref, JTextField tfPref, JButton btnCoder, KeyEvent ev) {
   switch (ev.getKeyCode()) {
      case KeyEvent.VK_F9:
        choosePrefix(btnPref, btnCoder);
        break;
      case KeyEvent.VK_F12:
      	if (!CodedPrefUtils.isPrefCoded(btnPref.getText()))
          chooseExpand(tfPref, btnPref.getText());
          break;
      case KeyEvent.VK_F1:
        handleLookup(tfPref);
        break;
      case KeyEvent.VK_ESCAPE:
        setVisible(false);
        break;
      case KeyEvent.VK_ENTER: {
        if (ev.getID() == KeyEvent.KEY_TYPED && ev.getModifiers() == 0)
          btnSearch.doClick();
      }
        break;
      case KeyEvent.VK_DOWN:
        if (tfPref == tfPref1)
          tfPref2.requestFocus();
        else if (tfPref == tfPref2)
          tfPref3.requestFocus();
        else if (tfPref == tfPref3)
          tfPref4.requestFocus();
        else if (tfPref == tfPref4)
          tfPref5.requestFocus();
        else if (tfPref == tfPref5)
          tfPref1.requestFocus();
        break;
      case KeyEvent.VK_UP:
        if (tfPref == tfPref1)
          tfPref5.requestFocus();
        else if (tfPref == tfPref2)
          tfPref1.requestFocus();
        else if (tfPref == tfPref3)
          tfPref2.requestFocus();
        else if (tfPref == tfPref4)
          tfPref3.requestFocus();
        else if (tfPref == tfPref5)
          tfPref4.requestFocus();
        break;
    }
    if (ev.getModifiers() == InputEvent.CTRL_MASK && ev.getKeyCode()==KeyEvent.VK_F6)
    	BisisApp.getMainFrame().selectNextInternalFrame();
    if (ev.getKeyCode() == KeyEvent.VK_F2) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      tfPref.setText(sdf.format(new Date()));
    }
  }
  
  private void handleLocalSearch() {

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

  	    btnSearch.setEnabled(false);
  	    
  	 	String sortPrefix = ((SortPrefix)cbSort.getSelectedItem()).name;
  	    SearchStatusDlg statusDlg = new SearchStatusDlg();
  	    if(BisisApp.appConfig.getLibrary().equals("bgb") ){
  	        this.locId = BisisApp.appConfig.getCodersHelper().getLocationCodeByNameExtended(cbOdlj.getSelectedItem().toString());
        }

  	 	SearchTask task = new SearchTask( btnPref1.getText(), cbOper1.getSelectedItem().toString(), text1,
                                          btnPref2.getText(), cbOper2.getSelectedItem().toString(), text2,
                                          btnPref3.getText(), cbOper3.getSelectedItem().toString(), text3,
                                          btnPref4.getText(), cbOper4.getSelectedItem().toString(), text4,
                                          btnPref5.getText(), text5, sortPrefix, statusDlg, locId);

  	 	statusDlg.addActionListener(task);
  	 	task.execute();
  	 	statusDlg.setVisible(true);
  	 	btnSearch.setEnabled(true);

  }

  private void handleNetSearch() {
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
    }else {
      btnSearch.setEnabled(false);
      JCheckList checkList =(JCheckList)spServerList.getViewport().getView();
      for (int i=0; i<checkList.getModel().getSize();i++){
        CheckableItem ci = (CheckableItem) checkList.getModel().getElementAt(i);
        if(ci.isSelected()){

        }
      }
      JOptionPane.showMessageDialog(BisisApp.mf, "Pretraga u mrezi nije implementirana!", "Pretraga u mreži", JOptionPane.INFORMATION_MESSAGE);
    }
  }
  
  private void refreshServerList(List<LibraryConfiguration> serverList) {
    if (serverList.size() > 0) {
      JPanel pServers=new JPanel();
      pServers.setLayout(new BoxLayout(pServers, BoxLayout.Y_AXIS));
      spServerList.setViewportView(pServers);

      Vector<CheckableItem> items=new Vector<CheckableItem>();
      for (LibraryConfiguration lc:serverList) {
          if (lc.getLibraryFullName()!=null) {
              CheckableItem item = new CheckableItem(lc);
              item.setSelected(true);
              items.add(item);
          }
      }
      CheckableItem[] arr=new CheckableItem[items.size()];
      arr=items.toArray(arr);
      JCheckList list = new JCheckList(arr);
      spServerList.getViewport().setView(list); 
      rbZipNetSearch.setEnabled(true);
    }
  }
  
  private void populateServerList() {
    try {
      List<LibraryConfiguration> receivedList = BisisApp.bisisService.getAllConfigurations(BisisApp.appConfig.getLibrary()).execute().body();
      if (receivedList != null) {
        refreshServerList(receivedList);
      }else{
        JOptionPane.showMessageDialog(BisisApp.mf,Messages.getString("SERVER_LIST_EMPTY"),Messages.getString("SEARCH_SEARCHING"),JOptionPane.ERROR_MESSAGE);
      }
    }catch (Exception e){
      e.printStackTrace();
      JOptionPane.showMessageDialog(BisisApp.mf,Messages.getString("SERVER_LIST_EMPTY"),Messages.getString("SEARCH_SEARCHING"),JOptionPane.ERROR_MESSAGE);
    }

  }
  
  /**
   * Support function used from processResponse and accessible from runners
   * inside threads
   */
  public synchronized void setErrors(String text) {
	  JOptionPane.showMessageDialog(BisisApp.mf,
			  text, Messages.getString("SEARCH_SEARCHING"), JOptionPane.INFORMATION_MESSAGE);
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
    lookup.setVisible(true);
    if (lookup.isSelected()) {
      char c = lookup.getSelectedChar();
      int pos = tf.getCaretPosition();
      String s1 = tf.getText().substring(0, pos);
      String s2 = (pos == tf.getText().length()) ? "" : tf.getText().substring(pos);
      tf.setText(s1 + c + s2);
      tf.setCaretPosition(pos+1);
    }
  }



  // operatori
  private CharacterLookup lookup = new CharacterLookup(BisisApp.mf);
  
  private PrefixListDlg prefixListDlg = new PrefixListDlg(BisisApp.mf);
  private ExpandListDlg expandListDlg = new ExpandListDlg(BisisApp.mf);
  private boolean dirtyPrefixSet = false;
  
  //added by Miroslav Zaric -  required for NetSearch
  //private ThreadDispatcher td;
  //private LinkedHashMap<String, NetHitListFrame> netSearchResultFrames=new LinkedHashMap<String, NetHitListFrame>();
  //Filter za odeljenja bgb
  public static String locId = null;
  public static int locIdIndex = 0;
  private static final Dimension textPanelDim = new Dimension(200,20);
  private static final Dimension prefButtonDim = new Dimension(70,20);
  private static String delims = ", ;:\"()[]{}-+!\t\r\n\f";
  //private static String delims = ", ;:()[]{}-+/.!\t\r\n\f";

	 
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
          