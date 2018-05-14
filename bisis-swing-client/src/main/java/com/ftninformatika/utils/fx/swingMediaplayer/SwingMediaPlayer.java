package com.ftninformatika.utils.fx.swingMediaplayer;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.*;

import javax.swing.*;

public class SwingMediaPlayer extends JDialog {
    private static final String VIDEO =
            "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";

    public SwingMediaPlayer(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Proba");
            frame.setSize(860, 640);
            frame.setVisible(true);
            JInternalFrame jInternalFrame = new JInternalFrame("internalFrame");
            jInternalFrame.setSize(300,300);
            frame.add(jInternalFrame);
            jInternalFrame.setVisible(true);
            SwingMediaPlayer dialog = new SwingMediaPlayer(frame, true);
            dialog.add(new JavaFXVideoPanel(VIDEO));
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setSize(400, 300);
            dialog.setVisible(true);
        });
    }

    static class JavaFXVideoPanel extends JFXPanel {
        public JavaFXVideoPanel(String url) {
            super();
            Platform.runLater(() -> createScene(url));
        }

        private void createScene(String url) {
            Media media = new Media(url);
            MediaView tv = new MediaView(new MediaPlayer(media));
            setScene(new Scene(new StackPane(tv)));

            tv.getMediaPlayer().play();
        }
    }
}