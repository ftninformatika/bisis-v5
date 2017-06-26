package com.ftninformatika.bisis.search;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableRowSorter;

import com.ftninformatika.bisis.format.UItem;
import com.ftninformatika.utils.CenteredDialog;
import com.ftninformatika.utils.FilteredJList;


public class CodeChoiceDialog extends CenteredDialog {

  private JPanel jContentPane = null;
  private JLabel fieldLabel = null;
  private JLabel indicatorNameLabel = null;
  //	private CodesList codesList = null;
  private JButton okButton = null;
  private JButton cancelButton = null;
  private JPanel buttonsPanel = null;
  private JScrollPane jScrollPane = null;
  private FilteredJList filteredJList = null;

  //private JTextField filterTextField = null;

  private String title = null;
  private List<UItem> cList = null;
  private String ownerString = null;
  private String nameString = null;

  private String selectedCode = null;

  public CodeChoiceDialog() {
    super();
    initialize();
  }

  public CodeChoiceDialog(Frame owner, String title, List<UItem> cList, String ownerString, String nameString) {
    super(owner, true);
    this.title = title;
    this.cList = cList;
    this.ownerString = ownerString;
    this.nameString = nameString;
    initialize();
  }

  public String getSelectedCode() {
    return selectedCode;
  }

  private void initialize() {
    this.setSize(373, 400);
    this.setTitle(title);
    filteredJList = new FilteredJList();

    filteredJList.getJList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    for (UItem item : cList) {
      filteredJList.addItem(item.toString());
    }

    okButton = new JButton();
    okButton.setSize(new java.awt.Dimension(88, 26));
    okButton.setText("Izaberi");
    okButton.setIcon(new ImageIcon(getClass().getResource(
        "/icons/ok.gif")));
    cancelButton = new JButton("Odustani");
    cancelButton.setIcon(new ImageIcon(getClass().getResource(
        "/icons/remove.gif")));

    getRootPane().setDefaultButton(okButton);
    this.setContentPane(getJContentPane());

    filteredJList.getJList().addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          handleOk();
        }
      }
    });
    filteredJList.getJList().addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        handleKeys(e);
      }
    });
    cancelButton.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        handleKeys(e);
      }
    });
    cancelButton.addActionListener(e -> handleCancel());
    okButton.addActionListener(e -> handleOk());
    okButton.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        handleKeys(e);
      }
    });
  }

  private void handleKeys(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
      handleCancel();

  }

  private JPanel getJContentPane() {
    if (jContentPane == null) {
      fieldLabel = new JLabel(ownerString);
      indicatorNameLabel = new JLabel(nameString);
      jContentPane = new JPanel();
      jContentPane.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.gridx = 0;
      c.gridy = 0;
      c.fill = GridBagConstraints.BOTH;
      c.anchor = GridBagConstraints.LINE_START;
      c.insets = new Insets(10, 10, 5, 10);
      jContentPane.add(fieldLabel, c);
      c.gridy = 1;
      jContentPane.add(indicatorNameLabel, c);
      c.gridy = 2;
      c.fill = GridBagConstraints.BOTH;
      c.anchor = GridBagConstraints.CENTER;
      jContentPane.add(filteredJList, c);
      c = new GridBagConstraints();
      c.gridy = 3;
      c.anchor = GridBagConstraints.CENTER;
      c.insets = new Insets(5, 10, 10, 10);
      c.fill = GridBagConstraints.BOTH;
      buttonsPanel = new JPanel();
      buttonsPanel.add(okButton);
      buttonsPanel.add(cancelButton);
      jContentPane.add(buttonsPanel, c);

    }
    return jContentPane;
  }

  private void handleOk() {
    setSelectedCode();
    setVisible(false);
  }

  private void handleCancel() {
    dispose();
  }

  private void setSelectedCode() {
    try {
      String selectedString = filteredJList.getJList().getSelectedValue().toString();
      selectedCode = selectedString.substring(0, selectedString.indexOf("-"));
    } catch (NullPointerException e) {
      selectedCode = null;
    }
  }
}  
