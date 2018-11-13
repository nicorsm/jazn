package model.impl;

import model.interfaces.IPosition;

public class Position implements IPosition {

	private int x;
	private int y;
	
	public Position(int x, int y) {
		this.setPosition(x,y);
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
}
