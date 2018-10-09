/**
 *
 */
package com.ftninformatika.bisis.libenv;

import com.ftninformatika.bisis.circ.CircLocation;
import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.utils.Messages;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.librarian.LibrarianContext;
import com.ftninformatika.bisis.librarian.ProcessType;
import javafx.scene.control.ComboBox;
import net.miginfocom.swing.MigLayout;

/**
 * @author dimicb
 */
public class LibrarianFrame extends JInternalFrame {

    public LibrarianFrame() {
        super(Messages.getString("LibrarianEnvironment.LIBRARIANS"), false, true, true, true); //$NON-NLS-1$
        this.setSize(900, 700);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        createLibrariansTable();
        createProcTypeLists();
        loadCombos();
        layoutPanels();
        ponistiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initializeForm();
            }
        });
        sacuvajButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSaveLibrarian();
            }
        });
        librariansTable.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                handleKeys(librariansTable, ke);
            }
        });
        strelicaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAddProcessType();
            }
        });
        libProcTypesList.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                handleKeys(libProcTypesList, ke);
            }
        });
        libProcTypesList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    handleShowPopup(e.getX(), e.getY());
                }
            }
        });
    }

    public void initializeForm() {
        Librarian lib = new Librarian();
        lib.setContext(new LibrarianContext());
        loadLibrarian(lib);
        librariansTable.clearSelection();
        allProcTypesListModel.setProcTypeList(LibEnvProxy.getAllProcTypes());
    }

    private void createLibrariansTable() {
        librariansTableModel = new LibrarianTableModel();
        librariansTable = new JTable(librariansTableModel);
        librariansScrollPane = new JScrollPane(librariansTable);
        librariansTable.putClientProperty("Quaqua.Table.style", "striped");
        librariansTable.setRowSorter(new TableRowSorter<LibrarianTableModel>(librariansTableModel));

        listSelModel = librariansTable.getSelectionModel();
        listSelModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                handleListSelectionChanged();
            }
        });
    }

    private void createProcTypeLists() {
	/*	allProcTypesTableModel = new ProcessTypeTableModel();
		allProcTypesTable = new JTable(allProcTypesTableModel);
		allProcTypesScrollPane = new JScrollPane(allProcTypesTable);		
		allProcTypesTable.setRowSorter(new TableRowSorter<ProcessTypeTableModel>(allProcTypesTableModel));
		
		libProcTypesTableModel = new ProcessTypeTableModel(new ArrayList<ProcessType>());
		libProcTypesTable = new JTable(libProcTypesTableModel);
		libProcTypesScrollPane = new JScrollPane(libProcTypesTable);
		libProcTypesTable.setRowSorter(new TableRowSorter<ProcessTypeTableModel>(libProcTypesTableModel));
		*/
        ProcessTypeListCellRenderer renderer = new ProcessTypeListCellRenderer();
        allProcTypesListModel = new ProcessTypeListModel(LibEnvProxy.getAllProcTypes(), null);
        allProcTypesList = new JList(allProcTypesListModel);
        allProcTypesList.setCellRenderer(renderer);
        allProcTypesScrollPane = new JScrollPane(allProcTypesList);

        libProcTypesListModel = new ProcessTypeListModel(null, null);
        libProcTypesList = new JList(libProcTypesListModel);

        libProcTypesList.setCellRenderer(renderer);
        libProcTypesScrollPane = new JScrollPane(libProcTypesList);

    }

    private void loadCombos(){
        defaultDepartmentCombo.setRenderer(new ComboBoxRenderer());
        defaultDepartmentCombo.addItem(" ");
        LibEnvProxy.getLocations().stream().forEach(i -> defaultDepartmentCombo.addItem(i));
        circDepartmentCombo.setRenderer(new ComboBoxRenderer());
        circDepartmentCombo.addItem(" ");
        LibEnvProxy.getCircLocations().stream().forEach(i -> circDepartmentCombo.addItem(i));
    }

    private void loadLibrarian(Librarian lib) {
        if (lib.getUsername() != null) usernameTxtFld.setText(lib.getUsername());
        else usernameTxtFld.setText(""); //$NON-NLS-1$
        if (lib.getPassword() != null) passwordTxtFld.setText(lib.getPassword());
        else passwordTxtFld.setText(""); //$NON-NLS-1$
        administracijaCheckBox.setSelected(lib.isAdministration());
        obradaCheckBox.setSelected(lib.isCataloguing());
        cirkulacijaCheckBox.setSelected(lib.isCirculation());
        redaktorRadioBtn.setSelected(lib.isRedaktor());
        inventatorRadioBtn.setSelected(lib.isInventator());
        if (lib.getIme() != null) nameTxtFld.setText(lib.getIme());
        else nameTxtFld.setText(""); //$NON-NLS-1$
        if (lib.getPrezime() != null) lastnameTxtFld.setText(lib.getPrezime());
        else lastnameTxtFld.setText(""); //$NON-NLS-1$
        if (lib.getEmail() != null) emailTxtFld.setText(lib.getEmail());
        else emailTxtFld.setText(""); //$NON-NLS-1$
        if (lib.getNapomena() != null) notesTxtArea.setText(lib.getNapomena());
        else notesTxtArea.setText("");             //$NON-NLS-1$
        libProcTypesListModel.setProcTypeList(lib.getContext().getProcessTypes());
        libProcTypesListModel.setDefaultProcessType(lib.getContext().getDefaultProcessType());
        if (lib.getDefaultDepartment() != null && !lib.getDefaultDepartment().isEmpty()) {
            setComboValue(defaultDepartmentCombo, lib.getDefaultDepartment());
        } else {
            defaultDepartmentCombo.setSelectedIndex(0);
        }
        if (lib.getCircDepartment() != null && !lib.getCircDepartment().isEmpty()) {
            setComboValue(circDepartmentCombo, lib.getCircDepartment());
        } else {
            circDepartmentCombo.setSelectedIndex(0);
        }
    }

    private void setComboValue(JComboBox comboBox, String value) {
        comboBox.setSelectedIndex(0);
        for (int i = 1; i < comboBox.getModel().getSize(); i++) {
            Object elem = comboBox.getModel().getElementAt(i);
            if (elem instanceof Location) {
                if (((Location) elem).getCoder_id().equals(value)) {
                    comboBox.setSelectedIndex(i);
                    break;
                }
            } else if (elem instanceof CircLocation) {
                if (((CircLocation) elem).getLocationCode().equals(value)) {
                    comboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private Librarian getLibrarianFromForm() {
        Librarian lib = new Librarian();
        lib.setContext(new LibrarianContext());
        lib.setUsername(usernameTxtFld.getText());
        lib.setPassword(passwordTxtFld.getText());
        lib.setAdministration(administracijaCheckBox.isSelected());
        lib.setCataloguing(obradaCheckBox.isSelected());
        lib.setCirculation(cirkulacijaCheckBox.isSelected());
        lib.setRedaktor(redaktorRadioBtn.isSelected());
        lib.setInventator(inventatorRadioBtn.isSelected());
        lib.setIme(nameTxtFld.getText());
        lib.setPrezime(lastnameTxtFld.getText());
        lib.setEmail(emailTxtFld.getText());
        lib.setNapomena(notesTxtArea.getText());
        lib.setBiblioteka(BisisApp.appConfig.getLibrary());
        lib.getContext().setProcessTypes((ArrayList<ProcessType>) libProcTypesListModel.getProcessTypes());
        lib.getContext().setDefaultProcessType(libProcTypesListModel.getDefaultProcessType());
        if (defaultDepartmentCombo.getSelectedItem() instanceof Location) {
            lib.setDefaultDepartment(((Location) defaultDepartmentCombo.getSelectedItem()).getCoder_id());
        } else {
            lib.setDefaultDepartment(null);
        }
        if (circDepartmentCombo.getSelectedItem() instanceof CircLocation) {
            lib.setCircDepartment(((CircLocation) circDepartmentCombo.getSelectedItem()).getLocationCode());
        } else {
            lib.setCircDepartment(null);
        }
        return lib;
    }

    private void handleListSelectionChanged() {
        if (getSelectedLibrarian() != null)
            loadLibrarian(getSelectedLibrarian());
    }

    private void handleSaveLibrarian() {
        if (validateFormData().equals("")) {
            boolean saved = librariansTableModel.updateLibrarian(getLibrarianFromForm());
            if (!saved)
                JOptionPane.showMessageDialog(BisisApp.getMainFrame(), Messages.getString("LIBENV_DB_ERROR"), Messages.getString("LIBENV_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
        } else
            JOptionPane.showMessageDialog(BisisApp.getMainFrame(), validateFormData(), Messages.getString("LIBENV_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
    }

    private void handleKeys(Component comp, KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_DELETE) {
            if (comp.equals(librariansTable))
                handleDeleteLibrarian();
            if (comp.equals(libProcTypesList))
                handleDeleteProcType();
        }
        if (comp.equals(libProcTypesList)
                && ke.getKeyCode() == KeyEvent.VK_D
                && ke.getModifiers() == InputEvent.SHIFT_MASK)
            handleSetDefault();

    }

    private void handleDeleteLibrarian() {
        if (getSelectedLibrarian() != null) {
            String message = MessageFormat.format(Messages.getString("LIBENV_DELETING_LIBRARIAN_WITH_USERNAME.0"), getSelectedLibrarian().getUsername());
            Object[] options = {Messages.getString("LIBENV_YES"), Messages.getString("LIBENV_CANCEL")}; //$NON-NLS-1$ //$NON-NLS-2$
            int ret = JOptionPane.showOptionDialog(BisisApp.getMainFrame(),
                    message, Messages.getString("LIBENV_DELETING_LIBRARIAN"), JOptionPane.DEFAULT_OPTION,  //$NON-NLS-1$
                    JOptionPane.YES_NO_OPTION, null, options, options[1]);
            if (ret == 0) {
                boolean deleted = librariansTableModel.deleteLibrarian(getSelectedLibrarian());
                if (!deleted)
                    JOptionPane.showMessageDialog(BisisApp.getMainFrame(), Messages.getString("LIBENV_DB_ERROR"), Messages.getString("LIBENV_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
            }

        }
    }

    private void handleDeleteProcType() {
        libProcTypesListModel.deleteProcessType(
                libProcTypesList.getSelectedIndex());
    }

    private void handleAddProcessType() {
        Object[] ptArray = allProcTypesList.getSelectedValues();
        for (Object o : ptArray) {
            if (o instanceof ProcessType) {
                ProcessType pt = (ProcessType) o;
                if (!libProcTypesListModel.containsProcessType(pt))
                    libProcTypesListModel.addProcessType(pt);
            }
        }
    }

    private void handleShowPopup(int x, int y) {
        if (!popupMenuCreated) {
            processTypePopupMenu.add(new SetDefaultAction());
            popupMenuCreated = true;
        }
        processTypePopupMenu.show(libProcTypesList, x, y);
    }

    private void handleSetDefault() {
        libProcTypesListModel.setDefaultProcessType(
                (ProcessType) libProcTypesList.getSelectedValue());
        libProcTypesList.repaint();
    }

    private Librarian getSelectedLibrarian() {
        if (librariansTable.getSelectedRow() > -1 &&
                librariansTable.getSelectedRow() < librariansTableModel.getRowCount())
            return librariansTableModel.getRow(
                    librariansTable.convertRowIndexToModel(librariansTable.getSelectedRow()));
        else return null;
    }

    private boolean validateUsername(String uname) {
        //if (uname == null || "".equals(uname))
        //	return false;
        if (uname.split("@").length == 2 && uname.split("@")[1].equals(BisisApp.appConfig.getLibrarian().getUsername().split("@")[1]))
            return true;

        return false;
    }

    private String validateFormData() {
        StringBuffer message = new StringBuffer();
        if (!validateUsername(usernameTxtFld.getText())) //$NON-NLS-1$
            message.append(MessageFormat.format(Messages.getString("LIBENV_USERNAME_FORMAT_ERROR"), BisisApp.appConfig.getLibrarian().getUsername().split("@")[1])); //$NON-NLS-1$
        if (passwordTxtFld.getText().equals("")) //$NON-NLS-1$
            message.append(Messages.getString("LIBENV_PASSWORD_REQUIRED")); //$NON-NLS-1$
        if (!administracijaCheckBox.isSelected()
                && !obradaCheckBox.isSelected()
                && !cirkulacijaCheckBox.isSelected())
            message.append(Messages.getString("LIBENV_SELECT_ROLE")); //$NON-NLS-1$
        if (!emailTxtFld.getText().equals("") && !emailTxtFld.getText().contains("@")) //$NON-NLS-1$ //$NON-NLS-2$
            message.append(Messages.getString("LIBENV_WRONG_EMAIL")); //$NON-NLS-1$
        if (obradaCheckBox.isSelected()
                && libProcTypesListModel.getDefaultProcessType() == null)
            message.append(Messages.getString("LIBENV_LIBRARIAN_DEFAULT_PROCESS_TYPE")); //$NON-NLS-1$


        return message.toString();
    }

    private void layoutPanels() {
        tabbedPane.addTab(Messages.getString("LibrarianEnvironment.BASIC_DATA"), firstTabpanel); //$NON-NLS-1$
        tabbedPane.addTab(Messages.getString("LibrarianEnvironment.PROCESSING_TYPES"), secondTabpanel); //$NON-NLS-1$
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_O);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_T);
        buttonsPanel.setLayout(new MigLayout("", "[right]5[right]", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        sacuvajButton.setIcon(new ImageIcon(getClass().getResource("/icons/ok.gif"))); //$NON-NLS-1$
        ponistiButton.setIcon(new ImageIcon(getClass().getResource("/icons/remove.gif")));         //$NON-NLS-1$
        buttonsPanel.add(sacuvajButton, "gapleft 500"); //$NON-NLS-1$
        buttonsPanel.add(ponistiButton, "grow"); //$NON-NLS-1$

        // prvi tab (osnovni podaci)
        loginDataPanel.setLayout(new MigLayout("", "", "[]0[]10[]0[]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        loginDataPanel.add(new JLabel(Messages.getString("LibrarianEnvironment.USERNAME")), "wrap"); //$NON-NLS-1$ //$NON-NLS-2$
        loginDataPanel.add(usernameTxtFld, "grow, wrap"); //$NON-NLS-1$
        loginDataPanel.add(new JLabel(Messages.getString("LibrarianEnvironment.PASSWORD")), "wrap"); //$NON-NLS-1$ //$NON-NLS-2$
        loginDataPanel.add(passwordTxtFld, "grow"); //$NON-NLS-1$

        privilegePanel.setLayout(new MigLayout("", "", "[]10[]10[]10[]10[]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        privilegePanel.setBorder(BorderFactory.createTitledBorder(Messages.getString("LibrarianEnvironment.ROLES")));         //$NON-NLS-1$
        privilegePanel.add(administracijaCheckBox, "wrap"); //$NON-NLS-1$
        privilegePanel.add(obradaCheckBox, "wrap"); //$NON-NLS-1$
        invRedGroup.add(inventatorRadioBtn);
        invRedGroup.add(redaktorRadioBtn);
        privilegePanel.add(redaktorRadioBtn, "wrap"); //$NON-NLS-1$
        privilegePanel.add(inventatorRadioBtn, "wrap"); //$NON-NLS-1$
        privilegePanel.add(cirkulacijaCheckBox);
        redaktorRadioBtn.setEnabled(false);
        inventatorRadioBtn.setEnabled(false);
        obradaCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (obradaCheckBox.isSelected()) {
                    redaktorRadioBtn.setEnabled(true);
                    inventatorRadioBtn.setEnabled(true);
                    //redaktorRadioBtn.setSelected(true);
                } else {
                    invRedGroup.clearSelection();
                    redaktorRadioBtn.setEnabled(false);
                    inventatorRadioBtn.setEnabled(false);
                }
            }
        });


        additionalDataPanel.setLayout(new MigLayout("", "[right]5[]", "[]15[]15[]15[]15[]15[]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        additionalDataPanel.setBorder(BorderFactory.createTitledBorder(Messages.getString("LibrarianEnvironment.ADDITIONAL_DATA"))); //$NON-NLS-1$
        additionalDataPanel.add(new JLabel(Messages.getString("LibrarianEnvironment.FIRST_NAME"))); //$NON-NLS-1$
        additionalDataPanel.add(nameTxtFld, "wrap, grow"); //$NON-NLS-1$
        additionalDataPanel.add(new JLabel(Messages.getString("LibrarianEnvironment.LAST_NAME"))); //$NON-NLS-1$
        additionalDataPanel.add(lastnameTxtFld, "wrap, grow"); //$NON-NLS-1$
        additionalDataPanel.add(new JLabel(Messages.getString("LibrarianEnvironment.E-MAIL"))); //$NON-NLS-1$
        additionalDataPanel.add(emailTxtFld, "wrap, grow"); //$NON-NLS-1$
        additionalDataPanel.add(new JLabel(Messages.getString("LibrarianEnvironment.NOTE"))); //$NON-NLS-1$
        notesScrollPane.setPreferredSize(new Dimension(100, 50));
        additionalDataPanel.add(notesScrollPane, "wrap, grow"); //$NON-NLS-1$
        additionalDataPanel.add(new JLabel(Messages.getString("LibrarianEnvironment.DEFAULT_DEPARTMENT"))); //$NON-NLS-1$
        additionalDataPanel.add(defaultDepartmentCombo, "wrap, grow"); //$NON-NLS-1$
        additionalDataPanel.add(new JLabel(Messages.getString("LibrarianEnvironment.CIRC_DEPARTMENT"))); //$NON-NLS-1$
        additionalDataPanel.add(circDepartmentCombo, "wrap, grow"); //$NON-NLS-1$

        firstTabpanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 5, 0, 5);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.3;
        //c.weighty = 0.3;
        c.insets = new Insets(0, 0, 10, 30);
        c.fill = GridBagConstraints.BOTH;
        firstTabpanel.add(loginDataPanel, c);
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 90);
        firstTabpanel.add(privilegePanel, c);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.7;
        c.gridheight = 2;
        firstTabpanel.add(additionalDataPanel, c);

        //drugi tab (tipovi obrade)
        strelicaButton.setIcon(new ImageIcon(getClass().getResource("/icons/4arrow4.gif"))); //$NON-NLS-1$
        secondTabpanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 0, 10, 0);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 1;
        secondTabpanel.add(allProcTypesScrollPane, c);
        c.gridx = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        c.weighty = 0;
        c.insets = new Insets(0, 20, 0, 20);
        secondTabpanel.add(strelicaButton, c);
        c.gridx = 2;
        c.insets = new Insets(10, 0, 10, 0);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 1;
        secondTabpanel.add(libProcTypesScrollPane, c);
        GridBagConstraints cMain = new GridBagConstraints();
        setLayout(new GridBagLayout());
        cMain.gridx = 0;
        cMain.gridy = 0;
        cMain.weightx = 1.0;
        cMain.weighty = 0.6;
        cMain.fill = GridBagConstraints.BOTH;
        add(librariansScrollPane, cMain);
        cMain.gridy = 1;
        cMain.weighty = 0.35;
        add(tabbedPane, cMain);
        cMain.gridy = 2;
        cMain.weighty = 0.05;
        add(buttonsPanel, cMain);
    }

    private static final int txtFldLength = 30;

    private JTable librariansTable;
    private LibrarianTableModel librariansTableModel;
    private JScrollPane librariansScrollPane;
    private ListSelectionModel listSelModel;


    private JTabbedPane tabbedPane = new JTabbedPane();

    private JPanel firstTabpanel = new JPanel();
    private JPanel secondTabpanel = new JPanel();

    private JPanel loginDataPanel = new JPanel();
    private JTextField usernameTxtFld = new JTextField(txtFldLength);
    private JTextField passwordTxtFld = new JTextField(txtFldLength);

    private JPanel privilegePanel = new JPanel();
    private JCheckBox administracijaCheckBox = new JCheckBox(Messages.getString("LibrarianEnvironment.ADMINISTRATION")); //$NON-NLS-1$
    private JCheckBox obradaCheckBox = new JCheckBox(Messages.getString("LibrarianEnvironment.CATALOGUING"));     //$NON-NLS-1$
    private JRadioButton redaktorRadioBtn = new JRadioButton(Messages.getString("LibrarianEnvironment.REDACTOR"));     //$NON-NLS-1$
    private JRadioButton inventatorRadioBtn = new JRadioButton(Messages.getString("LibrarianEnvironment.INVENTATOR"));     //$NON-NLS-1$
    private JCheckBox cirkulacijaCheckBox = new JCheckBox(Messages.getString("LibrarianEnvironment.CIRCULATION")); //$NON-NLS-1$
    private ButtonGroup invRedGroup = new ButtonGroup();

    private JPanel additionalDataPanel = new JPanel();
    private JTextField nameTxtFld = new JTextField(txtFldLength);
    private JTextField lastnameTxtFld = new JTextField(txtFldLength);
    private JTextField emailTxtFld = new JTextField(txtFldLength);
    private JTextArea notesTxtArea = new JTextArea();
    private JScrollPane notesScrollPane = new JScrollPane(notesTxtArea);
    private JComboBox defaultDepartmentCombo = new JComboBox<>();
    private JComboBox circDepartmentCombo = new JComboBox<>();
	
	/*
	 * table
	private ProcessTypeTableModel allProcTypesTableModel;
	private JTable allProcTypesTable;
	private JScrollPane allProcTypesScrollPane;
	private ProcessTypeTableModel libProcTypesTableModel;
	private JTable libProcTypesTable;
	private JScrollPane libProcTypesScrollPane;
	*/

    /*list */
    private ProcessTypeListModel allProcTypesListModel;
    private JList allProcTypesList;
    private JScrollPane allProcTypesScrollPane;
    private ProcessTypeListModel libProcTypesListModel;
    private JList libProcTypesList;
    private JScrollPane libProcTypesScrollPane;


    private JButton strelicaButton = new JButton();

    private JPanel buttonsPanel = new JPanel();
    private JButton sacuvajButton = new JButton(Messages.getString("LibrarianEnvironment.SAVE")); //$NON-NLS-1$
    private JButton ponistiButton = new JButton(Messages.getString("LibrarianEnvironment.NEW_LIBRARIAN")); //$NON-NLS-1$

    private JPopupMenu processTypePopupMenu = new JPopupMenu();
    private boolean popupMenuCreated = false;


    public class SetDefaultAction extends AbstractAction {

        public SetDefaultAction() {
            putValue(SHORT_DESCRIPTION, Messages.getString("LIBENV_SETTING_DEFAULT_PROCESS_TYPE")); //$NON-NLS-1$
            putValue(NAME, Messages.getString("LibrarianEnvironment.SET_DEFAULT"));      //$NON-NLS-1$
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.SHIFT_MASK));
        }

        public void actionPerformed(ActionEvent ev) {
            handleSetDefault();
        }
    }


}
