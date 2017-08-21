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
    private JTabbedPane tabbedPane1;
    private JList list1;
    private JButton button1;

    public ChoseTheme(){
        initialize();
    }

    public void initialize(){
        this.setSize(300, 300);

        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        Dimension screen = getToolkit().getScreenSize();
        this.setLocation((screen.width - getPreferredSize().width) / 2,
                (screen.height - getPreferredSize().height) / 2);
        this.pack();



    }
}
