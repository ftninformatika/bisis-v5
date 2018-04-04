package com.ftninformatika.bisis.editor.groupinv;

import com.ftninformatika.utils.Messages;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import com.ftninformatika.bisis.BisisApp;
import net.miginfocom.swing.MigLayout;

public class GroupInvFrame extends JInternalFrame {

    public GroupInvFrame() {
        super(Messages.getString("GROUP_INV"), true, true, true, true);
//        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
//        setResizable(true);
        setPreferredSize(new Dimension(700, 310));
        pack();
        createTable();

        saveChangesButton.setIcon(new ImageIcon(getClass().getResource(
                "/icons/ok.gif")));
        resetTableButton.setIcon(new ImageIcon(getClass().getResource(
                "/icons/remove.gif")));
        //layout
        GridBagConstraints c = new GridBagConstraints();
        loadFilePanel.setLayout(new MigLayout("insets 20 10 10 10", "[]10[]", "[]20[]"));
        loadFilePanel.add(fileNameTxtFld, "grow");
        loadFilePanel.add(browseButton, "grow, wrap");
        loadFilePanel.add(loadFileButton, "");

        tabbedPane.addTab(Messages.getString("MANUAL_INPUT"), manualInputPanel);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_R);
        tabbedPane.addTab(Messages.getString("LOAD_FILE"), loadFilePanel);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_U);

        manualInputPanel.setLayout(new MigLayout("insets 10", "[]10[]", "[]10[]"));
        manualInputPanel.add(new JLabel(Messages.getString("INV_NUM")), "grow, wrap");
        manualInputPanel.add(invBrojTextField, "grow");
        manualInputPanel.add(dodajButton, "grow");

        scrollPane = new JScrollPane(inventarTable);
        buttonsPanel.setLayout(new MigLayout("", "5[]300[right]5[right]", ""));


        buttonsPanel.add(changeValueButton);
        buttonsPanel.add(saveChangesButton);
        buttonsPanel.add(resetTableButton);

        setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0.1;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 10, 10, 10);
        add(tabbedPane, c);
        c.weightx = 1;
        c.gridy = 1;
        c.weighty = 0.9;
        add(scrollPane, c);
        c.gridy = 2;
        c.weighty = 0.1;
        add(buttonsPanel, c);

        //action
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                handleOpenFile();
            }
        });

        dodajButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                handleAddItem();
            }
        });
        invBrojTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
                    dodajButton.doClick();
            }
        });

        loadFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                handleLoadFile();
            }
        });

        changeValueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                handleChangeValue();
            }
        });

        saveChangesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                handleSaveChanges();
            }
        });

        resetTableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                handleClearList();
            }
        });

        inventarTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                handleTableSelectionChanged();
            }
        });
    }



    private void createTable() {
        inventarTable.setModel(tableModel);
        ListSelectionModel selModel = inventarTable.getSelectionModel();
        selModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventarTable.setAutoCreateRowSorter(true);
        inventarTable.setDefaultRenderer(inventarTable.getColumnClass(0), tableRenderer);
        inventarTable.putClientProperty("Quaqua.Table.style", "striped");
        changeValueButton.setEnabled(false);
		
	/*	selModel.addListSelectionListener(new ListSelectionListener(){
  		public void valueChanged(ListSelectionEvent e) {
        handleTableSelectionChanged();				
  		}			
  	});*/
    }

    private void handleTableSelectionChanged() {
        inventarTable.repaint();
        if (tableModel.columnCanBeSelected(inventarTable.getSelectedColumn()))
            changeValueButton.setEnabled(true);
        else
            changeValueButton.setEnabled(false);
    }

    private void handleOpenFile() {
        int returnVal = fileChooser.showOpenDialog(BisisApp.getMainFrame());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileNameTxtFld.setText(fileChooser.getSelectedFile().getName());
            //fileChooser.getSelectedFile()
        }
    }

    private void handleAddItem() {
        tableModel.addItem(invBrojTextField.getText());
        invBrojTextField.setText("");
    }

    private void handleLoadFile() {
        if (fileChooser.getSelectedFile() != null) {
            List<String> invBrojevi = GroupInvFileUtils.readFile(fileChooser.getSelectedFile());
            LoadFileTask task = new LoadFileTask();
            task.setInvNumbers(invBrojevi);
            task.setTableModel(tableModel);
            try {
                String str = task.doInBackground();
            } catch (Exception e) {

                e.printStackTrace();
            }
            for (String invBroj : tableModel.getNeispravni()) {
                GroupInvFileUtils.errwriter.append(invBroj + "\n");
            }
            GroupInvFileUtils.errwriter.close();
        }
    }

    private void handleChangeValue() {
        int selectedColumn = inventarTable.getSelectedColumn();
        if (tableModel.isCodedColimn(selectedColumn)) {
            GroupInvCodeDialog codeDialog = new GroupInvCodeDialog
                    (tableModel.getColumnName(selectedColumn), tableModel.getCodes(selectedColumn));
            codeDialog.setVisible(true);
            if (codeDialog.getSelectedCode() != null) {
                tableModel.changeCode(codeDialog.getSelectedCode(), selectedColumn, codeDialog.getDatumStatusa());
            }
            codeDialog.setVisible(false);
        } else {
            GroupInvTextDialog textDialog = new GroupInvTextDialog(tableModel.getColumnName(selectedColumn));
            textDialog.setVisible(true);
            if (textDialog.getNewText() != null) {
                tableModel.changeText(textDialog.getNewText(), selectedColumn);
            }
            textDialog.setVisible(false);
        }
    }

    private void handleSaveChanges() {
        boolean ok = tableModel.updateRecords();
        if (ok)
            JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
                    Messages.getString("DATA_SUCESSFULLY_SAVED"), Messages.getString("REPORT"), JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
                    Messages.getString("ERROR_DATA_NOT_SAVED"), Messages.getString("EDITOR_ERROR"), JOptionPane.ERROR_MESSAGE);
    }

    private void handleClearList() {
        tableModel.clearList();
        inventarTable.repaint();
        changeValueButton.setEnabled(false);
    }

    private JPanel manualInputPanel = new JPanel();
    private JPanel loadFilePanel = new JPanel();

    private JPanel buttonsPanel = new JPanel();
    private JTable inventarTable = new JTable();
    private GroupInvTableCellRenderer tableRenderer = new GroupInvTableCellRenderer();
    private JScrollPane scrollPane;
    private GroupInvTableModel tableModel = new GroupInvTableModel();
    private JTabbedPane tabbedPane = new JTabbedPane();

    private JTextField invBrojTextField = new JTextField(10);
    private JButton dodajButton = new JButton(Messages.getString("EDITOR_ADD_BUTTON"));
    private JTextField fileNameTxtFld = new JTextField(20);
    private JButton browseButton = new JButton(Messages.getString("EDITOR_FIND_FILE_BUTTON"));
    private JButton loadFileButton = new JButton(Messages.getString("EDITOR_LOAD_NUMS_BUTTON"));
    private JButton changeValueButton = new JButton(Messages.getString("EDITOR_CHANGE_VALUE_BUTTON"));
    private JButton saveChangesButton = new JButton(Messages.getString("EDITOR_SAVE_BUTTON"));
    private JButton resetTableButton = new JButton(Messages.getString("EDITOR_CANCEL_BUTTON"));
    private JFileChooser fileChooser = new JFileChooser();


}
