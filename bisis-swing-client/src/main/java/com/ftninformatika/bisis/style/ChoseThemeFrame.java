package com.ftninformatika.bisis.style;

import com.ftninformatika.bisis.BisisApp;
import com.seaglasslookandfeel.SeaGlassLookAndFeel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Petar on 8/21/2017.
 */
public class ChoseThemeFrame extends JInternalFrame {

    private JComboBox choseTheme;
    private JButton save;
    private JButton cancel;


    public ChoseThemeFrame(){
        super("Odaberite temu");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        choseTheme = new JComboBox();
        choseTheme.addItem("SeaGlass");
        choseTheme.addItem("Legacy");
        save = new JButton("Sa\u010duvaj");
        cancel = new JButton("Ponisti");

        FlowLayout layout = new FlowLayout();

        setLayout(layout);
        add(choseTheme);
        add(save);
        add(cancel);
        pack();

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(choseTheme.getSelectedItem().equals("SeaGlass")) {
                    try {
                        UIManager.installLookAndFeel("seaglass", "com.seaglasslookandfeel.SeaGlassLookAndFeel");
                        UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
                        SwingUtilities.updateComponentTreeUI(BisisApp.getMainFrame());
                        BisisApp.getMainFrame().pack();
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                else if (choseTheme.getSelectedItem().equals("Legacy")){
                    try {
                        UIManager.setLookAndFeel(
                                UIManager.getSystemLookAndFeelClassName());
                        SwingUtilities.updateComponentTreeUI(BisisApp.getMainFrame());
                        BisisApp.getMainFrame().pack();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (InstantiationException e1) {
                        e1.printStackTrace();
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (UnsupportedLookAndFeelException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

    }
}
