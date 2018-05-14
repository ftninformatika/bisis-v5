package com.ftninformatika.bisis.admin.unlock;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class UnlockPanel extends JPanel {

    private JFXPanel jfxPanel;          // The JavaFX component(s)

    public UnlockPanel(){
        initialize();
    }

    private void initialize(){
        // The JavaFX 2.x JFXPanel makes the Swing integration seamless
        jfxPanel = new JFXPanel();

        // Create the JavaFX Scene
        createScene();
        setLayout(new BorderLayout());
        add(jfxPanel, BorderLayout.CENTER);

    }

    private void createScene() {
        // The Scene needs to be created on "FX user thread", NOT on the
        // AWT Event Thread
        FXMLLoader fxmlLoader = new FXMLLoader();
        Platform.setImplicitExit(false);
        PlatformImpl.runLater(() -> {
            try {
                Scene scene = new Scene(fxmlLoader.load(getClass().getResourceAsStream("/fx/unlock/unlockFrame.fxml")));
                scene.getStylesheets().add
                        (getClass().getResource("/fx/unlock/css/unlockFrame.css").toExternalForm());
                jfxPanel.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


//    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setMinimumSize(new Dimension(640, 480));
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // Add the Swing JPanel and make visible
//        frame.getContentPane().add(new UnlockPanel());
//        frame.setVisible(true);
//    }
}
