
package com.ftninformatika.bisis.editor.merge;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.html.HTMLEditorKit;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.editorutils.CenteredDialog;
import com.ftninformatika.bisis.editor.recordtree.RecordUtils;
import com.ftninformatika.bisis.records.*;
import com.ftninformatika.bisis.records.serializers.PrimerakSerializer;
import com.ftninformatika.utils.Messages;
import net.miginfocom.swing.MigLayout;

public class MergeRecordsFrame extends JInternalFrame {

	public MergeRecordsFrame(){
		super(Messages.getString("MERGE_RECORD_TITLE"), false, true, false, true);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setSize(new Dimension(240,400));
		initialize();
	}

	public void initialize(){
		setLayout(new MigLayout("insets 10 10 10 20","[]","[][]20[][]20[]"));
		add(new JLabel(Messages.getString("MERGE_RECORD_RN_REC")),"wrap");
		add(idOsnovniZapisTxtFld,"wrap");
		rnOstaliZapisiTextArea = new JTextArea(12,8);
		scrollPane = new JScrollPane(rnOstaliZapisiTextArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(new JLabel(Messages.getString("MERGE_RECORD_RNS_TO_ADD")),"wrap");
		add(scrollPane,"wrap");
		mergeButton = new JButton(new ImageIcon(getClass().getResource(
  	"/icons/Check16.png")));
		mergeButton.setText(Messages.getString("MERGE_RECORD_MERGE"));


	  okBtn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
						executeMerge();
				}
	  });
	  cancelBtn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					checkMergedDialog.dispose();
				}
	  });

		add(mergeButton,"");

		mergeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleMergeRecords();
			}
		});
	}


	private void handleMergeRecords() {
		try{
					String rnOsnovni = idOsnovniZapisTxtFld.getText();
					boolean numberFormatOk = true;
					int rnOsnovniInt = 0;

					try{
						rnOsnovniInt = Integer.parseInt(rnOsnovni);
					}catch(NumberFormatException e){
						numberFormatOk = false;
					}
					if(numberFormatOk){
						String text = rnOstaliZapisiTextArea.getText();
						String[] rnsToAddStringArray = text.split("\n");
						int[] idsToAdd = new int[rnsToAddStringArray.length];
						for(int i=0; i<rnsToAddStringArray.length;i++){
							try{
								idsToAdd[i] = Integer.parseInt(rnsToAddStringArray[i]);
							}catch(NumberFormatException e){
								numberFormatOk = false;
							}
						}
						if(!numberFormatOk){
							JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
									Messages.getString("MERGE_RECORD_RN_ERROR"),
									Messages.getString("MERGE_RECORD_ERROR"),JOptionPane.ERROR_MESSAGE);
							return;
						}
						recBasic = BisisApp.getRecordManager().getRecordByRN(rnOsnovniInt);
						if(recBasic==null){
							JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
									MessageFormat.format(Messages.getString("MERGE_RECORD_NO_REC_WITH_RN"), rnOsnovni),
									Messages.getString("MERGE_RECORD_ERROR"),JOptionPane.ERROR_MESSAGE);
							return;
						}
						recsToAdd = new ArrayList<Record>();
						for(int rn:idsToAdd){
							Record rec = BisisApp.getRecordManager().getRecordByRN(rn);
							if(rec==null){
								JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
										MessageFormat.format(Messages.getString("MERGE_RECORD_NO_REC_WITH_RN"),rn),
										Messages.getString("MERGE_RECORD_ERROR"),JOptionPane.ERROR_MESSAGE);
								return;
							}
							recsToAdd.add(rec);
						}

						// merge - prepisuju se primerci odnosno godine
						// UDK - na osnovni zapis se dodaje 675 iz prvog zapisa koji ga
						// sadrzi (od zapisa koji se dodaju)

						for(Record rec:recsToAdd){
							for(Primerak p:rec.getPrimerci())
								recBasic.getPrimerci().add(p.copy());
							for(Godina g:rec.getGodine())
								recBasic.getGodine().add(g.copy());
						}

					 String udk = "";
			   for (int i = 0; i < recsToAdd.size(); i++) {
			     String test = recsToAdd.get(i).getSubfieldContent("675a");
			     if (test != null) {
			       udk = test;
			       break;
			     }
			   }
			   Field f675 = recBasic.getField("675");
			   if (f675 == null) {
			     f675 = new Field("675");
			     recBasic.add(f675);
			   }
			   Subfield sf675a = f675.getSubfield('a');
			   if (sf675a == null) {
			     sf675a = new Subfield('a');
			     f675.add(sf675a);
			   }
			   sf675a.setContent(udk);

			   checkMergedDialog =
			   		new CenteredDialog(BisisApp.getMainFrame());
			   checkMergedDialog.setTitle(Messages.getString("MERGE_RECORD_TITLE"));
			   JEditorPane editorPane = new JEditorPane();
			   editorPane.setEditable(false);
			   editorPane.setEditorKit(new HTMLEditorKit());
			   String checkMergedStr = "";
			   checkMergedStr +=(Messages.getString("MERGE_RECORD_HTML_BASIC_REC"));
			   checkMergedStr +=("<b>RN = "+recBasic.getRN()+"</b><br>");
			   checkMergedStr +=(RecordFactory.toFullFormat(0, RecordUtils.sortFields(PrimerakSerializer.primerciUPolja(recBasic)), true));
			   PrimerakSerializer.poljaUPrimerke(recBasic);
			   checkMergedStr +=("\n");
			   checkMergedStr +=("-----------------------------------------------------<br>");
			   checkMergedStr +=(Messages.getString("MERGE_RECORD_RECS_TO_DELETE"));
			   for(Record rec:recsToAdd){
			   	checkMergedStr +=("<b>RN = "+rec.getRN()+"</b><br>");
			   	checkMergedStr +=(RecordFactory.toFullFormat(0, PrimerakSerializer.primerciUPolja(rec), true));
			   	checkMergedStr +=("<br>-----------------------<br>");
			   }
			   checkMergedStr +=("</html>");

			   JScrollPane scrollPane = new JScrollPane(editorPane);
			   editorPane.setSize(300,200);
			   editorPane.setText(checkMergedStr);
			   checkMergedDialog.setLayout(new MigLayout("","[][]","[]"));
			   checkMergedDialog.add(scrollPane,"span 2, grow, wrap");
			   okBtn.setText(Messages.getString("MERGE_RECORD_MERGE"));
			   cancelBtn.setText(Messages.getString("MERGE_RECORD_CANCEL"));
			   checkMergedDialog.add(okBtn, "split 2");
			   checkMergedDialog.add(cancelBtn,"");
			   checkMergedDialog.setSize(600,400);
			   checkMergedDialog.setVisible(true);

						}
		}catch(Exception e){
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
					Messages.getString("MERGE_RECORD_ERROR2")+e.getClass(),
					Messages.getString("MERGE_RECORD_ERROR"),JOptionPane.ERROR_MESSAGE);
		}

		}

	private void executeMerge(){
		try{
//			BisisApp.getRecordManager().update(recBasic);
//			for(Record rec:recsToAdd){
//				BisisApp.getRecordManager().delete(rec.get_id());
//			}
			MergeRecordsWrapper mergeRecordsWrapper = new MergeRecordsWrapper(recBasic, (ArrayList<Record>) recsToAdd);
			Boolean ret = BisisApp.bisisService.mergeRecords(mergeRecordsWrapper).execute().body();
			if (!ret) {
                throw new Exception();
            }
		}catch(Exception e){
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
					Messages.getString("MERGE_RECORD_ERROR_MERGING"),
					Messages.getString("MERGE_RECORD_ERROR"),JOptionPane.ERROR_MESSAGE);
			return;

		}
		JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
				Messages.getString("MERGE_RECORD_SUCCESS_MERGING"),Messages.getString("MERGE_RECORD_TITLE"),
				JOptionPane.INFORMATION_MESSAGE);
		checkMergedDialog.dispose();
	}


	private JTextField idOsnovniZapisTxtFld = new JTextField(10);
	private JTextArea rnOstaliZapisiTextArea;
	private JScrollPane scrollPane;
	private JButton mergeButton;
	private Record recBasic;
	private List<Record> recsToAdd;
	private CenteredDialog checkMergedDialog;
	private JButton okBtn = new JButton(new ImageIcon(getClass().getResource(
 "/icons/ok.gif")));
	private JButton cancelBtn  = new JButton(new ImageIcon(getClass().getResource(
 "/icons/remove.gif")));


}

