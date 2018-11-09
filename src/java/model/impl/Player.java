package model.impl;

import model.interfaces.*;

public class Player implements IPlayer {
	
	private Team team;
	private int jerseyNumber;
	private Role role;
	private IPosition position;
	
	public void init(Team team, int jerseyNumber, Role role) {
		this.team = team;
		this.jerseyNumber = jerseyNumber;
		this.role = role;
		this.position = new Position(0,0);
	}
	
	public Team getTeam() {
		return this.team;
	}
	
	public int getJerseyNumber() {
		return this.jerseyNumber;
	}
	
	public Role getRole() {
		return this.role;
	}
	
	public IPosition getPosition() {
		return this.position;
	}
	
	public void setPosition(int x, int y) {
		this.position.setPosition(x,y);
	}

}
