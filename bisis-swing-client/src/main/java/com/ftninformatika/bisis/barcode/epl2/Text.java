package com.ftninformatika.bisis.barcode.epl2;

public class Text extends Component {
  
  public Text() {
    super();
    text = "";
    size = 3;
    pageCode="B";
  }
  
  public Text(int originX, int originY, Rotation rotation, int size, String text,String pageCode) {
    super(originX, originY, rotation);
    this.size = size;
    this.text = text;
    if (pageCode.equals("1251")){
    	this.pageCode="C";
    }else{
    	this.pageCode="B";
    }
  }
  
  public String getCommand() {
    StringBuffer buff = new StringBuffer();
    buff.append('I');
    buff.append("8,");
    buff.append(pageCode);
    buff.append(",001");
    buff.append('\n');
    buff.append('A');
    buff.append(originX);
    buff.append(',');
    buff.append(originY);
    buff.append(',');
    buff.append(rotation.getCode());
    buff.append(',');
    buff.append(size);
    buff.append(",1,1,N,\"");
    buff.append(text);
    buff.append('\"');
  
    return buff.toString();
  }
  
  private String text;
  private int size;
  private String pageCode;
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }
  
}
