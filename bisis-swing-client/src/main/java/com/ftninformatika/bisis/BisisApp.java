package com.ftninformatika.bisis;

import com.ftninformatika.bisis.login.LoginFrame;

import javax.swing.*;

/**
 * Created by Petar on 6/20/2017.
 */
public class BisisApp {

    public static void main(String[] args) {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        JDialog.setDefaultLookAndFeelDecorated(true);
        LoginFrame login = new LoginFrame();
        boolean correct = false;
        while (!correct){
            if (login.isConfirmed()) {

                //librarian = new Librarian(login.getUsername(), login.getPassword());
                //if (librarian.login()) {
//                    correct = true;
//                    login.disp();
//                    mf.setVisible(true);
//                } else {
//                    JOptionPane.showMessageDialog(null, "Pogre\u0161no ime/lozinka",
//                            "Greska", JOptionPane.ERROR_MESSAGE);
//                    login.setVis(true);
//                }
            } else {
                System.exit(0);
            }

        }

    }
}
