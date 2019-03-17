package com.ftninformatika.bisis.editor.merge;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.editorutils.CenteredDialog;
import com.ftninformatika.bisis.editor.recordtree.RecordUtils;
import com.ftninformatika.bisis.records.*;
import com.ftninformatika.bisis.records.serializers.PrimerakSerializer;
import com.ftninformatika.utils.Messages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MergeController {

    @FXML TextField osnovniRn;
    @FXML TextField drugiRn;
    @FXML ListView listaZapisa;
    @FXML javafx.scene.control.Label osnovniOpis;
    private Record osnovni = null;
    private List<Record> ostali = new ArrayList<>();
    private CenteredDialog checkMergedDialog;
    private JButton okBtn = new JButton(new ImageIcon(getClass().getResource(
            "/icons/ok.gif")));
    private JButton cancelBtn  = new JButton(new ImageIcon(getClass().getResource(
            "/icons/remove.gif")));

    public MergeController(){
        init();
    }

    public void init(){
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
    }

    public void cancel(){
        osnovniRn.setText(null);
        drugiRn.setText(null);
        listaZapisa.getItems().setAll(new ArrayList());
        osnovniOpis.setText("Није одабран основни запис");
        osnovni = null;
        ostali = new ArrayList<>();
    }


    public void dodajOsnovni(){

        if(osnovniRn.getText() != null && !osnovniRn.getText().equals("")){
            int rnOsnovniInt = 999999999;
            boolean numberFormatOk = true;
            try{
                rnOsnovniInt = Integer.parseInt(osnovniRn.getText());
            }catch(NumberFormatException e){
                numberFormatOk = false;
            }
            if (numberFormatOk) {
                Record recBasic = null;
                try {
                    recBasic = BisisApp.getRecordManager().getRecordByRN(rnOsnovniInt);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
                            Messages.getString("MERGE_RECORD_RN_ERROR"),
                            Messages.getString("MERGE_RECORD_ERROR"), JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(recBasic == null){
                    JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
                            MessageFormat.format(Messages.getString("MERGE_RECORD_NO_REC_WITH_RN"), rnOsnovniInt+""),
                            Messages.getString("MERGE_RECORD_ERROR"),JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //pronasao osnovni
                else {
                    RecordPreview rp = new RecordPreview();
                    rp.init(recBasic);
                    String osnText = "Пронађен основни запис: " + recBasic.getRN() + " " + rp.getAuthor() + " - " + rp.getTitle();
                    osnovniOpis.setText(osnText);
                    osnovniOpis.setMaxWidth(500);
                    osnovniOpis.setWrapText(true);
                    osnovniOpis.setStyle("-fx-font-weight: bold");
                    osnovni = recBasic;

                }
            }
        }
    }

    public void dodajOstale(){
        if(drugiRn.getText() != null && !drugiRn.getText().equals("")){
            int rnDrugog = 999999999;
            boolean numberFormatOk = true;
            try{
                rnDrugog = Integer.parseInt(drugiRn.getText());
            }catch(NumberFormatException e){
                numberFormatOk = false;
            }
            if (numberFormatOk) {
                Record recBasic = null;
                try {
                    recBasic = BisisApp.getRecordManager().getRecordByRN(rnDrugog);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
                            Messages.getString("MERGE_RECORD_RN_ERROR"),
                            Messages.getString("MERGE_RECORD_ERROR"), JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(recBasic == null){
                    JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
                            MessageFormat.format(Messages.getString("MERGE_RECORD_NO_REC_WITH_RN"), rnDrugog+""),
                            Messages.getString("MERGE_RECORD_ERROR"),JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //pronasao zapis
                else {
                    RecordPreview rp = new RecordPreview();
                    rp.init(recBasic);
                    String listText = "RN: " + recBasic.getRN() + "   " + rp.getTitle() + " - " + rp.getAuthor();
                    if (!ostali.contains(recBasic))
                        ostali.add(recBasic);
                    if(!listaZapisa.getItems().contains(listText))
                        listaZapisa.getItems().add(listText);

                }
            }
        }
    }

    public void handleMege(){
        if(osnovni == null)
            return;
        try {
        for(Record rec:ostali){
            for(Primerak p:rec.getPrimerci())
                osnovni.getPrimerci().add(p.copy());
            for(Godina g:rec.getGodine())
                osnovni.getGodine().add(g.copy());
        }

        String udk = "";
        for (int i = 0; i < ostali.size(); i++) {
            String test = ostali.get(i).getSubfieldContent("675a");
            if (test != null) {
                udk = test;
                break;
            }
        }
        Field f675 = osnovni.getField("675");
        if (f675 == null) {
            f675 = new Field("675");
            osnovni.add(f675);
        }
//        Subfield sf675a = f675.getSubfield('a');
//        if (sf675a == null) {
//            sf675a = new Subfield('a');
//            f675.add(sf675a);
//        }
//        sf675a.setContent(udk);

        checkMergedDialog =
                new CenteredDialog(BisisApp.getMainFrame());
        checkMergedDialog.setTitle(Messages.getString("MERGE_RECORD_TITLE"));
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setEditorKit(new HTMLEditorKit());
        String checkMergedStr = "";
        checkMergedStr +=(Messages.getString("MERGE_RECORD_HTML_BASIC_REC"));
        checkMergedStr +=("<b>RN = "+osnovni.getRN()+"</b><br>");
        checkMergedStr +=(RecordFactory.toFullFormat(0, RecordUtils.sortFields(PrimerakSerializer.primerciUPolja(osnovni)), true));
        PrimerakSerializer.poljaUPrimerke(osnovni);
        checkMergedStr +=("\n");
        checkMergedStr +=("-----------------------------------------------------<br>");
        checkMergedStr +=(Messages.getString("MERGE_RECORD_RECS_TO_DELETE"));
        for(Record rec:ostali){
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
    catch(Exception e){
        JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
        Messages.getString("MERGE_RECORD_ERROR2")+e.getClass(),
        Messages.getString("MERGE_RECORD_ERROR"),JOptionPane.ERROR_MESSAGE);
        }
    }

    public void executeMerge(){
        MergeRecordsWrapper mergeRecordsWrapper = new MergeRecordsWrapper(osnovni, (ArrayList<Record>) ostali);
        Boolean ret = null;
        try {
            try {
                ret = BisisApp.bisisService.mergeRecords(mergeRecordsWrapper).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                ret = false;
            }
            if (ret != null && !ret)
                throw new Exception();
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
}
