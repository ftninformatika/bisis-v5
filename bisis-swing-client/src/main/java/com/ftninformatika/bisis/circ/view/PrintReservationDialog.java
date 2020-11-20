package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.utils.WindowUtils;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.awt.*;

/**
 * @author marijakovacevic
 */
public class PrintReservationDialog extends JDialog {

    private JRViewer jr;

    public PrintReservationDialog() {
        super();
        initialize();
    }

    private void initialize() {
        this.setModal(true);
        this.setLayout(new BorderLayout());
        this.setSize(800, 450);
        this.add(getPanel(), java.awt.BorderLayout.CENTER);

        WindowUtils.centerOnScreen(this);
    }

    private JRViewer getPanel() {
        if (jr == null) {
            try {
                jr = new JRViewer(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jr;
    }

    public void setJasper(JasperPrint jp) {
        try {
            this.remove(jr);
            jr = new JRViewer(jp);
            this.add(jr, java.awt.BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
