package com.ftninformatika.utils;

import java.awt.*;

/** Contains miscellaneous methods for GUI window manipulation.
 *
 *  @author Branko Milosavljevic, mbranko@uns.ns.ac.yu
 *  @version 1.0
 */
public class WindowUtils {

  /** Centers a window on the screen.
   *
   *  @param window The window to be centered
   */
  public static void centerOnScreen(Window window) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = window.getSize();
    window.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
  }
  
  /** Retrieves screen width in pixels. */
  public static int getScreenWidth() {
    return Toolkit.getDefaultToolkit().getScreenSize().width;
  }

  /** Retrieves screen height in pixels. */
  public static int getScreenHeight() {
    return Toolkit.getDefaultToolkit().getScreenSize().height;
  }
  
  /** Retrieves screen dimensions in pixels. */
  public static Dimension getScreenSize() {
    return Toolkit.getDefaultToolkit().getScreenSize();
  }
  
  public static void maximizeOrExtend(Frame frame) {
    Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().
        getMaximumWindowBounds();
    frame.setLocation(0, 0);
    frame.setSize(rec.width, rec.height);
    if (Toolkit.getDefaultToolkit().isFrameStateSupported(Frame.MAXIMIZED_BOTH))
      frame.setExtendedState(Frame.MAXIMIZED_BOTH);
  }
}