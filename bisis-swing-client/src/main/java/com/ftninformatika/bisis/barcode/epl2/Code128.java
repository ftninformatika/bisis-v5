package com.ftninformatika.bisis.barcode.epl2;

public class Code128 extends Component {

  private String defaultNumPrint = "B";

  public Code128() { 
    super();
    code = "";
  }
  
  public Code128(int originX, int originY, Rotation rotation, int widebar, int narrowbar,int barwidth,String code) {
    super(originX, originY, rotation,widebar,narrowbar,barwidth);
    this.code = code;
  }

  public void disableNumbers() {
    defaultNumPrint = "N";
  }
  
  public String getCommand() {
    StringBuffer buff = new StringBuffer();
    buff.append('B');
    buff.append(originX);
    buff.append(',');
    buff.append(originY);
    buff.append(',');
    buff.append(rotation.getCode());
    buff.append(",1,");
    buff.append(widebar);
    buff.append(',');
    buff.append(narrowbar);
    buff.append(',');
    buff.append(barwidth);
    buff.append(",");
    buff.append(defaultNumPrint);
    buff.append(",\"");
    buff.append(code);
    buff.append('\"');
    return buff.toString();
  }
  
  private String code;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
