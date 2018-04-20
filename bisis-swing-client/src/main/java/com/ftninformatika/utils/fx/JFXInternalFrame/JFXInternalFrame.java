package com.ftninformatika.utils.fx.JFXInternalFrame;

import com.ftninformatika.bisis.admin.unlock.UnlockController;
import com.ftninformatika.bisis.admin.unlock.UnlockPanel;
import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
/**
 * @author Petar
 */
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class JFXInternalFrame extends JInternalFrame{
    JPanel panel;
    JFXPanel jfxPanel;

    /**
     *
     * @param title Title of internal JFrame
     * @param fxmlPath Path to .fxml file
     * @param controllerClass Controller for .fxml, if specified in .fxml pass null!
     */
    public JFXInternalFrame(String title, String fxmlPath, Class controllerClass){
        super(title, true, true, true, true);
        initialize(fxmlPath, controllerClass);
    }

    private void initialize(String fxmlPath, Class controllerClass){

        panel = new JPanel();
        jfxPanel = new JFXPanel();

        try {
            createScene(fxmlPath,controllerClass);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        panel.setLayout(new BorderLayout());
        panel.add(jfxPanel, BorderLayout.CENTER);
        setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
    }

    private void createScene(String fxmlPath, Class controllerClass) throws InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(JFXInternalFrame.class.getClass().getResource(fxmlPath));
        final CountDownLatch done = new CountDownLatch(1);
        Platform.setImplicitExit(false);
        PlatformImpl.runLater(() -> {
            try {
                Scene scene = new Scene(fxmlLoader.load());
                jfxPanel.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            done.countDown();
        });
        done.await();
        if (controllerClass != null) {
            Object controller = null;
            try {
                controller = controllerClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            fxmlLoader.setController(controllerClass);
        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Proba jfxinternalframe");
        JFXInternalFrame jfxInternalFrame = new JFXInternalFrame("Proba", "/fx/unlock/UnlockFrame.fxml", null);
        frame.add(jfxInternalFrame);
        frame.setVisible(true);
    }
}
