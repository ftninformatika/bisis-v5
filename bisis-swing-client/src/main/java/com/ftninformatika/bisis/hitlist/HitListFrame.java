package com.ftninformatika.bisis.hitlist;

import com.ftninformatika.bisis.records.RecordModification;
import com.ftninformatika.bisis.search.Result;
import com.ftninformatika.utils.Messages;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.html.HTMLEditorKit;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.cards.Report;
import com.ftninformatika.bisis.editor.EditorFrame;
import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.bisis.hitlist.formatters.RecordFormatter;
import com.ftninformatika.bisis.hitlist.formatters.RecordFormatterFactory;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.bisis.editor.recordtree.RecordUtils;
import net.miginfocom.swing.MigLayout;

public class HitListFrame extends JInternalFrame {

    public static final int PAGE_SIZE = 10;

    public HitListFrame(Result queryResult, String sQuery) {
        super(Messages.getString("HITLIST_SEARCHRESULTS"), true, true, true, true);
        this.queryResult = queryResult;
        renderer.setResults(queryResult);
        this.query = sQuery;
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        btnFirst.setIcon(new ImageIcon(getClass().getResource("/icons/first.gif")));
        btnPrev.setIcon(new ImageIcon(getClass().getResource("/icons/prev.gif")));
        btnNext.setIcon(new ImageIcon(getClass().getResource("/icons/next.gif")));
        btnLast.setIcon(new ImageIcon(getClass().getResource("/icons/last.gif")));
        btnEdit.setIcon(new ImageIcon(getClass().getResource("/icons/edit.gif")));
        btnDelete.setIcon(new ImageIcon(getClass().getResource("/icons/remove.gif")));
        btnNew.setIcon(new ImageIcon(getClass().getResource("/icons/copy.gif")));
        btnInventar.setIcon(new ImageIcon(EditorFrame.class.getResource("/icons/book16.png")));
        btnAnalitika.setIcon(new ImageIcon(EditorFrame.class.getResource("/icons/doc_rich16.png")));

        lbHitList.setModel(hitListModel);
        lbHitList.setCellRenderer(renderer);
        spHitList.setViewportView(lbHitList);
        //spHitList.setPreferredSize(new Dimension(500, 500));
        rnLabel.setText("<html><B>RN</B>");
        rnTxtFld.setEditable(false);
        cardPane.setEditorKit(new HTMLEditorKit());
        cardPane.setEditable(false);
        fullFormatPane.setEditorKit(new HTMLEditorKit());
        formatter = RecordFormatterFactory.getFormatter(RecordFormatterFactory.FORMAT_FULL);
        fullFormatPane.setEditable(false);
        JScrollPane cardPaneScroll = new JScrollPane(cardPane);
        //cardPaneScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane fullFormatPaneScroll = new JScrollPane(fullFormatPane);
        inventarTable.setModel(inventarTableModel);
        inventarTable.setAutoCreateRowSorter(true);
        inventarTable.setCellSelectionEnabled(true);
        inventarTable.putClientProperty("Quaqua.Table.style", "striped");
        //inventarTable.setDefaultRenderer(inventarTable.getColumnClass(0), inventartTableRenderer);
        //inventarTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        adjustInventarColumnWidth();

        //uploadedFilesTable.setModel(uploadedFilesTableModel);

        JScrollPane inventarScrollPane = new JScrollPane(inventarTable);
        JScrollPane uploadedFilesScrollPane = new JScrollPane(uploadedFilesTable);

        //inventarScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        createMetaDataPanel();

        tabbedPane.setPreferredSize(new Dimension(400, 500));
        JPanel inventarTabPanel = new JPanel();
        inventarTabPanel.setLayout(new BorderLayout());
        inventarTabPanel.add(inventarScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab(Messages.getString("HITLIST_FULLFORMAT"), fullFormatPaneScroll);
        tabbedPane.addTab(Messages.getString("HITLIST_CARD"), cardPaneScroll);
        tabbedPane.addTab(Messages.getString("HITLIST_INVENTAR"), inventarScrollPane);
        tabbedPane.addTab(Messages.getString("HITLIST_DOCUMENTS"), uploadedFilesScrollPane);
			/*if(!BisisApp.isFileMgrEnabled()){
				tabbedPane.setEnabledAt(3, false);
			}else{
				tabbedPane.setEnabledAt(3, true);
			}*/

        tabbedPane.addTab("Мета подаци", metaDataPanel);
        pageTxtFld.setPreferredSize(new Dimension(35, 25));

        // panel na kom su prikazani svi pogoci
        MigLayout layout = new MigLayout(
                "",
                "[][][][]",
                "[]rel[]rel[grow]para[]");
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new GridLayout(3, 1));
        labelsPanel.add(lQuery);
        labelsPanel.add(lFromTo);
        labelsPanel.add(lBrPrimeraka);
        allResultsPanel.setLayout(layout);
        allResultsPanel.add(labelsPanel, "span 5,wrap");
        allResultsPanel.add(btnFirst, "span 5, split 5");
        allResultsPanel.add(btnPrev, "");
        allResultsPanel.add(pageTxtFld, "");
        allResultsPanel.add(btnNext, "");
        allResultsPanel.add(btnLast, "wrap");
        allResultsPanel.add(spHitList, "span 5, grow, wrap");
        allResultsPanel.add(btnBranches, "span 5, left, wrap");


        oneResultPanel.setLayout(new MigLayout("insets dialog, wrap", "[]rel[]rel[grow]rel[]", "[]10[grow]15[]"));
        oneResultPanel.add(rnLabel, "");
        oneResultPanel.add(rnTxtFld, "");
        oneResultPanel.add(pubTypeLabel, "wrap");
        oneResultPanel.add(tabbedPane, "span 4, split 1, wrap, grow");
        oneResultPanel.add(btnAnalitika, "span 5, split 5, right");
        oneResultPanel.add(btnDelete, "");
        oneResultPanel.add(btnInventar, "");
        oneResultPanel.add(btnNew, "");
        oneResultPanel.add(btnEdit, "wrap");
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                allResultsPanel, oneResultPanel);
        splitPane.setOneTouchExpandable(true);
        //int loc = (int)((splitPane.getBounds().getWidth()-splitPane.getDividerSize())/2);
        splitPane.setDividerLocation(500);
        add(splitPane);
        pack();
        if (!BisisApp.appConfig.getLibrarian().isObrada()) {
            btnEdit.setEnabled(false);
            btnNew.setEnabled(false);
            btnDelete.setEnabled(false);
            btnInventar.setEnabled(false);
        }
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                shutdown();
            }
        });
        listSelModel = lbHitList.getSelectionModel();
        listSelModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                handleListSelectionChanged();
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

        btnFirst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                page = 0;
                updateAvailability();
                displayPage();
            }
        });

        btnLast.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                page = pageCount() - 1;
                updateAvailability();
                displayPage();
            }
        });

        btnBrief.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                renderer.setFormatter(RecordFormatterFactory.FORMAT_BRIEF);
                hitListModel.refresh();
            }
        });

        pageTxtFld.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ev) {
                if (ev.getKeyCode() == KeyEvent.VK_ENTER) {
                    // skok na odgovarajucu stranicu
                    try {
                        int pageNum = Integer.parseInt(pageTxtFld.getText());
                        if (pageNum >= 0 && pageNum < pageCount()) {
                            page = pageNum;
                            updateAvailability();
                            displayPage();
                            lbHitList.grabFocus();
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        });

        btnFull.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                renderer.setFormatter(RecordFormatterFactory.FORMAT_FULL);
                hitListModel.refresh();
            }
        });

        lbHitList.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ev) {
                handleKeys(ev);
            }
        });

        lbHitList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    btnEdit.doClick();
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                handleDeleteRecord();
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String recordId = ((Record) lbHitList.getSelectedValue()).get_id();
                    Record rec = BisisApp.recMgr.getAndLock(recordId, BisisApp.appConfig.getLibrarian().get_id());

                    if (rec == null) {
                        JTextArea ta = new JTextArea(4, 10);
                        ta.setWrapStyleWord(true);
                        ta.setLineWrap(true);
                        ta.setCaretPosition(0);
                        ta.setEditable(false);
                        String rn = "";
                        if(selectedRecord.getRN() != 0)
                            rn = selectedRecord.getRN() + "";
                        ta.setText(MessageFormat.format(Messages.getString("HITLIST_RECORD_WITH_ID.0.IS_LOCKED"), "" + rn + "\n"));
                        JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
                                new JScrollPane(ta),
                                Messages.getString("RECORD_LOCKED"), JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    Obrada.editRecord(rec);
                }/* catch (LockException e) {
                    JOptionPane.showMessageDialog(BisisApp.getMainFrame(),e.getMessage(),"Zaklju\u010dan zapis",JOptionPane.INFORMATION_MESSAGE);
                }*/ catch (IOException e) {
                    JOptionPane.showMessageDialog(BisisApp.getMainFrame(), e.getMessage(), Messages.getString("HITLIST_ERROR_OCCURRED"), JOptionPane.INFORMATION_MESSAGE);

                    e.printStackTrace();
                }
            }
        });

        btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                Record rec = ((Record) lbHitList.getSelectedValue()).copyWithoutHoldings();
                if (rec != null) Obrada.newRecord(rec);
            }
        });

        btnInventar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String recordId = ((Record) lbHitList.getSelectedValue()).get_id();
                Record rec = null;
                try {
                    rec = BisisApp.recMgr.getAndLock(recordId, BisisApp.appConfig.getLibrarian().get_id());

                    if (rec == null) { //vraca null ako je vec u upotrebi
                        JOptionPane.showMessageDialog(BisisApp.getMainFrame(), MessageFormat.format(Messages.getString("HITLIST_RECORD_WITH_ID.0.IS_LOCKED"), recordId), Messages.getString("RECORD_LOCKED"), JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    Obrada.editInventar(rec);
                } /*catch (LockException e) {
							JOptionPane.showMessageDialog(BisisApp.getMainFrame(),e.getMessage(),"Zaklju\u010dan zapis",JOptionPane.INFORMATION_MESSAGE);
		                } */ catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(BisisApp.getMainFrame(), Messages.getString("HITLIST_PLEASE_SELECT_RECORD"), Messages.getString("MAIN_ERROR"), JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(BisisApp.getMainFrame(), Messages.getString("HITLIST_SERVER_ERROR"), Messages.getString("MAIN_ERROR"), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        btnAnalitika.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Record rec = ((Record) lbHitList.getSelectedValue()).copyWithoutHoldings();
                if (rec != null) Obrada.editAnalitika(rec);
            }
        });

        btnBranches.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleBranches();
            }
        });

        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                handleLoadTabs();
            }
        });

        ListSelectionModel tableSelModel = inventarTable.getSelectionModel();
        tableSelModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                inventarTable.repaint();
            }
        });

        uploadedFilesTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    handleOpenFile();
                }
            }
        });

        btnBrief.setFocusable(false);
        btnFull.setFocusable(false);
        btnPrev.setFocusable(false);
        btnNext.setFocusable(false);
        btnFirst.setFocusable(false);
        btnLast.setFocusable(false);
        btnDelete.setFocusable(false);
        btnEdit.setFocusable(false);
        btnNew.setFocusable(false);
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(btnBrief);
        btnGroup.add(btnFull);
        btnBrief.setSelected(true);
        lQuery.setText(MessageFormat.format(Messages.getString("HITLIST_.0.QUERY_HTML"), query));
        updateAvailability();
        displayPage();
        lbHitList.requestFocus();
    }

    private void shutdown() {
        BisisApp.mf.getSearchFrame().show();
        try {
            BisisApp.mf.getSearchFrame().setSelected(true);
        } catch (PropertyVetoException e) {
        }
        BisisApp.mf.getSearchFrame().setDefaultFocus();
        setVisible(false);
    }

    private void updateAvailability() {
        btnPrev.setEnabled(page != 0);
        btnNext.setEnabled(page != pageCount() - 1);
    }

    private void displayPage() {
        if (queryResult == null || queryResult.getRecords().length == 0)
            return;
        int count = PAGE_SIZE;

        if (page == pageCount() - 1) {  //ako je poslednja stranica
            if (queryResult.getResultCount() % PAGE_SIZE == 0) {
                count = PAGE_SIZE;
            } else {
                count = queryResult.getResultCount() % PAGE_SIZE;
            }
        }
        pageTxtFld.setText(String.valueOf(page));
        String[] recIDs = new String[count];
        for (int i = 0; i < count; i++)
            recIDs[i] = queryResult.getRecords()[page * PAGE_SIZE + i];
        hitListModel.setHits(recIDs);
        lbHitList.setSelectedIndex(0);
        handleListSelectionChanged();
        lFromTo.setText(MessageFormat.format(Messages.getString("HITLIST_HITS0.1.2_HTML"), page * PAGE_SIZE + 1, page * PAGE_SIZE + count, queryResult.getResultCount()));
        lBrPrimeraka.setText(MessageFormat.format(Messages.getString("HITLIST_ITEMS_NO.0._HTML"), queryResult.getInvs().size()));

    }

    private int pageCount() {
        if (queryResult == null || queryResult.getResultCount() == 0)
            return 0;
        return queryResult.getResultCount() / PAGE_SIZE + (queryResult.getResultCount() % PAGE_SIZE > 0 ? 1 : 0);
    }


    private void handleKeys(KeyEvent ev) {
        switch (ev.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                btnEdit.doClick();
                break;
            case KeyEvent.VK_ESCAPE:
                shutdown();
                break;
            case KeyEvent.VK_LEFT:
                btnPrev.doClick();
                break;
            case KeyEvent.VK_RIGHT:
                btnNext.doClick();
        }
        if (ev.getKeyCode() == KeyEvent.VK_F6 &&
                ev.getModifiers() == InputEvent.CTRL_MASK) {
            BisisApp.mf.selectNextInternalFrame();
        }
    }

    private void handleListSelectionChanged() {
        String recordId = ((Record) lbHitList.getSelectedValue()).get_id();//--------------
        Record zapis = null;
        try {
            zapis = BisisApp.recMgr.getRecord(recordId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        selectedRecord = zapis;
        rnTxtFld.setText(String.valueOf(selectedRecord.getRN()));
        String pubTypeStr = "";
        if (selectedRecord.getPubType() == 2)
            pubTypeStr = Messages.getString("HITLIST_SERIAL");
        else if (selectedRecord.getPubType() == 3)
            pubTypeStr = Messages.getString("HITLIST_ANALITIC");
        else
            pubTypeStr = Messages.getString("HITLIST_MONOGRAPH");
        pubTypeLabel.setText("<html><B>" + pubTypeStr + "</B>");
        if (selectedRecord.getPubType() == 3)
            btnAnalitika.setEnabled(false);
        else
            btnAnalitika.setEnabled(true);
        handleLoadTabs();
    }

    private void handleLoadTabs() {
        if (tabbedPane.getSelectedIndex() == 0) {
            fullFormatPane.setText(formatter.toHTML(selectedRecord, "sr"));
            fullFormatPane.setCaretPosition(0);
        } else if (tabbedPane.getSelectedIndex() == 1) {
            loadCard(selectedRecord);
        } else if (tabbedPane.getSelectedIndex() == 2) {

            String recordId = ((Record) lbHitList.getSelectedValue()).get_id();
            try {
                selectedRecord = BisisApp.recMgr.getRecord(recordId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            inventarTableModel.setRecord(selectedRecord);
            adjustInventarColumnWidth();

        } else if (tabbedPane.getSelectedIndex() == 3) {
            String recordId = ((Record) lbHitList.getSelectedValue()).get_id();
            //selectedRecord = BisisApp.getRecordManager().getRecord(recordId); //TODO - uploaded files nema
            //uploadedFilesTableModel.setDocFiles(BisisApp.getRecordManager().getDocFiles(selectedRecord.getRN()));
        } else if (tabbedPane.getSelectedIndex() == 4) {
            String recordId = ((Record) lbHitList.getSelectedValue()).get_id();
            Record zapis = null;
            try {
                zapis = BisisApp.recMgr.getRecord(recordId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectedRecord = zapis;
            loadMetaData(selectedRecord);
        }
    }
//  public void setRecordsQueryResult(List<String> recs, String sQuery){
//      this.recordsQueryResultIds = recs;
//      this.query = sQuery;
//      renderer.setResults();
//      updateAvailability();
//      page = 0;
//      lQuery.setText(MessageFormat.format(Messages.getString("HITLIST_.0.QUERY_HTML"), sQuery));
//      displayPage();
//  }

    //private void handleTab

    private void handleBranches() {
        BisisApp.mf.addBranchesFrame(query, queryResult.getRecords());
    }

    /*
     * ucitava listic za zapis record
     * ucitava se default listic za tip publikacije zapisa
     * TODO
     */
    private void loadCard(Record record) {
        String html = Report.makeOne(record, true);
        cardPane.setText(html);
        cardPane.setCaretPosition(0);
    }

    private void createModificationList(Record rec){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        DefaultListModel model = new DefaultListModel();
        JList list = new JList(model);
        list.putClientProperty("Quaqua.List.style", "striped");
        modificationScrollPane.setViewportView(list);
        int cnt = 0;
        for (RecordModification r: rec.getRecordModifications()){
            String s = (cnt + 1) + ".  " + sdf.format(r.getDateOfModification()) + " - " + r.getLibrarianUsername() + "   ";
            model.add(cnt, s);
            cnt++;
        }
    }


    private void createMetaDataPanel() {
        metaDataPanel.setLayout(new MigLayout("", "", "[]10[]20[]10[]20[]"));
        metaDataPanel.add(new JLabel(Messages.getString("HITLIST_CREATED_BY_HTML")));
        metaDataPanel.add(recCreatorLabel, "wrap");

        metaDataPanel.add(new JLabel(Messages.getString("HITLIST_MODOFIED_BY_HTML")));
        metaDataPanel.add(recModifierLabel, "wrap");

        metaDataPanel.add(new JLabel(Messages.getString("HITLIST_CREATION_DATE_HTML")));
        metaDataPanel.add(recCreationDateLabel, "wrap");

        metaDataPanel.add(new JLabel(Messages.getString("HITLIST_CHANGED_DATE_HTML")));
        metaDataPanel.add(recModificationDateLabel, "wrap");

        metaDataPanel.add(new JLabel(Messages.getString("HITLIST_NOT_MODIFIED_SINCE_HTML")), "wrap");
        metaDataPanel.add(modificationScrollPane, "wrap");

    }

    private void loadMetaData(Record rec) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        if (rec.getCreator() != null)
            recCreatorLabel.setText(rec.getCreator().getUsername());
        else
            recCreatorLabel.setText("");

        if (rec.getModifier() != null)
            recModifierLabel.setText(rec.getModifier().getUsername());
        else
            recModifierLabel.setText("");

        if (rec.getCreationDate() != null)
            recCreationDateLabel.setText(sdf.format(rec.getCreationDate()));
        else
            recCreationDateLabel.setText("");

        if (rec.getLastModifiedDate() != null)
            recModificationDateLabel.setText(sdf.format(rec.getLastModifiedDate()));
        else
            recModificationDateLabel.setText("");
        //ako ima modifikacija od kada su ubacene
        if (rec.getRecordModifications().size() > 0) {
            createModificationList(rec);
            modificationScrollPane.setOpaque(true);
            modificationScrollPane.setEnabled(false);
        }
        else {
            JLabel noModification = new JLabel(Messages.getString("HITLIST_NOT_MODIFIED_SINCE_HTML2"));
            noModification.setOpaque(true);
            modificationScrollPane.setViewportView(noModification);
            modificationScrollPane.setOpaque(true);

        }
    }

    private void loadUploadedFiles(Record rec) {


    }

    public void setSearchModel(SearchModel s) {
        this.searchModel = s;
    }

    private void handleDeleteRecord() {
        Record rec = (Record) lbHitList.getSelectedValue();
        Object[] options = {Messages.getString("HITLIST_DELETE"), Messages.getString("HITLIST_CANCEL")};
        StringBuffer messBuff = new StringBuffer();
        messBuff.append(Messages.getString("HITLIST_ARE_YOU_SURE_TO_DELETE"));
        messBuff.append(RecordUtils.getDeleteRecordReport(rec));
        int ret = JOptionPane.showOptionDialog(null, messBuff.toString(), Messages.getString("HITLIST_DELETING"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[1]);
        if (ret == 0) {
            // jos jednom pitamo
            Object[] options1 = {Messages.getString("HITLIST_CANCEL"), Messages.getString("HITLIST_CONFIRM")};
            messBuff = new StringBuffer();
            messBuff.append(Messages.getString("HITLIST_CONFIRMATION_DELETING_RECORD"));
            messBuff.append("<html><b>ID=" + rec.getRecordID() + "</b></html>\n");
            messBuff.append("<html><b>RN=" + rec.getRN() + "</b></html>");
            ret = JOptionPane.showOptionDialog(null, messBuff.toString(), Messages.getString("HITLIST_DELETING"),
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options1, options[0]);
            if (ret == 1) {
                boolean deleted = hitListModel.remove(lbHitList.getSelectedIndex());
                hitListModel.refresh();
                if (deleted)
                    JOptionPane.showMessageDialog(null, Messages.getString("HITLIST_RECORD_SUCCESSFULY_DELETED"));
                else
                    JOptionPane.showMessageDialog(null, Messages.getString("HITLIST_ERROR_WHILE_DELETING"));
            }
        }
    }

    private void adjustInventarColumnWidth() {
        TableColumn column = null;
        int napomenaColumnIndex = inventarTableModel.getColumnIndex(Messages.getString("HITLIST_NOTE"));
        int invBrojColumnIndex = inventarTableModel.getColumnIndex(Messages.getString("HITLIST_INV_NUM"));
        for (int i = 0; i < inventarTableModel.getColumnCount(); i++) {
            column = inventarTable.getColumnModel().getColumn(i);
            if (inventarTableModel.isSifriranaKolona(i))
                column.setPreferredWidth(30);
            else if (i == napomenaColumnIndex || i == invBrojColumnIndex)
                column.setPreferredWidth(100);
            else
                column.setPreferredWidth(80);
        }
    }

    public void setQueryResults(String query, Result queryResults) {
        this.queryResult = queryResults;
        this.query = query;
        renderer.setResults(queryResults);
        updateAvailability();
        page = 0;
        lQuery.setText(MessageFormat.format(Messages.getString("HITLIST_.0.QUERY_HTML"), query));
        displayPage();
    }

    private void handleOpenFile() {
		/*DocFile docFile = uploadedFilesTableModel.getFileForRow(uploadedFilesTable.getSelectedRow());
		File f = FileManagerClient.download(BisisApp.getFileManagerURL(), docFile);
		try {
			Desktop.getDesktop().open(f);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Gre\u0161ka prilikim otvaranja dokumenta.\n","Gre\u0161ka",JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			e.printStackTrace();
		}*/
    }


    private JLabel lQuery = new JLabel();
    private JLabel lFromTo = new JLabel();
    private JLabel lBrPrimeraka = new JLabel();
    private JToggleButton btnBrief = new JToggleButton(Messages.getString("HITLIST_BRIEF"));
    private JToggleButton btnFull = new JToggleButton(Messages.getString("HITLIST_FULL"));
    private JButton btnPrev = new JButton(/*"Prethodni"*/);
    private JButton btnNext = new JButton(/*"Slede\u0107i"*/);
    private JButton btnFirst = new JButton(/*"Prvi"*/);
    private JButton btnLast = new JButton(/*"Poslednji"*/);
    private JTextField pageTxtFld = new JTextField(3);

    private JButton btnDelete = new JButton(Messages.getString("HITLIST_DELETE"));
    private JButton btnEdit = new JButton(Messages.getString("HITLIST_OPEN"));
    private JButton btnNew = new JButton(Messages.getString("HITLIST_NEW"));
    private JButton btnInventar = new JButton(Messages.getString("HITLIST_INVENTAR"));
    private JButton btnAnalitika = new JButton(Messages.getString("HITLIST_ANALITICS"));

    private JButton btnBranches = new JButton(Messages.getString("HITLIST_GROUPVIEW"));

    private JScrollPane spHitList = new JScrollPane();
    private JList lbHitList = new JList();
    private HitListModel hitListModel = new HitListModel();
    private ListSelectionModel listSelModel;
    private HitListRenderer renderer = new HitListRenderer();

    private JPanel allResultsPanel = new JPanel();
    private JPanel oneResultPanel = new JPanel();
    private JSplitPane splitPane;

    private JTabbedPane tabbedPane = new JTabbedPane();
    private JLabel rnLabel = new JLabel();
    private JTextField rnTxtFld = new JTextField(5);

    private JLabel pubTypeLabel = new JLabel();
    private JEditorPane fullFormatPane = new JEditorPane();
    private JEditorPane cardPane = new JEditorPane();
    private JPanel metaDataPanel = new JPanel();
    private JLabel recCreatorLabel = new JLabel("");
    private JLabel recModifierLabel = new JLabel("");
    private JLabel recCreationDateLabel = new JLabel("");
    private JLabel recModificationDateLabel = new JLabel("");
    private JScrollPane modificationScrollPane = new JScrollPane();

    private JTable inventarTable = new JTable();
    private InventarTabTableModel inventarTableModel = new InventarTabTableModel();
    //private InventarTabTableCellRenderer inventartTableRenderer = new InventarTabTableCellRenderer();

    private JTable uploadedFilesTable = new JTable();
    //private UploadedFilesTableModel uploadedFilesTableModel = new UploadedFilesTableModel();


    private RecordFormatter formatter;
    private Record selectedRecord = null;
    private SearchModel searchModel;

    private Result queryResult;

    private String query;
    private int page = 0;
}
