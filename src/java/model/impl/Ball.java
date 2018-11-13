package model.impl;

import model.interfaces.*;

public class Ball implements IBall {

	private IPosition position;
	private IPlayer owner;
	
	public Ball(int x, int y) {
		this.setPosition(x,y);
	}

	public IPosition getPosition() {
		return this.position;
	}

	public void setPosition(int x, int y) {
		this.position = new Position(x,y);
	}
	
	public IPlayer getOwner() {
		return this.owner;
	}
	
	public void setOwner(IPlayer owner) {
		System.out.println("Ball was passed to " + owner.toString());
		this.owner = owner;
	}

}
