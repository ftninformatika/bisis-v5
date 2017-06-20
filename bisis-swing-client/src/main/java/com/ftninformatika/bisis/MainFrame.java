package com.ftninformatika.bisis;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.librarian.Librarian;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.util.Vector;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("BISIS "); // + BisisApp.VERSION);
        add(desktop, BorderLayout.CENTER);
        add(getStatusnaLinija(), BorderLayout.SOUTH);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                shutdown();
            }
        });
       // desktop.add(searchFrame);
    }

    public void initialize(Librarian lib){
        statusnaLinija.setText("Bibliotekar: "+BisisApp.appConfig.getLibrarian().getUsername());
        if (lib.isAdministration()){
         /*   desktop.add(getIntOznFrame());
            desktop.add(getNacinFrame());
            desktop.add(getOdeljenjeFrame());
            desktop.add(getInvknjFrame());
            desktop.add(getPodlokacijaFrame());
            desktop.add(getPovezFrame());
            desktop.add(get992bFrame());
            desktop.add(getStatusFrame());
            desktop.add(getFormatFrame());
            desktop.add(getCountersFrame());
            desktop.add(getSearchAdvancedFrame());*/
        }
        if (lib.isCirculation() && !lib.isAdministration() && !lib.isCataloguing()){
        //    Cirkulacija.startApp(lib);
        }else{
         //   searchFrame.setVisible(true);
        }
    }
/*
    public void showSearchFrame() {
        try {
            if (!searchFrame.isVisible())
                searchFrame.setVisible(true);
            if (searchFrame.isIcon())
                searchFrame.setIcon(false);
            if (!searchFrame.isSelected())
                searchFrame.setSelected(true);
        } catch (Exception ex) {
        }
    }

    public void showHitlistFrame() {
        try {
            if (!hlf.isVisible())
                hlf.setVisible(true);
            if (hlf.isIcon())
                hlf.setIcon(false);
            if (!hlf.isSelected())
                hlf.setSelected(true);
        } catch (Exception ex) {
        }
    }

    public void addHitListFrame(String query, Result queryResults) {
        if(hlf==null){
            hlf = new HitListFrame(query, queryResults);
            desktop.add(hlf);
        }
        else
            hlf.setQueryResults(query, queryResults);
        showHitlistFrame();
        try {
            hlf.setMaximum(true);
        } catch (PropertyVetoException e) {
        }
        hlf.setVisible(true);
    }
    public void addBranchesFrame(String query, int[] hits) {
        brf = new GrupniPrikazFrame(query, hits);
        desktop.add(brf);
        brf.setVisible(true);
    }

    public NetHitListFrame addNetHitListFrame(String query, String convId, boolean compress,LibraryServerDesc lib, Vector<BriefInfoModel> hits) {
        NetHitListFrame hlf = new NetHitListFrame(query, convId, compress, lib, hits);
        desktop.add(hlf);
        hlf.setVisible(true);
        return hlf;
    }

    public void addReportFrame(String title, JasperPrint jp) {
        ReportFrame rf = new ReportFrame(title, jp);
        desktop.add(rf);
        rf.setVisible(true);
    }
*/
    private void shutdown() {
        /*if(Obrada.isEditorClosable()){
            searchFrame.closeSearchFrame();
            Cirkulacija.getApp().close();
            System.exit(0);
        }*/
        System.exit(0);
    }
/*
    public SearchFrame getSearchFrame(){
        return searchFrame;
    }

    public HitListFrame getHitListFrame(){
        return hlf;
    }

    public void insertFrame(JInternalFrame frame){
        desktop.add(frame);
    }

    public BackupDlg getBackupDlg(){
        if (backupDlg == null)
            backupDlg = new BackupDlg();
        return backupDlg;
    }

    public ReportChooserDlg getReportChooserDlg(){
        if (reportChooserDlg == null)
            reportChooserDlg = new ReportChooserDlg();
        return reportChooserDlg;
    }

    public CoderFrame getOdeljenjeFrame(){
        if (odeljenjeFrame == null){
            odeljenjeFrame = new CoderFrame(TableCatalog.getTable("Odeljenje"));
        }
        return odeljenjeFrame;
    }

    public CoderFrame getIntOznFrame(){
        if (intOznFrame == null){
            intOznFrame = new CoderFrame(TableCatalog.getTable("Interna_oznaka"));
        }
        return intOznFrame;
    }

    public CoderFrame getNacinFrame(){
        if (nacinFrame == null){
            nacinFrame = new CoderFrame(TableCatalog.getTable("Nacin_nabavke"));
        }
        return nacinFrame;
    }

    public CoderFrame getPodlokacijaFrame(){
        if (podlokacijaFrame == null){
            podlokacijaFrame = new CoderFrame(TableCatalog.getTable("Podlokacija"));
        }
        return podlokacijaFrame;
    }

    public CoderFrame getPovezFrame(){
        if (povezFrame == null){
            povezFrame = new CoderFrame(TableCatalog.getTable("Povez"));
        }
        return povezFrame;
    }

    public CoderFrame get992bFrame(){
        if (_992bFrame == null){
            _992bFrame = new CoderFrame(TableCatalog.getTable("Sifarnik_992b"));
        }
        return _992bFrame;
    }

    public CoderFrame getFormatFrame(){
        if (formatFrame == null){
            formatFrame = new CoderFrame(TableCatalog.getTable("SigFormat"));
        }
        return formatFrame;
    }

    public CoderFrame getStatusFrame(){
        if (statusFrame == null){
            statusFrame = new CoderFrame(TableCatalog.getTable("Status_Primerka"));
        }
        return statusFrame;
    }

    public CoderFrame getInvknjFrame(){
        if (invknjFrame == null){
            invknjFrame = new CoderFrame(TableCatalog.getTable("Invknj"));
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
    }*/
    public JTextField getStatusnaLinija(){
        if(statusnaLinija==null){
            statusnaLinija = new JTextField();
            statusnaLinija.setEditable(false);
        }
        return statusnaLinija;
    }

    public void selectNextInternalFrame(){
        desktop.selectFrame(true);
    }

    private JDesktopPane desktop = new JDesktopPane();
  /*  private SearchFrame searchFrame = new SearchFrame();
    private HitListFrame hlf = null;
    private GrupniPrikazFrame brf=null;
    private BackupDlg backupDlg = null;
    private ReportChooserDlg reportChooserDlg = null;

    private CoderFrame intOznFrame = null;
    private CoderFrame nacinFrame = null;
    private CoderFrame odeljenjeFrame = null;
    private CoderFrame podlokacijaFrame = null;
    private CoderFrame povezFrame = null;
    private CoderFrame _992bFrame = null;
    private CoderFrame formatFrame = null;
    private CoderFrame statusFrame = null;
    private CoderFrame invknjFrame = null;
    private CoderFrame countersFrame = null;
    private SearchAdvancedFrame searchAdvancedFrame= null;*/
    private JTextField statusnaLinija = null;


}
