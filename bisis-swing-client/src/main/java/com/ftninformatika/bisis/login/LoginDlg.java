package com.ftninformatika.bisis.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.ftninformatika.utils.WindowUtils;
import net.miginfocom.swing.MigLayout;

public class LoginDlg extends JDialog {

  public LoginDlg(LoginFrame parent) {
    super(parent, "BISIS prijavljivanje", true);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    btnCancel.setFocusable(false);
    btnOK.setFocusable(false);
    getRootPane().setDefaultButton(btnOK);
    btnCancel.setIcon(new ImageIcon(getClass().getResource(
        "/icons/remove.gif")));
    btnOK.setIcon(new ImageIcon(getClass().getResource(
        "/icons/ok.gif")));
    btnCancel.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        handleCancel();
      }
    });
    btnOK.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        handleOK();
      }
    });
    MigLayout layout = new MigLayout(
        "insets dialog, wrap",
        "[right]rel[150lp]",
        "[]rel[]para[]");
    setLayout(layout);
    
    add(new JLabel("Bibliotekar"), "");
    add(tfUsername, "grow, wrap");
    add(new JLabel("Lozinka"), "");
    add(pfPassword, "grow, wrap");
    add(btnCancel, "span 2, split 2, tag cancel");
    add(btnOK, "wrap, tag ok");
    pack();
    WindowUtils.centerOnScreen(this);
  }
  
  public boolean isConfirmed() {
    return confirmed;
  }
  
  public String getUsername() {
    return tfUsername.getText().trim();
  }
  
  public String getPassword() {
    return new String(pfPassword.getPassword()).trim();
  }
  
  private void handleOK() {
    confirmed = true;
    setVisible(false);
  }
  
  private void handleCancel() {
    confirmed = false;
    setVisible(false);
  }
  
  JTextField tfUsername = new JTextField();
  JPasswordField pfPassword = new JPasswordField();
  JButton btnCancel = new JButton("Odustani");
  JButton btnOK = new JButton("Prijavi se");
  
  boolean confirmed = false;
}
