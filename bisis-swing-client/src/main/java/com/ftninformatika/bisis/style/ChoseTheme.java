package com.ftninformatika.bisis.style;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Petar on 8/17/2017.
 */
public class ChoseTheme extends JInternalFrame {
    private JPanel panel1;
    private JButton poništiButton;
    private JButton sačuvajButton;
    private JComboBox comboBox1;

    public ChoseTheme(){
        super("Odaberite temu");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        pack();

    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("ChoseTheme");
        frame.setContentPane(new ChoseTheme().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
