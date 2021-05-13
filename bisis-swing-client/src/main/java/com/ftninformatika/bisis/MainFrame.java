package com.ftninformatika.bisis;

import com.ftninformatika.bisis.admin.coders.CoderFrame;
import com.ftninformatika.bisis.admin.coders.TableCatalog;
import com.ftninformatika.bisis.circ.Cirkulacija;
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
import com.ftninformatika.bisis.search.net.NetHitListFrame;
import com.ftninformatika.utils.Messages;
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
        if (lib.hasRole(Librarian.Role.ADMINISTRACIJA)){
            desktop.add(getCountersFrame());
            desktop.add(getSearchAdvancedFrame());
        }
        if (lib.hasRole(Librarian.Role.CIRKULACIJA) && !lib.hasRole(Librarian.Role.ADMINISTRACIJA) && !lib.hasRole(Librarian.Role.OBRADA)){
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

    public void addNetHitListFrame(NetHitListFrame netHitListFrame) {
        try {
            desktop.remove(nhlf);
            desktop.repaint();
        } catch (Exception e) {}
        nhlf = netHitListFrame;
        desktop.add(netHitListFrame);
        nhlf.setVisible(true);
    }

    public void addReportFrame(String title, JasperPrint jp) {
        ReportFrame rf = new ReportFrame(title, jp);
        desktop.add(rf);
        rf.setVisible(true);
    }

    private void shutdown() {
        if (Obrada.isEditorClosable()) {
            searchFrame.closeSearchFrame();
            Cirkulacija.getApp().close();
            System.exit(0);
        }
        System.exit(0);
    }

    public void logOut() {
        if (Obrada.isEditorClosable()) {
            searchFrame.closeSearchFrame();
            Cirkulacija.getApp().close();
        }
        BisisApp.clearForRestart();
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

    public void selectNextInternalFrame(){
        desktop.selectFrame(true);
    }

    private JDesktopPane desktop = new JDesktopPane();
    private SearchFrame searchFrame = new SearchFrame();
    private HitListFrame hlf = null;
    private NetHitListFrame nhlf = null;
    private GrupniPrikazFrame brf=null;
//    private BackupDlg backupDlg = null;
    private ReportChooserDlg reportChooserDlg = null;
    private CoderFrame countersFrame = null;
    private SearchAdvancedFrame searchAdvancedFrame= null;
    private JTextField statusnaLinija = null;


}
