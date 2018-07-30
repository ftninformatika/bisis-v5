package com.ftninformatika.utils.fx.JFXInternalFrame;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.coders.CodersHelper;
import com.ftninformatika.bisis.coders.JfxCoderFrameController;
import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

@Getter
@Setter
public class JFXCoderFrame extends JInternalFrame implements Initializable {

    JFXPanel jfxPanel;
    int code = -1;
    private static final String fxmlPath = "/fx/coder/jfxCoderFrame.fxml";
    private static final String cssPath = null;//"/fx/coder/jfxCoderFrame.fxml";

    public JFXCoderFrame(int code){
        super(CodersHelper.getLocaleCoderName(code), true, true, true, true);
        this.code = code;
        initializeM();
    }

    private void initializeM(){
        jfxPanel = new JFXPanel();
        try {
            createScene();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().add(jfxPanel, BorderLayout.CENTER);
        pack();
    }

    public void createScene() throws InterruptedException {
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
                fxmlLoader.<JfxCoderFrameController>getController().initCoder(code);
            } catch (IOException e) {
                e.printStackTrace();
            }
            done.countDown();
        });
        done.await();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Proba jfxinternalframe");
        JFXCoderFrame jfxInternalFrame = new JFXCoderFrame(1);
        frame.add(jfxInternalFrame);
        jfxInternalFrame.setVisible(true);
        frame.setVisible(true);
    }

}
