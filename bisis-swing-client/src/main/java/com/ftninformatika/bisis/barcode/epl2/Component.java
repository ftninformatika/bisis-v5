package com.ftninformatika.bisis.barcode.epl2;

public abstract class Component {
  
  public Component() {
    originX = 0;
    originY = 0;
    rotation = Rotation.R0;
  }
  
  public Component(int originX, int originY, Rotation rotation, int widebar,int narrowbar, int barwidth ) {
    this.originX = originX;
    this.originY = originY;
    this.rotation = rotation;
    this.widebar=widebar;
    this.narrowbar=narrowbar;
    this.barwidth=barwidth;
  }
  public Component(int originX, int originY, Rotation rotation ) {
	    this.originX = originX;
	    this.originY = originY;
	    this.rotation = rotation;
	  }
  
  public abstract String getCommand();
  
  protected Rotation rotation;
  protected int originX;
  protected int originY;
  protected int widebar;
  protected int narrowbar;
  protected int barwidth;
  
  public Rotation getRotation() {
    return rotation;
  }
  public void setRotation(Rotation rotation) {
    this.rotation = rotation;
  }
  public int getOriginX() {
    return originX;
  }
  public void setOriginX(int originX) {
    this.originX = originX;
  }
  public int getOriginY() {
    return originY;
  }
  public void setOriginY(int originY) {
    this.originY = originY;
  }

public int getBarwidth() {
	return barwidth;
}

public void setBarwidth(int barwidth) {
	this.barwidth = barwidth;
}

public int getNarrowbar() {
	return narrowbar;
}

public void setNarrowbar(int narrowbar) {
	this.narrowbar = narrowbar;
}

public int getWidebar() {
	return widebar;
}

public void setWidebar(int widebar) {
	this.widebar = widebar;
}
}
