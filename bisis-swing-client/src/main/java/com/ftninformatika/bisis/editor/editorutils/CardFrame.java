package com.ftninformatika.bisis.editor.editorutils;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.MessageFormat;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.cards.PaperBL;
import com.ftninformatika.bisis.cards.Report;
import com.ftninformatika.bisis.editor.recordtree.CurrRecord;
import net.miginfocom.swing.MigLayout;
/**
 * @author dimicb
 * 
 * frejm za prikaz listica
 *
 */
public class CardFrame extends CenteredDialog implements Printable {  
 
  private JComboBox vrsteListicaCmbBox;
  private JButton showButton;
  private JEditorPane editorPane;
  private JScrollPane scrollPane;
  private JPanel choicePanel;
  
  private JPanel buttonsPanel;
  private JButton zatvoriButton;
  private JButton stampajButton;
  
  public CardFrame(){
    super(BisisApp.getMainFrame());
    setTitle("Prikaz zapisa u formi listi\u0107a");
    setResizable(false);    
    editorPane = new JEditorPane();
    editorPane.setEditorKit(new HTMLEditorKit());
    scrollPane = new JScrollPane(editorPane);
    scrollPane.getViewport().setView(editorPane);
    
    editorPane.setEditable(false);
   // editorPane.setSize(500, 400);
   // editorPane.setBorder(arg0)
    showButton = new JButton("Prika\u017ei");
    initialize();    
    setSize(785, 528);
    
    choicePanel = new JPanel();
    MigLayout migLayout = new MigLayout("","","[]10[]10[]");
    choicePanel.setLayout(migLayout);
    choicePanel.add(new JLabel("Vrste listi\u0107a: "));
    choicePanel.add(vrsteListicaCmbBox);
    choicePanel.add(showButton);
    
    buttonsPanel = new JPanel();
    buttonsPanel.setLayout(new MigLayout("","","[]5[]"));
    zatvoriButton = new JButton("Zatvori");
    zatvoriButton.setIcon(new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis4/client/images/remove.gif")));
    stampajButton = new JButton("\u0160tampaj");
    stampajButton.setIcon(new ImageIcon(getClass().getResource(
    "/com/gint/app/bisis4/client/images/print_16.png")));
    buttonsPanel.add(stampajButton);
    buttonsPanel.add(zatvoriButton);
    
    //GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    
    MigLayout layout = new MigLayout("","[center]10[center]","[center]10[center]10[center]");
    setLayout(layout);
    c.gridx = 0;
    c.gridy = 0;   
    c.weighty = 0.05;    
    c.insets = new Insets(0,10,0,10);
    c.anchor = GridBagConstraints.CENTER;
    c.weightx = 0;
    add(choicePanel,c);    
    c.gridy = 1;  
    c.weightx = 1;
   // c.weightx = 0.9;
    c.fill = GridBagConstraints.BOTH;
    c.weighty = 0.9;
    add(scrollPane,c);    
    c.gridy = 2;
    c.weighty = 0.05;
    c.anchor = GridBagConstraints.CENTER;
    c.weightx = 0;    
    c.fill = GridBagConstraints.NONE;
    add(buttonsPanel,c);
    
    showButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {       
        showCard();
      }      
    });    
    zatvoriButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        handleCloseDialog();       
      }      
    });
    stampajButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {     
        handlePrintCard();
      }      
    });
    vrsteListicaCmbBox.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent ke) {
        handleKeys(vrsteListicaCmbBox,ke);        
      }
    });
    editorPane.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent ke) {
        handleKeys(editorPane,ke);        
      }
    });
    showButton.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent ke) {
        handleKeys(showButton,ke);        
      }
    });   
  }
  
  private void initialize(){
    Report.ucitajParam();
    Report.loadReportTypes();
    vrsteListicaCmbBox = new JComboBox(Report.reportTypes);
    vrsteListicaCmbBox.setSelectedItem(Report.currentType);
    showCard();    
  }
  
  private void showCard(){
    text = Report.makeOne(CurrRecord.record.getRecordID(), CurrRecord.record,
        vrsteListicaCmbBox.getSelectedItem().toString());    
    editorPane.setText(htmlScreenHeader + text + htmlScreenFooter);
    editorPane.setCaretPosition(0);    
  }
  
  private void handleCloseDialog(){
    dispose();
  }
  
  private void handleKeys(Component comp, KeyEvent ke) {
    if(ke.getKeyCode()==KeyEvent.VK_ESCAPE)
      zatvoriButton.doClick();
    if(ke.getKeyCode()==KeyEvent.VK_ENTER && comp.equals(vrsteListicaCmbBox))
      showButton.doClick();
    
  }
  
  private void handlePrintCard(){
  	printerJob = PrinterJob.getPrinterJob();
    
    PageFormat pageFormat= printerJob.defaultPage(paper);//TODO-hardcoded
    
    pageFormat = printerJob.validatePage(pageFormat);
    printerJob.setCopies(1);
    //printerJob.setPrintable(editorPane.getPrintable(new MessageFormat(""), new MessageFormat("")), pageFormat);
    printerJob.setPrintable(this, pageFormat);
    if (printerJob.printDialog()) {
      pages = paginateReport();      
      try { printerJob.print(); } catch (Exception ex) { ex.printStackTrace(); }
    }
   editorPane.setText(htmlScreenHeader + text + htmlScreenFooter);
  }
  
  private String[] paginateReport() {
    Vector<String> tempRetVal = new Vector<String>();
    //int totalLength = text.length();
    int startPos = 0;    
    while (startPos != -1) {
      int pageEnd = findNextPageBreakPos(startPos, text);      
      if (pageEnd != -1)
        tempRetVal.addElement(text.substring(startPos, pageEnd));      
      else                              
      	tempRetVal.addElement(text.substring(startPos));
     startPos = pageEnd;
      
    }
    String[] retVal = new String[tempRetVal.size()];
    tempRetVal.copyInto(retVal);
    return retVal;
  }
  
  private int findNextPageBreakPos(int startPos, String text) {
    int curr = startPos;
    int count = 0; 
    while (count < Report.brRedova) {
      curr = text.indexOf("<BR>", curr+1);
      if (curr != -1)
          count++;
      else
        break;
    }
    if (count == Report.brRedova)    	
         return curr+ 4;
    	
    else
      return -1;
  }

	public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex >= pages.length){			
      return Printable.NO_SUCH_PAGE;
		}
		editorPane.setText(htmlPrintHeader + pages[pageIndex] + htmlPrintFooter);    
  htmlPrintHeader="<HTML><BODY BGCOLOR=white TEXT=black><FONT FACE=\"Courier New\" SIZE=\""+Report.fontSize+"\">\n";
  g.translate(Report.translateX,Report.translateY);
  editorPane.paint(g);
  return Printable.PAGE_EXISTS;
		
	}
	
	
	
	 private static final String htmlScreenHeader = "<HTML><HEAD></HEAD><BODY BGCOLOR=white TEXT=black><FONT FACE=\"Courier New\">\n";
	 private static final String htmlScreenFooter = "</FONT></BODY></HTML>";
	 private static String htmlPrintHeader ;
	 private static final String htmlPrintFooter = "</FONT></BODY></HTML>";
	 
	 private PaperBL paper = new PaperBL();
	 
	 private String text;
	 private String[] pages;
	 private PrinterJob printerJob;
  
  
  
  
  
}
