package com.ftninformatika.bisis.editor.registries;

import com.ftninformatika.utils.WindowUtils;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class EditItemDlg extends JDialog {

  public EditItemDlg(Frame parent) {
    super(parent, "Stavka", true);
    
    Box center = Box.createHorizontalBox();
    center.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    Box box1 = Box.createVerticalBox();
    box1.add(lValue1);
    box1.add(lValue2);
    Box box2 = Box.createVerticalBox();
    box2.add(tfValue1);
    box2.add(tfValue2);
    Box box3 = Box.createVerticalBox();
    box3.add(Box.createHorizontalStrut(5));
    box3.add(Box.createHorizontalStrut(5));
    center.add(box1);
    center.add(box3);
    center.add(box2);
    
    Box south = Box.createHorizontalBox();
    south.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    south.add(Box.createHorizontalGlue());
    south.add(btnCancel);
    south.add(btnOK);
    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handleOK();
      }
    });
    btnOK.setFocusable(false);
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handleCancel();
      }
    });
    btnCancel.setFocusable(false);
    
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(center, BorderLayout.CENTER);
    getContentPane().add(south, BorderLayout.SOUTH);
    getRootPane().setDefaultButton(btnOK);
    
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    pack();
    WindowUtils.centerOnScreen(this);
  }
  
  public void setVisible(boolean visible) {
    if (visible)
      tfValue1.requestFocus();
    super.setVisible(visible);
  }
  
  public void makeNew(int registryType) {
    setTitle("Nova stavka za registar: " + Registries.getLongName(registryType));
    tfValue1.setText("");
    tfValue2.setText("");
    setVisibility(registryType);
    setVisible(true);
  }
  
  public void edit(int registryType, String value1, String value2) {
    setTitle("Promena stavke za registar: " + Registries.getLongName(registryType));
    tfValue1.setText(value1);
    tfValue2.setText(value2);
    setVisibility(registryType);
    setVisible(true);
  }
  
  public void search(int registryType) {
    setTitle("Pretraga registra: " + Registries.getLongName(registryType));
    tfValue1.setText("");
    tfValue2.setText("");
    setVisibility(registryType);
    setVisible(true);
  }
  
  public void handleOK() {
    confirmed = true;
    value1 = tfValue1.getText().trim();
    value2 = tfValue2.getText().trim();
    if ("".equals(value1))
      value1 = null;
    if ("".equals(value2))
      value2 = null;
    setVisible(false);
  }
  
  public void handleCancel() {
    confirmed = false;
    value1 = null;
    value2 = null;
    setVisible(false);
  }
  
  public String getValue1() {
    return value1;
  }
  
  public String getValue2() {
    return value2;
  }
  
  private void setVisibility(int registryType) {
    lValue1.setText(Registries.getLabel1(registryType));
    lValue2.setText(Registries.getLabel2(registryType));
    if (registryType == Registries.AUTORI || registryType == Registries.UDK) {
      lValue2.setVisible(true);
      tfValue2.setVisible(true);
    } else {
      lValue2.setVisible(false);
      tfValue2.setVisible(false);
    }
    pack();
    WindowUtils.centerOnScreen(this);
  }

  private String value1 = null;
  private String value2 = null;
  private boolean confirmed = false;
  
  private JLabel lValue1 = new JLabel();
  private JLabel lValue2 = new JLabel();
  private JTextField tfValue1 = new JTextField(40);
  private JTextField tfValue2 = new JTextField(40);
  private JButton btnCancel = new JButton("Odustani", 
      new ImageIcon(RegistryDlg.class.getResource(
        "/icons/delete.png")));
  private JButton btnOK = new JButton("Prihvati", 
      new ImageIcon(RegistryDlg.class.getResource(
        "/icons/ok.png")));
}
