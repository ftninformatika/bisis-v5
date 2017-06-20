package com.ftninformatika.bisis.login;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class SplashScreen extends JWindow {

  public SplashScreen() {
    p = new JPanel();
    //p.setPreferredSize(new Dimension(400,300));
    p.setLayout(new BorderLayout());
    p.add(image, BorderLayout.CENTER);
    p.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    
    message.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    message.setHorizontalAlignment(SwingConstants.LEFT);
    progressBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    progressBar.setIndeterminate(true);
    
    Box box = Box.createVerticalBox();
    box.add(message);
    box.add(progressBar);
    p.add(box, BorderLayout.SOUTH);
    setContentPane(p);
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    pack();
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
  }
  
  public JLabel getMessage() {
    return message;
  }
  public JLabel getImage() {
    return image;
  }
  public JProgressBar getProgressBar() {
    return progressBar;
  }
  public void setMessage(JLabel label) {
    message = label;
  }
  public void setImage(String imageURL) {
    URL url = getClass().getResource(imageURL);
    if (url != null) {
      image.setIcon(new ImageIcon(url));
      this.pack();
      this.validate();
      Dimension screen = getToolkit().getScreenSize();
      this.setLocation((screen.width - getSize().width) / 2,
          (screen.height - getSize().height) / 2);
    }
  }
  public void setProgressBar(JProgressBar bar) {
    progressBar = bar;
  }
  private JProgressBar progressBar = new JProgressBar();
  private JLabel message = new JLabel();
  private JLabel image = new JLabel();
  private JPanel p = null;

}
