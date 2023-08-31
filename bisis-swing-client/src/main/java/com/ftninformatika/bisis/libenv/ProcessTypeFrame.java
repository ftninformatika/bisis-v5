package com.ftninformatika.bisis.libenv;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.formattree.FormatUtils;
import com.ftninformatika.bisis.format.PubTypes;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.UFormat;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.librarian.ProcessType;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.librarian.db.ProcessTypeDB;
import com.ftninformatika.utils.Messages;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * @author dimicb
 */
public class ProcessTypeFrame extends JInternalFrame {

    public ProcessTypeFrame() {
        super(Messages.getString("ProcessType.PROCESSING_TYPES"), false, true, true, true); //$NON-NLS-1$
        this.setSize(780, 550);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        createProcessTypeTable();
        createPubTypeTree();
        createSubfieldsTable();
        cretePubTypeCmbBox();
        layoutPanels();
        ponistiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initializeForm();
            }
        });
        sacuvajButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSaveProcessType();
            }
        });
        pubTypeCmbBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleUpdatePubTypeTree();
            }
        });
        strelicaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAddSubfields();
            }
        });
        processTypeTable.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                handleKeys(processTypeTable, ke);
            }
        });
        subfieldsTable.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                handleKeys(subfieldsTable, ke);
            }
        });

    }

    public void initializeForm() {
        processTypeTable.clearSelection();
        selectedProcessType = new ProcessType();
        loadProcessType(selectedProcessType);
    }

    private void createProcessTypeTable() {
        processTypeTableModel = new ProcessTypeTableModel();
        processTypeTable = new JTable(processTypeTableModel);
        tableScrollPane = new JScrollPane(processTypeTable);
        processTypeTable.setRowSorter(new TableRowSorter<ProcessTypeTableModel>(processTypeTableModel));
        listSelModel = processTypeTable.getSelectionModel();
        listSelModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                handleListSelectionChanged();
            }
        });
    }

    private void createPubTypeTree() {
        treeModel = new PubTypeTreeModel(1);
        pubTypeTree = new JTree(treeModel);
        pubTypeTree.putClientProperty("JTree.lineStyle", "None"); //$NON-NLS-1$ //$NON-NLS-2$
        pubTypeTree.setRootVisible(false);
        ((BasicTreeUI) pubTypeTree.getUI()).setCollapsedIcon(null);
        ((BasicTreeUI) pubTypeTree.getUI()).setExpandedIcon(null);
        treeCellRenderer = new PubTypeTreeCellRenderer();
        pubTypeTree.setCellRenderer(treeCellRenderer);
        treeScrollPane = new JScrollPane(pubTypeTree);
    }

    private void createSubfieldsTable() {
        sfTableModel = new ProcessTypeSubfieldsTableModel();
        subfieldsTable = new JTable(sfTableModel);
        subfieldsTable.setAutoCreateRowSorter(true);
        sfTableScrollPane = new JScrollPane(subfieldsTable);
        SfTableCellRenderer renderer = new SfTableCellRenderer();
        USubfield usf = new USubfield();
        subfieldsTable.setDefaultRenderer(usf.getClass(), renderer);
        TableColumn column = subfieldsTable.getColumnModel().getColumn(0);
        column.setPreferredWidth(400);

    }

    private void cretePubTypeCmbBox() {
        PubTypeCmbBoxCellRenderer renderer = new PubTypeCmbBoxCellRenderer();
        pubTypeCmbBox.setRenderer(renderer);
        for (int i = 0; i <= PubTypes.getPubTypeCount(); i++) {
            UFormat uf = PubTypes.getPubType(i);
            if (uf != null) {
                pubTypeCmbBox.addItem(uf);
            }
        }
    }

    private void handleListSelectionChanged() {
        selectedProcessType = getSelectedProcessType();
        loadProcessType(selectedProcessType);
    }

    private void loadProcessType(ProcessType pt) {
        if (pt != null) {
            sfTableModel.setSubfieldsList(pt.getInitialSubfields(), pt.getMandatorySubfields());
            if (pt.getPubType() != null) {
                UFormat uf = pt.getPubType();
                pubTypeCmbBox.setSelectedItem(uf);
                treeModel.setPubTypeId(uf.getPubType());
            } else if (pubTypeCmbBox.getItemCount() > 0) {
                pubTypeCmbBox.setSelectedIndex(0);
            }
            if (pt.getName() != null) {
                processTypeNameTxtFld.setText(pt.getName());
            } else {
                processTypeNameTxtFld.setText("");
            }
        } else {
            sfTableModel.setSubfieldsList(new ArrayList<>(), new ArrayList<>());
            pubTypeCmbBox.setSelectedIndex(0);
            processTypeNameTxtFld.setText("");
        }
    }

    private ProcessType getProcessTypeFromForm() {
        selectedProcessType.setName(processTypeNameTxtFld.getText());
        selectedProcessType.setPubType((UFormat) pubTypeCmbBox.getSelectedItem());
        return selectedProcessType;
    }

    private void handleSaveProcessType() {
        if (processTypeNameTxtFld.getText() != null && !processTypeNameTxtFld.getText().trim().equals("")) {
            int selectedRow = processTypeTable.getSelectedRow();
            ProcessType savedProcessType = processTypeTableModel.updateProcessType(getProcessTypeFromForm());
            if (selectedRow == -1) {
                processTypeTable.setRowSelectionInterval(processTypeTableModel.getRowCount() - 1, processTypeTableModel.getRowCount() - 1);
            } else {
                processTypeTable.setRowSelectionInterval(selectedRow, selectedRow);
            }
            if (savedProcessType == null) {
                JOptionPane.showMessageDialog(BisisApp.getMainFrame(), Messages.getString("LIBENV_DB_ERROR"), Messages.getString("LIBENV_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
            } else {
                JOptionPane.showMessageDialog(BisisApp.getMainFrame(), Messages.getString("LIBENV_DB_SUCCESS"), Messages.getString("LIBENV_SUCCESS"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
            }
        } else {
            JOptionPane.showMessageDialog(BisisApp.getMainFrame(), Messages.getString("LIBENV_ERROR_NAME"), Messages.getString("LIBENV_ERROR"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdatePubTypeTree() {
        if (validatePubTypeChange().equals("")) //$NON-NLS-1$
            treeModel.setPubTypeId
                    (((UFormat) pubTypeCmbBox.getSelectedItem()).getPubType());
        else {
            JOptionPane.showMessageDialog(BisisApp.getMainFrame(), validatePubTypeChange(), Messages.getString("LIBENV_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
            pubTypeCmbBox.setSelectedItem(PubTypes.getPubType(treeModel.getPubTypeId()));
        }

    }

    private void handleAddSubfields() {
        TreePath[] selectedNodes = pubTypeTree.getSelectionPaths();
        if (selectedNodes != null) {
            for (int i = 0; i < selectedNodes.length; i++) {
                if (selectedNodes[i].getLastPathComponent() instanceof UField)
                    sfTableModel.addField((UField) selectedNodes[i].getLastPathComponent());
                else if (selectedNodes[i].getLastPathComponent() instanceof USubfield)
                    sfTableModel.addSubfield((USubfield) selectedNodes[i].getLastPathComponent());
            }
        }
    }

    private ProcessType getSelectedProcessType() {
        if (processTypeTable.getSelectedRow() > -1 &&
                processTypeTable.getSelectedRow() < processTypeTableModel.getRowCount())
            return processTypeTableModel.getRow(
                    processTypeTable.convertRowIndexToModel(processTypeTable.getSelectedRow()));
        return null;
    }

    private void handleKeys(Component comp, KeyEvent ke) {
        if (comp.equals(processTypeTable) && ke.getKeyCode() == KeyEvent.VK_DELETE)
            handleDeleteProcesstype();
        if (comp.equals(subfieldsTable) && ke.getKeyCode() == KeyEvent.VK_DELETE)
            handleDeleteSubfield();
        if (comp.equals(subfieldsTable) && ke.getKeyCode() == KeyEvent.VK_UP
                && ke.getModifiers() == InputEvent.ALT_MASK) {
            int index = subfieldsTable.getSelectedRow();
            if (index > 0) {
                sfTableModel.replaceWithPrevious(sfTableModel.getRow(index));
                sfTableModel.fireTableDataChanged();
                subfieldsTable.setRowSelectionInterval(index - 1, index - 1);
            }
        }
        if (comp.equals(subfieldsTable) && ke.getKeyCode() == KeyEvent.VK_DOWN
                && ke.getModifiers() == InputEvent.ALT_MASK) {
            int index = subfieldsTable.getSelectedRow();
            if (index < subfieldsTable.getRowCount() - 1) {
                sfTableModel.replaceWithNext(sfTableModel.getRow(index));
                sfTableModel.fireTableDataChanged();
                subfieldsTable.setRowSelectionInterval(index + 1, index + 1);
            }
        }

    }

    private void handleDeleteProcesstype() {
        if (getSelectedProcessType() != null) {
            if (isProcessTypeFree(getSelectedProcessType())) {
                String message = MessageFormat.format(Messages.getString("DELETING_PROCESS_TYPE.0"), getSelectedProcessType().getName());
                Object[] options = {Messages.getString("LIBENV_YES"), Messages.getString("LIBENV_CANCEL")}; //$NON-NLS-1$ //$NON-NLS-2$
                int ret = JOptionPane.showOptionDialog(BisisApp.getMainFrame(),
                        message, Messages.getString("LIBENV_DELETING_PT"), JOptionPane.DEFAULT_OPTION,  //$NON-NLS-1$
                        JOptionPane.YES_NO_OPTION, null, options, options[1]);
                if (ret == 0) {
                    processTypeTableModel.deleteProcessType(getSelectedProcessType());
                    initializeForm();
                }
            } else {
                String message = Messages.getString("PT_BINDED_TO_LIBRARIAN_CANT_BE_DELETED"); //$NON-NLS-1$
                JOptionPane.showMessageDialog(BisisApp.getMainFrame(), message, Messages.getString("LIBENV_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
            }
        }


    }

    private void handleDeleteSubfield() {
        if (getSelectedSubfield() != null)
            sfTableModel.deleteSubfield(getSelectedSubfield());
    }

    /*
     * proverava da li postoji bibliotekar koji
     * sadrzi ovaj tip obrade
     */
    private boolean isProcessTypeFree(ProcessType pt) {
        List<LibrarianDB> librarians = LibEnvProxy.getAllLibrarians();
        if (librarians != null) {
            for (LibrarianDB lib : librarians) {
                if (lib.getContext().getProcessTypes() != null && lib.getContext().getProcessTypes().size() > 0) {
                    for (ProcessTypeDB ptLib : lib.getContext().getProcessTypes())
                        if (ptLib != null && pt.getName() != null && ptLib.getName().equals(pt.getName()))
                            return false;
                }
            }
            return true;
        } else {
            JOptionPane.showMessageDialog(BisisApp.getMainFrame(), Messages.getString("LIBENV_DB_ERROR"), Messages.getString("LIBENV_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
            return true;
        }
    }


    private USubfield getSelectedSubfield() {
        if (subfieldsTable.getSelectedRow() > -1 &&
                subfieldsTable.getSelectedColumn() < sfTableModel.getRowCount())
            return sfTableModel.getRow(subfieldsTable.convertRowIndexToModel
                    (subfieldsTable.getSelectedRow()));
        return null;
    }


    private String validatePubTypeChange() {
        String messagePref = Messages.getString("SFS_DONT_BELONG_TO_PUB_TYPE"); //$NON-NLS-1$
        StringBuffer buff = new StringBuffer();
        for (USubfield usf : sfTableModel.getInitialSubfields())
            if (FormatUtils.pubTypeContainsSubfield
                    (((UFormat) pubTypeCmbBox.getSelectedItem()).getPubType(), usf))
                buff.append(FormatUtils.returnSubfieldSpec(usf) + "\n"); //$NON-NLS-1$
        if (buff.toString().equals("")) //$NON-NLS-1$
            return ""; //$NON-NLS-1$
        else {
            return messagePref + buff.toString();
        }
    }

    private void layoutPanels() {
        buttonsPanel.setLayout(new MigLayout("", "[right]5[right]", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        sacuvajButton.setIcon(new ImageIcon(getClass().getResource(
                "/icons/ok.gif"))); //$NON-NLS-1$
        ponistiButton.setIcon(new ImageIcon(getClass().getResource(
                "/icons/remove.gif")));         //$NON-NLS-1$
        buttonsPanel.add(sacuvajButton, "gapleft 500"); //$NON-NLS-1$
        buttonsPanel.add(ponistiButton, "grow"); //$NON-NLS-1$
        processTypePanel.setLayout(new GridBagLayout());
        strelicaButton.setIcon(new ImageIcon(getClass().getResource(
                "/icons/4arrow4.gif"))); //$NON-NLS-1$
        JPanel pubTypePanel = new JPanel();

        pubTypePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        //c.weighty = 0.05;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.LINE_START;
        pubTypePanel.add(new JLabel(Messages.getString("ProcessType.PUBLICATION_TYPE")), c); //$NON-NLS-1$
        c.gridy = 1;
        c.insets = new Insets(0, 5, 5, 5);
        pubTypePanel.add(pubTypeCmbBox, c);
        c.gridy = 2;
        c.weighty = 0.9;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        pubTypePanel.add(treeScrollPane, c);


        JPanel ptPanel = new JPanel();
        ptPanel.setLayout(new MigLayout("", "[]", "[]5[]10[]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        ptPanel.add(new JLabel(Messages.getString("ProcessType.NAME_PROCESS_TYPE")), "wrap"); //$NON-NLS-1$ //$NON-NLS-2$
        ptPanel.add(processTypeNameTxtFld, "wrap, grow"); //$NON-NLS-1$
        ptPanel.add(sfTableScrollPane, "grow"); //$NON-NLS-1$
        tabbedPane.addTab(Messages.getString("ProcessType.SETTINGS"), ptPanel); //$NON-NLS-1$

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.4;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        processTypePanel.add(pubTypePanel, c);
        c.gridx = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0;
        c.weighty = 0;
        processTypePanel.add(strelicaButton, c);
        c.gridx = 2;
        c.weightx = 0.5;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        processTypePanel.add(tabbedPane, c);

        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.3;
        c.fill = GridBagConstraints.BOTH;
        add(tableScrollPane, c);
        c.gridy = 1;
        c.weighty = 0.7;
        add(processTypePanel, c);
        c.gridy = 2;
        c.weighty = 0.05;
        add(buttonsPanel, c);
    }

    private ProcessType selectedProcessType = null;

    private JTable processTypeTable;
    private ProcessTypeTableModel processTypeTableModel;
    private JScrollPane tableScrollPane;
    private ListSelectionModel listSelModel;

    private JTree pubTypeTree;
    private PubTypeTreeModel treeModel;
    private JScrollPane treeScrollPane;
    private PubTypeTreeCellRenderer treeCellRenderer;

    private JTable subfieldsTable;
    private ProcessTypeSubfieldsTableModel sfTableModel;
    private JScrollPane sfTableScrollPane;

    private JPanel processTypePanel = new JPanel();
    private JComboBox pubTypeCmbBox = new JComboBox();
    private JTextField processTypeNameTxtFld = new JTextField(20);
    private JButton strelicaButton = new JButton();
    private JTabbedPane tabbedPane = new JTabbedPane();

    private JPanel buttonsPanel = new JPanel();
    private JButton sacuvajButton = new JButton(Messages.getString("ProcessType.SAVE")); //$NON-NLS-1$
    private JButton ponistiButton = new JButton(Messages.getString("ProcessType.NEW")); //$NON-NLS-1$


    public class PubTypeCmbBoxCellRenderer extends JLabel
            implements ListCellRenderer {

        public PubTypeCmbBoxCellRenderer() {
            super();
            setOpaque(true);
        }

        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof UFormat) {
                UFormat uf = (UFormat) value;
                setText(uf.getPubType() + "-" + uf.getName());                     //$NON-NLS-1$
            }
            if (isSelected) {
                setForeground(list.getSelectionForeground());
                setBackground(list.getSelectionBackground());
            } else {
                setForeground(list.getForeground());
                setBackground(list.getBackground());
            }
            return this;
        }

    }


}
