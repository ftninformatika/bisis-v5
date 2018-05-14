package com.ftninformatika.utils.fx.JFXInternalFrame;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
/**
 * @author Petar
 */
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

public class JFXInternalFrame extends JInternalFrame implements Initializable {
    JFXPanel jfxPanel;

    /**
     *
     * @param title Title of internal JFrame
     * @param fxmlPath Path to .fxml file
     * @param cssPath Path to .css file, pass null if no css
     * @param controllerClass Controller for .fxml, if specified in .fxml pass null!
     */
    public JFXInternalFrame(String title, String fxmlPath, String cssPath,  Class controllerClass){
        super(title, true, true, true, true);
        initializeM(fxmlPath, cssPath, controllerClass);
    }

    private void initializeM(String fxmlPath, String cssPath,  Class controllerClass){

        jfxPanel = new JFXPanel();

        try {
            createScene(fxmlPath, cssPath, controllerClass);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().add(jfxPanel, BorderLayout.CENTER);
        pack();
    }

    protected void createScene(String fxmlPath, String cssPath, Class controllerClass) throws InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        final CountDownLatch done = new CountDownLatch(0);
        Platform.setImplicitExit(false);
        PlatformImpl.runAndWait(() -> {
            try {
                Scene scene = new Scene(fxmlLoader.load(getClass().getResourceAsStream(fxmlPath)));
                if (cssPath != null)
                    scene.getStylesheets().add
                            (getClass().getResource(cssPath).toExternalForm());
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
            fxmlLoader.setController(controller);
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    public static void main(String[] args) {
    //    SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setMinimumSize(new Dimension(640, 480));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Proba jfxinternalframe");
            JFXInternalFrame jfxInternalFrame = new JFXInternalFrame("naslcv",
                    "/fx/merge/mergeFrame.fxml",
                    null,null);
            JInternalFrame jInternalFrame = new JInternalFrame("frejm2");
            frame.add(jfxInternalFrame);
            jfxInternalFrame.setVisible(true);
            frame.setVisible(true);
    //    });
    }


}
