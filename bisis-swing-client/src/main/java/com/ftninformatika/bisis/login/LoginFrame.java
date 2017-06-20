package com.ftninformatika.bisis.login;

import javax.swing.JFrame;

public class LoginFrame extends JFrame {

  public LoginFrame() {
    setTitle("BISIS prijavljivanje");
    setBounds(-1000, -1000, 0, 0);
    String osname = System.getProperty("os.name");
    if (!osname.equals("Linux"))
      setVisible(true);
    dlg = new LoginDlg(this);
    dlg.setVisible(true);
//    dlg.dispose();
//    if (!osname.equals("Linux"))
//      setVisible(false);
  }
  
  public boolean isConfirmed() {
    return dlg.isConfirmed();
  }
  
  public String getUsername() {
    return dlg.getUsername();
  }
  
  public String getPassword() {
    return dlg.getPassword();
  }
  
  public void setVis(boolean vis){
  	dlg.setVisible(vis);
  	//setVisible(vis);
  }
  
  public void disp(){
  	dlg.dispose();
  	dispose();
//  	if (!osname.equals("Linux"))
//  		setVisible(false);
  }
  
  LoginDlg dlg;
  String osname;
}
