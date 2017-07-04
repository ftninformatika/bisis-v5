package com.ftninformatika.bisis.editor.recordtree;


import com.ftninformatika.bisis.records.Field;

public class IndicatorNode {
	
	
	
	private int index;
	
	private Field owner;
	
	
	

	public IndicatorNode(int index, char value, Field owner) {		
		this.index = index;		
		this.owner = owner;
		if(index==1)
			owner.setInd1(value);
		else owner.setInd2(value);
		
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public char getValue() {
		if(index==1)
			return owner.getInd1();
		else return owner.getInd2();
	}

	public void setValue(char value) {		
		if(index==1)
			owner.setInd1(value);
		else owner.setInd2(value);
		
	}

	public Field getOwner() {
		return owner;
	}

	public void setOwner(Field owner) {
		this.owner = owner;
	}

	

	

}
