package com.ftninformatika.bisis.admin.coders;

public class Column {

  private String name;
  private String caption;
  private int type;
  private int length;
  private int precision;
  private boolean key;
  private boolean visible;

  public Column() {
  }

  public Column(String name, String caption, int type, int length,
      int precision, boolean key) {
    this.name = name;
    this.caption = caption;
    this.type = type;
    this.length = length;
    this.precision = precision;
    this.key = key;
  }

  public Column(String name, String caption, int type, int length,
                int precision, boolean key, boolean visible) {
    this.name = name;
    this.caption = caption;
    this.type = type;
    this.length = length;
    this.precision = precision;
    this.key = key;
    this.visible = visible;
  }

  public String getCaption() {
    return caption;
  }
  public void setCaption(String caption) {
    this.caption = caption;
  }
  public int getLength() {
    return length;
  }
  public void setLength(int length) {
    this.length = length;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getPrecision() {
    return precision;
  }
  public void setPrecision(int precision) {
    this.precision = precision;
  }
  public int getType() {
    return type;
  }
  public void setType(int type) {
    this.type = type;
  }
  public boolean isKey() {
    return key;
  }
  public void setKey(boolean key) {
    this.key = key;
  }
}
