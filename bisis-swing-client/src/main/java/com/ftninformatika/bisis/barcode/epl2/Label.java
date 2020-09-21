package com.ftninformatika.bisis.barcode.epl2;

import static com.ftninformatika.bisis.barcode.epl2.Rotation.R0;
import static com.ftninformatika.bisis.barcode.epl2.Rotation.R90;

import java.util.ArrayList;
import java.util.List;

public class Label {
  
  private int width;
  private int height;
  private int resolution;
  private int widebar;
  private int narrowbar;
  private int barwidth;
  private String pageCode;
  private int currentY = 20;
  private List<Component> components = new ArrayList<Component>();

  public Label(int width, int height, int resolution,int widebar, int narrowbar, 
      int barwidth, String pageCode) {
    this.width = width;
    this.height = height;
    this.resolution = resolution;
    this.barwidth = barwidth;
    this.widebar = widebar;
    this.narrowbar = narrowbar;
    this.pageCode = pageCode;
  }

  public Label() {
  }

  public void setCurrentY(int currentY) {
    this.currentY = currentY;
  }
  
  public void appendText(String text,int size) {
    Text t = new Text(20, currentY, R0, size, text,pageCode);
    currentY += 22;
    components.add(t);
  }

  public void appendText(String text,int size, int originX) {
    Text t = new Text(originX, currentY, R0, size, text,pageCode);
    currentY += 22;
    components.add(t);
  }

  public void appendR90Text(String text, int size) {
    Text t = new Text(width, currentY - 80, R90, size, text,pageCode);
    currentY += 22;
    components.add(t);
  }

  public void appendCode128RsideText(String text, int size) {
    Text t = new Text(width  - 70, currentY - 18, R0, size, text,pageCode);
    components.add(t);
  }

  public void appendCode128RsideText2(String text, int size) {
    Text t = new Text(width  - 70, this.currentY - 40, R0, size, text,pageCode);
    components.add(t);
  }
  
  public void appendBlankLine() {
    currentY += 22;
  }

  public void appendSpace(int space) {
    currentY += space;
  }
  
  public void appendCode128(String code) {
    Code128 code128 = new Code128(20, currentY, R0,widebar, narrowbar,
        barwidth, code);
    currentY += 82;
    components.add(code128);
  }

  public void appendCode128(String code, int originX) {
    Code128 code128 = new Code128(originX, currentY, R0,widebar, narrowbar,
            barwidth, code);
    currentY += 82;
    components.add(code128);
  }

  public void appendCode128WithoutNum(String code) {
    Code128 code128 = new Code128(20, currentY, R0,widebar, narrowbar,
            barwidth, code);
    currentY += 60;
    code128.disableNumbers();
    components.add(code128);
  }

  public void appendCode128WithoutNum(String code, int originX) {
    Code128 code128 = new Code128(originX, currentY, R0,widebar, narrowbar,
            barwidth, code);
    currentY += 60;
    code128.disableNumbers();
    components.add(code128);
  }
  
  public String getCommands() {
    StringBuffer buff = new StringBuffer();
    buff.append('q');
    buff.append(width);
    buff.append("\n\nN\n");
    for (Component c : components) {
      buff.append(c.getCommand());
      buff.append('\n');
    }
    buff.append("P1\n");
    return buff.toString();
  }

  public int getBarwidth() {
    return barwidth;
  }

  public void setBarwidth(int barwidth) {
    this.barwidth = barwidth;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getResolution() {
    return resolution;
  }

  public void setResolution(int resolution) {
    this.resolution = resolution;
  }

  public List<Component> getComponents() {
    return components;
  }

  public void setComponents(List<Component> components) {
    this.components = components;
  }

}
