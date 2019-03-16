package com.ftninformatika.bisis;

import com.ftninformatika.bisis.admin.coders.CoderFrame;
import com.ftninformatika.bisis.admin.coders.TableCatalog;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.admin.coders.CodersHelper;
import com.ftninformatika.bisis.config.TestConfig;
import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.bisis.hitlist.HitListFrame;
import com.ftninformatika.bisis.hitlist.groupview.GrupniPrikazFrame;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.report.ReportChooserDlg;
import com.ftninformatika.bisis.report.ReportFrame;
import com.ftninformatika.bisis.search.Result;
import com.ftninformatika.bisis.search.SearchAdvancedFrame;
import com.ftninformatika.bisis.search.SearchFrame;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.fx.JFXInternalFrame.JFXCoderFrame;
import com.ftninformatika.utils.fx.JFXInternalFrame.JFXInternalFrame;
import net.sf.jasperreports.engine.JasperPrint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.text.MessageFormat;


public class MainFrame extends JFrame {

    public MainFrame() {
        String iconPath = "/icons/appicon.png";
        String title = Messages.getString("MAIN_BISIS.0");
        if (BisisApp.appConfig instanceof TestConfig) {
            iconPath = "/icons/appicon_test.png";
            title = Messages.getString("MAIN_BISIS_TEST.0");
        }
        setTitle(MessageFormat.format(title, BisisApp.appVersion));
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        setIconImage(icon.getImage());
        add(desktop, BorderLayout.CENTER);
        add(getStatusnaLinija(), BorderLayout.SOUTH);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                shutdown();
            }
        });
        desktop.add(searchFrame);
    }

    public void initialize(Librarian lib){
        statusnaLinija.setText(MessageFormat.format(Messages.getString("MAIN_LIBRARIAN.0"), BisisApp.appConfig.getLibrarian().getUsername()));
        if (lib.isAdministracija()){
            desktop.add(getIntOznFrame());
            desktop.add(getNacinFrame());
            desktop.add(getOdeljenjeFrame());
            desktop.add(getInvknjFrame());
            desktop.add(getPodlokacijaFrame());
            desktop.add(getPovezFrame());
            desktop.add(get992bFrame());
            desktop.add(getStatusFrame());
            desktop.add(getFormatFrame());
            desktop.add(getUnlockFrame());
            desktop.add(getCountersFrame());
            desktop.add(getSearchAdvancedFrame());
        }
        if (lib.isCirkulacija() && !lib.isAdministracija() && !lib.isObrada()){
            Cirkulacija.startApp(lib);
        }else{
            searchFrame.setVisible(true);
        }
    }

    public void showFrame(JInternalFrame frame) {
        try {
            if (!frame.isVisible())
                frame.setVisible(true);
            if (frame.isIcon())
                frame.setIcon(false);
            if (!frame.isSelected())
                frame.setSelected(true);
        } catch (Exception ex) {
        }
    }

    public void showSearchFrame() {
        showFrame(searchFrame);
    }

    public void showHitlistFrame() {
        showFrame(hlf);
    }

        public void addHitListFrame(Result queryResults, String sQuery) {
            if(hlf==null){
                hlf = new HitListFrame(queryResults,sQuery);
                desktop.add(hlf);
            }
            else {
                hlf.setQueryResults(sQuery, queryResults);
                showHitlistFrame();
            }
            try {
                hlf.setMaximum(true);
            } catch (PropertyVetoException e) {
            }
            hlf.setVisible(true);
        }
        public void addBranchesFrame(String query, String[] hits) {
            brf = new GrupniPrikazFrame(query, hits);
            desktop.add(brf);
            brf.setVisible(true);
        }
/*
        public NetHitListFrame addNetHitListFrame(String query, String convId, boolean compress,LibraryServerDesc lib, Vector<BriefInfoModel> hits) {
            NetHitListFrame hlf = new NetHitListFrame(query, convId, compress, lib, hits);
            desktop.add(hlf);
            hlf.setVisible(true);
            return hlf;
        }
*/
        public void addReportFrame(String title, JasperPrint jp) {
            ReportFrame rf = new ReportFrame(title, jp);
            desktop.add(rf);
            rf.setVisible(true);
        }

    private void shutdown() {
        if(Obrada.isEditorClosable()){
            searchFrame.closeSearchFrame();
            Cirkulacija.getApp().close();
            System.exit(0);
        }
        System.exit(0);
    }

    public SearchFrame getSearchFrame(){
        return searchFrame;
    }

    public HitListFrame getHitListFrame(){
        return hlf;
    }

    public void insertFrame(JInternalFrame frame){


        desktop.add(frame);
    }
/*
    public BackupDlg getBackupDlg(){
        if (backupDlg == null)
            backupDlg = new BackupDlg();
        return backupDlg;
    }*/

    public ReportChooserDlg getReportChooserDlg(){
        if (reportChooserDlg == null)
            reportChooserDlg = new ReportChooserDlg();
        return reportChooserDlg;
    }

    public JFXCoderFrame getOdeljenjeFrame(){
        if (odeljenjeFrame == null){
            odeljenjeFrame = new JFXCoderFrame(CodersHelper.ODELJENJE_CODER);
            odeljenjeFrame.setResizable(false);
            odeljenjeFrame.setMaximizable(false);
        }
        return odeljenjeFrame;
    }

    public JFXCoderFrame getIntOznFrame(){
        if (intOznFrame == null){
            intOznFrame = new JFXCoderFrame(CodersHelper.INTERNAOZNAKA_CODER);
            intOznFrame.setMaximizable(false);
            intOznFrame.setResizable(false);
        }
        return intOznFrame;
    }

    public JFXCoderFrame getNacinFrame(){
        if (nacinFrame == null){
            nacinFrame = new JFXCoderFrame(CodersHelper.NACINNABAVKE_CODER);
            nacinFrame.setResizable(false);
            nacinFrame.setMaximizable(false);
        }
        return nacinFrame;
    }

    public JFXCoderFrame getPodlokacijaFrame(){
        if (podlokacijaFrame == null){
            podlokacijaFrame = new JFXCoderFrame(CodersHelper.PODLOKACIJA_CODER);
            podlokacijaFrame.setMaximizable(false);
            podlokacijaFrame.setResizable(false);
        }
        //zbog reload- a sifarnika TODO- staviti na svako mesto gde su sifarnici koji se filtriraju
        try {
            podlokacijaFrame.createScene();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return podlokacijaFrame;
    }

    public JFXCoderFrame getPovezFrame(){
        if (povezFrame == null){
            povezFrame = new JFXCoderFrame(CodersHelper.POVEZ_CODER);
            povezFrame.setResizable(false);
            povezFrame.setMaximizable(false);
        }
        return povezFrame;
    }

    public JFXCoderFrame get992bFrame(){
        if (_992bFrame == null){
            _992bFrame = new JFXCoderFrame(CodersHelper.TASK_CODER);
            _992bFrame.setMaximizable(false);
            _992bFrame.setResizable(false);
        }
        return _992bFrame;
    }

    public JFXCoderFrame getFormatFrame(){
        if (formatFrame == null){
            formatFrame = new JFXCoderFrame(CodersHelper.FORMAT_CODER);
            formatFrame.setResizable(false);
            formatFrame.setMaximizable(false);
        }
        return formatFrame;
    }

    public JFXCoderFrame getStatusFrame(){
        if (statusFrame == null){
            statusFrame = new JFXCoderFrame(CodersHelper.STATUS_CODER);
            statusFrame.setMaximizable(false);
            statusFrame.setResizable(false);
        }
        return statusFrame;
    }

    public JFXCoderFrame getInvknjFrame(){
        if (invknjFrame == null){
            invknjFrame = new JFXCoderFrame(CodersHelper.INVENTARNAKNJIGA_CODER);
            invknjFrame.setResizable(false);
            invknjFrame.setMaximizable(false);
        }
        return invknjFrame;
    }

    public CoderFrame getCountersFrame(){
        if (countersFrame == null){
            countersFrame = new CoderFrame(TableCatalog.getTable("Counters"));
        }
        return countersFrame;
    }
    public SearchAdvancedFrame getSearchAdvancedFrame(){
        if (searchAdvancedFrame == null){
            searchAdvancedFrame = new SearchAdvancedFrame();
        }
        return searchAdvancedFrame;
    }
    public JTextField getStatusnaLinija(){
        if(statusnaLinija==null){
            statusnaLinija = new JTextField();
            statusnaLinija.setEditable(false);
        }
        return statusnaLinija;
    }

    public JInternalFrame getUnlockFrame(){
        if(unlockFrame == null){
            unlockFrame =  new JFXInternalFrame(Messages.getString("MAINFRAME_UNLOCK_TITLE"),
                    "/fx/unlock/unlockFrame.fxml",
                    "/fx/unlock/css/unlockFrame.css",null);
            unlockFrame.setResizable(false);
            unlockFrame.setMaximizable(false);
            unlockFrame.setSize(420, 250);
            unlockFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
        return unlockFrame;
    }

    public void selectNextInternalFrame(){
        desktop.selectFrame(true);
    }

    private JDesktopPane desktop = new JDesktopPane();
    private SearchFrame searchFrame = new SearchFrame();
    private HitListFrame hlf = null;
    private GrupniPrikazFrame brf=null;
//    private BackupDlg backupDlg = null;
    private ReportChooserDlg reportChooserDlg = null;
    private JInternalFrame unlockFrame = null;
    private JFXCoderFrame intOznFrame = null;
    private JFXCoderFrame nacinFrame = null;
    private JFXCoderFrame odeljenjeFrame = null;
    private JFXCoderFrame podlokacijaFrame = null;
    private JFXCoderFrame povezFrame = null;
    private JFXCoderFrame _992bFrame = null;
    private JFXCoderFrame formatFrame = null;
    private JFXCoderFrame statusFrame = null;
    private JFXCoderFrame invknjFrame = null;
    private CoderFrame countersFrame = null;
    private SearchAdvancedFrame searchAdvancedFrame= null;
    private JTextField statusnaLinija = null;


}
